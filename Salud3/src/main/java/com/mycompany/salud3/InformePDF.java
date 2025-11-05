package com.mycompany.salud3;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.File;

public class InformePDF {
    public static void main(String[] args) throws Exception {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream cs = new PDPageContentStream(doc, page);

        PDFont font = PDType1Font.HELVETICA_BOLD;
        cs.beginText();
        cs.setFont(font, 16);
        cs.newLineAtOffset(50, 700);
        cs.showText("Informe de Entrenamientos");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA, 12);
        cs.newLineAtOffset(50, 670);
        cs.showText("Fecha: 2025-11-05 | Duración: 45 min | Calorías: 360");
        cs.endText();

        try {
            PDImageXObject img = PDImageXObject.createFromFile("grafico_barras.jpg", doc);
            cs.drawImage(img, 50, 450, 400, 250);
        } catch (Exception e) {
            System.out.println("No se encontró grafico_barras.jpg, se omitirá la imagen.");
        }

        cs.close();
        doc.save("informe_entrenos.pdf");
        doc.close();

        System.out.println("PDF generado correctamente.");
    }
}