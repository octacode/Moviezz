package octacode.allblue.code.moviezz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;


public class DetailFragment extends Fragment {

    View mRootView;
    String movie_id;
    private Menu menu;
    private AdView mAdView;

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
        this.menu=menu;
        SQLiteDatabase liteDatabase = new MovieDbHelper(getContext()).getWritableDatabase();
        String query_check = "Select * from "+ MovieContract.FavouritesTable.TABLE_NAME+" where "+ MovieContract.FavouritesTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+ " = "+movie_id;
        Cursor cursor = liteDatabase.rawQuery(query_check,null);
        if(cursor.getCount()<=0)
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.star_empty));
        else
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.star));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_favourites:
                long inserted_row = 0L;
                SQLiteDatabase liteDatabase = new MovieDbHelper(getContext()).getWritableDatabase();
                String query_check = "Select * from " + MovieContract.FavouritesTable.TABLE_NAME + " where " + MovieContract.FavouritesTable.COLUMN_MAIN_MOVIE_ID_DOUBLE + " = " + movie_id;
                Cursor cursor = liteDatabase.rawQuery(query_check, null);
                if (cursor.getCount() <= 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MovieContract.MainMovieTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE, genre_ids);
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
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.star));
                    Toast.makeText(getContext(), "Added to Favourites.", Toast.LENGTH_SHORT).show();
                } else {
                    liteDatabase.delete(
                            MovieContract.FavouritesTable.TABLE_NAME,
                            MovieContract.FavouritesTable.COLUMN_MAIN_MOVIE_ID_DOUBLE + " =? ",
                            new String[]{movie_id}
                    );
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.star_empty));
                    Toast.makeText(getContext(), "Removed from Favourites.", Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, "Deleted Sucessfully");
                }
                Log.d(LOG_TAG, String.valueOf(inserted_row));
                break;

            case R.id.action_share_movie:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, title+" rated "+ratings+" looks great to me. Have you seen it? #Moviebuzz");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String adult,backdrop_url,overview,title,poster_url,language,ratings,rel_date,genre_ids;
    double vote_avg,popularity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_detail, container, false);
        HolderDetail viewHolder = new HolderDetail(mRootView);
        adult = getActivity().getIntent().getStringExtra("ADULT");
        language = getActivity().getIntent().getStringExtra("LANGUAGE");
        popularity = Double.parseDouble(getActivity().getIntent().getStringExtra("POPULARITY"));
        ratings = getActivity().getIntent().getStringExtra("RATINGS");
        movie_id = getActivity().getIntent().getStringExtra("MOVIE_ID");
        backdrop_url = getActivity().getIntent().getStringExtra("BACKDROP_URL");
        overview = getActivity().getIntent().getStringExtra("OVERVIEW");
        title = getActivity().getIntent().getStringExtra("TITLE");
        poster_url = getActivity().getIntent().getStringExtra("POSTER_URL");
        vote_avg = Double.parseDouble(getActivity().getIntent().getStringExtra("VOTE_AVG"));
        rel_date = getActivity().getIntent().getStringExtra("REL_DATE");
        genre_ids = getActivity().getIntent().getStringExtra("GENRE_IDS");
        switch(adult){
            case "true": adult = "True";break;
            case "false": adult = "False";break;
            default: adult="";
        }
        viewHolder.title.setText(adult);
        viewHolder.date.setText(Utility.datePresenter(rel_date));
        viewHolder.overview.setText(overview);
        viewHolder.ratings.setText(String.valueOf((double) Math.round(vote_avg * 10d) / 10d));
        DetailActivity.mTitle.setText(title);
        DetailActivity.main_title.setText(title);
        Picasso.with(getContext()).load(poster_url).into(DetailActivity.image_view_poster);
        Picasso.with(getContext()).load(backdrop_url).into(DetailActivity.image_backdrop);
        mAdView = (AdView) mRootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return mRootView;
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public class HolderDetail {
        private TextView title, ratings, date, overview;

        HolderDetail(View mRootView) {
            title = (TextView) mRootView.findViewById(R.id.adult_detail);
            ratings = (TextView) mRootView.findViewById(R.id.detail_ratings);
            date = (TextView) mRootView.findViewById(R.id.date_detail);
            overview = (TextView) mRootView.findViewById(R.id.overview_detail);
        }
    }
}