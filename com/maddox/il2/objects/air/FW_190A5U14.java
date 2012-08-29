// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FW_190A5U14.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190A5, PaintSchemeBMPar03, NetAircraft, Aircraft

public class FW_190A5U14 extends com.maddox.il2.objects.air.FW_190A5
{

    public FW_190A5U14()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.FW_190A5U14.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() >= 0.98F)
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_MGUN01") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        {
            hierMesh().chunkVisible("7mmC_D0", false);
            hierMesh().chunkVisible("7mmCowl_D0", true);
        }
        if(getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("20mmL_D0", false);
        if(getGunByHookName("_CANNON04") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("20mmR_D0", false);
        if(!(getGunByHookName("_ExternalDev05") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            hierMesh().chunkVisible("Flap01_D0", false);
            hierMesh().chunkVisible("Flap01Holed_D0", true);
        }
        if(!(getGunByHookName("_ExternalDev06") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            hierMesh().chunkVisible("Flap04_D0", false);
            hierMesh().chunkVisible("Flap04Holed_D0", true);
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

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190A5U14.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Fw-190A-5U14/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar03())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943.1F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitFW_190A5.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Fw-190A-5.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 9, 9, 9, 
            9, 9, 2, 2, 9, 9, 3, 3, 3, 3, 
            9, 9, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
            "_ExternalDev01", "_ExternalDev02", "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Torp", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190 1", null, "BombGunTorpFiume 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Torp_PTB", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190 1", null, "BombGunTorpFiume 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
