package com.example.foody;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBDataSource {
    private SQLiteDatabase database;
    private DBHelper dbhelper;
    private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_MAKANAN, DBHelper.COLUMN_WAKTU,
            DBHelper.COLUMN_JUMLAH};

    public DBDataSource(Context context){
        dbhelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    private Catatanku cursorToCatatanku(Cursor cursor){
        Catatanku brng = new Catatanku();
        brng.setId(cursor.getLong(0));
        brng.setMakanan(cursor.getString(1));
        brng.setWaktu(cursor.getString(2));
        brng.setJumlah(cursor.getString(3));
        return brng;
    }


    public Catatanku createCatatanku(String makanan, String waktu, String jumlah){
        Catatanku catatanku = new Catatanku();
        ContentValues v = new ContentValues();
        v.put(DBHelper.COLUMN_MAKANAN, makanan);
        v.put(DBHelper.COLUMN_WAKTU, waktu);
        v.put(DBHelper.COLUMN_JUMLAH, jumlah);

        long insertId = database.insert(DBHelper.TABLE_NAME,null,v);
        Cursor c =database.query(DBHelper.TABLE_NAME,allColumns,
                DBHelper.COLUMN_ID+" = "+insertId,null,null,
                null,null);
        c.moveToFirst();
        catatanku = cursorToCatatanku(c);
        c.close();
        return catatanku;
    }

    public ArrayList<Catatanku> getAllCatatanku(){
        ArrayList<Catatanku> daftarCatatanku = new ArrayList<Catatanku>();
        Cursor c= database.query(DBHelper.TABLE_NAME,allColumns,null,null,
                null,null,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            Catatanku b = cursorToCatatanku(c);
            daftarCatatanku.add(b);
            c.moveToNext();
        }
        c.close();
        return daftarCatatanku;
    }

    //masih belum tuntas, get 1 Catatanku, update, delete
    public Catatanku getCatatanku(long id){
        Catatanku catatanku = new Catatanku();
        Cursor c = database.query(DBHelper.TABLE_NAME, allColumns, "_id = "+id,null,null,
                null,null);
        c.moveToFirst();
        catatanku = cursorToCatatanku(c);
        c.close();
        return catatanku;
    }

    public void updateCatatanku(Catatanku b){
        String s = "_id = "+b.getId();
        ContentValues v = new ContentValues();
        v.put(DBHelper.COLUMN_MAKANAN,b.getMakanan());
        v.put(DBHelper.COLUMN_WAKTU, b.getWaktu());
        v.put(DBHelper.COLUMN_JUMLAH, b.getJumlah());
        database.update(DBHelper.TABLE_NAME,v,s,null);
    }

    public void deleteCatatanku(long id) {
        String s = "_id = " + id;
        database.delete(DBHelper.TABLE_NAME,s,null);
    }

}
