package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.il2.objects.weapons.MGunBK75;
import com.maddox.il2.objects.weapons.PylonHS129BK75;
import com.maddox.rts.Property;

public class HS_129B3Wa extends HS_129
{
  private int phase = 0;
  private float disp = 0.0F;
  private int oldbullets = -1;
  private BulletEmitter g1 = null;
  private boolean BK75dropped = false;
  private float BK75stabilizingMultiplier = 2.0F;

  public void auxPressed(int paramInt)
  {
    if (paramInt == 1)
    {
      this.FM.CT.dropExternalStores(true);
    }
    super.auxPressed(paramInt);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if ((!this.BK75dropped) && (!this.FM.isPlayers()) && ((isNetMaster()) || (!isNet())) && (!this.FM.AS.isPilotDead(0)) && (!isNetPlayer()) && (this.FM.AS.astateBailoutStep == 0))
    {
      if ((this.FM.AS.astateEngineStates[0] > 2) || (this.FM.AS.astateEngineStates[1] > 2) || (this.FM.EI.engines[0].getReadyness() < 0.5F) || (this.FM.EI.engines[1].getReadyness() < 0.5F) || (((Maneuver)this.FM).get_maneuver() == 49) || (this.FM.isReadyToReturn()))
      {
        this.FM.CT.dropExternalStores(true);
      }
    }
  }

  public boolean dropExternalStores(boolean paramBoolean)
  {
    if (!this.BK75dropped)
    {
      if ((paramBoolean) && ((this.FM.getSpeedKMH() > 342.0F) || (this.FM.getSpeedKMH() < 95.0F)))
        return false;
      sfxHit(1.0F, new Point3d(0.0D, 0.0D, -1.0D));
      this.BK75dropped = true;
      ((Gun)this.g1).destroy();
      this.FM.Sq.liftKeel /= this.BK75stabilizingMultiplier;
      if (World.getPlayerAircraft() == this) {
        World.cur().scoreCounter.playerDroppedExternalStores(100);
      }
      for (int i = 0; i < this.FM.CT.Weapons.length; i++)
      {
        localObject = this.FM.CT.Weapons[i];
        if (localObject == null)
          continue;
        for (int j = 0; j < localObject.length; j++)
        {
          GunEmpty localGunEmpty = localObject[j];
          if ((!(localGunEmpty instanceof MGunBK75)) && (!(localGunEmpty instanceof PylonHS129BK75)))
            continue;
          this.FM.CT.Weapons[i][j] = GunEmpty.get();
          localGunEmpty = GunEmpty.get();
        }

      }

      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.FM.Vwld);
      localVector3d.z -= 8.0D;

      hierMesh().chunkVisible("Hole_d0", true);
      Object localObject = new Wreckage(this, hierMesh().chunkFind("BK75Wreck_D0"));
      ((Wreckage)localObject).setSpeed(localVector3d);
      ((Wreckage)localObject).collide(true);
      hierMesh().chunkSetAngles("BK75Pod_D0", 0.0F, 180.0F, 0.0F);
      hierMesh().chunkSetAngles("Barrel_D0", 0.0F, 145.0F, 0.0F);
      hierMesh().chunkVisible("BK75Pod_D0", false);
      hierMesh().chunkVisible("Barrel_D0", false);
      hierMesh().chunkVisible("sled_d0", false);
      hierMesh().chunkVisible("shell_d0", false);
      return true;
    }

    return false;
  }

  public void onAircraftLoaded()
  {
    if (this.FM.CT.Weapons[1] != null) {
      this.g1 = this.FM.CT.Weapons[1][0];
    }
    this.FM.Sq.liftKeel *= this.BK75stabilizingMultiplier;
  }

  public void update(float paramFloat) {
    if (this.g1 != null) {
      switch (this.phase) {
      case 0:
        if ((!this.g1.isShots()) || (this.oldbullets == this.g1.countBullets())) break;
        this.oldbullets = this.g1.countBullets();
        this.phase += 1;
        hierMesh().chunkVisible("Shell_D0", true);
        this.disp = 0.0F; break;
      case 1:
        this.disp += 6.0F * paramFloat;
        resetYPRmodifier();
        Aircraft.xyz[0] = this.disp;
        hierMesh().chunkSetLocate("Barrel_D0", Aircraft.xyz, Aircraft.ypr);

        Aircraft.xyz[0] = (this.disp * 1.5F);
        hierMesh().chunkSetLocate("Sled_D0", Aircraft.xyz, Aircraft.ypr);

        if (this.disp < 0.75F) break;
        this.phase += 1; break;
      case 2:
        Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Shell_D0"));

        Eff3DActor.New(localWreckage, null, null, 1.0F, Wreckage.SMOKE, 3.0F);

        Vector3d localVector3d = new Vector3d();
        localVector3d.set(this.FM.Vwld);
        localVector3d.z -= 8.0D;
        localWreckage.setSpeed(localVector3d);
        hierMesh().chunkVisible("Shell_D0", false);
        this.phase += 1;
        break;
      case 3:
        this.disp -= 0.8F * paramFloat;
        resetYPRmodifier();
        Aircraft.xyz[0] = this.disp;
        hierMesh().chunkSetLocate("Barrel_D0", Aircraft.xyz, Aircraft.ypr);

        Aircraft.xyz[0] = (this.disp * 1.5F);
        hierMesh().chunkSetLocate("Sled_D0", Aircraft.xyz, Aircraft.ypr);
        if (this.disp > 0.0F)
          break;
        this.disp = 0.0F;
        Aircraft.xyz[0] = this.disp;
        hierMesh().chunkSetLocate("Barrel_D0", Aircraft.xyz, Aircraft.ypr);

        hierMesh().chunkSetLocate("Sled_D0", Aircraft.xyz, Aircraft.ypr);
        this.phase += 1; break;
      case 4:
        this.phase = 0;
      }
    }

    super.update(paramFloat);
  }

  static
  {
    Class localClass = HS_129B3Wa.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Hs-129");
    Property.set(localClass, "meshName", "3do/plane/Hs-129B-3Wa/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1943.9F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Hs-129B-3.fmd");
    Property.set(localClass, "cockpitClass", CockpitHS_129B3.class);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_HEAVYCANNON01", "_ExternalDev01" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG131ki 500", "MGunMG131ki 500", "MGunBK75 13", "PylonHS129BK75" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}