package com.example.fhirapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private List<String> titleValues = new ArrayList<>();
    private List<String> dateValues = new ArrayList<>();
    private Context mContext;
    private List<Encounter> encounterList;
    private List<DiagnosticReport> diagnosticReportList;
    private List<Allergy> allergyList;
    private List<Medications> medicationsList;
    private List<Appointment> appointmentList;
    private String type;

    public RecyclerViewAdapter(List<String> titleValues, List<String> dateValues, Context mContext, List<Encounter> encounterList, List<DiagnosticReport> diagnosticReportList, List<Allergy> allergyList, List<Medications> medicationsList, List<Appointment> appointmentList, String type, int size) {

        this.type = type;
        this.mContext = mContext;
        if(size == -1){
            this.titleValues = titleValues;
            this.dateValues = dateValues ;

            if(type.equals("Encounter")){
                this.encounterList = encounterList;
            }else if(type.equals("DiagnosticReport")){
                this.diagnosticReportList = diagnosticReportList;
            }else if(type.equals("Allergies")){
                this.allergyList = allergyList;
            }else if(type.equals("Medications")){
                this.medicationsList = medicationsList;
            }else if (type.equals("Appointment")){
                this.appointmentList =appointmentList;
            }
        }else{
            this.titleValues = titleValues.subList(0,3);
            this.dateValues = dateValues.subList(0,size);
            if(type.equals("Encounter")){
                this.encounterList = encounterList.subList(0,size);
            }else if(type.equals("DiagnosticReport")){
                this.diagnosticReportList = diagnosticReportList.subList(0,size);
            }else if(type.equals("Allergies")){
                this.allergyList = allergyList.subList(0,size);
            }else if(type.equals("Medications")){
                this.medicationsList = medicationsList.subList(0,size);
            }else if (type.equals("Appointment")){
                this.appointmentList = appointmentList.subList(0,size);
            }
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder: called");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called");


        holder.title.setText(titleValues.get(position));
        holder.date.setText(dateValues.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Encounter")){
                    String textDetails = encounterList.get(position).getHtmlContent();
                    Intent intent = new Intent(mContext, showSectionDetails.class);
                    intent.putExtra("divTextEncounter", textDetails);
                    mContext.startActivity(intent);
                }else if(type.equals("DiagnosticReport")){
                    String textDetails = diagnosticReportList.get(position).getHtmlContent();
                    String diagnosticPdfUrl = diagnosticReportList.get(position).getDiagnosticPdfUrl();
                    Intent intent = new Intent(mContext, showSectionDetails.class);
                    intent.putExtra("divTextEncounter", textDetails);
                    intent.putExtra("diagnosticPdfUrl",diagnosticPdfUrl);
                    intent.putExtra("diagnosticPdfTitle",titleValues.get(position));
                    mContext.startActivity(intent);
                }else if(type.equals("Allergies")){
                    String textDetails = allergyList.get(position).getHtmlContent();
                    Intent intent = new Intent(mContext, showSectionDetails.class);
                    intent.putExtra("divTextEncounter", textDetails);
                    mContext.startActivity(intent);
                }else if(type.equals("Medications")){
                    String textDetails = medicationsList.get(position).getHtmlContent();
                    Intent intent = new Intent(mContext, showSectionDetails.class);
                    intent.putExtra("divTextEncounter", textDetails);
                    mContext.startActivity(intent);
                }else if(type.equals("Appointment")) {
                    String textDetails = appointmentList.get(position).getAppointmentHtmlContent();
                    Intent intent = new Intent(mContext, showSectionDetails.class);
                    intent.putExtra("divTextEncounter", textDetails);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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