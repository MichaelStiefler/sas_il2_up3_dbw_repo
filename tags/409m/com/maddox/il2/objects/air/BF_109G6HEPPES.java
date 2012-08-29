// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_109G6HEPPES.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Wing;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, PaintSchemeSpecial, TypeBNZFighter, TypeAcePlane, 
//            Aircraft, NetAircraft

public class BF_109G6HEPPES extends com.maddox.il2.objects.air.BF_109
    implements com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeAcePlane
{

    public BF_109G6HEPPES()
    {
        kangle = 0.0F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.Skill = 3;
    }

    public void update(float f)
    {
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
        }
        hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 20F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = 0.8F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1 || f == 1.0F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
        }
        if(f > 0.99F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
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
        if(f <= f1 || f == 1.0F)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
        }
        if(f > 0.99F)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
        }
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
            return;
        }
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
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109G6HEPPES.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-109G-6Early(ofHeppes)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109G-6Early.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 1, 1, 1, 1, 1, 9, 9, 
            9, 9, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev01", "_ExternalDev01", 
            "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U3-MK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-SC250", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900", null, 
            null, null, "BombGunSC250 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-SC500", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900", null, 
            null, null, "BombGunSC500 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2-SC50", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC50", null, 
            null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3-DROPTANK", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900", "FuelTankGun_Type_D", 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R5-MK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, "MGunMK108k 35", "MGunMK108k 35", null, null, 
            "PylonMk108", "PylonMk108", null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R6-MG151-20", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMG15120MGki 200", null, "MGunMG15120MGk 135", "MGunMG15120MGk 135", null, null, null, null, 
            "PylonMG15120", "PylonMG15120", null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U3R6-MG151-20", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", null, "MGunMG15120MGk 135", "MGunMG15120MGk 135", null, null, null, null, 
            "PylonMG15120", "PylonMG15120", null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3R6-MG151-20", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMG15120MGki 200", null, "MGunMG15120MGk 135", "MGunMG15120MGk 135", null, null, "PylonETC900", "FuelTankGun_Type_D", 
            "PylonMG15120", "PylonMG15120", null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
    }
}
