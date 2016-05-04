package com.durian.sixkids.durian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.durian.sixkids.durian.common.MusicModel;
import com.durian.sixkids.durian.common.SetStatusBarTextColor;
import com.durian.sixkids.durian.musicplay.MusicList;
import com.durian.sixkids.durian.musicplay.MusicPlay;
import com.durian.sixkids.durian.util.ConstUtil;
import com.durian.sixkids.durian.util.PlayService;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MusicMainActivity extends AppCompatActivity implements View.OnClickListener ,View.OnTouchListener{
//    private SystemBarTintManager tintManager;

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
    private boolean isPlaying = false;
    private int playIndex = 0;
    private List<MusicModel> musics = new ArrayList<MusicModel>();
    private final  static  String [] paths = {"/storage/emulated/0/KuwoMusic/music/TiK ToK (Live).mp3","/storage/emulated/0/KuwoMusic/music/Uptown Funk.mp3","/storage/emulated/0/KuwoMusic/music/You Are Beautiful.mp3"};
    private final static int  MUSIC_NUM = 3;
    private   boolean isFirstPlaying = true;
    private int time = 0;
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

        initListener();
        initTouch();
        initModels();
        setPlayingStatus();
    }

    private void initModels(){
        MusicModel model2 = new MusicModel();
        model2.setAlbum("Promo Only Mainstream Radio October");
        model2.setSinger("Ke.Ha");
        model2.setName("Tik Tok");
        model2.setResId(R.drawable.tktk_img);
        musics.add(model2);

        MusicModel model3 = new MusicModel();
        model3.setAlbum("Uptown Funk");
        model3.setSinger("Mark Ronson");
        model3.setName("Uptown Funk");
        model3.setResId(R.drawable.updown_funk_img);
        musics.add(model3);

        MusicModel model4 = new MusicModel();
        model4.setAlbum("You Are Beautiful");
        model4.setSinger("James Blunt");
        model4.setName("Bigger");
        model4.setResId(R.drawable.yrbf_img);
        musics.add(model4);
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
                Intent intent = new Intent(this, MusicList.class);
                intent.putExtra("isPlaying",isPlaying);
                intent.putExtra("playIndex",playIndex);
                intent.putExtra("isFirstPlaying",isFirstPlaying);
                intent.putExtra("time",time);
                startActivityForResult(intent,0);
                break;
            case R.id.main_activity_title_me:
                tvMusicLibrary.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMyMusic.setTextColor(getResources().getColor(R.color.textdefualtcolor));
                tvMe.setTextColor(getResources().getColor(R.color.orangecolor));
                break;
            case R.id.main_activity_bottom_bar_iv:
                Intent in = new Intent(this, MusicPlay.class);
                in.putExtra("isPlaying",isPlaying);
                in.putExtra("playIndex",playIndex);
                in.putExtra("isFirstPlaying",isFirstPlaying);
                in.putExtra("time",time);
                startActivityForResult(in,0);
                break;
            case R.id.main_activity_bottom_bar_play_btn:
                break;
            case R.id.main_activity_bottom_bar_next_btn:
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
        super.onActivityResult(requestCode, resultCode, data);
        isPlaying = data.getBooleanExtra("isPlaying",false);
        playIndex = data.getIntExtra("playIndex",0);
        isFirstPlaying = data.getBooleanExtra("isFirstPlaying",true);
        time = data.getIntExtra("time",time);
        setPlayingStatus();
    }

    //设置状态
    void setPlayingStatus(){
        if (isPlaying){
            ivPlay.setImageResource(R.drawable.h_nowplaying_bar_pause_n);
        }else{
            ivPlay.setImageResource(R.drawable.h_nowplaying_bar_play_n);
        }

        MusicModel model = musics.get(playIndex);
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
                if (isPlaying){
                    play(ConstUtil.STATE_PLAY,false);
                }else{
                    play(ConstUtil.STATE_PAUSE,false);
                }
                break;
            case R.id.main_activity_bottom_bar_next_btn:
                setOnNext(event);
                play(0,true);
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
            ivNext.setImageResource(R.drawable.h_nowplaying_bar_next_n);
            isPlaying = true;
            playIndex = (playIndex +1 )%MUSIC_NUM;
            setPlayingStatus();

        }
    }
    //playing
    private void setOnPlaying(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (isPlaying){
                ivPlay.setImageResource(R.drawable.h_nowplaying_bar_pause_p);
            }else{
                ivPlay.setImageResource(R.drawable.h_nowplaying_bar_play_p);
            }
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            if (isPlaying){
                ivPlay.setImageResource(R.drawable.h_nowplaying_bar_play_n);
            }else {
                ivPlay.setImageResource(R.drawable.h_nowplaying_bar_pause_n);

            }
            isPlaying  = !isPlaying;
        }
    }

    /**
     * 设置播放
     * @param state
     * @param isNext
     */
    private void play(int state,boolean isNext){
    if (isFirstPlaying || isNext){
        playMusic(paths[playIndex]);
        isFirstPlaying = false;
    }else{
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
}
