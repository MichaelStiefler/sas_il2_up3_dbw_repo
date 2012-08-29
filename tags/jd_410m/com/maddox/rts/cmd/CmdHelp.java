package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.StrMath;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CmdHelp extends Cmd
{
  public static final String SIZE = "SIZE";
  public static final String WIDTH = "WIDTH";
  public static final String NAMES = "NAMES";
  private static StringBuffer buf = new StringBuffer();

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = 10;
    if (paramMap.containsKey("SIZE")) {
      List localList1 = (List)paramMap.get("SIZE");
      if (localList1.size() > 0) {
        String str1 = localList1.get(0).toString();
        i = Integer.parseInt(str1);
        if (i < 8) i = 8;
        if (i > 40) i = 40;
      }
    }

    int j = 0;
    int k = 0;
    if (paramMap.containsKey("WIDTH")) {
      List localList2 = (List)paramMap.get("WIDTH");
      if (localList2.size() > 0) {
        String str2 = localList2.get(0).toString();
        j = Integer.parseInt(str2);
        if (j < 0) j = 0;
      }
    }

    int m = 1;
    int n = 0;
    if (paramMap.containsKey("NAMES")) {
      n = 1;
    }
    List localList3 = null;
    if (paramMap.containsKey("_$$"))
      localList3 = (List)paramMap.get("_$$");
    else {
      n = 1;
    }
    CmdEnv localCmdEnv = paramCmdEnv;
    int i1 = 0;
    while (localCmdEnv != null) {
      Map localMap = (Map)localCmdEnv.area().get("_$$$command");
      if (localMap != null) {
        Set localSet = localMap.keySet();
        Iterator localIterator = localSet.iterator();
        while (localIterator.hasNext()) {
          String str3 = (String)localIterator.next();
          Cmd localCmd = (Cmd)localMap.get(str3);
          String str4 = (String)localCmd.properties().get("HELP");
          if ((str4 != null) && (!paramCmdEnv.noAccess(localCmd))) {
            if (localList3 != null) {
              m = 0;
              for (int i2 = 0; i2 < localList3.size(); i2++) {
                if (StrMath.simple((String)localList3.get(i2), str3)) {
                  if (n != 0) {
                    m = 1; break;
                  }
                  INFO_HARD("<" + str3 + ">");
                  INFO_HARD("  " + str4);

                  break;
                }
              }
            }

            if ((n != 0) && (m != 0)) {
              i1 = typeName(i1, i, str3);
              if (j > 0) {
                while (i1 / j != k) {
                  INFO_HARD("");
                  k++;
                }
              }
            }
          }
        }
      }
      localCmdEnv = localCmdEnv.parent();
    }
    if ((localList3 == null) || (n != 0)) {
      INFO_HARD("");
    }
    return CmdEnv.RETURN_OK;
  }

  private int typeName(int paramInt1, int paramInt2, String paramString)
  {
    int i = (paramInt1 / paramInt2 + 1) * paramInt2;
    int j = i - paramInt1;
    if (j < paramString.length() + 1)
      j = paramString.length() + 1;
    i = paramInt1 + j;
    buf.delete(0, buf.length());
    buf.append(' ');
    buf.append(paramString);
    j -= paramString.length() + 1;
    while (j-- > 0)
      buf.append(' ');
    if (INFO_HARD) {
      System.out.print(buf.toString());
    }
    return i;
  }

  public CmdHelp()
  {
    this.param.put("SIZE", "10");
    this.param.put("WIDTH", "0");
    this.param.put("NAMES", null);
    this._properties.put("NAME", "help");
    this._levelAccess = 2;
  }
}