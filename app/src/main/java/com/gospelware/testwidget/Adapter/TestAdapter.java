package com.gospelware.testwidget.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gospelware.testwidget.R;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 16/02/2016.
 */
public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private List<String> list;
    private TestItemClickListener mListener;

    public TestAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_and_drop_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
     final ViewHolder vh=((ViewHolder) holder);
        vh.textView.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTestItemClick(v,vh.getLayoutPosition());
            }
        });
    }

public interface TestItemClickListener{
    void onTestItemClick(View view, int position);
    void onTestItemDelete(View view,int position);
}


    public void setTestItemClickListener(TestItemClickListener listener){
        this.mListener=listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return true;

    }

    @Override
    public void onItemDismiss(RecyclerView.ViewHolder holder, int position) {
        mListener.onTestItemDelete(holder.itemView,position);
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_text)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
