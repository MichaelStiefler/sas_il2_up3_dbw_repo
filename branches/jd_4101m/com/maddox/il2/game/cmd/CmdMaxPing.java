package com.maddox.il2.game.cmd;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdMaxPing extends Cmd
  implements MsgTimeOutListener
{
  public static final String DELAY = "DELAY";
  public static final String NUM = "WARNINGS";
  private int maxping = 3000;
  private int delay = 10;
  private int num = 3;
  private MsgTimeOut msg;

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null) return null;
    if (Main.cur().netServerParams.isMirror()) return null;

    if (paramMap.containsKey("DELAY")) {
      this.delay = arg(paramMap, "DELAY", 0, 5, 0, 60);
    }
    if (paramMap.containsKey("WARNINGS")) {
      this.num = arg(paramMap, "WARNINGS", 0, 3, 0, 100);
    }
    if (paramMap.containsKey("_$$")) {
      this.maxping = arg(paramMap, "_$$", 0, 3000, 0, 30000);
    } else {
      INFO_HARD(" maxping  " + this.maxping + " ms");
      INFO_HARD(" delay    " + this.delay + " s");
      INFO_HARD(" warnings " + this.num);
    }

    checkTimeMsg();
    return CmdEnv.RETURN_OK;
  }

  public void msgTimeOut(Object paramObject)
  {
    if (this.delay <= 0) return;
    if (this.maxping <= 0) return;
    this.msg.post(this.delay);

    for (int i = 0; i < NetEnv.hosts().size(); i++) {
      NetUser localNetUser = (NetUser)NetEnv.hosts().get(i);
      if (Actor.isAlive(localNetUser.findAircraft())) {
        NetChannel localNetChannel = localNetUser.masterChannel();
        int j = localNetChannel.ping();
        if (j > this.maxping) {
          int k = 0;
          if (!Property.containsValue(localNetUser, "maxpingCounter"))
            Property.set(localNetUser, "maxpingCounter", k);
          else {
            k = Property.intValue(localNetUser, "maxpingCounter");
          }
          k++;
          if (k > this.num) {
            Chat.sendLog(0, "user_timeouts", localNetUser, null);
            ((NetUser)NetEnv.host()).kick(localNetUser);
          } else {
            Property.set(localNetUser, "maxpingCounter", k);
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(localNetUser);
            String str = "Your ping (" + j + ") is larger than allowed (" + this.maxping + ").";
            Main.cur().chat.send(null, str, localArrayList, 0, false);
          }
        }
      }
    }
  }

  private void checkTimeMsg() {
    if (this.msg == null) {
      this.msg = new MsgTimeOut();
      this.msg.setListener(this);
      this.msg.setNotCleanAfterSend();
      this.msg.setFlags(64);
    }
    if (this.delay <= 0) return;
    if (this.maxping <= 0) return;
    if (!this.msg.busy())
      this.msg.post(this.delay);
  }

  public CmdMaxPing()
  {
    this.param.put("DELAY", null);
    this.param.put("WARNINGS", null);
    this._properties.put("NAME", "maxping");
    this._levelAccess = 1;
  }
}