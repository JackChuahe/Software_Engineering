package com.durian.sixkids.durian.me;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.durian.sixkids.durian.R;

/**
 * Created by JackCai on 2016/5/2.
 */
public class LocalManagement extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {

    private RelativeLayout user;
    private RelativeLayout scanmusic,localmusic,allmucic,downmusic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.localmanagement_fragment);


        user=(RelativeLayout)this.findViewById(R.id.lguser);
        scanmusic=(RelativeLayout)this.findViewById(R.id.lglocalmusic);
        localmusic=(RelativeLayout)this.findViewById(R.id.lgsingmusic);
        downmusic=(RelativeLayout)this.findViewById(R.id.lgcollmusic);
        allmucic=(RelativeLayout)this.findViewById(R.id.lgallmusic);


//        user.setOnClickListener(this);
//        scanmusic.setOnClickListener(this);

        user.setOnTouchListener(this);
        scanmusic.setOnTouchListener(this);
        downmusic.setOnTouchListener(this);
        allmucic.setOnTouchListener(this);
        localmusic.setOnTouchListener(this);



    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.lguser:

                break;
            case R.id.lgsingmusic:

                break;
        }

    }

    public void setColor(int id,MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                findViewById(id).setBackgroundColor(getResources().getColor(R.color.re_down));
                break;
            case MotionEvent.ACTION_UP:
                findViewById(id).setBackgroundColor(getResources().getColor(R.color.re_up));
                if(id==R.id.lguser){
                    Intent intent1=new Intent(LocalManagement.this,MeSetting.class);
                    startActivity(intent1);
                }else if(id==R.id.lgsingmusic){
                    Intent intent2=new Intent(LocalManagement.this,ScanLocal.class);
                    startActivity(intent2);
                }
                break;
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId()==R.id.lguser||v.getId()==R.id.lgallmusic||v.getId()==R.id.lglocalmusic||v.getId()==R.id.lgsingmusic||v.getId()==R.id.lgcollmusic){
            setColor(v.getId(),event);
        }
        return true;
    }


    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
}
