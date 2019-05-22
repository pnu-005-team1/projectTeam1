package kr.ac.pnu.cse.searchwidget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LocationsRepo {

    private DBHelper dbHelper;

    public LocationsRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Locations locations) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Locations.KEY_address, locations.address);
        values.put(Locations.KEY_email,locations.email);
        values.put(Locations.KEY_name, locations.name);

        // Inserting Row
        long locations_Id = db.insert(Locations.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) locations_Id;
    }
    public Cursor getLocationstList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                Locations.KEY_ROWID + "," +
                Locations.KEY_ID + "," +
                Locations.KEY_name + "," +
                Locations.KEY_email + "," +
                Locations.KEY_address +
                " FROM " + Locations.TABLE;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }


    public Cursor  getLocationsListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                Locations.KEY_ROWID + "," +
                Locations.KEY_ID + "," +
                Locations.KEY_name + "," +
                Locations.KEY_email + "," +
                Locations.KEY_address +
                " FROM " + Locations.TABLE +
                " WHERE " +  Locations.KEY_name + "  LIKE  '%" +search + "%' "
                ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }

    public Locations getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                Locations.KEY_ID + "," +
                Locations.KEY_name + "," +
                Locations.KEY_email + "," +
                Locations.KEY_address +
                " FROM " + Locations.TABLE
                + " WHERE " +
                Locations.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Locations locations = new Locations();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                locations.locations_ID =cursor.getInt(cursor.getColumnIndex(Locations.KEY_ID));
                locations.name =cursor.getString(cursor.getColumnIndex(Locations.KEY_name));
                locations.email  =cursor.getString(cursor.getColumnIndex(Locations.KEY_email));
                locations.address =cursor.getString(cursor.getColumnIndex(Locations.KEY_address));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return locations;
    }


}
