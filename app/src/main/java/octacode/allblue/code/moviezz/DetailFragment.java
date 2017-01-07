package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class DetailFragment extends Fragment{

    View mRootView;
    String movie_id;

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
        mRootView = inflater.inflate(R.layout.fragment_detail, container, false);
        String id = getActivity().getIntent().getStringExtra("ID");
        movie_id = getActivity().getIntent().getStringExtra("MOVIE_ID");
        String adult = getActivity().getIntent().getStringExtra("ADULT");
        String backdrop_url = getActivity().getIntent().getStringExtra("BACKDROP_URL");
        String overview = getActivity().getIntent().getStringExtra("OVERVIEW");
        String title = getActivity().getIntent().getStringExtra("TITLE");
        String poster_url = getActivity().getIntent().getStringExtra("POSTER_URL");
        String language = getActivity().getIntent().getStringExtra("LANGUAGE");
        String popularity = getActivity().getIntent().getStringExtra("POPULARITY");
        String ratings = getActivity().getIntent().getStringExtra("RATINGS");
        String vote_avg = getActivity().getIntent().getStringExtra("VOTE_AVG");
        String rel_date = getActivity().getIntent().getStringExtra("REL_DATE");

        Toast.makeText(getContext(),title+" "+overview+"  "+rel_date+" "+movie_id,Toast.LENGTH_LONG).show();
        return mRootView;
    }

    public class HolderDetail{

        private TextView title,ratings,date,overview,language,runtime,homepage,budget,revenue,adult;
        public HolderDetail(View mRootView){
            title=(TextView)mRootView.findViewById(R.id.title_detail);
            ratings=(TextView)mRootView.findViewById(R.id.detail_ratings);
            date=(TextView)mRootView.findViewById(R.id.date_detail);
            overview=(TextView)mRootView.findViewById(R.id.overview_detail);
            language=(TextView)mRootView.findViewById(R.id.original_language_detail);
            runtime=(TextView)mRootView.findViewById(R.id.runtime_detail);
            homepage=(TextView)mRootView.findViewById(R.id.homepage_detail);
            budget=(TextView)mRootView.findViewById(R.id.budget_detail);
            revenue=(TextView)mRootView.findViewById(R.id.revenue_detail);
            adult=(TextView)mRootView.findViewById(R.id.adult_detail);
        }
    }

    public class FetchDetailTask extends AsyncTask<String,Void,String>{

        public FetchDetailTask() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}