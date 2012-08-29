package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class ME_210CA1ZSTR extends ME_210
    implements TypeFighter, TypeBNZFighter, TypeStormovik, TypeStormovikArmored
{

    public ME_210CA1ZSTR()
    {
    }

    protected void moveBayDoor(float f)
    {
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

    static 
    {
        java.lang.Class class1 = ME_210CA1ZSTR.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Me-210");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Me-210Ca-1Zerstorer/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Me-210Ca-1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitME_210CA1ZSTR.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66895F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 10, 10, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_CANNON03"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "MGunPaK40 42"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null
        });
    }
}
