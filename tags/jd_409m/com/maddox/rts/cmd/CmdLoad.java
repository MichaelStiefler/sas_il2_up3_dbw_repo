package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdLoad extends Cmd
{
  public static final String NAME = "NAME";
  public static final String HELP = "HELP";
  public static final String PARAM = "PARAM";
  public static final String DELETE = "DELETE";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (!paramMap.containsKey("_$$")) {
      ERR_HARD("Arguments is empty");
      return null;
    }
    List localList = (List)paramMap.get("_$$");
    if (localList.size() > 1) {
      ERR_HARD("Many arguments or unknown keyword");
      return null;
    }

    if (paramMap.containsKey("DELETE")) {
      if (paramCmdEnv.delAtom("_$$$command", (String)localList.get(0))) return CmdEnv.RETURN_OK;
      return null;
    }

    String str1 = (String)localList.get(0);
    Cmd localCmd = null;
    try {
      Class localClass = Class.forName(str1);
      localCmd = (Cmd)localClass.newInstance();
    } catch (Exception localException) {
      if (paramMap.containsKey("NAME"));
      return null;
    }

    Object localObject1 = null;
    String str2 = null;
    Object localObject2;
    if (paramMap.containsKey("PARAM")) {
      if (localCmd.properties().containsKey("PARAMS")) {
        localList = (List)paramMap.get("PARAM");
        localObject2 = (Map)localCmd.properties().get("PARAMS");
        StringBuffer localStringBuffer = null;
        Iterator localIterator = localList.iterator();

        if (localIterator.hasNext()) {
          localObject1 = (String)localIterator.next();
          if (!((Map)localObject2).containsKey(localObject1)) {
            ERR_HARD("Class " + str1 + " not supported parameter " + (String)localObject1);
            return null;
          }
        }
        do
        {
          str2 = (String)localIterator.next();
          if (((Map)localObject2).containsKey(str2)) {
            ((Map)localObject2).put(localObject1, localStringBuffer == null ? null : localStringBuffer.toString());
            localObject1 = str2;
            localStringBuffer = null;
          } else {
            if (localStringBuffer == null) localStringBuffer = new StringBuffer(); else
              localStringBuffer.append(' ');
            localStringBuffer.append(QuoteTokenizer.toToken(str2));
          }
        }
        while (localIterator.hasNext());

        ((Map)localObject2).put(localObject1, localStringBuffer == null ? null : localStringBuffer.toString());
      }
      else {
        ERR_HARD("Class " + str1 + " not supported parameters");
        return null;
      }
    }

    if (paramMap.containsKey("HELP")) {
      localList = (List)paramMap.get("HELP");
      if (localList.size() > 0) {
        localObject2 = new StringBuffer();
        for (int i = 0; i < localList.size(); i++) {
          ((StringBuffer)localObject2).append((String)localList.get(i));
          ((StringBuffer)localObject2).append(' ');
        }
        localCmd.properties().put("HELP", ((StringBuffer)localObject2).toString());
      }
    }

    if (paramMap.containsKey("NAME")) {
      localList = (List)paramMap.get("NAME");
      if (localList.size() != 1) {
        ERR_HARD("Bad format keyword NAME");
        return null;
      }
      localObject1 = (String)localList.get(0);
      localCmd.properties().put("NAME", localObject1);
    } else {
      localObject1 = (String)localCmd.properties().get("NAME");
      if (localObject1 == null) {
        ERR_HARD("Properties NAME not found");
        return null;
      }
    }

    paramCmdEnv.setAtom("_$$$command", (String)localObject1, localCmd);

    return localCmd;
  }

  public CmdLoad() {
    this.jdField_param_of_type_JavaUtilTreeMap.remove("_$$");
    this.jdField_param_of_type_JavaUtilTreeMap.put("NAME", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("HELP", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("PARAM", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("DELETE", null);
    this._properties.put("NAME", "load");
    this._levelAccess = 0;
  }
}