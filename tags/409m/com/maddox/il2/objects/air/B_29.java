// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   B_29.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            B_29X, PaintSchemeBMPar05, PaintSchemeFMPar06, TypeBomber, 
//            NetAircraft, Aircraft

public class B_29 extends com.maddox.il2.objects.air.B_29X
    implements com.maddox.il2.objects.air.TypeBomber
{

    public B_29()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.B_29.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "B-29");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/B-29(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/B-29(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1943.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 2800.9F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/B-29.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 10, 10, 10, 11, 11, 12, 12, 13, 13, 
            14, 14, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", 
            "_MGUN11", "_MGUN12", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x1600", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, "BombGun1600lbs 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x300", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun300lbs 3", "BombGun300lbs 3"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20x100", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun50kg 3", "BombGun50kg 3", "BombGun50kg 7", "BombGun50kg 7"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x500", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun500lbs 2", "BombGun500lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x2000", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, "BombGun2000lbs 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x1000", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun1000lbs 2", "BombGun1000lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x2000", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun2000lbs 1", "BombGun2000lbs 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "16x300", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun300lbs 8", "BombGun300lbs 8", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "10x500", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 5", "BombGun500lbs 5", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20x250", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun250lbs 8", "BombGun250lbs 8", "BombGun250lbs 2", "BombGun250lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x1600", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1600lbs 1", "BombGun1600lbs 1", "BombGun1600lbs 2", "BombGun1600lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20x500", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 8", "BombGun500lbs 8", "BombGun500lbs 2", "BombGun500lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12x1000", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 2", "BombGun1000lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x2000", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun2000lbs 1", "BombGun2000lbs 1", "BombGun2000lbs 2", "BombGun2000lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12x1600", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1600lbs 1", "BombGun1600lbs 1", "BombGun1600lbs 2", "BombGun1600lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20x1000", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1000lbs 8", "BombGun1000lbs 8", "BombGun1000lbs 2", "BombGun1000lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "10x2000", new java.lang.String[] {
            "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun2000lbs 3", "BombGun2000lbs 3", "BombGun2000lbs 2", "BombGun2000lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
