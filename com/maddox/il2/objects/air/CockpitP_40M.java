// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitP_40M.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitP_40M extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.prop = (8F * setOld.prop + fm.CT.getStepControl()) / 9F;
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 45F)
                    setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float throttle;
        float prop;
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;

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
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, fm.Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(-tmpV.y, tmpV.x));
        }
    }

    public CockpitP_40M()
    {
        super("3DO/Cockpit/P-40M/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        t1 = 0L;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "Textur1", "Textur2", "Textur25", "Textur3", "Textur4", "Textur6", "Textur7", "Textur9"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("Z_Throttle1", -66.81F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        if(fm.CT.getStepControlAuto())
            mesh.chunkSetAngles("Z_Pitch1", -70F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Pitch1", -70F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixt1", -70.8F * fm.EI.engines[0].getControlMix(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Columnbase", (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 16F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 0.0F, -20F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_LeftPedal2", 0.0F, 0.0F, 23F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_LPedalStep", 0.0F, 0.0F, -20F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_RightPedal", 0.0F, 0.0F, 20F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_RightPedal2", 0.0F, 0.0F, -23F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_RPedalStep", 0.0F, 0.0F, 20F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_SW_LandLights", fm.AS.bLandingLightOn ? 60F : 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_SW_UVLights", cockpitLightControl ? 60F : 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_SW_NavLights", fm.AS.bNavLightsOn ? 60F : 0.0F, 0.0F, 0.0F);
        mesh.chunkVisible("XLampCoolant1", fm.EI.engines[0].tOilOut > fm.EI.engines[0].tOilCritMax);
        mesh.chunkVisible("XLampFuel1", fm.M.fuel < 40.1F);
        mesh.chunkVisible("XLampGear1", fm.Gears.isAnyDamaged() || !fm.Gears.isOperable());
        mesh.chunkSetAngles("Z_Altimeter1", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -10800F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -1080F), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, 25F, -25F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0321F, -0.0321F);
        mesh.chunkSetLocate("Z_TurnBank2a", xyz, ypr);
        mesh.chunkSetAngles("Z_TurnBank3", -cvt(getBall(7D), -7F, 7F, -14F, 14F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Carbair1", cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.15F, -50F, 50F, -60F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flaps1", cvt(fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 80F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gearc1", cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 80F), 0.0F, 0.0F);
        if(fm.Gears.lgear)
            mesh.chunkSetAngles("Z_GearL1", cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 80F), 0.0F, 0.0F);
        if(fm.Gears.rgear)
            mesh.chunkSetAngles("Z_GearR1", cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 80F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Coolant1", cvt(fm.EI.engines[0].tWaterOut, 40F, 160F, 0.0F, 130F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 4F, 0.0F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 15F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pres1", cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 343.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Heading1", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 3500F, 0.0F, 280F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage3", true);
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("HullDamage4", true);
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage2", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Gauge", false);
            mesh.chunkVisible("Gauge_D1", true);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_Carbair1", false);
            mesh.chunkVisible("Z_Coolant1", false);
            mesh.chunkVisible("Z_Oilpres1", false);
            mesh.chunkVisible("HullDamage1", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("XGlassDamage1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("HullDamage3", true);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("HullDamage2", true);
            mesh.chunkVisible("XGlassDamage2", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("HullDamage2", true);
            mesh.chunkVisible("HullDamage4", true);
        }
        retoggleLight();
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

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private long t1;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 17F, 56.5F, 107.5F, 157F, 204F, 220.5F, 238.5F, 256.5F, 274.5F, 
        293F, 311F, 330F, 342F
    };
    private static final float variometerScale[] = {
        -170F, -147F, -124F, -101F, -78F, -48F, 0.0F, 48F, 78F, 101F, 
        124F, 147F, 170F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
