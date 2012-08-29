// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgMouseListener.java

package com.maddox.rts;


public interface MsgMouseListener
{

    public abstract void msgMouseButton(int i, boolean flag);

    public abstract void msgMouseMove(int i, int j, int k);

    public abstract void msgMouseAbsMove(int i, int j, int k);
}
