package com.example.fhirapp;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.MyViewHolder> {


    Context context;
    RealmResults<FileNotes> notesList;

    OnImageClick mListner;

    public DocumentsAdapter(Context context, RealmResults<FileNotes> notesList, final OnImageClick mListner) {
        this.context = context;
        this.notesList = notesList;
        this.mListner = mListner ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false), mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FileNotes fileNotes = notesList.get(position);

        holder.titleoutput.setText(fileNotes.getTitle());
        holder.titledescription.setText(fileNotes.getDescription());

        String docType = null;

        String formatTime = DateFormat.getDateTimeInstance().format(fileNotes.createdtime);
        holder.timeoutput.setText(formatTime);

        int clickType = 0;

        String pdfAddress = fileNotes.getPdfAddress();
        byte[] diagnosticreportByte = fileNotes.getDiagnosticPdfReport();

        Log.d("Inside_Activity_the1","onBindViewHolder");

        try {
            docType= fileNotes.getDocType();
            String[] docTypeSplit = docType.split("/");
            String docTypeSplitvalue= docTypeSplit[0];

            if (docType.contains("image"))
            {
                holder.documentType.setText("Image");
            }
            else if (docType.equals("application/pdf"))
            {
                holder.documentType.setText("Pdf");
            }
            else if (docType.contains("wordprocessingml"))
            {
                holder.documentType.setText("Word Doc");
            }
            else if (docType.contains("spreadsheetml"))
            {
                holder.documentType.setText("Excel Doc");
            }
            else if (docType.contains("text/plain"))
            {
                holder.documentType.setText("Text Doc");
            }
            else if (docType.contains("comma-separated-values"))
            {
                holder.documentType.setText("CSV Doc");
            }

            else
            {
                holder.documentType.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



      //  Log.e("docType",docType);


        String finalDocType = docType;
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                int clickType = 1;
                mListner.displayFullScreenImage(notesList,pdfAddress, clickType, finalDocType,diagnosticreportByte);



               return true;
            }
        });

        String finalDocType1 = docType;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickType = 0;
                Log.d("Inside_Activity_the2","onClick");
                mListner.displayFullScreenImage(notesList,pdfAddress,clickType, finalDocType1,diagnosticreportByte);

            }
        });



    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleoutput,documentType;
        TextView titledescription;
        TextView timeoutput;
        //ImageView viewImage;
        OnImageClick mListner;



        public MyViewHolder(@NonNull View itemView, OnImageClick mListner) {
            super(itemView);

            this.mListner = mListner;
            titleoutput = itemView.findViewById(R.id.titleoutput);
            titledescription = itemView.findViewById(R.id.descriptionoutput);
            timeoutput = itemView.findViewById(R.id.timeoutput);
            //viewImage = itemView.findViewById(R.id.viewimageButton);
            documentType =itemView.findViewById(R.id.documentType);
        }
    }

interface OnImageClick{
        void displayFullScreenImage(RealmResults<FileNotes> notesList,  String pdfAddress, int clickType, String docType,byte[] diagnosticreportByte);


}



}
