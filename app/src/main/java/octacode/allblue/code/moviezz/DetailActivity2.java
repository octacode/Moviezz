package octacode.allblue.code.moviezz;

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


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import octacode.allblue.code.moviezz.adapter.FeaturedCrewAdapter;
import octacode.allblue.code.moviezz.adapter.TopCastAdapter;
import octacode.allblue.code.moviezz.adapter.TrailersAdapter;

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
        setGenre(genre_ids);
        setFeaturedCrew();
        setTopCast();
        setTrailer();
    }

    private void setGenre(String genre_ids) {
        TextView genre_tv = (TextView)findViewById(R.id.text_view_genre_detail);
        String splits[]=genre_ids.split(" ");
        genre_ids = "";
        for(int i=0;i<splits.length;i++) {
            splits[i] = Utility.getGenreName(Integer.parseInt(splits[i]));
            genre_ids=genre_ids+splits[i]+"       ";
        }
        genre_tv.setText(genre_ids);
    }

    private void setFeaturedCrew() {
        mRecyclerView =(RecyclerView)findViewById(R.id.rv_featured_crew);
        String name= "Shashwat",role="Director";
        ArrayList<InfoTransfer> list = new ArrayList<>();
        InfoTransfer dummy = new InfoTransfer(name,role);
        for(int i=0;i<80;i++)
            list.add(dummy);
        FeaturedCrewAdapter featuredCrewAdapter = new FeaturedCrewAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(featuredCrewAdapter);
        featuredCrewAdapter.notifyDataSetChanged();
    }

    private void setTopCast(){
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_top_cast);
        String name = "Shashwat",role = "Protagonist",id_url = "http://image.tmdb.org/t/p/w185//qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg";
        ArrayList<InfoTransfer> list = new ArrayList<>();
        InfoTransfer dummy = new InfoTransfer(name,role,id_url);
        for(int i=0;i<100;i++)
            list.add(dummy);
        TopCastAdapter topCastAdapter = new TopCastAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(topCastAdapter);
        topCastAdapter.notifyDataSetChanged();
    }

    private void setTrailer(){
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_videos);
        String name = "Official Trailer", url = "http://image.tmdb.org/t/p/w185//qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg";
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

