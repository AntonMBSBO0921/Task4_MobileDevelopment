package ru.mirea.maiorov.rumireamaiorovlesson4;

import ru.mirea.maiorov.rumireamaiorovlesson4.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public	class	MainActivity	extends	AppCompatActivity	{
    private	ActivityMainBinding	binding;
    @Override
    protected	void	onCreate(Bundle	savedInstanceState)	{
        super.onCreate(savedInstanceState);
        binding	= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editTextMirea.setText("Мой	номер	по	списку	№16");
        binding.buttonMirea.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View	v)	{
                Log.d(MainActivity.class.getSimpleName(),"onClickListener");
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMusicPlayer();
            }
        });
    }

    private void openMusicPlayer() {
        Intent intent = new Intent(this, music_player.class);
        startActivity(intent);
    }
}