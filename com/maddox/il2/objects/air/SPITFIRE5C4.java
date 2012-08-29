// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SPITFIRE5C4.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE, PaintSchemeFMPar04, Aircraft, NetAircraft

public class SPITFIRE5C4 extends com.maddox.il2.objects.air.SPITFIRE
{

    public SPITFIRE5C4()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.6F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 1.0F, 0.0F, -95F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.SPITFIRE5C4.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
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
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SpitfireMkVc4xH(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/SpitfireMkVc4xH(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SpitfireVB.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitSpit5C.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
