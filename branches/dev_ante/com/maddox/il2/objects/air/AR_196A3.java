package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.rts.Property;

public class AR_196A3 extends AR_196
{
  private static Point3d tmpp = new Point3d();

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 2; j++)
        if (this.FM.Gears.clpGearEff[i][j] != null) {
          tmpp.set(this.FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
          tmpp.z = 0.01D;
          this.FM.Gears.clpGearEff[i][j].pos.setAbs(tmpp);
          this.FM.Gears.clpGearEff[i][j].pos.reset();
        }
  }

  static
  {
    Class localClass = AR_196A3.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "FlightModel", "FlightModels/Ar-196A-3.fmd");
    Property.set(localClass, "meshName", "3DO/Plane/Ar-196A-3/hier.him");
    Property.set(localClass, "iconFar_shortClassName", "Ar-196");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "yearService", 1938.5F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "cockpitClass", new Class[] { CockpitAR_196.class, CockpitAR_196_Gunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 1, 10, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01", "_CANNON02", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17si 500", "MGunMGFFki 60", "MGunMGFFki 60", "MGunMG15t 525", null, null });

    Aircraft.weaponsRegister(localClass, "2sc50", new String[] { "MGunMG17si 500", "MGunMGFFki 60", "MGunMGFFki 60", "MGunMG15t 525", "BombGunSC50 1", "BombGunSC50 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}