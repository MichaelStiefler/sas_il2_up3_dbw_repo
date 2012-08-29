// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Mig_15bislate.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Mig_15F, PaintSchemeFCSPar1956, PaintSchemeFMPar1956, PaintSchemeFMPar06, 
//            NetAircraft, Aircraft

public class Mig_15bislate extends com.maddox.il2.objects.air.Mig_15F
{

    public Mig_15bislate()
    {
    }

    public void update(float f)
    {
        super.update(f);
        if(super.FM.getSpeedKMH() > 1100F)
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 1.0F;
            com.maddox.il2.ai.World.cur();
        }
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.Mig_15bislate.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "MiG-15");
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar1956());
        com.maddox.rts.Property.set(class1, "meshName_sk", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(class1, "meshName_ro", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(class1, "meshName_hu", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1951.4F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MiG-15F.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitMig_15F.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 0, 0, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDroptanks", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null
        });
    }
}