package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class R_5 extends R_5xyz
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "R-5");
    Property.set(localClass, "meshName", "3do/plane/R-5/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar08());
    Property.set(localClass, "yearService", 1931.0F);
    Property.set(localClass, "yearExpired", 1944.0F);
    Property.set(localClass, "FlightModel", "FlightModels/R-5.fmd");
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 10, 10, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05" });

    weaponsRegister(localClass, "default", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "Gunpods", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5GPodL 1", "PylonR5GPodR 1", null, null, null });

    weaponsRegister(localClass, "Gunpods+8x10+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "Gunpods+16x10+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "Gunpods+4x50+2x50", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "8x50+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "2x100+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "2x100+2x50+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}