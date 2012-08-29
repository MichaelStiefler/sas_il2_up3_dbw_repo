package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public abstract class SEAGLADIATOR extends Scheme1
  implements TypeFighter, TypeTNBFighter
{
  public boolean bChangedPit = true;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      this.bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      this.bChangedPit = true;
  }

  public void moveCockpitDoor(float paramFloat) {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.6F);
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.0F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        Aircraft.debugprintln(this, "*** Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(8.100000381469727D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-006D), paramShot);
        }
      }
      else
      {
        if (paramString.startsWith("xxcontrols")) {
          i = paramString.charAt(10) - '0';
          switch (i) {
          case 1:
            if (getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 2.3F), paramShot) <= 0.0F)
            {
              break;
            }
            if (World.Rnd().nextFloat() < 0.25F) {
              this.FM.AS.setControlsDamage(paramShot.initiator, 2);
              Aircraft.debugprintln(this, "*** Rudder Controls: Disabled..");
            }

            if (World.Rnd().nextFloat() >= 0.25F) break;
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Elevator Controls: Disabled..");
          case 2:
          case 3:
            if (getEnergyPastArmor(1.5F, paramShot) > 0.0F) {
              this.FM.AS.setControlsDamage(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");
            }

          }

        }

        if (paramString.startsWith("xxspar")) {
          Aircraft.debugprintln(this, "*** Spar Construction: Hit..");

          if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(9.5F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");

            nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
          }
          if ((paramString.startsWith("xxsparli")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");

            nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparri")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");

            nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparlm")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");

            nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparrm")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");

            nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparlo")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");

            nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparro")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");

            nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxstabl")) && (getEnergyPastArmor(16.200001F, paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** StabL Spars Damaged..");

            nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxstabr")) && (getEnergyPastArmor(16.200001F, paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** StabR Spars Damaged..");

            nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), paramShot.initiator);
          }

        }

        if (paramString.startsWith("xxlock")) {
          Aircraft.debugprintln(this, "*** Lock Construction: Hit..");

          if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");

            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");

            nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");

            nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockal")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** AroneL Lock Shot Off..");

            nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockar")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** AroneR Lock Shot Off..");

            nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
          }

        }

        if (paramString.startsWith("xxeng")) {
          Aircraft.debugprintln(this, "*** Engine Module: Hit..");
          if (paramString.endsWith("prop")) {
            if ((getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
            {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);

              Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
            }

          }
          else if (paramString.endsWith("case")) {
            if (getEnergyPastArmor(2.1F, paramShot) > 0.0F) {
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
          } else if (paramString.startsWith("xxeng1cyls")) {
            if ((getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 4.4F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.12F))
            {
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

              Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");

              if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
              {
                this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
                Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
              }

              if (World.Rnd().nextFloat() < 0.005F) {
                this.FM.AS.setEngineStuck(paramShot.initiator, 0);
                Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
              }

              getEnergyPastArmor(22.5F, paramShot);
            }
          } else if (paramString.endsWith("eqpt")) {
            if ((getEnergyPastArmor(0.2721F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
            {
              if (World.Rnd().nextFloat() < 0.1F) {
                this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);

                Aircraft.debugprintln(this, "*** Engine Module: Magneto 0 Destroyed..");
              }

              if (World.Rnd().nextFloat() < 0.1F) {
                this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);

                Aircraft.debugprintln(this, "*** Engine Module: Magneto 1 Destroyed..");
              }

              if (World.Rnd().nextFloat() < 0.1F) {
                this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);

                Aircraft.debugprintln(this, "*** Engine Module: Prop Controls Cut..");
              }

              if (World.Rnd().nextFloat() < 0.1F) {
                this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);

                Aircraft.debugprintln(this, "*** Engine Module: Throttle Controls Cut..");
              }

              if (World.Rnd().nextFloat() < 0.1F) {
                this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7);

                Aircraft.debugprintln(this, "*** Engine Module: Mix Controls Cut..");
              }
            }

          }
          else if (paramString.endsWith("oil1")) {
            this.FM.AS.hitOil(paramShot.initiator, 0);
            Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
          }
        }

        if (paramString.startsWith("xxoil")) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          Aircraft.debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
        }

        if ((paramString.startsWith("xxtank1")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.99F))
        {
          if (this.FM.AS.astateTankStates[0] == 0) {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");

            this.FM.AS.hitTank(paramShot.initiator, 0, 1);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F))
          {
            this.FM.AS.hitTank(paramShot.initiator, 0, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
        }
        if (paramString.startsWith("xxmgun")) {
          if (paramString.endsWith("01")) {
            Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");

            this.FM.AS.setJamBullets(0, 0);
          }
          if (paramString.endsWith("02")) {
            Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");

            this.FM.AS.setJamBullets(0, 1);
          }
          if (paramString.endsWith("03")) {
            Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");

            this.FM.AS.setJamBullets(1, 0);
          }
          if (paramString.endsWith("04")) {
            Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");

            this.FM.AS.setJamBullets(1, 1);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
        }
      }
    }
    else if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    } else if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xbay")) {
      if (chunkDamageVisible("DinghyBay1") < 2)
        hitChunk("DinghyBay1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 2))
        hitChunk("StabL", paramShot);
      if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 1))
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
    } else if (paramString.startsWith("xarone")) {
      if ((paramString.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
      {
        hitChunk("AroneL", paramShot);
      }if ((paramString.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
      {
        hitChunk("AroneR", paramShot);
      }
    } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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
      }hitFlesh(j, paramShot, i);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
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

  public void msgExplosion(Explosion paramExplosion) {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && (paramExplosion.power > 0.0F) && (paramExplosion.chunkName.startsWith("Tail1")))
    {
      if (World.Rnd().nextFloat(0.0F, 0.038F) < paramExplosion.power)
        this.FM.AS.setControlsDamage(paramExplosion.initiator, 1);
      if (World.Rnd().nextFloat(0.0F, 0.042F) < paramExplosion.power)
        this.FM.AS.setControlsDamage(paramExplosion.initiator, 2);
    }
    super.msgExplosion(paramExplosion);
  }

  protected void moveAileron(float paramFloat) {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneLrod1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneLrod2_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneLn_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneRrod1_D0", 0.0F, 30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneRrod2_D0", 0.0F, 30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneRn_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1) {
    case 11:
      hierMesh().chunkVisible("PodkosLw_D0", false);
      hierMesh().chunkVisible("PodkosRw_D0", false);
      break;
    case 17:
      hierMesh().chunkVisible("PodkosLw_D0", false);
      break;
    case 18:
      hierMesh().chunkVisible("PodkosLw_D0", false);
      break;
    case 19:
      hierMesh().chunkVisible("WireC_D0", false);
    case 12:
    case 13:
    case 14:
    case 15:
    case 16: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = SEAGLADIATOR.class;

    Property.set(localClass, "originCountry", PaintScheme.countryBritain);
  }
}