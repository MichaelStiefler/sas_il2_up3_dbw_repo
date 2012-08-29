// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FW_190F8.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190, PaintSchemeFMPar03, NetAircraft

public class FW_190F8 extends com.maddox.il2.objects.air.FW_190
{

    public FW_190F8()
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
        com.maddox.il2.objects.air.FW_190F8.moveGear(hierMesh(), f);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190F8.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Fw-190F-8(Beta)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190F-8.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitFW_190F8.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.FW_190F8.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.FW_190F8.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, null
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "4sc50", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "1sc250", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunSC250"
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "1sc2504sc50", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC250"
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "1ab250", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunAB250"
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "1sc500", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunSC500"
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "1sc5004sc50", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC500"
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "1ab500", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunAB500"
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "1sd500", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunSD500"
        });
        com.maddox.il2.objects.air.FW_190F8.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
