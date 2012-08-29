// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   S_199.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109G14, PaintSchemeFMPar04, NetAircraft, Aircraft

public class S_199 extends com.maddox.il2.objects.air.BF_109G14
{

    public S_199()
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
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

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.S_199.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "S199");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/S-199/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1947F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1958F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitS_199.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/S-199.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 3, 3, 3, 3, 
            3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON02", "_CANNON03", "_ExternalDev01", "_ExternalDev01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
            "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 250", "MGunMG131si 250", "MGunMG15120kh 135", "MGunMG15120kh 135", null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xSC50", new java.lang.String[] {
            "MGunMG131si 250", "MGunMG131si 250", "MGunMG15120kh 135", "MGunMG15120kh 135", "PylonETC50 1", null, null, null, null, "BombGunSC50 1", 
            "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xSC50", new java.lang.String[] {
            "MGunMG131si 250", "MGunMG131si 250", "MGunMG15120kh 135", "MGunMG15120kh 135", "PylonETC50 1", null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", 
            "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xSC70", new java.lang.String[] {
            "MGunMG131si 250", "MGunMG131si 250", "MGunMG15120kh 135", "MGunMG15120kh 135", "PylonETC50 1", null, null, null, null, "BombGunSC70 1", 
            "BombGunSC70 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-SC250", new java.lang.String[] {
            "MGunMG131si 250", "MGunMG131si 250", "MGunMG15120kh 135", "MGunMG15120kh 135", "PylonETC900 1", null, "BombGunSC250 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
