package com.durian.sixkids.durian.musicplay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.MusicModel;
import com.durian.sixkids.durian.common.Musics;
import com.durian.sixkids.durian.common.MyViewPagerAdapter;
import com.durian.sixkids.durian.util.ConstUtil;
import com.durian.sixkids.durian.util.PlayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by JackCai on 2016/5/2.
 */
public class MusicPlay extends AppCompatActivity implements View.OnTouchListener{
    private final static int PAGE_WAVE = 0;
    private final static int PAGE_CENTER = 1;
    private final static int PAGE_LYC = 2;


    private SeekBar seekBar;
    private ViewPager viewPager;
    private MyViewPagerAdapter adapter;
    private List<View>views = new ArrayList<View>();
    private final static int MAX_NUM_WAVES = 20;
    private final static int [] anims = {R.drawable.anim_wave_1,R.drawable.anim_wave_2,R.drawable.anim_wave_3,R.drawable.anim_wave_4,R.drawable.anim_wave_5,R.drawable.anim_wave_6,R.drawable.anim_wave_7,R.drawable.anim_wave_8,R.drawable.anim_wave_9,R.drawable.anim_wave_10};
    private List<AnimationDrawable> animationDrawables = new ArrayList<AnimationDrawable>();
    private boolean isFirstPlaying = true;

    private ImageView ivBack;
    private ImageView ivCollect;
    private ImageView ivPreBtn;
    private ImageView ivPlaying;
    private ImageView ivNext;

    private TextView tvSongName;
    private TextView tvAlbum;
    private RelativeLayout rlBg;

    private LinearLayout lyProgress;
    private TextView tvNowTime;
    private TextView tvEndTime;

    private ImageView ivPlayType;
    private ImageView ivDownload;
    private ImageView ivShare;

    private ImageView ivCenterImg;
//    private int nowTime = 25;




    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Toast.makeText(getApplicationContext(),"da",Toast.LENGTH_SHORT).show();
          /*  android.view.ViewGroup.LayoutParams lp = lyProgress.getLayoutParams();
            lp.width = nowTime;
            lyProgress.setLayoutParams(lp);
            tvNowTime.setText("00:"+ Musics.time);*/
            if (PlayService.service != null) {
                seekBar.setMax(PlayService.service.getDuration());
                seekBar.setProgress(PlayService.service.getCurrentDuration());
                int min = PlayService.service.getCurrentDuration()/60000;
                int second = (PlayService.service.getCurrentDuration() / 1000)%60;
                tvNowTime.setText(min+":"+second);
                 min = PlayService.service.getDuration()/60000;
                 second = (PlayService.service.getDuration() / 1000)%60;
                tvEndTime.setText(min+":"+second);
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.music_play);
        getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager = (ViewPager)findViewById(R.id.music_play_view_pager);

        ivBack = (ImageView)findViewById(R.id.back);
        ivCollect = (ImageView)findViewById(R.id.music_play_collect);
        ivPreBtn = (ImageView)findViewById(R.id.music_pre_btn);
        ivPlaying = (ImageView)findViewById(R.id.music_play_btn);
        ivNext = (ImageView)findViewById(R.id.music_next_btn);
        tvAlbum = (TextView)findViewById(R.id.music_play_song_detail);
        tvSongName = (TextView)findViewById(R.id.music_play_songname);

        rlBg = (RelativeLayout)findViewById(R.id.music_play_bg);
        tvNowTime = (TextView)findViewById(R.id.music_play_now_time);
        tvEndTime = (TextView)findViewById(R.id.music_play_end_time);

        seekBar = (SeekBar)findViewById(R.id.seekBar);

        ivPlayType = (ImageView)findViewById(R.id.play_type);
        ivDownload = (ImageView)findViewById(R.id.play_download);
        ivShare = (ImageView)findViewById(R.id.play_share);

       // lyProgress = (LinearLayout)findViewById(R.id.music_play_progress);

        initWindow();
        initFragment();

        getData();
        setPlayStatus();
        initTouchListener();
        initThread();

