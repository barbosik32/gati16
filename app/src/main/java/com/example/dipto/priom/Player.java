package com.example.dipto.priom;

import android.graphics.Point;
import android.util.Log;

public class Player {
    Point Guti[] = new Point[17];
    int source = -1;
    int dest = -1;
    int ownSource = -1;
    boolean multiEat;
    private final int gameArray[][] = new int[42][42];
    private final int eatArray[][] = new int[42][42];
    String gutiColor;
    int availableGuti;
    boolean isTurn;
    boolean isWin;

    public Player(String GCol, Point position[]){
        gutiColor = GCol;
        isTurn = false;
        isWin = false;
        multiEat = false;
        availableGuti = 16;
        initializeGutiPosition(position);
        initializeGameArray();
        initializeEatArray();
    }

    Point getTouchType(Point p, Point [] position, Point [] opponent){
        for(int i=1; i<=41; i++){
            if( ( (p.x>position[i].x-20) & (p.x<position[i].x+20) ) & ( (p.y>position[i].y-20) & (p.y<position[i].y+20) ) ){

                for (int j=1; j<=16; j++){
                    if(isEqualPoint(position[i],Guti[j])){
                        //Log.e("Priom","its own guti");
                        if(dest == -1){
                            source = i;
                            ownSource = j;
                        }
                        return position[i];
                    }
                    if (isEqualPoint(position[i],opponent[j])){
                        //Log.e("Priom","its opponent guti");
                        return position[i];
                    }
                }
                //Log.e("Priom","its empty");
                if (source != -1){
                    dest = i;
                }
                return position[i];
            }
        }
        //Log.e("Priom","its invalid");
        return p;
    }

    String getMoveType(){
        if(gameArray[source][dest] == 1){
            return "move";
        }
        else if (gameArray[source][dest] == 2){
            return "eat";
        }
        else return "invalid";
    }

    void move(Point [] position){
        Guti[ownSource] = position[dest];
    }
    
    boolean eat(Point position[], Player opponent){
        if(eatArray[source][dest] != -1){
            for(int i=1; i<=16; i++){
                if(isEqualPoint(opponent.Guti[i],position[eatArray[source][dest] ])){
                    Guti[ownSource] = position[dest];
                    opponent.Guti[i] = new Point(-100,-100);
                    opponent.availableGuti --;
                    source = dest;
                    dest = -1;
                    multiEat = true;
                    return true;
                }
            }
            dest = -1;
            return false;
        }
        return false;
    }

    void update(Player opponent, Point pos, Point position[]){
        Point pp = getTouchType(pos,position,opponent.Guti);
        if (source != -1 && dest != -1){
            ///Get move type...........
            String moveTYpe = getMoveType();
            if (moveTYpe.equals("move")){
                if (!multiEat){
                    ///Move...........
                    move(position);
                    swapTurn(opponent);
                    Log.e("Priom","Move.............");
                }
                else{
                    swapTurn(opponent);
                }
            }
            else if (moveTYpe.equals("eat")){
                ///Eat............
                if (!eat(position,opponent) && multiEat){
                    swapTurn(opponent);
                }
                Log.e("Priom","Eat.............");
            }
            else if (moveTYpe.equals("invalid") && multiEat){
                swapTurn(opponent);
                Log.e("Priom","Swap Turn.............");
            }
            else{
                dest = -1;
            }
        }
        else if (multiEat && position[source].x != pp.x && position[source].y != pp.y){
            swapTurn(opponent);
            Log.e("Priom","Swap Turn1.............");
        }
    }

    void swapTurn(Player opponent){
        source = -1;
        dest = -1;
        isTurn = false;
        multiEat = false;
        opponent.isTurn = true;
    }

    private void initializeGutiPosition(Point position[]){
        if(gutiColor.equals("red")){
            for (int i=1; i<=15; i++){
                Guti[i] = position[i];
            }
            Guti[16] = position[18];
        }

        else{
            for (int i=27, j=1; i<=41; i++, j++){
                Guti[j] = position[i];
            }
            Guti[16] = position[24];
        }
    }

