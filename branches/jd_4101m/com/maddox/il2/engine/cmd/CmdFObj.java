package com.maddox.il2.engine.cmd;

import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.GObj;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.StrMath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdFObj extends Cmd
{
  public static final String RELOAD = "RELOAD";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = FObj.NextFObj(0);
    Object localObject = null;
    int k;
    if (paramMap.containsKey("_$$")) {
      localObject = (List)paramMap.get("_$$");
      ArrayList localArrayList = new ArrayList(((List)localObject).size());
      for (k = 0; k < ((List)localObject).size(); k++)
        localArrayList.add(((String)((List)localObject).get(k)).toLowerCase());
      localObject = localArrayList;
    }

    if (paramMap.containsKey("RELOAD")) {
      if (localObject != null) {
        int j = 0;
        while (i != 0) {
          for (k = 0; k < ((List)localObject).size(); k++) {
            String str2 = FObj.Name(i).toLowerCase();
            if (StrMath.simple((String)((List)localObject).get(k), str2)) {
              j = 1;
              FObj localFObj = (FObj)(FObj)GObj.getJavaObject(i);
              if (localFObj != null) {
                if (localFObj.ReLoad()) {
                  INFO_SOFT(localFObj.Name() + " Reloaded"); break;
                }
                ERR_HARD(localFObj.Name() + " NOT Reloaded"); break;
              }
              ERR_HARD(str2 + " NOT found");

              break;
            }
          }
          i = FObj.NextFObj(i);
        }
        if (j == 0) {
          ERR_HARD(localObject + " NOT found");
          return null;
        }
      }
      else {
        ERR_HARD("Bad command format");
        return null;
      }
    }
    else while (i != 0) {
        String str1 = FObj.Name(i).toLowerCase();
        k = 0;
        if (localObject != null) {
          for (int m = 0; m < ((List)localObject).size(); m++)
            if (StrMath.simple((String)((List)localObject).get(m), str1)) {
              k = 1;
              break;
            }
        }
        else {
          k = 1;
        }
        if (k != 0)
          INFO_HARD("(" + GObj.LinkCount(i) + ") " + str1 + " CppClass:" + GObj.getCppClassName(i));
        i = FObj.NextFObj(i);
      }

    return CmdEnv.RETURN_OK;
  }

  public CmdFObj() {
    this.param.put("RELOAD", null);
    this._properties.put("NAME", "fobj");
    this._levelAccess = 0;
  }
}