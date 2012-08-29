// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_2_1940Early.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            IL_2, PaintSchemeBMPar02, PaintSchemeBCSPar02, NetAircraft

public class IL_2_1940Early extends com.maddox.il2.objects.air.IL_2
{

    public IL_2_1940Early()
    {
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -20F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -20F * f, 0.0F);
        hierMesh().chunkSetAngles("FlettnerL_D0", 0.0F, 40F * f, 0.0F);
        hierMesh().chunkSetAngles("FlettnerR_D0", 0.0F, 40F * f, 0.0F);
        hierMesh().chunkSetAngles("FlettnerRodL_D0", 0.0F, -37F * f, 0.0F);
        hierMesh().chunkSetAngles("FlettnerRodR_D0", 0.0F, -37F * f, 0.0F);
        hierMesh().chunkSetAngles("WeightL_D0", 0.0F, 20F * f, 0.0F);
        hierMesh().chunkSetAngles("WeightR_D0", 0.0F, 20F * f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.IL_2_1940Early.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "IL2");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Il-2-1940Early(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3do/plane/Il-2-1940Early/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeBCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Il-2-1940.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitIL_2_1940.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.81F);
        com.maddox.rts.Property.set(class1, "Handicap", 1.0F);
        com.maddox.il2.objects.air.IL_2_1940Early.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 
            9, 9, 3, 3
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_Cannon01", "_Cannon02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", 
            "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalDev06", "_BombSpawn01", "_BombSpawn02"
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "8xRS82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "30xAO10", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunAO10 7", "BombGunAO10 7", "BombGunAO10 8", "BombGunAO10 8", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", 
            null, null, null, null
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "4xFAB50", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "6xFAB50", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "4xFAB100", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "6xFAB100", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "2xFAB250", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "2xVAP250", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunShVAKk 210", "MGunShVAKk 210", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonVAP250", "PylonVAP250", "BombGunPhBall", "BombGunPhBall"
        });
        com.maddox.il2.objects.air.IL_2_1940Early.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
