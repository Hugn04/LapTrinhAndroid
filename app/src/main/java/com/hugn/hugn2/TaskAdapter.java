package com.hugn.hugn2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class TaskAdapter  extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Task> taskList;


    public TaskAdapter(Context context, int layout, List<Task> taskList) {
        this.context = context;
        this.layout = layout;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txtName);
            holder.imgDelete=(ImageView) view.findViewById(R.id.imgDelete);
            holder.imgEdit=(ImageView) view.findViewById(R.id.imgEdit);
            view.setTag(holder);

            return null;

        }else {
            holder = (ViewHolder) view.getTag();
        }
        Task task = this.taskList.get(i);
        holder.txtName.setText(task.getTaskName());

        return  view;
    }
    private class  ViewHolder {
        TextView txtName;
        ImageView imgDelete, imgEdit;

    }
}
