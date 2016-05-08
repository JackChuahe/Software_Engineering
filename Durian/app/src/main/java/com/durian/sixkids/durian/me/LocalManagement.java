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

import com.durian.sixkids.durian.MusicMainActivity;
import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.musicplay.MusicList;
import com.durian.sixkids.durian.musicplay.MusicPlay;
import com.durian.sixkids.durian.search_recomm.MusicLibrary;

/**
 * Created by JackCai on 2016/5/2.
 */
public class LocalManagement   implements View.OnClickListener,View.OnTouchListener {

    private RelativeLayout user;
    private RelativeLayout scanmusic,localmusic,allmucic,downmusic;
    private MusicMainActivity activity;
    private View view;
    public LocalManagement(MusicMainActivity activity , View view){
        this.activity = activity;
        this.view = view;
        intit();
    }

    private void intit() {
        user=(RelativeLayout)view.findViewById(R.id.lguser);
        scanmusic=(RelativeLayout)view.findViewById(R.id.lglocalmusic);
        localmusic=(RelativeLayout)view.findViewById(R.id.lgsingmusic);
        downmusic=(RelativeLayout)view.findViewById(R.id.lgcollmusic);
        allmucic=(RelativeLayout)view.findViewById(R.id.lgallmusic);


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
                view.findViewById(id).setBackgroundColor(activity.getResources().getColor(R.color.re_down));
                break;
            case MotionEvent.ACTION_UP:
                view.findViewById(id).setBackgroundColor(activity.getResources().getColor(R.color.re_up));
                if(id==R.id.lguser){
                    Intent intent1=new Intent(activity,MeSetting.class);
                    activity.startActivity(intent1);
                }else if(id==R.id.lgsingmusic){
                    Intent intent2=new Intent(activity,ScanLocal.class);
                    activity.startActivity(intent2);
                }else if(id==R.id.lgcollmusic){
                    Intent intent3=new Intent(activity,DownLoadLookUp.class);
                    activity.startActivity(intent3);
                }else if(id == R.id.lglocalmusic){
                    Intent intent = new Intent(activity, MusicList.class);
                    intent.putExtra("isPlaying",activity.isPlaying());
                    intent.putExtra("playIndex",activity.getPlayIndex());
                    intent.putExtra("isFirstPlaying",activity.isFirstPlaying());
                    intent.putExtra("time",activity.getTime());
                    activity.startActivityForResult(intent,0);
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
