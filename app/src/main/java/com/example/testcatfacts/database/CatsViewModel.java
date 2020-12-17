package com.example.testcatfacts.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.testcatfacts.pojo.Cat;

import java.util.List;

public class CatsViewModel extends AndroidViewModel {

    private CatsDatabase database;
    private LiveData<List<Cat>> cats;

    public CatsViewModel(@NonNull Application application) {
        super(application);
        database = CatsDatabase.getInstance(application);
        cats = database.catsDao().getAllFacts();
    }

    public LiveData<List<Cat>> getCats() {
        return cats;
    }
}
