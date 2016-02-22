package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gospelware.testwidget.R;
import com.gospelware.testwidget.Widget.ShareLocationClock;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 22/02/2016.
 */
public class ShareLocationClockActivity extends AppCompatActivity {

    @Bind(R.id.clock)
    ShareLocationClock clock;

    @Bind(R.id.seekbar)
    SeekBar seekBar;

    @Bind(R.id.text_time_remain)
    TextView tv;

    private int time = 60 * 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_location_clock_main);
        ButterKnife.bind(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clock.setProgress(progress);
                setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void setText(int progress) {

        float ratio = ((float) (100-progress) / 100f);

        float tLeft = (float) time * ratio;

        int h=(int)tLeft/60;

        int m=(int)tLeft%60;

        tv.setText(h+"h "+m+"m");
    }
}
