package com.example.dipto.priom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;


public class PlayThing extends AppCompatImageView {
    private Context context;
    public static final int DICE_STATE_DICE = 0;
    public static final int DICE_STATE_CUP = 1;
    public static final int DICE_STATE_SHAKING = 2;
    public int state;
    private int num;

    public PlayThing(Context context, int theState, int theNum ) {
        super(context);
        this.context = context;
        state = theState;
        num = theNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum( int theNum ) {
        num = theNum;
    }

    protected synchronized void onDraw(Canvas cv ) {
        super.onDraw( cv );
        if( state == PlayThing.DICE_STATE_SHAKING || state == PlayThing.DICE_STATE_CUP )
            return;
        Rect rectSrc, rectDst;
        Bitmap bm;
        switch( num ) {
            case 1: bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.dice1 ); break;
            case 2: bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.dice2 ); break;
            case 3: bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.dice3 ); break;
            case 4: bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.dice4 ); break;
            case 5: bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.dice5 ); break;
            default: bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.dice6 ); break;
        }
        rectSrc = new Rect( 0, 0, bm.getWidth(), bm.getHeight() );
        rectDst = new Rect( 0, 0, getWidth(), getHeight());
        cv.drawBitmap( bm, rectSrc, rectDst, null );
        bm.recycle();
    }
}
