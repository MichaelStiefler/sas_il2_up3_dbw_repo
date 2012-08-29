package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class G4M1_11 extends G4M
    implements TypeBomber
{

    public G4M1_11()
    {
        ftpos = 0.0F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.CT.Weapons[3] != null)
        {
            hierMesh().chunkVisible("Bay1_D0", false);
            hierMesh().chunkVisible("Bay2_D0", false);
        }
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.turret[1].tu[0];
        float f2 = FM.turret[1].tu[1];
        f1 -= 360F;
        if(java.lang.Math.abs(f1) > 2.0F || java.lang.Math.abs(f2) > 2.0F)
        {
            float f3 = (float)java.lang.Math.toDegrees(java.lang.Math.atan2(f1, f2));
            ftpos = 0.8F * ftpos + 0.2F * f3;
            hierMesh().chunkSetAngles("Turret2E_D0", 0.0F, ftpos, 0.0F);
        }
    }

    private float ftpos;

    static 
    {
        java.lang.Class class1 = G4M1_11.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "G4M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/G4M1-11(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/G4M1-11(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeBCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/G4M1-11.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitG4M1_11.class, CockpitG4M1_11_Bombardier.class, CockpitG4M1_11_NGunner.class, CockpitG4M1_11_AGunner.class, CockpitG4M1_11_TGunner.class, CockpitG4M1_11_RGunner.class, CockpitG4M1_11_LGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.4078F);
        G4M1_11.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 3, 3, 3
        });
        G4M1_11.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03"
        });
        G4M1_11.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null
        });
        G4M1_11.weaponsRegister(class1, "50x15", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun15kgJ 25", "BombGun15kgJ 25", null
        });
        G4M1_11.weaponsRegister(class1, "16x50", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun50kgJ 8", "BombGun50kgJ 8", null
        });
        G4M1_11.weaponsRegister(class1, "16x50inc", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun50kgIncJ 8", "BombGun50kgIncJ 8", null
        });
        G4M1_11.weaponsRegister(class1, "12x60", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun60kgJ 6", "BombGun60kgJ 6", null
        });
        G4M1_11.weaponsRegister(class1, "8x100", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun100kgJ 4", "BombGun100kgJ 4", null
        });
        G4M1_11.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun250kgJ 1"
        });
        G4M1_11.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun250kgJ 1", "BombGun250kgJ 1", null
        });
        G4M1_11.weaponsRegister(class1, "3x250", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun250kgJ 1", "BombGun250kgJ 2", null
        });
        G4M1_11.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500kgJ 1"
        });
        G4M1_11.weaponsRegister(class1, "1x5002x250", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500kgJ 1", "BombGun250kgJ 2", null
        });
        G4M1_11.weaponsRegister(class1, "1x600", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun600kgJ 1"
        });
        G4M1_11.weaponsRegister(class1, "1x800", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun800kgJ 1"
        });
        G4M1_11.weaponsRegister(class1, "1xtyp91", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGunTorpType91 1"
        });
        G4M1_11.weaponsRegister(class1, "1xtyp91_late", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunType99No1t 250", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGunTorpType91late 1"
        });
        G4M1_11.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}