package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class HS_129B2 extends HS_129
{
  static
  {
    Class localClass = HS_129B2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Hs-129");
    Property.set(localClass, "meshName", "3do/plane/Hs-129B-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Hs-129B-2.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 1, 0, 9, 1, 1, 1, 1, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_HEAVYCANNON01", "_ExternalDev01", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "R2-1xMk101", new String[] { "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", "MGunMK103k 30", null, "PylonHS129MK101", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "R3-4xMG17", new String[] { "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, "PylonHS129MG17S", "MGunMG17k 250", "MGunMG17k 250", "MGunMG17k 250", "MGunMG17k 250", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "R4-1xSC250", new String[] { "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, null, null, null, null, null, "BombGunSC250", null, null, null });

    Aircraft.weaponsRegister(localClass, "R4-2xCS50", new String[] { "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, null, null, null, null, null, null, null, "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "R4-4xSC50", new String[] { "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, null, null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "Wa-1xBK37", new String[] { null, null, "MGunMG15120s 125", "MGunMG15120s 125", null, "MGunBK374Hs129 32", "PylonHS129BK37", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}