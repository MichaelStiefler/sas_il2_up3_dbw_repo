package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import java.util.ArrayList;
import java.util.List;

public class ActorPosStatic extends ActorPos
{
  private Actor actor = null;

  private List baseAttached = null;

  private Loc L = new Loc();

  public Actor actor() {
    return this.actor;
  }

  public Object[] getBaseAttached() {
    if (this.baseAttached != null)
      return this.baseAttached.toArray();
    return null;
  }

  public Object[] getBaseAttached(Object[] paramArrayOfObject)
  {
    if (this.baseAttached != null)
      return this.baseAttached.toArray(paramArrayOfObject);
    return null;
  }

  protected List getListBaseAttached() {
    return this.baseAttached;
  }

  public void inValidate(boolean paramBoolean) {
    if (this.baseAttached != null) {
      int j = this.baseAttached.size();
      for (int i = 0; i < j; i++) {
        Actor localActor = (Actor)this.baseAttached.get(i);
        if (localActor == localActor.pos.actor())
          localActor.pos.inValidate(true);
      }
    }
  }

  public void setBase(Actor paramActor, Object paramObject, long paramLong, boolean paramBoolean)
  {
    throw new ActorException("static position not changed base");
  }

  public void changeBase(Actor paramActor, Object paramObject, long paramLong, boolean paramBoolean)
  {
    throw new ActorException("static position not changed base");
  }

  public void getRel(Loc paramLoc)
  {
    paramLoc.set(this.L);
  }

  public void getRel(Point3d paramPoint3d, Orient paramOrient)
  {
    this.L.get(paramPoint3d, paramOrient);
  }

  public void getRel(Point3d paramPoint3d)
  {
    this.L.get(paramPoint3d);
  }

  public void getRel(Orient paramOrient)
  {
    this.L.get(paramOrient);
  }
  public Loc getRel() {
    return this.L;
  }
  public Point3d getRelPoint() {
    return this.L.getPoint();
  }
  public Orient getRelOrient() {
    return this.L.getOrient();
  }
  protected void changePosInEnvs(Point3d paramPoint3d1, Point3d paramPoint3d2) {
    inValidate(true);
    int i = this.actor.flags;
    if ((i & 0x1) == 1)
      Engine.cur.drawEnv.changedPosStatic(this.actor, paramPoint3d1, paramPoint3d2);
    if ((i & 0x30) == 16)
      Engine.cur.collideEnv.changedPosStatic(this.actor, paramPoint3d1, paramPoint3d2);
    if ((i & 0x200) == 512)
      Engine.cur.dreamEnv.changedListenerPos(this.actor, paramPoint3d1, paramPoint3d2);
    if ((i & 0x100) == 256)
      Engine.cur.dreamEnv.changedFirePos(this.actor, paramPoint3d1, paramPoint3d2);
  }

  public void setRel(Loc paramLoc)
  {
    changePosInEnvs(this.L.getPoint(), paramLoc.getPoint());
    this.L.set(paramLoc);
  }

  public void setRel(Point3d paramPoint3d, Orient paramOrient)
  {
    changePosInEnvs(this.L.getPoint(), paramPoint3d);
    this.L.set(paramPoint3d, paramOrient);
  }

  public void setRel(Point3d paramPoint3d)
  {
    changePosInEnvs(this.L.getPoint(), paramPoint3d);
    this.L.set(paramPoint3d);
  }

  public void setRel(Orient paramOrient)
  {
    this.L.set(paramOrient);
  }

  public void getAbs(Loc paramLoc)
  {
    paramLoc.set(this.L);
  }

  public void getAbs(Point3d paramPoint3d, Orient paramOrient)
  {
    this.L.get(paramPoint3d, paramOrient);
  }

  public void getAbs(Point3d paramPoint3d)
  {
    this.L.get(paramPoint3d);
  }

  public void getAbs(Orient paramOrient)
  {
    this.L.get(paramOrient);
  }

  public Loc getAbs() {
    return this.L;
  }
  public Point3d getAbsPoint() {
    return this.L.getPoint();
  }
  public Orient getAbsOrient() {
    return this.L.getOrient();
  }

  public void setAbs(Loc paramLoc) {
    setRel(paramLoc);
  }

  public void setAbs(Point3d paramPoint3d, Orient paramOrient)
  {
    setRel(paramPoint3d, paramOrient);
  }

  public void setAbs(Point3d paramPoint3d)
  {
    setRel(paramPoint3d);
  }

