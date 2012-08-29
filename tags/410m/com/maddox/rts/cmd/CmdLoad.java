// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdLoad.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdLoad extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(!map.containsKey("_$$"))
        {
            ERR_HARD("Arguments is empty");
            return null;
        }
        java.util.List list = (java.util.List)map.get("_$$");
        if(list.size() > 1)
        {
            ERR_HARD("Many arguments or unknown keyword");
            return null;
        }
        if(map.containsKey("DELETE"))
            if(cmdenv.delAtom("_$$$command", (java.lang.String)list.get(0)))
                return com.maddox.rts.CmdEnv.RETURN_OK;
            else
                return null;
        java.lang.String s = (java.lang.String)list.get(0);
        com.maddox.rts.Cmd cmd = null;
        try
        {
            java.lang.Class class1 = java.lang.Class.forName(s);
            cmd = (com.maddox.rts.Cmd)class1.newInstance();
        }
        catch(java.lang.Exception exception)
        {
            if(!map.containsKey("NAME"));
            return null;
        }
        java.lang.String s1 = null;
        Object obj = null;
        if(map.containsKey("PARAM"))
            if(cmd.properties().containsKey("PARAMS"))
            {
                java.util.List list1 = (java.util.List)map.get("PARAM");
                java.util.Map map1 = (java.util.Map)cmd.properties().get("PARAMS");
                java.lang.StringBuffer stringbuffer1 = null;
                java.util.Iterator iterator = list1.iterator();
                if(iterator.hasNext())
                {
                    s1 = (java.lang.String)iterator.next();
                    if(!map1.containsKey(s1))
                    {
                        ERR_HARD("Class " + s + " not supported parameter " + s1);
                        return null;
                    }
                }
                while(iterator.hasNext()) 
                {
                    java.lang.String s2 = (java.lang.String)iterator.next();
                    if(map1.containsKey(s2))
                    {
                        map1.put(s1, stringbuffer1 != null ? ((java.lang.Object) (stringbuffer1.toString())) : null);
                        s1 = s2;
                        stringbuffer1 = null;
                    } else
                    {
                        if(stringbuffer1 == null)
                            stringbuffer1 = new StringBuffer();
                        else
                            stringbuffer1.append(' ');
                        stringbuffer1.append(com.maddox.util.QuoteTokenizer.toToken(s2));
                    }
                }
                map1.put(s1, stringbuffer1 != null ? ((java.lang.Object) (stringbuffer1.toString())) : null);
            } else
            {
                ERR_HARD("Class " + s + " not supported parameters");
                return null;
            }
        if(map.containsKey("HELP"))
        {
            java.util.List list2 = (java.util.List)map.get("HELP");
            if(list2.size() > 0)
            {
                java.lang.StringBuffer stringbuffer = new StringBuffer();
                for(int i = 0; i < list2.size(); i++)
                {
                    stringbuffer.append((java.lang.String)list2.get(i));
                    stringbuffer.append(' ');
                }

                cmd.properties().put("HELP", stringbuffer.toString());
            }
        }
        if(map.containsKey("NAME"))
        {
            java.util.List list3 = (java.util.List)map.get("NAME");
            if(list3.size() != 1)
            {
                ERR_HARD("Bad format keyword NAME");
                return null;
            }
            s1 = (java.lang.String)list3.get(0);
            cmd.properties().put("NAME", s1);
        } else
        {
            s1 = (java.lang.String)cmd.properties().get("NAME");
            if(s1 == null)
            {
                ERR_HARD("Properties NAME not found");
                return null;
            }
        }
        cmdenv.setAtom("_$$$command", s1, cmd);
        return cmd;
    }

    public CmdLoad()
    {
        param.remove("_$$");
        param.put("NAME", null);
        param.put("HELP", null);
        param.put("PARAM", null);
        param.put("DELETE", null);
        _properties.put("NAME", "load");
        _levelAccess = 0;
    }

    public static final java.lang.String NAME = "NAME";
    public static final java.lang.String HELP = "HELP";
    public static final java.lang.String PARAM = "PARAM";
    public static final java.lang.String DELETE = "DELETE";
}
