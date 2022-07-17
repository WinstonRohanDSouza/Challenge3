package com.example.fhirapp;

public class Event
{

    public String StartTime;
    public  String EndTime;
    public long duration;
    public String unformattedStartTime;
    public String unformattedEndTime;



    public Event(String startTime, String endTime, long duration, String unformattedStartTime, String unformattedEndTime) {
        StartTime = startTime;
        EndTime = endTime;
        this.duration = duration;
        this.unformattedStartTime = unformattedStartTime;
        this.unformattedEndTime = unformattedEndTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getUnformattedEndTime() {
        return unformattedEndTime;
    }

    public void setUnformattedEndTime(String unformattedEndTime) {
        this.unformattedEndTime = unformattedEndTime;
    }

    public String getUnformattedStartTime() {
        return unformattedStartTime;
    }

    public void setUnformattedStartTime(String unformattedStartTime) {
        this.unformattedStartTime = unformattedStartTime;
    }


    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        this.StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        this.EndTime = endTime;
    }
}


//
//{
//    public static ArrayList<Event> eventsList = new ArrayList<>();
//
//    public static ArrayList<Event> eventsForDate(LocalDate date)
//    {
//        ArrayList<Event> events = new ArrayList<>();
//
//        for(Event event : eventsList)
//        {
//            if(event.getStartDate().equals(date))
//                events.add(event);
//        }
//
//        return events;
//    }
//
//
//    private String name;
//    private LocalDate StartDate, EndDate;
//    private LocalTime time;
//
//    public LocalDate getEndDate() {
//    return EndDate;
//}
//
//    public LocalDate getStartDate() {
//    return StartDate;
//}
//
//    public void setStartDate(LocalDate startDate) {
//    this.StartDate = startDate;
//}
//
//    public void setEndDate(LocalDate endDate) {
//    this.EndDate = endDate;
//}
//
//    public Event(String name, LocalDate date, LocalTime time)
//    {
//        this.name = name;
//        this.StartDate = date;
//        this.time = time;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public void setName(String name)
//    {
//        this.name = name;
//    }
//
//
//
//
//    public LocalTime getTime()
//    {
//        return time;
//    }
//
//    public void setTime(LocalTime time)
//    {
//        this.time = time;
//    }
//}
