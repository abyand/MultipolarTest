package com.example.abyandafa.multipolartest.main;

import android.content.Context;
import android.widget.EditText;

import com.example.abyandafa.multipolartest.baseclass.BaseMVP;
import com.example.abyandafa.multipolartest.baseclass.OutputBasic2;

import java.util.List;

/**
 * Created by Abyan Dafa on 11/06/2018.
 */

public interface MainMVP {



    interface View extends BaseMVP.View{
        void setOutput2Visibility(boolean isVisible);
        void setProgressVisibility(boolean isVisble);
        void showOptionSaveDatabase();
        void showOptionGetDatabase();
        void toLocalDatabaseActivity();
        void toServerDatabaseActivity();
        void onFilterFinished(String result);
        void onBasic2ResultFinished(List<OutputBasic2> result);

    }

    interface ViewPresenter extends BaseMVP.ViewPresenter{
        void filterInput(String input);
        void basic2ResultFinished();
        void startSaveToLocalDatabase(String input);
        void startSaveToServerDatabase(String input);
    }

    interface ModelPresenter extends BaseMVP.ModelPresenter{
        void onSaveToLocalFinished(boolean isSuccess);
        Context getContext();
    }

    interface Model{
        void saveToLocalDatabase(String input, String output, List<OutputBasic2> output2);
        void saveToServerDatabase(String input, String output, String output2);
    }

}
