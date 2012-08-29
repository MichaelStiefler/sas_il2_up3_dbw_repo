// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   YAK_1B.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK, PaintSchemeFMPar02, PaintSchemeFCSPar05, TypeTNBFighter, 
//            NetAircraft

public class YAK_1B extends com.maddox.il2.objects.air.YAK
    implements com.maddox.il2.objects.air.TypeTNBFighter
{

    public YAK_1B()
    {
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("Wind_luk", 0.0F, FM.EI.engines[0].getControlRadiator() * 19F, 0.0F);
        hierMesh().chunkSetAngles("Water_luk", 0.0F, FM.EI.engines[0].getControlRadiator() * 15F, 0.0F);
        super.update(f);
    }

    public void update_windluk(float f)
    {
        super.update(f);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.YAK_1B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Yak-1B(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_fr", "3DO/Plane/Yak-1B(fr)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fr", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1942.6F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak-1B.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitYAK_1.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.6759F);
        com.maddox.il2.objects.air.YAK_1B.weaponTriggersRegister(class1, new int[] {
            0, 1, 9, 9, 9, 9, 9, 9, 2, 2, 
            2, 2, 2, 2, 3, 3
        });
        com.maddox.il2.objects.air.YAK_1B.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", 
            "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.YAK_1B.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBsi 201", "MGunShVAKki 120", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.YAK_1B.weaponsRegister(class1, "6rs82", new java.lang.String[] {
            "MGunUBsi 201", "MGunShVAKki 120", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "RocketGunRS82", "RocketGunRS82", 
            "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null
        });
        com.maddox.il2.objects.air.YAK_1B.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunUBsi 201", "MGunShVAKki 120", null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1"
        });
        com.maddox.il2.objects.air.YAK_1B.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
