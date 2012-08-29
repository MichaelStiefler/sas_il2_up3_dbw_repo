package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;

public class RoadSegment
{
  private static Point3d P = new Point3d();
  public long waitTime;
  public LongBridge br;
  public BridgeSegment brSg;
  public Point3d start;
  public double begR;
  public double endR;
  public double dHeight;
  public double length2D;
  public double length2Dallprev;
  public Vector3f normal;
  public float yaw;
  public Vector2d dir2D;

  RoadSegment(RoadSegment paramRoadSegment)
  {
    this.waitTime = paramRoadSegment.waitTime;
    this.br = paramRoadSegment.br;
    this.brSg = paramRoadSegment.brSg;
    this.start = new Point3d(paramRoadSegment.start);
    this.begR = paramRoadSegment.begR;
    this.endR = paramRoadSegment.endR;
    this.dHeight = paramRoadSegment.dHeight;
    this.length2D = paramRoadSegment.length2D;
    this.length2Dallprev = paramRoadSegment.length2Dallprev;
    this.normal = new Vector3f(paramRoadSegment.normal);
    this.yaw = paramRoadSegment.yaw;
    this.dir2D = new Vector2d(paramRoadSegment.dir2D);
  }

  private static final long convertWTime(double paramDouble)
  {
    if (Math.abs(paramDouble) < 0.1D)
      return 0L;
    return ()(paramDouble * 60.0D * 1000.0D + (paramDouble > 0.0D ? 0.5D : -0.5D));
  }

  public RoadSegment(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt1, int paramInt2)
  {
    this.start = new Point3d();
    this.normal = new Vector3f();
    this.dir2D = new Vector2d();
    set(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramInt1, paramInt2);
  }

  public void setZ(double paramDouble)
  {
    this.start.jdField_z_of_type_Double = paramDouble;
  }

