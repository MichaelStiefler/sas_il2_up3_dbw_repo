package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Point_Runaway;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MsgDreamListener;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeAmphibiousPlane;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.rts.Message;
import com.maddox.rts.Time;
import java.util.ArrayList;
import java.util.List;

public abstract class Airport extends Actor
  implements MsgDreamListener
{
  public static final int TYPE_ANY = 7;
  public static final int TYPE_GROUND = 1;
  public static final int TYPE_MARITIME = 2;
  public static final int TYPE_CARRIER = 4;
  private static Point3f PlLoc = new Point3f();
  public int takeoffRequest = 0;
  public int landingRequest = 0;

  private static Point3d pd = new Point3d();

  public static Airport nearest(Point3f paramPoint3f, int paramInt1, int paramInt2)
  {
    pd.set(paramPoint3f.x, paramPoint3f.y, paramPoint3f.z);
    return nearest(pd, paramInt1, paramInt2);
  }

  public static Airport nearest(Point3d paramPoint3d, int paramInt1, int paramInt2)
  {
    Object localObject = null;
    double d1 = 0.0D;
    pd.set(paramPoint3d.x, paramPoint3d.y, paramPoint3d.z);
    int i = World.cur().airports.size();
    for (int j = 0; j < i; j++) {
      Airport localAirport = (Airport)World.cur().airports.get(j);

      if ((((paramInt2 & 0x1) == 0) || (!(localAirport instanceof AirportGround))) && 
        (((paramInt2 & 0x2) == 0) || (!(localAirport instanceof AirportMaritime))) && (
        ((paramInt2 & 0x4) == 0) || (!(localAirport instanceof AirportCarrier))))
      {
        continue;
      }

      if (!Actor.isAlive(localAirport))
        continue;
      if (paramInt1 >= 0) {
        int k = localAirport.getArmy();
        if ((k != 0) && (k != paramInt1)) {
          continue;
        }
      }
      pd.z = localAirport.pos.getAbsPoint().z;
      double d2 = pd.distanceSquared(localAirport.pos.getAbsPoint());
      if ((localObject == null) || (d2 < d1)) {
        localObject = localAirport;
        d1 = d2;
      }
    }
    if (d1 > 225000000.0D)
      localObject = null;
    return localObject;
  }

  public Airport()
  {
    this.flags |= 512;
    World.cur().airports.add(this);
  }

  public static double distToNearestAirport(Point3d paramPoint3d)
  {
    return distToNearestAirport(paramPoint3d, -1, 7);
  }

  public static double distToNearestAirport(Point3d paramPoint3d, int paramInt1, int paramInt2) {
    Airport localAirport = nearest(paramPoint3d, paramInt1, paramInt2);
    if (localAirport == null) return 225000000.0D;
    return localAirport.pos.getAbsPoint().distance(paramPoint3d);
  }

  public static Airport makeLandWay(FlightModel paramFlightModel)
  {
    paramFlightModel.AP.way.curr().getP(PlLoc);
    int i = 0;
    Airport localAirport = null;
    int j = paramFlightModel.actor.getArmy();

    if ((paramFlightModel.actor instanceof TypeSailPlane)) {
      i = 2;
      localAirport = nearest(PlLoc, j, i);
    }
    else if (paramFlightModel.AP.way.isLandingOnShip()) {
      i = 4;
      localAirport = nearest(PlLoc, j, i);
      if (!Actor.isAlive(localAirport)) {
        i = 1;
        localAirport = nearest(PlLoc, j, i);
      }
    }
    else {
      i = 3;
      if (!(paramFlightModel.actor instanceof TypeAmphibiousPlane)) i &= -3;
      localAirport = nearest(PlLoc, j, i);
      if (!Actor.isAlive(localAirport)) {
        i = 4;
        localAirport = nearest(PlLoc, j, i);
      }
    }

    Aircraft.debugprintln(paramFlightModel.actor, "Searching a place to land - Selecting RWY Type " + i);

    if (Actor.isAlive(localAirport)) {
      if (localAirport.landWay(paramFlightModel)) {
        paramFlightModel.AP.way.landingAirport = localAirport;
        return localAirport;
      }
      return null;
    }
    return null;
  }

  public boolean landWay(FlightModel paramFlightModel)
  {
    return false;
  }

  public void rebuildLandWay(FlightModel paramFlightModel) {
  }

  public void rebuildLastPoint(FlightModel paramFlightModel) {
  }

  public double ShiftFromLine(FlightModel paramFlightModel) {
    return 0.0D;
  }

  public int landingFeedback(Point3d paramPoint3d, Aircraft paramAircraft) {
    if (paramAircraft.FM.CT.GearControl > 0.0F) return 0;
    if (this.landingRequest > 0) return 1;
    double d1 = 640000.0D;
    List localList = Engine.targets(); int i = localList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localList.get(j);
      if (((localActor instanceof Aircraft)) && (localActor != paramAircraft)) {
        Aircraft localAircraft = (Aircraft)localActor;
        Point3d localPoint3d = localAircraft.pos.getAbsPoint();
        double d2 = (paramPoint3d.x - localPoint3d.x) * (paramPoint3d.x - localPoint3d.x) + (paramPoint3d.y - localPoint3d.y) * (paramPoint3d.y - localPoint3d.y) + (paramPoint3d.z - localPoint3d.z) * (paramPoint3d.z - localPoint3d.z);

        if (d2 < d1) {
          if (((Maneuver)localAircraft.FM).get_maneuver() == 25) {
            if ((((Maneuver)localAircraft.FM).wayCurPos instanceof Point_Runaway)) return 2;
            if ((localAircraft.FM.AP.way.isLanding()) && (localAircraft.FM.AP.way.Cur() > 5)) return 1;
          }
          if ((((Maneuver)localAircraft.FM).get_maneuver() == 26) || (((Maneuver)localAircraft.FM).get_maneuver() == 64))
          {
            return 2;
          }
        }
      }
    }
    this.landingRequest = 2000;
    return 0; } 
  public abstract boolean nearestRunway(Point3d paramPoint3d, Loc paramLoc);

  public abstract void setTakeoff(Point3d paramPoint3d, Aircraft[] paramArrayOfAircraft);

  public Object getSwitchListener(Message paramMessage) { return this; }

  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  protected void update()
  {
    if (this.takeoffRequest > 0) this.takeoffRequest -= 1;
    if (this.landingRequest > 0) this.landingRequest -= 1;
  }

  public void msgDream(boolean paramBoolean)
  {
    if (paramBoolean) {
      if (interpGet("AirportTicker") == null)
        interpPut(new Interpolater(), "AirportTicker", Time.current(), null);
    }
    else interpEnd("AirportTicker");
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      Airport.this.update();
      return true;
    }
  }
}