package com.example.theguardiannews.utils;

import android.app.Application;

import androidx.room.Room;

import com.example.theguardiannews.database.DatabaseHelper;

public class App extends Application {

    public static App instance;
    private DatabaseHelper db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(this, DatabaseHelper.class, "DataBase")
                .allowMainThreadQueries()
                .build();
    }

    public DatabaseHelper getDatabaseInstance() {
        return db;
    }
}
