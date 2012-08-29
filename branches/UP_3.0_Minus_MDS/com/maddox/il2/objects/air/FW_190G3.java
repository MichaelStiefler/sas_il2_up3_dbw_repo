// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FW_190G3.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.objects.weapons.FuelTank_Type_D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190G, PaintSchemeBMPar02, TypeStormovik, TypeBomber, 
//            NetAircraft, Aircraft

public class FW_190G3 extends com.maddox.il2.objects.air.FW_190G
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeBomber
{

    public FW_190G3()
    {
        fuel_tank = 0;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.M.massEmpty -= 78F;
        hierMesh().chunkVisible("7mmC_D0", false);
        hierMesh().chunkVisible("7mmCowl_D0", true);
        hierMesh().chunkVisible("Flap01_D0", true);
        hierMesh().chunkVisible("Flap04_D0", true);
        hierMesh().chunkVisible("Flap01Holed_D0", false);
        hierMesh().chunkVisible("Flap04Holed_D0", false);
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            for(int i = 0; i < aobj.length; i++)
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.FuelTank_Type_D)
                    fuel_tank++;

        }
        if(fuel_tank > 1)
        {
            hierMesh().chunkVisible("Flap01_D0", false);
            hierMesh().chunkVisible("Flap04_D0", false);
            hierMesh().chunkVisible("Flap01Holed_D0", true);
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

    private int fuel_tank;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190G3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Fw-190A-5(Beta)/hier_FG.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1942.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Fw-190A-5-165.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitFW_190A5.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 9, 9, 9, 
            9, 9, 2, 2, 9, 9, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 
            3, 3, 9, 3, 3, 3, 3, 3, 3, 3, 
            3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04", "_ExternalBomb06", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb07", "_ExternalDev01", "_ExternalDev02", 
            "_ExternalBomb05", "_ExternalBomb05", "_ExternalDev11", "_ExternalBomb10", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb11", "_ExternalBomb08", "_ExternalBomb08", "_ExternalBomb09", 
            "_ExternalBomb09"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonETC501FW190 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc250_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonETC501FW190 1", null, 
            "BombGunSC250 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1ab250_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonETC501FW190 1", null, 
            "BombGunAB250 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc500_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonETC501FW190 1", null, 
            "BombGunSC500 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1ab500_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonETC501FW190 1", null, 
            "BombGunAB500 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sd500_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonETC501FW190 1", null, 
            "BombGunSD500 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sc50_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonETC501FW190 1", null, 
            null, null, null, "BombGunSC50 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunNull 1", "BombGunNull 1", 
            "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc250_1tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunSC250 1", null, null, "BombGunSC250 1", "PylonETC501FW190 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2ab250_1tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunAB250 1", null, null, "BombGunAB250 1", "PylonETC501FW190 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunSC250 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC250 1", "PylonETC501FW190 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2ab250", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunAB250 1", null, null, "BombGunAB250 1", "PylonETC501FW190 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3sc250", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250 1", "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunSC250 1", null, null, "BombGunSC250 1", "PylonETC501FW190 1", null, 
            "BombGunSC250 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}