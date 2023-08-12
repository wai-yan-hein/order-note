/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.report;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileNotFoundException;

/**
 *
 * @author Lenovo
 */
public class ReportUtil {

    public static void orderCheck(String outPath) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(outPath);
//Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
//Add paragraph to the document
        doc.add(new Paragraph("Hello World!"));
//Close document
        doc.close();
    }
}
