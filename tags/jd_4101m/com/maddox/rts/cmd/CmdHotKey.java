package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdRun;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.VK;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdHotKey extends Cmd
{
  public static final String CMD = "CMD";
  public static final String ENV = "ENV";
  public static final String CMDRUN = "CMDRUN";
  public static final String REALTIME = "REALTIME";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (exist(paramMap, "_$$")) {
      int i = 0;

      int k = 0;
      if (nargs(paramMap, "_$$") > 1) {
        i = VK.getKeyFromText(arg(paramMap, "_$$", 0));
        k = 1;
      }
      int j = VK.getKeyFromText(arg(paramMap, "_$$", k));
      if (j == 0) {
        ERR_SOFT("unknown HotKey - " + arg(paramMap, "_$$", k));
        return null;
      }
      String str1 = args(paramMap, "ENV");
      if (str1 == null) str1 = "default";
      if (nargs(paramMap, "CMD") > 0) {
        HotKeyEnv.addHotKey(str1, i, j, args(paramMap, "CMD"));
      } else if (nargs(paramMap, "CMDRUN") > 0) {
        String str2 = args(paramMap, "CMDRUN");
        HotKeyEnv.addHotKey(str1, i, j, str2);
        HotKeyCmdEnv.addCmd("cmdrun", new HotKeyCmdRun(paramCmdEnv, exist(paramMap, "REALTIME"), str2));
      } else {
        ERR_SOFT("empty HotKey command name");
        return null;
      }
    }
    else
    {
      Object localObject;
      HotKeyEnv localHotKeyEnv;
      if (((exist(paramMap, "ENV")) && (0 == nargs(paramMap, "ENV"))) || (!exist(paramMap, "ENV"))) {
        localObject = HotKeyEnv.allEnv().iterator();
        INFO_HARD("HotKey environments:");
        while (((Iterator)localObject).hasNext()) {
          localHotKeyEnv = (HotKeyEnv)((Iterator)localObject).next();
          INFO_HARD(localHotKeyEnv.name() + (localHotKeyEnv.isEnabled() ? " ENABLED" : " DISABLED"));
        }
      } else {
        localObject = args(paramMap, "ENV");
        if (localObject == null) localObject = "default";
        localHotKeyEnv = HotKeyEnv.env((String)localObject);
        if (localHotKeyEnv == null) {
          ERR_SOFT("environment '" + (String)localObject + "' not present");
          return null;
        }
        INFO_HARD("HotKey environment: " + (String)localObject);
        showHotKeys(localHotKeyEnv.all());
      }
    }
    return CmdEnv.RETURN_OK;
  }

  private void showHotKeys(HashMapInt paramHashMapInt) {
    HashMapIntEntry localHashMapIntEntry = paramHashMapInt.nextEntry(null);
    while (localHashMapIntEntry != null) {
      int i = localHashMapIntEntry.getKey();
      int j = i >> 16 & 0xFFFF;
      int k = i & 0xFFFF;
      String str = (String)localHashMapIntEntry.getValue();
      if (j != 0) INFO_HARD(VK.getKeyText(j) + " " + VK.getKeyText(k) + " = " + str); else
        INFO_HARD(VK.getKeyText(k) + " = " + str);
      localHashMapIntEntry = paramHashMapInt.nextEntry(localHashMapIntEntry);
    }
  }

  public CmdHotKey() {
    this.param.put("CMD", null);
    this.param.put("ENV", null);
    this.param.put("CMDRUN", null);
    this.param.put("REALTIME", null);
    this._properties.put("NAME", "hotkey");
    this._levelAccess = 1;
  }
}