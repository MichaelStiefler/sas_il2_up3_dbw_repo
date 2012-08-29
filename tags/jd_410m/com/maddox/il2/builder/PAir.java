package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.rts.Property;

public class PAir extends PPoint
{
  public static final int NORMFLY = 0;
  public static final int TAKEOFF = 1;
  public static final int LANDING = 2;
  public static final int GATTACK = 3;
  public static final String[] types = { "NORMFLY", "TAKEOFF", "LANDING", "GATTACK" };
  private int type;
  public double height;
  public double speed;
  private Actor target = null;
  public String sTarget = null;
  public int iTarget = 0;
  public boolean bRadioSilence = false;

  public void setType(int paramInt) {
    this.type = paramInt;
    setIcon();
  }
  public int type() {
    return this.type;
  }
  public Actor getTarget() {
    if (Actor.isValid(this.target))
      return this.target;
    if (this.sTarget != null) {
      Actor localActor = Actor.getByName(this.sTarget);
      if (Actor.isValid(localActor)) {
        if ((localActor instanceof Path)) {
          Path localPath = (Path)localActor;
          if (this.iTarget >= localPath.points())
            this.iTarget = (localPath.points() - 1);
          if (this.iTarget < 0) {
            this.iTarget = 0;
            this.sTarget = null;
            this.target = null;
          } else {
            PPoint localPPoint = localPath.point(this.iTarget);
            this.target = localPPoint;
          }
        } else {
          this.target = localActor;
        }
        return this.target;
      }
      this.sTarget = null;
    }
    this.target = null;
    return null;
  }

  public void setTarget(Actor paramActor) {
    this.target = paramActor;
    if (this.target != null)
      if ((this.type != 1) && (this.type != 2)) {
        if ((this.target instanceof PAir)) setType(0); else
          setType(3);
      }
      else if ((this.target instanceof PPoint)) {
        if ((this.target.getOwner() instanceof PathChief)) {
          PathChief localPathChief = (PathChief)this.target.getOwner();
          if (!PlMisChief.isAirport(localPathChief._iType, localPathChief._iItem))
            this.target = null;
        } else {
          this.target = null;
        }
      } else if (!"true".equals(Property.stringValue(this.target.getClass(), "IsAirport", (String)null)))
        this.target = null;
  }

  public void destroy()
  {
    this.target = null;
    this.sTarget = null;
    Path localPath = (Path)getOwner();
    super.destroy();
    if (localPath != null)
      localPath.computeTimes();
  }

  public PAir(Path paramPath, PPoint paramPPoint, Mat paramMat, Point3d paramPoint3d, int paramInt, double paramDouble1, double paramDouble2)
  {
    super(paramPath, paramPPoint, paramMat, paramPoint3d);
    setType(paramInt);
    this.height = paramDouble1;
    this.speed = paramDouble2;
    paramPath.computeTimes();
  }

  public PAir(Path paramPath, PPoint paramPPoint, Point3d paramPoint3d, int paramInt, double paramDouble1, double paramDouble2)
  {
    this(paramPath, paramPPoint, (Mat)null, paramPoint3d, paramInt, paramDouble1, paramDouble2);
  }

  public PAir(Path paramPath, PPoint paramPPoint, String paramString, Point3d paramPoint3d, int paramInt, double paramDouble1, double paramDouble2)
  {
    this(paramPath, paramPPoint, IconDraw.get(paramString), paramPoint3d, paramInt, paramDouble1, paramDouble2);
  }

  private void setIcon() {
    String str = null;
    switch (this.type) { case 0:
      str = "normfly"; break;
    case 1:
      str = "takeoff"; break;
    case 2:
      str = "landing"; break;
    case 3:
      str = "gattack"; break;
    default:
      return;
    }
    this.icon = IconDraw.get("icons/" + str + ".mat");
  }
}