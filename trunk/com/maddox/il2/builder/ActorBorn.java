package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import java.util.ArrayList;

public class ActorBorn extends Actor
  implements ActorAlign
{
  public int r = 3000;
  public ArrayList airNames = new ArrayList();
  public boolean bParachute = true;

  public boolean zutiStaticPositionOnly = false;
  public int zutiSpawnHeight = 1000;
  public int zutiSpawnSpeed = 200;
  public int zutiSpawnOrient = 0;

  public int zutiRadarHeight_MIN = 0;
  public int zutiRadarHeight_MAX = 5000;
  public int zutiRadarRange = 50;
  public boolean zutiWasPrerenderedMIN = false;
  public boolean zutiWasPrerenderedMAX = false;
  public boolean zutiWasPrerenderedRange = false;
  public int zutiMaxBasePilots = 0;
  public boolean zutiAirspawnOnly = false;
  public boolean zutiAirspawnIfCarrierFull = false;
  public Zuti_WAircraftLoadout zutiAcLoadouts = null;

  public ArrayList zutiHomeBaseCountries = null;

  public boolean zutiEnablePlaneLimits = false;
  public boolean zutiDecreasingNumberOfPlanes = false;
  public boolean zutiIncludeStaticPlanes = false;
  public boolean zutiDisableSpawning = false;
  public boolean zutiEnableFriction = false;
  public double zutiFriction = 3.8D;

  public void align() {
    alignPosToLand(0.0D, true);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public ActorBorn(Point3d paramPoint3d) {
    this.flags |= 8192;
    this.pos = new ActorPosMove(this);
    this.pos.setAbs(paramPoint3d);
    align();
    drawing(true);
    this.icon = IconDraw.get("icons/born.mat");
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }
}