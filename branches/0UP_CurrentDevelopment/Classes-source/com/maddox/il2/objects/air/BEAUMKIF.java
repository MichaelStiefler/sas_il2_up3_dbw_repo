// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BEAUMKIF.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BEAU, PaintSchemeBMPar02, TypeFighter, TypeStormovik, 
//            NetAircraft, Aircraft

public class BEAUMKIF extends com.maddox.il2.objects.air.BEAU
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeStormovik
{

    public BEAUMKIF()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BEAUMKIF.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Beaufighter");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BeaufighterMk1F/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/BeaufighterMk1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBEAU21.class, com.maddox.il2.objects.air.CockpitBEAU1Gun.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7394F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 1, 1, 9, 9, 
            3, 3, 9, 3, 9, 9, 2, 2, 2, 2, 
            2, 2, 2, 2, 9, 3, 9, 3, 10, 0, 
            0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev02", "_ExternalDev03", 
            "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev04", "_ExternalDev05", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalDev06", "_ExternalBomb04", "_ExternalDev07", "_ExternalBomb05", "_MGUN09", "_MGUN10", 
            "_MGUN11"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}