    private boolean isEqualPoint(Point A, Point B){
        if ((A.x == B.x) && (A.y == B.y) ){
            return true;
        }
        else return false;
    }

    private void initializeGameArray(){
        for(int i=1;i<=41;i++){
            for(int j=1;j<=41;j++){
                gameArray[i][j] = 0;
            }
        }

        gameArray[1][2] = 1;
        gameArray[1][6] = 1;
        gameArray[1][10] = 1;

        gameArray[1][3] = 2;
        gameArray[1][11] = 2;
        gameArray[1][19] = 2;

        gameArray[2][1] = 1;
        gameArray[2][6] = 1;
        gameArray[2][11] = 1;
        gameArray[2][7] = 1;
        gameArray[2][3] = 1;

        gameArray[2][10] = 2;
        gameArray[2][20] = 2;
        gameArray[2][12] = 2;
        gameArray[2][4] = 2;

        gameArray[3][2] = 1;
        gameArray[3][7] = 1;
        gameArray[3][12] = 1;
        gameArray[3][8] = 1;
        gameArray[3][4] = 1;

        gameArray[3][1] = 2;
        gameArray[3][11] = 2;
        gameArray[3][21] = 2;
        gameArray[3][13] = 2;
        gameArray[3][5] = 2;

        gameArray[4][3] = 1;
        gameArray[4][8] = 1;
        gameArray[4][13] = 1;
        gameArray[4][9] = 1;
        gameArray[4][5] = 1;

        gameArray[4][2] = 2;
        gameArray[4][12] = 2;
        gameArray[4][22] = 2;
        gameArray[4][14] = 2;

        gameArray[5][4] = 1;
        gameArray[5][9] = 1;
        gameArray[5][14] = 1;

        gameArray[5][3] = 2;
        gameArray[5][13] = 2;
        gameArray[5][23] = 2;

        gameArray[6][1] = 1;
        gameArray[6][2] = 1;
        gameArray[6][10] = 1;
        gameArray[6][11] = 1;

        gameArray[6][16] = 2;

        gameArray[7][2] = 1;
        gameArray[7][3] = 1;
        gameArray[7][11] = 1;
        gameArray[7][12] = 1;

        gameArray[7][17] = 2;
        gameArray[7][15] = 2;

        gameArray[8][3] = 1;
        gameArray[8][4] = 1;
        gameArray[8][12] = 1;
        gameArray[8][13] = 1;

        gameArray[8][16] = 2;
        gameArray[8][18] = 2;

        gameArray[9][4] = 1;
        gameArray[9][5] = 1;
        gameArray[9][13] = 1;
        gameArray[9][14] = 1;

        gameArray[9][17] = 2;

        gameArray[10][1] = 1;
        gameArray[10][6] = 1;
        gameArray[10][11] = 1;
        gameArray[10][15] = 1;
        gameArray[10][19] = 1;

        gameArray[10][2] = 2;
        gameArray[10][12] = 2;
        gameArray[10][20] = 2;
        gameArray[10][28] = 2;

        gameArray[11][2] = 1;
        gameArray[11][6] = 1;
        gameArray[11][7] = 1;
        gameArray[11][10] = 1;
        gameArray[11][12] = 1;
        gameArray[11][15] = 1;
        gameArray[11][16] = 1;
        gameArray[11][20] = 1;

        gameArray[11][1] = 2;
        gameArray[11][3] = 2;
        gameArray[11][13] = 2;
        gameArray[11][19] = 2;
        gameArray[11][21] = 2;
        gameArray[11][29] = 2;

        gameArray[12][3] = 1;
        gameArray[12][7] = 1;
        gameArray[12][8] = 1;
        gameArray[12][11] = 1;
        gameArray[12][13] = 1;
        gameArray[12][16] = 1;
        gameArray[12][17] = 1;
        gameArray[12][21] = 1;

        gameArray[12][2] = 2;
        gameArray[12][4] = 2;
        gameArray[12][10] = 2;
        gameArray[12][14] = 2;
        gameArray[12][20] = 2;
        gameArray[12][22] = 2;
        gameArray[12][30] = 2;

        gameArray[13][4] = 1;
        gameArray[13][8] = 1;
        gameArray[13][9] = 1;
        gameArray[13][12] = 1;
        gameArray[13][14] = 1;
        gameArray[13][17] = 1;
        gameArray[13][18] = 1;
        gameArray[13][22] = 1;

        gameArray[13][3] = 2;
        gameArray[13][5] = 2;
        gameArray[13][11] = 2;
        gameArray[13][21] = 2;
        gameArray[13][23] = 2;
        gameArray[13][31] = 2;

        gameArray[14][5] = 1;
        gameArray[14][9] = 1;
        gameArray[14][13] = 1;
        gameArray[14][18] = 1;
        gameArray[14][23] = 1;

        gameArray[14][4] = 2;
        gameArray[14][12] = 2;
        gameArray[14][22] = 2;
        gameArray[14][32] = 2;

        gameArray[15][10] = 1;
        gameArray[15][11] = 1;
        gameArray[15][19] = 1;
        gameArray[15][20] = 1;

        gameArray[15][7] = 2;
        gameArray[15][25] = 2;

        gameArray[16][11] = 1;
        gameArray[16][12] = 1;
        gameArray[16][20] = 1;
        gameArray[16][21] = 1;

        gameArray[16][6] = 2;
        gameArray[16][8] = 2;
        gameArray[16][24] = 2;
        gameArray[16][26] = 2;

        gameArray[17][12] = 1;
        gameArray[17][13] = 1;
        gameArray[17][21] = 1;
        gameArray[17][22] = 1;

        gameArray[17][7] = 2;
        gameArray[17][9] = 2;
        gameArray[17][25] = 2;
        gameArray[17][27] = 2;

        gameArray[18][13] = 1;
        gameArray[18][14] = 1;
        gameArray[18][22] = 1;
        gameArray[18][23] = 1;

        gameArray[18][8] = 2;
        gameArray[18][26] = 2;

        gameArray[19][10] = 1;
        gameArray[19][15] = 1;
        gameArray[19][20] = 1;
        gameArray[19][24] = 1;
        gameArray[19][28] = 1;

        gameArray[19][1] = 2;
        gameArray[19][11] = 2;
        gameArray[19][21] = 2;
        gameArray[19][29] = 2;
        gameArray[19][37] = 2;

        gameArray[20][11] = 1;
        gameArray[20][15] = 1;
        gameArray[20][16] = 1;
        gameArray[20][19] = 1;
        gameArray[20][21] = 1;
        gameArray[20][24] = 1;
        gameArray[20][25] = 1;
        gameArray[20][29] = 1;

        gameArray[20][10] = 2;
        gameArray[20][2] = 2;
        gameArray[20][12] = 2;
        gameArray[20][22] = 2;
        gameArray[20][30] = 2;
        gameArray[20][38] = 2;
        gameArray[20][28] = 2;

        gameArray[21][12] = 1;
        gameArray[21][16] = 1;
        gameArray[21][17] = 1;
        gameArray[21][20] = 1;
        gameArray[21][22] = 1;
        gameArray[21][25] = 1;
        gameArray[21][26] = 1;
        gameArray[21][30] = 1;

        gameArray[21][3] = 2;
        gameArray[21][11] = 2;
        gameArray[21][13] = 2;
        gameArray[21][19] = 2;
        gameArray[21][23] = 2;
        gameArray[21][29] = 2;
        gameArray[21][31] = 2;
        gameArray[21][39] = 2;

        gameArray[22][13] = 1;
        gameArray[22][17] = 1;
        gameArray[22][18] = 1;
        gameArray[22][21] = 1;
        gameArray[22][23] = 1;
        gameArray[22][26] = 1;
        gameArray[22][27] = 1;
        gameArray[22][31] = 1;

        gameArray[22][4] = 2;
        gameArray[22][12] = 2;
        gameArray[22][14] = 2;
        gameArray[22][20] = 2;
        gameArray[22][30] = 2;
        gameArray[22][32] = 2;
        gameArray[22][40] = 2;

        gameArray[23][14] = 1;
        gameArray[23][18] = 1;
        gameArray[23][22] = 1;
        gameArray[23][27] = 1;
        gameArray[23][32] = 1;

        gameArray[23][5] = 2;
        gameArray[23][13] = 2;
        gameArray[23][21] = 2;
        gameArray[23][31] = 2;
        gameArray[23][41] = 2;

        gameArray[24][19] = 1;
        gameArray[24][20] = 1;
        gameArray[24][28] = 1;
        gameArray[24][29] = 1;

        gameArray[24][16] = 2;
        gameArray[24][34] = 2;

        gameArray[25][20] = 1;
        gameArray[25][21] = 1;
        gameArray[25][29] = 1;
        gameArray[25][30] = 1;

        gameArray[25][15] = 2;
        gameArray[25][17] = 2;
        gameArray[25][33] = 2;
        gameArray[25][35] = 2;

        gameArray[26][21] = 1;
        gameArray[26][22] = 1;
        gameArray[26][30] = 1;
        gameArray[26][31] = 1;

        gameArray[26][16] = 2;
        gameArray[26][18] = 2;
        gameArray[26][34] = 2;
        gameArray[26][36] = 2;

        gameArray[27][22] = 1;
        gameArray[27][23] = 1;
        gameArray[27][31] = 1;
        gameArray[27][32] = 1;

        gameArray[27][17] = 2;
        gameArray[27][35] = 2;

        gameArray[28][19] = 1;
        gameArray[28][24] = 1;
        gameArray[28][29] = 1;
        gameArray[28][33] = 1;
        gameArray[28][37] = 1;

        gameArray[28][10] = 2;
        gameArray[28][20] = 2;
        gameArray[28][30] = 2;
        gameArray[28][38] = 2;

        gameArray[29][20] = 1;
        gameArray[29][24] = 1;
        gameArray[29][25] = 1;
        gameArray[29][28] = 1;
        gameArray[29][30] = 1;
        gameArray[29][33] = 1;
        gameArray[29][34] = 1;
        gameArray[29][38] = 1;

        gameArray[29][11] = 2;
        gameArray[29][19] = 2;
        gameArray[29][21] = 2;
        gameArray[29][31] = 2;
        gameArray[29][39] = 2;
        gameArray[29][37] = 2;

        gameArray[30][21] = 1;
        gameArray[30][25] = 1;
        gameArray[30][26] = 1;
        gameArray[30][29] = 1;
        gameArray[30][31] = 1;
        gameArray[30][34] = 1;
        gameArray[30][35] = 1;
        gameArray[30][39] = 1;

        gameArray[30][12] = 2;
        gameArray[30][20] = 2;
        gameArray[30][22] = 2;
        gameArray[30][28] = 2;
        gameArray[30][32] = 2;
        gameArray[30][38] = 2;
        gameArray[30][40] = 2;

        gameArray[31][22] = 1;
        gameArray[31][26] = 1;
        gameArray[31][27] = 1;
        gameArray[31][30] = 1;
        gameArray[31][32] = 1;
        gameArray[31][35] = 1;
        gameArray[31][36] = 1;
        gameArray[31][40] = 1;

        gameArray[31][13] = 2;
        gameArray[31][21] = 2;
        gameArray[31][23] = 2;
        gameArray[31][29] = 2;
        gameArray[31][39] = 2;
        gameArray[31][41] = 2;

        gameArray[32][23] = 1;
        gameArray[32][27] = 1;
        gameArray[32][31] = 1;
        gameArray[32][36] = 1;
        gameArray[32][41] = 1;

        gameArray[32][14] = 2;
        gameArray[32][22] = 2;
        gameArray[32][30] = 2;
        gameArray[32][40] = 2;

        gameArray[33][28] = 1;
        gameArray[33][29] = 1;
        gameArray[33][37] = 1;
        gameArray[33][38] = 1;

        gameArray[33][25] = 2;

        gameArray[34][29] = 1;
        gameArray[34][30] = 1;
        gameArray[34][38] = 1;
        gameArray[34][39] = 1;

        gameArray[34][24] = 2;
        gameArray[34][26] = 2;

        gameArray[35][30] = 1;
        gameArray[35][31] = 1;
        gameArray[35][39] = 1;
        gameArray[35][40] = 1;

        gameArray[35][25] = 2;
        gameArray[35][27] = 2;

        gameArray[36][31] = 1;
        gameArray[36][32] = 1;
        gameArray[36][40] = 1;
        gameArray[36][41] = 1;

        gameArray[36][26] = 2;

        gameArray[37][28] = 1;
        gameArray[37][33] = 1;
        gameArray[37][38] = 1;

        gameArray[37][19] = 2;
        gameArray[37][29] = 2;
        gameArray[37][39] = 2;

        gameArray[38][29] = 1;
        gameArray[38][33] = 1;
        gameArray[38][34] = 1;
        gameArray[38][37] = 1;
        gameArray[38][39] = 1;

        gameArray[38][20] = 2;
        gameArray[38][28] = 2;
        gameArray[38][30] = 2;
        gameArray[38][40] = 2;

        gameArray[39][30] = 1;
        gameArray[39][34] = 1;
        gameArray[39][35] = 1;
        gameArray[39][38] = 1;
        gameArray[39][40] = 1;

        gameArray[39][21] = 2;
        gameArray[39][29] = 2;
        gameArray[39][31] = 2;
        gameArray[39][37] = 2;
        gameArray[39][41] = 2;

        gameArray[40][31] = 1;
        gameArray[40][35] = 1;
        gameArray[40][36] = 1;
        gameArray[40][39] = 1;
        gameArray[40][41] = 1;

        gameArray[40][22] = 2;
        gameArray[40][30] = 2;
        gameArray[40][32] = 2;
        gameArray[40][38] = 2;

        gameArray[41][32] = 1;
        gameArray[41][36] = 1;
        gameArray[41][40] = 1;

        gameArray[41][39] = 2;
        gameArray[41][31] = 2;
        gameArray[41][23] = 2;
    }

