package com.durian.sixkids.durian.me;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.durian.sixkids.durian.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yu on 2016/5/4.
 */
public class FdAdapter extends BaseAdapter{
    private ArrayList<HashMap<String,Object>> list;
    private LayoutInflater mInflater;

    public FdAdapter(Context context,ArrayList<HashMap<String,Object>>list){
          mInflater=LayoutInflater.from(context);
          this.list=list;
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

        if(null!=list&&!list.isEmpty()){
            holder.text1.setText(list.get(position).get("text1").toString());
            holder.text2.setText(list.get(position).get("text2").toString());
        }

        return convertView;
    }
}
