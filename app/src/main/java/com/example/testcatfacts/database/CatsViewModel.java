package com.example.testcatfacts.database;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.testcatfacts.api.ApiFactory;
import com.example.testcatfacts.api.ApiService;
import com.example.testcatfacts.pojo.Cat;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CatsViewModel extends AndroidViewModel {

    private static CatsDatabase database;
    private CompositeDisposable compositeDisposable;
    private LiveData<List<Cat>> cats;

    public CatsViewModel(@NonNull Application application) {
        super(application);
        database = CatsDatabase.getInstance(application);
        cats = database.catsDao().getAllFacts();
    }

    public LiveData<List<Cat>> getCats() {
        return cats;
    }

    @SuppressWarnings("unchecked")
    private void insertCats(List<Cat> cats) {
        new InsertCatsTask().execute(cats);
    }

    private void deleteAllCats() {
        new DeleteAllCats().execute();
    }

    private static class InsertCatsTask extends AsyncTask<List<Cat>, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Cat>... lists) {
            if (lists != null && lists.length > 0) {
                database.catsDao().insertCats(lists[0]);
            }
            return null;
        }
    }

    private static class DeleteAllCats extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.catsDao().deleteAllCats();
            return null;
        }
    }

    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getAllCatsFacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cat>>() {
                    @Override
                    public void accept(List<Cat> cats) throws Exception {
                        deleteAllCats();
                        insertCats(cats);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplication(), "Error: " +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
