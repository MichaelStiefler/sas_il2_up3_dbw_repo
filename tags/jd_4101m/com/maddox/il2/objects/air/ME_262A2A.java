package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class ME_262A2A extends ME_262
  implements TypeStormovik
{
  static
  {
    Class localClass = ME_262A2A.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Me 262");
    Property.set(localClass, "meshName", "3DO/Plane/Me-262A-2a/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());

    Property.set(localClass, "yearService", 1944.1F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Me-262A-1a.fmd");
    Property.set(localClass, "cockpitClass", CockpitME_262.class);
    Property.set(localClass, "LOSElevation", 0.74615F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24" });

    weaponsRegister(localClass, "default", new String[] { "MGunMK108k 100", "MGunMK108k 100", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xSC250", new String[] { "MGunMK108k 100", "MGunMK108k 100", "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "24r4m", new String[] { "MGunMK108k 100", "MGunMK108k 100", null, null, "PylonMe262_R4M_Left", "PylonMe262_R4M_Right", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}