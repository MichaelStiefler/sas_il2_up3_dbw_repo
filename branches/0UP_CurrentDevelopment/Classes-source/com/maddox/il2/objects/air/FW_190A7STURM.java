// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FW_190A7STURM.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190NEW, PaintSchemeFMPar01, TypeFighter, TypeBNZFighter, 
//            NetAircraft, Aircraft

public class FW_190A7STURM extends com.maddox.il2.objects.air.FW_190NEW
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter
{

    public FW_190A7STURM()
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("20mmL_D0", false);
        if(getGunByHookName("_CANNON04") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("20mmR_D0", false);
        if(getGunByHookName("_CANNON07") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("30mmL_D0", false);
        if(getGunByHookName("_CANNON08") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("30mmR_D0", false);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 20F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.FW_190A7STURM.moveGear(hierMesh(), f);
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
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190A7STURM.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Fw-190A-7/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1943.1F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190A-7sturm.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitFW_190A8.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 9, 2, 2, 
            9, 1, 1, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", 
            "_ExternalBomb01", "_CANNON07", "_CANNON08", "_ExternalDev01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15120MGkh 140", "MGunMG15120MGkh 140", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "bidon", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15120MGkh 140", "MGunMG15120MGkh 140", null, null, null, null, 
            "FuelTankGun_Type_D 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, "MGunMK108kh 55", "MGunMK108kh 55", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2+bidon", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            "FuelTankGun_Type_D 1", "MGunMK108kh 55", "MGunMK108kh 55", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R6", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15120MGkh 140", "MGunMG15120MGkh 140", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            null, "MGunMK108kh 55", "MGunMK108kh 55", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R6+bidon", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15120MGkh 140", "MGunMG15120MGkh 140", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "FuelTankGun_Type_D 1", "MGunMK108kh 55", "MGunMK108kh 55", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2+R6", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            null, "MGunMK108kh 55", "MGunMK108kh 55", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2+R6+bidon", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "FuelTankGun_Type_D 1", "MGunMK108kh 55", "MGunMK108kh 55", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
