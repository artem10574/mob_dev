package com.ru.mirea.malinovskiy.thread;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ru.mirea.malinovskiy.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView infoTextView = binding.textView;
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());

        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БИСО-01-20, НОМЕР ПО СПИСКУ: 18, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Остров проклятых");
        infoTextView.append("\nНовое имя потока: " + mainThread.getName());


        binding.buttonMirea.setOnClickListener(v -> {
            String totalStr = binding.editTotalLessons.getText().toString();
            String daysStr = binding.editSchoolDays.getText().toString();

            if (!totalStr.isEmpty() && !daysStr.isEmpty()) {
                new Thread(new Runnable() {
                    public void run() {
                        int numberThread = counter++;
                        int total = Integer.parseInt(totalStr);
                        int days = Integer.parseInt(daysStr);
                        double avg = (double) total / days;

                        try {
                            Thread.sleep(1000); // имитация длительной операции
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(() -> {
                            binding.textView.append(String.format("\nСреднее: %.2f пар/день", avg));
                        });
                    }
                }).start();
            }
        });
    }
}