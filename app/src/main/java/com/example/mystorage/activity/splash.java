package com.example.mystorage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mystorage.R;
import com.example.mystorage.util.SharedPreference;

public class splash extends AppCompatActivity {

    ImageView logo;
    TextView name,own1,own2;

    Animation topAnim,bottomAnim;

    SharedPreference sharedPreference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        logo=findViewById(R.id.logoimg);
        name=findViewById(R.id.logonameimg);
        own1=findViewById(R.id.ownone);
        own2=findViewById(R.id.owntwo);
        sharedPreference=new SharedPreference(this);

        //if show error on top animation then enter alt+enter and click 1st opter and create file then go to res and code in bot and top anim
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        logo.setAnimation(topAnim);
        name.setAnimation(bottomAnim);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sharedPreference.isLogin())
                {
                    Intent intent= new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent= new Intent(splash.this, LoginUi.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 4000);
    }
}