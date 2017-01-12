package octacode.allblue.code.moviezz.fetchers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shasha on 12/1/17.
 */

public class FetchSimilarMovies extends AsyncTask<String,Void,Void> {
        private String LOG_TAG = getClass().getSimpleName();

        @Override
        protected Void doInBackground (String...params){
            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String jsonStr = null;

            try {

                String query_string = "https://api.themoviedb.org/3/movie/" + params[0] + "/similar?api_key=ebe982a0f82f328dafb62d76595c40d0&language=en-US&page=1";
                URL url = new URL(query_string);

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
                jsonStr = buffer.toString();
                Log.d(LOG_TAG, jsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
/*
* movie_id = getActivity().getIntent().getStringExtra("MOVIE_ID");
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
        genre_ids = getActivity().getIntent().getStringExtra("GENRE_IDS");
                      */