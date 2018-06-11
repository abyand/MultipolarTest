package com.example.abyandafa.multipolartest.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abyandafa.multipolartest.R;
import com.example.abyandafa.multipolartest.baseclass.OutputBasic2;
import com.example.abyandafa.multipolartest.show.ShowActivity;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainMVP.View {

    //widget
    private EditText inputText;
    private TextView outputBasic1;
    private TextView outputBasic2;
    private CardView viewBasic2;
    private Button toSaveDB;
    private Button toFilterInput;
    private Button showBasic2View;
    private Button toShowDatabaseActivity;

    //presenter
    private MainMVP.ViewPresenter vPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vPresenter = new MainPresenter(this);
        vPresenter.start();
    }

    @Override
    public void initView() {
        inputText = findViewById(R.id.input);
        outputBasic1 = findViewById(R.id.basic_1_output);
        outputBasic2 = findViewById(R.id.basic_2_output);
        viewBasic2 = findViewById(R.id.basic_2_view);
        toSaveDB = findViewById(R.id.save_to_db);
        toFilterInput = findViewById(R.id.start_filter);
        showBasic2View = findViewById(R.id.show_output_2);
        toShowDatabaseActivity = findViewById(R.id.show_database);

    }

    @Override
    public void initContent() {
        showBasic2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewBasic2.getVisibility() == View.GONE) setOutput2Visibility(false);
                else setOutput2Visibility(true);
            }
        });
        toFilterInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputText.getText().toString().isEmpty())
                    showToast("Please fill the input form!");
                else vPresenter.filterInput(inputText.getText().toString());
            }
        });
        toShowDatabaseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionGetDatabase();
            }
        });
        toSaveDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputText.getText().toString().isEmpty())
                    showToast("Please fill the input form!");
                else
                    showOptionSaveDatabase();
            }
        });


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
    public void setOutput2Visibility(boolean isVisible) {
        if(isVisible) {
            viewBasic2.setVisibility(View.GONE);
            showBasic2View.setText("Show Output 2");
        }
        else{
            viewBasic2.setVisibility(View.VISIBLE);
            showBasic2View.setText("Hide Output 2");
        }
    }

    @Override
    public void setProgressVisibility(boolean isVisble) {

    }

    @Override
    public void showOptionSaveDatabase() {
        final CharSequence option[] = new CharSequence[] {"Local", "Server"};
        final String input = inputText.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save to");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                if(which == 0)
                {
                    vPresenter.startSaveToLocalDatabase(input);

                }
            }
        });
        builder.show();

    }

    @Override
    public void showOptionGetDatabase() {
        final CharSequence option[] = new CharSequence[] {"Local", "Server"};
        final String input = inputText.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Show from");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                if(which == 0)
                    intent.putExtra("type", 0);
                else
                    intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
        builder.show();
    }

    @Override
    public void toLocalDatabaseActivity() {

    }

    @Override
    public void toServerDatabaseActivity() {

    }

    @Override
    public void onFilterFinished(String result) {
        outputBasic1.setText(result);
        Log.d("Woi", "onFilterFinished: " + result);
        Log.d("Woi", "onFilterFinished: " + inputText.getText().toString());

        if(outputBasic1.getText().toString().matches(" "))
            outputBasic1.setText(inputText.getText().toString());

    }

    @Override
    public void onBasic2ResultFinished(List<OutputBasic2> result) {
        outputBasic2.setText("");
        for(int i = 0; i < result.size(); i++)
        {
            OutputBasic2 current = result.get(i);
            String standardOutput = "Kata = " + current.getWord() +
                    ", jumlah perulangan = " + current.getRepetitiveCount() +
                    ", index pertama = " + current.getIndex() ;
            outputBasic2.setText(outputBasic2.getText().toString() + standardOutput + "\n\n");
        }
    }
}
