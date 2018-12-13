package com.example.develop.car_monit;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.Tag;
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
    Paint ppp = new Paint();

    //setColor count up
    int color_loop_past = 0;
    int color_loop_current = 0;
    int color_loop_future = 0;

    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car);

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
    int car14_color = ContextCompat.getColor(getContext(), R.color.car14);
    int car15_color = ContextCompat.getColor(getContext(), R.color.car15);
    int car16_color = ContextCompat.getColor(getContext(), R.color.car16);
    int car17_color = ContextCompat.getColor(getContext(), R.color.car17);
    int car18_color = ContextCompat.getColor(getContext(), R.color.car18);
    int car19_color = ContextCompat.getColor(getContext(), R.color.car19);
    int car20_color = ContextCompat.getColor(getContext(), R.color.car20);


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

        //Canvas width:877 ; height: 721
          //paint for past & future car
//        canvas.drawCircle(0, 0 , 20, smallPaiint);
//        canvas.drawCircle(730, 730 , 20, smallPaiint);
//
//        for(int i = 0; i <= 30; i +=2){
//            setPaintAttr(ppp, i, 100); //switch the color type of each 13 cars
//            switchRect(i,canvas);
//        }


        //check first udp receive update flag
        if (getUdpFlag) {

            //Hot area/region onDraw
            for (int i = 0; i < 16; i++){
//                Log.d(TAG, "hotarea : " + i + "," + MainActivity.hotSpotArea[i]);
                if(MainActivity.hotSpotArea[i].equals("0")){
                    setPaintAttr(ppp, 10, 0);
                    switchRect(i,canvas);
                } else {
                    switch (MainActivity.hotSpotArea[i]){
                        case "1" :
                            ppp.setStrokeWidth((float) 6.0);
                            ppp.setStyle(Paint.Style.STROKE);
                            setPaintAttr(ppp, 4, 150); //switch the color type of each 13 cars
                            switchRect(i,canvas);
                            break;
                        case "2" :
                            ppp.setStrokeWidth((float) 6.0);
                            ppp.setStyle(Paint.Style.STROKE);
                            setPaintAttr(ppp, 16, 150); //switch the color type of each 13 cars
                            switchRect(i,canvas);
                            break;
                        case "3" :
                            ppp.setStrokeWidth((float) 6.0);
                            ppp.setStyle(Paint.Style.STROKE);
                            setPaintAttr(ppp, 10, 150); //switch the color type of each 13 cars
                            switchRect(i,canvas);
                            break;
                    }
                }
            }

            //Past position onDraw
            for (int i = 0; i < Integer.parseInt(MainActivity.heading[0]) * 7 * 2 - (Integer.parseInt(MainActivity.heading[0]) * 2); i += 2) {
                setPaintAttr(smallPaiint, 2, 50); //switch the color type of each 13 cars

                //check x y position is -1.0, if yes then skip it
                if (!MainActivity.past_position[i + 1].equals("-1.0") && !MainActivity.past_position[i].equals("-1.0")) {
//                    Log.i(TAG, "past position:" + Float.parseFloat(MainActivity.past_position[i + 1])  + "," + Float.parseFloat(MainActivity.past_position[i]));
                    canvas.drawCircle(Float.parseFloat(MainActivity.past_position[i])/(float)0.0342465753424658 , Float.parseFloat(MainActivity.past_position[i + 1])/(float)0.0342465753424658, 5, smallPaiint);
                }

                //calculate setColor count up
                if (color_loop_past == (Integer.parseInt(MainActivity.heading[0]) - 1) * 2) {
                    color_loop_past = 0;
                } else {
                    color_loop_past += 2;
                }
            }

            //Current position onDraw
            for (int i = 0; i < Integer.parseInt(MainActivity.heading[0]) * 2; i += 2) {
                setPaintAttr(paint, 2, 255);    //switch the color type of each 13 cars

                //check x y position is -1.0, if yes then skip it
                if (!MainActivity.current_position[i + 1].equals("-1.0") && !MainActivity.current_position[i].equals("-1.0")) {
                    canvas.drawBitmap(bitmap, Float.parseFloat(MainActivity.current_position[i])/(float)0.0342465753424658-30, Float.parseFloat(MainActivity.current_position[i + 1])/(float)0.0342465753424658-20, paint);
//                    Bitmap bitmap = new Bitmap(R.drawable.car);
//                    canvas.drawBitmap();
//                    Log.i(TAG, "current position:" + (Float.parseFloat(MainActivity.current_position[i + 1]) - 105) * 23 + "," + (720 - ((Float.parseFloat(MainActivity.current_position[i]) - 309) * 23)));
//                    canvas.drawCircle(Float.parseFloat(MainActivity.current_position[i])/(float)0.0342465753424658 , Float.parseFloat(MainActivity.current_position[i + 1])/(float)0.0342465753424658, 10, paint);
                }

                //calculate setColor count up
                if (color_loop_current ==  (Integer.parseInt(MainActivity.heading[0]) - 1) * 2) {
                    color_loop_current = 0;
                } else {
                    color_loop_current += 2;
                }
            }

            //future position onDraw
            for (int i = 0; i < Integer.parseInt(MainActivity.heading[0]) * 7 * 2; i += 2) {

                if (!MainActivity.future_position[i + 1].equals("-1.0") && !MainActivity.future_position[i].equals("-1.0")) {
                    if( i >= Integer.parseInt(MainActivity.heading[0]) * 7 * 2 - Integer.parseInt(MainActivity.heading[0])*2){
                        setPaintAttr(smallPaiint, 4, 200);   //switch the color type red for last point
                        canvas.drawCircle(Float.parseFloat(MainActivity.future_position[i])/(float)0.0342465753424658 , Float.parseFloat(MainActivity.future_position[i + 1])/(float)0.0342465753424658, 13, smallPaiint);
                    }else{
                        setPaintAttr(smallPaiint, 2, 100);  //switch the color type of each 13 cars
                        canvas.drawCircle(Float.parseFloat(MainActivity.future_position[i])/(float)0.0342465753424658 , Float.parseFloat(MainActivity.future_position[i + 1])/(float)0.0342465753424658, 7, smallPaiint);
                    }
                }


//                    Log.i(TAG, "future position:" + (Float.parseFloat(MainActivity.future_position[i + 1]) - 105) * 23 + "," + (720 - ((Float.parseFloat(MainActivity.future_position[i]) - 309) * 23)));



                //calculate setColor count up
                if (color_loop_future == (Integer.parseInt(MainActivity.heading[0]) - 1) * 2) {
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
                paints.setStrokeWidth(50.3f);
                paints.setFontFeatureSettings("Perhaps");
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
            case 26:
                paints.setColor(car14_color);
                paints.setAlpha(alpha);
                break;
            case 28:
                paints.setColor(car15_color);
                paints.setAlpha(alpha);
                break;
            case 30:
                paints.setColor(car16_color);
                paints.setAlpha(alpha);
                break;
            case 32:
                paints.setColor(car17_color);
                paints.setAlpha(alpha);
                break;
            case 34:
                paints.setColor(car18_color);
                paints.setAlpha(alpha);
                break;
            case 36:
                paints.setColor(car19_color);
                paints.setAlpha(alpha);
                break;
            case 38:
                paints.setColor(car20_color);
                paints.setAlpha(alpha);
                break;
        }
    }

    public void switchRect(int i, Canvas canvas){
        switch(i){
            case 0:
                canvas.drawRect(0,0,(float)182.5,(float)182.5,ppp);
                break;
            case 1:
                canvas.drawRect((float)182.5,0,365,(float)182.5,ppp);
                break;
            case 2:
                canvas.drawRect(365,0,(float)547.5,(float)182.5,ppp);
                break;
            case 3:
                canvas.drawRect((float)547.5,0,735,(float)182.5,ppp);
                break;
            case 4:
                canvas.drawRect(0,(float)182.5,(float)182.5,365,ppp);
                break;
            case 5:
                canvas.drawRect((float)182.5,(float)182.5,365,365,ppp);
                break;
            case 6:
                canvas.drawRect(365,(float)182.5,(float)547.5,365,ppp);
                break;
            case 7:
                canvas.drawRect((float)547.5,(float)182.5,735,365,ppp);
                break;
            case 8:
                canvas.drawRect(0,365,(float)182.5,(float)547.5,ppp);
                break;
            case 9:
                canvas.drawRect((float)182.5,365,365,(float)547.5,ppp);
                break;
            case 10:
                canvas.drawRect(365,365,(float)547.5,(float)547.5,ppp);
                break;
            case 11:
                canvas.drawRect((float)547.5,365,735,(float)547.5,ppp);
                break;
            case 12:
                canvas.drawRect(0,(float)547.5,(float)182.5,730,ppp);
                break;
            case 13:
                canvas.drawRect((float)182.5,(float)547.5,365,730,ppp);
                break;
            case 14:
                canvas.drawRect(365,(float)547.5,(float)547.5,730,ppp);
                break;
            case 15:
                canvas.drawRect((float)547.5,(float)547.5,735,730,ppp);
                break;
        }
    }
}