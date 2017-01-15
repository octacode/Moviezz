package octacode.allblue.code.moviezz.fetchers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import octacode.allblue.code.moviezz.DetailFragment2;
import octacode.allblue.code.moviezz.Utility;
import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

/**
 * Created by shasha on 12/1/17.
 */

public class FetchDetails extends AsyncTask<String,Void,Void> {

    private Context mContext;
    private String language,movie_id;
    public FetchDetails(Context mContext){this.mContext=mContext;}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        DetailFragment2.loadingPanel2.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        language = params[1];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonStr = null;

        try {

            String query_string = "https://api.themoviedb.org/3/movie/" + params[0] + "?api_key=ebe982a0f82f328dafb62d76595c40d0&language=en-US";
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
            movie_id=params[0];
            try{
                JSONObject jsonObject = new JSONObject(jsonStr);
                String budget = jsonObject.getString("budget");
                String revenue = jsonObject.getString("revenue");
                String adult = jsonObject.getString("adult");
                String runtime = jsonObject.getString("runtime");
                String homepage = jsonObject.getString("homepage");
                SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getWritableDatabase();
                String query_check = "Select * from "+ MovieContract.DetailTable.TABLE_NAME+" where "+ MovieContract.DetailTable.COLUMN_MOVIE_ID+ " = "+params[0];
                Cursor cursor = liteDatabase.rawQuery(query_check,null);
                if(cursor.getCount()<=0) {
                    ContentValues cv = new ContentValues();
                    cv.put(MovieContract.DetailTable.COLUMN_ADULT,adult);
                    cv.put(MovieContract.DetailTable.COLUMN_BUDGET,budget);
                    cv.put(MovieContract.DetailTable.COLUMN_HOMEPAGE,homepage);
                    cv.put(MovieContract.DetailTable.COLUMN_MOVIE_ID,params[0]);
                    cv.put(MovieContract.DetailTable.COLUMN_REVENUE,revenue);
                    cv.put(MovieContract.DetailTable.COLUMN_RUNTIME,runtime);
                    liteDatabase.insert(MovieContract.DetailTable.TABLE_NAME,null,cv);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getReadableDatabase();
        String query_check = "Select * from "+ MovieContract.DetailTable.TABLE_NAME+" where "+ MovieContract.DetailTable.COLUMN_MOVIE_ID+ " = "+movie_id;
        Cursor cursor = liteDatabase.rawQuery(query_check,null);
        if(cursor.moveToFirst()){
            String revenue = cursor.getString(cursor.getColumnIndex(MovieContract.DetailTable.COLUMN_REVENUE));
            revenue = Utility.getBudget(revenue);
            String budget = cursor.getString(cursor.getColumnIndex(MovieContract.DetailTable.COLUMN_BUDGET));
            budget = Utility.getBudget(budget);

            DetailFragment2.revenue_tv.setText(revenue);
            DetailFragment2.homepage_tv.setText(cursor.getString(cursor.getColumnIndex(MovieContract.DetailTable.COLUMN_HOMEPAGE)));
            DetailFragment2.budget_tv.setText(budget);
            DetailFragment2.original_language_tv.setText(Utility.getLang(language));
            DetailFragment2.runtime_tv.setText(Utility.getRuntime(cursor.getString(cursor.getColumnIndex(MovieContract.DetailTable.COLUMN_RUNTIME))));
        }
        DetailFragment2.loadingPanel2.setVisibility(View.GONE);
        DetailFragment2.linearLayout.setVisibility(View.VISIBLE);
    }
}
