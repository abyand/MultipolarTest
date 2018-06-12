package com.example.abyandafa.multipolartest.show;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.abyandafa.multipolartest.R;
import com.example.abyandafa.multipolartest.database.BasicTableClass;
import com.example.abyandafa.multipolartest.database.DatabaseHelper;
import com.example.abyandafa.multipolartest.service.RequestSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Abyan Dafa on 12/06/2018.
 */

public class ShowModel implements ShowMVP.Model {

    private ShowMVP.ModelPresenter mPresenter;

    public ShowModel(ShowMVP.ModelPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getFromLocalDatabase() {
        DatabaseHelper db = new DatabaseHelper(this.mPresenter.getContext());
        List<BasicTableClass> listTable = updateValue(db.getAllRow());

        this.mPresenter.onRequestSuccess(listTable);
    }

    private List<BasicTableClass> updateValue(List<BasicTableClass> allRow) {
        for(int i = 0; i < allRow.size(); i++)
        {
            BasicTableClass currentRow = allRow.get(i);

            // update Kata Berulang value
            String kataBerulang = "";
            String[] inputs = currentRow.getKataBerulang().split(";");
            for(int j = 0; j < inputs.length; j++)
                kataBerulang = kataBerulang + inputs[j] + " \n";
            currentRow.setKataBerulang(kataBerulang);

            //set Maks output to 100 char
            String output = currentRow.getOutput();
            if(output.length()>100)
            {
                int j = 100;
                while (output.length()> j )
                {
                    char a = output.charAt(j);
                    if(Character.toString(a).equals(" "))
                    {
                        j++;
                        break;
                    }
                    j++;
                }
                output = output.substring(0, j-1);
                output += "....";
            }
            currentRow.setOutput(output);
        }
        return allRow;
    }

    @Override
    public void getFromServerDatabase() {
        JSONObject obj = new JSONObject();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                this.mPresenter.getContext().getResources().getString(R.string.baseUrl)+"/getrows", obj, response -> {
            try {


                if(response.getString("response").equals("Success"))
                {
                    JSONArray data = response.getJSONArray("data");
                    List<BasicTableClass> listTable = getListData(data);
                    this.mPresenter.onRequestSuccess(listTable);
                }
                else this.mPresenter.onRequestFailed("Network Error");


            } catch (JSONException e) {
                e.printStackTrace();
                this.mPresenter.onRequestFailed("Network Error");
            }
        }, error -> {
            error.printStackTrace();
            this.mPresenter.onRequestFailed("Network Error");
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(0,0,0));
        RequestSingleton.getInstance(this.mPresenter.getContext()).addToRequestQueue(req);
    }

    private List<BasicTableClass> getListData(JSONArray data) {
        List<BasicTableClass>  listData = new ArrayList<>();

        for(int i = 0; i < data.length(); i++)
        {
            try {
                JSONObject currentObject = data.getJSONObject(i);
                int id = currentObject.getInt("id");
                String input = currentObject.getString("input");
                String output = currentObject.getString("output");
                String tanggal = currentObject.getString("tanggal_proses");
                String kataUlang = currentObject.getString("kata_berulang");

                BasicTableClass currentRow = new BasicTableClass(id, input, output, tanggal, kataUlang);

                listData.add(currentRow);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return updateValue(listData);
    }
}
