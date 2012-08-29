// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdGC.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;

public class CmdGC extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        java.lang.Runtime.getRuntime().gc();
        java.lang.Runtime.getRuntime().runFinalization();
        INFO_HARD("Memory: total(" + java.lang.Runtime.getRuntime().totalMemory() + ") free(" + java.lang.Runtime.getRuntime().freeMemory() + ")");
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdGC()
    {
        _properties.put("NAME", "GC");
        _levelAccess = 1;
    }
}
