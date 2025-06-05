package com.ru.mirea.malinovskiy.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ru.mirea.malinovskiy.mireaproject.databinding.FragmentMicrophoneBinding;

import java.io.File;
import java.io.IOException;

public class MicrophoneFragment extends Fragment {

    private FragmentMicrophoneBinding binding;
    private MediaRecorder audioRecorder;
    private boolean isRecordingActive = false;

    private File outputDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private String outputFilePath = new File(outputDirectory, "recorded_audio.3gp").getAbsolutePath();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMicrophoneBinding.inflate(inflater, container, false);

        setupButtonListeners();

        return binding.getRoot();
    }

    private void setupButtonListeners() {
        binding.btnStop.setOnClickListener(v -> stopAudioRecording());

        binding.btnRecord.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1001);
                return;
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1002);
                return;
            }

            startAudioRecording();
        });
    }

    private void startAudioRecording() {
        try {
            audioRecorder = new MediaRecorder();
            audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            audioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            audioRecorder.setOutputFile(outputFilePath);

            audioRecorder.prepare();
            audioRecorder.start();

            isRecordingActive = true;
            updateButtonState(true);

            showToast("Запись начата");

        } catch (IOException e) {
            showToast("Ошибка запуска записи: " + e.getMessage());
            releaseRecorder();
        }
    }

    private void stopAudioRecording() {
        if (!isRecordingActive || audioRecorder == null) return;

        try {
            audioRecorder.stop();
        } catch (RuntimeException e) {
            showToast("Ошибка остановки записи");
        } finally {
            releaseRecorder();
            isRecordingActive = false;
            updateButtonState(false);
            showToast("Файл сохранён в: " + outputFilePath);
        }
    }

    private void releaseRecorder() {
        if (audioRecorder != null) {
            audioRecorder.release();
            audioRecorder = null;
        }
    }

    private void updateButtonState(boolean isRecording) {
        binding.btnRecord.setEnabled(!isRecording);
        binding.btnStop.setEnabled(isRecording);
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseRecorder();
    }
}
