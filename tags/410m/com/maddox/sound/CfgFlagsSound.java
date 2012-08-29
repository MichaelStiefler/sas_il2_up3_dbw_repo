// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgFlagsSound.java

package com.maddox.sound;

import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.sound:
//            CfgFlagsInfo, AudioDevice

public class CfgFlagsSound
    implements com.maddox.rts.CfgFlags
{

    public CfgFlagsSound(java.lang.String s, com.maddox.sound.CfgFlagsInfo acfgflagsinfo[])
    {
        sectName = null;
        ini = null;
        name = s;
        flags = acfgflagsinfo;
        applyState = new boolean[acfgflagsinfo.length];
        for(int i = 0; i < acfgflagsinfo.length; i++)
        {
            applyState[i] = true;
            acfgflagsinfo[i].value = com.maddox.sound.AudioDevice.getControl(acfgflagsinfo[i].code) != 0;
        }

    }

    public int firstFlag()
    {
        return 0;
    }

    public int countFlags()
    {
        return flags.length;
    }

    public boolean defaultFlag(int i)
    {
        return flags[i].isDefault;
    }

    public java.lang.String nameFlag(int i)
    {
        return flags[i].name;
    }

    public boolean isPermanentFlag(int i)
    {
        return true;
    }

    public boolean isEnabledFlag(int i)
    {
        return com.maddox.sound.AudioDevice.isControlEnabled(flags[i].code) && com.maddox.sound.AudioDevice._cur_en;
    }

    public boolean get(int i)
    {
        return flags[i].value;
    }

    public void set(int i, boolean flag)
    {
        flags[i].value = flag;
    }

    public int apply(int i)
    {
        applyState[i] = com.maddox.sound.AudioDevice.setControl(flags[i].code, flags[i].value ? 1 : 0);
        return 0;
    }

    public boolean needApply()
    {
        boolean flag = false;
        int i = 0;
        do
        {
            if(i >= flags.length)
                break;
            if(!applyState[i])
            {
                flag = true;
                break;
            }
            i++;
        } while(true);
        return flag;
    }

    public int applyStatus(int i)
    {
        return 0;
    }

    public java.lang.String name()
    {
        return name;
    }

    public boolean isPermanent()
    {
        return true;
    }

    public boolean isEnabled()
    {
        return true;
    }

    public void load(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        ini = inifile;
        sectName = s;
        com.maddox.rts.CfgTools.load(this, inifile, s);
    }

    public com.maddox.rts.IniFile loadedSectFile()
    {
        return ini;
    }

    public java.lang.String loadedSectName()
    {
        return sectName;
    }

    public void save()
    {
        save(ini, sectName);
    }

    public void save(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        ini = inifile;
        sectName = s;
        com.maddox.rts.CfgTools.save(false, this, inifile, s);
    }

    public int apply()
    {
        for(int i = 0; i < flags.length; i++)
            apply(i);

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

    java.lang.String name;
    com.maddox.sound.CfgFlagsInfo flags[];
    boolean applyState[];
    java.lang.String sectName;
    com.maddox.rts.IniFile ini;
}
