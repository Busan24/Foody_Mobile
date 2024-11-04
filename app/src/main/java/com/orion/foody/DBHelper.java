package com.orion.foody;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String db_name = "foody";
    public static final int db_version = 1;
    public static final String TABLE_NAME = "tbl_catatanku";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MAKANAN = "makanan_catatanku";
    public static final String COLUMN_WAKTU = "waktu_catatanku";
    public static final String COLUMN_JUMLAH = "jumlah_catatanku";

    public static final String sql_create_table = "create table "+
            TABLE_NAME + " ("+
            COLUMN_ID  + " integer primary key autoincrement, "+
            COLUMN_MAKANAN  + " varchar(50) not null, "+
            COLUMN_WAKTU  + " time not null, "+
            COLUMN_JUMLAH  + " integer not null);";

    public DBHelper(Context context){
        super(context,db_name,null, db_version);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(sql_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
