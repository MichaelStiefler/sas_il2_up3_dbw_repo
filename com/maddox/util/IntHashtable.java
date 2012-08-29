// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IntHashtable.java

package com.maddox.util;

import java.io.PrintStream;

public final class IntHashtable
{

    public IntHashtable()
    {
        defaultValue = -1;
        initialize(3);
    }

    public IntHashtable(int i)
    {
        defaultValue = -1;
        initialize(com.maddox.util.IntHashtable.leastGreaterPrimeIndex((int)((float)i / 0.4F)));
    }

    public IntHashtable(int i, int j)
    {
        defaultValue = -1;
        defaultValue = j;
        initialize(com.maddox.util.IntHashtable.leastGreaterPrimeIndex((int)((float)i / 0.4F)));
    }

    public int size()
    {
        return count;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public void put(int i, int j)
    {
        if(count > highWaterMark)
            rehash();
        if(countDeletings > delWaterMark)
            rehashSameSize();
        int k = find(i);
        if(keyList[k] <= 0x80000001)
        {
            keyList[k] = i;
            count++;
        }
        values[k] = j;
    }

    public int get(int i)
    {
        return values[find(i)];
    }

    public void remove(int i)
    {
        int j = find(i);
        if(keyList[j] > 0x80000001)
        {
            keyList[j] = 0x80000001;
            values[j] = defaultValue;
            count--;
            countDeletings++;
            if(count < lowWaterMark)
                rehash();
            if(countDeletings > delWaterMark)
                rehashSameSize();
        }
    }

    public void clear()
    {
        if(count != 0)
            clearAll();
    }

    public int getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(int i)
    {
        defaultValue = i;
        rehash();
    }

    public boolean equals(java.lang.Object obj)
    {
        if(obj.getClass() != getClass())
            return false;
        com.maddox.util.IntHashtable inthashtable = (com.maddox.util.IntHashtable)obj;
        if(inthashtable.size() != count || inthashtable.defaultValue != defaultValue)
            return false;
        for(int i = 0; i < keyList.length; i++)
        {
            int j = keyList[i];
            if(j > 0x80000001 && inthashtable.get(j) != values[i])
                return false;
        }

        return true;
    }

    public static boolean isValidKey(int i)
    {
        return i > 0x80000001;
    }

    public int[] values()
    {
        return values;
    }

    public int[] keyList()
    {
        return keyList;
    }

    public int getByIndex(int i)
    {
        if(keyList[i] > 0x80000001)
            return values[i];
        else
            return defaultValue;
    }

    public void setByIndex(int i, int j)
    {
        if(keyList[i] > 0x80000001)
            values[i] = j;
    }

    public void removeByIndex(int i)
    {
        if(keyList[i] > 0x80000001)
        {
            keyList[i] = 0x80000001;
            values[i] = defaultValue;
            count--;
            countDeletings++;
        }
    }

    public int getIndex(int i)
    {
        int j = find(i);
        if(keyList[j] > 0x80000001)
            return j;
        else
            return -1;
    }

    public void validate()
    {
        if(countDeletings > delWaterMark)
            rehashSameSize();
    }

    private void initialize(int i)
    {
        if(i < 0)
            i = 0;
        else
        if(i >= PRIMES.length)
        {
            java.lang.System.out.println("IntHashtable TOO BIG");
            i = PRIMES.length - 1;
        }
        primeIndex = i;
        int j = PRIMES[i];
        values = new int[j];
        keyList = new int[j];
        clearAll();
        lowWaterMark = (int)((float)j * 0.0F);
        highWaterMark = (int)((float)j * 0.4F);
        delWaterMark = (int)((float)j * 0.4F);
    }

    private void clearAll()
    {
        for(int i = 0; i < keyList.length; i++)
        {
            keyList[i] = 0x80000000;
            values[i] = defaultValue;
        }

        count = 0;
        countDeletings = 0;
    }

    private void rehash()
    {
        int ai[] = values;
        int ai1[] = keyList;
        int i = primeIndex;
        if(count > highWaterMark)
            i++;
        else
        if(count < lowWaterMark)
            i -= 2;
        initialize(i);
        for(int j = ai.length - 1; j >= 0; j--)
        {
            int k = ai1[j];
            if(k > 0x80000001)
                putInternal(k, ai[j]);
        }

    }

    private void rehashSameSize()
    {
        int ai[] = values;
        int ai1[] = keyList;
        initialize(primeIndex);
        for(int i = ai.length - 1; i >= 0; i--)
        {
            int j = ai1[i];
            if(j > 0x80000001)
                putInternal(j, ai[i]);
        }

    }

    private void putInternal(int i, int j)
    {
        int k = find(i);
        if(keyList[k] <= 0x80000001)
        {
            keyList[k] = i;
            count++;
        }
        values[k] = j;
    }

    private int find(int i)
    {
        if(i <= 0x80000001)
            throw new IllegalArgumentException("key can't be less than 0x80000001");
        int j = -1;
        int k = (i ^ 0x4000000) % keyList.length;
        if(k < 0)
            k = -k;
        int l = 0;
        do
        {
            int i1 = keyList[k];
            if(i1 == i)
                return k;
            if(i1 <= 0x80000001)
            {
                if(i1 == 0x80000000)
                {
                    if(j >= 0)
                        k = j;
                    return k;
                }
                if(j < 0)
                    j = k;
            }
            if(l == 0)
            {
                l = i % (keyList.length - 1);
                if(l < 0)
                    l = -l;
                l++;
            }
            k = (k + l) % keyList.length;
        } while(true);
    }

    private static int leastGreaterPrimeIndex(int i)
    {
        int j;
        for(j = 0; j < PRIMES.length; j++)
            if(i < PRIMES[j])
                return j;

        return j - 1;
    }

    public static final int EMPTY = 0x80000000;
    public static final int DELETED = 0x80000001;
    public static final int MAX_UNUSED = 0x80000001;
    private int defaultValue;
    private int primeIndex;
    private static final float highWaterFactor = 0.4F;
    private int highWaterMark;
    private static final float lowWaterFactor = 0F;
    private int lowWaterMark;
    private static final float delWaterFactor = 0.4F;
    private int delWaterMark;
    private int count;
    private int countDeletings;
    private int values[];
    private int keyList[];
    private static final int PRIMES[] = {
        11, 37, 71, 127, 179, 257, 359, 491, 661, 887, 
        1181, 1553, 2053, 2683, 3517, 4591, 6007, 7817, 10193, 13291, 
        17291, 22481, 29251, 38053, 49499, 64373, 0x146f5, 0x1a93f, 0x228c7, 0x2cec3, 
        0x3a67f, 0x4bed5, 0x62b61, 0x80551, 0xa6d71, 0xd8e4d, 0x119f83, 0x16e907, 0x1dc8cd, 0x26b81d, 
        0x3255f7, 0x416fb7, 0x551159, 0x6e96ad, 0x8fc3e1, 0xbae545, 0xf2f6d7, 0x13bda69, 0x19a9c11, 0x215caef, 
        0x2b5ee7f, 0x3861c6d, 0x494be7f, 0x5f4918b, 0x7bdf05d, 0xa10852f, 0xd157a05, 0x110251f1, 0x161c9dc7, 0x1cbecd37, 
        0x255e713f, 0x30946025, 0x3f2749d9, 0x521979c7, 0x6abab7f1, 0x7fffffff
    };

}
