// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_40M.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_40, PaintSchemeFMPar05, PaintSchemeFMPar06, Aircraft, 
//            NetAircraft

public class P_40M extends com.maddox.il2.objects.air.P_40
{

    public P_40M()
    {
    }

    public void update(float f1)
    {
        super.update(f1);
        f = com.maddox.il2.objects.air.Aircraft.cvt(FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 5F, -17F);
        hierMesh().chunkSetAngles("Water2_D0", 0.0F, f, 0.0F);
        hierMesh().chunkSetAngles("Water3_D0", 0.0F, f, 0.0F);
        f = java.lang.Math.min(f, 0.0F);
        hierMesh().chunkSetAngles("Water1_D0", 0.0F, f, 0.0F);
        hierMesh().chunkSetAngles("Water4_D0", 0.0F, f, 0.0F);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static float f;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_40M.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-40");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-40M(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-40M(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_rz", "3DO/Plane/P-40M(RZ)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_rz", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-40M.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_40M.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0692F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 3, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "500lb", new java.lang.String[] {
            "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "BombGun500lbs", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1000lb", new java.lang.String[] {
            "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "BombGun1000lbs", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "droptank", new java.lang.String[] {
            "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", null, "FuelTankGun_Tank75gal"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
