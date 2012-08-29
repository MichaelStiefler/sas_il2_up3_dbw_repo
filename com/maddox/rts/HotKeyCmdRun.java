// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyCmdRun.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            HotKeyCmd, CmdEnv

public class HotKeyCmdRun extends com.maddox.rts.HotKeyCmd
{

    public void begin()
    {
        env.exec(name());
    }

    public HotKeyCmdRun(com.maddox.rts.CmdEnv cmdenv, boolean flag, java.lang.String s)
    {
        super(flag, s);
        env = cmdenv;
    }

    private com.maddox.rts.CmdEnv env;
}
