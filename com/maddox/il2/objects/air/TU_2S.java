// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TU_2S.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            TU_2, PaintSchemeBMPar05, TypeBomber, NetAircraft, 
//            Aircraft

public class TU_2S extends com.maddox.il2.objects.air.TU_2
    implements com.maddox.il2.objects.air.TypeBomber
{

    public TU_2S()
    {
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 85F * f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.TU_2S.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Tu-2");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Tu-2S/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1942.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1956.6F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Tu-2S.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 10, 11, 12, 3, 3, 3, 3, 3, 
            3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01", "_BombSpawn02", 
            "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6fab50", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8fab50", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB50", "BombGunFAB50", null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB100", "BombGunFAB100", null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB250", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab2506fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, null, null, 
            "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, null, null, 
            "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB500", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab5006fab50", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab5006fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab5004fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, 
            "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB500", "BombGunFAB500", 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, 
            "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab1000", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB1000", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab10002fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", "BombGunFAB1000", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab1000", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab10006fab50", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab10006fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab10002fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB250", "BombGunFAB250", 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab10001fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB500", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3fab1000", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab2000", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB2000", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
