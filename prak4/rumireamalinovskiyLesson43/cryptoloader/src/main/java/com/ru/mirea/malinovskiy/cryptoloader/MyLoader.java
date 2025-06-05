package com.ru.mirea.malinovskiy.cryptoloader;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import android.util.Log;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MyLoader extends AsyncTaskLoader<String> {
    private static final String TAG = "MyLoader";
    private final byte[] cipherText;
    private final byte[] keyBytes;

    public static final String ARG_CIPHER_TEXT = "cipher_text";
    public static final String ARG_KEY = "key";

    public MyLoader(Context context, byte[] cipherText, byte[] keyBytes) {
        super(context);
        this.cipherText = cipherText;
        this.keyBytes = keyBytes;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(cipherText);
            return new String(result);
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при расшифровке", e);
            return null;
        }
    }
}