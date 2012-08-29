// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   YAK_9B.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK, PaintSchemeFMPar05, TypeBNZFighter, TypeStormovik, 
//            NetAircraft, Aircraft

public class YAK_9B extends com.maddox.il2.objects.air.YAK
    implements com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeStormovik
{

    public YAK_9B()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -80F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 80F * f, 0.0F);
        f1 = java.lang.Math.max(-f * 1500F, -60F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 82.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 82.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -85F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.YAK_9B.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90F * f, 0.0F);
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("Water_luk", 0.0F, 12F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        super.update(f);
        int i = 0;
        if(FM.CT.Weapons[3] != null)
        {
            for(int j = 0; j < FM.CT.Weapons[3].length; j++)
                if(FM.CT.Weapons[3][j] != null && FM.CT.Weapons[3][j].haveBullets())
                    i++;

        }
        float f1 = 0.14F;
        switch(i)
        {
        case 0: // '\0'
        default:
            FM.setGCenter(0.1F);
            FM.setGC_Gear_Shift(0.0F);
            break;

        case 1: // '\001'
            FM.setGCenter(0.1F - f1);
            FM.setGC_Gear_Shift(0.0F + f1);
            // fall through

        case 2: // '\002'
            FM.setGCenter(0.1F - 2.0F * f1);
            FM.setGC_Gear_Shift(0.0F + 2.0F * f1);
            break;

        case 3: // '\003'
            FM.setGCenter(0.1F - 3F * f1);
            FM.setGC_Gear_Shift(0.0F + 3F * f1);
            break;

        case 4: // '\004'
            FM.setGCenter(0.1F - 4F * f1);
            FM.setGC_Gear_Shift(0.0F + 4F * f1);
            break;
        }
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
        java.lang.Class class1 = com.maddox.il2.objects.air.YAK_9B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Yak-9B(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1952.8F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak-9B.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitYAK_9D.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.6432F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 3, 3, 3, 3, 3, 3, 3, 3, 
            9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", 
            "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBsi 200", "MGunShVAKki 120", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunUBsi 200", "MGunShVAKki 120", null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3fab100", new java.lang.String[] {
            "MGunUBsi 200", "MGunShVAKki 120", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4fab100", new java.lang.String[] {
            "MGunUBsi 200", "MGunShVAKki 120", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2ptab", new java.lang.String[] {
            "MGunUBsi 200", "MGunShVAKki 120", null, null, null, null, null, null, "BombGunPTAB25", "BombGunPTAB25", 
            "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4ptab", new java.lang.String[] {
            "MGunUBsi 200", "MGunShVAKki 120", null, null, null, null, "BombGunPTAB25", "BombGunPTAB25", "BombGunPTAB25", "BombGunPTAB25", 
            "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
