// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SoundFlags.java

package com.maddox.sound;

import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;

public class SoundFlags
    implements com.maddox.rts.CfgFlags
{

    public SoundFlags()
    {
        ini = null;
        sect = null;
        value = 0;
    }

    public java.lang.String name()
    {
        return null;
    }

    public int firstFlag()
    {
        return 0;
    }

    public int countFlags()
    {
        return 0;
    }

    public java.lang.String nameFlag(int i)
    {
        return null;
    }

    public boolean defaultFlag(int i)
    {
        return false;
    }

    public boolean isPermanentFlag(int i)
    {
        return true;
    }

    public boolean isEnabledFlag(int i)
    {
        if(com.maddox.il2.engine.Config.cur != null)
            return com.maddox.il2.engine.Config.cur.isSoundUse();
        else
            return true;
    }

    public boolean get(int i)
    {
        return (value & 1 << i) != 0;
    }

    public void set(int i, boolean flag)
    {
        i = 1 << i;
        if(flag)
            value |= i;
        else
            value &= ~i;
    }

    public int applyStatus(int i)
    {
        return 0;
    }

    public boolean isPermanent()
    {
        return true;
    }

    public boolean isEnabled()
    {
        if(com.maddox.il2.engine.Config.cur != null)
            return com.maddox.il2.engine.Config.cur.isSoundUse();
        else
            return true;
    }

    public void load(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        ini = inifile;
        sect = s;
        com.maddox.rts.CfgTools.load(this, inifile, s);
    }

    public com.maddox.rts.IniFile loadedSectFile()
    {
        return ini;
    }

    public java.lang.String loadedSectName()
    {
        return sect;
    }

    public void save()
    {
        save(ini, sect);
    }

    public void save(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        com.maddox.rts.CfgTools.save(false, this, inifile, s);
    }

    public int apply()
    {
        return 0;
    }

    public int apply(int i)
    {
        return 0;
    }

    public int applyStatus()
    {
        return 0;
    }

    public void applyExtends(int i)
    {
    }

    public void reset()
    {
    }

    com.maddox.rts.IniFile ini;
    java.lang.String sect;
    int value;
}
