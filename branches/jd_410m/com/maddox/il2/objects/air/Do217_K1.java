package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class Do217_K1 extends Do217
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
  }

  protected void moveBayDoor(float paramFloat)
  {
    int i;
    if (paramFloat < 0.02F)
    {
      hierMesh().chunkVisible("Bay_D0", true);
      for (i = 1; i <= 9; i++)
      {
        hierMesh().chunkVisible("BayL0" + i + "_D0", false);
        hierMesh().chunkVisible("BayR0" + i + "_D0", false);
      }
    }
    else
    {
      hierMesh().chunkVisible("Bay_D0", false);
      for (i = 1; i <= 9; i++)
      {
        hierMesh().chunkVisible("BayL0" + i + "_D0", true);
        hierMesh().chunkVisible("BayR0" + i + "_D0", true);
      }

      i = paramFloat < 0.8F ? 1 : 0;

      hierMesh().chunkVisible("BayL03_D0", i);
      hierMesh().chunkVisible("BayR03_D0", i);
      hierMesh().chunkVisible("BayL06_D0", i);
      hierMesh().chunkVisible("BayR06_D0", i);

      hierMesh().chunkSetAngles("BayL01_D0", 0.0F, Aircraft.cvt(paramFloat, 0.04F, 0.7F, 0.0F, 120.5F), 0.0F);
      hierMesh().chunkSetAngles("BayL04_D0", 0.0F, Aircraft.cvt(paramFloat, 0.04F, 0.7F, 0.0F, 120.5F), 0.0F);
      hierMesh().chunkSetAngles("BayR01_D0", 0.0F, Aircraft.cvt(paramFloat, 0.04F, 0.7F, 0.0F, -120.5F), 0.0F);
      hierMesh().chunkSetAngles("BayR04_D0", 0.0F, Aircraft.cvt(paramFloat, 0.04F, 0.7F, 0.0F, -120.5F), 0.0F);

      hierMesh().chunkSetAngles("BayL02_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.98F, 0.0F, -155.5F), 0.0F);
      hierMesh().chunkSetAngles("BayL05_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.98F, 0.0F, -155.5F), 0.0F);
      hierMesh().chunkSetAngles("BayR02_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.98F, 0.0F, 155.5F), 0.0F);
      hierMesh().chunkSetAngles("BayR05_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.98F, 0.0F, 155.5F), 0.0F);

      hierMesh().chunkSetAngles("BayL03_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.9F, 0.0F, -150.5F), 0.0F);
      hierMesh().chunkSetAngles("BayL06_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.9F, 0.0F, -150.5F), 0.0F);
      hierMesh().chunkSetAngles("BayR03_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.9F, 0.0F, 150.5F), 0.0F);
      hierMesh().chunkSetAngles("BayR06_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.9F, 0.0F, 150.5F), 0.0F);

      if (this.thisWeaponsName.endsWith("Torpedo"))
      {
        hierMesh().chunkVisible("BayL09_D0", i);
        hierMesh().chunkVisible("BayR09_D0", i);

        hierMesh().chunkSetAngles("BayL07_D0", 0.0F, Aircraft.cvt(paramFloat, 0.04F, 0.7F, 0.0F, 120.5F), 0.0F);
        hierMesh().chunkSetAngles("BayL08_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.98F, 0.0F, -155.5F), 0.0F);
        hierMesh().chunkSetAngles("BayL09_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.9F, 0.0F, -150.5F), 0.0F);

        hierMesh().chunkSetAngles("BayR07_D0", 0.0F, Aircraft.cvt(paramFloat, 0.04F, 0.7F, 0.0F, -120.5F), 0.0F);
        hierMesh().chunkSetAngles("BayR08_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.98F, 0.0F, 150.5F), 0.0F);
        hierMesh().chunkSetAngles("BayR09_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.9F, 0.0F, 150.5F), 0.0F);
      }
    }
  }

  protected void mydebug(String paramString)
  {
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Do-217");
    Property.set(localClass, "meshName", "3do/plane/Do217_K1/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Do217K-1.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN10", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn05" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "28xSC50", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC50 7", "BombGunSC50 7", "BombGunSC50 7", "BombGunSC50 7", null, null, null, null, null, null });

    weaponsRegister(localClass, "4xSC250", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", null, null, null, null, null, null });

    weaponsRegister(localClass, "4xSC500", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", null, null, null, null, null, null });

    weaponsRegister(localClass, "4xSD500", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSD500 1", "BombGunSD500 1", "BombGunSD500 1", "BombGunSD500 1", null, null, null, null, null, null });

    weaponsRegister(localClass, "2xSC1000", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, "BombGunSC1000 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC1000 1", null, null });

    weaponsRegister(localClass, "3xSD1000", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSD1000 1", "BombGunNull 1", "BombGunNull 1", "BombGunSD1000 1", null, "BombGunSD1000 1", null, null, null, null });

    weaponsRegister(localClass, "4xSD1000", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSD1000 1", "BombGunSD1000 1", "BombGunSD1000 1", "BombGunSD1000 1", null, null, null, null, null, null });

    weaponsRegister(localClass, "1xSC1800", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, null, null, "BombGunSC1800 1", null });

    weaponsRegister(localClass, "1xTorpedo", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, null, null, null, "BombGunTorpF5Bheavy 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}