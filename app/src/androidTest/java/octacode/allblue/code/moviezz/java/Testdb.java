package octacode.allblue.code.moviezz.java;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieContract.MainMovieTable;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

/**
 * Created by shasha on 4/1/17.
 */

public class Testdb extends AndroidTestCase {

    String LOG_TAG = getClass().getSimpleName();
    public void testCreatedb() throws Throwable {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        SQLiteDatabase liteDatabase = new MovieDbHelper(getContext()).getWritableDatabase();
        assertEquals(true, liteDatabase.isOpen());
        liteDatabase.close();
    }

    public void testInsertReaddb(){
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        ContentValues cv=new ContentValues();
        int test_page=1;

        String test_poster_path="/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg",
                test_adult="false",
                test_title="Rogue One: A Star Wars Story",
                test_language="En",
                test_overview="A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy.",
                test_backdrop_path="/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg",
                test_genre_ids="123,432,5,3";

        double test_movie_id=330459,
                test_popularity=160.57498,
                test_vote_count=1350,
                test_vote_average=7.2,
                test_ratings=8.9;

        cv.put(MainMovieTable.COLUMN_MAIN_PAGE_INT,test_page);
        cv.put(MainMovieTable.COLUMN_MAIN_POSTER_PATH_TEXT,test_poster_path);
        cv.put(MainMovieTable.COLUMN_MAIN_ADULT_TEXT,test_adult);
        cv.put(MainMovieTable.COLUMN_MAIN_TITLE_TEXT,test_title);
        cv.put(MainMovieTable.COLUMN_MAIN_ORG_LANGUAGE_TEXT,test_language);
        cv.put(MainMovieTable.COLUMN_MAIN_OVERVIEW_TEXT,test_overview);
        cv.put(MainMovieTable.COLUMN_MAIN_BACKDROP_PATH_TEXT,test_backdrop_path);
        cv.put(MainMovieTable.COLUMN_MAIN_GENRE_IDS_TEXT,test_genre_ids);
        cv.put(MainMovieTable.COLUMN_MAIN_RATINGS_DOUBLE,test_ratings);
        cv.put(MainMovieTable.COLUMN_MAIN_MOVIE_ID_DOUBLE,test_movie_id);
        cv.put(MainMovieTable.COLUMN_MAIN_POPULARITY_DOUBLE,test_popularity);
        cv.put(MainMovieTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE,test_vote_count);
        cv.put(MainMovieTable.COLUMN_MAIN_VOTE_AVERAGE_DOUBLE,test_vote_average);

        SQLiteDatabase liteDatabase = new MovieDbHelper(mContext).getWritableDatabase();
        long movie_row_id=liteDatabase.insert(MovieContract.FavouritesTable.TABLE_NAME,null,cv);
        assertTrue(movie_row_id != -1);
        Log.d(LOG_TAG,"Movie returned is : "+movie_row_id);

        String test_columns[]={
                String.valueOf(movie_row_id),
                MainMovieTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE,
                MainMovieTable.COLUMN_MAIN_MOVIE_ID_DOUBLE,
                MainMovieTable.COLUMN_MAIN_PAGE_INT,
                MainMovieTable.COLUMN_MAIN_POSTER_PATH_TEXT,
                MainMovieTable.COLUMN_MAIN_ADULT_TEXT,
                MainMovieTable.COLUMN_MAIN_TITLE_TEXT,
                MainMovieTable.COLUMN_MAIN_ORG_LANGUAGE_TEXT,
                MainMovieTable.COLUMN_MAIN_OVERVIEW_TEXT,
                MainMovieTable.COLUMN_MAIN_BACKDROP_PATH_TEXT,
                MainMovieTable.COLUMN_MAIN_GENRE_IDS_TEXT,
                MainMovieTable.COLUMN_MAIN_RATINGS_DOUBLE,
                MainMovieTable.COLUMN_MAIN_POPULARITY_DOUBLE,
                MainMovieTable.COLUMN_MAIN_VOTE_AVERAGE_DOUBLE
        };

        Cursor cursor = liteDatabase.query(MovieContract.FavouritesTable.TABLE_NAME,
                test_columns,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()) {
            int movie_index = cursor.getColumnIndex(MainMovieTable.COLUMN_MAIN_MOVIE_ID_DOUBLE);
            Log.d(LOG_TAG, "String is " + cursor.getDouble(movie_index));

            int overview_index = cursor.getColumnIndex(MainMovieTable.COLUMN_MAIN_OVERVIEW_TEXT);
            Log.d(LOG_TAG, cursor.getString(overview_index));
        }
        cursor.close();
    }
}