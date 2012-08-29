// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MouseCursor.java

package com.maddox.rts;


public interface MouseCursor
{

    public abstract void setCursor(int i);

    public static final int NONE = 0;
    public static final int NORMAL = 1;
    public static final int CROSS = 2;
    public static final int HAND = 3;
    public static final int HELP = 4;
    public static final int IBEAM = 5;
    public static final int NO = 6;
    public static final int SIZEALL = 7;
    public static final int SIZENESW = 8;
    public static final int SIZENS = 9;
    public static final int SIZENWSE = 10;
    public static final int SIZEWE = 11;
    public static final int UP = 12;
    public static final int WAIT = 13;
}
