// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   StrMath.java

package com.maddox.util;


public class StrMath
{

    public StrMath()
    {
    }

    public static boolean simple(java.lang.String s, java.lang.String s1)
    {
        if(s == null || s1 == null)
            return false;
        char ac[] = new char[s.length() + 1];
        char ac1[] = new char[s1.length() + 1];
        s.getChars(0, s.length(), ac, 0);
        s1.getChars(0, s1.length(), ac1, 0);
        ac[s.length()] = '\0';
        ac1[s1.length()] = '\0';
        int i = 0;
        int j = 0;
        int k = -1;
        int l = -1;
        do
        {
            for(; ac[i] == ac1[j]; i++)
            {
                if(ac1[j] == 0)
                    return true;
                j++;
            }

            if(ac1[j] != 0 && ac[i] == '?')
            {
                i++;
                j++;
            } else
            if(ac[i] == '*')
            {
                k = ++i;
                l = j;
            } else
            if(l != -1 && ac1[l] != 0)
            {
                j = ++l;
                i = k;
            } else
            {
                return false;
            }
        } while(true);
    }
}
