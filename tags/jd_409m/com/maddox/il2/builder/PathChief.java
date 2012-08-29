package com.maddox.il2.builder;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class PathChief extends Path
{
  public ActorMesh[] units;
  public int _iType;
  public int _iItem;
  public int _sleep = 0;
  public int _skill = 2;
  public float _slowfire = 1.0F;

  private Point3d prevStart = new Point3d();
  private Point3d prevEnd = new Point3d();

  private Point3d p2 = new Point3d();

  public void computeTimes()
  {
    int i = points();
    if (i == 0) return;
    Object localObject = (PNodes)point(0);
    ((PNodes)localObject).jdField_time_of_type_Double = 0.0D;
    if (PlMisChief.moveType(this._iType) != 2) {
      double d1 = PlMisChief.speed(this._iType, this._iItem);
      for (int k = 1; k < i; k++) {
        double d3 = ((PNodes)localObject).len();
        double d4 = d3 / d1;
        PNodes localPNodes2 = (PNodes)point(k);
        localPNodes2.jdField_time_of_type_Double = (((PNodes)localObject).jdField_time_of_type_Double + ((PNodes)localObject).timeoutMin * 60.0D + d4);
        localObject = localPNodes2;
      }
    } else {
      for (int j = 1; j < i; j++) {
        PNodes localPNodes1 = (PNodes)point(j);
        double d2 = ((PNodes)localObject).computeTime(localPNodes1);
        localPNodes1.jdField_time_of_type_Double = (((PNodes)localObject).jdField_time_of_type_Double + ((PNodes)localObject).timeoutMin * 60.0D + d2);
        localObject = localPNodes1;
      }
    }
    Plugin.builder.doUpdateSelector();
  }
  public void computeTimesLoaded() {
    int i = points();
    if (i == 0) return;
    Object localObject = (PNodes)point(0);
    ((PNodes)localObject).jdField_time_of_type_Double = 0.0D;
    if (PlMisChief.moveType(this._iType) != 2) {
      double d1 = PlMisChief.speed(this._iType, this._iItem);
      for (int k = 1; k < i; k++) {
        double d3 = ((PNodes)localObject).len();
        double d4 = d3 / d1;
        PNodes localPNodes2 = (PNodes)point(k);
        localPNodes2.jdField_time_of_type_Double = (((PNodes)localObject).jdField_time_of_type_Double + ((PNodes)localObject).timeoutMin * 60.0D + d4);
        if (localPNodes2.timeoutMin > 0.0D) {
          localPNodes2.timeoutMin = Math.round((localPNodes2.timeoutMin * 60.0D - localPNodes2.jdField_time_of_type_Double) / 60.0D);
        }
        if (localPNodes2.timeoutMin < 0.0D)
          localPNodes2.timeoutMin = 0.0D;
        localObject = localPNodes2;
      }
    } else {
      for (int j = 1; j < i; j++) {
        PNodes localPNodes1 = (PNodes)point(j);
        double d2 = ((PNodes)localObject).computeTime(localPNodes1);
        localPNodes1.jdField_time_of_type_Double = (((PNodes)localObject).jdField_time_of_type_Double + ((PNodes)localObject).timeoutMin * 60.0D + d2);
        if (localPNodes1.timeoutMin > 0.0D) {
          localPNodes1.timeoutMin = Math.round((localPNodes1.timeoutMin * 60.0D - localPNodes1.jdField_time_of_type_Double) / 60.0D);
        }
        if (localPNodes1.timeoutMin < 0.0D)
          localPNodes1.timeoutMin = 0.0D;
        localObject = localPNodes1;
      }
    }
  }

  public void placeUnits(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    if ((paramPoint3d2 == null) || (paramPoint3d1.distance(paramPoint3d2) < 0.1D)) {
      paramPoint3d2 = new Point3d(paramPoint3d1);
      paramPoint3d2.jdField_y_of_type_Double += 100.0D;
    }
    if ((paramPoint3d1.equals(this.prevStart)) && (paramPoint3d2.equals(this.prevEnd)))
      return;
    this.prevStart.set(paramPoint3d1);
    this.prevEnd.set(paramPoint3d2);

    float f1 = 0.0F;
    float f2 = 0.0F;
    for (int i = 0; i < this.units.length; i++)
    {
      float f3 = this.units[i].mesh().collisionR();
      float f4 = f3;

      if (f3 > f1) f1 = f3;
      if (f4 <= f2) continue; f2 = f4;
    }
    if (PlMisChief.moveType(this._iType) != 3) {
      f1 *= 2.0F;
      f2 *= 2.0F;
    }

    if (f1 <= 0.001F) f1 = 0.001F;
    if (f2 <= 0.001F) f2 = 0.001F;

    int j = 1;
    int k = this.units.length;
    float f5 = (f2 * j + f2 * 0.5F * (j - 1)) / (f1 * k + f1 * 0.5F * (k - 1));

    if (PlMisChief.moveType(this._iType) != 3) {
      for (int m = 2; m <= this.units.length; m++) {
        int n = (this.units.length + m - 1) / m;
        float f6 = (f2 * m + f2 * 0.5F * (m - 1)) / (f1 * n + f1 * 0.5F * (n - 1));

        if (Math.abs(f6 - 0.5F) < Math.abs(f5 - 0.5F)) {
          f5 = f6;
          j = m;
          k = n;
        }
      }

    }

    double d1 = paramPoint3d2.jdField_x_of_type_Double - paramPoint3d1.jdField_x_of_type_Double;
    double d2 = paramPoint3d2.jdField_y_of_type_Double - paramPoint3d1.jdField_y_of_type_Double;
    double d3 = Math.atan2(d2, d1);
    double d4 = Math.sin(-d3);
    double d5 = Math.cos(-d3);
    d3 = Geom.RAD2DEG((float)d3);

    float f7 = (f2 * j + f2 * 0.5F * (j - 1)) * 0.5F;
    float f8 = (f1 * k + f1 * 0.5F * (k - 1)) * 0.5F;
    int i1 = 0;
    for (int i2 = 0; i2 < k; i2++)
      for (int i3 = 0; i3 < j; i1++)
      {
        if (i1 >= this.units.length)
        {
          break;
        }

        double d6 = -f8 + f1 * 0.5F + i2 * 1.5F * f1;
        double d7 = f7 - f2 * 0.5F - i3 * 1.5F * f2;
        double d8 = d5 * d6 + d4 * d7;
        double d9 = -d4 * d6 + d5 * d7;

        double d10 = 0.0D;
        int i4 = this.units[(this.units.length - 1 - i1)].mesh().hookFind("Ground_Level");
        if (i4 != -1) {
          localObject = new Matrix4d();
          this.units[(this.units.length - 1 - i1)].mesh().hookMatrix(i4, (Matrix4d)localObject);
          d10 = -((Matrix4d)localObject).m23;
        }

        Object localObject = new Point3d(paramPoint3d1);
        localObject.jdField_x_of_type_Double += d8;
        localObject.jdField_y_of_type_Double += d9;
        ((Point3d)localObject).z = (Engine.land().HQ(((Point3d)localObject).jdField_x_of_type_Double, ((Point3d)localObject).jdField_y_of_type_Double) + d10);

        Orient localOrient = new Orient();
        localOrient.setYPR((float)d3, 0.0F, 0.0F);
        Vector3f localVector3f = new Vector3f();
        Engine.land().N(((Point3d)localObject).jdField_x_of_type_Double, ((Point3d)localObject).jdField_y_of_type_Double, localVector3f);
        localOrient.orient(localVector3f);
        this.units[(this.units.length - 1 - i1)].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs((Point3d)localObject, localOrient);
        this.units[(this.units.length - 1 - i1)].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

        i3++;
      }
  }

  public void drawing(boolean paramBoolean)
  {
    super.drawing(paramBoolean);
    if (this.units != null)
      for (int i = 0; i < this.units.length; i++)
        if (Actor.isValid(this.units[i]))
          this.units[i].drawing(paramBoolean);
  }

  public void clear() {
    if (this.units != null) {
      for (int i = 0; i < this.units.length; i++)
        if (Actor.isValid(this.units[i])) {
          this.units[i].destroy();
          this.units[i] = null;
        }
      this.units = null;
    }
    this.prevStart.set(0.0D, 0.0D, 0.0D);
    this.prevEnd.set(0.0D, 0.0D, 0.0D);
  }

  public void destroy() {
    if (isDestroyed()) return;
    clear();
    super.destroy();
  }

  public void setUnits(int paramInt1, int paramInt2, SectFile paramSectFile, int paramInt3, Point3d paramPoint3d) {
    clear();
    try {
      int i = paramSectFile.vars(paramInt3);
      this.units = new ActorMesh[i];
      for (int j = 0; j < i; j++) {
        String str1 = paramSectFile.var(paramInt3, j);
        Class localClass = Class.forName(str1);
        String str2 = Property.stringValue(localClass, "meshName", null);
        if (str2 == null) {
          Method localMethod = localClass.getMethod("getMeshNameForEditor", null);
          str2 = (String)localMethod.invoke(localClass, null);
        }
        if (str2.toLowerCase().endsWith(".sim"))
          this.units[j] = new ActorSimpleMesh(str2);
        else
          this.units[j] = new ActorSimpleHMesh(str2);
      }
      this._iType = paramInt1;
      this._iItem = paramInt2;
      placeUnits(paramPoint3d, null);
    } catch (Exception localException) {
      destroy();
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      throw new RuntimeException(localException.toString());
    }
  }

  public void updateUnitsPos() {
    if (this.units == null) return;
    if (points() == 0) return;
    if (!Actor.isValid(point(0))) return;
    PNodes localPNodes = (PNodes)point(0);
    if (localPNodes.posXYZ != null) {
      this.p2.set(localPNodes.posXYZ[4], localPNodes.posXYZ[5], localPNodes.posXYZ[6]);

      placeUnits(point(0).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.p2);
    } else {
      placeUnits(point(0).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), null);
    }
  }

  public void pointMoved(PPoint paramPPoint)
  {
    computeTimes();
    int i = pointIndx(paramPPoint);
    if ((i != 0) && (i != 1))
      return;
    updateUnitsPos();
  }

  public PathChief(Pathes paramPathes, int paramInt1, int paramInt2, int paramInt3, SectFile paramSectFile, int paramInt4, Point3d paramPoint3d) {
    super(paramPathes);
    this.moveType = paramInt1;
    setUnits(paramInt2, paramInt3, paramSectFile, paramInt4, paramPoint3d);
  }
}