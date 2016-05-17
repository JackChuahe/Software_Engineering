package com.durian.sixkids.durian.musicplay;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.DBMuiscs;
import com.durian.sixkids.durian.common.MusicModel;
import com.durian.sixkids.durian.common.Musics;
import com.durian.sixkids.durian.common.NetWorkOp;
import com.durian.sixkids.durian.common.ShareMusics;
import com.durian.sixkids.durian.search_recomm.MusicSearch;
import com.durian.sixkids.durian.util.ConstUtil;
import com.durian.sixkids.durian.util.PlayService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackCai on 2016/5/2.
 */
public class MusicList extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener ,View.OnTouchListener,AdapterView.OnItemLongClickListener{
    private ListView listView;

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

    private LinearLayout lyShare;
    private LinearLayout lyCollect;
    private LinearLayout lyDelete;


    private DialogDeal dialogDeal;
    public AlertDialog alertDialog;

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
        lyShare = (LinearLayout)findViewById(R.id.music_list_share);

        rlAllHead = (RelativeLayout)findViewById(R.id.all_head);
        ivHeadSongimg = (ImageView) findViewById(R.id.music_list_music_head_iv);

        ivHeadPlay = (ImageView)findViewById(R.id.music_list_head_song_name_playing_iv);
        ivHeadNext = (ImageView)findViewById(R.id.music_list_head_song_name_next_iv);

        lyCollect = (LinearLayout)findViewById(R.id.lyCollect);
        lyDelete = (LinearLayout)findViewById(R.id.lyDelete);

        ivWave.setImageResource(R.drawable.animation);
        animation = (AnimationDrawable)ivWave.getDrawable();

        initWindow();
        initListener();
        getData();
        initData();
        setPlayingStatus();
        initTouchListener();

