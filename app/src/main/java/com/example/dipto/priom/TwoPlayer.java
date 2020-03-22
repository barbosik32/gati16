package com.example.dipto.priom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class TwoPlayer extends AppCompatActivity {
    TwoPlayerView twoPlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        twoPlayerView = new TwoPlayerView(this);
        setContentView(twoPlayerView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Priom","On Pause Called");
        //Log.e("Priom","Pause Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Priom","On Resume Called");
        //Log.e("Priom","Resume Called");
    }
}
