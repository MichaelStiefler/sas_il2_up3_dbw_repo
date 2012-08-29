// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgIntEngine.java

package com.maddox.il2.engine;

import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.il2.engine:
//            CfgGObj

class CfgIntEngine extends com.maddox.il2.engine.CfgGObj
    implements com.maddox.rts.CfgInt
{

    public java.lang.String name()
    {
        return com.maddox.il2.engine.CfgIntEngine.Name(cppObj);
    }

    public boolean isPermanent()
    {
        return com.maddox.il2.engine.CfgIntEngine.IsPermanent(cppObj);
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

    public int apply()
    {
        if(!isEnabledState(state))
        {
            return 0;
        } else
        {
            int i = applyStatus();
            com.maddox.il2.engine.CfgIntEngine.SetState(cppObj, state);
            return i;
        }
    }

    public int applyStatus()
    {
        int i = com.maddox.il2.engine.CfgIntEngine.GetState(cppObj);
        if(state == i)
            return 0;
        if(!isEnabledState(state))
        {
            return 0;
        } else
        {
            int j = com.maddox.il2.engine.CfgIntEngine.GetStateStatus(cppObj, state) | com.maddox.il2.engine.CfgIntEngine.GetStateStatus(cppObj, i);
            return j;
        }
    }

    public void reset()
    {
        state = com.maddox.il2.engine.CfgIntEngine.GetState(cppObj);
    }

    public int firstState()
    {
        return com.maddox.il2.engine.CfgIntEngine.FirstState(cppObj);
    }

    public int countStates()
    {
        return com.maddox.il2.engine.CfgIntEngine.CountStates(cppObj);
    }

    public int defaultState()
    {
        return com.maddox.il2.engine.CfgIntEngine.DefaultState(cppObj);
    }

    public java.lang.String nameState(int i)
    {
        if(i != clamp(i, firstState(), countStates()))
            return "Unknown";
        else
            return com.maddox.il2.engine.CfgIntEngine.NameState(cppObj, i);
    }

    public boolean isEnabledState(int i)
    {
        if(i != clamp(i, firstState(), countStates()))
            return false;
        else
            return (com.maddox.il2.engine.CfgIntEngine.GetStateStatus(cppObj, i) & 2) != 0;
    }

    public int get()
    {
        return state;
    }

    public void set(int i)
    {
        state = clamp(i, firstState(), countStates());
    }

    public CfgIntEngine(int i)
    {
        super(i);
        state = defaultState();
    }

    private static native java.lang.String Name(int i);

    private static native boolean IsPermanent(int i);

    private static native int FirstState(int i);

    private static native int CountStates(int i);

    private static native int DefaultState(int i);

    private static native java.lang.String NameState(int i, int j);

    private static native int GetStateStatus(int i, int j);

    private static native int GetState(int i);

    private static native int SetState(int i, int j);

    private int state;
}
