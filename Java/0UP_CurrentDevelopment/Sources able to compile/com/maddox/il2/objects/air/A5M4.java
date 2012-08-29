package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class A5M4 extends A5M
{

    public A5M4()
    {
        flapps = 0.0F;
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public static boolean bChangedPit = false;
    private float flapps;

    static 
    {
        java.lang.Class class1 = A5M4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A5M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A5M4(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A5M4(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A5M4.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitA5M4.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7498F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVikkersKs 500", "MGunVikkersKs 500", null, null
        });
        Aircraft.weaponsRegister(class1, "1xdt", new java.lang.String[] {
            "MGunVikkersKs 500", "MGunVikkersKs 500", "PylonA5MPLN1", "FuelTankGun_TankA5M"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
