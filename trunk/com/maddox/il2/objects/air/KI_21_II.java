package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class KI_21_II extends KI_21
  implements TypeBomber
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, cvt(paramFloat, 0.01F, 0.06F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, cvt(paramFloat, 0.01F, 0.06F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -38.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL11_D0", 0.0F, 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -45.0F));
    paramHierMesh.chunkSetAngles("GearL13_D0", 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -157.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, cvt(paramFloat, 0.3F, 0.35F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, cvt(paramFloat, 0.3F, 0.35F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, cvt(paramFloat, 0.34F, 0.99F, 0.0F, -38.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR11_D0", 0.0F, 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -45.0F));
    paramHierMesh.chunkSetAngles("GearR13_D0", 0.0F, cvt(paramFloat, 0.34F, 0.99F, 0.0F, -157.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 35.0F) { f1 = 35.0F; bool = false; }
      if (f2 < -25.0F) { f2 = -25.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false; break;
    case 1:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -5.0F) { f2 = -5.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 2:
      if (f1 < -20.0F) { f1 = -20.0F; bool = false; }
      if (f1 > 20.0F) { f1 = 20.0F; bool = false; }
      if (f2 < -89.0F) { f2 = -89.0F; bool = false; }
      if (f2 <= -12.0F) break; f2 = -12.0F; bool = false; break;
    case 3:
      if (f1 < -10.0F) { f1 = -10.0F; bool = false; }
      if (f1 > 10.0F) { f1 = 10.0F; bool = false; }
      if (f2 < -10.0F) { f2 = -10.0F; bool = false; }
      if (f2 <= 10.0F) break; f2 = 10.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    hierMesh().chunkSetAngles("Blister4_D0", 0.0F, -45.0F, 0.0F);
    hierMesh().chunkSetAngles("Blister5_D0", 0.0F, -45.0F, 0.0F);
    hierMesh().chunkSetAngles("Blister6_D0", 0.0F, -45.0F, 0.0F);
    hierMesh().chunkSetAngles("Turret3C_D0", -45.0F, 0.0F, 0.0F);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-21");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-21-II(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-21-II(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeBCSPar01());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-21-II.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "20x15", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 3", "BombGun15kgJ 3", "BombGun15kgJ 2" });

    weaponsRegister(localClass, "16x50", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 2", "BombGun50kgJ 2", "BombGun50kgJ 2", "BombGun50kgJ 3", "BombGun50kgJ 3" });

    weaponsRegister(localClass, "9x100", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ" });

    weaponsRegister(localClass, "4x250", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun250kgJ", "BombGun250kgJ", null, null, "BombGun250kgJ", "BombGun250kgJ", null, null, null });

    weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", null, "BombGun500kgJ", null, null, "BombGun500kgJ", null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}