package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import java.util.ArrayList;

class ActorPosStaticEff3D extends ActorPosStatic
{
  protected void drawingChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.cur.drawEnv.add(actor()); else
      Engine.cur.drawEnv.remove(actor());
  }

  protected void collideChange(boolean paramBoolean)
  {
    if (paramBoolean) Engine.cur.collideEnv.add(actor()); else
      Engine.cur.collideEnv.remove(actor());
  }

  protected void changePosInEnvs(Point3d paramPoint3d1, Point3d paramPoint3d2) {
    inValidate(true);
    int i = actor().flags;
    if ((i & 0x1) == 1)
      Engine.cur.drawEnv.changedPos(actor(), paramPoint3d1, paramPoint3d2);
    if ((i & 0x30) == 16)
      Engine.cur.collideEnv.changedPosStatic(actor(), paramPoint3d1, paramPoint3d2);
    if ((i & 0x200) == 512)
      Engine.cur.dreamEnv.changedListenerPos(actor(), paramPoint3d1, paramPoint3d2);
    if ((i & 0x100) == 256)
      Engine.cur.dreamEnv.changedFirePos(actor(), paramPoint3d1, paramPoint3d2);
  }

  protected void clearEnvs(Actor paramActor) {
    int i = paramActor.flags;
    if (((i & 0x1) != 0) && (((i & 0x2) == 0) || (base() == null)))
      Engine.cur.drawEnv.remove(paramActor);
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
      Engine.cur.drawEnv.add(paramActor);
    if ((i & 0x30) == 16)
      Engine.cur.collideEnv.addStatic(paramActor);
    if ((i & 0x200) == 512)
      Engine.cur.dreamEnv.addListener(paramActor);
    if ((i & 0x100) == 256)
      Engine.dreamEnv().addFire(paramActor);
  }

  public ActorPosStaticEff3D(Actor paramActor, Loc paramLoc) {
    super(paramActor, paramLoc);
  }
}