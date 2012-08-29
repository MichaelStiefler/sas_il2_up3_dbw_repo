// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitP_47D10.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitP_47D10 extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float throttle;
        float prop;
        float mix;
        float stage;
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;

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
                setNew.throttle = 0.85F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl * 0.15F;
                setNew.prop = 0.85F * setOld.prop + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.getStepControl() * 0.15F;
                setNew.stage = 0.85F * setOld.stage + (float)((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlCompressor() * 0.15F;
                setNew.mix = 0.85F * setOld.mix + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix() * 0.15F;
                setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getKren()) < 45F)
                    setNew.azimuth = (35F * setOld.azimuth + -((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 200F;
                if(((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude() > 3000F)
                {
                    float f = (float)java.lang.Math.sin(1.0F * cvt(((com.maddox.il2.fm.FlightModelMain) (fm)).getOverload(), 1.0F, 8F, 1.0F, 0.45F) * cvt(((com.maddox.il2.fm.FlightModelMain) (fm)).AS.astatePilotStates[0], 0.0F, 100F, 1.0F, 0.1F) * (0.001F * (float)com.maddox.rts.Time.current()));
                    if(f > 0.0F)
                    {
                        pictBlinker+= = 0.3F;
                        if(pictBlinker > 1.0F)
                            pictBlinker = 1.0F;
                    } else
                    {
                        pictBlinker-= = 0.3F;
                        if(pictBlinker < 0.0F)
                            pictBlinker = 0.0F;
                    }
                }
                pictTurba = 0.97F * pictTurba + 0.03F * (0.5F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getPowerOutput() + 0.5F * cvt(((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getRPM(), 0.0F, 2000F, 0.0F, 1.0F));
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
            waypoint.getP(com.maddox.il2.objects.air.Cockpit.P1);
            ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.V)).sub(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.P1)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.V)).y, ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.V)).x));
        }
    }

    public CockpitP_47D10()
    {
        super("3DO/Cockpit/P-47D-10/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictBlinker = 0.0F;
        bNeedSetUp = true;
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "prib1", "prib2", "prib3", "prib4", "prib5", "prib6", "shkala", "prib1_d1", "prib2_d1", "prib3_d1", 
            "prib4_d1", "prib5_d1", "prib6_d1"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("armPedalL", 0.0F, -15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("armPedalR", 0.0F, 15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("PedalL", 0.0F, 15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("PedalR", 0.0F, -15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl) * 16F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("supercharge", 0.0F, 70F * setNew.stage, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("throtle", 0.0F, 0.0F, -62.7F * setNew.throttle);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("prop", 0.0F, 70F * setNew.prop, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("mixtura", 0.0F, 55F * setNew.mix, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("flaplever", 0.0F, 0.0F, 70F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zfuelR", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel, 0.0F, 981F, 0.0F, 6F), fuelGallonsScale), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zfuelL", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel, 0.0F, 981F, 0.0F, 4F), fuelGallonsAuxScale), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zacceleration", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getOverload(), -4F, 12F, -77F, 244F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zSpeed1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14F), speedometerScale), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zclimb", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F);
        ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getW())));
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zTurn1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 25F, -25F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zSlide1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(7D), -7F, 7F, -16F, 16F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zManifold1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getManifoldPressure(), 0.3386378F, 2.370465F, 0.0F, 320F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAlt1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 10800F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAlt1b", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 1080F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zRPM1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 4500F, 0.0F, 316F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zoiltemp1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 300F, 0.0F, 84F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zClock1b", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zClock1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zhorizont1a", 0.0F, -((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -45F, 45F, 0.0328F, -0.0328F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("zhorizont1b", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zturborpm1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(pictTurba, 0.0F, 2.0F, 0.0F, 207.5F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zpressfuel1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.4F, 0.0F, -154F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zpressoil1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAzimuth1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -5F, 5F, -5F, 5F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAzimuth1b", 0.0F, 90F - setNew.azimuth, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zMagAzimuth1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -65F, 65F, -65F, 65F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zMagAzimuth1b", -90F + setNew.azimuth, 0.0F, 0.0F);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.01F * pictBlinker;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("zBlink1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("zBlink2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Red1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.05F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Green1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.95F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Red2", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel / ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.maxFuel < 0.15F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Red3", false);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Green2", false);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Red4", false);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes1_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("pricel", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("pricel_d1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_MASK", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) != 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes1_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("pribors1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("pribors1_d1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zamper", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zAzimuth1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zAzimuth1b", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zSpeed1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zacceleration", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zMagAzimuth1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zMagAzimuth1b", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zpresswater1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zclimb", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zRPM1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zoiltemp1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zturbormp1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zfas1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zoxipress1a", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) != 0);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) != 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes2_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) != 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_OilSplats_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) != 0);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("pribors2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("pribors2_d1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zClock1b", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zClock1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zfuelR", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zfuelL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zsuction1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zTurn1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zSlide1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zhorizont1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zAlt1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zAlt1b", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zpressfuel1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zpressoil1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("ztempoil1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zManifold1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zBlink1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zBlink2", false);
        }
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D0o", mat);
    }

    public void toggleLight()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl;
        if(((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl)
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
    }

    private void retoggleLight()
    {
        if(((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
        } else
        {
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        }
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictTurba;
    private float pictBlinker;
    private boolean bNeedSetUp;
    private static final float fuelGallonsScale[] = {
        0.0F, 8.25F, 17.5F, 36.5F, 54F, 90F, 108F
    };
    private static final float fuelGallonsAuxScale[] = {
        0.0F, 38F, 62.5F, 87F, 104F
    };
    private static final float speedometerScale[] = {
        0.0F, 5F, 47.5F, 92F, 134F, 180F, 227F, 241F, 255F, 262.5F, 
        270F, 283F, 296F, 312F, 328F
    };
    private static final float variometerScale[] = {
        -170F, -147F, -124F, -101F, -78F, -48F, 0.0F, 48F, 78F, 101F, 
        124F, 147F, 170F
    };













}
