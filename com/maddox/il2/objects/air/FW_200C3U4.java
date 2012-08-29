// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FW_200C3U4.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_200, PaintSchemeBMPar04, TypeBomber, NetAircraft, 
//            Aircraft

public class FW_200C3U4 extends com.maddox.il2.objects.air.FW_200
    implements com.maddox.il2.objects.air.TypeBomber
{

    public FW_200C3U4()
    {
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
    }

    public void typeBomberAdjSpeedMinus()
    {
    }

    public void typeBomberUpdate(float f)
    {
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
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_200C3U4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW200");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/FW-200C-3U4/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1941.1F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1949F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/FW-200C-3U4.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 15, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_BombSpawn01", "_BombSpawn02", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", 
            "_BombSpawn11", "_BombSpawn12", "_BombSpawn13", "_BombSpawn14"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12sc50", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, 
            null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12sc502sc250", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC250", "BombGunSC250", 
            null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12sc504sc250", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC250", "BombGunSC250", 
            "BombGunSC250", "BombGunSC250", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12sc504sc500", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC500", "BombGunSC500", 
            "BombGunSC500", "BombGunSC500", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12sc502sc1000", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", 
            null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12sc504sc1000", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", 
            "BombGunSC1000", "BombGunSC1000", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sc250", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", null, null, 
            "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6sc250", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", 
            "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc500", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sc500", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", null, null, 
            "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6sc500", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", 
            "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sd500", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sd500", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", null, null, 
            "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6sd500", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500", 
            "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc1000", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, 
            "BombGunSC1000", "BombGunSC1000", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sc1000", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", 
            "BombGunSC1000", "BombGunSC1000", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc1800", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, 
            "BombGunSC1800", "BombGunSC1800", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2ab1000", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, 
            "BombGunAB1000", "BombGunAB1000", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4ab1000", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunAB1000", "BombGunAB1000", 
            "BombGunAB1000", "BombGunAB1000", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2pc1600", new java.lang.String[] {
            "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, 
            "BombGunPC1600", "BombGunPC1600", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
