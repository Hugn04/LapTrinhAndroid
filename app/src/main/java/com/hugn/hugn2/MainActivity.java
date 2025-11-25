package com.hugn.hugn2;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar sb1, sb2, sb3;
    TextView sc1, sc2, sc3;
    int score1 = 0;
    int score2 = 0;
    int score3 = 0;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        sb1 = findViewById(R.id.seekBar1);
        sb2 = findViewById(R.id.seekBar2);
        sb3 = findViewById(R.id.seekBar3);

        sc1 = findViewById(R.id.score1);
        sc2 = findViewById(R.id.score2);
        sc3 = findViewById(R.id.score3);

        btn = findViewById(R.id.btnStart);


        CountDownTimer countDownTimer = new CountDownTimer(60000, 30) {


            @Override
            public void onTick(long l) {
                runSeekBar(sb1, 3);
                runSeekBar(sb2, 3);
                runSeekBar(sb3, 3);
                if (sb1.getProgress() >= sb1.getMax()) {
                    score1 += 10;
                    score2 = tru(score2);
                    score3 = tru(score3);
                    btn.setVisibility(View.VISIBLE);
                    showScore();
                    btn.setText("Chơi lại");
                    this.cancel();
                }
                if (sb2.getProgress() >= sb2.getMax()) {
                    score2 += 10;
                    score1 = tru(score1);
                    score3 = tru(score3);
                    btn.setVisibility(View.VISIBLE);
                    btn.setText("Chơi lại");
                    showScore();
                    this.cancel();
                }
                if (sb3.getProgress() >= sb3.getMax()) {
                    score3 += 10;
                    score2 = tru(score2);
                    score1 = tru(score1);
                    btn.setVisibility(View.VISIBLE);
                    btn.setText("Chơi lại");
                    showScore();
                    this.cancel();
                }


            }

            @Override
            public void onFinish() {
                Log.d("UserInfo", "Hung");
            }


        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setVisibility(View.INVISIBLE);
                sb1.setProgress(0);
                sb2.setProgress(0);
                sb3.setProgress(0);
                countDownTimer.start();
            }
        });
    }

    private void runSeekBar(SeekBar sb, int speed) {
        Random random = new Random();
        int randomNumber = random.nextInt(speed);
        sb.setProgress(sb.getProgress() + randomNumber);
    }

    private int tru(int number) {
        if (number <= 0) {
            return number;
        } else {
            return number - 5;
        }


    }

    private void showScore() {
        sc1.setText("Ech: "+score1);
        sc2.setText("Pikachu: "+score2);
        sc3.setText("Rua: "+score3);
    }
}