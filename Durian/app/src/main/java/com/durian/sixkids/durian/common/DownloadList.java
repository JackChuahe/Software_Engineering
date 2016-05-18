package com.durian.sixkids.durian.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackCai on 2016/5/18.
 */
public class DownloadList {
    //正在下载的音乐列表
    public static List<MusicModel> downloading = new ArrayList<MusicModel>();
    //已经下载完成的音乐列表
    public static List<MusicModel> downloaded = new ArrayList<MusicModel>();
}
