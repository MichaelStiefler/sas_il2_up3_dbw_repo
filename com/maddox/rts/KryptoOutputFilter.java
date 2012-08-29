// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KryptoOutputFilter.java

package com.maddox.rts;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class KryptoOutputFilter extends java.io.FilterOutputStream
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

    public KryptoOutputFilter(java.io.OutputStream outputstream)
    {
        super(outputstream);
        sw = 0;
        sw = 0;
    }

    public KryptoOutputFilter(java.io.OutputStream outputstream, int ai[])
    {
        super(outputstream);
        sw = 0;
        key = ai;
        if(ai != null && ai.length == 0)
            key = null;
        sw = 0;
    }

    public void write(int i)
        throws java.io.IOException
    {
        if(key != null)
        {
            sw = (sw + 1) % key.length;
            out.write(i ^ key[sw]);
        } else
        {
            out.write(i);
        }
    }

    public void write(byte abyte0[], int i, int j)
        throws java.io.IOException
    {
        if(key != null)
        {
            for(int k = 0; k < j; k++)
            {
                sw = (sw + 1) % key.length;
                out.write(abyte0[i + k] ^ key[sw]);
            }

        } else
        {
            out.write(abyte0, i, j);
        }
    }

    private int key[] = {
        255, 170
    };
    private int sw;
}
