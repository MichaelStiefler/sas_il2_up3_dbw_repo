package com.maddox.il2.objects.trains;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.RoadPart;
import com.maddox.il2.ai.ground.RoadPath;
import com.maddox.il2.ai.ground.RoadSegment;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Interpolate;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;

public class Train extends Chief
{
  public static final float TRAIN_SPEED = 11.111112F;
  private static final float TRAIN_SLOWSPEED = 0.7222222F;
  private static final float TRAIN_ACCEL = 1.5F;
  private static final float TRAIN_CRUSHACCEL = 3.5F;
  public static final double TRAIN_SPEEDUPDIST = 350.0D;
  private ArrayList wagons;
  private float trainLength;
  private RoadPath road;
  private long startDelay;
  private int headSeg;
  private double headAlong;
  private int tailSeg;
  private double tailAlong;
  private float curSpeed;
  private double milestoneDist;
  private float requiredSpeed;
  private float maxAcceler;

  void getStateData(TrainState paramTrainState)
  {
    paramTrainState._headSeg = this.headSeg;
    paramTrainState._headAlong = this.headAlong;
    paramTrainState._curSpeed = this.curSpeed;
    paramTrainState._milestoneDist = this.milestoneDist;
    paramTrainState._requiredSpeed = this.requiredSpeed;
    paramTrainState._maxAcceler = this.maxAcceler;
  }

  protected final float getEngineSmokeKoef()
  {
    if (this.requiredSpeed < 11.111112F) {
      return this.curSpeed / 11.111112F;
    }
    return 1.0F;
  }

  protected final boolean stoppedForever() {
    return this.requiredSpeed < 0.0F;
  }

  private Train(Train paramTrain, int paramInt)
  {
    if (paramInt <= 0) {
      ERR("Split at head");
    }

    this.road = new RoadPath(paramTrain.road);
    this.startDelay = this.road.get(0).waitTime;
    if (this.startDelay < 0L) this.startDelay = 0L;

    this.road.RegisterTravellerToBridges(this);

    setName(paramTrain.name() + "x");
    setArmy(paramTrain.getArmy());
    this.headSeg = (int)paramTrain.getLocationOfWagon(paramInt, true);
    this.headAlong = paramTrain.getLocationOfWagon(paramInt, false);

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.road.get(this.headSeg).computePos_Fit(this.headAlong, 0.0D, 0.0F));
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    computeSpeedsWhenCrush(paramTrain.curSpeed);

    this.wagons = new ArrayList();
    for (int i = paramInt; i < paramTrain.wagons.size(); i++) {
      Wagon localWagon = (Wagon)paramTrain.wagons.get(i);
      this.wagons.add(localWagon);
      localWagon.setOwner(this);
    }
    for (int j = paramTrain.wagons.size() - 1; j >= paramInt; j--) {
      paramTrain.wagons.remove(j);
    }

    paramTrain.recomputeTrainLength();
    recomputeTrainLength();
    placeTrain(false, false);

