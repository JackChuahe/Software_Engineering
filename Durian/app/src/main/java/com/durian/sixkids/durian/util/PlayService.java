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
import java.util.Random;

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

    /**
     * 播放模式  顺序循环 循环 单曲
     */
    public final static int PLAY_TYPE_ORDER = 0;
    public final static int PLAY_TYPE_RANDOM = 1;
    public final static int PLAY_TYPE_SINGLE = 2;
    public final static int PLAY_TYPE_NUM = 3;
    public static int PLAYTYPE = PLAY_TYPE_ORDER;

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
     * 设置播放模式
     */
    public static void setPlayType(){
        PLAYTYPE = (PLAYTYPE+1)%PLAY_TYPE_NUM;
    }
    /**
     * 当前歌曲播放完成
     */
    public void songCompleted(){
        switch (ACTIVITY){
            case MAIN_ACTIVITY:
                playNext();
                play( Musics.musicModels.get(Musics.playIndex).getSrc());
                musicMainActivity.playNext();
                break;
            case MUSIC_LIST:
                playNext();
                play( Musics.musicModels.get(Musics.playIndex).getSrc());
                musicList.playNext();
                break;
            case MUSIC_PLAY:
                playNext();
                play( Musics.musicModels.get(Musics.playIndex).getSrc());
                musicPlay.playNext();
                break;
            default:
                playNext();
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

    /**
     * 播放下一首
     */
    public static void playNext(){
        switch (PLAYTYPE){
            case PLAY_TYPE_ORDER:
                Musics.playIndex = (Musics.playIndex+1)%Musics.musicModels.size();
                break;
            case PLAY_TYPE_RANDOM:
                Musics.playIndex = new Random(System.currentTimeMillis()).nextInt(Musics.musicModels.size());
                break;
            case PLAY_TYPE_SINGLE:
                break;
        }
        setState();
    }
    public static void setState(){
        Musics.time = 0;
        Musics.isPlaying = true;
        Musics.isFirstPlaying = false;
    }

    public static void playPre(){
        switch (PLAYTYPE){
            case PLAY_TYPE_ORDER:
                if (Musics.playIndex == 0)Musics.playIndex = Musics.musicModels.size()-1;
                else{
                    Musics.playIndex--;
                }
                break;
            case PLAY_TYPE_RANDOM:
                Musics.playIndex = new Random(System.currentTimeMillis()).nextInt(Musics.musicModels.size());
                break;
            case PLAY_TYPE_SINGLE:
                break;
        }
        setState();
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

