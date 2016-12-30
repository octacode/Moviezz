package octacode.allblue.code.moviezz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class DetailFragment extends Fragment {

    private String LOG_TAG=DetailFragment.this.getClass().getSimpleName();
    Activity mActivity=getActivity();
    public DetailFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
