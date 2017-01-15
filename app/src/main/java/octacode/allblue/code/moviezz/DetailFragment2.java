package octacode.allblue.code.moviezz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import octacode.allblue.code.moviezz.fetchers.FetchDetails;


public class DetailFragment2 extends Fragment {
    public static ProgressBar loadingPanel2;
    public static LinearLayout linearLayout;

    public DetailFragment2() {
    }
    public static TextView runtime_tv,original_language_tv,budget_tv,revenue_tv,homepage_tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail__fragment2, container, false);
        runtime_tv = (TextView)rootView.findViewById(R.id.runtime_name);
        original_language_tv = (TextView)rootView.findViewById(R.id.original_language_name);
        budget_tv = (TextView)rootView.findViewById(R.id.budget_name);
        revenue_tv = (TextView)rootView.findViewById(R.id.revenue_name);
        homepage_tv = (TextView)rootView.findViewById(R.id.homepage_name);
        loadingPanel2 = (ProgressBar)rootView.findViewById(R.id.loadingPanel2);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.linealLayoutDetail2);

        FetchDetails fetchDetails = new FetchDetails(getContext());
        fetchDetails.execute(getActivity().getIntent().getStringExtra("MOVIE_ID"),getActivity().getIntent().getStringExtra("LANGUAGE"));
        return rootView;
    }

}
