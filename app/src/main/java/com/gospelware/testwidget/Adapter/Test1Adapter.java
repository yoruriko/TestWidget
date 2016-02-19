package com.gospelware.testwidget.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gospelware.testwidget.R;
import com.gospelware.testwidget.Widget.SlidingView;
import com.gospelware.testwidget.Widget.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 18/02/2016.
 */
public class Test1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SlidingView.SlidingMenuOpenListener {

    private List<String> list;
    private OnSlidingItemClickListener mListener;
    private SlidingView openMenu;

    public Test1Adapter(List<String> list, OnSlidingItemClickListener mListener) {
        this.list = list;
        this.mListener = mListener;
    }

    @Override
    public void onMenuOpen(SlidingView view) {
        this.openMenu = view;
    }

    @Override
    public void onMenuMove(SlidingView view) {
        if (openMenu != null && openMenu != view) {
            openMenu.closeMenus();
            openMenu = null;
        }
    }

    public void parentMove() {
        if (openMenu != null) {
            openMenu.closeMenus();
            openMenu = null;
        }
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
            content.getLayoutParams().width = Utils.getScreenWidth(itemView.getContext());

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

        vh.slidingView.setSlidingMenuOpenListener(this);

        vh.itemPosition = position;

        vh.content.setText(list.get(position));

        vh.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemDeleteClicked(vh.getLayoutPosition());
            }
        });

        vh.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemEditClicked(vh.slidingView, vh.getLayoutPosition());
            }
        });

        vh.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openMenu != null) {
                    openMenu.closeMenus();
                    openMenu = null;
                } else
                    mListener.onItemContentClicked(vh.slidingView, vh.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
