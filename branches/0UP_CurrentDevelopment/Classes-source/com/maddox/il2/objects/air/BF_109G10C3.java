// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_109G10C3.java

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
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, CockpitBF_109G10, PaintSchemeFMPar04, TypeBNZFighter, 
//            Aircraft, Cockpit, NetAircraft

public class BF_109G10C3 extends com.maddox.il2.objects.air.BF_109
    implements com.maddox.il2.objects.air.TypeBNZFighter
{

    public BF_109G10C3()
    {
        cockpitDoor_ = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 250F;
        kangle = 0.0F;
        bHasBlister = true;
    }

    public void update(float f)
    {
        if(super.FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
        }
        hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 16F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
        if((double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && super.FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                    ((com.maddox.il2.objects.air.CockpitBF_109G10)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
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
        float f1 = 0.9F - (float)((com.maddox.il2.ai.Wing)getOwner()).aircIndex(this) * 0.1F;
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
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getGear() < 0.98F)
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
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                    com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
                setDoorSnd(f);
            }
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

    public float cockpitDoor_;
    private float fMaxKMHSpeedForOpenCanopy;
    private float kangle;
    public boolean bHasBlister;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109G10C3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-109G-10C3/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109G-10C3.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_109G10.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7498F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 1, 1, 1, 1, 1, 9, 9, 
            9, 9, 2, 2, 9, 9, 3, 3, 3, 3, 
            3, 3, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev01", "_ExternalDev01", 
            "_ExternalDev02", "_ExternalDev03", "_ExternalRock01", "_ExternalRock02", "_ExternalDev04", "_ExternalDev05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
            "_ExternalBomb05", "_ExternalBomb06", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-SC250", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, null, null, null, null, "BombGunSC250 1", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-AB250", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, null, null, null, null, "BombGunAB250 1", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-SC500", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, null, null, null, null, "BombGunSC500 1", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-AB500", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, 
            null, null, null, null, null, null, "BombGunAB500 1", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2-SC50", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC50Bf109 1", null, 
            null, null, null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", 
            "BombGunSC50 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2-AB23", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC50Bf109 1", null, 
            null, null, null, null, null, null, null, "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", 
            "BombGunAB23 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2-WfrGr21", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, null, null, 
            null, null, "RocketGunWfrGr21 1", "RocketGunWfrGr21 1", "PylonRO_WfrGr21 1", "PylonRO_WfrGr21 1", null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3-DROPTANK", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R4-2XMK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", "MGunMK108kh 35", "MGunMK108kh 35", null, null, null, null, 
            "PylonMk108 1", "PylonMk108 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R6-2XMG151-20", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", "MGunMG15120kh 135", "MGunMG15120kh 135", null, null, null, null, 
            "PylonMG15120 1", "PylonMG15120 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2R3-TANKWfrGr21", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            null, null, "RocketGunWfrGr21 1", "RocketGunWfrGr21 1", "PylonRO_WfrGr21 1", "PylonRO_WfrGr21 1", null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3R6-MG151-20", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", "MGunMG15120kh 135", "MGunMG15120kh 135", null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            "PylonMG15120 1", "PylonMG15120 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3R4-2XMK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMG15120MGki 200", "MGunMK108kh 35", "MGunMK108kh 35", null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            "PylonMk108 1", "PylonMk108 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U4-MK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U4R2-MK108WfrGr21", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", null, null, null, null, null, null, 
            null, null, "RocketGunWfrGr21 1", "RocketGunWfrGr21 1", "PylonRO_WfrGr21 1", "PylonRO_WfrGr21 1", null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U4R3-TANK1XMK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", null, null, null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U4R4-3XMK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", "MGunMK108kh 35", "MGunMK108kh 35", null, null, null, null, 
            "PylonMk108 1", "PylonMk108 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U4R6-MK1082XMG151-20", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", "MGunMG15120kh 135", "MGunMG15120kh 135", null, null, null, null, 
            "PylonMG15120 1", "PylonMG15120 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U4R3R4-3XMK108", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", "MGunMK108kh 35", "MGunMK108kh 35", null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            "PylonMk108 1", "PylonMk108 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U4R3R6-MK1082XMG151-20", new java.lang.String[] {
            "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", "MGunMG15120kh 135", "MGunMG15120kh 135", null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            "PylonMG15120 1", "PylonMG15120 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R5-NOMG131", new java.lang.String[] {
            null, null, null, "MGunMG15120ki 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonRECO1BF109 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R5-TANK-NOMG131", new java.lang.String[] {
            null, null, null, "MGunMG15120ki 200", null, null, null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonRECO1BF109 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R5-2XTANK-NOMG131", new java.lang.String[] {
            null, null, null, "MGunMG15120ki 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", "PylonRECO1BF109 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
