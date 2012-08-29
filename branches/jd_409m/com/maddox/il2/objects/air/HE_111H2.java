package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class HE_111H2 extends HE_111
{
  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay3_D0", 0.0F, 74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay5_D0", 0.0F, 74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay6_D0", 0.0F, -94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay7_D0", 0.0F, 74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay8_D0", 0.0F, -94.0F * paramFloat, 0.0F);

    hierMesh().chunkSetAngles("Bay9_D0", 0.0F, -74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay10_D0", 0.0F, 94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay11_D0", 0.0F, -74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay12_D0", 0.0F, 94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay13_D0", 0.0F, -74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay14_D0", 0.0F, 94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay15_D0", 0.0F, -74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay16_D0", 0.0F, 94.0F * paramFloat, 0.0F);
  }

  static
  {
    Class localClass = HE_111H2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "He-111");
    Property.set(localClass, "meshName", "3do/plane/He-111H-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1939.5F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/He-111H-2.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitHE_111H2.class, CockpitHE_111H2_Bombardier.class, CockpitHE_111H2_NGunner.class, CockpitHE_111H2_TGunner.class, CockpitHE_111H2_BGunner.class, CockpitHE_111H2_LGunner.class, CockpitHE_111H2_RGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "30xSC50", new String[] { "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC50 3", "BombGunSC50 3", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4" });

    Aircraft.weaponsRegister(localClass, "20xSC70", new String[] { "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 3", "BombGunSC70 3" });

    Aircraft.weaponsRegister(localClass, "2SC250A", new String[] { "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", null, null, "BombGunSC50 2", "BombGunSC50 2", "BombGunSC50 2", "BombGunSC50 2" });

    Aircraft.weaponsRegister(localClass, "2SC250B", new String[] { "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", null, null, "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 2" });

    Aircraft.weaponsRegister(localClass, "4xSC250", new String[] { "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}