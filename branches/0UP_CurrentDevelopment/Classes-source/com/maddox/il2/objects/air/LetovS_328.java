// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LetovS_328.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Letov, PaintSchemeBMPar00s, NetAircraft, PaintScheme, 
//            Aircraft

public class LetovS_328 extends com.maddox.il2.objects.air.Letov
{

    public LetovS_328()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.LetovS_328.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "S-328");
        com.maddox.rts.Property.set(class1, "meshName", "3do/Plane/LetovS-328/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00s());
        com.maddox.rts.Property.set(class1, "meshName_de", "3do/Plane/LetovS-328_DE/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_de", new PaintSchemeBMPar00s());
        com.maddox.rts.Property.set(class1, "meshName_sk", "3do/Plane/LetovS-328_SK/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_sk", new PaintSchemeBMPar00s());
        com.maddox.rts.Property.set(class1, "yearService", 1935F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1950F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/LetovS-328.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.Cockpit_RanwersLetov.class
        });
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countrySlovakia);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 10, 9, 9, 9, 9, 9, 9, 
            9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalDev07", "_ExternalDev08", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", 
            "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", 
            "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8*10kg", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", 
            "PylonS328 1", "PylonS328 1", null, null, null, null, null, "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", 
            "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4*20kg", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", null, null, "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", 
            "PylonS328 1", "PylonS328 1", null, null, null, null, null, null, null, "BombGun20kgCZ", 
            "BombGun20kgCZ", null, null, "BombGun20kgCZ", "BombGun20kgCZ", null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6*20kg", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", 
            "PylonS328 1", "PylonS328 1", null, null, null, null, null, "BombGun20kgCZ", "BombGun20kgCZ", "BombGun20kgCZ", 
            "BombGun20kgCZ", null, null, "BombGun20kgCZ", "BombGun20kgCZ", null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2*20kg+6*10kg", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", 
            "PylonS328 1", "PylonS328 1", null, null, null, null, null, "BombGun20kgCZ", "BombGun20kgCZ", "BombGun10kgCZ", 
            "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2*50kgCZ", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "BombGun50kgCZ", "BombGun50kgCZ", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1*100kgCZ", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "BombGun100kgCZ"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2*50kg", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, 
            null, null, null, null, "BombGun50kg", "BombGun50kg", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1*100kg", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, 
            null, null, null, null, null, null, "BombGun100kg", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1*ParaFlare", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, 
            null, null, "BombGunParaFlare", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2*ParaFlare", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, 
            null, null, "BombGunParaFlare", "BombGunParaFlare", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1*ParaFlare+2*20kg", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", 
            "PylonS328 1", "PylonS328 1", "BombGunParaFlare", null, null, null, null, null, null, "BombGun20kgCZ", 
            "BombGun20kgCZ", null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2*ParaFlare+6*10kg", new java.lang.String[] {
            "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", 
            "PylonS328 1", "PylonS328 1", "BombGunParaFlare", "BombGunParaFlare", null, null, null, null, null, "BombGun10kgCZ", 
            "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
