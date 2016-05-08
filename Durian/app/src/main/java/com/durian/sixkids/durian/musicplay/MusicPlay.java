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
import android.widget.TextView;
import android.widget.Toast;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.MusicModel;
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
    private ViewPager viewPager;
    private MyViewPagerAdapter adapter;
    private List<View>views = new ArrayList<View>();
    private final static int MAX_NUM_WAVES = 20;
    private final static int [] anims = {R.drawable.anim_wave_1,R.drawable.anim_wave_2,R.drawable.anim_wave_3,R.drawable.anim_wave_4,R.drawable.anim_wave_5,R.drawable.anim_wave_6,R.drawable.anim_wave_7,R.drawable.anim_wave_8,R.drawable.anim_wave_9,R.drawable.anim_wave_10};
    private List<AnimationDrawable> animationDrawables = new ArrayList<AnimationDrawable>();
    private List<MusicModel> musics = new ArrayList<MusicModel>();
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

    private boolean isPlaying = true;
    private int playIndex = 0;
    private ImageView ivCenterImg;
    private int nowTime = 25;
    private int time = 0;
    private final  static  String [] paths = {"/storage/sdcard0/zcw/Uptown Funk.mp3","/storage/sdcard0/zcw/TiK ToK (Live).mp3","/storage/sdcard0/zcw/You Are Beautiful.mp3"};

    private final static int  MUSIC_NUM = 3;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Toast.makeText(getApplicationContext(),"da",Toast.LENGTH_SHORT).show();
            android.view.ViewGroup.LayoutParams lp = lyProgress.getLayoutParams();
            lp.width = nowTime;
            lyProgress.setLayoutParams(lp);
            tvNowTime.setText("00:"+ time);

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

        lyProgress = (LinearLayout)findViewById(R.id.music_play_progress);

        initWindow();
        initFragment();

        initModels();
        getData();
        setPlayStatus();
        initTouchListener();
        initThread();
    }

    private void initThread(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                    if (isPlaying){
                        nowTime += 8;
                        ++time;
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

//    /**
//     * 启动线程
//     */
//    private void initThread(){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//
//                    if (isPlaying){
//                        nowTime += 10;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                               // lyProgress.setLayoutParams(new LinearLayout.LayoutParams(nowTime, 1));
//                            }
//                        });
//                        try {
//                            Thread.sleep(400);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }


    //设置背景
    private void setBg(){
        rlBg.setBackgroundResource(musics.get(playIndex).getPlayBgId());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f, 1.0f);
        alphaAnimation.setDuration(2000);
        rlBg.setAnimation(alphaAnimation);

    }

    private void initTouchListener(){
        ivPlaying.setOnTouchListener(this);
        ivPreBtn.setOnTouchListener(this);
        ivNext.setOnTouchListener(this);
    }
    private void getData(){
        Intent intent = getIntent();
        isPlaying = intent.getBooleanExtra("isPlaying",false);
        playIndex = intent.getIntExtra("playIndex",playIndex);
        isFirstPlaying = intent.getBooleanExtra("isFirstPlaying",true);
        time = intent.getIntExtra("time",0);
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
        viewPager.setCurrentItem(1);
    }

    /**
     * 启动wave动画效果
     */
    private void waveAnimation(){
        if (isPlaying){
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
                if (isPlaying){
                    play(ConstUtil.STATE_PLAY,false);
                }else{
                    play(ConstUtil.STATE_PAUSE,false);
                }
                break;
            case R.id.music_pre_btn:
                touchedPreBtn(event);
                play(0,true);
                break;
            case R.id.music_next_btn:
                touchedNextBtn(event);
                play(0,true);
                break;
            case R.id.music_play_collect:

                break;
        }
        return true;
    }

    /**
     * 播放 按钮事件处理
     */
    private  void touchedPlayBtn(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isPlaying) {
                ivPlaying.setImageResource(R.drawable.nowplaying_pause_p);
            } else {
                ivPlaying.setImageResource(R.drawable.nowplaying_play_p);
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            if (isPlaying) {
                ivPlaying.setImageResource(R.drawable.nowplaying_play_n);
            } else {
                ivPlaying.setImageResource(R.drawable.nowplaying_pause_n);
            }
            isPlaying = !isPlaying;
            waveAnimation(); //设置波浪效果
        }
    }



    private void touchedPreBtn(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ivPreBtn.setImageResource(R.drawable.nowplaying_prev_p);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            ivPreBtn.setImageResource(R.drawable.nowplaying_prev_n);
            if (playIndex == 0)playIndex = MUSIC_NUM - 1;
            else{
                playIndex = (playIndex-1)%MUSIC_NUM;
            }

            isPlaying = true;
            setPlayStatus();
        }
    }

    private void touchedNextBtn(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ivNext.setImageResource(R.drawable.nowplaying_next_p);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            ivNext.setImageResource(R.drawable.nowplaying_next_n);
            playIndex = (playIndex+1)%MUSIC_NUM;
            isPlaying = true;
            setPlayStatus();
        }
    }

    private void touchedCollectBtn(MotionEvent event){

    }
    /**
     * 初始化模型
     */
    private void initModels(){
        MusicModel model3 = new MusicModel();
        model3.setAlbum("Uptown Funk");
        model3.setSinger("Mark Ronson");
        model3.setName("Uptown Funk");
        model3.setPlayBgId(R.drawable.upfun_play_bg);
        model3.setResId(R.drawable.updown_funk_img);
        musics.add(model3);

        MusicModel model2 = new MusicModel();
        model2.setAlbum("Promo Only Mainstream Radio October");
        model2.setSinger("Ke.Ha");
        model2.setName("Tik Tok");
        model2.setPlayBgId(R.drawable.tktk_play_bg);
        model2.setResId(R.drawable.tktk_img);
        musics.add(model2);



        MusicModel model4 = new MusicModel();
        model4.setAlbum("You Are Beautiful");
        model4.setSinger("James Blunt");
        model4.setName("Bigger");
        model4.setResId(R.drawable.yrbf_img);
        model4.setPlayBgId(R.drawable.yrbtf_play_bg);
        musics.add(model4);
    }

    /**
     * 设置播放
     */
    private void setPlayStatus(){
        MusicModel model = musics.get(playIndex);
        if (isPlaying){
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
        Intent intent = new Intent();
        intent.putExtra("isPlaying",isPlaying);
        intent.putExtra("playIndex",playIndex);
        intent.putExtra("isFirstPlaying",isFirstPlaying);
        intent.putExtra("time",time);
        setResult(1,intent);
        finish();
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
            nowTime = 25;
            time = 0;
            tvNowTime.setText("00:00");
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
