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

public class CmdParam extends Cmd
{
  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    Object localObject1 = null;
    List localList = null;
    if (paramMap.containsKey("_$$")) {
      localList = (List)paramMap.get("_$$");
      if (localList.size() > 0) {
        localObject1 = (String)localList.get(0);
        localList.remove(0);
      }
    }
    if (localObject1 == null) {
      ERR_HARD("Bad command format");
      return null;
    }

    Cmd localCmd = (Cmd)paramCmdEnv.atom("_$$$command", (String)localObject1);
    if (localCmd == null) {
      ERR_HARD("Command " + (String)localObject1 + " not found");
      return null;
    }

    if (paramCmdEnv.noAccess(localCmd)) {
      ERR_HARD("Access denied");
      return null;
    }

    if (!localCmd.properties().containsKey("PARAMS")) {
      ERR_HARD("Command " + (String)localObject1 + " not supported parameters");
      return null;
    }

    Map localMap = (Map)localCmd.properties().get("PARAMS");
    Object localObject2;
    if (localList.size() > 0) {
      StringBuffer localStringBuffer = null;
      Iterator localIterator1 = localList.iterator();

      if (localIterator1.hasNext()) {
        localObject2 = (String)localIterator1.next();
        if (!localMap.containsKey(localObject2)) {
          ERR_HARD("Class " + (String)localObject1 + " not supported parameter " + (String)localObject2);
          return null;
        }
        localObject1 = localObject2;
      }

      while (localIterator1.hasNext()) {
        localObject2 = (String)localIterator1.next();
        if (localMap.containsKey(localObject2)) {
          localMap.put(localObject1, localStringBuffer == null ? null : localStringBuffer.toString());
          localObject1 = localObject2;
          localStringBuffer = null;
        } else {
          if (localStringBuffer == null) localStringBuffer = new StringBuffer(); else
            localStringBuffer.append(' ');
          localStringBuffer.append(QuoteTokenizer.toToken((String)localObject2));
        }
      }
      localMap.put(localObject1, localStringBuffer == null ? null : localStringBuffer.toString());
      return CmdEnv.RETURN_OK;
    }

    boolean bool1 = paramCmdEnv.flag("fast");
    boolean bool2 = false;
    if (!bool1)
      bool2 = paramCmdEnv.flag("echo");
    if (bool2) {
      localObject2 = localMap.keySet();
      Iterator localIterator2 = ((Set)localObject2).iterator();
      while (localIterator2.hasNext()) {
        Object localObject3 = localIterator2.next();
        Object localObject4 = localMap.get(localObject3);
        if (localObject4 != null)
          INFO_HARD(localObject3.toString() + " is " + localObject4.toString());
        else {
          INFO_HARD(localObject3.toString() + " is NULL");
        }
      }
    }

    return CmdEnv.RETURN_OK;
  }

  public CmdParam() {
    this.param.remove("_$$");
    this._properties.put("NAME", "param");
    this._levelAccess = 2;
  }
}