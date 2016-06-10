package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gospelware.testwidget.R;
import com.gospelware.testwidget.Widget.BounceBallView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ricogao on 03/05/2016.
 */
public class BallActivity extends AppCompatActivity {

    boolean isStart = false;

    @OnClick(R.id.ball)
    void start(BounceBallView ballView) {
        if (isStart) {
            ballView.stopMove();
        } else {
            ballView.startMove();
        }
        isStart = !isStart;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ball_activity_layout);
        ButterKnife.bind(this);
    }


}
