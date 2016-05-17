package com.durian.sixkids.durian.me;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.SetStatusBarTextColor;
import com.durian.sixkids.durian.util.PlayService;


/**
 * Created by JackCai on 2016/5/2.
 */
public class MeSetting extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout umreturn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.usermanager);
        initWindow();
        getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PlayService.ACTIVITY = PlayService.OTHER;

        umreturn = (RelativeLayout) this.findViewById(R.id.umre);
        SetStatusBarTextColor.setMiuiStatusBarDarkMode(this,true);
       umreturn.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.umre) {
          //  Toast.makeText(this,"de",Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}

