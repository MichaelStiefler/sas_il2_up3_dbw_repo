// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   P_47D.java

package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_47, PaintSchemeFMPar05, PaintSchemeFMPar06, TypeFighterAceMaker, 
//            NetAircraft

public class P_47D extends com.maddox.il2.objects.air.P_47
    implements com.maddox.il2.objects.air.TypeFighterAceMaker
{

    public P_47D()
    {
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
    }

    public boolean typeFighterAceMakerToggleAutomation()
    {
        k14Mode++;
        if(k14Mode > 2)
            k14Mode = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + k14Mode);
        return true;
    }

    public void typeFighterAceMakerAdjDistanceReset()
    {
    }

    public void typeFighterAceMakerAdjDistancePlus()
    {
        k14Distance += 10F;
        if(k14Distance > 800F)
            k14Distance = 800F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
    }

    public void typeFighterAceMakerAdjDistanceMinus()
    {
        k14Distance -= 10F;
        if(k14Distance < 200F)
            k14Distance = 200F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
    }

    public void typeFighterAceMakerAdjSideslipReset()
    {
    }

    public void typeFighterAceMakerAdjSideslipPlus()
    {
        k14WingspanType--;
        if(k14WingspanType < 0)
            k14WingspanType = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerAdjSideslipMinus()
    {
        k14WingspanType++;
        if(k14WingspanType > 9)
            k14WingspanType = 9;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeByte(k14Mode);
        netmsgguaranted.writeByte(k14WingspanType);
        netmsgguaranted.writeFloat(k14Distance);
    }

    public void typeFighterAceMakerReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        k14Mode = ((int) (netmsginput.readByte()));
        k14WingspanType = ((int) (netmsginput.readByte()));
        k14Distance = netmsginput.readFloat();
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

    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_47D.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "P-47");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/P-47D(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/P-47D(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "noseart", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1944F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1947.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/P-47D-27_late.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitP_47D25.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.1104F);
        com.maddox.il2.objects.air.P_47D.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 1, 1, 9, 3, 
            3, 3, 9, 9, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.P_47D.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev01", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalBomb01", "_ExternalDev02", "_ExternalDev03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06"
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "overload", new java.lang.String[] {
            "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "tank", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "tank2x500", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", "BombGun500lbs", 
            "BombGun500lbs", null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "tank6x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", null, 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "tank2x5006x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", "BombGun500lbs", 
            "BombGun500lbs", null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "6x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", 
            "BombGun500lbs", null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "2x5006x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", 
            "BombGun500lbs", null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "1x1000", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, 
            null, "BombGun1000lbs", null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "1x10002x500", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", 
            "BombGun500lbs", "BombGun1000lbs", null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "1x10006x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, 
            null, "BombGun1000lbs", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "1x10002x5006x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", 
            "BombGun500lbs", "BombGun1000lbs", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.P_47D.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
