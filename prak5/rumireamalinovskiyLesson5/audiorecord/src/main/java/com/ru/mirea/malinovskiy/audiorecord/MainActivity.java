package com.ru.mirea.malinovskiy.audiorecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 200;
    private boolean isWork = false;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String recordFilePath;
    private boolean isRecording = true;
    private boolean isPlaying = true;

    private Button btnRecord;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация View
        btnRecord = findViewById(R.id.recordButton);
        btnPlay = findViewById(R.id.playButton);

        // Путь к файлу
        recordFilePath = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "audiorecordtest.3gp").getAbsolutePath();

        // Проверка разрешений
        checkPermissions();

        // Обработчики событий
        btnRecord.setOnClickListener(v -> {
            if (isWork) {
                if (isRecording) {
                    startRecording();
                    btnRecord.setText("Остановить");
                    btnPlay.setEnabled(false);
                } else {
                    stopRecording();
                    btnRecord.setText("Начать запись");
                    btnPlay.setEnabled(true);
                }
                isRecording = !isRecording;
            }
        });

        btnPlay.setOnClickListener(v -> {
            if (isWork) {
                if (isPlaying) {
                    startPlaying();
                    btnPlay.setText("Остановить");
                    btnRecord.setEnabled(false);
                } else {
                    stopPlaying();
                    btnPlay.setText("Воспроизвести");
                    btnRecord.setEnabled(true);
                }
                isPlaying = !isPlaying;
            }
        });
    }

    private void checkPermissions() {
        int audioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (audioPermission == PackageManager.PERMISSION_GRANTED &&
                storagePermission == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isWork = true;
            } else {
                finish();
            }
        }
    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordFilePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            Log.e("AudioRecord", "Prepare failed", e);
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void startPlaying() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(recordFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e("AudioRecord", "Prepare failed", e);
        }
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}