  public void setAbs(Orient paramOrient)
  {
    setRel(paramOrient);
  }
  public void reset() {
  }

  public void resetAsBase() {
  }

  public boolean isChanged() {
    return false;
  }

  public void getCurrent(Loc paramLoc) {
    paramLoc.set(this.L);
  }

  public void getCurrent(Point3d paramPoint3d)
  {
    this.L.get(paramPoint3d);
  }

  public Loc getCurrent() {
    return this.L;
  }
  public Point3d getCurrentPoint() {
    return this.L.getPoint();
  }
  public Orient getCurrentOrient() {
    return this.L.getOrient();
  }

  public void getPrev(Loc paramLoc) {
    paramLoc.set(this.L);
  }

  public Loc getPrev()
  {
    return this.L;
  }

  protected void drawingChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.cur.drawEnv.addStatic(this.actor); else
      Engine.cur.drawEnv.removeStatic(this.actor);
  }

  protected void collideChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.cur.collideEnv.addStatic(this.actor); else
      Engine.cur.collideEnv.removeStatic(this.actor);
  }

  protected void dreamFireChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.dreamEnv().addFire(this.actor); else
      Engine.dreamEnv().removeFire(this.actor);
  }

  public Loc getRender()
  {
    return this.L;
  }

  public void getRender(Loc paramLoc)
  {
    paramLoc.set(this.L);
  }

  public void getRender(Point3d paramPoint3d, Orient paramOrient)
  {
    this.L.get(paramPoint3d, paramOrient);
  }

  public void getRender(Point3d paramPoint3d)
  {
    this.L.get(paramPoint3d);
  }

  public void getTime(long paramLong, Loc paramLoc)
  {
    paramLoc.set(this.L);
  }

  public void getTime(long paramLong, Point3d paramPoint3d)
  {
    this.L.get(paramPoint3d);
  }

  protected void addChildren(Actor paramActor)
  {
    if (this.baseAttached == null)
      this.baseAttached = new ArrayList();
    this.baseAttached.add(paramActor);
  }

  protected void removeChildren(Actor paramActor)
  {
    this.baseAttached.remove(this.baseAttached.indexOf(paramActor));
  }

  protected void clearEnvs(Actor paramActor) {
    int i = paramActor.flags;
    if (((i & 0x1) != 0) && (((i & 0x2) == 0) || (base() == null)))
      Engine.cur.drawEnv.removeStatic(paramActor);
    if ((i & 0x30) == 16)
      Engine.cur.collideEnv.removeStatic(paramActor);
    if ((i & 0x200) == 512)
      Engine.cur.dreamEnv.removeListener(paramActor);
    if ((i & 0x100) == 256)
      Engine.dreamEnv().removeFire(paramActor);
    int j = Engine.cur.posChanged.indexOf(paramActor);
    if (j >= 0)
      Engine.cur.posChanged.remove(j);
  }

  protected void initEnvs(Actor paramActor) {
    paramActor.pos = this;
    int i = paramActor.flags;
    if (((i & 0x1) != 0) && (((i & 0x2) == 0) || (base() == null)))
      Engine.cur.drawEnv.addStatic(paramActor);
    if ((i & 0x30) == 16)
      Engine.cur.collideEnv.addStatic(paramActor);
    if ((i & 0x200) == 512)
      Engine.cur.dreamEnv.addListener(paramActor);
    if ((i & 0x100) == 256)
      Engine.dreamEnv().addFire(paramActor);
  }

  public void destroy() {
    if (this.actor == null) return;
    clearEnvs(this.actor);
    if (this.baseAttached != null) {
      while (this.baseAttached.size() > 0) {
        Actor localActor = (Actor)this.baseAttached.get(0);
        if (localActor == localActor.pos.actor())
          localActor.pos.changeBase(null, null, true);
        else
          this.baseAttached.remove(0);
      }
    }
    this.actor = null;
  }

  public ActorPosStatic(Actor paramActor) {
    this.actor = paramActor;
    initEnvs(this.actor);
  }

  public ActorPosStatic(Actor paramActor, Point3d paramPoint3d, Orient paramOrient) {
    this.actor = paramActor;
    this.L.set(paramPoint3d, paramOrient);
    initEnvs(this.actor);
  }

  public ActorPosStatic(Actor paramActor, Loc paramLoc) {
    this.actor = paramActor;
    this.L.set(paramLoc);
    initEnvs(this.actor);
  }
}