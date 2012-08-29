// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgMusFlags.java

package com.maddox.sound;

import com.maddox.rts.CmdEnv;

// Referenced classes of package com.maddox.sound:
//            SoundFlags

public class CfgMusFlags extends com.maddox.sound.SoundFlags
{

    public CfgMusFlags()
    {
    }

    public java.lang.String name()
    {
        return "MusFlags";
    }

    public int countFlags()
    {
        return 1;
    }

    public java.lang.String nameFlag(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            return "play";
        }
        return null;
    }

    public int apply(int i)
    {
        com.maddox.rts.CmdEnv.top().exec("music APPLY");
        return 0;
    }
}
