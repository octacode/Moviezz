package octacode.allblue.code.moviezz.fetchers;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import octacode.allblue.code.moviezz.CastActivity;
import octacode.allblue.code.moviezz.DetailActivity;
import octacode.allblue.code.moviezz.R;
import octacode.allblue.code.moviezz.adapter.TopCastAdapter;
import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieContract.PersonTable;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

import static octacode.allblue.code.moviezz.R.id.cast_image_list_item;

/**
 * Created by shasha on 13/1/17.
 */

public class FetchCrewDetails extends AsyncTask<String,Void,Void> {

    private String LOG_TAG = getClass().getSimpleName();
    private Context mContext;
    private String person_id;
    String biography,birthday,profile_path,name,place_of_birth,gender;
    ProgressDialog progressDialog;

    public FetchCrewDetails(Context context){mContext=context;}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();
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

            String query_string = "https://api.themoviedb.org/3/person/" + params[0] + "?api_key=ebe982a0f82f328dafb62d76595c40d0&language=en-US";
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
                JSONObject jsonObject = new JSONObject(jsonStr);
                String profile_path = jsonObject.getString("profile_path");
                String biography = jsonObject.getString("biography");
                String birthday = jsonObject.getString("birthday");
                String gender = jsonObject.getString("gender");
                String name = jsonObject.getString("name");
                String place_of_birth = jsonObject.getString("place_of_birth");
                String person_id = params[0];
                this.biography = biography;
                this.birthday = birthday;
                this.gender = gender;
                this.name = name;
                this.profile_path = "http://image.tmdb.org/t/p/w185"+profile_path;
                this.place_of_birth = place_of_birth;
                this.person_id = person_id;
                if(gender.matches("2"))
                    gender="Male";
                if(gender.matches("1"))
                    gender="female";
                if(gender.matches("0"))
                    gender="";

                SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getWritableDatabase();
                String query_check = "Select * from "+ PersonTable.TABLE_NAME+" where "+ PersonTable.COLUMN_PERSON_ID+ " = "+params[0];
                Cursor cursor = liteDatabase.rawQuery(query_check,null);
                if(cursor.getCount()<=0){
                    ContentValues cv = new ContentValues();
                    cv.put(PersonTable.COLUMN_NAME,name);
                    cv.put(PersonTable.COLUMN_AKA,this.profile_path);
                    cv.put(PersonTable.COLUMN_BIOGRAPHY,biography);
                    cv.put(PersonTable.COLUMN_DOB,birthday);
                    cv.put(PersonTable.COLUMN_GENDER,gender);
                    cv.put(PersonTable.COLUMN_PERSON_ID,person_id);
                    cv.put(PersonTable.COLUMN_PLACE_OF_BIRTH,place_of_birth);
                    cv.put(PersonTable.COLUMN_PERSON_ID,params[0]);
                    liteDatabase.insert(PersonTable.TABLE_NAME,null,cv);
                    Log.d(LOG_TAG,"** Inserted Successfully **"+name);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            Log.d(LOG_TAG,"**Well, This is not an I/O Exception.**");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getReadableDatabase();
        String query_check = "Select * from "+ MovieContract.PersonTable.TABLE_NAME+" where "+ PersonTable.COLUMN_PERSON_ID+ " = "+person_id;
        Cursor cursor = liteDatabase.rawQuery(query_check,null);
        if(cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_NAME));
            biography = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_BIOGRAPHY));
            birthday = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_DOB));
            place_of_birth = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_PLACE_OF_BIRTH));
            gender = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_GENDER));
            profile_path = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_AKA));
        }

        if(name.matches("null")){name = "";}
        if(biography.matches("null")){biography = "";}
        if(birthday.matches("null")){birthday = "";}
        if(place_of_birth.matches("null")){place_of_birth = "";}
        if(gender.matches("null")){gender = "";}
        if(profile_path.matches("null")){profile_path = "";}

        Intent intent = new Intent(mContext,CastActivity.class);
        intent.putExtra("NAME",name);
        intent.putExtra("BIOGRAPHY",biography);
        intent.putExtra("DOB",birthday);
        intent.putExtra("PLACE_OF_BIRTH",place_of_birth);
        intent.putExtra("GENDER",gender);
        intent.putExtra("PROFILE_PATH",profile_path);
        mContext.startActivity(intent);
    }
}
