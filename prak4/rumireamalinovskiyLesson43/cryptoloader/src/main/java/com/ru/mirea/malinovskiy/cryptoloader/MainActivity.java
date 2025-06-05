package com.ru.mirea.malinovskiy.cryptoloader;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 1;
    private EditText editTextInput;
    private Button buttonProcess;

    private Key secretKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.editTextInput);
        buttonProcess = findViewById(R.id.buttonProcess);

        secretKey = generateKey();

        buttonProcess.setOnClickListener(v -> {
            String input = editTextInput.getText().toString();
            if (!input.isEmpty()) {
                byte[] encrypted = encryptMsg(input, (SecretKey) secretKey);

                Bundle args = new Bundle();
                args.putByteArray(MyLoader.ARG_CIPHER_TEXT, encrypted);
                args.putByteArray("key", secretKey.getEncoded());

                LoaderManager.getInstance(this).initLoader(LOADER_ID, args, this);
            }
        });
    }

    // ====== Методы LoaderCallbacks ======
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LOADER_ID && args != null) {
            byte[] cipherText = args.getByteArray(MyLoader.ARG_CIPHER_TEXT);
            byte[] key = args.getByteArray("key");
            return new MyLoader(this, cipherText, key);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (loader.getId() == LOADER_ID && data != null) {
            Toast.makeText(this, "Расшифрованный текст: " + data, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Очистка, если нужно
    }

    // ====== Шифрование AES ======
    public static SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any_data_used_as_random_seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return kg.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String message, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}