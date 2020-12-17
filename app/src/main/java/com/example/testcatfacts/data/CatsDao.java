package com.example.testcatfacts.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.testcatfacts.pojo.Cat;

import java.util.List;

@Dao
public interface CatsDao {
    @Query("SELECT * FROM cats")
    LiveData<List<Cat>> getAllCatsFacts();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertCat(List<Cat> cat);

    @Query("DELETE FROM cats")
    void deleteAllCats();
}
