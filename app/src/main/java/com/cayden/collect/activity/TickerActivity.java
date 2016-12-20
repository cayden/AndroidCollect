package com.cayden.collect.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.cayden.collect.R;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class TickerActivity extends AppCompatActivity {
    private TickerView tickerView;
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private float index=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        tickerView = (TickerView) findViewById(R.id.tickerView);
        tickerView.setCharacterList(TickerUtils.getDefaultNumberList());
        tickerView.setTextColor(R.color.colorPrimary);
        tickerView.setTextSize(100);
        tickerView.setAnimationDuration(500);
        tickerView.setAnimationInterpolator(new OvershootInterpolator());
        tickerView.setGravity(Gravity.START);
        handler.postDelayed(runnable, 1000); //每隔1s执行

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try {
                index++;
                tickerView.setText("$"+index);
                handler.postDelayed(this, 1000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

}
