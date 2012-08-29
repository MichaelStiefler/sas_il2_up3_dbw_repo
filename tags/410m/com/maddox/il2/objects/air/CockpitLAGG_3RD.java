// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitLAGG_3RD.java

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
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitLAGG_3RD extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.prop = (10F * setOld.prop + fm.EI.engines[0].getControlProp()) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F)) / 11F;
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

    public CockpitLAGG_3RD()
    {
        super("3do/cockpit/LaGG-3RD/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "prib_one", "prib_two", "prib_three", "prib_four", "prib_five", "shkala", "prib_one_dd", "prib_two_dd", "prib_three_dd", "Gauges_03", 
            "Gauges_04", "Gauges_04_D", "Gauges_05"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(useRealisticNavigationInstruments())
        {
            mesh.materialReplace("prib_three", "EmptyGauge");
            mesh.materialReplace("prib_three_dd", "EmptyGaugeD");
            mesh.materialReplace("prib_three_night", "EmptyGauge_night");
            mesh.materialReplace("prib_three_dd_night", "EmptyGaugeD_night");
            mesh.chunkVisible("zRPK10", false);
            setNightMats(true);
            setNightMats(false);
        }
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("zGas1T", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 1000F, 0.0F, 73F), 0.0F);
        mesh.chunkSetAngles("zGasPres1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.6F * fm.EI.engines[0].getPowerOutput(), 0.0F, 1.0F, 0.0F, 64F), 0.0F);
        mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.005F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 272.5F), 0.0F);
        mesh.chunkSetAngles("zFuelPrs1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 80F * fm.EI.engines[0].getPowerOutput() * fm.EI.engines[0].getReadyness(), 0.0F, 160F, 0.0F, 272.5F), 0.0F);
        mesh.chunkSetAngles("zRPM1a", 0.0F, floatindex(cvt(fm.EI.engines[0].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F);
        mesh.chunkSetAngles("richag", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Ped_Base", 0.0F, -fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("Tross_L", 0.0F, fm.CT.getRudder() * 15.65F, 0.0F);
        mesh.chunkSetAngles("Tross_R", 0.0F, fm.CT.getRudder() * 15.65F, 0.0F);
        mesh.chunkSetAngles("n_corr", 0.0F, 80F - interp(setNew.throttle, setOld.throttle, f) * 95F, 0.0F);
        mesh.chunkSetAngles("r_one", 0.0F, -20F * (float)(fm.CT.WeaponControl[0] ? 1 : 0), 0.0F);
        mesh.chunkSetAngles("r_two", 0.0F, -20F * (float)(fm.CT.WeaponControl[1] ? 1 : 0), 0.0F);
        mesh.chunkSetAngles("r_turn", 0.0F, 20F * fm.CT.BrakeControl, 0.0F);
        mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(fm.Or.getTangage(), -40F, 40F, -40F, 40F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("zGas1a", 0.0F, cvt(fm.M.fuel / 0.725F, 0.0F, 300F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F);
        if((fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x20) == 0)
        {
            w.set(fm.getW());
            fm.Or.transform(w);
            mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
            mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8D), -8F, 8F, 24F, -24F), 0.0F);
        }
        mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(setNew.vspeed, -10F, 10F, -180F, 180F), 0.0F);
        mesh.chunkSetAngles("zClock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zClock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zRPK10", 0.0F, cvt(interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), -25F, 25F, 35F, -35F), 0.0F);
        mesh.chunkVisible("Z_GearLRed1", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_GearRRed1", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_GearLGreen1", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_GearRGreen1", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_Red3", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_Red1", fm.M.fuel < 36F);
        mesh.chunkVisible("Z_Red2", fm.EI.engines[0].tOilOut > 115F || fm.EI.engines[0].tOilOut < 40F);
        mesh.chunkVisible("Z_Red5", fm.EI.engines[0].tWaterOut < 60F || fm.EI.engines[0].tWaterOut > 110F || fm.EI.engines[0].tOilIn < 40F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("prib_D1", false);
            mesh.chunkVisible("prib_DD1", true);
            mesh.chunkVisible("zAlt1a", false);
            mesh.chunkVisible("zAlt1b", false);
            mesh.chunkVisible("zVariometer1a", false);
            mesh.chunkVisible("zSpeed1a", false);
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("radio", false);
            mesh.chunkVisible("radio_d1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("prib_D2", false);
            mesh.chunkVisible("prib_DD2", true);
            mesh.chunkVisible("zAzimuth1a", false);
            mesh.chunkVisible("zAzimuth1b", false);
        }
        if((fm.AS.astateCockpitState & 2) != 0 || (fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
        if((fm.AS.astateCockpitState & 0x80) == 0);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 15.5F, 50F, 95.5F, 137F, 182.5F, 212F, 230F, 242F, 
        254.5F, 267.5F, 279F, 292F, 304F, 317F, 329.5F, 330F
    };
    private static final float rpmScale[] = {
        0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55F, 77.5F, 104F, 133.5F, 162.5F, 
        192F, 224F, 254F, 255.5F, 260F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
