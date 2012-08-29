package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmdKick extends Cmd
{
  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    fillUsers(paramMap, localArrayList);

    for (int i = 0; i < localArrayList.size(); i++) {
      NetUser localNetUser = (NetUser)localArrayList.get(i);
      if (!localNetUser.isMaster()) {
        ((NetUser)NetEnv.host()).kick(localNetUser);
      }
    }
    return CmdEnv.RETURN_OK;
  }

  private void fillUsers(Map paramMap, List paramList) {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();

    if (paramMap.containsKey("_$$")) {
      int i = nargs(paramMap, "_$$");
      for (int j = 0; j < i; j++) {
        String str1 = arg(paramMap, "_$$", j);
        for (int k = 0; k < NetEnv.hosts().size(); k++) {
          NetUser localNetUser = (NetUser)NetEnv.hosts().get(k);
          String str2 = localNetUser.uniqueName();
          if (str1.equals(str2)) {
            if (localHashMap.containsKey(localNetUser)) break;
            localHashMap.put(localNetUser, null);
            localArrayList.add(localNetUser); break;
          }
        }
      }

    }

    paramList.addAll(localArrayList);
  }

  public CmdKick() {
    this._properties.put("NAME", "kick");
    this._levelAccess = 1;
  }
}