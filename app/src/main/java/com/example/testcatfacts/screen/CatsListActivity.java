package com.example.testcatfacts.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.testcatfacts.R;
import com.example.testcatfacts.adapter.CatsAdapter;
import com.example.testcatfacts.pojo.Cat;

import java.util.ArrayList;
import java.util.List;

public class CatsListActivity extends AppCompatActivity {

    private CatsAdapter adapter;
    private CatsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CatsViewModel.class);
        adapter = new CatsAdapter();
        adapter.setCats(new ArrayList<Cat>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        viewModel.getCatLiveData().observe(this, new Observer<List<Cat>>() {
            @Override
            public void onChanged(List<Cat> cats) {
                adapter.setCats(cats);
            }
        });
        viewModel.loadData();
    }
}
