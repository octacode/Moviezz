package octacode.allblue.code.moviezz.fetchers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import octacode.allblue.code.moviezz.DetailActivity;
import octacode.allblue.code.moviezz.InfoTransfer;
import octacode.allblue.code.moviezz.adapter.TrailersAdapter;
import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

/**
 * Created by shasha on 12/1/17.
 */

public class FetchTrailers extends AsyncTask<String,Void,Void> {

    private Context mContext;
    private String LOG_TAG = getClass().getSimpleName();
    private String movie_id;

    public FetchTrailers(Context context){
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
            //Log.d(LOG_TAG, jsonStr);

            try{
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray results = jsonObject.getJSONArray("results");
                String db_key="",db_name="";
                movie_id = params[0];
                //movie_id,key,name;
                for(int i=0;i<results.length();i++){
                    JSONObject object = results.getJSONObject(i);
                    db_key=db_key+object.getString("key")+"__SPLITTER__";
                    db_name=db_name+object.getString("name")+"__SPLITTER__";
                }
                SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getWritableDatabase();
                String query_check = "Select * from "+ MovieContract.TrailerTable.TABLE_NAME+" where "+ MovieContract.TrailerTable.COLUMN_MOVIE_ID+ " = "+params[0];
                Cursor cursor = liteDatabase.rawQuery(query_check,null);
                if(cursor.getCount()<=0){
                    ContentValues cv = new ContentValues();
                    //"https://www.youtube.com/watch?v="+key
                    //"http://img.youtube.com/vi/"+key+"/0.jpg"
                    cv.put(MovieContract.TrailerTable.COLUMN_MOVIE_ID,params[0]);
                    cv.put(MovieContract.TrailerTable.COLUMN_NAME,db_name);
                    cv.put(MovieContract.TrailerTable.COLUMN_URL,db_key);
                    cv.put(MovieContract.TrailerTable.COLUMN_POSTER_URL,db_key);
                    liteDatabase.insert(MovieContract.TrailerTable.TABLE_NAME,null,cv);
                //    Log.d(LOG_TAG,"** Trailer Inserted **");
                //    Log.d(LOG_TAG,db_name);
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
        SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getReadableDatabase();
        String query_check = "Select * from "+ MovieContract.TrailerTable.TABLE_NAME+" where "+ MovieContract.TrailerTable.COLUMN_MOVIE_ID+ " = "+movie_id;
        Cursor cursor = liteDatabase.rawQuery(query_check,null);
        String db_name="",db_poster_pic="",db_url="";
        if(cursor.moveToFirst()) {
            db_name = cursor.getString(cursor.getColumnIndex(MovieContract.TrailerTable.COLUMN_NAME));
            db_poster_pic = cursor.getString(cursor.getColumnIndex(MovieContract.TrailerTable.COLUMN_POSTER_URL));
            db_url = cursor.getString(cursor.getColumnIndex(MovieContract.TrailerTable.COLUMN_URL));
        }
        String splits_name[] = db_name.split("__SPLITTER__");
        String splits_poster_pic[] = db_poster_pic.split("__SPLITTER__");
        String splits_url[] = db_url.split("__SPLITTER__");
        ArrayList<InfoTransfer> list = new ArrayList<>();

        for(int i=0;i<splits_name.length-1;i++){
            InfoTransfer infoTransfer = new InfoTransfer(splits_name[i],splits_poster_pic[i],splits_url[i]);
            list.add(infoTransfer);
        }

        TrailersAdapter trailerAdapter = new TrailersAdapter(mContext,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        DetailActivity.mRecyclerView_trailers.setLayoutManager(layoutManager);
        DetailActivity.mRecyclerView_trailers.setAdapter(trailerAdapter);
        trailerAdapter.notifyDataSetChanged();

    }
}
