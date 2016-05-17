package com.durian.sixkids.durian.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackCai on 2016/5/17.
 */
public class ShareMusics {
    public static List<MusicModel> shareMusics = new ArrayList<MusicModel>();

    /**
     * 通知服务器
     * @return
     */
    public static boolean notifyWebService(MusicModel model){
        shareMusics.add(model);
        //连接网络，访问服务器
        return true;
    }

    /**
     * 取消分享
     * @param model
     * @return
     */
    public static boolean cancelShare(MusicModel model){
        //连接网络，访问服务器
        return true;
    }
}
