package com.cryptogram;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

/**
 * Created by siddhantvinchurkar on 4/1/16.
 */
public class NewMessage extends AppCompatActivity{

    FloatingActionButton send;
    EditText message;
    TextView liveCcount,preview;
    int min = 1,max=5,random=0,lno=0,m=0,dmin=211,dmax=999,d=0;
    Random r=new Random();
    StringBuilder sb=new StringBuilder();
    String pre;
    String themer="1";
    String packageName="nothing";
    int theme=1;
    Boolean enabled=false,confirm=true,def=false;
    LinearLayout eback;
    android.support.design.widget.CoordinatorLayout entire;

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(NewMessage.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.slideup, R.anim.slideoutup);
        SharedPreferences settings=getSharedPreferences("com.volatile.cryptogram_preferences",Context.MODE_PRIVATE);
        enabled=settings.getBoolean("set_en",false);
        confirm=settings.getBoolean("confirm",true);
        def=settings.getBoolean("def",false);
        packageName=settings.getString("defapp","nothing");
        themer=settings.getString("theme","1");
        theme=Integer.parseInt(themer);
        if(theme==0&&enabled==true){
            eback.setBackgroundColor(getResources().getColor(R.color.white));
            preview.setTextColor(getResources().getColor(R.color.black));
            message.setBackgroundResource(R.color.white);
            message.setTextColor(getResources().getColor(R.color.black));
            preview.setHintTextColor(getResources().getColor(R.color.black));
        }
        if(theme==1||enabled==false){
            eback.setBackgroundColor(getResources().getColor(R.color.black));
            preview.setTextColor(getResources().getColor(R.color.white));
            message.setBackgroundResource(R.color.black);
            message.setTextColor(getResources().getColor(R.color.white));
            preview.setHintTextColor(getResources().getColor(R.color.white));
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_support);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        random = r.nextInt((max - min) + 1) + min;
        d = r.nextInt((dmax - dmin) + 1) + dmin;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.sicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Tell others about Cryptogram!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("text/plain");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Check out Cryptogram at: https://play.google.com/store/apps/details?id=com.volatile.cryptogram");
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
        send = (FloatingActionButton) findViewById(R.id.send);
        message=(EditText)findViewById(R.id.message);
        entire=(android.support.design.widget.CoordinatorLayout)findViewById(R.id.entire);
        eback=(LinearLayout)findViewById(R.id.eback);
        entire.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeBottom() {
                Intent intent=new Intent(NewMessage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
        liveCcount=(TextView)findViewById(R.id.liveCcount);
        preview=(TextView)findViewById(R.id.preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Preview!
            }
        });
        TextWatcher textwatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                liveCcount.setText("\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+String.valueOf(100-s.length())+" characters left");
               String k= message.getText().toString();
               char[] arr = k.toCharArray();
                for(int i=0;i<arr.length;i++){
                    lno=registerchar(arr[i]);
                    char h=encrypt(random,lno);
                    sb.append(h);
                }
                pre=sb.toString();
                sb.delete(0,sb.length());
                preview.setText(pre);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        message.addTextChangedListener(textwatcher);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(message.getText().toString())) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(NewMessage.this);
                    ab.setCancelable(false);
                    ab.setIcon(R.drawable.icon);
                    ab.setTitle("Empty Message");
                    ab.setMessage("You haven't entered any message. Due to various security reasons, Cryptogram does not allow you to send empty messages. Please type in a message before sending it.");
                    ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ab.create();
                    ab.show();
                }
                if(enabled==true&&confirm==true){
                Snackbar.make(v,"Are you sure?",Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!TextUtils.isEmpty(message.getText().toString())){
                                final Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                    if(def==true){
                                    intent.setPackage(packageName);}
                                int e = random;
                                if (d % 2 == 0) {
                                    d += 1;
                                }
                                e = (e * 1000) + d;
                                int j = ((e - 111) / 2) + 3;
                                String x;
                                if (j < 1000) {
                                    x = "0" + String.valueOf(j);
                                } else {
                                    x = String.valueOf(j);
                                }
                                intent.putExtra(Intent.EXTRA_TEXT, pre + x);
                                AlertDialog.Builder ab = new AlertDialog.Builder(NewMessage.this);
                                ab.setCancelable(false);
                                ab.setTitle("Here's your Decryption Key!");
                                ab.setMessage(String.valueOf(e));
                                ab.setIcon(R.drawable.sicon);
                                ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences sp = getSharedPreferences("Crypt Count", Context.MODE_PRIVATE);
                                        int c = sp.getInt("Messages encrypted so far", 0);
                                        c++;
                                        SharedPreferences.Editor edit = sp.edit();
                                        edit.putInt("Messages encrypted so far", c);
                                        edit.commit();
                                        startActivity(Intent.createChooser(intent, "Send via"));
                                    }
                                });
                                ab.create();
                                ab.show();
                            }}
                        }).show();}
                else if(enabled==false){
                    Snackbar.make(v,"Are you sure?",Snackbar.LENGTH_LONG)
                            .setAction("Yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!TextUtils.isEmpty(message.getText().toString())){
                                        final Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_SEND);
                                        intent.setType("text/plain");
                                        if(def==true){
                                            intent.setPackage(packageName);}
                                        int e = random;
                                        if (d % 2 == 0) {
                                            d += 1;
                                        }
                                        e = (e * 1000) + d;
                                        int j = ((e - 111) / 2) + 3;
                                        String x;
                                        if (j < 1000) {
                                            x = "0" + String.valueOf(j);
                                        } else {
                                            x = String.valueOf(j);
                                        }
                                        intent.putExtra(Intent.EXTRA_TEXT, pre + x);
                                        AlertDialog.Builder ab = new AlertDialog.Builder(NewMessage.this);
                                        ab.setCancelable(false);
                                        ab.setTitle("Here's your Decryption Key!");
                                        ab.setMessage(String.valueOf(e));
                                        ab.setIcon(R.drawable.sicon);
                                        ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                SharedPreferences sp = getSharedPreferences("Crypt Count", Context.MODE_PRIVATE);
                                                int c = sp.getInt("Messages encrypted so far", 0);
                                                c++;
                                                SharedPreferences.Editor edit = sp.edit();
                                                edit.putInt("Messages encrypted so far", c);
                                                edit.commit();
                                                startActivity(Intent.createChooser(intent, "Send via"));
                                            }
                                        });
                                        ab.create();
                                        ab.show();
                                    }}
                            }).show();}
                if(enabled==true&&confirm==false){
                    if(!TextUtils.isEmpty(message.getText().toString())){
                        final Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        if(def==true){
                            intent.setPackage(packageName);}
                        int e = random;
                        if (d % 2 == 0) {
                            d += 1;
                        }
                        e = (e * 1000) + d;
                        int j = ((e - 111) / 2) + 3;
                        String x;
                        if (j < 1000) {
                            x = "0" + String.valueOf(j);
                        } else {
                            x = String.valueOf(j);
                        }
                        intent.putExtra(Intent.EXTRA_TEXT, pre + x);
                        AlertDialog.Builder ab = new AlertDialog.Builder(NewMessage.this);
                        ab.setCancelable(false);
                        ab.setTitle("Here's your Decryption Key!");
                        ab.setMessage(String.valueOf(e));
                        ab.setIcon(R.drawable.sicon);
                        ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sp = getSharedPreferences("Crypt Count", Context.MODE_PRIVATE);
                                int c = sp.getInt("Messages encrypted so far", 0);
                                c++;
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putInt("Messages encrypted so far", c);
                                edit.commit();
                                startActivity(Intent.createChooser(intent, "Send via"));
                            }
                        });
                        ab.create();
                        ab.show();}}
            }
        });



    }

    private int registerchar(char a){
        switch (a){
            case 'a':m=1;break;
            case 'A':m=1;break;
            case 'b':m=2;break;
            case 'B':m=2;break;
            case 'c':m=3;break;
            case 'C':m=3;break;
            case 'd':m=4;break;
            case 'D':m=4;break;
            case 'e':m=5;break;
            case 'E':m=5;break;
            case 'f':m=6;break;
            case 'F':m=6;break;
            case 'g':m=7;break;
            case 'G':m=7;break;
            case 'h':m=8;break;
            case 'H':m=8;break;
            case 'i':m=9;break;
            case 'I':m=9;break;
            case 'j':m=10;break;
            case 'J':m=10;break;
            case 'k':m=11;break;
            case 'K':m=11;break;
            case 'l':m=12;break;
            case 'L':m=12;break;
            case 'm':m=13;break;
            case 'M':m=13;break;
            case 'n':m=14;break;
            case 'N':m=14;break;
            case 'o':m=15;break;
            case 'O':m=15;break;
            case 'p':m=16;break;
            case 'P':m=16;break;
            case 'q':m=17;break;
            case 'Q':m=17;break;
            case 'r':m=18;break;
            case 'R':m=18;break;
            case 's':m=19;break;
            case 'S':m=19;break;
            case 't':m=20;break;
            case 'T':m=20;break;
            case 'u':m=21;break;
            case 'U':m=21;break;
            case 'v':m=22;break;
            case 'V':m=22;break;
            case 'w':m=23;break;
            case 'W':m=23;break;
            case 'x':m=24;break;
            case 'X':m=24;break;
            case 'y':m=25;break;
            case 'Y':m=25;break;
            case 'z':m=26;break;
            case 'Z':m=26;break;
            case '/':m=27;break;
            case '*':m=28;break;
            case '+':m=29;break;
            case '-':m=30;break;
            case '!':m=31;break;
            case '=':m=32;break;
            case '?':m=33;break;
            case '0':m=34;break;
            case '1':m=35;break;
            case '2':m=36;break;
            case '3':m=37;break;
            case '4':m=38;break;
            case '5':m=39;break;
            case '6':m=40;break;
            case '7':m=41;break;
            case '8':m=42;break;
            case '9':m=43;break;
            default:m=0;break;
        }
        return m;
    }
