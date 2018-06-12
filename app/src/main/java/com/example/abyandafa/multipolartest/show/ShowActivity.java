package com.example.abyandafa.multipolartest.show;

import android.content.Context;
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
import com.example.abyandafa.multipolartest.baseclass.OutputBasic2;
import com.example.abyandafa.multipolartest.database.BasicTableClass;
import com.example.abyandafa.multipolartest.database.DatabaseHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ShowActivity extends AppCompatActivity implements ShowMVP.View{


    private ShowMVP.ViewPresenter vPresenter;

    // 0 mean from local, 1 mean from server
    private int showType;
    private TableLayout tableLayout;

    private TextView titleID;
    private TextView titleInput;
    private TextView titleOutput;
    private TextView titleTanggal;
    private TextView titleKata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        vPresenter = new ShowPresenter(this);
        vPresenter.start();
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        showType =bundle.getInt("type");
        tableLayout = findViewById(R.id.table_layout);
    }

    @Override
    public void initContent() {
        this.vPresenter.getDatabase(showType);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressVisibility(boolean isVisble) {

    }

    @Override
    public void onDatabaseGet(List<BasicTableClass> listBasicTable) {
        tableLayout.removeAllViews();

        //initialize Title Table
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
        tableLayout.addView(trTitle);

        for(int i = 0; i < listBasicTable.size(); i++)
        {
            BasicTableClass currentRow = listBasicTable.get(i);

            TableRow tr=new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            tr.removeAllViews();

            String strID=String.valueOf(currentRow.getId());
            TextView valueID= setRow(new TextView(this), strID, 1f);

            String strInput = currentRow.getInput();
            TextView valueInput= setRow(new TextView(this), strInput, 7f);

            String strOutput = currentRow.getOutput();
            TextView valueOutput= setRow(new TextView(this), strOutput, 6f);

            String strTanggal = currentRow.getTanggalProses();
            TextView valueTanggal= setRow(new TextView(this), strTanggal, 4f);

            String strKata = currentRow.getKataBerulang();
            TextView valueKata= setRow(new TextView(this), strKata, 4f);

            tr.addView(valueID);
            tr.addView(valueInput);
            tr.addView(valueOutput);
            tr.addView(valueTanggal);
            tr.addView(valueKata);


            tableLayout.addView(tr);
            final View vline1 = new View(this);
            vline1.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
            vline1.setBackgroundColor(Color.BLACK);
            tableLayout.addView(vline1);  // add line below each row

        }

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
