package com.gospelware.testwidget.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;


import com.gospelware.testwidget.Adapter.SimpleCallBack;
import com.gospelware.testwidget.Adapter.TestAdapter;
import com.gospelware.testwidget.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ricogao on 16/02/2016.
 */
public class DragAndDropActivity extends AppCompatActivity implements TestAdapter.TestItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private TestAdapter mAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_and_drop_layout);

        ButterKnife.bind(this);

        list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }

        mAdapter = new TestAdapter(list);
        mAdapter.setTestItemClickListener(this);

        ItemTouchHelper.Callback callback = new SimpleCallBack(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onTestItemClick(View view, int position) {
        Toast.makeText(this,list.get(position)+" is clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTestItemDelete(View view, int position) {
        showDeleteDialog(position);
    }

    public void showDeleteDialog(final int position) {

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Delete");
        dialog.setMessage("Are you sure to delete?");

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.notifyItemChanged(position);
            }
        });

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        dialog.show();
    }
}
