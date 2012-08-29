package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdSet extends Cmd
{
  public static final String REMOVE = "REMOVE";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (paramMap.containsKey("_$$")) {
      List localList = (List)paramMap.get("_$$");
      String str = (String)localList.get(0);
      if (localList.size() > 1) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 1; i < localList.size(); i++) {
          if (i != 1) localStringBuffer.append(' ');
          localStringBuffer.append(QuoteTokenizer.toToken((String)localList.get(i)));
        }
        if (paramCmdEnv.setAtom(str, localStringBuffer.toString()))
          return localStringBuffer;
      }
      else if (paramMap.containsKey("REMOVE")) {
        if (paramCmdEnv.delAtom(str)) return CmdEnv.RETURN_OK;
        return null;
      }

    }

    ERR_HARD("Bad command format");
    return null;
  }

  public CmdSet() {
    this.param.put("REMOVE", null);
    this._properties.put("NAME", "set");
    this._levelAccess = 2;
  }
}