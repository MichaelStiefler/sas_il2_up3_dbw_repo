package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class KI_46_RECCE extends KI_46
    implements TypeFighter
{

    public KI_46_RECCE()
    {
        bChangedPit = true;
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

    public boolean bChangedPit;

    static 
    {
        java.lang.Class class1 = KI_46_RECCE.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-46");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-46(Recce)(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-46-IIIRecce.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitKI_46_RECCE.class);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_Clip04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
