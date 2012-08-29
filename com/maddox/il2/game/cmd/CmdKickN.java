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

public class CmdKickN extends Cmd
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
      int i = Cmd.nargs(paramMap, "_$$");
      for (int j = 0; j < i; j++) {
        String str = Cmd.arg(paramMap, "_$$", j);
        if ((str.charAt(0) >= '0') && (str.charAt(0) <= '9')) {
          int k = Cmd.arg(paramMap, "_$$", j, 1000, 0, 1000);
          if ((k > 0) && (k <= NetEnv.hosts().size())) {
            NetUser localNetUser = (NetUser)NetEnv.hosts().get(k - 1);
            if (!localHashMap.containsKey(localNetUser)) {
              localHashMap.put(localNetUser, null);
              localArrayList.add(localNetUser);
            }
          }
        }
      }
    }
    paramList.addAll(localArrayList);
  }

  public CmdKickN() {
    this._properties.put("NAME", "kick#");
    this._levelAccess = 1;
  }
}