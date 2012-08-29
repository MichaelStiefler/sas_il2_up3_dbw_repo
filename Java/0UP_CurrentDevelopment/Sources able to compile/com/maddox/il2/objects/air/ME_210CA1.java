package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class ME_210CA1 extends ME_210
    implements TypeFighter, TypeBNZFighter, TypeStormovik, TypeStormovikArmored, TypeDiveBomber
{

    public ME_210CA1()
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

    public boolean typeDiveBomberToggleAutomation()
    {
        return false;
    }

    public void typeDiveBomberAdjAltitudeReset()
    {
    }

    public void typeDiveBomberAdjAltitudePlus()
    {
    }

    public void typeDiveBomberAdjAltitudeMinus()
    {
    }

    public void typeDiveBomberAdjVelocityReset()
    {
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
    }

    public void typeDiveBomberAdjDiveAngleReset()
    {
    }

    public void typeDiveBomberAdjDiveAnglePlus()
    {
    }

    public void typeDiveBomberAdjDiveAngleMinus()
    {
    }

    public void typeDiveBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeDiveBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = ME_210CA1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Me-210");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Me-210Ca-1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Me-210Ca-1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitME_210CA1.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66895F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 10, 10, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null
        });
        Aircraft.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunSC250", "BombGunSC250", null
        });
        Aircraft.weaponsRegister(class1, "2ab250", new java.lang.String[] {
            "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunAB250", "BombGunAB250", null
        });
        Aircraft.weaponsRegister(class1, "2sc500", new java.lang.String[] {
            "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunSC500", "BombGunSC500", null
        });
        Aircraft.weaponsRegister(class1, "2ab500", new java.lang.String[] {
            "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunAB500", "BombGunAB500", null
        });
        Aircraft.weaponsRegister(class1, "2sd500", new java.lang.String[] {
            "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunSD500", "BombGunSD500", null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
