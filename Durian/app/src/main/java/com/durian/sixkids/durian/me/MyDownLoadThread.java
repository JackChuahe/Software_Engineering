package com.durian.sixkids.durian.me;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by yu on 2016/5/4.
 */
public class MyDownLoadThread extends Thread implements View.OnClickListener
{

    private RoundProgressBar pBar;
    private TextView btn;
    private TextView su;
    private int index = 0;
    private boolean isStart = false;

    private Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            String d=new String();
            Random ra=new Random();
            if(index<=100) {

                if(index<100) {
                    if(index%8==0) {
                        int l = ra.nextInt(100) + 100;
                        int r = ra.nextInt(10);
                        d = l + "." + r + "KB";
                        su.setText(d);
                    }
                    pBar.setProgress(index);
                }else{
                    su.setText("0B");
                    pBar.setProgress(index);
                    btn.setText("下载完成");
                }
            }
        }

    };

    public MyDownLoadThread(RoundProgressBar pBar, TextView btn,TextView su)
    {
        this.pBar = pBar;
        this.btn = btn;
        this.su=su;
    }

    @Override
    public void onClick(View v)
    {
        if (!isStart)
        {
            isStart = true;
            btn.setText("开始下载");
            pBar.setMax(100);
            this.start();
        }

    }

    @Override
    public void run()
    {
        while (index < 100)
        {
            index++;
            if(index<=100) {
                handler.sendEmptyMessage(0);
            }
            try
            {
                sleep(100);
            }
            catch (InterruptedException e)
            {
// TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
}
