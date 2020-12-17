package com.example.testcatfacts.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.testcatfacts.pojo.Cat;

import java.util.List;

@Database(entities = {Cat.class}, version = 1, exportSchema = false)
public abstract class CatsDatabase extends RoomDatabase {

    private static CatsDatabase database;
    private static final String DB_NAME = "cats.db";
    private static final Object LOCK = new Object();

    public static CatsDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context,CatsDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract CatsDao catsDao();
}
