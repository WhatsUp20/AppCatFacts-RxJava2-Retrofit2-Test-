package com.example.testcatfacts.screen;

import com.example.testcatfacts.pojo.Cat;

import java.util.List;

public interface CatView {
    void showData(List<Cat> cats);

    void showError();
}