  public void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt1, int paramInt2)
  {
    this.waitTime = convertWTime(paramDouble5);
    this.start.set(paramDouble1, paramDouble2, paramDouble3);
    this.begR = paramDouble4;
    if (paramInt1 < 0) {
      this.br = null;
      this.brSg = null;
    } else {
      this.br = LongBridge.getByIdx(paramInt1);
      this.brSg = BridgeSegment.getByIdx(paramInt1, paramInt2);
    }
  }

  public boolean IsLandAligned() {
    return this.start.jdField_z_of_type_Double < 0.0D;
  }

  public boolean IsPassableBy(Actor paramActor) {
    return (this.brSg == null) || ((this.br.isUsableFor(paramActor)) && (!this.brSg.IsDamaged()));
  }

  public boolean IsDamaged() {
    return (this.brSg != null) && (this.brSg.IsDamaged());
  }

  public void ComputeDerivedData(RoadSegment paramRoadSegment1, RoadSegment paramRoadSegment2) throws Exception
  {
    if (paramRoadSegment1 == null) {
      this.length2Dallprev = 0.0D;
    }
    else {
      paramRoadSegment1.length2Dallprev += paramRoadSegment1.length2D;
    }
    if (paramRoadSegment2 == null) {
      this.endR = this.begR;
      this.dHeight = 0.0D;
      this.length2D = 0.0D;
      this.normal.set(0.0F, 0.0F, 1.0F);
      this.yaw = 0.0F;
      this.dir2D.set(1.0D, 0.0D);
    } else {
      this.endR = paramRoadSegment2.begR;
      P.sub(paramRoadSegment2.start, this.start);

      if (paramRoadSegment2.IsLandAligned())
        this.dHeight = (Engine.land().HQ(paramRoadSegment2.start.jdField_x_of_type_Double, paramRoadSegment2.start.jdField_y_of_type_Double) - this.start.jdField_z_of_type_Double);
      else {
        this.dHeight = (paramRoadSegment2.start.jdField_z_of_type_Double - this.start.jdField_z_of_type_Double);
      }
      this.length2D = Math.sqrt(P.jdField_x_of_type_Double * P.jdField_x_of_type_Double + P.jdField_y_of_type_Double * P.jdField_y_of_type_Double);
      if (this.length2D <= 0.0D) throw new Exception("RS: too close points");
      this.normal.set((float)(-P.jdField_x_of_type_Double * P.jdField_z_of_type_Double), (float)(-P.jdField_y_of_type_Double * P.jdField_z_of_type_Double), (float)(this.length2D * this.length2D));
      this.normal.normalize();
      this.yaw = (float)(Math.atan2(P.jdField_y_of_type_Double, P.jdField_x_of_type_Double) * 57.295779513082323D);
      this.dir2D.set(P.jdField_x_of_type_Double, P.jdField_y_of_type_Double);
      this.dir2D.normalize();

      if (this.br != null) {
        if ((paramRoadSegment1 != null) && (paramRoadSegment1.br == null)) {
          paramRoadSegment1.br = this.br;
          paramRoadSegment1.brSg = this.brSg;
        }
        if ((paramRoadSegment2.br == null) && ((paramRoadSegment1 == null) || (paramRoadSegment1.brSg != this.brSg)))
        {
          paramRoadSegment2.br = this.br;
          paramRoadSegment2.brSg = this.brSg;
        }
      }
    }
  }

  public Point3d getEndP()
  {
    return new Point3d(this.start.jdField_x_of_type_Double + this.dir2D.jdField_x_of_type_Double * this.length2D, this.start.jdField_y_of_type_Double + this.dir2D.jdField_y_of_type_Double * this.length2D, this.start.jdField_z_of_type_Double + this.dHeight);
  }

  double computePosAlong(Point3d paramPoint3d)
  {
    if (this.length2D == 0.0D)
      return 0.0D;
    double d = (paramPoint3d.jdField_x_of_type_Double - this.start.jdField_x_of_type_Double) * this.dir2D.jdField_x_of_type_Double + (paramPoint3d.jdField_y_of_type_Double - this.start.jdField_y_of_type_Double) * this.dir2D.jdField_y_of_type_Double;

    return d;
  }

  public double computePosAlong_Fit(Point3d paramPoint3d)
  {
    if (this.length2D == 0.0D) return 0.0D;
    double d = (paramPoint3d.jdField_x_of_type_Double - this.start.jdField_x_of_type_Double) * this.dir2D.jdField_x_of_type_Double + (paramPoint3d.jdField_y_of_type_Double - this.start.jdField_y_of_type_Double) * this.dir2D.jdField_y_of_type_Double;

    return d >= this.length2D ? this.length2D : d <= 0.0D ? 0.0D : d;
  }

  public double computePosSide(Point3d paramPoint3d)
  {
    if (this.length2D == 0.0D) return 0.0D;

    Vector2d localVector2d = new Vector2d(paramPoint3d.jdField_x_of_type_Double - this.start.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double - this.start.jdField_y_of_type_Double);
    double d1 = localVector2d.dot(this.dir2D);
    if (d1 <= 0.0D) d1 = 0.0D;
    else if (d1 >= this.length2D) d1 = this.length2D;
    double d2 = localVector2d.lengthSquared() - d1 * d1;

    if (d2 <= 0.0D) return 0.0D;
    d2 = Math.sqrt(d2);

    return this.dir2D.jdField_x_of_type_Double * localVector2d.jdField_y_of_type_Double - this.dir2D.jdField_y_of_type_Double * localVector2d.jdField_x_of_type_Double > 0.0D ? -d2 : d2;
  }

  public double computePosSide_Fit(Point3d paramPoint3d, float paramFloat)
  {
    if (this.length2D == 0.0D) return 0.0D;

    Vector2d localVector2d = new Vector2d(paramPoint3d.jdField_x_of_type_Double - this.start.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double - this.start.jdField_y_of_type_Double);
    double d1 = localVector2d.dot(this.dir2D);
    if (d1 <= 0.0D) d1 = 0.0D;
    else if (d1 >= this.length2D) d1 = this.length2D;
    double d2 = localVector2d.lengthSquared() - d1 * d1;

    if (d2 <= 0.0D) return 0.0D;
    d2 = Math.sqrt(d2);

    if (this.dir2D.jdField_x_of_type_Double * localVector2d.jdField_y_of_type_Double - this.dir2D.jdField_y_of_type_Double * localVector2d.jdField_x_of_type_Double > 0.0D) d2 = -d2;

    double d3 = d1 / this.length2D;
    double d4 = this.begR * (1.0D - d3) + this.endR * d3;
    if (paramFloat > 0.0F) {
      d4 -= paramFloat;
      if (d4 <= 0.0D) d4 = 0.0D;
    }

    if (d2 <= -d4) d2 = -d4;
    else if (d2 >= d4) d2 = d4;
    return d2;
  }

  public Point3d computePos_Fit(double paramDouble1, double paramDouble2, float paramFloat)
  {
    if (this.length2D == 0.0D) return new Point3d(this.start);
    if (paramDouble1 <= 0.0D) paramDouble1 = 0.0D;
    else if (paramDouble1 >= this.length2D) paramDouble1 = this.length2D;
    double d1 = paramDouble1 / this.length2D;
    double d2 = this.begR * (1.0D - d1) + this.endR * d1;
    if (paramFloat > 0.0F) {
      d2 -= paramFloat;
      if (d2 <= 0.0D) d2 = 0.0D;
    }
    if (paramDouble2 <= -d2) paramDouble2 = -d2;
    else if (paramDouble2 >= d2) paramDouble2 = d2;
    Point3d localPoint3d = new Point3d(this.start);

    localPoint3d.jdField_x_of_type_Double += this.dir2D.jdField_x_of_type_Double * paramDouble1 + this.dir2D.jdField_y_of_type_Double * paramDouble2;
    localPoint3d.jdField_y_of_type_Double += this.dir2D.jdField_y_of_type_Double * paramDouble1 - this.dir2D.jdField_x_of_type_Double * paramDouble2;

    if (localPoint3d.jdField_z_of_type_Double < 0.0D)
      localPoint3d.jdField_z_of_type_Double = Engine.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double);
    else
      localPoint3d.jdField_z_of_type_Double += d1 * this.dHeight;
    return localPoint3d;
  }

  public Point3d computePos_FitBegCirc(double paramDouble1, double paramDouble2, float paramFloat)
  {
    if (this.length2D == 0.0D) return new Point3d(this.start);
    double d2;
    double d1;
    if (paramDouble1 <= 0.0D) {
      d2 = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
      double d3;
      if (paramFloat > 0.0F) {
        d3 = this.begR - paramFloat;
        if (d3 <= 0.0D) d3 = 0.0D; 
      }
      else {
        d3 = this.begR;
      }
      if (d2 > d3) {
        double d4 = d3 / d2;
        paramDouble1 *= d4;
        paramDouble2 *= d4;
      }
      d1 = paramDouble1 / this.length2D;
    } else {
      if (paramDouble1 >= this.length2D) paramDouble1 = this.length2D;
      d1 = paramDouble1 / this.length2D;
      d2 = this.begR * (1.0D - d1) + this.endR * d1;
      if (paramFloat > 0.0F) {
        d2 -= paramFloat;
        if (d2 <= 0.0D) d2 = 0.0D;
      }
      if (paramDouble2 <= -d2) paramDouble2 = -d2;
      else if (paramDouble2 >= d2) paramDouble2 = d2;
    }
    Point3d localPoint3d = new Point3d(this.start);

    localPoint3d.jdField_x_of_type_Double += this.dir2D.jdField_x_of_type_Double * paramDouble1 + this.dir2D.jdField_y_of_type_Double * paramDouble2;
    localPoint3d.jdField_y_of_type_Double += this.dir2D.jdField_y_of_type_Double * paramDouble1 - this.dir2D.jdField_x_of_type_Double * paramDouble2;

    if (localPoint3d.jdField_z_of_type_Double < 0.0D)
      localPoint3d.jdField_z_of_type_Double = Engine.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double);
    else
      localPoint3d.jdField_z_of_type_Double += d1 * this.dHeight;
    return localPoint3d;
  }
}