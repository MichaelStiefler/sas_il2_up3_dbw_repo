// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgKeyboardListener.java

package com.maddox.rts;


public interface MsgKeyboardListener
{

    public abstract void msgKeyboardKey(int i, boolean flag);

    public abstract void msgKeyboardChar(char c);
}
