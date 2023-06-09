package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class JU_87 extends Scheme1
  implements TypeDiveBomber
{
  public int diveMechStage = 0;
  public boolean bNDives = false;
  private boolean bDropsBombs = false;
  private long dropStopTime = -1L;

  public static boolean bChangedPit = false;

  public float fDiveRecoveryAlt = 750.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.AS.wantBeaconsNet(true);
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    if (paramFloat > 0.0F) {
      hierMesh().chunkSetAngles("AroneRodL_D0", 0.0F, -26.5F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("AroneRodR_D0", 0.0F, -27.5F * paramFloat, 0.0F);
    } else {
      hierMesh().chunkSetAngles("AroneRodL_D0", 0.0F, -27.5F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("AroneRodR_D0", 0.0F, -26.5F * paramFloat, 0.0F);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("FlapRod01_D0", 0.0F, 0.75F * f, 0.0F);
    hierMesh().chunkSetAngles("FlapRod02_D0", 0.0F, 0.95F * f, 0.0F);
    hierMesh().chunkSetAngles("FlapRod03_D0", 0.0F, 0.95F * f, 0.0F);
    hierMesh().chunkSetAngles("FlapRod04_D0", 0.0F, 0.75F * f, 0.0F);
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true;
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.35F, 0.0F, 0.3F);
    hierMesh().chunkSetLocate("GearL33_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.35F, 0.0F, 0.3F);
    hierMesh().chunkSetLocate("GearR33_D0", xyz, ypr);
  }

  public void update(float paramFloat)
  {
    if ((this == World.getPlayerAircraft()) && ((this.FM instanceof RealFlightModel)))
    {
      if (((RealFlightModel)this.FM).isRealMode()) { switch (this.diveMechStage) {
        case 0:
          if ((this.bNDives) && (this.FM.CT.AirBrakeControl == 1.0F) && (this.FM.Loc.z > this.fDiveRecoveryAlt)) {
            this.diveMechStage += 1;
            this.bNDives = false;
          }
          else {
            this.bNDives = (this.FM.CT.AirBrakeControl != 1.0F);
          }break;
        case 1:
          this.FM.CT.setTrimElevatorControl(-0.65F);
          this.FM.CT.trimElevator = -0.65F;
          if ((this.FM.CT.AirBrakeControl != 0.0F) && (this.FM.CT.saveWeaponControl[3] == 0) && ((this.FM.CT.Weapons[3] == null) || (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].countBullets() != 0)) && ((this.FM.Loc.z >= this.fDiveRecoveryAlt) || ((this instanceof JU_87D5))))
          {
            break;
          }
          if (this.FM.CT.AirBrakeControl == 0.0F) {
            this.diveMechStage += 1;
          }
          if ((this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].countBullets() == 0)) {
            this.diveMechStage += 1;
          }
          if (this.FM.Loc.z >= this.fDiveRecoveryAlt) break;
          this.diveMechStage += 1;
          if (!World.cur().diffCur.Limited_Ammo) break;
          this.bDropsBombs = true; break;
        case 2:
          if (this.FM.isTick(41, 0)) {
            this.FM.CT.setTrimElevatorControl(0.85F);
            this.FM.CT.trimElevator = 0.85F;
          }
          if ((this.FM.CT.AirBrakeControl != 0.0F) && (this.FM.Or.getTangage() <= 0.0F)) break; this.diveMechStage += 1; break;
        case 3:
          this.FM.CT.setTrimElevatorControl(0.0F);
          this.FM.CT.trimElevator = 0.0F;
          this.diveMechStage = 0;
        }
      } else {
        this.FM.CT.setTrimElevatorControl(0.0F);
        this.FM.CT.trimElevator = 0.0F;
      }

    }

    if ((this.bDropsBombs) && 
      (this.FM.isTick(3, 0)) && 
      (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].haveBullets())) {
      this.FM.CT.WeaponControl[3] = true;
    }

    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F) {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
    } else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
    }
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 1:
      if (this.FM.turret.length <= 0) break;
      this.FM.turret[0].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    if (paramArrayOfFloat[0] < -25.0F) { paramArrayOfFloat[0] = -25.0F; bool = false;
    } else if (paramArrayOfFloat[0] > 25.0F) { paramArrayOfFloat[0] = 25.0F; bool = false; }
    float f1 = Math.abs(paramArrayOfFloat[0]);
    if (paramArrayOfFloat[1] < -10.0F) { paramArrayOfFloat[1] = -10.0F; bool = false; }
    if (paramArrayOfFloat[1] > 45.0F) { paramArrayOfFloat[1] = 45.0F; bool = false; }
    if (!bool) return false;

    float f2 = paramArrayOfFloat[1];
    if ((f1 < 1.2F) && (f2 < 13.3F)) return false;
    return (f2 >= -3.1F) || (f2 <= -4.6F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      this.FM.AS.hitTank(this, 0, 6);
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      this.FM.AS.hitTank(this, 1, 6);
      return super.cutFM(37, paramInt2, paramActor);
    case 34:
      this.FM.cut(9, paramInt2, paramActor);
      break;
    case 37:
      this.FM.cut(10, paramInt2, paramActor);
    case 35:
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);

    if (paramExplosion.chunkName != null) {
      if (paramExplosion.chunkName.startsWith("CF")) {
        if (World.Rnd().nextFloat() < 0.005F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 0);
        if (World.Rnd().nextFloat() < 0.005F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 1);
        if (World.Rnd().nextFloat() < 0.005F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 2);
      }
      if ((paramExplosion.chunkName.startsWith("Engine")) && 
        (paramExplosion.power > 0.011F)) {
        this.FM.AS.hitOil(paramExplosion.initiator, 0);
      }

      if (paramExplosion.chunkName.startsWith("Tail")) {
        if (World.Rnd().nextFloat() < 0.01F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 1);
        if (World.Rnd().nextFloat() < 0.11F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 2);
      }
    }
    super.msgExplosion(paramExplosion);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.61F)) {
      this.FM.AS.hitTank(paramShot.initiator, 0, World.Rnd().nextInt(1, 2));
    }

    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.61F)) {
      this.FM.AS.hitTank(paramShot.initiator, 1, World.Rnd().nextInt(1, 2));
    }

    if (paramShot.chunkName.startsWith("Tail")) {
      if (World.Rnd().nextFloat() < 0.005F) this.FM.AS.setControlsDamage(paramShot.initiator, 1);
      if (World.Rnd().nextFloat() < 0.005F) this.FM.AS.setControlsDamage(paramShot.initiator, 2);
    }

    if ((paramShot.chunkName.startsWith("Wing")) && (getEnergyPastArmor(4.35F, paramShot) > 0.0F) && 
      (World.Rnd().nextFloat() < 0.05F)) this.FM.AS.setControlsDamage(paramShot.initiator, 0);

    if (paramShot.chunkName.startsWith("Engine")) {
      if (World.Rnd().nextFloat() < 9.999F * paramShot.mass) {
        this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
      }
      if ((paramShot.chunkName.endsWith("_D2")) && (World.Rnd().nextFloat() < 0.75F)) {
        return;
      }
    }

    if (paramShot.chunkName.startsWith("Pilot1")) {
      if ((Pd.z > 0.7599999904632568D) && (v1.x < 0.0D)) {
        killPilot(paramShot.initiator, 0);
        if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");
        return;
      }
      if (paramShot.power * Math.abs(v1.x) > 10370.0D) {
        this.FM.AS.hitPilot(paramShot.initiator, 1, (int)(paramShot.mass * 1000.0F * 0.5F));
      }
      paramShot.chunkName = "CF_D0";
    }

    if (paramShot.chunkName.startsWith("Pilot2")) {
      if ((Pd.z > 0.7599999904632568D) && (v1.x > 0.0D)) {
        killPilot(paramShot.initiator, 1);
        if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");
        return;
      }
      if (paramShot.power * Math.abs(v1.x) > 10370.0D) {
        this.FM.AS.hitPilot(paramShot.initiator, 1, (int)(paramShot.mass * 1000.0F * 0.5F));
      }
      paramShot.chunkName = "CF_D0";
    }

    super.msgShot(paramShot);
  }

  public boolean typeDiveBomberToggleAutomation()
  {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset() {
  }

  public void typeDiveBomberAdjAltitudePlus() {
    this.fDiveRecoveryAlt += 25.0F;
    if (this.fDiveRecoveryAlt > 6000.0F) {
      this.fDiveRecoveryAlt = 6000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fDiveRecoveryAlt) });
  }

  public void typeDiveBomberAdjAltitudeMinus() {
    this.fDiveRecoveryAlt -= 25.0F;
    if (this.fDiveRecoveryAlt < 0.0F) {
      this.fDiveRecoveryAlt = 0.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fDiveRecoveryAlt) });
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus()
  {
  }

  public void typeDiveBomberAdjVelocityMinus()
  {
  }

  public void typeDiveBomberAdjDiveAngleReset()
  {
  }

  public void typeDiveBomberAdjDiveAnglePlus() {
  }

  public void typeDiveBomberAdjDiveAngleMinus() {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = JU_87.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}