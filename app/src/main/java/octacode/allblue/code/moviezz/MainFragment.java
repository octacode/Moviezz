package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class MainFragment extends Fragment {

    private Main_Movie_Adapter adapter;
    private RecyclerView recyclerView;
    private int PAGE_LOADED=0;
    private List<MovieInfo> arrayList=new ArrayList<>();
    private Parcelable recyclerViewState;


    public MainFragment() {
    }

    public void updateMovieRecycler() {
        FetchMovieTask fetchMovieTask=new FetchMovieTask();
        String settings=PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.pref_sort_key),"Most Popular");
        fetchMovieTask.execute(settings, String.valueOf(PAGE_LOADED+1));
        PAGE_LOADED++;
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

        adapter = new Main_Movie_Adapter(getContext(),arrayList);

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
        return rootView;

    }

    public class FetchMovieTask extends AsyncTask<String,Void,ArrayList<MovieInfo>>{

        private final String LOG_TAG=FetchMovieTask.class.getSimpleName();

        private ArrayList<MovieInfo> getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String ID="id";
            final String ORGLANG="original_language";
            final String ORGTITLE="original_title";
            final String OVER="overview";
            final String RELDATE="release_date";
            final String POSTERPATH="poster_path";
            final String POPULARITY="popularity";
            final String VOTAVG="vote_average";

            final String RESULT="results";
            final String POSTER_BASE_URL="http://image.tmdb.org/t/p/w185";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(RESULT);

            ArrayList<MovieInfo> resultStrs=new ArrayList<>();

            for(int i = 0; i < movieArray.length(); i++) {

                String id;
                String orgLang;
                String orgTitle;
                String overview;
                String relDate;
                String postURL;
                String popularity;
                String votAvg;

                JSONObject movieInfo = movieArray.getJSONObject(i);

                id=movieInfo.getString(ID);
                orgLang=movieInfo.getString(ORGLANG);
                orgTitle=movieInfo.getString(ORGTITLE);
                overview=movieInfo.getString(OVER);
                relDate=movieInfo.getString(RELDATE);

                postURL= Uri.parse(POSTER_BASE_URL).buildUpon().
                        appendEncodedPath(movieInfo.getString(POSTERPATH)).build().toString();
                //Log.d("MainActivity", "Value: " + postURL);
                popularity=movieInfo.getString(POPULARITY);
                votAvg=movieInfo.getString(VOTAVG);

                resultStrs.add(new MovieInfo(orgTitle,postURL));
            }
            return resultStrs;

        }

        @Override
        protected ArrayList<MovieInfo> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJsonStr = null;

            try {

                final String MOVIE_BASE_URL =
                        "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM = "sort_by";
                final String APPID_PARAM = "api_key";
                final String PAGE_PARAM = "page";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, params[0])
                        .appendQueryParameter(APPID_PARAM, "ebe982a0f82f328dafb62d76595c40d0")
                        .appendQueryParameter(PAGE_PARAM,params[1])
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();
                Log.v(LOG_TAG,movieJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieJsonStr);
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieInfo> result) {
            if (result != null) {

                for (int i = 0; i < result.size(); i++)
                    arrayList.add(result.get(i));
            }
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

        }
    }
}

