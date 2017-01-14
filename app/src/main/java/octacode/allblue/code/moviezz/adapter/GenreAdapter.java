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
 * Created by shasha on 12/1/17.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private Context mContext;
    private List<InfoTransfer> mGenreList;

    public GenreAdapter(Context context,List<InfoTransfer> mGenreList){
        mContext=context;
        this.mGenreList=mGenreList;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_list_item,parent,false);
        return new GenreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        holder.genre_tv.setText(mGenreList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mGenreList.size();
    }

    class GenreViewHolder extends RecyclerView.ViewHolder{
        TextView genre_tv;
        GenreViewHolder(View itemView) {
            super(itemView);
            genre_tv = (TextView)itemView.findViewById(R.id.genre_tv);
        }
    }
}
