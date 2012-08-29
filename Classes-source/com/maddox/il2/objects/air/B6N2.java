// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   B6N2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            B6N, PaintSchemeBMPar00, PaintSchemeFCSPar02, TypeBomber, 
//            BombsightOPB, NetAircraft, Aircraft

public class B6N2 extends com.maddox.il2.objects.air.B6N
    implements com.maddox.il2.objects.air.TypeBomber
{

    public B6N2()
    {
    }

    public boolean typeBomberToggleAutomation()
    {
        if(FM.isPlayers())
        {
            FM.CT.setTrimAileronControl(0.07F);
            FM.CT.setTrimElevatorControl(-0.23F);
            FM.CT.setTrimRudderControl(0.18F);
        }
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjDistanceReset();
    }

    public void typeBomberAdjDistancePlus()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjDistancePlus();
    }

    public void typeBomberAdjDistanceMinus()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjDistanceMinus();
    }

    public void typeBomberAdjSideslipReset()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjSideslipReset();
    }

    public void typeBomberAdjSideslipPlus()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjSideslipPlus();
    }

    public void typeBomberAdjSideslipMinus()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjSideslipMinus();
    }

    public void typeBomberAdjAltitudeReset()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjAltitudeReset();
    }

    public void typeBomberAdjAltitudePlus()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjAltitudePlus();
    }

    public void typeBomberAdjAltitudeMinus()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjAltitudeMinus();
    }

    public void typeBomberAdjSpeedReset()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjSpeedReset();
    }

    public void typeBomberAdjSpeedPlus()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjSpeedPlus();
    }

    public void typeBomberAdjSpeedMinus()
    {
        com.maddox.il2.objects.air.BombsightOPB.AdjSpeedMinus();
    }

    public void typeBomberUpdate(float f)
    {
        com.maddox.il2.objects.air.BombsightOPB.Update(f);
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.B6N2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "B6N");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/B6N2(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/B6N2(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/B6N2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitB6N2.class, com.maddox.il2.objects.air.Cockpit_BombsightOPB.class, com.maddox.il2.objects.air.CockpitB6N2_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7394F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 9, 3, 9, 3, 9, 3, 3, 9, 
            9, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalBomb02", "_ExternalDev06", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x30", new java.lang.String[] {
            "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null, null, null, null, null, "PylonB6NPLN1", 
            "PylonB6NPLN1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x50", new java.lang.String[] {
            "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null, null, null, null, null, "PylonB6NPLN1", 
            "PylonB6NPLN1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, "PylonB5NPLN1", "BombGun250kgJ 1", null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null, null, "PylonB6NPLN1", "BombGun250kgJ 1", "BombGun250kgJ 1", null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, "PylonB5NPLN1", "BombGun500kgJ 1", null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x600", new java.lang.String[] {
            "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, "PylonB5NPLN1", "BombGun600kgJ 1", null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x91", new java.lang.String[] {
            "MGunVikkersKt 500", "MGunVikkersKt 500", "PylonB5NPLN0", "BombGunTorpMk13 1", null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
    }
}
