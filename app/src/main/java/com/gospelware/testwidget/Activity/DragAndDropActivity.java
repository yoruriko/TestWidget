package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


import com.gospelware.testwidget.Adapter.SimpleCallBack;
import com.gospelware.testwidget.Adapter.TestAdapter;
import com.gospelware.testwidget.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 16/02/2016.
 */
public class DragAndDropActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_and_drop_layout);

        ButterKnife.bind(this);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }

        TestAdapter adapter = new TestAdapter(list,this);

        ItemTouchHelper.Callback callback=new SimpleCallBack(adapter);
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
