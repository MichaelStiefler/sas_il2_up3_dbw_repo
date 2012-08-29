// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgTools.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.util.Collection;

// Referenced classes of package com.maddox.rts:
//            Cfg, RTSConf, CfgInt, IniFile, 
//            CfgFlags

public class CfgTools
{

    public CfgTools()
    {
    }

    public static final void register(com.maddox.rts.Cfg cfg)
    {
        com.maddox.rts.RTSConf.cur.cfgMap.put(cfg.name(), cfg);
    }

    public static final com.maddox.rts.Cfg get(java.lang.String s)
    {
        return (com.maddox.rts.Cfg)com.maddox.rts.RTSConf.cur.cfgMap.get(s);
    }

    public static final java.util.Collection all()
    {
        return com.maddox.rts.RTSConf.cur.cfgMap.values();
    }

    public static void load(com.maddox.rts.CfgInt cfgint, com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        if(!cfgint.isEnabled())
            return;
        if(inifile == null || s == null || s.length() == 0)
        {
            return;
        } else
        {
            cfgint.set(inifile.get(s, cfgint.name(), cfgint.defaultState(), cfgint.firstState(), (cfgint.firstState() + cfgint.countStates()) - 1));
            return;
        }
    }

    public static void load(com.maddox.rts.CfgFlags cfgflags, com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        if(!cfgflags.isEnabled())
            return;
        if(inifile == null || s == null || s.length() == 0)
            return;
        int i = cfgflags.countFlags();
        for(int j = 0; j < i; j++)
        {
            int k = j + cfgflags.firstFlag();
            if(cfgflags.isEnabledFlag(k))
                cfgflags.set(k, inifile.get(s, cfgflags.name() + "." + cfgflags.nameFlag(k), cfgflags.defaultFlag(k)));
        }

    }

    public static void save(boolean flag, com.maddox.rts.CfgInt cfgint, com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        if(!cfgint.isEnabled())
            return;
        if(flag && !cfgint.isPermanent())
            return;
        if(inifile == null || s == null || s.length() == 0)
        {
            return;
        } else
        {
            inifile.set(s, cfgint.name(), cfgint.get());
            return;
        }
    }

    public static void save(boolean flag, com.maddox.rts.CfgFlags cfgflags, com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        if(!cfgflags.isEnabled())
            return;
        if(flag && !cfgflags.isPermanent())
            return;
        if(inifile == null || s == null || s.length() == 0)
            return;
        int i = cfgflags.countFlags();
        for(int j = 0; j < i; j++)
        {
            int k = j + cfgflags.firstFlag();
            com.maddox.rts.CfgTools.save(flag, cfgflags, k, inifile, s);
        }

    }

    public static void save(boolean flag, com.maddox.rts.CfgFlags cfgflags, int i, com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        if(!cfgflags.isEnabledFlag(i))
            return;
        if(flag && !cfgflags.isPermanentFlag(i))
            return;
        if(inifile == null || s == null || s.length() == 0)
        {
            return;
        } else
        {
            inifile.set(s, cfgflags.name() + "." + cfgflags.nameFlag(i), cfgflags.get(i));
            return;
        }
    }
}
