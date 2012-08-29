package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class HE_111 extends Scheme2
  implements TypeBomber, TypeTransport
{
  public boolean bPitUnfocused = true;

  private boolean bSightAutomation = false;
  private boolean bSightBombDump = false;
  private float fSightCurDistance = 0.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;
  public float fSightCurAltitude = 850.0F;
  public float fSightCurSpeed = 150.0F;
  public float fSightCurReadyness = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1100.0F, -80.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 60.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -13.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 36.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, -f * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, f * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -13.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 36.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, f * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, -f * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", paramFloat, 0.0F, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();

    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] - 0.055F, 0.0F, 0.1625F, 0.0F, 0.1625F);

    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);

    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] - 0.055F, 0.0F, 0.1625F, 0.0F, 0.1625F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (paramBoolean) {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3) {
        if (World.Rnd().nextFloat() < 0.05F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);
        if (World.Rnd().nextFloat() < 0.05F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);
      }
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 3) {
        if (World.Rnd().nextFloat() < 0.05F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);
        if (World.Rnd().nextFloat() < 0.05F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 3, 1);
      }
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[0] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[1] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.125F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.125F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[2] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[3] + "0", 0, this);
    }

    for (int i = 1; i < 6; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -40.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if ((this instanceof HE_111H2)) {
        if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
        if (f1 > 15.0F) { f1 = 15.0F; bool = false; }
        if (f2 < -27.0F) { f2 = -27.0F; bool = false; }
        if (f2 <= 13.0F) break; f2 = 13.0F; bool = false;
      } else {
        if (f1 < -25.0F) { f1 = -25.0F; bool = false; }
        if (f1 > 15.0F) { f1 = 15.0F; bool = false; }
        if (f2 < -40.0F) { f2 = -40.0F; bool = false; }
        if (f2 <= 0.0F) break; f2 = 0.0F; bool = false; } break;
    case 1:
      if (f1 < -42.0F) { f1 = -42.0F; bool = false; }
      if (f1 > 42.0F) { f1 = 42.0F; bool = false; }
      if (f2 < -3.0F) { f2 = -3.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 2:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 40.0F) { f1 = 40.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 46.0F) break; f2 = 46.0F; bool = false; break;
    case 3:
      if (f1 < -55.0F) { f1 = -55.0F; bool = false; }
      if (f1 > 23.0F) { f1 = 23.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 > 40.0F) { f2 = 40.0F; bool = false; }
      if (f2 <= 55.0F + 0.5F * f1) break; f2 = 55.0F + 0.5F * f1; bool = false; break;
    case 4:
      if (f1 < -23.0F) { f1 = -23.0F; bool = false; }
      if (f1 > 55.0F) { f1 = 55.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 > 40.0F) { f2 = 40.0F; bool = false; }
      if (f2 <= 55.0F - 0.5F * f1) break; f2 = 55.0F - 0.5F * f1; bool = false; break;
    case 5:
      if (f1 < -3.0F) { f1 = -3.0F; bool = false; }
      if (f1 > 3.0F) { f1 = 3.0F; bool = false; }
      if (f2 < -3.0F) { f2 = -3.0F; bool = false; }
      if (f2 <= 3.0F) break; f2 = 3.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
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
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 3);
      if (World.Rnd().nextInt(0, 99) >= 66) break; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 1); break;
    case 38:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 1, 3);
      if (World.Rnd().nextInt(0, 99) >= 66) break; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 1, 1); break;
    case 11:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 19:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 13:
      killPilot(this, 0);
      killPilot(this, 1);
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          if (Aircraft.v1.jdField_z_of_type_Double > 0.5D)
            getEnergyPastArmor(5.0D / Aircraft.v1.jdField_z_of_type_Double, paramShot);
          else if (Aircraft.v1.jdField_x_of_type_Double > 0.9396926164627075D)
            getEnergyPastArmor(10.0D / Aircraft.v1.jdField_x_of_type_Double * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        else if (paramString.endsWith("p2"))
          getEnergyPastArmor(5.0D / Math.abs(Aircraft.v1.jdField_z_of_type_Double), paramShot);
        else if ((paramString.endsWith("p3a")) || (paramString.endsWith("p3b")))
          getEnergyPastArmor(8.0D / Math.abs(Aircraft.v1.jdField_x_of_type_Double) * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        else if (paramString.endsWith("p4")) {
          if (Aircraft.v1.jdField_x_of_type_Double > 0.7071067690849304D)
            getEnergyPastArmor(8.0D / Aircraft.v1.jdField_x_of_type_Double * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          else if (Aircraft.v1.jdField_x_of_type_Double > -0.7071067690849304D)
            getEnergyPastArmor(6.0F, paramShot);
        }
        else if ((paramString.endsWith("o1")) || (paramString.endsWith("o2"))) {
          if (Aircraft.v1.jdField_x_of_type_Double > 0.7071067690849304D)
            getEnergyPastArmor(8.0D / Aircraft.v1.jdField_x_of_type_Double * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          else {
            getEnergyPastArmor(5.0F, paramShot);
          }
        }
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
          if (getEnergyPastArmor(1.0F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.12F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.12F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("Rudder Controls Out.."); break;
        case 3:
        case 4:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Ailerons Controls Out.."); break;
        case 5:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.75F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            debuggunnery("*** Engine1 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.45F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          debuggunnery("*** Engine1 Prop Controls Out.."); break;
        case 6:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.75F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 1, 1);
            debuggunnery("*** Engine2 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.45F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 1, 6);
          debuggunnery("*** Engine2 Prop Controls Out..");
        }

      }

      if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("cf1")) || (paramString.endsWith("cf2"))) && 
          (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("CF") > 2) && (getEnergyPastArmor(19.9F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F)) {
          debuggunnery("*** CF Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
          msgCollision(this, "WingLIn_D0", "WingLIn_D0");
          msgCollision(this, "WingRIn_D0", "WingRIn_D0");
        }

        if (((paramString.endsWith("ta1")) || (paramString.endsWith("ta2"))) && 
          (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(19.9F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F)) {
          debuggunnery("*** Tail1 Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }

        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(17.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(17.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("e1")) && 
          ((paramPoint3d.jdField_y_of_type_Double > 2.79D) || (paramPoint3d.jdField_y_of_type_Double < 2.32D)) && (getEnergyPastArmor(18.0F, paramShot) > 0.0F)) {
          debuggunnery("*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }

        if ((paramString.endsWith("e2")) && 
          ((paramPoint3d.jdField_y_of_type_Double < -2.79D) || (paramPoint3d.jdField_y_of_type_Double > -2.32D)) && (getEnergyPastArmor(18.0F, paramShot) > 0.0F)) {
          debuggunnery("*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }
      }

      if ((paramString.startsWith("xxbomb")) && 
        (World.Rnd().nextFloat() < 0.01F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].haveBullets())) {
        debuggunnery("*** Bomb Payload Detonates..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 100);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 100);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, 100);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 100);
        msgCollision(this, "CF_D0", "CF_D0");
      }

      if (paramString.startsWith("xxprop")) {
        i = 0;
        if (paramString.endsWith("2")) {
          i = 1;
        }
        if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.35F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 3);
          debuggunnery("*** Engine" + (i + 1) + " Governor Failed..");
        }
      }
      if (paramString.startsWith("xxengine")) {
        i = 0;
        if (paramString.startsWith("xxengine2")) {
          i = 1;
        }
        if (paramString.endsWith("base")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, i);
              debuggunnery("*** Engine" + (i + 1) + " Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
              debuggunnery("*** Engine" + (i + 1) + " Crank Case Hit - Engine Damaged..");
            }

          }

        }
        else if (paramString.endsWith("cyl")) {
          if ((getEnergyPastArmor(1.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersRatio() * 0.5F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debuggunnery("*** Engine" + (i + 1) + " Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] < 1) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetEngineState(paramShot.initiator, i, 1);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 960000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 3);
              debuggunnery("*** Engine" + (i + 1) + " Cylinders Hit - Engine Fires..");
            }
            getEnergyPastArmor(25.0F, paramShot);
          }
        } else if ((paramString.endsWith("sup")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 0);
          debuggunnery("*** Engine" + (i + 1) + " Supercharger Out..");
        }
      }

      if (paramString.startsWith("xxoil")) {
        i = 0;
        if (paramString.endsWith("2")) {
          i = 1;
        }
        if (getEnergyPastArmor(0.21F, paramShot) > 0.0F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.42F, paramShot);
        }
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
          if (paramShot.power < 14100.0F) {
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 1);
            }
            if (World.Rnd().nextFloat() < 0.02F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            }
            if ((paramShot.powerType == 3) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] > 2) && (World.Rnd().nextFloat() < 0.4F))
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 10);
          }
          else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 56000.0F)));
          }
        }
      }
      return;
    }

    if (paramString.startsWith("xoil")) {
      if (paramString.equals("xoil1")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        paramString = "xengine1";
      }
      if (paramString.equals("xoil2")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 1);
        paramString = "xengine2";
      }
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xnose")) {
      if (chunkDamageVisible("Nose1") < 2) {
        hitChunk("Nose1", paramShot);
      }
      if (paramShot.power > 200000.0F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 0, World.Rnd().nextInt(3, 192));
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 1, World.Rnd().nextInt(3, 192));
      }
      if (World.Rnd().nextFloat() < 0.1F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
      if (paramPoint3d.jdField_x_of_type_Double > 4.505000114440918D) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
      } else if (paramPoint3d.jdField_y_of_type_Double > 0.0D) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
        if (World.Rnd().nextFloat() < 0.1F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
        if (World.Rnd().nextFloat() < 0.1F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
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
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    } else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwinglin")) {
      if (chunkDamageVisible("WingLIn") < 3)
        hitChunk("WingLIn", paramShot);
    }
    else if (paramString.startsWith("xwingrin")) {
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

    }
    else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2) {
        hitChunk("Engine2", paramShot);
      }

    }
    else if (paramString.startsWith("xgear")) {
      if (World.Rnd().nextFloat() < 0.1F) {
        debuggunnery("*** Gear Hydro Failed..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.setHydroOperable(false);
      }
    } else if (paramString.startsWith("xturret")) {
      if (paramString.startsWith("xturret1")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 0);
      }
      if (paramString.startsWith("xturret2")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 0);
      }
      if (paramString.startsWith("xturret3")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(12, 0);
      }
      if (paramString.startsWith("xturret4")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(13, 0);
      }
      if (paramString.startsWith("xturret5"))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(14, 0);
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

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      if ((this.bPitUnfocused) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout)) {
        hierMesh().chunkVisible("Pilot1_FAK", false);
        hierMesh().chunkVisible("Pilot1_FAL", true);
        hierMesh().chunkVisible("Head1_FAK", false);
      }
      if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Pilot1_FAK", false);
      hierMesh().chunkVisible("Pilot1_FAL", false);
      hierMesh().chunkVisible("Head1_FAK", false); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      if ((this.bPitUnfocused) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout)) {
        hierMesh().chunkVisible("Pilot2_FAK", false);
        hierMesh().chunkVisible("Pilot2_FAL", true);
        hierMesh().chunkVisible("HMask2_D0", false);
      }
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) {
        hierMesh().chunkVisible("Pilot2_FAK", false);
        hierMesh().chunkVisible("Pilot2_FAL", false);
        hierMesh().chunkVisible("HMask2_D0", false);
      }
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      hierMesh().chunkVisible("HMask3_D0", false);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length != 6) break; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[5].bIsOperable = false; break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
      hierMesh().chunkVisible("HMask4_D0", false);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 4:
      hierMesh().chunkVisible("Pilot5_D0", false);
      hierMesh().chunkVisible("Pilot5_D1", true);
      hierMesh().chunkVisible("HMask5_D0", false);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      if ((!hierMesh().isChunkVisible("Nose_D0")) && (!hierMesh().isChunkVisible("Nose_D1")) && (!hierMesh().isChunkVisible("Nose_D2")) && (!hierMesh().isChunkVisible("Nose_D3")))
      {
        break;
      }
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      if ((!hierMesh().isChunkVisible("Nose_D0")) && (!hierMesh().isChunkVisible("Nose_D1")) && (!hierMesh().isChunkVisible("Nose_D2")) && (!hierMesh().isChunkVisible("Nose_D3")))
      {
        break;
      }
      hierMesh().chunkVisible("Gore2_D0", true); break;
    case 2:
      hierMesh().chunkVisible("Gore3_D0", true);
    }
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
    if (this.fSightCurForwardAngle > 85.0F) {
      this.fSightCurForwardAngle = 85.0F;
    }
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F) {
      this.fSightCurForwardAngle = 0.0F;
    }
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 0.1F;
    if (this.fSightCurSideslip > 3.0F) {
      this.fSightCurSideslip = 3.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjSideslipMinus() {
    this.fSightCurSideslip -= 0.1F;
    if (this.fSightCurSideslip < -3.0F) {
      this.fSightCurSideslip = -3.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjAltitudeReset() {
    this.fSightCurAltitude = 850.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F) {
      this.fSightCurAltitude = 10000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus() {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 850.0F) {
      this.fSightCurAltitude = 850.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset() {
    this.fSightCurSpeed = 150.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 600.0F) {
      this.fSightCurSpeed = 600.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus() {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 150.0F) {
      this.fSightCurSpeed = 150.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat) {
    if (Math.abs(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.getKren()) > 4.5D) {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F) {
        this.fSightCurReadyness = 0.0F;
      }
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    } else if (this.bSightAutomation)
    {
      this.fSightCurDistance -= this.fSightCurSpeed / 3.6F * paramFloat;
      if (this.fSightCurDistance < 0.0F) {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / this.fSightCurAltitude));
      if (this.fSightCurDistance < this.fSightCurSpeed / 3.6F * Math.sqrt(this.fSightCurAltitude * 0.203874F)) {
        this.bSightBombDump = true;
      }
      if (this.bSightBombDump)
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTick(3, 0)) {
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].haveBullets())) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = false;
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

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
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

  static
  {
    Class localClass = HE_111.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}