package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdSpeedBar extends Cmd
{
  public static final String SHOW = "SHOW";
  public static final String HIDE = "HIDE";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null) return null;
    if (Main.cur().netServerParams.isMaster()) {
      if (paramMap.containsKey("SHOW")) {
        Main.cur().netServerParams.setShowSpeedBar(true);
        return CmdEnv.RETURN_OK;
      }if (paramMap.containsKey("HIDE")) {
        Main.cur().netServerParams.setShowSpeedBar(false);
        return CmdEnv.RETURN_OK;
      }
    }
    INFO_HARD("  SpeedBar is " + (Main.cur().netServerParams.isShowSpeedBar() ? "SHOW" : "HIDE"));
    return CmdEnv.RETURN_OK;
  }

  public CmdSpeedBar() {
    this.param.put("SHOW", null);
    this.param.put("HIDE", null);
    this._properties.put("NAME", "speedbar");
    this._levelAccess = 1;
  }
}