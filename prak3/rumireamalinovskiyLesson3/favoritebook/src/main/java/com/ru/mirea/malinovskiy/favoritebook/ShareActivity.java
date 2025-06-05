package com.ru.mirea.malinovskiy.favoritebook;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        // Получение данных из MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String bookName = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quote = extras.getString(MainActivity.QUOTES_KEY);

            TextView textViewDeveloperBook = findViewById(R.id.textViewDeveloperBook);
            textViewDeveloperBook.setText(String.format("Моя любимая книга: %s\nЦитата: %s", bookName, quote));
        }
    }

    // Метод для отправки данных обратно
    public void onSendData(android.view.View view) {
        EditText editTextBookName = findViewById(R.id.editTextBookName);
        EditText editTextQuote = findViewById(R.id.editTextQuote);

        String bookName = editTextBookName.getText().toString();
        String quote = editTextQuote.getText().toString();

        String message = String.format("Название Вашей любимой книги: %s. Цитата: %s", bookName, quote);

        Intent data = new Intent();
        data.putExtra(MainActivity.USER_MESSAGE, message);
        setResult(RESULT_OK, data);
        finish(); // Закрываем активность
    }
}