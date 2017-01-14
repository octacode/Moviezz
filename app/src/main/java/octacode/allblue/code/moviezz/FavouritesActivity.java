package octacode.allblue.code.moviezz;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import octacode.allblue.code.moviezz.adapter.FavouritesAdapter;
import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

public class FavouritesActivity extends AppCompatActivity {

    String LOG_TAG = getClass().getSimpleName();

    FavouritesAdapter favouritesAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
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
                Intent intent = new Intent(FavouritesActivity.this,DetailActivity.class);
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
                intent.putExtra("GENRE_IDS",cursor.getString(MainFragment.COLUMN_VOTE_COUNT));
                startActivity(intent);
            }
        });
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item? ")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No",null)
                .create();

        fav_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favouritesAdapter = (FavouritesAdapter)parent.getAdapter();
                        Cursor cursor = favouritesAdapter.getCursor();
                        SQLiteDatabase liteDatabase = new MovieDbHelper(FavouritesActivity.this).getWritableDatabase();
                        liteDatabase.delete(
                                MovieContract.FavouritesTable.TABLE_NAME,
                                MovieContract.FavouritesTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+ " =? ",
                                new String[]{cursor.getString(MainFragment.COLUMN_MOVIE_ID)}
                        );
                        favouritesAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                        startActivity(new Intent(FavouritesActivity.this,FavouritesActivity.class));
                    }
                });
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
        case android.R.id.home:
            onBackPressed();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
