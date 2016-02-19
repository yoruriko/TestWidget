package com.gospelware.testwidget.Adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by ricogao on 16/02/2016.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(RecyclerView.ViewHolder holder, int position);

}
