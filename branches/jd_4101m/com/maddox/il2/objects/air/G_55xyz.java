package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class G_55xyz extends Scheme1
  implements TypeFighter
{
  private static final float[] gear8scale = { 0.0F, 3.75F, 13.75F, 27.68F, 42.75F, 57.610001F, 70.93F, 82.25F, 91.0F, 96.699997F, 98.800003F };

  public float canopyF = 0.0F;
  private boolean tiltCanopyOpened = false;
  private boolean slideCanopyOpened = false;
  private boolean blisterRemoved = false;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    this.FM.Gears.computePlaneLandPose(this.FM);
    mydebuggunnery("H = " + this.FM.Gears.H);
    mydebuggunnery("Pitch = " + this.FM.Gears.Pitch);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    if (paramInt1 == 19)
      this.FM.Gears.hitCentreGear();
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("hmask1_D0", false);
    else {
      hierMesh().chunkVisible("hmask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }
    if ((this.tiltCanopyOpened) && (!this.blisterRemoved) && (this.FM.getSpeed() > 75.0F))
    {
      doRemoveBlister1();
    }

    if (paramBoolean)
    {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.39F)) {
        this.FM.AS.hitTank(this, 0, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
    }
  }

  private final void doRemoveBlister1()
  {
    this.blisterRemoved = true;
    if (hierMesh().chunkFindCheck("Blister1_D0") != -1)
    {
      hierMesh().hideSubTrees("Blister1_D0");
      Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
      localWreckage.collide(true);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.FM.Vwld);
      localWreckage.setSpeed(localVector3d);
    }
  }

  public void moveCockpitDoor(float paramFloat)
  {
    if (paramFloat > this.canopyF)
    {
      if (((this.FM.Gears.onGround()) && (this.FM.getSpeed() < 5.0F)) || ((this.tiltCanopyOpened) && ((this.FM.isPlayers()) || (isNetPlayer()))))
      {
        this.tiltCanopyOpened = true;
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100.0F * paramFloat, 0.0F);
      }
      else
      {
        this.slideCanopyOpened = true;
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.28F);
        hierMesh().chunkSetLocate("Blister4L_D0", Aircraft.xyz, Aircraft.ypr);
      }

    }
    else if (((this.FM.Gears.onGround()) && (this.FM.getSpeed() < 5.0F) && (!this.slideCanopyOpened)) || (this.tiltCanopyOpened))
    {
      hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100.0F * paramFloat, 0.0F);
      if ((this.FM.getSpeed() > 70.0F) && (paramFloat < 0.6F) && (!this.blisterRemoved))
      {
        doRemoveBlister1();
      }
      if (paramFloat == 0.0F)
        this.tiltCanopyOpened = false;
    }
    else
    {
      resetYPRmodifier();
      Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.3F);
      hierMesh().chunkSetLocate("Blister4L_D0", Aircraft.xyz, Aircraft.ypr);

      if (paramFloat == 0.0F) {
        this.slideCanopyOpened = false;
      }
    }
    this.canopyF = paramFloat;

    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  private static final float floatindex(float paramFloat, float[] paramArrayOfFloat) {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1)
      return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    if (i < 0)
      return paramArrayOfFloat[0];
    if (i == 0) {
      if (paramFloat > 0.0F)
        return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      return paramArrayOfFloat[0];
    }
    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.18F, 0.99F, 0.0F, 90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.2F, 0.0F, 90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("Gearl6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.18F, 0.99F, 0.0F, -30.0F), 0.0F);
    float tmp90_89 = (Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F); Aircraft.xyz[1] = tmp90_89; Aircraft.xyz[0] = tmp90_89;

    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.3F, 0.99F, 0.0F, 0.45F);
    paramHierMesh.chunkSetLocate("Gearl9_D0", Aircraft.xyz, Aircraft.ypr);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.18F, 0.99F, 0.0F, 90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.2F, 0.0F, 90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.18F, 0.99F, 0.0F, 30.0F), 0.0F);
    float tmp211_210 = (Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F); Aircraft.xyz[1] = tmp211_210; Aircraft.xyz[0] = tmp211_210;

    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.3F, 0.99F, 0.0F, 0.45F);
    paramHierMesh.chunkSetLocate("GearR9_D0", Aircraft.xyz, Aircraft.ypr);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.11F, 0.67F, 0.0F, -70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC3L_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.15F, 0.0F, 80.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC3R_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.15F, 0.0F, -80.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.2085F, 0.0F, 0.2085F);

    hierMesh().chunkSetLocate("GearL8_D0", Aircraft.xyz, Aircraft.ypr);

    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.2085F, 0.0F, 0.2085F);

    hierMesh().chunkSetLocate("GearR8_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -35.0F * paramFloat;
    hierMesh().chunkSetAngles("FlapInL_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("FlapInR_D0", 0.0F, f, 0.0F);
    f = -35.0F * paramFloat;
    hierMesh().chunkSetAngles("FlapOutL_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("FlapOutR_D0", 0.0F, f, 0.0F);
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Filter_D0", 0.0F, -20.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);

    super.update(paramFloat);
  }

  protected void setControlDamage(Shot paramShot, int paramInt)
  {
    if (World.Rnd().nextFloat() < 0.002F)
    {
      if (getEnergyPastArmor(4.0F, paramShot) > 0.0F)
      {
        this.FM.AS.setControlsDamage(paramShot.initiator, paramInt);
        mydebuggunnery(paramInt + " Controls Out... //0 = AILERON, 1 = ELEVATOR, 2 = RUDDER");
      }
    }
  }

  protected void moveAileron(float paramFloat)
  {
    float f = -(paramFloat * 30.0F);
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, f, 0.0F);

    f = -(paramFloat * 30.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, f, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -31.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    if (paramFloat < 0.0F)
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -20.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    }
    else
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        mydebuggunnery("Armor: Hit..");
        if (paramString.startsWith("xxarmorp")) {
          j = paramString.charAt(8) - '0';
          switch (j) {
          case 1:
            getEnergyPastArmor(22.760000228881836D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

            if (paramShot.power > 0.0F) break;
            doRicochetBack(paramShot); break;
          case 3:
            getEnergyPastArmor(9.366F, paramShot);
            break;
          case 5:
            getEnergyPastArmor(12.699999809265137D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
          case 2:
          case 4:
          }
        }

      }
      else if (paramString.startsWith("xxcontrols")) {
        mydebuggunnery("Controls: Hit..");
        j = paramString.charAt(10) - '0';
        switch (j) {
        case 1:
        case 2:
          if (getEnergyPastArmor(0.25F / ((float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z) + 1.0E-004F), paramShot) <= 0.0F)
          {
            break;
          }

          if (World.Rnd().nextFloat() < 0.05F) {
            mydebuggunnery("Controls: Elevator Wiring Hit, Elevator Controls Disabled..");

            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() >= 0.75F) break;
          mydebuggunnery("Controls: Rudder Wiring Hit, Rudder Controls Disabled..");

          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 3:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F) break;
          mydebuggunnery("*** Control Column: Hit, Controls Destroyed..");

          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 4:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);

          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          mydebuggunnery("Quadrant: Hit, Engine Controls Disabled.."); break;
        case 5:
        case 7:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          mydebuggunnery("*** Aileron Controls: Control Crank Destroyed.."); break;
        case 6:
        case 8:
          if ((getEnergyPastArmor(4.0D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
          {
            break;
          }
          mydebuggunnery("Controls: Aileron Wiring Hit, Aileron Controls Disabled..");

          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
        }

      }
      else if (paramString.startsWith("xxspar")) {
        mydebuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparri")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparlo")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparro")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          mydebuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
      } else {
        if (paramString.startsWith("xxlock")) {
          mydebuggunnery("Lock Construction: Hit..");
          if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: Rudder1 Lock Shot Off..");

            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: VatorL Lock Shot Off..");

            nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: VatorR Lock Shot Off..");

            nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockalL")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: AroneL Lock Shot Off..");

            nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockalR")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: AroneR Lock Shot Off..");

            nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
          }

        }

        if (paramString.startsWith("xxeng")) {
          if (((paramString.endsWith("prop")) || (paramString.endsWith("pipe"))) && (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
          {
            this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
          }if ((paramString.endsWith("case")) || (paramString.endsWith("gear"))) {
            if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
              if (World.Rnd().nextFloat() < paramShot.power / 140000.0F)
              {
                this.FM.AS.setEngineStuck(paramShot.initiator, 0);
                mydebuggunnery("*** Engine Crank Case Hit - Engine Stucks..");
              }

              if (World.Rnd().nextFloat() < paramShot.power / 85000.0F)
              {
                this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
                mydebuggunnery("*** Engine Crank Case Hit - Engine Damaged..");
              }
            }
            else if (World.Rnd().nextFloat() < 0.02F) {
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
            }
            else {
              this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));

              mydebuggunnery("*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
            }

            getEnergyPastArmor(12.0F, paramShot);
          }
          if ((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2"))) {
            if ((getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 4.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.75F))
            {
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));

              mydebuggunnery("*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");

              if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
              {
                this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
                mydebuggunnery("*** Engine Cylinders Hit - Engine Fires..");
              }
            }

            getEnergyPastArmor(25.0F, paramShot);
          }
          if (paramString.endsWith("supc")) {
            if (getEnergyPastArmor(0.05F, paramShot) > 0.0F)
              this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
            getEnergyPastArmor(2.0F, paramShot);
          }
          if (paramString.startsWith("xxeng1oil")) {
            this.FM.AS.hitOil(paramShot.initiator, 0);
            mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
          }

          mydebuggunnery("*** Engine state = " + this.FM.AS.astateEngineStates[0]);
        }
        else if (paramString.startsWith("xxtank"))
        {
          j = paramString.charAt(6) - '1';
          if ((getEnergyPastArmor(0.19F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
          {
            if (this.FM.AS.astateTankStates[j] == 0) {
              mydebuggunnery("Fuel System: Fuel Tank Pierced..");
              this.FM.AS.hitTank(paramShot.initiator, j, 1);
              this.FM.AS.doSetTankState(paramShot.initiator, j, 1);
            } else if (this.FM.AS.astateTankStates[j] == 1) {
              mydebuggunnery("Fuel System: Fuel Tank Pierced (2)..");

              this.FM.AS.hitTank(paramShot.initiator, j, 1);
              this.FM.AS.doSetTankState(paramShot.initiator, j, 2);
            }
            if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F))
            {
              this.FM.AS.hitTank(paramShot.initiator, j, 2);
              mydebuggunnery("Fuel System: Fuel Tank Pierced, State Shifted..");
            }
          }

          mydebuggunnery("Tank State: " + this.FM.AS.astateTankStates[j]);
        }
        else if (paramString.startsWith("xxmgun")) {
          if (paramString.endsWith("01")) {
            mydebuggunnery("Armament System: Left Machine Gun: Disabled..");

            this.FM.AS.setJamBullets(0, 0);
          }
          if (paramString.endsWith("02")) {
            mydebuggunnery("Armament System: Right Machine Gun: Disabled..");

            this.FM.AS.setJamBullets(1, 0);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), paramShot);
        }
        else if (paramString.startsWith("xxcannon")) {
          if (paramString.endsWith("02")) {
            mydebuggunnery("Armament System: Left Cannon: Disabled..");

            this.FM.AS.setJamBullets(3, 0);
          }
          if (paramString.endsWith("03")) {
            mydebuggunnery("Armament System: Right Cannon: Disabled..");

            this.FM.AS.setJamBullets(4, 0);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), paramShot);
        }
      }
    }
    else if (paramString.startsWith("xcf")) {
      setControlDamage(paramShot, 0);
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstab")) {
      if (paramString.startsWith("xstabl"))
        hitChunk("StabL", paramShot);
      if (paramString.startsWith("xstabr"))
        hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvator")) {
      if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1))
      {
        hitChunk("VatorL", paramShot);
      }if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
      {
        hitChunk("VatorR", paramShot);
      }
    } else if (paramString.startsWith("xwing")) {
      if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingLIn", paramShot);
      }
      if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingRIn", paramShot);
      }
      if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingLMid", paramShot);
      }
      if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingRMid", paramShot);
      }
      if ((paramString.startsWith("xwinglout1")) && (chunkDamageVisible("WingLOut") < 3))
      {
        hitChunk("WingLOut", paramShot);
      }if ((paramString.startsWith("xwingrout1")) && (chunkDamageVisible("WingROut") < 3))
      {
        hitChunk("WingROut", paramShot);
      }
    } else if (paramString.startsWith("xarone")) {
      if ((paramString.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
      {
        hitChunk("AroneL", paramShot);
      }if ((paramString.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
      {
        hitChunk("AroneR", paramShot);
      }
    } else if (paramString.startsWith("xgear")) {
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
      {
        mydebuggunnery("Undercarriage: Stuck..");

        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }
    } else if (paramString.startsWith("xoil")) {
      if (World.Rnd().nextFloat() < 0.12F) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
      }
    }
    else if ((!paramString.startsWith("xblister")) && ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))))
    {
      j = 0;
      int k;
      if (paramString.endsWith("a")) {
        j = 1;
        k = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        j = 2;
        k = paramString.charAt(6) - '1';
      } else {
        k = paramString.charAt(5) - '1';
      }hitFlesh(k, paramShot, j);
    }
  }

  protected void mydebuggunnery(String paramString)
  {
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "originCountry", PaintScheme.countryItaly);
  }
}