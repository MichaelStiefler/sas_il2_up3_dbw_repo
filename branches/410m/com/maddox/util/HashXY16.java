// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashXY16.java

package com.maddox.util;


public class HashXY16
{

    public static int key(int i, int j)
    {
        return tbl[(i & 0xf) << 4 | j & 0xf] | tbl[i & 0xf0 | (j & 0xf0) >> 4] << 8 | (i & 0xff00) << 24 | (j & 0xff00) << 16;
    }

    private HashXY16()
    {
    }

    private static int tbl[];

    static 
    {
        tbl = new int[256];
        int i = 0;
        for(int j = 0; j < 16; j++)
        {
            int k = (j & 1) << 1 | (j & 2) << 2 | (j & 4) << 3 | (j & 8) << 4;
            for(int l = 0; l < 16; l++)
            {
                int i1 = (l & 1) << 0 | (l & 2) << 1 | (l & 4) << 2 | (l & 8) << 3;
                tbl[i++] = k | i1;
            }

        }

    }
}
