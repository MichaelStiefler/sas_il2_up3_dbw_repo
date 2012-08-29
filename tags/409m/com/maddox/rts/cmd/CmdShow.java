// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdShow.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdArea;
import com.maddox.rts.CmdEnv;
import com.maddox.util.StrMath;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CmdShow extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = false;
        java.lang.String s = null;
        Object obj = null;
        if(map.containsKey("HISTORY"))
        {
            java.util.Map map1 = (java.util.Map)cmdenv.area().get("_$$$history");
            if(map1 != null)
            {
                java.util.Set set = map1.keySet();
                java.lang.Object obj2;
                java.lang.Object obj4;
                for(java.util.Iterator iterator = set.iterator(); iterator.hasNext(); INFO_HARD(obj2.toString() + " is " + obj4.toString()))
                {
                    obj2 = iterator.next();
                    obj4 = map1.get(obj2);
                }

                return com.maddox.rts.CmdEnv.RETURN_OK;
            } else
            {
                return null;
            }
        }
        if(map.containsKey("AREA"))
        {
            java.util.List list = (java.util.List)map.get("AREA");
            if(list.size() >= 1)
            {
                s = (java.lang.String)list.get(0);
                com.maddox.rts.CmdEnv cmdenv1 = cmdenv;
                java.lang.Object obj1;
                for(obj1 = null; cmdenv1 != null && obj1 == null; cmdenv1 = cmdenv1.parent())
                    obj1 = cmdenv1.atom(null, s);

                if(obj1 == null)
                {
                    ERR_SOFT("Area " + s + " not found");
                    return null;
                }
                if(!(obj1 instanceof java.util.Map))
                {
                    ERR_SOFT("Atom " + s + " not Map");
                    return null;
                }
            }
        }
        java.util.List list1;
        if(map.containsKey("_$$"))
        {
            list1 = (java.util.List)map.get("_$$");
        } else
        {
            list1 = null;
            flag = true;
        }
        for(com.maddox.rts.CmdEnv cmdenv2 = cmdenv; cmdenv2 != null; cmdenv2 = cmdenv2.parent())
        {
            java.util.Map map2 = null;
            if(s != null)
                map2 = (java.util.Map)cmdenv2.atom(null, s);
            else
                map2 = cmdenv2.area();
            if(map2 != null)
            {
                java.util.Set set1 = map2.keySet();
                for(java.util.Iterator iterator1 = set1.iterator(); iterator1.hasNext();)
                {
                    java.lang.Object obj3 = iterator1.next();
                    if(list1 != null)
                    {
                        int i;
                        for(i = 0; i < list1.size(); i++)
                            if(com.maddox.util.StrMath.simple((java.lang.String)list1.get(i), obj3.toString()))
                                break;

                        if(i == list1.size())
                            obj3 = null;
                    }
                    if(obj3 != null && com.maddox.rts.CmdEnv.validAtomName(obj3.toString()))
                    {
                        java.lang.Object obj5 = map2.get(obj3);
                        if(obj5 != null)
                        {
                            if(obj5 instanceof java.util.Map)
                                INFO_HARD(obj3.toString() + " is Map");
                            else
                            if(obj5 instanceof java.util.List)
                                INFO_HARD(obj3.toString() + " is List");
                            else
                            if(obj5 instanceof com.maddox.rts.CmdArea)
                                INFO_HARD(obj3.toString() + " is Area");
                            else
                                INFO_HARD(obj3.toString() + " is " + obj5.toString());
                        } else
                        {
                            INFO_HARD(obj3.toString() + " is NULL");
                        }
                        flag = true;
                    }
                }

            }
        }

        if(flag)
        {
            return com.maddox.rts.CmdEnv.RETURN_OK;
        } else
        {
            ERR_SOFT("Atom not found");
            return null;
        }
    }

    public CmdShow()
    {
        param.put("AREA", null);
        param.put("HISTORY", null);
        _properties.put("NAME", "show");
        _levelAccess = 2;
    }

    public static final java.lang.String AREA = "AREA";
    public static final java.lang.String HISTORY = "HISTORY";
}
