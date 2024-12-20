package com.example.assignment2.utils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.example.assignment2.models.DonateSite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class CreatePDF {

    public CreatePDF() {}

    public void createPdf(Context context, String[] data, DonateSite donateSite) {
        // Create a new document
        PdfDocument document = new PdfDocument();

        // Define constants
        int pageWidth = 595;
        int pageHeight = 842;
        int margin = 50;
        int rowHeight = 50;
        int columnWidth = 200;
        int padding = 10;
        int contentHeight = pageHeight - 2 * margin; // Available height for content

        TextPaint paint = new TextPaint();
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2);

        // Define the table headers
        String[] headers = {
                "Site ID",
                "Location",
                "Address",
                "Date",
                "Start time",
                "End time",

                "A collected",
                "A- collected",
                "B collected",
                "B- collected",
                "AB collected",
                "AB- collected",
                "O collected",
                "O- collected",

                "Total collect",
                "Total donor",
                "Total volunteer",
                "Manager",
                "Manager phone"};

        // Track the Y position
        int y = margin;
        int currentPage = 1;

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPage).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Draw the title and date
        drawText(canvas, "Report from " + data[1] + " donation site", margin, y, pageWidth - 2 * margin, paint);
        y += rowHeight;
        drawText(canvas, "Date: " + data[3], margin, y, pageWidth - 2 * margin, paint);
        y += rowHeight;

        // Draw the headers and data
        for (int i = 0; i < headers.length; i++) {
            // Check if the content exceeds the page height
            if (y + rowHeight > contentHeight + margin) {
                // Finish the current page
                document.finishPage(page);

                // Start a new page
                currentPage++;
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPage).create();
                page = document.startPage(pageInfo);
                canvas = page.getCanvas();
                y = margin;

                // Redraw headers on the new page
                for (int j = i; j < headers.length; j++) {
                    drawText(canvas, headers[j], margin + (j * columnWidth) + padding, y + padding + 10, columnWidth, paint);
                    canvas.drawRect(margin + (j * columnWidth), y, margin + (j + 1) * columnWidth, y + rowHeight, borderPaint);
                }
                y += rowHeight; // Move Y position below headers
            }

            // Draw the header text
            drawText(canvas, headers[i], margin + padding, y + padding + 10, columnWidth, paint);
            canvas.drawRect(margin, y, margin + columnWidth, y + rowHeight, borderPaint);

            // Draw the corresponding data
            drawText(canvas, data[i], margin + columnWidth + padding, y + padding + 10, columnWidth, paint);
            canvas.drawRect(margin + columnWidth, y, margin + 2 * columnWidth, y + rowHeight, borderPaint);

            y += rowHeight; // Move Y position for the next row
        }

        // Finish the last page
        document.finishPage(page);

        // Save the document to a file
        String fileName = removeVietnameseSymbols(donateSite.getName());
        String site = fileName.replace(" ", "_");
        String date = donateSite.getDate().replace("/", "_");
        File file = new File(context.getExternalFilesDir(null), site + "_" + date + ".pdf");

        try {
            document.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the document
        document.close();
    }

    private void drawText(Canvas canvas, String text, int x, int y, int width, TextPaint paint) {
        StaticLayout staticLayout = new StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(x, y);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    public static String removeVietnameseSymbols(String input) {
        // Normalize the string to decompose the characters
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Remove diacritical marks using a regular expression
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized).replaceAll("");
        // Replace special Vietnamese characters
        result = result.replaceAll("Đ", "D").replaceAll("đ", "d");
        return result;
    }
}