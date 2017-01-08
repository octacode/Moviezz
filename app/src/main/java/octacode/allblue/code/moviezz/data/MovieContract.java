package octacode.allblue.code.moviezz.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by shasha on 4/1/17.
 */

public class MovieContract {

    public static final String PATH_MOVIE_TABLE = "movie";

    public static final String PATH_FAV_TABLE = "favourites";

    public static final String CONTENT_AUTHORITY = "octacode.allblue.code.moviezz";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MainMovieTable implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_TABLE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY +"/"+ PATH_MOVIE_TABLE;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY +"/"+ PATH_MOVIE_TABLE;

        public static Uri buildMoviewithId(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static final String TABLE_NAME = "MainMovie";
        public static final String _ID = "_id";

        public static final String COLUMN_MAIN_PAGE_INT = "page";
        public static final String COLUMN_MAIN_POSTER_PATH_TEXT = "poster_path";
        public static final String COLUMN_MAIN_ADULT_TEXT = "adult";
        public static final String COLUMN_MAIN_OVERVIEW_TEXT= "overview";
        public static final String COLUMN_MAIN_MOVIE_ID_DOUBLE = "movie_id";
        public static final String COLUMN_MAIN_TITLE_TEXT = "title";
        public static final String COLUMN_MAIN_ORG_LANGUAGE_TEXT = "original_lainguage";
        public static final String COLUMN_MAIN_BACKDROP_PATH_TEXT = "backdrop_path";
        public static final String COLUMN_MAIN_GENRE_IDS_TEXT = "genre_ids";
        public static final String COLUMN_MAIN_RATINGS_DOUBLE = "ratings";
        public static final String COLUMN_MAIN_POPULARITY_DOUBLE = "popularity";
        public static final String COLUMN_MAIN_VOTE_COUNT_DOUBLE = "vote_count";
        public static final String COLUMN_MAIN_VOTE_AVERAGE_DOUBLE = "vote_average";
    }

    public static final class FavouritesTable implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV_TABLE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY +"/"+ PATH_FAV_TABLE;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY +"/"+ PATH_FAV_TABLE;

        public static Uri buildMoviewithId(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static final String TABLE_NAME = "FavouriteMovies";
        public static final String _ID = "_id";

        public static final String COLUMN_MAIN_PAGE_INT = "page";
        public static final String COLUMN_MAIN_POSTER_PATH_TEXT = "poster_path";
        public static final String COLUMN_MAIN_ADULT_TEXT = "adult";
        public static final String COLUMN_MAIN_OVERVIEW_TEXT= "overview";
        public static final String COLUMN_MAIN_MOVIE_ID_DOUBLE = "movie_id";
        public static final String COLUMN_MAIN_TITLE_TEXT = "title";
        public static final String COLUMN_MAIN_ORG_LANGUAGE_TEXT = "original_lainguage";
        public static final String COLUMN_MAIN_BACKDROP_PATH_TEXT = "backdrop_path";
        public static final String COLUMN_MAIN_GENRE_IDS_TEXT = "genre_ids";
        public static final String COLUMN_MAIN_RATINGS_DOUBLE = "ratings";
        public static final String COLUMN_MAIN_POPULARITY_DOUBLE = "popularity";
        public static final String COLUMN_MAIN_VOTE_COUNT_DOUBLE = "vote_count";
        public static final String COLUMN_MAIN_VOTE_AVERAGE_DOUBLE = "vote_average";
    }

}
