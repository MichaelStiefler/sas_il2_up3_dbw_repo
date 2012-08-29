// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   YAK_15.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK, PaintSchemeFMPar06, PaintSchemeFCSPar05, NetAircraft

public class YAK_15 extends com.maddox.il2.objects.air.YAK
{

    public YAK_15()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC99_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, com.maddox.il2.objects.air.YAK_15.cvt(f, 0.1F, 0.6F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.YAK_15.cvt(f, 0.1F, 0.6F, 0.0F, -83.5F), 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, com.maddox.il2.objects.air.YAK_15.cvt(f, 0.4F, 0.9F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.YAK_15.cvt(f, 0.4F, 0.9F, 0.0F, -83.5F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.YAK_15.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxCannon01"))
            {
                if(getEnergyPastArmor(9.8F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Cannon (0) Disabled..");
                    FM.AS.setJamBullets(1, 0);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
                return;
            }
            if(s.startsWith("xxCannon02"))
            {
                if(getEnergyPastArmor(9.8F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Cannon (0) Disabled..");
                    FM.AS.setJamBullets(1, 1);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
                return;
            }
        }
        super.hitBone(s, shot, point3d);
    }

    public void update(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && FM.AS.isMaster())
            if(FM.EI.engines[0].getPowerOutput() > 0.8F && FM.EI.engines[0].getStage() == 6)
            {
                if(FM.EI.engines[0].getPowerOutput() > 0.95F)
                    FM.AS.setSootState(this, 0, 3);
                else
                    FM.AS.setSootState(this, 0, 2);
            } else
            {
                FM.AS.setSootState(this, 0, 0);
            }
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
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Yak-15(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/Yak-15(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1946F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak-15.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitYAK_15.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0989F);
        com.maddox.il2.objects.air.YAK_15.weaponTriggersRegister(class1, new int[] {
            1, 1
        });
        com.maddox.il2.objects.air.YAK_15.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.YAK_15.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVYaki 60", "MGunVYaki 60"
        });
        com.maddox.il2.objects.air.YAK_15.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
