// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdParam.java

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

public class CmdParam extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        java.lang.String s = null;
        java.util.List list = null;
        if(map.containsKey("_$$"))
        {
            list = (java.util.List)map.get("_$$");
            if(list.size() > 0)
            {
                s = (java.lang.String)list.get(0);
                list.remove(0);
            }
        }
        if(s == null)
        {
            ERR_HARD("Bad command format");
            return null;
        }
        com.maddox.rts.Cmd cmd = (com.maddox.rts.Cmd)cmdenv.atom("_$$$command", s);
        if(cmd == null)
        {
            ERR_HARD("Command " + s + " not found");
            return null;
        }
        if(cmdenv.noAccess(cmd))
        {
            ERR_HARD("Access denied");
            return null;
        }
        if(!cmd.properties().containsKey("PARAMS"))
        {
            ERR_HARD("Command " + s + " not supported parameters");
            return null;
        }
        java.util.Map map1 = (java.util.Map)cmd.properties().get("PARAMS");
        if(list.size() > 0)
        {
            java.lang.StringBuffer stringbuffer = null;
            java.util.Iterator iterator = list.iterator();
            if(iterator.hasNext())
            {
                java.lang.String s1 = (java.lang.String)iterator.next();
                if(!map1.containsKey(s1))
                {
                    ERR_HARD("Class " + s + " not supported parameter " + s1);
                    return null;
                }
                s = s1;
            }
            while(iterator.hasNext()) 
            {
                java.lang.String s2 = (java.lang.String)iterator.next();
                if(map1.containsKey(s2))
                {
                    map1.put(s, stringbuffer != null ? ((java.lang.Object) (stringbuffer.toString())) : null);
                    s = s2;
                    stringbuffer = null;
                } else
                {
                    if(stringbuffer == null)
                        stringbuffer = new StringBuffer();
                    else
                        stringbuffer.append(' ');
                    stringbuffer.append(com.maddox.util.QuoteTokenizer.toToken(s2));
                }
            }
            map1.put(s, stringbuffer != null ? ((java.lang.Object) (stringbuffer.toString())) : null);
            return com.maddox.rts.CmdEnv.RETURN_OK;
        }
        boolean flag = cmdenv.flag("fast");
        boolean flag1 = false;
        if(!flag)
            flag1 = cmdenv.flag("echo");
        if(flag1)
        {
            java.util.Set set = map1.keySet();
            for(java.util.Iterator iterator1 = set.iterator(); iterator1.hasNext();)
            {
                java.lang.Object obj = iterator1.next();
                java.lang.Object obj1 = map1.get(obj);
                if(obj1 != null)
                    INFO_HARD(obj.toString() + " is " + obj1.toString());
                else
                    INFO_HARD(obj.toString() + " is NULL");
            }

        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdParam()
    {
        param.remove("_$$");
        _properties.put("NAME", "param");
        _levelAccess = 2;
    }
}
