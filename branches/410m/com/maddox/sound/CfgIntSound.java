// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgIntSound.java

package com.maddox.sound;

import com.maddox.rts.CfgInt;
import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.sound:
//            AudioDevice

public class CfgIntSound
    implements com.maddox.rts.CfgInt
{

    public CfgIntSound(int i, java.lang.String s, java.lang.String as[], int j)
    {
        if(as == null)
            as = stdStateNames;
        code = i;
        name = s;
        stateNames = as;
        defState = j;
        value = com.maddox.sound.AudioDevice.getControl(i);
        needApply = false;
        ini = null;
        sectName = null;
    }

    public int firstState()
    {
        return 0;
    }

    public int countStates()
    {
        return stateNames.length;
    }

    public int defaultState()
    {
        return defState;
    }

    public java.lang.String nameState(int i)
    {
        return stateNames[i];
    }

    public boolean isEnabledState(int i)
    {
        return true;
    }

    public int get()
    {
        return value;
    }

    public void set(int i)
    {
        value = i;
        if(code == 1)
            com.maddox.sound.AudioDevice._cur_en = i > 0;
        needApply = !com.maddox.sound.AudioDevice.setControl(code, value);
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
        return (com.maddox.sound.AudioDevice._cur_en || code == 1) && com.maddox.sound.AudioDevice.isControlEnabled(code);
    }

    public void load(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        ini = inifile;
        sectName = s;
        value = inifile.get(s, name, defState);
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
        inifile.set(s, name, value);
    }

    public int apply()
    {
        com.maddox.sound.AudioDevice.setControl(code, value);
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

    int code;
    int value;
    java.lang.String name;
    int defState;
    java.lang.String stateNames[];
    boolean needApply;
    com.maddox.rts.IniFile ini;
    java.lang.String sectName;
    public static java.lang.String stdStateNames[] = {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
        "10", "11", "12", "13", "14", "15"
    };

}
