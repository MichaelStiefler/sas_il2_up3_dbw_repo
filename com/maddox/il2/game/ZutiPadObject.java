package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.vehicles.artillery.RocketryRocket;
import com.maddox.rts.Property;

public class ZutiPadObject
{
  private Actor actor;
  private Point3d livePos = null;
  private Point3d oldPos = null;
  private Point3d deadPos = new Point3d(-99999.999989999997D, -99999.999989999997D, 0.0D);
  private float azimut = 0.0F;
  private boolean isPlayerPlane = false;
  private boolean isPlayerArmyScout;
  private boolean visibleForPlayerArmy = false;

  private boolean refreshIntervalSet = false;

  private String name = null;

  public int type = -1;

  public ZutiPadObject(Actor paramActor, boolean paramBoolean)
  {
    try
    {
      if (paramActor == null) {
        return;
      }
      if (World.getPlayerAircraft() == paramActor) {
        this.isPlayerPlane = true;
      }
      this.refreshIntervalSet = paramBoolean;
      this.actor = paramActor;
      this.livePos = this.actor.pos.getAbsPoint();
      this.oldPos = new Point3d(this.livePos.x, this.livePos.y, this.livePos.z);
      this.azimut = this.actor.pos.getAbsOrient().azimut();
      this.name = this.actor.name();
      this.isPlayerArmyScout = ZutiRadarObject.isPlayerArmyScout(paramActor, ZutiSupportMethods.getPlayerArmy());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void refreshPosition()
  {
    if ((this.actor == null) || (this.livePos == null)) {
      return;
    }
    this.oldPos = new Point3d(this.livePos.x, this.livePos.y, this.livePos.z);
    this.azimut = this.actor.pos.getAbsOrient().azimut();
  }

  public String getName()
  {
    return this.name;
  }

  public Point3d getPosition()
  {
    if (!isAlive())
      return this.deadPos;
    if (this.refreshIntervalSet) {
      return this.oldPos;
    }
    return this.livePos;
  }

  public float getAzimut()
  {
    return this.azimut;
  }

  public boolean isAlive() {
    if (this.actor == null) {
      return false;
    }
    if ((this.actor instanceof RocketryRocket)) {
      return !((RocketryRocket)this.actor).isDamaged();
    }
    return !this.actor.getDiedFlag();
  }

  public Actor getOwner() {
    return this.actor;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ZutiPadObject)) {
      return false;
    }
    ZutiPadObject localZutiPadObject = (ZutiPadObject)paramObject;

    return this.actor.equals(localZutiPadObject.getOwner());
  }

  public int hashCode()
  {
    return this.actor.hashCode();
  }

  public int getArmy()
  {
    if (isAlive()) {
      return this.actor.getArmy();
    }
    return -1;
  }

  public boolean isGroundUnit()
  {
    if (this.actor == null) {
      return false;
    }
    return isGroundUnit(this.actor);
  }

  public Mat getIcon()
  {
    if (this.actor.icon != null) {
      return this.actor.icon;
    }
    Class localClass = this.actor.getClass();

    Mat localMat = (Mat)Property.value(localClass, "iconMat", null);

    if (localMat == null)
    {
      String str = Property.stringValue(localClass, "iconName", null);

      if (str != null)
        try {
          localMat = Mat.New(str); } catch (Exception localException) {
          localException.printStackTrace();
        }
    }
    return localMat;
  }

  public boolean isPlayerPlane()
  {
    return this.isPlayerPlane;
  }

  public static boolean isGroundUnit(Actor paramActor)
  {
    return (!(paramActor instanceof SndAircraft)) && (!(paramActor instanceof RocketryRocket));
  }

  public boolean isVisibleForPlayerArmy()
  {
    if (this.isPlayerArmyScout) {
      return true;
    }
    return this.visibleForPlayerArmy;
  }

  public void setVisibleForPlayerArmy(boolean paramBoolean)
  {
    this.visibleForPlayerArmy = paramBoolean;
  }

  public boolean isPlayerArmyScout()
  {
    return this.isPlayerArmyScout;
  }
}