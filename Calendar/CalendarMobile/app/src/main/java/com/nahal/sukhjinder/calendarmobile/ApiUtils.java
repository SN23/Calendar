package com.nahal.sukhjinder.calendarmobile;

public class ApiUtils {

    private ApiUtils() {
    }

//  Change Base URL to address of the computer that is running the backend python script

    public static final String BASE_URL = "http://192.168.1.150:5000/";

    public static CalendarApi getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(CalendarApi.class);
    }
}
