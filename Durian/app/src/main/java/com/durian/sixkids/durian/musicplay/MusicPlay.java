package com.durian.sixkids.durian.musicplay;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackCai on 2016/5/2.
 */
public class MusicPlay extends AppCompatActivity{
    private ViewPager viewPager;
    private MyViewPagerAdapter adapter;
    private List<View>views = new ArrayList<View>();
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
        View view = inflater.inflate(R.layout.fragment_music_play_center,null);
        views.add(view);
        adapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(adapter);

    }

    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
