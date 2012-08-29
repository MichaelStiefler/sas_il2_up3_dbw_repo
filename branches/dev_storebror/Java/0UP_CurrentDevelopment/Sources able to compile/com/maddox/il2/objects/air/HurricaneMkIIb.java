package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

public class HurricaneMkIIb extends Hurricane
    implements TypeFighter, TypeStormovik
{

    public HurricaneMkIIb()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_MGUN09") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        {
            hierMesh().chunkVisible("OuterMGL_D0", false);
            hierMesh().chunkVisible("OuterMGR_D0", false);
        }
        if((getBulletEmitterByHookName("_ExternalDev01") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getBulletEmitterByHookName("_ExternalBomb01") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            hierMesh().chunkVisible("PylonL_D0", false);
            hierMesh().chunkVisible("PylonR_D0", false);
        }
        if(com.maddox.il2.ai.World.cur().camouflage == 2)
            hierMesh().chunkVisible("filter", true);
        else
            hierMesh().chunkVisible("filter", false);
    }

    static 
    {
        java.lang.Class class1 = HurricaneMkIIb.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkIIb(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkIIa.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitHURRII.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 3, 3, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", 
            "_MGUN11", "_MGUN12", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", 
            "MGunBrowning303k 334", "MGunBrowning303k 335", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x250lb", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", 
            "MGunBrowning303k 334", "MGunBrowning303k 335", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2xfab100", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", 
            "MGunBrowning303k 334", "MGunBrowning303k 335", "BombGunFAB100 1", "BombGunFAB100 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2xDrop44", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", 
            "MGunBrowning303k 334", "MGunBrowning303k 335", null, null, "FuelTankGun_Tank44gal 1", "FuelTankGun_Tank44gal 1"
        });
        Aircraft.weaponsRegister(class1, "2xDrop80", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", 
            "MGunBrowning303k 334", "MGunBrowning303k 335", "FuelTankGun_Tank80 1", "FuelTankGun_Tank80 1", null, null
        });
        Aircraft.weaponsRegister(class1, "8xMG", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", null, null, 
            null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "8xMG-2x250lb", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", null, null, 
            null, null, "BombGun250lbsE 1", "BombGun250lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "8xMG-2x500lb", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", null, null, 
            null, null, "BombGun500lbsE 1", "BombGun500lbsE 1", null, null
        });
        Aircraft.weaponsRegister(class1, "8xMG-2xfab100", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", null, null, 
            null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null
        });
        Aircraft.weaponsRegister(class1, "8xMG-2xDrop44", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", null, null, 
            null, null, null, null, "FuelTankGun_Tank44gal 1", "FuelTankGun_Tank44gal 1"
        });
        Aircraft.weaponsRegister(class1, "8xMG-2xDrop80", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", null, null, 
            null, null, null, null, "FuelTankGun_Tank80 1", "FuelTankGun_Tank80 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
