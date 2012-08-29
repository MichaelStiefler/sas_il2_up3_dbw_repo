// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Stdio.java

package com.maddox.util;


public class Stdio
{

    public Stdio()
    {
    }

    public static void sscanf(java.lang.String s, java.lang.String s1)
    {
        int l;
        for(int i = l = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(c < '+' || c == ',')
                continue;
            begs[l] = i;
            do
            {
                if(i >= s.length())
                    break;
                char c1 = s.charAt(i);
                if(c1 <= '+' || c1 == ',')
                    break;
                i++;
            } while(true);
            ends[l] = i;
            l++;
        }

        int k;
label0:
        for(int j = k = 0; j < s1.length() - 1 && k < l; j++)
        {
            char c2 = s1.charAt(j);
            if(c2 != '%')
                continue;
            java.lang.String s2 = s.substring(begs[k], ends[k]);
            do
            {
                if(++j >= s1.length())
                    continue label0;
                char c3 = s1.charAt(j);
                switch(java.lang.Character.toUpperCase(c3))
                {
                case 67: // 'C'
                case 69: // 'E'
                case 72: // 'H'
                case 74: // 'J'
                case 75: // 'K'
                case 77: // 'M'
                case 78: // 'N'
                case 80: // 'P'
                case 81: // 'Q'
                case 82: // 'R'
                case 84: // 'T'
                case 85: // 'U'
                case 86: // 'V'
                case 87: // 'W'
                default:
                    break;

                case 68: // 'D'
                case 73: // 'I'
                    I[k] = java.lang.Integer.parseInt(s2);
                    k++;
                    continue label0;

                case 88: // 'X'
                    I[k] = java.lang.Integer.parseInt(s2, 16);
                    k++;
                    continue label0;

                case 66: // 'B'
                    I[k] = java.lang.Integer.parseInt(s2, 2);
                    k++;
                    continue label0;

                case 79: // 'O'
                    I[k] = java.lang.Integer.parseInt(s2, 8);
                    k++;
                    continue label0;

                case 70: // 'F'
                case 71: // 'G'
                case 76: // 'L'
                    D[k] = java.lang.Double.parseDouble(s2);
                    k++;
                    continue label0;

                case 83: // 'S'
                    S[k] = s2;
                    k++;
                    continue label0;
                }
            } while(true);
        }

    }

    public static int getInt(int i)
    {
        return I[i];
    }

    public static float getFloat(int i)
    {
        return (float)D[i];
    }

    public static double getDouble(int i)
    {
        return D[i];
    }

    public static java.lang.String getString(int i)
    {
        return S[i];
    }

    private static final int MP = 12;
    private static int I[] = new int[12];
    private static double D[] = new double[12];
    private static java.lang.String S[] = new java.lang.String[12];
    private static int begs[] = new int[12];
    private static int ends[] = new int[12];

}
