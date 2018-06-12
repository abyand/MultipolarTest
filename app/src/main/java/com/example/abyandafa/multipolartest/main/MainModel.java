package com.example.abyandafa.multipolartest.main;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.abyandafa.multipolartest.R;
import com.example.abyandafa.multipolartest.baseclass.OutputBasic2;
import com.example.abyandafa.multipolartest.database.BasicTableClass;
import com.example.abyandafa.multipolartest.database.DatabaseHelper;
import com.example.abyandafa.multipolartest.service.RequestSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            this.mPresenter.onSaveFinished(true, "Success");
        }
        else this.mPresenter.onSaveFinished(false, "Failed");
    }

    @Override
    public void saveToServerDatabase(String input, String output, List<OutputBasic2> output2) {
        String kataBerulang = "";
        for(int i = 0; i < output2.size() ; i++)
        {
            OutputBasic2 current = output2.get(i);
            kataBerulang+= "[" + current.getWord() + ", " +
                    current.getRepetitiveCount() + ", " +
                    current.getIndex()+"];";
        }

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(c);

        JSONObject obj = new JSONObject();
        try {
            obj.accumulate("input", input);
            obj.accumulate("output", output);
            obj.accumulate("tanggal_proses", formattedDate);
            obj.accumulate("kata_berulang", kataBerulang);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                context.getResources().getString(R.string.baseUrl)+"/addrow", obj, response -> {
            Log.d("wkwkk", "saveToServerDatabase: ");
            try {
                if(response.getString("response").equals("Success"))
                {
                    this.mPresenter.onSaveFinished(true, "Success");
                }
                else this.mPresenter.onSaveFinished(false, "Failed");
            } catch (JSONException e) {
                e.printStackTrace();
                this.mPresenter.onSaveFinished(false, "Network Error");
            }
        }, error -> {
            error.printStackTrace();
            this.mPresenter.onSaveFinished(false, "Network Error");
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(0,0,0));
        RequestSingleton.getInstance(this.context).addToRequestQueue(req);
    }
}
