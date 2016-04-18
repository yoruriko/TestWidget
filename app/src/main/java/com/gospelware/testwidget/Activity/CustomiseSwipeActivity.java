package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gospelware.testwidget.Adapter.CustomiseSwipeAdapter;
import com.gospelware.testwidget.R;
import com.gospelware.testwidget.Widget.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ricogao on 14/04/2016.
 */
public class CustomiseSwipeActivity extends AppCompatActivity {

    @Bind(R.id.swipeLayout)
    PullToRefreshView swipeRefreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @OnClick(R.id.startBtn) void toggleRefresh(){
        isRefresh = !isRefresh;
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

    boolean isRefresh = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customise_swipe);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + " item");
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        CustomiseSwipeAdapter adapter = new CustomiseSwipeAdapter(list);
        recyclerView.setAdapter(adapter);

    }

}
