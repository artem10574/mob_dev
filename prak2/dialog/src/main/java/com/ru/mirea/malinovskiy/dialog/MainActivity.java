package com.ru.mirea.malinovskiy.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Обработчик нажатия для выбора времени
    public void onClickShowTimeDialog(View view) {
        MyTimeDialogFragment timeDialogFragment = new MyTimeDialogFragment();
        timeDialogFragment.show(getSupportFragmentManager(), "timePicker");
    }

    // Обработчик нажатия для выбора даты
    public void onClickShowDateDialog(View view) {
        MyDateDialogFragment dateDialogFragment = new MyDateDialogFragment();
        dateDialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // Обработчик нажатия для показа прогресса
    public void onClickShowProgressDialog(View view) {
        MyProgressDialogFragment progressDialogFragment = new MyProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), "progressDialog");
    }

    // Обработка выбора времени
    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        String time = "Выбранное время: " + hourOfDay + ":" + minute;
        Toast.makeText(this, time, Toast.LENGTH_LONG).show();
    }

    // Обработка выбора даты
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        String date = "Выбранная дата: " + dayOfMonth + "." + (month + 1) + "." + year;
        Toast.makeText(this, date, Toast.LENGTH_LONG).show();
    }
}