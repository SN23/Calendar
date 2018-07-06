package com.nahal.sukhjinder.calendarmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEvent extends Activity {


    private static final String TAG = "AddEvent";
    private CalendarApi calendarApi;
    private String startTime;
    private String endTime;
    private String date;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        Button submitButton = findViewById(R.id.submit_button);
        final TimePicker startTimePicker = findViewById(R.id.start_time);
        final TimePicker endTimePicker = findViewById(R.id.end_time);
        final EditText eventDescription = findViewById(R.id.event_descrption);

        Intent intent = getIntent();
        String day = intent.getStringExtra("Day");
        date = "05" + "/" + day + "/" + "2018";
        calendarApi = ApiUtils.getAPIService();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = getTime(startTime, startTimePicker);
                endTime = getTime(endTime, endTimePicker);
                String description = eventDescription.getText().toString();
                apiCall(date, startTime, endTime, description);
                finish();
            }
        });
    }

    public String getTime(String time, TimePicker timePicker) {
        time = timePicker.getHour() + ":" + timePicker.getMinute();
        try {
            Date dateObj = simpleDateFormat.parse(time);
            time = new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }


    private void apiCall(String date, String startTime, String endTime, String description) {

        calendarApi.insertEvent(new Event(date, startTime, endTime, description))
                .enqueue(new Callback<Event>() {

                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Event Added", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        Log.e(TAG, "Unable to submit post to API.");
                        Toast.makeText(getApplicationContext(), "Event Added Failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}