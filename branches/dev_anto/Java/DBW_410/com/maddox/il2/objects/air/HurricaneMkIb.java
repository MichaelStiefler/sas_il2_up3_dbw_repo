// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HurricaneMkIb.java

package com.maddox.il2.objects.air;

// Referenced classes of package com.maddox.il2.objects.air:
//            Hurricane, PaintSchemeFMPar03, TypeFighter, NetAircraft, 
//            Aircraft

public class HurricaneMkIb extends com.maddox.il2.objects.air.Hurricane
    implements com.maddox.il2.objects.air.TypeFighter
{

    public HurricaneMkIb()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HurricaneMkIb.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkI(Multi1)/hierMkIb.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkIa12lbs.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHURRII.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[8]);
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
    }
}