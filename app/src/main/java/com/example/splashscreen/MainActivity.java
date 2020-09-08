package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCR=5000;
    Animation tooAnimation,bottomAnimaton;
    ImageView appImage;
    TextView appName,appTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tooAnimation= AnimationUtils.loadAnimation(this,R.anim.top_aimation);
        bottomAnimaton= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        appImage=findViewById(R.id.splash_Img);
        appName=findViewById(R.id.app_Name);
        appTitle=findViewById(R.id.app_Desc);
        appImage.setAnimation(tooAnimation);
        appName.setAnimation(bottomAnimaton);
        appTitle.setAnimation(bottomAnimaton);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                Pair[] pairs=new Pair[3];
                pairs[0]=new Pair<View,String>(appImage,"logo_img");
                pairs[1]=new Pair<View,String>(appName,"logo_name");
                pairs[2]=new Pair<View,String>(appTitle,"logo_title");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent,options.toBundle());
                    finish();
                }
            }
        },SPLASH_SCR);
    }
}