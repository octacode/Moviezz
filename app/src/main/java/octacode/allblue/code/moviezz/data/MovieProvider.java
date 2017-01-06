package octacode.allblue.code.moviezz.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by shasha on 4/1/17.
 */

public class MovieProvider extends ContentProvider {

    public static final int MOVIE=100;
    public static final int MOVIE_WITH_ID=101;

    private MovieDbHelper movieDbHelper;
    private static UriMatcher matcher=buildUriMatcher();
    private static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        String authority=MovieContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE_TABLE,MOVIE);
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE_TABLE + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper=new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (matcher.match(uri)){
            //movie
            case MOVIE:
                retCursor=movieDbHelper.getReadableDatabase().query(
                        MovieContract.MainMovieTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            //movie/*
            case MOVIE_WITH_ID:
                retCursor=movieDbHelper.getReadableDatabase().query(
                        MovieContract.MainMovieTable.TABLE_NAME,
                        projection,
                        MovieContract.MainMovieTable._ID + " = '" + ContentUris.parseId(uri) + "'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default :
                throw new UnsupportedOperationException("Unknown uri : "+uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match=matcher.match(uri);
        switch (match){
            case MOVIE:
                return MovieContract.MainMovieTable.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return MovieContract.MainMovieTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri : "+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
