import { Injectable } from '@angular/core';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

@Injectable({
  providedIn: 'root'
})
export class ExportService {

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

  // Export multi-onglets (très utile pour Dashboard)
  exportMultipleSheets(sheets: { name: string; data: any[] }[], filename: string): void {
    const wb = XLSX.utils.book_new();

    sheets.forEach(sheet => {
      const ws = XLSX.utils.json_to_sheet(sheet.data);
      XLSX.utils.book_append_sheet(wb, ws, sheet.name.substring(0, 31)); // Max 31 caractères
    });

    const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const blob = new Blob([excelBuffer], { 
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' 
    });
    saveAs(blob, `${filename}.xlsx`);
  }

  // ====================== PDF ======================

  async exportElementToPDF(elementId: string, filename: string = 'export'): Promise<void> {
    const element = document.getElementById(elementId);
    if (!element) {
      alert('Élément non trouvé pour l\'export PDF');
      return;
    }

    try {
      const canvas = await html2canvas(element, {
        scale: 2,
        useCORS: true,
        backgroundColor: '#ffffff',
        logging: false
      });

      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4');
      const pageWidth = pdf.internal.pageSize.getWidth();
      const pageHeight = pdf.internal.pageSize.getHeight();

      const imgWidth = canvas.width;
      const imgHeight = canvas.height;
      const ratio = Math.min((pageWidth - 20) / imgWidth, (pageHeight - 40) / imgHeight);

      const finalWidth = imgWidth * ratio;
      const finalHeight = imgHeight * ratio;

      pdf.setFontSize(16);
      pdf.text('Plateforme Décisionnelle BEA', 20, 20);
      pdf.setFontSize(11);
      pdf.text(`Généré le : ${new Date().toLocaleDateString('fr-FR')} à ${new Date().toLocaleTimeString('fr-FR')}`, 20, 30);

      pdf.addImage(imgData, 'PNG', 10, 40, finalWidth, finalHeight);
      pdf.save(`${filename}.pdf`);

    } catch (error) {
      console.error('Erreur export PDF:', error);
      alert('Erreur lors de la génération du PDF');
    }
  }
}