package com.example.hoangtienmanh.imagelistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hoang Tien Manh on 3/29/2018.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "Tintuc_Manager";


    // Tên bảng: Tintuc360.
    private static final String TABLE_TINTUC = "Tintuc360";

    private static final String COLUMN_TINTUC_ID ="_id";
    private static final String COLUMN_TINTUC_TITLE ="Tintuc_Title";
    private static final String COLUMN_TINTUC_TTCONTENT = "Tintuc_Content";
    private static final String COLUMN_TINTUC_CONTENT1 = "Tintuc_Content1";
    private static final String COLUMN_TINTUC_CONTENT2 = "Tintuc_Content2";
    private static final String COLUMN_TINTUC_DANHMUC = "Tintuc_Danhmuc";
    private static final String COLUMN_TINTUC_LINKBAIVIET = "Tintuc_Linkbaiviet";
    private static final String COLUMN_TINTUC_LINKANH = "Tintuc_Linkanh";

    // Tên bảng: NoiDung.


    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo các bảng.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script tạo bảng.
        String script1 = "CREATE TABLE " + TABLE_TINTUC +"("
                + COLUMN_TINTUC_ID + " INTEGER PRIMARY KEY," + COLUMN_TINTUC_TITLE + " TEXT ,"+COLUMN_TINTUC_TTCONTENT+" TEXT,"
                + COLUMN_TINTUC_CONTENT1 + " TEXT," + COLUMN_TINTUC_CONTENT2+ " TEXT,"+ COLUMN_TINTUC_DANHMUC
                + " TEXT," +COLUMN_TINTUC_LINKBAIVIET + " TEXT," + COLUMN_TINTUC_LINKANH +" TEXT" + ")";
        db.execSQL(script1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       // Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TINTUC);


        // Và tạo lại.
        onCreate(db);
    }

    // Nếu trong bảng Note chưa có dữ liệu,
    // Trèn vào mặc định 2 bản ghi.



//--------------------Bảng tin tức ------------------------------------------------------------------
    public void addTintuc(Tintuc tintuc) {
        //Log.i(TAG, "MyDatabaseHelper.addTINTUC ... " + tintuc.danhmuc);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TINTUC_TITLE, tintuc.tieude);
        values.put(COLUMN_TINTUC_TTCONTENT, tintuc.noidungtieude);
        values.put(COLUMN_TINTUC_CONTENT1, tintuc.noidung1);
        values.put(COLUMN_TINTUC_CONTENT2, tintuc.noidung2);
        values.put(COLUMN_TINTUC_DANHMUC, tintuc.danhmuc);
        values.put(COLUMN_TINTUC_LINKBAIVIET,tintuc.linkBaiviet);
        values.put(COLUMN_TINTUC_LINKANH,tintuc.linkAnh);


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_TINTUC, null, values);


        // Đóng kết nối database.
        db.close();
    }


    public Tintuc getTintuc(int id) {
        //Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TINTUC, new String[] { COLUMN_TINTUC_ID,
                        COLUMN_TINTUC_TITLE, COLUMN_TINTUC_TTCONTENT, COLUMN_TINTUC_CONTENT1,COLUMN_TINTUC_CONTENT2, COLUMN_TINTUC_DANHMUC , COLUMN_TINTUC_LINKBAIVIET, COLUMN_TINTUC_LINKANH  }, COLUMN_TINTUC_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Tintuc tintuc = new Tintuc(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
        // return note
        return tintuc;
    }

    public Tintuc gettintuc(String tieude){
        Tintuc tintuc = new Tintuc();
        String sql = "SELECT * FROM "+TABLE_TINTUC;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);


        if (cursor.moveToFirst()) {
            do {
                if( cursor.getString(1).equals(tieude)) {
                    tintuc.Id = Integer.parseInt(cursor.getString(0));
                    tintuc.tieude = (cursor.getString(1));
                    tintuc.noidungtieude = (cursor.getString(2));
                    tintuc.noidung1 = (cursor.getString(3));
                    tintuc.noidung2 = (cursor.getString(4));
                    tintuc.danhmuc = (cursor.getString(5));
                    tintuc.linkBaiviet = (cursor.getString(6));
                    tintuc.linkAnh = (cursor.getString(7));
                }
            } while (cursor.moveToNext());
        }
            return tintuc;
    }

    public ArrayList<Tintuc> getTintuc_danhmuc(String danhmuc) {
        //Log.i(TAG, "MyDatabaseHelper.getAllTintuc ... " );

        ArrayList<Tintuc> tintucList = new ArrayList<Tintuc>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TINTUC;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                if( cursor.getString(1).equals(danhmuc)) {
                    Tintuc tintuc = new Tintuc();
                    tintuc.Id = Integer.parseInt(cursor.getString(0));
                    tintuc.tieude = (cursor.getString(1));
                    tintuc.noidungtieude = (cursor.getString(2));
                    tintuc.noidung1 = (cursor.getString(3));
                    tintuc.noidung2 = (cursor.getString(4));
                    tintuc.danhmuc = (cursor.getString(5));
                    tintuc.linkBaiviet = (cursor.getString(6));
                    tintuc.linkAnh = (cursor.getString(7));

                    // Thêm vào danh sách.
                    tintucList.add(tintuc);
                }
            } while (cursor.moveToNext());
        }

        // return note list
        return tintucList;
    }

    public ArrayList<Tintuc> getAllTintuc() {
       // Log.i(TAG, "MyDatabaseHelper.getAllTintuc ... " );

        ArrayList<Tintuc> tintucList = new ArrayList<Tintuc>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TINTUC;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Tintuc tintuc = new Tintuc();
                tintuc.Id = Integer.parseInt(cursor.getString(0));
                tintuc.tieude = (cursor.getString(1));
                tintuc.noidungtieude = (cursor.getString(2));
                tintuc.noidung1 = (cursor.getString(3));
                tintuc.noidung2 = (cursor.getString(4));
                tintuc.danhmuc = (cursor.getString(5));
                tintuc.linkBaiviet = (cursor.getString(6));
                tintuc.linkAnh = (cursor.getString(7));

                // Thêm vào danh sách.
                tintucList.add(tintuc);
            } while (cursor.moveToNext());
        }

        // return note list
        return tintucList;
    }

    public int getTintucCount() {
        //Log.i(TAG, "MyDatabaseHelper.getTintucCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_TINTUC;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public void deleteTintuc() {
        Log.e("CMM","da xoa");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TINTUC,null,null);
        db.close();
    }

    public int updateTintuc(Tintuc tintuc) {
        //Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + tintuc.noidung1);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TINTUC_TITLE, tintuc.tieude);
        values.put(COLUMN_TINTUC_TTCONTENT, tintuc.noidungtieude);
        values.put(COLUMN_TINTUC_CONTENT1, tintuc.noidung1);
        values.put(COLUMN_TINTUC_CONTENT2, tintuc.noidung2);
        values.put(COLUMN_TINTUC_DANHMUC, tintuc.danhmuc);
        values.put(COLUMN_TINTUC_LINKBAIVIET,tintuc.linkBaiviet);
        values.put(COLUMN_TINTUC_LINKANH,tintuc.linkAnh);

        // updating row
        return db.update(TABLE_TINTUC, values, COLUMN_TINTUC_ID + " = ?",
                new String[]{String.valueOf(tintuc.Id)});
    }
//---------------------------------------------------------------------------------------------------



//-----------------------------------Bảng Chi tiết tin tức ------------------------------------------


}