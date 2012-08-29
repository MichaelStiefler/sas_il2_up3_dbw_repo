// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TA_152H1.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190, PaintSchemeFMPar05, Aircraft, NetAircraft

public class TA_152H1 extends com.maddox.il2.objects.air.FW_190
{

    public TA_152H1()
    {
        kangle = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -102F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -102F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 20F * f, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.TA_152H1.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
            return;
        }
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.44F, 0.0F, 0.44F);
        hierMesh().chunkSetLocate("GearL2a_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.44F, 0.0F, 0.44F);
        hierMesh().chunkSetLocate("GearR2a_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void update(float f)
    {
        for(int i = 1; i < 15; i++)
            hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10F * kangle, 0.0F);

        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        if(FM.Loc.z > 9000D)
        {
            if(!FM.EI.engines[0].getControlAfterburner())
                FM.EI.engines[0].setAfterburnerType(2);
        } else
        if(!FM.EI.engines[0].getControlAfterburner())
            FM.EI.engines[0].setAfterburnerType(1);
        super.update(f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.TA_152H1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ta.152");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ta-152H-1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944.6F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ta-152H-1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitTA_152.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON03", "_CANNON04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108k 90", "MGunMG15120s  175", "MGunMG15120s  175"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }
}
