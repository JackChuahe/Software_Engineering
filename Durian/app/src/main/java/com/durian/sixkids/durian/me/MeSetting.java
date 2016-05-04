package com.durian.sixkids.durian.me;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.durian.sixkids.durian.R;


/**
 * Created by JackCai on 2016/5/2.
 */
public class MeSetting extends Activity implements View.OnClickListener{
    private ImageView umreturn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.usermanager);

        umreturn = (ImageView) this.findViewById(R.id.umre);

       umreturn.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.umre) {
          //  Toast.makeText(this,"de",Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}

