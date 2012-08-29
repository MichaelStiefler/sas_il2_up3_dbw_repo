package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.rts.Property;

public class P_11C extends P_11
{
  public static boolean bChangedPit = false;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject != null)
      for (int i = 0; i < arrayOfObject.length; i++)
        if ((arrayOfObject[i] instanceof Bomb)) {
          hierMesh().chunkVisible("RackL_D0", true);
          hierMesh().chunkVisible("RackR_D0", true);
        }
  }

  static
  {
    Class localClass = P_11C.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P.11");
    Property.set(localClass, "meshName", "3DO/Plane/P-11c(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());
    Property.set(localClass, "meshName_pl", "3DO/Plane/P-11c/hier.him");
    Property.set(localClass, "PaintScheme_pl", new PaintSchemeFCSPar01());
    Property.set(localClass, "meshName_ro", "3DO/Plane/P-11c(Romanian)/hier.him");
    Property.set(localClass, "PaintScheme_ro", new PaintSchemeFMPar00());
    Property.set(localClass, "originCountry", PaintScheme.countryPoland);

    Property.set(localClass, "yearService", 1934.0F);
    Property.set(localClass, "yearExpired", 1939.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-11c.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_11C.class);
    Property.set(localClass, "LOSElevation", 0.7956F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 750", "MGunBrowning303sipzl 750", "MGunBrowning303ki 350", "MGunBrowning303ki 350", null, null });

    weaponsRegister(localClass, "2puw125", new String[] { "MGunBrowning303sipzl 750", "MGunBrowning303sipzl 750", "MGunBrowning303ki 350", "MGunBrowning303ki 350", "BombGunPuW125", "BombGunPuW125" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}