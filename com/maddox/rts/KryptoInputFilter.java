// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KryptoInputFilter.java

package com.maddox.rts;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class KryptoInputFilter extends java.io.FilterInputStream
{

    public void kryptoResetSwitch()
    {
        sw = 0;
    }

    public int[] kryptoGetKey()
    {
        return key;
    }

    public void kryptoSetKey(int ai[])
    {
        key = ai;
        if(key != null && key.length == 0)
            key = null;
        sw = 0;
    }

    public KryptoInputFilter(java.io.InputStream inputstream)
    {
        super(inputstream);
        sw = 0;
        sw = 0;
    }

    public KryptoInputFilter(java.io.InputStream inputstream, int ai[])
    {
        super(inputstream);
        sw = 0;
        key = ai;
        if(ai != null && ai.length == 0)
            key = null;
        sw = 0;
    }

    public boolean markSupported()
    {
        return false;
    }

    public int read()
        throws java.io.IOException
    {
        int i = in.read();
        if(key == null)
            return i;
        sw = (sw + 1) % key.length;
        if(i != -1)
            i ^= key[sw];
        return i;
    }

    public int read(byte abyte0[], int i, int j)
        throws java.io.IOException
    {
        int k = in.read(abyte0, i, j);
        if(key == null || k <= 0)
            return k;
        for(int l = 0; l < k; l++)
        {
            sw = (sw + 1) % key.length;
            abyte0[i + l] = (byte)(abyte0[i + l] ^ key[sw]);
        }

        return k;
    }

    private int key[] = {
        255, 170
    };
    private int sw;
}
