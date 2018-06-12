package com.example.abyandafa.multipolartest.main;

import android.content.Context;
import android.util.Log;

import com.example.abyandafa.multipolartest.baseclass.OutputBasic2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Abyan Dafa on 11/06/2018.
 */

public class MainPresenter implements MainMVP.ViewPresenter, MainMVP.ModelPresenter {

    private MainMVP.View view;
    private MainMVP.Model model;
    private Context context;


    private String output1;
    private List<OutputBasic2> listOutput2;


    public MainPresenter(MainMVP.View view) {
        this.view = view;
        this.context = view.getContext();
        this.model = new MainModel(this);
        this.listOutput2 = new ArrayList<>();
    }

    @Override
    public void start() {
        this.view.initView();
        this.view.initContent();
    }

    @Override
    public void onRequestFailed(String message) {

    }

    @Override
    public void filterInput(String input) {
        listOutput2.clear();
        output1 = "";
        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)",
                Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher reMatcher = re.matcher(input);
        String newSentences;
        String output = "";
        while (reMatcher.find()) {
            newSentences = reMatcher.group();
            newSentences = removeConsecutiveSameWord(newSentences);
            output+= newSentences + " ";
        }
        this.view.onFilterFinished(output);
        this.output1 = output;
        basic2ResultFinished();
    }

    @Override
    public void basic2ResultFinished() {
        this.view.onBasic2ResultFinished(listOutput2);
    }

    public String removeConsecutiveSameWord(String input)
    {
        //String regex = "\\b(\\w+)(\\W+\\1\\b)+";
        String regex = "\\b(\\w+)(\\W+\\1\\b)+";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        String output = input;

        Matcher m = p.matcher(input);
        while (m.find()) {
            if(m.group().contains("-")) continue;
            output = output.replaceAll(m.group(), m.group(1));
            Log.d("KEKEKE", "removeConsecutiveSameWord: " + output);
            addListBasic2Output(m.group(), input);
        }
        return output;
    }
    private void addListBasic2Output(String output, String text){
        String[] words = output.split(" ");
        int repetitiveCount = words.length;
        int wordFirstIndex = getWordFirstIndex(words[0], text);
        listOutput2.add(new OutputBasic2(words[0], repetitiveCount, wordFirstIndex));
    }

    private int getWordFirstIndex(String word, String text)
    {
        String[] words = text.split(" ");
        for(int i = 0; i < words.length; i++)
        {
            if(word.equals(words[i])) return i;
        }
        return -1;
    }



    @Override
    public void startSaveToLocalDatabase(String input) {
        filterInput(input);
        Log.d("kucingku", "startSaveToLocalDatabase: " + output1);
        this.model.saveToLocalDatabase(input, output1, listOutput2);
    }

    @Override
    public void startSaveToServerDatabase(String input) {
        filterInput(input);
        this.model.saveToServerDatabase(input, output1, listOutput2);
    }


    @Override
    public void onSaveFinished(boolean isSuccess, String message) {
        if(isSuccess) this.view.showToast(message);
        else this.view.showToast(message);
    }

    @Override
    public Context getContext()
    {
        return context;
    }
}
