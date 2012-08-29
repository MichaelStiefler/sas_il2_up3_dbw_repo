package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Formation;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.D3A;
import com.maddox.il2.objects.air.F4U;
import com.maddox.il2.objects.air.G4M2E;
import com.maddox.il2.objects.air.I_16TYPE24DRONE;
import com.maddox.il2.objects.air.JU_87;
import com.maddox.il2.objects.air.MXY_7;
import com.maddox.il2.objects.air.Scheme4;
import com.maddox.il2.objects.air.TB_3_4M_34R_SPB;
import com.maddox.il2.objects.air.TypeBNZFighter;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeGlider;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.air.TypeTNBFighter;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.BombGunParafrag8;
import com.maddox.il2.objects.weapons.TorpedoGun;
import java.io.PrintStream;

public class AirGroup
{
  public int nOfAirc;
  public Aircraft[] airc;
  public Squadron sq;
  public Way w;
  public Vector3d Pos = new Vector3d();
  public AirGroupList[] enemies;
  public AirGroupList[] friends;
  public AirGroup clientGroup;
  public AirGroup targetGroup;
  public AirGroup leaderGroup;
  public AirGroup rejoinGroup;
  public int grAttached;
  public int gTargetPreference;
  public int aTargetPreference;
  public boolean enemyFighters;
  private boolean gTargWasFound;
  private boolean gTargDestroyed;
  private int gTargMode;
  private Actor gTargActor;
  private Point3d gTargPoint;
  private float gTargRadius;
  private boolean aTargWasFound;
  private boolean aTargDestroyed;
  private boolean WeWereInGAttack;
  private boolean WeWereInAttack;
  public byte formationType;
  private byte oldFType;
  private float oldFScale;
  private boolean oldFInterp;
  public boolean fInterpolation;
  private int oldEnemyNum;
  public int timeOutForTaskSwitch;
  public int grTask;
  public static final int FLY_WAYPOINT = 1;
  public static final int DEFENDING = 2;
  public static final int ATTACK_AIR = 3;
  public static final int ATTACK_GROUND = 4;
  public static final int TAKEOFF = 5;
  public static final int LANDING = 6;
  public static final int GT_MODE_NONE = 0;
  public static final int GT_MODE_CHIEF = 1;
  public static final int GT_MODE_AROUND_POINT = 2;
  private static String[] GTList = { "NO_TASK.", "FLY_WAYPOINT", "DEFENDING", "ATTACK_AIR", "ATTACK_GROUND", "TAKEOFF", "LANDING" };

  private static Vector3d tmpV = new Vector3d();
  private static Vector3d tmpV1 = new Vector3d();
  private static Vector3d tmpV3d = new Vector3d();
  private static Point3d tmpP = new Point3d();
  private static Point3d tmpP3d = new Point3d();
  private static Vector2d P1P2vector = new Vector2d();
  private static Vector2d norm1 = new Vector2d();
  private static Vector2d norm2 = new Vector2d();
  private static Vector2d myPoint = new Vector2d();
  private static Vector3f tmpVf = new Vector3f();

  public String grTaskName()
  {
    return GTList[this.grTask];
  }

  public AirGroup() {
    initVars();
  }

  public AirGroup(Squadron paramSquadron, Way paramWay)
  {
    initVars();
    this.sq = paramSquadron;
    this.w = paramWay;
  }

  public AirGroup(AirGroup paramAirGroup)
  {
    initVars();
    if (paramAirGroup == null) return;
    this.sq = paramAirGroup.sq;
    if (paramAirGroup.w != null) {
      this.w = new Way(paramAirGroup.w);
      this.w.setCur(paramAirGroup.w.Cur());
    } else {
      this.w = new Way();
      WayPoint localWayPoint = new WayPoint((float)paramAirGroup.Pos.x, (float)paramAirGroup.Pos.y, (float)paramAirGroup.Pos.z);
      this.w.add(localWayPoint);
    }
    this.Pos.set(paramAirGroup.Pos);
    int i = AirGroupList.length(paramAirGroup.enemies[0]);
    for (int j = 0; j < i; j++)
      AirGroupList.addAirGroup(this.enemies, 0, AirGroupList.getGroup(paramAirGroup.enemies[0], j));
    i = AirGroupList.length(paramAirGroup.friends[0]);
    for (int k = 0; k < i; k++)
      AirGroupList.addAirGroup(this.friends, 0, AirGroupList.getGroup(paramAirGroup.friends[0], k));
    this.rejoinGroup = paramAirGroup;
    this.gTargetPreference = paramAirGroup.gTargetPreference;
    this.aTargetPreference = paramAirGroup.aTargetPreference;
    this.enemyFighters = paramAirGroup.enemyFighters;
    this.oldEnemyNum = paramAirGroup.oldEnemyNum;
    if (AirGroupList.groupInList(War.Groups[0], paramAirGroup))
      AirGroupList.addAirGroup(War.Groups, 0, this);
    else
      AirGroupList.addAirGroup(War.Groups, 1, this);
  }

  public void initVars()
  {
    this.nOfAirc = 0;
    this.airc = new Aircraft[16];
    this.sq = null;
    this.w = null;
    this.Pos = new Vector3d(0.0D, 0.0D, 0.0D);
    this.enemies = new AirGroupList[1];
    this.friends = new AirGroupList[1];
    this.clientGroup = null;
    this.targetGroup = null;
    this.leaderGroup = null;
    this.rejoinGroup = null;
    this.grAttached = 0;
    this.gTargetPreference = 0;
    this.aTargetPreference = 9;
    this.enemyFighters = false;
    this.gTargWasFound = false;
    this.gTargDestroyed = false;
    this.gTargMode = 0;
    this.gTargActor = null;
    this.gTargPoint = new Point3d();
    this.gTargRadius = 0.0F;
    this.aTargWasFound = false;
    this.aTargDestroyed = false;
    this.WeWereInGAttack = false;
    this.WeWereInAttack = false;
    this.formationType = -1;
    this.fInterpolation = false;
    this.oldFType = -1; this.oldFScale = 0.0F; this.oldFInterp = false;
    this.oldEnemyNum = 0;
    this.timeOutForTaskSwitch = 0;
    this.grTask = 1;
  }

