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

public class P_51D25NA extends P_51Mustang
    implements TypeFighterAceMaker
{

    public P_51D25NA()
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
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        float f1 = (float)java.lang.Math.sin(Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
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
                    ((CockpitP_51D25K14)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(java.lang.Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(false);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(FM.Vwld);
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
        k14Mode = netmsginput.readByte();
        k14WingspanType = netmsginput.readByte();
        k14Distance = netmsginput.readFloat();
    }

    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;
    public boolean bHasBlister;
    private float fMaxKMHSpeedForOpenCanopy;

    static 
    {
        java.lang.Class class1 = P_51D25NA.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-51");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-51D-25NA(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/P-51D-25NA(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-51D-25NA(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1947.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-51D-25.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitP_51D25K14.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.1188F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalDev14", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, null, null, 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x250_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun250lbs 1", "BombGun250lbs 1", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x500_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun500lbs 1", "BombGun500lbs 1", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x75Tank_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank75gal2New", "FuelTankGun_Tank75gal2New", "Pylon51Late", "Pylon51Late", 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x110Tank_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank110gal2", "FuelTankGun_Tank110gal2", "Pylon51Late", "Pylon51Late", 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x250_4xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x500_4xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x75Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank75gal2New", "FuelTankGun_Tank75gal2New", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x108Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x110Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank110gal2", "FuelTankGun_Tank110gal2", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x165Tank_4xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank165gal2", "FuelTankGun_Tank165gal2", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x75Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank75gal2New", "FuelTankGun_Tank75gal2New", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x108Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", null, null, "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x110Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank110gal2", "FuelTankGun_Tank110gal2", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xM2_APIT_2x165Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "MGunBrowning50kAPIT 270", "FuelTankGun_Tank165gal2", "FuelTankGun_Tank165gal2", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, null, null, 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x250_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun250lbs 1", "BombGun250lbs 1", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x500_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun500lbs 1", "BombGun500lbs 1", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x75Tank_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "FuelTankGun_Tank75gal2New", "FuelTankGun_Tank75gal2New", "Pylon51Late", "Pylon51Late", 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x110Tank_6x45", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "FuelTankGun_Tank110gal2", "FuelTankGun_Tank110gal2", "Pylon51Late", "Pylon51Late", 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x250_4xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x500_4xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, "Pylon51Late", "Pylon51Late", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x75Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "FuelTankGun_Tank75gal2New", "FuelTankGun_Tank75gal2New", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x108Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x110Tank_6xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "FuelTankGun_Tank110gal2", "FuelTankGun_Tank110gal2", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x165Tank_4xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "FuelTankGun_Tank165gal2", "FuelTankGun_Tank165gal2", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, "PylonP51RAIL", "PylonP51RAIL", "PylonP51RAIL", null, null, 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x75Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "FuelTankGun_Tank75gal2New", "FuelTankGun_Tank75gal2New", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x108Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", null, null, "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x110Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "FuelTankGun_Tank110gal2", "FuelTankGun_Tank110gal2", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xM2_APIT_2x165Tank", new java.lang.String[] {
            "MGunBrowning50kAPIT 400", "MGunBrowning50kAPIT 400", null, null, "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "FuelTankGun_Tank165gal2", "FuelTankGun_Tank165gal2", "Pylon51Late", "Pylon51Late", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
