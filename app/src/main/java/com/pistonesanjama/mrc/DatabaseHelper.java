package com.pistonesanjama.mrc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "users.db";

    public DatabaseHelper(Context context) {
        super(context, "users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, firstname VARCHAR(255) NOT NULL," +
                "lastname VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, phone VARCHAR(255) NOT NULL," +
                "username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists users");

    }

    public boolean insertData(String firstname, String lastname, String email, String phone, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues =  new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = db.insert("users", null, contentValues);
        if (result == -1) return false;
            else
                return true;

    }

    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username =?", new String[] {username});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username =? AND password =?", new String[] {username, password});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public boolean getUserProfile(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username =?", new String[] {username});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor searchUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase(); //get the database that was created in this instance
        Cursor cursor = db.rawQuery("select * from users where username =?", new String[]{username});

        if (cursor.moveToLast()) {
            String firstname = cursor.getString(0);
            String lastname = cursor.getString(1);
            String email = cursor.getString(2);
            String phone = cursor.getString(3);
            return cursor;

        }else {
            Log.e("error not found", "user can't be found or database empty");
        }
        cursor.close();
        return cursor;
    }

}
