// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SharedTokenizer.java

package com.maddox.util;


public class SharedTokenizer
{

    public static void set(java.lang.String s)
    {
        int i = s.length();
        if(i > buf.length)
            buf = new char[i];
        s.getChars(0, i, buf, 0);
        ptr = 0;
        for(end = i; ptr < end; ptr++)
            if(buf[ptr] > ' ')
                break;

        for(; end > ptr; end--)
            if(buf[end - 1] > ' ')
                break;

    }

    public static java.lang.String getGap()
    {
        if(ptr >= end)
            return null;
        start = ptr;
        stop = end;
        ptr = end;
        for(; stop > start; stop--)
            if(buf[stop - 1] > ' ')
                break;

        if(stop <= start)
            return null;
        else
            return new String(buf, start, stop - start);
    }

    public static boolean hasMoreTokens()
    {
        return ptr < end;
    }

    public static int countTokens()
    {
        int i = 0;
        for(int j = ptr; j < end;)
        {
            i++;
            for(; j < end; j++)
                if(buf[j] <= ' ')
                    break;

            for(; j < end; j++)
                if(buf[j] > ' ')
                    break;

        }

        return i;
    }

    private static void nextWord()
    {
        start = ptr;
        for(; ptr < end; ptr++)
            if(buf[ptr] <= ' ')
                break;

        stop = ptr;
        for(; ptr < end; ptr++)
            if(buf[ptr] > ' ')
                break;

    }

    public static void _nextWord()
    {
        com.maddox.util.SharedTokenizer.nextWord();
    }

    public static java.lang.String nextToken()
    {
        if(ptr >= end)
        {
            return null;
        } else
        {
            com.maddox.util.SharedTokenizer.nextWord();
            return new String(buf, start, stop - start);
        }
    }

    public static java.lang.String next()
    {
        return com.maddox.util.SharedTokenizer.nextToken();
    }

    public static java.lang.String next(java.lang.String s)
    {
        java.lang.String s1 = com.maddox.util.SharedTokenizer.nextToken();
        if(s1 != null)
            return s1;
        else
            return s;
    }

    public static java.lang.String _getString()
    {
        if(start >= end)
            return null;
        else
            return new String(buf, start, stop - start);
    }

    public static int _getInt()
        throws java.lang.NumberFormatException
    {
        if(start >= end)
            throw new NumberFormatException();
        byte byte0 = 10;
        int i = 0;
        boolean flag = false;
        int j = start;
        int k = stop;
        int l;
        if(buf[j] == '-')
        {
            flag = true;
            l = 0x80000000;
            j++;
        } else
        {
            l = 0x80000001;
        }
        int i1 = l / byte0;
        if(j < k)
        {
            int j1 = java.lang.Character.digit(buf[j++], byte0);
            if(j1 < 0)
                throw new NumberFormatException();
            i = -j1;
        }
        while(j < k) 
        {
            int k1 = java.lang.Character.digit(buf[j++], byte0);
            if(k1 < 0)
                throw new NumberFormatException();
            if(i < i1)
                throw new NumberFormatException();
            i *= byte0;
            if(i < l + k1)
                throw new NumberFormatException();
            i -= k1;
        }
        if(flag)
        {
            if(j > start + 1)
                return i;
            else
                throw new NumberFormatException();
        } else
        {
            return -i;
        }
    }

    public static int next(int i)
    {
        if(ptr >= end)
            return i;
        com.maddox.util.SharedTokenizer.nextWord();
        byte byte0 = 10;
        int j = 0;
        boolean flag = false;
        int k = start;
        int l = stop;
        int i1;
        if(buf[k] == '-')
        {
            flag = true;
            i1 = 0x80000000;
            k++;
        } else
        {
            i1 = 0x80000001;
        }
        int j1 = i1 / byte0;
        if(k < l)
        {
            int k1 = java.lang.Character.digit(buf[k++], byte0);
            if(k1 < 0)
                return i;
            j = -k1;
        }
        while(k < l) 
        {
            int l1 = java.lang.Character.digit(buf[k++], byte0);
            if(l1 < 0)
                return i;
            if(j < j1)
                return i;
            j *= byte0;
            if(j < i1 + l1)
                return i;
            j -= l1;
        }
        if(flag)
        {
            if(k > start + 1)
                return j;
            else
                return i;
        } else
        {
            return -j;
        }
    }

    public static int next(int i, int j, int k)
    {
        int l = com.maddox.util.SharedTokenizer.next(i);
        if(l < j)
            l = j;
        if(l > k)
            l = k;
        return l;
    }

    public static double next(double d)
    {
        if(ptr >= end)
            return d;
        return java.lang.Double.parseDouble(com.maddox.util.SharedTokenizer.next());
        java.lang.Exception exception;
        exception;
        return d;
    }

    public static double next(double d, double d1, double d2)
    {
        double d3 = com.maddox.util.SharedTokenizer.next(d);
        if(d3 < d1)
            d3 = d1;
        if(d3 > d2)
            d3 = d2;
        return d3;
    }

    public static boolean next(boolean flag)
    {
        return com.maddox.util.SharedTokenizer.next(flag ? 1 : 0) != 0;
    }

    private SharedTokenizer()
    {
    }

    private static char buf[] = new char[128];
    private static int ptr;
    private static int end;
    private static int start;
    private static int stop;

}
