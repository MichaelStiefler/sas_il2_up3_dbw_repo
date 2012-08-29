// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LAGG_3SERIES66.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            LAGG_3, PaintSchemeFMPar04, TypeTNBFighter, Aircraft, 
//            NetAircraft

public class LAGG_3SERIES66 extends com.maddox.il2.objects.air.LAGG_3
    implements com.maddox.il2.objects.air.TypeTNBFighter
{

    public LAGG_3SERIES66()
    {
    }

    public void update(float f)
    {
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.2F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.2F), 0.0F);
        }
        super.update(f);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", -75F * f, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1400F, -80F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.LAGG_3SERIES66.moveGear(hierMesh(), f);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.LAGG_3SERIES66.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "LaGG");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/LaGG-3series66/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/LaGG-3series66.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitLAGG_3SERIES66.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.69445F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 3, 3, 9, 2, 9, 2, 9, 2, 
            9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 
            9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalRock01", "_ExternalDev02", "_ExternalRock02", "_ExternalDev03", "_ExternalRock03", 
            "_ExternalDev04", "_ExternalRock04", "_ExternalDev05", "_ExternalRock05", "_ExternalDev06", "_ExternalRock06", "_ExternalDev07", "_ExternalRock07", "_ExternalDev08", "_ExternalRock08", 
            "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBs 200", "MGunShVAKk 160", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunUBs 200", "MGunShVAKk 160", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDROPTANK", new java.lang.String[] {
            "MGunUBs 200", "MGunShVAKk 160", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "FuelTankGun_Tank80", "FuelTankGun_Tank80"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
