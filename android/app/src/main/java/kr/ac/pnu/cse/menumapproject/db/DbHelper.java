package kr.ac.pnu.cse.menumapproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import kr.ac.pnu.cse.menumapproject.model.MenuModel;

public class DbHelper extends SQLiteOpenHelper {

    static private String TABLE_NAME = "menuTable";

    private int dbVersion = 1;
    private Context context;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.dbVersion = version;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlString = " CREATE TABLE " + TABLE_NAME + " ( " +
                " _ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " MENU TEXT, " +
                " PRICE INTEGER, " +
                " LAT REAL , " +
                " LNG REAL  ) ";

        db.execSQL(sqlString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * DB에 들어있는 모든 데이터를 보여주는 함수
     */
    public ArrayList<MenuModel> getAllMenuList() {
        ArrayList<MenuModel> menuModelList = new ArrayList<>();

        String sqlString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlString, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                MenuModel menu = new MenuModel();
                menu.menuName = cursor.getString(cursor.getColumnIndex("MENU"));
                menu.menuPrice = cursor.getInt(cursor.getColumnIndex("PRICE"));
                menu.lat = cursor.getFloat(cursor.getColumnIndex("LAT"));
                menu.lng = cursor.getFloat(cursor.getColumnIndex("LNG"));

                menuModelList.add(menu);
            }
            cursor.close();
        }

        return menuModelList;
    }

    /**
     * DB에 더미 데이터를 넣는 함수
     */
    public void addDummyData() {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("MENU", "대왕돈까스");
        contentValues1.put("PRICE", 6000);
        contentValues1.put("LAT", 35.235123);
        contentValues1.put("LNG", 129.087664);
        db.insert(TABLE_NAME, null, contentValues1);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("MENU", "동경생돈까스");
        contentValues2.put("PRICE", 8000);
        contentValues2.put("LAT", 35.233236);
        contentValues2.put("LNG", 129.085307);
        db.insert(TABLE_NAME, null, contentValues2);
    }

    /**
     * DB를 리셋하는 함수
     */
    public void dbReset() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void addMenu(String restName, float lat, float lng) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("MENU", restName);
        contentValues1.put("PRICE", 0);
        contentValues1.put("LAT", lat);
        contentValues1.put("LNG", lng);
        db.insert(TABLE_NAME, null, contentValues1);
    }

    public void removeMenu(String restName) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_NAME, "MENU=\"" + restName + "\"", null);
    }
}
