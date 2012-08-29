// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_36A4.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_36, PaintSchemeFMPar01, PaintSchemeFCSPar02, NetAircraft, 
//            Aircraft

public class P_36A4 extends com.maddox.il2.objects.air.P_36
{

    public P_36A4()
    {
        kangle = 0.0F;
    }

    public void update(float f)
    {
        for(int i = 1; i < 9; i++)
            hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10F * kangle, 0.0F);

        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_36A4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-36");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Hawk75A-4(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/Hawk75A-4(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-36A-4.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
