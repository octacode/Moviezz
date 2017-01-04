package octacode.allblue.code.moviezz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DetailFragment extends Fragment {

    View mrootview;

    private String LOG_TAG=DetailFragment.this.getClass().getSimpleName();
    public DetailFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(getContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mrootview = inflater.inflate(R.layout.fragment_detail, container, false);
        Activity mActivity=getActivity();
        String id=mActivity.getIntent().getStringExtra("ID");
        String vote_avg=mActivity.getIntent().getStringExtra("VOTE_AVG");
        String backdrop_url=mActivity.getIntent().getStringExtra("BACK_URL");
        String org_lang=mActivity.getIntent().getStringExtra("LANG");
        String org_title=mActivity.getIntent().getStringExtra("TITLE");
        String overview=mActivity.getIntent().getStringExtra("OVERVIEW");
        String popularity=mActivity.getIntent().getStringExtra("POPULARITY");
        String rel_date=mActivity.getIntent().getStringExtra("REL_DATE");

        Log.d(LOG_TAG,id);
        Log.d(LOG_TAG,vote_avg);
        Log.d(LOG_TAG,backdrop_url);
        Log.d(LOG_TAG,org_lang);
        Log.d(LOG_TAG,org_title);
        Log.d(LOG_TAG,overview);
        Log.d(LOG_TAG,popularity);
        Log.d(LOG_TAG,rel_date);
        return mrootview;
    }
}

class HolderDetail{

    private TextView title,ratings,date,overview,language,runtime,homepage,budget,revenue,adult;
    public HolderDetail(View mrootview){
        title=(TextView)mrootview.findViewById(R.id.title_detail);
        ratings=(TextView)mrootview.findViewById(R.id.detail_ratings);
        date=(TextView)mrootview.findViewById(R.id.date_detail);
        overview=(TextView)mrootview.findViewById(R.id.overview_detail);
        language=(TextView)mrootview.findViewById(R.id.original_language_detail);
        runtime=(TextView)mrootview.findViewById(R.id.runtime_detail);
        homepage=(TextView)mrootview.findViewById(R.id.homepage_detail);
        budget=(TextView)mrootview.findViewById(R.id.budget_detail);
        revenue=(TextView)mrootview.findViewById(R.id.revenue_detail);
        adult=(TextView)mrootview.findViewById(R.id.adult_detail);
    }
}
