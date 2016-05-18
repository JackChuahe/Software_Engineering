package com.durian.sixkids.durian.me;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.durian.sixkids.durian.MusicMainActivity;
import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.ClassfyMusic;
import com.durian.sixkids.durian.common.DBMuiscs;
import com.durian.sixkids.durian.common.DownloadList;
import com.durian.sixkids.durian.common.Musics;
import com.durian.sixkids.durian.musicplay.MusicList;
import com.durian.sixkids.durian.musicplay.MusicPlay;
import com.durian.sixkids.durian.search_recomm.MusicLibrary;

import java.util.List;

/**
 * Created by JackCai on 2016/5/2.
 */
public class LocalManagement   implements View.OnClickListener,View.OnTouchListener ,AdapterView.OnItemClickListener{

    private RelativeLayout user;
    private RelativeLayout scanmusic,localmusic,allmucic,downmusic;
    private MusicMainActivity activity;
    private ListView listView;
    private ListAdapter adapter;
    private List<ClassfyMusic> list;

    private View view;
    public LocalManagement(MusicMainActivity activity , View view){
        this.activity = activity;
        this.view = view;
        intit();
    }

    private void intit() {
        user=(RelativeLayout)view.findViewById(R.id.lguser);
        scanmusic=(RelativeLayout)view.findViewById(R.id.lglocalmusic);
        localmusic=(RelativeLayout)view.findViewById(R.id.lgsingmusic);
        downmusic=(RelativeLayout)view.findViewById(R.id.lgcollmusic);
        allmucic=(RelativeLayout)view.findViewById(R.id.lgallmusic);
        listView = (ListView)view.findViewById(R.id.localmanage_listview);

          initDataFromDB();
        adapter = new ListAdapter(list,activity);
        listView.setAdapter(adapter);
        LayoutInflater inflater = LayoutInflater.from(activity);
        listView.addFooterView(inflater.inflate(R.layout.localmanagement_list_no_more_item,null));
        listView.setOnItemClickListener(this);
//        user.setOnClickListener(this);
//        scanmusic.setOnClickListener(this);

        user.setOnTouchListener(this);
        scanmusic.setOnTouchListener(this);
        downmusic.setOnTouchListener(this);
        allmucic.setOnTouchListener(this);
        localmusic.setOnTouchListener(this);
    }

    /**
     * 从数据库获取数据
     */
    private void initDataFromDB(){
       list = DBMuiscs.queryClassfy();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.lguser:

                break;
            case R.id.lgsingmusic:

                break;
        }

    }

    public void setColor(int id,MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.findViewById(id).setBackgroundColor(activity.getResources().getColor(R.color.re_down));
                break;
            case MotionEvent.ACTION_UP:
                view.findViewById(id).setBackgroundColor(activity.getResources().getColor(R.color.re_up));
                if(id==R.id.lguser){
                    Intent intent1=new Intent(activity,MeSetting.class);
                    activity.startActivity(intent1);
                }else if(id==R.id.lgsingmusic){
                    Intent intent2=new Intent(activity,ScanLocal.class);
                    activity.startActivity(intent2);
                }else if(id==R.id.lgcollmusic){
                    Intent intent3=new Intent(activity,DownLoadLookUp.class);
                    activity.startActivity(intent3);
                }else if(id == R.id.lglocalmusic){
                    Musics.musicModels.clear();
                    Musics.musicModels.addAll(DBMuiscs.queryAllMusic());
                    Intent intent = new Intent(activity, MusicList.class);
                    activity.startActivityForResult(intent,0);
                }
                break;
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId()==R.id.lguser||v.getId()==R.id.lgallmusic||v.getId()==R.id.lglocalmusic||v.getId()==R.id.lgsingmusic||v.getId()==R.id.lgcollmusic){
            setColor(v.getId(),event);
        }

        return true;
    }

    /**
     * 列表项被点击后
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String singer = list.get(position).getSinger();
        Musics.musicModels.clear();
        Musics.musicModels.addAll(DBMuiscs.queryBySinger(singer));
        Intent intent = new Intent(activity,MusicList.class);
        activity.startActivity(intent);
    }


    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
}


class ListAdapter extends BaseAdapter{
    public List<ClassfyMusic> list;
    private LayoutInflater inflater;

    public ListAdapter(List<ClassfyMusic> list , Context context){
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
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
        Holder holder = null;
        if (convertView == null){
            holder = new Holder();
            convertView = inflater.inflate(R.layout.local_singer_musi_classfy_item,null);
            holder.imageView = (ImageView)convertView.findViewById(R.id.loveitemimage1) ;
            holder.tvSinger = (TextView)convertView.findViewById(R.id.loveitemtext1);
            holder.tvAlbumm = (TextView)convertView.findViewById(R.id.loveitemtext2);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        holder.imageView.setImageResource(list.get(position).getResId());
        holder.tvAlbumm.setText(list.get(position).getAlbum()+" | 共"+list.get(position).getCount()+"首");
        holder.tvSinger.setText(list.get(position).getSinger());

        return convertView;
    }

    class  Holder{
        ImageView imageView;
        TextView tvSinger;
        TextView tvAlbumm;
    }
}

