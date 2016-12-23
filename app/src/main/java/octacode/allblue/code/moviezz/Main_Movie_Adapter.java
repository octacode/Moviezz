package octacode.allblue.code.moviezz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shasha on 23/12/16.
 */

public class Main_Movie_Adapter extends RecyclerView.Adapter<Main_Movie_Adapter.Movie_ViewHolder> {

    private Context mContext;
    private List<MovieInfo> main_list;

    public class Movie_ViewHolder extends RecyclerView.ViewHolder {
        TextView movie_title;
        ImageView thumbnail;
        public Movie_ViewHolder(View itemView) {
            super(itemView);
            movie_title=(TextView)itemView.findViewById(R.id.main_list_item_title);
            thumbnail=(ImageView)itemView.findViewById(R.id.main_list_item_thumbnail);
        }
    }

    public Main_Movie_Adapter(Context mContext,List<MovieInfo> main_list){
        this.mContext=mContext;
        this.main_list=main_list;
    }

    @Override
    public Movie_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item_view= LayoutInflater.from(parent.getContext()).inflate(
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
        MovieInfo movieInfo=main_list.get(position);
        holder.movie_title.setText(movieInfo.getName());
        holder.thumbnail.setImageResource(movieInfo.getThumbnail());
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
