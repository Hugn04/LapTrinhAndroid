package com.hugn.hugn2;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ArrayList<Task> arrayTask;
    ListView lvTask;
    TaskAdapter taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        lvTask = (ListView) findViewById(R.id.listViewTask);
        arrayTask = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, R.layout.item_task, arrayTask);
        lvTask.setAdapter(taskAdapter);


        database = new Database(this, "task.sqlite", null, 1);
        database.queryData("CREATE TABLE IF NOT EXISTS Task(id INTEGER PRIMARY KEY AUTOINCREMENT, taskName VARCHAR(50), status VARCHAR(10)");
        database.queryData("INSERT INOT Task VALUES(null, 'Làm bài tập', 'Đang làm')");
        Cursor dataTasks = database.getData("SELECT * FROM Task");
        while (dataTasks.moveToNext()) {
            String taskName = dataTasks.getString(1);
            Toast.makeText(this, taskName, Toast.LENGTH_SHORT).show();

        }


    }
}