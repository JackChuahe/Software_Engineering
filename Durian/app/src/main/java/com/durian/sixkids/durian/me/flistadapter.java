package com.durian.sixkids.durian.me;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.durian.sixkids.durian.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yu on 2016/5/4.
 */
public class flistadapter extends BaseAdapter {
    private ArrayList<HashMap<String,Object>>list;
    private LayoutInflater mInflater;
    private Context context;
    private class Holder{
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        com.durian.sixkids.durian.me.RoundProgressBar bar;
    }

    public flistadapter(Context context,ArrayList<HashMap<String,Object>>list){
        this.context=context;
        this.list=list;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return null==list?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(null==convertView){
            holder=new Holder();
            convertView=mInflater.inflate(R.layout.downitemlayout,null);

            holder.text1=(TextView)convertView.findViewById(R.id.ditext1);
            holder.text2=(TextView)convertView.findViewById(R.id.ditext2);
            holder.text3=(TextView)convertView.findViewById(R.id.ditext4);
            holder.text4=(TextView)convertView.findViewById(R.id.ditext6);
            holder.bar=(com.durian.sixkids.durian.me.RoundProgressBar)convertView.findViewById(R.id.roundProgressBar1);

            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }

        if(null!=list&&!list.isEmpty()){

            holder.text1.setText(list.get(position).get("text1").toString());
            holder.text2.setText(list.get(position).get("text2").toString());
            holder.text3.setText(list.get(position).get("text3").toString());
            holder.text4.setText(list.get(position).get("text4").toString());

            holder.bar.setCricleColor(Color.RED);
            holder.bar.setMax(100);
            holder.bar.setCricleProgressColor(Color.GREEN);

            holder.bar.setTextColor(R.color.org);
            holder.bar.setProgress(0);

            MyDownLoadThread down = new MyDownLoadThread(holder.bar,
                    holder.text4,holder.text2);
            down.setDaemon(true);

            holder.bar.setOnClickListener(down);


        }


        return convertView;
    }
}
