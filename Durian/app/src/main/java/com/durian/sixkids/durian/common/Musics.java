package com.durian.sixkids.durian.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackCai on 2016/5/17.
 */
public class Musics {
    /**
     * 音乐列表
     */
    public static List<MusicModel> musicModels = new ArrayList<MusicModel>();

    public static boolean isPlaying = false;
    public static int time = 0;
    public static int playIndex = 0;
    public static boolean isFirstPlaying = true;
}
