package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public class HS_129B3Wa extends HS_129
{
  private int phase;
  private float disp;
  private int oldbullets;
  private BulletEmitter g1;

  public HS_129B3Wa()
  {
    this.phase = 0;
    this.disp = 0.0F;
    this.oldbullets = -1;
    this.g1 = null;
  }

  public void onAircraftLoaded()
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null)
      this.g1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0];
  }

  public void update(float paramFloat)
  {
    if (this.g1 != null) {
      switch (this.phase)
      {
      default:
        break;
      case 0:
        if ((!this.g1.isShots()) || (this.oldbullets == this.g1.countBullets()))
          break;
        this.oldbullets = this.g1.countBullets();
        this.phase += 1;
        hierMesh().chunkVisible("Shell_D0", true);
        this.disp = 0.0F; break;
      case 1:
        this.disp += 4.5F * paramFloat;
        resetYPRmodifier();
        Aircraft.xyz[0] = this.disp;
        hierMesh().chunkSetLocate("Barrel_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetLocate("Shell_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetLocate("Sled_D0", Aircraft.xyz, Aircraft.ypr);
        if (this.disp < 0.75F) break;
        this.phase += 1; break;
      case 2:
        Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Shell_D0"));
        Eff3DActor.New(localWreckage, null, null, 1.0F, Wreckage.SMOKE, 3.0F);
        Vector3d localVector3d = new Vector3d();
        localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Vwld);
        localWreckage.setSpeed(localVector3d);
        hierMesh().chunkVisible("Shell_D0", false);
        this.phase += 1;
        break;
      case 3:
        this.disp -= 0.375F * paramFloat;
        resetYPRmodifier();
        Aircraft.xyz[0] = this.disp;
        hierMesh().chunkSetLocate("Barrel_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetLocate("Shell_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetLocate("Sled_D0", Aircraft.xyz, Aircraft.ypr);
        if (this.disp > 0.0F)
          break;
        this.disp = 0.0F;
        Aircraft.xyz[0] = this.disp;
        hierMesh().chunkSetLocate("Barrel_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetLocate("Shell_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetLocate("Sled_D0", Aircraft.xyz, Aircraft.ypr);
        this.phase += 1; break;
      case 4:
        this.phase = 0;
      }
    }
    super.update(paramFloat);
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Class localClass = HS_129B3Wa.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Hs-129");
    Property.set(localClass, "meshName", "3do/plane/Hs-129B-3Wa/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1943.9F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Hs-129B-2.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitHS129.class });

    Property.set(localClass, "LOSElevation", 0.7394F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_HEAVYCANNON01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG15120s 125", "MGunMG15120s 125", "MGunPaK40 12" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null });
  }
}