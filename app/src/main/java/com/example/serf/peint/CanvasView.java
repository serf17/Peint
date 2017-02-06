package com.example.serf.peint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;

import java.util.Random;

/**
 * Created by serf on 26/01/2017.
 */

public class CanvasView extends View {
    public int height;
    public int width;
    private Bitmap elbitmap;
    private Canvas elcanvas;
    private Path elpath;
    private Float eX , eY;
    private Paint mpaint;
    private static final float TOLERANCE  = 5;
    private Random rd = new Random();
    Context context;


    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        elpath = new Path();
        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.GREEN);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeJoin(Paint.Join.ROUND);
        mpaint.setStrokeWidth(4f);

    }

    public void ChangeColor(){
        mpaint.setColor(Color.rgb(rd.nextInt()*256,rd.nextInt()*256,rd.nextInt()*256));
    }

    protected void  onSizeChangd(int w, int h, int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        elbitmap =  Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        elcanvas = new Canvas(elbitmap);

    }

    private void startTouch(float x, float y){
        mpaint.setColor(Color.rgb(rd.nextInt()*256,rd.nextInt()*256,rd.nextInt()*256));
        elpath.moveTo(x,y);
        eX = x;
        eY = y;

    }

    private void moveTouch(float x, float y){
        float dx = Math.abs(x- eX);
        float dy = Math.abs(x- eY);
        if(dx >=TOLERANCE || dy >= TOLERANCE){
            elpath.quadTo(eX,eY,(x + eX) / 2, (y + eY) / 2);
            eX=x;
            eY= y;


        }

    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(elpath,mpaint);
    }

    private void upTouch(){
        elpath.lineTo(eX,eY);
    }

    public void clearCanvas(){
        elpath.reset();
        invalidate();
    }

    public  boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }

}
