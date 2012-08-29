package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdArea;
import com.maddox.rts.CmdEnv;
import com.maddox.util.StrMath;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CmdShow extends Cmd
{
  public static final String AREA = "AREA";
  public static final String HISTORY = "HISTORY";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = 0;
    String str = null;

    CmdEnv localCmdEnv = null;
    Object localObject1;
    Set localSet;
    Iterator localIterator;
    Object localObject2;
    if (paramMap.containsKey("HISTORY")) {
      localObject1 = (Map)paramCmdEnv.area().get("_$$$history");
      if (localObject1 != null) {
        localSet = ((Map)localObject1).keySet();
        localIterator = localSet.iterator();
        while (localIterator.hasNext()) {
          localObject2 = localIterator.next();
          Object localObject3 = ((Map)localObject1).get(localObject2);
          INFO_HARD(localObject2.toString() + " is " + localObject3.toString());
        }
        return CmdEnv.RETURN_OK;
      }
      return null;
    }
    List localList;
    if (paramMap.containsKey("AREA")) {
      localList = (List)paramMap.get("AREA");
      if (localList.size() >= 1) {
        str = (String)localList.get(0);
        localCmdEnv = paramCmdEnv;
        localObject1 = null;
        while ((localCmdEnv != null) && (localObject1 == null)) {
          localObject1 = localCmdEnv.atom(null, str);
          localCmdEnv = localCmdEnv.parent();
        }
        if (localObject1 == null) {
          ERR_SOFT("Area " + str + " not found");
          return null;
        }if (!(localObject1 instanceof Map)) {
          ERR_SOFT("Atom " + str + " not Map");
          return null;
        }
      }
    }

    if (paramMap.containsKey("_$$")) {
      localList = (List)paramMap.get("_$$");
    } else {
      localList = null;
      i = 1;
    }

    localCmdEnv = paramCmdEnv;
    while (localCmdEnv != null) {
      localObject1 = null;
      if (str != null) localObject1 = (Map)localCmdEnv.atom(null, str); else {
        localObject1 = localCmdEnv.area();
      }
      if (localObject1 != null) {
        localSet = ((Map)localObject1).keySet();
        localIterator = localSet.iterator();
        while (localIterator.hasNext()) {
          localObject2 = localIterator.next();
          if (localList != null)
          {
            for (int j = 0; (j < localList.size()) && 
              (!StrMath.simple((String)localList.get(j), localObject2.toString())); j++);
            if (j == localList.size())
              localObject2 = null;
          }
          if ((localObject2 != null) && (CmdEnv.validAtomName(localObject2.toString()))) {
            Object localObject4 = ((Map)localObject1).get(localObject2);
            if (localObject4 != null) {
              if ((localObject4 instanceof Map))
                INFO_HARD(localObject2.toString() + " is Map");
              else if ((localObject4 instanceof List))
                INFO_HARD(localObject2.toString() + " is List");
              else if ((localObject4 instanceof CmdArea))
                INFO_HARD(localObject2.toString() + " is Area");
              else
                INFO_HARD(localObject2.toString() + " is " + localObject4.toString());
            }
            else INFO_HARD(localObject2.toString() + " is NULL");

            i = 1;
          }
        }
      }
      localCmdEnv = localCmdEnv.parent();
    }

    if (i != 0) {
      return CmdEnv.RETURN_OK;
    }
    ERR_SOFT("Atom not found");
    return null;
  }

  public CmdShow()
  {
    this.param.put("AREA", null);
    this.param.put("HISTORY", null);
    this._properties.put("NAME", "show");
    this._levelAccess = 2;
  }
}