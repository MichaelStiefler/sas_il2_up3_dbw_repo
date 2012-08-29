package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

public class BF_110C4B extends BF_110
{

    public BF_110C4B()
    {
    }

    public void update(float f)
    {
        afterburnerhud();
        super.update(f);
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void afterburnerhud()
    {
        if(FM.isPlayers() && FM.EI.engines[0].getControlAfterburner())
            com.maddox.il2.game.HUD.logRightBottom("Start- und Notleistung ENABLED!");
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = BF_110C4B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf-110");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-110C-4B/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-110C-4b.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBF_110C4B.class, CockpitBF_110Early_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66895F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 10, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", null, null
        });
        Aircraft.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", "BombGunSC250", "BombGunSC250"
        });
        Aircraft.weaponsRegister(class1, "2ab250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", "BombGunAB250", "BombGunAB250"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
