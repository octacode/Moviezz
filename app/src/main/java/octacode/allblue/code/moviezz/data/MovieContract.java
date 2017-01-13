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

    public static final class ReviewTable implements BaseColumns{
        public static final String TABLE_NAME  = "ReviewTable";
        static final String _ID = "_id";

        public static final String COLUMN_MOVIE_ID_DOUBLE = "movie_id";
        public static final String COLUMN_TOTAL_RESULTS_INT = "total_results";
        public static final String COLUMN_TRAILER_IDS_STRING = "review_id";
        public static final String COLUMN_MOVIE_AUTHOR = "author";
        public static final String COLUMN_MOVIE_CONTENT = "content";
        public static final String COLUMN_MOVIE_URL = "trailer_url";
    }

    public static final class CastTable implements BaseColumns{
        public static final String TABLE_NAME = "cast_table";
        static final String _ID = "_id";

        public static final String COLUMN_CREDIT_ID = "credit_id";
        public static final String COLUMN_CHARACTER_PLAYED = "character";
        public static final String COLUMN_NAME = "cast_name";
        public static final String COLUMN_PROFILE_URL = "profile_url";
        public static final String COLUMN_MOVIE_ID = "movie_id";
    }

    public static final class CrewTable implements BaseColumns{
        public static final String TABLE_NAME = "crew_table";
        static final String _ID = "_id";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_CREDIT_ID = "credit_id";
        public static final String COLUMN_ROLE = "role";
        public static final String COLUMN_NAME = "crew_name";
        public static final String COLUMN_PROFILE_URL = "profile_url";
    }

    public static final class TrailerTable implements BaseColumns{
        public static final String TABLE_NAME = "trailer_table";
        static final String _ID = "_id";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_POSTER_URL = "poster_url";
    }

    public static final class DetailTable implements BaseColumns{
        public static final String TABLE_NAME = "detail_table";
        static final String _ID = "_id";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_BUDGET = "movie_budget";
        public static final String COLUMN_REVENUE = "movie_revenue";
        public static final String COLUMN_ADULT = "movie_adult";
        public static final String COLUMN_RUNTIME = "movie_runtime";
        public static final String COLUMN_HOMEPAGE = "movie_homepage";
    }
}
