package com.durian.sixkids.durian.me;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.durian.sixkids.durian.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by JackCai on 2016/5/2.
 */
public class DownLoadLookUp extends Activity implements View.OnClickListener{
    private ImageView dlimage;
    private RoundProgressBar bar;
    private flistadapter adapter;
    private  FdAdapter adapter2;
    private ArrayList<HashMap<String,Object>>list1;
    private ArrayList<HashMap<String,Object>>list2;
    private ListView listview1,listview2;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.downmusiclayout);
         listview1=(ListView)this.findViewById(R.id.downlist);
         listview2=(ListView)this.findViewById(R.id.downlaterlist);
        dlimage=(ImageView)this.findViewById(R.id.dlimage);
        dlimage.setOnClickListener(this);
//        bar=(RoundProgressBar)this.findViewById(R.id.roundProgressBar2);
//        bar.setCricleColor(Color.RED);
//        bar.setMax(100);
//        bar.setCricleProgressColor(Color.GREEN);
//
//        bar.setProgress(20);
          list1=new ArrayList<HashMap<String,Object>>();

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("text1","Maps");
        map.put("text2","OB");
        map.put("text3","13.2MB");
        map.put("text4","下载中");

        list1.add(map);
         map=new HashMap<String,Object>();
        map.put("text1","Because Of You");
        map.put("text2","0B");
        map.put("text3","15.4MB");
        map.put("text4","下载中");
        list1.add(map);

        adapter=new flistadapter(this,list1);
        listview1.setAdapter(adapter);



        list2=new ArrayList<HashMap<String,Object>>();

        HashMap<String,Object> map2=new HashMap<String,Object>();
        map2.put("text1","Tik Tok");
        map2.put("text2","14.5MB");
        list2.add(map2);

        map2=new HashMap<String,Object>();
        map2.put("text1","Uptown Funk");
        map2.put("text2","12.9MB");
        list2.add(map2);


        map2=new HashMap<String,Object>();
        map2.put("text1","You Are Beautiful");
        map2.put("text2","15.9MB");
        list2.add(map2);



        adapter2=new FdAdapter(this,list2);
        listview2.setAdapter(adapter2);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.dlimage:
                this.finish();
                break;
        }
    }
}
