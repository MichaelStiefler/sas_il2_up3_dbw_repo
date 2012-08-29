package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Time;
import java.util.ArrayList;

public class ActorPosMovePob extends ActorPos
{
  private Actor actor = null;

  private Loc Labs = new Loc();

  private boolean absChanged = false;

  private Loc curLabs = new Loc();

  private Loc prevLabs = new Loc();

  private static Loc __l = new Loc();

  public void inValidate(boolean paramBoolean)
  {
    if (!this.absChanged) Engine.cur.posChanged.add(this.actor);
    this.absChanged = true;
  }

  public Actor actor() {
    return this.actor;
  }
  public Actor homeBase() {
    return null;
  }
  public Actor base() {
    return null;
  }
  public Object baseHook() {
    return null;
  }

  public void getRel(Loc paramLoc) {
    paramLoc.set(this.Labs);
  }

  public void getRel(Point3d paramPoint3d, Orient paramOrient)
  {
    this.Labs.get(paramPoint3d, paramOrient);
  }

  public void getRel(Point3d paramPoint3d)
  {
    this.Labs.get(paramPoint3d);
  }

  public void getRel(Orient paramOrient)
  {
    this.Labs.get(paramOrient);
  }

  public Loc getRel() {
    return this.Labs;
  }
  public Point3d getRelPoint() {
    return this.Labs.getPoint();
  }
  public Orient getRelOrient() {
    return this.Labs.getOrient();
  }

  public void setRel(Point3d paramPoint3d) {
    this.Labs.set(paramPoint3d);
    inValidate(false);
  }

  public void getAbs(Loc paramLoc)
  {
    paramLoc.set(this.Labs);
  }

  public void getAbs(Point3d paramPoint3d, Orient paramOrient)
  {
    this.Labs.get(paramPoint3d, paramOrient);
  }

  public void getAbs(Point3d paramPoint3d)
  {
    this.Labs.get(paramPoint3d);
  }

  public void getAbs(Orient paramOrient)
  {
    this.Labs.get(paramOrient);
  }

  public Loc getAbs()
  {
    return this.Labs;
  }

  public Point3d getAbsPoint()
  {
    return this.Labs.getPoint();
  }

  public Orient getAbsOrient()
  {
    return this.Labs.getOrient();
  }

  public void setAbs(Point3d paramPoint3d)
  {
    this.Labs.set(paramPoint3d);
    inValidate(false);
  }

  public boolean isChanged() {
    return this.absChanged;
  }

  public void getCurrent(Loc paramLoc) {
    paramLoc.set(this.curLabs);
  }

  public void getCurrent(Point3d paramPoint3d)
  {
    this.curLabs.get(paramPoint3d);
  }

  public Loc getCurrent() {
    return this.curLabs;
  }
  public Point3d getCurrentPoint() {
    return this.curLabs.getPoint();
  }
  public Orient getCurrentOrient() {
    return this.curLabs.getOrient();
  }

  public void getPrev(Loc paramLoc) {
    paramLoc.set(this.prevLabs);
  }

  public Loc getPrev()
  {
    return this.prevLabs;
  }

  public Loc getRender()
  {
    getRender(__l);
    return __l;
  }

  public void getRender(Loc paramLoc)
  {
    paramLoc.interpolate(this.curLabs, this.Labs, Time.tickOffset());
  }

  public void getRender(Point3d paramPoint3d, Orient paramOrient)
  {
    getRender(__l);
    __l.get(paramPoint3d, paramOrient);
  }

