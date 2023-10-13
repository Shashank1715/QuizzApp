package com.example.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_screen extends AppCompatActivity {
    ImageView image1;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        image1= findViewById(R.id.imageView1);
        name= findViewById(R.id.textView1);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_file);
        name.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(Splash_screen.this,Login_page.class);
                startActivity(i);
                finish();
            }
        },5000);
    }
}