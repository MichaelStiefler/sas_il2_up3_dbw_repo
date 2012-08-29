// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   F_51D30NA.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_51Mustang, CockpitF_51D30K14, PaintSchemeFMPar05, PaintSchemeFMPar06, 
//            TypeFighterAceMaker, Aircraft, Cockpit, NetAircraft

public class F_51D30NA extends com.maddox.il2.objects.air.P_51Mustang
    implements com.maddox.il2.objects.air.TypeFighterAceMaker
{

    public F_51D30NA()
    {
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        bHasBlister = true;
        fMaxKMHSpeedForOpenCanopy = 150F;
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        float f1 = (float)java.lang.Math.sin(com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
        hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void update(float f)
    {
        super.update(f);
        if((double)FM.CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                    ((com.maddox.il2.objects.air.CockpitF_51D30K14)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(java.lang.Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(((com.maddox.il2.engine.ActorHMesh) (this)), hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(false);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(((com.maddox.JGP.Tuple3d) (FM.Vwld)));
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            FM.CT.bHasCockpitDoorControl = false;
            FM.setGCenter(-0.3F);
        }
    }

    public boolean typeFighterAceMakerToggleAutomation()
    {
        k14Mode++;
        if(k14Mode > 2)
            k14Mode = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + k14Mode);
        return true;
    }

    public void typeFighterAceMakerAdjDistanceReset()
    {
    }

    public void typeFighterAceMakerAdjDistancePlus()
    {
        k14Distance += 10F;
        if(k14Distance > 800F)
            k14Distance = 800F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
    }

    public void typeFighterAceMakerAdjDistanceMinus()
    {
        k14Distance -= 10F;
        if(k14Distance < 200F)
            k14Distance = 200F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
    }

    public void typeFighterAceMakerAdjSideslipReset()
    {
    }

    public void typeFighterAceMakerAdjSideslipPlus()
    {
        k14WingspanType--;
        if(k14WingspanType < 0)
            k14WingspanType = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerAdjSideslipMinus()
    {
        k14WingspanType++;
        if(k14WingspanType > 9)
            k14WingspanType = 9;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeByte(k14Mode);
        netmsgguaranted.writeByte(k14WingspanType);
        netmsgguaranted.writeFloat(k14Distance);
    }

    public void typeFighterAceMakerReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        k14Mode = ((int) (netmsginput.readByte()));
        k14WingspanType = ((int) (netmsginput.readByte()));
        k14Distance = netmsginput.readFloat();
    }

    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;
    public boolean bHasBlister;
    private float fMaxKMHSpeedForOpenCanopy;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F_51D30NA.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "F-51");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/F-51D-30NA(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/F-51D-30NA(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "noseart", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1947F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1957.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/P-51D-30.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitF_51D30K14.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.1188F);
        com.maddox.il2.objects.air.F_51D30NA.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalDev14", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16"
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x75Nap", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun75GalNapalm 1", "BombGun75GalNapalm 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x110Nap", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun110GalNapalm 1", "BombGun110GalNapalm 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x250_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x500_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x75Nap_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun75GalNapalm 1", "BombGun75GalNapalm 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x110Nap_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun110GalNapalm 1", "BombGun110GalNapalm 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x75Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank75gal2New 1", "FuelTankGun_Tank75gal2New 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x108Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank108gal2 1", "FuelTankGun_Tank108gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x110Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank110gal2 1", "FuelTankGun_Tank110gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x165Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank165gal2 1", "FuelTankGun_Tank165gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_10xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x75Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank75gal2New 1", "FuelTankGun_Tank75gal2New 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x108Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank108gal2 1", "FuelTankGun_Tank108gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x110Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank110gal2 1", "FuelTankGun_Tank110gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "6xM2_APIT_2x165Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank165gal2 1", "FuelTankGun_Tank165gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x75Nap", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun75GalNapalm 1", "BombGun75GalNapalm 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x110Nap", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun110GalNapalm 1", "BombGun110GalNapalm 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x250_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x500_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x75Nap_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun75GalNapalm 1", "BombGun75GalNapalm 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x110Nap_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun110GalNapalm 1", "BombGun110GalNapalm 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x75Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank75gal2New 1", "FuelTankGun_Tank75gal2New 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x108Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank108gal2 1", "FuelTankGun_Tank108gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x110Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank110gal2 1", "FuelTankGun_Tank110gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x165Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank165gal2 1", "FuelTankGun_Tank165gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", null, null, 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_10xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", "PylonP51RAIL 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x75Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank75gal2New 1", "FuelTankGun_Tank75gal2New 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x108Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank108gal2 1", "FuelTankGun_Tank108gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x110Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank110gal2 1", "FuelTankGun_Tank110gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "4xM2_APIT_2x165Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank165gal2 1", "FuelTankGun_Tank165gal2 1", "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.F_51D30NA.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
