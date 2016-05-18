package com.durian.sixkids.durian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.durian.sixkids.durian.common.DBMuiscs;
import com.durian.sixkids.durian.common.MusicModel;
import com.durian.sixkids.durian.common.Musics;
import com.durian.sixkids.durian.common.MyViewPagerAdapter;
import com.durian.sixkids.durian.common.SetStatusBarTextColor;
import com.durian.sixkids.durian.me.LocalManagement;
import com.durian.sixkids.durian.musicplay.MusicList;
import com.durian.sixkids.durian.musicplay.MusicPlay;
import com.durian.sixkids.durian.search_recomm.MusicLibrary;
import com.durian.sixkids.durian.search_recomm.MusicSearch;
import com.durian.sixkids.durian.util.ConstUtil;
import com.durian.sixkids.durian.util.PlayService;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MusicMainActivity extends AppCompatActivity implements View.OnClickListener ,View.OnTouchListener,ViewPager.OnPageChangeListener{
//    private SystemBarTintManager tintManager;
    private final static int ME = 1;
    private final static int MUSIC_LIBRAY = 0;

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
    private MyViewPagerAdapter adapter;
    private List<View> views = new ArrayList<View>();


    private MusicLibrary musicLibrary;
    private LocalManagement localManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_music_main);
        initWindow();
       getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        SetStatusBarTextColor.setMiuiStatusBarDarkMode(this,true);

        initViewPager();
        initListener();
        initTouch();
        initModels();
        setPlayingStatus();


        /**
         *
         */
        if (PlayService.musicMainActivity == null){
            PlayService.musicMainActivity = this;
        }
        PlayService.ACTIVITY = PlayService.MAIN_ACTIVITY;  //设置为当前页面
    }

    private  void initViewPager(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View mainSearchView = inflater.inflate(R.layout.activity_recommendation,null);
        musicLibrary = new MusicLibrary(this,mainSearchView);
        views.add(mainSearchView);

        View localManaView = inflater.inflate(R.layout.localmanagement_fragment,null);
        localManagement = new LocalManagement(this,localManaView);
        views.add(localManaView);

        adapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

    }

    /**
     * 初始化模型
     */
    private void initModels(){
        Musics.musicModels = DBMuiscs.queryAllMusic();
    }

    private void initTouch(){
        ivPlay.setOnTouchListener(this);
        ivNext.setOnTouchListener(this);
        ivSearchBtn.setOnTouchListener(this);
    }
    private void initListener(){

        tvMusicLibrary.setOnClickListener(this);
        tvMyMusic.setOnClickListener(this);
        tvMe.setOnClickListener(this);
        ivAlbum.setOnClickListener(this);
        ivSearchBtn.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //onPageSelected(MUSIC_LIBRAY);
        setPlayingStatus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_activity_title_musiclibrary:
                tvMusicLibrary.setTextColor(getResources().getColor(R.color.orangecolor));
                tvMyMusic.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMe.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                viewPager.setCurrentItem(MUSIC_LIBRAY);
                break;
            case R.id.main_activity_title_mymusic:
                Intent intent = new Intent(this, MusicList.class);
//                musicLibrary = null;
//                localManagement = null;
//                System.gc();
                startActivityForResult(intent,0);
                break;
            case R.id.main_activity_title_me:
                tvMusicLibrary.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMyMusic.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMe.setTextColor(getResources().getColor(R.color.orangecolor));
                viewPager.setCurrentItem(ME);
                break;
            case R.id.main_activity_bottom_bar_iv:
                Intent in = new Intent(this, MusicPlay.class);
//                musicLibrary = null;
//                localManagement = null;
//                System.gc();
                startActivityForResult(in,0);
                break;
            case R.id.main_activity_bottom_bar_play_btn:
                break;
            case R.id.main_activity_bottom_bar_next_btn:
                break;
            case R.id.main_activity_title_search_iv:
                Intent intent1 =new Intent(this,MusicSearch.class);
                startActivity(intent1);
                break;
        }

    }

    /**
     * 回调函数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)return;
        setPlayingStatus();
    }

    //设置状态
    private void setPlayingStatus(){
        if (Musics.isPlaying){
            ivPlay.setImageResource(R.drawable.h_nowplaying_bar_pause_n);
        }else{
            ivPlay.setImageResource(R.drawable.h_nowplaying_bar_play_n);
        }

        MusicModel model = Musics.musicModels.get(Musics.playIndex);
        tvSongName.setText(model.getSinger()+" - "+ model.getName());
        tvSinger.setText(model.getAlbum());
        //设置logo
        ivAlbum.setImageResource(model.getResId());
    }

    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.main_activity_bottom_bar_play_btn:
                setOnPlaying(event);
                break;
            case R.id.main_activity_bottom_bar_next_btn:
                setOnNext(event);
                break;
            case R.id.main_activity_title_search_iv:
                setSearch(event);
                break;
        }
        return true;
    }

    //search icon
    private void setSearch(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            ivSearchBtn.setImageResource(R.drawable.search_icon_p);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            ivSearchBtn.setImageResource(R.drawable.h_search_icon_n);
        }
    }
    // next
    private void setOnNext(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            ivNext.setImageResource(R.drawable.h_nowplaying_bar_next_p);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            play(0,true);
            playNext(); //播放下一曲
        }
    }

    /**
     * 播放下一曲
     */
    public void playNext(){
        ivNext.setImageResource(R.drawable.h_nowplaying_bar_next_n);
        setPlayingStatus();
        //play(0,true);
    }
    //playing
    private void setOnPlaying(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (Musics.isPlaying){
                ivPlay.setImageResource(R.drawable.h_nowplaying_bar_pause_p);
            }else{
                ivPlay.setImageResource(R.drawable.h_nowplaying_bar_play_p);
            }
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            if (Musics.isPlaying){
                ivPlay.setImageResource(R.drawable.h_nowplaying_bar_play_n);
            }else {
                ivPlay.setImageResource(R.drawable.h_nowplaying_bar_pause_n);

            }
            Musics.isPlaying  = !Musics.isPlaying;

            if (Musics.isPlaying){
                play(ConstUtil.STATE_PLAY,false);
            }else{
                play(ConstUtil.STATE_PAUSE,false);
            }
        }
    }

    /**
     * 设置播放
     * @param state
     * @param isNext
     */
    private void play(int state,boolean isNext){
    if (isNext){
        PlayService.playNext();
        playMusic(Musics.musicModels.get(Musics.playIndex).getSrc());
    }else if (Musics.isFirstPlaying){
        playMusic(Musics.musicModels.get(Musics.playIndex).getSrc());
    } else{
        sendBroadCastToService(state);
    }
}

    protected void sendBroadCastToService(int state){
        Intent intent = new Intent();
        intent.setAction(ConstUtil.MUSICSERVICE_ACTION);
        intent.putExtra("control", state);
        this.sendBroadcast(intent);
    }

    public void playMusic(String path){
        Intent intent=new Intent(this,PlayService.class);
        intent.putExtra("path", path);
        this.startService(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
//            localManagement = null;
//            System.gc();
            tvMusicLibrary.setTextColor(getResources().getColor(R.color.orangecolor));
            tvMyMusic.setTextColor(getResources().getColor(R.color.textdefualtcolor));
            tvMe.setTextColor(getResources().getColor(R.color.textdefualtcolor));
//            musicLibrary = new MusicLibrary(this,views.get(0));
        }else {
//            musicLibrary = null;
//            System.gc();
            tvMusicLibrary.setTextColor(getResources().getColor(R.color.textdefualtcolor));
            tvMyMusic.setTextColor(getResources().getColor(R.color.textdefualtcolor));
            tvMe.setTextColor(getResources().getColor(R.color.orangecolor));
//            localManagement = new LocalManagement(this,views.get(1));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
