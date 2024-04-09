package ru.mirea.maiorov.cryptoloader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<byte[]> {

    private final byte[] encryptedMsg;
    private final byte[] key;

    public MyLoader(@NonNull Context context, byte[] encryptedMsg, byte[] key) {
        super(context);
        this.encryptedMsg = encryptedMsg;
        this.key = key;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public byte[] loadInBackground() {
        // Восстановление ключа
        SecretKey originalKey = new SecretKeySpec(key, "AES");
        // Дешифровать текст и вернуть дешифрованные данные
        return decryptMsg(encryptedMsg, originalKey);
    }

    public static byte[] decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
