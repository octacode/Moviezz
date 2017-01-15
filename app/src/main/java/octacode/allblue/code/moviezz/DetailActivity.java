package octacode.allblue.code.moviezz;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import octacode.allblue.code.moviezz.adapter.GenreAdapter;
import octacode.allblue.code.moviezz.fetchers.FetchCrewCast;
import octacode.allblue.code.moviezz.fetchers.FetchTrailers;

public class DetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    public static TextView mTitle,main_title;
    public static ImageView image_backdrop;
    public static CircleImageView image_view_poster;

    public static RecyclerView mRecyclerView_featured,mRecyclerView_top_cast,mRecyclerView_trailers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);
        getSupportFragmentManager().beginTransaction().add(R.id.container_detail,new DetailFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container_detail_2,new DetailFragment2()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container_3_review, new ReviewFragment()).commit();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

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
        setTopCastCrew();
        setTrailer();
    }


    private void setGenre(String genre_ids) {
        genre_ids=genre_ids.replace("[","");
        genre_ids=genre_ids.replace("]","");
        String splits[]=genre_ids.split(",");
        ArrayList<InfoTransfer> infoTransferList= new ArrayList<>();
        InfoTransfer genre_name;
        for(int i=0;i<splits.length-1;i++) {
            splits[i] = Utility.getGenreName(Integer.parseInt(splits[i]));
            genre_name = new InfoTransfer(splits[i]);
            infoTransferList.add(genre_name);
        }
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_genre);
        GenreAdapter genreAdapter = new GenreAdapter(this,infoTransferList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(genreAdapter);
        genreAdapter.notifyDataSetChanged();
    }

    private void setTopCastCrew(){
        mRecyclerView_top_cast = (RecyclerView)findViewById(R.id.rv_top_cast);
        mRecyclerView_featured =(RecyclerView)findViewById(R.id.rv_featured_crew);
        FetchCrewCast fetchCrewCast = new FetchCrewCast(this);
        fetchCrewCast.execute(getIntent().getStringExtra("MOVIE_ID"));
    }

    private void setTrailer(){
        mRecyclerView_trailers = (RecyclerView)findViewById(R.id.rv_videos);
        FetchTrailers fetchTrailers = new FetchTrailers(this);
        fetchTrailers.execute(getIntent().getStringExtra("MOVIE_ID"));
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

