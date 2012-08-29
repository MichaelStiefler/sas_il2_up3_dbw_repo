package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import java.util.ArrayList;
import java.util.List;

public class ZutiTargetsSupportMethods
{
  public static boolean checkIfActorPartOfTDestroyGround(Actor paramActor, TDestroyGround paramTDestroyGround)
  {
    if (paramActor.pos == null) {
      return false;
    }
    paramActor.pos.getAbs(TDestroyGround.p);
    TDestroyGround.p.z = paramTDestroyGround.pos.getAbsPoint().z;
    if (paramTDestroyGround.pos.getAbsPoint().distance(TDestroyGround.p) <= paramTDestroyGround.r)
    {
      if (!Target.isStaticActor(paramActor)) {
        return false;
      }
      paramTDestroyGround.countActors -= 1;

      if (paramTDestroyGround.countActors <= 0)
      {
        return true;
      }
    }

    return false;
  }

  public static boolean checkIfActorPartOfTDefenceGround(Actor paramActor, TDefenceGround paramTDefenceGround)
  {
    if (paramActor.pos == null) {
      return false;
    }
    paramActor.pos.getAbs(TDefenceGround.p);
    TDefenceGround.p.z = paramTDefenceGround.pos.getAbsPoint().z;
    if (paramTDefenceGround.pos.getAbsPoint().distance(TDefenceGround.p) <= paramTDefenceGround.r)
    {
      if (!Target.isStaticActor(paramActor)) {
        return false;
      }
      paramTDefenceGround.countActors -= 1;

      if (paramTDefenceGround.countActors <= 0)
      {
        return true;
      }
    }

    return false;
  }

