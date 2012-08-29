// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AVIA_B534.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Avia_B5xx, PaintSchemeFMPar00s, NetAircraft, PaintScheme

public class AVIA_B534 extends com.maddox.il2.objects.air.Avia_B5xx
{

    public AVIA_B534()
    {
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = com.maddox.il2.fm.Atmosphere.temperature((float)FM.Loc.z) - 273.15F;
        float f2 = com.maddox.il2.fm.Pitot.Indicator((float)FM.Loc.z, FM.getSpeedKMH());
        if(f2 < 0.0F)
            f2 = 0.0F;
        float f3 = (((FM.EI.engines[0].getControlRadiator() * f * f2) / (f2 + 50F)) * (FM.EI.engines[0].tWaterOut - f1)) / 256F;
        FM.EI.engines[0].tWaterOut -= f3;
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
        java.lang.Class class1 = com.maddox.il2.objects.air.AVIA_B534.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "B-534");
        com.maddox.rts.Property.set(class1, "meshName_sk", "3DO/Plane/AviaB-534/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar00s());
        com.maddox.rts.Property.set(class1, "meshName_de", "3DO/Plane/AviaB-534(de)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_de", new PaintSchemeFMPar00s());
        com.maddox.rts.Property.set(class1, "meshName_hu", "3DO/Plane/AviaB-534(hu)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar00s());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/AviaB-534(multi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00s());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1950F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/AviaB-534.fmd");
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countrySlovakia);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitAVIA_B534.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66F);
        com.maddox.il2.objects.air.AVIA_B534.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 9, 9, 9, 9, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.AVIA_B534.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10"
        });
        com.maddox.il2.objects.air.AVIA_B534.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.AVIA_B534.weaponsRegister(class1, "6*10kg", new java.lang.String[] {
            "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", 
            null, null, null, null, "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ"
        });
        com.maddox.il2.objects.air.AVIA_B534.weaponsRegister(class1, "4*20kg", new java.lang.String[] {
            "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "PylonS328 1", "PylonS328 1", null, null, "PylonS328 1", "PylonS328 1", 
            "BombGun20kgCZ", "BombGun20kgCZ", "BombGun20kgCZ", "BombGun20kgCZ", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.AVIA_B534.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
