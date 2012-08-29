// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MIG_9_I_300.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MIG_9, PaintSchemeFMPar06, Aircraft, NetAircraft

public class MIG_9_I_300 extends com.maddox.il2.objects.air.MIG_9
{

    public MIG_9_I_300()
    {
        nCN37 = -1;
    }

    public void update(float f)
    {
        super.update(f);
        if(FM.AS.isMaster() && FM.CT.Weapons[1] != null && FM.CT.Weapons[1][0] != null)
        {
            if(FM.CT.Weapons[1][0].countBullets() < nCN37)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.Aircraft.cvt(FM.getAltitude(), 3000F, 7000F, 0.0F, 0.1F))
                    FM.EI.engines[0].setEngineStops(this);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.Aircraft.cvt(FM.getAltitude(), 3000F, 7000F, 0.0F, 0.1F))
                    FM.EI.engines[1].setEngineStops(this);
            }
            nCN37 = FM.CT.Weapons[1][0].countBullets();
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private int nCN37;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "MiG-9");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MiG-9(F-2)(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/MiG-9(F-2)(ru)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1946F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1956F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MiG-9.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitMIG_9.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.75635F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVYak 80", "MGunVYak 80", "MGunN57ki 21"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }
}
