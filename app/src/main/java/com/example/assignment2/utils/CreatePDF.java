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

        // Create a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();

        // Start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
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
                "Priority blood type",
                "Total collect",
                "Total donor",
                "Total volunteer",
                "Manager",
                "Manager phone"};

        // Set the starting position for the table
        int x = 100;
        int y = 120;
        int rowHeight = 50;
        int columnWidth = 200;
        int padding = 10;

        drawText(canvas, "Report from " + data[1] + " donation site", 180, 50, 500, paint);

        drawText(canvas, "Date: " + data[3], 250, 80, 500, paint);


        // Draw the table headers
        for (int i = 0; i < headers.length; i++) {
            drawText(canvas, headers[i], x + padding, y + (i * rowHeight) + padding + 10, columnWidth, paint);
            // Draw the border for the header cell
            canvas.drawRect(x, y + (i * rowHeight), x + columnWidth, y + ((i + 1) * rowHeight), borderPaint);
        }

        // Draw the table data
        for (int i = 0; i < data.length; i++) {
            drawText(canvas, data[i], x + columnWidth + padding, y + (i * rowHeight) + padding + 10, columnWidth, paint);
            // Draw the border for the data cell
            canvas.drawRect(x + columnWidth, y + (i * rowHeight), x + 2 * columnWidth, y + ((i + 1) * rowHeight), borderPaint);
        }

        // Finish the page
        document.finishPage(page);
        String fileName = removeVietnameseSymbols(donateSite.getName());
        String site = fileName.replace(" ","_");
        String date = donateSite.getDate().replace("/", "_");
        // Save the document in the app-specific storage directory
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