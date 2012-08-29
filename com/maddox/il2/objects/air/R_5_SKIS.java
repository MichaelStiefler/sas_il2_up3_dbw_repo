// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   R_5_SKIS.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            R_5xyz, PaintSchemeFCSPar08, TypeBomber, Aircraft, 
//            NetAircraft

public class R_5_SKIS extends com.maddox.il2.objects.air.R_5xyz
    implements com.maddox.il2.objects.air.TypeBomber
{

    public R_5_SKIS()
    {
        skiAngleL = 0.0F;
        skiAngleR = 0.0F;
        spring = 0.15F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.CT.bHasBrakeControl = false;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 9: // '\t'
            hierMesh().chunkVisible("SkiL2_D0", false);
            break;

        case 10: // '\n'
            hierMesh().chunkVisible("SkiR2_D0", false);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveFan(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            boolean flag = false;
            float f1 = com.maddox.il2.objects.air.Aircraft.cvt(FM.getSpeed(), 20F, 50F, 1.0F, 0.0F);
            float f2 = com.maddox.il2.objects.air.Aircraft.cvt(FM.getSpeed(), 0.0F, 20F, 0.0F, 0.5F);
            if(FM.Gears.gWheelSinking[0] > 0.0F)
            {
                flag = true;
                skiAngleL = 0.5F * skiAngleL + 0.5F * FM.Or.getTangage();
                if(skiAngleL > 20F)
                    skiAngleL = skiAngleL - spring;
                hierMesh().chunkSetAngles("SkiL0_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-f2, f2), com.maddox.il2.ai.World.Rnd().nextFloat(-f2 * 2.0F, f2 * 2.0F) - skiAngleL, com.maddox.il2.ai.World.Rnd().nextFloat(f2, f2));
            } else
            {
                if((double)skiAngleL > (double)(f1 * -10F) + 0.01D)
                {
                    skiAngleL = skiAngleL - spring;
                    flag = true;
                } else
                if((double)skiAngleL < (double)(f1 * -10F) - 0.01D)
                {
                    skiAngleL = skiAngleL + spring;
                    flag = true;
                }
                hierMesh().chunkSetAngles("SkiL0_D0", 0.0F, -skiAngleL, 0.0F);
            }
            if(FM.Gears.gWheelSinking[1] > 0.0F)
            {
                flag = true;
                skiAngleR = 0.5F * skiAngleR + 0.5F * FM.Or.getTangage();
                if(skiAngleR > 20F)
                    skiAngleR = skiAngleR - spring;
                hierMesh().chunkSetAngles("SkiR0_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-f2, f2), com.maddox.il2.ai.World.Rnd().nextFloat(-f2 * 2.0F, f2 * 2.0F) - skiAngleR, com.maddox.il2.ai.World.Rnd().nextFloat(f2, f2));
                if(FM.Gears.gWheelSinking[0] == 0.0F && FM.Or.getRoll() < 365F && FM.Or.getRoll() > 355F)
                {
                    skiAngleL = skiAngleR;
                    hierMesh().chunkSetAngles("SkiL0_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-f2, f2), com.maddox.il2.ai.World.Rnd().nextFloat(-f2 * 2.0F, f2 * 2.0F) - skiAngleL, com.maddox.il2.ai.World.Rnd().nextFloat(f2, f2));
                }
            } else
            {
                if((double)skiAngleR > (double)(f1 * -10F) + 0.01D)
                {
                    skiAngleR = skiAngleR - spring;
                    flag = true;
                } else
                if((double)skiAngleR < (double)(f1 * -10F) - 0.01D)
                {
                    skiAngleR = skiAngleR + spring;
                    flag = true;
                }
                hierMesh().chunkSetAngles("SkiR0_D0", 0.0F, -skiAngleR, 0.0F);
            }
            if(!flag && f1 == 0.0F)
            {
                super.moveFan(f);
                return;
            }
            hierMesh().chunkSetAngles("SkiC_D0", 0.0F, (skiAngleL + skiAngleR) / 2.0F, 0.0F);
            float f3 = skiAngleL / 20F;
            hierMesh().chunkSetAngles("SkiL1_D0", 0.0F, f3 * -2F + 0.25F * suspL, f3 * 8.25F + 3F * suspL);
            hierMesh().chunkSetAngles("SkiL2_D0", 0.0F, f3 * -7F + 1.25F * suspL, f3 * -6.25F + 2.75F * suspL);
            if(skiAngleL < 0.0F)
            {
                hierMesh().chunkSetAngles("SkiL3_D0", 0.0F, 0.0F, f3 * 15F);
                hierMesh().chunkSetAngles("SkiL4_D0", 0.0F, 0.0F, 0.0F);
                hierMesh().chunkSetAngles("SkiL5_D0", 0.0F, 0.0F, 0.0F);
                hierMesh().chunkSetAngles("SkiL6_D0", 0.0F, 0.0F, 0.0F);
                hierMesh().chunkSetAngles("SkiL7_D0", 0.0F, 0.0F, 0.0F);
                hierMesh().chunkSetAngles("SkiL8_D0", 0.0F, 0.0F, 0.0F);
            } else
            {
                hierMesh().chunkSetAngles("SkiL3_D0", 0.0F, f3 * 40F, f3 * 70F);
                float f4 = f3 * -5F;
                float f6 = f3 * 10F;
                float f8 = f3 * 15F;
                float f10 = f3 * 20F;
                hierMesh().chunkSetAngles("SkiL4_D0", 0.0F, -f4, -f10);
                hierMesh().chunkSetAngles("SkiL5_D0", 0.0F, -f6, -f10);
                hierMesh().chunkSetAngles("SkiL6_D0", 0.0F, -f8, -f10);
                hierMesh().chunkSetAngles("SkiL7_D0", 0.0F, -f6, -f10);
                hierMesh().chunkSetAngles("SkiL8_D0", 0.0F, -f6, -f10);
            }
            f3 = skiAngleR / 20F;
            hierMesh().chunkSetAngles("SkiR1_D0", 0.0F, f3 * 2.0F - 0.25F * suspR, f3 * 8.25F + 3F * suspR);
            hierMesh().chunkSetAngles("SkiR2_D0", 0.0F, f3 * -7F + 1.25F * suspR, f3 * -6.25F + 2.75F * suspR);
            if(skiAngleR < 0.0F)
            {
                hierMesh().chunkSetAngles("SkiR3_D0", 0.0F, 0.0F, f3 * 15F);
                hierMesh().chunkSetAngles("SkiR4_D0", 0.0F, 0.0F, 0.0F);
                hierMesh().chunkSetAngles("SkiR5_D0", 0.0F, 0.0F, 0.0F);
                hierMesh().chunkSetAngles("SkiR6_D0", 0.0F, 0.0F, 0.0F);
                hierMesh().chunkSetAngles("SkiR7_D0", 0.0F, 0.0F, 0.0F);
                hierMesh().chunkSetAngles("SkiR8_D0", 0.0F, 0.0F, 0.0F);
            } else
            {
                hierMesh().chunkSetAngles("SkiR3_D0", 0.0F, f3 * 40F, f3 * 70F);
                float f5 = f3 * -5F;
                float f7 = f3 * 10F;
                float f9 = f3 * 15F;
                float f11 = f3 * 20F;
                hierMesh().chunkSetAngles("SkiR4_D0", 0.0F, -f7, -f11);
                hierMesh().chunkSetAngles("SkiR5_D0", 0.0F, -f7, -f11);
                hierMesh().chunkSetAngles("SkiR6_D0", 0.0F, -f5, -f11);
                hierMesh().chunkSetAngles("SkiR7_D0", 0.0F, -f9, -f11);
                hierMesh().chunkSetAngles("SkiR8_D0", 0.0F, -f7, -f11);
            }
        }
        super.moveFan(f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
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

    private float skiAngleL;
    private float skiAngleR;
    private float spring;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "R-5");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3do/plane/R-5/hier_skis.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFCSPar08())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1931F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1944F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitI_16TYPE6.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/R-5.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 10, 10, 0, 0, 0, 0, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 9, 9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", 
            "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", 
            "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", 
            "_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Gunpods", new java.lang.String[] {
            "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonR5GPodL 1", "PylonR5GPodR 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Gunpods+8x10+2x100", new java.lang.String[] {
            "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, 
            null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunAO10S 1", null, "BombGunAO10S 1", 
            null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", 
            null, "BombGunAO10S 1", null, "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Gunpods+16x10+2x100", new java.lang.String[] {
            "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, 
            null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", 
            "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", 
            "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Gunpods+4x50+2x50", new java.lang.String[] {
            "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, 
            null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x50+2x100", new java.lang.String[] {
            "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", 
            "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100+2x100", new java.lang.String[] {
            "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, 
            null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100+2x50+2x100", new java.lang.String[] {
            "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, 
            null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
