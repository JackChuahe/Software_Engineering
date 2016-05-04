package com.durian.sixkids.durian.search_recomm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.durian.sixkids.durian.MusicMainActivity;
import com.durian.sixkids.durian.R;

import java.util.Timer;
import java.util.TimerTask;



public class MusicLibrary  {

    private ImageView head;
    private ProgressBar pro;
    private LinearLayout linearLayout;
    private MusicMainActivity activity;
    private View view;

    public MusicLibrary(MusicMainActivity activity,View view){
        this.activity = activity;
        this.view = view;
        initial();
    }


    private void initial() {

        head = (ImageView) view.findViewById(R.id.head);
        pro = (ProgressBar) view.findViewById(R.id.pro);
        linearLayout= (LinearLayout) view.findViewById(R.id.dian);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(activity,MusicSearch.class);
                activity.startActivity(intent);
            }
        });


        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {

               Message message=new Message();
                message.what=1;
                handler.sendMessage(message);

            }
        };
        timer.schedule(tast, 3500);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    pro.setVisibility(View.GONE);
                    head.setImageResource(R.drawable.head);
                    break;
            }

            super.handleMessage(msg);
        }
    };

}
