// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   F_86F_30.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F_86F, PaintSchemeFMPar06, PaintSchemeFMPar1956, NetAircraft, 
//            Aircraft

public class F_86F_30 extends com.maddox.il2.objects.air.F_86F
{

    public F_86F_30()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.F_86F_30.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "F-86");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/F-86F(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/F-86F(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_de", "3DO/Plane/F-86F(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_de", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1949.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1949.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/F-86F-25L.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitF_86Flate.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            9, 3, 3, 9, 3, 3, 9, 2, 2, 9, 
            2, 2, 9, 9, 9, 9, 9, 3, 3, 9, 
            3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalBomb01", "_ExternalBomb01", "_ExternalDev06", "_ExternalBomb02", "_ExternalBomb02", "_ExternalDev07", "_ExternalRock01", "_ExternalRock01", "_ExternalDev08", 
            "_ExternalRock02", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalBomb03", "_ExternalBomb03", "_ExternalDev14", 
            "_ExternalBomb04", "_ExternalBomb04", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", 
            "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalBomb07", "_ExternalBomb08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x120dt+2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x75nap", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun75Napalm 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x75nap+2x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun75Napalm 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x75nap+2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun75Napalm 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500+2x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500+2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500+2x750", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, 
            null, "BombGun500lbs 1", "BombGunNull 1", null, "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun750lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500+2xM117", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, 
            null, "BombGun500lbs 1", "BombGunNull 1", null, "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun750lbsM117 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x500", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, 
            null, "BombGun500lbs 1", "BombGunNull 1", null, "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x750", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun750lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x750+2x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun750lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x750+2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun750lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM117", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun750lbsM117 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM117+2x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun750lbsM117 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM117+2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun750lbsM117 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000+2x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000+2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM114", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs_M114 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun1000lbs_M114 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM114+2x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs_M114 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun1000lbs_M114 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM114+2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs_M114 1", "BombGunNull 1", "PylonF86_Outboard 1", 
            "BombGun1000lbs_M114 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xMk12+1x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGunMk12 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xMk7+1x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "BombGunMk7 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "16xHVAR", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
            "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xHVAR+2x120dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xHVAR+2x207dt", new java.lang.String[] {
            "MGunBrowningM3 270", "MGunBrowningM3 270", "MGunBrowningM3b 270", "MGunBrowningM3b 270", "MGunBrowningM3c 270", "MGunBrowningM3c 270", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
