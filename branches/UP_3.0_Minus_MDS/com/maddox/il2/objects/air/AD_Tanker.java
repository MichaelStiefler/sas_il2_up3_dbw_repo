// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AD_Tanker.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeTransport, TypeTankerDrogue, PaintScheme, 
//            Aircraft, NetAircraft, Cockpit, AircraftLH

public abstract class AD_Tanker extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeTransport, com.maddox.il2.objects.air.TypeTankerDrogue
{

    public AD_Tanker()
    {
        prevGear = 0.0F;
        prevGear2 = 0.0F;
        prevWing = 1.0F;
        cGearPos = 0.0F;
        cGear = 0.0F;
        bNeedSetup = true;
    }

    protected void moveAileron(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * f, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * f, 0.0F);
    }

    public void onAircraftLoaded()
    {
        ((com.maddox.il2.objects.air.Aircraft)this).onAircraftLoaded();
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f, com.maddox.il2.fm.FlightModel flightmodel)
    {
        float f1 = 10F * f;
        if(flightmodel != null)
            f = 10F * java.lang.Math.max(f, ((com.maddox.il2.fm.FlightModelMain) (flightmodel)).CT.getAirBrake());
        else
            f = f1;
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        if(bGearExtending)
        {
            if(flightmodel == null)
            {
                float f2 = com.maddox.il2.objects.air.Aircraft.cvt(f1, 0.0F, 1.0F, 0.0F, 0.7071068F);
                f2 = 2.0F * f2 * f2;
                hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 41F * f2, 0.0F);
                f2 = com.maddox.il2.objects.air.Aircraft.cvt(f1, 0.0F, 0.25F, 0.0F, 1.0F);
            }
            float f3;
            float f6;
            if(f < 4F)
            {
                f3 = f6 = com.maddox.il2.objects.air.Aircraft.cvt(f, 3F, 4F, 0.0F, 0.4F);
            } else
            {
                f3 = com.maddox.il2.objects.air.Aircraft.cvt(f, 4F, 8F, 0.75F, 2.0F);
                f3 = (float)java.lang.Math.sqrt(f3);
                f3 = com.maddox.il2.objects.air.Aircraft.cvt(f3, (float)java.lang.Math.sqrt(0.75D), (float)java.lang.Math.sqrt(2D), 0.4F, 1.0F);
                f6 = com.maddox.il2.objects.air.Aircraft.cvt(f, 4F, 8.5F, 0.75F, 2.0F);
                f6 = (float)java.lang.Math.sqrt(f6);
                f6 = com.maddox.il2.objects.air.Aircraft.cvt(f6, (float)java.lang.Math.sqrt(0.75D), (float)java.lang.Math.sqrt(2D), 0.4F, 1.0F);
            }
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 81F * f3, 0.0F);
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 84F * f3, 0.0F);
            hiermesh.chunkSetAngles("GearL8_D0", 0.0F, 83F * f3, 0.0F);
            hiermesh.chunkSetAngles("GearL33_D0", 0.0F, -104F * f3, 0.0F);
            hiermesh.chunkSetAngles("GearL6_D0", 0.0F, 40F * f3, 0.0F);
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -168F * f3, 0.0F);
            hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -90F * f3, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 81F * f6, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 84F * f6, 0.0F);
            hiermesh.chunkSetAngles("GearR8_D0", 0.0F, 83F * f6, 0.0F);
            hiermesh.chunkSetAngles("GearR33_D0", 0.0F, -104F * f6, 0.0F);
            hiermesh.chunkSetAngles("GearR6_D0", 0.0F, 40F * f6, 0.0F);
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -168F * f6, 0.0F);
            hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -90F * f6, 0.0F);
        } else
        {
            if(flightmodel == null)
            {
                float f4 = com.maddox.il2.objects.air.Aircraft.cvt(f1, 8.5F, 10F, 0.0F, 1.0F);
                hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 41F * f4, 0.0F);
                f4 = com.maddox.il2.objects.air.Aircraft.cvt(f1, 8.5F, 8.75F, 0.0F, 1.0F);
            }
            float f5;
            if(f > 7.5F)
                f5 = com.maddox.il2.objects.air.Aircraft.cvt(f, 7.5F, 8.5F, 0.9F, 1.0F);
            else
                f5 = com.maddox.il2.objects.air.Aircraft.cvt(f, 3F, 7.5F, 0.0F, 0.9F);
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 81F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 70F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearL8_D0", 0.0F, 83F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearL33_D0", 0.0F, -104F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearL6_D0", 0.0F, 40F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -168F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -90F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 81F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 84F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearR8_D0", 0.0F, 83F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearR33_D0", 0.0F, -104F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearR6_D0", 0.0F, 40F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -168F * f5, 0.0F);
            hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -90F * f5, 0.0F);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        com.maddox.il2.objects.air.AD_Tanker.moveGear(hiermesh, f, ((com.maddox.il2.fm.FlightModel) (null)));
    }

    protected void moveGear(float f)
    {
        if(prevGear > f)
            bGearExtending = false;
        else
            bGearExtending = true;
        prevGear = f;
        com.maddox.il2.objects.air.AD_Tanker.moveGear(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), f, ((com.maddox.il2.objects.sounds.SndAircraft)this).FM);
        f *= 10F;
        if(bGearExtending)
        {
            float f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 1.0F, 0.0F, 0.7071068F);
            cGearPos = 2.0F * f1 * f1;
        } else
        {
            cGearPos = com.maddox.il2.objects.air.Aircraft.cvt(f, 8.5F, 10F, 0.0F, 1.0F);
        }
    }

    public void moveSteering(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveArrestorHook(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -37F * f, 0.0F);
        arrestor = f;
    }

    public void moveWheelSink()
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.gWheelSinking[0], 0.0F, 0.231F, 0.0F, 0.231F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("GearL7_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.gWheelSinking[1], 0.0F, 0.231F, 0.0F, -0.231F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("GearR7_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        f *= 18F;
        if(bGearExtending)
        {
            if(f < 1.5F)
            {
                hiermesh.chunkSetAngles("WingLFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 1.5F, 0.0F, 2.6F), 0.0F);
                hiermesh.chunkSetAngles("WingRFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 1.5F, 0.0F, 2.6F), 0.0F);
            } else
            if(f < 2.5F)
            {
                hiermesh.chunkSetAngles("WingLFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 1.5F, 2.5F, 2.6F, 5.1F), 0.0F);
                hiermesh.chunkSetAngles("WingRFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 1.5F, 2.5F, 2.6F, 5.1F), 0.0F);
            } else
            {
                hiermesh.chunkSetAngles("WingLFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 2.5F, 17.9F, 5.1F, 105F), 0.0F);
                hiermesh.chunkSetAngles("WingRFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 2.5F, 16F, 5.1F, 105F), 0.0F);
            }
        } else
        if(f < 9F)
        {
            if(f < 6.8F)
                hiermesh.chunkSetAngles("WingRFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 6.8F, 0.0F, 45F), 0.0F);
            else
                hiermesh.chunkSetAngles("WingRFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 6.8F, 9F, 45F, 50F), 0.0F);
            if(f < 7.5F)
                hiermesh.chunkSetAngles("WingLFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.75F, 7.5F, 0.0F, 45F), 0.0F);
            else
                hiermesh.chunkSetAngles("WingLFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 7.5F, 9F, 45F, 50F), 0.0F);
        } else
        if(f < 11F)
        {
            hiermesh.chunkSetAngles("WingLFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 9F, 11F, 50F, 60F), 0.0F);
            hiermesh.chunkSetAngles("WingRFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 9F, 11F, 50F, 60F), 0.0F);
        } else
        {
            hiermesh.chunkSetAngles("WingLFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 11F, 15.75F, 60F, 105F), 0.0F);
            hiermesh.chunkSetAngles("WingRFold_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 11F, 15.75F, 60F, 105F), 0.0F);
        }
    }

    public void moveWingFold(float f)
    {
        if(prevWing > f)
            bGearExtending = false;
        else
            bGearExtending = true;
        prevWing = f;
        if(f < 0.001F)
        {
            ((com.maddox.il2.objects.air.NetAircraft)this).setGunPodsOn(true);
            ((com.maddox.il2.objects.air.Aircraft)this).hideWingWeapons(false);
        } else
        {
            ((com.maddox.il2.objects.air.NetAircraft)this).setGunPodsOn(false);
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.WeaponControl[0] = false;
            ((com.maddox.il2.objects.air.Aircraft)this).hideWingWeapons(true);
        }
        moveWingFold(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), f);
    }

    protected void moveAirBrake(float f)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -70F * f, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("BrakeB01_D0", 0.0F, -30F * f, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -70F * f, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("BrakeB02_D0", 0.0F, 30F * f, 0.0F);
        if((double)f < 0.20000000000000001D)
            com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.18F, 0.0F, -0.05F);
        else
            com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.22F, 0.99F, -0.05F, -0.22F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("BrakeB01e_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("BrakeB02e_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void moveCockpitDoor(float f)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.625F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.06845F);
        com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 1.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.13F);
        com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -8F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("Pilot1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            ((com.maddox.il2.objects.sounds.SndAircraft)this).setDoorSnd(f);
        }
    }

    protected void moveFlap(float f)
    {
        float f1 = 55F * f;
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap1_D0", 0.0F, 0.0F, f1);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap2_D0", 0.0F, 0.0F, f1);
    }

    public void update(float f)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).update(f);
        if(bNeedSetup)
        {
            cGear = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.GearControl;
            bNeedSetup = false;
        }
        cGear = com.maddox.il2.objects.air.AD_Tanker.filter(f, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.GearControl, cGear, 999.9F, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.dvGear);
        if(prevGear2 > cGear)
            bGearExtending2 = false;
        else
            bGearExtending2 = true;
        prevGear2 = cGear;
        float f1 = 10F * cGear;
        if(bGearExtending2)
        {
            float f2 = com.maddox.il2.objects.air.Aircraft.cvt(f1, 0.0F, 1.0F, 0.0F, 0.7071068F);
            cGearPos = 2.0F * f2 * f2;
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC2_D0", 0.0F, 41F * cGearPos, 0.0F);
            f2 = com.maddox.il2.objects.air.Aircraft.cvt(f1, 0.0F, 0.25F, 0.0F, 1.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC4_D0", 0.0F, 140F * f2, 0.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC5_D0", 0.0F, 140F * f2, 0.0F);
        } else
        {
            cGearPos = com.maddox.il2.objects.air.Aircraft.cvt(f1, 8.5F, 10F, 0.0F, 1.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC2_D0", 0.0F, 41F * cGearPos, 0.0F);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude() < 1000F || (double)((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.getGear() > 0.0D || (double)((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.getArrestor() > 0.0D)
        {
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("FuelLine1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Drogue1_D0", false);
        } else
        {
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("FuelLine1_D0", true);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Drogue1_D0", true);
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D1", true);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            break;
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.78F, shot);
                else
                if(s.endsWith("g1"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(9.96F / (1E-005F + (float)java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x)), shot);
                else
                if(s.endsWith("g2"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(30F, 50F) / (1E-005F + (float)java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x)), shot);
            } else
            if(s.startsWith("xxcontrols"))
            {
                if(s.endsWith("1"))
                {
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.12F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Evelator Controls Out..");
                    }
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.12F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Rudder Controls Out..");
                    }
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.12F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Arone Controls Out..");
                    }
                } else
                if(s.endsWith("2"))
                {
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Evelator Controls Out..");
                    }
                } else
                if(s.endsWith("3") && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Rudder Controls Out..");
                }
            } else
            if(s.startsWith("xxspar"))
            {
                if((s.endsWith("t1") || s.endsWith("t2") || s.endsWith("t3") || s.endsWith("t4")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y + ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Tail1 Spars Broken in Half..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                if((s.endsWith("li1") || s.endsWith("li2") || s.endsWith("li3") || s.endsWith("li4")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLIn Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if((s.endsWith("ri1") || s.endsWith("ri2") || s.endsWith("ri3") || s.endsWith("ri4")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRIn Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if((s.endsWith("lm1") || s.endsWith("lm2") || s.endsWith("lm3") || s.endsWith("lm4")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if((s.endsWith("rm1") || s.endsWith("rm2") || s.endsWith("rm3") || s.endsWith("rm4")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if((s.endsWith("lo1") || s.endsWith("lo2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLOut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if((s.endsWith("ro1") || s.endsWith("ro2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingROut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if((s.endsWith("sl1") || s.endsWith("sl2") || s.endsWith("sl3") || s.endsWith("sl4") || s.endsWith("sl5")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabL") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** StabL Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "StabL_D3", shot.initiator);
                }
                if((s.endsWith("sr1") || s.endsWith("sr2") || s.endsWith("sr3") || s.endsWith("sr4") || s.endsWith("sr5")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabR") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** StabR Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "StabR_D3", shot.initiator);
                }
                if(s.endsWith("e1"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6F, shot);
            } else
            if(s.startsWith("xxeng1"))
            {
                if(s.endsWith("prp") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setKillPropAngleDevice(shot.initiator);
                if(s.endsWith("cas") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 200000F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineStuck(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Engine Stucks..");
                    }
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 50000F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Engine Damaged..");
                    }
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 28000F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Cylinder Feed Out, " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylinders() + " Left..");
                    }
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setReadyness(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() + "..");
                }
                if(s.endsWith("cyl") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.45F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersRatio() * 1.75F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Cylinders Hit, " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylinders() + " Left..");
                    if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateEngineStates[0] < 1)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 1);
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 24000F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 3);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Cylinders Hit - Engine Fires..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("sup") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.05F, shot) > 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setKillCompressor(shot.initiator);
            } else
            if(s.startsWith("xxtank"))
            {
                int i = s.charAt(6) - 49;
                if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, i, 1);
                    if(shot.powerType == 3 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.11F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, i, 2);
                }
            } else
            {
                if(s.startsWith("xxmgunl1") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, 0);
                if(s.startsWith("xxmgunr1") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, 1);
                if(s.startsWith("xxmgunl2") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, 2);
                if(s.startsWith("xxmgunr2") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, 3);
                if(s.startsWith("xxhispa1") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 0);
                if(s.startsWith("xxhispa2") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 1);
                if(s.startsWith("xxhispa3") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 2);
                if(s.startsWith("xxhispa4") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 3);
            }
        } else
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
        {
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("CF", shot);
            if(((com.maddox.JGP.Tuple3d) (point3d)).x > -2.2000000000000002D)
            {
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x40);
                if(((com.maddox.JGP.Tuple3d) (point3d)).x < -1D && ((com.maddox.JGP.Tuple3d) (point3d)).z > 0.55000000000000004D)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 2);
                if(((com.maddox.JGP.Tuple3d) (point3d)).z > 0.65000000000000002D)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 1);
                if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) < 0.80000001192092896D)
                    if(((com.maddox.JGP.Tuple3d) (point3d)).y > 0.0D)
                    {
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 4);
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 8);
                    } else
                    {
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x10);
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x20);
                    }
            }
        } else
        if(s.startsWith("xeng"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Engine1") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xtail"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Keel1", shot);
        else
        if(s.startsWith("xrudder"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Rudder1", shot);
        else
        if(s.startsWith("xstab"))
        {
            if(s.startsWith("xstabl") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabL") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabL", shot);
            if(s.startsWith("xstabr") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabR") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabR", shot);
        } else
        if(s.startsWith("xvator"))
        {
            if(s.startsWith("xvatorl"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorL", shot);
            if(s.startsWith("xvatorr"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorR", shot);
        } else
        if(s.startsWith("xwing"))
        {
            if(s.startsWith("xwinglin") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLIn", shot);
            if(s.startsWith("xwingrin") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRIn", shot);
            if(s.startsWith("xwinglmid"))
            {
                if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLMid", shot);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.mass + 0.02F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitOil(shot.initiator, 0);
            }
            if(s.startsWith("xwingrmid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRMid", shot);
            if(s.startsWith("xwinglout") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLOut", shot);
            if(s.startsWith("xwingrout") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xarone"))
        {
            if(s.startsWith("xaronel"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneL", shot);
            if(s.startsWith("xaroner"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneR", shot);
        } else
        if(s.startsWith("xoil"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.5F, shot) > 0.0F)
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Oil Radiator Hit, Oil Radiator Pierced..");
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitOil(shot.initiator, 0);
            }
        } else
        if(s.startsWith("xwater"))
        {
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateEngineStates[0] == 0)
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Water Radiator Pierced..");
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 1);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.doSetEngineState(shot.initiator, 0, 1);
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateEngineStates[0] == 1)
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Water Radiator Pierced..");
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 1);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.doSetEngineState(shot.initiator, 0, 2);
            }
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int j;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                j = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                j = s.charAt(6) - 49;
            } else
            {
                j = s.charAt(5) - 49;
            }
            ((com.maddox.il2.objects.air.AircraftLH)this).hitFlesh(j, shot, ((int) (byte0)));
        }
    }

    public void rareAction(float f, boolean flag)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).rareAction(f, flag);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude() < 3000F)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
        else
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Head1_D0"));
    }

    private static final float filter(float f, float f1, float f2, float f3, float f4)
    {
        float f5 = (float)java.lang.Math.exp(-f / f3);
        float f6 = f1 + (f2 - f1) * f5;
        if(f6 < f1)
        {
            f6 += f4 * f;
            if(f6 > f1)
                f6 = f1;
        } else
        if(f6 > f1)
        {
            f6 -= f4 * f;
            if(f6 < f1)
                f6 = f1;
        }
        return f6;
    }

    private float prevGear;
    private float prevGear2;
    private static boolean bGearExtending = false;
    private static boolean bGearExtending2 = false;
    private float prevWing;
    private float cGearPos;
    private float cGear;
    private boolean bNeedSetup;
    protected float arrestor;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.AD_Tanker.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
