// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgIntValue.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            CfgInt, CfgTools, IniFile

public class CfgIntValue
    implements com.maddox.rts.CfgInt
{

    public int firstState()
    {
        return first;
    }

    public int countStates()
    {
        return count;
    }

    public int defaultState()
    {
        return def;
    }

    public void set(int i)
    {
        state[0] = clamp(i, firstState(), countStates());
    }

    public int get()
    {
        return state[0];
    }

    public int apply()
    {
        return 0;
    }

    public void reset()
    {
    }

    public int applyStatus()
    {
        return 0;
    }

    public void applyExtends(int i)
    {
    }

    public boolean isEnabledState(int i)
    {
        return i == clamp(i, firstState(), countStates());
    }

    public java.lang.String nameState(int i)
    {
        return "Unknown";
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
        com.maddox.rts.CfgTools.load(this, inifile, s);
        iniFile = inifile;
        iniSect = s;
    }

    public void save()
    {
        save(iniFile, iniSect);
    }

    public void save(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        com.maddox.rts.CfgTools.save(true, this, inifile, s);
    }

    public com.maddox.rts.IniFile loadedSectFile()
    {
        return iniFile;
    }

    public java.lang.String loadedSectName()
    {
        return iniSect;
    }

    protected int clamp(int i, int j, int k)
    {
        if(i < j)
            i = j;
        if(i >= j + k)
            i = (j + k) - 1;
        return i;
    }

    public CfgIntValue(java.lang.String s, int ai[], int i, int j, int k)
    {
        name = s;
        state = ai;
        first = i;
        count = j;
        state[0] = def = k;
        com.maddox.rts.CfgTools.register(this);
    }

    private java.lang.String name;
    private int state[];
    private int first;
    private int count;
    private int def;
    protected com.maddox.rts.IniFile iniFile;
    protected java.lang.String iniSect;
}
