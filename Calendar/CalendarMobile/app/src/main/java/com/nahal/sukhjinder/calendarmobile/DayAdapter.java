package com.nahal.sukhjinder.calendarmobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayAdapter extends ArrayAdapter {

    private CalendarApi calendarApi;
    private List<Event> events = null;
    private Date eventDate;


    String[] days = new String[32];

    public DayAdapter(@NonNull Context context, int resource, String[] days) {
        super(context, resource, days);
        this.days = days;
        calendarApi = ApiUtils.getAPIService();
    }

    @Override
    public int getCount() {
        return 32;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_grid_item, parent, false);
        final LinearLayout linearLayout = view.findViewById(R.id.day_grid_linear_layout);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        eventDate = null;

        calendarApi.getAllEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        events = response.body();


                        for (int i = 0; i < events.size(); i++) {

                            try {
                                eventDate = simpleDateFormat.parse(events.get(i).getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (position == eventDate.getDate() + 1) {
                                linearLayout.addView(addTV(events.get(i).getDescription()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });


        TextView dayTV = view.findViewById(R.id.day_tv);
        dayTV.setText(days[position]);

        return view;
    }


    public TextView addTV(String eventDesc) {
        TextView eventTV = new TextView(getContext());
        if (eventDesc.length() > 7) {
            eventTV.setText(eventDesc.substring(0, 7));
        } else {
            eventTV.setText(eventDesc);
        }
        eventTV.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        eventTV.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        eventTV.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        eventTV.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 0);
        eventTV.setLayoutParams(params);

        return eventTV;
    }
}
