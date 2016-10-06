package com.cryptogram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by siddhantvinchurkar on 7/1/16.
 */
public class SplashScreen extends Activity{

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        handler=new Handler();
        ProgressBar v = (ProgressBar) findViewById(R.id.progress);
        v.getIndeterminateDrawable().setColorFilter(0xFF00FFF9,
                PorterDuff.Mode.SRC_ATOP);
        class Time implements Runnable{

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for (int i=0;i<=1;i++){
                    try{
                        Thread.sleep(3000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            }}
            new Thread(new Time()).start();
    }

    @Override
    protected void onResume() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
