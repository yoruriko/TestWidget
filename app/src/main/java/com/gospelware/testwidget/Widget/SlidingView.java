package com.gospelware.testwidget.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.gospelware.testwidget.R;

/**
 * Created by ricogao on 18/02/2016.
 */
public class SlidingView extends HorizontalScrollView {

    private static final String TAG = SlidingView.class.getSimpleName();
    private static final int EDIT_FLAG = 111;
    private static final int DELETE_FLAG = 222;
    private static final int NO_FLAG = -1;


    private boolean isDeleteOpen;
    private boolean isEditOpen;
    private boolean isMeasured;

    private Button buttonEdit;
    private Button buttonDelete;
    private TextView textView;

    private int buttonWidth;

    private SlidingMenuOpenListener mListener;


    public SlidingView(Context context) {
        super(context);
    }

    public SlidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public interface SlidingMenuOpenListener {
        void onMenuOpen(SlidingView view);
        void onMenuMove(SlidingView view);
    }

    public void setSlidingMenuOpenListener(SlidingMenuOpenListener listener) {
        this.mListener = listener;
    }

    public void initMeasure() {
        if (!isMeasured) {

            textView = (TextView) findViewById(R.id.content);
            buttonDelete = (Button) findViewById(R.id.button_delete);
            buttonEdit = (Button) findViewById(R.id.button_edit);

            isMeasured = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        initMeasure();

        this.scrollTo(buttonEdit.getWidth(), 0);

        buttonWidth = Math.max(buttonDelete.getWidth(), buttonEdit.getWidth());

    }


    public boolean isMenuOpen() {
        return isDeleteOpen || isEditOpen;
    }

    public void closeMenus() {

        smoothScrollTo(buttonWidth, 0);
        setMenuFlag(NO_FLAG);

        Log.i(TAG, "Menu Close");

    }

    public void setMenuFlag(int flag) {

        if (flag == EDIT_FLAG) {

            isEditOpen = true;
            mListener.onMenuOpen(this);

        } else if (flag == DELETE_FLAG) {

            isDeleteOpen = true;
            mListener.onMenuOpen(this);

        } else {

            isEditOpen = false;
            isDeleteOpen = false;

        }

        Log.i(TAG, "editFlag=" + isEditOpen + " ,deleteFlag=" + isDeleteOpen);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                mListener.onMenuMove(this);
                break;
            case MotionEvent.ACTION_UP:
                changeScrollX();
                return true;
        }


        return super.onTouchEvent(ev);
    }

    public void changeScrollX() {

        int dx = getScrollX();

        if (dx >= (2 * buttonWidth) - (buttonWidth / 2)) {

            smoothScrollTo(2 * buttonWidth, 0);

            setMenuFlag(DELETE_FLAG);

        } else if (dx >= (buttonWidth / 2)) {

            closeMenus();

        } else {

            this.smoothScrollTo(0, 0);

            setMenuFlag(EDIT_FLAG);
        }

    }


}
