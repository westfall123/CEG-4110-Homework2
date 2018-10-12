package com.example.westf.homework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

public class AnalogClockView extends View {

    int hour, minute, second;
    private int height, width, pad, fontSize, numberSpacing, radius = 0;
    private int hand, hourHand;
    private Paint paint;
    private boolean instantiated;
    private Rect rect = new Rect();

    private int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    android.text.format.Time time;
    int i;

    public AnalogClockView(Context context) {
        super(context);
    }

    public AnalogClockView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public AnalogClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setupClock() {
        height = getHeight();
        width = getWidth();
        pad = numberSpacing + 50;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics());
        int minVal = Math.min(height, width);
        radius = minVal / 2 - pad;
        hand = minVal / 20;
        hourHand = minVal / 7;
        paint = new Paint();
        instantiated = true;
    }

    public void drawHand(Canvas canvas, double setLocation, boolean isHour) {
        double angle = Math.PI * setLocation / 30 - Math.PI / 2;

        int handRadius = isHour ? radius - hand - hourHand : radius - hand;

        canvas.drawLine(width / 2, height / 2, (float) (width / 2 + Math.cos(angle) * handRadius),
                (float) (height / 2 + Math.sin(angle) * handRadius), paint);
    }

    public void drawHands(Canvas canvas) {
        Calendar cal = Calendar.getInstance();
        float hour = cal.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;

        //get current time from clockView
        drawHand(canvas, (hour + getMinute() / 60) * 5f, true);
        drawHand(canvas, getMinute(), false);
        drawHand(canvas, getSecond(), false);
    }

    public void drawNums(Canvas canvas) {

        paint.setTextSize(fontSize);

        for (int number : nums) {
            String tmp = String.valueOf(number);
            paint.getTextBounds(tmp, 0, tmp.length(), rect);
            double angle = Math.PI / 6 * (number - 3);
            int x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            int y = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
            canvas.drawText(tmp, x, y, paint);
        }
    }
    public void drawCircleBackground(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius + pad - 10, paint);
    }

    public void drawCenter(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, 12, paint);
    }

    public void onDraw(Canvas canvas) {
        if (!instantiated) {
            setupClock();
        }

        canvas.drawColor(Color.BLACK);
        drawCircleBackground(canvas);
        drawCenter(canvas);
        drawNums(canvas);
        drawHands(canvas);

        postInvalidateDelayed(500);
        invalidate();
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void setTime(int[] time) {
        hour = time[0];
        minute = time[1];
        second = time[2];
    }

}
