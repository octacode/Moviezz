package octacode.allblue.code.moviezz.fetchers;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;

import octacode.allblue.code.moviezz.SearchActivity;
import octacode.allblue.code.moviezz.SearchInfo;
import octacode.allblue.code.moviezz.Utility;

/**
 * Created by shasha on 28/1/17.
 */

public class FetchSearch extends AsyncTask<String,Void,ArrayList<SearchInfo>> {
    private final String LOG_TAG=getClass().getSimpleName();
    private Context mContext;
    public FetchSearch(Context context){
        mContext=context;
    }

    @Override
    protected ArrayList<SearchInfo> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;
        ArrayList<SearchInfo> searchInfoArrayList = new ArrayList<>();

        try {
            String request_string = "https://api.themoviedb.org/3/search/movie?api_key=ebe982a0f82f328dafb62d76595c40d0&query=" + params[0];
            URL url = new URL(request_string);

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
            Log.d(LOG_TAG,movieJsonStr);

            try{
                final String OWM_ID = "id";
                final String OWM_ORGLANG = "original_language";
                final String OWM_ORGTITLE = "title";
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
                final String OWM_GENRE_IDS="genre_ids";

                JSONObject movieJson = new JSONObject(movieJsonStr);
                JSONArray movieArray = movieJson.getJSONArray(OWM_RESULT);
                for(int i=0;i<movieArray.length();i++) {
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

                    genre_ids = movieInfo.getString(OWM_GENRE_IDS);
                    id = movieInfo.getString(OWM_ID);
                    orgLang = movieInfo.getString(OWM_ORGLANG);
                    orgTitle = movieInfo.getString(OWM_ORGTITLE);
                    overview = movieInfo.getString(OWM_OVER);
                    relDate = movieInfo.getString(OWM_RELDATE);
                    adult = movieInfo.getString(OWM_ADULT);

                    postURL = Uri.parse(POSTER_BASE_URL).buildUpon().
                            appendEncodedPath(movieInfo.getString(OWM_POSTERPATH)).build().toString();
                    backdropURl = Uri.parse(BACKDROP_BASE_URL).buildUpon().
                            appendEncodedPath(movieInfo.getString(OWM_BACKDROPPATH)).build().toString();

                    popularity = movieInfo.getString(OWM_POPULARITY);
                    votAvg = movieInfo.getString(OWM_VOTAVG);
                    SearchInfo searchInfo = new SearchInfo(id,orgLang,orgTitle,overview,relDate,postURL,adult,popularity,votAvg,backdropURl,genre_ids);
                    searchInfoArrayList.add(searchInfo);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            }
        catch (IOException e){
            e.printStackTrace();
        }
        return searchInfoArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<SearchInfo> searchInfos) {
        super.onPostExecute(searchInfos);
        if(searchInfos.size()==0){
            //Do Something
        }
        else{
            mContext.startActivity(new Intent(mContext, SearchActivity.class).putExtra("overview",searchInfos.get(0).getOverview()));
        }
    }
}
