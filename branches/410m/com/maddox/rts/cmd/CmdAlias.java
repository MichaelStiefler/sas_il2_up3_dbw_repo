// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdAlias.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CmdAlias extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(!map.containsKey("_$$"))
            return show(cmdenv, "*");
        java.util.List list = (java.util.List)map.get("_$$");
        if(map.containsKey("REMOVE"))
        {
            if(cmdenv.delAtom("_$$$alias", list.get(0).toString()))
                return com.maddox.rts.CmdEnv.RETURN_OK;
        } else
        {
            if(list.size() == 1)
                return show(cmdenv, list.get(0).toString());
            java.lang.StringBuffer stringbuffer = new StringBuffer();
            for(int i = 1; i < list.size(); i++)
            {
                if(i != 1)
                    stringbuffer.append(' ');
                stringbuffer.append(com.maddox.util.QuoteTokenizer.toToken(list.get(i).toString()));
            }

            if(cmdenv.setAtom("_$$$alias", list.get(0).toString(), stringbuffer.toString()))
                return com.maddox.rts.CmdEnv.RETURN_OK;
        }
        return null;
    }

    private java.lang.Object show(com.maddox.rts.CmdEnv cmdenv, java.lang.String s)
    {
        java.util.Map map = (java.util.Map)cmdenv.area().get("_$$$alias");
        if(map != null)
        {
            java.util.Set set = map.keySet();
            java.lang.String s1;
            java.lang.String s2;
            for(java.util.Iterator iterator = set.iterator(); iterator.hasNext(); INFO_HARD(s1 + " is " + s2))
            {
                s1 = (java.lang.String)iterator.next();
                s2 = (java.lang.String)map.get(s1);
            }

            return com.maddox.rts.CmdEnv.RETURN_OK;
        } else
        {
            return null;
        }
    }

    public CmdAlias()
    {
        param.put("REMOVE", null);
        _properties.put("NAME", "alias");
        _levelAccess = 2;
    }

    public static final java.lang.String REMOVE = "REMOVE";
}
