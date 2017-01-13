package octacode.allblue.code.moviezz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;
import octacode.allblue.code.moviezz.fetchers.FetchReviewTask;

public class ReviewFragment extends Fragment {

    public ReviewFragment() {

    }

    private String LOG_TAG = getClass().getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static TextView tv_review,tv_author,tv_nothing,tv_review_url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        tv_review=(TextView)rootView.findViewById(R.id.review_tv);
        tv_author = (TextView)rootView.findViewById(R.id.review_author_name);
        tv_review_url = (TextView)rootView.findViewById(R.id.review_url);
        tv_nothing = (TextView)rootView.findViewById(R.id.no_review);
        FetchReviewTask fetchReviewTask = new FetchReviewTask(getContext());
        fetchReviewTask.execute(getActivity().getIntent().getStringExtra("MOVIE_ID"));
        return rootView;
    }
}
