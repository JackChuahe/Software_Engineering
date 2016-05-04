package com.durian.sixkids.durian.musicplay;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.MusicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackCai on 2016/5/2.
 */
public class MusicList extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener ,View.OnTouchListener{
    private ListView listView;
    private List<MusicModel> modelList;
    private MusicListAdapter adapter;
    private LinearLayout backLy;
    private ImageView ivWave;
    private AnimationDrawable animation ;
    private RelativeLayout rl;
    private ImageView ivHeadSongimg;
    private RelativeLayout rlAllHead;
    private TextView tvSongName;
    private TextView tvAlbum;
    private ImageView ivSearch;

    private ImageView ivHeadPlay;
    private ImageView ivHeadNext;

    private boolean isPlaying = true;
    private int playIndex = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_music_list);
        getWindow().setFlags(Window.FEATURE_NO_TITLE, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listView = (ListView)findViewById(R.id.music_list_view);
        backLy = (LinearLayout)findViewById(R.id.music_list_back_ly);
        ivWave = (ImageView)findViewById(R.id.music_play_wave);
        rl = (RelativeLayout)findViewById(R.id.music_list_playing_head);
        tvAlbum = (TextView)findViewById(R.id.music_head_music_detail) ;
        tvSongName = (TextView)findViewById(R.id.music_list_head_song_name_tv);
        ivSearch = (ImageView)findViewById(R.id.music_list_search_iv);


        rlAllHead = (RelativeLayout)findViewById(R.id.all_head);
        ivHeadSongimg = (ImageView) findViewById(R.id.music_list_music_head_iv);

        ivHeadPlay = (ImageView)findViewById(R.id.music_list_head_song_name_playing_iv);
        ivHeadNext = (ImageView)findViewById(R.id.music_list_head_song_name_next_iv);

        ivWave.setImageResource(R.drawable.animation);
        animation = (AnimationDrawable)ivWave.getDrawable();

        initWindow();
        initListener();
        initData();
        getData();
        setPlayingStatus();
        initTouchListener();
    }

    /**
     * 获取上一个界面的数据
     */
    private void getData(){
        Intent intent = getIntent();
        isPlaying = intent.getBooleanExtra("isPlaying",true);
        playIndex = intent.getIntExtra("playIndex",0);
    }
    /**
     * 添加touch监听事件
     */
    private void initTouchListener(){
        ivSearch.setOnTouchListener(this);
        ivHeadNext.setOnTouchListener(this);
        ivHeadPlay.setOnTouchListener(this);
    }


    private void initListener(){
        backLy.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        ivHeadSongimg.setOnClickListener(this);
        rl.setOnClickListener(this);
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
        model2.setHeadBgId(R.drawable.test_bg_head);
        model2.setResId(R.drawable.test_song_head_bg);
        modelList.add(model2);

        MusicModel model3 = new MusicModel();
        model3.setAlbum("Uptown Funk");
        model3.setSinger("Mark Ronson");
        model3.setName("Uptown Funk");
        model3.setPlay(false);
        model3.setHeadBgId(R.drawable.uptown_head_bg);
        model3.setResId(R.drawable.updown_funk_img);
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
                keyBack();
                break;
            case R.id.music_list_music_head_iv:
                entryMusicPlay();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            keyBack();
        }
        return true;
    }

    private void keyBack(){
        Intent intent = new Intent();
        intent.putExtra("isPlaying",true);
        intent.putExtra("playIndex",playIndex);
        setResult(1,intent);
        finish();
    }

    /**
     * 进入到下一个界面
     */
    private void entryMusicPlay(){
        Intent in = new Intent(this, MusicPlay.class);
        in.putExtra("isPlaying",isPlaying);
        in.putExtra("playIndex",playIndex);
        startActivityForResult(in,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)return;
        super.onActivityResult(requestCode, resultCode, data);
        isPlaying = data.getBooleanExtra("isPlaying",false);
        playIndex = data.getIntExtra("playIndex",0);
        setPlayingStatus();
    }

    /**
     * 设置状态
     */
    void setPlayingStatus(){
        MusicModel model;
        if (playIndex == 0){
            model = modelList.get(1);
            model.setPlay(true);
            modelList.get(2).setPlay(false);

            ivHeadSongimg.setImageResource(model.getResId());
            tvSongName.setText(model.getSinger()+" - "+model.getName());
            tvAlbum.setText(model.getAlbum());
        }else{
            model = modelList.get(2);
            model.setPlay(true);
            modelList.get(1).setPlay(false);

            ivHeadSongimg.setImageResource(model.getResId());
            tvSongName.setText(model.getSinger()+" - "+model.getName());
            tvAlbum.setText(model.getAlbum());
        }
        adapter.notifyDataSetChanged();  //更新列表适配器
        //设置播放状态
        if (isPlaying){
            ivHeadPlay.setImageResource(R.drawable.nowplaying_pause_n);
        }else{
            ivHeadPlay.setImageResource(R.drawable.nowplaying_play_n);
        }
        setAnimation();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 1){
            playIndex = 0;

        }else if(position == 2){
            playIndex = 1;
        }
        isPlaying = true;

        rlAllHead.setBackgroundResource(modelList.get(playIndex+1).getHeadBgId());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(500);
        rlAllHead.setAnimation(alphaAnimation);

        setPlayingStatus();
        //entryMusicPlay();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.music_list_search_iv:
                touchedSearch(event);
                break;
            case R.id.music_list_head_song_name_next_iv:
                touchedNext(event);
                break;
            case R.id.music_list_head_song_name_playing_iv:
                touchedPlay(event);
                break;

        }
        return true;
    }


    /**
     * 按了搜索按钮后
     * @param event
     */
    private void touchedSearch(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            ivSearch.setImageResource(R.drawable.search_icon_p);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            ivSearch.setImageResource(R.drawable.h_search_icon_n);
        }
    }
    private void touchedNext(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            ivHeadNext.setImageResource(R.drawable.nowplaying_next_p);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            ivHeadNext.setImageResource(R.drawable.nowplaying_next_n);
            playIndex = (playIndex +1)%2;
            rlAllHead.setBackgroundResource(modelList.get(playIndex+1).getHeadBgId());
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
            alphaAnimation.setDuration(500);
            isPlaying = true;
            rlAllHead.setAnimation(alphaAnimation);
            setPlayingStatus();
        }
    }

    private void touchedPlay(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if(isPlaying){
                ivHeadPlay.setImageResource(R.drawable.nowplaying_pause_p);
            }else{
                ivHeadPlay.setImageResource(R.drawable.nowplaying_play_p);
            }
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            if(isPlaying){
                ivHeadPlay.setImageResource(R.drawable.nowplaying_play_n);
            }else{
                ivHeadPlay.setImageResource(R.drawable.nowplaying_pause_n);
            }
            isPlaying = !isPlaying;
            setPlayingStatus();
        }
    }

    /**
     * 设置动画
     */
    private void setAnimation(){
        if (isPlaying){
            animation.start();
        }else {
            animation.stop();
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
