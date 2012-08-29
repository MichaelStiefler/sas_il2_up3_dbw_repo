// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MC_205_3.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MC_202xyz, PaintSchemeFCSPar02, PaintSchemeFMPar01, NetAircraft

public class MC_205_3 extends com.maddox.il2.objects.air.MC_202xyz
{

    public MC_205_3()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -114F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -114F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -80F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.MC_205_3.cvt(f, 0.11F, 0.67F, 0.0F, -38F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.MC_205_3.cvt(f, 0.01F, 0.09F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.MC_205_3.cvt(f, 0.01F, 0.09F, 0.0F, -80F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.MC_205_3.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.65F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC3_D0", 0.0F, com.maddox.il2.objects.air.MC_205_3.cvt(f, -30F, 30F, 30F, -30F), 0.0F);
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
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "M.C.205");
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/MC-205_III(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MC-205_III(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MC-205.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitMC_205.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7898F);
        com.maddox.il2.objects.air.MC_205_3.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1
        });
        com.maddox.il2.objects.air.MC_205_3.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.MC_205_3.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127siMC205 370", "MGunBredaSAFAT127siMC205 370", "MGunMG15120k 250", "MGunMG15120k 250"
        });
        com.maddox.il2.objects.air.MC_205_3.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
