package com.cayden.collect.activity.mvp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cayden.collect.R;

public class MvpActivity extends AppCompatActivity implements MainView {

    MainPresenter presenter;

    TextView mShowTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
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

        mShowTx=(TextView)findViewById(R.id.text1);
        loadDatas();
    }

    public void loadDatas(){
        presenter=new MainPresenter().addTaskListener(this);
        presenter.getData();
    }



    @Override
    public void onShowString(String json) {
        mShowTx.setText(json);
    }
}
