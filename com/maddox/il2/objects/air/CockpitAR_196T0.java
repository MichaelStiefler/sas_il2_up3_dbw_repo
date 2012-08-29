// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitAR_196T0.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
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
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitAR_196T0 extends com.maddox.il2.objects.air.CockpitPilot
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
            fm = com.maddox.il2.ai.World.getPlayerFM();
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
                setNew.azimuth = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (499F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 500F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            ((com.maddox.JGP.Tuple3d) (tmpV)).sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public CockpitAR_196T0()
    {
        super("3DO/Cockpit/AR-196T/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        setNew.dimPosition = 1.0F;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (((com.maddox.il2.objects.air.Cockpit)this).mesh)), "LAMPHOOK1");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        ((com.maddox.il2.engine.Actor)this).pos.base().draw.lightMap().put("LAMPHOOK1", ((java.lang.Object) (light1)));
        hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (((com.maddox.il2.objects.air.Cockpit)this).mesh)), "LAMPHOOK2");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(126F, 232F, 245F);
        light2.light.setEmit(0.0F, 0.0F);
        ((com.maddox.il2.engine.Actor)this).pos.base().draw.lightMap().put("LAMPHOOK2", ((java.lang.Object) (light2)));
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "87DClocks1", "87DClocks2", "87DClocks3", "87DClocks4", "87DClocks5", "87DPlanks2"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void reflectWorldToInstruments(float f)
    {
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor(), 0.0F, 1.0F, 0.0F, 0.6F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        if(((com.maddox.il2.objects.air.Cockpit)this).fm == null)
            return;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAlt1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAlt2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, -360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAltCtr2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 6000F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ReviTinter", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -30F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ReviTint", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 40F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zBoost1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zSpeed", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zRPM1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zFuel1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel / 0.72F, 0.0F, 250F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zCoolant1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut, 0.0F, 130F, 0.0F, 13F), temperatureScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zOilTemp1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 130F, 0.0F, 13F), temperatureScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zFuelPrs1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zOilPrs1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zTurnBank", ((com.maddox.il2.objects.air.Cockpit)this).cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -3F, 3F, 30F, -30F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zBall", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(6D), -6F, 6F, -10F, 10F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zCompass", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zRepeater", -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zCompassOil1", ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zCompassOil3", ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zCompassOil2", -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zVSI", ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -15F, 15F, -135F, 135F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zHour", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zMinute", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zColumn1", (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl) * 15F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl) * 10F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zPedalL", -((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder() * 10F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zPedalR", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder() * 10F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zThrottle1", ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f) * 80F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zPitch1", (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getStepControl() >= 0.0F ? ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getStepControl() : ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f)) * 45F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zFlaps1", 55F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zPipka1", 60F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AirBrakeControl, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zBrake1", 46.5F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AirBrakeControl, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlCompressor() > 0)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = 0.155F;
            com.maddox.il2.objects.air.Cockpit.ypr[2] = 22F;
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("zBoostCrank1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    public void toggleDim()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl;
    }

    public void toggleLight()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl;
        if(((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl)
        {
            light1.light.setEmit(0.005F, 0.5F);
            light2.light.setEmit(0.005F, 0.5F);
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        }
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Radio_D0", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Radio_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes1_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes2_D1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Radio_D0", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Radio_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes1_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes2_D1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Revi_D0", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_ReviTint", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_ReviTinter", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_MASK", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Revi_D1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("PoppedPanel_D0", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Repeater1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Azimuth1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Compass1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Speedometer1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_TurnBank1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_TurnBank2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("PoppedPanel_D1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) != 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_OilSplats_D1", true);
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, -12.33333F, 18.5F, 37F, 62.5F, 90F, 110.5F, 134F, 158.5F, 186F, 
        212.5F, 238.5F, 265F, 289.5F, 315F, 339.5F, 346F, 346F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 54F, 111F, 171.5F, 229.5F, 282.5F, 334F, 342.5F, 342.5F
    };
    private static final float fuelScale[] = {
        0.0F, 11.5F, 24.5F, 46.5F, 67F, 88F
    };
    private static final float temperatureScale[] = {
        0.0F, 15.5F, 35F, 50F, 65F, 79F, 92F, 117.5F, 141.5F, 178.5F, 
        222.5F, 261.5F, 329F, 340F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
