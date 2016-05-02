package com.durian.sixkids.durian.musicplay;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.MusicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackCai on 2016/5/2.
 */
public class MusicList extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private List<MusicModel> modelList;
    private MusicListAdapter adapter;
    private LinearLayout backLy;
    private ImageView ivWave;
    private AnimationDrawable animation ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_music_list);
        getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listView = (ListView)findViewById(R.id.music_list_view);
        backLy = (LinearLayout)findViewById(R.id.music_list_back_ly);
        ivWave = (ImageView)findViewById(R.id.music_play_wave);
        ivWave.setImageResource(R.drawable.animation);
        animation = (AnimationDrawable)ivWave.getDrawable();
        animation.start();
        initWindow();
        initListener();
        initData();
    }


    private void initListener(){
        backLy.setOnClickListener(this);
    }
    private void initData(){
       modelList = new ArrayList<MusicModel>();

        MusicModel model1 = new MusicModel();
        model1.setAlbum("Beautiful Angel");
        model1.setSinger("萧萧");
        model1.setName("爱要坦荡荡");
        model1.setPlay(false);
        modelList.add(model1);

        MusicModel model2 = new MusicModel();
        model2.setAlbum("Promo Only Mainstream Radio October");
        model2.setSinger("Ke.Ha");
        model2.setName("Tik Tok");
        model2.setPlay(true);
        modelList.add(model2);

        MusicModel model3 = new MusicModel();
        model3.setAlbum("Uptown Funk");
        model3.setSinger("Mark Ronson");
        model3.setName("Uptown Funk");
        model3.setPlay(false);
        modelList.add(model3);

        MusicModel model4 = new MusicModel();
        model4.setAlbum("小飞行");
        model4.setSinger("棉花糖");
        model4.setName("陪你到世界的终结");
        model4.setPlay(false);
        modelList.add(model4);

        MusicModel model5 = new MusicModel();
        model5.setAlbum("PTX, Vol .2");
        model5.setSinger("Pentatonix");
        model5.setName("Love Again");
        model3.setPlay(false);
        modelList.add(model5);

        MusicModel model6 = new MusicModel();
        model6.setAlbum("Singles");
        model6.setSinger("Maroon 5");
        model6.setName("Maps");
        model6.setPlay(false);
        modelList.add(model6);

        MusicModel model7 = new MusicModel();
        model7.setAlbum("克卜勒(Kepler)");
        model7.setSinger("孙燕姿");
        model7.setName("尚好的青春");
        model7.setPlay(false);
        modelList.add(model7);


        MusicModel model9 = new MusicModel();
        model9.setAlbum("Lenka");
        model9.setSinger("Lenka");
        model9.setName("The Show");
        model9.setPlay(false);
        modelList.add(model9);

        MusicModel model10 = new MusicModel();
        model10.setAlbum("Miss You Much");
        model10.setSinger("Leslie");
        model10.setName("风继续吹");
        model10.setPlay(false);
        modelList.add(model10);

        adapter = new MusicListAdapter(modelList,this);
        listView.setAdapter(adapter);

    }
    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music_list_back_ly:
                this.finish();
                break;
        }
    }
}

class MusicListAdapter extends BaseAdapter{

    private List<MusicModel> modelList;
    private LayoutInflater inflater;
    public MusicListAdapter(List<MusicModel> modelList, Context context){
        this.modelList = modelList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            convertView = inflater.inflate(R.layout.music_list_item,null);
            holder.songName = (TextView)convertView.findViewById(R.id.music_item_song_name_tv);
            holder.tvNum = (TextView)convertView.findViewById(R.id.music_item_num);
            holder.songDetail  = (TextView)convertView.findViewById(R.id.music_item_detail_tv);
            holder.playing = (ImageView)convertView.findViewById(R.id.music_item_now_play_iv);

            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        MusicModel model = modelList.get(position);
        holder.songName.setText(model.getName());
        holder.songDetail.setText(model.getSinger()+" | "+model.getAlbum());
        if (model.isPlay()){
            holder.playing.setVisibility(View.VISIBLE);
            holder.tvNum.setVisibility(View.GONE);
        }else{
            holder.playing.setVisibility(View.GONE);
            holder.tvNum.setVisibility(View.VISIBLE);
            holder.tvNum.setText("0"+(position+1)+"");
        }

        return convertView;
    }

    class Holder{
        TextView tvNum;
        TextView songName;
        TextView songDetail;
        ImageView playing;
    }
}
