// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BsData.java

package com.maddox.netphone;

import java.util.Vector;

public class BsData
{

    public BsData(int i)
    {
        links = new Vector();
        size = i * 8;
        wp = 0;
        data = new byte[i];
        for(int j = 0; j < i; j++)
            data[j] = 0;

        maxlen = 0;
        rdflag = false;
    }

    protected int size;
    protected int wp;
    protected int maxlen;
    protected boolean rdflag;
    protected byte data[];
    protected java.util.Vector links;
}
