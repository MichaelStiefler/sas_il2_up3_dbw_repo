// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgFlagsEngine.java

package com.maddox.il2.engine;

import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.il2.engine:
//            CfgGObj

class CfgFlagsEngine extends com.maddox.il2.engine.CfgGObj
    implements com.maddox.rts.CfgFlags
{

    public java.lang.String name()
    {
        return com.maddox.il2.engine.CfgFlagsEngine.Name(cppObj);
    }

    public boolean isPermanent()
    {
        return com.maddox.il2.engine.CfgFlagsEngine.IsPermanent(cppObj);
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
        if(!isEnabled())
            return 0;
        int i = com.maddox.il2.engine.CfgFlagsEngine.FirstFlag(cppObj);
        int j = com.maddox.il2.engine.CfgFlagsEngine.CountFlags(cppObj);
        int k = 0;
        for(int l = 0; l < j; l++)
        {
            if(com.maddox.il2.engine.CfgFlagsEngine.IsEnabledFlag(cppObj, i))
            {
                boolean flag = com.maddox.il2.engine.CfgFlagsEngine.Get(cppObj, i);
                boolean flag1 = (flags & 1 << l) != 0;
                if(flag1 != flag)
                {
                    k |= com.maddox.il2.engine.CfgFlagsEngine.GetFlagStatus(cppObj, i);
                    com.maddox.il2.engine.CfgFlagsEngine.Set(cppObj, i, flag1);
                }
            }
            i++;
        }

        return k;
    }

    public int applyStatus()
    {
        int i = com.maddox.il2.engine.CfgFlagsEngine.FirstFlag(cppObj);
        int j = com.maddox.il2.engine.CfgFlagsEngine.CountFlags(cppObj);
        int k = 0;
        for(int l = 0; l < j; l++)
        {
            if(com.maddox.il2.engine.CfgFlagsEngine.IsEnabledFlag(cppObj, i))
            {
                boolean flag = com.maddox.il2.engine.CfgFlagsEngine.Get(cppObj, i);
                boolean flag1 = (flags & 1 << l) != 0;
                if(flag1 != flag)
                    k |= com.maddox.il2.engine.CfgFlagsEngine.GetFlagStatus(cppObj, i);
            }
            i++;
        }

        return k;
    }

    public void reset()
    {
        int i = com.maddox.il2.engine.CfgFlagsEngine.FirstFlag(cppObj);
        int j = com.maddox.il2.engine.CfgFlagsEngine.CountFlags(cppObj);
        flags = 0;
        for(int k = 0; k < j; k++)
        {
            if(com.maddox.il2.engine.CfgFlagsEngine.IsEnabledFlag(cppObj, i))
            {
                if(com.maddox.il2.engine.CfgFlagsEngine.Get(cppObj, i))
                    flags |= 1 << k;
            } else
            if(com.maddox.il2.engine.CfgFlagsEngine.GetDefaultFlag(cppObj, i))
                flags |= 1 << k;
            i++;
        }

    }

    public int firstFlag()
    {
        return com.maddox.il2.engine.CfgFlagsEngine.FirstFlag(cppObj);
    }

    public int countFlags()
    {
        return com.maddox.il2.engine.CfgFlagsEngine.CountFlags(cppObj);
    }

    public boolean defaultFlag(int i)
    {
        int j = clamp(i, firstFlag(), countFlags());
        if(j != i)
            return false;
        else
            return com.maddox.il2.engine.CfgFlagsEngine.GetDefaultFlag(cppObj, j);
    }

    public java.lang.String nameFlag(int i)
    {
        int j = clamp(i, firstFlag(), countFlags());
        if(j != i)
            return "Unknown";
        else
            return com.maddox.il2.engine.CfgFlagsEngine.NameFlag(cppObj, j);
    }

    public boolean isPermanentFlag(int i)
    {
        int j = clamp(i, firstFlag(), countFlags());
        if(j != i)
            return false;
        else
            return com.maddox.il2.engine.CfgFlagsEngine.IsPermanentFlag(cppObj, j);
    }

    public boolean isEnabledFlag(int i)
    {
        int j = clamp(i, firstFlag(), countFlags());
        if(j != i)
            return false;
        else
            return com.maddox.il2.engine.CfgFlagsEngine.IsEnabledFlag(cppObj, j);
    }

    public boolean get(int i)
    {
        int j = clamp(i, firstFlag(), countFlags());
        if(j != i)
            return false;
        else
            return com.maddox.il2.engine.CfgFlagsEngine.Get(cppObj, j);
    }

    public void set(int i, boolean flag)
    {
        int j = clamp(i, firstFlag(), countFlags());
        if(j == i && com.maddox.il2.engine.CfgFlagsEngine.IsEnabledFlag(cppObj, j))
            if(flag)
                flags |= 1 << j - firstFlag();
            else
                flags &= ~(1 << j - firstFlag());
    }

    public int apply(int i)
    {
        int j = clamp(i, firstFlag(), countFlags());
        if(j != i || !com.maddox.il2.engine.CfgFlagsEngine.IsEnabledFlag(cppObj, j))
            return 0;
        boolean flag = com.maddox.il2.engine.CfgFlagsEngine.Get(cppObj, j);
        boolean flag1 = (flags & 1 << j - firstFlag()) != 0;
        if(flag1 == flag)
        {
            return 0;
        } else
        {
            int k = com.maddox.il2.engine.CfgFlagsEngine.GetFlagStatus(cppObj, j);
            com.maddox.il2.engine.CfgFlagsEngine.Set(cppObj, j, flag1);
            return k;
        }
    }

    public int applyStatus(int i)
    {
        int j = clamp(i, firstFlag(), countFlags());
        if(j != i || !com.maddox.il2.engine.CfgFlagsEngine.IsEnabledFlag(cppObj, j))
            return 0;
        boolean flag = com.maddox.il2.engine.CfgFlagsEngine.Get(cppObj, j);
        boolean flag1 = (flags & 1 << j - firstFlag()) != 0;
        if(flag1 == flag)
        {
            return 0;
        } else
        {
            int k = com.maddox.il2.engine.CfgFlagsEngine.GetFlagStatus(cppObj, j);
            return k;
        }
    }

    public CfgFlagsEngine(int i)
    {
        super(i);
        reset();
    }

    private static native java.lang.String Name(int i);

    private static native boolean IsPermanent(int i);

    private static native int FirstFlag(int i);

    private static native int CountFlags(int i);

    private static native boolean GetDefaultFlag(int i, int j);

    private static native java.lang.String NameFlag(int i, int j);

    private static native boolean IsPermanentFlag(int i, int j);

    private static native boolean IsEnabledFlag(int i, int j);

    private static native int GetFlagStatus(int i, int j);

    private static native boolean Get(int i, int j);

    private static native int Set(int i, int j, boolean flag);

    private int flags;
}
