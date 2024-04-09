package ru.mirea.maiorov.rumireamaiorovlesson4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.maiorov.rumireamaiorovlesson4.databinding.ActivityMusicPlayerBinding;

public class music_player extends AppCompatActivity {

    private ActivityMusicPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textViewTitle.setText("Song Name");
        binding.buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(music_player.class.getSimpleName(), "Play/Pause button clicked");
            }
        });
    }
}
