package octacode.allblue.code.moviezz.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
                retCursor=movieDbHelper.getWritableDatabase().query(
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
                retCursor=movieDbHelper.getWritableDatabase().query(
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
        final int match = matcher.match(uri);
        Uri retUri = null;
        switch(match){
            case MOVIE:
                long _id = movieDbHelper.getWritableDatabase().insert(MovieContract.MainMovieTable.TABLE_NAME,null,values);
                if(_id>0)
                    retUri = MovieContract.MainMovieTable.buildMoviewithId(_id);
                else
                    throw new UnsupportedOperationException("SQL Database insertion failed for uri : "+uri);
                break;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase movieDbHelper = new MovieDbHelper(getContext()).getWritableDatabase();
        final int match = matcher.match(uri);
        int rowsDeleted=-1;
        switch (match){
            case MOVIE:
                rowsDeleted = movieDbHelper.delete(MovieContract.MainMovieTable.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("SQL Database deletion failed for uri : "+uri);
        }
        if(selection == null || rowsDeleted!=0)
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase movieDbHelper = new MovieDbHelper(getContext()).getWritableDatabase();
        final int match = matcher.match(uri);
        int rowsUpdated=-1;
        switch (match){
            case MOVIE:
                rowsUpdated = movieDbHelper.update(MovieContract.MainMovieTable.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("SQL Database updating failed for uri : "+uri);
        }
        if(selection == null || rowsUpdated!=0)
            getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final int match=matcher.match(uri);
        final SQLiteDatabase db=new MovieDbHelper(getContext()).getWritableDatabase();
        switch (match){
            case MOVIE:
                db.beginTransaction();
                int returnCount=0;
                try{
                    for(ContentValues value : values){
                        long _id = db.insert(MovieContract.MainMovieTable.TABLE_NAME,null,value);
                        if(_id!=-1)
                        returnCount++;
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
            return returnCount;

            default :
                return super.bulkInsert(uri, values);
        }
    }
}
