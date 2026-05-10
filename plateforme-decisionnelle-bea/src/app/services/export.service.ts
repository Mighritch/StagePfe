import { Injectable } from '@angular/core';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

@Injectable({
  providedIn: 'root'
})
export class ExportService {

  // ── Palette de marque ─────────────────────────────────────────────────
  private readonly BRAND_PRIMARY   = '#1a3a5c'; // Bleu marine foncé
  private readonly BRAND_SECONDARY = '#2e6da4'; // Bleu moyen
  private readonly BRAND_ACCENT    = '#378ADD'; // Bleu clair (couleur chart)
  private readonly BRAND_LIGHT     = '#e8f0fb'; // Bleu très clair (fond bande)
  private readonly TEXT_DARK       = '#1e293b';
  private readonly TEXT_MUTED      = '#64748b';

  // ── Marges et dimensions A4 (mm) ──────────────────────────────────────
  private readonly MARGIN_LEFT   = 15;
  private readonly MARGIN_RIGHT  = 15;
  private readonly HEADER_HEIGHT = 42; // espace réservé en haut
  private readonly FOOTER_HEIGHT = 14; // espace réservé en bas

  // ====================== EXCEL ======================

  exportToExcel(data: any[], filename: string, sheetName = 'Données'): void {
    const ws = XLSX.utils.json_to_sheet(data);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, sheetName);

    const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const blob = new Blob([excelBuffer], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    saveAs(blob, `${filename}.xlsx`);
  }

