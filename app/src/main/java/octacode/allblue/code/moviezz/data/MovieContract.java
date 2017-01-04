package octacode.allblue.code.moviezz.data;

import android.provider.BaseColumns;

/**
 * Created by shasha on 4/1/17.
 */

public class MovieContract {

    public static final class MainMovieTable implements BaseColumns{
        public static final String TABLE_NAME = "MainMovie";
        public static final String _ID = "id";

        public static final String COLUMN_MAIN_PAGE_INT = "page";
        public static final String COLUMN_MAIN_POSTER_PATH_TEXT = "poster_path";
        public static final String COLUMN_MAIN_ADULT_TEXT = "adult";
        public static final String COLUMN_MAIN_MOVIE_ID_INT = "movie_id";
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
