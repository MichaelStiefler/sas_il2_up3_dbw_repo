// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   I_15_m25.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_153_M62, PaintSchemeFMPar00, NetAircraft, Aircraft

public class I_15_m25 extends com.maddox.il2.objects.air.I_153_M62
{

    public I_15_m25()
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

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 15F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 15F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, 170F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, 170F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -85F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -100F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.I_15_m25.moveGear(hierMesh(), f);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.I_15_m25.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "I-15");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/I-15/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar00())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1937F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1942F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitI_15.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/I-15-M25.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 0, 0, 2, 2, 2, 2, 2, 2, 
            2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 
            9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xAO10", new java.lang.String[] {
            "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", null, null, null, null, null, null, 
            null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAO10_2xFAB50", new java.lang.String[] {
            "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", null, null, null, null, null, null, 
            null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xFAB50", new java.lang.String[] {
            "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", null, null, null, null, null, null, 
            null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xRS82", new java.lang.String[] {
            "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", "MGunShKASsi 750", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            "RocketGunRS82", "RocketGunRS82", null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
