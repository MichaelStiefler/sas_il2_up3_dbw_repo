// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitFW_190A1.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitFW_190A1 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.altimeter = fm.getAltitude();
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
                setNew.vspeed = (499F * setOld.vspeed + fm.getVertSpeed()) / 500F;
                setNew.azimuth = ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float altimeter;
        float throttle;
        float dimPosition;
        float azimuth;
        float waypointAzimuth;
        float vspeed;

        private Variables()
        {
        }

    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("Blister1_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("Blister1_D0", true);
        super.doFocusLeave();
    }

    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(com.maddox.il2.objects.air.Cockpit.P1);
            com.maddox.il2.objects.air.Cockpit.V.sub(com.maddox.il2.objects.air.Cockpit.P1, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.V)).y, ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.V)).x));
        }
    }

    public CockpitFW_190A1()
    {
        super("3DO/Cockpit/FW-190A-1/hier.him", "bf109");
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        setNew.dimPosition = 1.0F;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(super.mesh, "LIGHTHOOK_L");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        hooknamed = new HookNamed(super.mesh, "LIGHTHOOK_R");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(126F, 232F, 245F);
        light2.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK2", light2);
        super.cockpitNightMats = (new java.lang.String[] {
            "A4GP1", "A4GP2", "A4GP3", "A4GP4", "A4GP5", "A4GP6", "A5GP3Km"
        });
        setNightMats(false);
        super.cockpitDimControl = !super.cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN02");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON03");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON04");
            gun[4] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON01");
            gun[5] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON02");
        }
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.43F);
        super.mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyTop", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("Glass", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, -0.33F);
        super.mesh.chunkSetLocate("RearArmorPlate", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("NeedleALT", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(setNew.altimeter, 0.0F, 10000F, 0.0F, -180F));
        super.mesh.chunkSetAngles("NeedleManPress", -cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleFuel", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel / 0.72F, 0.0F, 400F, 0.0F, 4F), fuelScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleOilTemp", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 3F), oilTempScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleFuelPress", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        float f1;
        if(aircraft().isFMTrackMirror())
        {
            f1 = aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f1 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -6F, 6F, -50F, 50F);
            if(aircraft().fmTrack() != null)
                aircraft().fmTrack().setCockpitAzimuthSpeed(f1);
        }
        super.mesh.chunkSetAngles("NeedleAHTurn", f1, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleAHBank", cvt(getBall(7D), -7F, 7F, 11F, -11F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren());
        super.mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 12F, -12F));
        super.mesh.chunkSetAngles("NeedleCD", setNew.vspeed >= 0.0F ? -floatindex(cvt(setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale) : floatindex(cvt(-setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RepeaterOuter", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RepeaterPlane", interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleHBSmall", -105F + (float)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhi() - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhiMin()) * 5F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleHBLarge", -270F + (float)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhi() - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleTrimmung", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl() * 25F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleHClock", -cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleMClock", -cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleSClock", -cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        resetYPRmodifier();
        if(gun[0] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[0].countBullets(), 0.0F, 500F, -0.044F, 0.0F);
            super.mesh.chunkSetLocate("RC_MG17_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            super.mesh.chunkVisible("XLampMG17_1", !gun[0].haveBullets());
        }
        if(gun[1] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[1].countBullets(), 0.0F, 500F, -0.044F, 0.0F);
            super.mesh.chunkSetLocate("RC_MG17_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            super.mesh.chunkVisible("XLampMG17_2", !gun[1].haveBullets());
        }
        if(gun[4] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[4].countBullets(), 0.0F, 200F, -0.017F, 0.0F);
            super.mesh.chunkSetLocate("RC_MG151_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[5] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[5].countBullets(), 0.0F, 200F, -0.017F, 0.0F);
            super.mesh.chunkSetLocate("RC_MG151_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[2] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[2].countBullets(), 0.0F, 55F, -0.018F, 0.0F);
            super.mesh.chunkSetLocate("RC_MGFF_WingL", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[3] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[3].countBullets(), 0.0F, 55F, -0.018F, 0.0F);
            super.mesh.chunkSetLocate("RC_MGFF_WingR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        super.mesh.chunkSetAngles("IgnitionSwitch", 24F * (float)((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 0.058F);
        super.mesh.chunkSetLocate("Revi16Tinter", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl) * 20F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.WeaponControl[1] ? -0.004F : 0.0F;
        super.mesh.chunkSetLocate("SecTrigger", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.ypr[0] = interp(setNew.throttle, setOld.throttle, f) * 34F * 0.91F;
        com.maddox.il2.objects.air.Cockpit.xyz[2] = com.maddox.il2.objects.air.Cockpit.ypr[0] > 7F ? -0.006F : 0.0F;
        super.mesh.chunkSetLocate("ThrottleQuad", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("RPedalBase", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F);
        super.mesh.chunkSetAngles("RPedalStrut", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F);
        super.mesh.chunkSetAngles("RPedal", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getBrake() * 15F);
        super.mesh.chunkSetAngles("LPedalBase", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F);
        super.mesh.chunkSetAngles("LPedalStrut", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F);
        super.mesh.chunkSetAngles("LPedal", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getBrake() * 15F);
        super.mesh.chunkVisible("XLampTankSwitch", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel > 144F);
        super.mesh.chunkVisible("XLampFuelLow", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 43.2F);
        super.mesh.chunkVisible("XLampFlapLPos_3", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() > 0.9F);
        super.mesh.chunkVisible("XLampFlapLPos_2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() > 0.1F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() < 0.5F);
        super.mesh.chunkVisible("XLampFlapLPos_1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() < 0.1F);
        super.mesh.chunkVisible("XLampFlapRPos_3", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() > 0.9F);
        super.mesh.chunkVisible("XLampFlapRPos_2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() > 0.1F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() < 0.5F);
        super.mesh.chunkVisible("XLampFlapRPos_1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() < 0.1F);
        super.mesh.chunkVisible("XLampGearL_1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.05F);
        super.mesh.chunkVisible("XLampGearL_2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.95F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
        super.mesh.chunkVisible("XLampGearR_1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.05F);
        super.mesh.chunkVisible("XLampGearR_2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.95F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkVisible("XLampGearC_1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.05F);
        super.mesh.chunkVisible("XLampGearC_2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.95F);
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    public void toggleLight()
    {
        super.cockpitLightControl = !super.cockpitLightControl;
        if(super.cockpitLightControl)
        {
            light1.light.setEmit(0.005F, 0.75F);
            light2.light.setEmit(0.005F, 0.75F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    private void retoggleLight()
    {
        if(super.cockpitLightControl)
        {
            setNightMats(false);
            setNightMats(true);
        } else
        {
            setNightMats(true);
            setNightMats(false);
        }
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
        {
            if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
            {
                super.mesh.chunkVisible("Revi16", false);
                super.mesh.chunkVisible("Revi16Tinter", false);
                super.mesh.chunkVisible("Z_Z_MASK", false);
                super.mesh.chunkVisible("Z_Z_RETICLE", false);
                super.mesh.chunkVisible("DRevi16", true);
            }
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("HullDamage1", true);
            super.mesh.materialReplace("A4GP1", "DA4GP1");
            super.mesh.materialReplace("A4GP1_night", "DA4GP1_night");
            super.mesh.chunkVisible("NeedleRPM", false);
            super.mesh.chunkVisible("NeedleManPress", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage2", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
            super.mesh.chunkVisible("HullDamage3", true);
            super.mesh.materialReplace("A4GP2", "DA4GP2");
            super.mesh.materialReplace("A4GP2_night", "DA4GP2_night");
            super.mesh.chunkVisible("NeedleAHBank", false);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = -0.01F;
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -0.01F;
            com.maddox.il2.objects.air.Cockpit.ypr[0] = -5F;
            com.maddox.il2.objects.air.Cockpit.ypr[2] = -5F;
            super.mesh.chunkSetLocate("IPCentral", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            super.mesh.chunkVisible("NeedleOilPress", false);
            super.mesh.chunkVisible("NeedleFuelPress", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("HullDamage2", true);
            super.mesh.materialReplace("A4GP3", "DA4GP3");
            super.mesh.materialReplace("A4GP3_night", "DA4GP3_night");
            super.mesh.chunkVisible("NeedleALT", false);
            super.mesh.chunkVisible("NeedleKMH", false);
        }
        retoggleLight();
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

    private float tmp;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null, null, null, null, null
    };
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 18.5F, 67F, 117F, 164F, 215F, 267F, 320F, 379F, 427F, 
        428F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 53F, 108F, 170F, 229F, 282F, 334F, 342.5F, 342.5F
    };
    private static final float fuelScale[] = {
        0.0F, 16F, 35F, 52.5F, 72F, 72F
    };
    private static final float manPrsScale[] = {
        0.0F, 0.0F, 0.0F, 15.5F, 71F, 125F, 180F, 235F, 290F, 245F, 
        247F, 247F
    };
    private static final float oilfuelNeedleScale[] = {
        0.0F, 38F, 84F, 135.5F, 135F
    };
    private static final float vsiNeedleScale[] = {
        0.0F, 48F, 82F, 96.5F, 111F, 120.5F, 130F, 130F
    };
    private static final float oilTempScale[] = {
        0.0F, 23F, 52F, 81F, 81F
    };

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitFW_190A1.class, "normZN", 0.72F);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitFW_190A1.class, "gsZN", 0.66F);
    }






}
