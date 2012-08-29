package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class R_5xyz extends Scheme1
  implements TypeScout, TypeBomber, TypeTransport, TypeStormovik
{
  float radPos = 0.0F;
  float suspR = 0.0F;
  float suspL = 0.0F;
  private boolean bDynamoOperational;
  private float dynamoOrient;
  private boolean bDynamoRotary;
  private int pk;
  private boolean gunnerAiming = false;
  private float gunnerAnimation = 0.0F;
  private boolean gunnerDead = false;
  private boolean gunnerEjected = false;
  public boolean strafeWithGuns = false;

  public R_5xyz()
  {
    this.bDynamoOperational = true;
    this.dynamoOrient = 0.0F;
    this.bDynamoRotary = false;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.thisWeaponsName.startsWith("Gunpods"))
    {
      this.strafeWithGuns = true;
    }
  }

  public boolean cut(String paramString)
  {
    boolean bool = super.cut(paramString);
    if (paramString.equalsIgnoreCase("WingLIn"))
      hierMesh().chunkVisible("WingLMid_CAP", true);
    else if (paramString.equalsIgnoreCase("WingRIn"))
      hierMesh().chunkVisible("WingRMid_CAP", true);
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    Wreckage localWreckage;
    switch (paramInt1)
    {
    case 19:
      this.FM.Gears.hitCentreGear();
      break;
    case 9:
      if (hierMesh().chunkFindCheck("GearL0_D0") == -1)
        break;
      hierMesh().hideSubTrees("GearL0_D0");
      localWreckage = new Wreckage(this, hierMesh().chunkFind("GearL0_D0"));
      localWreckage.collide(true);
      this.FM.Gears.hitLeftGear();
      break;
    case 10:
      if (hierMesh().chunkFindCheck("GearR0_D0") == -1)
        break;
      hierMesh().hideSubTrees("GearR0_D0");
      localWreckage = new Wreckage(this, hierMesh().chunkFind("GearR0_D0"));
      localWreckage.collide(true);
      this.FM.Gears.hitRightGear();
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    if (paramArrayOfFloat[0] < -25.0F) {
      paramArrayOfFloat[0] = -25.0F;
      bool = false;
    } else if (paramArrayOfFloat[0] > 25.0F) {
      paramArrayOfFloat[0] = 25.0F;
      bool = false;
    }
    float f1 = Math.abs(paramArrayOfFloat[0]);
    if (f1 < 10.0F) {
      if (paramArrayOfFloat[1] < -5.0F) {
        paramArrayOfFloat[1] = -5.0F;
        bool = false;
      }
    } else if (paramArrayOfFloat[1] < -15.0F) {
      paramArrayOfFloat[1] = -15.0F;
      bool = false;
    }
    if (paramArrayOfFloat[1] > 35.0F) {
      paramArrayOfFloat[1] = 35.0F;
      bool = false;
    }
    if (!bool)
      return false;
    float f2 = paramArrayOfFloat[1];
    if ((f1 < 2.0F) && (f2 < 17.0F))
      return false;
    if (f2 > -5.0F)
      return true;
    if (f2 > -12.0F) {
      f2 += 12.0F;
      return f1 > 12.0F + f2 * 2.571429F;
    }
    f2 = -f2;
    return f1 > f2;
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -25.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder1a_D0", 0.0F, -25.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder1b_D0", 0.0F, -25.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    float f1 = 0.0F;
    float f2 = 0.0F;
    float f3 = 0.0F;
    float f4 = 0.0F;
    if (paramFloat > 0.0F)
    {
      f1 = -30.6F * paramFloat;
      f2 = -28.0F * paramFloat;
    }
    else if (paramFloat < 0.0F)
    {
      f1 = -28.0F * paramFloat;
      f2 = -30.6F * paramFloat;
    }

    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorLa_D0", 0.0F, f1, f3);
    hierMesh().chunkSetAngles("VatorLb_D0", 0.0F, f2, f4);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorRa_D0", 0.0F, f1, f3);
    hierMesh().chunkSetAngles("VatorRb_D0", 0.0F, f2, f4);
  }

  public void moveWheelSink()
  {
    this.suspL = (0.9F * this.suspL + 0.1F * this.FM.Gears.gWheelSinking[0]);
    this.suspR = (0.9F * this.suspR + 0.1F * this.FM.Gears.gWheelSinking[1]);

    if (this.suspL > 0.035F)
      this.suspL = 0.035F;
    if (this.suspR > 0.035F)
      this.suspR = 0.035F;
    if (this.suspL < 0.0F)
      this.suspL = 0.0F;
    if (this.suspR < 0.0F) {
      this.suspR = 0.0F;
    }
    float f = 40.0F;
    hierMesh().chunkSetAngles("GearL2_D0", 0.0F, this.suspL * -12.0F * f, 0.0F);
    hierMesh().chunkSetAngles("GearL0_D0", 0.0F, this.suspL * -4.0F * f, 0.0F);
    hierMesh().chunkSetAngles("GearL3_D0", 0.0F, this.suspL * -10.0F * f, 0.0F);
    hierMesh().chunkSetAngles("GearR2_D0", 0.0F, this.suspR * -12.0F * f, 0.0F);
    hierMesh().chunkSetAngles("GearR0_D0", 0.0F, this.suspR * -4.0F * f, 0.0F);
    hierMesh().chunkSetAngles("GearR3_D0", 0.0F, this.suspR * -10.0F * f, 0.0F);
  }

  public void update(float paramFloat)
  {
    float f = this.FM.EI.engines[0].getControlRadiator();

    Aircraft.xyz[0] = 0.0F;
    Aircraft.xyz[1] = 0.0F;
    Aircraft.xyz[2] = (f * -0.45F);
    Aircraft.ypr[0] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    hierMesh().chunkSetLocate("Water_D0", Aircraft.xyz, Aircraft.ypr);
    super.update(paramFloat);

    if (this.gunnerAiming)
    {
      if (this.gunnerAnimation < 1.0D)
      {
        this.gunnerAnimation += 0.025F;
        moveGunner();
      }

    }
    else if (this.gunnerAnimation > 0.0D)
    {
      this.gunnerAnimation -= 0.025F;
      moveGunner();
    }
  }

  private void moveGunner()
  {
    if ((this.gunnerDead) || (this.gunnerEjected)) {
      return;
    }
    if (this.gunnerAnimation > 0.5D)
    {
      hierMesh().chunkVisible("Pilot2_D0", true);
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkSetAngles("Pilot2_D0", (this.gunnerAnimation - 0.5F) * 360.0F - 180.0F, 0.0F, 0.0F);
    }
    else if (this.gunnerAnimation > 0.25D)
    {
      Aircraft.xyz[0] = 0.0F;
      Aircraft.xyz[1] = 0.0F;
      Aircraft.xyz[2] = ((this.gunnerAnimation - 0.5F) * 0.5F);
      Aircraft.ypr[0] = 180.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      hierMesh().chunkSetLocate("Pilot2_D0", Aircraft.xyz, Aircraft.ypr);
      hierMesh().chunkVisible("Pilot2_D0", true);
      hierMesh().chunkVisible("Pilot3_D0", false);
    }
    else
    {
      Aircraft.xyz[0] = 0.0F;
      Aircraft.xyz[1] = 0.0F;
      Aircraft.xyz[2] = (this.gunnerAnimation * 0.5F);
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = (this.gunnerAnimation * -110.0F);
      hierMesh().chunkSetLocate("Pilot3_D0", Aircraft.xyz, Aircraft.ypr);
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot3_D0", true);
    }
  }

  protected void moveAileron(float paramFloat)
  {
    super.moveAileron(-paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    Actor localActor = War.GetNearestEnemy(this, 16, 7000.0F);
    Aircraft localAircraft = War.getNearestEnemy(this, 6000.0F);

    int i = ((this.FM.CT.Weapons[10] != null) && (this.FM.CT.Weapons[10][0].haveBullets())) || ((this.FM.CT.Weapons[10] != null) && (this.FM.CT.Weapons[10][1].haveBullets())) ? 1 : 0;

    if (i == 0) {
      this.FM.turret[0].bIsOperable = false;
    }
    if ((i != 0) && (((localActor != null) && (!(localActor instanceof BridgeSegment))) || (localAircraft != null)))
    {
      if (!this.gunnerAiming)
      {
        this.gunnerAiming = true;
      }
    }
    else if (this.gunnerAiming)
    {
      this.gunnerAiming = false;
    }

    if (this.FM.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
    }
    else
    {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
      hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
    }
  }

  protected void moveFan(float paramFloat)
  {
    if (this.bDynamoOperational)
    {
      this.pk = Math.abs((int)(this.FM.Vwld.length() / 14.0D));
      if (this.pk >= 1)
        this.pk = 1;
    }
    if (this.bDynamoRotary != (this.pk == 1))
    {
      this.bDynamoRotary = (this.pk == 1);
      hierMesh().chunkVisible("Prop2_d0", !this.bDynamoRotary);
      hierMesh().chunkVisible("PropRot2_d0", this.bDynamoRotary);
    }
    this.dynamoOrient = (this.bDynamoRotary ? (this.dynamoOrient - 17.987F) % 360.0F : (float)(this.dynamoOrient - this.FM.Vwld.length() * 1.544401526451111D) % 360.0F);
    hierMesh().chunkSetAngles("Prop2_d0", 0.0F, this.dynamoOrient, 0.0F);
    super.moveFan(paramFloat);
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    if (paramInt == 2)
    {
      super.doRemoveBodyFromPlane(3);
      this.gunnerEjected = true;
    }
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    case 0:
      break;
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      if (paramFloat > 0.0F) break;
      this.gunnerDead = true;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D1", hierMesh().isChunkVisible("Pilot2_D0"));
      hierMesh().chunkVisible("Pilot3_D1", hierMesh().isChunkVisible("Pilot3_D0"));
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      this.gunnerDead = true;
    }
  }

  protected void moveGear(float paramFloat)
  {
    super.moveGear(paramFloat);
  }

  public void typeBomberAdjAltitudeMinus()
  {
  }

  public void typeBomberAdjAltitudePlus()
  {
  }

  public void typeBomberAdjAltitudeReset()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjSideslipMinus()
  {
  }

  public void typeBomberAdjSideslipPlus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSpeedMinus()
  {
  }

  public void typeBomberAdjSpeedPlus()
  {
  }

  public void typeBomberAdjSpeedReset()
  {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberUpdate(float paramFloat)
  {
  }

  protected void moveFlap(float paramFloat)
  {
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(9.96F / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), paramShot);
        }

        return;
      }

      if (paramString.startsWith("xxcontrols"))
      {
        if (paramString.endsWith("7")) {
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);

            Aircraft.debugprintln(this, "*** Rudder Controls Out.. (#7)");
          }
        }
        else if (paramString.endsWith("8"))
        {
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);

            Aircraft.debugprintln(this, "*** Evelator Controls Out.. (#8)");
          }
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);

            Aircraft.debugprintln(this, "*** Arone Controls Out.. (#8)");
          }
        }
        else if (paramString.endsWith("5")) {
          if (World.Rnd().nextFloat() < 0.5F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);

            Aircraft.debugprintln(this, "*** Evelator Controls Out.. (#5)");
          }
        }
        else if (paramString.endsWith("6")) {
          if (World.Rnd().nextFloat() < 0.5F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);

            Aircraft.debugprintln(this, "*** Rudder Controls Out.. (#6)");
          }
        }
        else if (((paramString.endsWith("2")) || (paramString.endsWith("4"))) && 
          (World.Rnd().nextFloat() < 0.5F))
        {
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);

          Aircraft.debugprintln(this, "*** Arone Controls Out.. (#2/#4)");
        }

        return;
      }

      if ((paramString.startsWith("xxeng")) || (paramString.startsWith("xxEng")))
      {
        Aircraft.debugprintln(this, "*** Engine Module: Hit..");

        if (paramString.endsWith("prop")) {
          Aircraft.debugprintln(this, "*** Prop hit");
        } else if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(2.1F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F)
            {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
            }

            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
            }

            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));

            Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }

          getEnergyPastArmor(12.7F, paramShot);
        }
        else if (paramString.startsWith("xxeng1cyls"))
        {
          if ((getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 4.4F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.12F))
          {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

            Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");

            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
              Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
            }

            if (World.Rnd().nextFloat() < 0.005F)
            {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
            }

            getEnergyPastArmor(22.5F, paramShot);
          }
        }
        else if (paramString.endsWith("Oil1"))
        {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
        }
        return;
      }
      if (paramString.startsWith("xxoil"))
      {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(0.22F, paramShot);
        Aircraft.debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
      }
      else if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';

        if ((getEnergyPastArmor(0.4F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.99F))
        {
          if (this.FM.AS.astateTankStates[i] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }

          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.25F))
          {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
        }
      }
      else if (paramString.startsWith("xxlock"))
      {
        Aircraft.debugprintln(this, "*** Lock Construction: Hit..");

        if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

      }
      else if (paramString.startsWith("xMgun"))
      {
        if (paramString.endsWith("1"))
        {
          Aircraft.debugprintln(this, "*** Rear Gun #1: Disabled..");
          this.FM.AS.setJamBullets(10, 0);
        }
        if (paramString.endsWith("2"))
        {
          Aircraft.debugprintln(this, "*** Rear Gun #2: Disabled..");
          this.FM.AS.setJamBullets(10, 1);
        }

        getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
      }
      else if (paramString.startsWith("xxgun"))
      {
        if (paramString.endsWith("0"))
        {
          Aircraft.debugprintln(this, "*** Fixed Gun #1: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }

        getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
      }
      else if (paramString.startsWith("xxspar"))
      {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");

        if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(9.5F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparli")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxspar2i")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparlm")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparrm")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparlo")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparro")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), paramShot.initiator);
        }

      }

      return;
    }

    if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
    {
      i = 0;
      int j;
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
      if (j == 2)
        j = 1;
      Aircraft.debugprintln(this, "*** hitFlesh..");
      hitFlesh(j, paramShot, i);
    }
    else if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit")))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xturret1b"))
    {
      Aircraft.debugprintln(this, "*** Turret Gun: Disabled.. (xturret1b)");
      this.FM.AS.setJamBullets(10, 0);
      getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
    }
    else if (paramString.startsWith("xeng"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail"))
    {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel"))
    {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder"))
    {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab"))
    {
      if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 2)) {
        hitChunk("StabL", paramShot);
      }
      if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 2))
        hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvator"))
    {
      if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1)) {
        hitChunk("VatorL", paramShot);
      }
      if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
        hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xWing"))
    {
      Aircraft.debugprintln(this, "*** xWing: " + paramString);

      if ((paramString.startsWith("xWingLIn")) && (chunkDamageVisible("WingLIn") < 3)) {
        hitChunk("WingLIn", paramShot);
      }
      if ((paramString.startsWith("xWingRIn")) && (chunkDamageVisible("WingRIn") < 3)) {
        hitChunk("WingRIn", paramShot);
      }
      if ((paramString.startsWith("xWingLmid")) && (chunkDamageVisible("WingLMid") < 3)) {
        hitChunk("WingLMid", paramShot);
      }
      if ((paramString.startsWith("xWingRmid")) && (chunkDamageVisible("WingRMid") < 3))
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwing"))
    {
      Aircraft.debugprintln(this, "*** xwing: " + paramString);

      if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3)) {
        hitChunk("WingLOut", paramShot);
      }
      if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xarone"))
    {
      if ((paramString.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
      {
        hitChunk("AroneL1", paramShot);
      }

      if ((paramString.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
      {
        hitChunk("AroneR1", paramShot);
      }
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}