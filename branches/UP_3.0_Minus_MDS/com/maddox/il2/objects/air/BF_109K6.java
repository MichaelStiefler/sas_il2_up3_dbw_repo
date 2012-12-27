// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BF_109K6.java

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
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, CockpitBF_109K4, PaintSchemeFMPar06, TypeBNZFighter, 
//            Aircraft, Cockpit, NetAircraft

public class BF_109K6 extends com.maddox.il2.objects.air.BF_109
    implements com.maddox.il2.objects.air.TypeBNZFighter
{

    public BF_109K6()
    {
        cockpitDoor_ = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 250F;
        kangle = 0.0F;
        bHasBlister = true;
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("Flettner1_D0", 0.0F, -45F * f, 0.0F);
    }

    public void update(float f)
    {
        if(super.FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
        }
        hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 20F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
        if((double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && super.FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                    ((com.maddox.il2.objects.air.CockpitBF_109K4)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(java.lang.Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(((com.maddox.il2.engine.ActorHMesh) (this)), hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld)));
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
        if(f <= f1 || f == 1.0F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
        }
        f2 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -f2, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, f2, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 90F * f, 0.0F, 0.0F);
        if(f > 0.99F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
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
        float f1 = 0.9F - (float)((com.maddox.il2.ai.Wing)getOwner()).aircIndex(((com.maddox.il2.objects.air.Aircraft) (this))) * 0.1F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1 || f == 1.0F)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
        }
        f2 = java.lang.Math.max(-f * 1500F, -90F);
        hierMesh().chunkSetAngles("GearL4_D0", 0.0F, -f2, 0.0F);
        hierMesh().chunkSetAngles("GearR4_D0", 0.0F, f2, 0.0F);
        hierMesh().chunkSetAngles("GearC2_D0", 90F * f, 0.0F, 0.0F);
        if(f > 0.99F)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
        }
    }

    public void moveSteering(float f)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getGear() >= 0.98F)
            hierMesh().chunkSetAngles("GearC2_D0", 90F, -f, 0.0F);
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

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_MGUN01") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        {
            hierMesh().chunkVisible("GunL_D0", false);
            hierMesh().chunkVisible("GunR_D0", false);
        }
        if(getGunByHookName("_CANNON02") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("GunL_D0", false);
        if(getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("GunR_D0", false);
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

    public float cockpitDoor_;
    private float fMaxKMHSpeedForOpenCanopy;
    private float kangle;
    public boolean bHasBlister;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109K6.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3do/plane/Bf-109K-6/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1944.11F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1955F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Bf-109K-6.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_109K4.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.7498F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1, 9, 3, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMK108ki 45", "MGunMK108ki 45", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xMG151", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMG15120MGki 100", "MGunMG15120MGki 100", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xMG151", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMG15120MGki 200", "MGunMK108ki 45", "MGunMK108ki 45", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xMK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMK108ki 45", "MGunMK108ki 45", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMK108ki 45", "MGunMK108ki 45", "PylonETC900 1", "BombGunSC250 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMK108ki 45", "MGunMK108ki 45", "PylonETC900 1", "BombGunSC500 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB250", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMK108ki 45", "MGunMK108ki 45", "PylonETC900 1", "BombGunAB250 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB500", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMK108ki 45", "MGunMK108ki 45", "PylonETC900 1", "BombGunAB500 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "DropTank", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMK108ki 45", "MGunMK108ki 45", "PylonETC900 1", null, "FuelTankGun_Type_D 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R4DropTank", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", "MGunMG15120MGki 100", "MGunMG15120MGki 100", "PylonETC900 1", null, "FuelTankGun_Type_D 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}