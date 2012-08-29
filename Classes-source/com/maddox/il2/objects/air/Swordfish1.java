// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Swordfish1.java

package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Swordfish, PaintSchemeBMPar01, PaintSchemeBMPar02f, NetAircraft, 
//            PaintScheme

public class Swordfish1 extends com.maddox.il2.objects.air.Swordfish
{

    public Swordfish1()
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
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Swordfish");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Swordfish1(multi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/Swordfish1(gb)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeBMPar02f());
        com.maddox.rts.Property.set(class1, "meshName_rn", "3DO/Plane/Swordfish1(gb)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_rn", new PaintSchemeBMPar02f());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Swordfish.fmd");
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryBritain);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSwordfish.class, com.maddox.il2.objects.air.CockpitSwordfish_TAG.class
        });
        com.maddox.il2.objects.air.Swordfish1.weaponTriggersRegister(class1, new int[] {
            0, 10, 3, 3, 3, 3, 3, 3, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Swordfish1.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_turret1", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", 
            "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", 
            "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb01", "_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26", "_ExternalBomb27", "_ExternalBomb28", 
            "_ExternalBomb29", "_ExternalBomb30", "_ExternalBomb31", "_ExternalBomb32", "_ExternalBomb33"
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "1_1xTorpedo", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunTorpMk12", null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "2_3x500lb", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", "BombGun500lbsE", "BombGun500lbsE", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGun500lbsE", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "3_1x500lb+4x250lb", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGun500lbsE", null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "4_1x500lb+8x100lb", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "BombGun500lbsE", "BombGunNull", "BombGun50kg", "BombGun50kg", "BombGun50kg", 
            "BombGun50kg", "BombGun50kg", "BombGun50kg", "BombGun50kg", "BombGun50kg"
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "5_3x500lb+8xflare", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", "BombGun500lbsE", "BombGun500lbsE", null, null, null, null, "BombGunParaFlareUK", "BombGunNull", 
            "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", 
            "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", null, "BombGun500lbsE", null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "6_1xtorpedo+8xflare", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, "BombGunParaFlareUK", "BombGunNull", 
            "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", 
            "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunTorpMk12", null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "7_1x500lb+4x250lb+8xflare", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGunParaFlareUK", "BombGunNull", 
            "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", 
            "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", null, "BombGun500lbsE", null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "8_1x500lb+8xFlare_AI", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "BombGun500lbsE", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK", 
            "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK"
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "4x250lb+8xflare", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGunParaFlareUK", "BombGunNull", 
            "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", 
            "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "6x250lb", new java.lang.String[] {
            "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGun250lbsE", "BombGun250lbsE", null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Swordfish1.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
