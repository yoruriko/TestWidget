package com.gospelware.testwidget.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gospelware.testwidget.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 14/04/2016.
 */
public class CustomiseSwipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> items;

    public CustomiseSwipeAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_and_drop_item_layout,parent,false);
        return new customiseSwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            customiseSwipeViewHolder vh = (customiseSwipeViewHolder)holder;
            vh.itemText.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class customiseSwipeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_text)
        TextView itemText;

        public customiseSwipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
