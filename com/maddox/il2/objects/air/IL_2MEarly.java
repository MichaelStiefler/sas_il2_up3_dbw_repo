package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class IL_2MEarly extends IL_2
{
  static
  {
    Class localClass = IL_2MEarly.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "IL2");
    Property.set(localClass, "meshName", "3do/plane/Il-2MEarly(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());
    Property.set(localClass, "meshName_ru", "3do/plane/Il-2MEarly/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeBCSPar03());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Il-2MEarly.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitIL_2_1942.class, CockpitIL2_Gunner.class });

    Property.set(localClass, "LOSElevation", 0.81F);
    Property.set(localClass, "Handicap", 1.2F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 3, 3, 10 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_Cannon01", "_Cannon02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_BombSpawn01", "_BombSpawn02", "_MGUN03" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xRS82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xBRS82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xRS132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xBRS132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xM13", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "216xAJ-2", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, "BombGunAmpoule", "BombGunAmpoule", "BombGunAmpoule", "BombGunAmpoule", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "30xAO10", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, "BombGunAO10 7", "BombGunAO10 7", "BombGunAO10 8", "BombGunAO10 8", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "50xAO10", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, "BombGunAO10 12", "BombGunAO10 12", "BombGunAO10 13", "BombGunAO10 13", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xFAB50", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xFAB50_4xRS82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xFAB50_4xRS132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "6xFAB50", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "2fab100", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "2fab1004rs82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "2fab1004rs132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "2fab1004brs82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "2fab1004brs132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "2fab1004m13", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4xFAB100", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4fab1004rs82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4fab1004rs132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4fab1004brs82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4fab1004brs132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "4fab1004m13", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "6xFAB100", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "2xFAB250", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null, null, null, null, null, null, "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "2xVAP250", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonVAP250", "PylonVAP250", "BombGunPhBall", "BombGunPhBall", "MGunUBt 200" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}