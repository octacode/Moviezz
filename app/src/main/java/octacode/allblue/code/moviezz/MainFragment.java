package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import octacode.allblue.code.moviezz.data.MovieContract;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private Main_Movie_Adapter movieAdapter;
    private RecyclerView recyclerView;
    private List<Transfer> arrayList=new ArrayList<>();
    private Parcelable recyclerViewState;
    private static final int MOVIE_LOADER = 0;

    private final String[] MOVIE_COLUMNS={
            MovieContract.MainMovieTable._ID,
            MovieContract.MainMovieTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE,
            MovieContract.MainMovieTable.COLUMN_MAIN_ADULT_TEXT,
            MovieContract.MainMovieTable.COLUMN_MAIN_BACKDROP_PATH_TEXT,
            MovieContract.MainMovieTable.COLUMN_MAIN_GENRE_IDS_TEXT,
            MovieContract.MainMovieTable.COLUMN_MAIN_MOVIE_ID_DOUBLE,
            MovieContract.MainMovieTable.COLUMN_MAIN_ORG_LANGUAGE_TEXT,
            MovieContract.MainMovieTable.COLUMN_MAIN_OVERVIEW_TEXT,
            MovieContract.MainMovieTable.COLUMN_MAIN_PAGE_INT,
            MovieContract.MainMovieTable.COLUMN_MAIN_POPULARITY_DOUBLE,
            MovieContract.MainMovieTable.COLUMN_MAIN_POSTER_PATH_TEXT,
            MovieContract.MainMovieTable.COLUMN_MAIN_RATINGS_DOUBLE,
            MovieContract.MainMovieTable.COLUMN_MAIN_TITLE_TEXT,
            MovieContract.MainMovieTable.COLUMN_MAIN_VOTE_AVERAGE_DOUBLE
    };

    public final int COLUMN_ID=0;
    public final int COLUMN_VOTE_COUNT=1;
    public final int COLUMN_ADULT=2;
    public final int COLUMN_BACKDROP_URL=3;
    public final int COLUMN_GENRE_IDS=4;
    public final int COLUMN_MOVIE_ID=5;
    public final int COLUMN_LANGUAGE=6;
    public final int COLUMN_OVERVIEW=7;
    public final int COLUMN_PAGE=8;
    public final int COLUMN_POPULARITY=9;
    public final int COLUMN_POSTER_URL=10;
    public final int COLUMN_RATINGS=11;
    public final int COLUMN_TITLE=12;
    public final int COLUMN_VOTE_AVERAGE=13;


    public MainFragment() {
    }

    public void updateMovieRecycler() {
        FetchMovieTask fetchMovieTask=new FetchMovieTask(getContext());
        String settings=PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.pref_sort_key),"Most Popular");
        fetchMovieTask.execute(settings);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieRecycler();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.first_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(getContext(),"Settings",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),SettingsActivit.class));
                break;
            case R.id.action_favourites:
                Toast.makeText(getContext(),"Favourites",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_refresh:
                updateMovieRecycler();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new Main_Movie_Adapter(getContext(),arrayList);
        recyclerView=(RecyclerView) rootView.findViewById(R.id.main_grid_view);
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if((mLayoutManager.getChildCount()+mLayoutManager.findFirstVisibleItemPosition()) >= mLayoutManager.getItemCount())
                    updateMovieRecycler();
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                MovieContract.MainMovieTable.CONTENT_URI,
                MOVIE_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
