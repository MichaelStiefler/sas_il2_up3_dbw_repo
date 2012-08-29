// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ScoreItem.java

package com.maddox.il2.ai;


public class ScoreItem
{

    public ScoreItem(int i, double d)
    {
        type = i;
        score = d;
    }

    public static final int AIRCRAFT = 0;
    public static final int TANK = 1;
    public static final int CAR = 2;
    public static final int ARTILLERY = 3;
    public static final int AAA = 4;
    public static final int BRIDGE = 5;
    public static final int TRAIN = 6;
    public static final int SHIP = 7;
    public static final int AIRSTATIC = 8;
    public static final int RADIO = 9;
    public static final int _ALL_ = 10;
    public static final int TARGET_PRIMARY = 100;
    public static final int TARGET_SECONDARY = 101;
    public static final int TARGET_SECRET = 102;
    public int type;
    public double score;
}
