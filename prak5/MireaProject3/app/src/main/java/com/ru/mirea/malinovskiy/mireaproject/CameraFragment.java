package com.ru.mirea.malinovskiy.mireaproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class CameraFragment extends Fragment {

    private ImageView capturedImageView;
    private ActivityResultLauncher<Intent> photoCaptureLauncher;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_camera, container, false);

        Button captureButton = fragmentView.findViewById(R.id.take_photo_button);
        capturedImageView = fragmentView.findViewById(R.id.imageView);

        // Настройка обработчика результата съемки фото
        photoCaptureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == requireActivity().RESULT_OK) {
                        Intent resultData = result.getData();
                        if (resultData != null && resultData.getExtras() != null) {
                            Bitmap capturedImage = (Bitmap) resultData.getExtras().get("data");
                            if (capturedImage != null) {
                                capturedImageView.setImageBitmap(capturedImage);
                                showToast("Фото успешно сделано");
                            }
                        }
                    }
                });

        captureButton.setOnClickListener(v -> checkCameraPermissionAndCapture());

        return fragmentView;
    }

    private void checkCameraPermissionAndCapture() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Запрашиваем разрешение, если его нет
            requestCameraPermission();
        } else {
            // Если разрешение есть - запускаем камеру
            launchCamera();
        }
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }

    private void launchCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            photoCaptureLauncher.launch(captureIntent);
        } else {
            showToast("Не найдено приложение для работы с камерой");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                showToast("Для работы с камерой необходимо разрешение");
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}