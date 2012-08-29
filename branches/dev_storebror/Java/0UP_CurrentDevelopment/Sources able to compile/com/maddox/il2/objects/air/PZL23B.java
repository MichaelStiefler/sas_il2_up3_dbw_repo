package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class PZL23B extends PZL23xyz
    implements TypeDiveBomber
{

    public PZL23B()
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    public void doKillPilot(int i)
    {
        super.doKillPilot(i);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    public void doMurderPilot(int i)
    {
        super.doMurderPilot(i);
        if(super.FM.isPlayers())
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
        java.lang.Class class1 = PZL23B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "PZL23");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/PZL23B(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "meshName_pl", "3DO/Plane/PZL23B/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_pl", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "meshName_ro", "3DO/Plane/PZL23B(Romanian)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/PZL23B.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitPZL23B.class, CockpitPZL23B_TGunner.class, CockpitPZL23B_BGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.87195F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 10, 11, 3, 3, 3, 3, 3, 3, 3, 
            3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", 
            "_ExternalBomb08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunVikkersKt 680", "MGunVikkersKt 380", null, null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "6xPuW50", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunVikkersKt 680", "MGunVikkersKt 380", null, null, "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW50 1", 
            "BombGunPuW50 1"
        });
        Aircraft.weaponsRegister(class1, "8xPuW50", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunVikkersKt 680", "MGunVikkersKt 380", "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW50 1", 
            "BombGunPuW50 1"
        });
        Aircraft.weaponsRegister(class1, "6xPuW100", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunVikkersKt 680", "MGunVikkersKt 380", null, null, "BombGunPuW100 1", "BombGunPuW100 1", "BombGunPuW100 1", "BombGunPuW100 1", "BombGunPuW100 1", 
            "BombGunPuW100 1"
        });
        Aircraft.weaponsRegister(class1, "6xPuW100+2xPuW50", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunVikkersKt 680", "MGunVikkersKt 380", "BombGunPuW50 1", "BombGunPuW50 1", "BombGunPuW100 1", "BombGunPuW100 1", "BombGunPuW100 1", "BombGunPuW100 1", "BombGunPuW100 1", 
            "BombGunPuW100 1"
        });
        Aircraft.weaponsRegister(class1, "6x50kg", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunVikkersKt 680", "MGunVikkersKt 380", null, null, "BombGun50kg 1", "BombGun50kg 1", "BombGun50kg 1", "BombGun50kg 1", "BombGun50kg 1", 
            "BombGun50kg 1"
        });
        Aircraft.weaponsRegister(class1, "8x50kg", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunVikkersKt 680", "MGunVikkersKt 380", "BombGun50kg 1", "BombGun50kg 1", "BombGun50kg 1", "BombGun50kg 1", "BombGun50kg 1", "BombGun50kg 1", "BombGun50kg 1", 
            "BombGun50kg 1"
        });
        Aircraft.weaponsRegister(class1, "6x100kg", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunVikkersKt 680", "MGunVikkersKt 380", null, null, "BombGun100kg 1", "BombGun100kg 1", "BombGun100kg 1", "BombGun100kg 1", "BombGun100kg 1", 
            "BombGun100kg 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
