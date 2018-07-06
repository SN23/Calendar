package com.nahal.sukhjinder.calendarmobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CalendarApi {


    @POST("/events")
    Call<Event> insertEvent(@Body Event event);

    @GET("/events")
    Call<List<Event>> getAllEvents();

}
