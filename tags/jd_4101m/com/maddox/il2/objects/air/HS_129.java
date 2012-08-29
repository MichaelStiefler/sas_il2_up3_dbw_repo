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

public abstract class HS_129 extends Scheme2
  implements TypeStormovikArmored
{
  public float canopyF = 0.0F;
  private boolean fullCanopyOpened = false;
  private boolean sideWindowOpened = false;
  public boolean sideWindow = false;
  float suspR = 0.0F;
  float suspL = 0.0F;
  public boolean bChangedPit = true;
  CockpitHS_129 pit = null;
  private boolean slideRWindow = false;

  public void registerPit(CockpitHS_129 paramCockpitHS_129)
  {
    this.pit = paramCockpitHS_129;
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
      hierMesh().chunkVisible("pilotarm2_d0", false);
      hierMesh().chunkVisible("pilotarm1_d0", false);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("radiator1_d0", 0.0F, -63.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);

    hierMesh().chunkSetAngles("radiator2_d0", 0.0F, -63.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);

    super.update(paramFloat);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    Aircraft.xyz[0] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.xyz[1] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    if (paramFloat > this.canopyF)
    {
      if (((this.FM.Gears.onGround()) && (this.FM.getSpeed() < 5.0F)) || ((this.fullCanopyOpened) && ((this.FM.isPlayers()) || (isNetPlayer()))))
      {
        this.sideWindow = false;
        this.fullCanopyOpened = true;
        Aircraft.xyz[1] = (paramFloat * 1.0F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
      }
      else
      {
        this.sideWindow = true;
        if ((this.pit != null) && (this.canopyF == 0.0F))
          this.slideRWindow = this.pit.isViewRight();
        this.sideWindowOpened = true;
        Aircraft.xyz[1] = (paramFloat * 0.33F);
        if (this.slideRWindow)
          hierMesh().chunkSetLocate("Blister2R_D0", Aircraft.xyz, Aircraft.ypr);
        else {
          hierMesh().chunkSetLocate("Blister2_D0", Aircraft.xyz, Aircraft.ypr);
        }
      }

    }
    else if (((this.FM.Gears.onGround()) && (this.FM.getSpeed() < 5.0F) && (!this.sideWindowOpened)) || (this.fullCanopyOpened))
    {
      this.sideWindow = false;
      Aircraft.xyz[1] = (paramFloat * 1.0F);
      hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);

      if (paramFloat == 0.0F)
        this.fullCanopyOpened = false;
    }
    else
    {
      this.sideWindow = true;
      Aircraft.xyz[1] = (paramFloat * 0.33F);
      if (this.slideRWindow)
        hierMesh().chunkSetLocate("Blister2R_D0", Aircraft.xyz, Aircraft.ypr);
      else
        hierMesh().chunkSetLocate("Blister2_D0", Aircraft.xyz, Aircraft.ypr);
      if (paramFloat == 0.0F) {
        this.sideWindowOpened = false;
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

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      super.moveFan(paramFloat);
      float f1 = this.FM.CT.getAileron();
      float f2 = this.FM.CT.getElevator();

      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 25.0F * f1, f2 * 15.0F);
      hierMesh().chunkSetAngles("pilotarm2_d0", cvt(f1, -1.0F, 1.0F, 22.0F, -7.0F), 0.0F, cvt(f1, -1.0F, 1.0F, 7.0F, 2.0F) - cvt(f2, -1.0F, 1.0F, -18.0F, 18.0F));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, cvt(f1, -1.0F, 1.0F, 3.0F, 9.0F) + cvt(f2, -1.0F, 0.0F, -43.0F, -12.0F) + cvt(f2, 0.0F, 1.0F, -12.0F, 17.0F));
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 95.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 95.5F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -35.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -120.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -120.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -127.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -127.0F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    if (f > -3.0F) {
      f = 0.0F;
    }
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, -f, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    super.moveAileron(-paramFloat);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
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
    Aircraft.xyz[0] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.xyz[1] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    float f = 1150.0F;

    Aircraft.xyz[1] = (-this.suspL * 7.0F);
    hierMesh().chunkSetLocate("GearL2_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearL7_D0", 0.0F, -this.suspL * f, 0.0F);
    hierMesh().chunkSetAngles("GearL8_D0", 0.0F, this.suspL * f, 0.0F);

    Aircraft.xyz[1] = (-this.suspR * 7.0F);
    hierMesh().chunkSetLocate("GearR2_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearR7_D0", 0.0F, -this.suspR * f, 0.0F);
    hierMesh().chunkSetAngles("GearR8_D0", 0.0F, this.suspR * f, 0.0F);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", Aircraft.cvt(paramFloat, -65.0F, 65.0F, -65.0F, 65.0F), 0.0F, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int m;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        debuggunnery("Armor: Hit..");

        if (paramString.endsWith("2")) {
          getEnergyPastArmor(World.Rnd().nextFloat(1.96F, 3.4839F), paramShot);
        }
        else if (paramString.endsWith("3"))
        {
          if (paramPoint3d.z < 0.08D) {
            getEnergyPastArmor(8.585000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
          }
          else if (paramPoint3d.z < 0.09D)
            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
          else if ((paramPoint3d.y > 0.175D) && (paramPoint3d.y < 0.287D) && (paramPoint3d.z < 0.177D))
          {
            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
          } else if ((paramPoint3d.y > -0.334D) && (paramPoint3d.y < -0.177D) && (paramPoint3d.z < 0.204D))
          {
            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
          } else if ((paramPoint3d.z > 0.288D) && (Math.abs(paramPoint3d.y) < 0.077D)) {
            getEnergyPastArmor(World.Rnd().nextFloat(8.5F, 12.46F) / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
          }
          else
          {
            getEnergyPastArmor(10.510000228881836D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
          }

        }
        else if (paramString.endsWith("1"))
        {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 30.0F), paramShot);

          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);

          debuggunnery("Armor: Armor Glass: Hit..");
          if (paramShot.power <= 0.0F)
          {
            debuggunnery("Armor: Armor Glass: Bullet Stopped..");
            if (World.Rnd().nextFloat() < 0.96F)
              doRicochetBack(paramShot);
          }
        }
        else if (paramString.endsWith("4"))
        {
          getEnergyPastArmor(5.510000228881836D / (Math.abs(Aircraft.v1.z) + 9.999999747378752E-005D), paramShot);

          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
          }
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
          }
        }
        else if (paramString.endsWith("6"))
        {
          if (paramPoint3d.z > 0.448D)
          {
            if ((paramPoint3d.z > 0.609D) && (Math.abs(paramPoint3d.y) > 0.251D))
              debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
            else {
              getEnergyPastArmor(10.604999542236328D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
            }

          }
          else if (Math.abs(paramPoint3d.y) > 0.264D)
          {
            if (paramPoint3d.z > 0.021D) {
              getEnergyPastArmor(8.510000228881836D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
            }
            else
            {
              debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
            }
          } else if ((paramPoint3d.z < -0.352D) && (Math.abs(paramPoint3d.y) < 0.04D))
            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
          else {
            getEnergyPastArmor(8.060000419616699D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
          }

        }
        else if (paramString.endsWith("7")) {
          getEnergyPastArmor(6.059999942779541D / (Math.abs(Aircraft.v1.z) + 9.999999747378752E-005D), paramShot);
        }
        else if (paramString.endsWith("8"))
        {
          if (((paramPoint3d.y > 0.112D) && (paramPoint3d.z < -0.319D)) || ((paramPoint3d.y < -0.065D) && (paramPoint3d.z > 0.038D) && (paramPoint3d.z < 0.204D)))
          {
            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
          }
          else getEnergyPastArmor(8.060000419616699D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

        }
        else if (paramString.endsWith("9"))
        {
          if ((paramPoint3d.z > 0.611D) && (paramPoint3d.z < 0.674D) && (Math.abs(paramPoint3d.y) < 0.0415D))
          {
            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
          }
          else getEnergyPastArmor(8.060000419616699D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
        }
      }
      else
      {
        int i;
        if (paramString.startsWith("xxcontrol"))
        {
          debuggunnery("Controls: Hit..");
          if (World.Rnd().nextFloat() >= 0.99F)
          {
            i = new Integer(paramString.substring(9)).intValue();

            switch (i)
            {
            case 3:
            case 4:
              if (getEnergyPastArmor(3.5F, paramShot) <= 0.0F)
                break;
              this.FM.AS.setControlsDamage(paramShot.initiator, 2);
              debuggunnery("Controls: Rudder Controls: Fuselage Line Destroyed.."); break;
            case 1:
            case 2:
              if (getEnergyPastArmor(0.002F, paramShot) <= 0.0F)
                break;
              this.FM.AS.setControlsDamage(paramShot.initiator, 1);
              debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken.."); break;
            case 5:
            case 6:
            case 7:
            case 8:
              if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) {
                break;
              }
              this.FM.AS.setControlsDamage(paramShot.initiator, 0);
              debuggunnery("Controls: Aileron Controls: Disabled..");
            }

          }

        }
        else if (paramString.startsWith("xxspar"))
        {
          debuggunnery("Spar Construction: Hit..");
          if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
          {
            debuggunnery("Spar Construction: Tail1 Spars Broken in Half..");
            nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
          }
          if ((paramString.startsWith("xxsparli")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            debuggunnery("Spar Construction: WingLIn Spars Damaged..");
            nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
          }
          if ((paramString.startsWith("xxsparri")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            debuggunnery("Spar Construction: WingRIn Spars Damaged..");
            nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
          }
          if ((paramString.startsWith("xxsparlm")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(17.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            debuggunnery("Spar Construction: WingLMid Spars Damaged..");
            nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
          }
          if ((paramString.startsWith("xxsparrm")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(17.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            debuggunnery("Spar Construction: WingRMid Spars Damaged..");
            nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
          }
          if ((paramString.startsWith("xxsparlo")) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(13.2F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            debuggunnery("Spar Construction: WingLOut Spars Damaged..");
            nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
          }
          if ((paramString.startsWith("xxsparro")) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(13.2F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            debuggunnery("Spar Construction: WingROut Spars Damaged..");
            nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
          }
        }
        else if (paramString.startsWith("xxwj"))
        {
          if (getEnergyPastArmor(World.Rnd().nextFloat(12.5F, 55.959999F), paramShot) > 0.0F)
          {
            if (paramString.endsWith("l"))
            {
              debuggunnery("Spar Construction: WingL Console Lock Destroyed..");
              nextDMGLevels(4, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
            }
            else
            {
              debuggunnery("Spar Construction: WingR Console Lock Destroyed..");
              nextDMGLevels(4, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
            }

          }

        }
        else if (paramString.startsWith("xxlock"))
        {
          debuggunnery("Lock Construction: Hit..");
          if (paramString.startsWith("xxlockr"))
          {
            i = paramString.charAt(6) - '0';
            if (getEnergyPastArmor(6.56F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)
            {
              if (i < 3)
              {
                debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
              }
              else
              {
                debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
                nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), paramShot.initiator);
              }
            }
          }

          if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            debuggunnery("Lock Construction: VatorL Lock Shot Off..");
            nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            debuggunnery("Lock Construction: VatorR Lock Shot Off..");
            nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
          }

        }
        else if (paramString.startsWith("xxeng"))
        {
          i = paramString.charAt(5) - '1';
          debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Hit..");

          if (paramString.endsWith("prop"))
          {
            if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            {
              if (World.Rnd().nextFloat() < 0.5F)
              {
                this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
                debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Prop Governor Hit, Disabled..");
              }
              else
              {
                this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
                debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Prop Governor Hit, Damaged..");
              }

            }

          }
          else if (paramString.endsWith("gear"))
          {
            if (getEnergyPastArmor(4.6F, paramShot) > 0.0F)
            {
              if (World.Rnd().nextFloat() < 0.5F)
              {
                this.FM.EI.engines[i].setEngineStuck(paramShot.initiator);
                debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Reductor Gear..");
              }
              else
              {
                this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
                this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
                debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Reductor Gear Damaged, Prop Governor Failed..");
              }

            }

          }
          else if (paramString.endsWith("supc"))
          {
            if (getEnergyPastArmor(2.0F, paramShot) > 0.0F)
            {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Supercharger Disabled..");
            }

          }
          else if (paramString.endsWith("feed"))
          {
            if ((getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F) && (this.FM.EI.engines[i].getPowerOutput() > 0.7F))
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 100);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Pressurized Fuel Line Pierced, Fuel Flamed..");
            }

          }
          else if (paramString.endsWith("fuel"))
          {
            if (getEnergyPastArmor(1.1F, paramShot) > 0.0F)
            {
              this.FM.EI.engines[i].setEngineStops(paramShot.initiator);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Fuel Line Stalled, Engine Stalled..");
            }

          }
          else if (paramString.endsWith("case"))
          {
            if (getEnergyPastArmor(3.1F, paramShot) > 0.0F)
            {
              if (World.Rnd().nextFloat() < paramShot.power / 175000.0F)
              {
                this.FM.AS.setEngineStuck(paramShot.initiator, i);
                debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Crank Ball Bearing..");
              }

              if (World.Rnd().nextFloat() < paramShot.power / 50000.0F)
              {
                this.FM.AS.hitEngine(paramShot.initiator, i, 2);
                debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
              }

              this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));

              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
            }

            getEnergyPastArmor(World.Rnd().nextFloat(22.5F, 33.599998F), paramShot);
          }
          else if (paramString.endsWith("cyls"))
          {
            if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.0F))
            {
              this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");

              if (World.Rnd().nextFloat() < paramShot.power / 24000.0F)
              {
                this.FM.AS.hitEngine(paramShot.initiator, i, 3);
                debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Cylinders Hit, Engine Fires..");
              }

              if (World.Rnd().nextFloat() < 0.01F)
              {
                this.FM.AS.setEngineStuck(paramShot.initiator, i);
                debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Piston Head..");
              }

              getEnergyPastArmor(22.5F, paramShot);
            }
          }
          else if ((paramString.endsWith("mag1")) || (paramString.endsWith("mag2")))
          {
            if (World.Rnd().nextFloat() < 0.5F)
            {
              m = paramString.charAt(9) - '1';
              this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, m);

              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Magneto " + m + " Destroyed..");
            }

          }
          else if (paramString.endsWith("oil1"))
          {
            this.FM.AS.hitOil(paramShot.initiator, i);
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Oil Radiator Hit..");
          }

        }
        else if (paramString.startsWith("xxoil"))
        {
          if (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 2.345F), paramShot) > 0.0F)
          {
            i = paramString.charAt(5) - '1';
            this.FM.AS.hitOil(paramShot.initiator, i);
            getEnergyPastArmor(0.22F, paramShot);
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Oil Tank Pierced..");
          }

        }
        else if (paramString.startsWith("xxw"))
        {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 0.75F), paramShot) > 0.0F)
          {
            i = paramString.charAt(3) - '1';
            if (this.FM.AS.astateEngineStates[i] == 0)
            {
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Water Radiator Pierced..");

              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              this.FM.AS.doSetEngineState(paramShot.initiator, i, 2);
            }
            getEnergyPastArmor(2.22F, paramShot);
          }
        }
        else if (paramString.startsWith("xxtank"))
        {
          i = paramString.charAt(6) - '1';
          if ((getEnergyPastArmor(World.Rnd().nextFloat(1.0F, 2.23F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.1F))
          {
            if (this.FM.AS.astateTankStates[i] == 0)
            {
              debuggunnery("Fuel Tank " + (i + 1) + ": Pierced..");
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
              this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
            }
            else if (this.FM.AS.astateTankStates[i] == 1)
            {
              debuggunnery("Fuel Tank " + (i + 1) + ": Pierced..");
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
              this.FM.AS.doSetTankState(paramShot.initiator, i, 2);
            }
            if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.2F))
            {
              this.FM.AS.hitTank(paramShot.initiator, 2, 2);
              debuggunnery("Fuel Tank " + (i + 1) + ": Hit..");
            }
          }
        }
        else if (paramString.startsWith("xxhyd"))
        {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot) > 0.0F)
          {
            debuggunnery("Hydro System: Disabled..");
            this.FM.AS.setInternalDamage(paramShot.initiator, 0);
          }
        }
        else if (paramString.startsWith("xxrevi"))
        {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        }
        else
        {
          if (paramString.startsWith("xxmgun"))
          {
            if (paramString.endsWith("01"))
            {
              debuggunnery("left side MG17: Disabled..");
              this.FM.AS.setJamBullets(0, 0);
            }
            if (paramString.endsWith("02"))
            {
              debuggunnery("right side MG17: Disabled..");
              this.FM.AS.setJamBullets(0, 1);
            }
            getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 12.96F), paramShot);
          }
          if (paramString.startsWith("xxcannon"))
          {
            if (paramString.endsWith("01"))
            {
              debuggunnery("left side cannon: Disabled..");
              this.FM.AS.setJamBullets(0, 2);
            }
            if (paramString.endsWith("02"))
            {
              debuggunnery("right side cannon: Disabled..");
              this.FM.AS.setJamBullets(0, 3);
            }
            getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
          }
          if (paramString.startsWith("xmk101"))
          {
            debuggunnery("MK101: Disabled..");
            this.FM.AS.setJamBullets(1, 0);
            getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
          }
          if (paramString.startsWith("xmk103"))
          {
            debuggunnery("MK103: Disabled..");
            this.FM.AS.setJamBullets(1, 0);
            getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
          }
          if (paramString.startsWith("xbk75gun"))
          {
            debuggunnery("BK75: Disabled..");
            this.FM.AS.setJamBullets(1, 0);
            getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
          }
          if (paramString.startsWith("xbk37gun"))
          {
            debuggunnery("BK37: Disabled..");
            this.FM.AS.setJamBullets(1, 0);
            getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
          }
          if (paramString.startsWith("xmg17"))
          {
            float f = World.Rnd().nextFloat(0.0F, 4.0F);
            if (f < 1.5F)
            {
              this.FM.AS.setJamBullets(1, 0);
            }
            else if (f < 2.0F)
            {
              this.FM.AS.setJamBullets(1, 1);
            }
            else if (f < 2.5F)
            {
              this.FM.AS.setJamBullets(1, 2);
            }
            else
            {
              this.FM.AS.setJamBullets(1, 3);
            }
            debuggunnery("one of 4xMG17: Disabled..");
            getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
          }
        }
      }

    }
    else if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit")))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xblister"))
    {
      if (World.Rnd().nextFloat() < 0.12F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
      if (World.Rnd().nextFloat() < 0.12F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      }
      if (World.Rnd().nextFloat() < 0.12F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
    }
    else
    {
      int j;
      if (paramString.startsWith("xengine"))
      {
        j = paramString.charAt(7) - '0';
        if (chunkDamageVisible("Engine" + j) < 2)
          hitChunk("Engine" + j, paramShot);
        this.bChangedPit = true;
      }
      else if (paramString.startsWith("xtail"))
      {
        if (chunkDamageVisible("Tail1") < 3)
          hitChunk("Tail1", paramShot);
      }
      else if (paramString.startsWith("xkeel"))
      {
        j = paramString.charAt(5) - '0';
        if (chunkDamageVisible("Keel" + j) < 3)
          hitChunk("Keel" + j, paramShot);
        if (hierMesh().isChunkVisible("keel1_d2"))
          hierMesh().chunkVisible("Wire_D0", false);
      }
      else if (paramString.startsWith("xrudder"))
      {
        j = paramString.charAt(7) - '0';
        if (chunkDamageVisible("Rudder" + j) < 1)
          hitChunk("Rudder" + j, paramShot);
      }
      else if (paramString.startsWith("xstab"))
      {
        if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 3))
          hitChunk("StabL", paramShot);
        if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 3))
          hitChunk("StabR", paramShot);
      }
      else if (paramString.startsWith("xvator"))
      {
        if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1))
        {
          hitChunk("VatorL", paramShot);
        }if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
        {
          hitChunk("VatorR", paramShot);
        }
      } else if (paramString.startsWith("xwing"))
      {
        if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
        {
          hitChunk("WingLIn", paramShot);
          this.bChangedPit = true;
        }
        if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
        {
          hitChunk("WingRIn", paramShot);
          this.bChangedPit = true;
        }
        if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
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
      else if (paramString.startsWith("xgear"))
      {
        if ((paramString.endsWith("1")) && (World.Rnd().nextFloat() < 0.05F))
        {
          debuggunnery("Hydro System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 0);
        }
        if (paramString.endsWith("2"))
        {
          if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(6.8F, 29.35F), paramShot) > 0.0F))
          {
            debuggunnery("Undercarriage: Stuck..");
            this.FM.AS.setInternalDamage(paramShot.initiator, 3);
          }
          String str = "" + paramString.charAt(5);
          hitChunk("Gear" + str.toUpperCase() + "2", paramShot);
        }
      }
      else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
      {
        int k = 0;

        if (paramString.endsWith("a"))
        {
          k = 1;
          m = paramString.charAt(6) - '1';
        }
        else if (paramString.endsWith("b"))
        {
          k = 2;
          m = paramString.charAt(6) - '1';
        }
        else {
          m = paramString.charAt(5) - '1';
        }hitFlesh(m, paramShot, k);
      }
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1)
    {
    case 36:
      this.bChangedPit = true;
      break;
    case 33:
      this.bChangedPit = true;
      break;
    case 3:
      this.bChangedPit = true;
      break;
    case 4:
      this.bChangedPit = true;
      break;
    case 19:
      this.FM.Gears.hitCentreGear();
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 11:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 10:
      doWreck("GearR3_D0");
      this.FM.Gears.hitRightGear();
      break;
    case 9:
      doWreck("GearL3_D0");
      this.FM.Gears.hitLeftGear();
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  private void doWreck(String paramString)
  {
    if (hierMesh().chunkFindCheck(paramString) != -1)
    {
      hierMesh().hideSubTrees(paramString);
      Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind(paramString));
      localWreckage.collide(true);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.FM.Vwld);
      localWreckage.setSpeed(localVector3d);
    }
  }

  static
  {
    Class localClass = HS_129.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}