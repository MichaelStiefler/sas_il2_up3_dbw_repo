// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FW_190A9.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190, PaintSchemeFMPar03, NetAircraft, Aircraft

public class FW_190A9 extends com.maddox.il2.objects.air.FW_190
{

    public FW_190A9()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 20F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.FW_190A9.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
            return;
        }
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
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190A9.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Fw-190A-9(Beta)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190A-9.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitFW_190A8.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 3, 9, 9, 1, 1, 9, 
            9, 1, 1, 1, 1, 9, 9, 1, 1, 9, 
            9, 1, 1, 9, 9, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalDev02", "_CANNON03", "_CANNON04", "_ExternalDev03", 
            "_ExternalDev04", "_CANNON05", "_CANNON06", "_CANNON07", "_CANNON08", "_ExternalDev05", "_ExternalDev06", "_CANNON09", "_CANNON10", "_ExternalDev07", 
            "_ExternalDev08", "_CANNON11", "_CANNON12", "_ExternalDev09", "_ExternalDev10", "_ExternalRock01", "_ExternalRock02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, "PylonMG15120Internal", "PylonMG15120Internal", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc500", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "BombGunSC500 1", "PylonMG15120Internal", "PylonMG15120Internal", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "r14mg15120", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, "PylonMG15120x2", 
            "PylonMG15120x2", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "r22mk108", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, "PylonMG15120Internal", "PylonMG15120Internal", "MGunMK108kh 55", "MGunMK108kh 55", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "r32mk103", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMk103", 
            "PylonMk103", "MGunMK103kh 35", "MGunMK103kh 35", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "r62wfrgr21", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
    }
}