  public void release()
  {
    for (int i = 0; i < this.nOfAirc; i++) {
      if (this.airc[i] != null) ((Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group = null;
      this.airc[i] = null;
    }
    this.nOfAirc = 0;
    this.sq = null;
    this.w = null;
    this.Pos = null;
    if (this.enemies[0] != null) this.enemies[0].release();
    if (this.friends[0] != null) this.friends[0].release();
    this.enemies = null;
    this.friends = null;
    this.clientGroup = null;
    this.targetGroup = null;
    this.leaderGroup = null;
    this.rejoinGroup = null;
    this.gTargPoint = null;
  }

  public void addAircraft(Aircraft paramAircraft)
  {
    if (this.nOfAirc >= 16) {
      System.out.print("Group > 16 in squadron " + this.sq.name());
      return;
    }
    int i;
    if (paramAircraft.getSquadron() == this.sq) {
      for (i = 0; i < this.nOfAirc; i++) {
        if ((this.airc[i].getSquadron() != this.sq) || (this.airc[i].getWing().indexInSquadron() * 4 + this.airc[i].aircIndex() > paramAircraft.getWing().indexInSquadron() * 4 + paramAircraft.aircIndex()))
          break;
      }
    }
    else
      i = this.nOfAirc;
    for (int j = this.nOfAirc - 1; j >= i; j--) {
      this.airc[(j + 1)] = this.airc[j];
    }
    this.airc[i] = paramAircraft;
    if (this.w != null) {
      paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way = new Way(this.w);
      paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(this.w.Cur());
    }
    this.nOfAirc += 1;

    if ((paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver))
      ((Maneuver)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group = this;
  }

  public void delAircraft(Aircraft paramAircraft)
  {
    for (int i = 0; i < this.nOfAirc; i++) {
      if (paramAircraft == this.airc[i]) {
        ((Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group = null;
        for (int j = i; j < this.nOfAirc - 1; j++) {
          this.airc[j] = this.airc[(j + 1)];
        }
        this.nOfAirc -= 1;
        break;
      }
    }
    if ((this.grTask == 1) || (this.grTask == 2)) setTaskAndManeuver(0);
  }

  public void changeAircraft(Aircraft paramAircraft1, Aircraft paramAircraft2)
  {
    for (int i = 0; i < this.nOfAirc; i++)
      if (paramAircraft1 == this.airc[i]) {
        ((Maneuver)paramAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group = null;
        ((Maneuver)paramAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group = this;
        ((Maneuver)paramAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setBusy(false);
        this.airc[i] = paramAircraft2;
        return;
      }
  }

  public void rejoinToGroup(AirGroup paramAirGroup)
  {
    if (paramAirGroup == null) return;
    for (int i = this.nOfAirc - 1; i >= 0; i--) {
      Aircraft localAircraft = this.airc[i];
      delAircraft(localAircraft);
      paramAirGroup.addAircraft(localAircraft);
    }
    this.rejoinGroup = null;
  }

  public void attachGroup(AirGroup paramAirGroup)
  {
    if (paramAirGroup == null) return;
    for (int i = 0; i < this.nOfAirc; i++)
      if ((this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) {
        Maneuver localManeuver = (Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        if ((!(localManeuver instanceof RealFlightModel)) || (!((RealFlightModel)localManeuver).isRealMode())) {
          if (localManeuver.get_maneuver() == 26) return;
          if (localManeuver.get_maneuver() == 64) return;
        }
      }
    this.w = null;
    this.w = new Way(paramAirGroup.w);
    this.w.setCur(paramAirGroup.w.Cur());
    for (int j = 0; j < this.nOfAirc; j++) {
      this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way = null;
      this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way = new Way(paramAirGroup.w);
      this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(paramAirGroup.w.Cur());
    }
    Formation.leaderOffset(this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel, this.formationType, this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Offset_of_type_ComMaddoxJGPVector3d);
    this.leaderGroup = paramAirGroup;
    this.leaderGroup.grAttached += 1;
    this.grTask = 1;
    setFormationAndScale(paramAirGroup.formationType, 1.0F, true);
  }

  public void detachGroup(AirGroup paramAirGroup)
  {
    if (paramAirGroup == null) return;
    this.leaderGroup.grAttached -= 1;
    if (this.leaderGroup.grAttached < 0) this.leaderGroup.grAttached = 0;
    this.leaderGroup = null;
    this.grTask = 1;
    setTaskAndManeuver(0);
  }

  public int numInGroup(Aircraft paramAircraft)
  {
    for (int i = 0; i < this.nOfAirc; i++) {
      if (paramAircraft == this.airc[i]) {
        return i;
      }
    }
    return -1;
  }

  public void setEnemyFighters()
  {
    int i = AirGroupList.length(this.enemies[0]);
    this.enemyFighters = false;
    for (int j = 0; j < i; j++) {
      AirGroup localAirGroup = AirGroupList.getGroup(this.enemies[0], j);
      if ((localAirGroup.nOfAirc > 0) && ((localAirGroup.airc[0] instanceof TypeFighter))) {
        this.enemyFighters = true;
        return;
      }
    }
  }

  public void setFormationAndScale(byte paramByte, float paramFloat, boolean paramBoolean)
  {
    if ((this.oldFType == paramByte) && (this.oldFScale == paramFloat) && (this.oldFInterp == paramBoolean)) return;
    this.fInterpolation = paramBoolean;
    for (int i = 1; i < this.nOfAirc; i++) {
      if ((this.airc[i] instanceof TypeGlider)) return;
      ((Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).formationScale = paramFloat;
      Formation.gather(this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel, paramByte, tmpV);
      if (!paramBoolean) this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Offset_of_type_ComMaddoxJGPVector3d.set(tmpV);
      this.formationType = ((Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).formationType;
    }
    if ((this.grTask == 1) || (this.grTask == 2)) setTaskAndManeuver(0);
    this.oldFType = paramByte; this.oldFScale = paramFloat; this.oldFInterp = paramBoolean;
  }

  public void formationUpdate()
  {
    if (this.fInterpolation) {
      int i = 0;
      for (int j = 1; j < this.nOfAirc; j++)
        if (Actor.isAlive(this.airc[j])) {
          if ((this.airc[j] instanceof TypeGlider)) return;
          Formation.gather(this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel, this.formationType, tmpV);
          tmpV1.sub(tmpV, this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Offset_of_type_ComMaddoxJGPVector3d);
          float f = (float)tmpV1.length();
          if (f != 0.0F) {
            i = 1;
            if (f < 0.1F) { this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Offset_of_type_ComMaddoxJGPVector3d.set(tmpV);
            } else
            {
              double d = 0.0004D * tmpV1.length();
              if (d > 1.0D) d = 1.0D;

              tmpV1.normalize();
              tmpV1.scale(d);
              this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Offset_of_type_ComMaddoxJGPVector3d.add(tmpV1);
            }
          }
        }
      if (i == 0) this.fInterpolation = false;
      if ((this.grTask == 1) || (this.grTask == 2)) setTaskAndManeuver(0);
    }
  }

  public boolean groupsInContact(AirGroup paramAirGroup)
  {
    for (int i = 0; i < this.nOfAirc; i++) {
      for (int j = 0; j < paramAirGroup.nOfAirc; j++) {
        tmpV.sub(this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, paramAirGroup.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        if (tmpV.lengthSquared() < 50000000.0D) return true;
      }
    }
    return false;
  }

  public boolean inCorridor(Point3d paramPoint3d)
  {
    if (this.w == null) return true;
    int i = this.w.Cur();
    if (i == 0) return true;
    this.w.prev();
    tmpP = this.w.curr().getP();
    this.w.setCur(i);
    tmpV.sub(this.w.curr().getP(), tmpP);
    P1P2vector.set(tmpV);
    float f1 = (float)P1P2vector.length();
    if (f1 > 1.0E-004F) P1P2vector.scale(1.0F / f1); else
      P1P2vector.set(1.0D, 0.0D);
    tmpV.sub(paramPoint3d, tmpP);
    myPoint.set(tmpV);
    if (P1P2vector.dot(myPoint) < -25000.0D) return false;
    norm1.set(-P1P2vector.y, P1P2vector.x);
    float f2 = (float)norm1.dot(myPoint);
    if (f2 > 25000.0F) return false;
    if (f2 < -25000.0F) return false;
    tmpV.sub(paramPoint3d, this.w.curr().getP());
    myPoint.set(tmpV);
    return P1P2vector.dot(myPoint) <= 25000.0D;
  }

  public void setGroupTask(int paramInt)
  {
    this.grTask = paramInt;
    if ((this.grTask == 1) || (this.grTask == 2)) setTaskAndManeuver(0);
    else
      for (int i = 0; i < this.nOfAirc; i++) {
        if (((Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isBusy()) continue; setTaskAndManeuver(i);
      }
  }

  public void dropBombs()
  {
    for (int i = 0; i < this.nOfAirc; i++) {
      if (((Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isBusy()) continue; ((Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).bombsOut = true;
    }
    if (this.friends[0] != null) {
      int j = AirGroupList.length(this.friends[0]);
      for (int k = 0; k < j; k++) {
        AirGroup localAirGroup = AirGroupList.getGroup(this.friends[0], k);
        if ((localAirGroup == null) || (localAirGroup.leaderGroup != this)) continue; localAirGroup.dropBombs();
      }
    }
  }

  public Aircraft firstOkAirc(int paramInt)
  {
    for (int i = 0; i < this.nOfAirc; i++) {
      if ((paramInt >= 0) && (paramInt < this.nOfAirc) && (
        (i == paramInt) || (
        (i == paramInt + 1) && (this.airc[i].aircIndex() == this.airc[paramInt].aircIndex() + 1))))
        continue;
      Maneuver localManeuver = (Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (((localManeuver.get_task() == 7) || (localManeuver.get_task() == 6) || (localManeuver.get_task() == 4)) && (localManeuver.isOk()))
      {
        return this.airc[i];
      }
    }
    return null;
  }

  public boolean waitGroup(int paramInt)
  {
    Aircraft localAircraft = firstOkAirc(paramInt);
    Maneuver localManeuver = (Maneuver)this.airc[paramInt].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    if (localAircraft != null) {
      localManeuver.airClient = localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      localManeuver.set_task(1);
      localManeuver.clear_stack();
      localManeuver.set_maneuver(59);
      return true;
    }
    localManeuver.set_task(3);
    localManeuver.clear_stack();
    localManeuver.set_maneuver(21);
    return false;
  }

  public void setGTargMode(int paramInt)
  {
    this.gTargetPreference = paramInt;
  }

  public void setGTargMode(Actor paramActor)
  {
    if ((paramActor != null) && (Actor.isAlive(paramActor))) {
      if (((paramActor instanceof BigshipGeneric)) || ((paramActor instanceof ShipGeneric)) || ((paramActor instanceof Chief)) || ((paramActor instanceof Bridge)))
      {
        this.gTargMode = 1;
        this.gTargActor = paramActor;
      } else {
        this.gTargMode = 2;
        this.gTargActor = paramActor;
        this.gTargPoint.set(paramActor.pos.getAbsPoint());
        this.gTargRadius = 200.0F;
        if ((paramActor instanceof BigshipGeneric)) {
          this.gTargRadius = 20.0F;
          setGTargMode(6);
        }
      }
    }
    else this.gTargMode = 0;
  }

  public void setGTargMode(Point3d paramPoint3d, float paramFloat)
  {
    this.gTargMode = 2;
    this.gTargPoint.set(paramPoint3d);
    this.gTargRadius = paramFloat;
  }

  public Actor setGAttackObject(int paramInt)
  {
    if (paramInt > this.nOfAirc - 1) return null;
    if (paramInt < 0) return null;
    Actor localActor = null;
    if (this.gTargMode == 1)
      localActor = War.GetRandomFromChief(this.airc[paramInt], this.gTargActor);
    else if (this.gTargMode == 2)
      localActor = War.GetNearestEnemy(this.airc[paramInt], this.gTargetPreference, this.gTargPoint, this.gTargRadius);
    else localActor = null;

    if (localActor != null) { this.gTargWasFound = true; this.gTargDestroyed = false; }
    if ((localActor == null) && (this.gTargWasFound)) { this.gTargDestroyed = true; this.gTargWasFound = false;
    }
    return localActor;
  }

  public void setATargMode(int paramInt)
  {
    this.aTargetPreference = paramInt;
  }

  public AirGroup chooseTargetGroup()
  {
    if (this.enemies == null) return null;
    int i = AirGroupList.length(this.enemies[0]);
    Object localObject = null;
    float f = 1.0E+012F;
    int j = 0;
    for (int k = 0; k < i; k++) {
      AirGroup localAirGroup = AirGroupList.getGroup(this.enemies[0], k);
      j = 0;
      if ((localAirGroup != null) && (localAirGroup.nOfAirc > 0)) {
        if (this.aTargetPreference == 9) j = 1;
        else if ((this.aTargetPreference == 7) && ((localAirGroup.airc[0] instanceof TypeFighter))) j = 1;
        else if ((this.aTargetPreference == 8) && (!(localAirGroup.airc[0] instanceof TypeFighter))) j = 1;
        if (j != 0) {
          for (int m = 0; m < localAirGroup.nOfAirc; m++) {
            if ((!Actor.isAlive(localAirGroup.airc[m])) || (!localAirGroup.airc[m].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isCapableOfBMP()) || (localAirGroup.airc[m].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage())) {
              continue;
            }
            j = 1;
            break;
          }
        }
        if (j != 0) {
          tmpV.sub(this.Pos, localAirGroup.Pos);
          if (tmpV.lengthSquared() < f) {
            localObject = localAirGroup;
            f = (float)tmpV.lengthSquared();
          }
        }
      }
    }
    return localObject;
  }

  public boolean somebodyAttacks()
  {
    int i = 0;
    for (int j = 0; j < this.nOfAirc; j++) {
      Maneuver localManeuver = (Maneuver)this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (((localManeuver instanceof RealFlightModel)) && (((RealFlightModel)localManeuver).isRealMode()) && (this.airc[j].aircIndex() == 0))
      {
        i = 1;
        break;
      }
      if ((!isWingman(j)) && (localManeuver.isOk()) && (localManeuver.hasCourseWeaponBullets())) {
        i = 1;
        break;
      }
    }
    return i;
  }

  public boolean somebodyGAttacks()
  {
    int i = 0;
    for (int j = 0; j < this.nOfAirc; j++) {
      Maneuver localManeuver = (Maneuver)this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (((localManeuver instanceof RealFlightModel)) && (((RealFlightModel)localManeuver).isRealMode()) && (this.airc[j].aircIndex() == 0))
      {
        i = 1;
        break;
      }
      if ((localManeuver.isOk()) && (localManeuver.get_task() != 1)) {
        i = 1;
        break;
      }
    }
    return i;
  }

  public void switchWayPoint()
  {
    Maneuver localManeuver = (Maneuver)this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    tmpV.sub(this.w.curr().getP(), localManeuver.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f1 = (float)tmpV.lengthSquared();
    int i = this.w.Cur();
    this.w.next();
    tmpV.sub(this.w.curr().getP(), localManeuver.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f2 = (float)tmpV.lengthSquared();
    this.w.setCur(i);
    if (f1 > f2) {
      String str = this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetName();
      this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
      this.w.next();
      if ((this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 0) && (this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget() == null)) this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().setTarget(str);
      if (this.w.curr().getTarget() == null) this.w.curr().setTarget(str);
    }
  }

  public boolean isWingman(int paramInt)
  {
    if (paramInt < 0) return false;
    Maneuver localManeuver = (Maneuver)this.airc[paramInt].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    if (((this.airc[paramInt].aircIndex() & 0x1) != 0) && (!localManeuver.aggressiveWingman)) {
      if (paramInt > 0) localManeuver.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = this.airc[(paramInt - 1)].jdField_FM_of_type_ComMaddoxIl2FmFlightModel; else
        return false;
      if ((localManeuver.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) && (this.airc[(paramInt - 1)].aircIndex() == this.airc[paramInt].aircIndex() - 1) && (this.enemyFighters) && (Actor.isAlive(this.airc[(paramInt - 1)])) && (((Maneuver)localManeuver.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel).isOk()))
        return true;
    }
    return false;
  }

  public Aircraft chooseTarget(AirGroup paramAirGroup)
  {
    Aircraft localAircraft = null;
    if ((paramAirGroup != null) && (paramAirGroup.nOfAirc > 0)) localAircraft = paramAirGroup.airc[com.maddox.il2.ai.World.Rnd().nextInt(0, paramAirGroup.nOfAirc - 1)];
    if ((localAircraft != null) && (
      (!Actor.isAlive(localAircraft)) || (!localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isCapableOfBMP()) || (localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage()))) {
      for (int i = 0; i < paramAirGroup.nOfAirc; i++) {
        if ((Actor.isAlive(paramAirGroup.airc[i])) && (paramAirGroup.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isCapableOfBMP()) && (!paramAirGroup.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage())) {
          localAircraft = paramAirGroup.airc[i];
        }
      }
    }
    return localAircraft;
  }

  public FlightModel setAAttackObject(int paramInt)
  {
    if (paramInt > this.nOfAirc - 1) return null;
    if (paramInt < 0) return null;

    Aircraft localAircraft = null;
    AirGroup localAirGroup = this.targetGroup;
    if ((localAirGroup == null) || (localAirGroup.nOfAirc == 0)) localAirGroup = chooseTargetGroup();
    localAircraft = chooseTarget(localAirGroup);

    if (localAircraft != null) { this.aTargWasFound = true; this.aTargDestroyed = false; }
    if ((localAircraft == null) && (this.aTargWasFound)) { this.aTargDestroyed = true; this.aTargWasFound = false;
    }
    if (localAircraft != null) return localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    return null;
  }

  public void setTaskAndManeuver(int paramInt)
  {
    if (paramInt > this.nOfAirc - 1) return;
    if (paramInt < 0) return;
    Maneuver localManeuver1 = (Maneuver)this.airc[paramInt].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    Maneuver localManeuver3;
    Maneuver localManeuver2;
    Maneuver localManeuver4;
    int i;
    switch (this.grTask)
    {
    case 1:
      localManeuver3 = null;
      localManeuver2 = null;
      localManeuver4 = null;
      tmpV.set(0.0D, 0.0D, 0.0D);
      i = 0; break;
    case 4:
    case 3:
    case 2:
    case 5:
    case 6: } while (true) { localManeuver3 = (Maneuver)this.airc[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      int j;
      if ((this.airc[i] instanceof TypeGlider)) { localManeuver3.accurate_set_FOLLOW(); } else {
        tmpV.add(localManeuver3.jdField_Offset_of_type_ComMaddoxJGPVector3d);
        if ((!localManeuver3.isBusy()) || (((localManeuver3 instanceof RealFlightModel)) && (((RealFlightModel)localManeuver3).isRealMode()) && (localManeuver3.isOk())))
        {
          localManeuver3.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = null;
          if ((this.leaderGroup == null) || (this.leaderGroup.nOfAirc == 0)) {
            localManeuver3.accurate_set_task_maneuver(3, 21);
          }
          else if (((Maneuver)this.leaderGroup.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isBusy()) {
            localManeuver3.accurate_set_task_maneuver(3, 21);
          } else {
            localManeuver3.accurate_set_FOLLOW();
            localManeuver3.followOffset.set(tmpV);
            localManeuver3.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = this.leaderGroup.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
          }

          tmpV.set(0.0D, 0.0D, 0.0D);
          for (j = i + 1; j < this.nOfAirc; j++) {
            localManeuver2 = (Maneuver)this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
            tmpV.add(localManeuver2.jdField_Offset_of_type_ComMaddoxJGPVector3d);
            if (!localManeuver2.isBusy()) {
              localManeuver2.accurate_set_FOLLOW();
              if ((this.airc[j] instanceof TypeGlider)) continue;
              if (localManeuver4 == null) {
                localManeuver2.followOffset.set(tmpV);
                localManeuver2.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = localManeuver3;
              } else {
                localManeuver2.followOffset.set(localManeuver2.jdField_Offset_of_type_ComMaddoxJGPVector3d);
                localManeuver2.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = localManeuver4;
              }
            }
            if ((localManeuver2 instanceof RealFlightModel)) {
              if ((this.airc[j].aircIndex() & 0x1) != 0) continue; localManeuver4 = localManeuver2; } else {
              localManeuver4 = null;
            }
          }
          break;
        }
      }
      i++; if (i < this.nOfAirc)
      {
        continue;
      }

      if ((goto 2405) || 
        (localManeuver1.isBusy()))
        break;
      if ((localManeuver1.target_ground == null) || (!Actor.isAlive(localManeuver1.target_ground)) || (localManeuver1.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(localManeuver1.target_ground.pos.getAbsPoint()) > 3000.0D))
      {
        localManeuver1.target_ground = setGAttackObject(paramInt);
      }if (localManeuver1.target_ground == null) {
        if ((waitGroup(paramInt)) || (paramInt != 0)) break;
        if (localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) {
          localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
        }
        setGroupTask(1);
      }
      else
      {
        if ((this.airc[paramInt] instanceof TypeDockable))
        {
          if ((this.airc[paramInt] instanceof I_16TYPE24DRONE))
            ((I_16TYPE24DRONE)this.airc[paramInt]).typeDockableAttemptDetach();
          if ((this.airc[paramInt] instanceof MXY_7))
            ((MXY_7)this.airc[paramInt]).typeDockableAttemptDetach();
          if ((this.airc[paramInt] instanceof G4M2E))
            ((G4M2E)this.airc[paramInt]).typeDockableAttemptDetach();
          if ((this.airc[paramInt] instanceof TB_3_4M_34R_SPB))
            ((TB_3_4M_34R_SPB)this.airc[paramInt]).typeDockableAttemptDetach();
        }
        if (((localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() == localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.size() - 1) && (localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3)) || ((this.airc[paramInt] instanceof MXY_7)))
        {
          localManeuver1.kamikaze = true;
          localManeuver1.set_task(7);
          localManeuver1.clear_stack();
          localManeuver1.set_maneuver(46);
        }
        else {
          j = 1;
          if (localManeuver1.hasRockets()) j = 0;
          if ((localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0] != null) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0] != null) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0].bulletMassa() > 0.05F) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0].countBullets() > 0))
          {
            j = 0;
          }if (((j != 0) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.getWeaponMass() < 7.0F)) || (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.getWeaponMass() < 1.0F)) {
            Voice.speakEndOfAmmo(this.airc[paramInt]);
            if ((waitGroup(paramInt)) || (paramInt != 0)) break;
            if (localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) {
              localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
            }
            setGroupTask(1);
          }
          else
          {
            int m;
            int n;
            if (((localManeuver1.target_ground instanceof Prey)) && ((((Prey)localManeuver1.target_ground).HitbyMask() & 0x1) == 0))
            {
              float f = 0.0F;
              for (m = 0; m < 4; m++) {
                if (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[m] != null) {
                  for (n = 0; n < localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[m].length; n++) {
                    if ((localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[m][n] == null) || (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[m][n].countBullets() == 0) || 
                      (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[m][n].bulletMassa() <= f)) continue;
                    f = localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[m][n].bulletMassa();
                  }
                }
              }

              if ((f < 0.08F) || (((localManeuver1.target_ground instanceof TgtShip)) && (f < 0.55F)))
              {
                localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
                localManeuver1.set_task(1);
                localManeuver1.clear_stack();
                localManeuver1.set_maneuver(21);
                localManeuver1.target_ground = null;
                break;
              }
            }

            if ((localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].countBullets() != 0)) {
              if ((localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof TorpedoGun)) {
                if ((localManeuver1.target_ground instanceof TgtShip)) {
                  localManeuver1.set_task(7);
                  localManeuver1.clear_stack();
                  localManeuver1.set_maneuver(51);
                } else {
                  localManeuver1.set_task(7);
                  localManeuver1.clear_stack();
                  localManeuver1.set_maneuver(43);
                }
              }
              else if ((localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof BombGunPara)) {
                this.w.curr().setTarget(null);
                localManeuver1.target_ground = null;
                this.grTask = 1;
                setTaskAndManeuver(paramInt);
              }
              else if ((localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].bulletMassa() < 10.0F) && (!(localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof BombGunParafrag8))) {
                localManeuver1.set_task(7);
                localManeuver1.clear_stack();
                localManeuver1.set_maneuver(52);
              }
              else if (((this.airc[paramInt] instanceof TypeDiveBomber)) && (localManeuver1.Alt > 1200.0F)) {
                localManeuver1.set_task(7);
                localManeuver1.clear_stack();
                localManeuver1.set_maneuver(50);
              }
              else {
                localManeuver1.set_task(7);
                localManeuver1.clear_stack();
                localManeuver1.set_maneuver(43);
              }
            }
            else if (((localManeuver1.target_ground instanceof BridgeSegment)) && (!localManeuver1.hasRockets())) {
              localManeuver1.set_task(1);
              localManeuver1.clear_stack();
              localManeuver1.set_maneuver(59);
              localManeuver1.target_ground = null;
            }
            else if (((this.airc[paramInt] instanceof F4U)) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2] != null) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][0].bulletMassa() > 100.0D) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][0].countBullets() > 0))
            {
              localManeuver1.set_task(7);
              localManeuver1.clear_stack();
              localManeuver1.set_maneuver(47);
            }
            else if (((this.airc[paramInt] instanceof TypeFighter)) || ((this.airc[paramInt] instanceof TypeStormovik))) {
              localManeuver1.set_task(7);
              localManeuver1.clear_stack();
              localManeuver1.set_maneuver(43);
            }
            else {
              this.w.curr().setTarget(null);
              localManeuver1.target_ground = null;
              this.grTask = 1;
              setTaskAndManeuver(paramInt);
              this.grTask = 4;

              break;

              if (localManeuver1.isBusy()) break;
              if ((!(localManeuver1 instanceof RealFlightModel)) || (!((RealFlightModel)localManeuver1).isRealMode())) {
                localManeuver1.bombsOut = true;
                localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
              }
              if (isWingman(paramInt))
              {
                localManeuver1.airClient = localManeuver1.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel;
                localManeuver1.followOffset.set(200.0D, 0.0D, 20.0D);
                localManeuver1.set_task(5);
                localManeuver1.clear_stack();
                localManeuver1.set_maneuver(65);
              }
              else
              {
                localManeuver1.airClient = null;
                int k = 1;
                if (inCorridor(localManeuver1.jdField_Loc_of_type_ComMaddoxJGPPoint3d)) k = 0;
                if (k != 0) {
                  m = this.w.Cur();
                  this.w.next();
                  if (inCorridor(localManeuver1.jdField_Loc_of_type_ComMaddoxJGPPoint3d)) k = 0;
                  this.w.setCur(m);
                  if (k != 0) {
                    m = this.w.Cur();
                    this.w.prev();
                    if (inCorridor(localManeuver1.jdField_Loc_of_type_ComMaddoxJGPPoint3d)) k = 0;
                    this.w.setCur(m);
                  }
                }
                if (k != 0) {
                  localManeuver1.set_task(3);
                  localManeuver1.clear_stack();
                  localManeuver1.set_maneuver(21);
                }
                else
                {
                  if ((localManeuver1.target == null) || (!((Maneuver)localManeuver1.target).isOk()) || (localManeuver1.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(localManeuver1.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 4000.0D))
                    localManeuver1.target = setAAttackObject(paramInt);
                  if ((localManeuver1.target == null) || (!localManeuver1.hasCourseWeaponBullets())) {
                    if ((waitGroup(paramInt)) || (paramInt != 0)) break;
                    setGroupTask(1);
                  }
                  else
                  {
                    localManeuver1.set_task(6);
                    if ((localManeuver1.target.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) {
                      if (((localManeuver1.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeBNZFighter)) || (localManeuver1.VmaxH > localManeuver1.target.VmaxH + 30.0F) || (((localManeuver1.target.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTNBFighter)) && (!(localManeuver1.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTNBFighter))))
                      {
                        localManeuver1.clear_stack();
                        localManeuver1.set_maneuver(62);
                      }
                      else {
                        localManeuver1.clear_stack();
                        localManeuver1.set_maneuver(27);
                      }
                    }
                    else if ((localManeuver1.target.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik))
                    {
                      ((Pilot)localManeuver1).attackStormoviks();
                    }
                    else
                    {
                      ((Pilot)localManeuver1).attackBombers();

                      break;

                      localManeuver3 = null;
                      localManeuver2 = null;
                      localManeuver4 = null;
                      tmpV.set(0.0D, 0.0D, 0.0D);
                      m = 0;
                      while (true) { localManeuver3 = (Maneuver)this.airc[m].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
                        tmpV.add(localManeuver3.jdField_Offset_of_type_ComMaddoxJGPVector3d);
                        if ((!localManeuver3.isBusy()) || (m == this.nOfAirc - 1) || (((localManeuver3 instanceof RealFlightModel)) && (((RealFlightModel)localManeuver3).isRealMode())))
                        {
                          localManeuver3.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = null;
                          if ((this.clientGroup != null) && (this.clientGroup.nOfAirc > 0) && (this.clientGroup.airc[0] != null)) {
                            localManeuver3.airClient = this.clientGroup.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
                            localManeuver3.accurate_set_task_maneuver(5, 59);
                          } else {
                            localManeuver3.accurate_set_task_maneuver(3, 21);
                          }
                          tmpV.set(0.0D, 0.0D, 0.0D);
                          for (n = m + 1; n < this.nOfAirc; n++) {
                            localManeuver2 = (Maneuver)this.airc[n].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
                            tmpV.add(localManeuver2.jdField_Offset_of_type_ComMaddoxJGPVector3d);
                            if (!localManeuver2.isBusy()) {
                              localManeuver2.accurate_set_FOLLOW();
                              if (localManeuver4 == null) {
                                localManeuver2.followOffset.set(tmpV);
                                localManeuver2.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = localManeuver3;
                              } else {
                                localManeuver2.followOffset.set(localManeuver2.jdField_Offset_of_type_ComMaddoxJGPVector3d);
                                localManeuver2.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = localManeuver4;
                              }
                            }
                            if ((localManeuver2 instanceof RealFlightModel)) {
                              if ((this.airc[n].aircIndex() & 0x1) != 0) continue; localManeuver4 = localManeuver2; } else {
                              localManeuver4 = null;
                            }
                          }
                        }
                        else
                        {
                          m++; if (m < this.nOfAirc)
                          {
                            continue;
                          }

                          if ((goto 2405) || 
                            (!localManeuver1.isBusy())) break; return;

                          if (!localManeuver1.isBusy()) break; return;

                          if (localManeuver1.isBusy()) return;
                          localManeuver1.set_maneuver(21);
                        } } 
                    }
                  }
                }
              }
            }
          }
        }
      } }
  }

  public void update() {
    if ((this.nOfAirc == 0) || (this.airc[0] == null)) return;
    for (int i = 1; i < this.nOfAirc; i++) {
      if (!Actor.isAlive(this.airc[i])) {
        delAircraft(this.airc[i]);
        i--;
      }
    }
    Maneuver localManeuver1 = (Maneuver)this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    if (this.leaderGroup != null) {
      if (this.leaderGroup.nOfAirc == 0) { detachGroup(this.leaderGroup);
      } else if (this.leaderGroup.airc[0] == null) { detachGroup(this.leaderGroup);
      }
      else if (this.leaderGroup.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()) {
        detachGroup(this.leaderGroup);
      } else {
        localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(this.leaderGroup.w.Cur());
        if ((localManeuver1.get_maneuver() == 21) && (!((Maneuver)this.leaderGroup.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isBusy())) {
          setTaskAndManeuver(0);
        }
      }

    }

    if (this.w == null) this.w = new Way(localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way);
    if ((!localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()) && (localManeuver1.isOk())) this.w.setCur(localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur());
    int j;
    if (!localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding())
      for (j = 1; j < this.nOfAirc; j++) {
        if ((((Maneuver)this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()) || (((Maneuver)this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isBusy()))
          continue;
        ((Maneuver)this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(this.w.Cur());
      }
    if (localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().isRadioSilence())
      for (j = 0; j < this.nOfAirc; j++) ((Maneuver)this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).silence = true;
    else {
      for (j = 0; j < this.nOfAirc; j++) ((Maneuver)this.airc[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).silence = false;
    }

    this.Pos.set(localManeuver1.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

    if (this.formationType == -1) setFormationAndScale(0, 1.0F, true);

    if (this.timeOutForTaskSwitch == 0)
    {
      int n;
      Object localObject;
      switch (this.w.curr().Action) {
      case 3:
        j = (this.w.curr().getTarget() != null) || ((this.airc[0] instanceof TypeFighter)) || ((this.airc[0] instanceof TypeStormovik)) || ((this.airc[0] instanceof D3A)) || ((this.airc[0] instanceof MXY_7)) || ((this.airc[0] instanceof JU_87)) ? 1 : 0;

        if (this.grTask == 4) {
          this.WeWereInGAttack = true;
          bool1 = somebodyGAttacks();
          n = 0;
          for (int i2 = 0; i2 < this.nOfAirc; i2++) {
            Maneuver localManeuver2 = (Maneuver)this.airc[i2].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
            if (localManeuver2.gattackCounter < 7) continue; n = 1;
          }
          if ((!bool1) || (n != 0) || (this.gTargDestroyed)) {
            this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
            this.w.next();
            setGroupTask(1);
            for (int i4 = 1; i4 < this.nOfAirc; i4++) {
              Maneuver localManeuver4 = (Maneuver)this.airc[i4].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
              localManeuver4.push(57);
              localManeuver4.pop();
            }
            setFormationAndScale(0, 1.0F, true);
            if (n != 0)
              for (int i6 = 0; i6 < this.nOfAirc; i6++) ((Maneuver)this.airc[i6].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).gattackCounter = 0;
          }
        }
        else if (this.grTask == 3) {
          switchWayPoint();
          this.WeWereInGAttack = true;
          if (AirGroupList.length(this.enemies[0]) != this.oldEnemyNum) setGroupTask(3);
          bool1 = somebodyAttacks();
          if ((!bool1) || (this.aTargDestroyed)) {
            setGroupTask(1);
            if (!bool1) this.timeOutForTaskSwitch = 90;
            for (n = 1; n < this.nOfAirc; n++) {
              localObject = (Maneuver)this.airc[n].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
              if (!((Maneuver)localObject).isBusy()) {
                ((Maneuver)localObject).push(57);
                ((Maneuver)localObject).pop();
              }
            }
          }
        }
        if ((this.grTask != 1) || (this.w.curr().Action != 3)) break;
        this.gTargWasFound = false; this.gTargDestroyed = false;
        this.gTargMode = 0;
        if (j == 0) break;
        setFormationAndScale(5, 8.0F, true);
        if (localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance() >= 5000.0F) break;
        boolean bool1 = false;
        if (this.w.curr().getTarget() != null) {
          setGTargMode(this.w.curr().getTarget());
          if (this.gTargMode != 0) {
            localManeuver1.target_ground = setGAttackObject(0);
            if ((localManeuver1.target_ground != null) && (localManeuver1.target_ground.distance(this.airc[0]) < 12000.0D))
            {
              setGroupTask(4);
              Voice.speakBeginGattack(this.airc[0]);
            } else if (localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance() < 1500.0F) { bool1 = true; } 
          } else {
            bool1 = true;
          } } else {
          bool1 = true;
        }if (!bool1) break;
        Engine.land(); tmpP3d.set(this.w.curr().x(), this.w.curr().y(), Landscape.HQ(this.w.curr().x(), this.w.curr().y()));
        setGTargMode(tmpP3d, 800.0F);
        localManeuver1.target_ground = setGAttackObject(0);
        if (localManeuver1.target_ground == null) break;
        setGroupTask(4);
        Voice.speakBeginGattack(this.airc[0]); break;
      case 0:
      case 2:
        if (this.grTask == 2)
        {
          int k;
          if (this.enemyFighters) {
            k = AirGroupList.length(this.enemies[0]);
            for (n = 0; n < k; n++) {
              localObject = AirGroupList.getGroup(this.enemies[0], n);
              if ((((AirGroup)localObject).nOfAirc > 0) && ((localObject.airc[0] instanceof TypeFighter))) {
                this.targetGroup = ((AirGroup)localObject);
                setGroupTask(3);
                break;
              }
            }
          }
          if (this.w.Cur() >= this.w.size() - 1) {
            setGroupTask(1);
            setFormationAndScale(0, 1.0F, true);
          }
          if ((this.clientGroup == null) || (this.clientGroup.nOfAirc == 0) || (this.clientGroup.w.Cur() >= this.clientGroup.w.size() - 1) || (this.clientGroup.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()))
          {
            localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
            this.w.setCur(localManeuver1.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur());
            for (k = 1; k < this.nOfAirc; k++) ((Maneuver)this.airc[k].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(this.w.Cur());
            setGroupTask(1);
            setFormationAndScale(0, 1.0F, true);
          }
          switchWayPoint();
        }
        else
        {
          boolean bool2;
          if (this.grTask == 3) {
            switchWayPoint();
            this.WeWereInGAttack = true;
            if (AirGroupList.length(this.enemies[0]) != this.oldEnemyNum) setGroupTask(3);
            bool2 = somebodyAttacks();
            if ((bool2) && (!this.aTargDestroyed)) break;
            setGroupTask(1);
            setFormationAndScale(0, 1.0F, false);
            if (!bool2) this.timeOutForTaskSwitch = 90;
            for (n = 1; n < this.nOfAirc; n++) {
              localObject = (Maneuver)this.airc[n].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
              if (!((Maneuver)localObject).isBusy()) {
                ((Maneuver)localObject).push(57);
                ((Maneuver)localObject).pop();
              }
            }
          }
          else
          {
            int i3;
            int i5;
            if (this.grTask == 4) {
              this.WeWereInGAttack = true;
              bool2 = somebodyGAttacks();
              n = 0;
              for (i3 = 0; i3 < this.nOfAirc; i3++) {
                Maneuver localManeuver3 = (Maneuver)this.airc[i3].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
                if (localManeuver3.gattackCounter < 7) continue; n = 1;
              }
              if ((bool2) && (n == 0) && (!this.gTargDestroyed)) break;
              setGroupTask(1);
              setFormationAndScale(0, 1.0F, true);
              for (i5 = 1; i5 < this.nOfAirc; i5++) {
                Maneuver localManeuver5 = (Maneuver)this.airc[i5].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
                localManeuver5.push(57);
                localManeuver5.pop();
              }
              if (n == 0) break;
              for (int i7 = 0; i7 < this.nOfAirc; i7++) ((Maneuver)this.airc[i7].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).gattackCounter = 0; 
            }
            else
            {
              if (this.grTask != 1) break;
              if ((this.WeWereInGAttack) || (this.gTargMode != 0)) {
                this.WeWereInGAttack = false;
                this.gTargMode = 0;
                setFormationAndScale(0, 1.0F, false);
                ((Maneuver)this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).WeWereInGAttack = true;
              }
              if (this.WeWereInAttack) {
                this.WeWereInAttack = false;
                setFormationAndScale(0, 1.0F, false);
                ((Maneuver)this.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).WeWereInAttack = true;
              }
              if ((this.w.Cur() > 0) && (this.grAttached == 0) && (this.oldFType == 0)) {
                this.w.curr().getP(tmpP);
                tmpV.sub(tmpP, this.Pos);
                if (tmpV.lengthSquared() < 4000000.0D) setFormationAndScale(0, 2.5F, true); else
                  setFormationAndScale(0, 1.0F, true);
              }
              int m = this.w.Cur();
              this.w.next();
              if ((this.w.curr().Action == 2) || ((this.w.curr().Action == 3) && (((this.w.curr().getTarget() != null) && (!(this.airc[0] instanceof Scheme4))) || ((this.airc[0] instanceof TypeStormovik)) || ((this.airc[0] instanceof JU_87)))))
              {
                this.w.curr().getP(tmpP);
                tmpV.sub(tmpP, this.Pos);
                float f = (float)tmpV.length();
                if (f < 20000.0F) setFormationAndScale(5, 8.0F, true);
              }
              this.w.setCur(m);
              if (this.w.curr().getTarget() != null) {
                Actor localActor = this.w.curr().getTargetActorRandom();
                if ((localActor != null) && ((localActor instanceof Aircraft))) {
                  tmpV.sub(((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.Pos);
                  if (tmpV.lengthSquared() < 144000000.0D) {
                    if (localActor.getArmy() == this.airc[0].getArmy()) {
                      if (((this.airc[0] instanceof TypeFighter)) && (!localManeuver1.hasBombs()) && (!localManeuver1.hasRockets())) {
                        if (this.w.Cur() < this.w.size() - 2) {
                          this.clientGroup = ((Maneuver)((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group;
                          setGroupTask(2);
                          setFormationAndScale(0, 2.5F, true);
                        }
                      }
                      else attachGroup(((Maneuver)((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group);

                    }
                    else if (((this.airc[0] instanceof TypeFighter)) || ((this.airc[0] instanceof TypeStormovik))) {
                      this.targetGroup = ((Maneuver)((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group;
                      setGroupTask(3);
                    }
                  }
                }

              }
              else if (AirGroupList.length(this.enemies[0]) > 0) {
                int i1 = 0;
                if ((this.airc[0] instanceof TypeStormovik)) {
                  i3 = AirGroupList.length(this.enemies[0]);
                  for (i5 = 0; i5 < i3; i5++) {
                    AirGroup localAirGroup = AirGroupList.getGroup(this.enemies[0], i5);
                    if ((localAirGroup != null) && (localAirGroup.nOfAirc != 0) && (!(localAirGroup.airc[0] instanceof TypeFighter))) {
                      i1 = 1;
                      this.targetGroup = localAirGroup;
                      break;
                    }
                  }
                  if (i1 != 0) {
                    if (localManeuver1.hasBombs())
                      i1 = 0;
                    m = this.w.Cur();
                    while ((i1 != 0) && (this.w.Cur() < this.w.size() - 1)) {
                      if (this.w.curr().Action == 3) i1 = 0;
                      this.w.next();
                    }
                    this.w.setCur(m);
                  }
                }
                if ((i1 == 0) && ((this.airc[0] instanceof TypeFighter))) {
                  for (i3 = 0; i3 < this.nOfAirc; i3++) {
                    if (!((Maneuver)this.airc[i3].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).canAttack()) continue; i1 = 1;
                  }if ((i1 != 0) && (localManeuver1.jdField_CT_of_type_ComMaddoxIl2FmControls.getWeaponMass() > 220.0F)) {
                    i1 = 0;
                  }
                }
                if (i1 != 0) setGroupTask(3);
              }

              if (this.rejoinGroup == null) break; rejoinToGroup(this.rejoinGroup); } 
          }
        }break;
      case 1:
      default:
        this.grTask = 1;
      }
    }
    this.oldEnemyNum = AirGroupList.length(this.enemies[0]);
    if (this.timeOutForTaskSwitch > 0) this.timeOutForTaskSwitch -= 1;
  }
}