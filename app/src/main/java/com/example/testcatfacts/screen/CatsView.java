package com.example.testcatfacts.screen;

import com.example.testcatfacts.pojo.Cat;

import java.util.List;

public interface CatsView {
    void showData(List<Cat> cats);

    void showError();
}
