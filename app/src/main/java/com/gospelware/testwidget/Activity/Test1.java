package com.gospelware.testwidget.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gospelware.testwidget.Adapter.Test1Adapter;
import com.gospelware.testwidget.R;
import com.gospelware.testwidget.Widget.SlidingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ricogao on 18/02/2016.
 */
public class Test1 extends AppCompatActivity implements Test1Adapter.OnSlidingItemClickListener {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.tv)
    TextView tv;

    @Bind(R.id.edt)
    EditText edt;

    @Bind(R.id.button_image)
    ImageView image;

    @Bind(R.id.box)
    RelativeLayout box;

    @Bind(R.id.mask)
    FrameLayout mask;

    @OnClick(R.id.mask)
    void clickMask() {
        hideEditText();
    }

    @OnClick(R.id.box)
    void clickBox() {
        if (tv.isShown()) {
            showEditText();
        } else {
            hideEditText();
        }
    }

    private List<String> list;
    private Test1Adapter mAdapter;
    private boolean isEditing;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (msg.what == 0) {
                imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
            } else {
                imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
                if (msg.obj != null) {
                    list.add(0, (String) msg.obj);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dy) > 5)
                    mAdapter.parentMove();
            }
        });

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
        Toast.makeText(this, "Edit :" + list.get(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemDeleteClicked(int position) {
        Toast.makeText(this, "Deleted :" + list.get(position), Toast.LENGTH_SHORT).show();
        list.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onBackPressed() {

        if (isEditing) {
            hideEditText();
            return;
        }
        super.onBackPressed();
    }

    public void showEditText() {

        isEditing = true;

        tv.animate()
                .alpha(0f)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        box.setClickable(false);
                        edt.setVisibility(View.VISIBLE);
                        mask.setVisibility(View.VISIBLE);
                        image.animate()
                                .rotationBy(45)
                                .setDuration(300)
                                .start();
                        edt.animate()
                                .alpha(1f)
                                .setDuration(300)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        edt.requestFocus();
                                        mHandler.obtainMessage(0).sendToTarget();
                                    }
                                })
                                .start();
                    }
                })
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        tv.setVisibility(ImageView.GONE);
                        box.setClickable(true);
                    }
                })
                .setDuration(300)
                .start();
    }

    public void hideEditText() {

        isEditing = false;

        edt.animate()
                .alpha(0f)
                .setDuration(300)
                .withStartAction(
                        new Runnable() {
                            @Override
                            public void run() {

                                box.setClickable(false);
                                tv.setVisibility(View.VISIBLE);
                                mask.setVisibility(View.GONE);
                                image.animate()
                                        .rotationBy(-45)
                                        .setDuration(300)
                                        .start();
                                tv.animate()
                                        .alpha(1)
                                        .setDuration(300)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (TextUtils.isEmpty(edt.getText()))
                                                    mHandler.obtainMessage(1).sendToTarget();
                                                else
                                                    mHandler.obtainMessage(1, edt.getText().toString()).sendToTarget();
                                                edt.clearFocus();
                                                edt.setText("");
                                            }
                                        })
                                        .start();
                            }
                        }
                )
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        edt.setVisibility(View.GONE);
                        box.setClickable(true);
                    }
                })
                .start();
    }
}
