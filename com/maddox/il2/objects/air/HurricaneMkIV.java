// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   HurricaneMkIV.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Hurricane, PaintSchemeFMPar04, TypeStormovik, TypeStormovikArmored, 
//            NetAircraft, Aircraft

public class HurricaneMkIV extends com.maddox.il2.objects.air.Hurricane
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeStormovikArmored
{

    public HurricaneMkIV()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(!(getGunByHookName("_MGUN03") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            hierMesh().chunkVisible("pod_L", true);
            hierMesh().chunkVisible("inner_barrel_L", true);
            hierMesh().chunkVisible("outer_barrel_L", true);
        }
        if(!(getGunByHookName("_MGUN04") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            hierMesh().chunkVisible("pod_R", true);
            hierMesh().chunkVisible("inner_barrel_R", true);
            hierMesh().chunkVisible("outer_barrel_R", true);
        }
        if((getBulletEmitterByHookName("_ExternalDev01") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getBulletEmitterByHookName("_ExternalBomb01") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            hierMesh().chunkVisible("PylonL_D0", false);
        if((getBulletEmitterByHookName("_ExternalDev02") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getBulletEmitterByHookName("_ExternalBomb02") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            hierMesh().chunkVisible("PylonR_D0", false);
        super.onAircraftLoaded();
        if(super.FM.isPlayers())
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = true;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.dvCockpitDoor = 1.0F;
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HurricaneMkIV.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "HurriMkIV");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/HurricaneMkIV(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1942F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/HurricaneMkIV.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHURRII.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.66895F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 
            9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalRock03", "_ExternalRock03", "_ExternalRock06", "_ExternalRock06", 
            "_ExternalRock05", "_ExternalRock05", "_ExternalRock04", "_ExternalRock04", "_ExternalRock07", "_ExternalRock07", "_ExternalRock08", "_ExternalRock08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "VickerS40mmAP", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", "MGunVickers40mmAPk 15", "MGunVickers40mmAPk 15", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "VickerS40mmHE", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", "MGunVickers40mmk 15", "MGunVickers40mmk 15", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", null, null, null, null, null, null, "BombGun250lbsE 1", "BombGun250lbsE 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", null, null, null, null, null, null, "BombGun500lbsE 1", "BombGun500lbsE 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xRP3", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", null, null, null, null, null, null, null, null, 
            "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", 
            null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xRP3_1xDrop", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", null, null, "FuelTankGun_Tank44gal 1", null, null, null, null, null, 
            null, "PylonTEMPESTPLN4 1", null, null, null, "RocketGunHVAR5BEAU 1", "BombGunNull 1", null, null, "RocketGunHVAR5BEAU 1", 
            "BombGunNull 1", null, null, "RocketGunHVAR5BEAU 1", "BombGunNull 1", null, null, "RocketGunHVAR5BEAU 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDrop90", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", null, null, "FuelTankGun_TankTempest 1", "FuelTankGun_TankTempest 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
