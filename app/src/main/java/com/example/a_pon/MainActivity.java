package com.example.a_pon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView startLabel;
    private TextView gameOver;
    private ImageView box;
    private ImageView ball;

    private int frameWidth;
    private int frameHeight;
    private int boxSizeX;
    private int boxSizeY;

    private float ballX;
    private float ballY;
    private float boxX;
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private boolean action_flg = false;
    private boolean start_flg = false;
    private boolean vectorX_flg = false;
    private boolean vectorY_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startLabel = findViewById(R.id.startLabel);
        gameOver = findViewById(R.id.gameover);
        box = findViewById(R.id.box);
        ball = findViewById(R.id.ball);

        gameOver.setVisibility(View.INVISIBLE);
    }

    public void changePos() {
        hitCheck();

        if(vectorX_flg) {
            ballX -= 12;
        }
        else {
            ballX += 12;
        }
        if(vectorY_flg) {
            ballY -= 12;
        }
        else {
            ballY += 12;
        }
        if (ballX == frameWidth) {
            vectorX_flg = true;
        }
        if(ballX == 0) {
            vectorX_flg = false;
        }
        if (ballY == frameHeight) {
            gameOver.setVisibility(View.VISIBLE);
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
        if(ballY == 0) {
            vectorY_flg = false;
        }

        ball.setX(ballX);
        ball.setY(ballY);

        if (action_flg) {
            boxX -= 20;

        } else {
            boxX += 20;
        }

        if (boxX < 0) boxX = 0;

        if (boxX > frameWidth - boxSizeX) boxX = frameWidth - boxSizeX;

        box.setX(boxX);
    }

    public void hitCheck() {
        float ballCenterX = ballX + ball.getWidth();
        float ballCenterY = ballY + ball.getHeight();

        if (ballCenterX <= boxX + boxSizeX && ballCenterX >= boxX && ballCenterY == (frameHeight + boxSizeY)) {
            vectorY_flg = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!start_flg) {

            start_flg = true;

            FrameLayout frame = findViewById(R.id.frame);
            frameWidth = frame.getWidth();
            frameHeight = frame.getHeight();

            boxX = box.getX();
            boxSizeX = box.getWidth();
            boxSizeY = box.getHeight();

            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;

            }
        }
        return true;
    }
}