package com.ru.mirea.malinovskiy.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickNewActivity(View view) {
        // Создаем Intent для запуска SecondActivity
        Intent intent = new Intent(this, SecondActivity.class);


        startActivity(intent);
    }
}