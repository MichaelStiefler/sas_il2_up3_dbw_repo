package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class MOSQUITO6 extends MOSQUITO
  implements TypeFighter, TypeStormovik
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Mosquito");
    Property.set(localClass, "meshName", "3DO/Plane/Mosquito_FB_MkVI(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_gb", "3DO/Plane/Mosquito_FB_MkVI(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar06());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Mosquito-FBMkVI.fmd");
    Property.set(localClass, "cockpitClass", CockpitMosquito6.class);
    Property.set(localClass, "LOSElevation", 0.6731F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "extra", new String[] { "MGunBrowning303kipzl 780", "MGunBrowning303kipzl 780", "MGunBrowning303kipzl 780", "MGunBrowning303kipzl 780", "MGunHispanoMkIkpzl 175", "MGunHispanoMkIkpzl 175", "MGunHispanoMkIkpzl 175", "MGunHispanoMkIkpzl 175", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, "BombGun250lbsE 1", "BombGun250lbsE 1" });

    Aircraft.weaponsRegister(localClass, "4x250", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1" });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, "BombGun500lbsE 1", "BombGun500lbsE 1" });

    Aircraft.weaponsRegister(localClass, "4x500", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}