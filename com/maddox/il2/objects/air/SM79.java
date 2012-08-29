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
import com.maddox.il2.engine.Mat;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class SM79 extends Scheme6
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
  public int numBombsOld;
  public float bayDoorAngle = 0.0F;

  boolean wasInTorpedoAttack = false;
  int numEvasive = 0;
  int timeEvasive = 0;
  int timeTorpedoDrop = 0;
  private boolean bDynamoOperational;
  private float dynamoOrient;
  private boolean bDynamoRotary;
  private int pk;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    hierMesh().chunkSetAngles("GearR7_D0", 0.0F, 1.0F, 0.0F);
    hierMesh().chunkSetAngles("GearL7_D0", 0.0F, 1.0F, 0.0F);
    hierMesh().chunkSetAngles("GearR6_D0", 0.0F, -1.0F, 0.0F);
    hierMesh().chunkSetAngles("GearL6_D0", 0.0F, -1.0F, 0.0F);
    int i;
    if ((this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12")) || (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6")))
    {
      for (i = 1; i <= 12; i++)
      {
        hierMesh().chunkVisible("BombRack" + i + "_D0", true);
        this.numBombsOld = 12;
        if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6"))
          this.numBombsOld = 6;
      }
    }
    if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("5"))
    {
      for (i = 1; i <= 5; i++)
      {
        hierMesh().chunkVisible("BombRack250_" + i + "_D0", true);
        this.numBombsOld = 5;
      }
    }
    if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("2"))
    {
      hierMesh().chunkVisible("BombRack500_1_D0", true);
      hierMesh().chunkVisible("BombRack500_2_D0", true);
      this.numBombsOld = 2;
    }

    if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("1x"))
    {
      hierMesh().chunkVisible("Torpedo_Support_D0", true);
      this.numBombsOld = 0;
    }
  }

  public SM79()
  {
    this.bDynamoOperational = true;
    this.dynamoOrient = 0.0F;
    this.bDynamoRotary = false;
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    if ((hierMesh().isChunkVisible("Prop2_D1")) && (hierMesh().isChunkVisible("Prop3_D1")) && ((hierMesh().isChunkVisible("Prop1_D0")) || (hierMesh().isChunkVisible("PropRot1_D0"))))
    {
      mydebuggunnery("!!!!!!!!!!!!!!!!!!! HIT PROP 1 !!!!!!!!!!!!!!!!!!!!!");
      hitProp(0, 0, Engine.actorLand());
    }

    if ((hierMesh().isChunkVisible("Prop2_D1")) && (hierMesh().isChunkVisible("Prop1_D1")) && ((hierMesh().isChunkVisible("Prop3_D0")) || (hierMesh().isChunkVisible("PropRot3_D0"))))
    {
      mydebuggunnery("!!!!!!!!!!!!!!!!!!! HIT PROP 3 !!!!!!!!!!!!!!!!!!!!!");
      hitProp(2, 0, Engine.actorLand());
    }

    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean)
    {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.39F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 3) && (World.Rnd().nextFloat() < 0.39F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[2] > 3) && (World.Rnd().nextFloat() < 0.39F)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);
      }
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[0] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[1] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[2] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.1F)) {
        nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[3] + "0", 0, this);
      }
    }

    War localWar = War.cur();

    Actor localActor = War.GetNearestEnemy(this, 16, 6000.0F);
    Aircraft localAircraft = War.getNearestEnemy(this, 5000.0F);

    if (((localActor != null) && (!(localActor instanceof BridgeSegment))) || (localAircraft != null))
    {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor() < 0.01F)
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this, 1);
      }

    }

    drawBombs();

    for (int i = 1; i <= 5; i++)
    {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("hmask" + i + "_d0", false);
      else
        hierMesh().chunkVisible("hmask" + i + "_d0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
    }
  }

  protected void drawBombs()
  {
    if (!this.bPitUnfocused) {
      return;
    }
    if (this.jdField_thisWeaponsName_of_type_JavaLangString.endsWith("drop"))
    {
      int i = 0;

      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null)
      {
        for (int j = 0; j < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length; j++)
        {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][j] != null)
            i += this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][j].countBullets();
        }
        int k;
        int m;
        if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12x1"))
        {
          for (k = 2; k < i + 1; k++)
          {
            hierMesh().chunkVisible("Bomb100Kg" + k + "_D0", true);
          }
          for (m = i + 1; m <= 12; m++)
          {
            hierMesh().chunkVisible("Bomb100Kg" + m + "_D0", false);
          }
        }

        if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12x5"))
        {
          for (k = 2; k < i + 1; k++)
          {
            hierMesh().chunkVisible("Bomb50Kg" + k + "_D0", true);
          }
          for (m = i + 1; m <= 12; m++)
          {
            hierMesh().chunkVisible("Bomb50Kg" + m + "_D0", false);
          }
        }

        if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6"))
        {
          for (k = 2; k < i + 1; k++)
          {
            hierMesh().chunkVisible("Bomb100Kg" + k + "_D0", true);
          }
          for (m = i + 1; m <= 6; m++)
          {
            hierMesh().chunkVisible("Bomb100Kg" + m + "_D0", false);
          }

        }

        if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("5"))
        {
          for (k = 2; k < i + 1; k++)
          {
            hierMesh().chunkVisible("Bomb250Kg" + k + "_D0", true);
          }
          for (m = i + 1; m <= 5; m++)
          {
            hierMesh().chunkVisible("Bomb250Kg" + m + "_D0", false);
          }
        }

        if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("2"))
        {
          if (i == 2)
          {
            hierMesh().chunkVisible("Bomb500Kg2_D0", true);
          }
          else
          {
            hierMesh().chunkVisible("Bomb500Kg2_D0", false);
          }
        }
      }
    }
  }

  protected void moveFan(float paramFloat)
  {
    if (this.bDynamoOperational)
    {
      this.pk = Math.abs((int)(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() / 14.0D));
      if (this.pk >= 1)
        this.pk = 1;
    }
    if (this.bDynamoRotary != (this.pk == 1))
    {
      this.bDynamoRotary = (this.pk == 1);
      hierMesh().chunkVisible("Dina_D0", !this.bDynamoRotary);
      hierMesh().chunkVisible("PropDina_D0", this.bDynamoRotary);
    }
    this.dynamoOrient = (this.bDynamoRotary ? (this.dynamoOrient - 17.987F) % 360.0F : (float)(this.dynamoOrient - this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() * 1.544401526451111D) % 360.0F);
    hierMesh().chunkSetAngles("Dina_D0", 0.0F, this.dynamoOrient, 0.0F);
    super.moveFan(paramFloat);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("hmask1_d0", false);

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
      hierMesh().chunkVisible("Pilot5_D0", false);
      hierMesh().chunkVisible("hmask5_d0", false);
      hierMesh().chunkVisible("Pilot5_D1", true);
      this.bPilot5Killed = true;
    }
  }

  public void update(float paramFloat)
  {
    for (int i = 0; i < 3; i++) {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getControlProp() < 0.5F)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setControlProp(0.0F);
      else
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setControlProp(1.0F);
    }
    if (Config.isUSE_RENDER())
    {
      if (this.bPitUnfocused)
      {
        Mat localMat = hierMesh().material(hierMesh().materialFind("InteriorFake1"));
        hierMesh().materialReplace("InteriorFake", localMat);
      }

    }

    float f1 = 0.0F;

    f1 = Pitot.Indicator((float)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Loc.jdField_z_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH());
    Controls localControls = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls;

    float f2 = (210.0F - f1) / 70.0F;
    if (f2 > 1.0F) f2 = 1.0F;
    else if (f2 < 0.0F) f2 = 0.0F;
    f2 *= f2;

    float f4 = -35.0F * f2;
    hierMesh().chunkSetAngles("Flap_RearL_D0", 0.0F, f4, 0.0F);
    hierMesh().chunkSetAngles("Flap_RearR_D0", 0.0F, f4, 0.0F);

    f4 = -3.0F * f2;
    hierMesh().chunkSetAngles("Flap_FrontL_D0", 0.0F, f4, 0.0F);
    hierMesh().chunkSetAngles("Flap_FrontR_D0", 0.0F, f4, 0.0F);

    localControls.forceFlaps(f2);
    localControls.bHasFlapsControl = false;

    float f3 = localControls.getAileron();
    float f5 = -(f3 * 30.0F + f2 * 17.0F);
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, f5, 0.0F);

    f5 = -(f3 * 30.0F - f2 * 17.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, f5, 0.0F);

    super.update(paramFloat);
  }

  protected void moveAileron(float paramFloat)
  {
    Controls localControls = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls;
    float f1 = localControls.getFlap();

    float f2 = -(paramFloat * 30.0F + f1 * 17.0F);
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, f2, 0.0F);

    f2 = -(paramFloat * 30.0F - f1 * 17.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, f2, 0.0F);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    boolean bool = isChunkAnyDamageVisible("Tail1_D");

    if (paramFloat < 0.99F)
    {
      if (!hierMesh().isChunkVisible("Tur2_DoorR_D0"))
      {
        hierMesh().chunkVisible("Tur2_DoorR_D0", true);
        hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
        hierMesh().chunkVisible("Tur2_DoorL_D0", true);
        hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
        hierMesh().chunkVisible("Tur1_DoorR_D0", true);
        hierMesh().chunkVisible("Tur1_DoorR_open_D0", false);
        hierMesh().chunkVisible("Tur1_DoorL_D0", true);
        hierMesh().chunkVisible("Tur1_DoorL_open_D0", false);
      }
      float f = 13.8F * paramFloat;
      hierMesh().chunkSetAngles("Tur2_Door1_D0", 0.0F, -f, 0.0F);
      f = 8.8F * paramFloat;
      hierMesh().chunkSetAngles("Tur2_Door2_D0", 0.0F, -f, 0.0F);
      f = 3.1F * paramFloat;
      hierMesh().chunkSetAngles("Tur2_Door3_D0", 0.0F, -f, 0.0F);
      f = 14.0F * paramFloat;
      hierMesh().chunkSetAngles("Tur2_DoorL_D0", 0.0F, -f, 0.0F);
      hierMesh().chunkSetAngles("Tur2_DoorR_D0", 0.0F, f, 0.0F);
      hierMesh().chunkSetAngles("Tur1_DoorL_D0", 0.0F, f, 0.0F);
      hierMesh().chunkSetAngles("Tur1_DoorR_D0", 0.0F, -f, 0.0F);
    }
    else if (hierMesh().isChunkVisible("Tur2_DoorR_D0"))
    {
      hierMesh().chunkVisible("Tur2_DoorR_D0", false);
      hierMesh().chunkVisible("Tur2_DoorR_open_D0", true);
      hierMesh().chunkVisible("Tur2_DoorL_D0", false);
      hierMesh().chunkVisible("Tur2_DoorL_open_D0", true);
      hierMesh().chunkVisible("Tur1_DoorR_D0", false);
      hierMesh().chunkVisible("Tur1_DoorR_open_D0", bool);
      hierMesh().chunkVisible("Tur1_DoorL_D0", false);
      hierMesh().chunkVisible("Tur1_DoorL_open_D0", bool);
    }
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
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor() < 0.01F)
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this, 1);
    super.hitDaSilk();
  }

  protected void mydebuggunnery(String paramString)
  {
  }

  protected void setControlDamage(Shot paramShot, int paramInt)
  {
    if (World.Rnd().nextFloat() < 0.002F)
    {
      if (getEnergyPastArmor(4.0F, paramShot) > 0.0F)
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, paramInt);
        mydebuggunnery(paramInt + " Controls Out... //0 = AILERON, 1 = ELEVATOR, 2 = RUDDER");
      }
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    mydebuggunnery("HitBone called! " + paramString);
    mydebuggunnery("IN: " + paramShot.power);
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
        {
          if (Aircraft.v1.jdField_z_of_type_Double > 0.5D) {
            getEnergyPastArmor(4.0D / Aircraft.v1.jdField_z_of_type_Double, paramShot);
          }
          else if (Aircraft.v1.jdField_x_of_type_Double > 0.9396926164627075D)
            getEnergyPastArmor(8.0D / Aircraft.v1.jdField_x_of_type_Double * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(4.0D / Math.abs(Aircraft.v1.jdField_z_of_type_Double), paramShot);
        }
        else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(7.0D / Math.abs(Aircraft.v1.jdField_x_of_type_Double) * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        else if (paramString.endsWith("p4"))
        {
          if (Aircraft.v1.jdField_x_of_type_Double > 0.7071067690849304D) {
            getEnergyPastArmor(7.0D / Aircraft.v1.jdField_x_of_type_Double * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          }
          else if (Aircraft.v1.jdField_x_of_type_Double > -0.7071067690849304D)
            getEnergyPastArmor(5.0F, paramShot);
        }
        else if ((paramString.endsWith("a1")) || (paramString.endsWith("a3")) || (paramString.endsWith("a4")))
          if (Aircraft.v1.jdField_x_of_type_Double > 0.7071067690849304D)
            getEnergyPastArmor(1.0D / Aircraft.v1.jdField_x_of_type_Double * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          else
            getEnergyPastArmor(0.8F, paramShot);
      }
      if (paramString.startsWith("xxspar"))
      {
        getEnergyPastArmor(4.0F, paramShot);
        if (((paramString.endsWith("cf1")) || (paramString.endsWith("cf2"))) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("CF") > 2) && (getEnergyPastArmor(15.9F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F))
        {
          mydebuggunnery("*** CF Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
          msgCollision(this, "WingLIn_D0", "WingLIn_D0");
          msgCollision(this, "WingRIn_D0", "WingRIn_D0");
        }
        if (((paramString.endsWith("t1")) || (paramString.endsWith("t2"))) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(15.9F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F))
        {
          mydebuggunnery("*** Tail1 Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D2", paramShot.initiator);
        }
        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D2", paramShot.initiator);
        }
        if ((paramString.endsWith("e1")) && ((paramPoint3d.jdField_y_of_type_Double > 2.79D) || (paramPoint3d.jdField_y_of_type_Double < 2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          mydebuggunnery("*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }
        if ((paramString.endsWith("e2")) && ((paramPoint3d.jdField_y_of_type_Double < -2.79D) || (paramPoint3d.jdField_y_of_type_Double > -2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          mydebuggunnery("*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }
        if ((paramString.endsWith("e3")) && ((paramPoint3d.jdField_y_of_type_Double < -2.79D) || (paramPoint3d.jdField_y_of_type_Double > -2.32D)) && (getEnergyPastArmor(17.0F, paramShot) > 0.0F))
        {
          mydebuggunnery("*** Engine3 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine3_D0", paramShot.initiator);
        }

        if (((paramString.endsWith("k1")) || (paramString.endsWith("k2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          mydebuggunnery("*** Keel spars damaged..");
          nextDMGLevels(1, 2, "Keel1_D0", paramShot.initiator);
        }
        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          mydebuggunnery("*** Right Stab spars damaged..");
          nextDMGLevels(1, 2, "StabR_D0", paramShot.initiator);
        }
        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          mydebuggunnery("*** Left Stab spars damaged..");
          nextDMGLevels(1, 2, "StabL_D0", paramShot.initiator);
        }
      }
      if ((paramString.startsWith("xxbomb")) && (World.Rnd().nextFloat() < 0.01F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].haveBullets()))
      {
        mydebuggunnery("*** Bomb Payload Detonates..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 100);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 100);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, 100);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 100);
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
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 3);
          mydebuggunnery("*** Engine" + (i + 1) + " Governor Failed..");
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
            if (World.Rnd().nextFloat() < paramShot.power / 190000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, i);
              mydebuggunnery("*** Engine" + (i + 1) + " Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
              mydebuggunnery("*** Engine" + (i + 1) + " Crank Case Hit - Engine Damaged..");
            }
          }
        }
        else if (paramString.endsWith("cyls"))
        {
          if ((getEnergyPastArmor(1.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersRatio() * 1.3F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4500.0F)));
            mydebuggunnery("*** Engine" + (i + 1) + " Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] < 1)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetEngineState(paramShot.initiator, i, 1);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 800000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 3);
              mydebuggunnery("*** Engine" + (i + 1) + " Cylinders Hit - Engine Fires..");
            }
            mydebuggunnery("*** Engine" + (i + 1) + " state " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i]);
          }

        }
        else if ((paramString.endsWith("supc")) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.95F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 0);
          mydebuggunnery("*** Engine" + (i + 1) + " Supercharger Out..");
        }
        if ((paramString.endsWith("oil1")) || (paramString.endsWith("oil2")) || (paramString.endsWith("oil3")))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.42F, paramShot);
        }
        mydebuggunnery("*** Engine" + (i + 1) + " state = " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i]);
      }

      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(1.1F, paramShot) > 0.0F)
          if (paramShot.power < 14100.0F)
          {
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] < 1)
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.1F))
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            if ((paramShot.powerType == 3) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] > 1) && (World.Rnd().nextFloat() < 0.07F))
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 10);
          }
          else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 40000.0F)));
          }
        mydebuggunnery("*** Tank " + (i + 1) + " state = " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i]);
      }
      if (paramString.startsWith("xxlock"))
      {
        mydebuggunnery("Lock Construction: Hit..");
        if (paramString.startsWith("xxlockr"))
        {
          if (((paramString.startsWith("xxlockr1")) || (paramString.startsWith("xxlockr2"))) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }
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

        if ((paramString.startsWith("xxlockal")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          mydebuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockar")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

      }

    }

    if (paramString.startsWith("x12"))
    {
      if (paramString.startsWith("x12,7_01"))
      {
        if ((getEnergyPastArmor(5.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.startsWith("x12,7_00"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 2);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.startsWith("x12,7_02"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.startsWith("x12,7_03"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 4);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
      else if (paramString.startsWith("x12,7_04"))
      {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 3);
          getEnergyPastArmor(11.98F, paramShot);
        }
      }
    }

    if (paramString.startsWith("xcf"))
    {
      setControlDamage(paramShot, 0);
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (World.Rnd().nextFloat() < 0.1F)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
      if (World.Rnd().nextFloat() < 0.1F)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
      if (World.Rnd().nextFloat() < 0.1F)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
      if (World.Rnd().nextFloat() < 0.1F)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
      if (World.Rnd().nextFloat() < 0.1F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
      }
      getEnergyPastArmor(1.0F, paramShot);
    }
    else if (paramString.startsWith("xtail"))
    {
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Tail1") < 3) {
        hitChunk("Tail1", paramShot);
      }
      if (World.Rnd().nextFloat() < 0.1F)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
      if (World.Rnd().nextFloat() < 0.1F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
      }
      getEnergyPastArmor(3.0F, paramShot);
    }
    else if (paramString.startsWith("xkeel"))
    {
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder"))
    {
      setControlDamage(paramShot, 2);
      hitChunk("Rudder1", paramShot);
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
      if (chunkDamageVisible("WingLIn") < 3)
        hitChunk("WingLIn", paramShot);
      getEnergyPastArmor(1.0F, paramShot);
    }
    else if (paramString.startsWith("xwingrin"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingRIn") < 3)
        hitChunk("WingRIn", paramShot);
      getEnergyPastArmor(1.0F, paramShot);
    }
    else if (paramString.startsWith("xwinglmid"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingLMid") < 3)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid"))
    {
      setControlDamage(paramShot, 0);
      if (chunkDamageVisible("WingRMid") < 3)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout"))
    {
      if (chunkDamageVisible("WingLOut") < 3)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout"))
    {
      if (chunkDamageVisible("WingROut") < 3)
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
        mydebuggunnery("*** Gear Hydro Failed..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.setHydroOperable(false);
      }
    }
    else if (paramString.startsWith("xturret"))
    {
      if (paramString.startsWith("xturret1"))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 0);
      if (paramString.startsWith("xturret2"))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 0);
      if (paramString.startsWith("xturret3"))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(12, 0);
      if (paramString.startsWith("xturret4"))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(13, 0);
      if (paramString.startsWith("xturret5"))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(14, 0);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")) || (paramString.startsWith("xpilox")) || (paramString.startsWith("xheax")))
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
      }
      mydebuggunnery("call HitFlesh:  " + j + " " + i + " " + paramShot.power);
      hitFlesh(j, paramShot, i);
    }
    mydebuggunnery("out:  " + paramShot.power);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    if (paramInt1 == 19)
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.hitCentreGear();
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 800.0F, -70.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -81.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -81.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, 45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 53.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 53.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, 1.2F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, 1.2F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -1.2F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -1.2F * f, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  protected void moveBayDoor(float paramFloat)
  {
    float f = paramFloat * 4.0F;
    hierMesh().chunkSetAngles("DoorL_D0", 0.0F, 23.0F * f, 0.0F);
    hierMesh().chunkSetAngles("DoorR_D0", 0.0F, -23.0F * f, 0.0F);
    f = -(paramFloat * 3.5F);
    hierMesh().chunkSetAngles("Gambali_D0", 0.0F, f, 0.0F);
    this.bayDoorAngle = paramFloat;
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryItaly);
  }
}