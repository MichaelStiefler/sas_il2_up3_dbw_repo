package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

public class SeaHurricaneMkIIc extends Hurricane
{

    public SeaHurricaneMkIIc()
    {
        arrestor = 0.0F;
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if((!isNet() || !isNetMirror()) && !s.startsWith("Hook"))
            super.msgCollision(actor, s, s1);
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -57F * f, 0.0F);
        resetYPRmodifier();
        Aircraft.xyz[2] = 0.1385F * f;
        arrestor = f;
    }

    public void update(float f)
    {
        super.update(f);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() > 0.2F)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle != 0.0F)
            {
                float f1 = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle, -50F, 7F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f1;
                moveArrestorHook(arrestor);
            } else
            {
                float f2 = (-33F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVSink) / 57F;
                if(f2 < 0.0F && super.FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(this, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f2 > 0.0F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() < 0.95F)
                    f2 = 0.0F;
                if(f2 > 0.2F)
                    f2 = 0.2F;
                if(f2 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f2);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f2);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > 1.0F)
                    arrestor = 1.0F;
                moveArrestorHook(arrestor);
            }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_CANNON01") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        {
            hierMesh().chunkVisible("CannonL_D0", false);
            hierMesh().chunkVisible("CannonR_D0", false);
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

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasArrestorControl = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    private float arrestor;
    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = SeaHurricaneMkIIc.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SeaHurricaneMkIIc(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SeaHurricaneMkII.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitSEAHURRICANE2.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 
            9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalRock03", "_ExternalRock03", "_ExternalRock06", "_ExternalRock06", 
            "_ExternalRock05", "_ExternalRock05", "_ExternalRock04", "_ExternalRock04", "_ExternalRock07", "_ExternalRock07", "_ExternalRock08", "_ExternalRock08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, "BombGun250lbsE 1", "BombGun250lbsE 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, "BombGun500lbsE 1", "BombGun500lbsE 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "8xRP3", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, null, null, 
            "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", 
            null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1", null, "RocketGunHVAR5BEAU 1"
        });
        Aircraft.weaponsRegister(class1, "4xRP3_1xDrop", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "FuelTankGun_Tank44gal 1", null, null, null, null, null, 
            null, "PylonTEMPESTPLN4 1", null, null, null, "RocketGunHVAR5BEAU 1", "BombGunNull 1", null, null, "RocketGunHVAR5BEAU 1", 
            "BombGunNull 1", null, null, "RocketGunHVAR5BEAU 1", "BombGunNull 1", null, null, "RocketGunHVAR5BEAU 1"
        });
        Aircraft.weaponsRegister(class1, "2xDrop90", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "FuelTankGun_TankTempest 1", "FuelTankGun_TankTempest 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xDrop44", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "FuelTankGun_Tank44gal 1", "FuelTankGun_Tank44gal 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "8xRS82 Russian Field Mod", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, null, null, 
            "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1", null, "RocketGunRS82 1", null, "RocketGunRS82 1", null, "RocketGunRS82 1", null, "RocketGunRS82 1", 
            null, "RocketGunRS82 1", null, "RocketGunRS82 1", null, "RocketGunRS82 1", null, "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "8xM13 Russian Field Mod", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, null, null, 
            "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1", null, "RocketGunM13 1", null, "RocketGunM13 1", null, "RocketGunM13 1", null, "RocketGunM13 1", 
            null, "RocketGunM13 1", null, "RocketGunM13 1", null, "RocketGunM13 1", null, "RocketGunM13 1"
        });
        Aircraft.weaponsRegister(class1, "2x250_NoCannon", new java.lang.String[] {
            null, null, null, null, null, null, null, null, "BombGun250lbsE 1", "BombGun250lbsE 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x500_NoCannon", new java.lang.String[] {
            null, null, null, null, null, null, null, null, "BombGun500lbsE 1", "BombGun500lbsE 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xCannon", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x250_2xCannon", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, null, null, "BombGun250lbsE 1", "BombGun250lbsE 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x500_2xCannon", new java.lang.String[] {
            "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", null, null, null, null, null, null, "BombGun500lbsE 1", "BombGun500lbsE 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
