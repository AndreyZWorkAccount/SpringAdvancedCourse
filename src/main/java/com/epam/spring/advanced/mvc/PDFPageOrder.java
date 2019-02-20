package com.epam.spring.advanced.mvc;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.epam.spring.core.model.Order;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFPageOrder extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest req,
            HttpServletResponse resp) throws Exception {

        List<Order> listOrders = (List<Order>) model.get("orders");
        
        doc.add(new Paragraph("Orders list"));
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f, 1.0f });
        table.setSpacingBefore(10);

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Order id", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("User", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Event", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Seat", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);

        // write table row data
        for (Order order : listOrders) {
            table.addCell(String.valueOf(order.getId()));
            table.addCell(String.valueOf(order.getUser().getId()) + " " + order.getUser().getName());
            table.addCell(String.valueOf(order.getEvent().getId()) + " " + order.getEvent().getName());
            table.addCell(String.valueOf(order.getSeat()));
            table.addCell(String.valueOf(order.getPrice()));
        }

        doc.add(table);
    }

}
