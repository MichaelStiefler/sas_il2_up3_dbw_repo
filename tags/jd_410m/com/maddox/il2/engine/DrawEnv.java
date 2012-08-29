package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.List;

public abstract class DrawEnv
{
  public static final int LANDPLATE = 1;
  public static final int STATIC = 2;
  public static final int MOVED = 12;
  public static final int MOVED_SHORT = 4;
  public static final int MOVED_LONG = 8;
  public static final int ALL = 15;

  public void preRender(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, int paramInt, List paramList1, List paramList2, List paramList3, boolean paramBoolean)
  {
  }

  public void getFiltered(AbstractCollection paramAbstractCollection, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt, ActorFilter paramActorFilter)
  {
  }

  public void getFiltered(AbstractMap paramAbstractMap, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt, ActorFilter paramActorFilter)
  {
  }

  public void staticTrimToSize()
  {
  }

  protected void changedPos(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
  }

  protected void add(Actor paramActor)
  {
  }

  protected void remove(Actor paramActor)
  {
  }

  protected void changedPosStatic(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
  }

  protected void addStatic(Actor paramActor)
  {
  }

  protected void removeStatic(Actor paramActor)
  {
  }

  public void setUniformMaxDist(int paramInt1, int paramInt2, float paramFloat)
  {
  }

  protected void clear()
  {
  }

  public void resetGameClear()
  {
  }

  public void resetGameCreate()
  {
  }

  protected void preRender(Actor paramActor, int paramInt, List paramList1, List paramList2, List paramList3)
  {
    if ((paramList2 != null) && ((paramInt & 0x2) != 0)) paramList2.add(paramActor);
    else if ((paramList1 != null) && ((paramInt & 0x1) != 0)) paramList1.add(paramActor);
    if ((paramList3 != null) && ((paramInt & 0x4) != 0)) paramList3.add(paramActor);
    Engine.cur.profile.draw += 1;
    List localList = paramActor.pos.getListBaseAttached();
    if (localList == null)
      return;
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localList.get(j);
      if ((!Actor.isValid(localActor)) || ((localActor.flags & 0x3) != 3) || (localActor.draw == null)) {
        continue;
      }
      paramInt = localActor.draw.preRender(localActor);
      if (paramInt > 0)
        preRender(localActor, paramInt, paramList1, paramList2, paramList3);
    }
  }
}