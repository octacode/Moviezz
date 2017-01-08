package octacode.allblue.code.moviezz;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by shasha on 7/1/17.
 */

public class Main_Movie_Adapter extends CursorAdapter {

    private String LOG_TAG = getClass().getSimpleName();

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

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final Holder viewHolder=(Holder)view.getTag();
        String title = cursor.getString(MainFragment.COLUMN_TITLE);
        final String postURL = cursor.getColumnName(MainFragment.COLUMN_POSTER_URL);
        Picasso.with(context).load(postURL).into(viewHolder.thumbnail, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(postURL).error(R.mipmap.ic_launcher).into(viewHolder.thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Log.d(LOG_TAG,"Error Lodaing Images.");
                    }
                });
            }
        });
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
