// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BattleMkII.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            FaireyBattle, PaintSchemeBMPar00, TypeBomber, BombsightNorden, 
//            NetAircraft

public class BattleMkII extends com.maddox.il2.objects.air.FaireyBattle
    implements com.maddox.il2.objects.air.TypeBomber
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
        return com.maddox.il2.objects.air.BombsightNorden.ToggleAutomation();
    }

    public void typeBomberAdjDistanceReset()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjDistanceReset();
    }

    public void typeBomberAdjDistancePlus()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjDistancePlus();
    }

    public void typeBomberAdjDistanceMinus()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjDistanceMinus();
    }

    public void typeBomberAdjSideslipReset()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjSideslipReset();
    }

    public void typeBomberAdjSideslipPlus()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjSideslipPlus();
    }

    public void typeBomberAdjSideslipMinus()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjSideslipMinus();
    }

    public void typeBomberAdjAltitudeReset()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjAltitudeReset();
    }

    public void typeBomberAdjAltitudePlus()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjAltitudePlus();
    }

    public void typeBomberAdjAltitudeMinus()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjAltitudeMinus();
    }

    public void typeBomberAdjSpeedReset()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjSpeedReset();
    }

    public void typeBomberAdjSpeedPlus()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjSpeedPlus();
    }

    public void typeBomberAdjSpeedMinus()
    {
        com.maddox.il2.objects.air.BombsightNorden.AdjSpeedMinus();
    }

    public void typeBomberUpdate(float f)
    {
        com.maddox.il2.objects.air.BombsightNorden.Update(f);
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
            com.maddox.il2.objects.air.BombsightNorden.SetActiveBombNames(new java.lang.String[] {
                "FAB-100"
            });
            com.maddox.il2.objects.air.BombsightNorden.ResetAll(1, this);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private float bpos;
    private float bcurpos;
    private long btme;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BattleMkII.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FaireyBattle");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/FaireyBattle(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1941.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/BattleMkII.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBattleMkII.class, com.maddox.il2.objects.air.Cockpit_BombsightNordenSimple.class, com.maddox.il2.objects.air.CockpitBattleMkII_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7394F);
        com.maddox.il2.objects.air.BattleMkII.weaponTriggersRegister(class1, new int[] {
            0, 10, 9, 3, 9, 3, 9, 3, 3, 3, 
            3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.BattleMkII.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalBomb02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", 
            "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11"
        });
        com.maddox.il2.objects.air.BattleMkII.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 400", "MGunVikkersKt 485", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BattleMkII.weaponsRegister(class1, "4x250lb", new java.lang.String[] {
            "MGunBrowning303k 400", "MGunVikkersKt 485", null, null, null, null, null, null, null, null, 
            "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, "BombGun250lbsE 1", "BombGun250lbsE 1"
        });
        com.maddox.il2.objects.air.BattleMkII.weaponsRegister(class1, "1x500lb", new java.lang.String[] {
            "MGunBrowning303k 400", "MGunVikkersKt 485", null, null, "PylonB5NPLN1 1", "BombGun500lbsE 1", null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BattleMkII.weaponsRegister(class1, "6x250lb", new java.lang.String[] {
            "MGunBrowning303k 400", "MGunVikkersKt 485", null, null, null, null, null, null, null, null, 
            "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1"
        });
        com.maddox.il2.objects.air.BattleMkII.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