  public static void checkForDeactivatedTargets()
  {
    World localWorld = World.cur();
    if ((localWorld == null) || (localWorld.targetsGuard == null)) {
      return;
    }
    List localList = localWorld.targetsGuard.zutiGetTargets();

    if ((localList == null) || (localList.size() == 0)) {
      return;
    }

    ArrayList localArrayList = new ArrayList();

    Target localTarget = null;
    Point3d localPoint3d = null;
    TDestroy localTDestroy = null;
    TDestroyBridge localTDestroyBridge = null;
    TDefenceBridge localTDefenceBridge = null;
    TDestroyGround localTDestroyGround = null;
    TDefenceGround localTDefenceGround = null;
    TEscort localTEscort = null;
    TInspect localTInspect = null;

    int i = localList.size();
    for (int j = 0; j < i; j++)
    {
      localTarget = (Target)localList.get(j);
      boolean bool;
      if ((localTarget instanceof TDestroy))
      {
        localTDestroy = (TDestroy)localTarget;
        bool = localTDestroy.checkActor();

        if ((localTDestroy.actor != null) && ((localTDestroy.actor instanceof BigshipGeneric)))
        {
          if (((BigshipGeneric)localTDestroy.actor).zutiGetDying() > 0)
          {
            ZutiSupportMethods.removeTarget(localTDestroy.nameTarget);
            localArrayList.add(localTarget);
          }
        }
        else if ((localTDestroy.actor != null) && ((localTDestroy.actor instanceof ShipGeneric)))
        {
          if (((ShipGeneric)localTDestroy.actor).zutiGetDying() > 0)
          {
            ZutiSupportMethods.removeTarget(localTDestroy.nameTarget);
            localArrayList.add(localTarget);
          }
        }
        else if (localTDestroy.alive == -1)
        {
          localWorld.targetsGuard.zutiTargetNamesToRemove.add(localTDestroy.nameTarget);

          localArrayList.add(localTarget);
        }
        else if ((localTDestroy.actor != null) && (localTDestroy.actor.getDiedFlag()))
        {
          ZutiSupportMethods.removeTarget(localTDestroy.nameTarget);
          localArrayList.add(localTarget);
        }
        else if (!bool)
        {
          ZutiSupportMethods.removeTarget(localTDestroy.nameTarget);
          localArrayList.add(localTarget);
        }
      }
      else if ((localTarget instanceof TDestroyGround))
      {
        localTDestroyGround = (TDestroyGround)localTarget;
        if (!localTDestroyGround.getDiedFlag())
          continue;
        localPoint3d = localTDestroyGround.pos.getAbsPoint();

        ZutiSupportMethods.removeTarget(localPoint3d.x, localPoint3d.y);
        localArrayList.add(localTarget);
      }
      else if ((localTarget instanceof TDestroyBridge))
      {
        localTDestroyBridge = (TDestroyBridge)localTarget;
        if (!localTDestroyBridge.actor.getDiedFlag())
          continue;
        ZutiSupportMethods.removeTarget(localTDestroyBridge.actor.name());
        localArrayList.add(localTarget);
      }
      else if ((localTarget instanceof TInspect))
      {
        localTInspect = (TInspect)localTarget;
        if (!localTInspect.getDiedFlag())
          continue;
        if ((localTInspect.nameTarget == null) || (localTInspect.nameTarget.trim().length() < 1))
        {
          localPoint3d = localTInspect.point;

          ZutiSupportMethods.removeTarget(localPoint3d.x, localPoint3d.y);
          localArrayList.add(localTarget);
        }
        else
        {
          ZutiSupportMethods.removeTarget(localTInspect.nameTarget);
          localArrayList.add(localTarget);
        }

      }
      else if ((localTarget instanceof TEscort))
      {
        localTEscort = (TEscort)localTarget;
        bool = localTEscort.checkActor();

        if ((localTEscort.actor != null) && ((localTEscort.actor instanceof BigshipGeneric)))
        {
          if (((BigshipGeneric)localTEscort.actor).zutiGetDying() > 0)
          {
            ZutiSupportMethods.removeTarget(localTEscort.nameTarget);
            localArrayList.add(localTarget);
          }
        }
        else if ((localTEscort.actor != null) && ((localTEscort.actor instanceof ShipGeneric)))
        {
          if (((ShipGeneric)localTEscort.actor).zutiGetDying() > 0)
          {
            ZutiSupportMethods.removeTarget(localTEscort.nameTarget);
            localArrayList.add(localTarget);
          }
        }
        else if (localTEscort.alive == -1)
        {
          localWorld.targetsGuard.zutiTargetNamesToRemove.add(localTEscort.nameTarget);

          localArrayList.add(localTarget);
        }
        else if ((localTEscort.actor != null) && (localTEscort.actor.getDiedFlag()))
        {
          ZutiSupportMethods.removeTarget(localTEscort.nameTarget);
          localArrayList.add(localTarget);
        }
        else if (!bool)
        {
          ZutiSupportMethods.removeTarget(localTEscort.nameTarget);
          localArrayList.add(localTarget);
        }
      }
      else if ((localTarget instanceof TDefenceGround))
      {
        localTDefenceGround = (TDefenceGround)localTarget;
        if (!localTDefenceGround.getDiedFlag())
          continue;
        localPoint3d = localTDefenceGround.pos.getAbsPoint();

        ZutiSupportMethods.removeTarget(localPoint3d.x, localPoint3d.y);
        localArrayList.add(localTarget);
      }
      else {
        if (!(localTarget instanceof TDefenceBridge))
          continue;
        localTDefenceBridge = (TDefenceBridge)localTarget;
        if (!localTDefenceBridge.actor.getDiedFlag())
          continue;
        ZutiSupportMethods.removeTarget(localTDefenceBridge.actor.name());
        localArrayList.add(localTarget);
      }

    }

    i = localArrayList.size();
    for (j = 0; j < i; j++)
    {
      localList.remove((Target)localArrayList.get(j));
    }
  }

