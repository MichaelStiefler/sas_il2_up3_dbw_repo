// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgNpFlags.java

package com.maddox.sound;

import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.sound:
//            SoundFlags, AudioDevice

public class CfgNpFlags extends com.maddox.sound.SoundFlags
{

    public CfgNpFlags()
    {
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
            return "Enabled";

        case 1: // '\001'
            return "PTTMode";

        case 2: // '\002'
            return "PlayClicks";
        }
        return null;
    }

    public java.lang.String name()
    {
        return "RadioFlags";
    }

    public void load(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        super.load(inifile, s);
        int i = inifile.get(s, "Preemphasis", 85);
        com.maddox.sound.AudioDevice.setControl(506, i);
        i = inifile.get(s, "RadioLatency", 50);
        com.maddox.sound.AudioDevice.setControl(507, i);
        com.maddox.sound.AudioDevice.setAGC(inifile.get(s, "AGC", true));
    }

    public void save(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        super.save(inifile, s);
        inifile.set(s, "Preemphasis", com.maddox.sound.AudioDevice.getControl(506));
        inifile.set(s, "RadioLatency", com.maddox.sound.AudioDevice.getControl(507));
        inifile.set(s, "AGC", com.maddox.sound.AudioDevice.getAGC());
    }

    public int apply(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            com.maddox.sound.AudioDevice.setPTTMode((value & 2) != 0);
            break;

        case 2: // '\002'
            com.maddox.sound.AudioDevice.setPhoneFX((value & 4) != 0);
            break;
        }
        return 0;
    }

    public int apply()
    {
        for(int i = 0; i < countFlags(); i++)
            apply(i);

        return 0;
    }

    protected static final java.lang.String emphName = "Preemphasis";
    protected static final java.lang.String latnName = "RadioLatency";
    protected static final java.lang.String agcName = "AGC";
}
