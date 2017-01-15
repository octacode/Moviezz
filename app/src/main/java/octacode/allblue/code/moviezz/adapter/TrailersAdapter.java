package octacode.allblue.code.moviezz.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import octacode.allblue.code.moviezz.InfoTransfer;
import octacode.allblue.code.moviezz.R;

/**
 * Created by shasha on 11/1/17.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private Context mContext;
    private List<InfoTransfer> mVideoList;
    private View itemView;

    public TrailersAdapter(Context context,List<InfoTransfer> mVideoList){
        mContext=context;
        this.mVideoList=mVideoList;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailers_list_item,parent,false);
        return new TrailerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        final InfoTransfer infoTransfer = mVideoList.get(position);
        //(NAME,ID)
        Picasso.with(mContext).load("http://img.youtube.com/vi/"+infoTransfer.getRole()+"/0.jpg").error(R.mipmap.ic_launcher).into(holder.trailer_thumbnail);
        holder.trailer_text.setText(infoTransfer.getName());
        /*
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v="+infoTransfer.getRole();
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{
        ImageView trailer_thumbnail;
        TextView trailer_text;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailer_text=(TextView)itemView.findViewById(R.id.video_name);
            trailer_thumbnail=(ImageView)itemView.findViewById(R.id.video_thumbnail);
        }
    }
}
