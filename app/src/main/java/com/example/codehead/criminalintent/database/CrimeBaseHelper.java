package com.example.codehead.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.codehead.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.DATE;
import static com.example.codehead.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.SOLVED;
import static com.example.codehead.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.SUSPECT;
import static com.example.codehead.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.TITLE;
import static com.example.codehead.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.UUID;
import static com.example.codehead.criminalintent.database.CrimeDbSchema.CrimeTable.NAME;


public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL("create table "+ NAME+"(" +"_id integer primary key autoincrement,"+ UUID+","+ TITLE +","+ DATE +","+ SOLVED +","+ SUSPECT +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
