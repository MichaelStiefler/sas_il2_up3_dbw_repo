package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.NearestTargets;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.List;

public class War
{
  public static final int TICK_DIV4 = 4;
  public static final int TICK_DIV8 = 8;
  public static final int TICK_DIV16 = 16;
  public static final int TICK_DIV32 = 32;
  public static final int ARMY_NUM = 2;
  public static AirGroupList[] Groups = new AirGroupList[2];

  private static int curArmy = 0;
  private static int curGroup = 0;

  private static Vector3d tmpV = new Vector3d();
  private static Vector3d Ve = new Vector3d();
  private static Vector3d Vtarg = new Vector3d();

  public static War cur()
  {
    return World.cur().war;
  }
  public boolean isActive() {
    if (!Mission.isPlaying()) return false;
    if (NetMissionTrack.isPlaying()) return false;
    if (Mission.isSingle()) return true;

    return (Mission.isServer()) && ((Mission.isCoop()) || (Mission.isDogfight()));
  }

  public void onActorDied(Actor paramActor1, Actor paramActor2)
  {
    if (!isActive()) return;
    if (((paramActor1 instanceof Aircraft)) && 
      ((((Aircraft)paramActor1).FM instanceof Maneuver))) {
      Maneuver localManeuver = (Maneuver)((Aircraft)paramActor1).FM;
      if (localManeuver.Group != null) {
        localManeuver.Group.delAircraft((Aircraft)paramActor1);
        localManeuver.Group = null;
      }
    }
  }

  public void missionLoaded()
  {
  }

  public void resetGameCreate()
  {
    curArmy = 0;
    curGroup = 0;
  }

  public void resetGameClear()
  {
    for (int i = 0; i < 2; i++)
      while (Groups[i] != null) {
        Groups[i].G.release();
        AirGroupList.delAirGroup(Groups, i, Groups[i].G);
      }
  }

