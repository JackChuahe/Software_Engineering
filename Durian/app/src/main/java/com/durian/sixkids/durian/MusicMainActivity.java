package com.durian.sixkids.durian;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.w3c.dom.Text;

public class MusicMainActivity extends AppCompatActivity implements View.OnClickListener{
//    private SystemBarTintManager tintManager;

    private LinearLayout lyBottomBar ;
    private TextView tvMusicLibrary;
    private TextView tvMyMusic;
    private TextView tvMe;
    private ImageView ivSearchBtn;
    private ImageView ivAlbum;
    private TextView tvSongName;
    private TextView tvSinger;
    private ImageView ivPlay;
    private ImageView ivNext;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
       // initWindow();
        setContentView(R.layout.activity_music_main);
       getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lyBottomBar = (LinearLayout)findViewById(R.id.main_activity_bottom_bar);
        tvMusicLibrary = (TextView)findViewById(R.id.main_activity_title_musiclibrary);
        tvMyMusic = (TextView)findViewById(R.id.main_activity_title_mymusic);
        tvMe = (TextView)findViewById(R.id.main_activity_title_me);
        tvSongName = (TextView)findViewById(R.id.main_activity_bottom_bar_song_name_tv);
        tvSinger = (TextView)findViewById(R.id.main_activity_bottom_bar_singer_tv);

        ivSearchBtn = (ImageView)findViewById(R.id.main_activity_title_search_iv);
        ivAlbum = (ImageView)findViewById(R.id.main_activity_bottom_bar_iv);
        ivPlay = (ImageView)findViewById(R.id.main_activity_bottom_bar_play_btn);
        ivNext = (ImageView)findViewById(R.id.main_activity_bottom_bar_next_btn);

        viewPager = (ViewPager)findViewById(R.id.main_activity_viewpager);

        initListener();
    }

    private void initListener(){

        tvMusicLibrary.setOnClickListener(this);
        tvMyMusic.setOnClickListener(this);
        tvMe.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_activity_title_musiclibrary:
                tvMusicLibrary.setTextColor(getResources().getColor(R.color.orangecolor));
                tvMyMusic.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMe.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                break;
            case R.id.main_activity_title_mymusic:
                tvMusicLibrary.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMyMusic.setTextColor(getResources().getColor(R.color.orangecolor));
                tvMe.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                break;
            case R.id.main_activity_title_me:
                tvMusicLibrary.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMyMusic.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMe.setTextColor(getResources().getColor(R.color.orangecolor));
                break;
            case R.id.main_activity_title_search_iv:
                break;
            case R.id.main_activity_bottom_bar_play_btn:
                break;
            case R.id.main_activity_bottom_bar_next_btn:
                break;
        }

    }


//    private void initWindow(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintColor(Color.parseColor("#c1c1be"));
//            tintManager.setStatusBarTintEnabled(true);
//        }
//    }
}
