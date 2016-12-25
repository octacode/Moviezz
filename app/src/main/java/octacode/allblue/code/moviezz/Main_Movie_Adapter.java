package octacode.allblue.code.moviezz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shasha on 23/12/16.
 */

class Main_Movie_Adapter extends RecyclerView.Adapter<Main_Movie_Adapter.Movie_ViewHolder> {

    private Context mContext;
    private List<MovieInfo> main_list;
    int position;

    class Movie_ViewHolder extends RecyclerView.ViewHolder {
        TextView movie_title;
        ImageView thumbnail;
        Movie_ViewHolder(View itemView) {
            super(itemView);
            movie_title=(TextView)itemView.findViewById(R.id.main_list_item_title);
            thumbnail=(ImageView)itemView.findViewById(R.id.main_list_item_thumbnail);
        }
    }

    Main_Movie_Adapter(Context mContext,List<MovieInfo> main_list){
        this.mContext=mContext;
        this.main_list=main_list;
    }

    private View item_view;
    @Override
    public Movie_ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        item_view= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.listitem_main,parent,false
        );

        /*
        if(viewType==1||viewType==7||viewType==13||viewType==19||viewType==28||viewType==34)
            item_view=LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_special,parent,false);
        */

        return new Movie_ViewHolder(item_view);

    }


    @Override
    public void onBindViewHolder(Movie_ViewHolder holder, int position) {
        this.position=position;
        final MovieInfo movieInfo=main_list.get(position);
        holder.movie_title.setText(movieInfo.getTitle());
        Picasso.with(mContext).load(movieInfo.getImage_url()).into(holder.thumbnail);

        item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent item=new Intent(mContext,DetailActivity2.class);
                ArrayList<String> data_fetched=MainFragment.data_fetched;
                for(int i=0;i<data_fetched.size();i++){
                    String splits[]=data_fetched.get(i).split(" ");
                    if(splits[0].matches(movieInfo.getImage_url())){
                        item.putExtra(Intent.EXTRA_TEXT,data_fetched.get(i));
                        Log.d("Shasha:->",data_fetched.get(i));
                    }
                }
                mContext.startActivity(item);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return main_list.size();
    }

}
