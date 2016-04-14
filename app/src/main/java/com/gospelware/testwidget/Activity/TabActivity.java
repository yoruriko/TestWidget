package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gospelware.testwidget.Adapter.MyFragmentAdapter;
import com.gospelware.testwidget.Fragment.MyFragment;
import com.gospelware.testwidget.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 04/04/2016.
 */
public class TabActivity extends AppCompatActivity{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    private List<String> titles;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initTab();
        initFragmentAdapter();
    }

    public void initTab(){
        MyFragment fragment;
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        titles.add("Bar");
        titles.add("Clubs");
        titles.add("Restaurants");
        for(String title:titles){
            fragment = new MyFragment();
            fragments.add(fragment);
            mTabs.addTab(mTabs.newTab().setText(title));
        }
    }

    public void initFragmentAdapter() {
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),titles,fragments);
        viewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(viewPager);
        mTabs.setTabsFromPagerAdapter(adapter);
    }

}