        /**
         *
         */
        if (PlayService.musicList == null){
            PlayService.musicList = this;
        }
        PlayService.ACTIVITY = PlayService.MUSIC_LIST;
    }

    /**
     *
     */
    private void initData(){
        adapter = new MusicListAdapter(Musics.musicModels,this);
        listView.setAdapter(adapter);
    }

    /**
     * 获取上一个界面的数据
     */
    private void getData(){
        //Intent intent = getIntent();
    }
    /**
     * 添加touch监听事件
     */
    private void initTouchListener(){
        ivSearch.setOnTouchListener(this);
        ivHeadNext.setOnTouchListener(this);
        ivHeadPlay.setOnTouchListener(this);

        lyDelete.setOnTouchListener(this);
        lyCollect.setOnTouchListener(this);
        lyShare.setOnTouchListener(this);
    }


    private void initListener(){
        backLy.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        ivHeadSongimg.setOnClickListener(this);
        rl.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        listView.setOnItemLongClickListener(this);
        //lyShare.setOnClickListener(this);
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
            case R.id.music_list_search_iv:
                Intent intent1 =new Intent(this,MusicSearch.class);
                startActivity(intent1);
                break;
            case R.id.music_list_share:
                Toast toast = Toast.makeText(this,"歌曲分享成功！分享就是快乐！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
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
        //Intent intent = new Intent();
        //setResult(1,intent);
        finish();
    }

    /**
     * 进入到下一个界面
     */
    private void entryMusicPlay(){
        Intent in = new Intent(this, MusicPlay.class);
        startActivityForResult(in,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)return;
        super.onActivityResult(requestCode, resultCode, data);
        setPlayingStatus();
    }

    /**
     * 设置状态
     */
    void setPlayingStatus(){
        MusicModel model;
        model = Musics.musicModels.get(Musics.playIndex);
        model.setPlay(true);
        ivHeadSongimg.setImageResource(model.getResId());
        tvSongName.setText(model.getSinger()+" - "+model.getName());
        tvAlbum.setText(model.getAlbum());
        for(int i = 0 ; i < Musics.musicModels.size() ;++i){
            if(i != Musics.playIndex) {
                Musics.musicModels.get(i).setPlay(false);
            }
        }

        adapter.notifyDataSetChanged();  //更新列表适配器
        //设置播放状态
        if (Musics.isPlaying){
            ivHeadPlay.setImageResource(R.drawable.nowplaying_pause_n);
        }else{
            ivHeadPlay.setImageResource(R.drawable.nowplaying_play_n);
        }

        rlAllHead.setBackgroundResource(model.getHeadBgId());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.65f, 1.0f);
        alphaAnimation.setDuration(1000);
        rlAllHead.setAnimation(alphaAnimation);
        setAnimation();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Musics.playIndex = position;
        Musics.isPlaying = true;
        play(0,true);
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
            case R.id.lyCollect:
                collectTouchDown(event);
                break;
            case R.id.lyDelete:
                deleteTouchDown(event);
                break;
            case R.id.music_list_share:
                shareTouchDown(event);
                break;
        }
        return true;
    }

    /**
     * 删搜藏键被按下
     * @param event
     */
    private void collectTouchDown(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            lyCollect.setBackgroundResource(R.drawable.linear_layout_stroke_down);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            lyCollect.setBackgroundResource(R.drawable.linear_layout_stroke);
        }
    }

    /**
     * 分享键被按下
     * @param event
     */
    private void shareTouchDown(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            lyShare.setBackgroundResource(R.drawable.linear_layout_stroke_down);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            lyShare.setBackgroundResource(R.drawable.linear_layout_stroke);
            prepareShare(Musics.playIndex);
        }
    }

    /**
     * 删除键被按下
     * @param event
     */
    private void deleteTouchDown(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            lyDelete.setBackgroundResource(R.drawable.linear_layout_stroke_down);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            lyDelete.setBackgroundResource(R.drawable.linear_layout_stroke);
            //对话框
            Toast toast = Toast.makeText(getApplicationContext(),"已下载",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    /**
     * 准备分享
     * @param index
     */
    private void prepareShare(final int index){
        // 判断是否开启了wifi
        switch (NetWorkOp.GetNetype(this)) {
            case NetWorkOp.NETTYPE_NO:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("当前没有网络连接!");
                builder.show();
                break;
            case NetWorkOp.NETTYPE_WIFI:
                share(index);
                break;
            default:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("当前为非WIFI网络！分享音乐会造成巨大流量损耗?!");
                builder1.setCancelable(true);
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case -2:
                                share(index);
                                break;
                            case -1:
                                break;
                        }
                    }
                };
                builder1.setNegativeButton("继续分享",listener);
                builder1.setPositiveButton("取消",listener);
                builder1.show();
                break;
        }

    }

    /**
     * 分享
     * @param index
     */
    private void share(int index){
        if (ShareMusics.shareMusics.contains(Musics.musicModels.get(index))){
            Toast.makeText(this,"该音乐已经加入到分享曲单中!",Toast.LENGTH_LONG).show();
            return;
        }

        ShareMusics.notifyWebService(Musics.musicModels.get(index));

        Toast toast = new Toast(getApplicationContext());
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.toast_view,null);
        TextView tv = (TextView) view.findViewById(R.id.toast_tv);
        tv.setText("分享成功！");
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }


    private void chooseDialog(int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater  = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.dialog_layout,null);
         dialogDeal = new DialogDeal(this,view,Musics.musicModels.get(index).getName(), index);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        //builder.show();
    }

    /**
     * 准备删除歌曲
     * @param position
     */
    private void prepareDelMusic(final int position){
        if (position == Musics.playIndex){  //若即将删除的歌曲是当前正在播放的歌曲
            Toast toast = Toast.makeText(getApplicationContext(),"\""+Musics.musicModels.get(position).getName()+"\"正在播放!无法删除!",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定删除歌曲 ?!\n"+Musics.musicModels.get(position).getName());
        builder.setCancelable(true);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case -2:
                        DBMuiscs.deleteMusic(Musics.musicModels.get(position).getName());
                        if (Musics.playIndex > position){  //删除后，改变当前播放的位置 同时更新列表
                            Musics.playIndex--;
                        }
                        Musics.musicModels.remove(position); //移除被删除项
                        adapter.notifyDataSetChanged();

                        Toast toast = new Toast(getApplicationContext());
                        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                        View view = inflater.inflate(R.layout.toast_view,null);
                        toast.setView(view);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                        break;
                    case -1:
                        break;
                }
            }
        };
        builder.setPositiveButton("取消", listener);
        builder.setNegativeButton("确定",listener);
        builder.show();
    }

    /**
     * 删除歌曲
     * @param index
     */
    private void deleteMusic(int index){

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
            playNext();
        }
    }

    public void playNext(){

        ivHeadNext.setImageResource(R.drawable.nowplaying_next_n);
        Musics.playIndex = (Musics.playIndex +1)%Musics.musicModels.size();
        rlAllHead.setBackgroundResource(Musics.musicModels.get(Musics.playIndex).getHeadBgId());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(500);
        Musics.isPlaying = true;
        rlAllHead.setAnimation(alphaAnimation);
        setPlayingStatus();
        play(0,true);  //播放下一曲
    }

    /**
     * 点击了播放按钮
     * @param event
     */
    private void touchedPlay(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if(Musics.isPlaying){
                ivHeadPlay.setImageResource(R.drawable.nowplaying_pause_p);
            }else{
                ivHeadPlay.setImageResource(R.drawable.nowplaying_play_p);
            }
        }else if(event.getAction() == MotionEvent.ACTION_UP){


            if(Musics.isPlaying){
                ivHeadPlay.setImageResource(R.drawable.nowplaying_play_n);
            }else{
                ivHeadPlay.setImageResource(R.drawable.nowplaying_pause_n);
            }
            Musics.isPlaying = !Musics.isPlaying;
            setAnimation();
           // setPlayingStatus();
            if (Musics.isPlaying){
                play(ConstUtil.STATE_PLAY,false);
            }else{
                play(ConstUtil.STATE_PAUSE,false);
            }
        }
    }

    /**
     * 设置动画
     */
    private void setAnimation(){
        if (Musics.isPlaying){
            animation.start();
        }else {
            animation.stop();
        }
    }

    /**
     * 设置播放
     * @param state
     * @param isNext
     */
    public void play(int state,boolean isNext){
        if (Musics.isFirstPlaying || isNext){
            playMusic(Musics.musicModels.get(Musics.playIndex).getSrc());
            Musics.isFirstPlaying = false;
        }else{
            sendBroadCastToService(state);
        }
    }

    protected void sendBroadCastToService(int state){
        Intent intent = new Intent();
        intent.setAction(ConstUtil.MUSICSERVICE_ACTION);
        intent.putExtra("control", state);
        this.sendBroadcast(intent);
    }

    public void playMusic(String path){
        Intent intent=new Intent(this,PlayService.class);
        intent.putExtra("path", path);
        this.startService(intent);
    }

    /**
     * 长按列表项监听事件
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        chooseDialog(position);
        return true;
    }

    /**
     * 对话框回调函数
     * @param option
     */
    public void dialogCallBack(int option,int position){
        switch (option){
            case DialogDeal.COLLECT:

            break;
            case DialogDeal.SHARE:
                prepareShare(position);
                break;
            case DialogDeal.DELETE:
                prepareDelMusic(position);
                break;
            case DialogDeal.DOWNLOAD:
                Toast toast = Toast.makeText(getApplicationContext(),"已下载",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                break;
        }
    }
}

