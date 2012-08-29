package com.maddox.il2.objects.air;

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

public class CockpitSM79_RGunner extends CockpitGunner
{
  private boolean firstEnter = true;
  private int bombToDrop = 0;
  private float dropTime = 0.0F;
  private boolean bNeedSetUp;
  private Hook hook1;
  private int iCocking;
  private int iOldVisDrums;
  private int iNewVisDrums;

  protected boolean doFocusEnter()
  {
    this.bombToDrop = 0;
    this.dropTime = 0.0F;
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);

      if (this.firstEnter)
      {
        if (aircraft().thisWeaponsName.startsWith("1x"))
        {
          this.mesh.chunkVisible("Stand", false);
          this.mesh.chunkVisible("Cylinder", false);
          this.mesh.chunkVisible("Support", false);
          this.mesh.chunkVisible("Reticle1", false);
          this.mesh.chunkVisible("Reticle2", false);
          this.mesh.chunkVisible("ZCursor1", false);
          this.mesh.chunkVisible("ZCursor2", false);
        }
        this.firstEnter = false;
      }

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

      ((SM79)this.fm.actor).bPitUnfocused = false;

      aircraft().hierMesh().chunkVisible("Interior2_D0", false);
      aircraft().hierMesh().chunkVisible("Interior1_D0", false);

      aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
      aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);
      aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", false);
      aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", false);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", false);

      aircraft().hierMesh().chunkVisible("Gambali_D0", false);
      int i;
      if ((aircraft().thisWeaponsName.startsWith("12")) || (aircraft().thisWeaponsName.startsWith("6")))
      {
        for (i = 1; i <= 12; i++)
        {
          this.mesh.chunkVisible("BombRack" + i, true);
          aircraft().hierMesh().chunkVisible("BombRack" + i + "_D0", false);
        }
      }
      if (aircraft().thisWeaponsName.startsWith("5"))
      {
        for (i = 1; i <= 5; i++)
        {
          this.mesh.chunkVisible("BombRack250_" + i, true);
          aircraft().hierMesh().chunkVisible("BombRack250_" + i + "_D0", false);
        }
      }
      if (aircraft().thisWeaponsName.startsWith("2"))
      {
        this.mesh.chunkVisible("BombRack500_1", true);
        this.mesh.chunkVisible("BombRack500_2", true);

        aircraft().hierMesh().chunkVisible("BombRack500_1_D0", false);
        aircraft().hierMesh().chunkVisible("BombRack500_2_D0", false);
      }

      return true;
    }
    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      ((SM79)this.fm.actor).bPitUnfocused = true;

      boolean bool = aircraft().isChunkAnyDamageVisible("Tail1_D");

      aircraft().hierMesh().chunkVisible("Interior2_D0", bool);
      aircraft().hierMesh().chunkVisible("Interior1_D0", true);

      if (!this.fm.AS.isPilotParatrooper(0))
      {
        aircraft().hierMesh().chunkVisible("Pilot1_D0", !((SM79)this.fm.actor).bPilot1Killed);
        aircraft().hierMesh().chunkVisible("Pilot1_D1", ((SM79)this.fm.actor).bPilot1Killed);
      }

      if (!this.fm.AS.isPilotParatrooper(1))
      {
        aircraft().hierMesh().chunkVisible("Pilot2_D0", !((SM79)this.fm.actor).bPilot2Killed);
        aircraft().hierMesh().chunkVisible("Pilot2_D1", ((SM79)this.fm.actor).bPilot2Killed);
      }

      if (!this.fm.AS.isPilotParatrooper(2))
      {
        aircraft().hierMesh().chunkVisible("Pilot3_D0", !((SM79)this.fm.actor).bPilot3Killed);
        aircraft().hierMesh().chunkVisible("Pilot3_D1", ((SM79)this.fm.actor).bPilot3Killed);
      }

      if (bool)
      {
        if (!this.fm.AS.isPilotParatrooper(3))
        {
          aircraft().hierMesh().chunkVisible("Pilot4_D0", !((SM79)this.fm.actor).bPilot4Killed);
          aircraft().hierMesh().chunkVisible("Pilot4_D1", ((SM79)this.fm.actor).bPilot4Killed);
        }

        if (!this.fm.AS.isPilotParatrooper(4))
        {
          aircraft().hierMesh().chunkVisible("Pilot5_D0", !((SM79)this.fm.actor).bPilot5Killed);
          aircraft().hierMesh().chunkVisible("Pilot5_D1", ((SM79)this.fm.actor).bPilot5Killed);
        }
      }

      float f = this.fm.CT.getCockpitDoor();
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

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);

      this.mesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);

      aircraft().hierMesh().chunkVisible("Gambali_D0", bool);
      int i;
      if (aircraft().thisWeaponsName.startsWith("12"))
      {
        for (i = 1; i <= 12; i++)
        {
          this.mesh.chunkVisible("BombRack" + i, false);
          aircraft().hierMesh().chunkVisible("BombRack" + i + "_D0", true);
        }
      }
      if (aircraft().thisWeaponsName.startsWith("6"))
      {
        for (i = 1; i <= 6; i++)
        {
          this.mesh.chunkVisible("BombRack" + i, false);
          aircraft().hierMesh().chunkVisible("BombRack" + i + "_D0", true);
        }
      }
      if (aircraft().thisWeaponsName.startsWith("5"))
      {
        for (i = 1; i <= 5; i++)
        {
          this.mesh.chunkVisible("BombRack250_" + i, false);
          aircraft().hierMesh().chunkVisible("BombRack250_" + i + "_D0", true);
        }
      }
      if (aircraft().thisWeaponsName.startsWith("2"))
      {
        this.mesh.chunkVisible("BombRack500_1", false);
        this.mesh.chunkVisible("BombRack500_2", false);

        aircraft().hierMesh().chunkVisible("BombRack500_1_D0", true);
        aircraft().hierMesh().chunkVisible("BombRack500_2_D0", true);
      }

      super.doFocusLeave();
    }
  }

  protected void drawBombsInt()
  {
    if (aircraft().thisWeaponsName.endsWith("drop"))
    {
      int i = 0;

      if (this.fm.CT.Weapons[3] != null)
      {
        for (int j = 0; j < this.fm.CT.Weapons[3].length; j++)
        {
          if (this.fm.CT.Weapons[3][j] != null) {
            i += this.fm.CT.Weapons[3][j].countBullets();
          }
        }

        if (i < ((SM79)this.fm.actor).numBombsOld)
        {
          ((SM79)this.fm.actor).numBombsOld = i;
          this.bombToDrop = (i + 1);
          this.dropTime = 0.0F;
        }

        if (aircraft().thisWeaponsName.startsWith("12x1"))
        {
          for (j = 1; j < i + 1; j++)
          {
            this.mesh.chunkVisible("Bomb100Kg" + j, true);
          }
          for (j = i + 1; j <= 12; j++)
          {
            this.mesh.chunkVisible("Bomb100Kg" + j, false);
          }
        }

        if (aircraft().thisWeaponsName.startsWith("12x5"))
        {
          for (j = 1; j < i + 1; j++)
          {
            this.mesh.chunkVisible("Bomb50Kg" + j, true);
          }
          for (j = i + 1; j <= 12; j++)
          {
            this.mesh.chunkVisible("Bomb50Kg" + j, false);
          }
        }

        if (aircraft().thisWeaponsName.startsWith("6"))
        {
          for (j = 1; j < i + 1; j++)
          {
            this.mesh.chunkVisible("Bomb100Kg" + j, true);
          }
          for (j = i + 1; j <= 6; j++)
          {
            this.mesh.chunkVisible("Bomb100Kg" + j, false);
          }
        }

        if (aircraft().thisWeaponsName.startsWith("5"))
        {
          for (j = 1; j < i + 1; j++)
          {
            this.mesh.chunkVisible("Bomb250Kg" + j, true);
          }
          for (j = i + 1; j <= 5; j++)
          {
            this.mesh.chunkVisible("Bomb250Kg" + j, false);
          }
        }

        if (aircraft().thisWeaponsName.startsWith("2"))
        {
          if (i == 2)
          {
            this.mesh.chunkVisible("Bomb500Kg1", true);
            this.mesh.chunkVisible("Bomb500Kg2", true);
          }
          if (i == 1)
          {
            this.mesh.chunkVisible("Bomb500Kg1", true);
            this.mesh.chunkVisible("Bomb500Kg2", false);
          }
          if (i == 0)
          {
            this.mesh.chunkVisible("Bomb500Kg1", false);
            this.mesh.chunkVisible("Bomb500Kg2", false);
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

      if (aircraft().thisWeaponsName.startsWith("12x1"))
      {
        this.mesh.chunkVisible("Bomb100Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.mesh.chunkSetLocate("Bomb100Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.4F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }

      if (aircraft().thisWeaponsName.startsWith("12x5"))
      {
        this.mesh.chunkVisible("Bomb50Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.mesh.chunkSetLocate("Bomb50Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.4F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }

      if (aircraft().thisWeaponsName.startsWith("6"))
      {
        this.mesh.chunkVisible("Bomb100Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.mesh.chunkSetLocate("Bomb100Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.4F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }
      if (aircraft().thisWeaponsName.startsWith("5"))
      {
        this.mesh.chunkVisible("Bomb250Kg" + this.bombToDrop, true);
        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.mesh.chunkSetLocate("Bomb250Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.5F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }
      if (aircraft().thisWeaponsName.startsWith("2"))
      {
        this.mesh.chunkVisible("Bomb500Kg" + this.bombToDrop, true);

        resetYPRmodifier();
        Cockpit.xyz[2] = (-this.dropTime);
        this.mesh.chunkSetLocate("Bomb500Kg" + this.bombToDrop, Cockpit.xyz, Cockpit.ypr);
        if (this.dropTime >= 0.5F)
        {
          this.bombToDrop = 0;
          this.dropTime = 0.0F;
        }
      }
    }
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    reflectPlaneToModel();

    this.mesh.chunkSetAngles("Zturret3A", 0.0F, -this.fm.turret[2].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Zturret3B", 0.0F, this.fm.turret[2].tu[1], 0.0F);

    this.mesh.chunkSetAngles("Zturret2A", 0.0F, -this.fm.turret[1].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Zturret2B", 0.0F, this.fm.turret[1].tu[1], 0.0F);

    float f1 = this.fm.CT.getCockpitDoor();
    aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", false);

    float f2 = -28.0F * f1;
    float f3 = cvt(f1, 0.0F, 1.0F, 0.0F, -20.0F);
    float f4 = cvt(f1, 0.5F, 1.0F, 0.0F, -15.0F);

    this.mesh.chunkSetAngles("Tur1_DoorL_Int_D0", -f3, -f2, -f4);
    this.mesh.chunkSetAngles("Tur1_DoorR_Int_D0", f3, f2, -f4);

    aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);

    float f5 = this.fm.CT.getCockpitDoor();
    if (f5 < 0.99F)
    {
      if (!this.mesh.isChunkVisible("Tur2_DoorR_Int_D0"))
      {
        this.mesh.chunkVisible("Tur2_DoorR_Int_D0", true);
        this.mesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);
        this.mesh.chunkVisible("Tur2_DoorL_Int_D0", true);
      }

      f2 = 13.8F * f5;
      this.mesh.chunkSetAngles("Tur2_Door1_Int_D0", 0.0F, -f2, 0.0F);
      f2 = 8.8F * f5;
      this.mesh.chunkSetAngles("Tur2_Door2_Int_D0", 0.0F, -f2, 0.0F);
      f2 = 3.1F * f5;
      this.mesh.chunkSetAngles("Tur2_Door3_Int_D0", 0.0F, -f2, 0.0F);
      f2 = 10.0F * f5;
      this.mesh.chunkSetAngles("Tur2_DoorL_Int_D0", 0.0F, -f2, 0.0F);
      this.mesh.chunkSetAngles("Tur2_DoorR_Int_D0", 0.0F, f2, 0.0F);
    }
    else
    {
      this.mesh.chunkVisible("Tur2_DoorR_Int_D0", false);
      this.mesh.chunkVisible("Tur2_DoorR_open_Int_D0", true);
      this.mesh.chunkVisible("Tur2_DoorL_Int_D0", false);

      this.mesh.chunkSetAngles("Tur2_Door1_Int_D0", 0.0F, -13.8F, 0.0F);
      this.mesh.chunkSetAngles("Tur2_Door2_Int_D0", 0.0F, -8.8F, 0.0F);
      this.mesh.chunkSetAngles("Tur2_Door3_Int_D0", 0.0F, -3.1F, 0.0F);
    }

    drawBombsInt();
    drawFallingBomb(paramFloat);

    float f6 = ((SM79)this.fm.actor).bayDoorAngle;

    if (aircraft().thisWeaponsName.startsWith("12"))
    {
      this.mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever07", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever08", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever09", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever10", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever11", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever12", 0.0F, 55.0F * f6, 0.0F);
    }
    if (aircraft().thisWeaponsName.startsWith("6"))
    {
      this.mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f6, 0.0F);
    }
    if (aircraft().thisWeaponsName.startsWith("5"))
    {
      this.mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f6, 0.0F);
    }

    if (aircraft().thisWeaponsName.startsWith("2"))
    {
      this.mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever07", 0.0F, 55.0F * f6, 0.0F);
      this.mesh.chunkVisible("Bridge1", true);
      this.mesh.chunkVisible("Bridge2", true);
    }

    if (aircraft().thisWeaponsName.endsWith("drop"))
    {
      int i = 0;
      int j;
      if (this.fm.CT.Weapons[3] != null)
      {
        for (j = 0; j < this.fm.CT.Weapons[3].length; j++)
        {
          if (this.fm.CT.Weapons[3][j] != null) {
            i += this.fm.CT.Weapons[3][j].countBullets();
          }
        }
      }
      if (aircraft().thisWeaponsName.startsWith("12"))
      {
        for (j = 1; j <= 12 - i; j++) {
          if (j < 10)
            this.mesh.chunkSetAngles("Typewriter_Key0" + j, 0.0F, 25.0F, 0.0F);
          else
            this.mesh.chunkSetAngles("Typewriter_Key" + j, 0.0F, 25.0F, 0.0F);
        }
      }
      if (aircraft().thisWeaponsName.startsWith("6"))
      {
        for (j = 1; j <= 6 - i; j++) {
          this.mesh.chunkSetAngles("Typewriter_Key0" + j, 0.0F, 25.0F, 0.0F);
        }
      }
      if (aircraft().thisWeaponsName.startsWith("5"))
      {
        for (j = 1; j <= 5 - i; j++) {
          this.mesh.chunkSetAngles("Typewriter_Key0" + j, 0.0F, 25.0F, 0.0F);
        }
      }
      if (aircraft().thisWeaponsName.startsWith("2"))
      {
        if (i == 0)
        {
          this.mesh.chunkSetAngles("Typewriter_Key02", 0.0F, 25.0F, 0.0F);
          this.mesh.chunkSetAngles("Typewriter_Key03", 0.0F, 25.0F, 0.0F);
          this.mesh.chunkSetAngles("Typewriter_Key06", 0.0F, 25.0F, 0.0F);
          this.mesh.chunkSetAngles("Typewriter_Key07", 0.0F, 25.0F, 0.0F);
        }
        if (i == 1)
        {
          this.mesh.chunkSetAngles("Typewriter_Key02", 0.0F, 25.0F, 0.0F);
          this.mesh.chunkSetAngles("Typewriter_Key03", 0.0F, 25.0F, 0.0F);
        }
      }
    }
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("Zturret4A", 0.0F, paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("Zturret4B", 0.0F, paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode())
      return;
    if (!aiTurret().bIsOperable)
    {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }

    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();

    if (f1 < -45.0F)
      f1 = -45.0F;
    if (f1 > 60.0F)
      f1 = 60.0F;
    if (f2 > 35.0F)
      f2 = 35.0F;
    if (f2 < -35.0F) {
      f2 = -35.0F;
    }

    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick()
  {
    if (!isRealMode())
      return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.bGunFire = false;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    if (this.bGunFire)
    {
      if (this.hook1 == null)
        this.hook1 = new HookNamed(aircraft(), "_12,7_00");
      doHitMasterAircraft(aircraft(), this.hook1, "_12,7_00");
      if (this.iCocking > 0)
        this.iCocking = 0;
      else
        this.iCocking = 1;
    }
    else {
      this.iCocking = 0;
    }

    if (this.emitter != null)
    {
      boolean bool = this.emitter.countBullets() % 2 == 0;
      this.mesh.chunkVisible("ZTurret1B-Bullet0", bool);
      this.mesh.chunkVisible("ZTurret1B-Bullet1", !bool);
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode())
      return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.bGunFire = false;
    else
      this.bGunFire = paramBoolean;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
  }

  public CockpitSM79_RGunner()
  {
    super("3DO/Cockpit/SM79Bombardier/hier_RGun.him", "he111_gunner");
    this.hook1 = null;
    this.iCocking = 0;
    this.iOldVisDrums = 2;
    this.iNewVisDrums = 2;
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Glass2"));
    this.mesh.materialReplace("Glass2", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot1"));
    this.mesh.materialReplace("Pilot1", localMat);
  }

  protected void reflectPlaneToModel()
  {
  }

  static
  {
    Property.set(CockpitSM79_RGunner.class, "aiTuretNum", 3);
    Property.set(CockpitSM79_RGunner.class, "weaponControlNum", 13);
    Property.set(CockpitSM79_RGunner.class, "astatePilotIndx", 5);
  }
}