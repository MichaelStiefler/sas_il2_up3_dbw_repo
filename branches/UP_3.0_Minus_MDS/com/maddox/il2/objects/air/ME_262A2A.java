// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   ME_262A2A.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_262, PaintSchemeBMPar05, TypeStormovik, NetAircraft, 
//            Aircraft

public class ME_262A2A extends com.maddox.il2.objects.air.ME_262
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public ME_262A2A()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_262A2A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Me 262");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Me-262A-2a/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1944.1F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Me-262A-1a.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitME_262.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.74615F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3, 9, 9, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", 
            "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", 
            "_ExternalRock25", "_ExternalRock26", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTypeD", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xSC250", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAB250", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "BombGunAB250", "BombGunAB250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xSC500", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xSD500", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAB500", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "BombGunAB500", "BombGunAB500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWfrGr21", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "RocketGunWfrGr21 1", "RocketGunWfrGr21 1", "PylonRO_WfrGr21_262", "PylonRO_WfrGr21_262", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24r4m", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", null, null, "PylonMe262_R4M_Left", "PylonMe262_R4M_Right", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24r4m_2xTypeD", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", null, null, "PylonMe262_R4M_Left", "PylonMe262_R4M_Right", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", 
            null, null, null, null, "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
