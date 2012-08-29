// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_111H6.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HE_111, PaintSchemeBMPar02, NetAircraft, Aircraft

public class HE_111H6 extends com.maddox.il2.objects.air.HE_111
{

    public HE_111H6()
    {
    }

    public void update(float f)
    {
        if(FM.turret[5].tMode == 2)
            FM.turret[5].tMode = 4;
        super.update(f);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HE_111H6.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He-111");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/He-111H-6/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-111H-6.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHE_111H6.class, com.maddox.il2.objects.air.CockpitHE_111H6_Bombardier.class, com.maddox.il2.objects.air.CockpitHE_111H6_NGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_TGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_BGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_LGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_RGunner.class
        });
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 15, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xSD250", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xSC500", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xAB500", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunAB500", "BombGunAB500", "BombGunAB500", "BombGunAB500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2SC1000", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC1000", "BombGunSC1000", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2PC1600", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunPC1600", "BombGunPC1600", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2SC2000", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC2000", "BombGunSC2000", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTorp", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGun4512", "BombGun4512", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
