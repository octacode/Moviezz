package octacode.allblue.code.moviezz;

import android.app.TabActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

public class MainActivity extends AppCompatActivity{
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }


        new MovieDbHelper(this).getWritableDatabase().delete(MovieContract.ReviewTable.TABLE_NAME,null,null);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main,new MainFragment()).commit();
        }

    @Override
    public void onBackPressed() {
        ++k;
        if(k==1){
            Toast.makeText(this, "Press back one more time to exit.", Toast.LENGTH_SHORT).show();
        }else{
            moveTaskToBack(true);
        }
    }
}
