// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 7/28/2011 9:06:08 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: fullnames 
// Source File Name:   BF_109E1.java

package com.maddox.il2.objects.air;


// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, CockpitBF_109E1, PaintSchemeFMPar01, Aircraft, 
//            Cockpit, NetAircraft

public class BF_109E1 extends com.maddox.il2.objects.air.BF_109E3
{



    public BF_109E1()
    {
    	super.bIsE3 = false;
    }


    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109E1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-109E-1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        
        com.maddox.rts.Property.set(class1, "meshName_de", "3DO/Plane/Bf-109E-1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_de", new PaintSchemeFCSPar01());
        
        
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1942F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109E-1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_109E3.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74985F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalDev01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG17k 750", "MGunMG17k 750", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc250", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG17k 750", "MGunMG17k 750", "PylonETC900 1", "BombGunSC250 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sc50", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG17k 750", "MGunMG17k 750", "PylonETC50Bf109 1", null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}