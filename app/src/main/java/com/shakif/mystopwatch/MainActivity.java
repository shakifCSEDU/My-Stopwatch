package com.shakif.mystopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private Button start,stop,reset;
    private int seconds = 0;
    private boolean running,wasrunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    if(savedInstanceState != null)
    {
        seconds = savedInstanceState.getInt("seconds");
        running = savedInstanceState.getBoolean("running");
    }
    textView = (TextView)findViewById(R.id.textViewId);
    start = (Button)findViewById(R.id.startId);
    stop = (Button)findViewById(R.id.stopId);
    reset = (Button)findViewById(R.id.resetId);
    start.setOnClickListener(this);
    stop.setOnClickListener(this::onClick);
    reset.setOnClickListener(this::onClick);
    runtimer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds",seconds);
        outState.putBoolean("running",running);
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasrunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(wasrunning)
            running  = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasrunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasrunning)
            running = true;
    }

    private void runtimer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                textView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this,500);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.stopId)
        {
            running = false;
        }
        if(v.getId() == R.id.startId)
        {
            running = true;
        }
        if(v.getId() == R.id.resetId)
        {
            running = false;
            seconds = 0;
        }
    }



}