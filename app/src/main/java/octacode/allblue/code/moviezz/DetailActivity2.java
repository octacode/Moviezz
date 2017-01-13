package octacode.allblue.code.moviezz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import octacode.allblue.code.moviezz.adapter.FeaturedCrewAdapter;
import octacode.allblue.code.moviezz.adapter.GenreAdapter;
import octacode.allblue.code.moviezz.adapter.TopCastAdapter;
import octacode.allblue.code.moviezz.adapter.TrailersAdapter;
import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.data.MovieDbHelper;
import octacode.allblue.code.moviezz.fetchers.FetchCrewCast;
import octacode.allblue.code.moviezz.fetchers.FetchTrailers;

public class DetailActivity2 extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    public static TextView mTitle,main_title;
    public static ImageView image_backdrop;
    private RecyclerView mRecyclerView;
    public static CircleImageView image_view_poster;
    public static TopCastAdapter topCastAdapter;
    public static TrailersAdapter trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        getSupportFragmentManager().beginTransaction().add(R.id.container_detail,new DetailFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container_detail_2,new DetailFragment2()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container_3_review, new ReviewFragment()).commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        main_title = (TextView) findViewById(R.id.main_title);
        mTitle = (TextView) findViewById(R.id.main_textview_title);
        image_backdrop = (ImageView) findViewById(R.id.detail_image_back_drop);
        image_view_poster = (CircleImageView) findViewById(R.id.detail_image_poster);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(this);
        mToolbar.inflateMenu(R.menu.menu_detail_activity2);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        String genre_ids = getIntent().getStringExtra("GENRE_IDS");
        Toast.makeText(this,genre_ids,Toast.LENGTH_SHORT).show();
        setGenre(genre_ids);
        setTopCast();
        setFeaturedCrew();
        setTrailer();
        setSimilarMovies();
    }


    private void setGenre(String genre_ids) {
        genre_ids=genre_ids.replace("[","");
        genre_ids=genre_ids.replace("]","");
        String splits[]=genre_ids.split(",");
        ArrayList<InfoTransfer> infoTransferList= new ArrayList<>();
        InfoTransfer genre_name;
        for(int i=0;i<splits.length;i++) {
            splits[i] = Utility.getGenreName(Integer.parseInt(splits[i]));
            genre_name = new InfoTransfer(splits[i]);
            infoTransferList.add(genre_name);
        }
        mRecyclerView =(RecyclerView)findViewById(R.id.rv_genre);
        GenreAdapter genreAdapter = new GenreAdapter(this,infoTransferList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(genreAdapter);
        genreAdapter.notifyDataSetChanged();
    }

    private void setFeaturedCrew() {
        mRecyclerView =(RecyclerView)findViewById(R.id.rv_featured_crew);

        SQLiteDatabase liteDatabase = new MovieDbHelper(this).getReadableDatabase();

        String query_check = "Select * from "+ MovieContract.CrewTable.TABLE_NAME+" where "+ MovieContract.CrewTable.COLUMN_MOVIE_ID+ " = "+getIntent().getStringExtra("MOVIE_ID");
        Cursor cursor = liteDatabase.rawQuery(query_check,null);

        ArrayList<InfoTransfer> list = new ArrayList<>();
        String name="",role="",profile_url="";

        if(cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(MovieContract.CrewTable.COLUMN_NAME));
            role = cursor.getString(cursor.getColumnIndex(MovieContract.CrewTable.COLUMN_ROLE));
        }

        String splits_name[] = name.split("__SPLITTER__");
        String splits_role[] = role.split("__SPLITTER__");

        for(int i=0;i<splits_name.length-1;i++){
            InfoTransfer infoTransfer = new InfoTransfer(splits_name[i],splits_role[i]);
            list.add(infoTransfer);
        }

        FeaturedCrewAdapter featuredCrewAdapter = new FeaturedCrewAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(featuredCrewAdapter);
        featuredCrewAdapter.notifyDataSetChanged();
    }

    private void setTopCast(){
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_top_cast);
        FetchCrewCast fetchCrewCast = new FetchCrewCast(this);
        fetchCrewCast.execute(getIntent().getStringExtra("MOVIE_ID"));

        SQLiteDatabase liteDatabase = new MovieDbHelper(this).getReadableDatabase();

        String query_check = "Select * from "+ MovieContract.CastTable.TABLE_NAME+" where "+ MovieContract.CastTable.COLUMN_MOVIE_ID+ " = "+getIntent().getStringExtra("MOVIE_ID");
        Cursor cursor = liteDatabase.rawQuery(query_check,null);

        ArrayList<InfoTransfer> list = new ArrayList<>();
        String name="",character="",profile_url="";
        if(cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(MovieContract.CastTable.COLUMN_NAME));
            character = cursor.getString(cursor.getColumnIndex(MovieContract.CastTable.COLUMN_CHARACTER_PLAYED));
            profile_url = cursor.getString(cursor.getColumnIndex(MovieContract.CastTable.COLUMN_PROFILE_URL));
        }

        String splits_name[] = name.split("__SPLITTER__");
        String splits_character[] = character.split("__SPLITTER__");
        String splits_profile_url[] = profile_url.split("__SPLITTER__");

        for(int i=0;i<splits_name.length-1;i++){
            if(splits_profile_url[i].matches("")){
                splits_profile_url[i]=getIntent().getStringExtra("POSTER_URL");
            }
            InfoTransfer infoTransfer = new InfoTransfer(splits_name[i],splits_character[i],splits_profile_url[i]);
            list.add(infoTransfer);
        }

        topCastAdapter = new TopCastAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(topCastAdapter);
        topCastAdapter.notifyDataSetChanged();

    }

    private void setTrailer(){
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_videos);
        FetchTrailers fetchTrailers = new FetchTrailers(this);
        SQLiteDatabase liteDatabase = new MovieDbHelper(this).getReadableDatabase();
        fetchTrailers.execute(getIntent().getStringExtra("MOVIE_ID"));
        String query_check = "Select * from "+ MovieContract.TrailerTable.TABLE_NAME+" where "+ MovieContract.TrailerTable.COLUMN_MOVIE_ID+ " = "+getIntent().getStringExtra("MOVIE_ID");
        Cursor cursor = liteDatabase.rawQuery(query_check,null);
        String db_name="",db_poster_pic="",db_url="";
        if(cursor.moveToFirst()) {
            db_name = cursor.getString(cursor.getColumnIndex(MovieContract.TrailerTable.COLUMN_NAME));
            db_poster_pic = cursor.getString(cursor.getColumnIndex(MovieContract.TrailerTable.COLUMN_POSTER_URL));
            db_url = cursor.getString(cursor.getColumnIndex(MovieContract.TrailerTable.COLUMN_URL));
        }
        String splits_name[] = db_name.split("__SPLITTER__");
        String splits_poster_pic[] = db_poster_pic.split("__SPLITTER__");
        String splits_url[] = db_url.split("__SPLITTER__");
        ArrayList<InfoTransfer> list = new ArrayList<>();

        for(int i=0;i<splits_name.length-1;i++){
            if(splits_name[i].matches("")){
                splits_name[i]=getIntent().getStringExtra("POSTER_URL");
            }
            InfoTransfer infoTransfer = new InfoTransfer(splits_name[i],splits_poster_pic[i],splits_url[i]);
            list.add(infoTransfer);
        }

        Toast.makeText(this,splits_name[splits_name.length-1],Toast.LENGTH_SHORT).show();

        trailerAdapter = new TrailersAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(trailerAdapter);
        trailerAdapter.notifyDataSetChanged();
    }

    private void setSimilarMovies() {
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_similar_movies);
        String name = "Kunk Fu Panda", url = "http://image.tmdb.org/t/p/w185//qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg";
        ArrayList<InfoTransfer> list = new ArrayList<>();
        InfoTransfer dummy = new InfoTransfer(name,url);
        for(int i=0;i<90;i++)
            list.add(dummy);
        TrailersAdapter trailerAdapter = new TrailersAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(trailerAdapter);
        trailerAdapter.notifyDataSetChanged();
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_activity2, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}

