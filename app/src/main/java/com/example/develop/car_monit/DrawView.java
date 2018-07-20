package com.example.develop.car_monit;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static com.example.develop.car_monit.MainActivity.getUdpFlag;

/**
 * Created by Jimmy Liang on 2018/7/13.
 */

public class DrawView extends View {
    private String TAG = DrawView.class.getSimpleName();    //Log TAG
    Paint paint = new Paint();  //paint for current car
    Paint smallPaiint = new Paint();    //paint for past & future car

    //setColor count up
    int color_loop_past = 0;
    int color_loop_future = 0;

    //Paint color value
    int car1_color = ContextCompat.getColor(getContext(), R.color.car1);
    int car2_color = ContextCompat.getColor(getContext(), R.color.car2);
    int car3_color = ContextCompat.getColor(getContext(), R.color.car3);
    int car4_color = ContextCompat.getColor(getContext(), R.color.car4);
    int car5_color = ContextCompat.getColor(getContext(), R.color.car5);
    int car6_color = ContextCompat.getColor(getContext(), R.color.car6);
    int car7_color = ContextCompat.getColor(getContext(), R.color.car7);
    int car8_color = ContextCompat.getColor(getContext(), R.color.car8);
    int car9_color = ContextCompat.getColor(getContext(), R.color.car9);
    int car10_color = ContextCompat.getColor(getContext(), R.color.car10);
    int car11_color = ContextCompat.getColor(getContext(), R.color.car11);
    int car12_color = ContextCompat.getColor(getContext(), R.color.car12);
    int car13_color = ContextCompat.getColor(getContext(), R.color.car13);


    public DrawView(Context context) {
        super(context, null);
        setWillNotDraw(false);

        smallPaiint.setColor(Color.BLACK);
        smallPaiint.setAlpha(150);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        setWillNotDraw(false);

        smallPaiint.setColor(Color.BLACK);
        smallPaiint.setAlpha(150);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //you can also init your attributes here (if you have any)
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "Inside onDraw function.");

        //check first udp receive update flag
        if (getUdpFlag) {

            //Past position onDraw
            for (int i = 0; i < 780; i += 2) {
                setPaintAttr(smallPaiint, color_loop_past, 50); //switch the color type of each 13 cars

                //check x y position is -1.0, if yes then skip it
                if (!MainActivity.past_position[i + 1].equals("-1.0") && !MainActivity.past_position[i].equals("-1.0")) {
//                    Log.i(TAG, "past position:" + (Float.parseFloat(MainActivity.past_position[i + 1]) - 105) * 23 + "," + (720 - ((Float.parseFloat(MainActivity.past_position[i]) - 309) * 23)));
                    canvas.drawCircle((Float.parseFloat(MainActivity.past_position[i + 1]) - 105) * 23, 720 - ((Float.parseFloat(MainActivity.past_position[i]) - 309) * 23), 5, smallPaiint);
                }

                //calculate setColor count up
                if (color_loop_past == 24) {
                    color_loop_past = 0;
                } else {
                    color_loop_past += 2;
                }
            }

            //Current position onDraw
            for (int i = 0; i < 26; i += 2) {
                setPaintAttr(paint, i, 255);    //switch the color type of each 13 cars

                //check x y position is -1.0, if yes then skip it
                if (!MainActivity.current_position[i + 1].equals("-1.0") && !MainActivity.current_position[i].equals("-1.0")) {
//                    Log.i(TAG, "current position:" + (Float.parseFloat(MainActivity.current_position[i + 1]) - 105) * 23 + "," + (720 - ((Float.parseFloat(MainActivity.current_position[i]) - 309) * 23)));
                    canvas.drawCircle((Float.parseFloat(MainActivity.current_position[i + 1]) - 105) * 23, 720 - ((Float.parseFloat(MainActivity.current_position[i]) - 309) * 23), 10, paint);
                }
            }

            //future position onDraw
            for (int i = 0; i < 780; i += 2) {
                setPaintAttr(smallPaiint, color_loop_future, 100);  //switch the color type of each 13 cars

                //check x y position is -1.0, if yes then skip it
                if (!MainActivity.future_position[i + 1].equals("-1.0") && !MainActivity.future_position[i].equals("-1.0")) {
//                    Log.i(TAG, "future position:" + (Float.parseFloat(MainActivity.future_position[i + 1]) - 105) * 23 + "," + (720 - ((Float.parseFloat(MainActivity.future_position[i]) - 309) * 23)));
                    canvas.drawCircle((Float.parseFloat(MainActivity.future_position[i + 1]) - 105) * 23, 720 - ((Float.parseFloat(MainActivity.future_position[i]) - 309) * 23), 7, smallPaiint);
                }

                //calculate setColor count up
                if (color_loop_future == 24) {
                    color_loop_future = 0;
                } else {
                    color_loop_future += 2;
                }
            }
        }
    }

    //switch the color type of each 13 cars
    public void setPaintAttr(Paint paints, int count, int alpha) {
        switch (count) {
            case 0:
                paints.setColor(car1_color);
                paints.setAlpha(alpha);
                break;
            case 2:
                paints.setColor(car2_color);
                paints.setAlpha(alpha);
                break;
            case 4:
                paints.setColor(car3_color);
                paints.setAlpha(alpha);
                break;
            case 6:
                paints.setColor(car4_color);
                paints.setAlpha(alpha);
                break;
            case 8:
                paints.setColor(car5_color);
                paints.setAlpha(alpha);
                break;
            case 10:
                paints.setColor(car6_color);
                paints.setAlpha(alpha);
                break;
            case 12:
                paints.setColor(car7_color);
                paints.setAlpha(alpha);
                break;
            case 14:
                paints.setColor(car8_color);
                paints.setAlpha(alpha);
                break;
            case 16:
                paints.setColor(car9_color);
                paints.setAlpha(alpha);
                break;
            case 18:
                paints.setColor(car10_color);
                paints.setAlpha(alpha);
                break;
            case 20:
                paints.setColor(car11_color);
                paints.setAlpha(alpha);
                break;
            case 22:
                paints.setColor(car12_color);
                paints.setAlpha(alpha);
                break;
            case 24:
                paints.setColor(car13_color);
                paints.setAlpha(alpha);
                break;
        }
    }
}