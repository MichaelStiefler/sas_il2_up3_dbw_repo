package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.util.HashMapExt;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class CmdHotKeyCmd extends Cmd
{
  public static final String ENV = "ENV";
  public static final String EXEC = "EXEC";
  public static final String TIME = "TIME";
  public static final String START = "START";
  public static final String STOP = "STOP";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    String str = Cmd.exist(paramMap, "ENV") ? Cmd.args(paramMap, "ENV") : null;
    Object localObject;
    if (Cmd.exist(paramMap, "EXEC")) {
      localObject = Cmd.args(paramMap, "EXEC");
      double d = Cmd.exist(paramMap, "TIME") ? Cmd.arg(paramMap, "TIME", 0, 0.0D, 0.0D, 60.0D) : 0.0D;
      int j = HotKeyCmd.exec(d, str, (String)localObject);
      if (j > 0) {
        INFO_SOFT("  " + j + " " + (String)localObject + " executed");
      } else {
        ERR_SOFT("command " + (String)localObject + " not executed");
        return null;
      }
    }
    else
    {
      int i;
      if (Cmd.exist(paramMap, "START")) {
        localObject = Cmd.args(paramMap, "START");
        i = HotKeyCmd.exec(true, str, (String)localObject);
        if (i > 0) {
          INFO_SOFT("  " + i + " " + (String)localObject + " started");
        } else {
          ERR_SOFT("command " + (String)localObject + " not started");
          return null;
        }
      }
      else if (Cmd.exist(paramMap, "STOP")) {
        localObject = Cmd.args(paramMap, "STOP");
        i = HotKeyCmd.exec(false, str, (String)localObject);
        if (i > 0) {
          INFO_SOFT("  " + i + " " + (String)localObject + " stoped");
        } else {
          ERR_SOFT("command " + (String)localObject + " not stoped");
          return null;
        }
      }
      else
      {
        HotKeyCmdEnv localHotKeyCmdEnv;
        if (((Cmd.exist(paramMap, "ENV")) && (0 == Cmd.nargs(paramMap, "ENV"))) || (!Cmd.exist(paramMap, "ENV"))) {
          localObject = HotKeyCmdEnv.allEnv().iterator();
          INFO_HARD("HotKeyCmd environments:");
          while (((Iterator)localObject).hasNext()) {
            localHotKeyCmdEnv = (HotKeyCmdEnv)((Iterator)localObject).next();
            INFO_HARD(localHotKeyCmdEnv.name() + (localHotKeyCmdEnv.isEnabled() ? " ENABLED" : " DISABLED"));
          }
        } else {
          localObject = Cmd.args(paramMap, "ENV");
          if (localObject == null) localObject = "default";
          localHotKeyCmdEnv = HotKeyCmdEnv.env((String)localObject);
          if (localHotKeyCmdEnv == null) {
            ERR_SOFT("environment '" + (String)localObject + "' not present");
            return null;
          }
          INFO_HARD("HotKeyCmd environment: " + (String)localObject);
          Iterator localIterator = localHotKeyCmdEnv.all().entrySet().iterator();
          while (localIterator.hasNext()) {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            INFO_HARD((String)localEntry.getKey() + (((HotKeyCmd)localEntry.getValue()).isRealTime() ? " REALTIME" : ""));
          }
        }
      }
    }
    return CmdEnv.RETURN_OK;
  }

  public CmdHotKeyCmd() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("ENV", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("EXEC", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TIME", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TIME", null);
    this._properties.put("NAME", "hotkeycmd");
    this._levelAccess = 1;
  }
}