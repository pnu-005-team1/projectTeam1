package kr.ac.pusan.cse.category5.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper{

    private static final String DB_Name="Database.db";
    private static final int DB_VER =1;

    public Database(Context context) {
        super(context, DB_Name,null,DB_VER);
    }


    public void addToFavorites(String RestaurantId)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(RestaurantId) VALUES('%s');",RestaurantId);
        db.execSQL(query);

    }

    public void removeFromFavorites(String RestaurantId)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE RestaurantId='%s';",RestaurantId);
        db.execSQL(query);

    }

    public boolean isFavorite(String RestaurantId)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites WHERE RestaurantId='%s';",RestaurantId);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()<=0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }
}
