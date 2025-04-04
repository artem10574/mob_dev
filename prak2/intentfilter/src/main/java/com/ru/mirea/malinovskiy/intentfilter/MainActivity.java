package com.ru.mirea.malinovskiy.intentfilter;

import android.content.Intent;
import android.view.View;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Обработчик нажатия для открытия браузера
    public void openBrowser(View view) {
        // Создаем URI с адресом сайта
        Uri address = Uri.parse("https://www.mirea.ru/?ysclid=m91mp7m280800211041");
        // Создаем Intent для действия ACTION_VIEW
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
        // Проверяем, есть ли приложения, способные обработать этот Intent
        if (openLinkIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(openLinkIntent);
        }
    }

    // Обработчик нажатия для отправки данных
    public void shareData(View view) {
        // Создаем Intent для действия ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        // Указываем тип данных (текст)
        shareIntent.setType("text/plain");
        // Добавляем дополнительные данные
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "ФАМИЛИЯ ИМЯ ОТЧЕСТВО");
        // Создаем диалог выбора приложения для отправки данных
        startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"));
    }
}