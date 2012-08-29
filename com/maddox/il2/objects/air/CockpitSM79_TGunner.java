package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitSM79_TGunner extends CockpitGunner
{
  private int bombToDrop = 0;
  private float dropTime = 0.0F;

  private boolean firstEnter = true;
  private Mat matNull;
  private Mat matInterior;
  public Vector3f w = new Vector3f();
  private boolean bNeedSetUp;
  private Hook hook1;
  private int iCocking;
  private int iOldVisDrums;
  private int iNewVisDrums;
  private float pictLever;

  protected void drawBombsInt()
  {
    if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.endsWith("drop"))
    {
      int i = 0;

      if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null)
      {
        for (int j = 0; j < this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length; j++)
        {
          if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][j] != null) {
            i += this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][j].countBullets();
          }
        }

        if (i < ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).numBombsOld)
        {
          ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).numBombsOld = i;
          this.bombToDrop = (i + 1);
          this.dropTime = 0.0F;
        }
        int k;
        int m;
        if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12x1"))
        {
          for (k = 1; k < i + 1; k++)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb100Kg" + k, true);
          }
          for (m = i + 1; m <= 12; m++)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb100Kg" + m, false);
          }
        }

        if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12x5"))
        {
          for (k = 1; k < i + 1; k++)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb50Kg" + k, true);
          }
          for (m = i + 1; m <= 12; m++)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb50Kg" + m, false);
          }
        }

        if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6"))
        {
          for (k = 1; k < i + 1; k++)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb100Kg" + k, true);
          }
          for (m = i + 1; m <= 6; m++)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb100Kg" + m, false);
          }
        }

        if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("5"))
        {
          for (k = 1; k < i + 1; k++)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb250Kg" + k, true);
          }
          for (m = i + 1; m <= 5; m++)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb250Kg" + m, false);
          }
        }

        if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("2"))
        {
          if (i == 2)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb500Kg1", true);
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb500Kg2", true);
          }
          if (i == 1)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb500Kg1", true);
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb500Kg2", false);
          }
          if (i == 0)
          {
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb500Kg1", false);
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb500Kg2", false);
          }
        }
      }
    }
  }

  protected void drawFallingBomb(float paramFloat)
  {
    if (this.bombToDrop != 0)
    {
      this.dropTime += 0.06F * paramFloat;

      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12x1"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb100Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Bomb100Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.6F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }

      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12x5"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb50Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Bomb50Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.6F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }

      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb100Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Bomb100Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.6F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("5"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb250Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Bomb250Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.6F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("2"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb500Kg" + this.bombToDrop, true);

        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Bomb500Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.6F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }
    }
  }

  protected boolean doFocusEnter()
  {
    if (this.firstEnter)
    {
      this.matNull = aircraft().hierMesh().material(aircraft().hierMesh().materialFind("NullMat"));
      this.matInterior = aircraft().hierMesh().material(aircraft().hierMesh().materialFind("InteriorFake1"));

      this.firstEnter = false;
    }

    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);

      aircraft().hierMesh().materialReplace("InteriorFake", this.matNull);

      aircraft().hierMesh().chunkVisible("Pilot1_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot1_D1", false);
      aircraft().hierMesh().chunkVisible("Pilot2_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot2_D1", false);
      aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot3_D1", false);
      aircraft().hierMesh().chunkVisible("Pilot4_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot4_D1", false);
      aircraft().hierMesh().chunkVisible("Pilot5_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot5_D1", false);

      ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPitUnfocused = false;

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_Int_D0", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int_D0", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_Int_D0", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door1_Int_D0", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door2_Int_D0", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door3_Int_D0", true);

      aircraft().hierMesh().chunkVisible("Interior2_D0", false);
      aircraft().hierMesh().chunkVisible("Interior1_D0", false);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", false);

      aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
      aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);
      aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", false);

      aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", false);

      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12"))
      {
        for (i = 1; i <= 12; i++)
        {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BombRack" + i, true);
          aircraft().hierMesh().chunkVisible("BombRack" + i + "_D0", false);
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("5"))
      {
        for (i = 1; i <= 5; i++)
        {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BombRack250_" + i, true);
          aircraft().hierMesh().chunkVisible("BombRack250_" + i + "_D0", false);
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("2"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BombRack500_1", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BombRack500_2", true);

        aircraft().hierMesh().chunkVisible("BombRack500_1_D0", false);
        aircraft().hierMesh().chunkVisible("BombRack500_2_D0", false);
      }

      for (int i = 1; i <= 12; i++) {
        aircraft().hierMesh().chunkVisible("Bomb100Kg" + i + "_D0", false);
      }
      for (int j = 1; j <= 5; j++) {
        aircraft().hierMesh().chunkVisible("Bomb250Kg" + j + "_D0", false);
      }
      aircraft().hierMesh().chunkVisible("Bomb500Kg2_D0", false);

      return true;
    }
    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPitUnfocused = true;

      boolean bool = aircraft().isChunkAnyDamageVisible("Tail1_D");

      if (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotParatrooper(0))
      {
        aircraft().hierMesh().chunkVisible("Pilot1_D0", !((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot1Killed);
        aircraft().hierMesh().chunkVisible("Pilot1_D1", ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot1Killed);
      }

      if (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotParatrooper(1))
      {
        aircraft().hierMesh().chunkVisible("Pilot2_D0", !((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot2Killed);
        aircraft().hierMesh().chunkVisible("Pilot2_D1", ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot2Killed);
      }

      if (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotParatrooper(2))
      {
        aircraft().hierMesh().chunkVisible("Pilot3_D0", !((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot3Killed);
        aircraft().hierMesh().chunkVisible("Pilot3_D1", ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot3Killed);
      }

      if (bool)
      {
        if (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotParatrooper(3))
        {
          aircraft().hierMesh().chunkVisible("Pilot4_D0", !((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot4Killed);
          aircraft().hierMesh().chunkVisible("Pilot4_D1", ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot4Killed);
        }

        if (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotParatrooper(4))
        {
          aircraft().hierMesh().chunkVisible("Pilot5_D0", !((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot5Killed);
          aircraft().hierMesh().chunkVisible("Pilot5_D1", ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot5Killed);
        }
      }

      aircraft().hierMesh().chunkVisible("Interior2_D0", bool);
      aircraft().hierMesh().chunkVisible("Interior1_D0", true);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door1_Int_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door2_Int_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door3_Int_D0", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);

      float f = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor();
      if (f > 0.99D)
      {
        aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", bool);
        aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", bool);
      }
      else
      {
        aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", bool);
        aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", bool);
      }

      aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", bool);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", bool);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", bool);
      int i;
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12"))
      {
        for (i = 1; i <= 12; i++)
        {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BombRack" + i, false);
          aircraft().hierMesh().chunkVisible("BombRack" + i + "_D0", true);
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("5"))
      {
        for (i = 1; i <= 5; i++)
        {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BombRack250_" + i, false);
          aircraft().hierMesh().chunkVisible("BombRack250_" + i + "_D0", true);
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("2"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BombRack500_1", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BombRack500_2", false);

        aircraft().hierMesh().chunkVisible("BombRack500_1_D0", true);
        aircraft().hierMesh().chunkVisible("BombRack500_2_D0", true);
      }

      super.doFocusLeave();
    }
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    drawBombsInt();
    drawFallingBomb(paramFloat);

    aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);

    float f1 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor();
    aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", false);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurDoorLever", 0.0F, -40.0F * (this.pictLever = 0.89F * this.pictLever + 0.11F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl), 0.0F);

    if (f1 < 0.99F)
    {
      if (!this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_Int_D0"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_Int_D0", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_Int_D0", true);
      }

      f2 = 13.8F * f1;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door1_Int_D0", 0.0F, -f2, 0.0F);
      f2 = 8.8F * f1;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door2_Int_D0", 0.0F, -f2, 0.0F);
      f2 = 3.1F * f1;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door3_Int_D0", 0.0F, -f2, 0.0F);

      f2 = 15.0F * f1;

      float f3 = cvt(f1, 0.2F, 1.0F, 0.0F, -16.0F);
      float f4 = cvt(f1, 0.4F, 1.0F, 0.0F, 7.5F);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_DoorL_Int_D0", -f3, -f2, -f4);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_DoorR_Int_D0", f3, f2, -f4);
    }
    else
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_Int_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int_D0", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_Int_D0", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door1_Int_D0", 0.0F, -13.8F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door2_Int_D0", 0.0F, -8.8F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door3_Int_D0", 0.0F, -3.1F, 0.0F);
    }

    float f2 = -20.0F * f1;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur1_DoorL_Int_D0", 0.0F, f2, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur1_DoorR_Int_D0", 0.0F, -f2, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret3A", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[2].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret3B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[2].tu[1], 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret2A", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[1].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret2B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[1].tu[1], 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret4A", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[3].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret4B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[3].tu[1], 0.0F);
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", 0.0F, paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode())
      return;
    if ((!aiTurret().bIsOperable) || (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor() < 0.5F))
    {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    if (f1 < -40.0F)
      f1 = -40.0F;
    if (f1 > 40.0F)
      f1 = 40.0F;
    if (f2 > 70.0F)
      f2 = 70.0F;
    if (f2 < -4.0F)
      f2 = -4.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick()
  {
    if (!isRealMode())
      return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.jdField_bGunFire_of_type_Boolean = false;
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
    if (this.jdField_bGunFire_of_type_Boolean)
    {
      if (this.hook1 == null)
        this.hook1 = new HookNamed(aircraft(), "_12,7_02");
      doHitMasterAircraft(aircraft(), this.hook1, "_12,7_02");
      if (this.iCocking > 0)
        this.iCocking = 0;
      else
        this.iCocking = 1;
    }
    else {
      this.iCocking = 0;
    }
    if (this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter != null)
    {
      boolean bool = this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.countBullets() % 2 == 0;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TurretB-Bullet0", bool);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TurretB-Bullet1", !bool);
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode())
      return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.jdField_bGunFire_of_type_Boolean = false;
    else
      this.jdField_bGunFire_of_type_Boolean = paramBoolean;
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D0o", localMat);
  }

  public CockpitSM79_TGunner()
  {
    super("3DO/Cockpit/SM79Tgun/hier.him", "he111_gunner");
    this.bNeedSetUp = true;
    this.hook1 = null;
    this.iCocking = 0;
    this.iOldVisDrums = 3;
    this.iNewVisDrums = 3;
    this.pictLever = 0.0F;
  }

  static
  {
    Property.set(CockpitSM79_TGunner.class, "aiTuretNum", 0);
    Property.set(CockpitSM79_TGunner.class, "weaponControlNum", 10);
    Property.set(CockpitSM79_TGunner.class, "astatePilotIndx", 2);
  }
}