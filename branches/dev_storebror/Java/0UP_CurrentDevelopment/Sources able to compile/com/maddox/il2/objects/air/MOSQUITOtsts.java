package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class MOSQUITOtsts extends MOSQUITO
    implements TypeFighter, TypeStormovik
{

    public MOSQUITOtsts()
    {
        phase = 0;
        disp = 0.0F;
        oldbullets = 0;
        g1 = null;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(thisWeaponsName.startsWith("2x") || thisWeaponsName.startsWith("L2x"))
        {
            hierMesh().chunkVisible("Rack1_D0", true);
            hierMesh().chunkVisible("Rack2_D0", true);
        } else
        {
            hierMesh().chunkVisible("Rack1_D0", false);
            hierMesh().chunkVisible("Rack2_D0", false);
        }
        if(thisWeaponsName.startsWith("Less") || thisWeaponsName.startsWith("L2x"))
        {
            hierMesh().chunkVisible("MGBar1", false);
            hierMesh().chunkVisible("MGBar2", false);
        } else
        {
            hierMesh().chunkVisible("MGBar1", true);
            hierMesh().chunkVisible("MGBar2", true);
        }
        if(FM.CT.Weapons[1] != null)
            g1 = FM.CT.Weapons[1][0];
    }

    public void update(float f)
    {
        if(g1 != null && oldbullets != g1.countBullets())
            switch(phase)
            {
            default:
                break;

            case 0: // '\0'
                if(g1.isShots())
                {
                    oldbullets = g1.countBullets();
                    phase = 1;
                    disp = 0.0F;
                }
                break;

            case 1: // '\001'
                disp += 12.6F * f;
                resetYPRmodifier();
                Aircraft.xyz[1] = disp;
                hierMesh().chunkSetLocate("Cannon_D0", Aircraft.xyz, Aircraft.ypr);
                if(disp >= 0.7F)
                    phase = 2;
                break;

            case 2: // '\002'
                disp -= 1.2F * f;
                resetYPRmodifier();
                Aircraft.xyz[1] = disp;
                hierMesh().chunkSetLocate("Cannon_D0", Aircraft.xyz, Aircraft.ypr);
                if(disp <= 0.0F)
                    phase = 3;
                break;

            case 3: // '\003'
                phase = 0;
                break;
            }
        super.update(f);
    }

    private int phase;
    private float disp;
    private int oldbullets;
    private com.maddox.il2.ai.BulletEmitter g1;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Mosquito");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Mosquito_FB_MkVI_Tse(Multi1)/TseTse_hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/Mosquito_FB_MkVI_Tse(GB)/TseTse_hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Mosquito-tsts.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitMosquitoTseTse.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.6731F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 3, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57AP 23", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "default_HE", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57HE 23", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "default_MIX", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57MIX 23", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "DropTanks", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57AP 23", null, null, "FuelTankGun_Tank100galtsetse 1", "FuelTankGun_Tank100galtsetse 1"
        });
        Aircraft.weaponsRegister(class1, "DropTanks_HE", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57HE 23", null, null, "FuelTankGun_Tank100galtsetse 1", "FuelTankGun_Tank100galtsetse 1"
        });
        Aircraft.weaponsRegister(class1, "DropTanks_MIX", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57MIX 23", null, null, "FuelTankGun_Tank100galtsetse 1", "FuelTankGun_Tank100galtsetse 1"
        });
        Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57AP 23", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2x250_HE", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57HE 23", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2x250_MIX", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57MIX 23", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57AP 23", "BombGun500lbsE 1", "BombGun500lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2x500_HE", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57HE 23", "BombGun500lbsE 1", "BombGun500lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2x500_MIX", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57MIX 23", "BombGun500lbsE 1", "BombGun500lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "Less_MG", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57AP 23", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "Less_MG_HE", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57HE 23", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "Less_MG_MIX", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57MIX 23", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "Less_MG_DropTanks", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57AP 23", null, null, "FuelTankGun_Tank100galtsetse 1", "FuelTankGun_Tank100galtsetse 1"
        });
        Aircraft.weaponsRegister(class1, "Less_MG_DropTanks_HE", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57HE 23", null, null, "FuelTankGun_Tank100galtsetse 1", "FuelTankGun_Tank100galtsetse 1"
        });
        Aircraft.weaponsRegister(class1, "Less_MG_DropTanks_MIX", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57MIX 23", null, null, "FuelTankGun_Tank100galtsetse 1", "FuelTankGun_Tank100galtsetse 1"
        });
        Aircraft.weaponsRegister(class1, "L2x250", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57AP 23", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "L2x250_HE", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57HE 23", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "L2x250_MIX", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57MIX 23", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "L2x500", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57AP 23", "BombGun500lbsE 1", "BombGun500lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "L2x500_HE", new java.lang.String[] {
            "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunBrowning303kipzl 1000", "MGunMolins_57HE 23", "BombGun500lbsE 1", "BombGun500lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
