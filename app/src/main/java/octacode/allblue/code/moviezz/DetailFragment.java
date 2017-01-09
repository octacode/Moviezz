package octacode.allblue.code.moviezz;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;


public class DetailFragment extends Fragment {

    View mRootView;
    String movie_id;

    private String LOG_TAG = DetailFragment.this.getClass().getSimpleName();

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_favourites:
                long inserted_row=0L;
                SQLiteDatabase liteDatabase = new MovieDbHelper(getContext()).getWritableDatabase();
                String query_check = "Select * from "+ MovieContract.FavouritesTable.TABLE_NAME+" where "+ MovieContract.FavouritesTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+ " = "+movie_id;
                Cursor cursor = liteDatabase.rawQuery(query_check,null);
                if(cursor.getCount()<=0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE, vot_count);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_ADULT_TEXT, adult);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_BACKDROP_PATH_TEXT, backdrop_url);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_GENRE_IDS_TEXT, rel_date);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_MOVIE_ID_DOUBLE, movie_id);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_ORG_LANGUAGE_TEXT, language);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_OVERVIEW_TEXT, overview);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_PAGE_INT, 1);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_POPULARITY_DOUBLE, popularity);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_POSTER_PATH_TEXT, poster_url);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_RATINGS_DOUBLE, ratings);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_TITLE_TEXT, title);
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_VOTE_AVERAGE_DOUBLE, vote_avg);
                    inserted_row = liteDatabase.insert(MovieContract.FavouritesTable.TABLE_NAME, null, contentValues);
                }
                else {
                    liteDatabase.delete(
                            MovieContract.FavouritesTable.TABLE_NAME,
                            MovieContract.FavouritesTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+ " =? ",
                            new String[]{movie_id}
                            );
                    Log.d(LOG_TAG,"Deleted Sucessfully");
                }
                Log.d(LOG_TAG, String.valueOf(inserted_row));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    String adult,backdrop_url,overview,title,poster_url,language,ratings,rel_date;
    double vot_count,vote_avg,popularity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_detail, container, false);
        HolderDetail viewHolder = new HolderDetail(mRootView);
        movie_id = getActivity().getIntent().getStringExtra("MOVIE_ID");
        adult = getActivity().getIntent().getStringExtra("ADULT");
        backdrop_url = getActivity().getIntent().getStringExtra("BACKDROP_URL");
        overview = getActivity().getIntent().getStringExtra("OVERVIEW");
        title = getActivity().getIntent().getStringExtra("TITLE");
        poster_url = getActivity().getIntent().getStringExtra("POSTER_URL");
        language = getActivity().getIntent().getStringExtra("LANGUAGE");
        popularity = Double.parseDouble(getActivity().getIntent().getStringExtra("POPULARITY"));
        ratings = getActivity().getIntent().getStringExtra("RATINGS");
        vote_avg = Double.parseDouble(getActivity().getIntent().getStringExtra("VOTE_AVG"));
        rel_date = getActivity().getIntent().getStringExtra("REL_DATE");
        viewHolder.title.setText(title);
        viewHolder.date.setText(rel_date);
        viewHolder.overview.setText(overview);
        viewHolder.ratings.setText(String.valueOf((double) Math.round(vote_avg * 10d) / 10d));
        DetailActivity2.mTitle.setText(title);
        DetailActivity2.main_title.setText(title);
        Picasso.with(getContext()).load(poster_url).into(DetailActivity2.image_view_poster);
        Picasso.with(getContext()).load(backdrop_url).into(DetailActivity2.image_backdrop);
        return mRootView;
    }

    public class HolderDetail {
        private TextView title, ratings, date, overview, language, runtime, homepage, budget, revenue, adult;

        public HolderDetail(View mRootView) {
            title = (TextView) mRootView.findViewById(R.id.title_detail);
            ratings = (TextView) mRootView.findViewById(R.id.detail_ratings);
            date = (TextView) mRootView.findViewById(R.id.date_detail);
            overview = (TextView) mRootView.findViewById(R.id.overview_detail);
            /*
            language = (TextView) mRootView.findViewById(R.id.original_language_detail);
            runtime = (TextView) mRootView.findViewById(R.id.runtime_detail);
            homepage = (TextView) mRootView.findViewById(R.id.homepage_detail);
            budget = (TextView) mRootView.findViewById(R.id.budget_detail);
            revenue = (TextView) mRootView.findViewById(R.id.revenue_detail);
            adult = (TextView) mRootView.findViewById(R.id.adult_detail);
            */
        }
    }
}