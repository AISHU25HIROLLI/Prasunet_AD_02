package com.example.todolistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<String> taskList;
    private OnItemClickListener listener;

    public TaskAdapter(List<String> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String task = taskList.get(position);
        holder.bind(task, position + 1); // Pass position + 1 to display 1-based numbering
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void addTask(String task) {
        taskList.add(task);
        notifyItemInserted(taskList.size() - 1);
    }

    public void editTask(int position, String task) {
        taskList.set(position, task);
        notifyItemChanged(position);
    }

    public void deleteTask(int position) {
        taskList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, taskList.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewTaskNumber;
        private TextView textViewTask;

        TaskViewHolder(View itemView) {
            super(itemView);
            textViewTaskNumber = itemView.findViewById(R.id.text_view_task_number);
            textViewTask = itemView.findViewById(R.id.text_view_task);
            itemView.setOnClickListener(this);
        }

        void bind(String task, int taskNumber) {
            textViewTaskNumber.setText("TASK " + taskNumber);
            textViewTask.setText(task);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(position);
            }
        }
    }
}

