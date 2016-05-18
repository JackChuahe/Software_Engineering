package com.durian.sixkids.durian.me;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.DBMuiscs;
import com.durian.sixkids.durian.common.DownloadList;
import com.durian.sixkids.durian.common.MusicModel;
import com.durian.sixkids.durian.common.Musics;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JackCai on 2016/5/2.
 */
public class DownLoadLookUp extends Activity implements View.OnClickListener{
    private ImageView dlimage;
    private RoundProgressBar bar;
    private Flistadapter adapter;
    private  FdAdapter adapter2;
    private ListView listview1,listview2;


    private Runnable runnable=new Runnable(){
        @Override
        public void run() {
            while(true){
                if(FlagPosition.position!=-1){

                    MusicModel model = DownloadList.downloading.get(FlagPosition.position);
                    DBMuiscs.insertMusic(model);
                    Musics.musicModels.add(model);
                    DownloadList.downloaded.add(model);
                    DownloadList.downloading.remove(FlagPosition.position);
                    handler.sendEmptyMessage(0);
                    FlagPosition.position=-1;
                }
            }
        }
    };

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
    };
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.downmusiclayout);
         listview1=(ListView)this.findViewById(R.id.downlist);
         listview2=(ListView)this.findViewById(R.id.downlaterlist);
        dlimage=(ImageView)this.findViewById(R.id.dlimage);
        dlimage.setOnClickListener(this);


        adapter=new Flistadapter(this);
        listview1.setAdapter(adapter);


        adapter2=new FdAdapter(this);
        listview2.setAdapter(adapter2);

        new Thread(runnable).start();

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
