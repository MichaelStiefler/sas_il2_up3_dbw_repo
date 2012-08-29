package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class PE_3SERIES1 extends PE_2
  implements TypeFighter
{
  private long tme = 0L;
  private float tpos = 0.0F;
  private float tlim = 0.0F;

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Turtle_D0", 0.0F, 0.0F, -2.0F);
    super.update(paramFloat);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -1.0F) { f2 = -1.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false; break;
    case 1:
      if (f1 < -2.0F) { f1 = -2.0F; bool = false; }
      if (f1 > 2.0F) { f1 = 2.0F; bool = false; }
      if (f2 < -2.0F) { f2 = -2.0F; bool = false; }
      if (f2 <= 2.0F) break; f2 = 2.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
    }
  }

  static
  {
    Class localClass = PE_3SERIES1.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Pe-3");
    Property.set(localClass, "meshName", "3DO/Plane/Pe-3/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Pe-3series1.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitPE3_1.class, CockpitPE3_1_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.76315F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 11, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_BombSpawn05", "_BombSpawn06" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunUBk 250", "MGunUBk 350", "MGunShKASt 750", "MGunShKASki 750", null, null });

    Aircraft.weaponsRegister(localClass, "2fab50", new String[] { "MGunUBk 250", "MGunUBk 350", "MGunShKASt 750", "MGunShKASki 750", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "2fab100", new String[] { "MGunUBk 250", "MGunUBk 350", "MGunShKASt 750", "MGunShKASki 750", "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}