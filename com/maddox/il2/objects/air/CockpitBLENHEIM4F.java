// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitBLENHEIM4F.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitBLENHEIM4F extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle1 = 0.85F * setOld.throttle1 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlThrottle() * 0.15F;
                setNew.throttle2 = 0.85F * setOld.throttle2 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlThrottle() * 0.15F;
                setNew.prop1 = 0.85F * setOld.prop1 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlProp() * 0.15F;
                setNew.prop2 = 0.85F * setOld.prop2 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlProp() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.azimut());
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(1.0F), f);
                setNew.waypointDeviation.setDeg(setOld.waypointDeviation.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                if((((com.maddox.il2.fm.FlightModelMain) (fm)).AS.astateCockpitState & 0x40) == 0)
                    setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                else
                    setNew.vspeed = (1990F * setOld.vspeed + fm.getVertSpeed()) / 2000F;
                setNew.radioalt = 0.9F * setOld.radioalt + 0.1F * ((fm.getAltitude() - com.maddox.il2.ai.World.land().HQ((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (fm)).Loc)).x, (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (fm)).Loc)).y)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
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
        float prop1;
        float prop2;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        com.maddox.il2.ai.AnglesFork waypointDeviation;
        float radioalt;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
            waypointDeviation = new AnglesFork();
        }

    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
            return 0.0F;
        waypoint.getP(tmpP);
        tmpV.sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)));
        float f;
        for(f = (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x)); f <= -180F; f += 180F);
        for(; f > 180F; f -= 180F);
        return f;
    }

    public CockpitBLENHEIM4F()
    {
        super("3DO/Cockpit/BlenheimMk4F/hier.him", "bf109");
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
        super.cockpitNightMats = (new java.lang.String[] {
            "prib_one_fin", "prib_two", "prib_three", "prib_four", "gauges2", "prib_one_fin_damage", "prib_two_damage", "prib_three_damage", "prib_four_damage", "gauges2_damage", 
            "PEICES1", "PEICES2"
        });
        mesh.chunkSetAngles("PRICEL_ST_MOS", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("PRICEL_MOS", 0.0F, 0.0F, 0.0F);
        setNightMats(false);
        interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("Nose_D0", false);
            aircraft().hierMesh().chunkVisible("Nose_D1", false);
            aircraft().hierMesh().chunkVisible("Nose_D2", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("Nose_D0", true);
        aircraft().hierMesh().chunkVisible("Nose_D1", true);
        aircraft().hierMesh().chunkVisible("Nose_D2", true);
        super.doFocusLeave();
    }

    public void reflectWorldToInstruments(float f)
    {
        resetYPRmodifier();
        super.mesh.chunkSetAngles("Canopy", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, -120F), 0.0F);
        super.mesh.chunkSetAngles("Z_Trim1", 0.0F, 161F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimAileronControl(), 0.0F);
        super.mesh.chunkSetAngles("Z_Trim2", 0.0F, 332F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimRudderControl(), 0.0F);
        super.mesh.chunkSetAngles("Z_Trim3", 0.0F, 722F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl(), 0.0F);
        super.mesh.chunkSetAngles("Z_Flaps1", 0.0F, -75.5F * (pictFlap = 0.85F * pictFlap + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl), 0.0F);
        super.mesh.chunkSetAngles("Z_Gear1", 0.0F, -75.5F * (pictGear = 0.85F * pictGear + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl), 0.0F);
        super.mesh.chunkSetAngles("Z_Throtle1", 0.0F, 90F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F);
        super.mesh.chunkSetAngles("Z_Prop1", 0.0F, 100F * interp(setNew.prop1, setOld.prop1, f), 0.0F);
        super.mesh.chunkSetAngles("Z_Prop2", 0.0F, 100F * interp(setNew.prop2, setOld.prop2, f), 0.0F);
        super.mesh.chunkSetAngles("Z_Mixture1", 0.0F, 90F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlMix(), 0.0F);
        super.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlCompressor(), 0.0F, 1.0F, 0.0F, -50F), 0.0F);
        super.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getControlCompressor(), 0.0F, 1.0F, 0.0F, -50F), 0.0F);
        super.mesh.chunkSetAngles("Z_RightPedal", 0.0F, -10F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F);
        super.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 10F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F);
        super.mesh.chunkSetAngles("Z_Columnbase", 0.0F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl) * 8F, 0.0F);
        super.mesh.chunkSetAngles("Z_Column", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl) * 57F, 0.0F);
        super.mesh.chunkSetAngles("Z_Brake", 0.0F, -25F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getBrake(), 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 10800F), 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 1080F), 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter3", 0.0F, cvt(interp(setNew.radioalt, setOld.radioalt, f), 0.0F, 609.6F, 0.0F, 720F), 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0)
            super.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeed()), 0.0F, 143.0528F, 0.0F, 32F), speedometerScaleFAF), 0.0F);
        w.set(((com.maddox.JGP.Tuple3d) (super.fm.getW())));
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        super.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, -48F, 48F), 0.0F);
        super.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(getBall(8D), -8F, 8F, 35F, -35F), 0.0F);
        super.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren(), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 0.0275F, -0.0275F);
        super.mesh.chunkSetLocate("Z_TurnBank4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_Climb1", 0.0F, floatindex(cvt(setNew.vspeed, -20.32F, 20.32F, 0.0F, 8F), variometerScale), 0.0F);
        super.mesh.chunkSetAngles("Z_Compass1", 0.0F, -setNew.azimuth.getDeg(f), 0.0F);
        super.mesh.chunkSetAngles("Z_Compass2", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        super.mesh.chunkSetAngles("Z_RPM1", 0.0F, floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 1000F, 5000F, 0.0F, 8F), rpmScale), 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0)
            super.mesh.chunkSetAngles("Z_RPM2", 0.0F, floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM(), 1000F, 5000F, 0.0F, 8F), rpmScale), 0.0F);
        else
            super.mesh.chunkSetAngles("Z_RPM2", 0.0F, floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM(), 500F, 9800F, 0.0F, 8F), rpmScale), 0.0F);
        super.mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        super.mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        super.mesh.chunkSetAngles("Z_Fuel1", 0.0F, 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 2332F, 0.0F, 77F));
        super.mesh.chunkSetAngles("Z_Fuel2", 0.0F, 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 2332F, 0.0F, 77F));
        super.mesh.chunkSetAngles("Z_FuelPres1", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() / 910F, 0.0F, 10F, 0.0F, 277F), 0.0F);
        super.mesh.chunkSetAngles("Z_FuelPres2", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM() / 880F, 0.0F, 10F, 0.0F, 277F), 0.0F);
        super.mesh.chunkSetAngles("Z_Temp1", 0.0F, floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilIn, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0)
            super.mesh.chunkSetAngles("Z_Temp2", 0.0F, floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tOilIn, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F);
        super.mesh.chunkSetAngles("Z_Pres1", 0.0F, pictManf1 = 0.9F * pictManf1 + 0.1F * cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 346.5F), 0.0F);
        super.mesh.chunkSetAngles("Z_Pres2", 0.0F, pictManf2 = 0.9F * pictManf2 + 0.1F * cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 346.5F), 0.0F);
        super.mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 40F, 100F, 0.0F, 274F), 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0)
            super.mesh.chunkSetAngles("Z_Oil2", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tOilOut, 40F, 100F, 0.0F, 274F), 0.0F);
        if(super.fm.getOverload() < 0.0F)
        {
            super.mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * (((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut / 10F) + ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() / 800F + super.fm.getOverload() / 1.8F, 0.0F, 12.59F, 0.0F, 277F), 0.0F);
            super.mesh.chunkSetAngles("Z_Oilpres2", 0.0F, cvt(1.0F + 0.05F * (((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tOilOut / 10F) + ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM() / 820F + super.fm.getOverload() / 1.8F, 0.0F, 12.59F, 0.0F, 277F), 0.0F);
        } else
        {
            super.mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * (((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut / 10F) + ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() / 800F + super.fm.getOverload() / 3.8F, 0.0F, 12.59F, 0.0F, 277F), 0.0F);
        }
        super.mesh.chunkSetAngles("Z_Oilpres2", 0.0F, cvt(1.0F + 0.05F * (((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tOilOut / 10F) + ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM() / 820F + super.fm.getOverload() / 3.8F, 0.0F, 12.59F, 0.0F, 277F), 0.0F);
        float f1 = 0.5F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() + 0.5F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM();
        f1 = 2.5F * (float)java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(f1))));
        super.mesh.chunkSetAngles("Z_Suction", 0.0F, cvt(f1, 0.0F, 10F, 0.0F, 302F), 0.0F);
        super.mesh.chunkSetAngles("Z_Approach", 0.0F, cvt(setNew.waypointDeviation.getDeg(f), -90F, 90F, -46.5F, 46.5F), 0.0F);
        super.mesh.chunkSetAngles("Z_AirTemp", 0.0F, cvt(com.maddox.il2.fm.Atmosphere.temperature((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z) - 273.15F, -17.8F, 60F, 0.0F, -109.5F), 0.0F);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
        {
            super.mesh.chunkVisible("Z_OilSplats1_D1", true);
            super.mesh.chunkVisible("Z_OilSplats2_D1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
        {
            super.mesh.chunkVisible("Z_Z_RETICLE", false);
            super.mesh.chunkVisible("Z_Z_MASK", false);
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("XHullDamage1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage4", true);
            super.mesh.chunkVisible("XHullDamage1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0)
        {
            super.mesh.chunkVisible("XHullDamage1", true);
            super.mesh.chunkVisible("Panel_D0", false);
            super.mesh.chunkVisible("Panel_D1", true);
            super.mesh.chunkVisible("Z_Fuel2", false);
            super.mesh.chunkVisible("Z_Altimeter1", false);
            super.mesh.chunkVisible("Z_Altimeter2", false);
            super.mesh.chunkVisible("Z_Altimeter3", false);
            super.mesh.chunkVisible("Z_Hour1", false);
            super.mesh.chunkVisible("Z_Minute1", false);
            super.mesh.chunkVisible("Z_Oilpres1", false);
            super.mesh.chunkVisible("Z_FuelPres2", false);
            super.mesh.chunkVisible("Z_TurnBank1", false);
            super.mesh.chunkVisible("Z_TurnBank2", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
            super.mesh.chunkVisible("XGlassDamage3", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0)
            super.mesh.chunkVisible("XHullDamage2", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
            super.mesh.chunkVisible("XGlassDamage2", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
            super.mesh.chunkVisible("XHullDamage3", true);
        retoggleLight();
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
    private static final float speedometerScaleFAF[] = {
        0.0F, 0.0F, 2.0F, 6F, 21F, 40F, 56F, 72.5F, 89.5F, 114F, 
        135.5F, 157F, 182.5F, 205F, 227.5F, 246.5F, 265.5F, 286F, 306F, 326F, 
        345.5F, 363F, 385F, 401F, 414.5F, 436.5F, 457F, 479F, 496.5F, 515F, 
        539F, 559.5F, 576.5F
    };
    private static final float variometerScale[] = {
        -158F, -110F, -63.5F, -29F, 0.0F, 29F, 63.5F, 110F, 158F
    };
    private static final float rpmScale[] = {
        0.0F, 20F, 54F, 99F, 151.5F, 195.5F, 249.25F, 284.5F, 313.75F
    };
    private static final float radScale[] = {
        0.0F, 3F, 7F, 13.5F, 21.5F, 27F, 34.5F, 50.5F, 71F, 94F, 
        125F, 161F, 202.5F, 253F, 315.5F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
