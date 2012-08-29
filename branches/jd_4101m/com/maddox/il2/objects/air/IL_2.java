package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.PylonKMB;
import com.maddox.rts.Property;

public abstract class IL_2 extends Scheme1
  implements TypeStormovikArmored
{
  private static float[][] GA = { { -68.099998F, 0.0F, 134.39999F, 0.0F }, { -74.699997F, -6.81F, 119.6F, 13.44F }, { -88.400002F, -14.29F, 109.7F, 25.41F }, { -112.7F, -23.129999F, 85.0F, 36.389999F }, { -142.39999F, -34.389999F, 58.599998F, 44.889999F }, { -166.3F, -48.639999F, 17.799999F, 50.759998F }, { -164.8F, -65.269997F, -28.9F, 52.549999F }, { -118.2F, -81.75F, -65.099998F, 49.66F }, { -63.099998F, -93.57F, -91.699997F, 43.139999F }, { -18.5F, -99.870003F, -108.3F, 33.959999F }, { 0.0F, -103.0F, 0.0F, 22.0F } };

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    World.cur(); if ((this == World.getPlayerAircraft()) && (this.FM.turret.length > 0) && (this.FM.AS.astatePilotStates[1] < 90) && (this.FM.turret[0].bIsAIControlled))
    {
      if ((this.FM.getOverload() > 7.0F) || (this.FM.getOverload() < -0.7F)) Voice.speakRearGunShake();
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    int i = (int)(paramFloat * 10.0F);
    float f = Math.max(-paramFloat * 1200.0F, -75.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -(GA[i][0] * (paramFloat - i / 10.0F) + GA[i][1]), 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, -(GA[i][2] * (paramFloat - i / 10.0F) + GA[i][3]));
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 55.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -(GA[i][0] * (paramFloat - i / 10.0F) + GA[i][1]), 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, -(GA[i][2] * (paramFloat - i / 10.0F) + GA[i][3]));
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, 55.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, f, 0.0F);
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveSteering(float paramFloat) { hierMesh().chunkSetAngles("GearC2_D0", 0.0F, paramFloat, 0.0F); }

  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.08F, 0.0F, 0.08F);
    hierMesh().chunkSetLocate("GearL9_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.08F, 0.0F, 0.08F);
    hierMesh().chunkSetLocate("GearR9_D0", xyz, ypr);
  }

  protected void moveElevator(float paramFloat)
  {
    float f = -16.0F * paramFloat;
    if (f < 0.0F) f = (float)(f * 1.75D);
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, f, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FlettnerL_D0", 0.0F, 40.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FlettnerR_D0", 0.0F, 40.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FlettnerRodL_D0", 0.0F, -37.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FlettnerRodR_D0", 0.0F, -37.0F * paramFloat, 0.0F);
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay3_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay5_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay6_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay7_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay8_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    if (paramArrayOfFloat[0] < -60.0F) { paramArrayOfFloat[0] = -60.0F; bool = false;
    } else if (paramArrayOfFloat[0] > 60.0F) { paramArrayOfFloat[0] = 60.0F; bool = false; }
    float f1 = Math.abs(paramArrayOfFloat[0]);
    if (f1 < 20.0F) {
      if (paramArrayOfFloat[1] < -10.0F) { paramArrayOfFloat[1] = -10.0F; bool = false; }
    }
    else if (paramArrayOfFloat[1] < -15.0F) { paramArrayOfFloat[1] = -15.0F; bool = false;
    }
    if (paramArrayOfFloat[1] > 45.0F) { paramArrayOfFloat[1] = 45.0F; bool = false; }
    if (!bool) return false;

    float f2 = paramArrayOfFloat[1];
    if ((f1 < 2.0F) && (f2 < 17.0F)) return false;
    if (f2 > -5.0F) return true;
    if (f2 > -12.0F) {
      f2 += 12.0F;
      return f1 > 12.0F + f2 * 2.571429F;
    }

    f2 = -f2;
    return f1 > f2;
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      if (this.FM.turret.length == 0) return;
      this.FM.turret[0].setHealth(paramFloat);
      hierMesh().chunkVisible("Turret1A_D0", false);
      hierMesh().chunkVisible("Turret1B_D0", false);
      hierMesh().chunkVisible("Turret1A_D1", true);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      if (this.FM.AS.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore1_D0", hierMesh().isChunkVisible("Blister1_D0"));
      hierMesh().chunkVisible("Gore2_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      if (!this.FM.AS.bIsAboutToBailout) {
        hierMesh().chunkVisible("Gore3_D0", true);
      }
      if ((hierMesh().chunkFindCheck("Helm_D0") == -1) || (!hierMesh().isChunkVisible("Helm_D0"))) break;
      hierMesh().chunkVisible("Helm_D0", false);
      Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Helm_D0"));
      localWreckage.collide(false);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.FM.Vwld); localWreckage.setSpeed(localVector3d);
    }

    if (paramInt == 1) {
      if (this.FM.turret == null) return;
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("Turret1A_D0", false);
      hierMesh().chunkVisible("Turret1B_D0", false);
    }
  }

  public void doRemoveBodyFromPlane(int paramInt) {
    super.doRemoveBodyFromPlane(paramInt);
    if ((paramInt == 1) && 
      (hierMesh().chunkFindCheck("Helm_D0") != -1) && (hierMesh().isChunkVisible("Helm_D0")))
      hierMesh().chunkVisible("Helm_D0", false);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject == null) return;
    for (int i = 0; i < arrayOfObject.length; i++) {
      if ((arrayOfObject[i] instanceof PylonKMB)) {
        for (int j = 1; j < 9; j++) hierMesh().chunkVisible("Bay" + j + "_D0", false);
        return;
      }
    }
    this.FM.AS.wantBeaconsNet(true);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;
    int j = ((this instanceof IL_2I)) || ((this instanceof IL_2MLate)) || ((this instanceof IL_2T)) || ((this instanceof IL_2Type3)) || ((this instanceof IL_2Type3M)) ? 1 : 0;

    int k = (!(this instanceof IL_2_1940Early)) && (!(this instanceof IL_2_1940Late)) ? 1 : 0;

    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.startsWith("xxarmorp")) {
          i = paramString.charAt(8) - '0';
          switch (i) {
          case 1:
            getEnergyPastArmor(7.070000171661377D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
            paramShot.powerType = 0;
            break;
          case 2:
          case 3:
            getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.y) + 9.999999747378752E-005D), paramShot);
            paramShot.powerType = 0;
            if ((paramShot.power > 0.0F) || (Math.abs(v1.x) <= 0.8659999966621399D)) break;
            doRicochet(paramShot); break;
          case 4:
            if (paramPoint3d.x > -1.35D) {
              getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
              paramShot.powerType = 0;
              if ((paramShot.power > 0.0F) || (Math.abs(v1.x) <= 0.8659999966621399D)) break;
              doRicochet(paramShot);
            }
            else {
              getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
            }
            break;
          case 5:
          case 6:
            getEnergyPastArmor(20.200000762939453D / (Math.abs(v1.y) + 9.999999747378752E-005D), paramShot);
            if (paramShot.power > 0.0F) break;
            if (Math.abs(v1.x) > 0.8659999966621399D)
              doRicochet(paramShot);
            else
              doRicochetBack(paramShot); break;
          case 7:
            getEnergyPastArmor(20.200000762939453D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
            if (paramShot.power > 0.0F) break;
            doRicochetBack(paramShot);
          }

        }

        if (paramString.startsWith("xxarmorc1")) {
          getEnergyPastArmor(7.070000171661377D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        if (paramString.startsWith("xxarmort1")) {
          getEnergyPastArmor(6.059999942779541D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        if (paramString.endsWith("10")) {
          i = 10;
        }
        switch (i) {
        case 1:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.33F) {
            debuggunnery("Controls: Throttle Controls Disabled..");
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < 0.33F) {
            debuggunnery("Controls: Prop Controls Disabled..");
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          }
          if (World.Rnd().nextFloat() >= 0.33F) break;
          debuggunnery("Controls: Mix Controls Disabled..");
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7); break;
        case 2:
          if (getEnergyPastArmor(1.1F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Control Column Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 3:
          if (getEnergyPastArmor(0.25F / ((float)Math.sqrt(v1.y * v1.y + v1.z * v1.z) + 1.0E-004F), paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.05F) {
            debuggunnery("Controls: Elevator Wiring Hit, Elevator Controls Disabled..");
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() >= 0.75F) break;
          debuggunnery("Controls: Rudder Wiring Hit, Rudder Controls Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 4:
          if (getEnergyPastArmor(0.25F / ((float)Math.sqrt(v1.y * v1.y + v1.z * v1.z) + 1.0E-004F), paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.03F) {
            debuggunnery("Controls: Elevator Wiring Hit, Elevator Controls Disabled..");
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() >= 0.15F) break;
          debuggunnery("Controls: Rudder Wiring Hit, Rudder Controls Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 5:
        case 6:
        case 9:
        case 10:
          if ((getEnergyPastArmor(4.0D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          debuggunnery("Controls: Aileron Wiring Hit, Aileron Controls Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 7:
        case 8:
          if (getEnergyPastArmor(5.25F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Aileron Cranks Hit, Aileron Controls Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
        }

      }

      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxspark")) && 
          (chunkDamageVisible("Keel1") > 1) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.1000000014901161D) && (getEnergyPastArmor(3.400000095367432D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: Keel Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }

        if (paramString.startsWith("xxsparlm")) {
          if (j != 0) {
            if ((chunkDamageVisible("WingLMid") > 0) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
              debuggunnery("Spar Construction: WingLMid Spar Hit and Holed..");
              nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), paramShot.initiator);
            }
          }
          else if ((chunkDamageVisible("WingLMid") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
            debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
            nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
          }
        }

        if (paramString.startsWith("xxsparrm")) {
          if (j != 0) {
            if ((chunkDamageVisible("WingRMid") > 0) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
              debuggunnery("Spar Construction: WingRMid Spar Hit and Holed..");
              nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
            }
          }
          else if ((chunkDamageVisible("WingRMid") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
            debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
            nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
          }
        }

        if (paramString.startsWith("xxsparlo")) {
          if (j != 0) {
            if ((chunkDamageVisible("WingLOut") > 0) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
              debuggunnery("Spar Construction: WingLOut Spar Hit and Holed..");
              nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), paramShot.initiator);
            }
          }
          else if ((chunkDamageVisible("WingLOut") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
            debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
            nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
          }
        }

        if (paramString.startsWith("xxsparro")) {
          if (j != 0) {
            if ((chunkDamageVisible("WingROut") > 0) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
              debuggunnery("Spar Construction: WingROut Spar Hit and Holed..");
              nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), paramShot.initiator);
            }
          }
          else if ((chunkDamageVisible("WingROut") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
            debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
            nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
          }
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(6.5D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: StabL Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "StabL_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsr")) && 
          (chunkDamageVisible("StabR") > 1) && (getEnergyPastArmor(6.5D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: StabL Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "StabR_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxeng")) {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(3.6F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              debuggunnery("Engine Module: Prop Governor Hit, Disabled..");
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);
            } else {
              debuggunnery("Engine Module: Prop Governor Hit, Oil Pipes Damaged..");
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
            }
        }
        else if (paramString.endsWith("gear")) {
          if ((getEnergyPastArmor(4.6F, paramShot) > 0.0F) && 
            (World.Rnd().nextFloat() < 0.25F)) {
            debuggunnery("Engine Module: Reductor Hit, Bullet Jams Reductor Gear..");
            this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
          }
        }
        else if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.01F, paramShot) > 0.0F) {
            debuggunnery("Engine Module: Supercharger Disabled..");
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
          }
        } else if (paramString.endsWith("feed")) {
          if ((getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Lines Hit, Engine Stalled..");
              this.FM.EI.engines[0].setEngineStops(paramShot.initiator);
            }
            if (World.Rnd().nextFloat() < 0.05F) {
              debuggunnery("Engine Module: Feed Gear Hit, Engine Jams..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Gear Hit, Half Cylinder Feed Cut-Out..");
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 6);
            }
          }
        } else if (paramString.endsWith("fue1")) {
          if (getEnergyPastArmor(0.89F, paramShot) > 0.0F) {
            debuggunnery("Engine Module: Fuel Feed Line Pierced, Engine Fires..");
            this.FM.AS.hitEngine(paramShot.initiator, 0, 100);
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              debuggunnery("Engine Module: Crank Case Hit, Bullet Jams Ball Bearings..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              debuggunnery("Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          debuggunnery("Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          getEnergyPastArmor(22.5F, paramShot);
        } else if (paramString.endsWith("cyl1")) {
          if ((getEnergyPastArmor(1.3F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.75F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debuggunnery("Engine Module: Cylinders Assembly Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Operating..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              debuggunnery("Engine Module: Cylinders Assembly Hit, Engine Fires..");
              this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              debuggunnery("Engine Module: Cylinders Assembly Hit, Bullet Jams Piston Head..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
          if ((Math.abs(paramPoint3d.y) < 0.137999996542931D) && 
            (getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Lines Hit, Engine Stalled..");
              this.FM.EI.engines[0].setEngineStops(paramShot.initiator);
            }
            if (World.Rnd().nextFloat() < 0.05F) {
              debuggunnery("Engine Module: Feed Gear Hit, Engine Jams..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Gear Hit, Half Cylinder Feed Cut-Out..");
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 6);
            }
          }
        }
        else if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          debuggunnery("Engine Module: Magneto " + i + " Hit, Magneto " + i + " Disabled..");
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        } else if ((paramString.startsWith("xxeng1oil")) && 
          (getEnergyPastArmor(0.5F, paramShot) > 0.0F)) {
          debuggunnery("Engine Module: Oil Radiator Hit, Oil Radiator Pierced..");
          this.FM.AS.hitOil(paramShot.initiator, 0);
        }

      }

      if (paramString.startsWith("xxw1")) {
        if (this.FM.AS.astateEngineStates[0] == 0) {
          debuggunnery("Engine Module: Water Radiator Pierced..");
          this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          this.FM.AS.doSetEngineState(paramShot.initiator, 0, 1);
        } else if (this.FM.AS.astateEngineStates[0] == 1) {
          debuggunnery("Engine Module: Water Radiator Pierced..");
          this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          this.FM.AS.doSetEngineState(paramShot.initiator, 0, 2);
        }
      }

      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.12F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel System: Fuel Tank " + i + " Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, 2, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          } else if (this.FM.AS.astateTankStates[i] == 1) {
            debuggunnery("Fuel System: Fuel Tank " + i + " Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, 2, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 2);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F)) {
            this.FM.AS.hitTank(paramShot.initiator, 2, 2);
            debuggunnery("Fuel System: Fuel Tank " + i + " Pierced, State Shifted..");
          }
        }
      }

      if (paramString.startsWith("xxmgun")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Armament System: Left Machine Gun: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Armament System: Right Machine Gun: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), paramShot);
      }
      if (paramString.startsWith("xxcannon")) {
        if ((paramString.endsWith("01")) && 
          (getEnergyPastArmor(0.25F, paramShot) > 0.0F)) {
          debuggunnery("Armament System: Left Cannon: Disabled..");
          this.FM.AS.setJamBullets(1, 0);
        }

        if ((paramString.endsWith("02")) && 
          (getEnergyPastArmor(0.25F, paramShot) > 0.0F)) {
          debuggunnery("Armament System: Right Cannon: Disabled..");
          this.FM.AS.setJamBullets(1, 1);
        }

        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if (paramString.startsWith("xxammo")) {
        if ((paramString.startsWith("xxammol1")) && 
          (World.Rnd().nextFloat() < 0.023F)) {
          debuggunnery("Armament System: Left Cannon: Chain Feed Jammed, Gun Disabled..");
          this.FM.AS.setJamBullets(1, 0);
        }

        if ((paramString.startsWith("xxammor1")) && 
          (World.Rnd().nextFloat() < 0.023F)) {
          debuggunnery("Armament System: Right Cannon: Chain Feed Jammed, Gun Disabled..");
          this.FM.AS.setJamBullets(1, 1);
        }

        if ((paramString.startsWith("xxammol2")) && 
          (World.Rnd().nextFloat() < 0.023F)) {
          debuggunnery("Armament System: Left Machine Gun: Chain Feed Jammed, Gun Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }

        if ((paramString.startsWith("xxammor2")) && 
          (World.Rnd().nextFloat() < 0.023F)) {
          debuggunnery("Armament System: Right Machine Gun: Chain Feed Jammed, Gun Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }

        getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 12.6F), paramShot);
      }
      if ((paramString.startsWith("xxbomb")) && 
        (World.Rnd().nextFloat() < 0.00345F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets())) {
        debuggunnery("Armament System: Bomb Payload Detonated..");
        this.FM.AS.hitTank(paramShot.initiator, 0, 10);
        this.FM.AS.hitTank(paramShot.initiator, 1, 10);
        nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
      }

      if ((paramString.startsWith("xxpnm")) && 
        (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot) > 0.0F)) {
        debuggunnery("Pneumo System: Disabled..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 1);
      }

      if ((paramString.startsWith("xxhyd")) && 
        (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot) > 0.0F)) {
        debuggunnery("Hydro System: Disabled..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }

      if (paramString.startsWith("xxins")) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }

      return;
    }

    if ((paramString.startsWith("xcockpit")) || (paramString.startsWith("xblister")))
    {
      if (paramPoint3d.z > 0.473D) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
      else if (paramPoint3d.y > 0.0D)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      else {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      }
    }

    if (paramString.startsWith("xcf")) {
      if (paramPoint3d.x < -1.94D) {
        if (chunkDamageVisible("Tail1") < 3)
          hitChunk("Tail1", paramShot);
      }
      else {
        if (paramPoint3d.x <= 1.342D)
        {
          if ((paramPoint3d.z < -0.591D) || ((paramPoint3d.z > 0.4079999923706055D) && (paramPoint3d.x > 0.0D))) {
            getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
            if ((paramShot.power <= 0.0F) && (Math.abs(v1.x) > 0.8659999966621399D))
              doRicochet(paramShot);
          }
          else {
            getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.y) + 9.999999747378752E-005D), paramShot);
            if ((paramShot.power <= 0.0F) && (Math.abs(v1.x) > 0.8659999966621399D)) {
              doRicochet(paramShot);
            }
          }
        }
        if (chunkDamageVisible("CF") < 3)
          hitChunk("CF", paramShot);
      }
    }
    else if (paramString.startsWith("xoil")) {
      if (paramPoint3d.z < -0.981D) {
        getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
        if (paramShot.power <= 0.0F)
          doRicochet(paramShot);
      }
      else if ((paramPoint3d.x > 0.537D) || (paramPoint3d.x < -0.1D))
      {
        getEnergyPastArmor(0.2000000029802322D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        if (paramShot.power <= 0.0F)
          doRicochetBack(paramShot);
      }
      else {
        getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.y) + 9.999999747378752E-005D), paramShot);
        if (paramShot.power <= 0.0F) {
          doRicochet(paramShot);
        }
      }
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xeng")) {
      if (paramPoint3d.z > 0.159D)
        getEnergyPastArmor(1.25F * World.Rnd().nextFloat(0.95F, 1.12F) / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
      else if ((paramPoint3d.x > 1.335D) && (paramPoint3d.x < 2.386D) && (paramPoint3d.z > -0.06D) && (paramPoint3d.z < 0.064D))
        getEnergyPastArmor(0.5D / (Math.abs(v1.y) + 9.999999747378752E-005D), paramShot);
      else if ((paramPoint3d.x > 2.53D) && (paramPoint3d.x < 2.992D) && (paramPoint3d.z > -0.235D) && (paramPoint3d.z < 0.011D))
        getEnergyPastArmor(4.039999961853027D / (Math.abs(v1.y) + 9.999999747378752E-005D), paramShot);
      else if ((paramPoint3d.x > 2.559D) && (paramPoint3d.z < -0.595D))
        getEnergyPastArmor(4.039999961853027D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
      else if ((paramPoint3d.x > 1.849D) && (paramPoint3d.x < 2.251D) && (paramPoint3d.z < -0.71D))
        getEnergyPastArmor(4.039999961853027D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
      else if (paramPoint3d.x > 3.003D)
        getEnergyPastArmor(World.Rnd().nextFloat(2.3F, 3.2F), paramShot);
      else if (paramPoint3d.z < -0.6060000061988831D)
        getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
      else {
        getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.y) + 9.999999747378752E-005D), paramShot);
      }
      if ((Math.abs(v1.x) > 0.8659999966621399D) && (
        (paramShot.power <= 0.0F) || (World.Rnd().nextFloat() < 0.1F))) {
        doRicochet(paramShot);
      }

      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (k != 0) {
        if (chunkDamageVisible("Tail1") < 3)
          hitChunk("Tail1", paramShot);
      }
      else
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 2)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 1)) {
        hitChunk("StabR", paramShot);
      }
    }
    else if (paramString.startsWith("xvator")) {
      if ((paramString.startsWith("xvatorl")) && 
        (chunkDamageVisible("VatorL") < 1)) {
        hitChunk("VatorL", paramShot);
      }

      if ((paramString.startsWith("xvatorr")) && 
        (chunkDamageVisible("VatorR") < 1)) {
        hitChunk("VatorR", paramShot);
      }
    }
    else if (paramString.startsWith("xwing")) {
      if ((paramString.startsWith("xwinglin")) && 
        (chunkDamageVisible("WingLIn") < 3)) {
        hitChunk("WingLIn", paramShot);
      }

      if ((paramString.startsWith("xwingrin")) && 
        (chunkDamageVisible("WingRIn") < 3)) {
        hitChunk("WingRIn", paramShot);
      }

      if ((paramString.startsWith("xwinglmid")) && 
        (chunkDamageVisible("WingLMid") < 3)) {
        hitChunk("WingLMid", paramShot);
      }

      if ((paramString.startsWith("xwingrmid")) && 
        (chunkDamageVisible("WingRMid") < 3)) {
        hitChunk("WingRMid", paramShot);
      }

      if ((paramString.startsWith("xwinglout")) && 
        (chunkDamageVisible("WingLOut") < 3)) {
        hitChunk("WingLOut", paramShot);
      }

      if ((paramString.startsWith("xwingrout")) && 
        (chunkDamageVisible("WingROut") < 3)) {
        hitChunk("WingROut", paramShot);
      }
    }
    else if (paramString.startsWith("xarone")) {
      if (paramString.startsWith("xaronel")) {
        hitChunk("AroneL", paramShot);
      }
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xgear")) {
      if ((paramString.endsWith("1")) && 
        (World.Rnd().nextFloat() < 0.05F)) {
        debuggunnery("Hydro System: Disabled..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }

      if (((paramString.endsWith("2a")) || (paramString.endsWith("2b"))) && 
        (World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)) {
        debuggunnery("Undercarriage: Stuck..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }
    }
    else if (paramString.startsWith("xturret")) {
      if (getEnergyPastArmor(0.25F, paramShot) > 0.0F) {
        debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
        this.FM.AS.setJamBullets(10, 0);
        this.FM.AS.setJamBullets(10, 1);
        getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 26.35F), paramShot);
      }
    } else if (paramString.startsWith("xhelm")) {
      getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 3.56F), paramShot);
      if (paramShot.power <= 0.0F)
        doRicochetBack(paramShot);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int m;
      if (paramString.endsWith("a")) {
        i = 1;
        m = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        m = paramString.charAt(6) - '1';
      } else {
        m = paramString.charAt(5) - '1';
      }
      hitFlesh(m, paramShot, i);
    }
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && (paramExplosion.power > 0.0F) && 
      (paramExplosion.chunkName.startsWith("Tail1"))) {
      if (World.Rnd().nextFloat(0.0F, 0.038F) < paramExplosion.power) {
        this.FM.AS.setControlsDamage(paramExplosion.initiator, 1);
      }
      if (World.Rnd().nextFloat(0.0F, 0.042F) < paramExplosion.power) {
        this.FM.AS.setControlsDamage(paramExplosion.initiator, 2);
      }
    }

    super.msgExplosion(paramExplosion);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 3; i++)
      if (this.FM.getAltitude() < 3000.0F) {
        if (hierMesh().chunkFindCheck("HMask" + i + "_D0") != -1) {
          hierMesh().chunkVisible("HMask" + i + "_D0", false);
        }
      }
      else if (hierMesh().chunkFindCheck("HMask" + i + "_D0") != -1)
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  static
  {
    Class localClass = IL_2.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}