  exportMultipleSheets(sheets: { name: string; data: any[] }[], filename: string): void {
    const wb = XLSX.utils.book_new();

    sheets.forEach(sheet => {
      const ws = XLSX.utils.json_to_sheet(sheet.data);
      XLSX.utils.book_append_sheet(wb, ws, sheet.name.substring(0, 31));
    });

    const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const blob = new Blob([excelBuffer], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    saveAs(blob, `${filename}.xlsx`);
  }

  // ====================== PDF ======================

  /**
   * Exporte un élément HTML en PDF multi-pages avec mise en page professionnelle.
   *
   * @param elementId   ID de l'élément HTML à capturer
   * @param filename    Nom du fichier de sortie (sans extension)
   * @param sectionTitle Titre affiché dans l'en-tête (ex : "Analyse des Absences")
   */
  async exportElementToPDF(
    elementId: string,
    filename: string = 'export',
    sectionTitle: string = 'Rapport analytique'
  ): Promise<void> {
    const element = document.getElementById(elementId);
    if (!element) {
      alert('Élément introuvable pour l\'export PDF.');
      return;
    }

    // ── Masquer temporairement les boutons d'export dans la capture ──────
    const exportButtons = element.querySelectorAll<HTMLElement>('.btn-export, .export-buttons');
    exportButtons.forEach(el => (el.style.display = 'none'));

    try {
      // ── Capture haute résolution ──────────────────────────────────────
      const canvas = await html2canvas(element, {
        scale: 3,
        useCORS: true,
        backgroundColor: '#ffffff',
        logging: false,
        scrollX: 0,
        scrollY: -window.scrollY,
        windowWidth: element.scrollWidth,
        windowHeight: element.scrollHeight,
      });

      // ── Rétablir les boutons ──────────────────────────────────────────
      exportButtons.forEach(el => (el.style.display = ''));

      const pdf = new jsPDF('p', 'mm', 'a4');
      const pageW = pdf.internal.pageSize.getWidth();   // 210 mm
      const pageH = pdf.internal.pageSize.getHeight();  // 297 mm

      const contentW = pageW - this.MARGIN_LEFT - this.MARGIN_RIGHT;
      const usableH  = pageH - this.HEADER_HEIGHT - this.FOOTER_HEIGHT;

      // ── Calculer le ratio px → mm ────────────────────────────────────
      const pxPerMm  = canvas.width / contentW;
      const sliceHpx = usableH * pxPerMm;         // hauteur d'une tranche en px
      const totalPages = Math.ceil(canvas.height / sliceHpx);

      for (let page = 0; page < totalPages; page++) {
        if (page > 0) pdf.addPage();

        this._drawHeader(pdf, pageW, sectionTitle, filename);
        this._drawFooter(pdf, pageW, pageH, page + 1, totalPages);
        this._drawWatermark(pdf, pageW, pageH);

        // ── Découper et coller la tranche ────────────────────────────
        const srcY = page * sliceHpx;
        const srcH = Math.min(sliceHpx, canvas.height - srcY);

        const sliceCanvas = document.createElement('canvas');
        sliceCanvas.width  = canvas.width;
        sliceCanvas.height = srcH;
        const ctx = sliceCanvas.getContext('2d')!;
        ctx.drawImage(canvas, 0, -srcY);

        const sliceData   = sliceCanvas.toDataURL('image/jpeg', 0.95);
        const sliceHeightMm = srcH / pxPerMm;

        pdf.addImage(
          sliceData,
          'JPEG',
          this.MARGIN_LEFT,
          this.HEADER_HEIGHT,
          contentW,
          sliceHeightMm
        );
      }

      pdf.save(`${filename}.pdf`);

    } catch (error) {
      exportButtons.forEach(el => (el.style.display = ''));
      console.error('Erreur export PDF :', error);
      alert('Erreur lors de la génération du PDF. Consultez la console pour plus de détails.');
    }
  }

  // ── En-tête ────────────────────────────────────────────────────────────

  private _drawHeader(pdf: jsPDF, pageW: number, sectionTitle: string, filename: string): void {
    // Bande de fond
    this._setFill(pdf, this.BRAND_PRIMARY);
    pdf.rect(0, 0, pageW, 28, 'F');

    // Bande accent fine
    this._setFill(pdf, this.BRAND_ACCENT);
    pdf.rect(0, 28, pageW, 2, 'F');

    // Logo / nom de l'organisme
    pdf.setFont('helvetica', 'bold');
    pdf.setFontSize(13);
    this._setTextColor(pdf, '#ffffff');
    pdf.text('BEA', this.MARGIN_LEFT, 11);

    pdf.setFont('helvetica', 'normal');
    pdf.setFontSize(8.5);
    this._setTextColor(pdf, '#b8d4f0');
    pdf.text('Plateforme Décisionnelle RH', this.MARGIN_LEFT, 17);

    // Titre de section (centré)
    pdf.setFont('helvetica', 'bold');
    pdf.setFontSize(12);
    this._setTextColor(pdf, '#ffffff');
    const titleW = pdf.getStringUnitWidth(sectionTitle) * 12 / pdf.internal.scaleFactor;
    pdf.text(sectionTitle, (pageW - titleW) / 2, 13);

    // Date & heure (alignées à droite)
    const now  = new Date();
    const date = now.toLocaleDateString('fr-FR');
    const time = now.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
    pdf.setFont('helvetica', 'normal');
    pdf.setFontSize(7.5);
    this._setTextColor(pdf, '#b8d4f0');
    pdf.text(`Généré le ${date} à ${time}`, pageW - this.MARGIN_RIGHT, 10, { align: 'right' });

    // Nom du fichier (référence)
    pdf.setFontSize(7);
    this._setTextColor(pdf, '#7aaed6');
    pdf.text(`Réf : ${filename}`, pageW - this.MARGIN_RIGHT, 16, { align: 'right' });

    // Ligne de séparation sous le header
    this._setDraw(pdf, this.BRAND_LIGHT);
    pdf.setLineWidth(0.3);
    pdf.line(this.MARGIN_LEFT, 32, pageW - this.MARGIN_RIGHT, 32);
  }

  // ── Pied de page ──────────────────────────────────────────────────────

  private _drawFooter(pdf: jsPDF, pageW: number, pageH: number, current: number, total: number): void {
    const y = pageH - this.FOOTER_HEIGHT + 4;

    // Ligne de séparation
    this._setDraw(pdf, '#cbd5e1');
    pdf.setLineWidth(0.3);
    pdf.line(this.MARGIN_LEFT, y - 2, pageW - this.MARGIN_RIGHT, y - 2);

    // Texte gauche
    pdf.setFont('helvetica', 'normal');
    pdf.setFontSize(7.5);
    this._setTextColor(pdf, this.TEXT_MUTED);
    pdf.text('© BEA — Document confidentiel, usage interne uniquement', this.MARGIN_LEFT, y + 3);

    // Numéro de page (droite)
    pdf.setFont('helvetica', 'bold');
    pdf.setFontSize(7.5);
    this._setTextColor(pdf, this.BRAND_SECONDARY);
    pdf.text(`Page ${current} / ${total}`, pageW - this.MARGIN_RIGHT, y + 3, { align: 'right' });
  }

  // ── Filigrane discret ─────────────────────────────────────────────────

  private _drawWatermark(pdf: jsPDF, pageW: number, pageH: number): void {
    pdf.saveGraphicsState();
    // Opacité simulée via une couleur très claire
    pdf.setFont('helvetica', 'bold');
    pdf.setFontSize(52);
    this._setTextColor(pdf, '#f0f4f8');
    pdf.text('BEA', pageW / 2, pageH / 2, {
      align: 'center',
      angle: 45,
    });
    pdf.restoreGraphicsState();
  }

  // ── Helpers de couleur (jsPDF attend des valeurs 0-255) ───────────────

  private _hexToRgb(hex: string): [number, number, number] {
    const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result
      ? [parseInt(result[1], 16), parseInt(result[2], 16), parseInt(result[3], 16)]
      : [0, 0, 0];
  }

  private _setFill(pdf: jsPDF, hex: string): void {
    const [r, g, b] = this._hexToRgb(hex);
    pdf.setFillColor(r, g, b);
  }

  private _setDraw(pdf: jsPDF, hex: string): void {
    const [r, g, b] = this._hexToRgb(hex);
    pdf.setDrawColor(r, g, b);
  }

  private _setTextColor(pdf: jsPDF, hex: string): void {
    const [r, g, b] = this._hexToRgb(hex);
    pdf.setTextColor(r, g, b);
  }
}