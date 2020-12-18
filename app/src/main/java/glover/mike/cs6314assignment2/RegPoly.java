package glover.mike.cs6314assignment2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Mike Glover on 28/11/2017.
 */

public class RegPoly {

    private float x0,y0,r;
    private int n;
    private float x[],y[];

    private Canvas c = null;
    private Paint p = null;

    private String[] adjustedHourNumbers = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "1", "2"};

    public RegPoly(int n, float r, float x0, float y0, Canvas c, Paint p) {
        this.x0 = x0;
        this.y0 = y0;
        this.r = r;
        this.n = n;
        this.c = c;
        this.p = p;

        x = new float[n];
        y = new float[n];

        for (int i = 0; i < n; i++){

            x[i] = (float)(this.x0 + this.r * Math.cos(2*Math.PI*i/n));
            y[i] = (float)(this.y0 + this.r * Math.sin(2*Math.PI*i/n));

        }
    }

    public void drawCircle(){
        c.drawCircle(this.x0, this.y0, this.r, this.p);
    }

    public void drawSquare(){
        c.drawRect(this.x0 - this.r, this.y0 - this.r, this.x0 + this.r, this.y0 + this.r, p);
    }

    public void drawRegPoly(){
        for(int i = 0; i < n; i++){
            c.drawLine(x[i], y[i], x[(i+1)%n], y[(i+1)%n], p);
        }
    }

    public void drawPoints(){
        for (int i = 0; i < n; i++){
            c.drawCircle(x[i], y[i], 5, p);
        }
    }

    public void drawRadius(int i){
        c.drawLine(x0, y0, x[i%n], y[i%n], p);
    }

    public void drawHourNumerals(){
        p.setTextSize(30);
        Rect rect = new Rect();
        for (int i = 0; i < n; i++){
            p.getTextBounds(adjustedHourNumbers[i], 0, adjustedHourNumbers[i].length(), rect);
            c.drawText(adjustedHourNumbers[i], x[i]-(rect.width()/2), y[i]+(rect.height()/2), p);
        }
    }
}
