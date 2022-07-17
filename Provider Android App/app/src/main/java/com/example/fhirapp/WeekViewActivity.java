package com.example.fhirapp;


import static com.example.fhirapp.CalendarUtils.daysInWeekArray;
import static com.example.fhirapp.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    //private RequestQueue mQueue;
    private List<Event> SlotEventList;
    String startTime,endTime,unformattedStartTime,unformattedEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);

        //mQueue = Volley.newRequestQueue(this);
        initWidgets();
        setWeekView();


    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);

    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();

    }



    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        Log.d("selectedDate", String.valueOf(date));
        setWeekView();
        GetSlotDetails(date);
    }

    public void GetSlotDetails(LocalDate date) {

        String finalUrl="";
        String baseUrl = "https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/Slot?schedule.actor=Practitioner/";
        String schedule_actor= "593923";
        String start= date.toString();
        String slot_type= "https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/codeSet/14249|24477854";
        finalUrl =  baseUrl.concat(schedule_actor).concat("&start=").concat(start).concat("&slot-type=").concat(slot_type);
        Log.d("finalUrl",finalUrl);

        String url = "https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/Slot?schedule.actor=Practitioner/593923&start=2022-01-04&slot-type=https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/codeSet/14249|24477854";

        jsonParseSlot(finalUrl);





    }

    public void jsonParseSlot(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        SlotEventList = new ArrayList<>();
        try {

            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (response != null)
                    //resultTextView.setText("Resposne : " + response.toString());
                    //Toast.makeText(getApplicationContext(), response.toString() , Toast.LENGTH_LONG).show();
                    try {
                        JSONArray jsonArray = response.getJSONArray("entry");

                        for (int i =0; i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            unformattedStartTime = ((JSONObject) (JSONObject)jsonObject.get("resource")).getString("start").toString();
                            unformattedEndTime = ((JSONObject) (JSONObject)jsonObject.get("resource")).getString("end").toString();

                           // 2022-01-04T20:30:00.000Z

//                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.XXXz", Locale.ENGLISH);
//                            LocalDate date = LocalDate.parse(startTime, formatter);

//                            Log.d("ZonedDateTime", String.valueOf(date));

                            String[] startTimeSplit = unformattedStartTime.split("T");
                            String[] endTimeSplit = unformattedEndTime.split("T");

                            startTime = startTimeSplit[1].substring(0, Math.min(startTimeSplit[1].length(), 5));
                            endTime =  endTimeSplit [1].substring(0, Math.min(endTimeSplit[1].length(), 5));

                            int startTimeI = Integer.parseInt(startTime.substring(0, Math.min(startTime.length(), 1)));
                            int endTimeI = Integer.parseInt(endTime.substring(0, Math.min(endTime.length(), 1)));

                            Log.d("startTimeI", String.valueOf(startTimeI));
                            Log.d("endTimeI", String.valueOf(endTimeI));

                            String startTime1 = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));
                            String endTime2 = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));

                            Log.d("startTime1",startTime1);
                            Log.d("endTime2",endTime2);


                            Log.d("startTime",startTime);

//                            String time1 = "16:00:00";
//                            String time2 = "19:00:00";

                            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                            Date date1 = null;
                            try {
                                date1 = format.parse(startTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date date2 = null;
                            try {
                                date2 = format.parse(endTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long duration = date2.getTime() - date1.getTime();

                             duration = (duration / 1000) / 60;

                            Log.d("durationDDD", String.valueOf(duration));

//                            LocalDate startTimeD = LocalDate.parse(startTime);
//                            Log.d("LocalDate", String.valueOf(startTimeD));

                            Event event = new Event(startTime1,endTime2,duration,unformattedStartTime,unformattedEndTime);
                            SlotEventList.add(event);

                            RecyclerView recyclerView = findViewById(R.id.SlotEvents);
                            SlotEventAdapter adapter = new SlotEventAdapter(getApplicationContext(),SlotEventList);//encountersList,diagnosticReportList,allergyList,medicationsList,appointmentList, type,-1);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));



                            Log.d("startTimeToendTime",startTime+" "+endTime);
                        }

                        Log.d("EntrySlotArray",jsonArray.toString());
                    } catch (JSONException e) {
                        Log.d("EntrySlotArray","Error wrong code");
                        SlotEventList.clear();
                        RecyclerView recyclerView = findViewById(R.id.SlotEvents);
                        SlotEventAdapter adapter = new SlotEventAdapter(getApplicationContext(),SlotEventList);//encountersList,diagnosticReportList,allergyList,medicationsList,appointmentList, type,-1);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));

                        AlertDialog.Builder alert = new AlertDialog.Builder(WeekViewActivity.this);
                        alert.setTitle("No Slots Available");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alert.create().show();


                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Accept", "application/json");;

                    return params;
                }
            };;
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {


//

//        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
//        SlotEventAdapter eventAdapter = new SlotEventAdapter(getApplicationContext(), dailyEvents);
//        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        //startActivity(new Intent(this, EventEditActivity.class));
    }
}