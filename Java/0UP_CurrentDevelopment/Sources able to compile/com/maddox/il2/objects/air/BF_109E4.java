package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public class BF_109E4 extends BF_109
{

    public BF_109E4()
    {
        cockpitDoor_ = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 250F;
        kangle = 0.0F;
        bHasBlister = true;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.ai.World.cur().camouflage == 2)
            hierMesh().chunkVisible("intake_D0", true);
        else
            hierMesh().chunkVisible("intake_D0", false);
    }

    public void update(float f)
    {
        if(super.FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 0.0F, Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F));
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 0.0F, Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F));
        }
        hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -38F * kangle, 0.0F);
        hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -38F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
        if((double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && super.FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                    ((CockpitBF_109Ex)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(java.lang.Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld);
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = false;
            super.FM.setGCenter(-0.5F);
        }
        if(super.FM.isPlayers())
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D0", false);
            else
                hierMesh().chunkVisible("CF_D0", true);
        if(super.FM.isPlayers())
        {
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D1", false);
            hierMesh().chunkVisible("CF_D2", false);
            hierMesh().chunkVisible("CF_D3", false);
        }
        if(super.FM.isPlayers())
        {
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("Blister1_D0", false);
            else
            if(bHasBlister)
                hierMesh().chunkVisible("Blister1_D0", true);
            com.maddox.JGP.Point3d point3d = ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft())).pos.getAbsPoint();
            if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.ai.World.land().HQ(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) < 0.0099999997764825821D)
                hierMesh().chunkVisible("CF_D0", true);
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Blister1_D0", false);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = 0.8F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -78F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -24F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 78F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 24F * f2, 0.0F, 0.0F);
        }
        if(f > 0.99F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -78F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -24F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 78F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 24F, 0.0F, 0.0F);
        }
        if(f < 0.01F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
        }
    }

    protected void moveGear(float f)
    {
        float f1 = 0.9F - (float)((com.maddox.il2.ai.Wing)getOwner()).aircIndex(this) * 0.1F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -24F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 24F * f2, 0.0F, 0.0F);
        }
        if(f > 0.99F)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78F, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -24F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78F, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 24F, 0.0F, 0.0F);
        }
    }

    public void moveSteering(float f)
    {
        if(f > 77.5F)
        {
            f = 77.5F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.steerAngle = f;
        }
        if(f < -77.5F)
        {
            f = -77.5F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.steerAngle = f;
        }
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void moveCockpitDoor(float f)
    {
        if(bHasBlister)
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
    }

    public float cockpitDoor_;
    private float fMaxKMHSpeedForOpenCanopy;
    private float kangle;
    public boolean bHasBlister;

    static 
    {
        java.lang.Class class1 = BF_109E4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-109E-4/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109E-4.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBF_109Ex.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74985F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
