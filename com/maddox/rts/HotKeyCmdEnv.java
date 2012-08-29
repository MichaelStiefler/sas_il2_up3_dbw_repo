// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyCmdEnv.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            HotKeyCmd, HotKeyEnv, RTSConf, HotKeyCmdEnvs, 
//            HotKeyEnvs

public final class HotKeyCmdEnv
{

    public final java.lang.String name()
    {
        return name;
    }

    public final boolean isEnabled()
    {
        return bEnabled;
    }

    public static boolean isEnabled(java.lang.String s)
    {
        com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.envs.get(s);
        if(hotkeycmdenv == null)
            return false;
        else
            return hotkeycmdenv.bEnabled;
    }

    public final void enable(boolean flag)
    {
        bEnabled = flag;
    }

    public static void enable(java.lang.String s, boolean flag)
    {
        com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.envs.get(s);
        if(hotkeycmdenv == null)
        {
            return;
        } else
        {
            hotkeycmdenv.bEnabled = flag;
            return;
        }
    }

    public final com.maddox.util.HashMapExt all()
    {
        return cmds;
    }

    public final com.maddox.rts.HotKeyCmd get(java.lang.String s)
    {
        return (com.maddox.rts.HotKeyCmd)cmds.get(s);
    }

    public final void add(com.maddox.rts.HotKeyCmd hotkeycmd)
    {
        cmds.put(hotkeycmd.name(), hotkeycmd);
        hotkeycmd.hotKeyCmdEnv = this;
    }

    public final java.lang.String toString()
    {
        return name();
    }

    public static final void addCmd(java.lang.String s, com.maddox.rts.HotKeyCmd hotkeycmd)
    {
        com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.envs.get(s);
        if(hotkeycmdenv == null)
            hotkeycmdenv = new HotKeyCmdEnv(s);
        hotkeycmdenv.cmds.put(hotkeycmd.name(), hotkeycmd);
        hotkeycmd.hotKeyCmdEnv = hotkeycmdenv;
    }

    public static final void addCmd(com.maddox.rts.HotKeyCmd hotkeycmd)
    {
        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.cur.cmds.put(hotkeycmd.name(), hotkeycmd);
        hotkeycmd.hotKeyCmdEnv = com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.cur;
    }

    public static final void setCurrentEnv(java.lang.String s)
    {
        com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.envs.get(s);
        if(hotkeycmdenv == null)
            hotkeycmdenv = new HotKeyCmdEnv(s);
        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.cur = hotkeycmdenv;
    }

    public static final com.maddox.rts.HotKeyCmdEnv currentEnv()
    {
        return com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.cur;
    }

    public static final java.util.List allEnv()
    {
        return com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.lst;
    }

    public static final com.maddox.rts.HotKeyCmdEnv env(java.lang.String s)
    {
        return (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.envs.get(s);
    }

    public com.maddox.rts.HotKeyEnv hotKeyEnv()
    {
        return hotKeyEnv;
    }

    protected HotKeyCmdEnv(java.lang.String s)
    {
        bEnabled = true;
        name = s;
        cmds = new HashMapExt();
        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.envs.put(s, this);
        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.lst.add(this);
        hotKeyEnv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.envs.get(s);
        if(hotKeyEnv == null)
            hotKeyEnv = new HotKeyEnv(s);
    }

    public static final java.lang.String DEFAULT = "default";
    public static final java.lang.String CMDRUN = "cmdrun";
    private java.lang.String name;
    private boolean bEnabled;
    private com.maddox.util.HashMapExt cmds;
    private com.maddox.rts.HotKeyEnv hotKeyEnv;
}
