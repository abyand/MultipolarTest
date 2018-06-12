package com.example.abyandafa.multipolartest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.abyandafa.multipolartest.baseclass.OutputBasic2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Abyan Dafa on 12/06/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "offline_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(BasicTableClass.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BasicTableClass.TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public long insert(String input, String output, List<OutputBasic2> list) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        String kataBerulang = "";

        for(int i = 0; i < list.size() ; i++)
        {
            OutputBasic2 current = list.get(i);
            kataBerulang+= "[" + current.getWord() + ", " +
                    current.getRepetitiveCount() + ", " +
                    current.getIndex()+"];";
        }
        Log.d("KUSINC", "insert: " + kataBerulang);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(c);

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(BasicTableClass.COLUMN_INPUT, input);
        values.put(BasicTableClass.COLUMN_OUTPUT, output);
        values.put(BasicTableClass.COLUMN_TANGGAL, formattedDate);
        values.put(BasicTableClass.COLUMN_KATA_BERULANG, kataBerulang);

        // insert row
        long id = db.insert(BasicTableClass.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public BasicTableClass getRow(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(BasicTableClass.TABLE_NAME,
                new String[]{BasicTableClass.COLUMN_ID, BasicTableClass.COLUMN_INPUT, BasicTableClass.COLUMN_OUTPUT, BasicTableClass.COLUMN_TANGGAL,
                BasicTableClass.COLUMN_KATA_BERULANG},
                BasicTableClass.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        BasicTableClass row = new BasicTableClass(
                cursor.getInt(cursor.getColumnIndex(BasicTableClass.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(BasicTableClass.COLUMN_INPUT)),
                cursor.getString(cursor.getColumnIndex(BasicTableClass.COLUMN_OUTPUT)),
                cursor.getString(cursor.getColumnIndex(BasicTableClass.COLUMN_TANGGAL)),
                cursor.getString(cursor.getColumnIndex(BasicTableClass.COLUMN_KATA_BERULANG)));

        // close the db connection
        cursor.close();

        return row;
    }

    public List<BasicTableClass> getAllRow() {
        List<BasicTableClass> rows = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + BasicTableClass.TABLE_NAME + " ORDER BY " +
                BasicTableClass.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasicTableClass row = new BasicTableClass();
                row.setId(cursor.getInt(cursor.getColumnIndex(BasicTableClass.COLUMN_ID)));
                row.setInput(cursor.getString(cursor.getColumnIndex(BasicTableClass.COLUMN_INPUT)));
                row.setOutput(cursor.getString(cursor.getColumnIndex(BasicTableClass.COLUMN_OUTPUT)));
                row.setTanggalProses(cursor.getString(cursor.getColumnIndex(BasicTableClass.COLUMN_TANGGAL)));
                row.setKataBerulang(cursor.getString(cursor.getColumnIndex(BasicTableClass.COLUMN_KATA_BERULANG)));
                rows.add(row);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return rows;
    }


}
