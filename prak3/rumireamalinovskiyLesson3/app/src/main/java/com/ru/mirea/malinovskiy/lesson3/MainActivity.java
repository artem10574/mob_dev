package com.ru.mirea.malinovskiy.lesson3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Обработчик нажатия для открытия браузера
    public void onClickOpenBrowser(View view) {
        Uri webpage = Uri.parse("https://www.mirea.ru/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    // Обработчик нажатия для отправки текста
    public void onClickSendText(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Привет от MIREA!");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Тема сообщения");
        startActivity(Intent.createChooser(intent, "Выберите приложение"));
    }

    // Обработчик нажатия для выбора контакта
    public void onClickPickContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 1); // Запрос кода 1 для получения результата
    }
}