package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.first_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(getContext(),"Settings",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),SettingsActivit.class));
                break;
            case R.id.action_favourites:
                Toast.makeText(getContext(),"Favourites",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_main, container, false);
        List<MovieInfo> arrayList=new ArrayList<>();
        MovieInfo mock_data=new MovieInfo("Jungle Book",R.mipmap.ic_launcher);
        for(int i=0;i<100;i++)
        arrayList.add(i,mock_data);
        Main_Movie_Adapter adapter = new Main_Movie_Adapter(getContext(),arrayList);
        RecyclerView recyclerView=(RecyclerView) rootView.findViewById(R.id.main_grid_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
