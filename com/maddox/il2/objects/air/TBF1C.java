package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class TBF1C extends TBF
{
  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -33.0F) { f1 = -33.0F; bool = false; }
      if (f1 > 33.0F) { f1 = 33.0F; bool = false; }
      if (f2 < -3.0F) { f2 = -3.0F; bool = false; }
      if (f2 <= 62.0F) break; f2 = 62.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  static
  {
    Class localClass = TBF1C.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "TBF");
    Property.set(localClass, "meshName", "3DO/Plane/TBF-1C(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());
    Property.set(localClass, "meshName_us", "3DO/Plane/TBF-1C(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar01());
    Property.set(localClass, "meshName_gb", "3DO/Plane/TBF-1C(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeBCSPar01());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/TBF-1C.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 11, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8xhvargp", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8xhvarap", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4x100", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1" });

    Aircraft.weaponsRegister(localClass, "4x1008xhvargp", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1" });

    Aircraft.weaponsRegister(localClass, "4x1008xhvarap", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1" });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x2508xhvargp", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x2508xhvarap", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4x250", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1" });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x5008xhvargp", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x5008xhvarap", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4x500", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1" });

    Aircraft.weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x1600", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, "BombGun1600lbs", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x2000", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, "BombGun2000lbs", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1xmk13", new String[] { "MGunBrowning50kWF 600", "MGunBrowning50kWF 600", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, "BombGunTorpMk13", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}