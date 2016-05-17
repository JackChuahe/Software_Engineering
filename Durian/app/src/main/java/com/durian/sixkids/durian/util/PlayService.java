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

import com.durian.sixkids.durian.MusicMainActivity;
import com.durian.sixkids.durian.common.Musics;
import com.durian.sixkids.durian.musicplay.MusicList;
import com.durian.sixkids.durian.musicplay.MusicPlay;

import java.io.IOException;
import java.util.List;

/**
 * Created by pc on 2016/4/19.
 */
public class PlayService extends android.app.Service {
    private MediaPlayer mediaPlayer;
    private String path;

    public static MusicMainActivity musicMainActivity;
    public static MusicList musicList;
    public static MusicPlay musicPlay;

    public final static int MAIN_ACTIVITY = 0;
    public final static int MUSIC_LIST = 1;
    public final static int MUSIC_PLAY= 2;
    public final static int OTHER= 2;
    public static int ACTIVITY = MAIN_ACTIVITY;
    public static PlayService service = null;

    private  int current;
    MusicServiceRecevier receiver=new MusicServiceRecevier();


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         path = intent.getStringExtra("path");
         play(path);

        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 当前歌曲播放完成
     */
    public void songCompleted(){
        switch (ACTIVITY){
            case MAIN_ACTIVITY:
                musicMainActivity.playNext();
                break;
            case MUSIC_LIST:
                musicList.playNext();
                break;
            case MUSIC_PLAY:
                musicPlay.playNext();
                break;
            default:
                Musics.time = 0;
                Musics.playIndex++;
                Musics.isPlaying = true;
                Musics.isFirstPlaying = false;
                play( Musics.musicModels.get(Musics.playIndex).getSrc());
                break;

        }
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

    /**
     * 获取总长度
     * @return
     */
    public int getDuration(){
        return mediaPlayer.getDuration();
    }
    /**
     * 获取当前数据
     * @return
     */
    public int getCurrentDuration(){
        return mediaPlayer.getCurrentPosition();
    }
    @Override
    public void onCreate() {
        mediaPlayer=new MediaPlayer();

        IntentFilter filter=new IntentFilter();
        filter.addAction(ConstUtil.MUSICSERVICE_ACTION);
        registerReceiver(receiver, filter);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                songCompleted();
            }
        });
        service = this;
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

