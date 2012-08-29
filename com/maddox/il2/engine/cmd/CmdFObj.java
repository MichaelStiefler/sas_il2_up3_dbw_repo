// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdFObj.java

package com.maddox.il2.engine.cmd;

import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.GObj;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.StrMath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdFObj extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        int i = com.maddox.il2.engine.FObj.NextFObj(0);
        java.lang.Object obj = null;
        if(map.containsKey("_$$"))
        {
            obj = (java.util.List)map.get("_$$");
            java.util.ArrayList arraylist = new ArrayList(((java.util.List) (obj)).size());
            for(int j = 0; j < ((java.util.List) (obj)).size(); j++)
                arraylist.add(((java.lang.String)((java.util.List) (obj)).get(j)).toLowerCase());

            obj = arraylist;
        }
        if(map.containsKey("RELOAD"))
        {
            if(obj != null)
            {
                boolean flag = false;
                for(; i != 0; i = com.maddox.il2.engine.FObj.NextFObj(i))
                {
                    for(int k = 0; k < ((java.util.List) (obj)).size(); k++)
                    {
                        java.lang.String s1 = com.maddox.il2.engine.FObj.Name(i).toLowerCase();
                        if(!com.maddox.util.StrMath.simple((java.lang.String)((java.util.List) (obj)).get(k), s1))
                            continue;
                        flag = true;
                        com.maddox.il2.engine.FObj fobj = (com.maddox.il2.engine.FObj)com.maddox.il2.engine.GObj.getJavaObject(i);
                        if(fobj != null)
                        {
                            if(fobj.ReLoad())
                                INFO_SOFT(fobj.Name() + " Reloaded");
                            else
                                ERR_HARD(fobj.Name() + " NOT Reloaded");
                        } else
                        {
                            ERR_HARD(s1 + " NOT found");
                        }
                        break;
                    }

                }

                if(!flag)
                {
                    ERR_HARD(obj + " NOT found");
                    return null;
                }
            } else
            {
                ERR_HARD("Bad command format");
                return null;
            }
        } else
        {
            for(; i != 0; i = com.maddox.il2.engine.FObj.NextFObj(i))
            {
                java.lang.String s = com.maddox.il2.engine.FObj.Name(i).toLowerCase();
                boolean flag1 = false;
                if(obj != null)
                {
                    for(int l = 0; l < ((java.util.List) (obj)).size(); l++)
                    {
                        if(!com.maddox.util.StrMath.simple((java.lang.String)((java.util.List) (obj)).get(l), s))
                            continue;
                        flag1 = true;
                        break;
                    }

                } else
                {
                    flag1 = true;
                }
                if(flag1)
                    INFO_HARD("(" + com.maddox.il2.engine.GObj.LinkCount(i) + ") " + s + " CppClass:" + com.maddox.il2.engine.GObj.getCppClassName(i));
            }

        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdFObj()
    {
        param.put("RELOAD", null);
        _properties.put("NAME", "fobj");
        _levelAccess = 0;
    }

    public static final java.lang.String RELOAD = "RELOAD";
}
