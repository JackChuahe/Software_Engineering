package com.durian.sixkids.durian.me;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.DownloadList;
import com.durian.sixkids.durian.common.MusicModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yu on 2016/5/4.
 */
public class FdAdapter extends BaseAdapter{
    private LayoutInflater mInflater;

    public FdAdapter(Context context){
          mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return null== DownloadList.downloaded?0:DownloadList.downloaded.size();
    }

    @Override
    public Object getItem(int position) {
        return DownloadList.downloaded.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private  class Holder{
        TextView text1;
        TextView text2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            holder=new Holder();

            convertView=mInflater.inflate(R.layout.lateritemlayout,null);

            holder.text1=(TextView)convertView.findViewById(R.id.laditext1);
            holder.text2=(TextView)convertView.findViewById(R.id.laditext2);

            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();

        }

        if(null!=DownloadList.downloaded&&!DownloadList.downloaded.isEmpty()){
            holder.text1.setText(DownloadList.downloaded.get(position).getName());
            holder.text2.setText(DownloadList.downloaded.get(position).getSize()+"MB");
        }

        return convertView;
    }
}
