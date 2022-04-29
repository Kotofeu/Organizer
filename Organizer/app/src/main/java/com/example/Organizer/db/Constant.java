package com.example.Organizer.db;

public class Constant {
    public static  final String TABLE_NAME = "task_tabele";
    public static  final String LIST_ITEM_INTENT = "list_item_intent";
    public static  final String EDIT_STATE = "edit_state";
    public static  final String ID = "id";
    public static  final String TITLE = "title";
    public static  final String DISC = "disc";
    public static  final String DATE = "date";
    public static  final String IMAGE = "image";
    public static  final String NOTIFI = "notify";
    public static  final String DB_NAME = "my_db.db";
    public static  final int DB_VERSION = 1;
    public static  final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT, "+
            DISC +  " TEXT, "+ DATE + " TEXT, " + IMAGE + " TEXT," + NOTIFI+ " INTEGER)";
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
