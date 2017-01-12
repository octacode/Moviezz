package octacode.allblue.code.moviezz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Short2;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;

public class ReviewFragment extends Fragment {

    String[] review_columns = {
            MovieContract.ReviewTable.COLUMN_MOVIE_ID_DOUBLE,
            MovieContract.ReviewTable.COLUMN_TRAILER_IDS_STRING,
            MovieContract.ReviewTable.COLUMN_TOTAL_RESULTS_INT,
            MovieContract.ReviewTable.COLUMN_MOVIE_URL,
            MovieContract.ReviewTable.COLUMN_MOVIE_AUTHOR,
            MovieContract.ReviewTable.COLUMN_MOVIE_CONTENT
    };

    public ReviewFragment() {

    }

    private String LOG_TAG = getClass().getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        TextView tv_review=(TextView)rootView.findViewById(R.id.review_tv);
        TextView tv_author = (TextView)rootView.findViewById(R.id.review_author_name);
        TextView tv_review_url = (TextView)rootView.findViewById(R.id.review_url);
        TextView tv_nothing = (TextView)rootView.findViewById(R.id.no_review);
        FetchReviewTask fetchReviewTask = new FetchReviewTask(getContext());
        fetchReviewTask.execute(getActivity().getIntent().getStringExtra("MOVIE_ID"));
        SQLiteDatabase liteDatabase = new MovieDbHelper(getContext()).getReadableDatabase();
        String query_check = "Select * from "+ MovieContract.ReviewTable.TABLE_NAME+" where "+ MovieContract.ReviewTable.COLUMN_MOVIE_ID_DOUBLE+ " = "+getActivity().getIntent().getStringExtra("MOVIE_ID");
        Cursor cursor = liteDatabase.rawQuery(query_check,null);
        if(cursor.getCount()<=0){
            tv_nothing.setVisibility(View.VISIBLE);
        }
        while(cursor.moveToNext()) {
            tv_author.setText(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewTable.COLUMN_MOVIE_AUTHOR)));
            tv_review.setText(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewTable.COLUMN_MOVIE_CONTENT)));
            tv_review_url.setText(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewTable.COLUMN_MOVIE_URL)));
        }
        return rootView;
    }
}