  public static void staticActorDied(Actor paramActor)
  {
    World localWorld = World.cur();
    if ((localWorld == null) || (localWorld.targetsGuard == null)) {
      return;
    }
    List localList = localWorld.targetsGuard.zutiGetTargets();

    int i = localList.size();

    Target localTarget = null;
    Point3d localPoint3d = null;
    TDestroyGround localTDestroyGround = null;
    TDefenceGround localTDefenceGround = null;

    for (int j = 0; j < i; j++)
    {
      localTarget = (Target)localList.get(j);
      if ((localTarget instanceof TDestroyGround))
      {
        localTDestroyGround = (TDestroyGround)localTarget;
        if (!checkIfActorPartOfTDestroyGround(paramActor, localTDestroyGround))
          continue;
        localPoint3d = localTDestroyGround.pos.getAbsPoint();

        ZutiSupportMethods.removeTarget(localPoint3d.x, localPoint3d.y);

        localWorld.targetsGuard.zutiTargetPosToRemove.add(localTarget);
      }
      else {
        if (!(localTarget instanceof TDefenceGround))
          continue;
        localTDefenceGround = (TDefenceGround)localTarget;
        if (!checkIfActorPartOfTDefenceGround(paramActor, localTDefenceGround))
          continue;
        localPoint3d = localTDefenceGround.pos.getAbsPoint();

        ZutiSupportMethods.removeTarget(localPoint3d.x, localPoint3d.y);

        localWorld.targetsGuard.zutiTargetPosToRemove.add(localTarget);
      }
    }
  }

  public static Point3d getNearestTarget(Point3d paramPoint3d, boolean paramBoolean)
  {
    World localWorld = World.cur();
    if ((localWorld == null) || (localWorld.targetsGuard == null)) {
      return null;
    }
    List localList = localWorld.targetsGuard.zutiGetTargets();
    Object localObject = null;

    double d1 = -1.0D;
    int i = localList.size();

    Target localTarget = null;
    Point3d localPoint3d = null;
    TDestroyBridge localTDestroyBridge = null;
    TDefenceBridge localTDefenceBridge = null;
    TDestroy localTDestroy = null;
    TEscort localTEscort = null;

    for (int j = 0; j < i; j++)
    {
      localTarget = (Target)localList.get(j);
      if (!localTarget.isAlive())
        continue;
      double d2;
      if ((paramBoolean) && ((localTarget instanceof TDestroyGround)))
      {
        localPoint3d = localTarget.pos.getAbsPoint();
        d2 = Math.pow(paramPoint3d.x - localPoint3d.x, 2.0D) + Math.pow(paramPoint3d.y - localPoint3d.y, 2.0D);

        if ((d2 < d1) || (d1 < 0.0D))
        {
          d1 = d2;
          localObject = localPoint3d;
        }
      }
      if ((paramBoolean) && ((localTarget instanceof TDestroyBridge)))
      {
        localTDestroyBridge = (TDestroyBridge)localTarget;
        if (localTDestroyBridge.actor == null) {
          continue;
        }
        localPoint3d = localTDestroyBridge.actor.pos.getAbsPoint();
        d2 = Math.pow(paramPoint3d.x - localPoint3d.x, 2.0D) + Math.pow(paramPoint3d.y - localPoint3d.y, 2.0D);

        if ((d2 < d1) || (d1 < 0.0D))
        {
          d1 = d2;
          localObject = localPoint3d;
        }
      }
      if ((paramBoolean) && ((localTarget instanceof TDestroy)))
      {
        localTDestroy = (TDestroy)localTarget;
        if (localTDestroy.actor == null) {
          continue;
        }
        if ((localTDestroy.actor instanceof Wing))
        {
          localPoint3d = getPointFromWing((Wing)localTDestroy.actor);
          if (localPoint3d == null)
          {
            continue;
          }
          d2 = Math.pow(paramPoint3d.x - localPoint3d.x, 2.0D) + Math.pow(paramPoint3d.y - localPoint3d.y, 2.0D);

          if ((d2 < d1) || (d1 < 0.0D))
          {
            d1 = d2;
            localObject = localPoint3d;
          }

        }
        else
        {
          localPoint3d = localTDestroy.actor.pos.getAbsPoint();
          d2 = Math.pow(paramPoint3d.x - localPoint3d.x, 2.0D) + Math.pow(paramPoint3d.y - localPoint3d.y, 2.0D);

          if ((d2 < d1) || (d1 < 0.0D))
          {
            d1 = d2;
            localObject = localPoint3d;
          }
        }
      }

      if ((!paramBoolean) && ((localTarget instanceof TDefenceGround)))
      {
        localPoint3d = localTarget.pos.getAbsPoint();
        d2 = Math.pow(paramPoint3d.x - localPoint3d.x, 2.0D) + Math.pow(paramPoint3d.y - localPoint3d.y, 2.0D);

        if ((d2 < d1) || (d1 < 0.0D))
        {
          d1 = d2;
          localObject = localPoint3d;
        }
      }
      if ((!paramBoolean) && ((localTarget instanceof TDefenceBridge)))
      {
        localTDefenceBridge = (TDefenceBridge)localTarget;
        if (localTDefenceBridge.actor == null) {
          continue;
        }
        localPoint3d = localTDestroy.actor.pos.getAbsPoint();
        d2 = Math.pow(paramPoint3d.x - localPoint3d.x, 2.0D) + Math.pow(paramPoint3d.y - localPoint3d.y, 2.0D);

        if ((d2 < d1) || (d1 < 0.0D))
        {
          d1 = d2;
          localObject = localPoint3d;
        }
      }
      if ((paramBoolean) || (!(localTarget instanceof TEscort)))
        continue;
      localTEscort = (TEscort)localTarget;
      localTEscort.checkActor();
      if (localTEscort.actor == null) {
        continue;
      }
      if ((localTEscort.actor instanceof Wing))
      {
        localPoint3d = getPointFromWing((Wing)localTDestroy.actor);
        if (localPoint3d == null)
        {
          continue;
        }
        d2 = Math.pow(paramPoint3d.x - localPoint3d.x, 2.0D) + Math.pow(paramPoint3d.y - localPoint3d.y, 2.0D);

        if ((d2 < d1) || (d1 < 0.0D))
        {
          d1 = d2;
          localObject = localPoint3d;
        }

      }
      else
      {
        localPoint3d = localTDestroy.actor.pos.getAbsPoint();
        d2 = Math.pow(paramPoint3d.x - localPoint3d.x, 2.0D) + Math.pow(paramPoint3d.y - localPoint3d.y, 2.0D);

        if ((d2 >= d1) && (d1 >= 0.0D))
          continue;
        d1 = d2;
        localObject = localPoint3d;
      }

    }

    return localObject;
  }

