package octacode.allblue.code.moviezz.data;

import android.provider.BaseColumns;

/**
 * Created by shasha on 4/1/17.
 */

public class MovieContract {

    public static final class MainMovieTable implements BaseColumns{
        public static final String TABLE_NAME = "MainMovie";
        public static final String _ID = "id";

        public static final String COLUMN_MAIN_PAGE = "page";
        public static final String COLUMN_MAIN_POSTERPATH = "poster_path";
        public static final String COLUMN_MAIN_ADULT = "adult";
        public static final String COLUMN_MAIN_OVERVIEW = "overview";
        public static final String COLUMN_MAIN_RELEASE_DATE = "release_date";
        public static final String COLUMN_MAIN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_MAIN_MOVIE_ID = "id";
        public static final String COLUMN_MAIN_TITLE = "title";
        public static final String COLUMN_MAIN_ORG_LANGUAGE = "original_lainguage";
        public static final String COLUMN_MAIN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_MAIN_POPULARITY = "popularity";
        public static final String COLUMN_MAIN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_MAIN_VOTE_AVERAGE = "vote_average";
    }
}
