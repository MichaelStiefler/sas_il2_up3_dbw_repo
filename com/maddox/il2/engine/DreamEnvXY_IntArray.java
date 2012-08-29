// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DreamEnvXY.java

package com.maddox.il2.engine;


class DreamEnvXY_IntArray
{

    public int[] array()
    {
        return arr;
    }

    public int size()
    {
        return size;
    }

    public void clear()
    {
        size = 0;
    }

    public void add(int i)
    {
        if(size + 1 > arr.length)
        {
            int ai[] = new int[(size + 1) * 2];
            for(int j = 0; j < size; j++)
                ai[j] = arr[j];

            arr = ai;
        }
        arr[size++] = i;
    }

    public DreamEnvXY_IntArray(int i)
    {
        size = 0;
        arr = new int[i];
    }

    private int size;
    private int arr[];
}