  public static Point3d getPointFromWing(Wing paramWing)
  {
    Point3d localPoint3d = null;
    int i = paramWing.airc.length;
    for (int j = 0; j < i; j++)
    {
      if ((paramWing.airc[j] == null) || (paramWing.airc[j].getDiedFlag()))
        continue;
      localPoint3d = paramWing.airc[j].pos.getAbsPoint();
      break;
    }

    return localPoint3d;
  }

  public static boolean isOverTarget(double paramDouble1, double paramDouble2)
  {
    World localWorld = World.cur();
    if ((localWorld == null) || (localWorld.targetsGuard == null)) {
      return false;
    }
    Target localTarget = null;
    TDestroyGround localTDestroyGround = null;
    TDefenceGround localTDefenceGround = null;
    List localList = localWorld.targetsGuard.zutiGetTargets();
    int i = localList.size();
    for (int j = 0; j < i; j++)
    {
      localTarget = (Target)localList.get(j);

      if ((localTarget instanceof TDestroyGround))
      {
        localTDestroyGround = (TDestroyGround)localTarget;
        if ((!localTDestroyGround.getDiedFlag()) && (localTDestroyGround.zutiIsOverTarged(paramDouble1, paramDouble2)))
          return true;
      } else {
        if (!(localTarget instanceof TDefenceGround))
          continue;
        localTDefenceGround = (TDefenceGround)localTarget;
        if ((!localTDefenceGround.getDiedFlag()) && (localTDefenceGround.zutiIsOverTarged(paramDouble1, paramDouble2))) {
          return true;
        }
      }
    }
    return false;
  }
}