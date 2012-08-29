// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FW_190G8.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.objects.weapons.FuelTank_Type_D;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190G, PaintSchemeBMPar02, TypeStormovik, TypeBomber, 
//            NetAircraft, Aircraft

public class FW_190G8 extends com.maddox.il2.objects.air.FW_190G
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeBomber
{

    public FW_190G8()
    {
        fuel_tank = 0;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_MGUN01") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        {
            hierMesh().chunkVisible("7mmC_D0", false);
            hierMesh().chunkVisible("7mmCowl_D0", true);
            FM.M.massEmpty -= 58F;
        } else
        {
            FM.M.massEmpty -= 24F;
        }
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
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190G8.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3do/plane/Fw-190A-8(Beta)/hier_G8.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1944F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Fw-190A-8.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitFW_190A8.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 3, 3, 9, 9, 9, 9, 
            9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalDev13", "_ExternalDev14", "_ExternalDev15", "_ExternalDev16", "_ExternalDev17", "_ExternalDev18", 
            "_ExternalBomb06", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb10", 
            "_ExternalBomb11", "_ExternalBomb11", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb04", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb05", "_ExternalBomb02", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalBomb03", "_ExternalDev19", "_ExternalDev20"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc250_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunSC250 1", null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1ab250_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunAB250 1", null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc500_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunSC500 1", null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1ab500_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunAB500 1", null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sd500_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunSD500 1", null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sc50_2tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", 
            "BombGunSC50 1", "BombGunNull 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc250_1tank", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGunSC250 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunSC250 1", null, null, null, null, null, null, null, null, 
            null, null, "FuelTankGun_Type_D 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2ab250_1tank", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGunAB250 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunAB250 1", null, null, null, null, null, null, null, null, 
            null, null, "FuelTankGun_Type_D 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc250_4sc70", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, "PylonETC71 1", "PylonETC71 1", 
            "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", null, null, 
            null, null, "BombGunSC250 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1ab250_4sc70", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, "PylonETC71 1", "PylonETC71 1", 
            "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", null, null, 
            null, null, "BombGunAB250 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc500_2sc50_2sc70", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, "PylonETC71 1", "PylonETC71 1", 
            "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", null, null, 
            null, null, "BombGunSC500 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1ab500_2sc50_2sc70", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, "PylonETC71 1", "PylonETC71 1", 
            "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", null, null, 
            null, null, "BombGunAB500 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sd500_2sc50_2sc70", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, "PylonETC71 1", "PylonETC71 1", 
            "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", null, null, 
            null, null, "BombGunSD500 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sc50_4sc70", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, "PylonETC71 1", "PylonETC71 1", 
            "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", "BombGunSC70 1", "BombGunNull 1", null, null, 
            null, null, null, null, "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", 
            "BombGunSC50 1", "BombGunNull 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGunSC250 1", null, 
            null, "BombGunSC250 1", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2ab250", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGunAB250 1", null, 
            null, "BombGunAB250 1", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3sc250", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGunSC250 1", null, 
            null, "BombGunSC250 1", "BombGunSC250 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc250_1sc500", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGunSC250 1", null, 
            null, "BombGunSC250 1", "BombGunSC500 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc1000", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunSC1000 1", null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc1800", new java.lang.String[] {
            null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC250 1", "PylonETC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGunSC1800 1", null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
