package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public abstract class SB extends Scheme2
  implements TypeBomber
{
  private long tme = 0L;
  private float topGunnerPosition = 0.0F;
  private float curTopGunnerPosition = 0.0F;
  private int radPosNum = 1;
  private Gun gun3;
  private Gun gun4;
  private float curTakeem = 0.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.gun3 = getGunByHookName("_MGUN03");
    this.gun4 = getGunByHookName("_MGUN04");
  }

  public void update(float paramFloat)
  {
    if (Time.current() > this.tme) {
      this.tme = (Time.current() + World.Rnd().nextLong(1000L, 5000L));
      if (this.FM.turret.length != 0)
      {
        byte tmp92_91 = (byte)Math.min(this.FM.AS.astatePilotStates[2], this.FM.AS.astatePilotStates[3]); this.FM.AS.astatePilotStates[3] = tmp92_91; this.FM.AS.astatePilotStates[2] = tmp92_91;
        Actor localActor = null;
        for (int i = 0; i < 3; i++) { if (!this.FM.turret[i].bIsOperable) continue; localActor = this.FM.turret[i].target; }
        for (i = 1; i < 3; i++) this.FM.turret[i].target = localActor;
        if (localActor != null) {
          if (Actor.isValid(localActor)) {
            this.pos.getAbs(tmpLoc2); localActor.pos.getAbs(tmpLoc3); tmpLoc2.transformInv(tmpLoc3.getPoint());
            if (tmpLoc3.getPoint().z > 0.0D)
              setRadist(1);
            else
              setRadist(2);
          }
        }
        else {
          this.topGunnerPosition = 0.0F;
        }
      }
    }
    if (this.FM.AS.astatePilotStates[2] < 90) {
      if (this.curTopGunnerPosition > this.topGunnerPosition)
        this.curTopGunnerPosition -= 0.3F * paramFloat;
      else {
        this.curTopGunnerPosition += 0.3F * paramFloat;
      }
      if (this.curTopGunnerPosition > 0.1F)
        this.FM.turret[2].bIsOperable = false;
      else {
        this.FM.turret[2].bIsOperable = (this.radPosNum == 2);
      }
      if (this.curTopGunnerPosition < 0.9F)
        this.FM.turret[1].bIsOperable = false;
      else {
        this.FM.turret[1].bIsOperable = (this.radPosNum == 1);
      }
    }
    if (this.curTopGunnerPosition > 0.05F) {
      if ((this instanceof SB_2M100A)) {
        resetYPRmodifier();
        xyz[1] = cvt(this.curTopGunnerPosition, 0.05F, 0.5F, 0.0F, -1.16F);
        xyz[2] = cvt(this.curTopGunnerPosition, 0.05F, 0.5F, 0.0F, 0.1F);
        hierMesh().chunkSetLocate("Blister3_D0", xyz, ypr);
        hierMesh().chunkSetAngles("Turret2M1_D0", 0.0F, 0.0F, cvt(this.curTopGunnerPosition, 0.6F, 0.9F, 0.0F, -40.0F));
        hierMesh().chunkSetAngles("Turret2M2_D0", 0.0F, cvt(this.curTopGunnerPosition, 0.6F, 0.9F, 0.0F, -40.0F), 0.0F);
        if ((this.curTopGunnerPosition > 0.6F) && (this.curTopGunnerPosition < 0.9F)) {
          hierMesh().chunkSetAngles("Turret2A_D0", 0.0F, 0.0F, 0.0F);
          hierMesh().chunkSetAngles("Turret2B_D0", 0.0F, 0.0F, 0.0F);
          this.FM.turret[1].target = null;
        }
        resetYPRmodifier();
        xyz[1] = cvt(this.curTopGunnerPosition, 0.6F, 0.9F, 0.0F, -0.115F);
        xyz[2] = cvt(this.curTopGunnerPosition, 0.6F, 0.9F, 0.0F, 0.229F);
        hierMesh().chunkSetLocate("Pilot3_D0", xyz, ypr);
      } else {
        resetYPRmodifier();
        xyz[2] = cvt(this.curTopGunnerPosition, 0.6F, 0.9F, 0.0F, 0.3F);
        hierMesh().chunkSetLocate("Pilot3_D0", xyz, ypr);
      }
    }
    if ((this.FM.AS.astateTankStates[0] > 1) || (this.FM.AS.astateTankStates[3] > 1))
      this.curTakeem += 0.02F * paramFloat;
    else if ((this.FM.AS.astateTankStates[1] > 1) || (this.FM.AS.astateTankStates[2] > 1)) {
      this.curTakeem += 0.05F * paramFloat;
    }
    if (this.curTakeem > 0.5F) {
      this.curTakeem = 0.5F;
    }
    super.update(paramFloat);
  }

  private void setRadist(int paramInt)
  {
    this.radPosNum = paramInt;
    if (this.FM.AS.astatePilotStates[2] > 90) return;
    hierMesh().chunkVisible("Pilot3_D0", false);
    hierMesh().chunkVisible("Pilot4_D0", false);
    hierMesh().chunkVisible("HMask3_D0", false);
    hierMesh().chunkVisible("HMask4_D0", false);
    this.FM.turret[1].bIsOperable = false;
    this.FM.turret[2].bIsOperable = false;
    switch (paramInt) {
    case 1:
      hierMesh().chunkVisible("Pilot3_D0", true);
      hierMesh().chunkVisible("HMask3_D0", this.FM.Loc.z > 3000.0D);
      this.FM.turret[1].bIsOperable = true;
      this.topGunnerPosition = 1.0F;
      break;
    case 2:
      hierMesh().chunkVisible("Pilot4_D0", true);
      hierMesh().chunkVisible("HMask4_D0", this.FM.Loc.z > 3000.0D);
      this.FM.turret[2].bIsOperable = true;
      this.topGunnerPosition = 0.0F;
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 5.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -175.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -155.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, cvt(paramFloat, 0.0F, 0.05F, 0.0F, 70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, cvt(paramFloat, 0.0F, 0.05F, 0.0F, -70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 5.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -175.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -155.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, cvt(paramFloat, 0.0F, 0.05F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, cvt(paramFloat, 0.0F, 0.05F, 0.0F, 70.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }

  public void moveWheelSink()
  {
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.39F)) this.FM.AS.hitTank(this, 0, 1);

      if ((this.FM.AS.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.39F)) this.FM.AS.hitTank(this, 1, 1);

      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.08F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.08F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.08F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.08F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.16F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.16F)) this.FM.AS.hitTank(this, 1, 0);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.16F)) this.FM.AS.hitTank(this, 1, 3);
      if ((this.FM.AS.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.16F)) this.FM.AS.hitTank(this, 1, 2);
    }

    for (int i = 1; i < 5; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("BayL_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("BayR_D0", 0.0F, 90.0F * paramFloat, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -43.0F) { f1 = -43.0F; bool = false; }
      if (f1 > 43.0F) { f1 = 43.0F; bool = false; }
      if (f2 < -15.0F) { f2 = -15.0F; bool = false; }
      if (f2 <= 15.0F) break; f2 = 15.0F; bool = false; break;
    case 1:
      if ((this instanceof SB_2M100A)) {
        if (f1 < -40.0F) { f1 = -40.0F; bool = false; }
        if (f1 > 40.0F) { f1 = 40.0F; bool = false; }
        if (f2 < -2.0F) { f2 = -2.0F; bool = false; }
        if (f2 <= 50.0F) break; f2 = 50.0F; bool = false;
      } else {
        if (f2 > 48.0F) { f2 = 48.0F; bool = false; }
        float f3 = Math.abs(f1);
        if (f3 < 6.0F) {
          if (f2 < -4.5F) { f2 = -4.5F; bool = false; }
        } else if (f3 < 18.0F) {
          if (f2 < -1.291667F * f3 + 3.25F) { f2 = -1.291667F * f3 + 3.25F; bool = false; }
        } else if (f3 < 42.0F) {
          if (f2 < -1.666667F * f2 + 10.0F) { f2 = -1.666667F * f2 + 10.0F; bool = false; }
        } else if (f3 < 100.0F) {
          if (f2 < -60.0F) { f2 = -60.0F; bool = false; }
        } else if (f3 < 138.0F) {
          if (f2 < -23.5F) { f2 = -23.5F; bool = false; }
        } else if (f3 < 165.0F) {
          if (f2 < 1.518519F * f3 - 233.05556F) { f2 = 1.518519F * f3 - 233.05556F; bool = false; }
        }
        else if (f2 < 1.5F * f3 - 230.0F) { f2 = 1.5F * f3 - 230.0F; bool = false;
        }
      }
      break;
    case 2:
      if (f1 < -25.0F) { f1 = -25.0F; bool = false; }
      if (f1 > 25.0F) { f1 = 25.0F; bool = false; }
      if (f2 < -35.0F) { f2 = -35.0F; bool = false; }
      if (f2 <= 10.0F) break; f2 = 10.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      hitProp(0, paramInt2, paramActor);
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      hitProp(1, paramInt2, paramActor);
      return super.cutFM(37, paramInt2, paramActor);
    case 19:
      this.FM.AS.setJamBullets(12, 0);
      if (this.FM.turret.length <= 0) break;
      this.FM.turret[2].bIsOperable = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(0.2F, paramShot);
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(0.2F, paramShot);
        }
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if ((World.Rnd().nextFloat() >= 0.05F) && ((paramShot.mass <= 0.092F) || (World.Rnd().nextFloat() >= 0.1F))) break;
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() >= 0.5F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6); break;
        case 2:
          if ((World.Rnd().nextFloat() >= 0.05F) && ((paramShot.mass <= 0.092F) || (World.Rnd().nextFloat() >= 0.1F))) break;
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
          }
          if (World.Rnd().nextFloat() >= 0.5F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6); break;
        case 3:
          if (getEnergyPastArmor(1.0F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debugprintln(this, "*** Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.12F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debugprintln(this, "*** Rudder Controls Out..");
        }

      }

      if (paramString.startsWith("xxspar")) {
        if ((paramString.startsWith("xxspart")) && 
          (World.Rnd().nextFloat() < 0.36F) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(6.8F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Tail1 Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }

        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(14.8F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(14.8F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(12.8F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(12.8F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(v1.x)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(9.1F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(v1.x)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(9.1F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(5.2F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsr")) && 
          (chunkDamageVisible("StabR") > 1) && (getEnergyPastArmor(5.2F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspare1")) && 
          (getEnergyPastArmor(28.0F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspare2")) && 
          (getEnergyPastArmor(28.0F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }
      }

      if ((paramString.startsWith("xxbmb")) && 
        (World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets())) {
        debugprintln(this, "*** Bomb Payload Detonates..");
        this.FM.AS.hitTank(paramShot.initiator, 0, 10);
        this.FM.AS.hitTank(paramShot.initiator, 1, 10);
        this.FM.AS.hitTank(paramShot.initiator, 2, 10);
        this.FM.AS.hitTank(paramShot.initiator, 3, 10);
        nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
      }

      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 0.4F), paramShot) > 0.0F)) {
          this.FM.EI.engines[i].setKillPropAngleDevice(paramShot.initiator);
          debugprintln(this, "*** Engine" + (i + 1) + " Prop Governor Failed..");
        }

        if ((paramString.endsWith("gear")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 1.1F), paramShot) > 0.0F)) {
          this.FM.EI.engines[i].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          debugprintln(this, "*** Engine" + (i + 1) + " Prop Governor Damaged..");
        }

        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 6.8F), paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Engine Damaged..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
              this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
              debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            }
            if (World.Rnd().nextFloat() < 0.08F) {
              this.FM.EI.engines[i].setEngineStuck(paramShot.initiator);
              debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
            }
            this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[i].setEngineStops(paramShot.initiator);
            debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Engine Stalled..");
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 10);
            debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
          }
          getEnergyPastArmor(6.0F, paramShot);
        }
        if (((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2"))) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.542F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.72F)) {
          this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debugprintln(this, "*** Engine" + (i + 1) + " Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[i].setEngineStuck(paramShot.initiator);
            debugprintln(this, "*** Engine" + (i + 1) + " Cylinder Case Broken - Engine Stuck..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 3);
            debugprintln(this, "*** Engine" + (i + 1) + " Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(World.Rnd().nextFloat(3.0F, 46.700001F), paramShot);
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          this.FM.EI.engines[i].setKillCompressor(paramShot.initiator);
          debugprintln(this, "*** Engine" + (i + 1) + " Supercharger Out..");
        }

        if ((paramString.endsWith("eqpt")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.001F, 0.2F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          if (World.Rnd().nextFloat() < 0.11F) {
            this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, World.Rnd().nextInt(0, 1));
            debugprintln(this, "*** Engine" + (i + 1) + " Magneto Out..");
          }
          if (World.Rnd().nextFloat() < 0.11F) {
            this.FM.EI.engines[i].setKillCompressor(paramShot.initiator);
            debugprintln(this, "*** Engine" + (i + 1) + " Compressor Feed Out..");
          }
        }
      }

      if (paramString.startsWith("xxoil")) {
        i = 0;
        if (paramString.endsWith("2")) {
          i = 1;
        }
        if (getEnergyPastArmor(0.21F, paramShot) > 0.0F) {
          this.FM.AS.hitOil(paramShot.initiator, i);
        }
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(0.03F, paramShot) > 0.0F) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 2);
          }
          if (paramShot.powerType == 3) {
            if (paramShot.power < 14100.0F) {
              if ((this.FM.AS.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.1F)) {
                this.FM.AS.hitTank(paramShot.initiator, i, 1);
              }

            }
            else
            {
              this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 28200.0F)));
            }
          }
        }
      }
      if (paramString.startsWith("xxhyd")) {
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }
      if (paramString.startsWith("xxpnm")) {
        this.FM.AS.setInternalDamage(paramShot.initiator, 1);
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if ((paramShot.power > 33000.0F) && (paramPoint3d.x > 1.0D)) {
        this.FM.AS.hitPilot(paramShot.initiator, 0, World.Rnd().nextInt(30, 192));
        this.FM.AS.hitPilot(paramShot.initiator, 1, World.Rnd().nextInt(30, 192));
      }

    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      if (chunkDamageVisible("StabL") < 2)
        hitChunk("StabL", paramShot);
    }
    else if (paramString.startsWith("xstabr")) {
      if (chunkDamageVisible("StabR") < 2)
        hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    } else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwinglin")) {
      if (((this.FM.AS.astateTankStates[0] > 1) || (this.FM.AS.astateTankStates[1] > 1)) && 
        (paramShot.powerType == 3) && (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.33F) && (World.Rnd().nextFloat() < this.curTakeem)) {
        this.FM.AS.hitTank(paramShot.initiator, World.Rnd().nextInt(0, 1), 3);
      }

      if (chunkDamageVisible("WingLIn") < 3)
        hitChunk("WingLIn", paramShot);
    }
    else if (paramString.startsWith("xwingrin")) {
      if (((this.FM.AS.astateTankStates[2] > 1) || (this.FM.AS.astateTankStates[3] > 1)) && 
        (paramShot.powerType == 3) && (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.33F) && (World.Rnd().nextFloat() < this.curTakeem)) {
        this.FM.AS.hitTank(paramShot.initiator, World.Rnd().nextInt(2, 3), 3);
      }

      if (chunkDamageVisible("WingRIn") < 3)
        hitChunk("WingRIn", paramShot);
    }
    else if (paramString.startsWith("xwinglmid")) {
      if (chunkDamageVisible("WingLMid") < 3)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid")) {
      if (chunkDamageVisible("WingRMid") < 3)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout")) {
      if (chunkDamageVisible("WingLOut") < 3)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout")) {
      if (chunkDamageVisible("WingROut") < 3)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel")) {
      hitChunk("AroneL", paramShot);
    } else if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    } else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2) {
        hitChunk("Engine1", paramShot);
      }
      this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 168000.0F));
      debugprintln(this, "*** Engine1 Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
    } else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2) {
        hitChunk("Engine2", paramShot);
      }
      this.FM.EI.engines[1].setReadyness(paramShot.initiator, this.FM.EI.engines[1].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 168000.0F));
      debugprintln(this, "*** Engine2 Hit - Readyness Reduced to " + this.FM.EI.engines[1].getReadyness() + "..");
    } else if (paramString.startsWith("xgear")) {
      if (World.Rnd().nextFloat() < 0.1F) {
        debugprintln(this, "*** Gear Hydro Failed..");
        this.FM.Gears.setHydroOperable(false);
      }
    } else if (paramString.startsWith("xturret")) {
      if (paramString.startsWith("xturret1")) {
        this.FM.AS.setJamBullets(10, 0);
        this.FM.AS.setJamBullets(10, 1);
      }
      if (paramString.startsWith("xturret2")) {
        this.FM.AS.setJamBullets(11, 0);
      }
      if (paramString.startsWith("xturret3"))
        this.FM.AS.setJamBullets(12, 0);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int j;
      if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 2:
    case 3:
      this.FM.turret[1].setHealth(paramFloat);
      this.FM.turret[2].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Gore2_D0", true);
      hierMesh().chunkVisible("Gore1_D0", hierMesh().isChunkVisible("Blister1_D0"));
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Gore3_D0", true);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      hierMesh().chunkVisible("HMask3_D0", false);
      break;
    case 3:
    }
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    if (paramInt >= 3) {
      doRemoveBodyChunkFromPlane("Pilot4");
      doRemoveBodyChunkFromPlane("Head4");
    }
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus()
  {
  }

  public void typeBomberAdjSideslipMinus()
  {
  }

  public void typeBomberAdjAltitudeReset()
  {
  }

  public void typeBomberAdjAltitudePlus()
  {
  }

  public void typeBomberAdjAltitudeMinus()
  {
  }

  public void typeBomberAdjSpeedReset()
  {
  }

  public void typeBomberAdjSpeedPlus()
  {
  }

  public void typeBomberAdjSpeedMinus()
  {
  }

  public void typeBomberUpdate(float paramFloat)
  {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  static
  {
    Class localClass = SB.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}