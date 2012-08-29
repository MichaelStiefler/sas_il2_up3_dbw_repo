package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CmdAlias extends Cmd
{
  public static final String REMOVE = "REMOVE";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (!paramMap.containsKey("_$$")) {
      return show(paramCmdEnv, "*");
    }
    List localList = (List)paramMap.get("_$$");
    if (paramMap.containsKey("REMOVE")) {
      if (paramCmdEnv.delAtom("_$$$alias", localList.get(0).toString()))
        return CmdEnv.RETURN_OK;
    } else {
      if (localList.size() == 1) {
        return show(paramCmdEnv, localList.get(0).toString());
      }
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 1; i < localList.size(); i++) {
        if (i != 1) localStringBuffer.append(' ');
        localStringBuffer.append(QuoteTokenizer.toToken(localList.get(i).toString()));
      }
      if (paramCmdEnv.setAtom("_$$$alias", localList.get(0).toString(), localStringBuffer.toString())) {
        return CmdEnv.RETURN_OK;
      }
    }
    return null;
  }

  private Object show(CmdEnv paramCmdEnv, String paramString) {
    Map localMap = (Map)paramCmdEnv.area().get("_$$$alias");
    if (localMap != null) {
      Set localSet = localMap.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext()) {
        String str1 = (String)localIterator.next();
        String str2 = (String)localMap.get(str1);
        INFO_HARD(str1 + " is " + str2);
      }
      return CmdEnv.RETURN_OK;
    }
    return null;
  }

  public CmdAlias() {
    this.param.put("REMOVE", null);
    this._properties.put("NAME", "alias");
    this._levelAccess = 2;
  }
}