package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gospelware.liquidbutton.LiquidButton;
import com.gospelware.testwidget.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 12/05/2016.
 */
public class TestLiquidButton extends AppCompatActivity {


    @Bind(R.id.btn)
    LiquidButton btn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_liquid_layout);
        ButterKnife.bind(this);

        btn.setFillAfter(true);
        btn.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                Toast.makeText(TestLiquidButton.this, "Finish!", Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startPour();
            }
        });

    }
}
