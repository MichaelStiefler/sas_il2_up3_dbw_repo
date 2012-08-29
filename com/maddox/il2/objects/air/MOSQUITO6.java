// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MOSQUITO6.java

package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MOSQUITO, PaintSchemeFMPar04, PaintSchemeFMPar06, TypeFighter, 
//            TypeStormovik, NetAircraft

public class MOSQUITO6 extends com.maddox.il2.objects.air.MOSQUITO
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeStormovik
{

    public MOSQUITO6()
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
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Mosquito");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Mosquito_FB_MkVI(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/Mosquito_FB_MkVI(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Mosquito-FBMkVI.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitMosquito6.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.6731F);
        com.maddox.il2.objects.air.MOSQUITO6.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 1, 1, 3, 3, 
            3, 3
        });
        com.maddox.il2.objects.air.MOSQUITO6.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", 
            "_BombSpawn01", "_BombSpawn02"
        });
        com.maddox.il2.objects.air.MOSQUITO6.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
            null, null
        });
        com.maddox.il2.objects.air.MOSQUITO6.weaponsRegister(class1, "extra", new java.lang.String[] {
            "MGunBrowning303kipzl 780", "MGunBrowning303kipzl 780", "MGunBrowning303kipzl 780", "MGunBrowning303kipzl 780", "MGunHispanoMkIkpzl 175", "MGunHispanoMkIkpzl 175", "MGunHispanoMkIkpzl 175", "MGunHispanoMkIkpzl 175", null, null, 
            null, null
        });
        com.maddox.il2.objects.air.MOSQUITO6.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
            "BombGun250lbsE 1", "BombGun250lbsE 1"
        });
        com.maddox.il2.objects.air.MOSQUITO6.weaponsRegister(class1, "4x250", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "BombGun250lbsE 1", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", "BombGun250lbsE 1"
        });
        com.maddox.il2.objects.air.MOSQUITO6.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
            "BombGun500lbsE 1", "BombGun500lbsE 1"
        });
        com.maddox.il2.objects.air.MOSQUITO6.weaponsRegister(class1, "4x500", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "BombGun500lbsE 1", "BombGun500lbsE 1", 
            "BombGun500lbsE 1", "BombGun500lbsE 1"
        });
        com.maddox.il2.objects.air.MOSQUITO6.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
