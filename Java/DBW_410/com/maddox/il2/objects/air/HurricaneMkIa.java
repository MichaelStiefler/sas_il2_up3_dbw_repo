// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 7/02/2011 4:50:06 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   HurricaneMkIa.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Hurricane, PaintSchemeFCSPar02, PaintSchemeFMPar00, NetAircraft

public class HurricaneMkIa extends Hurricane
{

    public HurricaneMkIa()
    {
    }
    
    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
    	if(World.cur().camouflage == 2)
    	{
    		hierMesh().chunkVisible("filter", true);
    	}
    	else
    	{
    		hierMesh().chunkVisible("filter", false);
    	}
    }

    static 
    {
        Class class1 = com.maddox.il2.objects.air.HurricaneMkIa.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "Hurri");
        Property.set(class1, "meshName_fi", "3DO/Plane/HurricaneMkI(Finnish)/hier.him");
        Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
        Property.set(class1, "meshName", "3DO/Plane/HurricaneMkI(Multi1)/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        Property.set(class1, "yearService", 1938F);
        Property.set(class1, "yearExpired", 1945.5F);
        Property.set(class1, "FlightModel", "FlightModels/HurricaneMkI.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
                com.maddox.il2.objects.air.CockpitHURRI.class
            });
        Property.set(class1, "LOSElevation", 0.965F);
        weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0
        });
        weaponHooksRegister(class1, new String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
        weaponsRegister(class1, "default", new String[] {
            "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 356", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 308", "MGunBrowning303k 334"
        });
        weaponsRegister(class1, "none", new String[] {
            null, null, null, null, null, null, null, null
        });
    }
}