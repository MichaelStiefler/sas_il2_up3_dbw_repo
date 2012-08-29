// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdHelp.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.StrMath;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CmdHelp extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        int i = 10;
        if(map.containsKey("SIZE"))
        {
            java.util.List list = (java.util.List)map.get("SIZE");
            if(list.size() > 0)
            {
                java.lang.String s = list.get(0).toString();
                i = java.lang.Integer.parseInt(s);
                if(i < 8)
                    i = 8;
                if(i > 40)
                    i = 40;
            }
        }
        int j = 0;
        int k = 0;
        if(map.containsKey("WIDTH"))
        {
            java.util.List list1 = (java.util.List)map.get("WIDTH");
            if(list1.size() > 0)
            {
                java.lang.String s1 = list1.get(0).toString();
                j = java.lang.Integer.parseInt(s1);
                if(j < 0)
                    j = 0;
            }
        }
        boolean flag = true;
        boolean flag1 = false;
        if(map.containsKey("NAMES"))
            flag1 = true;
        java.util.List list2 = null;
        if(map.containsKey("_$$"))
            list2 = (java.util.List)map.get("_$$");
        else
            flag1 = true;
        com.maddox.rts.CmdEnv cmdenv1 = cmdenv;
        int l = 0;
        for(; cmdenv1 != null; cmdenv1 = cmdenv1.parent())
        {
            java.util.Map map1 = (java.util.Map)cmdenv1.area().get("_$$$command");
            if(map1 != null)
            {
                java.util.Set set = map1.keySet();
                for(java.util.Iterator iterator = set.iterator(); iterator.hasNext();)
                {
                    java.lang.String s2 = (java.lang.String)iterator.next();
                    com.maddox.rts.Cmd cmd = (com.maddox.rts.Cmd)map1.get(s2);
                    java.lang.String s3 = (java.lang.String)cmd.properties().get("HELP");
                    if(s3 != null && !cmdenv.noAccess(cmd))
                    {
                        if(list2 != null)
                        {
                            flag = false;
                            for(int i1 = 0; i1 < list2.size(); i1++)
                            {
                                if(!com.maddox.util.StrMath.simple((java.lang.String)list2.get(i1), s2))
                                    continue;
                                if(flag1)
                                {
                                    flag = true;
                                } else
                                {
                                    INFO_HARD("<" + s2 + ">");
                                    INFO_HARD("  " + s3);
                                }
                                break;
                            }

                        }
                        if(flag1 && flag)
                        {
                            l = typeName(l, i, s2);
                            if(j > 0)
                                for(; l / j != k; k++)
                                    INFO_HARD("");

                        }
                    }
                }

            }
        }

        if(list2 == null || flag1)
            INFO_HARD("");
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private int typeName(int i, int j, java.lang.String s)
    {
        int k = (i / j + 1) * j;
        int l = k - i;
        if(l < s.length() + 1)
            l = s.length() + 1;
        k = i + l;
        buf.delete(0, buf.length());
        buf.append(' ');
        buf.append(s);
        for(l -= s.length() + 1; l-- > 0;)
            buf.append(' ');

        if(com.maddox.rts.Cmd.INFO_HARD)
            java.lang.System.out.print(buf.toString());
        return k;
    }

    public CmdHelp()
    {
        param.put("SIZE", "10");
        param.put("WIDTH", "0");
        param.put("NAMES", null);
        _properties.put("NAME", "help");
        _levelAccess = 2;
    }

    public static final java.lang.String SIZE = "SIZE";
    public static final java.lang.String WIDTH = "WIDTH";
    public static final java.lang.String NAMES = "NAMES";
    private static java.lang.StringBuffer buf = new StringBuffer();

}
