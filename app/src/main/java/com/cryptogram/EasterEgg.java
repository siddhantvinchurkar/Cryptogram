package com.cryptogram;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EasterEgg extends AppCompatActivity {

    LinearLayout ee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg);
        ee=(LinearLayout)findViewById(R.id.ee);
        ee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Cryptogram\nDesigned & Developed by\nSiddhant Vinchurkar",Toast.LENGTH_SHORT).show();
            }
        });
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
