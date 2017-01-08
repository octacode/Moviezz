package octacode.allblue.code.moviezz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

public class FavouritesActivity extends AppCompatActivity {

    String LOG_TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ListView fav_lv = (ListView)findViewById(R.id.favourites_list);
        SQLiteDatabase liteDatabase = new MovieDbHelper(this).getWritableDatabase();
        Cursor cursor = liteDatabase.query(MovieContract.FavouritesTable.TABLE_NAME,
                MainFragment.MOVIE_COLUMNS,
                null,
                null,
                null,
                null,
                null);
        while(cursor.moveToNext()){
            int title_index=cursor.getColumnIndex(MovieContract.FavouritesTable.COLUMN_MAIN_TITLE_TEXT);
            Log.d(LOG_TAG,cursor.getString(title_index));
        }
    }
}
