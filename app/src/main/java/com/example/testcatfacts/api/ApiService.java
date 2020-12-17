package com.example.testcatfacts.api;

import com.example.testcatfacts.pojo.Cat;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("facts")
    Observable<List<Cat>> getAllFacts();
}
