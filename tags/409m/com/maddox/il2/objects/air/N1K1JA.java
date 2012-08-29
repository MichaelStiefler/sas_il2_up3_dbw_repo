// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   N1K1JA.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            N1K, PaintSchemeFMPar01, PaintSchemeFCSPar05, NetAircraft, 
//            Aircraft

public class N1K1JA extends com.maddox.il2.objects.air.N1K
{

    public N1K1JA()
    {
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            for(int i = 1; i < 11; i++)
                hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -20F * f1, 0.0F);

        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.CT.Weapons[3] != null)
        {
            hierMesh().chunkVisible("RackL_D0", true);
            hierMesh().chunkVisible("RackR_D0", true);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.N1K1JA.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "N1K");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/N1K1-Ja(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/N1K1-Ja(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/N1K1-J.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x400dt", new java.lang.String[] {
            "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", null, null, "PylonN1K1PLN1", "FuelTankGun_TankN1K1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x30", new java.lang.String[] {
            "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "BombGun30kgJ 1", "BombGun30kgJ 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "MGunHo5k 100", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
