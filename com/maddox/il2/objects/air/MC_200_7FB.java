package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class MC_200_7FB extends MC_200xyz
{
  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    if (paramInt1 == 19)
      this.FM.Gears.hitCentreGear();
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "M.C.200");

    Property.set(localClass, "meshName_it", "3DO/Plane/MC-200_VII_FB(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeFCSPar02());
    Property.set(localClass, "meshName", "3DO/Plane/MC-200_VII_FB(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/MC-200.fmd");
    Property.set(localClass, "cockpitClass", CockpitMC_200_VII_new.class);
    Property.set(localClass, "LOSElevation", 0.9119F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127si 350", "MGunBredaSAFAT127si 350", null, null });

    Aircraft.weaponsRegister(localClass, "2x50", new String[] { "MGunBredaSAFAT127si 350", "MGunBredaSAFAT127si 350", "BombGun50kg 1", "BombGun50kg 1" });

    Aircraft.weaponsRegister(localClass, "2x100", new String[] { "MGunBredaSAFAT127si 350", "MGunBredaSAFAT127si 350", "BombGun100kg 1", "BombGun100kg 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}