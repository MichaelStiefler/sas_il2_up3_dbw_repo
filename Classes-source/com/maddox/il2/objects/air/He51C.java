// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   He51C.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CR_42, PaintSchemeFMPar00, NetAircraft, Aircraft

public class He51C extends com.maddox.il2.objects.air.CR_42
{

    public He51C()
    {
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45F * f, 0.0F);
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.He51C.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He51C");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/He51C/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/He51C/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He51C.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHe51C.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.742F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3, 3, 3, 3, 3, 9, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalDev01", "_ExternalBomb07"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, null, null, null, null, null, "FuelTankGun_Tank50l 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2MG17s + 4x50Kg HE Bombs", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, "FuelTankGun_Tank50l 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2MG17s + 6x10Kg HE Bombs", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "FuelTankGun_Tank50l 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2MG17s + Fuel Tank + 6x10Kg HE Bombs", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "FuelTankGun_Tank50l 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
