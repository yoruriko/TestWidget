package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gospelware.testwidget.R;
import butterknife.ButterKnife;


/**
 * Created by ricogao on 29/04/2016.
 */
public class BezierActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bezier_activity_layout);
        ButterKnife.bind(this);
    }
}

