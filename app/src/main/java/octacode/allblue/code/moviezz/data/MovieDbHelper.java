package octacode.allblue.code.moviezz.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import octacode.allblue.code.moviezz.data.MovieContract.MainMovieTable;

/**
 * Created by shasha on 4/1/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MAIN_MOVIE_DATABASE";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE_MAIN_MOVIE = "CREATE TABLE "+ MainMovieTable.TABLE_NAME+" ("
                +MainMovieTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "

                +MainMovieTable.COLUMN_MAIN_ADULT_TEXT+ " TEXT NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_BACKDROP_PATH_TEXT+ " TEXT NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_GENRE_IDS_TEXT+ " TEXT NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+ " REAL NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_ORG_LANGUAGE_TEXT+ " TEXT NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_PAGE_INT+ " INT NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_OVERVIEW_TEXT+ " TEXT NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_POPULARITY_DOUBLE+ " REAL NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_POSTER_PATH_TEXT+" TEXT NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_TITLE_TEXT+" TEXT NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_RATINGS_DOUBLE+" REAL NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE+" REAL NOT NULL, "
                +MainMovieTable.COLUMN_MAIN_VOTE_AVERAGE_DOUBLE+" REAL NOT NULL, "
                +"UNIQUE (" + MainMovieTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+") ON CONFLICT REPLACE)";

        db.execSQL(SQL_CREATE_TABLE_MAIN_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MainMovieTable.TABLE_NAME);
        onCreate(db);
    }
}
