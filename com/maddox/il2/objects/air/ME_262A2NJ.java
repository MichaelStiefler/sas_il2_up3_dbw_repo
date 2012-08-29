// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   ME_262A2NJ.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_262B, PaintSchemeFMPar05, TypeX4Carrier, Cockpit, 
//            NetAircraft, Aircraft

public class ME_262A2NJ extends com.maddox.il2.objects.air.ME_262B
    implements com.maddox.il2.objects.air.TypeX4Carrier
{

    public ME_262A2NJ()
    {
        bToFire = false;
        tX4Prev = 0L;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if((super.FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode() || !flag || !(super.FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)super.FM;
        if(pilot.get_maneuver() == 63 && ((com.maddox.il2.ai.air.Maneuver) (pilot)).target != null)
        {
            com.maddox.JGP.Point3d point3d = new Point3d(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.ai.air.Maneuver) (pilot)).target)).Loc);
            point3d.sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Loc)));
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.transformInv(((com.maddox.JGP.Tuple3d) (point3d)));
            if((((com.maddox.JGP.Tuple3d) (point3d)).x > 4000D && ((com.maddox.JGP.Tuple3d) (point3d)).x < 5500D || ((com.maddox.JGP.Tuple3d) (point3d)).x > 100D && ((com.maddox.JGP.Tuple3d) (point3d)).x < 5000D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F) && com.maddox.rts.Time.current() > tX4Prev + 10000L)
            {
                bToFire = true;
                tX4Prev = com.maddox.rts.Time.current();
            }
        }
    }

    public void typeX4CAdjSidePlus()
    {
        deltaAzimuth = 1.0F;
    }

    public void typeX4CAdjSideMinus()
    {
        deltaAzimuth = -1F;
    }

    public void typeX4CAdjAttitudePlus()
    {
        deltaTangage = 1.0F;
    }

    public void typeX4CAdjAttitudeMinus()
    {
        deltaTangage = -1F;
    }

    public void typeX4CResetControls()
    {
        deltaAzimuth = deltaTangage = 0.0F;
    }

    public float typeX4CgetdeltaAzimuth()
    {
        return deltaAzimuth;
    }

    public float typeX4CgetdeltaTangage()
    {
        return deltaTangage;
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            if(!((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore1_D0", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Head2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            if(!((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore3_D0", true);
            break;
        }
    }

    public void update(float f)
    {
        if(super.FM.isPlayers() && !com.maddox.il2.game.Main3D.cur3D().isViewOutside())
            hierMesh().chunkVisible("Blister1_D0", false);
        else
            hierMesh().chunkVisible("Blister1_D0", true);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
            hierMesh().chunkVisible("Blister1_D0", false);
        super.update(f);
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

    public boolean bToFire;
    private long tX4Prev;
    private float deltaAzimuth;
    private float deltaTangage;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_262A2NJ.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Me 262");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Me-262A-2NJ/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1944.1F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Me-262B-1a.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitME_262NJ.class, com.maddox.il2.objects.air.CockpitME_262NJR.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.7498F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 9, 9, 0, 0, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 
            9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev03", "_ExternalDev04", "_CANNON05", "_CANNON06", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", 
            "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", 
            "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_ExternalDev01", "_ExternalDev02", "_ExternalRock25", "_ExternalRock25", "_ExternalRock26", "_ExternalRock26", 
            "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "blue", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "FuelTankGun_Dag 1", "FuelTankGun_Dag 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "black", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "FuelTankGun_Nag 1", "FuelTankGun_Nag 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "mauser213blue", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "FuelTankGun_Dag 1", "FuelTankGun_Dag 1", "PylonMG15120Internal 1", "PylonMG15120Internal 1", "MGunMauser213C 200", "MGunMauser213C 200", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "mauser213black", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "FuelTankGun_Nag 1", "FuelTankGun_Nag 1", "PylonMG15120Internal 1", "PylonMG15120Internal 1", "MGunMauser213C 200", "MGunMauser213C 200", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24r4mblue", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "FuelTankGun_Dag 1", "FuelTankGun_Dag 1", null, null, null, null, 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "PylonMe262_R4M_Left 1", "PylonMe262_R4M_Right 1", null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24r4mblack", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "FuelTankGun_Nag 1", "FuelTankGun_Nag 1", null, null, null, null, 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "PylonMe262_R4M_Left 1", "PylonMe262_R4M_Right 1", null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xX4blue", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "FuelTankGun_Dag 1", "FuelTankGun_Dag 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1", 
            "PylonETC250 1", "PylonETC250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
