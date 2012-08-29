// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitAR_234B2NJ.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, AR_234B2NJ, Cockpit, Aircraft

public class CockpitAR_234B2NJ extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle1 = 0.92F * setOld.throttle1 + 0.08F * fm.EI.engines[0].getControlThrottle();
                setNew.throttle2 = 0.92F * setOld.throttle2 + 0.08F * fm.EI.engines[1].getControlThrottle();
                setNew.vspeed = (499F * setOld.vspeed + fm.getVertSpeed()) / 500F;
                setNew.azimuth = fm.Or.getYaw();
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
        float throttle1;
        float throttle2;
        float azimuth;
        float waypointAzimuth;
        float vspeed;

        private Variables()
        {
        }

    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(com.maddox.il2.objects.air.Cockpit.P1);
            com.maddox.il2.objects.air.Cockpit.V.sub(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.P1)), ((com.maddox.JGP.Tuple3d) (fm.Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(com.maddox.il2.objects.air.Cockpit.V.y, com.maddox.il2.objects.air.Cockpit.V.x));
        }
    }

    public CockpitAR_234B2NJ()
    {
        super("3DO/Cockpit/Ar-234B-2/hierNJ.him", "bf109");
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictGear = 0.0F;
        bEntered = false;
        cockpitNightMats = (new java.lang.String[] {
            "D_gauges_1_TR", "D_gauges_2_TR", "D_gauges_3_TR", "D_gauges_4_TR", "D_gauges_6_TR", "gauges_1_TR", "gauges_2_TR", "gauges_3_TR", "gauges_4_TR", "gauges_5_TR", 
            "gauges_6_TR", "gauges_7_TR", "gauges_8_TR", "gauges_9_TR"
        });
        setNightMats(false);
        interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("zAltWheel", 0.0F, cvt(((com.maddox.il2.objects.air.AR_234B2NJ)aircraft()).fSightCurAltitude, 0.0F, 6000F, 0.0F, 225F), 0.0F);
        mesh.chunkSetAngles("zAnglePointer", 0.0F, cvt(((com.maddox.il2.objects.air.AR_234B2NJ)aircraft()).fSightCurForwardAngle, 0.0F, 60F, 105F, 0.0F), 0.0F);
        mesh.chunkSetAngles("zAngleWheel", 0.0F, -10F * ((com.maddox.il2.objects.air.AR_234B2NJ)aircraft()).fSightCurForwardAngle, 0.0F);
        mesh.chunkSetAngles("zSpeedPointer", 0.0F, cvt(((com.maddox.il2.objects.air.AR_234B2NJ)aircraft()).fSightCurSpeed, 150F, 600F, 0.0F, 60F), 0.0F);
        mesh.chunkSetAngles("zSpeedWheel", 0.0F, 0.333F * ((com.maddox.il2.objects.air.AR_234B2NJ)aircraft()).fSightCurSpeed, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getRudder(), -1F, 1.0F, -0.03F, 0.03F);
        mesh.chunkSetLocate("PedalL", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -com.maddox.il2.objects.air.Cockpit.xyz[1];
        mesh.chunkSetLocate("PedalR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        pictGear = 0.8F * pictGear + 0.2F * fm.CT.GearControl;
        mesh.chunkSetAngles("ruchkaShassi", cvt(pictGear, 0.0F, 1.0F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("ruchkaGaza1", cvt(interp(setNew.throttle1, setOld.throttle1, f), 0.0F, 1.0F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("ruchkaGaza2", cvt(interp(setNew.throttle2, setOld.throttle2, f), 0.0F, 1.0F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("ruchkaSopla", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("ruchkaFuel1", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("ruchkaFuel2", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("ETrim", -30F * fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("RTrim", -300F * fm.CT.getTrimRudderControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("os_V", -15F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Srul", 60F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 0.0F);
        resetYPRmodifier();
        if(cockpitLightControl)
            com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.00365F;
        mesh.chunkSetLocate("Z_lightSW", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = fm.CT.saveWeaponControl[0] ? 0.0059F : 0.0F;
        mesh.chunkSetLocate("r_one", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = fm.CT.saveWeaponControl[3] ? 0.0059F : 0.0F;
        mesh.chunkSetLocate("r_two", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(-fm.CT.trimElevator, -0.5F, 0.5F, -0.0475F, 0.0475F);
        mesh.chunkSetLocate("Need_ETrim", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(-fm.CT.trimRudder, -0.5F, 0.5F, -0.029F, 0.029F);
        mesh.chunkSetLocate("Need_RTrim", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("zClock1a", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zClock1b", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zClock1c", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAirTemp", cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z), 213.09F, 313.09F, -30F, 20F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zHydropress", fm.Gears.isHydroOperable() ? 120F : 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zMCompc", 0.0F, 0.0F, cvt(fm.Or.getTangage(), -22.2F, 22.2F, -22.2F, 22.2F));
        mesh.chunkSetAngles("zMCompa", cvt(fm.Or.getKren(), -22.2F, 22.2F, -22.2F, 22.2F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zMCompb", -90F + interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("zSpeed1a", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt1a", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt1b", cvt(setNew.altimeter, 0.0F, 14000F, 0.0F, 315F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPM1", 22.5F + floatindex(cvt(fm.EI.engines[0].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPM2", 22.5F + floatindex(cvt(fm.EI.engines[1].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGasPrs1a", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.6F * fm.EI.engines[0].getPowerOutput(), 0.0F, 1.0F, 0.0F, 270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGasPrs1b", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.6F * fm.EI.engines[0].getPowerOutput(), 0.0F, 1.0F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGasPrs2a", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.6F * fm.EI.engines[1].getPowerOutput(), 0.0F, 1.0F, 0.0F, 270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGasPrs2b", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.6F * fm.EI.engines[1].getPowerOutput(), 0.0F, 1.0F, 0.0F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilPrs1a", cvt(1.0F + 0.005F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilPrs2a", cvt(1.0F + 0.005F * fm.EI.engines[1].tOilOut, 0.0F, 10F, 0.0F, 270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuelPrs1a", cvt(fm.M.fuel <= 1.0F ? 0.0F : 2.2F * fm.EI.engines[0].getPowerOutput() + 0.005F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuelPrs2a", cvt(fm.M.fuel <= 1.0F ? 0.0F : 2.2F * fm.EI.engines[1].getPowerOutput() + 0.005F * fm.EI.engines[1].tOilOut, 0.0F, 10F, 0.0F, 270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGasTemp1a", cvt(fm.EI.engines[0].tWaterOut, 300F, 1000F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGasTemp2a", cvt(fm.EI.engines[1].tWaterOut, 300F, 1000F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zHorizon1b", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.025F, -0.025F);
        mesh.chunkSetLocate("zHorizon1a", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("zSlide1a", cvt(getBall(6D), -6F, 6F, -15F, 15F), 0.0F, 0.0F);
        float f1;
        if(aircraft().isFMTrackMirror())
        {
            f1 = aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f1 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -6F, 6F, -26.5F, 26.5F);
            if(aircraft().fmTrack() != null)
                aircraft().fmTrack().setCockpitAzimuthSpeed(f1);
        }
        mesh.chunkSetAngles("zRoll1a", -f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("zVariometer1a", setNew.vspeed < 0.0F ? -floatindex(cvt(-setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale) : floatindex(cvt(setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAzimuth1a", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAzimuth1b", -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Course1a", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Course1b", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Air1", 135F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuelQ1", cvt(fm.M.fuel, 0.0F, 2400F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuelQ2", cvt(fm.M.fuel, 0.0F, 4000F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkVisible("Z_Red1", fm.getSpeedKMH() < 160F);
        mesh.chunkVisible("Z_Red2", fm.M.fuel < 600F);
        mesh.chunkVisible("Z_Red3", fm.M.fuel < 250F);
        mesh.chunkVisible("Z_Red4", fm.CT.getFlap() < 0.1F);
        mesh.chunkVisible("Z_Red5", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("Z_Red6", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("Z_Red7", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("Z_Red8", fm.CT.getFlap() < 0.1F);
        mesh.chunkVisible("Z_Green1", fm.CT.getFlap() > 0.665F);
        mesh.chunkVisible("Z_Green2", fm.CT.getGear() > 0.95F && fm.Gears.lgear);
        mesh.chunkVisible("Z_Green3", fm.CT.getGear() > 0.95F && fm.Gears.cgear);
        mesh.chunkVisible("Z_Green4", fm.CT.getGear() > 0.95F && fm.Gears.rgear);
        mesh.chunkVisible("Z_Green5", fm.CT.getFlap() > 0.665F);
        mesh.chunkVisible("Z_White1", fm.CT.getFlap() > 0.265F);
        mesh.chunkVisible("Z_White2", fm.CT.getFlap() > 0.265F);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    private void retoggleLight()
    {
        if(cockpitLightControl)
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
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("Z_Holes2_D1", true);
            mesh.chunkVisible("Z_Holes3_D1", true);
            mesh.chunkVisible("Z_Holes4_D1", true);
        }
        if((fm.AS.astateCockpitState & 1) == 0);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("pribors2_d1", true);
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("zAlt1a", false);
            mesh.chunkVisible("zAlt1b", false);
            mesh.chunkVisible("zHorizon1a", false);
            mesh.chunkVisible("zHorizon1b", false);
            mesh.chunkVisible("zSlide1a", false);
            mesh.chunkVisible("zRoll1a", false);
            mesh.chunkVisible("zRPM1", false);
            mesh.chunkVisible("zVariometer1a", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("Z_Holes5_D1", true);
            mesh.chunkVisible("pribors1_d1", true);
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("zGasPrs1b", false);
            mesh.chunkVisible("zGasPrs2b", false);
            mesh.chunkVisible("zSpeed1a", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("pribors4_d1", true);
            mesh.chunkVisible("pribors4", false);
            mesh.chunkVisible("zGasPrs2a", false);
            mesh.chunkVisible("zOilPrs1a", false);
            mesh.chunkVisible("zOilPrs2a", false);
            mesh.chunkVisible("zFuelPrs1a", false);
            mesh.chunkVisible("zFuelPrs2a", false);
            mesh.chunkVisible("zGasTemp1a", false);
            mesh.chunkVisible("zGasTemp2a", false);
            mesh.chunkVisible("zFuelQ1", false);
            mesh.chunkVisible("zFuelQ2", false);
        }
        if((fm.AS.astateCockpitState & 0x80) == 0);
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("Z_Holes6_D1", true);
            mesh.chunkVisible("pribors3_d1", true);
            mesh.chunkVisible("pribors3", false);
            mesh.chunkVisible("Z_Course1a", false);
            mesh.chunkVisible("Z_Course1b", false);
            mesh.chunkVisible("zRPM2", false);
            mesh.chunkVisible("zGasPrs1a", false);
        }
        if((fm.AS.astateCockpitState & 0x20) == 0);
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!isFocused())
        {
            return;
        } else
        {
            leave();
            super.doFocusLeave();
            return;
        }
    }

    private void enter()
    {
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 33.3");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(180F, 0.0F, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
        mesh.chunkVisible("Peribox", true);
        mesh.chunkVisible("zReticle2", true);
    }

    private void leave()
    {
        if(!bEntered)
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
            mesh.chunkVisible("Peribox", false);
            mesh.chunkVisible("zReticle2", false);
            return;
        }
    }

    public void doToggleAim(boolean flag)
    {
        if(!isFocused())
            return;
        if(isToggleAim() == flag)
            return;
        if(flag)
            enter();
        else
            leave();
    }

    public void destroy()
    {
        leave();
        super.destroy();
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private float pictAiler;
    private float pictElev;
    private float pictGear;
    private static final float speedometerScale[] = {
        0.0F, 18.5F, 67F, 117F, 164F, 215F, 267F, 320F, 379F, 427F, 
        428F
    };
    private static final float rpmScale[] = {
        0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55F, 77.5F, 104F, 133.5F, 162.5F, 
        192F, 224F, 254F, 255.5F, 260F
    };
    private static final float vsiNeedleScale[] = {
        0.0F, 48F, 82F, 96.5F, 111F, 120.5F, 130F, 130F
    };
    private float saveFov;
    private boolean bEntered;







}
