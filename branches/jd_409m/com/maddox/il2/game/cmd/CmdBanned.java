package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.Connect;
import com.maddox.il2.net.NetBanned;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdBanned extends Cmd
{
  public static final String NAME = "NAME";
  public static final String PATTERN = "PATTERN";
  public static final String IP = "IP";
  public static final String ADD = "ADD";
  public static final String REM = "REM";
  public static final String LOAD = "LOAD";
  public static final String SAVE = "SAVE";
  public static final String CLEAR = "CLEAR";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null)
      return null;
    int i = (paramMap.containsKey("NAME")) || (paramMap.containsKey("PATTERN")) || (paramMap.containsKey("IP")) ? 1 : 0;

    NetBanned localNetBanned = ((Connect)NetEnv.cur().connect).banned;
    int j = 1;

    if (paramMap.containsKey("CLEAR")) {
      j = 0;
      if (i != 0) {
        if (paramMap.containsKey("NAME")) localNetBanned.name.clear();
        if (paramMap.containsKey("PATTERN")) localNetBanned.patt.clear();
        if (paramMap.containsKey("IP")) localNetBanned.ip.clear(); 
      }
      else {
        localNetBanned.name.clear();
        localNetBanned.patt.clear();
        localNetBanned.ip.clear();
      }
    }

    if (paramMap.containsKey("LOAD")) {
      j = 0;
      String str1 = NetBanned.fileName;
      if (Cmd.nargs(paramMap, "LOAD") > 0)
        str1 = Cmd.arg(paramMap, "LOAD", 0);
      localNetBanned.load(str1);
    }
    int k;
    int n;
    String str3;
    if ((paramMap.containsKey("ADD")) && (i != 0)) {
      if (paramMap.containsKey("NAME")) {
        j = 0;
        k = Cmd.nargs(paramMap, "NAME");
        for (n = 0; n < k; n++) {
          str3 = Cmd.arg(paramMap, "NAME", n);
          if (!localNetBanned.name.contains(str3))
            localNetBanned.name.add(str3);
        }
      }
      if (paramMap.containsKey("PATTERN")) {
        j = 0;
        k = Cmd.nargs(paramMap, "PATTERN");
        for (n = 0; n < k; n++) {
          str3 = Cmd.arg(paramMap, "PATTERN", n);
          if (!localNetBanned.patt.contains(str3))
            localNetBanned.patt.add(str3);
        }
      }
      if (paramMap.containsKey("IP")) {
        j = 0;
        k = Cmd.nargs(paramMap, "IP");
        for (n = 0; n < k; n++) {
          str3 = Cmd.arg(paramMap, "IP", n);
          int[][] arrayOfInt1 = localNetBanned.ipItem(str3);
          if (arrayOfInt1 != null) {
            if (localNetBanned.findIpItem(arrayOfInt1) == -1)
              localNetBanned.ip.add(arrayOfInt1);
          }
          else ERR_HARD("Unknown format: " + str3);
        }
      }

    }

    if ((paramMap.containsKey("REM")) && (i != 0))
    {
      int i1;
      if (paramMap.containsKey("NAME")) {
        j = 0;
        k = Cmd.nargs(paramMap, "NAME");
        for (n = 0; n < k; n++) {
          str3 = Cmd.arg(paramMap, "NAME", n);
          i1 = localNetBanned.name.indexOf(str3);
          if (i1 >= 0)
            localNetBanned.name.remove(i1);
        }
      }
      if (paramMap.containsKey("PATTERN")) {
        j = 0;
        k = Cmd.nargs(paramMap, "PATTERN");
        for (n = 0; n < k; n++) {
          str3 = Cmd.arg(paramMap, "PATTERN", n);
          i1 = localNetBanned.patt.indexOf(str3);
          if (i1 >= 0)
            localNetBanned.patt.remove(i1);
        }
      }
      if (paramMap.containsKey("IP")) {
        j = 0;
        k = Cmd.nargs(paramMap, "IP");
        for (n = 0; n < k; n++) {
          str3 = Cmd.arg(paramMap, "IP", n);
          int[][] arrayOfInt2 = localNetBanned.ipItem(str3);
          if (arrayOfInt2 != null) {
            int i2 = localNetBanned.findIpItem(arrayOfInt2);
            if (i2 != -1)
              localNetBanned.ip.remove(i2);
          } else {
            ERR_HARD("Unknown format: " + str3);
          }
        }
      }
    }

    if (paramMap.containsKey("SAVE")) {
      j = 0;
      String str2 = NetBanned.fileName;
      if (Cmd.nargs(paramMap, "SAVE") > 0)
        str2 = Cmd.arg(paramMap, "SAVE", 0);
      localNetBanned.save(str2);
    }
    if (j != 0)
    {
      int m;
      if (((paramMap.containsKey("NAME")) || (i == 0)) && 
        (localNetBanned.name.size() > 0)) {
        INFO_HARD("Name:");
        for (m = 0; m < localNetBanned.name.size(); m++) {
          INFO_HARD("  " + (String)localNetBanned.name.get(m));
        }
      }
      if (((paramMap.containsKey("PATTERN")) || (i == 0)) && 
        (localNetBanned.patt.size() > 0)) {
        INFO_HARD("Pattern:");
        for (m = 0; m < localNetBanned.patt.size(); m++) {
          INFO_HARD("  " + (String)localNetBanned.patt.get(m));
        }
      }
      if (((paramMap.containsKey("IP")) || (i == 0)) && 
        (localNetBanned.ip.size() > 0)) {
        INFO_HARD("IP:");
        for (m = 0; m < localNetBanned.ip.size(); m++) {
          INFO_HARD("  " + localNetBanned.ipItem(m));
        }
      }
    }

    return CmdEnv.RETURN_OK;
  }

  public CmdBanned() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("NAME", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("PATTERN", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("IP", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("ADD", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("REM", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOAD", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("SAVE", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("CLEAR", null);
    this._properties.put("NAME", "banned");
    this._levelAccess = 1;
  }
}