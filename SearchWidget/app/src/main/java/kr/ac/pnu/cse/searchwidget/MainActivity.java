package kr.ac.pnu.cse.searchwidget;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter customAdapter;
    ListView listView;
    Cursor cursor;
    LocationsRepo locationsRepo ;
    private final static String TAG= MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationsRepo = new LocationsRepo(this);
        cursor=locationsRepo.getLocationstList();
        customAdapter = new CustomAdapter(MainActivity.this,  cursor, 0);
        listView = (ListView) findViewById(R.id.lstLocations);
        listView.setAdapter(customAdapter);

        if(cursor==null) insertDummy();
    }

    private void insertDummy(){
        Locations locations= new Locations();

        locations.address="22";
        locations.email="locationA@info.com";
        locations.name="Location A";
        locationsRepo.insert(locations);

        locationsRepo = new LocationsRepo(this);
        locations.address="22";
        locations.email="locationB@info.com";
        locations.name="Location B";
        locationsRepo.insert(locations);

        locationsRepo = new LocationsRepo(this);
        locations.address="22";
        locations.email="locationC@info.com";
        locations.name="Location C";
        locationsRepo.insert(locations);

        locationsRepo = new LocationsRepo(this);
        locations.address="22";
        locations.email="locationD@info.com";
        locations.name="Location D";
        locationsRepo.insert(locations);
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor=locationsRepo.getLocationsListByKeyword(s);
                    if (cursor==null){
                        Toast.makeText(MainActivity.this,"No records found!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                    }
                    customAdapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=locationsRepo.getLocationsListByKeyword(s);
                    if (cursor!=null){
                        customAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;

    }

}