package com.example.dipto.priom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class KeyView extends View {
    private Context context;
    private boolean pressed;
    private int no;

    public KeyView( Context context, int no ) {
        super(context);
        this.context = context;
        this.no = no;
        pressed = false;
    }

    protected synchronized void onDraw(Canvas cv ) {
        super.onDraw( cv );
        Rect rectSrc, rectDst;
        Bitmap bm;
        switch( no ) {
            case 0:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k00 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k00_press );
                break;
            case 1:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k01 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k01_press );
                break;
            case 2:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k02 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k02_press );
                break;
            case 3:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k03 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k03_press );
                break;
            case 4:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k04 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k04_press );
                break;
            case 5:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k05 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k05_press );
                break;
            case 6:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k06 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k06_press );
                break;
            case 7:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k07 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k07_press );
                break;
            case 8:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k08 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k08_press );
                break;
            case 9:
                if( !pressed )
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k09 );
                else
                    bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.k09_press );
                break;
            case 10:
                bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.ge );
                break;
            case 11:
                bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.goverchun);
                break;
            case 12:
                bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.kai );
                break;
            case 13:
                bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.chester);
                break;
            default:
                bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.yao );
                break;
        }
        rectSrc = new Rect( 0, 0, bm.getWidth(), bm.getHeight() );
        rectDst = new Rect( 0, 0, getWidth(), getHeight());
        cv.drawBitmap( bm, rectSrc, rectDst, null );
        bm.recycle();
    }

    public synchronized void setPress( boolean thePressed ) {
        pressed = thePressed;
        postInvalidate();
    }
}
