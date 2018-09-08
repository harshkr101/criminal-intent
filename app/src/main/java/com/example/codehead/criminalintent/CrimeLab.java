package com.example.codehead.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codehead.criminalintent.database.CrimeBaseHelper;
import com.example.codehead.criminalintent.database.CrimeCursorWrapper;
import com.example.codehead.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.example.codehead.criminalintent.database.CrimeDbSchema.CrimeTable.Cols;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext=context.getApplicationContext();
        mDatabase=new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public List<Crime> getCrimes() {
        List<Crime> crimes=new ArrayList<>();
        CrimeCursorWrapper cursorWrapper=queryCrimes(null,null);
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor=queryCrimes(Cols.UUID+"= ?",new String[] {id.toString()});
        try{
            if(cursor.getCount()==0){ return null;}
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }

    public void addCrime(Crime c){
        ContentValues values=getContentValues(c);
        mDatabase.insert(CrimeTable.NAME,null,values);

    }

    public File getPhotoFile(Crime crime){
        File filesDir=mContext.getFilesDir();
        return new File(filesDir,crime.getPhotoFileame());
    }

    public void updateCrime(Crime crime){
        String uuidString= crime.getId().toString();
        ContentValues values=getContentValues(crime);
        mDatabase.update(CrimeTable.NAME,values, Cols.UUID+"= ?",new String[]{uuidString});
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor=mDatabase.query(CrimeTable.NAME,null,whereClause,whereArgs,null,null,null);
        return  new CrimeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Cols.UUID,crime.getId().toString());
        contentValues.put(Cols.TITLE,crime.getTitle());
     //  contentValues.put(Cols.DETAILS,crime.getDetails());
        contentValues.put(Cols.DATE,crime.getDate().getTime());
        contentValues.put(Cols.SOLVED,crime.isSolved());
        contentValues.put(Cols.SUSPECT,crime.getSuspect());
        return contentValues;
    }
}
