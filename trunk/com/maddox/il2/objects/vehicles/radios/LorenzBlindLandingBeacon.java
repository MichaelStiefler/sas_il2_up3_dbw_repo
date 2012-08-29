package com.maddox.il2.objects.vehicles.radios;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;

public class LorenzBlindLandingBeacon extends BeaconGeneric
{
  Point3d p1 = new Point3d();
  Point3d p2 = new Point3d();
  private Point3d tempPoint1 = new Point3d();
  private Point3d tempPoint2 = new Point3d();
  private Point3d emittingPoint = new Point3d();
  public float outerMarkerDist = 3000.0F;
  public float innerMarkerDist = 300.0F;

  public LorenzBlindLandingBeacon()
  {
    this.innerMarkerDist = constr_arg1.innerMarkerDist;
    this.outerMarkerDist = constr_arg1.outerMarkerDist;
  }

  public void rideBeam(Aircraft paramAircraft, BlindLandingData paramBlindLandingData)
  {
    paramAircraft.pos.getAbs(this.p2);
    this.pos.getAbs(this.p1);
    this.p1.sub(this.p2);

    double d1 = Math.sqrt(this.p1.x * this.p1.x + this.p1.y * this.p1.y);
    double d2 = paramAircraft.FM.getAltitude() - this.pos.getAbsPoint().z;
    float f1 = getConeOfSilence(d1, d2);
    if (d1 > 35000.0D)
    {
      paramBlindLandingData.addSignal(0.0F, 0.0F, 35000.0F, false, 0.0F, 0.0F, 0.0F);
      return;
    }
    float f2 = f1;
    if (Landscape.rayHitHQ(this.emittingPoint, paramAircraft.FM.Loc, this.tempPoint1))
    {
      Landscape.rayHitHQ(paramAircraft.FM.Loc, this.emittingPoint, this.tempPoint2);
      this.tempPoint1.sub(this.tempPoint2);
      double d3 = Math.sqrt(this.tempPoint1.x * this.tempPoint1.x + this.tempPoint1.y * this.tempPoint1.y);
      f2 = cvt((float)d3, 0.0F, 5000.0F, 1.0F, 0.1F);
    }
    f2 *= cvt((float)d1, 0.0F, 35000.0F, 1.0F, 0.0F);

    float f3 = 57.324841F * (float)Math.atan2(this.p1.x, this.p1.y);
    f3 = this.pos.getAbsOrient().getYaw() + f3;
    for (f3 = (f3 + 180.0F) % 360.0F; f3 < 0.0F; f3 += 360.0F);
    while (f3 >= 360.0F) f3 -= 360.0F;

    float f4 = f3 - 90.0F;
    boolean bool = true;
    if (f4 > 90.0F)
    {
      f4 = -(f4 - 180.0F);
      bool = false;
    }

    float f5 = 0.0F;
    if (!bool)
      f5 = f4 * -18.0F;
    else {
      f5 = f4 * 18.0F;
    }
    paramBlindLandingData.addSignal(f4, f5 * f1, (float)d1, bool, f2, this.outerMarkerDist, this.innerMarkerDist);
  }

  private float getConeOfSilence(double paramDouble1, double paramDouble2)
  {
    float f = 57.324841F * (float)Math.atan2(paramDouble1, paramDouble2);
    return cvt(f, 20.0F, 40.0F, 0.0F, 1.0F);
  }

  public void missionStarting()
  {
    super.missionStarting();
    this.emittingPoint.x = this.pos.getAbsPoint().x;
    this.emittingPoint.y = this.pos.getAbsPoint().y;
    this.emittingPoint.z = (this.pos.getAbsPoint().z + 20.0D);
  }

  public void showGuideArrows()
  {
    hierMesh().chunkVisible("GuideArrows", true);
  }

  public void align()
  {
    super.align();
    this.emittingPoint.x = this.pos.getAbsPoint().x;
    this.emittingPoint.y = this.pos.getAbsPoint().y;
    this.emittingPoint.z = (this.pos.getAbsPoint().z + 20.0D);
  }
}