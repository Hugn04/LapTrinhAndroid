package com.hugn.hugn2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Task> taskList;

    public TaskAdapter(MainActivity context, int layout, List<Task> taskList) {
        this.context = context;
        this.layout = layout;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView txtName;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txtName);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Task task = taskList.get(i);
        holder.txtName.setText(task.getTaskName());

        // Set up click listeners for edit and delete buttons
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showEditDialog(task);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.database.queryData("DELETE FROM Task WHERE id = " + task.getId());
                context.loadTasks();
            }
        });

        return view;
    }
}
