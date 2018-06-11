package com.example.abyandafa.multipolartest.main;

import android.content.Context;
import android.util.Log;

import com.example.abyandafa.multipolartest.baseclass.OutputBasic2;
import com.example.abyandafa.multipolartest.database.BasicTableClass;
import com.example.abyandafa.multipolartest.database.DatabaseHelper;

import java.util.List;

/**
 * Created by Abyan Dafa on 11/06/2018.
 */

public class MainModel implements MainMVP.Model {

    private MainMVP.ModelPresenter mPresenter;
    private Context context;


    public MainModel(MainMVP.ModelPresenter mPresenter) {
        this.mPresenter = mPresenter;
        this.context = mPresenter.getContext();
    }

    @Override
    public void saveToLocalDatabase(String input, String output, List<OutputBasic2> output2) {
        DatabaseHelper db = new DatabaseHelper(this.context);
        long id = db.insert(input, output, output2);
        BasicTableClass row = db.getRow(id);
        if(row != null)
        {
            String[] haha = row.getKataBerulang().split(";");
            for(int i = 0; i < haha.length; i++) Log.d("HAHA", "saveToLocalDatabase: " + haha[i]);
            this.mPresenter.onSaveToLocalFinished(true);
        }
        else this.mPresenter.onSaveToLocalFinished(false);
    }

    @Override
    public void saveToServerDatabase(String input, String output, String output2) {

    }
}
