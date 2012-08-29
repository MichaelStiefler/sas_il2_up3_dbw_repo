package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class IL_4_DB3M extends IL_4
  implements TypeBomber
{
  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 11:
    case 19:
      hierMesh().chunkVisible("Wire_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = IL_4_DB3M.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "DB-3");
    Property.set(localClass, "meshName", "3DO/Plane/DB-3M/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/DB-3M.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "10fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "10fab100", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, "BombGunFAB100 5", "BombGunFAB100 5" });

    Aircraft.weaponsRegister(localClass, "3fab250", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", null, null });

    Aircraft.weaponsRegister(localClass, "3fab25010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "3fab25010fab100", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", "BombGunFAB100 5", "BombGunFAB100 5" });

    Aircraft.weaponsRegister(localClass, "1fab500", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab5002fab250", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB250", "BombGunFAB250", null, null });

    Aircraft.weaponsRegister(localClass, "3fab500", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB500", "BombGunFAB500", null, null });

    Aircraft.weaponsRegister(localClass, "3fab50010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB500", "BombGunFAB500", "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "1fab1000", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB1000", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab100010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB1000", "BombGunNull", null, null, "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}