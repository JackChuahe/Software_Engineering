package com.durian.sixkids.durian.me;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.util.PlayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.durian.sixkids.durian.R.color.btn_down;


/**
 * Created by JackCai on 2016/5/2.
 */
public class ScanLocal extends AppCompatActivity implements View.OnClickListener{
    private TextView songNumber;
    private TextView startText;
    private TextView Path;
    private TextView pathTitle;
    private ImageView image;
    private Button btn;
    private TextView left;
    private TextView Right;
    private RelativeLayout screturn;
    private boolean flag=false;
    private Animation animation;
    private List<String> songPaths;
    LinearInterpolator line=new LinearInterpolator();
    private Runnable scanPath=new Runnable(){
        int i=0;

        @Override
        public void run() {
            // TODO Auto-generated method stub
             i=i%(songPaths.size());
            if(i<songPaths.size()){
                Message msg=new Message();
                msg.what=1;
                msg.obj=songPaths.get(i);

                handler.sendMessage(msg);
            }
            i++;
        }

    };


    private Runnable songN =new Runnable(){
        int i=0;
        int falg=1;
        public void setI(int m){
            i=m;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if(falg==0){
                i=0;
                falg=1;
            }

            Message msg=new Message();
            msg.what=2;
            msg.obj=i;


            if(i<=15){
                handler.sendMessage(msg);
            }else{
                falg=1-falg;
                handler.removeCallbacks(scanPath);
                handler.removeCallbacks(songN);
            }
            i++;


        }

    };


    private Handler handler=new Handler(){
       int k=2;
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    Path.setText((String)msg.obj);
                    handler.postDelayed(scanPath, 5);
                    break;
                case 2:
                    int k=((Integer)msg.obj);
                    left.setText("已经扫描到");
                    songNumber.setText(String.valueOf(k));
                    handler.postDelayed(songN,300);
                    if(k==15){
                        left.setText("扫描到");
                        songNumber.setText(String.valueOf(k));
                        Path.setVisibility(View.INVISIBLE);
                        pathTitle.setVisibility(View.INVISIBLE);
                        btn.setText("开始扫描");
                        flag=false;
                        image.clearAnimation();
                    }
                    break;
            }

        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scanmusic);
        initWindow();
        getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        PlayService.ACTIVITY = PlayService.OTHER;

        Random ran=new Random();
        songPaths=new ArrayList<String>();
        String a="/mnt/sdcard/";
        for(int i=0;i<30;i++){
            for(int j=0;j<ran.nextInt(7)+10;j++){
                char h;
                int m=ran.nextInt(2);
                if(m==0){
                    h=(char)(ran.nextInt(10)+(int)'0');
                }else{
                    h=(char)(ran.nextInt(26)+(int)'a');
                }
                a=a+String.valueOf(h);
            }
            songPaths.add(a);
            a="/mnt/sdcard/";
        }



        Right=(TextView)this.findViewById(R.id.textright);
        left=(TextView)this.findViewById(R.id.textleft);
        songNumber=(TextView)this.findViewById(R.id.songnumber);
        songNumber.setVisibility(View.INVISIBLE);
        left.setVisibility(View.INVISIBLE);
        Right.setVisibility(View.INVISIBLE);
        startText=(TextView)this.findViewById(R.id.startscanText);

        Path=(TextView)this.findViewById(R.id.isplay);
        Path.setVisibility(View.INVISIBLE);

        pathTitle=(TextView)this.findViewById(R.id.songpath);
        pathTitle.setVisibility(View.INVISIBLE);

        screturn=(RelativeLayout)this.findViewById(R.id.screturn);
        image=(ImageView)this.findViewById(R.id.scbak1);
        btn=(Button)this.findViewById(R.id.btn);

        screturn.setOnClickListener(this);
        btn.setOnClickListener(this);
       // btn.setOnTouchListener(this);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.btn:
                //动画运转
                if(flag==false){
                    btn.setText("停止扫描");

                    animation= AnimationUtils.loadAnimation(this, R.anim.jiazai);
                    animation.setInterpolator(line);
                    image.startAnimation(animation);

                    //
                    left.setVisibility(View.VISIBLE);
                    Right.setVisibility(View.VISIBLE);
                    songNumber.setVisibility(View.VISIBLE);
                    Path.setVisibility(View.VISIBLE);
                    pathTitle.setVisibility(View.VISIBLE);
                    startText.setVisibility(View.INVISIBLE);

                    flag=true;
                    handler.post(scanPath);
                    handler.post(songN);
                }else{
                    flag=false;
                    btn.setText("开始扫描");
                    Path.setVisibility(View.INVISIBLE);
                    pathTitle.setVisibility(View.INVISIBLE);
                    handler.removeCallbacks(scanPath);
                    image.clearAnimation();
                    handler.removeCallbacks(songN);
                }

                break;
            case R.id.screturn:this.finish();
                break;
        }
    }
    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

}