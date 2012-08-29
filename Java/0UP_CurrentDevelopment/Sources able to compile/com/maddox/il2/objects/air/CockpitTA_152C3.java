package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Time;

public class CockpitTA_152C3 extends CockpitPilot
{
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


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(Cockpit.P1);
            Cockpit.V.sub(Cockpit.P1, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Cockpit.V)).y, ((com.maddox.JGP.Tuple3d) (Cockpit.V)).x));
        }
    }

    public CockpitTA_152C3()
    {
        super("3DO/Cockpit/Ta-152C3/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictGear = 0.0F;
        setNew.dimPosition = 1.0F;
        super.cockpitNightMats = (new java.lang.String[] {
            "D9GP1", "D9GP2", "Ta152GP3", "A5GP3Km", "Ta152GP4_MW50", "Ta152GP5", "A4GP6", "Ta152Trans2", "D9GP2"
        });
        setNightMats(false);
        super.cockpitDimControl = !super.cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON03");
            gun[1] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON04");
            gun[2] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON05");
            gun[3] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON06");
        }
        if(bomb[0] == null)
        {
            for(int i = 0; i < bomb.length; i++)
                bomb[i] = com.maddox.il2.objects.weapons.GunEmpty.get();

            if(((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalBomb05") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[1] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalBomb05");
                bomb[2] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalBomb05");
            } else
            if(((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalDev02") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[1] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalDev02");
                bomb[2] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalDev02");
            }
            if(((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalRock01") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalRock01");
                bomb[3] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalRock02");
            } else
            if(((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalBomb03") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalBomb03");
                bomb[3] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalBomb04");
            } else
            if(((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalDev05") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalDev05");
                bomb[3] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getBulletEmitterByHookName("_ExternalDev06");
            }
        }
        resetYPRmodifier();
        float f1;
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl == 0.0F)
            f1 = 0.0205F;
        else
            f1 = 0.0F;
        pictGear = 0.93F * pictGear + 0.07F * f1;
        Cockpit.xyz[2] = pictGear;
        super.mesh.chunkSetLocate("FahrHandle", Cockpit.xyz, Cockpit.ypr);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) == 0)
        {
            super.mesh.chunkSetAngles("NeedleALT", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(setNew.altimeter, 0.0F, 10000F, 0.0F, -180F));
        }
        super.mesh.chunkSetAngles("NeedleManPress", -cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 15F, 345F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) == 0)
            super.mesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleFuel", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel / 0.72F, 0.0F, 400F, 0.0F, 4F), fuelScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleWaterTemp", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 60F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedleOilTemp", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 60F), 0.0F, 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) == 0)
        {
            super.mesh.chunkSetAngles("NeedleFuelPress", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) == 0)
        {
            float f2;
            if(aircraft().isFMTrackMirror())
            {
                f2 = aircraft().fmTrack().getCockpitAzimuthSpeed();
            } else
            {
                f2 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -6F, 6F, 20F, -20F);
                if(aircraft().fmTrack() != null)
                    aircraft().fmTrack().setCockpitAzimuthSpeed(f2);
            }
            super.mesh.chunkSetAngles("NeedleAHTurn", 0.0F, f2, 0.0F);
            super.mesh.chunkSetAngles("NeedleAHBank", 0.0F, -cvt(getBall(6D), -6F, 6F, 11F, -11F), 0.0F);
            super.mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren());
            super.mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 12F, -12F));
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) == 0)
            super.mesh.chunkSetAngles("NeedleCD", setNew.vspeed >= 0.0F ? -floatindex(cvt(setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale) : floatindex(cvt(-setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale), 0.0F, 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) == 0)
        {
            super.mesh.chunkSetAngles("RepeaterOuter", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("RepeaterPlane", interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) == 0 && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) == 0)
            super.mesh.chunkSetAngles("NeedleTrimmung", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl() * 25F * 2.0F, 0.0F, 0.0F);
        resetYPRmodifier();
        if(gun[1] != null)
        {
            Cockpit.xyz[0] = cvt(gun[1].countBullets(), 0.0F, 500F, -0.046F, 0.0F);
            super.mesh.chunkSetLocate("RC_MG17_L", Cockpit.xyz, Cockpit.ypr);
        }
        if(gun[2] != null)
        {
            Cockpit.xyz[0] = cvt(gun[2].countBullets(), 0.0F, 500F, -0.046F, 0.0F);
            super.mesh.chunkSetLocate("RC_MG17_R", Cockpit.xyz, Cockpit.ypr);
        }
        if(gun[0] != null)
        {
            Cockpit.xyz[0] = cvt(gun[0].countBullets(), 0.0F, 500F, -0.019F, 0.024F);
            super.mesh.chunkSetLocate("RC_MG151_L", Cockpit.xyz, Cockpit.ypr);
        }
        if(gun[3] != null)
        {
            Cockpit.xyz[0] = cvt(gun[3].countBullets(), 0.0F, 500F, -0.019F, 0.024F);
            super.mesh.chunkSetLocate("RC_MG151_R", Cockpit.xyz, Cockpit.ypr);
        }
        super.mesh.chunkSetAngles("IgnitionSwitch", 24F * (float)((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl) * 20F);
        resetYPRmodifier();
        Cockpit.xyz[2] = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.WeaponControl[1] ? -0.004F : 0.0F;
        super.mesh.chunkSetLocate("SecTrigger", Cockpit.xyz, Cockpit.ypr);
        Cockpit.ypr[0] = interp(setNew.throttle, setOld.throttle, f) * 34F * 0.91F;
        Cockpit.xyz[2] = Cockpit.ypr[0] > 7F ? -0.006F : 0.0F;
        super.mesh.chunkSetLocate("ThrottleQuad", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("RPedalBase", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F);
        super.mesh.chunkSetAngles("RPedalStrut", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F);
        super.mesh.chunkSetAngles("RPedal", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getBrake() * 15F);
        super.mesh.chunkSetAngles("LPedalBase", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F);
        super.mesh.chunkSetAngles("LPedalStrut", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F);
        super.mesh.chunkSetAngles("LPedal", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getBrake() * 15F);
        super.mesh.chunkVisible("XLampTankSwitch", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel > 144F);
        super.mesh.chunkVisible("XLampFuelLow", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 43.2F);
        super.mesh.chunkVisible("XLampGearL_1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.05F);
        super.mesh.chunkVisible("XLampGearL_2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.95F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
        super.mesh.chunkVisible("XLampGearR_1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.05F);
        super.mesh.chunkVisible("XLampGearR_2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.95F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkVisible("XLampGearC_1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.05F);
        super.mesh.chunkVisible("XLampGearC_2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.95F);
        resetYPRmodifier();
        Cockpit.xyz[2] = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl < 0.5F ? 0.02F : 0.0F;
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    public void toggleLight()
    {
        super.cockpitLightControl = !super.cockpitLightControl;
        if(super.cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
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
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
        {
            super.mesh.chunkVisible("Revi16", false);
            super.mesh.chunkVisible("Revi16Tinter", false);
            super.mesh.chunkVisible("Z_Z_MASK", false);
            super.mesh.chunkVisible("Z_Z_RETICLE", false);
            super.mesh.chunkVisible("DRevi16", true);
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.materialReplace("D9GP1", "DD9GP1");
            super.mesh.materialReplace("D9GP1_night", "DD9GP1_night");
            super.mesh.chunkVisible("NeedleManPress", false);
            super.mesh.chunkVisible("NeedleRPM", false);
            super.mesh.chunkVisible("RepeaterOuter", false);
            super.mesh.chunkVisible("RepeaterPlane", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage2", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
            super.mesh.materialReplace("D9GP2", "DD9GP2");
            super.mesh.materialReplace("D9GP2_night", "DD9GP2_night");
            super.mesh.chunkVisible("NeedleAHCyl", false);
            super.mesh.chunkVisible("NeedleAHBank", false);
            super.mesh.chunkVisible("NeedleAHBar", false);
            super.mesh.chunkVisible("NeedleAHTurn", false);
            super.mesh.chunkVisible("NeedleFuelPress", false);
            super.mesh.chunkVisible("NeedleOilPress", false);
            super.mesh.materialReplace("Ta152GP4_MW50", "DTa152GP4");
            super.mesh.materialReplace("Ta152GP4_MW50_night", "DTa152GP4_night");
            super.mesh.chunkVisible("NeedleFuel", false);
            resetYPRmodifier();
            Cockpit.xyz[0] = -0.001F;
            Cockpit.xyz[1] = 0.008F;
            Cockpit.xyz[2] = 0.025F;
            Cockpit.ypr[0] = 3F;
            Cockpit.ypr[1] = -6F;
            Cockpit.ypr[2] = 1.5F;
            super.mesh.chunkSetLocate("IPCentral", Cockpit.xyz, Cockpit.ypr);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.materialReplace("Ta152GP3", "DTa152GP3");
            super.mesh.materialReplace("Ta152GP3_night", "DTa152GP3_night");
            super.mesh.chunkVisible("NeedleKMH", false);
            super.mesh.chunkVisible("NeedleCD", false);
            super.mesh.chunkVisible("NeedleAlt", false);
            super.mesh.chunkVisible("NeedleAltKM", false);
            super.mesh.materialReplace("Ta152Trans2", "DTa152Trans2");
            super.mesh.materialReplace("Ta152Trans2_night", "DTa152Trans2_night");
            super.mesh.chunkVisible("NeedleWaterTemp", false);
            super.mesh.chunkVisible("NeedleOilTemp", false);
        }
        retoggleLight();
    }

    private float tmp;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null, null, null
    };
    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    private com.maddox.il2.ai.BulletEmitter bomb[] = {
        null, null, null, null
    };
    private float pictAiler;
    private float pictElev;
    private float pictGear;
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







}
