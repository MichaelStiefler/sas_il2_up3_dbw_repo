// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdArea.java

package com.maddox.rts;


public interface CmdArea
{

    public abstract java.lang.Object atom(java.lang.String s);

    public abstract boolean setAtom(java.lang.String s, java.lang.Object obj);

    public abstract boolean delAtom(java.lang.String s);

    public abstract boolean existAtom(java.lang.String s, boolean flag);
}
