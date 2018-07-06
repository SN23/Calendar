package com.nahal.sukhjinder.calendarmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private DayAdapter dayAdapter;

    String[] days = {"", "", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24", "25", "26", "27", "28", "29", "30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.dayGridView);
        gridView.setNumColumns(7);

        dayAdapter = new DayAdapter(this, R.layout.day_grid_item, days);
        gridView.setAdapter(dayAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!(days[position].equals(""))) {
                    Intent intent = new Intent(MainActivity.this, AddEvent.class);
                    intent.putExtra("Day", days[position]);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dayAdapter.notifyDataSetChanged();
    }
}
