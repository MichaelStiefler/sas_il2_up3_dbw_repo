// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 9/06/2011 8:39:39 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   BF_109G2.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, CockpitBF_109G2, PaintSchemeFMPar03, PaintSchemeFCSPar02, 
//            Aircraft, Cockpit, NetAircraft

public class BF_109G2 extends BF_109
{

    public BF_109G2()
    {
        cockpitDoor_ = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 250F;
        kangle = 0.0F;
        bHasBlister = true;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(World.cur().camouflage == 2)
        {
            hierMesh().chunkVisible("intake_D0", true);
            hierMesh().chunkVisible("RFlap03_D0", true);
            hierMesh().chunkVisible("RFlap04_D0", true);
        } else
        {
            hierMesh().chunkVisible("intake_D0", false);
            hierMesh().chunkVisible("RFlap03_D0", false);
            hierMesh().chunkVisible("RFlap04_D0", false);
        }
    }

    public void update(float f)
    {
        if(super.FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
        }
        hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 16F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * ((FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
        if((double)((FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && super.FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == World.getPlayerAircraft())
                    ((CockpitBF_109G2)Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            Vector3d vector3d = new Vector3d();
            vector3d.set(((FlightModelMain) (super.FM)).Vwld);
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            ((FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = false;
            super.FM.setGCenter(-0.5F);
        }
        if(super.FM.isPlayers())
            if(!Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D0", false);
            else
                hierMesh().chunkVisible("CF_D0", true);
        if(super.FM.isPlayers())
        {
            if(!Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D1", false);
            hierMesh().chunkVisible("CF_D2", false);
            hierMesh().chunkVisible("CF_D3", false);
        }
        if(super.FM.isPlayers())
        {
            if(!Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("Blister1_D0", false);
            else
            if(bHasBlister)
                hierMesh().chunkVisible("Blister1_D0", true);
            com.maddox.JGP.Point3d point3d = ((Actor) (World.getPlayerAircraft())).pos.getAbsPoint();
            if(((Tuple3d) (point3d)).z - World.land().HQ(((Tuple3d) (point3d)).x, ((Tuple3d) (point3d)).y) < 0.0099999997764825821D)
                hierMesh().chunkVisible("CF_D0", true);
            if(((FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Blister1_D0", false);
        }
    }

    public static void moveGear(HierMesh hiermesh, float f)
    {
        float f1 = 0.8F;
        float f2 = -0.5F * (float)Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1 || f == 1.0F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
        }
        hiermesh.chunkSetAngles("GearC3_D0", 70F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("RFlap03_D0", 45F * (1.0F - f), 0.0F, 0.0F);
        hiermesh.chunkSetAngles("RFlap04_D0", -45F * (1.0F - f), 0.0F, 0.0F);
        if(f > 0.99F)
        {
            hiermesh.chunkSetAngles("RFlap03_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("RFlap04_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
        }
        if(f < 0.01F)
        {
            hiermesh.chunkSetAngles("RFlap03_D0", -45F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("RFlap04_D0", 45F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
        }
    }

    protected void moveGear(float f)
    {
        float f1 = 0.9F - (float)((Wing)getOwner()).aircIndex(this) * 0.1F;
        float f2 = -0.5F * (float)Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1 || f == 1.0F)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
        }
        hierMesh().chunkSetAngles("GearC3_D0", 70F * f, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RFlap03_D0", -45F * (1.0F - f), 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RFlap04_D0", 45F * (1.0F - f), 0.0F, 0.0F);
        if(f > 0.99F)
        {
            hierMesh().chunkSetAngles("RFlap03_D0", 0.0F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("RFlap04_D0", 0.0F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
        }
    }

    public void moveSteering(float f)
    {
        if(((FlightModelMain) (super.FM)).CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
            return;
        }
    }

    public void moveCockpitDoor(float f)
    {
        if(bHasBlister)
        {
            resetYPRmodifier();
            hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
            if(Config.isUSE_RENDER())
            {
                if(Main3D.cur3D().cockpits != null && Main3D.cur3D().cockpits[0] != null)
                    Main3D.cur3D().cockpits[0].onDoorMoved(f);
                setDoorSnd(f);
            }
        }
    }

    public float cockpitDoor_;
    private float fMaxKMHSpeedForOpenCanopy;
    private float kangle;
    public boolean bHasBlister;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.BF_109G2.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "Bf109");
        Property.set(class1, "meshName", "3DO/Plane/Bf-109G-2/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        Property.set(class1, "meshName_fi", "3DO/Plane/Bf-109G-2(fi)/hier.him");
        Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
        Property.set(class1, "meshName_hu", "3DO/Plane/Bf-109G-2(hu)/hier.him");
        Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar03());
        Property.set(class1, "meshName_sk", "3DO/Plane/Bf-109G-2(sk)/hier.him");
        Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar03());
        Property.set(class1, "yearService", 1942F);
        Property.set(class1, "yearExpired", 1945.5F);
        Property.set(class1, "FlightModel", "FlightModels/Bf-109G-2trop.fmd");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitBF_109G2.class
        });
        Property.set(class1, "LOSElevation", 0.7498F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 1, 1, 1, 1, 1, 9, 9, 
            9, 9, 3, 3, 3, 3, 3, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev01", "_ExternalDev01", 
            "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06"
        });
        Aircraft.weaponsRegister(class1, "default", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "R1-SC250", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, "BombGunSC250 1", null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "R1-AB250", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, "BombGunAB250 1", null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "R1-SC500", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, "BombGunSC500 1", null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "R1-AB500", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, "BombGunAB500 1", null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "R2-SC50", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC50Bf109 1", null, 
            null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, null
        });
        Aircraft.weaponsRegister(class1, "R2-AB23", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC50Bf109 1", null, 
            null, null, null, "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", null, null, null
        });
        Aircraft.weaponsRegister(class1, "R3-DROPTANK", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "R6-MG151-20", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15120MGki 200", "MGunMG15120MGki 200", "MGunMG15120kh 140", "MGunMG15120kh 140", null, null, null, null, 
            "PylonMG15120 1", "PylonMG15120 1", null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "R3R6-MG151-20", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", "MGunMG15120kh 135", "MGunMG15120kh 135", null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            "PylonMG15120 1", "PylonMG15120 1", null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "R1-2XDROPTANK", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null
        });
        Aircraft.weaponsRegister(class1, "R1-2XDROPTANK_SC500", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, "BombGunSC500 1", null, null, null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null
        });
        Aircraft.weaponsRegister(class1, "R2-RECON", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonRECO1BF109 1"
        });
        Aircraft.weaponsRegister(class1, "R2-RECON-DROPTANK", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, "PylonRECO1BF109 1"
        });
        Aircraft.weaponsRegister(class1, "R2-RECON-2xDROPTANK", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", "PylonRECO1BF109 1"
        });
        Aircraft.weaponsRegister(class1, "none", new String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}