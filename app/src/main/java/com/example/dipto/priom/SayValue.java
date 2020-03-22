package com.example.dipto.priom;


public class SayValue {
    public static final int SAY_VALUE_TYPE_SAY = 0;
    public static final int SAY_VALUE_TYPE_OPEN = 1;

    public int type;
    public int count;
    public int num;

    public SayValue() {
        type = SAY_VALUE_TYPE_SAY;
        count = -1;
        num = -1;
    }

    public SayValue clone() {
        SayValue sv = new SayValue();
        sv.type = type;
        sv.count = count;
        sv.num = num;
        return sv;
    }
}
