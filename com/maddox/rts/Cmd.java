// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cmd.java

package com.maddox.rts;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Referenced classes of package com.maddox.rts:
//            CmdEnv

public class Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.lang.String s)
    {
        return null;
    }

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        return null;
    }

    public static boolean exist(java.util.Map map, java.lang.String s)
    {
        return map.containsKey(s);
    }

    public static int nargs(java.util.Map map, java.lang.String s)
    {
        if(map.containsKey(s))
        {
            java.util.List list = (java.util.List)map.get(s);
            if(list != null)
                return list.size();
        }
        return 0;
    }

    public static java.lang.String arg(java.util.Map map, java.lang.String s, int i)
    {
        if(map.containsKey(s))
        {
            java.util.List list = (java.util.List)map.get(s);
            if(list != null && i >= 0 && i < list.size())
                return (java.lang.String)list.get(i);
        }
        return null;
    }

    public static int arg(java.util.Map map, java.lang.String s, int i, int j)
    {
        java.lang.String s1;
        s1 = com.maddox.rts.Cmd.arg(map, s, i);
        if(s1 == null)
            return j;
        return java.lang.Integer.parseInt(s1);
        java.lang.Exception exception;
        exception;
        return j;
    }

    public static int arg(java.util.Map map, java.lang.String s, int i, int j, int k, int l)
    {
        int i1 = com.maddox.rts.Cmd.arg(map, s, i, j);
        if(i1 < k)
            i1 = k;
        if(i1 > l)
            i1 = l;
        return i1;
    }

    public static long arg(java.util.Map map, java.lang.String s, int i, long l)
    {
        java.lang.String s1;
        s1 = com.maddox.rts.Cmd.arg(map, s, i);
        if(s1 == null)
            return l;
        return java.lang.Long.parseLong(s1);
        java.lang.Exception exception;
        exception;
        return l;
    }

    public static long arg(java.util.Map map, java.lang.String s, int i, long l, long l1, long l2)
    {
        long l3 = com.maddox.rts.Cmd.arg(map, s, i, l);
        if(l3 < l1)
            l3 = l1;
        if(l3 > l2)
            l3 = l2;
        return l3;
    }

    public static float arg(java.util.Map map, java.lang.String s, int i, float f)
    {
        java.lang.String s1;
        s1 = com.maddox.rts.Cmd.arg(map, s, i);
        if(s1 == null)
            return f;
        return java.lang.Float.parseFloat(s1);
        java.lang.Exception exception;
        exception;
        return f;
    }

    public static float arg(java.util.Map map, java.lang.String s, int i, float f, float f1, float f2)
    {
        float f3 = com.maddox.rts.Cmd.arg(map, s, i, f);
        if(f3 < f1)
            f3 = f1;
        if(f3 > f2)
            f3 = f2;
        return f3;
    }

    public static double arg(java.util.Map map, java.lang.String s, int i, double d)
    {
        java.lang.String s1;
        s1 = com.maddox.rts.Cmd.arg(map, s, i);
        if(s1 == null)
            return d;
        return java.lang.Double.parseDouble(s1);
        java.lang.Exception exception;
        exception;
        return d;
    }

    public static double arg(java.util.Map map, java.lang.String s, int i, double d, double d1, double d2)
    {
        double d3 = com.maddox.rts.Cmd.arg(map, s, i, d);
        if(d3 < d1)
            d3 = d1;
        if(d3 > d2)
            d3 = d2;
        return d3;
    }

    public static java.lang.String args(java.util.Map map, java.lang.String s)
    {
        if(map.containsKey(s))
        {
            java.util.List list = (java.util.List)map.get(s);
            if(list != null && list.size() > 0)
            {
                java.lang.StringBuffer stringbuffer = new StringBuffer();
                for(int i = 0; i < list.size(); i++)
                {
                    if(i != 0)
                        stringbuffer.append(' ');
                    stringbuffer.append((java.lang.String)list.get(i));
                }

                return stringbuffer.toString();
            }
        }
        return null;
    }

    protected void ERR_HARD(java.lang.String s)
    {
        if(ERR_HARD)
        {
            java.lang.String s1 = (java.lang.String)_properties.get("NAME");
            java.lang.System.err.println("ERROR " + (s1 == null ? s : s1 + ": " + s));
        }
    }

    protected void ERR_SOFT(java.lang.String s)
    {
        if(ERR_SOFT)
        {
            java.lang.String s1 = (java.lang.String)_properties.get("NAME");
            java.lang.System.err.println(s1 == null ? s : s1 + ": " + s);
        }
    }

    protected void INFO_HARD(java.lang.String s)
    {
        if(INFO_HARD)
            java.lang.System.out.println(s);
    }

    protected void INFO_SOFT(java.lang.String s)
    {
        if(INFO_SOFT)
            java.lang.System.out.println(s);
    }

    public boolean isRawFormat()
    {
        return false;
    }

    public java.util.Map properties()
    {
        return _properties;
    }

    public int levelAccess()
    {
        return _levelAccess;
    }

    public Cmd()
    {
        _properties = new HashMap();
        param = new TreeMap();
        param.put("_$$", null);
        _properties.put("PARAMS", param);
        _levelAccess = 0;
    }

    public static boolean INFO_HARD = true;
    public static boolean INFO_SOFT = true;
    public static boolean ERR_HARD = true;
    public static boolean ERR_SOFT = true;
    public static final java.lang.String PROP_NAME = "NAME";
    public static final java.lang.String PROP_HELP = "HELP";
    public static final java.lang.String PROP_PARAMS = "PARAMS";
    public static final java.lang.String TEMP = "_$$$$$";
    public static final java.lang.String PREVPREV = "_$$2";
    public static final java.lang.String PREV = "_$$1";
    public static final java.lang.String CURRENT = "_$$0";
    public static final java.lang.String ARGS = "_$$";
    protected java.util.HashMap _properties;
    protected java.util.TreeMap param;
    protected int _levelAccess;

}
