// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KI_43_IC.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            KI_43, PaintSchemeFMPar05, PaintSchemeFCSPar05, NetAircraft, 
//            Aircraft

public class KI_43_IC extends com.maddox.il2.objects.air.KI_43
{

    public KI_43_IC()
    {
        flapps = 0.0F;
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            for(int i = 1; i < 18; i++)
                hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -22F * f1, 0.0F);

            for(int j = 2; j < 10; j++)
                hierMesh().chunkSetAngles("Cowflap" + j + "_D0", 0.0F, 22F * f1, 0.0F);

            for(int k = 11; k < 12; k++)
                hierMesh().chunkSetAngles("Cowflap" + k + "_D0", 0.0F, 22F * f1, 0.0F);

        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float flapps;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.KI_43_IC.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-43");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-43-Ic(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-43-Ic(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-43-Ia.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitKI_43.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5265F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo103s 250", "MGunHo103s 250"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
