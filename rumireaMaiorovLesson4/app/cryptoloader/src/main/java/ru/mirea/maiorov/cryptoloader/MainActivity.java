package ru.mirea.maiorov.cryptoloader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.security.InvalidParameterException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<byte[]> {

    public static final String ARG_WORD = "word";
    private static final int LoaderID = 1234;

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextInput);
        button = findViewById(R.id.EncBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encryptAndLoad();
            }
        });
    }

    private void encryptAndLoad() {
        String inputText = editText.getText().toString();
        SecretKey secretKey = generateKey();

        // Шифруем текст
        byte[] encryptedMsg = encryptMsg(inputText, secretKey);

        // Отправляем зашифрованный текст и ключ в Loader
        Bundle bundle = new Bundle();
        bundle.putByteArray(ARG_WORD, encryptedMsg);
        bundle.putByteArray("key", secretKey.getEncoded());
        LoaderManager.getInstance(this).initLoader(LoaderID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<byte[]> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LoaderID) {
            return new MyLoader(this, args.getByteArray(ARG_WORD), args.getByteArray("key"));
        }
        throw new InvalidParameterException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<byte[]> loader, byte[] data) {
        if (loader.getId() == LoaderID) {
            // Дешифрованный текст получен, отобразим его
            String decryptedText = new String(data);
            Toast.makeText(this, "Decrypted Text: " + decryptedText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<byte[]> loader) {
        // Необходимые действия при сбросе Loader
    }

    public static SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String message, SecretKey secret) {
        try {
            // Шифрование сообщения
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
