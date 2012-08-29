// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HS_129B2.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HS_129, PaintSchemeBMPar02, NetAircraft, Aircraft

public class HS_129B2 extends com.maddox.il2.objects.air.HS_129
{

    public HS_129B2()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HS_129B2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hs-129");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Hs-129B-2/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Hs-129B-2.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1, 0, 9, 1, 1, 1, 
            1, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_HEAVYCANNON01", "_ExternalDev01", "_MGUN03", "_MGUN04", "_MGUN05", 
            "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2-1xMk101", new java.lang.String[] {
            "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", "MGunMK103k 30", null, "PylonHS129MK101", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3-4xMG17", new java.lang.String[] {
            "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, "PylonHS129MG17S", "MGunMG17k 250", "MGunMG17k 250", "MGunMG17k 250", 
            "MGunMG17k 250", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R4-1xSC250", new java.lang.String[] {
            "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, null, null, null, null, 
            null, "BombGunSC250", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R4-2xCS50", new java.lang.String[] {
            "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, null, null, null, null, 
            null, null, null, "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R4-4xSC50", new java.lang.String[] {
            "MGunMG17k 500", "MGunMG17k 500", "MGunMG15120k 125", "MGunMG15120k 125", null, null, null, null, null, null, 
            null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Wa-1xBK37", new java.lang.String[] {
            null, null, "MGunMG15120s 125", "MGunMG15120s 125", null, "MGunBK374Hs129 32", "PylonHS129BK37", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
