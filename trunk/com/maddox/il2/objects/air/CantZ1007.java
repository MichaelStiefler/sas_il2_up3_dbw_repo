package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import java.io.PrintStream;

public abstract class CantZ1007 extends Scheme6
{
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightSetForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;

  public boolean bPitUnfocused = true;

  public boolean bPilot1Killed = false;
  public boolean bPilot2Killed = false;
  public boolean bPilot3Killed = false;
  public boolean bPilot4Killed = false;
  public boolean bPilot5Killed = false;
  public boolean bPilot5KilledInBombPos = false;

  private float wheel1 = 0.0F;
  private float wheel2 = 0.0F;
  public float bayDoorAngle = 0.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    this.FM.Gears.computePlaneLandPose(this.FM);

    if (this.thisWeaponsName.endsWith("wing"))
    {
      hierMesh().chunkVisible("250kgWingRackL_D0", true);
      hierMesh().chunkVisible("250kgWingRackR_D0", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    if ((hierMesh().isChunkVisible("Prop2_D1")) && (hierMesh().isChunkVisible("Prop3_D1")) && ((hierMesh().isChunkVisible("Prop1_D0")) || (hierMesh().isChunkVisible("PropRot1_D0"))))
    {
      hitProp(0, 0, Engine.actorLand());
    }

    if ((hierMesh().isChunkVisible("Prop2_D1")) && (hierMesh().isChunkVisible("Prop1_D1")) && ((hierMesh().isChunkVisible("Prop3_D0")) || (hierMesh().isChunkVisible("PropRot3_D0"))))
    {
      hitProp(2, 0, Engine.actorLand());
    }

    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean)
    {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.39F))
        this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateEngineStates[1] > 3) && (World.Rnd().nextFloat() < 0.39F))
        this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateEngineStates[2] > 3) && (World.Rnd().nextFloat() < 0.39F)) {
        this.FM.AS.hitTank(this, 2, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.1F)) {
        nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
      }
    }

    War localWar = War.cur();

    Actor localActor = War.GetNearestEnemy(this, 16, 6000.0F);
    Aircraft localAircraft = War.getNearestEnemy(this, 5000.0F);

    if (((localActor != null) && (!(localActor instanceof BridgeSegment))) || (localAircraft != null))
    {
      if (this.FM.CT.getCockpitDoor() < 0.01F)
      {
        this.FM.AS.setCockpitDoor(this, 1);
      }

    }

    for (int i = 1; i <= 5; i++)
    {
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("hmask" + i + "_d0", false);
      else {
        hierMesh().chunkVisible("hmask" + i + "_d0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
      }

    }

    hierMesh().chunkVisible("Interior_D0", hierMesh().isChunkVisible("CF_D0"));
    hierMesh().chunkVisible("Interior_D1", hierMesh().isChunkVisible("CF_D1"));
    hierMesh().chunkVisible("Interior_D2", hierMesh().isChunkVisible("CF_D2"));
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[1].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[2].setHealth(paramFloat);
      this.FM.turret[3].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("hmask1_d0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      this.bPilot1Killed = true;
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("hmask2_d0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      this.bPilot2Killed = true;
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("hmask3_d0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      this.bPilot3Killed = true;
      break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("hmask4_d0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
      this.bPilot4Killed = true;
      break;
    case 4:
      if (hierMesh().isChunkVisible("Pilot5_D0"))
      {
        this.bPilot5KilledInBombPos = false;
        hierMesh().chunkVisible("hmask5_d0", false);
        hierMesh().chunkVisible("Pilot5_D0", false);
        hierMesh().chunkVisible("Pilot5_D1", true);
      }
      if (hierMesh().isChunkVisible("Pilot5bomb_D0"))
      {
        this.bPilot5KilledInBombPos = true;
        hierMesh().chunkVisible("Pilot5bomb_D0", false);
        hierMesh().chunkVisible("Pilot5bomb_D1", true);
      }
      this.bPilot5Killed = true;
    }
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i <= 9; i++)
    {
      hierMesh().chunkSetAngles("Radiator1_0" + i + "_D0", 0.0F, 30.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);

      hierMesh().chunkSetAngles("Radiator2_0" + i + "_D0", 0.0F, 30.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);

      hierMesh().chunkSetAngles("Radiator3_0" + i + "_D0", 0.0F, 30.0F * this.FM.EI.engines[2].getControlRadiator(), 0.0F);
    }

    for (i = 0; i <= 4; i++)
    {
      hierMesh().chunkSetAngles("Radiator1_1" + i + "_D0", 0.0F, 30.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);

      hierMesh().chunkSetAngles("Radiator2_1" + i + "_D0", 0.0F, 30.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);

      hierMesh().chunkSetAngles("Radiator3_1" + i + "_D0", 0.0F, 30.0F * this.FM.EI.engines[2].getControlRadiator(), 0.0F);
    }

    hierMesh().chunkSetAngles("Turret1C_D0", 0.0F, this.FM.turret[0].tu[1], 0.0F);

    if (this.bayDoorAngle > 0.5D)
    {
      if ((!this.bPilot5Killed) && (!this.FM.AS.isPilotParatrooper(4)))
      {
        hierMesh().chunkVisible("Pilot5_D0", false);
        hierMesh().chunkVisible("Pilot5bomb_D0", true);
        this.FM.turret[2].bIsOperable = false;
        this.FM.turret[3].bIsOperable = false;
      }

    }
    else if ((!this.bPilot5Killed) && (!this.FM.AS.isPilotParatrooper(4)))
    {
      hierMesh().chunkVisible("Pilot5_D0", true);
      hierMesh().chunkVisible("Pilot5bomb_D0", false);
      this.FM.turret[2].bIsOperable = true;
      this.FM.turret[3].bIsOperable = true;
    }

    super.update(paramFloat);
  }

  protected void moveElevator(float paramFloat)
  {
    if (paramFloat < 0.0F)
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 23.5F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 23.5F * paramFloat, 0.0F);
    }
    else
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 15.5F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 15.5F * paramFloat, 0.0F);
    }
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -23.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -23.0F * paramFloat, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -20.0F * paramFloat, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -55.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("TurDoorL_D0", 0.0F, 70.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("TurDoorR_D0", 0.0F, 70.0F * paramFloat, 0.0F);

    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  public void hitDaSilk()
  {
    if (this.FM.CT.getCockpitDoor() < 0.01F)
      this.FM.AS.setCockpitDoor(this, 1);
    super.hitDaSilk();
  }

  public void moveWheelSink()
  {
    this.wheel1 = (0.75F * this.wheel1 + 0.25F * this.FM.Gears.gWheelSinking[0]);
    this.wheel2 = (0.75F * this.wheel2 + 0.25F * this.FM.Gears.gWheelSinking[1]);
    resetYPRmodifier();

    Aircraft.xyz[1] = Aircraft.cvt(this.wheel1, 0.0F, 0.3F, 0.0F, 0.3F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);

    Aircraft.xyz[1] = Aircraft.cvt(this.wheel2, 0.0F, 0.3F, 0.0F, 0.3F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void mydebuggunnery(String paramString)
  {
    System.out.println(paramString);
  }

  protected void setControlDamage(Shot paramShot, int paramInt)
  {
    if (World.Rnd().nextFloat() < 0.002F)
    {
      if (getEnergyPastArmor(4.2F, paramShot) > 0.0F)
      {
        this.FM.AS.setControlsDamage(paramShot.initiator, paramInt);
      }
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
        {
          if (Aircraft.v1.z > 0.5D)
            getEnergyPastArmor(4.0D / Aircraft.v1.z, paramShot);
          else {
            getEnergyPastArmor(8.0D / Aircraft.v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          }
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(4.0D / Math.abs(Aircraft.v1.z), paramShot);
        }
        else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(7.0D / Math.abs(Aircraft.v1.x) * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        else if (paramString.endsWith("p4"))
        {
          if (Aircraft.v1.x > 0.7071067690849304D)
            getEnergyPastArmor(7.0D / Aircraft.v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          else
            getEnergyPastArmor(5.0F, paramShot);
        }
        else if ((paramString.endsWith("a1")) || (paramString.endsWith("a3")) || (paramString.endsWith("a4")))
          getEnergyPastArmor(0.6F, paramShot);
      }
      if (paramString.startsWith("xxspar"))
      {
        getEnergyPastArmor(4.0F, paramShot);
        if (((paramString.endsWith("cf1")) || (paramString.endsWith("cf2"))) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("CF") > 1) && (getEnergyPastArmor(15.9F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          msgCollision(this, "Tail1_D0", "Tail1_D0");
          msgCollision(this, "WingLIn_D0", "WingLIn_D0");
          msgCollision(this, "WingRIn_D0", "WingRIn_D0");
        }
        if (((paramString.endsWith("t1")) || (paramString.endsWith("t2"))) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 1) && (getEnergyPastArmor(15.9F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLIn") > 1) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingLIn_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingRIn") > 1) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingRIn_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLMid") > 1) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingLMid_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingRMid") > 1) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingRMid_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLOut") > 1) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingLOut_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingROut") > 1) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "WingROut_D2", paramShot.initiator);
        }
        if ((paramString.endsWith("e1")) && ((paramPoint3d.y > 2.79D) || (paramPoint3d.y < 2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }
        if ((paramString.endsWith("e2")) && ((paramPoint3d.y < -2.79D) || (paramPoint3d.y > -2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }
        if ((paramString.endsWith("e3")) && ((paramPoint3d.y < -2.79D) || (paramPoint3d.y > -2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "Engine3_D0", paramShot.initiator);
        }

        if (((paramString.endsWith("k1")) || (paramString.endsWith("k2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "Keel1_D0", paramShot.initiator);
        }
        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "StabR_D0", paramShot.initiator);
        }
        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingROut") > 1) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          nextDMGLevels(1, 2, "StabL_D0", paramShot.initiator);
        }
      }
      if ((paramString.startsWith("xxbomb")) && (World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets()))
      {
        this.FM.AS.hitTank(paramShot.initiator, 0, 100);
        this.FM.AS.hitTank(paramShot.initiator, 1, 100);
        this.FM.AS.hitTank(paramShot.initiator, 2, 100);
        this.FM.AS.hitTank(paramShot.initiator, 3, 100);
        msgCollision(this, "CF_D0", "CF_D0");
      }
      if (paramString.startsWith("xxprop"))
      {
        i = 0;
        if (paramString.endsWith("2"))
          i = 1;
        if (paramString.endsWith("3"))
          i = 2;
        if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.35F))
        {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
        }
      }

      if (paramString.startsWith("xxeng"))
      {
        i = 0;
        if (paramString.startsWith("xxeng2"))
          i = 1;
        if (paramString.startsWith("xxeng3"))
          i = 2;
        if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 220000.0F)
            {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
            }

            if (World.Rnd().nextFloat() < paramShot.power / 54000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
            }
          }

        }
        else if ((paramString.endsWith("cyls1")) || (paramString.endsWith("cyls2")))
        {
          if ((getEnergyPastArmor(1.5F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 0.6F))
          {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

            if (this.FM.AS.astateEngineStates[i] < 1)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 1);
              this.FM.AS.doSetEngineState(paramShot.initiator, i, 1);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 990000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 3);
            }

            getEnergyPastArmor(25.0F, paramShot);
          }
        }
        else if ((paramString.endsWith("supc")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
        {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
        }

        if ((paramString.endsWith("oil1")) || (paramString.endsWith("oil2")) || (paramString.endsWith("oil3")))
        {
          if (getEnergyPastArmor(0.63F, paramShot) > 0.0F)
            this.FM.AS.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.45F, paramShot);
        }

      }

      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(1.1F, paramShot) > 0.0F) {
          if (paramShot.power < 14100.0F)
          {
            if (this.FM.AS.astateTankStates[i] < 1)
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            if ((this.FM.AS.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.1F))
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[i] > 0) && (World.Rnd().nextFloat() < 0.05F))
              this.FM.AS.hitTank(paramShot.initiator, i, 10);
          }
          else {
            this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 41000.0F)));
          }
        }
      }
      if (paramString.startsWith("xxlock"))
      {
        if (paramString.startsWith("xxlockr"))
        {
          if (((paramString.startsWith("xxlockr1")) || (paramString.startsWith("xxlockr2"))) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }
        }
        if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockar")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

      }

    }

    if (paramString.startsWith("xmgun"))
    {
      if (paramString.endsWith("01"))
      {
        if ((getEnergyPastArmor(5.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(0, 0);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.endsWith("02"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(0, 1);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.endsWith("03"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(0, 2);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.endsWith("04"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(0, 3);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
    }

    if (paramString.startsWith("xcf"))
    {
      setControlDamage(paramShot, 0);
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("CF") < 2) {
        hitChunk("CF", paramShot);
      }
      if (World.Rnd().nextFloat() < 0.1F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      if (World.Rnd().nextFloat() < 0.1F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
      if (World.Rnd().nextFloat() < 0.1F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      if (World.Rnd().nextFloat() < 0.1F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      if (World.Rnd().nextFloat() < 0.1F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
      }
    }
    else if (paramString.startsWith("xtail"))
    {
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Tail1") < 2) {
        hitChunk("Tail1", paramShot);
      }
      if (World.Rnd().nextFloat() < 0.1F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      if (World.Rnd().nextFloat() < 0.1F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
      }
    }
    else if (paramString.startsWith("xkeel1"))
    {
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xkeel2"))
    {
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Keel2") < 2)
        hitChunk("Keel2", paramShot);
    }
    else if (paramString.startsWith("xrudder1"))
    {
      setControlDamage(paramShot, 2);
      hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xrudder2"))
    {
      setControlDamage(paramShot, 2);
      hitChunk("Rudder2", paramShot);
    }
    else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    }
    else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    }
    else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xwinglin"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingLIn") < 2) {
        hitChunk("WingLIn", paramShot);
      }
    }
    else if (paramString.startsWith("xwingrin"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingRIn") < 2) {
        hitChunk("WingRIn", paramShot);
      }
    }
    else if (paramString.startsWith("xwinglmid"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingLMid") < 2)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingRMid") < 2)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout"))
    {
      if (chunkDamageVisible("WingLOut") < 2)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout"))
    {
      if (chunkDamageVisible("WingROut") < 2)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel")) {
      hitChunk("AroneL", paramShot);
    }
    else if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xengine1"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2"))
    {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if (paramString.startsWith("xengine3"))
    {
      if (chunkDamageVisible("Engine3") < 2)
        hitChunk("Engine3", paramShot);
    }
    else if (paramString.startsWith("xgear"))
    {
      if (World.Rnd().nextFloat() < 0.1F)
      {
        this.FM.Gears.setHydroOperable(false);
      }
    }
    else if (paramString.startsWith("xturret"))
    {
      if (paramString.startsWith("xturret1"))
        this.FM.AS.setJamBullets(10, 0);
      if (paramString.startsWith("xturret2"))
        this.FM.AS.setJamBullets(11, 0);
      if (paramString.startsWith("xturret3"))
        this.FM.AS.setJamBullets(12, 0);
      if (paramString.startsWith("xturret4"))
        this.FM.AS.setJamBullets(13, 0);
      if (paramString.startsWith("xturret5"))
        this.FM.AS.setJamBullets(14, 0);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
    {
      i = 0;
      int j;
      if ((paramString.endsWith("a")) || (paramString.endsWith("abomb")))
      {
        i = 1;
        j = paramString.charAt(6) - '1';
      }
      else if ((paramString.endsWith("b")) || (paramString.endsWith("bbomb")))
      {
        i = 2;
        j = paramString.charAt(6) - '1';
      }
      else {
        j = paramString.charAt(5) - '1';
      }

      hitFlesh(j, paramShot, i);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    if (paramInt1 == 19)
      this.FM.Gears.hitCentreGear();
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 1.0F, 0.0F, 100.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 1.0F, 0.0F, 100.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 1.0F, 0.0F, -135.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 1.0F, 0.0F, -135.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.2F, 0.0F, -65.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.2F, 0.0F, 65.5F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.2F, 0.0F, -65.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.2F, 0.0F, -65.5F), 0.0F);

    paramHierMesh.chunkSetAngles("LightL_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.12F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("LightR_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.12F, 0.0F, -90.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Door_L_D0", 0.0F, 70.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Door_R_D0", 0.0F, 70.0F * paramFloat, 0.0F);

    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -0.26F);

    hierMesh().chunkSetLocate("LegFairing_D0", Aircraft.xyz, Aircraft.ypr);
    this.bayDoorAngle = paramFloat;
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryItaly);
  }
}