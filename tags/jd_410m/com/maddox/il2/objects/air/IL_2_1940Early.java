package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class IL_2_1940Early extends IL_2
{
  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FlettnerL_D0", 0.0F, 40.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FlettnerR_D0", 0.0F, 40.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FlettnerRodL_D0", 0.0F, -37.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FlettnerRodR_D0", 0.0F, -37.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("WeightL_D0", 0.0F, 20.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("WeightR_D0", 0.0F, 20.0F * paramFloat, 0.0F);
  }

  static
  {
    Class localClass = IL_2_1940Early.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "IL2");
    Property.set(localClass, "meshName", "3do/plane/Il-2-1940Early(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_ru", "3do/plane/Il-2-1940Early/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeBCSPar02());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Il-2-1940.fmd");
    Property.set(localClass, "cockpitClass", CockpitIL_2_1940.class);
    Property.set(localClass, "LOSElevation", 0.81F);
    Property.set(localClass, "Handicap", 1.0F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_Cannon01", "_Cannon02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_BombSpawn01", "_BombSpawn02" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "8xRS82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "30xAO10", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunAO10 7", "BombGunAO10 7", "BombGunAO10 8", "BombGunAO10 8", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, null, null });

    weaponsRegister(localClass, "4xFAB50", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6xFAB50", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4xFAB100", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6xFAB100", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB250", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xVAP250", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonVAP250", "PylonVAP250", "BombGunPhBall", "BombGunPhBall" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}