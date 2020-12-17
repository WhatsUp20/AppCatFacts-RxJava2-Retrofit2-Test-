package com.example.testcatfacts.screen;

import com.example.testcatfacts.api.ApiFactory;
import com.example.testcatfacts.api.ApiService;
import com.example.testcatfacts.pojo.Cat;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CatPresenter {

    private CompositeDisposable compositeDisposable;
    private CatView view;

    public CatPresenter(CatView view) {
        this.view = view;
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
                        view.showData(cats);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                        throwable.getMessage();
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
