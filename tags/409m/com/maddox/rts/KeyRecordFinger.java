// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KeyRecordFinger.java

package com.maddox.rts;


public interface KeyRecordFinger
{

    public abstract int checkPeriod();

    public abstract int countSaveFingers();

    public abstract int[] calculateFingers();

    public abstract void checkFingers(int ai[]);
}
