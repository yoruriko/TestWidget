package com.gospelware.testwidget.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gospelware.testwidget.R;
import com.gospelware.testwidget.Widget.SlidingView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 18/02/2016.
 */
public class Test1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private OnSlidingItemClickListener mListener;

    public Test1Adapter(List<String> list, OnSlidingItemClickListener mListener) {
        this.list = list;
        this.mListener = mListener;
    }

    class Test1ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.button_delete)
        Button buttonDelete;
        @Bind(R.id.button_edit)
        Button buttonEdit;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.slidingView)
        SlidingView slidingView;

        int itemPosition;

        public Test1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnSlidingItemClickListener {
        void onItemContentClicked(SlidingView view, int position);

        void onItemEditClicked(SlidingView view, int position);

        void onItemDeleteClicked(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_item, parent, false);
        Test1ViewHolder vh = new Test1ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Test1ViewHolder vh = (Test1ViewHolder) holder;
        vh.itemPosition = position;

        vh.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemDeleteClicked(vh.itemPosition);
            }
        });

        vh.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemEditClicked(vh.slidingView, vh.itemPosition);
            }
        });

        vh.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemContentClicked(vh.slidingView, vh.itemPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
