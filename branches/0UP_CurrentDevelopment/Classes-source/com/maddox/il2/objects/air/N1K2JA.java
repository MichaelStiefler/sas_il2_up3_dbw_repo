// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   N1K2JA.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            N1K2, PaintSchemeFMPar01, PaintSchemeFCSPar05, Aircraft, 
//            NetAircraft

public class N1K2JA extends com.maddox.il2.objects.air.N1K2
{

    public N1K2JA()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.35F, 0.95F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.35F, 0.4F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.35F, 0.95F, 0.0F, -48F), 0.0F);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.35F, 0.95F, 0.0F, -58F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.65F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.1F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.65F, 0.0F, -48F), 0.0F);
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.65F, 0.0F, -58F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 1.0F, -0.075F, 0.0F);
        com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 1.0F, 40F, 0.0F);
        hiermesh.chunkSetLocate("GearC2_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.N1K2JA.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        if(FM.CT.getGear() == 1.0F)
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.1F, 0.0F, 20F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.23F, 0.0F, 0.23F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.23F, 0.0F, -42F), 0.0F);
        hierMesh().chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.23F, 0.0F, -45F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, 0.27625F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, -33F), 0.0F);
        hierMesh().chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, -66F), 0.0F);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            for(int i = 1; i < 9; i++)
                hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -20F * f1, 0.0F);

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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.N1K2JA.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "N1K");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/N1K2-Ja(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/N1K2-Ja(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/N1K2-Ja_mod.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitN1K2JA.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.1716F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 0, 0, 1, 3, 3, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x400dt", new java.lang.String[] {
            "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, "PylonN1K1PLN1", "FuelTankGun_TankN1K1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20mmx900", new java.lang.String[] {
            "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20mmx900+1x400dt", new java.lang.String[] {
            "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, "PylonN1K1PLN1", "FuelTankGun_TankN1K1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20mmx900+2x60", new java.lang.String[] {
            "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20mmx900+4x60", new java.lang.String[] {
            "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20mmx900+2x100", new java.lang.String[] {
            "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", "BombGun100kgJ 1", "BombGun100kgJ 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20mmx900+4x100", new java.lang.String[] {
            "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20mmx900+2x250", new java.lang.String[] {
            "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", "BombGun250kgJ 1", "BombGun250kgJ 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
