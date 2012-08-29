package com.maddox.il2.game.cmd;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;

public class CmdTOD extends Cmd
{
  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    float f;
    if (paramMap.containsKey("_$$")) {
      if ((paramCmdEnv.levelAccess() == 0) || ((Mission.isDogfight()) && (Mission.isServer())))
      {
        f = arg(paramMap, "_$$", 0, 12.0F, 0.0F, 24.0F);
        World.setTimeofDay(f);
        if (Config.isUSE_RENDER())
          World.land().cubeFullUpdate();
        if (Mission.cur() != null)
          Mission.cur().replicateTimeofDay();
      }
    } else {
      f = World.getTimeofDay();
      INFO_HARD("Time Of Day: " + f);
    }

    return CmdEnv.RETURN_OK;
  }

  public CmdTOD() {
    this._properties.put("NAME", "tod");
    this._levelAccess = 2;
  }
}