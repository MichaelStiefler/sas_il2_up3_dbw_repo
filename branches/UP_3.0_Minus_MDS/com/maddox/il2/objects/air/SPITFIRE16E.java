// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   SPITFIRE16E.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE9, CockpitSpit16E, PaintSchemeFMPar05, PaintSchemeSPIT16, 
//            TypeFighterAceMaker, Aircraft, Cockpit, NetAircraft

public class SPITFIRE16E extends com.maddox.il2.objects.air.SPITFIRE9
    implements com.maddox.il2.objects.air.TypeFighterAceMaker
{

    public SPITFIRE16E()
    {
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        bHasBlister = true;
        fMaxKMHSpeedForOpenCanopy = 250F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(!(getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            hierMesh().chunkVisible("Cannon_L", true);
        if(!(getGunByHookName("_CANNON04") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            hierMesh().chunkVisible("Cannon_R", true);
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
        if((double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && super.FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                    ((com.maddox.il2.objects.air.CockpitSpit16E)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(java.lang.Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(((com.maddox.il2.engine.ActorHMesh) (this)), hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(false);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld)));
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = false;
            super.FM.setGCenter(-0.3F);
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

    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;
    public boolean bHasBlister;
    private float fMaxKMHSpeedForOpenCanopy;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.SPITFIRE16E.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/SpitfireMkXVIe(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_gb", "3DO/Plane/SpitfireMkXVIe(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_gb", ((java.lang.Object) (new PaintSchemeSPIT16())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1944F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/SpitfireXVI.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSpit16E.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1, 1, 9, 9, 9, 3, 
            3, 9, 3, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev08", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01", "_ExternalRock01", "_ExternalRock02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, null, null, 
            null, "PylonSpitC", "BombGun500lbsE", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250_1x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", "PylonSpitC", "BombGun500lbsE 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250dt30", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit30", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250dt45", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit45", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250dt90", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit90", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x30dt", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit30", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x45dt", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit45", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x90dt", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit90", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xRP3", new java.lang.String[] {
            "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, "PylonSpitROCK", "PylonSpitROCK", null, 
            null, null, null, "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xHispano", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H1x500", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, 
            null, "PylonSpitC", "BombGun500lbsE", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250_1x500", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", "PylonSpitC", "BombGun500lbsE 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250dt30", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit30", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250dt45", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit45", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250dt90", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit90", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H1x30dt", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit30", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H1x45dt", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit45", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H1x90dt", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit90", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2xRP3", new java.lang.String[] {
            null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitROCK", "PylonSpitROCK", null, 
            null, null, null, "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
