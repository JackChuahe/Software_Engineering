package com.durian.sixkids.durian.common;

/**
 * Created by JackCai on 2016/5/2.
 */
public class MusicModel {
    private String name;
    private String singer;
    private long  time;
    private int size;
    private String album;
    private int resId;
    private boolean isColleted;
    private int headBgId;
    private int playBgId;
    private boolean isPlay;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    private String src;

    public String getLyc() {
        return lyc;
    }

    public void setLyc(String lyc) {
        this.lyc = lyc;
    }

    private String lyc;

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public int getHeadBgId() {
        return headBgId;
    }

    public void setHeadBgId(int headBgId) {
        this.headBgId = headBgId;
    }

    public int getPlayBgId() {
        return playBgId;
    }

    public void setPlayBgId(int playBgId) {
        this.playBgId = playBgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public boolean isColleted() {
        return isColleted;
    }

    public void setColleted(boolean colleted) {
        isColleted = colleted;
    }
}
