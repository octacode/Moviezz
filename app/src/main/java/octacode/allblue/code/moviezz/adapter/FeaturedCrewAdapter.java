package octacode.allblue.code.moviezz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import octacode.allblue.code.moviezz.InfoTransfer;
import octacode.allblue.code.moviezz.R;

/**
 * Created by shasha on 11/1/17.
 */

public class FeaturedCrewAdapter extends RecyclerView.Adapter<FeaturedCrewAdapter.FeaturedCrewViewHolder> {

    private Context mContext;
    private List<InfoTransfer> mCrewList;

    public class FeaturedCrewViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView_Name,mTextView_Role;

        public FeaturedCrewViewHolder(View itemView) {
            super(itemView);
            mTextView_Name = (TextView)itemView.findViewById(R.id.featured_crew_list_item_name);
            mTextView_Role = (TextView)itemView.findViewById(R.id.featured_crew_list_item_role);
        }
    }

    public FeaturedCrewAdapter(Context context,List<InfoTransfer> crewList){
        mContext=context;
        mCrewList=crewList;
    }

    @Override
    public FeaturedCrewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.featured_crew_list_item,parent,false);
        return new FeaturedCrewViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mCrewList.size();
    }

    @Override
    public void onBindViewHolder(FeaturedCrewViewHolder holder, int position) {
        InfoTransfer infoTransfer = mCrewList.get(position);
        holder.mTextView_Name.setText(infoTransfer.getName());
        holder.mTextView_Role.setText(infoTransfer.getRole());
    }
}