private char encrypt(int random,int charno){
    char n='a';
    charno+=random;
    switch (charno){
        case 1: n='a';break;
        case 2: n='b';break;
        case 3: n='c';break;
        case 4: n='d';break;
        case 5: n='e';break;
        case 6: n='f';break;
        case 7: n='g';break;
        case 8: n='h';break;
        case 9: n='i';break;
        case 10: n='j';break;
        case 11: n='k';break;
        case 12: n='l';break;
        case 13: n='m';break;
        case 14: n='n';break;
        case 15: n='o';break;
        case 16: n='p';break;
        case 17: n='q';break;
        case 18: n='r';break;
        case 19: n='s';break;
        case 20: n='t';break;
        case 21: n='u';break;
        case 22: n='v';break;
        case 23: n='w';break;
        case 24: n='x';break;
        case 25: n='y';break;
        case 26: n='z';break;
        case 27: n='/';break;
        case 28: n='*';break;
        case 29: n='+';break;
        case 30: n='-';break;
        case 31: n='!';break;
        case 32: n='0';break;
        case 33: n='1';break;
        case 34: n='2';break;
        case 35: n='3';break;
        case 36: n='4';break;
        case 37: n='5';break;
        case 38: n='6';break;
        case 39: n='7';break;
        case 40: n='8';break;
        case 41: n='9';break;
        case 42: n='@';break;
        case 43: n='#';break;
        case 44: n='$';break;
        case 45: n='%';break;
        case 46: n='^';break;
        case 47: n='&';break;
        case 48: n='(';break;
        case 49: n=')';break;
        case 50: n='_';break;
        default:n='a';break;
    }
    return n;
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
            Universal.where=2;
            startActivity(new Intent(NewMessage.this,Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
