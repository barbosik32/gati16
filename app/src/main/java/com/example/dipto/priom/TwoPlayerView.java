package com.example.dipto.priom;

import android.content.Context;
import android.graphics.Canvas;

public class TwoPlayerView extends GamesView{
    Player playerA, playerB;
    public TwoPlayerView(Context context) {
        super(context);
        play = true;
        playerA = new Player("red", position);
        playerB = new Player("blue", position);
        playerB.isTurn = true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(playerA.isTurn){
            playerA.update(playerB,pos,position);
        }
        else {
            playerB.update(playerA,pos,position);
        }
        draww(canvas);
        invalidate();
    }

    private void draww(Canvas c){
        drawBoard(c);
        drawGuti(c,playerA.Guti,"red");
        drawGuti(c,playerB.Guti,"blue");
        if(playerB.source != -1 && playerB.isTurn){
            markSelected(c,position[playerB.source]);
        }
        else if (playerA.isTurn && playerA.source != -1){
            markSelected(c,position[playerA.source]);
        }

    }
}
