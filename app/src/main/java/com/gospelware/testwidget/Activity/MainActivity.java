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

    @OnClick(R.id.button_test2) void clickBtn2(){
        Intent it=new Intent(this,ShareLocationClockActivity.class);
        startActivity(it);
    }
    @OnClick(R.id.button_test3) void clickBtn3(){
        Intent it = new Intent(this,TabActivity.class);
        startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
