package com.cryptogram;

import android.app.Service;
import android.content.Context;
//import android.content.DialogInterface;    //This import is only required for the beta versions of Cryptogram.
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.*;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    Typeface typeface,btypeface;
    TextView title,mesf,mdsf,dcr,enc,bypass;
    Vibrator vibe;
    private FirebaseAnalytics mFirebaseAnalytics;
    int g=0;
    long[] pattern = {0, 50, 50, 50, 50, 50};
    Button cancel,grant;
    EditText dpass;
    ProgressBar progressBar1,progressBar2;
    RelativeLayout rlenc,rldcr,mainTheme;
    LinearLayout back;
    Boolean enabled=false,confirm=true;
    String themer="1";
    int theme=1;
    android.support.design.widget.CoordinatorLayout entire_main_screen;
    Handler handler=new Handler(), killerb=new Handler();
//    Handler killer=new Handler();    //This variable is only required for the beta versions of Cryptogram.
    String y,u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.sicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Tell others about Cryptogram!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setType("text/plain");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Check out Cryptogram at: https://play.google.com/store/apps/details?id=com.volatile.cryptogram");
                startActivity(Intent.createChooser(intent,"Share via"));
            }
        });
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(this);
        Bundle bundle=new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "T1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "APP_NO_CRASH");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "LAUNCH_TEST");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        typeface=Typeface.createFromAsset(getAssets(),"agency.ttf");
        btypeface=Typeface.createFromAsset(getAssets(),"agency_bold.ttf");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        /*The section below only applies to the beta versions of Cryptogram*/
        /*                          -----START-----                        */
