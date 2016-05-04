package com.durian.sixkids.durian.musicplay;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.MyViewPagerAdapter;

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

    private ImageView ivBack;
    private TextView tvSongName;
    private TextView tvAlbum;
    private ImageView ivCollect;
    private LinearLayout lyProgress;
    private ImageView ivPreBtn;
    private ImageView ivPlaying;
    private ImageView ivNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.music_play);
        getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager = (ViewPager)findViewById(R.id.music_play_view_pager);

        initWindow();
        initFragment();
    }

    private void initFragment(){

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View viewCenter = inflater.inflate(R.layout.fragment_music_play_center,null);
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

        //启动动画
        for (AnimationDrawable animationDrawable : animationDrawables){
            animationDrawable.start();
        }
        adapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

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
        }
        return false;
    }

    /**
     * 点击播放
     */
    private void setOnPlaying(){
        for(AnimationDrawable animationDrawable : animationDrawables){
            animationDrawable.stop();
        }
    }

    /**
     * 点击暂停
     */
    private void setOnPausing(){
        for(AnimationDrawable animationDrawable : animationDrawables){
            animationDrawable.start();
        }
    }

}
