package com.example.testcatfacts.screen;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.testcatfacts.api.ApiFactory;
import com.example.testcatfacts.api.ApiService;
import com.example.testcatfacts.data.AppDatabase;
import com.example.testcatfacts.pojo.Cat;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CatsViewModel extends AndroidViewModel {

    private static AppDatabase appDatabase;
    private LiveData<List<Cat>> catLiveData;
    private CompositeDisposable compositeDisposable;

    public CatsViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);
        catLiveData = appDatabase.catsDao().getAllCatsFacts();
    }

    public LiveData<List<Cat>> getCatLiveData() {
        return catLiveData;
    }

    @SuppressWarnings("unchecked")
    private void insertCat(List<Cat> cats) {
        new InsertCatTask().execute(cats);
    }

    private void deleteAllCats() {
        new DeleteAllCatsTask().execute();
    }

    private static class InsertCatTask extends AsyncTask<List<Cat>, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Cat>... lists) {
            if (lists != null && lists.length > 0) {
                appDatabase.catsDao().insertCat(lists[0]);
            }
            return null;
        }
    }

    private static class DeleteAllCatsTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.catsDao().deleteAllCats();
            return null;
        }
    }

    public void loadData() {
        compositeDisposable = new CompositeDisposable();
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        Disposable disposable = apiService.getAllFacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cat>>() {
                    @Override
                    public void accept(List<Cat> cats) throws Exception {
                        deleteAllCats();
                        insertCat(cats);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplication(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
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