        if (PlayService.musicPlay == null){
            PlayService.musicPlay = this;
        }
        PlayService.ACTIVITY = PlayService.MUSIC_PLAY;
    }

    private void initThread(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                    if (Musics.isPlaying){
                        handler.sendEmptyMessage(0);
                        try {
                            Thread.sleep(900);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        };

        new Thread(runnable).start();
    }

    //设置背景
    private void setBg(){
        rlBg.setBackgroundResource(Musics.musicModels.get(Musics.playIndex).getPlayBgId());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f, 1.0f);
        alphaAnimation.setDuration(2000);
        rlBg.setAnimation(alphaAnimation);

    }

    private void initTouchListener(){
        ivPlaying.setOnTouchListener(this);
        ivPreBtn.setOnTouchListener(this);
        ivNext.setOnTouchListener(this);
        ivShare.setOnTouchListener(this);
        ivDownload.setOnTouchListener(this);
        ivPlayType.setOnTouchListener(this);

    }
    private void getData(){
        //Intent intent = getIntent();
    }

    private void initFragment(){
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View viewCenter = inflater.inflate(R.layout.fragment_music_play_center,null);
        ivCenterImg = (ImageView) viewCenter.findViewById(R.id.fragment_music_play_album);

        View viewLyc = inflater.inflate(R.layout.music_lyc_layout,null);

        View viewWave = inflater.inflate(R.layout.music_play_wave,null);
        LinearLayout ly = (LinearLayout)viewWave.findViewById(R.id.wave_ly);

        int i = 0;
        Random random = new Random(System.currentTimeMillis());
        int oldIndex = -1;
        int ooIndex = -1;
        int curentIndex = -1;
        for(; i < MAX_NUM_WAVES;++i){
            View waveitem = inflater.inflate(R.layout.wave_item,null);
            ImageView imageView = (ImageView) waveitem.findViewById(R.id.wave_img_item);
            while(true){
                curentIndex = random.nextInt(anims.length);
                if(curentIndex != oldIndex && curentIndex != ooIndex){
                    ooIndex = oldIndex;
                    oldIndex = curentIndex;
                    break;
                }
            }

            imageView.setImageResource(anims[curentIndex]);
            AnimationDrawable animation = (AnimationDrawable)imageView.getDrawable();
            animationDrawables.add(animation);
            ly.addView(waveitem);
        }
        views.add(viewWave);
        views.add(viewCenter);
        views.add(viewLyc);

        adapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(PAGE_CENTER);
    }

    /**
     * 启动wave动画效果
     */
    private void waveAnimation(){
        if (Musics.isPlaying){
            //启动动画
            for (AnimationDrawable animationDrawable : animationDrawables){
                animationDrawable.start();
            }
        }else{
            for (AnimationDrawable animationDrawable : animationDrawables){
                animationDrawable.stop();
            }
        }
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
            case R.id.music_play_btn:
                touchedPlayBtn(event);
                break;
            case R.id.music_pre_btn:
                touchedPreBtn(event);

                break;
            case R.id.music_next_btn:
                touchedNextBtn(event);
                break;
            case R.id.music_play_collect:

                break;
            case R.id.play_type:
                touchedPlayType(event);
                break;
        }
        return true;
    }

    /**
     * 设置播放模式
     * @param event
     */
    private void touchedPlayType(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        }else if(event.getAction() == MotionEvent.ACTION_UP){

        }
    }

    /**
     * 播放 按钮事件处理
     */
    private  void touchedPlayBtn(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (Musics.isPlaying) {
                ivPlaying.setImageResource(R.drawable.nowplaying_pause_p);
            } else {
                ivPlaying.setImageResource(R.drawable.nowplaying_play_p);
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            if (Musics.isPlaying) {
                ivPlaying.setImageResource(R.drawable.nowplaying_play_n);
            } else {
                ivPlaying.setImageResource(R.drawable.nowplaying_pause_n);
            }
            Musics.isPlaying = !Musics.isPlaying;
            waveAnimation(); //设置波浪效果
            //设置播放状态
            if (Musics.isPlaying){
                play(ConstUtil.STATE_PLAY,false);
            }else{
                play(ConstUtil.STATE_PAUSE,false);
            }

        }
    }



    private void touchedPreBtn(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ivPreBtn.setImageResource(R.drawable.nowplaying_prev_p);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            ivPreBtn.setImageResource(R.drawable.nowplaying_prev_n);

            PlayService.playPre();
            playMusic(Musics.musicModels.get(Musics.playIndex).getSrc());
            setPlayStatus();
            //play(0,true);  //播放上一曲
        }
    }

    private void touchedNextBtn(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ivNext.setImageResource(R.drawable.nowplaying_next_p);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            play(0,true);
            playNext();
        }
    }

    /**
     * 播放下一曲
     */
    public void playNext(){
        ivNext.setImageResource(R.drawable.nowplaying_next_n);
        setPlayStatus();
        //play(0,true);//播放下一曲
    }

    /**
     * 点击了搜藏按钮
     *
     * @param event
     */
    private void touchedCollectBtn(MotionEvent event){

    }


    /**
     * 设置播放
     */
    private void setPlayStatus(){
        MusicModel model = Musics.musicModels.get(Musics.playIndex);
        if (Musics.isPlaying){
            ivPlaying.setImageResource(R.drawable.nowplaying_pause_n);
        }else{
            ivPlaying.setImageResource(R.drawable.nowplaying_play_n);
        }

        setBg();  //设置背景

        ivCenterImg.setImageResource(model.getResId());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(1000);
        ivCenterImg.setAnimation(alphaAnimation);

        tvSongName.setText(model.getName());
        tvAlbum.setText(model.getSinger()+" | "+model.getAlbum());
        waveAnimation();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            keyBack();
        }
        return true;
    }

    private void keyBack(){
        finish();
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
}
