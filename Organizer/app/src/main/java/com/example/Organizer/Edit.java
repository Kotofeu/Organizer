package com.example.Organizer;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.Organizer.adapter.Reminder;
import com.example.Organizer.adapter.link;
import com.example.Organizer.db.DBManager;
import com.example.Organizer.R;
import com.example.Organizer.db.Constant;

import java.time.Instant;
import java.util.Calendar;
import java.util.Locale;
public class Edit extends AppCompatActivity {
    private EditText edName, edDisk;
    private DBManager dbm;
    private CheckBox edNotifi;
    private Button edDate;
    private Spinner spinner;
    private View TimeLayout;
    private boolean isEditState = true;
    private link item;
    public long itemms = 0;
    static int isNotifi = 0;
    long now = 0;
    Calendar dateAndTime=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getmyIntent();
    }
    private void init(){
        TimeLayout = findViewById(R.id.TimeLayout);
        TimeLayout.setVisibility(View.INVISIBLE);
        dbm = new DBManager(this);
        edDisk = findViewById(R.id.edDick);
        edName = findViewById(R.id.edName);
        spinner = findViewById(R.id.SpinnerImage);
        edNotifi = findViewById(R.id.NotifiCheckBox);
        edDate = findViewById(R.id.DataText);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.image_list,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (edNotifi.isChecked()){
            isNotifi = 1;
            TimeLayout.setVisibility(View.VISIBLE);
        }
        else{
            isNotifi = 0;
            TimeLayout.setVisibility(View.INVISIBLE);
        }
    }
    private void getmyIntent(){
        Intent i = getIntent();
        if (i!= null){
            item = (link)i.getSerializableExtra(Constant.LIST_ITEM_INTENT);
            isEditState = i.getBooleanExtra(Constant.EDIT_STATE, true);
            if(!isEditState){
                edName.setText(item.getTitle());
                edDisk.setText(item.getDesc());
                edDate.setText(item.getDate());
                edNotifi.setChecked(item.getNotifi());
                spinner.setSelection(Integer.parseInt(item.getImage()));
                if (edNotifi.isChecked()){
                    isNotifi = 1;
                    TimeLayout.setVisibility(View.VISIBLE);
                }
                else{
                    isNotifi = 0;
                    TimeLayout.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
    protected void onResume() {
        super.onResume();
        dbm.openDB();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSave(View v){
        if (edNotifi.isChecked()){
            isNotifi = 1;
            TimeLayout.setVisibility(View.VISIBLE);
        }
        else{
            isNotifi = 0;
            TimeLayout.setVisibility(View.INVISIBLE);
        }
        if((edName.getText().toString().equals("")) || (edDate.getText().toString().toUpperCase(Locale.ROOT).equals("ВРЕМЯ") && (isNotifi != 0))){
            Toast.makeText(this, R.string.text_empty, Toast.LENGTH_SHORT).show();
        }
        else {
            if (isNotifi == 1){
                int canal =  (int) Math.ceil((Math.random()*1000));
                SendMess((itemms / 1000) - Instant.now().getEpochSecond(),edName.getText().toString(),edDisk.getText().toString(), spinner.getSelectedItemPosition(),canal,itemms);
            }
            if(isEditState){
                dbm.insertToDB(edName.getText().toString(),edDisk.getText().toString(), edDate.getText().toString(), String.valueOf(spinner.getSelectedItemPosition()), isNotifi);
                Toast.makeText(this, R.string.text_save, Toast.LENGTH_SHORT).show();
            } else
            {
                dbm.update(edName.getText().toString(),edDisk.getText().toString(), edDate.getText().toString(), String.valueOf(spinner.getSelectedItemPosition()), isNotifi, item.getId());
                Toast.makeText(this, R.string.text_update, Toast.LENGTH_SHORT).show();
            }
            finish();
            dbm.closeDB();
        }
    }
    public void setTime(View v) {
        new DatePickerDialog(Edit.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
        new TimePickerDialog(Edit.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    private void setInitialDateTime() {
        edDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
        itemms = dateAndTime.getTimeInMillis();
    }
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
    private void SendMess(long inter, String title, String desc, int img, int id, long date){
        createNotificatinChannel(id);
        Reminder r = new  Reminder();
        r.create(title,desc,img,id,date);
        Intent intent = new Intent(this, Reminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,id,intent,id);
        now = System.currentTimeMillis();

        AlarmManager alarmReceiver = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmReceiver.set(AlarmManager.RTC_WAKEUP,
                now + (inter * 1000), pendingIntent
        );
    }
    private void createNotificatinChannel(int id){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1"+id, "1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

    }
    public void CheckBoxNotify(View v){

        if (edNotifi.isChecked()){
            isNotifi = 1;
            TimeLayout.setVisibility(View.VISIBLE);
        }
        else{
            isNotifi = 0;
            TimeLayout.setVisibility(View.INVISIBLE);
        }
    }
}