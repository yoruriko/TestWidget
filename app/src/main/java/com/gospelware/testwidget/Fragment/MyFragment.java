package com.gospelware.testwidget.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gospelware.testwidget.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 04/04/2016.
 */
public class MyFragment extends Fragment {

    private View content;
    @Bind(R.id.fragment_text)
    TextView fragment_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.my_fragment_layout, container, false);
        ButterKnife.bind(this, content);
        return content;
    }

    public void setText(String text){
        fragment_text.setText(text);
    }


}
