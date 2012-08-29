package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class Lagg_5 extends LA_X
{

    public Lagg_5()
    {
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            for(int i = 0; i < 4; i++)
                if(FM.AS.astateTankStates[i] > 0 && FM.AS.astateTankStates[i] < 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.repairTank(i);

        }
    }

    static 
    {
        java.lang.Class class1 = Lagg_5.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "La");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/La-5(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Lagg_5.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitLA_5.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.750618F);
        Lagg_5.weaponTriggersRegister(class1, new int[] {
            1, 1, 3, 3, 9, 9
        });
        Lagg_5.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02"
        });
        Lagg_5.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKs 170", "MGunShVAKs 200", null, null, null, null
        });
        Lagg_5.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunShVAKs 170", "MGunShVAKs 200", "BombGunFAB50 1", "BombGunFAB50 1", null, null
        });
        Lagg_5.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShVAKs 170", "MGunShVAKs 200", "BombGunFAB100 1", "BombGunFAB100 1", null, null
        });
        Lagg_5.weaponsRegister(class1, "2xDROPTANK", new java.lang.String[] {
            "MGunShVAKs 170", "MGunShVAKs 200", null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80"
        });
        Lagg_5.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
