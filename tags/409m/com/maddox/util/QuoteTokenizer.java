// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   QuoteTokenizer.java

package com.maddox.util;


public class QuoteTokenizer
{

    public QuoteTokenizer(java.lang.String s)
    {
        buf = null;
        for(buf = new StringBuffer(s); buf.length() > 0; buf.deleteCharAt(0))
            if(buf.charAt(0) != ' ')
                break;

        int i;
        for(; buf.length() > 0; buf.deleteCharAt(i))
        {
            i = buf.length() - 1;
            if(buf.charAt(i) != ' ')
                break;
        }

    }

    public java.lang.String getGap()
    {
        java.lang.String s = buf.substring(0);
        buf.delete(0, buf.length());
        return s;
    }

    public boolean hasMoreTokens()
    {
        return buf.length() > 0;
    }

    public java.lang.String nextToken()
    {
        return nextWord();
    }

    public java.lang.String next()
    {
        return nextWord();
    }

    private java.lang.String nextWord()
    {
        for(; buf.length() > 0; buf.deleteCharAt(0))
            if(buf.charAt(0) != ' ')
                break;

        if(buf.length() == 0)
            return "";
        int i = 0;
        java.lang.String s = null;
        if(buf.charAt(i) == '"')
        {
            buf.deleteCharAt(0);
            for(; i < buf.length(); i++)
            {
                if(buf.charAt(i) != '"')
                    continue;
                if(i > 0 && buf.charAt(i - 1) == '\\')
                {
                    buf.deleteCharAt(i - 1);
                    i--;
                    continue;
                }
                buf.deleteCharAt(i);
                break;
            }

        } else
        {
            for(int j = buf.length(); i < j; i++)
                if(buf.charAt(i) == ' ')
                    break;

        }
        s = buf.substring(0, i);
        buf.delete(0, i);
        for(; buf.length() > 0; buf.deleteCharAt(0))
            if(buf.charAt(0) != ' ')
                break;

        return s;
    }

    public static java.lang.String toToken(java.lang.String s)
    {
        int i = s.length();
        int j;
        for(j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c == ' ' || c == '"')
                break;
        }

        if(j == i)
            return s;
        java.lang.StringBuffer stringbuffer = new StringBuffer(s);
        for(int k = 0; k < i; k++)
        {
            char c1 = stringbuffer.charAt(k);
            if(c1 == '"')
            {
                stringbuffer.insert(k, '\\');
                k++;
                i++;
            }
        }

        stringbuffer.insert(0, '"');
        stringbuffer.append('"');
        return stringbuffer.toString();
    }

    private java.lang.StringBuffer buf;
}
