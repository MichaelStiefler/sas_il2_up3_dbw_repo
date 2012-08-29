// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgJoyListener.java

package com.maddox.rts;


public interface MsgJoyListener
{

    public abstract void msgJoyButton(int i, int j, boolean flag);

    public abstract void msgJoyMove(int i, int j, int k);

    public abstract void msgJoyPov(int i, int j);

    public abstract void msgJoyPoll();
}
