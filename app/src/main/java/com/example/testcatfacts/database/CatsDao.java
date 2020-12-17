package com.example.testcatfacts.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.testcatfacts.pojo.Cat;

import java.util.List;

@Dao
public interface CatsDao {
    @Query("SELECT * FROM cats")
    LiveData<List<Cat>> getAllFacts();
}
