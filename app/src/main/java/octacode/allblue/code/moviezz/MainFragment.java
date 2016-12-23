package octacode.allblue.code.moviezz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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
        GridView recyclerView=(GridView) rootView.findViewById(R.id.main_grid_view);
        ArrayList<String> mock_data=new ArrayList<>();
        for(int i=0;i<10;i++)
        mock_data.add("Shashwat");
        ArrayAdapter<String> mock_Adapter=new ArrayAdapter<String>(getContext(),R.layout.listitem_main,R.id.main_list_item_textview,mock_data);
        recyclerView.setAdapter(mock_Adapter);
        return rootView;
    }

}
