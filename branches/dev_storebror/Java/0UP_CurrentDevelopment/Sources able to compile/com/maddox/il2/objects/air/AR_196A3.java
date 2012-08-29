package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;

public class AR_196A3 extends AR_196
{

    public AR_196A3()
    {
    }

    public void update(float f)
    {
        super.update(f);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 2; j++)
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpGearEff[i][j] != null)
                {
                    tmpp.set(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpGearEff[i][j])).pos.getAbsPoint());
                    tmpp.z = 0.01D;
                    ((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpGearEff[i][j])).pos.setAbs(tmpp);
                    ((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpGearEff[i][j])).pos.reset();
                }

        }

    }

    private static com.maddox.JGP.Point3d tmpp = new Point3d();

    static 
    {
        java.lang.Class class1 = AR_196A3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ar-196A-3.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ar-196A-3/hier.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ar-196");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1938.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitAR_196A3.class, CockpitAR_196_Gunner.class
        });
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 1, 10, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01", "_CANNON02", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMGFFki 60", "MGunMGFFki 60", "MGunMG15t 525", null, null
        });
        Aircraft.weaponsRegister(class1, "2sc50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMGFFki 60", "MGunMGFFki 60", "MGunMG15t 525", "BombGunSC50 1", "BombGunSC50 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