    private void initializeEatArray(){
        for(int i=1;i<=41;i++){
            for(int j=1;j<=41;j++){
                eatArray[i][j] = -1;
            }
        }
        eatArray[1][3] = 2;
        eatArray[1][11] = 6;
        eatArray[1][19] = 10;

        eatArray[2][10] = 6;
        eatArray[2][20] = 11;
        eatArray[2][12] = 7;
        eatArray[2][4] = 3;

        eatArray[3][1] = 2;
        eatArray[3][11] = 7;
        eatArray[3][21] = 12;
        eatArray[3][13] = 8;
        eatArray[3][5] = 4;

        eatArray[4][2] = 3;
        eatArray[4][12] = 8;
        eatArray[4][22] = 13;
        eatArray[4][14] = 9;

        eatArray[5][3] = 4;
        eatArray[5][13] = 9;
        eatArray[5][23] = 14;

        eatArray[6][16] = 11;

        eatArray[7][17] = 12;
        eatArray[7][15] = 11;

        eatArray[8][16] = 12;
        eatArray[8][18] = 13;

        eatArray[9][17] = 13;

        eatArray[10][2] = 6;
        eatArray[10][12] = 11;
        eatArray[10][20] = 15;
        eatArray[10][28] = 19;

        eatArray[11][1] = 6;
        eatArray[11][3] = 7;
        eatArray[11][13] = 12;
        eatArray[11][19] = 15;
        eatArray[11][21] = 16;
        eatArray[11][29] = 20;

        eatArray[12][2] = 7;
        eatArray[12][4] = 8;
        eatArray[12][10] = 11;
        eatArray[12][14] = 13;
        eatArray[12][20] = 16;
        eatArray[12][22] = 17;
        eatArray[12][30] = 21;

        eatArray[13][3] = 8;
        eatArray[13][5] = 9;
        eatArray[13][11] = 12;
        eatArray[13][21] = 17;
        eatArray[13][23] = 18;
        eatArray[13][31] = 22;

        eatArray[14][4] = 9;
        eatArray[14][12] = 13;
        eatArray[14][22] = 18;
        eatArray[14][32] = 23;

        eatArray[15][7] = 11;
        eatArray[15][25] = 20;

        eatArray[16][6] = 11;
        eatArray[16][8] = 12;
        eatArray[16][24] = 20;
        eatArray[16][26] = 21;

        eatArray[17][7] = 12;
        eatArray[17][9] = 13;
        eatArray[17][25] = 21;
        eatArray[17][27] = 22;

        eatArray[18][8] = 13;
        eatArray[18][26] = 22;

        eatArray[19][1] = 10;
        eatArray[19][11] = 15;
        eatArray[19][21] = 20;
        eatArray[19][29] = 24;
        eatArray[19][37] = 28;

        eatArray[20][10] = 15;
        eatArray[20][2] = 11;
        eatArray[20][12] = 16;
        eatArray[20][22] = 21;
        eatArray[20][30] = 25;
        eatArray[20][38] = 29;
        eatArray[20][28] = 24;

        eatArray[21][3] = 12;
        eatArray[21][11] = 16;
        eatArray[21][13] = 17;
        eatArray[21][19] = 20;
        eatArray[21][23] = 22;
        eatArray[21][29] = 25;
        eatArray[21][31] = 26;
        eatArray[21][39] = 30;

        eatArray[22][4] = 13;
        eatArray[22][12] = 17;
        eatArray[22][14] = 18;
        eatArray[22][20] = 21;
        eatArray[22][30] = 26;
        eatArray[22][32] = 27;
        eatArray[22][40] = 31;

        eatArray[23][5] = 14;
        eatArray[23][13] = 18;
        eatArray[23][21] = 22;
        eatArray[23][31] = 27;
        eatArray[23][41] = 32;

        eatArray[24][16] = 20;
        eatArray[24][34] = 29;

        eatArray[25][15] = 20;
        eatArray[25][17] = 21;
        eatArray[25][33] = 29;
        eatArray[25][35] = 30;

        eatArray[26][16] = 21;
        eatArray[26][18] = 22;
        eatArray[26][34] = 30;
        eatArray[26][36] = 31;

        eatArray[27][17] = 22;
        eatArray[27][35] = 31;

        eatArray[28][10] = 19;
        eatArray[28][20] = 24;
        eatArray[28][30] = 29;
        eatArray[28][38] = 33;

        eatArray[29][11] = 20;
        eatArray[29][19] = 24;
        eatArray[29][21] = 25;
        eatArray[29][31] = 30;
        eatArray[29][39] = 34;
        eatArray[29][37] = 33;

        eatArray[30][12] = 21;
        eatArray[30][20] = 25;
        eatArray[30][22] = 26;
        eatArray[30][28] = 29;
        eatArray[30][32] = 31;
        eatArray[30][38] = 34;
        eatArray[30][40] = 35;

        eatArray[31][13] = 22;
        eatArray[31][21] = 26;
        eatArray[31][23] = 27;
        eatArray[31][29] = 30;
        eatArray[31][39] = 35;
        eatArray[31][41] = 36;

        eatArray[32][14] = 23;
        eatArray[32][22] = 27;
        eatArray[32][30] = 31;
        eatArray[32][40] = 36;

        eatArray[33][25] = 29;

        eatArray[34][24] = 29;
        eatArray[34][26] = 30;

        eatArray[35][25] = 30;
        eatArray[35][27] = 31;

        eatArray[36][26] = 31;

        eatArray[37][19] = 28;
        eatArray[37][29] = 33;
        eatArray[37][39] = 38;

        eatArray[38][20] = 29;
        eatArray[38][28] = 33;
        eatArray[38][30] = 34;
        eatArray[38][40] = 39;

        eatArray[39][21] = 30;
        eatArray[39][29] = 34;
        eatArray[39][31] = 35;
        eatArray[39][37] = 38;
        eatArray[39][41] = 40;

        eatArray[40][22] = 31;
        eatArray[40][30] = 35;
        eatArray[40][32] = 36;
        eatArray[40][38] = 39;

        eatArray[41][39] = 40;
        eatArray[41][31] = 36;
        eatArray[41][23] = 32;
    }
    
    
}
