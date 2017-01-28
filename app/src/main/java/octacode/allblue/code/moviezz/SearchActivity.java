package octacode.allblue.code.moviezz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        Toast.makeText(this,getIntent().getStringExtra("overview"),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}
