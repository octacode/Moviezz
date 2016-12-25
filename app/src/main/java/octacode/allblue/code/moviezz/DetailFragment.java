package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailFragment extends Fragment {

    private String LOG_TAG=DetailFragment.this.getClass().getSimpleName();
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

        View rootview=inflater.inflate(R.layout.fragment_detail, container, false);

        String id=getActivity().getIntent().getExtras().getString("ID");
        String title=getActivity().getIntent().getExtras().getString("TITLE");
        String lang=getActivity().getIntent().getExtras().getString("LANG");
        String overview=getActivity().getIntent().getExtras().getString("OVERVIEW");
        String front_url=getActivity().getIntent().getExtras().getString("FRONT_URL");
        String rel_date=getActivity().getIntent().getExtras().getString("REL_DATE");
        String popularity=getActivity().getIntent().getExtras().getString("POPULARITY");
        String vote_avg=getActivity().getIntent().getExtras().getString("VOT_AVG");

        Log.d(LOG_TAG,id);
        Log.d(LOG_TAG,title);
        Log.d(LOG_TAG,lang);
        Log.d(LOG_TAG,overview);
        Log.d(LOG_TAG,front_url);
        Log.d(LOG_TAG,rel_date);
        Log.d(LOG_TAG,popularity);
        Log.d(LOG_TAG,vote_avg);

        return rootview;
    }

}
