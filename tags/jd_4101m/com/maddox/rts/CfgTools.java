package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.util.Collection;

public class CfgTools
{
  public static final void register(Cfg paramCfg)
  {
    RTSConf.cur.cfgMap.put(paramCfg.name(), paramCfg);
  }

  public static final Cfg get(String paramString) {
    return (Cfg)RTSConf.cur.cfgMap.get(paramString);
  }

  public static final Collection all() {
    return RTSConf.cur.cfgMap.values();
  }

  public static void load(CfgInt paramCfgInt, IniFile paramIniFile, String paramString) {
    if (!paramCfgInt.isEnabled())
      return;
    if ((paramIniFile == null) || (paramString == null) || (paramString.length() == 0)) return;
    paramCfgInt.set(paramIniFile.get(paramString, paramCfgInt.name(), paramCfgInt.defaultState(), paramCfgInt.firstState(), paramCfgInt.firstState() + paramCfgInt.countStates() - 1));
  }

  public static void load(CfgFlags paramCfgFlags, IniFile paramIniFile, String paramString)
  {
    if (!paramCfgFlags.isEnabled())
      return;
    if ((paramIniFile == null) || (paramString == null) || (paramString.length() == 0)) return;
    int i = paramCfgFlags.countFlags();
    for (int j = 0; j < i; j++) {
      int k = j + paramCfgFlags.firstFlag();
      if (paramCfgFlags.isEnabledFlag(k))
        paramCfgFlags.set(k, paramIniFile.get(paramString, paramCfgFlags.name() + "." + paramCfgFlags.nameFlag(k), paramCfgFlags.defaultFlag(k)));
    }
  }

  public static void save(boolean paramBoolean, CfgInt paramCfgInt, IniFile paramIniFile, String paramString) {
    if (!paramCfgInt.isEnabled())
      return;
    if ((paramBoolean) && (!paramCfgInt.isPermanent()))
      return;
    if ((paramIniFile == null) || (paramString == null) || (paramString.length() == 0)) return;
    paramIniFile.set(paramString, paramCfgInt.name(), paramCfgInt.get());
  }

  public static void save(boolean paramBoolean, CfgFlags paramCfgFlags, IniFile paramIniFile, String paramString) {
    if (!paramCfgFlags.isEnabled())
      return;
    if ((paramBoolean) && (!paramCfgFlags.isPermanent()))
      return;
    if ((paramIniFile == null) || (paramString == null) || (paramString.length() == 0)) return;
    int i = paramCfgFlags.countFlags();
    for (int j = 0; j < i; j++) {
      int k = j + paramCfgFlags.firstFlag();
      save(paramBoolean, paramCfgFlags, k, paramIniFile, paramString);
    }
  }

  public static void save(boolean paramBoolean, CfgFlags paramCfgFlags, int paramInt, IniFile paramIniFile, String paramString) {
    if (!paramCfgFlags.isEnabledFlag(paramInt))
      return;
    if ((paramBoolean) && (!paramCfgFlags.isPermanentFlag(paramInt)))
      return;
    if ((paramIniFile == null) || (paramString == null) || (paramString.length() == 0)) return;
    paramIniFile.set(paramString, paramCfgFlags.name() + "." + paramCfgFlags.nameFlag(paramInt), paramCfgFlags.get(paramInt));
  }
}