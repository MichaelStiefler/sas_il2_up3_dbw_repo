package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;

public class CmdGC extends Cmd
{
  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    Runtime.getRuntime().gc();
    Runtime.getRuntime().runFinalization();
    INFO_HARD("Memory: total(" + Runtime.getRuntime().totalMemory() + ") free(" + Runtime.getRuntime().freeMemory() + ")");
    return CmdEnv.RETURN_OK;
  }

  public CmdGC() {
    this._properties.put("NAME", "GC");
    this._levelAccess = 1;
  }
}