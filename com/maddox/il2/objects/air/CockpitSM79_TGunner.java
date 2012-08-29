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
        if (this.dropTime >= 0.6F)
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
        if (this.dropTime >= 0.6F)
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
        if (this.dropTime >= 0.6F)
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
        if (this.dropTime >= 0.6F)
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

      ((SM79)this.fm.actor).bPitUnfocused = false;

      this.mesh.chunkVisible("Tur2_DoorR_Int_D0", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.mesh.chunkVisible("Tur2_DoorR_open_Int_D0", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.mesh.chunkVisible("Tur2_DoorL_Int_D0", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));

      this.mesh.chunkVisible("Tur2_Door1_Int_D0", true);
      this.mesh.chunkVisible("Tur2_Door2_Int_D0", true);
      this.mesh.chunkVisible("Tur2_Door3_Int_D0", true);

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

      if (aircraft().thisWeaponsName.startsWith("12"))
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

      for (int i = 1; i <= 12; i++) {
        aircraft().hierMesh().chunkVisible("Bomb100Kg" + i + "_D0", false);
      }
      for (i = 1; i <= 5; i++) {
        aircraft().hierMesh().chunkVisible("Bomb250Kg" + i + "_D0", false);
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
      ((SM79)this.fm.actor).bPitUnfocused = true;

      boolean bool = aircraft().isChunkAnyDamageVisible("Tail1_D");

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

      aircraft().hierMesh().chunkVisible("Interior2_D0", bool);
      aircraft().hierMesh().chunkVisible("Interior1_D0", true);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);

      this.mesh.chunkVisible("Tur2_Door1_Int_D0", false);
      this.mesh.chunkVisible("Tur2_Door2_Int_D0", false);
      this.mesh.chunkVisible("Tur2_Door3_Int_D0", false);

      this.mesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);

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
      int i;
      if (aircraft().thisWeaponsName.startsWith("12"))
      {
        for (i = 1; i <= 12; i++)
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

    float f1 = this.fm.CT.getCockpitDoor();
    aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", false);

    this.mesh.chunkSetAngles("TurDoorLever", 0.0F, -40.0F * (this.pictLever = 0.89F * this.pictLever + 0.11F * this.fm.CT.cockpitDoorControl), 0.0F);

    if (f1 < 0.99F)
    {
      if (!this.mesh.isChunkVisible("Tur2_DoorR_Int_D0"))
      {
        this.mesh.chunkVisible("Tur2_DoorR_Int_D0", true);
        this.mesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);
        this.mesh.chunkVisible("Tur2_DoorL_Int_D0", true);
      }

      f2 = 13.8F * f1;
      this.mesh.chunkSetAngles("Tur2_Door1_Int_D0", 0.0F, -f2, 0.0F);
      f2 = 8.8F * f1;
      this.mesh.chunkSetAngles("Tur2_Door2_Int_D0", 0.0F, -f2, 0.0F);
      f2 = 3.1F * f1;
      this.mesh.chunkSetAngles("Tur2_Door3_Int_D0", 0.0F, -f2, 0.0F);

      f2 = 15.0F * f1;

      float f3 = cvt(f1, 0.2F, 1.0F, 0.0F, -16.0F);
      float f4 = cvt(f1, 0.4F, 1.0F, 0.0F, 7.5F);

      this.mesh.chunkSetAngles("Tur2_DoorL_Int_D0", -f3, -f2, -f4);
      this.mesh.chunkSetAngles("Tur2_DoorR_Int_D0", f3, f2, -f4);
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

    float f2 = -20.0F * f1;
    this.mesh.chunkSetAngles("Tur1_DoorL_Int_D0", 0.0F, f2, 0.0F);
    this.mesh.chunkSetAngles("Tur1_DoorR_Int_D0", 0.0F, -f2, 0.0F);

    this.mesh.chunkSetAngles("Zturret3A", 0.0F, -this.fm.turret[2].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Zturret3B", 0.0F, this.fm.turret[2].tu[1], 0.0F);

    this.mesh.chunkSetAngles("Zturret2A", 0.0F, -this.fm.turret[1].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Zturret2B", 0.0F, this.fm.turret[1].tu[1], 0.0F);

    this.mesh.chunkSetAngles("Zturret4A", 0.0F, -this.fm.turret[3].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Zturret4B", 0.0F, this.fm.turret[3].tu[1], 0.0F);
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurretA", 0.0F, paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("TurretB", 0.0F, paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode())
      return;
    if ((!aiTurret().bIsOperable) || (this.fm.CT.getCockpitDoor() < 0.5F))
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
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.bGunFire = false;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    if (this.bGunFire)
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
    if (this.emitter != null)
    {
      boolean bool = this.emitter.countBullets() % 2 == 0;
      this.mesh.chunkVisible("TurretB-Bullet0", bool);
      this.mesh.chunkVisible("TurretB-Bullet1", !bool);
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

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.mesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
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