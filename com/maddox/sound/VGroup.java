// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   VGroup.java

package com.maddox.sound;

import com.maddox.rts.CfgInt;
import com.maddox.rts.IniFile;
import java.util.HashMap;

// Referenced classes of package com.maddox.sound:
//            CfgIntSound

public class VGroup
    implements com.maddox.rts.CfgInt
{

    public VGroup(java.lang.String s, int i)
    {
        handle = 0;
        value = 7;
        ini = null;
        sectName = null;
        name = s;
        value = defaultState();
        handle = com.maddox.sound.VGroup.jniCreate(value, countStates(), i);
        map.put(s, this);
    }

    public int firstState()
    {
        return 0;
    }

    public int countStates()
    {
        return com.maddox.sound.CfgIntSound.stdStateNames.length;
    }

    public int defaultState()
    {
        return 7;
    }

    public java.lang.String nameState(int i)
    {
        return com.maddox.sound.CfgIntSound.stdStateNames[i];
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
        com.maddox.sound.VGroup.jniSet(handle, value);
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
        value = inifile.get(s, name, defaultState());
        com.maddox.sound.VGroup.jniSet(handle, value);
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

    protected void finalize()
    {
        com.maddox.sound.VGroup.jniDestroy(handle);
        handle = 0;
    }

    public static com.maddox.sound.VGroup get(java.lang.String s)
    {
        return (com.maddox.sound.VGroup)map.get(s);
    }

    protected static native int jniCreate(int i, int j, int k);

    protected static native void jniSet(int i, int j);

    protected static native void jniDestroy(int i);

    int handle;
    int value;
    java.lang.String name;
    com.maddox.rts.IniFile ini;
    java.lang.String sectName;
    static java.util.HashMap map = new HashMap();

}
