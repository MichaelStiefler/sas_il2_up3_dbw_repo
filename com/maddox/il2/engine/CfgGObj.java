// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgGObj.java

package com.maddox.il2.engine;

import com.maddox.rts.Cfg;
import com.maddox.rts.CfgTools;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.il2.engine:
//            GObj, GObjException, Engine, Landscape

public class CfgGObj extends com.maddox.il2.engine.GObj
{

    public com.maddox.rts.IniFile loadedSectFile()
    {
        return iniFile;
    }

    public java.lang.String loadedSectName()
    {
        return iniSect;
    }

    public void applyExtends(int i)
    {
        com.maddox.il2.engine.CfgGObj.ApplyExtends(i);
    }

    public static void ApplyExtends(int i)
    {
        if((i & 4) != 0)
        {
            com.maddox.rts.CmdEnv.top().exec("timeout 0 fobj *.tga RELOAD");
            try
            {
                com.maddox.il2.engine.Engine.land().ReLoadMap();
            }
            catch(java.lang.Exception exception) { }
        }
    }

    protected static com.maddox.rts.Cfg findByName(java.lang.String s)
    {
        com.maddox.rts.Cfg cfg = com.maddox.il2.engine.CfgGObj.FindByName(s);
        if(cfg == null)
        {
            throw new GObjException("CfgGObj " + s + " not found");
        } else
        {
            com.maddox.rts.CfgTools.register(cfg);
            return cfg;
        }
    }

    private static native com.maddox.rts.Cfg FindByName(java.lang.String s);

    protected int clamp(int i, int j, int k)
    {
        if(i < j)
            i = j;
        if(i >= j + k)
            i = (j + k) - 1;
        return i;
    }

    public CfgGObj(int i)
    {
        super(i);
    }

    public static final int ENABLED = 2;
    protected com.maddox.rts.IniFile iniFile;
    protected java.lang.String iniSect;
}
