package com.example.abyandafa.multipolartest.database;

/**
 * Created by Abyan Dafa on 12/06/2018.
 */

public class BasicTableClass {
    public static final String TABLE_NAME = "BasicTable";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_INPUT = "input";
    public static final String COLUMN_OUTPUT= "output";
    public static final String COLUMN_TANGGAL= "tanggal_proses";
    public static final String COLUMN_KATA_BERULANG= "kata_berulang";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_INPUT + " TEXT,"
                    + COLUMN_OUTPUT + " TEXT,"
                    + COLUMN_TANGGAL + " TEXT,"
                    + COLUMN_KATA_BERULANG + " TEXT"
                    + ")";

    private int id;
    private String input;
    private String output;
    private String tanggalProses;
    private String kataBerulang;

    public BasicTableClass() {
    }

    public BasicTableClass(int id, String input, String output, String tanggalProses, String kataBerulang) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.tanggalProses = tanggalProses;
        this.kataBerulang = kataBerulang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getTanggalProses() {
        return tanggalProses;
    }

    public void setTanggalProses(String tanggalProses) {
        this.tanggalProses = tanggalProses;
    }

    public String getKataBerulang() {
        return kataBerulang;
    }

    public void setKataBerulang(String kataBerulang) {
        this.kataBerulang = kataBerulang;
    }
}
