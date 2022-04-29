package com.example.Organizer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.Organizer.adapter.link;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Context context;
    private DBHelper DBHelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        this.context = context;
        DBHelper = new DBHelper(context);
    }
    public void openDB(){
        db = DBHelper.getReadableDatabase();
    }
    public void delete(int id){
        String select = Constant.ID + "=" + id;
        db.delete(Constant.TABLE_NAME, select, null);
    }
        public void update(String title, String disc, String date, String img, int isNotifi,int id){
        String select = Constant.ID + "=" + id;
        ContentValues cv = new ContentValues();
        cv.put(Constant.TITLE, title);
        cv.put(Constant.DISC, disc);
        cv.put(Constant.DATE, date);
        cv.put(Constant.IMAGE, img);
        cv.put(String.valueOf(Constant.NOTIFI), isNotifi);
        db.update(Constant.TABLE_NAME, cv,select, null);
    }
    public void insertToDB(String title, String disc, String date, String img, int isNotifi){
        ContentValues cv = new ContentValues();
        cv.put(Constant.TITLE, title);
        cv.put(Constant.DISC, disc);
        cv.put(Constant.DATE, date);
        cv.put(Constant.IMAGE, img);
        cv.put(String.valueOf(Constant.NOTIFI), isNotifi);
        db.insert(Constant.TABLE_NAME, null , cv);
    }
    public List<link> getFromDb() {
        List<link> tempList = new ArrayList<>();
        Cursor cursor = db.query(Constant.TABLE_NAME, null , null , null, null, null,null);
        while (cursor.moveToNext()){
            link l = new link();
            String title = cursor.getString(cursor.getColumnIndex(Constant.TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(Constant.DISC));
            String date = cursor.getString(cursor.getColumnIndex(Constant.DATE));
            String img = cursor.getString(cursor.getColumnIndex(Constant.IMAGE));
            int isNotifi = cursor.getInt(cursor.getColumnIndex(String.valueOf(Constant.NOTIFI)));
            int id = cursor.getInt(cursor.getColumnIndex(Constant.ID));
            l.setTitle(title);
            l.setDesc(desc);
            l.setDate(date);
            l.setImage(img);
            l.setNotifi(isNotifi);
            l.setId(id);
            tempList.add(l);
        }
        cursor.close();
        return tempList;
    }
    public void closeDB(){
        DBHelper.close();
    }
}
