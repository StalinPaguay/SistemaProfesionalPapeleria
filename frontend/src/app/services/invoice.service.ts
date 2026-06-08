import { Injectable } from '@angular/core';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  constructor() { }

  generateInvoice(venta: any, userName: string) {
    const doc = new jsPDF();
    
    // Header
    doc.setFontSize(22);
    doc.setTextColor(59, 130, 246); // Blue 500
    doc.text('PaperDS', 14, 20);
    
    doc.setFontSize(10);
    doc.setTextColor(100);
    doc.text('Sistema de Gestión Empresarial', 14, 26);
    
    // Invoice Title & Info
    doc.setFontSize(16);
    doc.setTextColor(40);
    doc.text('FACTURA DE COMPRA', 14, 40);
    
    doc.setFontSize(10);
    doc.text(`Fecha: ${new Date().toLocaleDateString()} ${new Date().toLocaleTimeString()}`, 14, 48);
    doc.text(`Cliente: ${userName}`, 14, 54);
    
    // Prepare table data
    const tableColumn = ["Producto", "Cantidad", "Precio Unit.", "Subtotal"];
    const tableRows: any[] = [];
    
    let subtotal = 0;
    
    venta.detalles.forEach((item: any) => {
      const itemData = [
        item.productoNombre,
        item.cantidad.toString(),
        `$${item.precioUnitario.toFixed(2)}`,
        `$${item.subtotal.toFixed(2)}`
      ];
      tableRows.push(itemData);
      subtotal += item.subtotal;
    });
    
    // Add Table
    autoTable(doc, {
      head: [tableColumn],
      body: tableRows,
      startY: 65,
      theme: 'grid',
      styles: { fontSize: 10, cellPadding: 4 },
      headStyles: { fillColor: [59, 130, 246] }
    });
    
    // Add Totals
    const finalY = (doc as any).lastAutoTable.finalY + 10;
    
    const iva = subtotal * 0.16; // 16% IVA por defecto (se puede cambiar)
    const total = subtotal + iva;
    
    doc.setFontSize(11);
    doc.text(`Subtotal: $${subtotal.toFixed(2)}`, 140, finalY);
    doc.text(`IVA (16%): $${iva.toFixed(2)}`, 140, finalY + 7);
    
    doc.setFontSize(14);
    doc.setTextColor(17, 24, 39); // Gray 900
    doc.text(`TOTAL: $${total.toFixed(2)}`, 140, finalY + 16);
    
    // Footer
    doc.setFontSize(10);
    doc.setTextColor(150);
    doc.text('¡Gracias por tu compra en PaperDS!', 105, 280, { align: 'center' });
    
    // Download PDF
    doc.save(`Factura_PaperDS_${new Date().getTime()}.pdf`);
  }
}
