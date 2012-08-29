package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.SFSReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdFile extends Cmd
{
  public static final String BREAK = "BREAK";
  public static final String CURENV = "CURENV";

  private void saveParams(CmdEnv paramCmdEnv, Object[] paramArrayOfObject)
  {
    for (int i = 0; i < 10; i++) {
      String str = Integer.toString(i);
      if (paramCmdEnv.existAtom(str, false)) {
        paramArrayOfObject[i] = paramCmdEnv.atom(str);
        paramCmdEnv.delAtom(str);
      } else if (paramCmdEnv.existAtom(str, true)) {
        paramCmdEnv.setAtom(str, null);
      }
    }
  }

  private void restoreParams(CmdEnv paramCmdEnv, Object[] paramArrayOfObject) {
    for (int i = 0; i < 10; i++) {
      String str = Integer.toString(i);
      if (paramArrayOfObject[i] != null)
        paramCmdEnv.setAtom(str, paramArrayOfObject[i]);
      else if (paramCmdEnv.existAtom(str, false))
        paramCmdEnv.delAtom(str);
    }
  }

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    String str1 = null;
    Object[] arrayOfObject = new Object[10];
    CmdEnv localCmdEnv = paramCmdEnv;

    saveParams(localCmdEnv, arrayOfObject);

    if (paramMap.containsKey("_$$")) {
      localObject1 = (List)paramMap.get("_$$");
      if (((List)localObject1).size() <= 11) {
        str1 = (String)((List)localObject1).get(0);
        for (int i = 1; i < ((List)localObject1).size(); i++) {
          String str2 = (String)((List)localObject1).get(i);
          paramCmdEnv.setAtom(Integer.toString(i - 1), str2);
        }
      } else {
        restoreParams(localCmdEnv, arrayOfObject);
        ERR_HARD("Bad command format");
        return null;
      }
    } else {
      restoreParams(localCmdEnv, arrayOfObject);
      ERR_HARD("Bad command format");
      return null;
    }

    Object localObject1 = null;
    Object localObject2 = null;
    int j = 1;
    if (!paramMap.containsKey("CURENV")) {
      paramCmdEnv = new CmdEnv(paramCmdEnv);
      j = 0;
    } else {
      localObject1 = paramCmdEnv.atom(null, "_$$0");
      localObject2 = paramCmdEnv.atom(null, "_$$1");
    }

    Object localObject3 = null;
    boolean bool1 = paramMap.containsKey("BREAK");
    boolean bool2 = paramCmdEnv.flag("fast");
    boolean bool3 = false;
    if (!bool2)
      bool3 = paramCmdEnv.flag("echo");
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(str1));
      StringBuffer localStringBuffer = new StringBuffer();
      String str3 = null;
      while (true)
      {
        str3 = localBufferedReader.readLine();
        if (str3 == null)
          break;
        if ((str3.length() > 0) && (!str3.startsWith("#"))) {
          localStringBuffer.append(str3);
          if ((bool3) && (localStringBuffer.charAt(0) != '@')) {
            if (j != 0)
              INFO_SOFT(CmdEnv.top().curNumCmd() + 1 + ">" + str3);
            else
              INFO_SOFT('>' + str3);
          }
          int k = 0;
          for (int m = localStringBuffer.length() - 1; m >= 0; m--) {
            if (localStringBuffer.charAt(m) == '\\') {
              localStringBuffer.setCharAt(m, ' ');
              k = 1;
              break;
            }if (localStringBuffer.charAt(m) != ' ') {
              break;
            }
          }
          if (k == 0) {
            if (localStringBuffer.charAt(0) == '@')
              localStringBuffer.setCharAt(0, ' ');
            localObject3 = paramCmdEnv.exec(getStr(localStringBuffer));
            if ((bool1) && (localObject3 == null)) {
              ERR_HARD("Execute file is breaked");
              break;
            }
            localStringBuffer.delete(0, localStringBuffer.length());
            bool2 = paramCmdEnv.flag("fast");
            if (bool2) { bool3 = false; continue; }
            bool3 = paramCmdEnv.flag("echo");
          }
        }
      }
      localBufferedReader.close();
    } catch (IOException localIOException) {
      ERR_HARD("File " + str1 + " not found");
      localObject3 = null;
    }
    if (j != 0) {
      paramCmdEnv.setAtom(null, "_$$0", localObject1);
      paramCmdEnv.setAtom(null, "_$$1", localObject2);
    }
    restoreParams(localCmdEnv, arrayOfObject);
    return localObject3;
  }

  private String getStr(StringBuffer paramStringBuffer) {
    for (int i = 0; i < paramStringBuffer.length(); i++) {
      if ((paramStringBuffer.charAt(i) != '\\') || 
        (i + 1 >= paramStringBuffer.length())) continue;
      switch (paramStringBuffer.charAt(i + 1)) { case 'n':
        paramStringBuffer.setCharAt(i, '\n'); paramStringBuffer.deleteCharAt(i + 1); break;
      case 'r':
        paramStringBuffer.setCharAt(i, '\r'); paramStringBuffer.deleteCharAt(i + 1); break;
      case 't':
        paramStringBuffer.setCharAt(i, '\t'); paramStringBuffer.deleteCharAt(i + 1); break;
      case 'f':
        paramStringBuffer.setCharAt(i, '\f'); paramStringBuffer.deleteCharAt(i + 1);
      }

    }

    return paramStringBuffer.toString();
  }

  public CmdFile() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("BREAK", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("CURENV", null);
    this._properties.put("NAME", "file");
    this._levelAccess = 1;
  }
}