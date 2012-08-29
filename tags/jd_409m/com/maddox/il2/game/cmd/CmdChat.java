package com.maddox.il2.game.cmd;

import com.maddox.il2.ai.Army;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdChat extends Cmd
{
  public static final String ALL = "ALL";
  public static final String MY_ARMY = "MY_ARMY";
  public static final String ARMY = "ARMY";
  public static final String TO = "TO";
  public static final String TON = "TO#";
  public static final String BUFSIZE = "BUFSIZE";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null)
      return null;
    if (Main.cur().chat == null)
      return null;
    if (Cmd.nargs(paramMap, "_$$") == 0) {
      if (paramMap.containsKey("BUFSIZE")) {
        if (Cmd.nargs(paramMap, "BUFSIZE") == 0) {
          INFO_HARD("Chat Buffer Size = " + Main.cur().chat.getMaxBuflen());
        } else {
          int i = Cmd.arg(paramMap, "BUFSIZE", 0, Main.cur().chat.getMaxBuflen(), 1, 1000);
          Main.cur().chat.setMaxBufLen(i);
        }
        return CmdEnv.RETURN_OK;
      }
      return null;
    }

    ArrayList localArrayList = new ArrayList();
    fillUsers(paramMap, localArrayList);
    if (localArrayList.size() == 0) return null;

    String str = Cmd.args(paramMap, "_$$");
    if (localArrayList.size() == NetEnv.hosts().size())
      Main.cur().chat.send(NetEnv.host(), str, null);
    else {
      Main.cur().chat.send(NetEnv.host(), str, localArrayList);
    }
    return CmdEnv.RETURN_OK;
  }

  private void fillUsers(Map paramMap, List paramList) {
    if (paramMap.containsKey("ALL")) {
      for (int i = 0; i < NetEnv.hosts().size(); i++)
        paramList.add(NetEnv.hosts().get(i));
      return;
    }
    int m;
    if (paramMap.containsKey("MY_ARMY")) {
      NetUser localNetUser1 = (NetUser)NetEnv.host();
      int k = localNetUser1.getArmy();
      for (m = 0; m < NetEnv.hosts().size(); m++) {
        localNetUser1 = (NetUser)NetEnv.hosts().get(m);
        if (localNetUser1.getArmy() == k)
          paramList.add(localNetUser1);
      }
      return;
    }

    if ((!paramMap.containsKey("TO")) && (!paramMap.containsKey("TO#")) && (!paramMap.containsKey("ARMY")))
    {
      for (int j = 0; j < NetEnv.hosts().size(); j++)
        paramList.add(NetEnv.hosts().get(j));
      return;
    }

    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    int n;
    String str1;
    int i2;
    NetUser localNetUser2;
    Object localObject;
    if (paramMap.containsKey("TO")) {
      m = Cmd.nargs(paramMap, "TO");
      if ((m == 1) && ("*".equals(Cmd.arg(paramMap, "TO", 0)))) {
        for (n = 0; n < NetEnv.hosts().size(); n++)
          paramList.add(NetEnv.hosts().get(n));
        return;
      }
      for (n = 0; n < m; n++) {
        str1 = Cmd.arg(paramMap, "TO", n);
        for (i2 = 0; i2 < NetEnv.hosts().size(); i2++) {
          localNetUser2 = (NetUser)NetEnv.hosts().get(i2);
          localObject = localNetUser2.uniqueName();
          if (str1.equals(localObject)) {
            if (localHashMap.containsKey(localNetUser2)) break;
            localHashMap.put(localNetUser2, null);
            localArrayList.add(localNetUser2); break;
          }
        }

      }

    }

    if (paramMap.containsKey("TO#")) {
      m = Cmd.nargs(paramMap, "TO#");
      for (n = 0; n < m; n++) {
        str1 = Cmd.arg(paramMap, "TO#", n);
        if ((str1.charAt(0) >= '0') && (str1.charAt(0) <= '9')) {
          i2 = Cmd.arg(paramMap, "TO#", n, 1000, 0, 1000);
          if ((i2 > 0) && (i2 <= NetEnv.hosts().size())) {
            localNetUser2 = (NetUser)NetEnv.hosts().get(i2 - 1);
            if (!localHashMap.containsKey(localNetUser2)) {
              localHashMap.put(localNetUser2, null);
              localArrayList.add(localNetUser2);
            }
          }
        }
      }
    }

    if (paramMap.containsKey("ARMY")) {
      m = Cmd.nargs(paramMap, "ARMY");
      for (n = 0; n < m; n++) {
        int i1 = -1;
        String str2 = Cmd.arg(paramMap, "ARMY", n);
        if ((str2.charAt(0) >= '0') && (str2.charAt(0) <= '9')) {
          i1 = Cmd.arg(paramMap, "ARMY", n, 1000, 0, 1000);
          if (i1 >= Army.amountNet())
            continue;
        } else {
          for (i1 = 0; i1 < Army.amountNet(); i1++) {
            if (Army.name(i1).equals(str2))
              break;
          }
          if (i1 == Army.amountNet())
            continue;
        }
        for (int i3 = 0; i3 < NetEnv.hosts().size(); i3++) {
          localObject = (NetUser)NetEnv.hosts().get(i3);
          if ((((NetUser)localObject).getArmy() != i1) || 
            (localHashMap.containsKey(localObject))) continue;
          localHashMap.put(localObject, null);
          localArrayList.add(localObject);
        }
      }

    }

    paramList.addAll(localArrayList);
  }

  public CmdChat() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("ALL", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("MY_ARMY", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("ARMY", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TO", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TO#", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("BUFSIZE", null);
    this._properties.put("NAME", "chat");
    this._levelAccess = 1;
  }
}