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
                menu.menuName = cursor.getString(0);
                menu.menuPrice = cursor.getInt(1);
                menu.lat = cursor.getFloat(2);
                menu.lng = cursor.getFloat(3);

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
        String sqlString = "INSERT INTO " + TABLE_NAME + " ( MENU, PRICE, LAT, LNG ) VALUES (DUMMY1, 1000, 35.231510, 129.086280) ";

        SQLiteDatabase db = getWritableDatabase();

        for(int i = 0 ; i < 10 ; i ++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("MENU", "DUMMY" + i);
            contentValues.put("PRICE", 1000 + i * 1000);
            contentValues.put("LAT", 35.231510f + (0.000005f * i));
            contentValues.put("LNG", 129.086280f+ (0.000002f * i));
            db.insert(TABLE_NAME, null, contentValues);
        }
    }
}
