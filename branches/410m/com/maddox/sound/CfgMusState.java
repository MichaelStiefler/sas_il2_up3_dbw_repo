// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgMusState.java

package com.maddox.sound;

import com.maddox.rts.CmdEnv;

// Referenced classes of package com.maddox.sound:
//            SoundFlags

public class CfgMusState extends com.maddox.sound.SoundFlags
{

    public CfgMusState()
    {
    }

    public java.lang.String name()
    {
        return "MusState";
    }

    public int countFlags()
    {
        return 3;
    }

    public java.lang.String nameFlag(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            return "takeoff";

        case 1: // '\001'
            return "inflight";

        case 2: // '\002'
            return "crash";
        }
        return null;
    }

    public int apply(int i)
    {
        com.maddox.rts.CmdEnv.top().exec("music APPLY");
        return 0;
    }
}
