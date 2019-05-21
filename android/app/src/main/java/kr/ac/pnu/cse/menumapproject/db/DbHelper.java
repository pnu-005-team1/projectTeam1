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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /**
     * DB에 들어있는 모든 데이터를 보여주는 함수
     */
    public ArrayList<MenuModel> getAllMenuList() {
        ArrayList<MenuModel> menuModelList = new ArrayList<>();

        String sqlString = "SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlString, null);

        if(cursor != null){
            while(cursor.moveToNext()) {
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
        /*
        for(int i = 0 ; i < 10 ; i ++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("MENU", "DUMMY" + i);
            contentValues.put("PRICE", 1000 + i * 1000);
            contentValues.put("LAT", 35.231510f + (0.001f * Math.random()));
            contentValues.put("LNG", 129.086280f+ (0.001f * Math.random()));
            db.insert(TABLE_NAME, null, contentValues);
        }*/

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

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put("MENU", "청춘돈까스");
        contentValues3.put("PRICE", 6000);
        contentValues3.put("LAT", 35.231847);
        contentValues3.put("LNG", 129.085359);
        db.insert(TABLE_NAME, null, contentValues3);

        ContentValues contentValues4 = new ContentValues();
        contentValues4.put("MENU", "쑝쑝돈까스");
        contentValues4.put("PRICE", 6000);
        contentValues4.put("LAT", 35.230306);
        contentValues4.put("LNG", 129.084975);
        db.insert(TABLE_NAME, null, contentValues4);

        ContentValues contentValues5 = new ContentValues();
        contentValues5.put("MENU", "톤쇼우");
        contentValues5.put("PRICE", 8900);
        contentValues5.put("LAT", 35.230429);
        contentValues5.put("LNG", 129.084269);
        db.insert(TABLE_NAME, null, contentValues5);

        ContentValues contentValues6 = new ContentValues();
        contentValues6.put("MENU", "돈까스겸");
        contentValues6.put("PRICE", 6500);
        contentValues6.put("LAT", 35.230623);
        contentValues6.put("LNG", 129.084101);
        db.insert(TABLE_NAME, null, contentValues6);

        ContentValues contentValues7 = new ContentValues();
        contentValues7.put("MENU", "명동돈까스");
        contentValues7.put("PRICE", 6000);
        contentValues7.put("LAT", 35.229549);
        contentValues7.put("LNG", 129.083553);
        db.insert(TABLE_NAME, null, contentValues7);
    }

    /**
     * DB를 리셋하는 함수
     */
    public void dbReset() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
