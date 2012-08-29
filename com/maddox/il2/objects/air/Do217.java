package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;
import java.io.PrintStream;

public abstract class Do217 extends Scheme2
  implements TypeBomber, TypeTransport
{
  public boolean bSightAutomation = false;
  private boolean bSightBombDump = false;
  public float fSightCurDistance = 0.0F;
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightSetForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;
  public float fSightCurReadyness = 0.0F;

  public boolean bPitUnfocused = true;
  private Maneuver maneuver = null;

  private float wheel1 = 0.0F;
  private float wheel2 = 0.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    this.FM.Gears.computePlaneLandPose(this.FM);
  }

  public boolean typeBomberToggleAutomation()
  {
    this.bSightAutomation = (!this.bSightAutomation);
    this.bSightBombDump = false;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (this.bSightAutomation ? "ON" : "OFF"));

    return this.bSightAutomation;
  }

  public void typeBomberAdjDistanceReset() {
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus() {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F)
      this.fSightCurForwardAngle = 85.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus() {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F)
      this.fSightCurForwardAngle = 0.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset() {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 0.1F;
    if (this.fSightCurSideslip > 3.0F)
      this.fSightCurSideslip = 3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 0.1F;
    if (this.fSightCurSideslip < -3.0F)
      this.fSightCurSideslip = -3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 850.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F)
      this.fSightCurAltitude = 10000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 850.0F)
      this.fSightCurAltitude = 850.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 150.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 600.0F)
      this.fSightCurSpeed = 600.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 150.0F)
      this.fSightCurSpeed = 150.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F)
        this.fSightCurReadyness = 0.0F;
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    } else if (this.bSightAutomation) {
      this.fSightCurDistance -= this.fSightCurSpeed / 3.6F * paramFloat;
      if (this.fSightCurDistance < 0.0F) {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / this.fSightCurAltitude));

      if (this.fSightCurDistance < this.fSightCurSpeed / 3.6F * Math.sqrt(this.fSightCurAltitude * 0.203874F))
      {
        this.bSightBombDump = true;
      }if (this.bSightBombDump)
        if (this.FM.isTick(3, 0)) {
          if ((this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].haveBullets()))
          {
            this.FM.CT.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else
          this.FM.CT.WeaponControl[3] = false;
    }
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeByte((this.bSightAutomation ? 1 : 0) | (this.bSightBombDump ? 2 : 0));

    paramNetMsgGuaranted.writeFloat(this.fSightCurDistance);
    paramNetMsgGuaranted.writeByte((int)this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeByte((int)((this.fSightCurSideslip + 3.0F) * 33.333328F));

    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurSpeed / 2.5F));
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurReadyness * 200.0F));
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
    int i = paramNetMsgInput.readUnsignedByte();
    this.bSightAutomation = ((i & 0x1) != 0);
    this.bSightBombDump = ((i & 0x2) != 0);
    this.fSightCurDistance = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readUnsignedByte();
    this.fSightCurSideslip = (-3.0F + paramNetMsgInput.readUnsignedByte() / 33.333328F);

    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = (paramNetMsgInput.readUnsignedByte() * 2.5F);
    this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean)
    {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.39F))
        this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateEngineStates[1] > 3) && (World.Rnd().nextFloat() < 0.39F))
        this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateEngineStates[2] > 3) && (World.Rnd().nextFloat() < 0.39F)) {
        this.FM.AS.hitTank(this, 2, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.1F)) {
        nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
      }
    }

    for (int i = 1; i <= 4; i++)
    {
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("hmask" + i + "_d0", false);
      else
        hierMesh().chunkVisible("hmask" + i + "_d0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
    }
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      this.FM.turret[3].setHealth(paramFloat);
      this.FM.turret[4].setHealth(paramFloat);
      break;
    case 2:
      this.FM.turret[1].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[2].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("hmask1_d0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("hmask2_d0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("hmask3_d0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("hmask4_d0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      hitProp(0, paramInt2, paramActor);
      break;
    case 36:
      hitProp(1, paramInt2, paramActor);
      break;
    case 35:
      this.FM.AS.hitEngine(this, 0, 3);
      if (World.Rnd().nextInt(0, 99) >= 66) break;
      this.FM.AS.hitEngine(this, 0, 1); break;
    case 38:
      this.FM.AS.hitEngine(this, 1, 3);
      if (World.Rnd().nextInt(0, 99) >= 66) break;
      this.FM.AS.hitEngine(this, 1, 1); break;
    case 11:
      hierMesh().chunkVisible("Wire1_D0", false);
      break;
    case 19:
      hierMesh().chunkVisible("Wire1_D0", false);
      this.FM.Gears.hitCentreGear();
      break;
    case 13:
      killPilot(this, 0);
      killPilot(this, 1);
      killPilot(this, 2);
      killPilot(this, 3);
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, Aircraft.cvt(paramFloat, -65.0F, 65.0F, 65.0F, -65.0F), 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    case 0:
      if (f2 > 45.0F)
      {
        f2 = 45.0F;
        bool = false;
      }
      if (f2 < -40.0F)
      {
        f2 = -40.0F;
        bool = false;
      }

      if (f1 > 50.0F)
      {
        f1 = 50.0F;
        bool = false;
      }
      if (f1 >= -25.0F)
        break;
      f1 = -25.0F;
      bool = false; break;
    case 1:
      if (f2 > 80.0F)
      {
        f2 = 80.0F;
        bool = false;
      }

      if (f2 < -3.0F)
      {
        f2 = -3.0F;
        bool = false;
      }

      if ((f1 > 135.0F) || (f1 < -135.0F))
      {
        if (f2 >= 45.0F)
          break;
        f2 = 45.0F;
        bool = false;
      }
      else
      {
        if ((f1 <= 110.0F) && (f1 >= -110.0F))
          break;
        if (f2 >= 0.0F)
          break;
        f2 = 0.0F;
        bool = false; } break;
    case 2:
      if (f2 > 45.0F)
      {
        f2 = 45.0F;
        bool = false;
      }
      if (f2 < -40.0F)
      {
        f2 = -40.0F;
        bool = false;
      }

      if (f1 > 50.0F)
      {
        f1 = 50.0F;
        bool = false;
      }
      if (f1 >= -50.0F)
        break;
      f1 = -50.0F;
      bool = false; break;
    case 3:
      if (f2 > 35.0F)
      {
        f2 = 35.0F;
        bool = false;
      }
      if (f2 < 0.0F)
      {
        f2 = 0.0F;
        bool = false;
      }

      if (f1 > 65.0F)
      {
        f1 = 65.0F;
        bool = false;
      }
      if (f1 >= -30.0F)
        break;
      f1 = -30.0F;
      bool = false; break;
    case 4:
      if (f2 > 35.0F)
      {
        f2 = 35.0F;
        bool = false;
      }
      if (f2 < 0.0F)
      {
        f2 = 0.0F;
        bool = false;
      }

      if (f1 > 30.0F)
      {
        f1 = 30.0F;
        bool = false;
      }
      if (f1 >= -65.0F)
        break;
      f1 = -65.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void moveWheelSink()
  {
    this.wheel1 = (0.8F * this.wheel1 + 0.2F * this.FM.Gears.gWheelSinking[0]);
    this.wheel2 = (0.8F * this.wheel2 + 0.2F * this.FM.Gears.gWheelSinking[1]);
    resetYPRmodifier();

    Aircraft.xyz[1] = (-Aircraft.cvt(this.wheel1, 0.0F, 0.3F, 0.0F, 0.3F));
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);

    Aircraft.xyz[1] = (-Aircraft.cvt(this.wheel2, 0.0F, 0.3F, 0.0F, 0.3F));
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void mydebuggunnery(String paramString)
  {
    System.out.println(paramString);
  }

  protected void setControlDamage(Shot paramShot, int paramInt)
  {
    if (World.Rnd().nextFloat() < 0.002F)
    {
      if (getEnergyPastArmor(4.0F, paramShot) > 0.0F)
      {
        this.FM.AS.setControlsDamage(paramShot.initiator, paramInt);
      }
    }
  }

  protected void hitChunk(String paramString, Shot paramShot)
  {
    super.hitChunk(paramString, paramShot);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
        if (paramString.endsWith("p1"))
        {
          if (Aircraft.v1.z > 0.5D) {
            getEnergyPastArmor(4.0D / Aircraft.v1.z, paramShot);
          }
          else if (Aircraft.v1.x > 0.9396926164627075D)
            getEnergyPastArmor(8.0D / Aircraft.v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          else
            getEnergyPastArmor(3.0F, paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(4.0D / Math.abs(Aircraft.v1.z), paramShot);
        }
        else if (paramString.endsWith("p3"))
          getEnergyPastArmor(7.0D / Math.abs(Aircraft.v1.x) * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        else
          getEnergyPastArmor(3.0F, paramShot);
      if (paramString.endsWith("p4"))
      {
        if (Aircraft.v1.x > 0.7071067690849304D) {
          getEnergyPastArmor(7.0D / Aircraft.v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        else if (Aircraft.v1.x > -0.7071067690849304D)
          getEnergyPastArmor(5.0F, paramShot);
        else
          getEnergyPastArmor(3.0F, paramShot);
      }
      else if ((paramString.endsWith("a1")) || (paramString.endsWith("a2"))) {
        getEnergyPastArmor(3.0D, paramShot);
      }
      if (paramString.startsWith("xxarmturr")) {
        getEnergyPastArmor(3.0F, paramShot);
      }
      if (paramString.startsWith("xxspar"))
      {
        getEnergyPastArmor(3.0F, paramShot);
        if (((paramString.endsWith("cf1")) || (paramString.endsWith("cf2"))) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("CF") > 1) && (getEnergyPastArmor(15.9F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          msgCollision(this, "Tail1_D0", "Tail1_D0");
          msgCollision(this, "WingLIn_D0", "WingLIn_D0");
          msgCollision(this, "WingRIn_D0", "WingRIn_D0");
        }
        if (((paramString.endsWith("t1")) || (paramString.endsWith("t2"))) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 1) && (getEnergyPastArmor(15.9F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLIn") > 1) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingLIn_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingRIn") > 1) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingRIn_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLMid") > 1) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingLMid_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingRMid") > 1) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingRMid_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLOut") > 1) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingLOut_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingROut") > 1) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingROut_D2", paramShot.initiator);
        }
        if ((paramString.endsWith("e1")) && ((paramPoint3d.y > 2.79D) || (paramPoint3d.y < 2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }
        if ((paramString.endsWith("e2")) && ((paramPoint3d.y < -2.79D) || (paramPoint3d.y > -2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }
        if ((paramString.endsWith("e3")) && ((paramPoint3d.y < -2.79D) || (paramPoint3d.y > -2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "Engine3_D0", paramShot.initiator);
        }

        if (((paramString.endsWith("k1")) || (paramString.endsWith("k2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingROut") > 1) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "Keel1_D0", paramShot.initiator);
        }
        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingROut") > 1) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "StabR_D0", paramShot.initiator);
        }
        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingROut") > 1) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "StabL_D0", paramShot.initiator);
        }
      }

      if ((paramString.startsWith("xxbomb")) && (World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets()))
      {
        this.FM.AS.hitTank(paramShot.initiator, 0, 100);
        this.FM.AS.hitTank(paramShot.initiator, 1, 100);
        this.FM.AS.hitTank(paramShot.initiator, 2, 100);
        this.FM.AS.hitTank(paramShot.initiator, 3, 100);
        msgCollision(this, "CF_D0", "CF_D0");
      }

      if (paramString.startsWith("xxeng"))
      {
        i = 0;
        if (paramString.startsWith("xxeng2")) {
          i = 1;
        }
        if (paramString.endsWith("prop"))
        {
          j = i;
          if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.35F))
          {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, j, 3);
          }

        }

        if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 190000.0F)
            {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
            }

            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
            }
          }

        }
        else if (paramString.endsWith("cyls"))
        {
          if ((getEnergyPastArmor(1.6F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.4F))
          {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4000.0F)));

            if (this.FM.AS.astateEngineStates[i] < 1)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 1);
              this.FM.AS.doSetEngineState(paramShot.initiator, i, 1);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 900000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 3);
            }

            getEnergyPastArmor(25.0F, paramShot);
          }
        }
        else if ((paramString.endsWith("supc")) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F))
        {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
        }

        if ((getEnergyPastArmor(0.42F, paramShot) > 0.0F) && ((paramString.endsWith("oil1")) || (paramString.endsWith("oil2")) || (paramString.endsWith("oil3"))))
        {
          this.FM.AS.hitOil(paramShot.initiator, i);
        }

      }

      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';
        if ((i < 4) && (getEnergyPastArmor(1.8F, paramShot) > 0.0F)) {
          if (paramShot.power < 14100.0F)
          {
            if (this.FM.AS.astateTankStates[i] < 1)
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            if ((this.FM.AS.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.12F))
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[i] > 1) && (World.Rnd().nextFloat() < 0.1F))
              this.FM.AS.hitTank(paramShot.initiator, i, 10);
          }
          else {
            this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 35000.0F)));
          }
        }
      }

      if (paramString.startsWith("xxlock"))
      {
        if (paramString.startsWith("xxlockr"))
        {
          if (((paramString.startsWith("xxlockr1")) || (paramString.startsWith("xxlockr2"))) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }
        }
        if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockar")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

      }

    }

    if (paramString.startsWith("xxmgun"))
    {
      if (paramString.endsWith("1"))
      {
        if ((getEnergyPastArmor(5.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(10, 0);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.endsWith("2"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(11, 2);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.endsWith("3"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(12, 1);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.endsWith("4"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(13, 4);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.endsWith("5"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(14, 3);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
    }
    if (paramString.startsWith("xcf"))
    {
      setControlDamage(paramShot, 0);
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      getEnergyPastArmor(4.0F, paramShot);
    }
    else if (paramString.startsWith("xnose"))
    {
      if (chunkDamageVisible("Nose") < 3)
        hitChunk("Nose", paramShot);
      if (paramShot.power > 200000.0F) {
        this.FM.AS.hitPilot(paramShot.initiator, 0, World.Rnd().nextInt(3, 192));

        this.FM.AS.hitPilot(paramShot.initiator, 1, World.Rnd().nextInt(3, 192));

        this.FM.AS.hitPilot(paramShot.initiator, 2, World.Rnd().nextInt(3, 192));

        this.FM.AS.hitPilot(paramShot.initiator, 3, World.Rnd().nextInt(3, 192));
      }

    }
    else if (paramString.startsWith("xtail"))
    {
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Tail1") < 2) {
        hitChunk("Tail1", paramShot);
      }
      if (World.Rnd().nextFloat() < 0.1F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      if (World.Rnd().nextFloat() < 0.1F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
      }
    }
    else if (paramString.startsWith("xkeel1"))
    {
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xkeel2"))
    {
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Keel2") < 2)
        hitChunk("Keel2", paramShot);
    }
    else if (paramString.startsWith("xrudder1"))
    {
      setControlDamage(paramShot, 2);
      hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xrudder2"))
    {
      setControlDamage(paramShot, 2);
      hitChunk("Rudder2", paramShot);
    }
    else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    }
    else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    }
    else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xwinglin"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingLIn") < 2)
        hitChunk("WingLIn", paramShot);
    }
    else if (paramString.startsWith("xwingrin"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingRIn") < 2)
        hitChunk("WingRIn", paramShot);
    }
    else if (paramString.startsWith("xwinglmid"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingLMid") < 2)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingRMid") < 2)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout"))
    {
      if (chunkDamageVisible("WingLOut") < 2)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout"))
    {
      if (chunkDamageVisible("WingROut") < 2)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel")) {
      hitChunk("AroneL", paramShot);
    }
    else if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xflap01")) {
      hitChunk("Flap01", paramShot);
    }
    else if (paramString.startsWith("xflap02")) {
      hitChunk("Flap02", paramShot);
    }
    else if (paramString.startsWith("xengine1"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2"))
    {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if (paramString.startsWith("xgear"))
    {
      if (World.Rnd().nextFloat() < 0.1F)
      {
        this.FM.Gears.setHydroOperable(false);
      }
    }
    else if (paramString.startsWith("xturret"))
    {
      if (paramString.startsWith("xturret1"))
        this.FM.AS.setJamBullets(10, 0);
      if (paramString.startsWith("xturret2"))
        this.FM.AS.setJamBullets(11, 0);
      if (paramString.startsWith("xturret3"))
        this.FM.AS.setJamBullets(12, 0);
      if (paramString.startsWith("xturret4"))
        this.FM.AS.setJamBullets(13, 0);
      if (paramString.startsWith("xturret5"))
        this.FM.AS.setJamBullets(14, 0);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
    {
      i = 0;

      if (paramString.endsWith("a"))
      {
        i = 1;
        j = paramString.charAt(6) - '1';
      }
      else if (paramString.endsWith("b"))
      {
        i = 2;
        j = paramString.charAt(6) - '1';
      }
      else {
        j = paramString.charAt(5) - '1';
      }

      hitFlesh(j, paramShot, i);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 1.0F, 0.0F, -107.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 1.0F, 0.0F, -107.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 1.0F, 0.0F, -19.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 1.0F, 0.0F, -19.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 1.0F, 0.0F, 60.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 1.0F, 0.0F, 60.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.2F, 0.0F, -65.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.2F, 0.0F, -65.5F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.2F, 0.0F, 65.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.2F, 0.0F, 65.5F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.3F, 0.0F, -60.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.2F, 0.0F, 75.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.2F, 0.0F, 75.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  static
  {
    Class localClass = Do217.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}