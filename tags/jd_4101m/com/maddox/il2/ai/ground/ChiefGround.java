package com.maddox.il2.ai.ground;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector2f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.MsgDreamListener;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChiefGround extends Chief
  implements MsgDreamListener
{
  private static final int PEACE = 0;
  private static final int FIGHT = 1;
  private static final int SHIFT = 2;
  private static final int BRAKE = 3;
  private int curState;
  private int stateCountdown;
  private boolean shift_ToRightSide;
  private boolean shift_SwitchToBrakeWhenDone;
  private ArrayList unitsPacked;
  private RoadPath road;
  private int minGrabSeg;
  private int maxGrabSeg;
  private int chiefSeg;
  private double chiefAlong;
  private int minSeg;
  private int maxSeg;
  private float groupSpeed;
  private float maxSpace;
  private boolean withPreys;
  private long waitTime;
  private int posCountdown;
  private Vector3d estim_speed;
  private Point3d tmpp = new Point3d();
  private int[] curForm;

  private static final void ERR_NO_UNITS(String paramString)
  {
    String str = "INTERNAL ERROR IN ChiefGround." + paramString + "(): No units";
    System.out.println(str);
    throw new ActorException(str);
  }

  private static final void ERR(String paramString) {
    String str = "INTERNAL ERROR IN ChiefGround: " + paramString;
    System.out.println(str);
    throw new ActorException(str);
  }

  private static final void ConstructorFailure() {
    throw new ActorException();
  }

  public boolean isPacked() {
    return (this.unitsPacked == null) || (this.unitsPacked.size() > 0);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  private void SetPosition(Point3d paramPoint3d, float paramFloat) {
    this.pos.getAbs(this.tmpp);
    this.pos.setAbs(paramPoint3d);
    this.estim_speed.sub(paramPoint3d, this.tmpp);
    if (paramFloat <= 1.0E-004F)
      this.estim_speed.set(0.0D, 0.0D, 0.0D);
    else
      this.estim_speed.scale(1.0D / paramFloat);
  }

  public double getSpeed(Vector3d paramVector3d)
  {
    double d = this.estim_speed.length();
    if (paramVector3d == null)
    {
      return d;
    }
    paramVector3d.set(this.estim_speed);
    if (d <= 0.0001D) {
      paramVector3d.set(0.0D, 0.0D, 0.0001D);
    }

    return d;
  }

  public ChiefGround(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
  {
    try
    {
      this.road = new RoadPath(paramSectFile2, paramString3);

      this.road.RegisterTravellerToBridges(this);

      setName(paramString1);
      setArmy(paramInt);
      this.chiefSeg = 0;
      this.chiefAlong = 0.0D;
      this.minSeg = -1;
      this.maxSeg = -1;
      this.waitTime = 0L;
      this.curForm = null;

      this.minGrabSeg = (this.maxGrabSeg = -1);

      this.pos = new ActorPosMove(this);
      this.pos.setAbs(this.road.get(0).start);
      this.pos.reset();
      this.posCountdown = 0;
      this.estim_speed = new Vector3d(0.0D, 0.0D, 0.0D);

      int i = paramSectFile1.sectionIndex(paramString2);
      int j = paramSectFile1.vars(i);
      if (j <= 0)
        throw new ActorException("ChiefGround: Missing units");
      this.unitsPacked = new ArrayList();
      for (int k = 0; k < j; k++) {
        String str = paramSectFile1.var(i, k);

        Object localObject = Spawn.get(str);
        if (localObject == null) {
          throw new ActorException("ChiefGround: Unknown type of object (" + str + ")");
        }

        int m = Finger.Int(str);
        int n = 0;
        this.unitsPacked.add(new UnitInPackedForm(k, m, n));
      }

      this.withPreys = false;

      unpackUnits();

      recomputeAveragePosition();

      if (!interpEnd("move"))
        interpPut(new Move(), "move", Time.current(), null);
    }
    catch (Exception localException) {
      System.out.println("ChiefGround creation failure:");
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      ConstructorFailure();
    }
  }

  public int getCodeOfBridgeSegment(UnitInterface paramUnitInterface)
  {
    int i = paramUnitInterface.GetUnitData().segmentIdx;
    return this.road.getCodeOfBridgeSegment(i);
  }

  public void BridgeSegmentDestroyed(int paramInt1, int paramInt2, Actor paramActor)
  {
    boolean bool = this.road.MarkDestroyedSegments(paramInt1, paramInt2);
    if (!bool) {
      return;
    }

    if (this.unitsPacked.size() > 0)
    {
      return;
    }

    Object[] arrayOfObject = getOwnerAttached();
    if (arrayOfObject.length <= 0) ERR_NO_UNITS("BridgeSegmentDestroyed");

    for (int i = 0; i < arrayOfObject.length; i++) {
      int j = ((UnitInterface)arrayOfObject[i]).GetUnitData().segmentIdx;
      if (!this.road.segIsWrongOrDamaged(j))
        continue;
      ((UnitInterface)arrayOfObject[i]).absoluteDeath(paramActor);
    }
  }

  private void recomputeAveragePosition()
  {
    if (this.unitsPacked.size() > 0) ERR("average position when PACKED");

    Object[] arrayOfObject = getOwnerAttached();

    if (arrayOfObject.length <= 0) ERR_NO_UNITS("recomputeAveragePosition");

    int i = arrayOfObject.length;
    int j = 10000;
    int k = -10000;
    for (int m = 0; m < i; m++)
    {
      int n = ((UnitInterface)arrayOfObject[m]).GetUnitData().segmentIdx;
      if (n < j) j = n;
      if (n <= k) continue; k = n;
    }

    Point3d localPoint3d = new Point3d(((Actor)arrayOfObject[0]).pos.getAbsPoint());
    double d = World.land().HQ(localPoint3d.x, localPoint3d.y);
    if (localPoint3d.z < d) {
      localPoint3d.z = d;
    }

    SetPosition(localPoint3d, 1.05F);

    this.posCountdown = SecsToTicks(World.Rnd().nextFloat(0.9F, 1.2F));

    this.road.unlockBridges(this, this.minGrabSeg, this.maxGrabSeg);
    this.minGrabSeg = j;
    this.maxGrabSeg = k;
    this.road.lockBridges(this, this.minGrabSeg, this.maxGrabSeg);
  }

  private void computePositionForPacked()
  {
    if (this.unitsPacked.size() > 0) ERR("advanced position when PACKED");

    Point3d localPoint3d = this.pos.getAbsPoint();
    double d1 = 999999.90000000002D;
    this.chiefSeg = this.minSeg;
    for (int i = this.minSeg; i <= this.maxSeg; i++) {
      double d2 = this.road.get(i).computePosAlong(localPoint3d);
      double d3 = this.road.get(i).computePosSide(localPoint3d);
      double d4 = d2 * d2 + d3 * d3;
      if (d1 >= d4) {
        d1 = d4;
        this.chiefSeg = i;
      }

    }

    this.chiefAlong = this.road.get(this.chiefSeg).computePosAlong_Fit(localPoint3d);
    SetPosition(this.road.get(this.chiefSeg).computePos_Fit(this.chiefAlong, 0.0D, 0.0F), 0.0F);

    this.posCountdown = SecsToTicks(World.Rnd().nextFloat(0.9F, 1.2F));

    this.road.unlockBridges(this, this.minGrabSeg, this.maxGrabSeg);
    this.minGrabSeg = (this.maxGrabSeg = this.chiefSeg);
    this.road.lockBridges(this, this.minGrabSeg, this.maxGrabSeg);
  }

  private void recomputeChiefWaitTime(int paramInt)
  {
    long l1 = this.road.getMaxWaitTime(paramInt, paramInt);
    long l2 = Time.tick();
    if ((l1 > l2) && (l1 > this.waitTime))
      this.waitTime = l1;
  }

  private void recomputeMinMaxSegments()
  {
    if (this.unitsPacked.size() > 0) ERR("min/max seg when PACKED");
    Object[] arrayOfObject = getOwnerAttached();
    if (arrayOfObject.length <= 0) ERR_NO_UNITS("recomputeMinMaxSegments");

    int i = arrayOfObject.length;
    int j = 10000;
    int k = -10000;
    for (int m = 0; m < i; m++) {
      int n = ((UnitInterface)arrayOfObject[m]).GetUnitData().segmentIdx;
      if (n < j) j = n;
      if (n <= k) continue; k = n;
    }

    this.road.unlockBridges(this, this.minGrabSeg, this.maxGrabSeg);
    this.minGrabSeg = j;
    this.maxGrabSeg = k;
    this.road.lockBridges(this, this.minGrabSeg, this.maxGrabSeg);

    if ((j == this.minSeg) && (k == this.maxSeg)) {
      return;
    }

    long l1 = this.road.getMaxWaitTime(Math.min(j, this.minSeg), k);
    long l2 = Time.tick();
    if ((l1 > l2) && (l1 > this.waitTime)) {
      this.waitTime = l1;
    }

    this.minSeg = j;
    this.maxSeg = k;
  }

  private void recomputeUnitsProperties_Packed()
  {
    int i = this.unitsPacked.size();
    if (i <= 0) {
      recomputeUnitsProperties();
      return;
    }

    this.groupSpeed = 100000.0F;
    this.maxSpace = -1.0F;
    this.weaponsMask = 0;
    this.hitbyMask = 0;

    for (int j = 0; j < i; j++) {
      UnitInPackedForm localUnitInPackedForm = (UnitInPackedForm)this.unitsPacked.get(j);

      float f = localUnitInPackedForm.SPEED_AVERAGE;
      if (f < this.groupSpeed) this.groupSpeed = f;

      f = localUnitInPackedForm.BEST_SPACE;
      if (f > this.maxSpace) this.maxSpace = f;

      this.weaponsMask |= localUnitInPackedForm.WEAPONS_MASK;
      this.hitbyMask |= localUnitInPackedForm.HITBY_MASK;
    }

    if ((this.groupSpeed < 0.001F) || (this.groupSpeed > 10000.0F)) {
      ERR("group speed is too small");
    }
    if (this.maxSpace <= 0.01F)
      ERR("maxSpace is too small");
  }

  public void recomputeUnitsProperties()
  {
    if (this.unitsPacked.size() > 0) {
      recomputeUnitsProperties_Packed();
      return;
    }
    Object[] arrayOfObject = getOwnerAttached();
    if (arrayOfObject.length <= 0) ERR_NO_UNITS("recomputeUnitsProperties");

    int i = arrayOfObject.length;
    this.groupSpeed = 10000.0F;
    this.maxSpace = -1.0F;
    this.weaponsMask = 0;
    this.hitbyMask = 0;
    this.withPreys = false;
    for (int j = 0; j < i; j++) {
      UnitInterface localUnitInterface = (UnitInterface)arrayOfObject[j];
      float f = localUnitInterface.SpeedAverage();
      if (f < this.groupSpeed) this.groupSpeed = f;
      f = localUnitInterface.BestSpace();
      if (f > this.maxSpace) this.maxSpace = f;
      if ((localUnitInterface instanceof Predator))
        this.weaponsMask |= ((Predator)localUnitInterface).WeaponsMask();
      if ((localUnitInterface instanceof Prey)) {
        this.hitbyMask |= ((Prey)localUnitInterface).HitbyMask();
        if (!(localUnitInterface instanceof Predator)) {
          this.withPreys = true;
        }
      }
    }
    if (this.groupSpeed <= 0.001F) {
      ERR("group speed is too small");
    }
    if (this.maxSpace <= 0.01F)
      ERR("maxSpace is too small");
  }

  private float computeMaxSpace(ArrayList paramArrayList, int paramInt1, int paramInt2)
  {
    float f1 = -1.0F;
    while (paramInt2-- > 0) {
      float f2 = ((UnitInterface)paramArrayList.get(paramInt1++)).BestSpace();
      if (f2 > f1) f1 = f2;
    }
    if (f1 <= 0.01F) {
      ERR("maxSpace is too small");
    }
    return f1;
  }

  private float computeMaxSpace(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
    float f1 = -1.0F;
    while (paramInt2-- > 0) {
      float f2 = ((UnitInterface)paramArrayOfObject[(paramInt1++)]).BestSpace();
      if (f2 > f1) f1 = f2;
    }
    if (f1 <= 0.01F) {
      ERR("maxSpace is too small");
    }
    return f1;
  }

  private static final int SecsToTicks(float paramFloat) {
    int i = (int)(0.5D + paramFloat / Time.tickLenFs());
    return i <= 0 ? 0 : i;
  }

  public Actor GetNearestEnemy(Point3d paramPoint3d, double paramDouble, int paramInt, float paramFloat)
  {
    if (this.unitsPacked.size() > 0) {
      return null;
    }

    if (paramFloat < 0.0F)
      NearestEnemies.set(paramInt);
    else {
      NearestEnemies.set(paramInt, -9999.9004F, paramFloat);
    }

    Actor localActor = NearestEnemies.getAFoundEnemy(paramPoint3d, paramDouble, getArmy());

    if (localActor == null) {
      return null;
    }

    if (!(localActor instanceof Prey)) {
      System.out.println("chiefg: nearest enemies: non-Prey");
      return null;
    }

    switch (this.curState) {
    case 0:
      if (this.withPreys) break;
      this.curState = 1;
      this.stateCountdown = SecsToTicks(World.Rnd().nextFloat(50.0F, 90.0F));
      reformIfNeed(false); break;
    case 1:
      this.stateCountdown = SecsToTicks(World.Rnd().nextFloat(50.0F, 90.0F));
      break;
    case 2:
    case 3:
    }

    return localActor;
  }

  public void Detach(Actor paramActor1, Actor paramActor2)
  {
    if (this.unitsPacked.size() > 0) ERR("Detaching when PACKED");
    Object[] arrayOfObject = getOwnerAttached();
    if (arrayOfObject.length <= 0) ERR_NO_UNITS("Detach");

    int i = arrayOfObject.length;

    for (int j = 0; (j < i) && 
      (paramActor1 != (Actor)arrayOfObject[j]); j++);
    if (j >= i) ERR("Detaching unknown unit");

    UnitInterface localUnitInterface1 = (UnitInterface)paramActor1;
    UnitData localUnitData1 = localUnitInterface1.GetUnitData();
    if (j < i - 1) {
      Actor localActor = (Actor)arrayOfObject[(j + 1)];
      UnitInterface localUnitInterface2 = (UnitInterface)localActor;
      UnitData localUnitData2 = localUnitInterface2.GetUnitData();
      localUnitData2.leader = localUnitData1.leader;
    }
    localUnitData1.leader = null;
    paramActor1.setOwner(null);

    if (i > 1) {
      recomputeUnitsProperties();
      recomputeMinMaxSegments();
      reformIfNeed(true);
      recomputeAveragePosition();
    }

    if (i <= 1)
    {
      this.road.UnregisterTravellerFromBridges(this);

      this.road.unlockBridges(this, this.minGrabSeg, this.maxGrabSeg);
      this.minGrabSeg = (this.maxGrabSeg = -1);

      World.onActorDied(this, paramActor2);

      destroy();
    }
  }

  public void msgDream(boolean paramBoolean)
  {
    int i = this.unitsPacked.size() > 0 ? 1 : 0;
    if (paramBoolean)
    {
      if (i == 0) {
        ERR("Wakeup out of place");
      }
      unpackUnits();
    }
    else {
      if (i != 0) {
        ERR("Sleeping out of place");
      }
      packUnits();
    }
  }

  public void packUnits()
  {
    if (this.unitsPacked.size() > 0) return;
    Object[] arrayOfObject = getOwnerAttached();
    if (arrayOfObject.length <= 0) ERR_NO_UNITS("packUnits");

    computePositionForPacked();

    int i = arrayOfObject.length;
    for (int j = 0; j < i; j++) {
      this.unitsPacked.add(((UnitInterface)arrayOfObject[j]).Pack());
      ((Actor)arrayOfObject[j]).destroy();
    }

    recomputeUnitsProperties();
  }

  public void unpackUnits()
  {
    int i = this.unitsPacked.size();
    if (i <= 0) return;
    if (getOwnerAttached().length > 0) ERR("unpack units");
    for (int j = 0; j < i; j++) {
      int k = ((UnitInPackedForm)this.unitsPacked.get(j)).CodeName();
      int m = ((UnitInPackedForm)this.unitsPacked.get(j)).CodeType();
      Object localObject = Spawn.get(m);

      int n = ((UnitInPackedForm)this.unitsPacked.get(j)).State();
      ((UnitSpawn)localObject).unitSpawn(k, n, this);
    }
    this.unitsPacked.clear();

    this.curState = 0;
    this.stateCountdown = 0;

    this.road.unlockBridges(this, this.minGrabSeg, this.maxGrabSeg);
    this.minGrabSeg = (this.maxGrabSeg = -1);

    recomputeUnitsProperties();

    formUnitsAfterUnpacking();
  }

  private static void AutoChooseFormation(int paramInt1, boolean paramBoolean, int paramInt2, float paramFloat, double paramDouble, int[] paramArrayOfInt)
  {
    if (paramInt2 <= 0) return;

    if ((paramInt1 == 2) || (paramInt1 == 3)) {
      paramArrayOfInt[0] = (paramBoolean ? 2 : 3);
      paramArrayOfInt[1] = 1;
      paramArrayOfInt[2] = paramInt2;
      return;
    }

    paramArrayOfInt[0] = 0;
    int i = (int)(paramDouble / paramFloat);
    if (i <= 0) i = 1;
    if (i > paramInt2) i = paramInt2;

    if (i <= 1) {
      paramArrayOfInt[1] = 1;
      paramArrayOfInt[2] = paramInt2;
      return;
    }
    if (paramInt1 == 0) {
      paramArrayOfInt[1] = 1;
      paramArrayOfInt[2] = paramInt2;
      return;
    }

    paramArrayOfInt[0] = 1;

    if ((i >= 3) && (paramInt2 < i - 1 + i - 1))
    {
      i /= 2;
      if (i < 2) i = 2;
    }
    paramArrayOfInt[1] = i;
    int j = 2 * i - 1;
    int k = (paramInt2 + j - 1) / j;
    paramArrayOfInt[2] = (k * 2);
    if ((paramInt2 + j - 1) % j < i - 1) paramArrayOfInt[2] -= 1;
  }

  private void formUnitsAfterUnpacking()
  {
    if (this.unitsPacked.size() > 0) return;
    Object[] arrayOfObject = getOwnerAttached();
    if (arrayOfObject.length <= 0) ERR_NO_UNITS("formUnitsAfterUnpacking");

    float f1 = (arrayOfObject.length - 1) * this.maxSpace;

    double d1 = this.road.get(this.chiefSeg).computePosAlong_Fit(this.pos.getAbsPoint());
    RoadPart localRoadPart = new RoadPart();
    this.road.FindFreeSpace(f1, this.chiefSeg, d1, localRoadPart);

    int[] arrayOfInt = new int[3];
    AutoChooseFormation(this.curState, this.shift_ToRightSide, arrayOfObject.length, this.maxSpace, this.road.ComputeMinRoadWidth(localRoadPart.begseg, localRoadPart.endseg), arrayOfInt);

    this.curForm = arrayOfInt;

    f1 = (arrayOfInt[2] - 1) * this.maxSpace;
    this.road.FindFreeSpace(f1, this.chiefSeg, d1, localRoadPart);

    double d2 = 1.0D;

    int i = localRoadPart.begseg;
    double d3 = localRoadPart.begt;

    int j = 0;
    float f2 = 0.0F;
    for (int k = 0; k < arrayOfInt[2]; k++) {
      int m = arrayOfInt[1];
      if (((k & 0x1) == 0) && (arrayOfInt[0] == 1)) m--;
      int n = m;
      if (j + n > arrayOfObject.length) n = arrayOfObject.length - j;
      float f3 = computeMaxSpace(arrayOfObject, j, n);
      float f4 = 0.0F;

      if (k > 0) {
        f4 = Math.max(f2, f3);
        double d4 = f4;
        d3 -= d4;
        while (d3 < 0.0D) {
          d4 = -d3;
          d3 = 0.0D;
          if (!this.road.segIsWrongOrDamaged(i - 1)) {
            i--;
            d3 = this.road.get(i).length2D - d4; continue;
          }

          d3 = -1.0D;
        }

        if (d3 < 0.0D)
          break;
      }
      for (int i1 = 0; i1 < n; i1++) {
        UnitData localUnitData = ((UnitInterface)arrayOfObject[j]).GetUnitData();

        localUnitData.segmentIdx = i;
        localUnitData.leaderDist = f4;
        if (j == 0) {
          localUnitData.leader = null;
        } else if (k == 0) {
          localUnitData.leader = ((Actor)arrayOfObject[0]);
          localUnitData.leaderDist = 0.0F;
        }
        else if ((arrayOfInt[0] == 0) || ((k & 0x1) == 0) || (i1 > 0)) {
          localUnitData.leader = ((Actor)arrayOfObject[(j - arrayOfInt[1])]);
        } else {
          localUnitData.leader = ((Actor)arrayOfObject[(j - arrayOfInt[1] + 1)]);
        }float f5 = arrayOfInt[0] == 0 ? f3 : this.maxSpace;
        float f6 = (arrayOfInt[1] - 1) * f5;
        if ((arrayOfInt[0] == 1) && ((k & 0x1) == 0)) f6 -= f5;
        localUnitData.sideOffset = (-f6 / 2.0F + i1 * f5);

        Actor localActor = (Actor)arrayOfObject[j];

        Point3d localPoint3d = this.road.get(i).computePos_Fit(d3, localUnitData.sideOffset, localActor.collisionR());

        localPoint3d.z += ((UnitInterface)localActor).HeightAboveLandSurface();
        localActor.pos.setAbs(localPoint3d);

        Vector3f localVector3f = new Vector3f();
        if (this.road.get(i).IsLandAligned())
          Engine.land().N(localPoint3d.x, localPoint3d.y, localVector3f);
        else
          localVector3f.set(this.road.get(i).normal);
        Orient localOrient = new Orient();
        localOrient.setYPR(this.road.get(i).yaw, 0.0F, 0.0F);
        localOrient.orient(localVector3f);
        localActor.pos.setAbs(localOrient);
        localActor.pos.reset();

        ((UnitInterface)arrayOfObject[j]).startMove();

        j++;
      }
      if (j >= arrayOfObject.length) break;
      f2 = f3;
    }

    if (j <= 0) {
      ERR("No room to place units");
    }

    for (; j < arrayOfObject.length; j++) {
      ((Actor)arrayOfObject[j]).destroy();
    }

    recomputeMinMaxSegments();
  }

  private void reformForSHIFT(Object[] paramArrayOfObject, float paramFloat, boolean paramBoolean)
  {
    if (paramArrayOfObject == null) return;

    ArrayList localArrayList = new ArrayList(paramArrayOfObject.length);
    for (int i = 0; i < paramArrayOfObject.length; i++) {
      localArrayList.add(paramArrayOfObject[i]);
    }

    Collections.sort(localArrayList, new Comparator()
    {
      public int compare(Object paramObject1, Object paramObject2)
      {
        UnitData localUnitData1 = ((UnitInterface)paramObject1).GetUnitData();
        UnitData localUnitData2 = ((UnitInterface)paramObject2).GetUnitData();
        RoadSegment localRoadSegment1 = ChiefGround.this.road.get(localUnitData1.segmentIdx);
        RoadSegment localRoadSegment2 = ChiefGround.this.road.get(localUnitData2.segmentIdx);
        double d = localRoadSegment2.length2Dallprev + localRoadSegment2.computePosAlong(((Actor)paramObject2).pos.getAbsPoint()) - localRoadSegment1.length2Dallprev - localRoadSegment1.computePosAlong(((Actor)paramObject1).pos.getAbsPoint());

        return d > 0.0D ? 1 : d == 0.0D ? 0 : -1;
      }
    });
    float f = paramBoolean ? 5000.0F : -5000.0F;
    for (int j = 0; j < localArrayList.size(); j++) {
      UnitData localUnitData = ((UnitInterface)localArrayList.get(j)).GetUnitData();
      if (j == 0) {
        localUnitData.leaderDist = 0.0F;
        localUnitData.leader = null;
      } else {
        localUnitData.leaderDist = paramFloat;
        localUnitData.leader = ((Actor)localArrayList.get(j - 1));
      }
      localUnitData.sideOffset = f;
    }

    for (j = 0; j < paramArrayOfObject.length; j++)
      ((UnitInterface)paramArrayOfObject[j]).forceReaskMove();
  }

  private void reform(Object[] paramArrayOfObject, int[] paramArrayOfInt)
  {
    if (paramArrayOfObject == null) return;

    if ((paramArrayOfInt[0] == 2) || (paramArrayOfInt[0] == 3)) {
      reformForSHIFT(paramArrayOfObject, this.maxSpace, paramArrayOfInt[0] == 2);
      return;
    }

    ArrayList localArrayList = new ArrayList(paramArrayOfObject.length);
    for (int i = 0; i < paramArrayOfObject.length; i++) {
      localArrayList.add(paramArrayOfObject[i]);
    }

    Collections.sort(localArrayList, new Comparator()
    {
      public int compare(Object paramObject1, Object paramObject2)
      {
        UnitData localUnitData1 = ((UnitInterface)paramObject1).GetUnitData();
        UnitData localUnitData2 = ((UnitInterface)paramObject2).GetUnitData();
        RoadSegment localRoadSegment1 = ChiefGround.this.road.get(localUnitData1.segmentIdx);
        RoadSegment localRoadSegment2 = ChiefGround.this.road.get(localUnitData2.segmentIdx);
        double d = localRoadSegment2.length2Dallprev + localRoadSegment2.computePosAlong(((Actor)paramObject2).pos.getAbsPoint()) - localRoadSegment1.length2Dallprev - localRoadSegment1.computePosAlong(((Actor)paramObject1).pos.getAbsPoint());

        return d > 0.0D ? 1 : d == 0.0D ? 0 : -1;
      }
    });
    for (i = 0; i < localArrayList.size(); i++) {
      UnitData localUnitData1 = ((UnitInterface)localArrayList.get(i)).GetUnitData();
      if (paramArrayOfInt[0] == 0) {
        localUnitData1.leaderDist = (i / paramArrayOfInt[1]);
      } else {
        j = i / (paramArrayOfInt[1] * 2 - 1);
        j = i % (paramArrayOfInt[1] * 2 - 1) < paramArrayOfInt[1] - 1 ? j * 2 : j * 2 + 1;
        localUnitData1.leaderDist = j;
      }

    }

    Collections.sort(localArrayList, new Comparator()
    {
      public int compare(Object paramObject1, Object paramObject2)
      {
        UnitData localUnitData1 = ((UnitInterface)paramObject1).GetUnitData();
        UnitData localUnitData2 = ((UnitInterface)paramObject2).GetUnitData();
        if (localUnitData1.leaderDist != localUnitData2.leaderDist) {
          double d1 = localUnitData1.leaderDist - localUnitData2.leaderDist;
          return d1 > 0.0D ? 1 : d1 == 0.0D ? 0 : -1;
        }
        RoadSegment localRoadSegment1 = ChiefGround.this.road.get(localUnitData1.segmentIdx);
        RoadSegment localRoadSegment2 = ChiefGround.this.road.get(localUnitData2.segmentIdx);
        double d2 = localRoadSegment1.computePosSide(((Actor)paramObject1).pos.getAbsPoint()) - localRoadSegment2.computePosSide(((Actor)paramObject2).pos.getAbsPoint());

        return d2 > 0.0D ? 1 : d2 == 0.0D ? 0 : -1;
      }
    });
    i = 0;
    float f1 = 0.0F;
    for (int j = 0; j < paramArrayOfInt[2]; j++) {
      int k = paramArrayOfInt[1];
      if (((j & 0x1) == 0) && (paramArrayOfInt[0] == 1)) k--;
      int m = k;
      if (i + m > localArrayList.size()) m = localArrayList.size() - i;
      float f2 = computeMaxSpace(localArrayList, i, m);
      float f3 = 0.0F;
      if (j > 0) f3 = Math.max(f1, f2);

      for (int n = 0; n < m; n++) {
        UnitData localUnitData2 = ((UnitInterface)localArrayList.get(i)).GetUnitData();

        localUnitData2.leaderDist = f3;
        int i1;
        if (i == 0) {
          i1 = -1;
        } else if (j == 0) {
          i1 = 0;
          localUnitData2.leaderDist = 0.0F;
        }
        else if ((paramArrayOfInt[0] == 0) || ((j & 0x1) == 0) || (n > 0)) {
          i1 = i - paramArrayOfInt[1];
        } else {
          i1 = i - paramArrayOfInt[1] + 1;
        }localUnitData2.leader = (i1 < 0 ? null : (Actor)localArrayList.get(i1));
        float f4 = paramArrayOfInt[0] == 0 ? f2 : this.maxSpace;
        float f5 = (paramArrayOfInt[1] - 1) * f4;
        if ((paramArrayOfInt[0] == 1) && ((j & 0x1) == 0)) f5 -= f4;
        localUnitData2.sideOffset = (-f5 / 2.0F + n * f4);

        i++;
      }
      if (i >= localArrayList.size()) break;
      f1 = f2;
    }

    for (i = 0; i < paramArrayOfObject.length; i++)
      ((UnitInterface)paramArrayOfObject[i]).forceReaskMove();
  }

  private int[] FindBestFormation(int paramInt)
  {
    float f = (paramInt - 1) * this.maxSpace;

    int[] arrayOfInt = new int[3];
    AutoChooseFormation(this.curState, this.shift_ToRightSide, paramInt, this.maxSpace, this.road.ComputeMinRoadWidth(this.maxSeg, this.minSeg), arrayOfInt);

    return arrayOfInt;
  }

  private void reformIfNeed(boolean paramBoolean)
  {
    Object[] arrayOfObject = getOwnerAttached();
    if (arrayOfObject.length <= 0) ERR_NO_UNITS("reformIfNeed");
    if (paramBoolean) {
      this.curForm = FindBestFormation(arrayOfObject.length);
      reform(arrayOfObject, this.curForm);
    } else {
      int[] arrayOfInt = FindBestFormation(arrayOfObject.length);
      if ((arrayOfInt[0] != this.curForm[0]) || (arrayOfInt[1] != this.curForm[1])) {
        this.curForm = arrayOfInt;
        reform(arrayOfObject, this.curForm);
      }
    }
  }

  public void CollisionOccured(UnitInterface paramUnitInterface, Actor paramActor)
  {
    if (!(paramActor instanceof UnitInterface)) {
      return;
    }
    if ((paramActor.getArmy() != getArmy()) && (paramActor.isAlive()) && (paramActor.getArmy() != 0) && (isAlive()) && (getArmy() != 0))
    {
      return;
    }
    Actor localActor = paramActor.getOwner();
    if (localActor == null) {
      return;
    }
    if (localActor == this) {
      return;
    }
    if (!(localActor instanceof ChiefGround)) {
      throw new ActorException("ChiefGround: ground unit with wrong owner");
    }

    ChiefGround localChiefGround = (ChiefGround)localActor;
    UnitInterface localUnitInterface = (UnitInterface)paramActor;

    Vector2d localVector2d1 = this.road.get(paramUnitInterface.GetUnitData().segmentIdx).dir2D;

    Vector2d localVector2d2 = localChiefGround.road.get(localUnitInterface.GetUnitData().segmentIdx).dir2D;

    int i = localVector2d1.x * localVector2d2.x + localVector2d1.y * localVector2d2.y < 0.0D ? 1 : 0;

    boolean bool = false;
    if (localChiefGround.waitTime < this.waitTime)
      bool = true;
    else if (localChiefGround.waitTime == this.waitTime) {
      if (localChiefGround.groupSpeed > this.groupSpeed)
        bool = true;
      else if ((localChiefGround.groupSpeed == this.groupSpeed) && 
        (localChiefGround.name().compareTo(name()) < 0)) {
        bool = true;
      }

    }

    this.curState = (localChiefGround.curState = 2);

    int j = SecsToTicks(World.Rnd().nextFloat(47.0F, 72.0F));
    int k = SecsToTicks(World.Rnd().nextFloat(47.0F, 72.0F));
    if (i != 0) {
      this.shift_ToRightSide = true;
      this.shift_SwitchToBrakeWhenDone = false;
      localChiefGround.shift_ToRightSide = true;
      localChiefGround.shift_SwitchToBrakeWhenDone = false;
      this.stateCountdown = j;
      localChiefGround.stateCountdown = k;
    } else {
      this.shift_ToRightSide = bool;
      this.shift_SwitchToBrakeWhenDone = bool;
      localChiefGround.shift_ToRightSide = (!bool);
      localChiefGround.shift_SwitchToBrakeWhenDone = (!bool);

      int m = SecsToTicks(World.Rnd().nextFloat(18.0F, 25.0F));
      if (bool) {
        localChiefGround.stateCountdown = j;
        localChiefGround.stateCountdown -= m;
      } else {
        this.stateCountdown = j;
        this.stateCountdown -= m;
      }
    }

    reformIfNeed(false);
    localChiefGround.reformIfNeed(false);
  }

  public double computePosAlong_Fit(int paramInt, Point3d paramPoint3d)
  {
    return this.road.get(paramInt).computePosAlong_Fit(paramPoint3d);
  }

  public double computePosAlong(int paramInt, Point3d paramPoint3d) {
    return this.road.get(paramInt).computePosAlong(paramPoint3d);
  }

  public double computePosSide(int paramInt, Point3d paramPoint3d) {
    return this.road.get(paramInt).computePosSide(paramPoint3d);
  }

  private static final float distance2D(Point3d paramPoint3d1, Point3d paramPoint3d2) {
    return (float)Math.sqrt((paramPoint3d1.x - paramPoint3d2.x) * (paramPoint3d1.x - paramPoint3d2.x) + (paramPoint3d1.y - paramPoint3d2.y) * (paramPoint3d1.y - paramPoint3d2.y));
  }

  private static boolean intersectLineCircle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    float f1 = paramFloat7 * paramFloat7;
    float f2 = paramFloat3 - paramFloat1;
    float f3 = paramFloat4 - paramFloat2;
    float f4 = f2 * f2 + f3 * f3;
    float f5 = ((paramFloat5 - paramFloat1) * f2 + (paramFloat6 - paramFloat2) * f3) / f4;
    if ((f5 >= 0.0F) && (f5 <= 1.0F)) {
      f6 = paramFloat1 + f5 * f2;
      f7 = paramFloat2 + f5 * f3;
      float f8 = (f6 - paramFloat5) * (f6 - paramFloat5) + (f7 - paramFloat6) * (f7 - paramFloat6);
      return f1 - f8 >= 0.0F;
    }
    float f6 = (paramFloat3 - paramFloat5) * (paramFloat3 - paramFloat5) + (paramFloat4 - paramFloat6) * (paramFloat4 - paramFloat6);
    float f7 = (paramFloat1 - paramFloat5) * (paramFloat1 - paramFloat5) + (paramFloat2 - paramFloat6) * (paramFloat2 - paramFloat6);
    return (f6 <= f1) || (f7 <= f1);
  }

  private static boolean intersectCircle(Point3d paramPoint3d1, double paramDouble1, Point3d paramPoint3d2, double paramDouble2, float paramFloat)
  {
    float f1 = (float)(paramPoint3d2.x + paramDouble2 * Geom.cosDeg(paramFloat));
    float f2 = (float)(paramPoint3d2.y + paramDouble2 * Geom.sinDeg(paramFloat));
    return intersectLineCircle((float)paramPoint3d2.x, (float)paramPoint3d2.y, f1, f2, (float)paramPoint3d1.x, (float)paramPoint3d1.y, (float)paramDouble1);
  }

  private UnitMove createStay_UnitMove(float paramFloat, int paramInt)
  {
    RoadSegment localRoadSegment = this.road.get(paramInt);
    if (localRoadSegment.IsLandAligned()) {
      return new UnitMove(paramFloat, new Vector3f(0.0F, 0.0F, -1.0F));
    }
    return new UnitMove(paramFloat, localRoadSegment.normal);
  }

  private boolean cantEnterIntoSegment_checkComplete(int paramInt)
  {
    if (paramInt >= this.road.nsegments() - 1) {
      int i = (this.waitTime > 0L) && (paramInt > this.maxSeg) ? 1 : 0;
      if (i == 0) {
        World.onTaskComplete(this);
      }
      return true;
    }
    return (!this.road.segIsPassableBy(paramInt, this)) || ((this.waitTime > 0L) && (paramInt > this.maxSeg));
  }

  private boolean cantEnterIntoSegment(int paramInt)
  {
    return (paramInt >= this.road.nsegments() - 1) || (!this.road.segIsPassableBy(paramInt, this)) || ((this.waitTime > 0L) && (paramInt > this.maxSeg));
  }

  private boolean cantEnterIntoSegmentPacked_checkComplete(int paramInt)
  {
    if (paramInt >= this.road.nsegments() - 1) {
      int i = this.waitTime > 0L ? 1 : 0;
      if (i == 0) {
        World.onTaskComplete(this);
      }
      return true;
    }
    return (!this.road.segIsPassableBy(paramInt, this)) || (this.waitTime > 0L);
  }

  private void moveChiefPacked(float paramFloat)
  {
    RoadSegment localRoadSegment = this.road.get(this.chiefSeg);
    double d = this.groupSpeed * paramFloat;
    while (this.chiefAlong + d >= localRoadSegment.length2D)
    {
      if (cantEnterIntoSegmentPacked_checkComplete(this.chiefSeg + 1))
      {
        this.chiefAlong = localRoadSegment.length2D;
        d = 0.0D;
        break;
      }

      this.chiefAlong = (this.chiefAlong + d - localRoadSegment.length2D);
      this.chiefSeg += 1;
      recomputeChiefWaitTime(this.chiefSeg);
      localRoadSegment = this.road.get(this.chiefSeg);

      this.road.unlockBridges(this, this.minGrabSeg, this.maxGrabSeg);
      this.minGrabSeg = (this.maxGrabSeg = this.chiefSeg);
      this.road.lockBridges(this, this.minGrabSeg, this.maxGrabSeg);
    }
    this.chiefAlong += d;
    SetPosition(localRoadSegment.computePos_Fit(this.chiefAlong, 0.0D, 0.0F), paramFloat);
  }

  public UnitMove AskMoveCommand(Actor paramActor, Point3d paramPoint3d, StaticObstacle paramStaticObstacle)
  {
    UnitInterface localUnitInterface = (UnitInterface)paramActor;
    UnitData localUnitData1 = localUnitInterface.GetUnitData();

    int i = (paramPoint3d != null) && (paramPoint3d.z < 0.0D) ? 1 : 0;
    int j = (paramPoint3d != null) && (paramPoint3d.z > 0.0D) ? 1 : 0;

    if (((this.curState == 2) || (this.curState == 3)) && (j != 0)) {
      j = 0;
    }

    RoadSegment localRoadSegment1 = this.road.get(localUnitData1.segmentIdx);
    Point3d localPoint3d1 = new Point3d(paramActor.pos.getAbsPoint());
    int k;
    double d5;
    if (i != 0)
    {
      k = 0;
      double d1 = localRoadSegment1.computePosAlong(localPoint3d1);
      if (d1 >= 0.0D)
        d1 = d1 > localRoadSegment1.length2D ? d1 - localRoadSegment1.length2D : 0.0D;
      while (true)
      {
        if (d1 > 0.0D)
          cantEnterIntoSegment_checkComplete(localUnitData1.segmentIdx + 1);
        RoadSegment localRoadSegment2;
        if (k >= 0) {
          if (cantEnterIntoSegment(localUnitData1.segmentIdx + 1)) {
            if (k != 0) break; 
          }
          else {
            localRoadSegment2 = this.road.get(localUnitData1.segmentIdx + 1);
            d5 = localRoadSegment2.computePosAlong(localPoint3d1);
            if (d5 >= 0.0D)
              d5 = d5 > localRoadSegment2.length2D ? d5 - localRoadSegment2.length2D : 0.0D;
            if (Math.abs(d5) < Math.abs(d1)) {
              k = 1;
              d1 = d5;
              localUnitData1.segmentIdx += 1; } else {
              if (k != 0)
                break;
            }
          }
        }
        if (k <= 0) {
          if (cantEnterIntoSegment(localUnitData1.segmentIdx - 1)) {
            break;
          }
          localRoadSegment2 = this.road.get(localUnitData1.segmentIdx - 1);
          d5 = localRoadSegment2.computePosAlong(localPoint3d1);
          if (d5 >= 0.0D)
            d5 = d5 > localRoadSegment2.length2D ? d5 - localRoadSegment2.length2D : 0.0D;
          if (Math.abs(d5) >= Math.abs(d1)) break;
          k = -1;
          d1 = d5;
          localUnitData1.segmentIdx -= 1;
        }

      }

      if (k != 0) {
        localRoadSegment1 = this.road.get(localUnitData1.segmentIdx);
        recomputeMinMaxSegments();

        reformIfNeed(false);
      }

    }
    else
    {
      k = 0;
      UnitMove localUnitMove = null;

      while (localRoadSegment1.computePosAlong(paramActor.pos.getAbsPoint()) >= localRoadSegment1.length2D - 1.0D)
      {
        if (cantEnterIntoSegment_checkComplete(localUnitData1.segmentIdx + 1))
        {
          localUnitMove = createStay_UnitMove(5.0F, localUnitData1.segmentIdx);
          break;
        }

        k = 1;
        localUnitData1.segmentIdx += 1;
        if ((localUnitData1.segmentIdx > this.maxSeg) || (localUnitData1.segmentIdx - 1 <= this.minSeg)) {
          recomputeMinMaxSegments();
        }
        localRoadSegment1 = this.road.get(localUnitData1.segmentIdx);
      }

      if (k != 0) {
        reformIfNeed(false);
      }
      if (localUnitMove != null) {
        return localUnitMove;
      }

    }

    Vector3d localVector3d = new Vector3d(localRoadSegment1.dir2D.x, localRoadSegment1.dir2D.y, 0.0D);
    if ((i == 0) && (paramStaticObstacle.updateState()))
    {
      double d2 = localRoadSegment1.computePosAlong(localPoint3d1);
      double d4 = this.road.ComputeSignedDistAlong(localUnitData1.segmentIdx, d2, paramStaticObstacle.segIdx, paramStaticObstacle.along);

      double d6 = paramActor.collisionR();
      if (d6 <= 0.0D) d6 = 0.0D;
      d6 += paramStaticObstacle.R;
      if (d6 <= 0.0D) d6 = 2.0D;
      d8 = d4;
      if (d4 >= 0.0D) {
        d8 -= d6;
        if (d8 <= 0.0D) d8 = 0.0D; 
      }
      else {
        d8 += d6;
        if (d8 >= 0.0D) d8 = 0.0D;

      }

      if ((Math.abs(d4) <= 130.0D) || 
        (d8 < -Math.max(7.0D * Math.abs(d6), 120.0D)))
      {
        paramStaticObstacle.clear();
      }
      else if (d4 <= 0.0D) {
        if (d8 >= -1.5D) {
          localVector3d.z = 2.5D;
        }

      }
      else if (d8 > Math.max(3.0D * d6, 20.0D))
      {
        localVector3d.z = Math.max(d6, 3.0D);
      }
      else
      {
        double d9 = 0.5D * d6;
        if (d9 < 0.01D)
        {
          localVector3d.z = 1.0D;
        }
        else {
          Vector2f localVector2f = new Vector2f((float)(paramStaticObstacle.pos.x - localPoint3d1.x), (float)(paramStaticObstacle.pos.y - localPoint3d1.y));

          if (localVector2f.length() < 0.1F)
          {
            localVector3d.z = 4.0D;
          }
          else {
            localVector2f.normalize();
            AnglesFork localAnglesFork = new AnglesFork();
            localAnglesFork.setSrcRad((float)Math.atan2(localVector2f.y, localVector2f.x));
            localAnglesFork.setDstDeg(localAnglesFork.getSrcDeg() + (paramStaticObstacle.side > 0.0D ? 90.0F : -90.0F));
            double d12 = d6 + 0.5D;
            if (!intersectCircle(paramStaticObstacle.pos, d12, localPoint3d1, d9, localAnglesFork.getSrcDeg()))
            {
              float f6 = localAnglesFork.getSrcDeg();
              localVector3d.set(Geom.cosDeg(f6), Geom.sinDeg(f6), d9);
            }
            else if (intersectCircle(paramStaticObstacle.pos, d12, localPoint3d1, d9, localAnglesFork.getDstDeg()))
            {
              localVector3d.z = 2.0D;
            }
            else {
              for (int m = 0; m < 6; m++) {
                float f8 = localAnglesFork.getDeg(0.5F);
                if (intersectCircle(paramStaticObstacle.pos, d12, localPoint3d1, d9, f8))
                  localAnglesFork.setSrcDeg(f8);
                else
                  localAnglesFork.setDstDeg(f8);
              }
              float f7 = localAnglesFork.getDstDeg();

              localVector3d.set(Geom.cosDeg(f7), Geom.sinDeg(f7), d9);
            }
          }
        }
      }
    }

    if (localVector3d.z > 0.0D)
    {
      j = 0;
      i = 1;
      localVector3d.x *= localVector3d.z;
      localVector3d.y *= localVector3d.z;
      paramPoint3d = new Point3d(localVector3d);
      paramPoint3d.z = -1.0D;
    }
    double d3;
    double d7;
    if (i != 0) {
      Point3d localPoint3d2 = new Point3d(localPoint3d1);
      localPoint3d2.x += paramPoint3d.x;
      localPoint3d2.y += paramPoint3d.y;
      d3 = localRoadSegment1.computePosAlong(localPoint3d2);
      d5 = localRoadSegment1.computePosSide(localPoint3d2);
      if (d3 >= localRoadSegment1.length2D - 0.2D)
      {
        localPoint3d2 = localRoadSegment1.computePos_Fit(localRoadSegment1.length2D, d5, paramActor.collisionR());
        localPoint3d2.x += localRoadSegment1.dir2D.x * 0.2D;
        localPoint3d2.y += localRoadSegment1.dir2D.y * 0.2D;
      }
      else {
        localPoint3d2 = localRoadSegment1.computePos_Fit(d3, d5, paramActor.collisionR());
      }
      d7 = distance2D(localPoint3d2, localPoint3d1);
      float f4 = (float)d7 / this.groupSpeed;

      Vector3f localVector3f1 = this.road.get(localUnitData1.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1.0F) : this.road.get(localUnitData1.segmentIdx).normal;

      return new UnitMove(localUnitInterface.HeightAboveLandSurface(), localPoint3d2, f4, localVector3f1, this.groupSpeed);
    }

    if (this.curState == 3) {
      return createStay_UnitMove(5.0F, localUnitData1.segmentIdx);
    }

    if (localUnitData1.leader == null)
    {
      float f1 = localUnitInterface.CommandInterval() * World.Rnd().nextFloat(0.8F, 1.0F);
      d3 = this.groupSpeed * f1;
      d5 = d3 + localRoadSegment1.computePosAlong(localPoint3d1);
      d7 = localUnitData1.sideOffset;
      if (j != 0) {
        d5 += paramPoint3d.x;
        d7 += paramPoint3d.y;
      }
      Point3d localPoint3d4;
      if (d5 >= localRoadSegment1.length2D - 0.2D)
      {
        localPoint3d4 = localRoadSegment1.computePos_Fit(localRoadSegment1.length2D, d7, paramActor.collisionR());
        localPoint3d4.x += localRoadSegment1.dir2D.x * 0.2D;
        localPoint3d4.y += localRoadSegment1.dir2D.y * 0.2D;
      } else {
        localPoint3d4 = localRoadSegment1.computePos_Fit(d5, d7, paramActor.collisionR());
      }
      d3 = distance2D(localPoint3d4, localPoint3d1);
      float f5 = (float)d3 / this.groupSpeed;
      Vector3f localVector3f2 = this.road.get(localUnitData1.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1.0F) : this.road.get(localUnitData1.segmentIdx).normal;

      return new UnitMove(localUnitInterface.HeightAboveLandSurface(), localPoint3d4, f5, localVector3f2, this.groupSpeed);
    }

    Actor localActor = localUnitData1.leader;
    UnitData localUnitData2 = ((UnitInterface)localActor).GetUnitData();
    RoadSegment localRoadSegment3 = this.road.get(localUnitData2.segmentIdx);

    Point3d localPoint3d3 = new Point3d();
    float f2 = localUnitInterface.CommandInterval();
    f2 *= World.Rnd().nextFloat(0.8F, 1.0F);
    float f3 = localActor.futurePosition(f2, localPoint3d3);

    f3 += 0.4F;

    double d8 = localRoadSegment3.computePosAlong(localPoint3d3);

    Point3d localPoint3d5 = new Point3d();
    paramActor.pos.getAbs(localPoint3d5);
    double d10 = localRoadSegment1.computePosAlong(localPoint3d5);

    double d11 = this.road.ComputeSignedDistAlong(localUnitData1.segmentIdx, d10, localUnitData2.segmentIdx, d8);

    double d13 = localUnitData1.leaderDist;
    if (j != 0) d13 += paramPoint3d.x;

    double d14 = d11 - d13;
    if (d14 < 0.0D) {
      f2 = localUnitInterface.StayInterval();
      if (localRoadSegment1.IsLandAligned()) {
        return new UnitMove(f2, new Vector3f(0.0F, 0.0F, -1.0F));
      }
      return new UnitMove(f2, this.road.get(localUnitData1.segmentIdx).normal);
    }

    double d15 = localRoadSegment1.length2D - d10;

    if (d14 <= d15) {
      d10 += d14;
    }
    else {
      d10 = localRoadSegment1.length2D;
      f3 = (float)(f3 * (d15 / d14));
    }

    f3 *= 1.05F;

    double d16 = localUnitData1.sideOffset;
    if (j != 0) d16 += paramPoint3d.y;

    localPoint3d5 = localRoadSegment1.computePos_FitBegCirc(d10, d16, paramActor.collisionR());
    if (d10 >= localRoadSegment1.length2D - 0.1D) {
      localPoint3d5.x += localRoadSegment1.dir2D.x * 0.2D;
      localPoint3d5.y += localRoadSegment1.dir2D.y * 0.2D;
    }
    d13 = distance2D(localPoint3d5, paramActor.pos.getAbsPoint());

    Vector3f localVector3f3 = this.road.get(localUnitData1.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1.0F) : this.road.get(localUnitData1.segmentIdx).normal;

    return new UnitMove(localUnitInterface.HeightAboveLandSurface(), localPoint3d5, f3 < 0.3F ? 0.3F : f3, localVector3f3, -1.0F);
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if ((ChiefGround.this.waitTime > 0L) && 
        (Time.tick() >= ChiefGround.this.waitTime)) {
        ChiefGround.access$002(ChiefGround.this, 0L);
      }
      if (ChiefGround.this.unitsPacked.size() > 0) {
        ChiefGround.this.moveChiefPacked(Time.tickLenFs());
        return true;
      }

      if (ChiefGround.access$306(ChiefGround.this) <= 0) {
        int i = ChiefGround.access$400(World.Rnd().nextFloat(300.0F, 500.0F));
        switch (ChiefGround.this.curState)
        {
        case 0:
          ChiefGround.access$302(ChiefGround.this, i);
          break;
        case 1:
          ChiefGround.access$502(ChiefGround.this, 0);
          ChiefGround.access$302(ChiefGround.this, i);
          ChiefGround.this.reformIfNeed(false);
          break;
        case 2:
          if (ChiefGround.this.shift_SwitchToBrakeWhenDone) {
            ChiefGround.access$502(ChiefGround.this, 3);

            ChiefGround.access$302(ChiefGround.this, ChiefGround.access$400(World.Rnd().nextFloat(38.0F, 65.0F)));
          } else {
            ChiefGround.access$502(ChiefGround.this, 0);
            ChiefGround.access$302(ChiefGround.this, i);
          }
          ChiefGround.this.reformIfNeed(false);
          break;
        case 3:
          ChiefGround.access$502(ChiefGround.this, 0);
          ChiefGround.access$302(ChiefGround.this, i);
          ChiefGround.this.reformIfNeed(true);
        }

      }

      if (ChiefGround.access$806(ChiefGround.this) <= 0) {
        ChiefGround.this.recomputeAveragePosition();
      }

      return true;
    }
  }
}