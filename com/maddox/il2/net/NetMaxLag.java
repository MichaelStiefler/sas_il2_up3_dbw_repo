package com.maddox.il2.net;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Time;
import java.util.List;

public class NetMaxLag
{
  private static final double far = 2000.0D;
  private static final double near = 100.0D;
  private static final double far2 = 4000000.0D;
  private static final double near2 = 10000.0D;
  private int warningCounter = 0;
  private double lastWarningTime = -1.0D;

  private void checkLag(Aircraft paramAircraft, boolean paramBoolean) {
    double d1 = Time.real() / 1000.0D;
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    double d2 = localNetServerParams.cheaterWarningDelay();
    if (!paramBoolean)
      d2 += 3.0D;
    if ((this.lastWarningTime > 0.0D) && 
      (d1 - this.lastWarningTime < d2)) {
      return;
    }
    int i = paramAircraft.getArmy();
    Point3d localPoint3d = paramAircraft.pos.getAbsPoint();
    long l1 = -1L;
    List localList = Engine.targets();
    int j = localList.size();
    Object localObject;
    for (int k = 0; k < j; k++) {
      localObject = (Actor)localList.get(k);
      if ((localObject == paramAircraft) || 
        (!(localObject instanceof Aircraft)) || 
        (!Actor.isAlive((Actor)localObject)) || 
        (((Actor)localObject).getArmy() == i) || 
        (!((Aircraft)localObject).isNetPlayer())) continue;
      double d3 = localPoint3d.distanceSquared(((Actor)localObject).pos.getAbsPoint());
      long l2;
      if (d3 > 4000000.0D) {
        if (paramBoolean) l2 = paramAircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel().getMaxTimeout() / 2; else
          l2 = localNetServerParams.masterChannel().getMaxTimeout() / 2;
      } else {
        double d4 = localNetServerParams.nearMaxLagTime();
        if (d3 > 10000.0D) {
          double d5 = Math.sqrt(d3);
          d4 += (d5 - 100.0D) / 1900.0D * (localNetServerParams.farMaxLagTime() - localNetServerParams.nearMaxLagTime());
        }
        l2 = ()(d4 * 1000.0D);
      }
      if ((l1 < 0L) || (l1 > l2))
        l1 = l2;
    }
    if (l1 < 0L) {
      return;
    }
    if (paramBoolean) localObject = paramAircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(); else
      localObject = localNetServerParams.masterChannel();
    if (localObject == null)
      return;
    if (l1 > ((NetChannel)localObject).getCurTimeoutOk()) {
      return;
    }
    this.lastWarningTime = d1;
    this.warningCounter += 1;
    int m = 0;
    if (localNetServerParams.cheaterWarningNum() >= 0) {
      m = this.warningCounter > localNetServerParams.cheaterWarningNum() ? 1 : 0;
    }
    if (m != 0) {
      if (paramBoolean) {
        Chat.sendLog(0, "user_cheatkick1", paramAircraft, null);
        ((NetUser)NetEnv.host()).kick(paramAircraft.netUser());
      } else {
        Chat.sendLog(0, "user_cheatkick2", (NetUser)NetEnv.host(), null);
        ((NetChannel)localObject).destroy("You have been kicked from the server .");
      }
    } else if (paramBoolean) {
      String str = "user_cheating" + (this.warningCounter % 3 + 1);
      Chat.sendLog(0, str, paramAircraft, null);
    }
  }

  public void doServerCheck(Aircraft paramAircraft) {
    checkLag(paramAircraft, true);
  }

  public void doClientCheck() {
    if (!Actor.isAlive(World.getPlayerAircraft())) return;
    checkLag(World.getPlayerAircraft(), false);
  }
}