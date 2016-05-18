package com.durian.sixkids.durian.search_recomm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.durian.sixkids.durian.MusicMainActivity;
import com.durian.sixkids.durian.R;
import com.durian.sixkids.durian.common.DBMuiscs;
import com.durian.sixkids.durian.common.DownloadList;
import com.durian.sixkids.durian.common.MusicModel;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SEELE on 2016/5/4.
 */
public class Guide extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.guide);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initWindow();
        // ��������һҳ������ͣ1.5s����ת����һ��activity
        initDB();
        final Intent localIntent = new Intent(Guide.this, MusicMainActivity.class);
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                startActivity(localIntent);
                finish();
            }
        };
        timer.schedule(tast, 1500);


    }

    private void initDB(){
        new DBMuiscs(getApplicationContext());
        DBMuiscs.clearMusics();

        // name ,album  ,singer ,playBg  ,headBg  ,resBg ,lyc
        MusicModel model3 = new MusicModel();
        model3.setAlbum("Uptown Funk");
        model3.setSinger("Mark Ronson");
        model3.setName("Uptown Funk");
        model3.setHeadBgId(R.drawable.uptown_head_bg);
        model3.setResId(R.drawable.updown_funk_img);
        model3.setPlayBgId(R.drawable.upfun_play_bg);
        model3.setSrc("/storage/sdcard0/zcw/Uptown Funk.mp3");
        model3.setLyc("This hit\\nThat ice cold\\nMichelle Pfeiffer\\nThat white gold\\nThis one, for them hood girls\\nThem good girls\\nStraight masterpieces\\nStylin’, while in\\nLivin’ it up in the city\\nGot Chucks on with Saint Laurent\\nGot kiss myself I’m so pretty\\nI’m too hot (hot damn)\\nCalled a police and a fireman\\nI’m too hot (hot damn)\\nMake a dragon wanna retire man\\nI’m too hot (hot damn)\\nSay my name you know who I am\\nI’m too hot (hot damn)\\nAm I bad ’bout that money\\nBreak it down\\nGirls hit your hallelujah (whuoo)\\nGirls hit your hallelujah (whuoo)\\nGirls hit your hallelujah (whuoo)\\n‘Cause Uptown Funk gon’ give it to you\\n‘Cause Uptown Funk gon’ give it to you\\n‘Cause Uptown Funk gon’ give it to you\\nSaturday night and we in the spot\\nDon’t believe me just watch (come on)\\nDon’t believe me just watch\\nDon’t believe me just watch\\nDon’t believe me just watch\\nDon’t believe me just watch\\nDon’t believe me just watch\\nHey, hey, hey, oh!\\nStop\\nWait a minute\\nFill my cup put some liquor in it");
        DBMuiscs.insertMusic(model3);



        MusicModel model2 = new MusicModel();
        model2.setAlbum("Promo Only Mainstream Radio October");
        model2.setSinger("Ke.Ha");
        model2.setName("Tik Tok");
        model2.setHeadBgId(R.drawable.test_bg_head);
        model2.setResId(R.drawable.tktk_img);
        model2.setPlayBgId(R.drawable.tktk_play_bg);
        model2.setSrc("/storage/sdcard0/zcw/TiK ToK (Live).mp3");
        model2.setLyc("Wake up in the morning feeling like P Diddy\\nPut my glasses on, I'm out the door\\nI'm gonna hit this city (Let's go)\\nBefore I leave,\\nBrush my teeth with a bottle of Jack\\nCause when I leave for the night,\\nI ain't coming back\\nI'm talking - pedicure on our toes, toes\\nTrying on all our clothes, clothes\\nBoys blowing up our phones, phones\\nDrop-toping, playing our favorite CDs\\nPulling up to the parties\\nTrying to get a little bit tipsy\\nDon't stop, make it pop\\nDJ, blow my speakers up\\nTonight, Imma fight\\nTill we see the sunlight\\nTick tock, on the clock\\nBut the party don't stop\\nWoah-oh oh oh\\nWoah-oh oh oh\\nDon't stop, make it pop\\nDJ, blow my speakers up\\nTonight, Imma fight\\nTill we see the sunlight\\nTick tock, on the clock\\nBut the party don't stop\\nWoah-oh oh oh\\nWoah-oh oh oh\\nAin't got a care in world,\\nBut got plenty of beer\\nAin't got no money in my pocket,\\nBut I'm already here");
        DBMuiscs.insertMusic(model2);

        MusicModel model4 = new MusicModel();
        model4.setAlbum("Bigger");
        model4.setSinger("James Blunt");
        model4.setName("You Are Beautiful");
        model4.setHeadBgId(R.drawable.yrbtf_head_bg);
        model4.setResId(R.drawable.yrbf_img);
        model4.setPlayBgId(R.drawable.yrbtf_play_bg);
        model4.setSrc("/storage/sdcard0/zcw/You Are Beautiful.mp3");
        model4.setLyc("My life is brilliant.\\nMy life is brilliant.\\nMy love is pure.\\nI saw an angel.\\nOf that I'm sure.\\nShe smiled at me on the subway.\\nShe was with another man.\\nBut I won't lose no sleep on that.\\nCause I've got a plan.\\nYou're beautiful. You're beautiful.\\nYou're beautiful, it's true.\\nI saw your face in a crowded place.\\nAnd I don't know what to do.\\nCause I'll never be with you.\\nYeah, she caught my eye.\\nAs we walked on by.\\nShe could see from my face that I was.\\nFlying high.(Fucking high)\\nAnd I don't think that I'll see her again.\\nBut we shared a moment that will last till the end. \\nYou're beautiful. You're beautiful.\\nYou're beautiful, it's true.\\nI saw your face in a crowded place.\\nAnd I don't know what to do.\\nCause I'll never be with you.\\nYou're beautiful. You're beautiful.\\nYou're beautiful, it's truth.\\nThere must be an angel with a smile on her face.\\nWhen she thought up that I should be with you.\\nBut it s time to face the truth.\\nI will never be with you.\n");
        DBMuiscs.insertMusic(model4);

        /**
         * 关于下载界面的列表
         */

        MusicModel m5 = new MusicModel();
        m5.setAlbum("Bigger");
        m5.setSinger("James Blunt");
        m5.setName("You Are Beautiful");
        m5.setHeadBgId(R.drawable.yrbtf_head_bg);
        m5.setResId(R.drawable.yrbf_img);
        m5.setPlayBgId(R.drawable.yrbtf_play_bg);
        m5.setSrc("/storage/sdcard0/zcw/You Are Beautiful.mp3");
        m5.setSize(15);
        DownloadList.downloading.add(m5);


        // name ,album  ,singer ,playBg  ,headBg  ,resBg ,lyc
        MusicModel m2 = new MusicModel();
        m2.setAlbum("Uptown Funk");
        m2.setSinger("Mark Ronson");
        m2.setName("Uptown Funk");
        m2.setHeadBgId(R.drawable.uptown_head_bg);
        m2.setResId(R.drawable.updown_funk_img);
        m2.setPlayBgId(R.drawable.upfun_play_bg);
        m2.setSrc("/storage/sdcard0/zcw/Uptown Funk.mp3");
        m2.setLyc("This hit\\nThat ice cold\\nMichelle Pfeiffer\\nThat white gold\\nThis one, for them hood girls\\nThem good girls\\nStraight masterpieces\\nStylin’, while in\\nLivin’ it up in the city\\nGot Chucks on with Saint Laurent\\nGot kiss myself I’m so pretty\\nI’m too hot (hot damn)\\nCalled a police and a fireman\\nI’m too hot (hot damn)\\nMake a dragon wanna retire man\\nI’m too hot (hot damn)\\nSay my name you know who I am\\nI’m too hot (hot damn)\\nAm I bad ’bout that money\\nBreak it down\\nGirls hit your hallelujah (whuoo)\\nGirls hit your hallelujah (whuoo)\\nGirls hit your hallelujah (whuoo)\\n‘Cause Uptown Funk gon’ give it to you\\n‘Cause Uptown Funk gon’ give it to you\\n‘Cause Uptown Funk gon’ give it to you\\nSaturday night and we in the spot\\nDon’t believe me just watch (come on)\\nDon’t believe me just watch\\nDon’t believe me just watch\\nDon’t believe me just watch\\nDon’t believe me just watch\\nDon’t believe me just watch\\nHey, hey, hey, oh!\\nStop\\nWait a minute\\nFill my cup put some liquor in it");
        m2.setSize(13);
        DownloadList.downloaded.add(m2);


        MusicModel m1 = new MusicModel();
        m1.setAlbum("Promo Only Mainstream Radio October");
        m1.setSinger("Ke.Ha");
        m1.setName("Tik Tok");
        m1.setHeadBgId(R.drawable.test_bg_head);
        m1.setResId(R.drawable.tktk_img);
        m1.setPlayBgId(R.drawable.tktk_play_bg);
        m1.setSrc("/storage/sdcard0/zcw/TiK ToK (Live).mp3");
        m1.setLyc("Wake up in the morning feeling like P Diddy\\nPut my glasses on, I'm out the door\\nI'm gonna hit this city (Let's go)\\nBefore I leave,\\nBrush my teeth with a bottle of Jack\\nCause when I leave for the night,\\nI ain't coming back\\nI'm talking - pedicure on our toes, toes\\nTrying on all our clothes, clothes\\nBoys blowing up our phones, phones\\nDrop-toping, playing our favorite CDs\\nPulling up to the parties\\nTrying to get a little bit tipsy\\nDon't stop, make it pop\\nDJ, blow my speakers up\\nTonight, Imma fight\\nTill we see the sunlight\\nTick tock, on the clock\\nBut the party don't stop\\nWoah-oh oh oh\\nWoah-oh oh oh\\nDon't stop, make it pop\\nDJ, blow my speakers up\\nTonight, Imma fight\\nTill we see the sunlight\\nTick tock, on the clock\\nBut the party don't stop\\nWoah-oh oh oh\\nWoah-oh oh oh\\nAin't got a care in world,\\nBut got plenty of beer\\nAin't got no money in my pocket,\\nBut I'm already here");
        m1.setSize(14);
        DownloadList.downloaded.add(m1);

    }

    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
