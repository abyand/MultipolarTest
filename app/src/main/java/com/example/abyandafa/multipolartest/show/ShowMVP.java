package com.example.abyandafa.multipolartest.show;

import android.content.Context;

import com.example.abyandafa.multipolartest.baseclass.BaseMVP;
import com.example.abyandafa.multipolartest.baseclass.OutputBasic2;
import com.example.abyandafa.multipolartest.database.BasicTableClass;

import java.util.List;

/**
 * Created by Abyan Dafa on 12/06/2018.
 */

public interface ShowMVP {

    interface View extends BaseMVP.View{
        void setProgressVisibility(boolean isVisble);
        void onDatabaseGet(List<BasicTableClass> listBasicTable);
    }

    interface ViewPresenter extends BaseMVP.ViewPresenter{
        void getDatabase(int type);
    }

    interface ModelPresenter extends BaseMVP.ModelPresenter{
        void onRequestSuccess(List<BasicTableClass> listBasicTable);
        Context getContext();
    }

    interface Model{
        void getFromLocalDatabase();
        void getFromServerDatabase();
    }

}
