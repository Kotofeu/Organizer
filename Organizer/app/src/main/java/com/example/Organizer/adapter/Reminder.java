package com.example.Organizer.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.Organizer.R;

public class Reminder extends BroadcastReceiver  {
    public static Reminder[] r = new Reminder[]{
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),
            new Reminder(),

    };
    public static int arrayid = 0;
    public  String title = "У вас важное уведомление!";
    public  String desc = "Зайдите в приложение";
    public  int id = 1;
    public  int image = 1;
    public  long date = 0;
    public void create(String t, String d, int im, int ida, long date){
        for (int j = 0; j<r.length; j++){
            if (r[j].title == "У вас важное уведомление!"){
                r[j].title = t;
                r[j].desc = d;
                r[j].id = ida;
                r[j].image = im;
                r[j].date = date;
                arrayid = j;
                break;
            }
        }
        sort();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1"+ r[first()].id)
                .setSmallIcon(R.drawable.warming)
                .setContentTitle(r[first()].title)
                .setContentText(r[first()].desc)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (r[first()].image == 1){
            builder.setSmallIcon(R.drawable.warming);
        }
        else if(r[first()].image == 2){
            builder.setSmallIcon(R.drawable.sport);
        }
        else  if(r[first()].image == 3){
            builder.setSmallIcon(R.drawable.party);
        }
        else{
            builder.setSmallIcon(R.drawable.defeult);
        }
        r[first()] = new Reminder();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(r[first()].id, builder.build());
    }
    public int first(){
        sort();
        for (int j = 0; j<r.length; j++) {
            if (r[j].title != "У вас важное уведомление!") {
                return j;
            }
        }
        return 0;
    }
    public void sort(){

        for (int i = 0; i < r.length; i++) {
            long min = r[i].date;
            int min_i = i;
            for (int j = i+1; j < r.length; j++) {
                if (r[j].date < min) {
                    min = r[j].date;
                    min_i = j;
                }
            }
            if (i != min_i) {
                Reminder tmp = r[i];
                r[i] = r[min_i];
                r[min_i] = tmp;
            }
        }
    }
}
