package com.example.abyandafa.multipolartest.show;

import android.content.Context;
import android.util.Log;

import com.example.abyandafa.multipolartest.database.BasicTableClass;

import java.util.List;

/**
 * Created by Abyan Dafa on 12/06/2018.
 */

public class ShowPresenter implements ShowMVP.ViewPresenter, ShowMVP.ModelPresenter {

    private ShowMVP.View view;
    private ShowMVP.Model model;
    private Context context;

    @Override
    public Context getContext()
    {
        return context;
    }

    public ShowPresenter(ShowMVP.View view) {
        this.view = view;
        this.context = view.getContext();
        this.model = new ShowModel(this);
    }

    @Override
    public void start() {
        this.view.initView();
        this.view.initContent();
    }

    @Override
    public void onRequestFailed(String message) {
        this.view.showToast(message);
    }

    @Override
    public void getDatabase(int type) {
        if(type == 0)
            this.model.getFromLocalDatabase();
        else
            this.model.getFromServerDatabase();
    }

    @Override
    public void onRequestSuccess(List<BasicTableClass> listBasicTable) {
        this.view.onDatabaseGet(listBasicTable);
    }
}
