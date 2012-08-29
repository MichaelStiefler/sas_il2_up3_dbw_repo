package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

public class CockpitSM79_Bombardier extends CockpitPilot
{
  private static final float[] speedometerScale = { 0.0F, 10.0F, 20.0F, 30.0F, 50.0F, 68.0F, 88.0F, 109.0F, 126.0F, 142.0F, 159.0F, 176.0F, 190.0F, 206.0F, 220.0F, 238.0F, 253.0F, 270.0F, 285.0F, 300.0F, 312.0F, 325.0F, 337.0F, 350.0F, 360.0F };

  private boolean firstEnter = true;
  private boolean bTurrVisible;
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered = false;
  private int bombToDrop = 0;
  private float dropTime = 0.0F;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();
  private LightPointActor light1;
  static Class class$com$maddox$il2$objects$air$CockpitSM79_Bombardier;
  private boolean bNeedSetUp;
  private Hook hook1;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);

      ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPitUnfocused = false;

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

      aircraft().hierMesh().chunkVisible("Gambali_D0", false);

      if ((aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12")) || (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6")))
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

      if (this.firstEnter)
      {
        if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("1x"))
        {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Stand", false);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Cylinder", false);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Support", false);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Reticle1", false);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Reticle2", false);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZCursor1", false);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZCursor2", false);
        }
        this.firstEnter = false;
      }

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
      aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", false);

      aircraft().hierMesh().chunkVisible("Gambali_D0", false);

      for (int i = 1; i <= 12; i++) {
        aircraft().hierMesh().chunkVisible("Bomb100Kg" + i + "_D0", false);
      }
      for (int j = 1; j <= 12; j++) {
        aircraft().hierMesh().chunkVisible("Bomb50Kg" + j + "_D0", false);
      }
      for (int k = 1; k <= 5; k++) {
        aircraft().hierMesh().chunkVisible("Bomb250Kg" + k + "_D0", false);
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

      aircraft().hierMesh().chunkVisible("Interior2_D0", bool);
      aircraft().hierMesh().chunkVisible("Interior1_D0", true);

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

      aircraft().hierMesh().chunkVisible("Gambali_D0", bool);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);
      int i;
      if ((aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12")) || (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6")))
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

      leave();
      super.doFocusLeave();
    }
  }

  private void enter() {
    this.saveFov = Main3D.FOVX;

    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(this.aAim, this.tAim, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
    this.bombToDrop = 0;
    this.dropTime = 0.0F;
  }

  private void leave() {
    if (this.bEntered)
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);

      localHookPilot.setSimpleUse(false);
      boolean bool = HotKeyEnv.isEnabled("aircraftView");
      HotKeyEnv.enable("PanView", bool);
      HotKeyEnv.enable("SnapView", bool);
      this.bEntered = false;
    }
  }

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
          mydebugcockpit("BOMB DROPPED!!! ");
          ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).numBombsOld = i;
          this.bombToDrop = (i + 1);
          this.dropTime = 0.0F;

          mydebugcockpit("Bomb to drop: " + this.bombToDrop);
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
      mydebugcockpit("drawFallingBomb Drop time = " + this.dropTime);

      this.dropTime += 0.03F;

      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12x1"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bomb100Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Bomb100Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.4F)
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
        if (this.dropTime >= 0.4F)
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
        if (this.dropTime >= 0.4F)
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
        if (this.dropTime >= 0.5F)
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
        if (this.dropTime >= 0.5F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }
    }
  }

  public void destroy() {
    super.destroy();
    leave();
  }

  public void doToggleAim(boolean paramBoolean)
  {
    if ((isFocused()) && (isToggleAim() != paramBoolean))
      if (paramBoolean)
        enter();
      else
        leave();
  }

  public CockpitSM79_Bombardier()
  {
    super("3DO/Cockpit/SM79Bombardier/hier.him", "he111");
    try {
      Loc localLoc = new Loc();
      localObject = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "CAMERAAIM");
      ((HookNamed)localObject).computePos(this, this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(), localLoc);
      this.aAim = localLoc.getOrient().getAzimut();
      this.tAim = localLoc.getOrient().getTangage();
      this.kAim = localLoc.getOrient().getKren();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }

    HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK1");
    Object localObject = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), (Loc)localObject);

    this.light1 = new LightPointActor(new LightPoint(), ((Loc)localObject).getPoint());
    this.light1.light.setColor(109.0F, 99.0F, 90.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    this.cockpitNightMats = new String[] { "Panel", "Needles" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      this.light1.light.setEmit(0.0032F, 7.2F);
      setNightMats(true);
    }
    else
    {
      this.light1.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    reflectPlaneToModel();
    resetYPRmodifier();

    aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);

    float f1 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor();
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
      f2 = 10.0F * f1;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_DoorL_Int_D0", 0.0F, -f2, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_DoorR_Int_D0", 0.0F, f2, 0.0F);
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

    float f2 = interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat) * 0.072F;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter", 0.0F, f2, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 460.0F, 0.0F, 23.0F), speedometerScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("z_Hour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("z_Minute", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("z_Second", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret3A", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[2].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret3B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[2].tu[1], 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret2A", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[1].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret2B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[1].tu[1], 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret4A", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[3].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zturret4B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[3].tu[1], 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[0].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[0].tu[1], 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -28.0F, 28.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZRudderTrim", 0.0F, interp(this.setNew.rudderTrim, this.setOld.rudderTrim, paramFloat) * 360.0F, 0.0F);

    float f3 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor();
    aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);

    float f4 = -28.0F * f3;
    float f5 = cvt(f3, 0.0F, 1.0F, 0.0F, -20.0F);
    float f6 = cvt(f3, 0.5F, 1.0F, 0.0F, -15.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur1_DoorL_Int_D0", -f5, -f4, -f6);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur1_DoorR_Int_D0", f5, f4, -f6);

    if (!aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("1x"))
    {
      resetYPRmodifier();
      f7 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getPitch();
      if (f7 > 360.0F) {
        f7 -= 360.0F;
      }

      f7 *= 0.00872664F;

      float f8 = ((SM79)aircraft()).fSightSetForwardAngle - (float)Math.toRadians(f7);
      float f9 = (float)(0.1691599935293198D * Math.tan(f8));

      if (f9 < 0.032F)
        f9 = 0.032F;
      else if (f9 > 0.21F) {
        f9 = 0.21F;
      }
      float f10 = f9 * 0.667F;

      Cockpit.xyz[0] = f9;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("ZCursor1", Cockpit.xyz, Cockpit.ypr);

      Cockpit.xyz[0] = f10;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("ZCursor2", Cockpit.xyz, Cockpit.ypr);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Cylinder", 0.0F, ((SM79)aircraft()).fSightCurSideslip, 0.0F);
    }

    drawBombsInt();

    drawFallingBomb(paramFloat);

    float f7 = ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bayDoorAngle;

    if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12"))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever07", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever08", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever09", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever10", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever11", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever12", 0.0F, 55.0F * f7, 0.0F);
    }

    if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6"))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f7, 0.0F);
    }

    if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("5"))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f7, 0.0F);
    }

    if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("2"))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TypewriterLever07", 0.0F, 55.0F * f7, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bridge1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Bridge2", true);
    }

    if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.endsWith("drop"))
    {
      int i = 0;
      int j;
      if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null)
      {
        for (j = 0; j < this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length; j++)
        {
          if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][j] != null) {
            i += this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][j].countBullets();
          }
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("12"))
      {
        for (j = 1; j <= 12 - i; j++) {
          if (j < 10)
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key0" + j, 0.0F, 25.0F, 0.0F);
          else
            this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key" + j, 0.0F, 25.0F, 0.0F);
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("6"))
      {
        for (j = 1; j <= 6 - i; j++) {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key" + j, 0.0F, 25.0F, 0.0F);
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("5"))
      {
        for (j = 1; j <= 5 - i; j++) {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key0" + j, 0.0F, 25.0F, 0.0F);
        }
      }
      if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("2"))
      {
        if (i == 0)
        {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key02", 0.0F, 25.0F, 0.0F);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key03", 0.0F, 25.0F, 0.0F);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key06", 0.0F, 25.0F, 0.0F);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key07", 0.0F, 25.0F, 0.0F);
        }
        if (i == 1)
        {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key02", 0.0F, 25.0F, 0.0F);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Typewriter_Key03", 0.0F, 25.0F, 0.0F);
        }
      }
    }
  }

  protected void mydebugcockpit(String paramString)
  {
  }

  public void reflectCockpitState()
  {
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState != 0)
    {
      if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles3", true);
      if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles3", true);
    }
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Glass2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Glass2", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Pilot1", localMat);
  }

  protected void reflectPlaneToModel()
  {
  }

  private class Variables
  {
    float altimeter;
    float rudderTrim;
    AnglesFork azimuth;
    private final CockpitSM79_Bombardier this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
    }

    Variables(CockpitSM79_Bombardier.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitSM79_Bombardier.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitSM79_Bombardier.access$102(CockpitSM79_Bombardier.this, CockpitSM79_Bombardier.this.setOld);
        CockpitSM79_Bombardier.access$202(CockpitSM79_Bombardier.this, CockpitSM79_Bombardier.this.setNew);
        CockpitSM79_Bombardier.access$302(CockpitSM79_Bombardier.this, CockpitSM79_Bombardier.this.setTmp);
        CockpitSM79_Bombardier.this.setNew.altimeter = CockpitSM79_Bombardier.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        CockpitSM79_Bombardier.this.setNew.rudderTrim = CockpitSM79_Bombardier.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getTrimRudderControl();
        CockpitSM79_Bombardier.this.setNew.azimuth.setDeg(CockpitSM79_Bombardier.this.setOld.azimuth.getDeg(1.0F), 90.0F + CockpitSM79_Bombardier.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.azimut());
      }
      float f1 = ((SM79)CockpitSM79_Bombardier.this.aircraft()).fSightCurForwardAngle;

      float f2 = 0.0F;

      if (CockpitSM79_Bombardier.this.bEntered)
      {
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(CockpitSM79_Bombardier.this.aAim + f2, CockpitSM79_Bombardier.this.tAim + f1, 0.0F);
      }
      return true;
    }
  }
}