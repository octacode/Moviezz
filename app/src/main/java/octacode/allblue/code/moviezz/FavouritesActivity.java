package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

public class FavouritesActivity extends AppCompatActivity {

    String LOG_TAG = getClass().getSimpleName();

    FavouritesAdapter favouritesAdapter;
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
        favouritesAdapter = new FavouritesAdapter(this,cursor);
        fav_lv.setAdapter(favouritesAdapter);
        fav_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                favouritesAdapter = (FavouritesAdapter)parent.getAdapter();
                Cursor cursor = favouritesAdapter.getCursor();
                Intent intent = new Intent(FavouritesActivity.this,DetailActivity2.class);
                intent.putExtra("ID",cursor.getLong(MainFragment.COLUMN_ID));
                intent.putExtra("MOVIE_ID",cursor.getString(MainFragment.COLUMN_MOVIE_ID));
                intent.putExtra("ADULT",cursor.getString(MainFragment.COLUMN_ADULT));
                intent.putExtra("BACKDROP_URL",cursor.getString(MainFragment.COLUMN_BACKDROP_URL));
                intent.putExtra("OVERVIEW",cursor.getString(MainFragment.COLUMN_OVERVIEW));
                intent.putExtra("TITLE",cursor.getString(MainFragment.COLUMN_TITLE));
                intent.putExtra("POSTER_URL",cursor.getString(MainFragment.COLUMN_POSTER_URL));
                intent.putExtra("LANGUAGE",cursor.getString(MainFragment.COLUMN_LANGUAGE));
                intent.putExtra("POPULARITY",cursor.getString(MainFragment.COLUMN_POPULARITY));
                intent.putExtra("VOTE_AVG",cursor.getString(MainFragment.COLUMN_VOTE_AVERAGE));
                intent.putExtra("RATINGS",cursor.getString(MainFragment.COLUMN_RATINGS));
                intent.putExtra("REL_DATE",cursor.getString(MainFragment.COLUMN_REL_DATE));
                startActivity(intent);
            }
        });
    }
}
