package com.ru.mirea.malinovskiy.mireaproject;

import android.Manifest;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class AudioRecordFragment extends Fragment {

    // Компоненты для работы с аудио
    private MediaRecorder audioRecorder;
    private MediaPlayer audioPlayer;
    private String audioFilePath;

    // Элементы интерфейса
    private Button toggleRecordButton;
    private Button togglePlayButton;
    private TextView audioStatusText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_audio, container, false);

        // Инициализация UI элементов
        toggleRecordButton = rootView.findViewById(R.id.recordButton);
        togglePlayButton = rootView.findViewById(R.id.playButton);
        audioStatusText = rootView.findViewById(R.id.statusTextView);

        // Настройка пути для сохранения аудиофайла
        File audioDir = requireContext().getExternalFilesDir(android.os.Environment.DIRECTORY_MUSIC);
        if (audioDir != null) {
            audioFilePath = new File(audioDir, "mirea_audio_record.3gp").getAbsolutePath();
        }

        togglePlayButton.setEnabled(false);

        // Обработка нажатия кнопки записи
        toggleRecordButton.setOnClickListener(v -> {
            if (audioRecorder == null) {
                beginRecording();
                toggleRecordButton.setText("Остановить");
                togglePlayButton.setEnabled(false);
            } else {
                finishRecording();
                toggleRecordButton.setText("Записать");
                togglePlayButton.setEnabled(true);
            }
        });

        // Обработка нажатия кнопки воспроизведения
        togglePlayButton.setOnClickListener(v -> {
            if (audioPlayer == null) {
                beginPlayback();
                togglePlayButton.setText("Остановить");
                toggleRecordButton.setEnabled(false);
            } else {
                finishPlayback();
                togglePlayButton.setText("Воспроизвести");
                toggleRecordButton.setEnabled(true);
            }
        });

        return rootView;
    }

    // Запуск процесса записи аудио
    private void beginRecording() {
        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setOutputFile(audioFilePath);
        audioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            audioRecorder.prepare();
            audioRecorder.start();
            audioStatusText.setText("Запись в процессе...");
        } catch (IOException e) {
            Log.e("AudioRecorder", "Ошибка при подготовке записи", e);
        }
    }

    // Остановка записи аудио
    private void finishRecording() {
        if (audioRecorder != null) {
            audioRecorder.stop();
            audioRecorder.release();
            audioRecorder = null;
            audioStatusText.setText("Запись сохранена");
        }
    }

    // Начало воспроизведения записи
    private void beginPlayback() {
        audioPlayer = new MediaPlayer();
        try {
            audioPlayer.setDataSource(audioFilePath);
            audioPlayer.prepare();
            audioPlayer.start();
            audioStatusText.setText("Идет воспроизведение...");
        } catch (IOException e) {
            Log.e("AudioPlayer", "Ошибка при воспроизведении", e);
        }
    }

    // Остановка воспроизведения
    private void finishPlayback() {
        if (audioPlayer != null) {
            audioPlayer.release();
            audioPlayer = null;
            audioStatusText.setText("Воспроизведение завершено");
        }
    }

    @Override
    public void onStop() {
        // Освобождение ресурсов при остановке фрагмента
        if (audioRecorder != null) {
            audioRecorder.release();
            audioRecorder = null;
        }

        if (audioPlayer != null) {
            audioPlayer.release();
            audioPlayer = null;
        }

        super.onStop();
    }
}