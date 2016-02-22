package com.gospelware.testwidget.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.mask)
    void clickMask() {
        animateEditText(false);
    }

    @OnClick(R.id.box)
    void clickBox() {
        if (!isEditing)
            animateEditText(true);
    }

    @OnClick(R.id.button_image)
    void clickImage() {
        if (isEditing) {
            if (TextUtils.isEmpty(edt.getText()))
                animateEditText(false);
            else
                edt.setText("");

        } else
            animateEditText(true);
    }

    private List<String> list;
    private Test1Adapter mAdapter;
    private boolean isEditing;

    private int currentEdit = -1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (msg.what == 0) {
                imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
            } else {
                imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
                if (msg.obj != null) {
                    if (currentEdit == -1)
                        list.add(0, (String) msg.obj);
                    else {
                        list.set(currentEdit, (String) msg.obj);
                        currentEdit = -1;
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1_main);
        ButterKnife.bind(this);

        initView();
        initList();
    }

    public  void  initView(){

        toolbar.setBackgroundResource(R.drawable.toolbarmask);
        toolbar.setTitle("Itineraries");
        setSupportActionBar(toolbar);

        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    animateEditText(false);
                    return true;
                }

                return false;
            }
        });
    }
    public  void initList(){

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
        else {
            Intent it = new Intent(this, DragAndDropActivity.class);
            startActivity(it);
            Toast.makeText(this, "Content :" + position, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemEditClicked(SlidingView view, int position) {

        view.closeMenus();
        currentEdit = position;
        animateEditText(true);
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
            animateEditText(false);
            return;
        }
        super.onBackPressed();
    }


    public void animateEditText(boolean open) {
        isEditing = open;

        int duration = 300;
        final View hideView = isEditing ? tv : edt;
        final View showView = isEditing ? edt : tv;


        hideView.animate()
                .alpha(0f)
                .setDuration(duration)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        startAction(showView);
                    }
                })
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        endAction(hideView);
                    }
                })
                .start();
    }

    public void startAction(View view) {

        int duration = 300;
        int rotation = isEditing ? 45 : -45;

        box.setClickable(false);
        view.setVisibility(View.VISIBLE);
        mask.setVisibility(isEditing ? View.VISIBLE : View.GONE);

        showHideKeyBoard();

        image.animate()
                .rotationBy(rotation)
                .setDuration(duration)
                .start();

        view.animate()
                .alpha(1f)
                .setDuration(duration)
                .start();
    }

    public void endAction(View view) {
        view.setVisibility(View.GONE);
        box.setClickable(true);
    }

    public void showHideKeyBoard() {
        if (isEditing) {
            if (currentEdit != -1)
                edt.setText(list.get(currentEdit));
            edt.requestFocus();
            mHandler.obtainMessage(0).sendToTarget();
        } else {
            if (TextUtils.isEmpty(edt.getText()))
                mHandler.obtainMessage(1).sendToTarget();
            else
                mHandler.obtainMessage(1, edt.getText().toString()).sendToTarget();
            edt.clearFocus();
            edt.setText("");
        }
    }


}
