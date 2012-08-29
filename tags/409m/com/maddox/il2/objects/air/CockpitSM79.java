// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitSM79.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, SM79, Aircraft, Cockpit

public class CockpitSM79 extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float dimPos;
        float throttleC;
        float throttleR;
        float throttleL;
        float propC;
        float propL;
        float propR;
        float MixC;
        float MixL;
        float MixR;
        float altimeter;
        float elevTrim;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        com.maddox.il2.ai.AnglesFork waypointDeviation;

        private Variables()
        {
            dimPos = 0.0F;
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
            waypointDeviation = new AnglesFork();
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
                if(cockpitDimControl)
                {
                    if(setNew.dimPos < 1.0F)
                        setNew.dimPos = setOld.dimPos + 0.03F;
                } else
                if(setNew.dimPos > 0.0F)
                    setNew.dimPos = setOld.dimPos - 0.03F;
                setNew.throttleC = 0.85F * setOld.throttleC + fm.EI.engines[1].getControlThrottle() * 0.15F;
                setNew.throttleL = 0.85F * setOld.throttleL + fm.EI.engines[0].getControlThrottle() * 0.15F;
                setNew.throttleR = 0.85F * setOld.throttleR + fm.EI.engines[2].getControlThrottle() * 0.15F;
                setNew.propC = 0.85F * setOld.propC + fm.EI.engines[1].getControlProp() * 0.15F;
                setNew.propL = 0.85F * setOld.propL + fm.EI.engines[0].getControlProp() * 0.15F;
                setNew.propR = 0.85F * setOld.propR + fm.EI.engines[2].getControlProp() * 0.15F;
                setNew.MixC = 0.85F * setOld.MixC + fm.EI.engines[1].getControlMix() * 0.15F;
                setNew.MixL = 0.85F * setOld.MixL + fm.EI.engines[0].getControlMix() * 0.15F;
                setNew.MixR = 0.85F * setOld.MixR + fm.EI.engines[2].getControlMix() * 0.15F;
                setNew.elevTrim = 0.85F * setOld.elevTrim + fm.CT.getTrimElevatorControl() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), 90F + fm.Or.azimut());
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(1.0F), f);
                setNew.waypointDeviation.setDeg(setOld.waypointDeviation.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.objects.air.SM79)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot3_D1", false);
            aircraft().hierMesh().chunkVisible("fakePilot3_D0", true);
            if(!((com.maddox.il2.objects.air.SM79)fm.actor).bPilot3Killed)
                mesh.chunkVisible("TailGunner", true);
            if(firstEnter)
            {
                if(aircraft().thisWeaponsName.startsWith("1x"))
                {
                    mesh.chunkVisible("Torpsight_Base", true);
                    mesh.chunkVisible("Torpsight_Knob", true);
                    mesh.chunkVisible("Torpsight_Rot", true);
                }
                firstEnter = false;
            }
            mesh.chunkVisible("Tur2_DoorR_Int", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
            mesh.chunkVisible("Tur2_DoorR_open_Int", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
            mesh.chunkVisible("Tur2_DoorL_Int", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
            mesh.chunkVisible("Tur2_DoorL_open_Int", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
            mesh.chunkVisible("Tur2_Door1_Int", true);
            mesh.chunkVisible("Tur2_Door2_Int", true);
            mesh.chunkVisible("Tur2_Door3_Int", true);
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", false);
            aircraft().hierMesh().chunkVisible("WingWireL_D0", false);
            aircraft().hierMesh().chunkVisible("Interior1_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(isFocused())
        {
            ((com.maddox.il2.objects.air.SM79)fm.actor).bPitUnfocused = true;
            boolean flag = aircraft().isChunkAnyDamageVisible("Tail1_D");
            mesh.chunkVisible("TailGunner", false);
            if(!fm.AS.isPilotParatrooper(2))
            {
                aircraft().hierMesh().chunkVisible("Pilot3_D0", !((com.maddox.il2.objects.air.SM79)fm.actor).bPilot3Killed);
                aircraft().hierMesh().chunkVisible("Pilot3_D1", ((com.maddox.il2.objects.air.SM79)fm.actor).bPilot3Killed);
            }
            if(flag)
            {
                aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", !aircraft().hierMesh().isChunkVisible("Tur1_DoorR_open_D0"));
                aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", !aircraft().hierMesh().isChunkVisible("Tur1_DoorR_open_D0"));
            }
            aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
            aircraft().hierMesh().chunkVisible("Turret2B_D0", flag);
            aircraft().hierMesh().chunkVisible("Turret3B_D0", flag);
            aircraft().hierMesh().chunkVisible("Turret4B_D0", flag);
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !mesh.isChunkVisible("Tur2_DoorR_open_Int"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", mesh.isChunkVisible("Tur2_DoorR_open_Int"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !mesh.isChunkVisible("Tur2_DoorR_open_Int"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", mesh.isChunkVisible("Tur2_DoorR_open_Int"));
            aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
            aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
            aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);
            aircraft().hierMesh().chunkVisible("WingWireL_D0", true);
            mesh.chunkVisible("Tur2_Door1_Int", false);
            mesh.chunkVisible("Tur2_Door2_Int", false);
            mesh.chunkVisible("Tur2_Door3_Int", false);
            mesh.chunkVisible("Tur2_DoorL_open_Int", false);
            mesh.chunkVisible("Tur2_DoorR_open_Int", false);
            aircraft().hierMesh().chunkVisible("Interior1_D0", true);
            super.doFocusLeave();
        }
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    protected void mydebugcockpit(java.lang.String s)
    {
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

    public CockpitSM79()
    {
        super("3DO/Cockpit/SM79Pilot/hier.him", "bf109");
        firstEnter = true;
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictFlap = 0.0F;
        pictGear = 0.0F;
        pictManf1 = 1.0F;
        pictManf2 = 1.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "LAMPHOOK1");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(109F, 99F, 90F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(mesh, "LAMPHOOK2");
        com.maddox.il2.engine.Loc loc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed1.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc1);
        light2 = new LightPointActor(new LightPoint(), loc1.getPoint());
        light2.light.setColor(109F, 99F, 90F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK2", light2);
        cockpitNightMats = (new java.lang.String[] {
            "SM79_gauges_1", "SM79_gauges_2", "SM79_gauges_3", "SM79_gauges_1_dmg", "Ita_Needles"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        loadBuzzerFX();
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        if(aircraft().thisWeaponsName.startsWith("1x"))
        {
            mesh.chunkSetAngles("Torpsight_Rot", 0.0F, ((com.maddox.il2.objects.air.SM79)aircraft()).fSightCurSideslip, 0.0F);
            mesh.chunkSetAngles("Torpsight_Knob", 0.0F, 2.0F * ((com.maddox.il2.objects.air.SM79)aircraft()).fSightCurSideslip, 0.0F);
        }
        aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
        float f1 = fm.CT.getCockpitDoor();
        if(f1 < 0.99F)
        {
            if(!mesh.isChunkVisible("Tur2_DoorR_Int"))
            {
                mesh.chunkVisible("Tur2_DoorR_Int", true);
                mesh.chunkVisible("Tur2_DoorR_open_Int", false);
                mesh.chunkVisible("Tur2_DoorL_Int", true);
                mesh.chunkVisible("Tur2_DoorL_open_Int", false);
            }
            float f2 = 13.8F * f1;
            mesh.chunkSetAngles("Tur2_Door1_Int", 0.0F, -f2, 0.0F);
            f2 = 8.8F * f1;
            mesh.chunkSetAngles("Tur2_Door2_Int", 0.0F, -f2, 0.0F);
            f2 = 3.1F * f1;
            mesh.chunkSetAngles("Tur2_Door3_Int", 0.0F, -f2, 0.0F);
            f2 = 14F * f1;
            mesh.chunkSetAngles("Tur2_DoorL_Int", 0.0F, -f2, 0.0F);
            mesh.chunkSetAngles("Tur2_DoorR_Int", 0.0F, f2, 0.0F);
        } else
        {
            mesh.chunkVisible("Tur2_DoorR_Int", false);
            mesh.chunkVisible("Tur2_DoorR_open_Int", true);
            mesh.chunkVisible("Tur2_DoorL_Int", false);
            mesh.chunkVisible("Tur2_DoorL_open_Int", true);
            mesh.chunkSetAngles("Tur2_Door1_Int", 0.0F, -13.8F, 0.0F);
            mesh.chunkSetAngles("Tur2_Door2_Int", 0.0F, -8.8F, 0.0F);
            mesh.chunkSetAngles("Tur2_Door3_Int", 0.0F, -3.1F, 0.0F);
        }
        mesh.chunkSetAngles("Mirino_02", 0.0F, cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, 80F), 0.0F);
        mesh.chunkSetAngles("ZSpeedL", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 460F, 0.0F, 23F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("ZSpeedR", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 460F, 0.0F, 23F), speedometerScale), 0.0F);
        mesh.chunkVisible("ZlampRedL", fm.CT.getPowerControl() < 0.15F && fm.CT.getGear() < 0.99F);
        mesh.chunkVisible("ZlampRedR", fm.CT.getPowerControl() < 0.15F && fm.CT.getGear() < 0.99F);
        buzzerFX(fm.CT.getPowerControl() < 0.15F && fm.CT.getGear() < 0.99F);
        mesh.chunkVisible("ZlampGreenL", fm.CT.getPowerControl() < 0.15F && fm.CT.getGear() > 0.99F);
        mesh.chunkVisible("ZlampGreenR", fm.CT.getPowerControl() < 0.15F && fm.CT.getGear() > 0.99F);
        mesh.chunkSetAngles("Mplane_LandingGear_L", 0.0F, 0.0F, 10F * fm.CT.getGear());
        mesh.chunkSetAngles("Mplane_LandingGear_R", 0.0F, 0.0F, 10F * fm.CT.getGear());
        mesh.chunkSetAngles("Mplane_LandingGear_L1", 0.0F, 90F * fm.CT.getGear(), 0.0F);
        mesh.chunkSetAngles("Mplane_LandingGear_R1", 0.0F, -90F * fm.CT.getGear(), 0.0F);
        mesh.chunkSetAngles("Zgear", 30F * (pictGear = 0.89F * pictGear + 0.11F * fm.CT.GearControl), 0.0F, 0.0F);
        float f3 = interp(setNew.altimeter, setOld.altimeter, f) * 0.036F;
        mesh.chunkSetAngles("Zalt1L", 0.0F, f3 * 10F, 0.0F);
        mesh.chunkSetAngles("Zalt1R", 0.0F, f3 * 10F, 0.0F);
        mesh.chunkSetAngles("Zalt2L", 0.0F, f3, 0.0F);
        mesh.chunkSetAngles("Zalt2R", 0.0F, f3, 0.0F);
        mesh.chunkSetAngles("ZclockHour", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("ZclockMin", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        float f4 = 0.104F;
        mesh.chunkSetAngles("ZRPML", 0.0F, fm.EI.engines[0].getRPM() * f4, 0.0F);
        mesh.chunkSetAngles("ZRPMC", 0.0F, fm.EI.engines[1].getRPM() * f4, 0.0F);
        mesh.chunkSetAngles("ZRPMR", 0.0F, fm.EI.engines[2].getRPM() * f4, 0.0F);
        mesh.chunkSetAngles("ZbarometrL", 0.0F, cvt(fm.EI.engines[0].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312F), 0.0F);
        mesh.chunkSetAngles("ZbarometrC", 0.0F, cvt(fm.EI.engines[1].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312F), 0.0F);
        mesh.chunkSetAngles("ZbarometrR", 0.0F, cvt(fm.EI.engines[2].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312F), 0.0F);
        mesh.chunkSetAngles("ZbarometrEL", 0.0F, floatindex(cvt(fm.EI.engines[0].tOilIn, 40F, 160F, 0.0F, 6F), oilTempScale), 0.0F);
        mesh.chunkSetAngles("ZbarometrUL", 0.0F, floatindex(cvt(fm.EI.engines[0].tOilOut, 40F, 160F, 0.0F, 6F), oilTempScale), 0.0F);
        mesh.chunkSetAngles("ZbarometrEC", 0.0F, floatindex(cvt(fm.EI.engines[1].tOilIn, 40F, 160F, 0.0F, 6F), oilTempScale), 0.0F);
        mesh.chunkSetAngles("ZbarometrUC", 0.0F, floatindex(cvt(fm.EI.engines[1].tOilOut, 40F, 160F, 0.0F, 6F), oilTempScale), 0.0F);
        mesh.chunkSetAngles("ZbarometrER", 0.0F, floatindex(cvt(fm.EI.engines[2].tOilIn, 40F, 160F, 0.0F, 6F), oilTempScale), 0.0F);
        mesh.chunkSetAngles("ZbarometrUR", 0.0F, floatindex(cvt(fm.EI.engines[2].tOilOut, 40F, 160F, 0.0F, 6F), oilTempScale), 0.0F);
        mesh.chunkSetAngles("ZoilL", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 10F, 0.0F, 300F), 0.0F);
        mesh.chunkSetAngles("ZoilC", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[1].tOilOut * fm.EI.engines[1].getReadyness(), 0.0F, 10F, 0.0F, 300F), 0.0F);
        mesh.chunkSetAngles("ZoilR", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[2].tOilOut * fm.EI.engines[2].getReadyness(), 0.0F, 10F, 0.0F, 300F), 0.0F);
        mesh.chunkSetAngles("ZfuelL", 0.0F, cvt(fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 3.25F, 0.0F, 270F), 0.0F);
        mesh.chunkSetAngles("ZfuelC", 0.0F, cvt(fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 3.25F, 0.0F, 270F), 0.0F);
        mesh.chunkSetAngles("ZfuelR", 0.0F, cvt(fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 3.25F, 0.0F, 270F), 0.0F);
        float f5 = setNew.vspeed * 18F;
        mesh.chunkSetAngles("ZclimbL", 0.0F, f5, 0.0F);
        mesh.chunkSetAngles("ZclimbR", 0.0F, f5, 0.0F);
        mesh.chunkSetAngles("ZThrottleL", 0.0F, -49F * interp(setNew.throttleL, setOld.throttleL, f), 0.0F);
        mesh.chunkSetAngles("ZThrottleC", 0.0F, -49F * interp(setNew.throttleC, setOld.throttleC, f), 0.0F);
        mesh.chunkSetAngles("ZThrottleR", 0.0F, -49F * interp(setNew.throttleR, setOld.throttleR, f), 0.0F);
        mesh.chunkSetAngles("MixturreL", 0.0F, 35F * interp(setNew.MixL, setOld.MixL, f), 0.0F);
        mesh.chunkSetAngles("MixturreC", 0.0F, 35F * interp(setNew.MixC, setOld.MixC, f), 0.0F);
        mesh.chunkSetAngles("MixturreR", 0.0F, 35F * interp(setNew.MixR, setOld.MixR, f), 0.0F);
        mesh.chunkSetAngles("PitchL", 0.0F, -40F * interp(setNew.propL, setOld.propL, f), 0.0F);
        mesh.chunkSetAngles("PitchC", 0.0F, -40F * interp(setNew.propC, setOld.propC, f), 0.0F);
        mesh.chunkSetAngles("PitchR", 0.0F, -40F * interp(setNew.propR, setOld.propR, f), 0.0F);
        mesh.chunkSetAngles("Wheel_PilotL", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 68F, 0.0F);
        mesh.chunkSetAngles("Wheel_PilotR", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 68F, 0.0F);
        mesh.chunkSetAngles("Wheel_Stick", 0.0F, -(pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 8F, 0.0F);
        mesh.chunkSetAngles("Z_BrkpresL", 0.0F, 7F * fm.CT.getBrake(), 0.0F);
        mesh.chunkSetAngles("Z_BrkpresR", 0.0F, -7F * fm.CT.getBrake(), 0.0F);
        resetYPRmodifier();
        float f6 = 0.07F * fm.CT.getRudder();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = f6;
        mesh.chunkSetLocate("Pedal_LeftL", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetLocate("Pedal_LeftR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -f6;
        mesh.chunkSetLocate("Pedal_RightL", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetLocate("Pedal_RightR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("ZmagnetoL", 0.0F, cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 110F), 0.0F);
        mesh.chunkSetAngles("ZmagnetoC", 0.0F, cvt(fm.EI.engines[1].getControlMagnetos(), 0.0F, 3F, 0.0F, 110F), 0.0F);
        mesh.chunkSetAngles("ZmagnetoR", 0.0F, cvt(fm.EI.engines[2].getControlMagnetos(), 0.0F, 3F, 0.0F, 110F), 0.0F);
        mesh.chunkSetAngles("Zdirectional_GiroC", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Zdirectional_GiroL", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Zdirectional_GiroR", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Zbank2L", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 23F, -23F), 0.0F);
        mesh.chunkSetAngles("Zbank1L", 0.0F, cvt(getBall(6D), -6F, 6F, -20.5F, 20.5F), 0.0F);
        mesh.chunkSetAngles("Zbank2R", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 23F, -23F), 0.0F);
        mesh.chunkSetAngles("Zbank1R", 0.0F, cvt(getBall(6D), -6F, 6F, -20.5F, 20.5F), 0.0F);
        mesh.chunkSetAngles("ZAH1R", 0.0F, -fm.Or.getKren(), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.011F, -0.015F);
        mesh.chunkSetLocate("ZcompassR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("zAH1L", 0.0F, -fm.Or.getKren(), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.02F, -0.02F);
        mesh.chunkSetLocate("ZcompassL", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_plane_FuelGaugeL", 0.0F, cvt(fm.M.fuel, 0.0F, 1720F, 0.0F, 245F), 0.0F);
        mesh.chunkSetAngles("Z_plane_FuelGaugeR", 0.0F, cvt(fm.M.fuel, 0.0F, 1720F, 0.0F, 245F), 0.0F);
        mesh.chunkSetAngles("Ztrim1", 0.0F, cvt(interp(setNew.elevTrim, setOld.elevTrim, f), -0.5F, 0.5F, -750F, 750F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -cvt(interp(setNew.elevTrim, setOld.elevTrim, f), -0.5F, 0.5F, -0.057F, 0.058F);
        mesh.chunkSetLocate("Z_Trim_Indicator", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_OAT", 0.0F, cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.15F, -40F, 40F, 0.0F, 93F), 0.0F);
    }

    public void reflectCockpitState()
    {
        if(!fm.AS.isPilotDead(2))
            mesh.chunkVisible("TailGunner", true);
        else
            mesh.chunkVisible("TailGunner", false);
        if((fm.AS.astateCockpitState & 0x80) != 0)
        {
            mesh.chunkVisible("Line01", false);
            mesh.chunkVisible("Panel_dmg", true);
            mesh.chunkVisible("PressL_dmg", true);
            mesh.chunkVisible("XGlassHoles1", true);
            mesh.chunkVisible("RPMC_dmg", true);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Line01", false);
            mesh.chunkVisible("Panel_dmg", true);
            mesh.chunkVisible("AltL_dmg", true);
            mesh.chunkVisible("RPMC_dmg", true);
            mesh.chunkVisible("OilTempC_dmg", true);
            mesh.chunkVisible("XGlassHoles2", true);
            mesh.chunkVisible("XGlassHoles1", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("Line01", false);
            mesh.chunkVisible("Panel_dmg", true);
            mesh.chunkVisible("ClockL_dmg", true);
            mesh.chunkVisible("XGlassHoles2", true);
            mesh.chunkVisible("XGlassHoles1", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0);
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlassHoles2", true);
            mesh.chunkVisible("PressL_dmg", true);
            mesh.chunkVisible("OilTempC_dmg", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("Line01", false);
            mesh.chunkVisible("Panel_dmg", true);
            mesh.chunkVisible("ClockL_dmg", true);
            mesh.chunkVisible("AltL_dmg", true);
            mesh.chunkVisible("XGlassHoles2", true);
            mesh.chunkVisible("XGlassHoles1", true);
            mesh.chunkVisible("RPMC_dmg", true);
            mesh.chunkVisible("PressL_dmg", true);
        }
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.0032F, 7.2F);
            light2.light.setEmit(0.003F, 7F);
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
    }

    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private boolean firstEnter;
    private static final float speedometerScale[] = {
        0.0F, 10F, 20F, 30F, 50F, 68F, 88F, 109F, 126F, 142F, 
        159F, 176F, 190F, 206F, 220F, 238F, 253F, 270F, 285F, 300F, 
        312F, 325F, 337F, 350F, 360F
    };
    private static final float oilTempScale[] = {
        0.0F, 26F, 54F, 95F, 154F, 244F, 359F
    };
    private boolean bNeedSetUp;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictFlap;
    private float pictGear;
    private float pictManf1;
    private float pictManf2;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
