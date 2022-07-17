package com.example.fhirapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class DocumentRecyclerViewAdapter extends RecyclerView.Adapter<DocumentRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "DocumentRecyclerViewAdapter";

    private List<String> titleValues = new ArrayList<>();
    private List<String> dateValues = new ArrayList<>();
    private List<String> fileLocation = new ArrayList<>();
    private Context mContext;

    private static final int PICK_PDF_FILE = 2;

    public DocumentRecyclerViewAdapter(List<String> titleValues, List<String> dateValues, Context mContext, List<String> fileLocation) {
        this.titleValues = titleValues;
        this.dateValues = dateValues;
        this.mContext = mContext;
        this.fileLocation = fileLocation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DocumentRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.title.setText(titleValues.get(position));
        holder.date.setText(dateValues.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewFile.class);
                intent.putExtra("fileLocation", fileLocation.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardview_title);
            date = itemView.findViewById(R.id.cardview_date);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
