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
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public abstract class Avia_B5xx extends Scheme1
  implements TypeScout, TypeFighter, TypeTNBFighter, TypeStormovik
{
  protected float kangle;
  public boolean bChangedPit = true;
  private CockpitAVIA_B534 pit = null;
  float suspR = 0.0F;
  float suspL = 0.0F;

  protected void moveFlap(float paramFloat)
  {
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -20.0F * paramFloat, 0.0F);
  }

  public void registerPit(CockpitAVIA_B534 paramCockpitAVIA_B534)
  {
    this.pit = paramCockpitAVIA_B534;
  }

  public void missionStarting()
  {
    super.missionStarting();
    if ((this.FM.CT.Weapons[0] == null) && (this.pit != null))
      this.pit.hideAllBullets();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  public void prepareCamouflage() {
    super.prepareCamouflage();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
    if ((this.FM.CT.Weapons[0] == null) && (this.pit != null))
      this.pit.hideAllBullets();
  }

  public void moveCockpitDoor(float paramFloat)
  {
    Aircraft.xyz[0] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    Aircraft.xyz[1] = (paramFloat * 0.62F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);

    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    this.suspL = (0.9F * this.suspL + 0.1F * this.FM.Gears.gWheelSinking[0] * 23.0F);

    hierMesh().chunkSetAngles("GearL2_D0", 0.0F, this.suspL * 5.0F, 0.0F);
    hierMesh().chunkSetAngles("GearL3_D0", 0.0F, this.suspL * 3.67F, 0.0F);
    hierMesh().chunkSetAngles("GearL4_D0", -this.suspL * 1.3F, 0.0F, 0.0F);

    this.suspR = (0.9F * this.suspR + 0.1F * this.FM.Gears.gWheelSinking[1] * 23.0F);

    hierMesh().chunkSetAngles("GearR2_D0", 0.0F, -this.suspR * 5.0F, 0.0F);
    hierMesh().chunkSetAngles("GearR3_D0", 0.0F, -this.suspR * 3.67F, 0.0F);
    hierMesh().chunkSetAngles("GearR4_D0", this.suspR * 1.3F, 0.0F, 0.0F);
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
      if (hierMesh().chunkFindCheck("GearL3_D0") == -1)
        break;
      hierMesh().hideSubTrees("GearL3_D0");
      localWreckage = new Wreckage(this, hierMesh().chunkFind("GearL3_D0"));
      localWreckage.collide(true);

      this.FM.Gears.hitLeftGear();
      break;
    case 10:
      if (hierMesh().chunkFindCheck("GearR3_D0") == -1)
        break;
      hierMesh().hideSubTrees("GearR3_D0");
      localWreckage = new Wreckage(this, hierMesh().chunkFind("GearR3_D0"));
      localWreckage.collide(true);

      this.FM.Gears.hitRightGear();
      break;
    case 3:
      if (World.Rnd().nextInt(0, 99) < 1)
      {
        this.FM.AS.hitEngine(this, 0, 4);
        hitProp(0, paramInt2, paramActor);
        this.FM.EI.engines[0].setEngineStuck(paramActor);
        return cut("engine1");
      }

      this.FM.AS.setEngineDies(this, 0);
      return false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
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

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 20.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -20.0F * paramFloat, 0.0F);

    float f = this.FM.CT.getAileron();
    hierMesh().chunkSetAngles("pilotarm2_d0", cvt(f, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(f, -1.0F, 1.0F, 6.0F, -8.0F) - cvt(paramFloat, -1.0F, 1.0F, -37.0F, 35.0F));

    hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, cvt(f, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(paramFloat, -1.0F, 0.0F, -61.0F, 0.0F) + cvt(paramFloat, 0.0F, 1.0F, 0.0F, 43.0F));

    if (paramFloat < 0.0F) {
      paramFloat /= 2.0F;
    }
    hierMesh().chunkSetAngles("Stick_D0", 0.0F, 15.0F * f, cvt(paramFloat, -1.0F, 1.0F, -16.0F, 16.0F));
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -20.0F * paramFloat, 0.0F);

    float f = this.FM.CT.getElevator();
    hierMesh().chunkSetAngles("pilotarm2_d0", cvt(paramFloat, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(paramFloat, -1.0F, 1.0F, 6.0F, -8.0F) - cvt(f, -1.0F, 1.0F, -37.0F, 35.0F));

    hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, cvt(paramFloat, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(f, -1.0F, 0.0F, -61.0F, 0.0F) + cvt(f, 0.0F, 1.0F, 0.0F, 43.0F));

    if (f < 0.0F) {
      f /= 2.0F;
    }
    hierMesh().chunkSetAngles("Stick_D0", 0.0F, 15.0F * paramFloat, cvt(f, -1.0F, 1.0F, -16.0F, 20.0F));
  }

  public void update(float paramFloat)
  {
    float f = this.FM.EI.engines[0].getControlRadiator();
    f = (-48.0F * f - 42.0F) * f + 33.0F;
    this.kangle = (0.95F * this.kangle + 0.05F * f);

    hierMesh().chunkSetAngles("radiator1_D0", 0.0F, this.kangle, 0.0F);
    hierMesh().chunkSetAngles("radiator2_D0", 0.0F, this.kangle, 0.0F);
    hierMesh().chunkSetAngles("radiator3_D0", 0.0F, this.kangle, 0.0F);
    hierMesh().chunkSetAngles("radiator4_D0", 0.0F, this.kangle, 0.0F);
    hierMesh().chunkSetAngles("radiator5_D0", 0.0F, this.kangle, 0.0F);
    hierMesh().chunkSetAngles("radiator6_D0", 0.0F, this.kangle, 0.0F);

    super.update(paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean)
    {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.4F))
        this.FM.EI.engines[0].setExtinguisherFire();
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D1", true);
      hierMesh().chunkVisible("pilotarm2_d0", false);
      hierMesh().chunkVisible("pilotarm1_d0", false);
    }
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    hierMesh().chunkVisible("pilotarm2_d0", false);
    hierMesh().chunkVisible("pilotarm1_d0", false);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        if ((paramString.endsWith("p1")) || (paramString.endsWith("p3"))) {
          getEnergyPastArmor(9.96F / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), paramShot);
        }

        return;
      }

      if (paramString.startsWith("xxcontrols"))
      {
        if (paramString.endsWith("8")) {
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);

            Aircraft.debugprintln(this, "*** Rudder Controls Out.. (#8)");
          }
        }
        else if (paramString.endsWith("9"))
        {
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);

            Aircraft.debugprintln(this, "*** Evelator Controls Out.. (#9)");
          }

          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);

            Aircraft.debugprintln(this, "*** Arone Controls Out.. (#9)");
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

      if (paramString.startsWith("xxeng"))
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
        else if ((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2")))
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
        else if (paramString.endsWith("oil1"))
        {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
        }
        else if (paramString.endsWith("supc"))
        {
          Aircraft.debugprintln(this, "*** Engine Module: Supercharger Hit..");
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && 
            (World.Rnd().nextFloat() < 0.05F)) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);

            Aircraft.debugprintln(this, "*** Engine Module: Supercharger Disabled..");
          }
        }

      }
      else if (paramString.endsWith("gear"))
      {
        Aircraft.debugprintln(this, "*** Engine Module: Gear Hit..");
        if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && 
          (World.Rnd().nextFloat() < 0.05F))
        {
          this.FM.AS.setEngineStuck(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module: gear hit, engine stuck..");
        }

      }
      else if (paramString.startsWith("xxtank"))
      {
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.99F))
        {
          if (this.FM.AS.astateTankStates[0] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, 0, 2);
          }

          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.25F))
          {
            this.FM.AS.hitTank(paramShot.initiator, 0, 2);
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
      else if (paramString.startsWith("xxmgun"))
      {
        if (paramString.endsWith("01"))
        {
          Aircraft.debugprintln(this, "*** Fixed Gun #1: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02"))
        {
          Aircraft.debugprintln(this, "*** Fixed Gun #2: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        if (paramString.endsWith("03"))
        {
          Aircraft.debugprintln(this, "*** Fixed Gun #3: Disabled..");
          this.FM.AS.setJamBullets(1, 0);
          if (this.pit != null)
            this.pit.jamLeftGun();
        }
        if (paramString.endsWith("04"))
        {
          Aircraft.debugprintln(this, "*** Fixed Gun #4: Disabled..");
          this.FM.AS.setJamBullets(1, 1);
          if (this.pit != null)
            this.pit.jamRightGun();
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
        else if ((paramString.startsWith("xxsparri")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
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
      int i = 0;
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
      Aircraft.debugprintln(this, "*** hitFlesh..");
      hitFlesh(j, paramShot, i);
    }
    else if (paramString.startsWith("xcockpit"))
    {
      if (World.Rnd().nextFloat() < 0.2F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      else if (World.Rnd().nextFloat() < 0.4F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
      else if (World.Rnd().nextFloat() < 0.6F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      else if (World.Rnd().nextFloat() < 0.8F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      else
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
    }
    else if (paramString.startsWith("xcf"))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
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
    else if (paramString.startsWith("xwing"))
    {
      Aircraft.debugprintln(this, "*** xWing: " + paramString);

      if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3)) {
        hitChunk("WingLIn", paramShot);
      }
      if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3)) {
        hitChunk("WingRIn", paramShot);
      }
      if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3)) {
        hitChunk("WingLMid", paramShot);
      }
      if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3)) {
        hitChunk("WingRMid", paramShot);
      }
      if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3)) {
        hitChunk("WingLOut", paramShot);
      }
      if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xarone"))
    {
      if ((paramString.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
        hitChunk("AroneL", paramShot);
      if ((paramString.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
        hitChunk("AroneR", paramShot);
    }
  }

  static
  {
    Class localClass = Avia_B5xx.class;
    Property.set(localClass, "originCountry", PaintScheme.countrySlovakia);
  }
}