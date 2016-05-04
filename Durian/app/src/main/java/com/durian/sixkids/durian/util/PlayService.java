package com.durian.sixkids.durian.util;

/**
 * Created by JackCai on 2016/5/4.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import java.io.IOException;
import java.util.List;

/**
 * Created by pc on 2016/4/19.
 */
public class PlayService extends android.app.Service {
    private MediaPlayer mediaPlayer;
    private String path;

    private int current;
    MusicServiceRecevier receiver=new MusicServiceRecevier();


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         path =intent.getStringExtra("path");
         play(path);


        return super.onStartCommand(intent, flags, startId);
    }
    public void play(String path){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate() {
        mediaPlayer=new MediaPlayer();

        IntentFilter filter=new IntentFilter();
        filter.addAction(ConstUtil.MUSICSERVICE_ACTION);
        registerReceiver(receiver, filter);
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    class MusicServiceRecevier extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int control=intent.getIntExtra("control",-1);
            switch (control){
                case ConstUtil.STATE_PAUSE:
                    mediaPlayer.pause();
                    break;
                case ConstUtil.STATE_PLAY:
                    mediaPlayer.start();
                default:
                    break;
            }
        }
    }

}

