package com.maddox.il2.objects.air;

import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class PE_3BIS extends PE_2
  implements TypeFighter
{
  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -110.0F) { f1 = -110.0F; bool = false; }
      if (f1 > 88.0F) { f1 = 88.0F; bool = false; }
      if (f2 < -1.0F) { f2 = -1.0F; bool = false; }
      if (f2 <= 55.0F) break; f2 = 55.0F; bool = false; break;
    case 1:
      if (f1 < -2.0F) { f1 = -2.0F; bool = false; }
      if (f1 > 2.0F) { f1 = 2.0F; bool = false; }
      if (f2 < -2.0F) { f2 = -2.0F; bool = false; }
      if (f2 <= 2.0F) break; f2 = 2.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      this.FM.turret[1].setHealth(paramFloat);
    }
  }

  static
  {
    Class localClass = PE_3BIS.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Pe-3");
    Property.set(localClass, "meshName", "3DO/Plane/Pe-3bis/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1941.5F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Pe-3bis.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitPE3bis.class, CockpitPE3bis_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.76315F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 10, 11, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_CANNON03", "_CANNON04", "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn05", "_BombSpawn06" });

    weaponsRegister(localClass, "default", new String[] { "MGunUBk 250", "MGunUBk 250", "MGunShVAKk 140", "MGunShVAKk 140", "MGunUBt 200", "MGunShKASki 750", null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab50", new String[] { "MGunUBk 250", "MGunUBk 250", "MGunShVAKk 140", "MGunShVAKk 140", "MGunUBt 200", "MGunShKASki 750", null, null, null, null, "BombGunFAB50", "BombGunFAB50" });

    weaponsRegister(localClass, "2fab100", new String[] { "MGunUBk 250", "MGunUBk 250", "MGunShVAKk 140", "MGunShVAKk 140", "MGunUBt 200", "MGunShKASki 750", null, null, null, null, "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "2fab1002fab50", new String[] { "MGunUBk 250", "MGunUBk 250", "MGunShVAKk 140", "MGunShVAKk 140", "MGunUBt 200", "MGunShKASki 750", "BombGunFAB50", "BombGunFAB50", null, null, "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}