package com.example.testcatfacts.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.testcatfacts.R;
import com.example.testcatfacts.adapter.CatsAdapter;
import com.example.testcatfacts.api.ApiFactory;
import com.example.testcatfacts.api.ApiService;
import com.example.testcatfacts.pojo.Cat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CatsListActivity extends AppCompatActivity implements CatsView{

    private RecyclerView recyclerView;
    private CatsAdapter adapter;
    private CatsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new CatsPresenter(this);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CatsAdapter();
        adapter.setCats(new ArrayList<Cat>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        presenter.loadData();

    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }

    @Override
    public void showData(List<Cat> cats) {
        adapter.setCats(cats);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
}