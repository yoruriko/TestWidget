package com.gospelware.testwidget.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gospelware.testwidget.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @OnClick(R.id.button_test1)
    void clickBtn1() {
        Intent it = new Intent(this, Test1.class);
        startActivity(it);
    }

    @OnClick(R.id.button_test2)
    void clickBtn2() {
        Intent it = new Intent(this, ShareLocationClockActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.button_test3)
    void clickBtn3() {
        Intent it = new Intent(this, TabActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.button_test4)
    void clickBtn4() {
        Intent it = new Intent(this, CustomiseSwipeActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.button_test5)
    void clickBtn5() {
        Intent it = new Intent(this, BottomBarActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.button_test6)
    void clickBtn6() {
        Intent it = new Intent(this, BezierActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.button_test7)
    void clickBtn7(){
        Intent it=new Intent(this,BallActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.button_test8)
    void clickBtn8(){
        Intent it=new Intent(this,TestLiquidButton.class);
        startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
