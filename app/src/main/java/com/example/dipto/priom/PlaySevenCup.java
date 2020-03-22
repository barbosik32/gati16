package com.example.dipto.priom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;


public class PlaySevenCup extends AppCompatImageView {
    private Context context;

    public PlaySevenCup(Context context ) {
        super(context);
        this.context = context;
    }

    protected synchronized void onDraw(Canvas cv ) {
        super.onDraw( cv );
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.cup);
        Rect rectSrc, rectDst;
        rectSrc = new Rect( 0, 0, bm.getWidth(), bm.getHeight() );
        rectDst = new Rect( 0, 0, getWidth(), getHeight());
        cv.drawBitmap( bm, rectSrc, rectDst, null );
        bm.recycle();
    }

}
