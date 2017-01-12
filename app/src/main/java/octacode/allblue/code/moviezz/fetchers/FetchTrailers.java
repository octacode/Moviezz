package octacode.allblue.code.moviezz.fetchers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

/**
 * Created by shasha on 12/1/17.
 */

public class FetchTrailers extends AsyncTask<String,Void,Void> {

    private Context mContext;
    private String LOG_TAG = getClass().getSimpleName();

    FetchTrailers(Context context){
        mContext=context;
    }
    @Override
    protected Void doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonStr = null;

        try {

            String query_string = "https://api.themoviedb.org/3/movie/" + params[0] + "/videos?api_key=ebe982a0f82f328dafb62d76595c40d0&language=en-US";
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

            try{
                JSONObject jsonObject = new JSONObject(jsonStr);
                String key = jsonObject.getString("key");
                String name = jsonObject.getString("name");
                //movie_id,key,name;
                SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getWritableDatabase();
                String query_check = "Select * from "+ MovieContract.TrailerTable.TABLE_NAME+" where "+ MovieContract.TrailerTable.COLUMN_MOVIE_ID+ " = "+params[0];
                Cursor cursor = liteDatabase.rawQuery(query_check,null);
                if(cursor.getCount()<=0){
                    ContentValues cv = new ContentValues();
                    cv.put(MovieContract.TrailerTable.COLUMN_MOVIE_ID,params[0]);
                    cv.put(MovieContract.TrailerTable.COLUMN_NAME,name);
                    cv.put(MovieContract.TrailerTable.COLUMN_URL,"https://www.youtube.com/watch?v="+key);
                    cv.put(MovieContract.TrailerTable.COLUMN_POSTER_URL,"http://img.youtube.com/vi/"+key+"/0.jpg");
                    liteDatabase.insert(MovieContract.TrailerTable.TABLE_NAME,null,cv);
                    Log.d(LOG_TAG,"** Trailer Inserted **");
                    Log.d(LOG_TAG,name);
                }
                cursor.close();
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
