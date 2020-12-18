package glover.mike.cs6314assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

/**
 * Created by Mike Glover on 28/11/2017.
 */

public class ClockSurfaceView extends SurfaceView implements Runnable {

    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;
    Paint framePaint;
    Paint markPaint;
    Paint handPaint;

    private float length;

    SharedPreferences prefs;

    private int clockFrameColour;
    private boolean clockFrame;
    private int frameStyle;

    private int clockMarkColour;
    private boolean clockMinMarks;
    private int clockHourMarks;

    private int clockHandColour;

    public ClockSurfaceView(Context context, float l, int clockFrameColour, boolean clockFrame,
                            int frameStyle, int clockMarkColour, boolean minMarks, int hourMarks,
                            int clockHandColour) {
        super(context);
        surfaceHolder = getHolder();
        length = l;
        this.clockFrameColour = clockFrameColour;
        this.clockFrame = clockFrame;
        this.frameStyle = frameStyle;

        this.clockMarkColour = clockMarkColour;
        this.clockMinMarks = minMarks;
        this.clockHourMarks = hourMarks;

        this.clockHandColour = clockHandColour;

        framePaint = new Paint();
        framePaint.setColor(this.clockFrameColour);
        framePaint.setStrokeWidth(5);
        framePaint.setStyle(Paint.Style.STROKE);

        markPaint = new Paint();
        markPaint.setColor(this.clockMarkColour);
        markPaint.setStrokeWidth(5);

        handPaint = new Paint();
        handPaint.setColor(this.clockHandColour);
        handPaint.setStrokeWidth(5);
    }

    public void onResumeSurfaceView(int clockFrameColour, boolean clockFrame, int frameStyle,
                                    int clockMarkColour, boolean minMarks, int hourMarks,
                                    int clockHandColour){
        running = true;
        this.clockFrameColour = clockFrameColour;
        this.clockFrame = clockFrame;
        this.frameStyle = frameStyle;

        this.clockMarkColour = clockMarkColour;
        this.clockMinMarks = minMarks;
        this.clockHourMarks = hourMarks;

        this.clockHandColour = clockHandColour;

        framePaint = new Paint();
        framePaint.setColor(this.clockFrameColour);
        framePaint.setStrokeWidth(5);
        framePaint.setStyle(Paint.Style.STROKE);

        markPaint = new Paint();
        markPaint.setColor(this.clockMarkColour);
        markPaint.setStrokeWidth(5);
        markPaint.setStyle(Paint.Style.FILL);

        handPaint = new Paint();
        handPaint.setColor(this.clockHandColour);
        handPaint.setStrokeWidth(5);
        handPaint.setStyle(Paint.Style.FILL);

        thread = new Thread(this);
        thread.start();
    }

    public void onPauseSurfaceView(){
        boolean retry = true;
        running = false;
        while(retry){
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        int hour = 0, min = 0, sec = 0;
        while(running){
            if (surfaceHolder.getSurface().isValid()){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                Canvas canvas = surfaceHolder.lockCanvas();

                int width = getWidth();
                int height = getHeight();

                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                RegPoly frame = new RegPoly(60, length, width/2, height/2, canvas, framePaint);

                RegPoly centreCircle = new RegPoly(60, 9, width/2, height/2, canvas, handPaint);
                RegPoly hourHand = new RegPoly(60, length-160, width/2, height/2, canvas, handPaint);
                RegPoly minHand = new RegPoly(60, length-80, width/2, height/2, canvas, handPaint);
                RegPoly secHand = new RegPoly(60, length-80, width/2, height/2, canvas, handPaint);

                RegPoly minMarks = new RegPoly(60, length-20, width/2, height/2, canvas, markPaint);
                RegPoly hourMarks = new RegPoly(12, length-40, width/2, height/2, canvas, markPaint);
                RegPoly hourNumeralMarks = new RegPoly(12, length-70, width/2, height/2, canvas, markPaint);

                //canvas.save();

                Calendar rightNow = Calendar.getInstance();
                hour = rightNow.get(Calendar.HOUR);
                min = rightNow.get(Calendar.MINUTE);
                sec = rightNow.get(Calendar.SECOND);

                centreCircle.drawCircle();
                if (clockFrame) {
                    if (frameStyle == 0) {
                        frame.drawCircle();
                    } else if (frameStyle == 1){
                        frame.drawSquare();
                    }
                }

                if (clockMinMarks) {
                    minMarks.drawPoints();
                }

                if (clockHourMarks == 0){
                    hourNumeralMarks.drawHourNumerals();
                } else if (clockHourMarks == 1){
                    hourMarks.drawPoints();
                } else if (clockHourMarks == 2){
                    hourMarks.drawPoints();
                    hourNumeralMarks.drawHourNumerals();
                }

                secHand.drawRadius(sec+45);
                minHand.drawRadius(min+45);
                hourHand.drawRadius((hour*5)+(min/12)+45);

                //canvas.restore();

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }
}
