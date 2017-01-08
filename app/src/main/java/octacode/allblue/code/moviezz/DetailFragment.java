package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DetailFragment extends Fragment {

    View mRootView;
    String movie_id;

    private String LOG_TAG = DetailFragment.this.getClass().getSimpleName();

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
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_detail, container, false);
        HolderDetail viewHolder = new HolderDetail(mRootView);
        movie_id = getActivity().getIntent().getStringExtra("MOVIE_ID");
        String adult = getActivity().getIntent().getStringExtra("ADULT");
        String backdrop_url = getActivity().getIntent().getStringExtra("BACKDROP_URL");
        String overview = getActivity().getIntent().getStringExtra("OVERVIEW");
        String title = getActivity().getIntent().getStringExtra("TITLE");
        String poster_url = getActivity().getIntent().getStringExtra("POSTER_URL");
        String language = getActivity().getIntent().getStringExtra("LANGUAGE");
        double popularity = Double.parseDouble(getActivity().getIntent().getStringExtra("POPULARITY"));
        String ratings = getActivity().getIntent().getStringExtra("RATINGS");
        double vote_avg = Double.parseDouble(getActivity().getIntent().getStringExtra("VOTE_AVG"));
        String rel_date = getActivity().getIntent().getStringExtra("REL_DATE");
        viewHolder.title.setText(title);
        viewHolder.date.setText(rel_date);
        viewHolder.overview.setText(overview);
        viewHolder.ratings.setText(String.valueOf((double) Math.round(vote_avg * 10d) / 10d));
        return mRootView;
    }

    public class HolderDetail {
        private TextView title, ratings, date, overview, language, runtime, homepage, budget, revenue, adult;

        public HolderDetail(View mRootView) {
            title = (TextView) mRootView.findViewById(R.id.title_detail);
            ratings = (TextView) mRootView.findViewById(R.id.detail_ratings);
            date = (TextView) mRootView.findViewById(R.id.date_detail);
            overview = (TextView) mRootView.findViewById(R.id.overview_detail);
            /*
            language = (TextView) mRootView.findViewById(R.id.original_language_detail);
            runtime = (TextView) mRootView.findViewById(R.id.runtime_detail);
            homepage = (TextView) mRootView.findViewById(R.id.homepage_detail);
            budget = (TextView) mRootView.findViewById(R.id.budget_detail);
            revenue = (TextView) mRootView.findViewById(R.id.revenue_detail);
            adult = (TextView) mRootView.findViewById(R.id.adult_detail);
            */
        }
    }
}