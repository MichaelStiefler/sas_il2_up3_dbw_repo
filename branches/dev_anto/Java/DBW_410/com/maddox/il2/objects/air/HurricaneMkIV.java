// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HurricaneMkIV.java

package com.maddox.il2.objects.air;

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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HurricaneMkIV.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "HurriMkIV");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkIV(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkIV.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHURRII.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66895F);
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
    }
}
