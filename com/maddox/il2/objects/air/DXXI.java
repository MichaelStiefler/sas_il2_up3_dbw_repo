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
import com.maddox.rts.Property;

public abstract class DXXI extends Scheme1
  implements TypeFighter
{
  public float canopyF = 0.0F;
  public boolean tiltCanopyOpened = false;
  private boolean slideCanopyOpened = false;
  public boolean blisterRemoved = false;
  private float suspension = 0.0F;

  public float canopyMaxAngle = 0.8F;
  public boolean hasSelfSealingTank = false;
  public boolean hasSkis = false;
  public boolean bChangedPit = true;

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      this.bChangedPit = true;
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("t1"))
        {
          getEnergyPastArmor(World.Rnd().nextFloat(21.0F, 42.0F) / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

          doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("t3")) {
          getEnergyPastArmor(8.9F, paramShot);
        } else if (paramString.endsWith("t4")) {
          getEnergyPastArmor(8.9F, paramShot);
        }
      } else if (paramString.startsWith("xxpanel"))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
      }
      else if (paramString.startsWith("xxrevi"))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
      }
      else if (paramString.startsWith("xxfrontglass"))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
      }
      else if (paramString.startsWith("xxrightglass"))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
      }
      else if (paramString.startsWith("xxleftglass"))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
      }
      else if (paramString.startsWith("xxcontrol"))
      {
        if (((paramString.endsWith("s1")) || (paramString.endsWith("7"))) && (getEnergyPastArmor(4.8F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Elevator Cables: Hit, Controls Destroyed..");
        }

        if ((paramString.endsWith("0")) && (getEnergyPastArmor(3.2F, paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Rudder Cabels: Hit, Controls Destroyed..");

          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
        }
        if ((paramString.endsWith("s4")) && (getEnergyPastArmor(0.2F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        }

        if (((paramString.endsWith("3")) || (paramString.endsWith("6"))) && (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.35F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Aileron Controls Out..");
        }
      }
      else if (paramString.startsWith("xxeng1"))
      {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 0.55F), paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 280000.0F)
            {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");

              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 100000.0F)
            {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");

              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 24.0F), paramShot);
        }

        if (paramString.endsWith("cyls"))
        {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 0.66F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 32200.0F)));

            debuggunnery("Engine Module: Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");

            if (World.Rnd().nextFloat() < paramShot.power / 1000000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
              debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
            }
          }

          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("mag1"))
        {
          debuggunnery("Engine Module: Magneto 1 Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);
        }
        if (paramString.endsWith("mag2"))
        {
          debuggunnery("Engine Module: Magneto 2 Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);
        }
        if (paramString.endsWith("oil"))
        {
          if ((World.Rnd().nextFloat() < 0.6F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
          {
            debuggunnery("Engine Module: Oil Radiator Hit..");
          }this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        }
        if (paramString.endsWith("supc"))
        {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 0);
            Aircraft.debugprintln(this, "*** Engine Module: Supercharger Disabled..");
          }

          getEnergyPastArmor(0.5F, paramShot);
        }
        if (paramString.endsWith("eqpt"))
        {
          if ((getEnergyPastArmor(0.2721F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
          {
            if (World.Rnd().nextFloat() < 0.2F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
              Aircraft.debugprintln(this, "*** Engine Module: Throttle Controls Cut..");
            }

            if (World.Rnd().nextFloat() < 0.2F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 7);
              Aircraft.debugprintln(this, "*** Engine Module: Mix Controls Cut..");
            }
          }

        }

      }
      else if (paramString.startsWith("xxlock"))
      {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

      }
      else if (paramString.startsWith("xxammo00"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          if (World.Rnd().nextFloat() < 0.5F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
          else
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
        }
      }
      else if (paramString.startsWith("xxmgun01"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.startsWith("xxmgun02")) {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.startsWith("xxmgun03")) {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 2);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.startsWith("xxmgun04")) {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 3);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.startsWith("xxoil1"))
      {
        if ((getEnergyPastArmor(2.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(6.75F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
      }
      else if (paramString.startsWith("xxspar"))
      {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.700001F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparri")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.700001F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparlm")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");

          nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparrm")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");

          nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparlo")) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");

          nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparro")) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");

          nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** Tail1 Spars Damaged..");
          nextDMGLevels(1, 2, "Tail1_D" + chunkDamageVisible("Tail1"), paramShot.initiator);
        }
      }
      else if (paramString.startsWith("xxtank1"))
      {
        i = 0;
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F))
        {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0)
          {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            if (this.hasSelfSealingTank)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            }
            else
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
            }
          }

          if ((World.Rnd().nextFloat() < 0.02F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.22F)))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
      }
    }
    else if (paramString.startsWith("xcf"))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (!paramString.startsWith("xblister"))
    {
      if (paramString.startsWith("xengine"))
      {
        if (chunkDamageVisible("Engine1") < 2)
          hitChunk("Engine1", paramShot);
      }
      else if (paramString.startsWith("xtail"))
      {
        if (chunkDamageVisible("Tail1") < 3)
          hitChunk("Tail1", paramShot);
      }
      else if (paramString.startsWith("xkeel")) {
        hitChunk("Keel1", paramShot);
      } else if (paramString.startsWith("xrudder"))
      {
        if (chunkDamageVisible("Rudder1") < 2)
          hitChunk("Rudder1", paramShot);
      }
      else if (paramString.startsWith("xstab"))
      {
        if (paramString.startsWith("xstabl"))
          hitChunk("StabL", paramShot);
        if (paramString.startsWith("xstabr"))
          hitChunk("StabR", paramShot);
      }
      else if (paramString.startsWith("xvator"))
      {
        if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 2))
        {
          hitChunk("VatorL", paramShot);
        }if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 2))
        {
          hitChunk("VatorR", paramShot);
        }
      } else if (paramString.startsWith("xwing"))
      {
        if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
        {
          hitChunk("WingLIn", paramShot);
        }if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
        {
          hitChunk("WingRIn", paramShot);
        }if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
        {
          hitChunk("WingLMid", paramShot);
        }if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
        {
          hitChunk("WingRMid", paramShot);
        }if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
        {
          hitChunk("WingLOut", paramShot);
        }if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
        {
          hitChunk("WingROut", paramShot);
        }
      } else if (paramString.startsWith("xarone"))
      {
        if (paramString.startsWith("xaronel"))
          hitChunk("AroneL", paramShot);
        if (paramString.startsWith("xaroner"))
          hitChunk("AroneR", paramShot);
      }
      else if (paramString.startsWith("xgearl"))
      {
        if (paramString.startsWith("xgearl2"))
          hitChunk("GearL22", paramShot);
      }
      else if (paramString.startsWith("xgearr"))
      {
        if (paramString.startsWith("xgearr2"))
          hitChunk("GearR22", paramShot);
      }
      else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
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
        }hitFlesh(j, paramShot, i);
      }
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
    }
    if ((!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (!isNetPlayer()))
    {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.2F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setExtinguisherFire();
    }
    if ((this.tiltCanopyOpened) && (!this.blisterRemoved) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 75.0F))
    {
      doRemoveBlister3();
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

  public void missionStarting()
  {
    super.missionStarting();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  public void prepareCamouflage()
  {
    super.prepareCamouflage();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  public void hitDaSilk()
  {
    super.hitDaSilk();
    if ((!this.blisterRemoved) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(0)))
    {
      doRemoveBlister2();
    }
  }

  private final void doRemoveBlister2()
  {
    this.blisterRemoved = true;
    if (hierMesh().chunkFindCheck("Blister2_D0") != -1)
    {
      hierMesh().hideSubTrees("Blister2_D0");
      Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister2_D0"));
      localWreckage.collide(true);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      localWreckage.setSpeed(localVector3d);
    }
  }

  private final void doRemoveBlister3()
  {
    this.blisterRemoved = true;
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.bHasCockpitDoorControl = false;
    this.bChangedPit = true;
    Wreckage localWreckage;
    Vector3d localVector3d;
    if (hierMesh().chunkFindCheck("Blister3_D0") != -1)
    {
      hierMesh().hideSubTrees("Blister3_D0");
      localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister3_D0"));
      localWreckage.collide(true);
      localVector3d = new Vector3d();
      localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      localWreckage.setSpeed(localVector3d);
    }
    if (hierMesh().chunkFindCheck("Blister2_D0") != -1)
    {
      hierMesh().hideSubTrees("Blister2_D0");
      localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister2_D0"));
      localWreckage.collide(true);
      localVector3d = new Vector3d();
      localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      localWreckage.setSpeed(localVector3d);
    }
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    if (paramFloat < 0.0F)
    {
      hierMesh().chunkSetAngles("RudderWireR_d0", -28.0F * paramFloat, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("RudderWireL_d0", -30.41F * paramFloat, 0.0F, 0.0F);
    }
    else
    {
      hierMesh().chunkSetAngles("RudderWireR_d0", -30.0F * paramFloat, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("RudderWireL_d0", -28.0F * paramFloat, 0.0F, 0.0F);
    }
  }

  protected void moveAileron(float paramFloat)
  {
    float f = -30.0F * paramFloat;
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, f, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = 50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void sfxFlaps(boolean paramBoolean)
  {
  }

  public void moveCockpitDoor(float paramFloat)
  {
    if (paramFloat > this.canopyF)
    {
      if (((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() < 5.0F)) || ((this.tiltCanopyOpened) && ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) || (isNetPlayer()))))
      {
        this.tiltCanopyOpened = true;
        hierMesh().chunkSetAngles("Blister2_D0", 0.0F, -paramFloat * 80.0F, 0.0F);
        hierMesh().chunkSetAngles("Blister3_D0", 0.0F, -paramFloat * 125.0F, 0.0F);
      }
      else
      {
        this.slideCanopyOpened = true;
        hierMesh().chunkSetAngles("Blister4L_D0", 0.0F, paramFloat * this.canopyMaxAngle, 0.0F);
      }

    }
    else if (((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() < 5.0F) && (!this.slideCanopyOpened)) || (this.tiltCanopyOpened))
    {
      hierMesh().chunkSetAngles("Blister2_D0", 0.0F, -paramFloat * 80.0F, 0.0F);
      hierMesh().chunkSetAngles("Blister3_D0", 0.0F, -paramFloat * 125.0F, 0.0F);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 50.0F) && (paramFloat < 0.6F) && (!this.blisterRemoved))
      {
        doRemoveBlister3();
      }
      if (paramFloat == 0.0F)
        this.tiltCanopyOpened = false;
    }
    else
    {
      hierMesh().chunkSetAngles("Blister4L_D0", 0.0F, paramFloat * this.canopyMaxAngle, 0.0F);
      if (paramFloat == 0.0F) {
        this.slideCanopyOpened = false;
      }
    }
    this.canopyF = paramFloat;
    if (this.canopyF < 0.01D)
    {
      this.canopyF = 0.0F;
    }

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
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround())
      this.suspension += 0.008F;
    else {
      this.suspension -= 0.008F;
    }
    if (this.suspension < 0.0F)
    {
      this.suspension = 0.0F;
      if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bTailwheelLocked = true;
      }
    }
    if (this.suspension > 0.1F) {
      this.suspension = 0.1F;
    }
    Aircraft.xyz[0] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    Aircraft.xyz[1] = (this.suspension / 10.0F);
    float f1 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 0.0F, 25.0F, 0.0F, 1.0F);

    float f2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] * f1 + this.suspension;

    Aircraft.xyz[2] = Aircraft.cvt(f2, 0.0F, 0.24F, 0.0F, 0.24F);
    hierMesh().chunkSetLocate("GearL2_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearL31_D0", f2 * 500.0F, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("GearL32_D0", -f2 * 500.0F, 0.0F, 0.0F);

    f2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] * f1 + this.suspension;
    Aircraft.xyz[2] = Aircraft.cvt(f2, 0.0F, 0.24F, 0.0F, 0.24F);
    hierMesh().chunkSetLocate("GearR2_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearR31_D0", f2 * 500.0F, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("GearR32_D0", -f2 * 500.0F, 0.0F, 0.0F);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 11:
      hierMesh().chunkVisible("WireL_D0", false);
      hierMesh().chunkVisible("WireR_D0", false);
      break;
    case 17:
      hierMesh().chunkVisible("WireL_D0", false);
      break;
    case 18:
      hierMesh().chunkVisible("WireL_D0", false);
      break;
    case 19:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.hitCentreGear();
      hierMesh().chunkVisible("Antenna_D0", false);
      break;
    case 3:
      if (World.Rnd().nextInt(0, 99) < 1)
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 4);
        hitProp(0, paramInt2, paramActor);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramActor);
        return cut("engine1");
      }

      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineDies(this, 0);
      return false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = DXXI.class;
    Property.set(localClass, "originCountry", PaintScheme.countryFinland);
  }
}