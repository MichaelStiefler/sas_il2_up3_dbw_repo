// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitDXXI_SARJA4.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitDXXI_SARJA4 extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        float throttle;
        float mix;
        float prop;
        float turn;
        float vspeed;
        float stbyPosition;
        float dimPos;

        private Variables()
        {
            azimuth = new AnglesFork();
        }

        Variables(com.maddox.il2.objects.air._cls1 _pcls1)
        {
            this();
        }
    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            if((fm.AS.astateCockpitState & 2) != 0 && setNew.stbyPosition < 1.0F)
            {
                setNew.stbyPosition = setOld.stbyPosition + 0.0125F;
                setOld.stbyPosition = setNew.stbyPosition;
            }
            setNew.altimeter = fm.getAltitude();
            if(java.lang.Math.abs(fm.Or.getKren()) < 30F && java.lang.Math.abs(fm.Or.tangage()) < 30F)
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
            setNew.throttle = (10F * setOld.throttle + fm.EI.engines[0].getControlThrottle()) / 11F;
            setNew.mix = (10F * setOld.mix + fm.EI.engines[0].getControlMix()) / 11F;
            setNew.prop = setOld.prop;
            if(setNew.prop < fm.EI.engines[0].getControlProp() - 0.01F)
                setNew.prop += 0.0025F;
            if(setNew.prop > fm.EI.engines[0].getControlProp() + 0.01F)
                setNew.prop -= 0.0025F;
            w.set(fm.getW());
            fm.Or.transform(w);
            setNew.turn = (12F * setOld.turn + w.z) / 13F;
            setNew.vspeed = (299F * setOld.vspeed + fm.getVertSpeed()) / 300F;
            pictSupc = 0.8F * pictSupc + 0.2F * (float)fm.EI.engines[0].getControlCompressor();
            if(cockpitDimControl)
            {
                if(setNew.dimPos < 1.0F)
                    setNew.dimPos = setOld.dimPos + 0.03F;
            } else
            if(setNew.dimPos > 0.0F)
                setNew.dimPos = setOld.dimPos - 0.03F;
            if((double)flaps > (double)fm.CT.FlapsControl - 0.050000000000000003D && (double)flaps < (double)fm.CT.FlapsControl + 0.050000000000000003D || fm.CT.bHasFlapsControlRed)
                flapsDirection = 0;
            else
            if(flaps < fm.CT.FlapsControl)
            {
                flaps = flaps + 0.00095F;
                flapsPump = flapsPump + flapsPumpIncrement;
                flapsDirection = 1;
                if(flapsPump < 0.0F || flapsPump > 1.0F)
                    flapsPumpIncrement = -flapsPumpIncrement;
            } else
            if(flaps > fm.CT.FlapsControl)
            {
                flaps = flaps - 0.005F;
                flapsPump = flapsPump + flapsPumpIncrement;
                flapsDirection = -1;
                if(flapsPump < 0.0F || flapsPump > 1.0F)
                    flapsPumpIncrement = -flapsPumpIncrement;
            }
            if(!fm.Gears.bTailwheelLocked && tailWheelLock < 1.0F)
                tailWheelLock = tailWheelLock + 0.05F;
            else
            if(fm.Gears.bTailwheelLocked && tailWheelLock > 0.0F)
                tailWheelLock = tailWheelLock - 0.05F;
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitDXXI_SARJA4()
    {
        super("3DO/Cockpit/DXXI_SARJA3_EARLY/hier.him", "bf109");
        setOld = new Variables(null);
        setNew = new Variables(null);
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictSupc = 0.0F;
        flaps = 0.0F;
        bEntered = false;
        hasRevi = true;
        tailWheelLock = 1.0F;
        flapsDirection = 0;
        flapsPump = 0.0F;
        flapsPumpIncrement = 0.1F;
        setRevi();
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "LAMPHOOK01");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK01", light1);
        hooknamed = new HookNamed(mesh, "LAMPHOOK02");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(126F, 232F, 245F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK02", light2);
        hooknamed = new HookNamed(mesh, "LAMPHOOK03");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light3 = new LightPointActor(new LightPoint(), loc.getPoint());
        light3.light.setColor(126F, 232F, 245F);
        light3.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK03", light3);
        hooknamed = new HookNamed(mesh, "LAMPHOOK04");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light4 = new LightPointActor(new LightPoint(), loc.getPoint());
        light4.light.setColor(126F, 232F, 245F);
        light4.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK04", light4);
        hooknamed = new HookNamed(mesh, "LAMPHOOK05");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light5 = new LightPointActor(new LightPoint(), loc.getPoint());
        light5.light.setColor(126F, 232F, 245F);
        light5.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK05", light5);
        cockpitNightMats = (new java.lang.String[] {
            "gauge_speed", "gauge_alt", "gauge_fuel", "gauges_various_1", "gauges_various_2", "LABELS1", "gauges_various_3", "gauges_various4", "gauges_various_3_dam", "gauge_alt_dam", 
            "gauges_various_2_dam"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void setRevi()
    {
        hasRevi = true;
        mesh.chunkVisible("reticle", true);
        mesh.chunkVisible("reticlemask", true);
        mesh.chunkVisible("Revi_D0", true);
        mesh.chunkVisible("Z_sight_cap", false);
        mesh.chunkVisible("tubeSight", false);
        mesh.chunkVisible("tubeSightLens", false);
        mesh.chunkVisible("tube_inside", false);
        mesh.chunkVisible("tube_mask", false);
        mesh.chunkVisible("Z_sight_cap_inside", false);
        mesh.chunkVisible("GlassTube", false);
        mesh.chunkVisible("GlassRevi", true);
        mesh.chunkVisible("Z_reviIron", true);
        mesh.chunkVisible("Z_reviDimmer", true);
        mesh.chunkVisible("Z_reviDimmerLever", true);
    }

    public void reflectWorldToInstruments(float f)
    {
        float f1 = 0.0F;
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("Z_reviIron", 90F * setNew.stbyPosition, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_manifold", cvt(pictManifold = 0.85F * pictManifold + 0.15F * fm.EI.engines[0].getManifoldPressure() * 76F, 30F, 120F, 22F, 296F), 0.0F, 0.0F);
        f1 = -15F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl);
        mesh.chunkSetAngles("Z_stick_horiz_axis", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_aileron_rods", -f1 / 14F, 0.0F, 0.0F);
        f1 = 14F * (pictElev = 0.85F * pictElev + 0.2F * fm.CT.ElevatorControl);
        mesh.chunkSetAngles("Z_Stick", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_elev_wire1", -f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_elev_wire2", -f1, 0.0F, 0.0F);
        f1 = fm.CT.getRudder();
        mesh.chunkSetAngles("Z_wheel_break_valve", -12F * f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_pedal_L", 24F * f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_pedal_R", -24F * f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_rudder_rod_L", -25F * f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_rudder_rod_R", 25F * f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle", -70F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixture", -70F * interp(setNew.mix, setOld.mix, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_alt1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_alt2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 720F), 0.0F, 0.0F);
        float f3 = com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH());
        if(f3 > 360F)
            mesh.chunkSetAngles("Z_Need_speed", cvt(f3, 360F, 600F, -329F, -550F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Need_speed", cvt(f3, 60F, 360F, 0.0F, -329F), 0.0F, 0.0F);
        f1 = setNew.azimuth.getDeg(f);
        mesh.chunkSetAngles("Z_Need_compass", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_rpm", cvt(fm.EI.engines[0].getRPM(), 440F, 3320F, 0.0F, -332F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_clock_hour", -cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, -720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_clock_minute", -cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_clock_sec", -cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, -360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_fuel", -cvt(fm.M.fuel, 0.0F, 300F, 0.0F, 52F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_oiltemp", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 329F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_oilpressure", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7F, 0.0F, -315F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_cylheadtemp", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, 110F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_fuelpressure", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 4F, 0.0F, 100F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_magneto", -30F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -cvt(fm.Or.getTangage(), -20F, 20F, 0.04F, -0.04F);
        mesh.chunkSetLocate("Z_Need_red_liquid", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Need_Turn", cvt(setNew.turn, -0.2F, 0.2F, -22.5F, 22.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_bank", -cvt(getBall(8D), -8F, 8F, 16.9F, -16.9F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_variometer", -cvt(setNew.vspeed, -20F, 20F, 180F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_oxygeneflow", -260F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_oxygenetank", -320F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_flaps_indicator", 0.7F * flaps, 0.0F, 0.0F);
        if(flapsDirection == 1)
        {
            mesh.chunkSetAngles("Z_flaps_valve", -33F, 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_flapsLeverKnob", -33F, 0.0F, 0.0F);
        } else
        if(flapsDirection == -1)
        {
            mesh.chunkSetAngles("Z_flaps_valve", 33F, 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_flapsLeverKnob", 33F, 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("Z_flaps_valve", 0.0F, 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_flapsLeverKnob", 0.0F, 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_trim_indicator", 1.9F * -fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_trim_wheel", 600F * fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        if(fm.CT.bHasBrakeControl)
        {
            float f2 = fm.CT.getBrake();
            mesh.chunkSetAngles("Z_break_handle", f2 * 20F, 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Need_breakpressureR", cvt(f2 + f2 * fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Need_breakpressureL", -cvt(f2 - f2 * fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Need_breakpressure1", -150F + f2 * 20F, 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_flaps_pump", -flapsPump * 40F, 0.0F, 0.0F);
        if(fm.AS.bLandingLightOn)
            mesh.chunkSetAngles("Z_switch_landing_light", -35F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_switch_landing_light", 0.0F, 0.0F, 0.0F);
        if(fm.AS.bNavLightsOn)
            mesh.chunkSetAngles("Z_switch_navigation_light", -35F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_switch_navigation_light", 0.0F, 0.0F, 0.0F);
        if(cockpitLightControl)
            mesh.chunkSetAngles("Z_switch_cockpit_light", -35F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_switch_cockpit_light", 0.0F, 0.0F, 0.0F);
        if(tailWheelLock >= 1.0F)
        {
            mesh.chunkSetAngles("Z_tailwheel", tailWheelLock * 57F, 0.0F, 7F);
            mesh.chunkSetAngles("Z_tailwheel_lever_wire", tailWheelLock * 57F, 0.0F, 7F);
        } else
        {
            mesh.chunkSetAngles("Z_tailwheel", tailWheelLock * 57F, 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_tailwheel_lever_wire", tailWheelLock * 57F, 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_wheelLockKnob", tailWheelLock * 57F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Need_extinguisher", (float)fm.EI.engines[0].getExtinguishers() * 95F, 0.0F, 0.0F);
        if(hasRevi)
        {
            mesh.chunkSetAngles("Z_reviDimmer", -cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, -90F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_reviDimmerLever", -cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, 0.004F), 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("Z_sight_cap", cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, -130F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_sight_cap_big", 0.0F, cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, -130F), 0.0F);
            mesh.chunkSetAngles("Z_sight_cap_inside", cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, -130F), 0.0F, 0.0F);
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            if(hasRevi)
            {
                mesh.chunkVisible("reticle", false);
                mesh.chunkVisible("reticlemask", false);
                mesh.chunkVisible("Revi_D0", false);
                mesh.chunkVisible("Revi_D1", true);
            }
            mesh.chunkVisible("GlassDamageFront2", true);
            mesh.chunkVisible("HullDamageRear", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("GlassDamageFront", true);
            mesh.chunkVisible("HullDamageRear", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Gauges_d0", false);
            mesh.chunkVisible("Gauges_d1", true);
            mesh.chunkVisible("HullDamageFront", true);
            mesh.chunkVisible("Z_Need_manifold", false);
            mesh.chunkVisible("Z_Need_oilpressure", false);
            mesh.chunkVisible("Z_Need_rpm", false);
            mesh.chunkVisible("Z_Need_alt1", false);
            mesh.chunkVisible("Z_Need_alt2", false);
            mesh.chunkVisible("Z_Need_variometer", false);
            mesh.chunkVisible("Z_Need_clock_sec", false);
            mesh.chunkVisible("Z_Need_clock_minute", false);
            mesh.chunkVisible("Z_Need_clock_hour", false);
            mesh.chunkVisible("Z_Need_clock_timer", false);
            mesh.chunkVisible("Z_Need_cylheadtemp", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("GlassDamageLeft", true);
            mesh.chunkVisible("HullDamageLeft", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("GlassDamageLeft", true);
            mesh.chunkVisible("HullDamageLeft", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("GlassDamageRight", true);
            mesh.chunkVisible("HullDamageRight", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("GlassDamageRight", true);
            mesh.chunkVisible("HullDamageRight", true);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
            if(hasRevi)
                mesh.chunkVisible("OilRevi", true);
            else
                mesh.chunkVisible("Oil", true);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("CF_D0", hiermesh.isChunkVisible("CF_D0"));
        mesh.chunkVisible("CF_D1", hiermesh.isChunkVisible("CF_D1"));
        mesh.chunkVisible("CF_D2", hiermesh.isChunkVisible("CF_D2"));
    }

    public void doToggleUp(boolean flag)
    {
        super.doToggleUp(flag);
        java.lang.System.out.println("TOGGLE UP");
    }

    private void enter()
    {
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 31");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
        mesh.chunkVisible("superretic", true);
        mesh.chunkVisible("Z_BoxTinter", true);
    }

    private void leave()
    {
        if(bEntered)
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
            mesh.chunkVisible("superretic", false);
            mesh.chunkVisible("Z_BoxTinter", false);
        }
    }

    public void destroy()
    {
        leave();
        super.destroy();
    }

    public void doToggleAim(boolean flag)
    {
        if(isFocused() && isToggleAim() != flag)
            if(flag)
                enter();
            else
                leave();
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        mesh.materialReplace("Gloss1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D1o"));
        mesh.materialReplace("Gloss2D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D0o"));
        mesh.materialReplace("Matt2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D2o"));
        mesh.materialReplace("Matt2D2o", mat);
    }

    protected boolean doFocusEnter()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        hiermesh.chunkVisible("tail1_internal_d0", false);
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
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        hiermesh.chunkVisible("tail1_internal_d0", true);
        if(isFocused())
        {
            leave();
            super.doFocusLeave();
        }
    }

    public boolean isToggleUp()
    {
        java.lang.System.out.println("isToggleUp");
        return super.isToggleUp();
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.005F, 0.2F);
            light2.light.setEmit(0.005F, 0.2F);
            light3.light.setEmit(0.005F, 0.2F);
            light4.light.setEmit(0.002F, 0.1F);
            light5.light.setEmit(0.005F, 0.2F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            light3.light.setEmit(0.0F, 0.0F);
            light4.light.setEmit(0.0F, 0.0F);
            light5.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private float pictSupc;
    private float flaps;
    private float pictManifold;
    private boolean bEntered;
    private float saveFov;
    private boolean hasRevi;
    private float tailWheelLock;
    private int flapsDirection;
    private float flapsPump;
    private float flapsPumpIncrement;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private com.maddox.il2.engine.LightPointActor light3;
    private com.maddox.il2.engine.LightPointActor light4;
    private com.maddox.il2.engine.LightPointActor light5;




















}
