package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdExtraOcclusion extends Cmd
{
  public static final String ON = "ON";
  public static final String OFF = "OFF";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null) return null;
    if ((Main.cur().netServerParams.isMaster()) && (!Main.cur().netServerParams.isSingle())) {
      if (paramMap.containsKey("ON")) {
        Main.cur().netServerParams.setExtraOcclusion(true);
        return CmdEnv.RETURN_OK;
      }if (paramMap.containsKey("OFF")) {
        Main.cur().netServerParams.setExtraOcclusion(false);
        return CmdEnv.RETURN_OK;
      }
    }
    INFO_HARD("  ExtraOcclusion is " + (Main.cur().netServerParams.isExtraOcclusion() ? "ON" : "OFF"));
    return CmdEnv.RETURN_OK;
  }

  public CmdExtraOcclusion() {
    this.param.put("ON", null);
    this.param.put("OFF", null);
    this._properties.put("NAME", "extraocclusion");
    this._levelAccess = 1;
  }
}