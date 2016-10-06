package com.cryptogram;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by siddhantvinchurkar on 5/1/16.
 */
public class Decrypt extends AppCompatActivity{

    Typeface typeface;
    Button btn_decrypt,paste;
    EditText msg_decrypt,key_decrypt;
    StringBuilder sb=new StringBuilder();
    ClipboardManager clipman;
    String pasteData="";
    String themer="1";
    int theme=1;
    Vibrator vibrate;
    int r=0;
    boolean flag=true;
    Boolean enabled=false;
    LinearLayout dback;
    android.support.design.widget.CoordinatorLayout all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decrypt_support);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
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
        vibrate=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        paste=(Button)findViewById(R.id.paste);
        clipman=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            paste.setVisibility(View.VISIBLE);
            if (!(clipman.hasPrimaryClip())) {


            } else if (!(clipman.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))) {

                // since the clipboard has data but it is not plain text

            } else {

                //since the clipboard contains plain text.
                ClipData.Item item = clipman.getPrimaryClip().getItemAt(0);

                // Gets the clipboard as text.
                pasteData = item.getText().toString();

            }
        }
        typeface=Typeface.createFromAsset(getAssets(),"agency.ttf");
        btn_decrypt=(Button)findViewById(R.id.btn_decrypt);
        btn_decrypt.setTypeface(typeface);
        msg_decrypt=(EditText)findViewById(R.id.msg_decrypt);
        key_decrypt=(EditText)findViewById(R.id.key_decrypt);
        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pasteData==""){
                    Toast.makeText(getApplicationContext(),"Nothing to paste!",Toast.LENGTH_SHORT).show();
                }
                else {
                    msg_decrypt.setText(pasteData);
                }
            }
        });
        all=(android.support.design.widget.CoordinatorLayout)findViewById(R.id.all);
        dback=(LinearLayout)findViewById(R.id.dback);
        all.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
                                   public void onSwipeBottom() {
                                       Intent intent = new Intent(Decrypt.this, MainActivity.class);
                                       startActivity(intent);
                                       finish();
                                   }
                               });

            btn_decrypt.setOnClickListener(new View.OnClickListener()

                                           {
                                               @Override
                                               public void onClick(View v) {
                                                   String x = msg_decrypt.getText().toString();
                                                   char[] tree = x.toCharArray();
                                                   StringBuilder o = new StringBuilder();
                                                   if (tree.length > 4) {
                                                       o.append(tree[tree.length - 4]);
                                                       o.append(tree[tree.length - 3]);
                                                       o.append(tree[tree.length - 2]);
                                                       o.append(tree[tree.length - 1]);
                                                       String p = o.toString();
                                                       o.delete(0, o.length());
                                                       if (!isInteger(p)) {
                                                           flag = false;
                                                       }
                                                       if (isInteger(p)) {
                                                           flag = true;
                                                           r = Integer.parseInt(p);
                                                           r -= 3;
                                                           r *= 2;
                                                           r += 111;
                                                       } else {
                                                           AlertDialog.Builder ab = new AlertDialog.Builder(Decrypt.this);
                                                           ab.setCancelable(false);
                                                           ab.setIcon(R.drawable.sicon);
                                                           ab.setTitle("Unknown Format");
                                                           ab.setMessage("Cryptogram encrypts messages in a specific format. The message you wish to decrypt is not in this format. Please enter a correctly formatted message and try again.");
                                                           ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {

                                                               }
                                                           });
                                                           ab.create();
                                                           ab.show();
                                                       }
                                                   }
                                                   if (TextUtils.isEmpty(msg_decrypt.getText().toString())) {
                                                       AlertDialog.Builder abc = new AlertDialog.Builder(Decrypt.this);
                                                       abc.setCancelable(false);
                                                       abc.setIcon(R.drawable.sicon);
                                                       abc.setTitle("What to decrypt?");
                                                       abc.setMessage("There's nothing to decrypt. Enter something.");
                                                       abc.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                           @Override
                                                           public void onClick(DialogInterface dialog, int which) {

                                                           }
                                                       });
                                                       abc.create();
                                                       abc.show();
                                                   }
                                                   if (TextUtils.isEmpty(key_decrypt.getText().toString())) {
                                                       AlertDialog.Builder abc = new AlertDialog.Builder(Decrypt.this);
                                                       abc.setCancelable(false);
                                                       abc.setIcon(R.drawable.sicon);
                                                       abc.setTitle("You haven't entered a decryption key!");
                                                       abc.setMessage("Please enter a valid decryption key.");
                                                       abc.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                           @Override
                                                           public void onClick(DialogInterface dialog, int which) {

                                                           }
                                                       });
                                                       abc.create();
                                                       abc.show();
                                                       vibrate.vibrate(500);
                                                   }
                                                   if (!TextUtils.isEmpty(msg_decrypt.getText().toString()) && !TextUtils.isEmpty(key_decrypt.getText().toString())) {
                                                       String check = key_decrypt.getText().toString();
                                                       int chk = Integer.parseInt(check);
                                                       if (chk == r) {
                                                           int key = r / 1000;
                                                           String msg = msg_decrypt.getText().toString();
                                                           char[] arr = msg.toCharArray();
                                                           for (int i = 0; i < arr.length - 4; i++) {
                                                               char w = decrypt(key, arr[i]);
                                                               sb.append(w);
                                                           }
                                                           String q = sb.toString();
                                                           sb.delete(0, sb.length());
                                                           AlertDialog.Builder zx = new AlertDialog.Builder(Decrypt.this);
                                                           zx.setCancelable(false);
                                                           zx.setIcon(R.drawable.sicon);
                                                           zx.setTitle("Here's your message!");
                                                           zx.setMessage(q);
                                                           zx.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   Intent intent = new Intent(Decrypt.this, MainActivity.class);
                                                                   SharedPreferences sp = getSharedPreferences("Crypt Count", Context.MODE_PRIVATE);
                                                                   int c = sp.getInt("Messages decrypted so far", 0);
                                                                   c++;
                                                                   SharedPreferences.Editor edit = sp.edit();
                                                                   edit.putInt("Messages decrypted so far", c);
                                                                   edit.commit();
                                                                   startActivity(intent);
                                                               }
                                                           });
                                                           zx.create();
                                                           zx.show();
                                                       } else {
                                                           if (flag) {
                                                               AlertDialog.Builder abc = new AlertDialog.Builder(Decrypt.this);
                                                               abc.setCancelable(false);
                                                               abc.setIcon(R.drawable.sicon);
                                                               abc.setTitle("Invalid Decryption Key");
                                                               abc.setMessage("Please enter a valid decryption key.");
                                                               abc.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(DialogInterface dialog, int which) {

                                                                   }
                                                               });
                                                               abc.create();
                                                               abc.show();
                                                               vibrate.vibrate(500);
                                                           }
                                                       }
                                                   }
                                               }
                                           }

            );

        key_decrypt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE) {
                    String x = msg_decrypt.getText().toString();
                    char[] tree = x.toCharArray();
                    StringBuilder o = new StringBuilder();
                    if (tree.length > 4) {
                        o.append(tree[tree.length - 4]);
                        o.append(tree[tree.length - 3]);
                        o.append(tree[tree.length - 2]);
                        o.append(tree[tree.length - 1]);
                        String p = o.toString();
                        o.delete(0, o.length());
                        if (!isInteger(p)) {
                            flag = false;
                        }
                        if (isInteger(p)) {
                            flag = true;
                            r = Integer.parseInt(p);
                            r -= 3;
                            r *= 2;
                            r += 111;
                        } else {
                            AlertDialog.Builder ab = new AlertDialog.Builder(Decrypt.this);
                            ab.setCancelable(false);
                            ab.setIcon(R.drawable.sicon);
                            ab.setTitle("Unknown Format");
                            ab.setMessage("Cryptogram encrypts messages in a specific format. The message you wish to decrypt is not in this format. Please enter a correctly formatted message and try again.");
                            ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            ab.create();
                            ab.show();
                        }
                    }
                    if (TextUtils.isEmpty(msg_decrypt.getText().toString())) {
                        AlertDialog.Builder abc = new AlertDialog.Builder(Decrypt.this);
                        abc.setCancelable(false);
                        abc.setIcon(R.drawable.sicon);
                        abc.setTitle("What to decrypt?");
                        abc.setMessage("There's nothing to decrypt. Enter something.");
                        abc.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        abc.create();
                        abc.show();
                    }
                    if (TextUtils.isEmpty(key_decrypt.getText().toString())) {
                        AlertDialog.Builder abc = new AlertDialog.Builder(Decrypt.this);
                        abc.setCancelable(false);
                        abc.setIcon(R.drawable.sicon);
                        abc.setTitle("You haven't entered a decryption key!");
                        abc.setMessage("Please enter a valid decryption key.");
                        abc.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        abc.create();
                        abc.show();
                        vibrate.vibrate(500);
                    }
                    if (!TextUtils.isEmpty(msg_decrypt.getText().toString()) && !TextUtils.isEmpty(key_decrypt.getText().toString())) {
                        String check = key_decrypt.getText().toString();
                        int chk = Integer.parseInt(check);
                        if (chk == r) {
                            int key = r / 1000;
                            String msg = msg_decrypt.getText().toString();
                            char[] arr = msg.toCharArray();
                            for (int i = 0; i < arr.length - 4; i++) {
                                char w = decrypt(key, arr[i]);
                                sb.append(w);
                            }
                            String q = sb.toString();
                            sb.delete(0, sb.length());
                            AlertDialog.Builder zx = new AlertDialog.Builder(Decrypt.this);
                            zx.setCancelable(false);
                            zx.setIcon(R.drawable.sicon);
                            zx.setTitle("Here's your message!");
                            zx.setMessage(q);
                            zx.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Decrypt.this, MainActivity.class);
                                    SharedPreferences sp = getSharedPreferences("Crypt Count", Context.MODE_PRIVATE);
                                    int c = sp.getInt("Messages decrypted so far", 0);
                                    c++;
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putInt("Messages decrypted so far", c);
                                    edit.commit();
                                    startActivity(intent);
                                }
                            });
                            zx.create();
                            zx.show();
                        } else {
                            if (flag) {
                                AlertDialog.Builder abc = new AlertDialog.Builder(Decrypt.this);
                                abc.setCancelable(false);
                                abc.setIcon(R.drawable.sicon);
                                abc.setTitle("Invalid Decryption Key");
                                abc.setMessage("Please enter a valid decryption key.");
                                abc.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                abc.create();
                                abc.show();
                                vibrate.vibrate(500);
                            }
                        }
                    }
                    return true;
                }else{
                    return false;
                }
            }
        });

        }

        @Override
    public void onBackPressed() {
        Intent intent=new Intent(Decrypt.this,MainActivity.class);
        startActivity(intent);
            finish();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.slideup, R.anim.slideoutup);
        SharedPreferences settings=getSharedPreferences("com.volatile.cryptogram_preferences",Context.MODE_PRIVATE);
        enabled=settings.getBoolean("set_en",false);
        themer=settings.getString("theme","1");
        theme=Integer.parseInt(themer);
        if(theme==0&&enabled==true){
            dback.setBackgroundColor(getResources().getColor(R.color.white));
            msg_decrypt.setBackgroundResource(R.color.white);
            key_decrypt.setBackgroundResource(R.color.white);
            msg_decrypt.setTextColor(getResources().getColor(R.color.black));
            key_decrypt.setTextColor(getResources().getColor(R.color.black));
        }
        else if(theme==1||enabled==false){
            dback.setBackgroundColor(getResources().getColor(R.color.black));
            msg_decrypt.setBackgroundResource(R.color.black);
            key_decrypt.setBackgroundResource(R.color.black);
            msg_decrypt.setTextColor(getResources().getColor(R.color.white));
            key_decrypt.setTextColor(getResources().getColor(R.color.white));
        }
        super.onResume();
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
            Universal.where=3;
            startActivity(new Intent(Decrypt.this,Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private char decrypt(int key,char e){
        int n=0;
        switch(e){

            case 'a': n=1;break;
            case 'b': n=2;break;
            case 'c': n=3;break;
            case 'd': n=4;break;
            case 'e': n=5;break;
            case 'f': n=6;break;
            case 'g': n=7;break;
            case 'h': n=8;break;
            case 'i': n=9;break;
            case 'j': n=10;break;
            case 'k': n=11;break;
            case 'l': n=12;break;
            case 'm': n=13;break;
            case 'n': n=14;break;
            case 'o': n=15;break;
            case 'p': n=16;break;
            case 'q': n=17;break;
            case 'r': n=18;break;
            case 's': n=19;break;
            case 't': n=20;break;
            case 'u': n=21;break;
            case 'v': n=22;break;
            case 'w': n=23;break;
            case 'x': n=24;break;
            case 'y': n=25;break;
            case 'z': n=26;break;
            case '/': n=27;break;
            case '*': n=28;break;
            case '+': n=29;break;
            case '-': n=30;break;
            case '!': n=31;break;
            case '0': n=32;break;
            case '1': n=33;break;
            case '2': n=34;break;
            case '3': n=35;break;
            case '4': n=36;break;
            case '5': n=37;break;
            case '6': n=38;break;
            case '7': n=39;break;
            case '8': n=40;break;
            case '9': n=41;break;
            case '@': n=42;break;
            case '#': n=43;break;
            case '$': n=44;break;
            case '%': n=45;break;
            case '^': n=46;break;
            case '&': n=47;break;
            case '(': n=48;break;
            case ')': n=49;break;
            case '_': n=50;break;
            default:n=0;break;

        }
        n-=key;
        char x=tally(n);
        return x;
    }
private char tally(int n){
    char v='a';

    switch (n){

        case 1: v='a';break;
        case 2: v='b';break;
        case 3: v='c';break;
        case 4: v='d';break;
        case 5: v='e';break;
        case 6: v='f';break;
        case 7: v='g';break;
        case 8: v='h';break;
        case 9: v='i';break;
        case 11: v='k';break;
        case 12: v='l';break;
        case 13: v='m';break;
        case 14: v='n';break;
        case 15: v='o';break;
        case 16: v='p';break;
        case 17: v='q';break;
        case 18: v='r';break;
        case 19: v='s';break;
        case 20: v='t';break;
        case 21: v='u';break;
        case 22: v='v';break;
        case 23: v='w';break;
        case 24: v='x';break;
        case 25: v='y';break;
        case 26: v='z';break;
        case 27: v='/';break;
        case 28: v='*';break;
        case 29: v='+';break;
        case 30: v='-';break;
        case 31: v='!';break;
        case 32: v='=';break;
        case 33: v='?';break;
        case 34: v='0';break;
        case 35: v='1';break;
        case 36: v='2';break;
        case 37: v='3';break;
        case 38: v='4';break;
        case 39: v='5';break;
        case 40: v='6';break;
        case 41: v='7';break;
        case 42: v='8';break;
        case 43: v='9';break;
        default:v='a';

    }

    return v;
}

    private boolean isInteger(String str){
        try{
            Integer.parseInt(str);
        }catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }

}
