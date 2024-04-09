package ru.mirea.maiorov.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.Arrays;

import ru.mirea.maiorov.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private	int	counter	=	0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView infoTextView = findViewById(R.id.textViewResult);
                Thread mainThread = Thread.currentThread();
                infoTextView.setText("Имя текущего потока: " + mainThread.getName());
                mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-09-21, НОМЕР ПО СПИСКУ: 16, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: НАВЕРНОЕ ОСТРОВ ПРОКЛЯТЫХ НОРМ");
                infoTextView.append("\n Новое имя потока: " + mainThread.getName());
                Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final double averagePairsPerDay = calculateAveragePairsPerDay();

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                binding.textViewResult.setText("Среднее кол-во пар в день: " + averagePairsPerDay);
                            }
                        });
                    }
                }).start();
                binding.buttonCalculate.setOnClickListener(new	View.OnClickListener()	{
                    @Override
                    public	void	onClick(View	v)	{
                        new	Thread(new	Runnable()	{
                            public	void run()	{
                                int	numberThread=counter++;
                                Log.d("ThreadProject",	String.format("Запущен поток № %d студентом	группы № %s	номер по списку	№ %d ",	numberThread,	"БСБО-09-21",	16));
                                long	endTime	=	System.currentTimeMillis()	+	20	*	1000;
                                while	(System.currentTimeMillis()	<	endTime)	{
                                    synchronized	(this)	{
                                        try	{
                                            wait(endTime	- System.currentTimeMillis());
                                            Log.d(MainActivity.class.getSimpleName(),	"Endtime:	"	+	endTime);
                                        }	catch	(Exception	e)	{
                                            throw	new	RuntimeException(e);
                                        }
                                    }
                                    Log.d("ThreadProject",	"Выполнен поток №	"	+	numberThread);
                                }
                            }
                        }).start();
                    }
                });
            }
        });
    }

    private double calculateAveragePairsPerDay() {
        EditText editTextDays = binding.editTextText;
        EditText editTextPairs = binding.editTextText2;

        int days = Integer.parseInt(editTextDays.getText().toString());
        int pairs = Integer.parseInt(editTextPairs.getText().toString());

        return (double) pairs / days;
    }
}
