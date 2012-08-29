package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class N1K1JA extends N1K
{
  public void update(float paramFloat)
  {
    super.update(paramFloat);

    float f = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f) > 0.01F) {
      this.flapps = f;
      for (int i = 1; i < 11; i++)
        hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -20.0F * f, 0.0F);
    }
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.FM.CT.Weapons[3] != null) {
      hierMesh().chunkVisible("RackL_D0", true);
      hierMesh().chunkVisible("RackR_D0", true);
    }
  }

  static
  {
    Class localClass = N1K1JA.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "N1K");
    Property.set(localClass, "meshName", "3DO/Plane/N1K1-Ja(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/N1K1-Ja(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/N1K1-J.fmd");

    weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 3, 3, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02" });

    weaponsRegister(localClass, "default", new String[] { "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", null, null, null, null });

    weaponsRegister(localClass, "1x400dt", new String[] { "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", null, null, "PylonN1K1PLN1", "FuelTankGun_TankN1K1" });

    weaponsRegister(localClass, "2x30", new String[] { "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "BombGun30kgJ 1", "BombGun30kgJ 1", null, null });

    weaponsRegister(localClass, "2x60", new String[] { "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}