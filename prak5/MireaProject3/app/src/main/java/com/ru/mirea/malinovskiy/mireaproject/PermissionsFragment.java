package com.ru.mirea.malinovskiy.mireaproject;

public class PermissionsFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_permissions, container, false);
        Button permissionButton = rootView.findViewById(R.id.requestPermissionButton);

        permissionButton.setOnClickListener(v -> requestRuntimePermissions());

        return rootView;
    }

    private void requestRuntimePermissions() {
        String[] requiredPermissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(requireActivity(), requiredPermissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean areAllPermissionsGranted = checkAllPermissionsGranted(results);

            String message = areAllPermissionsGranted
                    ? "Все разрешения получены"
                    : "Не все разрешения предоставлены";
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkAllPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}