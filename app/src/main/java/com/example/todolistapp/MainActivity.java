package com.example.todolistapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private EditText editTextTask;
    private Button buttonAddTask, buttonEditTask, buttonDeleteTask;
    private TaskAdapter taskAdapter;
    private List<String> taskList;
    private int selectedTaskPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTasks = findViewById(R.id.recycler_view_tasks);
        editTextTask = findViewById(R.id.edit_text_task);
        buttonAddTask = findViewById(R.id.button_add_task);
        buttonEditTask = findViewById(R.id.button_edit_task);
        buttonDeleteTask = findViewById(R.id.button_delete_task);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectedTaskPosition = position;
                editTextTask.setText(taskList.get(position));
                buttonAddTask.setVisibility(View.GONE);
                buttonEditTask.setVisibility(View.VISIBLE);
                buttonDeleteTask.setVisibility(View.VISIBLE);
            }
        });

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    if (selectedTaskPosition == -1) {
                        taskAdapter.addTask(task);
                    } else {
                        taskAdapter.editTask(selectedTaskPosition, task);
                        selectedTaskPosition = -1;
                        buttonAddTask.setVisibility(View.VISIBLE);
                        buttonEditTask.setVisibility(View.GONE);
                        buttonDeleteTask.setVisibility(View.GONE);
                    }
                    editTextTask.getText().clear();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTask = editTextTask.getText().toString().trim();
                if (!updatedTask.isEmpty()) {
                    taskAdapter.editTask(selectedTaskPosition, updatedTask);
                    selectedTaskPosition = -1;
                    buttonAddTask.setVisibility(View.VISIBLE);
                    buttonEditTask.setVisibility(View.GONE);
                    buttonDeleteTask.setVisibility(View.GONE);
                    editTextTask.getText().clear();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskAdapter.deleteTask(selectedTaskPosition);
                selectedTaskPosition = -1;
                buttonAddTask.setVisibility(View.VISIBLE);
                buttonEditTask.setVisibility(View.GONE);
                buttonDeleteTask.setVisibility(View.GONE);
                editTextTask.getText().clear();
            }
        });
    }
}
