package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class HurricaneMkIa extends Hurricane
{

    public HurricaneMkIa()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.ai.World.cur().camouflage == 2)
            hierMesh().chunkVisible("filter", true);
        else
            hierMesh().chunkVisible("filter", false);
    }

    static 
    {
        java.lang.Class class1 = HurricaneMkIa.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName_fi", "3DO/Plane/HurricaneMkI(Finnish)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkI(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkI.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitHURRI.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 356", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 308", "MGunBrowning303k 334"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
