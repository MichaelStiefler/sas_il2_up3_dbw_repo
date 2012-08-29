// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitI_250.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitI_250 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle1 = 0.85F * setOld.throttle1 + fm.EI.engines[0].getControlThrottle() * 0.15F;
                setNew.throttle2 = 0.85F * setOld.throttle2 + fm.EI.engines[1].getControlThrottle() * 0.15F;
                setNew.prop = 0.85F * setOld.prop + fm.CT.getStepControl() * 0.15F;
                setNew.mix = 0.85F * setOld.mix + fm.EI.engines[0].getControlMix() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                setNew.manifold1 = 0.8F * setOld.manifold1 + 0.2F * fm.EI.engines[0].getManifoldPressure();
                setNew.manifold2 = 0.8F * setOld.manifold2 + 0.2F * fm.EI.engines[1].getManifoldPressure();
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float throttle1;
        float throttle2;
        float prop;
        float mix;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        float manifold1;
        float manifold2;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    public CockpitI_250()
    {
        super("3DO/Cockpit/I-250/hier.him", "bf109");
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictGear = 0.0F;
        pictVRDK = 0.0F;
        pictTLck = 0.0F;
        pictMetl = 0.0F;
        pictTriE = 0.0F;
        pictTriR = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "gauges_01", "gauges_02", "gauges_03", "gauges_04", "gauges_05", "gauges_06", "DMG_gauges_01", "DMG_gauges_02", "DMG_gauges_03", "DMG_gauges_04", 
            "DMG_gauges_05", "DMG_gauges_06"
        });
        setNightMats(false);
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "LIGHTHOOK_L");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LIGHTHOOK_L", light1);
        hooknamed = new HookNamed(mesh, "LIGHTHOOK_R");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(126F, 232F, 245F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LIGHTHOOK_R", light2);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.6F);
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("PedalCrossBar", 0.0F, -12F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal_L", 0.0F, 12F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal_R", 0.0F, -12F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("IgnitionSwitch", 0.0F, cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, -129F), 0.0F);
        mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 35F * (pictGear = 0.8F * pictGear + 0.2F * fm.CT.GearControl));
        if(java.lang.Math.abs(fm.CT.FlapsControl - fm.CT.getFlap()) > 0.02F)
        {
            if(fm.CT.FlapsControl - fm.CT.getFlap() > 0.0F)
                mesh.chunkSetAngles("FlapHandle", 0.0F, -20F, 0.0F);
            else
                mesh.chunkSetAngles("FlapHandle", 0.0F, 20F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("TQHandle1", 0.0F, 49.9F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F);
        mesh.chunkSetAngles("PropPitchLvr", 0.0F, 49.9F * interp(setNew.prop, setOld.prop, f), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(interp(setNew.mix, setOld.mix, f), 0.0F, 1.0F, 0.1035F, 0.0F);
        mesh.chunkSetLocate("MixLvr", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Eng2Switch", 0.0F, 65F * (pictVRDK = 0.85F * pictVRDK + 0.15F * (fm.EI.engines[1].getStage() != 6 ? 0.0F : 1.0F)), 0.0F);
        mesh.chunkSetAngles("TQHandle2", 0.0F, 100F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F);
        mesh.chunkSetAngles("TWLock", 0.0F, -31F * (pictTLck = 0.85F * pictTLck + 0.15F * (fm.Gears.bTailwheelLocked ? 1.0F : 0.0F)), 0.0F);
        mesh.chunkSetAngles("ElevTrim", 0.0F, 0.0F, 42.2F * (pictTriE = 0.92F * pictTriE + 0.08F * fm.CT.getTrimElevatorControl()));
        mesh.chunkSetAngles("RuddrTrim", 0.0F, 79.9F * (pictTriR = 0.92F * pictTriR + 0.08F * fm.CT.getTrimRudderControl()), 0.0F);
        mesh.chunkSetAngles("FLCSA", 0.0F, -12F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F);
        mesh.chunkSetAngles("FLCSB", 0.0F, 12F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F);
        pictMetl = 0.96F * pictMetl + 0.04F * (0.6F * fm.EI.engines[1].getThrustOutput() * fm.EI.engines[1].getControlThrottle() * (fm.EI.engines[1].getStage() != 6 ? 0.02F : 1.0F));
        mesh.chunkSetAngles("NeedExhstPress2", 0.0F, cvt(pictMetl, 0.0F, 1.0F, 0.0F, 270F), 0.0F);
        mesh.chunkSetAngles("NeedFuelPress2", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.55F, 0.0F, 1.0F, 0.0F, 232F), 0.0F);
        mesh.chunkSetAngles("NeedExstT2", 0.0F, cvt(fm.EI.engines[1].tWaterOut, 300F, 900F, 0.0F, 239F), 0.0F);
        mesh.chunkSetAngles("NeedMfdP2", 0.0F, cvt(setNew.manifold2, 0.399966F, 2.133152F, 0.0F, 338F), 0.0F);
        mesh.chunkSetAngles("NeedOilT2", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 82F), 0.0F);
        mesh.chunkSetAngles("NeedWatT", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 82F), 0.0F);
        mesh.chunkSetAngles("NeedAlt_Km", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 60000F, 0.0F, 2160F), 0.0F);
        mesh.chunkSetAngles("NeedAlt_M", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 60000F, 0.0F, 21600F), 0.0F);
        mesh.chunkSetAngles("NeedCompass", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedHour", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("NeedMin", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("NeedSpeed", 0.0F, cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1200F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("NeedClimb", 0.0F, cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        mesh.chunkSetAngles("NeedRPMA", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("NeedRPMB", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("NeedMfdP1", 0.0F, cvt(setNew.manifold1, 0.399966F, 2.133152F, 0.0F, 335F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0204F, -0.0204F);
        mesh.chunkSetLocate("NeedAHCyl", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("NeedAHBar", 0.0F, fm.Or.getKren(), 0.0F);
        mesh.chunkSetAngles("NeedTurn", 0.0F, cvt(getBall(8D), -8F, 8F, -29F, 29F), 0.0F);
        mesh.chunkSetAngles("NeedOilT1", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 125F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("NeedFuelPress1", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.32F, 0.0F, 1.0F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("NeedOilPress1", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("NeedFuel", 0.0F, cvt(fm.M.fuel, 0.0F, 432F, 0.0F, 180F), 0.0F);
        mesh.chunkVisible("FlareGearUp_R", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
        mesh.chunkVisible("FlareGearUp_L", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
        mesh.chunkVisible("FlareGearDn_R", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        mesh.chunkVisible("FlareGearDn_L", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
        mesh.chunkVisible("FlareFuel", fm.M.fuel < 81F);
    }

    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
            return 0.0F;
        waypoint.getP(tmpP);
        tmpV.sub(tmpP, fm.Loc);
        float f;
        for(f = (float)(57.295779513082323D * java.lang.Math.atan2(-tmpV.y, tmpV.x)); f <= -180F; f += 180F);
        for(; f > 180F; f -= 180F);
        return f;
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Gages6_D0", false);
            mesh.chunkVisible("Gages6_D1", true);
            mesh.chunkVisible("NeedAHCyl", false);
            mesh.chunkVisible("NeedAHBar", false);
            mesh.chunkVisible("NeedMfdP1", false);
            mesh.chunkVisible("NeedOilT1", false);
            mesh.chunkVisible("NeedFuelPress1", false);
            mesh.chunkVisible("NeedOilPress1", false);
            mesh.chunkVisible("Z_Holes6_D1", true);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("GunSight", false);
            mesh.chunkVisible("GSGlassMain", false);
            mesh.chunkVisible("DGunSight", true);
        }
        if((fm.AS.astateCockpitState & 1) == 0);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("OilSplats", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Gages4_D0", false);
            mesh.chunkVisible("Gages4_D1", true);
            mesh.chunkVisible("NeedAlt_Km", false);
            mesh.chunkVisible("NeedAlt_M", false);
            mesh.chunkVisible("NeedCompass", false);
            mesh.chunkVisible("NeedHour", false);
            mesh.chunkVisible("NeedMin", false);
            mesh.chunkVisible("DamageHull1", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("Gages1_D0", false);
            mesh.chunkVisible("Gages1_D1", true);
            mesh.chunkVisible("NeedExstT2", false);
            mesh.chunkVisible("NeedExhstPress2", false);
            mesh.chunkVisible("NeedFuelPress2", false);
            mesh.chunkVisible("DamageHull1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("Gages2_D0", false);
            mesh.chunkVisible("Gages2_D1", true);
            mesh.chunkVisible("NeedOilT2", false);
            mesh.chunkVisible("NeedWatT", false);
            mesh.chunkVisible("NeedMfdP2", false);
            mesh.chunkVisible("DamageHull2", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("Gages5_D0", false);
            mesh.chunkVisible("Gages5_D1", true);
            mesh.chunkVisible("NeedSpeed", false);
            mesh.chunkVisible("NeedClimb", false);
            mesh.chunkVisible("NeedRPMA", false);
            mesh.chunkVisible("NeedRPMB", false);
            mesh.chunkVisible("DamageHull2", true);
            mesh.chunkVisible("DamageHull3", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("Gages7_D0", false);
            mesh.chunkVisible("Gages7_D1", true);
            mesh.chunkVisible("NeedFuel", false);
            mesh.chunkVisible("NeedAmmeter", false);
            mesh.chunkVisible("NeedVmeter", false);
            mesh.chunkVisible("DamageHull1", true);
        }
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            setNightMats(true);
            light1.light.setEmit(0.005F, 0.5F);
            light2.light.setEmit(0.005F, 0.5F);
        } else
        {
            setNightMats(false);
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
        }
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

    public void doToggleDim()
    {
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private float pictAiler;
    private float pictElev;
    private float pictGear;
    private float pictVRDK;
    private float pictTLck;
    private float pictMetl;
    private float pictTriE;
    private float pictTriR;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;






}
