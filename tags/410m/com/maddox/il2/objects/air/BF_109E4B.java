// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_109E4B.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Wing;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.BombSC50;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, PaintSchemeFMPar02, NetAircraft

public class BF_109E4B extends com.maddox.il2.objects.air.BF_109
{

    public BF_109E4B()
    {
        kangle = 0.0F;
    }

    public void update(float f)
    {
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.BF_109E4B.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F));
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.BF_109E4B.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F));
        }
        hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -38F * kangle, 0.0F);
        hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -38F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            for(int i = 0; i < aobj.length; i++)
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.BombSC50)
                {
                    hierMesh().chunkVisible("Rack", false);
                    hierMesh().chunkVisible("ETC50", true);
                }

        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = 0.8F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -78F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -24F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 78F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 24F * f2, 0.0F, 0.0F);
        }
        if(f > 0.99F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -78F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -24F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 78F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 24F, 0.0F, 0.0F);
        }
        if(f < 0.01F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
        }
    }

    protected void moveGear(float f)
    {
        float f1 = 0.9F - (float)((com.maddox.il2.ai.Wing)getOwner()).aircIndex(this) * 0.1F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -24F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 24F * f2, 0.0F, 0.0F);
        }
        if(f > 0.99F)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78F, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -24F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78F, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 24F, 0.0F, 0.0F);
        }
    }

    public void moveSteering(float f)
    {
        if(f > 77.5F)
        {
            f = 77.5F;
            FM.Gears.steerAngle = f;
        }
        if(f < -77.5F)
        {
            f = -77.5F;
            FM.Gears.steerAngle = f;
        }
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109E4B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Bf-109E-4B/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109E-4B.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitBF_109Ex.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74985F);
        com.maddox.il2.objects.air.BF_109E4B.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.BF_109E4B.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.BF_109E4B.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_109E4B.weaponsRegister(class1, "4xSC50", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.BF_109E4B.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60", "BombGunSC250 1", null, null, null, null
        });
        com.maddox.il2.objects.air.BF_109E4B.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