    if (!interpEnd("move"))
      interpPut(new Move(), "move", Time.current(), null);
  }

  private static final void ERR_NO_WAGONS(String paramString)
  {
    String str = "INTERNAL ERROR IN Train." + paramString + "(): No wagons";
    System.out.println(str);
    throw new ActorException(str);
  }

  private static final void ERR(String paramString) {
    String str = "INTERNAL ERROR IN Train: " + paramString;
    System.out.println(str);
    throw new ActorException(str);
  }

  private static final void ConstructorFailure() {
    System.out.println("Train: Creation error");
    throw new ActorException();
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public Train(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
  {
    try
    {
      this.road = new RoadPath(paramSectFile2, paramString3);
      this.startDelay = this.road.get(0).waitTime;
      if (this.startDelay < 0L) this.startDelay = 0L;

      this.road.RegisterTravellerToBridges(this);

      setName(paramString1);
      setArmy(paramInt);
      this.headSeg = 0;
      this.headAlong = 0.0D;

      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.road.get(0).start);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

      int i = paramSectFile1.sectionIndex(paramString2);
      int j = paramSectFile1.vars(i);
      if (j <= 0) {
        throw new ActorException("Train: Missing wagons");
      }
      this.wagons = new ArrayList();
      for (int k = 0; k < j; k++) {
        String str = paramSectFile1.var(i, k);
        Object localObject = Spawn.get(str);
        if (localObject == null) {
          throw new ActorException("Train: Unknown class of wagon (" + str + ")");
        }

        Wagon localWagon = ((WagonSpawn)localObject).wagonSpawn(this);
        localWagon.setName(paramString1 + k);
        this.wagons.add(localWagon);
      }

      recomputeTrainLength();

      this.curSpeed = 0.0F;
      this.requiredSpeed = 0.0F;
      placeTrain(true, false);

      recomputeSpeedRequirements(this.road.get(this.headSeg).length2Dallprev + this.headAlong - this.trainLength);

      if (!interpEnd("move"))
        interpPut(new Move(), "move", Time.current(), null);
    }
    catch (Exception localException)
    {
      System.out.println("Train creation failure:");
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      ConstructorFailure();
    }
  }

  public void BridgeSegmentDestroyed(int paramInt1, int paramInt2, Actor paramActor)
  {
    boolean bool = this.road.MarkDestroyedSegments(paramInt1, paramInt2);
    if (!bool) {
      return;
    }

    for (int i = this.tailSeg; i <= this.headSeg; i++) {
      if (this.road.segIsWrongOrDamaged(i)) {
        break;
      }
    }
    if (i > this.headSeg) {
      return;
    }

    for (int j = 0; j < this.wagons.size(); j++) {
      Wagon localWagon = (Wagon)this.wagons.get(j);

      localWagon.absoluteDeath(paramActor);
    }

    this.road.UnregisterTravellerFromBridges(this);

    destroy();
  }

  private static final int SecsToTicks(float paramFloat)
  {
    int i = (int)(0.5D + paramFloat / Time.tickLenFs());
    return i <= 0 ? 0 : i;
  }

  public Actor GetNearestEnemy(Point3d paramPoint3d, double paramDouble, int paramInt)
  {
    NearestEnemies.set(paramInt);

    return NearestEnemies.getAFoundEnemy(paramPoint3d, paramDouble, getArmy());
  }

  private static float solveSquareEq(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = paramFloat2 * paramFloat2 - 4.0F * paramFloat1 * paramFloat3;
    if (f1 < 0.0F) {
      return -1.0F;
    }
    f1 = (float)Math.sqrt(f1);
    float f2 = (-paramFloat2 + f1) / (2.0F * paramFloat1);
    float f3 = (-paramFloat2 - f1) / (2.0F * paramFloat1);
    if (f3 > f2) {
      f2 = f3;
    }
    return f2 >= 0.0F ? f2 : -1.0F;
  }

  private static float findSideBOfTriangle(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return solveSquareEq(1.0F, 2.0F * paramFloat1 * paramFloat3, paramFloat1 * paramFloat1 - paramFloat2 * paramFloat2);
  }

  private void recomputeTrainLength()
  {
    this.trainLength = 0.0F;
    for (int i = 0; i < this.wagons.size(); i++) {
      Wagon localWagon = (Wagon)this.wagons.get(i);
      this.trainLength += localWagon.getLength();
    }
  }

  private void placeTrain(boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject;
    if (paramBoolean2) {
      for (int i = 0; i < this.wagons.size(); i++) {
        localObject = (Wagon)this.wagons.get(i);

        ((Wagon)localObject).place(null, null, false, true);
      }
      return;
    }

    if (paramBoolean1)
    {
      float f1 = this.trainLength * 1.02F;

      localObject = new RoadPart();
      if (!this.road.FindFreeSpace(f1, this.headSeg, this.headAlong, (RoadPart)localObject)) {
        System.out.println("Train: Not enough room for wagons");
        throw new ActorException();
      }

      this.headSeg = ((RoadPart)localObject).begseg;
      this.headAlong = ((RoadPart)localObject).begt;
    }

    int k = 0;
    double d2 = 0.0D;

    int j = this.headSeg;
    double d1 = this.headAlong;
    Point3d localPoint3d1 = this.road.get(j).computePos_Fit(d1, 0.0D, 0.0F);
    for (int m = 0; m < this.wagons.size(); m++) {
      Wagon localWagon = (Wagon)this.wagons.get(m);
      float f2 = localWagon.getLength();

      if (d1 >= f2) {
        k = j;
        d2 = d1 - f2;
      } else {
        if (j <= 0) {
          System.out.println("Train: No room for wagons (curly station road?)");
          throw new ActorException();
        }

        k = j - 1;
        d2 = this.road.get(k).length2D;
        float f3 = this.road.computeCosToNextSegment(k);
        float f4 = findSideBOfTriangle((float)d1, f2, f3);
        if ((f4 < 0.0F) || (f4 > d2)) {
          System.out.println("Train: internal error in computings (len=" + d2 + " B=" + f4 + ")");

          throw new ActorException();
        }
        d2 -= f4;
        if (d2 <= 0.0D) {
          d2 = 0.0D;
        }
      }
      Point3d localPoint3d2 = this.road.get(k).computePos_Fit(d2, 0.0D, 0.0F);

      localWagon.place(localPoint3d1, localPoint3d2, paramBoolean1, false);

      j = k;
      d1 = d2;
      localPoint3d1.set(localPoint3d2);

      this.tailSeg = k;
      this.tailAlong = d2;
    }
  }

  private final double getLocationOfWagon(int paramInt, boolean paramBoolean)
  {
    int i = this.headSeg;
    double d1 = this.headAlong;

    for (int j = 0; j < paramInt; j++) {
      Wagon localWagon = (Wagon)this.wagons.get(j);
      float f1 = localWagon.getLength();

      if (d1 >= f1) {
        d1 -= f1;
      } else {
        if (i <= 0) {
          System.out.println("Train: No room for wagons (curly station road)");
          throw new ActorException();
        }

        i--;
        double d2 = this.road.get(i).length2D;
        float f2 = this.road.computeCosToNextSegment(i);
        float f3 = findSideBOfTriangle((float)d1, f1, f2);
        if ((f3 < 0.0F) || (f3 > d2)) {
          System.out.println("Train: internal error in computings (len=" + d2 + " B=" + f3 + ")");

          throw new ActorException();
        }
        d1 = d2 - f3;
        if (d1 <= 0.0D) {
          d1 = 0.0D;
        }
      }
    }

    return paramBoolean ? i : d1;
  }

  private void computeSpeedsWhenCrush(float paramFloat)
  {
    this.curSpeed = (paramFloat * 0.9F);
    this.milestoneDist = 99999000.0D;
    this.requiredSpeed = 0.0F;
    this.maxAcceler = 3.5F;
  }

  private void LocoSound(int paramInt)
  {
    if ((this.wagons == null) || (this.wagons.size() <= 0)) {
      return;
    }
    Wagon localWagon = (Wagon)this.wagons.get(0);
    if ((localWagon == null) || (!(localWagon instanceof LocomotiveVerm))) {
      return;
    }

    switch (paramInt) { case 0:
      localWagon.newSound("models.train", true);
      break;
    case 1:
      localWagon.newSound("objects.train_signal", true);
      break;
    default:
      localWagon.breakSounds();
    }
  }

  private void recomputeSpeedRequirements(double paramDouble)
  {
    double d1 = this.road.get(this.road.nsegments() - 1).length2Dallprev;
    d1 -= this.trainLength;

    this.maxAcceler = 1.5F;
    double d2;
    if (d1 <= 350.0D) {
      if (paramDouble < d1 * 0.5D) {
        LocoSound(0);
        this.milestoneDist = (d1 * 0.5D);
        d2 = this.milestoneDist - paramDouble;
        this.requiredSpeed = 11.111112F;
      } else {
        LocoSound(1);
        this.milestoneDist = 99999900.0D;
        d2 = d1 - paramDouble;
        this.requiredSpeed = 0.7222222F;
      }

    }
    else if (paramDouble < 350.0D) {
      LocoSound(0);
      this.milestoneDist = 350.0D;
      d2 = this.milestoneDist - paramDouble;
      this.requiredSpeed = 11.111112F;
    } else if (paramDouble < d1 - 350.0D) {
      this.milestoneDist = (d1 - 350.0D);
      d2 = 175.0D;
      this.requiredSpeed = 11.111112F;
    } else {
      LocoSound(1);
      this.milestoneDist = 99999900.0D;
      d2 = d1 - paramDouble;
      this.requiredSpeed = 0.7222222F;
    }

    if (d2 > 0.05D) {
      float f = (float)((this.requiredSpeed * this.requiredSpeed - this.curSpeed * this.curSpeed) / (2.0D * d2));

      f = Math.abs(f);
      if (f <= this.maxAcceler) {
        this.maxAcceler = f;
      }
    }
    if (this.maxAcceler < 0.01F)
      this.maxAcceler = 0.01F;
  }

  private boolean cantEnterIntoSegment(int paramInt)
  {
    return (paramInt >= this.road.nsegments() - 1) || (!this.road.segIsPassableBy(paramInt, this));
  }

  private void moveTrain(float paramFloat)
  {
    if (this.requiredSpeed < 0.0F)
    {
      placeTrain(false, true);
      return;
    }

    RoadSegment localRoadSegment = this.road.get(this.headSeg);
    double d1 = localRoadSegment.length2Dallprev + this.headAlong - this.trainLength;
    if (d1 >= this.milestoneDist) {
      recomputeSpeedRequirements(d1);
    }

    float f1 = this.requiredSpeed - this.curSpeed;
    double d2;
    if (f1 != 0.0F) {
      f1 /= paramFloat;
      float f2;
      if (Math.abs(f1) > this.maxAcceler) {
        f1 = f1 >= 0.0F ? this.maxAcceler : -this.maxAcceler;
        f2 = this.curSpeed + paramFloat * f1;
        if (f2 < 0.0F)
          f2 = 0.0F;
      }
      else {
        f2 = this.requiredSpeed;
      }
      d2 = this.curSpeed * paramFloat + f1 * paramFloat * paramFloat * 0.5F;
      this.curSpeed = f2;
      if (d2 <= 0.0D)
        d2 = 0.0D;
    }
    else {
      d2 = this.curSpeed * paramFloat;
      if (this.requiredSpeed == 0.0F) {
        this.requiredSpeed = -1.0F;
      }

    }

    while (this.headAlong + d2 >= localRoadSegment.length2D)
    {
      if (cantEnterIntoSegment(this.headSeg + 1))
      {
        this.headAlong = localRoadSegment.length2D;
        d2 = 0.0D;
        this.curSpeed = 0.0F;
        this.requiredSpeed = -1.0F;

        if (this.headSeg + 1 < this.road.nsegments() - 1) break;
        World.onTaskComplete(this); break;
      }

      this.headAlong = (this.headAlong + d2 - localRoadSegment.length2D);
      this.headSeg += 1;
      localRoadSegment = this.road.get(this.headSeg);
    }
    this.headAlong += d2;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localRoadSegment.computePos_Fit(this.headAlong, 0.0D, 0.0F));

    placeTrain(false, false);
  }

  void wagonDied(Wagon paramWagon, Actor paramActor)
  {
    for (int i = 0; i < this.wagons.size(); i++) {
      if (paramWagon == (Wagon)this.wagons.get(i)) {
        break;
      }
    }
    if (i >= this.wagons.size()) {
      ERR("Unknown wagon");
    }

    if (this.requiredSpeed >= 0.0F)
    {
      if (i == 0) {
        computeSpeedsWhenCrush(this.curSpeed);
        if ((paramWagon instanceof LocomotiveVerm))
          World.onActorDied(this, paramActor);
      }
      else {
        computeSpeedsWhenCrush(this.curSpeed);
        if ((paramWagon instanceof LocomotiveVerm))
          World.onActorDied(this, paramActor);
      }
    }
  }

  boolean isAnybodyDead()
  {
    for (int i = 0; i < this.wagons.size(); i++) {
      Wagon localWagon = (Wagon)this.wagons.get(i);
      if (localWagon.IsDeadOrDying()) {
        return true;
      }
    }
    return false;
  }

  void setStateDataMirror(TrainState paramTrainState, boolean paramBoolean) {
    this.headSeg = paramTrainState._headSeg;
    this.headAlong = paramTrainState._headAlong;
    this.curSpeed = paramTrainState._curSpeed;
    this.milestoneDist = paramTrainState._milestoneDist;
    this.requiredSpeed = paramTrainState._requiredSpeed;
    this.maxAcceler = paramTrainState._maxAcceler;

    placeTrain(false, false);

    if ((this.requiredSpeed >= 0.0F) && (paramBoolean))
      computeSpeedsWhenCrush(this.curSpeed);
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (Time.current() >= Train.this.startDelay) {
        Train.this.moveTrain(Time.tickLenFs());
      }
      return true;
    }
  }

  public static class TrainState
  {
    public int _headSeg;
    public double _headAlong;
    public float _curSpeed;
    public double _milestoneDist;
    public float _requiredSpeed;
    public float _maxAcceler;
  }
}