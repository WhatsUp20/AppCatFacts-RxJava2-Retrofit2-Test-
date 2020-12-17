package com.example.testcatfacts.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.testcatfacts.pojo.Cat;

import java.util.List;

@Database(entities = {Cat.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "cats.db";
    private static AppDatabase database;
    private static Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context,AppDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract CatsDao catsDao();

}
