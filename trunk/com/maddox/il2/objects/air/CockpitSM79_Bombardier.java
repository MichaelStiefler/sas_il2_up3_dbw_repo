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
  private Point3d cameraPoint = new Point3d();
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
  private boolean bNeedSetUp;
  private Hook hook1;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);

      ((SM79)this.fm.actor).bPitUnfocused = false;

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
      aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", false);

      aircraft().hierMesh().chunkVisible("Gambali_D0", false);

      for (int i = 1; i <= 12; i++) {
        aircraft().hierMesh().chunkVisible("Bomb100Kg" + i + "_D0", false);
      }
      for (i = 1; i <= 12; i++) {
        aircraft().hierMesh().chunkVisible("Bomb50Kg" + i + "_D0", false);
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

      aircraft().hierMesh().chunkVisible("Gambali_D0", bool);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", this.mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));

      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);

      this.mesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);
      int i;
      if ((aircraft().thisWeaponsName.startsWith("12")) || (aircraft().thisWeaponsName.startsWith("6")))
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
      localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
      localHookPilot.setSimpleUse(false);
      boolean bool = HotKeyEnv.isEnabled("aircraftView");
      HotKeyEnv.enable("PanView", bool);
      HotKeyEnv.enable("SnapView", bool);
      this.bEntered = false;
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
          mydebugcockpit("BOMB DROPPED!!! ");
          ((SM79)this.fm.actor).numBombsOld = i;
          this.bombToDrop = (i + 1);
          this.dropTime = 0.0F;

          mydebugcockpit("Bomb to drop: " + this.bombToDrop);
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
      mydebugcockpit("drawFallingBomb Drop time = " + this.dropTime);

      this.dropTime += 0.03F;

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
      localObject = new HookNamed(this.mesh, "CAMERAAIM");
      ((HookNamed)localObject).computePos(this, this.pos.getAbs(), localLoc);
      this.aAim = localLoc.getOrient().getAzimut();
      this.tAim = localLoc.getOrient().getTangage();
      this.kAim = localLoc.getOrient().getKren();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }

    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK1");
    Object localObject = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), (Loc)localObject);

    this.light1 = new LightPointActor(new LightPoint(), ((Loc)localObject).getPoint());
    this.light1.light.setColor(109.0F, 99.0F, 90.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    this.cockpitNightMats = new String[] { "Panel", "Needles" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
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

    float f1 = this.fm.CT.getCockpitDoor();
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
      f2 = 10.0F * f1;
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

    float f2 = interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat) * 0.072F;
    this.mesh.chunkSetAngles("Z_Altimeter", 0.0F, f2, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 460.0F, 0.0F, 23.0F), speedometerScale), 0.0F);

    this.mesh.chunkSetAngles("z_Hour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("z_Minute", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("z_Second", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("Zturret3A", 0.0F, -this.fm.turret[2].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Zturret3B", 0.0F, this.fm.turret[2].tu[1], 0.0F);

    this.mesh.chunkSetAngles("Zturret2A", 0.0F, -this.fm.turret[1].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Zturret2B", 0.0F, this.fm.turret[1].tu[1], 0.0F);

    this.mesh.chunkSetAngles("Zturret4A", 0.0F, -this.fm.turret[3].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Zturret4B", 0.0F, this.fm.turret[3].tu[1], 0.0F);

    this.mesh.chunkSetAngles("TurretA", 0.0F, -this.fm.turret[0].tu[0], 0.0F);
    this.mesh.chunkSetAngles("TurretB", 0.0F, this.fm.turret[0].tu[1], 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -28.0F, 28.0F), 0.0F);

    this.mesh.chunkSetAngles("ZRudderTrim", 0.0F, interp(this.setNew.rudderTrim, this.setOld.rudderTrim, paramFloat) * 360.0F, 0.0F);

    float f3 = this.fm.CT.getCockpitDoor();
    aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);

    float f4 = -28.0F * f3;
    float f5 = cvt(f3, 0.0F, 1.0F, 0.0F, -20.0F);
    float f6 = cvt(f3, 0.5F, 1.0F, 0.0F, -15.0F);

    this.mesh.chunkSetAngles("Tur1_DoorL_Int_D0", -f5, -f4, -f6);
    this.mesh.chunkSetAngles("Tur1_DoorR_Int_D0", f5, f4, -f6);

    if (!aircraft().thisWeaponsName.startsWith("1x"))
    {
      resetYPRmodifier();
      f7 = this.fm.Or.getPitch();
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
      f9 *= 0.85F;
      float f10 = f9 * 0.667F;

      Cockpit.xyz[0] = f9;
      this.mesh.chunkSetLocate("ZCursor1", Cockpit.xyz, Cockpit.ypr);

      Cockpit.xyz[0] = f10;
      this.mesh.chunkSetLocate("ZCursor2", Cockpit.xyz, Cockpit.ypr);

      this.mesh.chunkSetAngles("Cylinder", 0.0F, ((SM79)aircraft()).fSightCurSideslip, 0.0F);
    }

    drawBombsInt();

    drawFallingBomb(paramFloat);

    float f7 = ((SM79)this.fm.actor).bayDoorAngle;

    if (aircraft().thisWeaponsName.startsWith("12"))
    {
      this.mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever07", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever08", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever09", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever10", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever11", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever12", 0.0F, 55.0F * f7, 0.0F);
    }

    if (aircraft().thisWeaponsName.startsWith("6"))
    {
      this.mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f7, 0.0F);
    }

    if (aircraft().thisWeaponsName.startsWith("5"))
    {
      this.mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55.0F * f7, 0.0F);
    }

    if (aircraft().thisWeaponsName.startsWith("2"))
    {
      this.mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55.0F * f7, 0.0F);
      this.mesh.chunkSetAngles("TypewriterLever07", 0.0F, 55.0F * f7, 0.0F);
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
          this.mesh.chunkSetAngles("Typewriter_Key" + j, 0.0F, 25.0F, 0.0F);
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

  protected void mydebugcockpit(String paramString)
  {
    System.out.println(paramString);
  }

  public void reflectCockpitState()
  {
    if (this.fm.AS.astateCockpitState != 0)
    {
      if ((this.fm.AS.astateCockpitState & 0x10) != 0)
        this.mesh.chunkVisible("XGlassHoles3", true);
      if ((this.fm.AS.astateCockpitState & 0x20) != 0)
        this.mesh.chunkVisible("XGlassHoles3", true);
    }
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
    private double sinSideSlip;
    private double cosSideSlip;
    private double sin35 = 0.57572D;
    private double cos35 = 0.81765D;
    private double sideSlipRad;

    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitSM79_Bombardier.this.fm != null)
      {
        CockpitSM79_Bombardier.access$102(CockpitSM79_Bombardier.this, CockpitSM79_Bombardier.this.setOld);
        CockpitSM79_Bombardier.access$202(CockpitSM79_Bombardier.this, CockpitSM79_Bombardier.this.setNew);
        CockpitSM79_Bombardier.access$302(CockpitSM79_Bombardier.this, CockpitSM79_Bombardier.this.setTmp);
        CockpitSM79_Bombardier.this.setNew.altimeter = CockpitSM79_Bombardier.this.fm.getAltitude();
        CockpitSM79_Bombardier.this.setNew.rudderTrim = CockpitSM79_Bombardier.this.fm.CT.getTrimRudderControl();
        CockpitSM79_Bombardier.this.setNew.azimuth.setDeg(CockpitSM79_Bombardier.this.setOld.azimuth.getDeg(1.0F), 90.0F + CockpitSM79_Bombardier.this.fm.Or.azimut());
      }
      float f1 = ((SM79)CockpitSM79_Bombardier.this.aircraft()).fSightCurForwardAngle;
      float f2 = ((SM79)CockpitSM79_Bombardier.this.aircraft()).fSightCurSideslip;

      if (CockpitSM79_Bombardier.this.bEntered)
      {
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(CockpitSM79_Bombardier.this.aAim + f2, CockpitSM79_Bombardier.this.tAim + f1, 0.0F);

        this.sideSlipRad = Math.toRadians(f2);

        this.sinSideSlip = Math.sin(this.sideSlipRad);
        this.cosSideSlip = Math.cos(this.sideSlipRad);

        CockpitSM79_Bombardier.this.cameraPoint.x = (-3.8827D - 0.17085D * (this.sinSideSlip * this.cos35 + (1.0D - this.cosSideSlip) * this.sin35));
        CockpitSM79_Bombardier.this.cameraPoint.y = (-0.345D + 0.17085D * (this.sinSideSlip * this.sin35 + (1.0D - this.cosSideSlip) * this.cos35));

        CockpitSM79_Bombardier.this.cameraPoint.z = -0.4693D;

        localHookPilot.setAim(CockpitSM79_Bombardier.this.cameraPoint);
      }
      return true;
    }
  }
}