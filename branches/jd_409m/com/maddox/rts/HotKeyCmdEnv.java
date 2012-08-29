package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class HotKeyCmdEnv
{
  public static final String DEFAULT = "default";
  public static final String CMDRUN = "cmdrun";
  private String name;
  private boolean bEnabled = true;
  private HashMapExt cmds;
  private HotKeyEnv hotKeyEnv;

  public final String name()
  {
    return this.name;
  }

  public final boolean isEnabled()
  {
    return this.bEnabled;
  }

  public static boolean isEnabled(String paramString)
  {
    HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.envs.get(paramString);
    if (localHotKeyCmdEnv == null) return false;
    return localHotKeyCmdEnv.bEnabled;
  }

  public final void enable(boolean paramBoolean)
  {
    this.bEnabled = paramBoolean;
  }

  public static void enable(String paramString, boolean paramBoolean)
  {
    HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.envs.get(paramString);
    if (localHotKeyCmdEnv == null) return;
    localHotKeyCmdEnv.bEnabled = paramBoolean;
  }

  public final HashMapExt all()
  {
    return this.cmds;
  }

  public final HotKeyCmd get(String paramString)
  {
    return (HotKeyCmd)this.cmds.get(paramString);
  }

  public final void add(HotKeyCmd paramHotKeyCmd)
  {
    this.cmds.put(paramHotKeyCmd.name(), paramHotKeyCmd);
    paramHotKeyCmd.hotKeyCmdEnv = this;
  }

  public final String toString() {
    return name();
  }

  public static final void addCmd(String paramString, HotKeyCmd paramHotKeyCmd)
  {
    HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.envs.get(paramString);
    if (localHotKeyCmdEnv == null)
      localHotKeyCmdEnv = new HotKeyCmdEnv(paramString);
    localHotKeyCmdEnv.cmds.put(paramHotKeyCmd.name(), paramHotKeyCmd);
    paramHotKeyCmd.hotKeyCmdEnv = localHotKeyCmdEnv;
  }

  public static final void addCmd(HotKeyCmd paramHotKeyCmd)
  {
    RTSConf.cur.hotKeyCmdEnvs.cur.cmds.put(paramHotKeyCmd.name(), paramHotKeyCmd);
    paramHotKeyCmd.hotKeyCmdEnv = RTSConf.cur.hotKeyCmdEnvs.cur;
  }

  public static final void setCurrentEnv(String paramString)
  {
    HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.envs.get(paramString);
    if (localHotKeyCmdEnv == null)
      localHotKeyCmdEnv = new HotKeyCmdEnv(paramString);
    RTSConf.cur.hotKeyCmdEnvs.cur = localHotKeyCmdEnv;
  }

  public static final HotKeyCmdEnv currentEnv()
  {
    return RTSConf.cur.hotKeyCmdEnvs.cur;
  }

  public static final List allEnv()
  {
    return RTSConf.cur.hotKeyCmdEnvs.lst;
  }

  public static final HotKeyCmdEnv env(String paramString)
  {
    return (HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.envs.get(paramString);
  }

  public HotKeyEnv hotKeyEnv()
  {
    return this.hotKeyEnv;
  }
  protected HotKeyCmdEnv(String paramString) {
    this.name = paramString;
    this.cmds = new HashMapExt();
    RTSConf.cur.hotKeyCmdEnvs.envs.put(paramString, this);
    RTSConf.cur.hotKeyCmdEnvs.lst.add(this);

    this.hotKeyEnv = ((HotKeyEnv)RTSConf.cur.hotKeyEnvs.envs.get(paramString));
    if (this.hotKeyEnv == null)
      this.hotKeyEnv = new HotKeyEnv(paramString);
  }
}