  public void interpolateTick()
  {
    if (!isActive()) return; try
    {
      if (Time.tickCounter() % 4 == 0) {
        checkCollisionForAircraft();
        if (Time.tickCounter() % 32 == 0) {
          checkGroupsContact();
          if (Time.tickCounter() % 64 == 0) {
            delEmptyGroups();
          }
        }
        upgradeGroups();
      }
      formationUpdate();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void upgradeGroups()
  {
    int i = AirGroupList.length(Groups[curArmy]);
    if (i > curGroup) { AirGroupList.getGroup(Groups[curArmy], curGroup).update();
    } else {
      curArmy += 1;
      if (curArmy > 1) curArmy = 0;
      curGroup = 0;
      return;
    }
    curGroup += 1;
  }

  private void formationUpdate() {
    for (int i = 0; i < 2; i++)
      if (Groups[i] != null) {
        int j = AirGroupList.length(Groups[i]);
        for (int k = 0; k < j; k++)
          AirGroupList.getGroup(Groups[i], k).formationUpdate();
      }
  }

  private void checkGroupsContact()
  {
    int i = AirGroupList.length(Groups[0]);
    int j = AirGroupList.length(Groups[1]);
    for (int k = 0; k < i; k++) {
      AirGroup localAirGroup1 = AirGroupList.getGroup(Groups[0], k);
      if ((localAirGroup1 == null) || (localAirGroup1.Pos == null))
        continue;
      for (int m = 0; m < j; m++) {
        AirGroup localAirGroup2 = AirGroupList.getGroup(Groups[1], m);
        if ((localAirGroup2 == null) || (localAirGroup2.Pos == null))
          continue;
        tmpV.sub(localAirGroup1.Pos, localAirGroup2.Pos);
        if ((tmpV.lengthSquared() < 400000000.0D) && (localAirGroup1.groupsInContact(localAirGroup2))) {
          if (!AirGroupList.groupInList(localAirGroup1.enemies[0], localAirGroup2)) {
            AirGroupList.addAirGroup(localAirGroup1.enemies, 0, localAirGroup2);
            if ((localAirGroup1.airc[0] != null) && (localAirGroup2.airc[0] != null)) Voice.speakEnemyDetected(localAirGroup1.airc[0], localAirGroup2.airc[0]);
            localAirGroup1.setEnemyFighters();
          }
          if (!AirGroupList.groupInList(localAirGroup2.enemies[0], localAirGroup1)) {
            AirGroupList.addAirGroup(localAirGroup2.enemies, 0, localAirGroup1);
            if ((localAirGroup1.airc[0] != null) && (localAirGroup2.airc[0] != null)) Voice.speakEnemyDetected(localAirGroup2.airc[0], localAirGroup1.airc[0]);
            localAirGroup2.setEnemyFighters();
          }
        } else {
          if (AirGroupList.groupInList(localAirGroup1.enemies[0], localAirGroup2)) {
            AirGroupList.delAirGroup(localAirGroup1.enemies, 0, localAirGroup2);
            localAirGroup1.setEnemyFighters();
          }
          if (AirGroupList.groupInList(localAirGroup2.enemies[0], localAirGroup1)) {
            AirGroupList.delAirGroup(localAirGroup2.enemies, 0, localAirGroup1);
            localAirGroup2.setEnemyFighters();
          }
        }
      }
    }
  }

  private void delEmptyGroups()
  {
    int i = AirGroupList.length(Groups[0]);
    int j = AirGroupList.length(Groups[1]);
    AirGroup localAirGroup;
    for (int k = 0; k < i; k++) {
      localAirGroup = AirGroupList.getGroup(Groups[0], k);
      if ((localAirGroup != null) && (localAirGroup.nOfAirc == 0)) {
        localAirGroup.release();
        AirGroupList.delAirGroup(Groups, 0, localAirGroup);
      }
    }
    for (k = 0; k < j; k++) {
      localAirGroup = AirGroupList.getGroup(Groups[1], k);
      if ((localAirGroup != null) && (localAirGroup.nOfAirc == 0)) {
        localAirGroup.release();
        AirGroupList.delAirGroup(Groups, 1, localAirGroup);
      }
    }
  }

  private void checkCollisionForAircraft()
  {
    List localList = Engine.targets();
    int k = localList.size();

    for (int i = 0; i < k; i++) {
      Actor localActor1 = (Actor)localList.get(i);
      if ((localActor1 instanceof Aircraft)) {
        FlightModel localFlightModel1 = ((Aircraft)localActor1).FM;
        for (int j = i + 1; j < k; j++) {
          Actor localActor2 = (Actor)localList.get(j);
          if ((i != j) && ((localActor2 instanceof Aircraft))) {
            FlightModel localFlightModel2 = ((Aircraft)localActor2).FM;
            if (((localFlightModel1 instanceof Pilot)) && ((localFlightModel2 instanceof Pilot))) {
              float f1 = (float)localFlightModel1.Loc.distanceSquared(localFlightModel2.Loc);
              if (f1 <= 10000000.0F) {
                if (localFlightModel1.actor.getArmy() != localFlightModel2.actor.getArmy()) {
                  if ((localFlightModel1 instanceof RealFlightModel)) testAsDanger(localFlightModel1, localFlightModel2);
                  if ((localFlightModel2 instanceof RealFlightModel)) testAsDanger(localFlightModel2, localFlightModel1);
                }
                Ve.sub(localFlightModel1.Loc, localFlightModel2.Loc);
                float f2 = (float)Ve.length();
                Ve.normalize();
                if (localFlightModel1.actor.getArmy() == localFlightModel2.actor.getArmy()) {
                  tmpV.set(Ve);
                  localFlightModel2.Or.transformInv(tmpV);
                  if ((tmpV.x > 0.0D) && 
                    (tmpV.y > -0.1D) && (tmpV.y < 0.1D) && (tmpV.z > -0.1D) && (tmpV.z < 0.1D)) {
                    ((Maneuver)localFlightModel2).setShotAtFriend(f2);
                  }

                  tmpV.set(Ve);
                  tmpV.scale(-1.0D);
                  localFlightModel1.Or.transformInv(tmpV);
                  if ((tmpV.x > 0.0D) && 
                    (tmpV.y > -0.1D) && (tmpV.y < 0.1D) && (tmpV.z > -0.1D) && (tmpV.z < 0.1D)) {
                    ((Maneuver)localFlightModel1).setShotAtFriend(f2);
                  }
                }

                if (f1 <= 20000.0F) {
                  float f3 = (localFlightModel1.actor.collisionR() + localFlightModel2.actor.collisionR()) * 1.5F;
                  f2 -= f3;
                  Vtarg.sub(localFlightModel2.Vwld, localFlightModel1.Vwld);
                  Vtarg.scale(1.5D);
                  float f4 = (float)Vtarg.length();
                  if (f4 >= f2) {
                    Vtarg.normalize();
                    Vtarg.scale(f2);
                    Ve.scale(Vtarg.dot(Ve));
                    Vtarg.sub(Ve);
                    if ((Vtarg.length() < f3) || (f2 < 0.0F)) {
                      if ((((Aircraft)localActor1).FM instanceof Pilot)) ((Maneuver)((Aircraft)localActor1).FM).setStrikeEmer(localFlightModel2);
                      if (!(((Aircraft)localActor2).FM instanceof Pilot)) continue; ((Maneuver)((Aircraft)localActor2).FM).setStrikeEmer(localFlightModel1);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public static void testAsDanger(FlightModel paramFlightModel1, FlightModel paramFlightModel2) {
    if ((paramFlightModel1.actor instanceof TypeTransport)) return;
    Ve.sub(paramFlightModel2.Loc, paramFlightModel1.Loc);
    paramFlightModel1.Or.transformInv(Ve);
    if (Ve.x > 0.0D) {
      float f = (float)Ve.length();
      Ve.normalize();
      ((Maneuver)paramFlightModel2).incDangerAggressiveness(4, (float)Ve.x, f, paramFlightModel1);
    }
  }

  public static Aircraft getNearestFriend(Aircraft paramAircraft)
  {
    return getNearestFriend(paramAircraft, 10000.0F);
  }

  public static Aircraft getNearestFriend(Aircraft paramAircraft, float paramFloat) {
    Point3d localPoint3d1 = paramAircraft.pos.getAbsPoint();
    double d1 = paramFloat * paramFloat;
    int i = paramAircraft.getArmy();
    Aircraft localAircraft = null;
    List localList = Engine.targets();
    int j = localList.size();
    for (int k = 0; k < j; k++) {
      Actor localActor = (Actor)localList.get(k);
      if ((!(localActor instanceof Aircraft)) || 
        (localActor == paramAircraft) || (localActor.getArmy() != i))
        continue;
      Point3d localPoint3d2 = localActor.pos.getAbsPoint();
      double d2 = (localPoint3d1.x - localPoint3d2.x) * (localPoint3d1.x - localPoint3d2.x) + (localPoint3d1.y - localPoint3d2.y) * (localPoint3d1.y - localPoint3d2.y) + (localPoint3d1.z - localPoint3d2.z) * (localPoint3d1.z - localPoint3d2.z);

      if (d2 < d1) {
        localAircraft = (Aircraft)localActor;
        d1 = d2;
      }

    }

    return localAircraft;
  }

  public static Aircraft getNearestFriendAtPoint(Point3d paramPoint3d, Aircraft paramAircraft, float paramFloat) {
    double d1 = paramFloat * paramFloat;
    int i = paramAircraft.getArmy();
    Aircraft localAircraft = null;
    List localList = Engine.targets();
    int j = localList.size();
    for (int k = 0; k < j; k++) {
      Actor localActor = (Actor)localList.get(k);
      if ((!(localActor instanceof Aircraft)) || 
        (localActor.getArmy() != i)) continue;
      Point3d localPoint3d = localActor.pos.getAbsPoint();
      double d2 = (paramPoint3d.x - localPoint3d.x) * (paramPoint3d.x - localPoint3d.x) + (paramPoint3d.y - localPoint3d.y) * (paramPoint3d.y - localPoint3d.y) + (paramPoint3d.z - localPoint3d.z) * (paramPoint3d.z - localPoint3d.z);

      if (d2 < d1) {
        localAircraft = (Aircraft)localActor;
        d1 = d2;
      }

    }

    return localAircraft;
  }

  public static Aircraft getNearestFriendlyFighter(Aircraft paramAircraft, float paramFloat)
  {
    double d1 = paramFloat * paramFloat;
    Point3d localPoint3d1 = paramAircraft.pos.getAbsPoint();
    int i = paramAircraft.getArmy();
    Object localObject = null;
    List localList = Engine.targets();
    int j = localList.size();
    for (int k = 0; k < j; k++) {
      Actor localActor = (Actor)localList.get(k);
      if ((localActor instanceof Aircraft)) {
        Aircraft localAircraft = (Aircraft)localActor;
        if ((localAircraft == paramAircraft) || (localAircraft.getArmy() != i) || (localAircraft.getWing() == paramAircraft.getWing()) || (!(localAircraft instanceof TypeFighter)))
        {
          continue;
        }
        Point3d localPoint3d2 = localAircraft.pos.getAbsPoint();
        double d2 = (localPoint3d1.x - localPoint3d2.x) * (localPoint3d1.x - localPoint3d2.x) + (localPoint3d1.y - localPoint3d2.y) * (localPoint3d1.y - localPoint3d2.y) + (localPoint3d1.z - localPoint3d2.z) * (localPoint3d1.z - localPoint3d2.z);

        if (d2 < d1) {
          localObject = localAircraft;
          d1 = d2;
        }
      }
    }

    return localObject;
  }

  public static Aircraft getNearestEnemy(Aircraft paramAircraft, float paramFloat)
  {
    double d1 = paramFloat * paramFloat;
    Point3d localPoint3d1 = paramAircraft.pos.getAbsPoint();
    int i = paramAircraft.getArmy();
    Aircraft localAircraft = null;
    List localList = Engine.targets();
    int j = localList.size();
    for (int k = 0; k < j; k++) {
      Actor localActor = (Actor)localList.get(k);
      if ((!(localActor instanceof Aircraft)) || 
        (localActor.getArmy() == i)) continue;
      Point3d localPoint3d2 = localActor.pos.getAbsPoint();
      double d2 = (localPoint3d1.x - localPoint3d2.x) * (localPoint3d1.x - localPoint3d2.x) + (localPoint3d1.y - localPoint3d2.y) * (localPoint3d1.y - localPoint3d2.y) + (localPoint3d1.z - localPoint3d2.z) * (localPoint3d1.z - localPoint3d2.z);

      if (d2 < d1) {
        localAircraft = (Aircraft)localActor;
        d1 = d2;
      }

    }

    return localAircraft;
  }

  public static Actor GetNearestEnemy(Actor paramActor, int paramInt, float paramFloat)
  {
    return NearestTargets.getEnemy(0, paramInt, paramActor.pos.getAbsPoint(), paramFloat, paramActor.getArmy());
  }

  public static Actor GetNearestEnemy(Actor paramActor, int paramInt1, float paramFloat, int paramInt2)
  {
    return NearestTargets.getEnemy(paramInt2, paramInt1, paramActor.pos.getAbsPoint(), paramFloat, paramActor.getArmy());
  }

  public static Actor GetNearestEnemy(Actor paramActor, int paramInt, float paramFloat, Point3d paramPoint3d)
  {
    return NearestTargets.getEnemy(0, paramInt, paramPoint3d, paramFloat, paramActor.getArmy());
  }

  public static Actor GetNearestEnemy(Actor paramActor, int paramInt, Point3d paramPoint3d, float paramFloat)
  {
    return NearestTargets.getEnemy(paramInt, 16, paramPoint3d, paramFloat, paramActor.getArmy());
  }

  public static Actor GetNearestEnemy(Actor paramActor, Point3d paramPoint3d, float paramFloat)
  {
    return NearestTargets.getEnemy(0, 16, paramPoint3d, paramFloat, paramActor.getArmy());
  }

  public static Actor GetNearestFromChief(Actor paramActor1, Actor paramActor2)
  {
    if (!Actor.isAlive(paramActor2)) return null;
    Object localObject = null;
    if (((paramActor2 instanceof Chief)) || ((paramActor2 instanceof Bridge))) {
      int i = paramActor2.getOwnerAttachedCount();
      if (i < 1) return null;
      localObject = (Actor)paramActor2.getOwnerAttached(0);
      double d1 = paramActor1.pos.getAbsPoint().distance(((Actor)localObject).pos.getAbsPoint());
      for (int j = 1; j < i; j++) {
        Actor localActor = (Actor)paramActor2.getOwnerAttached(j);
        double d2 = paramActor1.pos.getAbsPoint().distance(localActor.pos.getAbsPoint());
        if (d2 < d1) {
          d2 = d1;
          localObject = localActor;
        }
      }
    }
    return (Actor)localObject;
  }

  public static Actor GetRandomFromChief(Actor paramActor1, Actor paramActor2)
  {
    if (!Actor.isAlive(paramActor2)) return null;
    if (((paramActor2 instanceof Chief)) || ((paramActor2 instanceof Bridge))) {
      int i = paramActor2.getOwnerAttachedCount();
      if (i < 1) return null;
      Actor localActor;
      for (int j = 0; j < i; j++) {
        localActor = (Actor)paramActor2.getOwnerAttached(World.Rnd().nextInt(0, i - 1));
        if ((Actor.isValid(localActor)) && (localActor.isAlive())) return localActor;
      }
      for (j = 0; j < i; j++) {
        localActor = (Actor)paramActor2.getOwnerAttached(j);
        if ((Actor.isValid(localActor)) && (localActor.isAlive())) return localActor;
      }
    }
    return paramActor2;
  }

  public static Aircraft GetNearestEnemyAircraft(Actor paramActor, float paramFloat, int paramInt)
  {
    Actor localActor = GetNearestEnemy(paramActor, -1, paramFloat, paramInt);
    if ((localActor != null) && ((localActor instanceof Aircraft))) return (Aircraft)localActor;
    localActor = GetNearestEnemy(paramActor, -1, paramFloat, 9);
    if ((localActor != null) && ((localActor instanceof Aircraft)))
      return (Aircraft)localActor;
    return null;
  }
}