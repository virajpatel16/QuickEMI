package com.example.emi_master;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {
    private TextView principalAmountTextView;
    private TextView totalAmountTextView;
    private TextView tvInterestRate;
    private TextView etTenure;
    private ImageView img_pdf;
    private TextView tvEMI;
    private TextView tvTotalInterest;
    private TextView tvFeesCharges;
    private TextView tvTotalPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        principalAmountTextView = findViewById(R.id.principalAmountTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        tvInterestRate = findViewById(R.id.tvInterestRate);
        etTenure = findViewById(R.id.et_tenure);
        tvEMI = findViewById(R.id.tvEMI);
        img_pdf = findViewById(R.id.img_pdf);
        tvTotalInterest = findViewById(R.id.tvTotalInterest);
        tvFeesCharges = findViewById(R.id.tvFeesCharges);
        tvTotalPayment = findViewById(R.id.tvTotalPayment);


        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to generate our PDF file.
                generatePdfFromScreenshot();
            }
        });
        Intent intent = getIntent();

        if (intent != null) {
            String principalAmount = intent.getStringExtra("principal");
            String totalAmount = intent.getStringExtra("tvPrincipalAmt");
            String interestRate = intent.getStringExtra("interest");
            String tenure = intent.getStringExtra("tenure");
            String emi = intent.getStringExtra("emi");
            String totalInterest = intent.getStringExtra("total_interest");
            String feesCharges = intent.getStringExtra("fees");
            String totalPayment = intent.getStringExtra("tvTotalPayAmt");

            // Set the values to the TextViews
            principalAmountTextView.setText(principalAmount);
            totalAmountTextView.setText(totalAmount);
            tvInterestRate.setText(interestRate);
            etTenure.setText(tenure);
            tvEMI.setText(emi);
            tvTotalInterest.setText(totalInterest);
            tvFeesCharges.setText(feesCharges);
            tvTotalPayment.setText(totalPayment);
        }
    }

    private void generatePdfFromScreenshot() {
        // Find the root view of your layout
        LinearLayout rootView = findViewById(R.id.emi_details_container);  // Adjust this to your root view's ID

        // Create a bitmap from the root view
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);

        if (bitmap == null) {
            Log.e("generatePdfFromScreenshot", "Bitmap is null");
            return;
        }

        // Create a new PDF document
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Draw the bitmap onto the PDF page's canvas
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, null);

        // Finish the page
        pdfDocument.finishPage(page);

        // Save the PDF document to a file
        File directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (directory!= null) {
            if (!directory.exists() &&!directory.mkdirs()) {
                Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return;
            }

            // Generate a unique file name
            String fileName = "EmiDetails_" + System.currentTimeMillis() + ".pdf";
            File file = new File(directory, fileName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                pdfDocument.writeTo(fos);
                Toast.makeText(this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();

                // Show dialog to open or dismiss PDF
                showOpenPdfDialog(file);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to generate PDF file.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "External storage not available", Toast.LENGTH_SHORT).show();
        }

        // Close the PDF document
        pdfDocument.close();
    }
    private void showOpenPdfDialog(final File file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PDF Generated")
                .setMessage("Do you want to open the PDF file?")
                .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri pdfUri = FileProvider.getUriForFile(DetailsActivity.this, getApplicationContext().getPackageName() + ".provider", file);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(pdfUri, "application/pdf");
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}