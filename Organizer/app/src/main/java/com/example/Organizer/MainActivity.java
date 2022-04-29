package com.example.Organizer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.Organizer.adapter.MainAdapter;
import com.example.Organizer.R;
import com.example.Organizer.db.DBManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DBManager dbm;
    private TextView date_mounth, date_year, date_day, date_name;
    private RecyclerView rcView;
    private MainAdapter mainAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        dbm = new DBManager(this);
        date_mounth = findViewById(R.id.dateMounth);
        date_year = findViewById(R.id.dateYear);
        date_day = findViewById(R.id.dateDay);
        date_name = findViewById(R.id.DayName);
        rcView = findViewById(R.id.rcView);
        mainAdapter = new MainAdapter(this);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(rcView);
        rcView.setAdapter(mainAdapter);
        Date currentDate = new Date();
        DateFormat dateDay = new SimpleDateFormat("dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String dateM = calendar.getDisplayName(Calendar.MONTH,
                Calendar.LONG_FORMAT, new Locale("ru"));
        DateFormat dateY = new SimpleDateFormat("yyyy", Locale.getDefault());
        DateFormat dateName = new SimpleDateFormat("EEEE", Locale.getDefault());
        String date_d = dateDay.format(currentDate);
        String date_nameStr = dateName.format(currentDate);
        String date_y = dateY.format(currentDate);
        date_day.setText(date_d);
        date_year.setText(date_y);
        String mounth_name = dateM.toLowerCase(Locale.ROOT);
        date_mounth.setText(mounth_name);
        date_name.setText(date_nameStr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbm.openDB();
        mainAdapter.updAdapter(dbm.getFromDb());
    }

    public void onClickAdd(View view) {
        Intent i = new Intent(MainActivity.this, Edit.class);
        startActivity(i);
    }

    protected void onDestroy() {
        super.onDestroy();
        dbm.closeDB();
    }

    private ItemTouchHelper getItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainAdapter.remove(viewHolder.getAdapterPosition(), dbm);
            }
        });
    }


}
