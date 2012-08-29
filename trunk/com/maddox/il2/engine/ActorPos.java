package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Destroy;
import java.util.ArrayList;
import java.util.List;

public abstract class ActorPos
  implements Destroy
{
  public void inValidate()
  {
    inValidate(true);
  }

  public void inValidate(boolean paramBoolean)
  {
  }

  public void setUpdateEnable(boolean paramBoolean)
  {
  }

  public boolean isUpdateEnable()
  {
    return true;
  }

  public Actor actor() {
    return null;
  }

  public void actorChanged()
  {
    Actor localActor1 = actor();
    if (!Actor.isValid(localActor1))
      return;
    Object[] arrayOfObject = getBaseAttached();
    if (arrayOfObject == null)
      return;
    for (int i = 0; i < arrayOfObject.length; i++) {
      Actor localActor2 = (Actor)arrayOfObject[i];
      if (localActor2 == null)
        break;
      if ((!Actor.isValid(localActor2)) || (localActor2.pos.actor() != localActor2) || (localActor2.pos.baseHook() == null) || (!(localActor2.pos.baseHook() instanceof Hook)))
      {
        continue;
      }
      ((Hook)(Hook)localActor2.pos.baseHook()).baseChanged(localActor1);
    }

    inValidate(false);
  }

  public Actor homeBase()
  {
    return null;
  }

  public Actor base()
  {
    return null;
  }

  public Object baseHook()
  {
    return null;
  }

  public Object[] getBaseAttached()
  {
    return null;
  }

  public Object[] getBaseAttached(Object[] paramArrayOfObject)
  {
    return null;
  }

  protected List getListBaseAttached()
  {
    return null;
  }

  public void setBase(Actor paramActor, Hook paramHook, boolean paramBoolean)
  {
    throw new ActorException("method 'setBase' not implemented");
  }

  public void changeBase(Actor paramActor, Hook paramHook, boolean paramBoolean)
  {
    throw new ActorException("method 'changeBase' not implemented");
  }

  public void changeHookToRel()
  {
    throw new ActorException("method 'changeHookToRel' not implemented");
  }

  public void getRel(Loc paramLoc)
  {
    throw new ActorException("method 'getRel' not implemented");
  }

  public void getRel(Point3d paramPoint3d, Orient paramOrient)
  {
    throw new ActorException("method 'getRel' not implemented");
  }

  public void getRel(Point3d paramPoint3d)
  {
    throw new ActorException("method 'getRel' not implemented");
  }

  public void getRel(Orient paramOrient)
  {
    throw new ActorException("method 'getRel' not implemented");
  }

  public Loc getRel()
  {
    throw new ActorException("method 'getRel' not implemented");
  }

  public Point3d getRelPoint()
  {
    throw new ActorException("method 'getRelPoint' not implemented");
  }

  public Orient getRelOrient()
  {
    throw new ActorException("method 'getRelOrient' not implemented");
  }

  public void setRel(Loc paramLoc)
  {
    throw new ActorException("method 'setRel' not implemented");
  }

  public void setRel(Point3d paramPoint3d, Orient paramOrient)
  {
    throw new ActorException("method 'setRel' not implemented");
  }

  public void setRel(Point3d paramPoint3d)
  {
    throw new ActorException("method 'setRel' not implemented");
  }

  public void setRel(Orient paramOrient)
  {
    throw new ActorException("method 'setRel' not implemented");
  }

  public void getAbs(Loc paramLoc)
  {
    throw new ActorException("method 'getAbs' not implemented");
  }

  public void getAbs(Point3d paramPoint3d, Orient paramOrient)
  {
    throw new ActorException("method 'getAbs' not implemented");
  }

  public void getAbs(Point3d paramPoint3d)
  {
    throw new ActorException("method 'getAbs' not implemented");
  }

  public void getAbs(Orient paramOrient)
  {
    throw new ActorException("method 'getAbs' not implemented");
  }

  public Loc getAbs()
  {
    throw new ActorException("method 'getAbs' not implemented");
  }

  public Point3d getAbsPoint()
  {
    throw new ActorException("method 'getAbsPoint' not implemented");
  }

  public Orient getAbsOrient()
  {
    throw new ActorException("method 'getAbsOrient' not implemented");
  }

  public void setAbs(Loc paramLoc)
  {
    throw new ActorException("method 'setAbs' not implemented");
  }

  public void setAbs(Point3d paramPoint3d, Orient paramOrient)
  {
    throw new ActorException("method 'setAbs' not implemented");
  }

  public void setAbs(Point3d paramPoint3d)
  {
    throw new ActorException("method 'setAbs' not implemented");
  }

  public void setAbs(Orient paramOrient)
  {
    throw new ActorException("method 'setAbs' not implemented");
  }

  public void reset()
  {
    throw new ActorException("method 'reset' not implemented");
  }

  public void resetAsBase()
  {
    throw new ActorException("methodAs 'reset' not implemented");
  }

  public boolean isChanged()
  {
    throw new ActorException("method 'isChanged' not implemented");
  }

  public void getCurrent(Loc paramLoc)
  {
    throw new ActorException("method 'getCurrent' not implemented");
  }

  public void getCurrent(Point3d paramPoint3d)
  {
    throw new ActorException("method 'getCurrent' not implemented");
  }

  public Loc getCurrent()
  {
    throw new ActorException("method 'getCurrent' not implemented");
  }

  public Point3d getCurrentPoint()
  {
    throw new ActorException("method 'getCurrentPoint' not implemented");
  }

  public Orient getCurrentOrient()
  {
    throw new ActorException("method 'getCurrentOrient' not implemented");
  }

  public void getPrev(Loc paramLoc)
  {
    throw new ActorException("method 'getPrev' not implemented");
  }

  public Loc getPrev()
  {
    throw new ActorException("method 'getPrev' not implemented");
  }

  protected void drawingChange(boolean paramBoolean)
  {
  }

  protected void collideChange(boolean paramBoolean) {
  }

  protected void dreamFireChange(boolean paramBoolean) {
  }

  public Loc getRender() {
    throw new ActorException("method 'getRender' not implemented");
  }

  public void getRender(Loc paramLoc)
  {
    throw new ActorException("method 'getRender' not implemented");
  }

  public void getRender(Point3d paramPoint3d, Orient paramOrient)
  {
    throw new ActorException("method 'getRender' not implemented");
  }

  public void getRender(Point3d paramPoint3d)
  {
    throw new ActorException("method 'getRender' not implemented");
  }

  public void getTime(long paramLong, Loc paramLoc)
  {
    throw new ActorException("method 'getTime' not implemented");
  }

  public void getTime(long paramLong, Point3d paramPoint3d)
  {
    throw new ActorException("method 'getTime' not implemented");
  }

  protected void updateCurrent()
  {
    throw new ActorException("method 'updateCurrent' not implemented");
  }

  public double speed(Vector3d paramVector3d)
  {
    if (paramVector3d != null) {
      paramVector3d.set(0.0D, 0.0D, 0.0D);
    }
    return 0.0D;
  }

  protected void addChildren(Actor paramActor)
  {
    throw new ActorException("method 'addChildren' not implemented");
  }

  protected void removeChildren(Actor paramActor)
  {
    throw new ActorException("method 'removeChildren' not implemented");
  }
  public boolean isDestroyed() {
    return actor() == null;
  }
  public void destroy() {
  }

  protected void clearEnvs(Actor paramActor) {
    int i = paramActor.flags;
    if (((i & 0x1) != 0) && (((i & 0x2) == 0) || (base() == null)))
      Engine.cur.drawEnv.remove(paramActor);
    if ((i & 0x30) == 16)
      Engine.cur.collideEnv.remove(paramActor);
    if ((i & 0x200) == 512)
      Engine.cur.dreamEnv.removeListener(paramActor);
    if ((i & 0x100) == 256) {
      Engine.dreamEnv().removeFire(paramActor);
    }
    int j = Engine.cur.posChanged.indexOf(paramActor);
    if (j >= 0)
      Engine.cur.posChanged.remove(j);
  }

  protected void initEnvs(Actor paramActor)
  {
    paramActor.pos = this;
    int i = paramActor.flags;
    if (((i & 0x1) != 0) && (((i & 0x2) == 0) || (base() == null)))
      Engine.cur.drawEnv.add(paramActor);
    if ((i & 0x30) == 16)
      Engine.cur.collideEnv.add(paramActor);
    if ((i & 0x200) == 512)
      Engine.cur.dreamEnv.addListener(paramActor);
    if ((i & 0x100) == 256)
      Engine.dreamEnv().addFire(paramActor);
  }
}