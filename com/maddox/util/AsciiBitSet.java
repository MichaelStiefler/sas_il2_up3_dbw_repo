// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AsciiBitSet.java

package com.maddox.util;

import java.util.BitSet;

public class AsciiBitSet
{

    public AsciiBitSet()
    {
    }

    public static java.lang.String save(java.util.BitSet bitset, int i)
    {
        int j = 0;
        int k = (i + 5) / 6;
        java.lang.StringBuffer stringbuffer = new StringBuffer(k);
        for(int l = 0; l < k; l++)
        {
            int i1 = 0;
            for(int j1 = 0; j1 < 6 && j < i; j++)
            {
                if(bitset.get(j))
                    i1 |= 1 << j1;
                j1++;
            }

            stringbuffer.append((char)toAscii[i1]);
        }

        return stringbuffer.toString();
    }

    public static java.lang.String save(byte abyte0[], int i)
    {
        int j = 0;
        int k = (i + 5) / 6;
        java.lang.StringBuffer stringbuffer = new StringBuffer(k);
        for(int l = 0; l < k; l++)
        {
            int i1 = 0;
            for(int j1 = 0; j1 < 6 && j < i; j++)
            {
                int k1 = j >> 8;
                int l1 = 1 << (j & 0xff);
                if((abyte0[k1] & l1) != 0)
                    i1 |= 1 << j1;
                j1++;
            }

            stringbuffer.append((char)toAscii[i1]);
        }

        return stringbuffer.toString();
    }

    public static java.lang.String save(int i)
    {
        int j = 0;
        byte byte0 = 6;
        java.lang.StringBuffer stringbuffer = new StringBuffer(byte0);
        for(int k = 0; k < byte0; k++)
        {
            int l = 0;
            for(int i1 = 0; i1 < 6 && j < 32; j++)
            {
                int j1 = 1 << j;
                if((i & j1) != 0)
                    l |= 1 << i1;
                i1++;
            }

            stringbuffer.append((char)toAscii[l]);
        }

        for(; stringbuffer.length() > 1 && stringbuffer.charAt(stringbuffer.length() - 1) == '0'; stringbuffer.deleteCharAt(stringbuffer.length() - 1));
        return stringbuffer.toString();
    }

    public static int load(java.lang.String s)
    {
        int i = 0;
        int j = 0;
        int k = 6;
        if(k > s.length())
            k = s.length();
        for(int l = 0; l < k; l++)
        {
            int i1 = fromAscii[s.charAt(l) & 0x7f];
            for(int j1 = 0; j1 < 6 && j < 32; j++)
            {
                if((i1 & 1 << j1) != 0)
                    i |= 1 << j;
                j1++;
            }

        }

        return i;
    }

    public static void load(java.lang.String s, java.util.BitSet bitset, int i)
    {
        int j = 0;
        int k = (i + 5) / 6;
        for(int l = 0; l < k; l++)
        {
            int i1 = fromAscii[s.charAt(l) & 0x7f];
            for(int j1 = 0; j1 < 6 && j < i; j++)
            {
                if((i1 & 1 << j1) != 0)
                    bitset.set(j);
                else
                    bitset.clear(j);
                j1++;
            }

        }

    }

    public static byte[] load(java.lang.String s, byte abyte0[], int i)
    {
        int j = (i + 7) / 8;
        if(abyte0 == null || abyte0.length < j)
            abyte0 = new byte[j];
        int k = 0;
        int l = (i + 5) / 6;
        for(int i1 = 0; i1 < l; i1++)
        {
            int j1 = fromAscii[s.charAt(i1) & 0x7f];
            for(int k1 = 0; k1 < 6 && k < i; k++)
            {
                int l1 = k >> 8;
                int i2 = 1 << (k & 0xff);
                if((j1 & 1 << k1) != 0)
                    abyte0[l1] |= i2;
                else
                    abyte0[l1] &= ~i2;
                k1++;
            }

        }

        return abyte0;
    }

    private static int fromAscii[];
    private static int toAscii[] = {
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 
        97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 
        107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 
        117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 
        69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 
        79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 
        89, 90, 45, 43
    };

    static 
    {
        fromAscii = new int[128];
        for(int i = 0; i < toAscii.length; i++)
            fromAscii[toAscii[i]] = i;

    }
}
