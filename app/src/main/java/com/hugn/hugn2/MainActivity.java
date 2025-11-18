package com.hugn.hugn2;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ArrayList<Task> arrayTask;
    ListView lvTask;
    TaskAdapter taskAdapter;
    TextInputEditText editTextTask;
    Button buttonAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        lvTask = (ListView) findViewById(R.id.listViewTask);
        editTextTask = (TextInputEditText) findViewById(R.id.editTextTask);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        arrayTask = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, R.layout.item_task, arrayTask);
        lvTask.setAdapter(taskAdapter);


        database = new Database(this, "task.sqlite", null, 1);
        database.queryData("CREATE TABLE IF NOT EXISTS Task(id INTEGER PRIMARY KEY AUTOINCREMENT, taskName VARCHAR(50))");
        loadTasks();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = editTextTask.getText().toString();
                if (!taskName.isEmpty()) {
                    database.queryData("INSERT INTO Task VALUES(null, '" + taskName + "')");
                    loadTasks();
                    editTextTask.setText("");
                }
            }
        });
    }

    public void loadTasks() {
        arrayTask.clear();
        Cursor dataTasks = database.getData("SELECT * FROM Task");
        while (dataTasks.moveToNext()) {
            int id = dataTasks.getInt(0);
            String taskName = dataTasks.getString(1);
            arrayTask.add(new Task(id, taskName));
        }
        taskAdapter.notifyDataSetChanged();
    }

    public void showEditDialog(final Task task) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_task, null);
        final EditText editText = (EditText) dialogView.findViewById(R.id.editTextTask);
        editText.setText(task.getTaskName());

        new MaterialAlertDialogBuilder(this)
                .setTitle("Edit Task")
                .setView(dialogView)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTaskName = editText.getText().toString();
                        if (!newTaskName.isEmpty()) {
                            database.queryData("UPDATE Task SET taskName = '" + newTaskName + "' WHERE id = " + task.getId());
                            loadTasks();
                        }
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.queryData("DELETE FROM Task WHERE id = " + task.getId());
                        loadTasks();
                    }
                })
                .show();
    }
}