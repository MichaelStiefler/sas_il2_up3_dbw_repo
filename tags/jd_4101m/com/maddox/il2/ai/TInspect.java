package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiNetSendMethods;
import com.maddox.il2.game.ZutiRadarObject;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.List;

public class TInspect extends Target
{
  String nameTarget;
  Actor actor;
  boolean bLanding;
  public Point3d point;
  double r;
  private static Point3d p = new Point3d();
  private static Vector3d v0 = new Vector3d();
  private static Vector3d v1 = new Vector3d();

  boolean checkActor()
  {
    if (Actor.isAlive(this.actor)) {
      this.actor.pos.getAbs(this.point);
      return true;
    }
    if (this.actor != null)
      return false;
    this.actor = Actor.getByName(this.nameTarget);
    if (Actor.isAlive(this.actor)) {
      this.actor.pos.getAbs(this.point);
      return true;
    }
    return false;
  }

  protected boolean checkPeriodic()
  {
    if ((Mission.isCoop()) || (Mission.isDogfight())) {
      localObject = Engine.targets();
      int i = ((List)localObject).size();
      int j = World.getMissionArmy();
      for (int k = 0; k < i; k++) {
        Actor localActor = (Actor)((List)localObject).get(k);
        if (((localActor instanceof Aircraft)) && (Actor.isAlive(localActor)) && (localActor.getArmy() == j) && 
          (checkPeriodic(localActor))) {
          return true;
        }
      }
      return false;
    }
    Object localObject = World.getPlayerAircraft();
    if (!Actor.isValid((Actor)localObject)) return false;
    return checkPeriodic((Actor)localObject);
  }

  protected boolean checkPeriodic(Actor paramActor) {
    checkActor();
    paramActor.pos.getAbs(p);
    double d = p.z;
    p.z = this.point.z;
    if (p.distance(this.point) <= this.r)
    {
      boolean bool = Main.cur().mission.zutiRadar_ScoutCompleteRecon;
      if ((bool) && (!ZutiRadarObject.isPlayerArmyScout(paramActor, World.getMissionArmy()))) {
        return false;
      }

      Aircraft localAircraft = (Aircraft)paramActor;
      if (localAircraft.FM.isWasAirborne()) {
        if (this.bLanding) {
          if (Actor.isAlive(this.actor)) {
            paramActor.getSpeed(v0);
            this.actor.getSpeed(v1);
            v0.sub(v1);
            if (v0.length() > 5.0D)
              return false;
          }
          else if ((d - World.land().HQ(p.x, p.y) > 10.0D) || (paramActor.getSpeed(null) > 5.0D))
          {
            return false;
          }
        }
        setTaskCompleteFlag(true);
        setDiedFlag(true);

        zutiTargetDied();

        return true;
      }
    }
    return false;
  }

  protected boolean checkActorDied(Actor paramActor)
  {
    if ((Mission.isCoop()) || (Mission.isDogfight())) {
      if (!(paramActor instanceof Aircraft)) return false;
      int i = World.getMissionArmy();
      if (paramActor.getArmy() != i) return false;
      List localList = Engine.targets();
      int j = localList.size();
      for (int k = 0; k < j; k++) {
        paramActor = (Actor)localList.get(k);
        if (((paramActor instanceof Aircraft)) && (Actor.isAlive(paramActor)) && (paramActor.getArmy() == i)) {
          return false;
        }
      }
    }
    else if (paramActor != World.getPlayerAircraft()) {
      return false;
    }
    setDiedFlag(true);

    zutiTargetDied();

    return true;
  }
  protected boolean checkTimeoutOff() {
    setDiedFlag(true);
    return true;
  }

  public TInspect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, String paramString) {
    super(paramInt1, paramInt2);
    this.bLanding = paramBoolean;
    this.nameTarget = paramString;
    World.land(); this.point = new Point3d(paramInt3, paramInt4, Landscape.HQ(paramInt3, paramInt4));
    this.r = paramInt5;
  }

  private void zutiTargetDied()
  {
    if (ZutiSupportMethods.ZUTI_DEAD_TARGETS == null) {
      ZutiSupportMethods.ZUTI_DEAD_TARGETS = new ArrayList();
    }
    if (!ZutiSupportMethods.ZUTI_DEAD_TARGETS.contains(this)) {
      ZutiSupportMethods.ZUTI_DEAD_TARGETS.add(this);
    }
    zutiSendRemoveMessageToClients();
  }

  private void zutiSendRemoveMessageToClients()
  {
    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()))
    {
      if (this.actor == null)
        ZutiSupportMethods.removeTarget(this.point.x, this.point.y);
      else {
        ZutiSupportMethods.removeTarget(this.actor.name());
      }
      List localList = NetEnv.hosts();
      int i = localList.size();

      for (int j = 0; j < i; j++)
      {
        NetUser localNetUser = (NetUser)localList.get(j);
        if (this.actor != null)
          ZutiNetSendMethods.removeTInspectTarget(localNetUser, this.point, this.actor.name());
        else
          ZutiNetSendMethods.removeTInspectTarget(localNetUser, this.point, "null");
      }
    }
  }
}