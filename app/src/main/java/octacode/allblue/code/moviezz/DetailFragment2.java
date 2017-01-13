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
import octacode.allblue.code.moviezz.data.MovieContract.DetailTable;
import octacode.allblue.code.moviezz.data.MovieDbHelper;
import octacode.allblue.code.moviezz.fetchers.FetchDetails;
import octacode.allblue.code.moviezz.fetchers.FetchTrailers;


public class DetailFragment2 extends Fragment {
    public DetailFragment2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail__fragment2, container, false);
        FetchDetails fetchDetails = new FetchDetails(getContext());
        SQLiteDatabase liteDatabase = new MovieDbHelper(getContext()).getReadableDatabase();
        fetchDetails.execute(getActivity().getIntent().getStringExtra("MOVIE_ID"));
        String query_check = "Select * from "+ DetailTable.TABLE_NAME+" where "+ DetailTable.COLUMN_MOVIE_ID+ " = "+getActivity().getIntent().getStringExtra("MOVIE_ID");
        Cursor cursor = liteDatabase.rawQuery(query_check,null);
        Holder_Details holderDetails = new Holder_Details(rootView);
        if(cursor.moveToFirst()){
            holderDetails.revenue_tv.setText(cursor.getString(cursor.getColumnIndex(DetailTable.COLUMN_REVENUE)));
            holderDetails.homepage_tv.setText(cursor.getString(cursor.getColumnIndex(DetailTable.COLUMN_HOMEPAGE)));
            holderDetails.budget_tv.setText(cursor.getString(cursor.getColumnIndex(DetailTable.COLUMN_BUDGET)));
            holderDetails.original_language_tv.setText(getActivity().getIntent().getStringExtra("LANGUAGE"));
            holderDetails.runtime_tv.setText(cursor.getString(cursor.getColumnIndex(DetailTable.COLUMN_RUNTIME)));
        }
        return rootView;
    }

    class Holder_Details{
        TextView runtime_tv,original_language_tv,budget_tv,revenue_tv,homepage_tv;
        Holder_Details(View rootView){
            runtime_tv = (TextView)rootView.findViewById(R.id.runtime_name);
            original_language_tv = (TextView)rootView.findViewById(R.id.original_language_name);
            budget_tv = (TextView)rootView.findViewById(R.id.budget_name);
            revenue_tv = (TextView)rootView.findViewById(R.id.revenue_name);
            homepage_tv = (TextView)rootView.findViewById(R.id.homepage_name);
        }
    }
}
