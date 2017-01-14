package octacode.allblue.code.moviezz.fetchers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import octacode.allblue.code.moviezz.ReviewFragment;
import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

/**
 * Created by shasha on 12/1/17.
 */

public class FetchReviewTask extends AsyncTask<String,Void,Void> {

    private final String LOG_TAG=FetchReviewTask.class.getSimpleName();
    private Context mContext;
    public FetchReviewTask(Context context){
        mContext=context;
    }
    private String movie_id;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ReviewFragment.tv_nothing.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String reviewJsonStr = null;

        try {
            String query_string = "https://api.themoviedb.org/3/movie/" + params[0] + "/reviews?api_key=ebe982a0f82f328dafb62d76595c40d0&language=en-US&page=1";

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
            reviewJsonStr = buffer.toString();
//            Log.d(LOG_TAG,reviewJsonStr);

            try{
                JSONObject trailersJson = new JSONObject(reviewJsonStr);
                double movie_id = trailersJson.getDouble("id");
                this.movie_id = String.valueOf(movie_id);
                JSONArray results = trailersJson.getJSONArray("results");
                    JSONObject trailer = results.getJSONObject(0);
                    String trailer_id = trailer.getString("id");
                    String author  = trailer.getString("author");
                    String content = trailer.getString("content");
                    String review_url = trailer.getString("url");
                SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getWritableDatabase();
                String query_check = "Select * from "+ MovieContract.ReviewTable.TABLE_NAME+" where "+ MovieContract.ReviewTable.COLUMN_MOVIE_ID_DOUBLE+ " = "+movie_id;
                Cursor cursor = liteDatabase.rawQuery(query_check,null);
                if(cursor.getCount()<=0){
                    ContentValues cv = new ContentValues();
                    cv.put(MovieContract.ReviewTable.COLUMN_MOVIE_ID_DOUBLE,movie_id);
                    cv.put(MovieContract.ReviewTable.COLUMN_MOVIE_AUTHOR,author);
                    cv.put(MovieContract.ReviewTable.COLUMN_MOVIE_CONTENT,content);
                    cv.put(MovieContract.ReviewTable.COLUMN_MOVIE_URL,review_url);
                    cv.put(MovieContract.ReviewTable.COLUMN_TOTAL_RESULTS_INT,results.length());
                    liteDatabase.insert(MovieContract.ReviewTable.TABLE_NAME,null,cv);
    //                Log.d(LOG_TAG,"** Inserted **");
                }
  //              Log.d(LOG_TAG,content);
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
        String query_check = "Select * from "+ MovieContract.ReviewTable.TABLE_NAME+" where "+ MovieContract.ReviewTable.COLUMN_MOVIE_ID_DOUBLE+ " = "+movie_id;
        Cursor cursor = liteDatabase.rawQuery(query_check,null);
        if(cursor.getCount()>=0)ReviewFragment.tv_nothing.setVisibility(View.INVISIBLE);
        while(cursor.moveToNext()) {
            ReviewFragment.tv_author.setText(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewTable.COLUMN_MOVIE_AUTHOR)));
            ReviewFragment.tv_review.setText(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewTable.COLUMN_MOVIE_CONTENT)));
            ReviewFragment.tv_review_url.setText(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewTable.COLUMN_MOVIE_URL)));
        }
    }
}
