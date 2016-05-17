package com.durian.sixkids.durian.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by JackCai on 2016/5/17.
 */
public class NetWorkOp {

    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_CMWAP = 2;
    public static final int NETTYPE_CMNET = 3;
    public static final int NETTYPE_NO = -1;

    /**
     * 获取网络类型 -
     * @param context
     * @return -1 没有网络  1 WIFI网络  2：wap网络3：net网络
     */

    public static int GetNetype(Context context)
    {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo==null)
        {
            return netType;
        }
        int nType = networkInfo.getType();
        if(nType==ConnectivityManager.TYPE_MOBILE)
        {
            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet"))
            {
                netType = 3;
            }
            else
            {
                netType = 2;
            }
        }
        else if(nType==ConnectivityManager.TYPE_WIFI)
        {
            netType = 1;
        }
        return netType;
    }
}
