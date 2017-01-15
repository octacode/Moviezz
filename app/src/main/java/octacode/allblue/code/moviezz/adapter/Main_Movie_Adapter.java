package octacode.allblue.code.moviezz.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import octacode.allblue.code.moviezz.MainFragment;
import octacode.allblue.code.moviezz.R;

/**
 * Created by shasha on 7/1/17.
 */

public class Main_Movie_Adapter extends CursorAdapter {


    public Main_Movie_Adapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_main,parent,false);
        Holder viewHolder=new Holder(view);
        view.setTag(viewHolder);
        return view;
    }

    private Holder viewHolder;
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        viewHolder=(Holder)view.getTag();
        String title = cursor.getString(MainFragment.COLUMN_TITLE);
        final String postURL = cursor.getString(MainFragment.COLUMN_POSTER_URL);
        Picasso.with(context).load(postURL).error(R.mipmap.ic_launcher).into(viewHolder.thumbnail);
        viewHolder.title.setText(title);
    }


    public class Holder{
        ImageView thumbnail;
        TextView title;
        public Holder(View view){
            thumbnail = (ImageView)view.findViewById(R.id.main_list_item_thumbnail);
            title = (TextView)view.findViewById(R.id.main_list_item_title);
        }
    }
}
