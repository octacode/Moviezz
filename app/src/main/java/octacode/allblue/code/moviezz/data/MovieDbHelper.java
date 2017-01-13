package octacode.allblue.code.moviezz.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import octacode.allblue.code.moviezz.data.MovieContract.CastTable;
import octacode.allblue.code.moviezz.data.MovieContract.CrewTable;
import octacode.allblue.code.moviezz.data.MovieContract.DetailTable;
import octacode.allblue.code.moviezz.data.MovieContract.FavouritesTable;
import octacode.allblue.code.moviezz.data.MovieContract.MainMovieTable;
import octacode.allblue.code.moviezz.data.MovieContract.ReviewTable;
import octacode.allblue.code.moviezz.data.MovieContract.SimilarTable;
import octacode.allblue.code.moviezz.data.MovieContract.TrailerTable;

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

        final String SQL_CREATE_TABLE_FAV_MOVIE = "CREATE TABLE "+ FavouritesTable.TABLE_NAME+" ("
                +FavouritesTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "

                +FavouritesTable.COLUMN_MAIN_ADULT_TEXT+ " TEXT NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_BACKDROP_PATH_TEXT+ " TEXT NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_GENRE_IDS_TEXT+ " TEXT NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+ " REAL NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_ORG_LANGUAGE_TEXT+ " TEXT NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_PAGE_INT+ " INT NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_OVERVIEW_TEXT+ " TEXT NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_POPULARITY_DOUBLE+ " REAL NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_POSTER_PATH_TEXT+" TEXT NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_TITLE_TEXT+" TEXT NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_RATINGS_DOUBLE+" REAL NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE+" REAL NOT NULL, "
                +FavouritesTable.COLUMN_MAIN_VOTE_AVERAGE_DOUBLE+" REAL NOT NULL, "
                +"UNIQUE (" +FavouritesTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+") ON CONFLICT REPLACE)";

        final String SQL_CREATE_TABLE_REVIEW = "CREATE TABLE "+ ReviewTable.TABLE_NAME+" ("
                +ReviewTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "

                +ReviewTable.COLUMN_MOVIE_ID_DOUBLE+" REAL NOT NULL, "
                +ReviewTable.COLUMN_MOVIE_AUTHOR+" TEXT NOT NULL, "
                +ReviewTable.COLUMN_MOVIE_CONTENT+" TEXT NOT NULL, "
                +ReviewTable.COLUMN_MOVIE_URL+" TEXT NOT NULL, "
                +ReviewTable.COLUMN_TOTAL_RESULTS_INT+" INT NOT NULL, "
                +"UNIQUE (" + ReviewTable.COLUMN_MOVIE_ID_DOUBLE+") ON CONFLICT REPLACE)";

        final String SQL_CREATE_TABLE_CAST = "CREATE TABLE "+ CastTable.TABLE_NAME+" ("
                + CastTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "

                + CastTable.COLUMN_MOVIE_ID+" REAL NOT NULL, "
                + CastTable.COLUMN_CHARACTER_PLAYED+" TEXT NOT NULL, "
                + CastTable.COLUMN_NAME+" TEXT NOT NULL, "
                + CastTable.COLUMN_CREDIT_ID+" TEXT NOT NULL, "
                + CastTable.COLUMN_PROFILE_URL+" TEXT NOT NULL, "
                +"UNIQUE (" + CastTable.COLUMN_MOVIE_ID+") ON CONFLICT REPLACE)";

        final String SQL_CREATE_TABLE_CREW = "CREATE TABLE "+ CrewTable.TABLE_NAME+" ("
                + CrewTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "

                + CrewTable.COLUMN_MOVIE_ID+" REAL NOT NULL, "
                + CrewTable.COLUMN_ROLE+" TEXT NOT NULL, "
                + CrewTable.COLUMN_NAME+" TEXT NOT NULL, "
                + CrewTable.COLUMN_CREDIT_ID+" TEXT NOT NULL, "
                + CrewTable.COLUMN_PROFILE_URL+" TEXT NOT NULL, "
                +"UNIQUE (" + CrewTable.COLUMN_MOVIE_ID+") ON CONFLICT REPLACE)";

        final String SQL_CREATE_TABLE_TRAILERS = "CREATE TABLE "+ TrailerTable.TABLE_NAME+" ("
                +TrailerTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "

                +TrailerTable.COLUMN_MOVIE_ID+" REAL NOT NULL, "
                +TrailerTable.COLUMN_NAME+" TEXT NOT NULL, "
                +TrailerTable.COLUMN_URL+" TEXT NOT NULL, "
                +TrailerTable.COLUMN_POSTER_URL+" TEXT NOT NULL, "
                +"UNIQUE (" + TrailerTable.COLUMN_MOVIE_ID+") ON CONFLICT REPLACE)";

        final String SQL_CREATE_TABLE_DETAIL_TABLE = "CREATE TABLE "+ DetailTable.TABLE_NAME+" ("
                +DetailTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "

                +DetailTable.COLUMN_MOVIE_ID+" REAL NOT NULL, "
                +DetailTable.COLUMN_ADULT+" TEXT NOT NULL, "
                +DetailTable.COLUMN_BUDGET+" REAL NOT NULL, "
                +DetailTable.COLUMN_HOMEPAGE+" TEXT NOT NULL, "
                +DetailTable.COLUMN_REVENUE+" REAL NOT NULL, "
                +DetailTable.COLUMN_RUNTIME+" REAL NOT NULL, "
                +"UNIQUE (" + DetailTable.COLUMN_MOVIE_ID+") ON CONFLICT REPLACE)";

        final String SQL_CREATE_TABLE_SIMILAR_MOVIE = "CREATE TABLE "+ SimilarTable.TABLE_NAME+" ("
                +MainMovieTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "

                +SimilarTable.COLUMN_MAIN_ADULT_TEXT+ " TEXT NOT NULL, "
                +SimilarTable.COLUMN_MAIN_BACKDROP_PATH_TEXT+ " TEXT NOT NULL, "
                +SimilarTable.COLUMN_MAIN_GENRE_IDS_TEXT+ " TEXT NOT NULL, "
                +SimilarTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+ " REAL NOT NULL, "
                +SimilarTable.COLUMN_MAIN_ORG_LANGUAGE_TEXT+ " TEXT NOT NULL, "
                +SimilarTable.COLUMN_MAIN_PAGE_INT+ " INT NOT NULL, "
                +SimilarTable.COLUMN_MAIN_OVERVIEW_TEXT+ " TEXT NOT NULL, "
                +SimilarTable.COLUMN_MAIN_POPULARITY_DOUBLE+ " REAL NOT NULL, "
                +SimilarTable.COLUMN_MAIN_POSTER_PATH_TEXT+" TEXT NOT NULL, "
                +SimilarTable.COLUMN_MAIN_TITLE_TEXT+" TEXT NOT NULL, "
                +SimilarTable.COLUMN_MAIN_RATINGS_DOUBLE+" REAL NOT NULL, "
                +SimilarTable.COLUMN_MAIN_VOTE_COUNT_DOUBLE+" REAL NOT NULL, "
                +SimilarTable.COLUMN_MAIN_VOTE_AVERAGE_DOUBLE+" REAL NOT NULL, "
                +"UNIQUE (" + SimilarTable.COLUMN_MAIN_MOVIE_ID_DOUBLE+") ON CONFLICT REPLACE)";

        db.execSQL(SQL_CREATE_TABLE_MAIN_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_FAV_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_REVIEW);
        db.execSQL(SQL_CREATE_TABLE_CAST);
        db.execSQL(SQL_CREATE_TABLE_CREW);
        db.execSQL(SQL_CREATE_TABLE_TRAILERS);
        db.execSQL(SQL_CREATE_TABLE_DETAIL_TABLE);
        db.execSQL(SQL_CREATE_TABLE_SIMILAR_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MainMovieTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FavouritesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ReviewTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CrewTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CastTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TrailerTable.TABLE_NAME );
        db.execSQL("DROP TABLE IF EXISTS "+DetailTable.TABLE_NAME);
        onCreate(db);
    }
}
