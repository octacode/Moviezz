package octacode.allblue.code.moviezz.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import octacode.allblue.code.moviezz.InfoTransfer;
import octacode.allblue.code.moviezz.R;
import octacode.allblue.code.moviezz.fetchers.FetchCrewDetails;

/**
 * Created by shasha on 11/1/17.
 */

public class TopCastAdapter extends RecyclerView.Adapter<TopCastAdapter.TopCastHolder> {

    private List<InfoTransfer> mFeaturedCast;
    private Context mContext;
    private View itemView;
    @Override
    public TopCastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_cast_list_item,parent,false);
        return new TopCastHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TopCastHolder holder, int position) {
        final InfoTransfer featuredCast = mFeaturedCast.get(position);
        holder.top_name.setText(featuredCast.getName());
        holder.top_role.setText(featuredCast.getRole());
        Picasso.with(mContext).load(featuredCast.getId_url()).error(R.drawable.user).into(holder.cast_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchCrewDetails fetchCrewDetails = new FetchCrewDetails(mContext);
                fetchCrewDetails.execute(featuredCast.getCredit_id());
            }
        });
    }

    public TopCastAdapter(Context context, List<InfoTransfer> crewList){
        mContext=context;
        mFeaturedCast=crewList;
    }

    @Override
    public int getItemCount() {
        return mFeaturedCast.size();
    }

    public class TopCastHolder extends RecyclerView.ViewHolder{

        public TextView top_name,top_role;
        public ImageView cast_image;

        public TopCastHolder(View itemView) {
            super(itemView);
            top_name=(TextView)itemView.findViewById(R.id.cast_tv_list_name);
            top_role = (TextView)itemView.findViewById(R.id.cast_tv_list_role);
            cast_image = (ImageView)itemView.findViewById(R.id.cast_image_list_item);
        }
    }
}
