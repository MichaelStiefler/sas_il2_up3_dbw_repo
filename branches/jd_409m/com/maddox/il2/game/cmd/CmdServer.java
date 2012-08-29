package com.maddox.il2.game.cmd;

import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.USGS;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdServer extends Cmd
{
  private static final boolean DEBUG = true;
  public static final String NAME = "NAME";
  public static final String DESCRIPTION = "DESCRIPTION";
  public static final String PWD = "PWD";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur() == null) return null;
    if (Main.cur().netServerParams == null) return null;
    if (!Main.cur().netServerParams.isDedicated()) return null;

    int i = 1;
    String str;
    if ((!USGS.isUsed()) && (Cmd.nargs(paramMap, "NAME") > 0)) {
      str = Cmd.args(paramMap, "NAME");
      Main.cur().netServerParams.setServerName(str);
      Config.cur.netServerName = str;
      i = 0;
    }

    if ((!USGS.isUsed()) && (paramMap.containsKey("PWD"))) {
      i = 0;
      if (Cmd.nargs(paramMap, "PWD") > 0) {
        str = Cmd.args(paramMap, "PWD");
        Main.cur().netServerParams.setPassword(str);
      } else {
        Main.cur().netServerParams.setPassword(null);
      }
    }

    if (Cmd.nargs(paramMap, "DESCRIPTION") > 0) {
      str = Cmd.args(paramMap, "DESCRIPTION");
      Main.cur().netServerParams.serverDescription = str;
      Config.cur.netServerDescription = str;
      Main.cur().netServerParams.USGSupdate();
      i = 0;
    }

    if (i != 0) {
      int j = Main.cur().netServerParams.getType() >> 4;
      if (j == 0) {
        INFO_HARD("Type: Local server");
      }

      INFO_HARD("Name: " + Main.cur().netServerParams.serverName());
      INFO_HARD("Description: " + Main.cur().netServerParams.serverDescription);
    }
    return CmdEnv.RETURN_OK;
  }

  public CmdServer()
  {
    this.jdField_param_of_type_JavaUtilTreeMap.put("NAME", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("DESCRIPTION", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("PWD", null);

    this._properties.put("NAME", "server");
    this._levelAccess = 1;
  }
}