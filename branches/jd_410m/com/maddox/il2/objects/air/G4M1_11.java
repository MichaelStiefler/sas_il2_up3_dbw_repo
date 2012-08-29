package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class G4M1_11 extends G4M
  implements TypeBomber
{
  private float ftpos = 0.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.FM.CT.Weapons[3] != null) {
      hierMesh().chunkVisible("Bay1_D0", false);
      hierMesh().chunkVisible("Bay2_D0", false);
    }
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    float f1 = this.FM.turret[1].tu[0];
    float f2 = this.FM.turret[1].tu[1];

    f1 -= 360.0F;
    if ((Math.abs(f1) > 2.0F) || (Math.abs(f2) > 2.0F)) {
      float f3 = (float)Math.toDegrees(Math.atan2(f1, f2));
      this.ftpos = (0.8F * this.ftpos + 0.2F * f3);

      hierMesh().chunkSetAngles("Turret2E_D0", 0.0F, this.ftpos, 0.0F);
    }
  }

  static
  {
    Class localClass = G4M1_11.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "G4M");
    Property.set(localClass, "meshName", "3DO/Plane/G4M1-11(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "meshName_ja", "3DO/Plane/G4M1-11(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeBCSPar01());

    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/G4M1-11.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitG4M1_11.class, CockpitG4M1_11_Bombardier.class, CockpitG4M1_11_NGunner.class, CockpitG4M1_11_AGunner.class, CockpitG4M1_11_TGunner.class, CockpitG4M1_11_RGunner.class, CockpitG4M1_11_LGunner.class });

    Property.set(localClass, "LOSElevation", 1.4078F);

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null });

    weaponsRegister(localClass, "50x15", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun15kgJ 25", "BombGun15kgJ 25", null });

    weaponsRegister(localClass, "16x50", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun50kgJ 8", "BombGun50kgJ 8", null });

    weaponsRegister(localClass, "16x50inc", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun50kgIncJ 8", "BombGun50kgIncJ 8", null });

    weaponsRegister(localClass, "12x60", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun60kgJ 6", "BombGun60kgJ 6", null });

    weaponsRegister(localClass, "8x100", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun100kgJ 4", "BombGun100kgJ 4", null });

    weaponsRegister(localClass, "1x250", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun250kgJ 1" });

    weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun250kgJ 1", "BombGun250kgJ 1", null });

    weaponsRegister(localClass, "3x250", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun250kgJ 1", "BombGun250kgJ 2", null });

    weaponsRegister(localClass, "1x500", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500kgJ 1" });

    weaponsRegister(localClass, "1x5002x250", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500kgJ 1", "BombGun250kgJ 2", null });

    weaponsRegister(localClass, "1x600", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun600kgJ 1" });

    weaponsRegister(localClass, "1x800", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun800kgJ 1" });

    weaponsRegister(localClass, "1xtyp91", new String[] { "MGunBrowning303t 500", "MGunHo5t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGunTorpType91 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}