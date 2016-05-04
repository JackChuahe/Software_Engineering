package com.durian.sixkids.durian.search_recomm;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.SetStatusBarTextColor;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by SEELE on 2016/5/4.
 */
public class MusicSearch extends AppCompatActivity {
    private LinearLayout linone,lintwo,linthree;
    private Animation myAnimation,two,three;
    private SystemBarTintManager tintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.serach);
        getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        linone= (LinearLayout) findViewById(R.id.linone);
        lintwo= (LinearLayout) findViewById(R.id.lintwo);
        linthree= (LinearLayout) findViewById(R.id.linthree);
        myAnimation = AnimationUtils.loadAnimation(this, R.anim.alphaset);
        two = AnimationUtils.loadAnimation(this, R.anim.alphasettwo);
        three = AnimationUtils.loadAnimation(this, R.anim.alphasetthree);
        linone.setAnimation(myAnimation);
        lintwo.setAnimation(two);
        linthree.setAnimation(three);
        initWindow();
        SetStatusBarTextColor.setMiuiStatusBarDarkMode(this,true);
    }

    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
