package octacode.allblue.code.moviezz.fetchers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import octacode.allblue.code.moviezz.DetailActivity2;
import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieContract.CastTable;
import octacode.allblue.code.moviezz.data.MovieContract.CrewTable;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

/**
 * Created by shasha on 12/1/17.
 */

public class FetchCrewCast extends AsyncTask<String,Void,Void> {

    private final String LOG_TAG = getClass().getSimpleName();
    private Context mContext;

    FetchCrewCast(Context mContext){this.mContext=mContext;}

    @Override
    protected Void doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonStr = null;

        try{

            String query_string = "https://api.themoviedb.org/3/movie/"+params[0]+"/credits?api_key=ebe982a0f82f328dafb62d76595c40d0";
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
            Log.d(LOG_TAG,jsonStr);

            try{
                JSONObject fetched_crew = new JSONObject(jsonStr);
                double movie_id = fetched_crew.getDouble("id");
                JSONArray array_cast = fetched_crew.getJSONArray("cast");
                JSONArray array_crew = fetched_crew.getJSONArray("crew");

                String db_character="",db_name="",db_credit_id="",db_profile_url="";

                for(int i=0;i<array_cast.length();i++) {
                    JSONObject cast_json = array_cast.getJSONObject(i);
                    String character = cast_json.getString("character");
                    String credit_id = cast_json.getString("credit_id");
                    String name = cast_json.getString("name");
                    String profile_url = "http://image.tmdb.org/t/p/w185" + cast_json.getString("profile_path");

                    db_character=db_character+character+"__SPLITTER__";
                    db_credit_id=db_credit_id+credit_id+"__SPLITTER__";
                    db_name=db_name+name+"__SPLITTER__";
                    db_profile_url=db_profile_url+profile_url+"__SPLITTER__";
                }
                    SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getWritableDatabase();
                    String query_check = "Select * from " + CastTable.TABLE_NAME + " where " + CastTable.COLUMN_MOVIE_ID + " = " + movie_id;
                    Cursor cursor = liteDatabase.rawQuery(query_check, null);
                    Log.d(LOG_TAG,db_profile_url);
                    if(cursor.getCount()<=0) {
                        ContentValues cv = new ContentValues();
                        cv.put(CastTable.COLUMN_MOVIE_ID,movie_id);
                        cv.put(CastTable.COLUMN_NAME,db_name);
                        cv.put(CastTable.COLUMN_CREDIT_ID,db_credit_id);
                        cv.put(CastTable.COLUMN_CHARACTER_PLAYED,db_character);
                        cv.put(CastTable.COLUMN_PROFILE_URL,db_profile_url);
                        liteDatabase.insert(CastTable.TABLE_NAME, null, cv);
                    }

                db_character="";db_name="";db_credit_id="";db_profile_url="";
                for(int i=0;i<array_crew.length();i++) {
                    JSONObject crew_json = array_crew.getJSONObject(i);
                    String job = crew_json.getString("job");
                    String credit_id = crew_json.getString("credit_id");
                    String name = crew_json.getString("name");
                    String path_url = "http://image.tmdb.org/t/p/w185" + crew_json.getString("profile_path");
                    db_character = db_character + job + "__SPLITTER__";
                    db_credit_id = db_credit_id + credit_id + "__SPLITTER__";
                    db_name = db_name + name + "__SPLITTER__";
                    db_profile_url = db_profile_url + path_url + "__SPLITTER__";
                }
                Log.d(LOG_TAG,db_name);
                    liteDatabase = new MovieDbHelper(mContext).getWritableDatabase();
                    query_check = "Select * from "+ CrewTable.TABLE_NAME+" where "+ CrewTable.COLUMN_MOVIE_ID+ " = "+movie_id;
                    cursor = liteDatabase.rawQuery(query_check,null);
                    if(cursor.getCount()<=0){
                        ContentValues cv = new ContentValues();
                        cv.put(CrewTable.COLUMN_MOVIE_ID,movie_id);
                        cv.put(CrewTable.COLUMN_ROLE,db_character);
                        cv.put(CrewTable.COLUMN_NAME,db_name);
                        cv.put(CrewTable.COLUMN_CREDIT_ID,db_credit_id);
                        cv.put(CrewTable.COLUMN_PROFILE_URL,db_profile_url);
                        liteDatabase.insert(CrewTable.TABLE_NAME,null,cv);
                        Log.d(LOG_TAG,"**Inserted Successfully lpoipo**");
                    }
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
        DetailActivity2.topCastAdapter.notifyDataSetChanged();
        DetailActivity2.trailerAdapter.notifyDataSetChanged();
    }
}
