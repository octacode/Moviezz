package octacode.allblue.code.moviezz.fetchers;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import octacode.allblue.code.moviezz.data.MovieContract.MainMovieTable;

/**
 * Created by shasha on 6/1/17.
 */

public class FetchMovieTask extends AsyncTask<String,Void,Void> {

    private final String LOG_TAG=FetchMovieTask.class.getSimpleName();
    private Context mContext;
    public FetchMovieTask(Context context){
        mContext=context;
    }

    @Override
    protected Void doInBackground(String... params) {
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
                    .appendQueryParameter(PAGE_PARAM,"1")
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
            try {
                final String OWM_ID = "id";
                final String OWM_ORGLANG = "original_language";
                final String OWM_ORGTITLE = "original_title";
                final String OWM_OVER = "overview";
                final String OWM_RELDATE = "release_date";
                final String OWM_POSTERPATH = "poster_path";
                final String OWM_BACKDROPPATH = "backdrop_path";
                final String OWM_POPULARITY = "popularity";
                final String OWM_VOTAVG = "vote_average";
                final String OWM_ADULT = "adult";
                final String OWM_RESULT="results";
                final String POSTER_BASE_URL="http://image.tmdb.org/t/p/w185";
                final String BACKDROP_BASE_URL="http://image.tmdb.org/t/p/w500";
                final String OWM_VOTE_COUNT="vote_count";
                final String OWM_GENRE_IDS="genre_ids";

                JSONObject movieJson = new JSONObject(movieJsonStr);
                JSONArray movieArray = movieJson.getJSONArray(OWM_RESULT);


                Vector<ContentValues> cVVector = new Vector<ContentValues>(movieArray.length());

                for(int i=0;i<movieArray.length();i++){
                    String id;
                    String orgLang;
                    String orgTitle;
                    String overview;
                    String relDate;
                    String postURL;
                    String adult;
                    String popularity;
                    String votAvg;
                    String backdropURl;
                    String genre_ids;

                    JSONObject movieInfo = movieArray.getJSONObject(i);

                    genre_ids=movieInfo.getString(OWM_GENRE_IDS);
                    id=movieInfo.getString(OWM_ID);
                    orgLang=movieInfo.getString(OWM_ORGLANG);
                    orgTitle=movieInfo.getString(OWM_ORGTITLE);
                    overview=movieInfo.getString(OWM_OVER);
                    relDate=movieInfo.getString(OWM_RELDATE);
                    adult=movieInfo.getString(OWM_ADULT);

                    postURL= Uri.parse(POSTER_BASE_URL).buildUpon().
                            appendEncodedPath(movieInfo.getString(OWM_POSTERPATH)).build().toString();
                    backdropURl = Uri.parse(BACKDROP_BASE_URL).buildUpon().
                            appendEncodedPath(movieInfo.getString(OWM_BACKDROPPATH)).build().toString();

                    /*
                    Log.d(LOG_TAG, "Poster Value: " + postURL);
                    Log.d(LOG_TAG, "Backdrop Value: " + backdropURl);
                    */

                    popularity=movieInfo.getString(OWM_POPULARITY);
                    votAvg=movieInfo.getString(OWM_VOTAVG);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MainMovieTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE,genre_ids);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_ADULT_TEXT,adult);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_BACKDROP_PATH_TEXT,backdropURl);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_GENRE_IDS_TEXT,relDate);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_MOVIE_ID_DOUBLE,id);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_ORG_LANGUAGE_TEXT,orgLang);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_OVERVIEW_TEXT,overview);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_PAGE_INT,1);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_POPULARITY_DOUBLE,popularity);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_POSTER_PATH_TEXT,postURL);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_RATINGS_DOUBLE,popularity);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_TITLE_TEXT,orgTitle);
                    contentValues.put(MainMovieTable.COLUMN_MAIN_VOTE_AVERAGE_DOUBLE,votAvg);

                    cVVector.add(contentValues);

                    //Uri insert_uri=mContext.getContentResolver().insert(MainMovieTable.CONTENT_URI,contentValues);
                    /*
                    Log.d(LOG_TAG,"Insertion Successful : "+ ContentUris.parseId(insert_uri));
                    Log.d(LOG_TAG,"Insertion Successful : "+ orgTitle);
                    Log.d(LOG_TAG,"Insertion Successful : "+ overview);
                    Log.d(LOG_TAG,"Insertion Successful : "+ popularity);
                    */
                }

                if(cVVector.size()>0){
                    mContext.getContentResolver().delete(MainMovieTable.CONTENT_URI,null,null);
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                    mContext.getContentResolver().bulkInsert(MainMovieTable.CONTENT_URI,cvArray);
                }
                Log.d(LOG_TAG, "FetchMovie Task Complete: "+ cVVector.size()+ " Inserted");
            }
            catch (JSONException e){
                e.printStackTrace();
            }
                Log.d(LOG_TAG,movieJsonStr);
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
        return null;
    }
}

