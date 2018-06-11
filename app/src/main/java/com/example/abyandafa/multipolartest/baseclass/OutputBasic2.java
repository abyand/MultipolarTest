package com.example.abyandafa.multipolartest.baseclass;

/**
 * Created by Abyan Dafa on 12/06/2018.
 */

public class OutputBasic2 {
    private String word;
    private int repetitiveCount;
    private int index;

    public OutputBasic2(String word, int repetitiveCount, int index) {
        this.word = word;
        this.repetitiveCount = repetitiveCount;
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getRepetitiveCount() {
        return repetitiveCount;
    }

    public void setRepetitiveCount(int repetitiveCount) {
        this.repetitiveCount = repetitiveCount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
