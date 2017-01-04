package octacode.allblue.code.moviezz.java;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

/**
 * Created by shasha on 4/1/17.
 */

public class Testdb extends AndroidTestCase {

    public void testCreatedb() throws Throwable {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        SQLiteDatabase liteDatabase = new MovieDbHelper(getContext()).getWritableDatabase();
        assertEquals(true, liteDatabase.isOpen());
        liteDatabase.close();
    }

}