//        SharedPreferences sp=getSharedPreferences("Crypt Count",Context.MODE_PRIVATE);
//        boolean consent=sp.getBoolean("Beta-Consent",false);
//        AlertDialog.Builder ab=new AlertDialog.Builder(MainActivity.this);
//        ab.setCancelable(false);
//        ab.setIcon(R.drawable.sicon);
//        ab.setTitle("Disclaimer");
//        ab.setMessage("Thank you for downloading Cryptogram! This is a beta release and hence maybe unstable. You are free to opt-out of beta testing at anytime. Suggestions for improvements and bug reports would be much appreciated.");
//        ab.setPositiveButton("I agree", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                SharedPreferences sp=getSharedPreferences("Crypt Count",Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit=sp.edit();
//                edit.putBoolean("Beta-Consent",true);
//                edit.commit();
//            }
//        });
//        ab.setNegativeButton("I disagree", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                killer.post(new Runnable() {
//                    @Override
//                    public void run() {
//                       //Die!
//                    }
//                });
//            }
//        });
//        ab.create();
//        if(!consent) {
//            ab.show();
//        }
        /*                            -----END-----                        */
        /*The section above only applies to the beta versions of Cryptogram*/
        SharedPreferences sup=getSharedPreferences("com.volatile.cryptogram_preferences",Context.MODE_PRIVATE);
        String ww=sup.getString("passcode","0000a");
        final String passc=sup.getString("passcode","0000");
        Boolean myass=true;
        if(ww.equals("0000a"))myass=false;
        if(sup.getBoolean("set_en",false)&&sup.getBoolean("pass",false)&&myass){
            LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
            final View cheat=inflater.inflate(R.layout.password_dialog, null);
            AlertDialog.Builder aub=new AlertDialog.Builder(MainActivity.this);
            aub.setView(cheat);
            aub.setCancelable(false);
            aub.create();
            final AlertDialog show=aub.show();
            cancel=(Button)show.findViewById(R.id.cancel);
            grant=(Button)show.findViewById(R.id.grant);
            dpass=(EditText)show.findViewById(R.id.dpass);
            bypass=(TextView)show.findViewById(R.id.bypass);
            cancel.setText("CANCEL");
            grant.setVisibility(View.VISIBLE);
            vibe=(Vibrator)getSystemService(VIBRATOR_SERVICE);
            dpass.requestFocus();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    killerb.post(new Runnable() {
                        @Override
                        public void run() {
                            //Die!
                        }
                    });
                    finish();
                }
            });
            grant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dpass.getText().toString().equals(passc)&&g<2){
                        show.dismiss();
                        Toast.makeText(getApplicationContext(), "Access Granted!", Toast.LENGTH_SHORT).show();
                        vibe.vibrate(pattern,-1);
                    }
                    else{
                        if(g>=2){
                            bypass.setText("You have exceeded the number of password attempts allowed. Please try again later.");
                            bypass.setTextColor(getResources().getColor(R.color.Red));
                            vibe.vibrate(pattern, -1);
                            cancel.setText("OKAY");
                            InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(dpass.getWindowToken(), 0);
                            dpass.setVisibility(View.GONE);
                            grant.setVisibility(View.GONE);
                        }else {
                            g++;
                            bypass.setText("You have entered the wrong password. Please try again.");
                            bypass.setTextColor(getResources().getColor(R.color.Red));
                            vibe.vibrate(pattern, -1);
                        }
                    }
                }
            });
            dpass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId== EditorInfo.IME_ACTION_DONE) {
                        if(dpass.getText().toString().equals(passc)&&g<2){
                            show.dismiss();
                            Toast.makeText(getApplicationContext(),"Access Granted!",Toast.LENGTH_SHORT).show();
                            vibe.vibrate(pattern,-1);
                        }
                        else{
                            if(g>=2){
                                bypass.setText("You have exceeded the number of password attempts allowed. Please try again later.");
                                bypass.setTextColor(getResources().getColor(R.color.Red));
                                vibe.vibrate(pattern, -1);
                                cancel.setText("OKAY");
                                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(dpass.getWindowToken(), 0);
                                dpass.setVisibility(View.GONE);
                                grant.setVisibility(View.GONE);
                            }else {
                                g++;
                                bypass.setText("You have entered the wrong password. Please try again.");
                                bypass.setTextColor(getResources().getColor(R.color.Red));
                                vibe.vibrate(pattern, -1);
                            }
                        }
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            });
        }
        title=(TextView)findViewById(R.id.title);
        title.setTypeface(typeface);
        mesf=(TextView)findViewById(R.id.mesf);
        mesf.setTypeface(btypeface);
        mdsf=(TextView)findViewById(R.id.mdsf);
        mdsf.setTypeface(btypeface);
        dcr=(TextView)findViewById(R.id.dcr);
        dcr.setTypeface(typeface);
        enc=(TextView)findViewById(R.id.enc);
        enc.setTypeface(typeface);
        progressBar1=(ProgressBar)findViewById(R.id.progressBar1);
        progressBar2=(ProgressBar)findViewById(R.id.progressBar2);
        rldcr=(RelativeLayout)findViewById(R.id.rldcr);
        rlenc=(RelativeLayout)findViewById(R.id.rlenc);
        entire_main_screen=(android.support.design.widget.CoordinatorLayout)findViewById(R.id.entire_main_screen);
        mainTheme=(RelativeLayout)findViewById(R.id.mainTheme);
        back=(LinearLayout)findViewById(R.id.back);
        rlenc.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
                Intent intent=new Intent(MainActivity.this,NewMessage.class);
                startActivity(intent);
                finish();
            }
        });
        rldcr.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
                Intent intent=new Intent(MainActivity.this,Decrypt.class);
                startActivity(intent);
                finish();
            }
        });
        entire_main_screen.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                startActivity(new Intent(MainActivity.this,Settings.class));
            }
        });
        handler=new Handler();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enabled==true&&confirm==true){
                Snackbar.make(view, "Would you like to send a new encrypted message?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(MainActivity.this, NewMessage.class);
                                startActivity(intent);
                                finish();

                            }
                        }).show();}
                else if(enabled==false){
                    Snackbar.make(view, "Would you like to send a new encrypted message?", Snackbar.LENGTH_LONG)
                            .setAction("Yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(MainActivity.this, NewMessage.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }).show();}
                else if(enabled==true&&confirm==false){
                    Intent intent = new Intent(MainActivity.this, NewMessage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        FloatingActionButton dc = (FloatingActionButton) findViewById(R.id.dc);
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enabled==true&&confirm==true){
                Snackbar.make(view, "Would you like to decrypt a new message?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(MainActivity.this,Decrypt.class);
                                startActivity(intent);
                                finish();

                            }
                        }).show();}

                else if(enabled==false){
                    Snackbar.make(view, "Would you like to decrypt a new message?", Snackbar.LENGTH_LONG)
                            .setAction("Yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent=new Intent(MainActivity.this,Decrypt.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }).show();}

                else if(enabled==true&&confirm==false){
                    Intent intent = new Intent(MainActivity.this, Decrypt.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.slideindown, R.anim.slidedown);
        progressUpdate();
        SharedPreferences settings=getSharedPreferences("com.volatile.cryptogram_preferences",Context.MODE_PRIVATE);
        enabled=settings.getBoolean("set_en",false);
        confirm=settings.getBoolean("confirm", true);
        themer=settings.getString("theme","1");
        theme=Integer.parseInt(themer);
        if(theme==0&&enabled==true){
            mainTheme.setBackgroundColor(getResources().getColor(R.color.white));
            back.setBackgroundResource(R.drawable.white);
            title.setTextColor(getResources().getColor(R.color.black));
            mesf.setTextColor(getResources().getColor(R.color.black));
            mdsf.setTextColor(getResources().getColor(R.color.black));
            dcr.setTextColor(getResources().getColor(R.color.black));
            enc.setTextColor(getResources().getColor(R.color.black));
        }
        else if(theme==1||enabled==false){
            mainTheme.setBackgroundColor(getResources().getColor(R.color.black));
            back.setBackgroundResource(R.drawable.code);
            title.setTextColor(getResources().getColor(R.color.white));
            mesf.setTextColor(getResources().getColor(R.color.white));
            mdsf.setTextColor(getResources().getColor(R.color.white));
            dcr.setTextColor(getResources().getColor(R.color.white));
            enc.setTextColor(getResources().getColor(R.color.white));
        }
        SharedPreferences sp=getSharedPreferences("Crypt Count", Context.MODE_PRIVATE);
        int t=sp.getInt("Messages encrypted so far", 0);
        int r=sp.getInt("Messages decrypted so far", 0);
        if(t>999){
            Toast.makeText(getApplicationContext(),"Encrypt count max limit reached; resetting to zero!",Toast.LENGTH_LONG).show();
            t=0;
            SharedPreferences.Editor edit=sp.edit();
            edit.putInt("Messages encrypted so far",0);
            edit.commit();
        }
        if(r>999){
            Toast.makeText(getApplicationContext(),"Decrypt count max limit reached; resetting to zero!",Toast.LENGTH_LONG).show();
            r=0;
            SharedPreferences.Editor edit=sp.edit();
            edit.putInt("Messages decrypted so far",0);
            edit.commit();
        }
        if(t<10) {
            y = "\t\t0" + String.valueOf(t);
        }
        if(r<10) {
            u = "\t\t0" + String.valueOf(r);
        }
        if(t>=10) {
            y = "\t\t" + String.valueOf(t);
        }
        if(r>=10) {
            u = "\t\t" + String.valueOf(r);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id==R.id.settings){
            Universal.where=1;
            startActivity(new Intent(MainActivity.this,Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Progress implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            for (int i=0;i<=2000;i++){
                final int value=i;
                try{
                    Thread.sleep(1);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        progressBar1.setProgress(value);
                        progressBar2.setProgress(value);
                    }
                });
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Animation fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                    Animation fadein=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
                    progressBar1.startAnimation(fadeout);
                    progressBar2.startAnimation(fadeout);
                    mesf.startAnimation(fadeout);
                    mdsf.startAnimation(fadeout);
                    pause();
                    mesf.startAnimation(fadein);
                    mesf.setText(u);
                    mdsf.startAnimation(fadein);
                    mdsf.setText(y);
                    pause();
                    progressBar1.setVisibility(View.INVISIBLE);
                    progressBar2.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

    class Time implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            for (int i=0;i<=1;i++){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                    }
                });
            }

    }}

    private void progressUpdate(){
        new Thread(new Progress()).start();
    }

    private void pause(){
        new Thread(new Time()).start();
    }

}
