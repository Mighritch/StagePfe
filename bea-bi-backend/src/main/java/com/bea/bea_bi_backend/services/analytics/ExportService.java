package com.bea.bea_bi_backend.services.analytics;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service générique pour l'export de données en Excel (.xlsx) et PDF.
 * <p>
 * — Excel  : généré via Apache POI avec mise en forme (en-tête coloré, bandes alternées,
 *             ligne de totaux si applicable, ajustement automatique des colonnes).
 * — PDF    : généré via JasperReports à partir d'un template JRXML placé dans
 *             src/main/resources/reports/<templateName>.jrxml.
 *             Si le template est absent, un fallback HTML→PDF minimal est utilisé.
 * </p>
 */
@Service
public class ExportService {

    // ─────────────────────────────────────────────────────────────────────────
    //  EXCEL
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Génère un classeur Excel (.xlsx) à partir d'une liste de beans.
     *
     * @param data        liste de beans (entités JPA / vues)
     * @param sheetTitle  titre affiché dans la première ligne du tableau
     * @param columns     noms des colonnes (doivent correspondre aux noms de champs Java)
     * @param headers     libellés d'en-tête affichés dans la feuille
     * @return tableau d'octets du fichier .xlsx
     */
    public byte[] exportToExcel(List<?> data, String sheetTitle, String[] columns, String[] headers) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetTitle);

            // ── Styles ──────────────────────────────────────────────────────
            CellStyle titleStyle   = buildTitleStyle(workbook);
            CellStyle headerStyle  = buildHeaderStyle(workbook);
            CellStyle dataStyle    = buildDataStyle(workbook, false);
            CellStyle altDataStyle = buildDataStyle(workbook, true);
            CellStyle dateStyle    = buildDateStyle(workbook);

            // ── Ligne de titre ──────────────────────────────────────────────
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(sheetTitle + "  —  " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
            titleRow.setHeightInPoints(28);

            // ── En-têtes de colonnes ────────────────────────────────────────
            Row headerRow = sheet.createRow(1);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            headerRow.setHeightInPoints(20);

            // ── Lignes de données ───────────────────────────────────────────
            int rowNum = 2;
            for (Object item : data) {
                Row row = sheet.createRow(rowNum);
                boolean alt = (rowNum % 2 == 0);
                for (int col = 0; col < columns.length; col++) {
                    Cell cell = row.createCell(col);
                    Object value = getFieldValue(item, columns[col]);
                    setCellValue(cell, value, alt ? altDataStyle : dataStyle, dateStyle, workbook);
                }
                rowNum++;
            }

            // ── Auto-sizing colonnes ────────────────────────────────────────
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                // Largeur minimale de 12 caractères
                if (sheet.getColumnWidth(i) < 3072) {
                    sheet.setColumnWidth(i, 3072);
                }
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération Excel : " + e.getMessage(), e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  PDF via JasperReports
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Génère un PDF via un template JasperReports (.jrxml).
     *
     * @param data         liste de beans passée en datasource
     * @param templateName nom du fichier JRXML (sans extension) dans resources/reports/
     * @param parameters   paramètres additionnels passés au rapport (titre, filtres…)
     * @return tableau d'octets du fichier .pdf
     */
    public byte[] exportToPdf(List<?> data, String templateName, Map<String, Object> parameters) {
        try {
            String resourcePath = "/reports/" + templateName + ".jrxml";
            InputStream templateStream = getClass().getResourceAsStream(resourcePath);

            if (templateStream == null) {
                // Fallback : rapport simple généré programmatiquement
                return generateFallbackPdf(data, templateName, parameters);
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

            Map<String, Object> params = new HashMap<>(parameters);
            params.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération PDF : " + e.getMessage(), e);
        }
    }

    /**
     * Génère un PDF minimaliste via JasperReports sans template externe.
     * Utilisé en fallback quand le .jrxml n'est pas trouvé.
     */
    private byte[] generateFallbackPdf(List<?> data, String title, Map<String, Object> parameters) {
        try {
            // Création d'un rapport dynamique via JasperReports API
            net.sf.jasperreports.engine.design.JasperDesign design =
                    new net.sf.jasperreports.engine.design.JasperDesign();
            design.setName(title);
            design.setPageWidth(842);   // A4 landscape
            design.setPageHeight(595);
            design.setLeftMargin(30);
            design.setRightMargin(30);
            design.setTopMargin(30);
            design.setBottomMargin(30);
            design.setLanguage(JRReport.LANGUAGE_JAVA);

            // Champs dynamiques depuis le premier élément
            if (!data.isEmpty()) {
                for (Field field : data.get(0).getClass().getDeclaredFields()) {
                    net.sf.jasperreports.engine.design.JRDesignField jrField =
                            new net.sf.jasperreports.engine.design.JRDesignField();
                    jrField.setName(field.getName());
                    jrField.setValueClass(Object.class);
                    design.addField(jrField);
                }
            }

            // Bande de titre
            net.sf.jasperreports.engine.design.JRDesignBand titleBand =
                    new net.sf.jasperreports.engine.design.JRDesignBand();
            titleBand.setHeight(40);
            net.sf.jasperreports.engine.design.JRDesignStaticText titleText =
                    new net.sf.jasperreports.engine.design.JRDesignStaticText();
            titleText.setText(title + " — Export " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            titleText.setX(0);
            titleText.setY(0);
            titleText.setWidth(782);
            titleText.setHeight(30);
            titleText.setFontSize(14f);
            titleText.setBold(true);
            titleBand.addElement(titleText);
            design.setTitle(titleBand);

            JasperReport compiled = JasperCompileManager.compileReport(design);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);
            JasperPrint print = JasperFillManager.fillReport(compiled, parameters, ds);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur fallback PDF : " + e.getMessage(), e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  HELPERS — Reflection
    // ─────────────────────────────────────────────────────────────────────────

    private Object getFieldValue(Object obj, String fieldName) {
        try {
            // Cherche dans la classe et les superclasses
            Class<?> clazz = obj.getClass();
            while (clazz != null) {
                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field.get(obj);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private void setCellValue(Cell cell, Object value, CellStyle defaultStyle,
                              CellStyle dateStyle, Workbook workbook) {
        if (value == null) {
            cell.setCellValue("");
            cell.setCellStyle(defaultStyle);
        } else if (value instanceof Number num) {
            cell.setCellValue(num.doubleValue());
            cell.setCellStyle(defaultStyle);
        } else if (value instanceof Boolean b) {
            cell.setCellValue(b);
            cell.setCellStyle(defaultStyle);
        } else if (value instanceof java.util.Date d) {
            cell.setCellValue(d);
            cell.setCellStyle(dateStyle);
        } else if (value instanceof java.time.LocalDate ld) {
            cell.setCellValue(java.sql.Date.valueOf(ld));
            cell.setCellStyle(dateStyle);
        } else {
            cell.setCellValue(value.toString());
            cell.setCellStyle(defaultStyle);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  HELPERS — Styles Excel
    // ─────────────────────────────────────────────────────────────────────────

    private CellStyle buildTitleStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 13);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(false);
        return style;
    }

    private CellStyle buildHeaderStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorderThin(style);
        return style;
    }

    private CellStyle buildDataStyle(Workbook wb, boolean alternate) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 9);
        style.setFont(font);
        if (alternate) {
            style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorderThin(style);
        return style;
    }

    private CellStyle buildDateStyle(Workbook wb) {
        CellStyle style = buildDataStyle(wb, false);
        CreationHelper ch = wb.getCreationHelper();
        style.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));
        return style;
    }

    private void setBorderThin(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}