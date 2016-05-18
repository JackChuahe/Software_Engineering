package com.durian.sixkids.durian.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackCai on 2016/5/17.
 */
public class DBMuiscs  {
    private static SQLiteDatabase db;
    private static final String DB_NAME = "durian";
    public DBMuiscs(Context context){
        db = context.openOrCreateDatabase(DB_NAME,Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists music (name varchar,album varchar ,singer varchar,playBg integer ,headBg integer ,resBg integer,lyc varchar,src varchar,state integer)");
    }

    /**
     * 清空所有数据
     * @return
     */
    public static boolean clearMusics(){
        if (db!= null && db.isOpen()){
            db.execSQL("delete from music");
            return true;
        }
        return false;
    }
    /**
     * 查询所有音乐数据
     * @return
     */
   // name ,album  ,singer ,playBg  ,headBg  ,resBg ,lyc
    public static List<MusicModel> queryAllMusic(){
        List<MusicModel> musicModels = new ArrayList<MusicModel>();
        if (db!= null && db.isOpen()){
            Cursor c = db.rawQuery("select * from music where state = 1",null);
            while (c.moveToNext()){
                MusicModel m = new MusicModel();
                m.setName(c.getString(0));
                m.setAlbum(c.getString(1));
                m.setSinger(c.getString(2));
                m.setPlayBgId(c.getInt(3));
                m.setHeadBgId(c.getInt(4));
                m.setResId(c.getInt(5));
                m.setLyc(c.getString(6));
                m.setSrc(c.getString(7));
                musicModels.add(m);
            }
        }
        return musicModels;
    }

    /**
     * 按歌手查询数据
     * @param singer
     * @return
     */
    public static List<MusicModel> queryBySinger(String singer){
        List<MusicModel> musicModels = new ArrayList<MusicModel>();
        if (db!= null && db.isOpen()){
            Cursor c = db.rawQuery("select * from music where singer = ? and state = 1",new String[]{singer});
            while (c.moveToNext()){
                MusicModel m = new MusicModel();
                m.setName(c.getString(0));
                m.setAlbum(c.getString(1));
                m.setSinger(c.getString(2));
                m.setPlayBgId(c.getInt(3));
                m.setHeadBgId(c.getInt(4));
                m.setResId(c.getInt(5));
                m.setLyc(c.getString(6));
                m.setSrc(c.getString(7));
                musicModels.add(m);
            }
        }
        return musicModels;
    }

    /**
     * 删除歌曲
     * @param musicName
     * @return
     */
    public static boolean deleteMusic(String musicName){
        if (db!= null && db.isOpen()){
            db.execSQL("update music set state = 0 where name = ?",new Object[]{musicName});
            return true;
        }
        return false;
    }

    /**
     * 增加歌曲信息
     * @param m
     * @return
     */
    public static boolean insertMusic(MusicModel m){
        if (db!= null && db.isOpen()){
            try {
                db.execSQL("insert into music values(?,?,?,?,?,?,?,?,?)",new Object[]{m.getName(),m.getAlbum(),m.getSinger(),m.getPlayBgId(),m.getHeadBgId(),m.getResId(),m.getLyc(),m.getSrc(),1});
            }catch (SQLiteException e){
               System.err.println(e.getMessage());
            }

            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    // name ,album  ,singer ,playBg  ,headBg  ,resBg ,lyc
    public static List<ClassfyMusic> queryClassfy(){
       List<ClassfyMusic> list = new ArrayList<ClassfyMusic>();
        if (db != null && db.isOpen()){
            Cursor c = db.rawQuery("select singer,count(*) from music group by singer",null);
            while (c.moveToNext()){
                ClassfyMusic cm = new ClassfyMusic();
                cm.setCount(c.getInt(1));
                cm.setSinger(c.getString(0));
                Cursor ic = db.rawQuery("select resBg,album from music where singer = ?",new String[]{c.getString(0)});
                if (ic.moveToNext()){
                    cm.setAlbum(ic.getString(1));
                    cm.setResId(ic.getInt(0));
                }
                list.add(cm);
            }
            return list;
        }
        return list;
    }
}
