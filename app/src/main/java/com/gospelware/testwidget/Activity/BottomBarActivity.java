package com.gospelware.testwidget.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gospelware.testwidget.Adapter.CustomiseSwipeAdapter;
import com.gospelware.testwidget.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 25/04/2016.
 */
public class BottomBarActivity extends AppCompatActivity {

    @BindColor(R.color.genderRed)
    int genderRed;
    @BindColor(R.color.genderBlue)
    int genderBlue;
    @BindColor(R.color.bottombar_1)
    int bottombar_1;
    @BindColor(R.color.bottombar_2)
    int bottombar_2;

    @Bind(R.id.myCoordinator)
    CoordinatorLayout coordinatorLayout;

    private static final String TAG = BottomBarActivity.class.getSimpleName();
    private BottomBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_nav_activity_layout);
        ButterKnife.bind(this);
        bottomBar = BottomBar.attachShy(coordinatorLayout,findViewById(R.id.myScrollingContent),savedInstanceState);
        init();
    }

    protected void init() {
        bottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                Log.i(TAG, "Tab selected: " + menuItemId);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                Log.i(TAG, "Tab reselected: " + menuItemId);
            }
        });

        bottomBar.mapColorForTab(0,genderRed);
        bottomBar.mapColorForTab(1,genderBlue);
        bottomBar.mapColorForTab(2,bottombar_1);
        bottomBar.mapColorForTab(3,bottombar_2);


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (bottomBar != null) {
            bottomBar.onSaveInstanceState(outState);
        }
    }
}
