package com.example.dipto.priom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class GamesView extends View {
    Point pos = new Point();
    Paint linePaint;
    Paint GutiPaintA, GutiPaintB, markedPaint;
    Point position[] = new Point[42];
    //int gameArray[][] = new int[42][42];
    Point crossPoint[] = new Point[17];
    DisplayMetrics displayMetrics;
    WindowManager windowmanager;
    int deviceWidth, deviceHeight;
    int space = 80;
    boolean play;

    public GamesView(Context context) {
        super(context);

        linePaint = new Paint();
        GutiPaintA = new Paint();
        markedPaint = new Paint();
        GutiPaintA.setColor(Color.RED);
        GutiPaintB = new Paint();
        GutiPaintB.setColor(Color.BLUE);
        displayMetrics = new DisplayMetrics();


        getScreenSize();
        initializeAllPosition();
        applyStyleToLine();
        styleToMarkedGuti();
    }

    private void initializeAllPosition(){
        position[1] = new Point(space, space);
        position[2] = new Point(((deviceWidth-(space*2))/4)+space, space);
        position[3] = new Point((((deviceWidth-(space*2))/4)*2)+space, space);
        position[4] = new Point((((deviceWidth-(space*2))/4)*3)+space, space);
        position[5] = new Point(deviceWidth-space, space);

        position[10] = new Point(space, ((deviceHeight-(space*2))/4)+space);
        position[11] = new Point(((deviceWidth-(space*2))/4)+space, ((deviceHeight-(space*2))/4)+space);
        position[12] = new Point((((deviceWidth-(space*2))/4)*2)+space, ((deviceHeight-(space*2))/4)+space);
        position[13] = new Point((((deviceWidth-(space*2))/4)*3)+space, ((deviceHeight-(space*2))/4)+space);
        position[14] = new Point((((deviceWidth-(space*2))/4)*4)+space, ((deviceHeight-(space*2))/4)+space);

        crossPoint[1] = position[6] = getIntersectPoint(position[1],position[2],position[10]);
        crossPoint[2] = position[7] = getIntersectPoint(position[2],position[3],position[11]);
        crossPoint[3] = position[8] = getIntersectPoint(position[3],position[4],position[12]);
        crossPoint[4] = position[9] = getIntersectPoint(position[4],position[5],position[13]);

        position[19] = new Point(space, (((deviceHeight-(space*2))/4)*2)+space);
        position[20] = new Point((((deviceWidth-(space*2))/4)*1)+space, (((deviceHeight-(space*2))/4)*2)+space);
        position[21] = new Point((((deviceWidth-(space*2))/4)*2)+space, (((deviceHeight-(space*2))/4)*2)+space);
        position[22] = new Point((((deviceWidth-(space*2))/4)*3)+space, (((deviceHeight-(space*2))/4)*2)+space);
        position[23] = new Point((((deviceWidth-(space*2))/4)*4)+space, (((deviceHeight-(space*2))/4)*2)+space);

        crossPoint[5] = position[15] = getIntersectPoint(position[10],position[11],position[19]);
        crossPoint[6] = position[16] = getIntersectPoint(position[11],position[12],position[20]);
        crossPoint[7] = position[17] = getIntersectPoint(position[12],position[13],position[21]);
        crossPoint[8] = position[18] = getIntersectPoint(position[13],position[14],position[22]);

        position[28] = new Point(space, (((deviceHeight-(space*2))/4)*3)+space);
        position[29] = new Point((((deviceWidth-(space*2))/4)*1)+space, (((deviceHeight-(space*2))/4)*3)+space);
        position[30] = new Point((((deviceWidth-(space*2))/4)*2)+space, (((deviceHeight-(space*2))/4)*3)+space);
        position[31] = new Point((((deviceWidth-(space*2))/4)*3)+space, (((deviceHeight-(space*2))/4)*3)+space);
        position[32] = new Point((((deviceWidth-(space*2))/4)*4)+space, (((deviceHeight-(space*2))/4)*3)+space);

        crossPoint[9] = position[24] = getIntersectPoint(position[19],position[20],position[28]);
        crossPoint[10] = position[25] = getIntersectPoint(position[20],position[21],position[29]);
        crossPoint[11] = position[26] = getIntersectPoint(position[21],position[22],position[30]);
        crossPoint[12] = position[27] = getIntersectPoint(position[22],position[23],position[31]);

        position[37] = new Point(space, (((deviceHeight-(space*2))/4)*4)+space);
        position[38] = new Point((((deviceWidth-(space*2))/4)*1)+space, (((deviceHeight-(space*2))/4)*4)+space);
        position[39] = new Point((((deviceWidth-(space*2))/4)*2)+space, (((deviceHeight-(space*2))/4)*4)+space);
        position[40] = new Point((((deviceWidth-(space*2))/4)*3)+space, (((deviceHeight-(space*2))/4)*4)+space);
        position[41] = new Point((((deviceWidth-(space*2))/4)*4)+space, (((deviceHeight-(space*2))/4)*4)+space);

        crossPoint[13] = position[33] = getIntersectPoint(position[28],position[29],position[37]);
        crossPoint[14] = position[34] = getIntersectPoint(position[29],position[30],position[38]);
        crossPoint[15] = position[35] = getIntersectPoint(position[30],position[31],position[39]);
        crossPoint[16] = position[36] = getIntersectPoint(position[31],position[32],position[40]);
    }

    private void getScreenSize(){
        windowmanager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;
    }

    private Point getIntersectPoint(Point A, Point B, Point C){
        float x = (float) Math.sqrt( (Math.abs(A.x-B.x)*Math.abs(A.x-B.x)) + (Math.abs(A.y-B.y)*Math.abs(A.y-B.y)) );
        float y = (float) Math.sqrt( (Math.abs(A.x-C.x)*Math.abs(A.x-C.x)) + (Math.abs(A.y-C.y)*Math.abs(A.y-C.y)) );
        //Log.v("Priom", x + "+++" + y);
        return new Point((int) (A.x+(x/2)), (int) (A.y+(y/2)));
    }

    public void drawBoard(Canvas canvas){
        //Main Rectangle.....
        //Log.e("Priom","(" + position[32].x + "," + position[32].y +")" + "        (" + position[28].x + "," + position[28].y +")");
        canvas.drawLine(position[1].x,position[1].y,position[37].x,position[37].y,linePaint);
        canvas.drawLine(position[37].x,position[37].y,position[41].x,position[41].y,linePaint);
        canvas.drawLine(position[41].x,position[41].y,position[5].x,position[5].y,linePaint);
        canvas.drawLine(position[5].x,position[5].y,position[1].x,position[1].y,linePaint);

        //Main Cross......
        canvas.drawLine(position[1].x,position[1].y,position[41].x,position[41].y,linePaint);
        canvas.drawLine(position[5].x,position[5].y,position[37].x,position[37].y,linePaint);

        canvas.drawLine(position[10].x,position[10].y,position[14].x,position[14].y,linePaint);
        canvas.drawLine(position[19].x,position[19].y,position[23].x,position[23].y,linePaint);
        canvas.drawLine(position[32].x,position[32].y,position[28].x,position[28].y,linePaint);

        canvas.drawLine(position[2].x,position[2].y,position[38].x,position[38].y,linePaint);
        canvas.drawLine(position[3].x,position[3].y,position[39].x,position[39].y,linePaint);
        canvas.drawLine(position[4].x,position[4].y,position[40].x,position[40].y,linePaint);

        canvas.drawLine(position[4].x,position[4].y,position[14].x,position[14].y,linePaint);
        canvas.drawLine(position[3].x,position[3].y,position[23].x,position[23].y,linePaint);
        canvas.drawLine(position[2].x,position[2].y,position[32].x,position[32].y,linePaint);
        canvas.drawLine(position[10].x,position[10].y,position[40].x,position[40].y,linePaint);
        canvas.drawLine(position[19].x,position[19].y,position[39].x,position[39].y,linePaint);
        canvas.drawLine(position[28].x,position[28].y,position[38].x,position[38].y,linePaint);

        canvas.drawLine(position[10].x,position[10].y,position[2].x,position[2].y,linePaint);
        canvas.drawLine(position[19].x,position[19].y,position[3].x,position[3].y,linePaint);
        canvas.drawLine(position[28].x,position[28].y,position[4].x,position[4].y,linePaint);
        canvas.drawLine(position[38].x,position[38].y,position[14].x,position[14].y,linePaint);
        canvas.drawLine(position[39].x,position[39].y,position[23].x,position[23].y,linePaint);
        canvas.drawLine(position[40].x,position[40].y,position[32].x,position[32].y,linePaint);

    }

    private void applyStyleToLine(){
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(3);
    }

    private void styleToMarkedGuti(){
        markedPaint.setColor(Color.BLUE);
        markedPaint.setStyle(Paint.Style.STROKE);
        markedPaint.setStrokeWidth(1);
    }

    public void drawGuti(Canvas canvas, Point point[], String color){
        for (int i=1; i<=16; i++){
            if(color.equals("red")){
                canvas.drawCircle((float)point[i].x, (float)point[i].y, 18, GutiPaintA);
            }
            else{
                canvas.drawCircle((float)point[i].x, (float)point[i].y, 18, GutiPaintB);
            }
        }
    }

    public void markSelected(Canvas canvas, Point p){
        canvas.drawCircle(p.x,p.y,40,markedPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                pos.x = (int) x;
                pos.y = (int) y;
                //Log.e("Priom",pos.x+"+"+pos.y);
                break;
        }
        return true;
    }
}
