package com.example.studytreasures.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public MySQLiteHelper(@Nullable Context context) {
        super(context,"studytreasures.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table person(id varchar(10) primary key,num integer,username varchar(30),password varchar(30),money integer)");
        sqLiteDatabase.execSQL("create table shopping(id varchar(10),goodsName varchar(30),time bigint)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
