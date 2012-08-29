// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdSet.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdSet extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(map.containsKey("_$$"))
        {
            java.util.List list = (java.util.List)map.get("_$$");
            java.lang.String s = (java.lang.String)list.get(0);
            if(list.size() > 1)
            {
                java.lang.StringBuffer stringbuffer = new StringBuffer();
                for(int i = 1; i < list.size(); i++)
                {
                    if(i != 1)
                        stringbuffer.append(' ');
                    stringbuffer.append(com.maddox.util.QuoteTokenizer.toToken((java.lang.String)list.get(i)));
                }

                if(cmdenv.setAtom(s, stringbuffer.toString()))
                    return stringbuffer;
            } else
            if(map.containsKey("REMOVE"))
                if(cmdenv.delAtom(s))
                    return com.maddox.rts.CmdEnv.RETURN_OK;
                else
                    return null;
        }
        ERR_HARD("Bad command format");
        return null;
    }

    public CmdSet()
    {
        param.put("REMOVE", null);
        _properties.put("NAME", "set");
        _levelAccess = 2;
    }

    public static final java.lang.String REMOVE = "REMOVE";
}
