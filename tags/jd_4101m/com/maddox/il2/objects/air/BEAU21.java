package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class BEAU21 extends BEAU
  implements TypeFighter, TypeStormovik
{
  static
  {
    Class localClass = BEAU21.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Beaufighter");
    Property.set(localClass, "meshName", "3DO/Plane/BeaufighterMk21(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_gb", "3DO/Plane/BeaufighterMk21(AU)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1965.5F);

    Property.set(localClass, "FlightModel", "FlightModels/BeaufighterMk21.fmd");
    Property.set(localClass, "cockpitClass", CockpitBEAU21.class);
    Property.set(localClass, "LOSElevation", 0.7394F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 9, 9, 3, 3, 9, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev04", "_ExternalDev05", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "8xrock", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU" });

    weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "PylonBEAUPLN1", "PylonBEAUPLN1", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "PylonBEAUPLN1", "PylonBEAUPLN1", "BombGun500lbsE 1", "BombGun500lbsE 1", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1xtorp", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, null, null, "PylonBEAUPLN4", "BombGunTorpMk13", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1xtorp_late", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, null, null, "PylonBEAUPLN4", "BombGunTorpMk13late", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}