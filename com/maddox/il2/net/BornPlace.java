package com.maddox.il2.net;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import java.util.ArrayList;

public class BornPlace
{
  public Point2d place = new Point2d();
  public float r;
  public int army;
  public boolean bParachute = true;
  public ArrayList airNames;
  private static Vector3d v1 = new Vector3d();
  private static Point3d corn = new Point3d();
  private static Point3d corn1 = new Point3d();
  private static Point3d pship = new Point3d();

  static ClipFilter clipFilter = new ClipFilter();
  public int tmpForBrief;

  public Loc getAircraftPlace(Aircraft paramAircraft, int paramInt)
  {
    Loc localLoc = null;
    if ((paramInt < 0) || (paramInt > World.cur().airdrome.stay.length)) {
      double d1 = World.land().HQ(this.place.jdField_x_of_type_Double, this.place.jdField_y_of_type_Double);
      localLoc = new Loc(this.place.jdField_x_of_type_Double, this.place.jdField_y_of_type_Double, d1, 0.0F, 0.0F, 0.0F);
    }
    else
    {
      Object localObject;
      AirportCarrier localAirportCarrier;
      if (paramInt >= World.cur().airdrome.stayHold.length) {
        localObject = World.cur().airdrome.stay[paramInt][0];
        localLoc = new Loc(((Point_Stay)localObject).jdField_x_of_type_Float, ((Point_Stay)localObject).jdField_y_of_type_Float, 0.0D, 0.0F, 0.0F, 0.0F);
        if (!World.cur().diffCur.Takeoff_N_Landing)
          return localLoc;
        localAirportCarrier = (AirportCarrier)Airport.nearest(localLoc.getPoint(), -1, 4);
        if (localAirportCarrier != null)
        {
          corn.set(localLoc.getPoint());
          corn1.set(localLoc.getPoint());
          corn.jdField_z_of_type_Double = Engine.cur.land.HQ(localLoc.getPoint().jdField_x_of_type_Double, localLoc.getPoint().jdField_y_of_type_Double);
          corn1.jdField_z_of_type_Double = (corn.jdField_z_of_type_Double + 40.0D);
          Actor localActor = Engine.collideEnv().getLine(corn, corn1, false, clipFilter, pship);
          if (localAirportCarrier.ship() != localActor) {
            localAirportCarrier = null;
          }
        }
        if (localAirportCarrier != null)
          localAirportCarrier.getTakeoff(paramAircraft, localLoc);
        else
          localLoc = new Loc(((Point_Stay)localObject).jdField_x_of_type_Float, ((Point_Stay)localObject).jdField_y_of_type_Float, 1000.0D, 0.0F, 0.0F, 0.0F);
      }
      else {
        localObject = World.cur().airdrome.stay;
        localAirportCarrier = localObject[paramInt][(localObject[paramInt].length - 1)];
        World.land(); double d2 = Landscape.HQ(localAirportCarrier.jdField_x_of_type_Float, localAirportCarrier.jdField_y_of_type_Float);
        localLoc = new Loc(localAirportCarrier.jdField_x_of_type_Float, localAirportCarrier.jdField_y_of_type_Float, d2, 0.0F, 0.0F, 0.0F);
        int i = localObject[paramInt].length - 2;
        if (i >= 0) {
          Point3d localPoint3d1 = new Point3d(localAirportCarrier.jdField_x_of_type_Float, localAirportCarrier.jdField_y_of_type_Float, 0.0D);
          Point3d localPoint3d2 = new Point3d(localObject[paramInt][i].jdField_x_of_type_Float, localObject[paramInt][i].jdField_y_of_type_Float, 0.0D);
          Vector3d localVector3d = new Vector3d();
          localVector3d.sub(localPoint3d2, localPoint3d1);
          localVector3d.normalize();
          Orient localOrient = new Orient();
          localOrient.setAT0(localVector3d);
          if (!World.cur().diffCur.Takeoff_N_Landing) {
            localLoc.set(localOrient);
            return localLoc;
          }
          localLoc.getPoint().jdField_z_of_type_Double += paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H;
          Engine.land().N(localLoc.getPoint().jdField_x_of_type_Double, localLoc.getPoint().jdField_y_of_type_Double, v1);
          localOrient.orient(v1);
          localOrient.increment(0.0F, paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch, 0.0F);
          localLoc.set(localOrient);
        }
      }
    }
    return (Loc)localLoc;
  }

  public BornPlace(double paramDouble1, double paramDouble2, int paramInt, float paramFloat)
  {
    this.place.set(paramDouble1, paramDouble2);
    this.army = paramInt;
    this.r = paramFloat;
  }

  static class ClipFilter
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      return paramActor instanceof BigshipGeneric;
    }
  }
}