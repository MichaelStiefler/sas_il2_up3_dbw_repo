// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   F6F3Early.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F6F, PaintSchemeFMPar03, PaintSchemeFCSPar02, NetAircraft, 
//            Aircraft

public class F6F3Early extends com.maddox.il2.objects.air.F6F
{

    public F6F3Early()
    {
        flapps = 0.0F;
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            for(int i = 1; i < 8; i++)
                hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -22F * f1, 0.0F);

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

    private float flapps;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F6F3Early.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "F6F");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/F6F-3(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar03())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/F6F-3(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFCSPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1939F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/F6F3Early.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitF6F3.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.16055F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 3, 9, 3, 
            3, 9, 9, 9, 9, 9, 9, 2, 2, 2, 
            2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", 
            "_ExternalRock04", "_ExternalRock05", "_ExternalRock06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x150dt", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "FuelTankGun_Tank150gal", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, null, "BombGun100Lbs 1", 
            "BombGun100Lbs 1", null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1001x150dt", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "FuelTankGun_Tank150gal", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, null, "BombGun250lbs 1", 
            "BombGun250lbs 1", null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x2501x150dt", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "FuelTankGun_Tank150gal", "BombGun250lbs 1", 
            "BombGun250lbs 1", null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "PylonF6FPLN1", "BombGun500lbs 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, null, "BombGun500lbs 1", 
            "BombGun500lbs 1", null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x5001x150dt", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "FuelTankGun_Tank150gal", "BombGun500lbs 1", 
            "BombGun500lbs 1", null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "PylonF6FPLN1", "BombGun1000lbs 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
