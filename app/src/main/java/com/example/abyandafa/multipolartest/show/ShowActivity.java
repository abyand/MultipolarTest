package com.example.abyandafa.multipolartest.show;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abyandafa.multipolartest.R;
import com.example.abyandafa.multipolartest.database.BasicTableClass;
import com.example.abyandafa.multipolartest.database.DatabaseHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ShowActivity extends AppCompatActivity {


    // 0 mean from local, 1 mean from server
    private int showType;
    private TableLayout tv;

    private TextView titleID;
    private TextView titleInput;
    private TextView titleOutput;
    private TextView titleTanggal;
    private TextView titleKata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Bundle bundle = getIntent().getExtras();
        showType =bundle.getInt("type");
        tv = findViewById(R.id.table_layout);
        showTable(showType);
    }

    private void showTable(int showType) {
        DatabaseHelper db = new DatabaseHelper(this);
        List<BasicTableClass> allRow = db.getAllRow();
        try{
            tv.removeAllViews();
            TableRow trTitle=new TableRow(this);
            trTitle.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            trTitle.removeAllViews();

            titleID = setTitle(titleID, "No", 1f);
            titleInput = setTitle(titleInput, "Input", 7f);
            titleOutput = setTitle(titleID, "Output", 6f);
            titleTanggal = setTitle(titleTanggal, "Tanggal Proses", 4f);
            titleKata = setTitle(titleKata, "Kata ulang", 4f);
            trTitle.addView(titleID);
            trTitle.addView(titleInput);
            trTitle.addView(titleOutput);
            trTitle.addView(titleTanggal);
            trTitle.addView(titleKata);

            tv.addView(trTitle);
            final View vline2 = new View(this);
            vline2.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
            vline2.setBackgroundColor(Color.BLACK);
            tv.addView(vline2);


            Gson gson = new Gson();
            String data = gson.toJson(allRow);
            Log.d("WAKAKAK", "showTable: " + data);
            JSONArray jArray = new JSONArray(data);
            Log.d("WAKAKA", "showTable: " + jArray.toString());
            // when i=-1, loop will display heading of each column
            // then usually data will be display from i=0 to jArray.length()
            for(int i=0;i<jArray.length();i++){

                TableRow tr=new TableRow(this);
                tr.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                tr.removeAllViews();

                JSONObject json_data = jArray.getJSONObject(i);

                String strID=String.valueOf(json_data.getInt("id"));
                TextView valueID = new TextView(this);
                valueID= setRow(valueID, strID, 1f);
                tr.addView(valueID);

                String strInput=json_data.getString("input");
                TextView valueInput = new TextView(this);
                valueInput= setRow(valueInput, strInput, 7f);
                tr.addView(valueInput);

                String strOutput=json_data.getString("output");
                TextView valueOutput = new TextView(this);
                valueOutput= setRow(valueOutput, strOutput, 6f);
                tr.addView(valueOutput);

                String strTanggal=json_data.getString("tanggalProses");
                TextView valueTanggal = new TextView(this);
                valueTanggal= setRow(valueTanggal, strTanggal, 4f);
                tr.addView(valueTanggal);

                String strKata=json_data.getString("kataBerulang");
                TextView valueKata = new TextView(this);
                strKata = kata(strKata);
                valueKata= setRow(valueKata, strKata, 4f);
                tr.addView(valueKata);

                tv.addView(tr);
                final View vline1 = new View(this);
                vline1.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                vline1.setBackgroundColor(Color.BLACK);
                tv.addView(vline1);  // add line below each row
            }

        }catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
            Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
        }
    }

    private String kata(String input)
    {
        String kataBaru = "";
        String[] inputs = input.split(";");
        for(int i = 0; i < inputs.length; i++)
            kataBaru = kataBaru + inputs[i] + " \n";

        return kataBaru;
    }

    private TextView setTitle(TextView titleTextView, String text, float weight)
    {
        titleTextView = new TextView(this);
        titleTextView.setPadding(8, 8, 8, 8);
        titleTextView.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.WRAP_CONTENT, weight));
        titleTextView.setText(text);
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTextSize(16);
        titleTextView.setBackgroundResource(R.drawable.table_cell);

        return  titleTextView;
    }
    private TextView setRow(TextView titleTextView, String text, float weight)
    {
        titleTextView.setPadding(5, 5, 5, 5);
        titleTextView.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.WRAP_CONTENT, weight));
        if(weight != 7f)
            titleTextView.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.MATCH_PARENT, weight));

        titleTextView.setText(text);
        titleTextView.setTextColor(Color.GRAY);
        titleTextView.setTextSize(15);
        titleTextView.setBackgroundResource(R.drawable.table_row_cell);

        return  titleTextView;
    }


}
