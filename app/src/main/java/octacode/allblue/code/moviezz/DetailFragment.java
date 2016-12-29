package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class DetailFragment extends Fragment {

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
        /*
        String fetched=getActivity().getIntent().getExtras().getString(Intent.EXTRA_TEXT);
        Toast.makeText(getContext(),fetched.toString(),Toast.LENGTH_SHORT).show();
        */
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
