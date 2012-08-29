package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class BattleMkII extends FaireyBattle
    implements TypeBomber
{

    public BattleMkII()
    {
        bpos = 1.0F;
        bcurpos = 1.0F;
        btme = -1L;
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f < -31F)
            {
                f = -31F;
                flag = false;
            }
            if(f > 31F)
            {
                f = 31F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 52F)
            {
                f1 = 52F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public boolean typeBomberToggleAutomation()
    {
        return BombsightNorden.ToggleAutomation();
    }

    public void typeBomberAdjDistanceReset()
    {
        BombsightNorden.AdjDistanceReset();
    }

    public void typeBomberAdjDistancePlus()
    {
        BombsightNorden.AdjDistancePlus();
    }

    public void typeBomberAdjDistanceMinus()
    {
        BombsightNorden.AdjDistanceMinus();
    }

    public void typeBomberAdjSideslipReset()
    {
        BombsightNorden.AdjSideslipReset();
    }

    public void typeBomberAdjSideslipPlus()
    {
        BombsightNorden.AdjSideslipPlus();
    }

    public void typeBomberAdjSideslipMinus()
    {
        BombsightNorden.AdjSideslipMinus();
    }

    public void typeBomberAdjAltitudeReset()
    {
        BombsightNorden.AdjAltitudeReset();
    }

    public void typeBomberAdjAltitudePlus()
    {
        BombsightNorden.AdjAltitudePlus();
    }

    public void typeBomberAdjAltitudeMinus()
    {
        BombsightNorden.AdjAltitudeMinus();
    }

    public void typeBomberAdjSpeedReset()
    {
        BombsightNorden.AdjSpeedReset();
    }

    public void typeBomberAdjSpeedPlus()
    {
        BombsightNorden.AdjSpeedPlus();
    }

    public void typeBomberAdjSpeedMinus()
    {
        BombsightNorden.AdjSpeedMinus();
    }

    public void typeBomberUpdate(float f)
    {
        BombsightNorden.Update(f);
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.isPlayers())
        {
            BombsightNorden.SetActiveBombNames(new java.lang.String[] {
                "FAB-100"
            });
            BombsightNorden.ResetAll(1, this);
        }
    }

    private float bpos;
    private float bcurpos;
    private long btme;

    static 
    {
        java.lang.Class class1 = BattleMkII.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FaireyBattle");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/FaireyBattle(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1941.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/BattleMkII.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBattleMkII.class, Cockpit_BombsightNordenSimple.class, CockpitBattleMkII_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7394F);
        BattleMkII.weaponTriggersRegister(class1, new int[] {
            0, 10, 9, 3, 9, 3, 9, 3, 3, 3, 
            3, 3, 3, 3, 3, 3
        });
        BattleMkII.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalBomb02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", 
            "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11"
        });
        BattleMkII.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 400", "MGunVikkersKt 485", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        BattleMkII.weaponsRegister(class1, "4x250lb", new java.lang.String[] {
            "MGunBrowning303k 400", "MGunVikkersKt 485", null, null, null, null, null, null, null, null, 
            "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, "BombGun250lbsE 1", "BombGun250lbsE 1"
        });
        BattleMkII.weaponsRegister(class1, "1x500lb", new java.lang.String[] {
            "MGunBrowning303k 400", "MGunVikkersKt 485", null, null, "PylonB5NPLN1 1", "BombGun500lbsE 1", null, null, null, null, 
            null, null, null, null, null, null
        });
        BattleMkII.weaponsRegister(class1, "6x250lb", new java.lang.String[] {
            "MGunBrowning303k 400", "MGunVikkersKt 485", null, null, null, null, null, null, null, null, 
            "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1"
        });
        BattleMkII.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
