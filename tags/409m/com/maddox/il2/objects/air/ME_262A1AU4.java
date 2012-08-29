// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ME_262A1AU4.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_262, PaintSchemeFMPar05, TypeStormovik, Aircraft, 
//            NetAircraft

public class ME_262A1AU4 extends com.maddox.il2.objects.air.ME_262
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public ME_262A1AU4()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 103F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC21_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearC6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 73F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 73F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, 88F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.ME_262A1AU4.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        float f = FM.Gears.gWheelSinking[2];
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.19F, 0.0F, 0.19F);
        hierMesh().chunkSetLocate("GearC22_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        if(FM.CT.getGear() > 0.75F)
            hierMesh().chunkSetAngles("GearC21_D0", 0.0F, -90F + 40F * f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_262A1AU4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Me 262");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Me-262A-1aU4/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Me-262A-1aU4.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitME_262.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74615F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK214A 28"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
