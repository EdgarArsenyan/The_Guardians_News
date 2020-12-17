package com.example.theguardiannews.database;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = { UploadModel.class}, version = 2, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract DataDao getDataDao();
    private static DatabaseHelper instance;

    public static DatabaseHelper getDatabase(final Context context){
        if(instance == null){
            synchronized (DatabaseHelper.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseHelper.class, "news_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final DataDao mDao;
        PopulateDbAsync(DatabaseHelper db) {
            mDao = db.getDataDao();
        }
        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}