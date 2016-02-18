package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gospelware.testwidget.Adapter.Test1Adapter;
import com.gospelware.testwidget.R;
import com.gospelware.testwidget.Widget.SlidingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 18/02/2016.
 */
public class Test1 extends AppCompatActivity implements Test1Adapter.OnSlidingItemClickListener {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> list;
    private Test1Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1_layout);
        ButterKnife.bind(this);


        list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add("Test " + i);
        }

        mAdapter = new Test1Adapter(list, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemContentClicked(SlidingView view, int position) {

        if (view.isMenuOpen())
            view.closeMenus();
        else
            Toast.makeText(this, "Content :" + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemEditClicked(SlidingView view, int position) {

        view.closeMenus();
        Toast.makeText(this, "Edit :" + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemDeleteClicked(int position) {
        list.remove(position);
        mAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Deleted :" + position, Toast.LENGTH_SHORT).show();
    }
}
