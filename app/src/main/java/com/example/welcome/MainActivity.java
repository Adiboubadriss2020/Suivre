package com.example.welcome;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        TextView abt = (TextView) findViewById(R.id.abt);
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        textView.startAnimation(animation);
       final Intent i = new Intent(this,MapsActivity.class);
        Thread timer = new Thread(){
            public void run ()
            {
                try {
                   sleep(5000);
                }catch (Exception e)
                {

                }finally {
                    startActivity(i);
                  System.exit(0);

                }
            }
        };
        timer.start();
    }
}
