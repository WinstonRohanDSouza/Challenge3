package com.example.fhirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DocumentFullscreen extends AppCompatActivity {

    ImageView fullscreenimage;
    PDFView pdfView2;
    String DocumentAddress, docType;
    byte[] diagnosticreportByte;
    TextView textfullscreen;
    Uri mUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_fullscreen);

        Log.d("Inside_Activity_the4","DocumentFullscreen");

        fullscreenimage = findViewById(R.id.fullscreenimage);
        pdfView2 = findViewById(R.id.pdfView2);
        textfullscreen = findViewById(R.id.textfullscreen);


        DocumentAddress = getIntent().getStringExtra("DocumentAddress");
        diagnosticreportByte = getIntent().getByteArrayExtra("diagnosticreportByte");
        String docType = getIntent().getStringExtra("DocType");


        if (DocumentAddress !=null){
            mUri = Uri.parse(DocumentAddress);
        }





        if (docType.contains("image"))
        {
            fullscreenimage.setVisibility(View.VISIBLE);
            fullscreenimage.setImageURI(mUri);
            PhotoViewAttacher pAttacher;
            pAttacher = new PhotoViewAttacher(fullscreenimage);
            pAttacher.update();
        }
        else if (docType.contains("application/pdf"))
        {
            pdfView2.setVisibility(View.VISIBLE);

            if (DocumentAddress !=null){
                pdfView2.fromUri(mUri).load();
            }else  if (diagnosticreportByte !=null){
                pdfView2.fromBytes(diagnosticreportByte).load();
            }

        }
        else if (docType.equals("text/plain"))
        {
            String text = null;
            try {
                InputStream in = getContentResolver().openInputStream(mUri);


                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }

                 text = total.toString();



            }catch (Exception e) {
                //Do nothing

            }
            textfullscreen.setText(text);
            textfullscreen.setVisibility(View.VISIBLE);
            textfullscreen.setMovementMethod(new ScrollingMovementMethod());

        }



//         DEFINE DOC VIEW HERE
//        if (docType.contains("officedocument"))
//        {
//            if (docType.contains("wordprocessingml"))
//            {
//
//            }else if (docType.contains("spreadsheetml"))
//            {
//
//            }
//
//        }

    }
}