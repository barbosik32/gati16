package com.example.dipto.priom;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class Description extends AppCompatActivity {
    private ImageView imageOk;
    private TextView textHelp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.description );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        new Thread( new refreshViewThread()).start();
    }

    private class refreshViewThread extends Thread {
        public void run() {
            while( true ) {
                try {
                    Thread.sleep( 50 );
                } catch( Exception e ) {
                }
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame( rect );
                if( rect.top != 0 )
                    break;
            }
            updateHandler.post( updateViewRunnable );
        }
    }

    final Handler updateHandler = new Handler();
    final Runnable updateViewRunnable = new Runnable() {
        public void run() {
            getWindow().setBackgroundDrawable( getResources().getDrawable(R.drawable.bkcolor));
            FrameLayout alo = findViewById(R.id.layout_main);

            Rect rect = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame( rect );
            int screenWidth = rect.right - rect.left;
            int screenHeight = rect.bottom - rect.top;

            imageOk = new ImageView( Description.this );
            imageOk.setBackgroundResource( R.drawable.ok );
            FrameLayout.LayoutParams lp;
            lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 197 * screenWidth / 640;
            lp.topMargin = 710 * screenHeight / 960;
            lp.width = 258 * screenWidth / 640;
            lp.height = 59 * screenHeight / 960;
            imageOk.setLayoutParams(lp);
            alo.addView( imageOk );

            View.OnClickListener buttonClickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    if( v.equals( imageOk )) {
                        imageOk.setBackgroundResource( R.drawable.ok_light );
                        (new clickViewThread()).start();
                    }
                }
            };
            imageOk.setOnClickListener( buttonClickListener );

            int showFontSize = 18;
            if( screenWidth <= 320 )
                showFontSize = 18;
            else if( screenWidth < 720 )
                showFontSize = 21;
            else
                showFontSize = 24;

            textHelp = new TextView( Description.this );
            textHelp.setTextSize( showFontSize );
            textHelp.setText( "Game Super Jack: The dice 1 is the universal dice which is equal to any other ones (2, 3, 4, 5, 6). Each player : Look at the numbers of the five dices, and guess the number X (what he thinks the total of the dice with the number for all the two Players).");
            lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 43 * screenWidth / 640;
            lp.topMargin = 180 * screenHeight / 960;
            lp.width = 543 * screenWidth / 640;
            lp.height = 540 * screenHeight / 960;
            textHelp.setLayoutParams(lp);
            alo.addView( textHelp );
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class clickViewThread extends Thread {
        public void run() {
            try {
                Thread.sleep( 50 );
            } catch( Exception e ) {
            }
            finish();
        }
    }
}
