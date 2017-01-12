package octacode.allblue.code.moviezz;

import android.app.TabActivity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MovieDbHelper(this).getWritableDatabase().delete(MovieContract.ReviewTable.TABLE_NAME,null,null);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main,new MainFragment()).commit();
        }
}