/**
 * 对话框处理事件
 */
class DialogDeal implements View.OnTouchListener{
    public final static int DELETE = 0;
    public final static int SHARE = 1;
    public final static int COLLECT = 2;
    public final static int DOWNLOAD = 3;

    private MusicList activity;
    private TextView tvName;
    private LinearLayout lyDel ;
    private LinearLayout lyShare ;
    private LinearLayout lyCollect ;
    private LinearLayout lyDown ;
    private int index;

    public DialogDeal(MusicList musicList ,View view ,String name,int index){
        this.activity = musicList;
        this.index = index;
        tvName = (TextView)view.findViewById(R.id.dialog_music_name);
        lyShare = (LinearLayout)view.findViewById(R.id.dialog_share_ly);
        lyCollect = (LinearLayout)view.findViewById(R.id.dialog_collect_ly);
        lyDel = (LinearLayout)view.findViewById(R.id.dialog_del_ly);
        lyDown = (LinearLayout)view.findViewById(R.id.dialog_down_ly);

        tvName.setText(name);
        lyShare.setOnTouchListener(this);
        lyCollect.setOnTouchListener(this);
        lyDel.setOnTouchListener(this);
        lyDown.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.dialog_collect_ly:
                //Toast.makeText(activity,"collect",Toast.LENGTH_LONG).show();
                collectTouched(event);
                break;
            case R.id.dialog_del_ly:
                delTouched(event);
                break;
            case R.id.dialog_down_ly:
                downTouched(event);
                break;
            case R.id.dialog_share_ly:
                shareTouched(event);
                break;
        }
        return true;
    }

    /**
     * 收藏
     * @param event
     */
    private void collectTouched(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            lyCollect.setBackgroundResource(R.color.colorPrimaryDark);
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            lyCollect.setBackgroundResource(R.color.re_up);
            activity.dialogCallBack(COLLECT,index);
            activity.alertDialog.dismiss();
        }
    }
    /**
     * 下载
     * @param event
     */
    private void downTouched(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            lyDown.setBackgroundResource(R.color.colorPrimaryDark);
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            lyDown.setBackgroundResource(R.color.re_up);
            activity.dialogCallBack(DOWNLOAD,index);
            activity.alertDialog.dismiss();
        }
    }
    /**
     * 分享
     * @param event
     */
    private void shareTouched(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            lyShare.setBackgroundResource(R.color.colorPrimaryDark);
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            lyShare.setBackgroundResource(R.color.re_up);
            activity.dialogCallBack(SHARE,index);
            activity.alertDialog.dismiss();
        }
    }
    /**
     * 删除
     * @param event
     */
    private void delTouched(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            lyDel.setBackgroundResource(R.color.colorPrimaryDark);
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            lyDel.setBackgroundResource(R.color.re_up);
            activity.dialogCallBack(DELETE,index);
            activity.alertDialog.dismiss();
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
