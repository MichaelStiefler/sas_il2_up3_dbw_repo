// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FW_190D13.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190, CockpitFW_190D11, PaintSchemeFMPar06, TypeBomber, 
//            AircraftLH, Aircraft, Cockpit, NetAircraft

public class FW_190D13 extends com.maddox.il2.objects.air.FW_190
    implements com.maddox.il2.objects.air.TypeBomber
{

    public FW_190D13()
    {
        kangle = 0.0F;
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
    }

    public void typeBomberAdjSpeedMinus()
    {
    }

    public void typeBomberUpdate(float f)
    {
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 20F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.FW_190D13.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() >= 0.98F)
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        {
            hierMesh().chunkVisible("30mmL_D0", false);
            hierMesh().chunkVisible("30mmR_D0", false);
        }
        if(getGunByHookName("_MGUN01") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("30mmL_D0", false);
        if(getGunByHookName("_MGUN02") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("30mmR_D0", false);
    }

    public void update(float f)
    {
        for(int i = 1; i < 13; i++)
            hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10F * kangle, 0.0F);

        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        float f1 = com.maddox.il2.ai.World.Rnd().nextFloat(0.87F, 1.04F);
        if(FM.isPlayers() && FM.CT.cockpitDoorControl > 0.9F && FM.getSpeedKMH() > 180F * f1 && FM.AS.aircraft.hierMesh().chunkFindCheck("Blister1_D0") != -1 && FM.AS.getPilotHealth(0) > 0.0F && FM.isPlayers() && FM.CT.cockpitDoorControl > 0.9F && FM.getSpeedKMH() > 180F * f1 && FM.AS.aircraft.hierMesh().chunkFindCheck("Wire_D0") != -1 && FM.AS.getPilotHealth(0) > 0.0F)
        {
            super.playSound("aircraft.arrach", true);
            if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                ((com.maddox.il2.objects.air.CockpitFW_190D11)com.maddox.il2.game.Main3D.cur3D().cockpitCur).blowoffcanopyforCirXdl();
            FM.AS.aircraft.hierMesh().hideSubTrees("Wire_D0");
            FM.AS.aircraft.hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage((com.maddox.il2.engine.ActorHMesh)FM.AS.actor, FM.AS.aircraft.hierMesh().chunkFind("Blister1_D0"));
            com.maddox.il2.objects.Wreckage wreckage1 = new Wreckage((com.maddox.il2.engine.ActorHMesh)FM.AS.actor, FM.AS.aircraft.hierMesh().chunkFind("Wire_D0"));
            wreckage.collide(true);
            wreckage1.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (FM.AS.aircraft)).FM)).Vwld);
            wreckage.setSpeed(vector3d);
            wreckage1.setSpeed(vector3d);
            FM.CT.cockpitDoorControl = 0.9F;
            FM.CT.bHasCockpitDoorControl = false;
            FM.VmaxAllowed = 161F;
            FM.Sq.dragEngineCx[0] *= 6.2F;
        }
        super.update(f);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -0.53F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
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

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190D13.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Fw-190D-13(Beta)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1943.11F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190D-13.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitFW_190D11.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 1, 9, 3, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15120MGk 250", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunMG15120MGk 250", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC501FW190 1", "BombGunSC250 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500", new java.lang.String[] {
            "MGunMG15120MGk 250", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC501FW190 1", "BombGunSC500 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC1000", new java.lang.String[] {
            "MGunMG15120MGk 250", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC501FW190 1", "BombGunSC1000 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSB1000", new java.lang.String[] {
            "MGunMG15120MGk 250", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC501FW190 1", "BombGunSB1000 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xPC1000RS", new java.lang.String[] {
            "MGunMG15120MGk 250", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "PylonETC501FW190 1", "RocketGunPC1000RS 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
