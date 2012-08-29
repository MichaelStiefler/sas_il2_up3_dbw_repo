package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class Centauroxyz extends Scheme1
  implements TypeFighter
{
  private static final float[] gear8scale = { 
    0.0F, 3.75F, 13.75F, 27.68F, 42.75F, 57.610001F, 70.93F, 82.25F, 91.0F, 96.699997F, 
    98.800003F };
  public float canopyF;
  private boolean tiltCanopyOpened;
  private boolean slideCanopyOpened;
  private boolean blisterRemoved;

  static
  {
    Class class1 = CLASS.THIS();
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "originCountry", PaintScheme.countryItaly);
  }

  public Centauroxyz()
  {
    this.canopyF = 0.0F;
    this.tiltCanopyOpened = false;
    this.slideCanopyOpened = false;
    this.blisterRemoved = false;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.Gears.computePlaneLandPose(this.FM);
    mydebuggunnery("H = " + this.FM.Gears.H);
    mydebuggunnery("Pitch = " + this.FM.Gears.Pitch);
  }

  public void doMurderPilot(int i)
  {
    switch (i)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  protected boolean cutFM(int i, int j, Actor actor)
  {
    if (i == 19)
    {
      this.FM.Gears.hitCentreGear();
    }
    return super.cutFM(i, j, actor);
  }

  public void rareAction(float f, boolean flag)
  {
    super.rareAction(f, flag);
    if (this.FM.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("hmask1_D0", false);
    }
    else {
      hierMesh().chunkVisible("hmask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }
    if ((this.tiltCanopyOpened) && (!this.blisterRemoved) && (this.FM.getSpeed() > 75.0F))
    {
      doRemoveBlister1();
    }
    if (flag)
    {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.39F))
      {
        this.FM.AS.hitTank(this, 0, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.1F))
      {
        nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      }
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.1F))
      {
        nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      }
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.1F))
      {
        nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      }
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.1F))
      {
        nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
      }
    }
  }

  private final void doRemoveBlister1()
  {
    this.blisterRemoved = true;
    if (hierMesh().chunkFindCheck("Blister1_D0") != -1)
    {
      hierMesh().hideSubTrees("Blister1_D0");
      Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
      wreckage.collide(true);
      Vector3d vector3d = new Vector3d();
      vector3d.set(this.FM.Vwld);
      wreckage.setSpeed(vector3d);
    }
  }

  public void moveCockpitDoor(float f)
  {
    if (f > this.canopyF)
    {
      if (((this.FM.Gears.onGround()) && (this.FM.getSpeed() < 5.0F)) || ((this.tiltCanopyOpened) && ((this.FM.isPlayers()) || (isNetPlayer()))))
      {
        this.tiltCanopyOpened = true;
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100.0F * f, 0.0F);
      }
      else
      {
        this.slideCanopyOpened = true;
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.28F);
        hierMesh().chunkSetLocate("Blister4L_D0", Aircraft.xyz, Aircraft.ypr);
      }
    }
    else if (((this.FM.Gears.onGround()) && (this.FM.getSpeed() < 5.0F) && (!this.slideCanopyOpened)) || (this.tiltCanopyOpened))
    {
      hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100.0F * f, 0.0F);
      if ((this.FM.getSpeed() > 70.0F) && (f < 0.6F) && (!this.blisterRemoved))
      {
        doRemoveBlister1();
      }
      if (f == 0.0F)
      {
        this.tiltCanopyOpened = false;
      }
    }
    else {
      resetYPRmodifier();
      Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.3F);
      hierMesh().chunkSetLocate("Blister4L_D0", Aircraft.xyz, Aircraft.ypr);
      if (f == 0.0F)
      {
        this.slideCanopyOpened = false;
      }
    }
    this.canopyF = f;
    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(f);
      }
      setDoorSnd(f);
    }
  }

  private static final float floatindex(float f, float[] af)
  {
    int i = (int)f;
    if (i >= af.length - 1)
    {
      return af[(af.length - 1)];
    }
    if (i < 0)
    {
      return af[0];
    }
    if (i == 0)
    {
      if (f > 0.0F)
      {
        return af[0] + f * (af[1] - af[0]);
      }

      return af[0];
    }

    return af[i] + f % i * (af[(i + 1)] - af[i]);
  }

  public static void moveGear(HierMesh hiermesh, float f)
  {
    hiermesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(f, 0.18F, 0.99F, 0.0F, 90.0F), 0.0F);
    hiermesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(f, 0.02F, 0.2F, 0.0F, 90.0F), 0.0F);
    hiermesh.chunkSetAngles("Gearl6_D0", 0.0F, Aircraft.cvt(f, 0.18F, 0.99F, 0.0F, -30.0F), 0.0F);
    float tmp102_101 = (Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F); Aircraft.xyz[1] = tmp102_101; Aircraft.xyz[0] = tmp102_101;
    Aircraft.xyz[1] = Aircraft.cvt(f, 0.3F, 0.99F, 0.0F, 0.45F);
    hiermesh.chunkSetLocate("Gearl9_D0", Aircraft.xyz, Aircraft.ypr);
    hiermesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(f, 0.18F, 0.99F, 0.0F, 90.0F), 0.0F);
    hiermesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(f, 0.02F, 0.2F, 0.0F, 90.0F), 0.0F);
    hiermesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(f, 0.18F, 0.99F, 0.0F, 30.0F), 0.0F);
    float tmp239_238 = (Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F); Aircraft.xyz[1] = tmp239_238; Aircraft.xyz[0] = tmp239_238;
    Aircraft.xyz[1] = Aircraft.cvt(f, 0.3F, 0.99F, 0.0F, 0.45F);
    hiermesh.chunkSetLocate("GearR9_D0", Aircraft.xyz, Aircraft.ypr);
    hiermesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(f, 0.11F, 0.67F, 0.0F, -70.0F), 0.0F);
    hiermesh.chunkSetAngles("GearC3L_D0", 0.0F, Aircraft.cvt(f, 0.0F, 0.15F, 0.0F, 80.0F), 0.0F);
    hiermesh.chunkSetAngles("GearC3R_D0", 0.0F, Aircraft.cvt(f, 0.0F, 0.15F, 0.0F, -80.0F), 0.0F);
  }

  protected void moveGear(float f)
  {
    moveGear(hierMesh(), f);
  }

  public void moveSteering(float f)
  {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.2085F, 0.0F, 0.2085F);
    hierMesh().chunkSetLocate("GearL8_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.2085F, 0.0F, 0.2085F);
    hierMesh().chunkSetLocate("GearR8_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveFlap(float f)
  {
    float f1 = -35.0F * f;
    hierMesh().chunkSetAngles("FlapInL_D0", 0.0F, f1, 0.0F);
    hierMesh().chunkSetAngles("FlapInR_D0", 0.0F, f1, 0.0F);
    f1 = -35.0F * f;
    hierMesh().chunkSetAngles("FlapOutL_D0", 0.0F, f1, 0.0F);
    hierMesh().chunkSetAngles("FlapOutR_D0", 0.0F, f1, 0.0F);
  }

  public void update(float f)
  {
    hierMesh().chunkSetAngles("Filter_D0", 0.0F, -20.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    super.update(f);
  }

  protected void setControlDamage(Shot shot, int i)
  {
    if ((World.Rnd().nextFloat() < 0.002F) && (getEnergyPastArmor(4.0F, shot) > 0.0F))
    {
      this.FM.AS.setControlsDamage(shot.initiator, i);
      mydebuggunnery(i + " Controls Out... //0 = AILERON, 1 = ELEVATOR, 2 = RUDDER");
    }
  }

  protected void moveAileron(float f)
  {
    float f1 = -(f * 30.0F);
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, f1, 0.0F);
    f1 = -(f * 30.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, f1, 0.0F);
  }

  protected void moveRudder(float f)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -31.0F * f, 0.0F);
  }

  protected void moveElevator(float f)
  {
    if (f < 0.0F)
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -20.0F * f, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -20.0F * f, 0.0F);
    }
    else {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * f, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30.0F * f, 0.0F);
    }
  }

  protected void hitBone(String s, Shot shot, Point3d point3d)
  {
    boolean flag = false;
    if (s.startsWith("xx"))
    {
      if (s.startsWith("xxarmor"))
      {
        mydebuggunnery("Armor: Hit..");
        if (s.startsWith("xxarmorp"))
        {
          int i = s.charAt(8) - '0';
          switch (i)
          {
          case 1:
            getEnergyPastArmor(17.760000228881836D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), shot);
            if (shot.power > 0.0F)
              break;
            doRicochetBack(shot);

            break;
          case 3:
            getEnergyPastArmor(9.366F, shot);
            break;
          case 5:
            getEnergyPastArmor(12.699999809265137D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), shot);
          case 2:
          case 4:
          default:
            break;
          }
        }
      } else if (s.startsWith("xxcontrols"))
      {
        mydebuggunnery("Controls: Hit..");
        int j = s.charAt(10) - '0';
        switch (j)
        {
        case 1:
        case 2:
          if (getEnergyPastArmor(0.25F / ((float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z) + 1.0E-004F), shot) <= 0.0F)
            break;
          if (World.Rnd().nextFloat() < 0.05F)
          {
            mydebuggunnery("Controls: Elevator Wiring Hit, Elevator Controls Disabled..");
            this.FM.AS.setControlsDamage(shot.initiator, 1);
          }
          if (World.Rnd().nextFloat() >= 0.75F)
            break;
          mydebuggunnery("Controls: Rudder Wiring Hit, Rudder Controls Disabled..");
          this.FM.AS.setControlsDamage(shot.initiator, 2);

          break;
        case 3:
          if (getEnergyPastArmor(3.2F, shot) <= 0.0F)
            break;
          mydebuggunnery("*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(shot.initiator, 2);
          this.FM.AS.setControlsDamage(shot.initiator, 1);
          this.FM.AS.setControlsDamage(shot.initiator, 0);

          break;
        case 4:
          if (getEnergyPastArmor(0.1F, shot) <= 0.0F)
            break;
          this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x8);
          this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
          mydebuggunnery("Quadrant: Hit, Engine Controls Disabled..");

          break;
        case 5:
        case 7:
          if (getEnergyPastArmor(0.1F, shot) <= 0.0F)
            break;
          this.FM.AS.setControlsDamage(shot.initiator, 0);
          mydebuggunnery("*** Aileron Controls: Control Crank Destroyed..");

          break;
        case 6:
        case 8:
          if ((getEnergyPastArmor(4.0D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), shot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          mydebuggunnery("Controls: Aileron Wiring Hit, Aileron Controls Disabled..");
          this.FM.AS.setControlsDamage(shot.initiator, 0);
        default:
          break;
        }
      } else if (s.startsWith("xxspar"))
      {
        mydebuggunnery("Spar Construction: Hit..");
        if ((s.startsWith("xxsparli")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(4.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), shot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparri")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(4.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), shot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparlo")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(4.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), shot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparro")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(4.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), shot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
        }
        if ((s.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(2.26F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), shot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          mydebuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
        }
      }
      else {
        if (s.startsWith("xxlock"))
        {
          mydebuggunnery("Lock Construction: Hit..");
          if ((s.startsWith("xxlockr")) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
          }
          if ((s.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: VatorL Lock Shot Off..");
            nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
          }
          if ((s.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: VatorR Lock Shot Off..");
            nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
          }
          if ((s.startsWith("xxlockalL")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: AroneL Lock Shot Off..");
            nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
          }
          if ((s.startsWith("xxlockalR")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: AroneR Lock Shot Off..");
            nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
          }
        }
        if (s.startsWith("xxeng"))
        {
          if (((s.endsWith("prop")) || (s.endsWith("pipe"))) && (getEnergyPastArmor(0.2F, shot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
          {
            this.FM.EI.engines[0].setKillPropAngleDevice(shot.initiator);
          }
          if ((s.endsWith("case")) || (s.endsWith("gear")))
          {
            if (getEnergyPastArmor(0.2F, shot) > 0.0F)
            {
              if (World.Rnd().nextFloat() < shot.power / 140000.0F)
              {
                this.FM.AS.setEngineStuck(shot.initiator, 0);
                mydebuggunnery("*** Engine Crank Case Hit - Engine Stucks..");
              }
              if (World.Rnd().nextFloat() < shot.power / 85000.0F)
              {
                this.FM.AS.hitEngine(shot.initiator, 0, 2);
                mydebuggunnery("*** Engine Crank Case Hit - Engine Damaged..");
              }
            }
            else if (World.Rnd().nextFloat() < 0.02F)
            {
              this.FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
            }
            else {
              this.FM.EI.engines[0].setReadyness(shot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, shot.power / 48000.0F));
              mydebuggunnery("*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
            }
            getEnergyPastArmor(12.0F, shot);
          }
          if ((s.endsWith("cyl1")) || (s.endsWith("cyl2")))
          {
            if ((getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 4.0F), shot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.75F))
            {
              this.FM.EI.engines[0].setCyliderKnockOut(shot.initiator, World.Rnd().nextInt(1, (int)(shot.power / 19000.0F)));
              mydebuggunnery("*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
              if (World.Rnd().nextFloat() < shot.power / 48000.0F)
              {
                this.FM.AS.hitEngine(shot.initiator, 0, 2);
                mydebuggunnery("*** Engine Cylinders Hit - Engine Fires..");
              }
            }
            getEnergyPastArmor(25.0F, shot);
          }
          if (s.endsWith("supc"))
          {
            if (getEnergyPastArmor(0.05F, shot) > 0.0F)
            {
              this.FM.EI.engines[0].setKillCompressor(shot.initiator);
            }
            getEnergyPastArmor(2.0F, shot);
          }
          if (s.startsWith("xxeng1oil"))
          {
            this.FM.AS.hitOil(shot.initiator, 0);
            mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
          }
          mydebuggunnery("*** Engine state = " + this.FM.AS.astateEngineStates[0]);
        }
        else if (s.startsWith("xxtank"))
        {
          int k = s.charAt(6) - '1';
          if ((getEnergyPastArmor(0.19F, shot) > 0.0F) && (World.Rnd().nextFloat() < 0.13F))
          {
            if (this.FM.AS.astateTankStates[k] == 0)
            {
              mydebuggunnery("Fuel System: Fuel Tank Pierced..");
              this.FM.AS.hitTank(shot.initiator, k, 1);
              this.FM.AS.doSetTankState(shot.initiator, k, 1);
            }
            else if (this.FM.AS.astateTankStates[k] == 1)
            {
              mydebuggunnery("Fuel System: Fuel Tank Pierced (2)..");
              this.FM.AS.hitTank(shot.initiator, k, 1);
              this.FM.AS.doSetTankState(shot.initiator, k, 2);
            }
            if ((shot.powerType == 3) && (World.Rnd().nextFloat() < 0.21F))
            {
              this.FM.AS.hitTank(shot.initiator, k, 2);
              mydebuggunnery("Fuel System: Fuel Tank Pierced, State Shifted..");
            }
          }
          mydebuggunnery("Tank State: " + this.FM.AS.astateTankStates[k]);
        }
        else if (s.startsWith("xxmgun"))
        {
          if (s.endsWith("01"))
          {
            mydebuggunnery("Armament System: Left Machine Gun: Disabled..");
            this.FM.AS.setJamBullets(0, 0);
          }
          if (s.endsWith("02"))
          {
            mydebuggunnery("Armament System: Right Machine Gun: Disabled..");
            this.FM.AS.setJamBullets(1, 0);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), shot);
        }
        else if (s.startsWith("xxcannon"))
        {
          if (s.endsWith("02"))
          {
            mydebuggunnery("Armament System: Left Cannon: Disabled..");
            this.FM.AS.setJamBullets(3, 0);
          }
          if (s.endsWith("03"))
          {
            mydebuggunnery("Armament System: Right Cannon: Disabled..");
            this.FM.AS.setJamBullets(4, 0);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), shot);
        }
      }
    }
    else if (s.startsWith("xcf"))
    {
      setControlDamage(shot, 0);
      setControlDamage(shot, 1);
      setControlDamage(shot, 2);
      if (chunkDamageVisible("CF") < 3)
      {
        hitChunk("CF", shot);
      }
    }
    else if (s.startsWith("xeng"))
    {
      if (chunkDamageVisible("Engine1") < 2)
      {
        hitChunk("Engine1", shot);
      }
    }
    else if (s.startsWith("xtail"))
    {
      setControlDamage(shot, 1);
      setControlDamage(shot, 2);
      if (chunkDamageVisible("Tail1") < 3)
      {
        hitChunk("Tail1", shot);
      }
    }
    else if (s.startsWith("xkeel"))
    {
      hitChunk("Keel1", shot);
    }
    else if (s.startsWith("xrudder"))
    {
      setControlDamage(shot, 2);
      if (chunkDamageVisible("Rudder1") < 1)
      {
        hitChunk("Rudder1", shot);
      }
    }
    else if (s.startsWith("xstab"))
    {
      if (s.startsWith("xstabl"))
      {
        hitChunk("StabL", shot);
      }
      if (s.startsWith("xstabr"))
      {
        hitChunk("StabR", shot);
      }
    }
    else if (s.startsWith("xvator"))
    {
      if ((s.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1))
      {
        hitChunk("VatorL", shot);
      }
      if ((s.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
      {
        hitChunk("VatorR", shot);
      }
    }
    else if (s.startsWith("xwing"))
    {
      if ((s.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
      {
        setControlDamage(shot, 0);
        hitChunk("WingLIn", shot);
      }
      if ((s.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
      {
        setControlDamage(shot, 0);
        hitChunk("WingRIn", shot);
      }
      if ((s.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
      {
        setControlDamage(shot, 0);
        hitChunk("WingLMid", shot);
      }
      if ((s.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
      {
        setControlDamage(shot, 0);
        hitChunk("WingRMid", shot);
      }
      if ((s.startsWith("xwinglout1")) && (chunkDamageVisible("WingLOut") < 3))
      {
        hitChunk("WingLOut", shot);
      }
      if ((s.startsWith("xwingrout1")) && (chunkDamageVisible("WingROut") < 3))
      {
        hitChunk("WingROut", shot);
      }
    }
    else if (s.startsWith("xarone"))
    {
      if ((s.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
      {
        hitChunk("AroneL", shot);
      }
      if ((s.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
      {
        hitChunk("AroneR", shot);
      }
    }
    else if (s.startsWith("xgear"))
    {
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F))
      {
        mydebuggunnery("Undercarriage: Stuck..");
        this.FM.AS.setInternalDamage(shot.initiator, 3);
      }
    }
    else if (s.startsWith("xoil"))
    {
      if (World.Rnd().nextFloat() < 0.12F)
      {
        this.FM.AS.hitOil(shot.initiator, 0);
        mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
      }
    }
    else if ((!s.startsWith("xblister")) && ((s.startsWith("xpilot")) || (s.startsWith("xhead"))))
    {
      byte byte0 = 0;
      int l;
      int l;
      if (s.endsWith("a"))
      {
        byte0 = 1;
        l = s.charAt(6) - '1';
      }
      else
      {
        int l;
        if (s.endsWith("b"))
        {
          byte0 = 2;
          l = s.charAt(6) - '1';
        }
        else {
          l = s.charAt(5) - '1';
        }
      }
      hitFlesh(l, shot, byte0);
    }
  }

  protected void mydebuggunnery(String s)
  {
  }
}