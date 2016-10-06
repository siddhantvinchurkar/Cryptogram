package com.cryptogram;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Settings extends PreferenceActivity {

    private AppCompatDelegate mDelegate;
    LinearLayout set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        set=(LinearLayout)findViewById(R.id.set);
        toolbar.setNavigationIcon(R.drawable.arrow);
        toolbar.setTitle("Settings");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(Universal.where) {
                    case 0:startActivity(new Intent(Settings.this, MainActivity.class));Universal.where=0;break;
                    case 1:startActivity(new Intent(Settings.this, MainActivity.class));Universal.where=0;break;
                    case 2:startActivity(new Intent(Settings.this, NewMessage.class));Universal.where=0;break;
                    case 3:startActivity(new Intent(Settings.this, Decrypt.class));Universal.where=0;break;
                    default:startActivity(new Intent(Settings.this, MainActivity.class));Universal.where=0;break;
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getListView().setBackground(getResources().getDrawable(R.drawable.centered_settings));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = Settings.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        addPreferencesFromResource(R.xml.settings);
        set.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeRight() {
                switch (Universal.where) {
                    case 0:
                        startActivity(new Intent(Settings.this, MainActivity.class));
                        Universal.where = 0;
                        break;
                    case 1:
                        startActivity(new Intent(Settings.this, MainActivity.class));
                        Universal.where = 0;
                        break;
                    case 2:
                        startActivity(new Intent(Settings.this, NewMessage.class));
                        Universal.where = 0;
                        break;
                    case 3:
                        startActivity(new Intent(Settings.this, Decrypt.class));
                        Universal.where = 0;
                        break;
                    default:
                        startActivity(new Intent(Settings.this, MainActivity.class));
                        Universal.where = 0;
                        break;
                }
                finish();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slideinright,R.anim.slideoutleft);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switch(Universal.where) {
            case 0:startActivity(new Intent(Settings.this, MainActivity.class));Universal.where=0;break;
            case 1:startActivity(new Intent(Settings.this, MainActivity.class));Universal.where=0;break;
            case 2:startActivity(new Intent(Settings.this, NewMessage.class));Universal.where=0;break;
            case 3:startActivity(new Intent(Settings.this, Decrypt.class));Universal.where=0;break;
            default:startActivity(new Intent(Settings.this, MainActivity.class));Universal.where=0;break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}