  public void getTime(long paramLong, Loc paramLoc)
  {
    if (InterpolateAdapter.isProcess()) {
      paramLoc.interpolate(this.prevLabs, this.curLabs, ((float)(paramLong - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
    }
    else if (paramLong == Time.tick())
      paramLoc.set(this.curLabs);
    else if (paramLong < Time.tick())
      paramLoc.interpolate(this.prevLabs, this.curLabs, ((float)(paramLong - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
    else if (paramLong == Time.tickNext())
      paramLoc.set(this.Labs);
    else
      paramLoc.interpolate(this.curLabs, this.Labs, (float)(paramLong - Time.tick()) / Time.tickLenFms());
  }

  public void getTime(long paramLong, Point3d paramPoint3d)
  {
    if (InterpolateAdapter.isProcess()) {
      paramPoint3d.interpolate(this.prevLabs.getPoint(), this.curLabs.getPoint(), ((float)(paramLong - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
    }
    else if (paramLong == Time.tick())
      this.curLabs.get(paramPoint3d);
    else if (paramLong < Time.tick())
      paramPoint3d.interpolate(this.prevLabs.getPoint(), this.curLabs.getPoint(), ((float)(paramLong - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
    else if (paramLong == Time.tickNext())
      this.Labs.get(paramPoint3d);
    else
      paramPoint3d.interpolate(this.curLabs.getPoint(), this.Labs.getPoint(), (float)(paramLong - Time.tick()) / Time.tickLenFms());
  }

  public void resetAsBase()
  {
    reset();
  }

  public void reset() {
    updateCurrent();
    this.prevLabs.set(this.curLabs);
    int i = Engine.cur.posChanged.indexOf(this.actor);
    if (i >= 0)
      Engine.cur.posChanged.remove(i);
  }

  protected void updateCurrent()
  {
    this.prevLabs.set(this.curLabs);
    if (this.absChanged) {
      getAbs(this.curLabs);
      int i = this.actor.flags;
      if ((i & 0x3) == 1)
        Engine.cur.drawEnv.changedPos(this.actor, this.prevLabs.getPoint(), this.curLabs.getPoint());
      if ((i & 0x30) == 16)
        Engine.cur.collideEnv.changedPos(this.actor, this.prevLabs.getPoint(), this.curLabs.getPoint());
      if ((i & 0x200) == 512)
        Engine.cur.dreamEnv.changedListenerPos(this.actor, this.prevLabs.getPoint(), this.curLabs.getPoint());
      if ((i & 0x100) == 256)
        Engine.cur.dreamEnv.changedFirePos(this.actor, this.prevLabs.getPoint(), this.curLabs.getPoint());
      this.absChanged = false;
    }
  }

  public double speed(Vector3d paramVector3d)
  {
    if (InterpolateAdapter.isProcess()) {
      localPoint3d1 = this.prevLabs.getPoint();
      localPoint3d2 = this.curLabs.getPoint();
      d1 = localPoint3d2.jdField_x_of_type_Double - localPoint3d1.jdField_x_of_type_Double;
      d2 = localPoint3d2.jdField_y_of_type_Double - localPoint3d1.jdField_y_of_type_Double;
      d3 = localPoint3d2.jdField_z_of_type_Double - localPoint3d1.jdField_z_of_type_Double;
      d4 = 1.0F / Time.tickLenFs();
      if (paramVector3d != null)
        paramVector3d.set(d1 * d4, d2 * d4, d3 * d4);
      return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3) * d4;
    }
    Point3d localPoint3d1 = this.Labs.getPoint();
    Point3d localPoint3d2 = this.curLabs.getPoint();
    double d1 = localPoint3d1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double;
    double d2 = localPoint3d1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double;
    double d3 = localPoint3d1.jdField_z_of_type_Double - localPoint3d2.jdField_z_of_type_Double;
    double d4 = 1.0F / Time.tickLenFs();
    if (paramVector3d != null)
      paramVector3d.set(d1 * d4, d2 * d4, d3 * d4);
    return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3) * d4;
  }

  protected void drawingChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.cur.drawEnv.add(this.actor); else
      Engine.cur.drawEnv.remove(this.actor);
  }

  protected void collideChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.cur.collideEnv.add(this.actor); else
      Engine.cur.collideEnv.remove(this.actor);
  }

  public void destroy() {
    if (this.actor == null) return;
    clearEnvs(this.actor);
    this.actor = null;
  }

  public ActorPosMovePob(Actor paramActor) {
    this.actor = paramActor;
    initEnvs(this.actor);
  }

  public ActorPosMovePob(Actor paramActor, Point3d paramPoint3d, Orient paramOrient) {
    this.actor = paramActor;
    this.Labs.set(paramPoint3d, paramOrient);
    this.curLabs.set(this.Labs);
    this.prevLabs.set(this.Labs);
    initEnvs(this.actor);
  }

  public ActorPosMovePob(Actor paramActor, Loc paramLoc) {
    this.actor = paramActor;
    this.Labs.set(paramLoc);
    this.curLabs.set(this.Labs);
    this.prevLabs.set(this.Labs);
    initEnvs(this.actor);
  }
}