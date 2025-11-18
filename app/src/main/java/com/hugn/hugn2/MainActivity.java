package com.hugn.hugn2;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_STORAGE = 112;
    private RadioGroup radioGroup;
    private RadioButton radioButtonNho, radioButtonVua, radioButtonTo;
    private EditText editTextTongSoBai, editTextTenTep, editText1, editText2, editText3;
    private Button buttonDocLai, buttonLuuVao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        radioButtonNho = findViewById(R.id.radioButtonNho);
        radioButtonVua = findViewById(R.id.radioButtonVua);
        radioButtonTo = findViewById(R.id.radioButtonTo);
        editTextTongSoBai = findViewById(R.id.editTextTongSoBai);
        editTextTenTep = findViewById(R.id.editTextTenTep);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        buttonDocLai = findViewById(R.id.buttonDocLai);
        buttonLuuVao = findViewById(R.id.buttonLuuVao);

        buttonLuuVao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_STORAGE);
                } else {
                    luuVao();
                }
            }
        });

        buttonDocLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docLai();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void luuVao() {
        // Lưu thông tin đã chọn Options: nhỏ, vừa, to vào Ref của ứng dụng
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        String selectedOption = "";
        if (selectedId == R.id.radioButtonNho) {
            selectedOption = "nho";
        } else if (selectedId == R.id.radioButtonVua) {
            selectedOption = "vua";
        } else if (selectedId == R.id.radioButtonTo) {
            selectedOption = "to";
        }
        editor.putString("option", selectedOption);

        // Lưu tổng số bài vào Ref của ứng dụng
        editor.putString("tong_so_bai", editTextTongSoBai.getText().toString());

        // Lưu tên tệp và Ref của ứng dụng
        String tenTep = editTextTenTep.getText().toString();
        editor.putString("ten_tep", tenTep);

        editor.apply();

        // Lưu nội dung 3 dòng nhập phía dưới vào file text
        String content = editText1.getText().toString() + "\n" +
                editText2.getText().toString() + "\n" +
                editText3.getText().toString();

        FileUtils.taoFileTXT(content, tenTep + ".txt", this);
        Toast.makeText(this, "Đã lưu", Toast.LENGTH_SHORT).show();

    }

    private void docLai() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        // Load and set option
        String selectedOption = sharedPreferences.getString("option", "vua"); // default to "vua"
        if (selectedOption.equals("nho")) {
            radioGroup.check(R.id.radioButtonNho);
        } else if (selectedOption.equals("vua")) {
            radioGroup.check(R.id.radioButtonVua);
        } else if (selectedOption.equals("to")) {
            radioGroup.check(R.id.radioButtonTo);
        }

        // Load and set tong so bai
        String tongSoBai = sharedPreferences.getString("tong_so_bai", "3"); // default to "3"
        editTextTongSoBai.setText(tongSoBai);

        // Load and set ten tep
        String tenTep = sharedPreferences.getString("ten_tep", "");
        editTextTenTep.setText(tenTep);

        // Load content from file
        if (!tenTep.isEmpty()) {
            String fileContent = FileUtils.getTextFromFile(tenTep + ".txt", this);
            String[] lines = fileContent.split("\\n");
            if (lines.length >= 1) {
                editText1.setText(lines[0]);
            }
            if (lines.length >= 2) {
                editText2.setText(lines[1]);
            }
            if (lines.length >= 3) {
                editText3.setText(lines[2]);
            }
        }
        Toast.makeText(this, "Đã đọc lại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                luuVao();
            } else {
                Toast.makeText(this, "Permission denied to write to storage", Toast.LENGTH_SHORT).show();
            }
        }
    }
}