package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.bridges.Bridge;

public class WayPoint
{
  public static final int NORMFLY = 0;
  public static final int TAKEOFF = 1;
  public static final int LANDING = 2;
  public static final int GATTACK = 3;
  private Point3d p = new Point3d();
  public float Speed;
  public int Action;
  public int timeout;
  private boolean bHadTarget = false;
  protected String sTarget;
  protected Actor target;
  protected boolean bTargetFinished = false;

  protected boolean bRadioSilence = false;

  private static Vector3f V1 = new Vector3f();

  public WayPoint()
  {
  }

  public WayPoint(Point3f paramPoint3f)
  {
    this(paramPoint3f.x, paramPoint3f.y, paramPoint3f.z);
  }

  public WayPoint(Point3d paramPoint3d) {
    this((float)paramPoint3d.x, (float)paramPoint3d.y, (float)paramPoint3d.z);
  }

  public WayPoint(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    set(paramFloat1, paramFloat2, paramFloat3, 83.0F);
  }

  public WayPoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  public float x() {
    return (float)this.p.x; } 
  public float y() { return (float)this.p.y; } 
  public float z() { return (float)this.p.z; }

  public void setTarget(String paramString) {
    if (paramString == null) this.bHadTarget = false; else
      this.bHadTarget = true;
    this.sTarget = paramString;
    this.target = null;
  }

  public boolean isTargetFinished() {
    getTarget();
    return this.bTargetFinished;
  }

  public String getTargetName() {
    return this.sTarget;
  }

  public Actor getTarget() {
    if (this.sTarget != null) {
      Actor localActor = Actor.getByName(this.sTarget);
      if (localActor != null) {
        this.target = localActor;
        this.sTarget = null;
      }
    }
    if ((this.target == null) || ((this.target != null) && (!this.target.isAlive()))) {
      this.bTargetFinished = true;
      this.target = null;
    }
    if (!this.bHadTarget) this.bTargetFinished = false;
    return this.target;
  }

  public Actor getTargetActorRandom() {
    Actor localActor1 = getTarget();
    if ((!Actor.isValid(localActor1)) || (!localActor1.isAlive()))
      return null;
    if (((localActor1 instanceof Chief)) || ((localActor1 instanceof Bridge))) {
      int i = localActor1.getOwnerAttachedCount();
      if (i < 1)
        return null;
      Actor localActor2;
      for (int j = 0; j < i; j++) {
        localActor2 = (Actor)localActor1.getOwnerAttached(World.Rnd().nextInt(0, i - 1));
        if ((Actor.isValid(localActor2)) && (localActor2.isAlive()))
          return localActor2;
      }
      for (j = 0; j < i; j++) {
        localActor2 = (Actor)localActor1.getOwnerAttached(j);
        if ((Actor.isValid(localActor2)) && (localActor2.isAlive()))
          return localActor2;
      }
      return null;
    }
    return localActor1;
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.p.set(paramFloat1, paramFloat2, paramFloat3);
  }

  public void set(Point3f paramPoint3f)
  {
    this.p.set(paramPoint3f);
  }

  public void set(Point3d paramPoint3d)
  {
    this.p.set(paramPoint3d);
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.p.set(paramFloat1, paramFloat2, paramFloat3);
    this.Speed = paramFloat4;
  }

  public void set(float paramFloat)
  {
    this.Speed = paramFloat;
  }

  public void setTimeout(int paramInt)
  {
    this.timeout = paramInt;
  }

  public void set(WayPoint paramWayPoint)
  {
    set(paramWayPoint.getP());
    set(paramWayPoint.getV());
    this.sTarget = paramWayPoint.sTarget;
    this.target = paramWayPoint.target;
    this.Action = paramWayPoint.Action;
    this.timeout = paramWayPoint.timeout;
    this.bTargetFinished = paramWayPoint.bTargetFinished;
    this.bHadTarget = paramWayPoint.bHadTarget;
    this.bRadioSilence = paramWayPoint.bRadioSilence;
  }

  public Point3d getP()
  {
    return this.p;
  }

  public void getP(Point3f paramPoint3f)
  {
    paramPoint3f.set(this.p);
  }

  public void getP(Point3d paramPoint3d) {
    paramPoint3d.set(this.p);
  }

  public float getV() {
    return this.Speed;
  }

  public static Vector3f vector(WayPoint paramWayPoint1, WayPoint paramWayPoint2)
  {
    V1.x = (float)(paramWayPoint2.p.x - paramWayPoint1.p.x);
    V1.y = (float)(paramWayPoint2.p.y - paramWayPoint1.p.y);
    V1.z = (float)(paramWayPoint2.p.z - paramWayPoint1.p.z);
    return V1;
  }

  public boolean isRadioSilence()
  {
    return this.bRadioSilence;
  }
}