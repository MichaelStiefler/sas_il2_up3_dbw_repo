package com.maddox.il2.objects.air;

import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class HE_111H6 extends HE_111
{
  public void update(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[5].tMode == 2) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[5].tMode = 4;
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = HE_111H6.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "He-111");
    Property.set(localClass, "meshName", "3do/plane/He-111H-6/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/He-111H-6.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitHE_111H6.class, CockpitHE_111H6_Bombardier.class, CockpitHE_111H6_NGunner.class, CockpitHE_111H2_TGunner.class, CockpitHE_111H2_BGunner.class, CockpitHE_111H2_LGunner.class, CockpitHE_111H2_RGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 15, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xSD250", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500" });

    Aircraft.weaponsRegister(localClass, "4xSC500", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500" });

    Aircraft.weaponsRegister(localClass, "4xAB500", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunAB500", "BombGunAB500", "BombGunAB500", "BombGunAB500" });

    Aircraft.weaponsRegister(localClass, "2SC1000", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC1000", "BombGunSC1000", null, null });

    Aircraft.weaponsRegister(localClass, "2PC1600", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunPC1600", "BombGunPC1600", null, null });

    Aircraft.weaponsRegister(localClass, "2SC2000", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC2000", "BombGunSC2000", null, null });

    Aircraft.weaponsRegister(localClass, "2xTorp", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGun4512", "BombGun4512", null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}