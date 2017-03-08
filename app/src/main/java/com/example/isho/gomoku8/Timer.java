package com.example.isho.gomoku8;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity {

    CountDownTimer timer;
    TextView timerText;
    boolean running;
    boolean minuteTimer;
    long timeLeft;

    // Display format for min:sec
    public static final String FORMAT = "%02d:%02d";
    public static final int initialTime = 600000;
    public static final int minuteTime = 60000;
    public static final int timeUnit = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_screen);

        timerText = (TextView) findViewById(R.id.timer);
        timer = new MyCountDownTimer(initialTime, timeUnit, timerText);
    }

    public void startTimer(int time) {
        if (minuteTimer) {
            timeLeft = minuteTime;
            timerText.setText("01:00");
            timer.start();
        } else {
            timeLeft = time;
            timer.start();
        }
    }


    public void stopTimer() {

        timer.cancel();
    }

    public void timerReset() {
        //
    }


    public class MyCountDownTimer extends CountDownTimer {
        long initial, unit;
        boolean running = true;
        long timeLeft;
        TextView countDownText;

        public MyCountDownTimer(long initial, long unit, TextView timerText) {
            super(initial, unit);
            timeLeft = initial;
            countDownText = timerText;
        }

        @Override
        //required onTick() method
        //countDownText.setText("seconds remaining: " + millisUntilFinished / 1000);
        public void onTick(long msLeft) {
            countDownText.setText("Time: " + String.format(FORMAT,
                    TimeUnit.MILLISECONDS.toMinutes(msLeft)
            ));
        }

        public void pauseTimer() {

            this.cancel();
            running = false;
        }

        public void resumeTimer() {

            this.start();
            running = true;
        }

        //required onFinish() method
        public void onFinish() {

            countDownText.setText("Game Over!");
        }
    }
}
