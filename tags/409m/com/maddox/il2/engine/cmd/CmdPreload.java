// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdPreload.java

package com.maddox.il2.engine.cmd;

import com.maddox.il2.engine.Pre;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdPreload extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(map.containsKey("REGISTER"))
            com.maddox.il2.engine.Pre.register(true);
        if(map.containsKey("NOREGISTER"))
            com.maddox.il2.engine.Pre.register(false);
        if(map.containsKey("CLEAR"))
            com.maddox.il2.engine.Pre.clear();
        if(map.containsKey("SAVE"))
            com.maddox.il2.engine.Pre.save(com.maddox.rts.Cmd.arg(map, "SAVE", 0));
        if(map.containsKey("_$$"))
        {
            java.util.List list = (java.util.List)map.get("_$$");
            for(int i = 0; i < list.size(); i++)
                com.maddox.il2.engine.Pre.loading((java.lang.String)list.get(i));

        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdPreload()
    {
        param.put("SAVE", null);
        param.put("REGISTER", null);
        param.put("NOREGISTER", null);
        param.put("CLEAR", null);
        _properties.put("NAME", "preload");
        _levelAccess = 0;
    }

    public static final java.lang.String SAVE = "SAVE";
    public static final java.lang.String REGISTER = "REGISTER";
    public static final java.lang.String NOREGISTER = "NOREGISTER";
    public static final java.lang.String CLEAR = "CLEAR";
}
