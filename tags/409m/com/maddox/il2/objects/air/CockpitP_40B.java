// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitP_40B.java

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
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitP_40B extends com.maddox.il2.objects.air.CockpitPilot
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

    public CockpitP_40B()
    {
        super("3DO/Cockpit/P-40B/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        t1 = 0L;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "Arrows_Segment", "Indicators_2", "Indicators_2_Dam", "Indicators_3", "Indicators_3_Dam", "Indicators_4", "Indicators_4_Dam", "Indicators_5", "Indicators_5_Dam", "Indicators_6", 
            "Indicators_7"
        });
        setNightMats(false);
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
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.63F);
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkVisible("XLampCoolant1", fm.EI.engines[0].tOilOut > fm.EI.engines[0].tOilCritMax);
        mesh.chunkSetAngles("Z_Altimeter2", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -10800F), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -1080F), 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
        {
            mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
            mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
            mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        }
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 25F, -25F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(7D), -7F, 7F, -13F, 13F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank9", 0.0F, -fm.Or.getKren(), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(fm.Or.getTangage(), -45F, 45F, 0.03015F, -0.03015F);
        mesh.chunkSetLocate("Z_TurnBank3", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Climb1", 0.0F, floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, -interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Compass2", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 3500F, 0.0F, 504F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(fm.M.fuel, 0.0F, 400F, 0.0F, 267F), 0.0F);
        mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_Pres1", 0.0F, cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 1.71F, 0.0F, 343.5F), 0.0F);
        mesh.chunkSetAngles("Z_OilPres", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_FuelPres", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 4F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("Z_Carbair1", 0.0F, cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.15F, -50F, 50F, -50F, 50F), 0.0F);
        float f1 = fm.EI.engines[0].getRPM();
        f1 = 2.5F * (float)java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(f1))));
        mesh.chunkSetAngles("Z_Suction1", 0.0F, cvt(f1, 0.0F, 10F, 0.0F, 306F), 0.0F);
        mesh.chunkSetAngles("Z_Coolant1", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 40F, 160F, -65F, 65F), 0.0F);
        mesh.chunkSetAngles("Z_Trim1", 0.0F, 0.0F, 0.0F);
        if(fm.CT.getTrimAileronControl() > fm.CT.trimAileron + 0.01F)
            mesh.chunkSetAngles("Z_Trim1", 0.0F, 33F, 0.0F);
        else
        if(fm.CT.getTrimAileronControl() < fm.CT.trimAileron - 0.01F)
            mesh.chunkSetAngles("Z_Trim1", 0.0F, -33F, 0.0F);
        mesh.chunkSetAngles("Z_Trim2", 0.0F, 722F * fm.CT.getTrimRudderControl(), 0.0F);
        mesh.chunkSetAngles("Z_Trim3", 0.0F, -722F * fm.CT.getTrimElevatorControl(), 0.0F);
        mesh.chunkSetAngles("Z_gear_lever", -30F * fm.CT.GearControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_flaps_lever", 45F * fm.CT.FlapsControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Rad_lever", 0.0F, -75F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        mesh.chunkSetAngles("Z_Rad_rod", 0.0F, 75F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 0.0F, 66.81F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        mesh.chunkSetAngles("Z_Prop1", 0.0F, 66.5F * interp(setNew.prop, setOld.prop, f), 0.0F);
        mesh.chunkSetAngles("Z_Mixture1", 0.0F, 60.8F * fm.EI.engines[0].getControlMix(), 0.0F);
        mesh.chunkSetAngles("Z_RightPedal1", 0.0F, -20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_RightPedal2", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_RightPedal3", 0.0F, 0.0F, 20F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_LeftPedal1", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal2", 0.0F, -20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal3", 0.0F, 0.0F, -20F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_Columnbase", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, 0.0F);
        mesh.chunkSetAngles("Z_Column", 0.0F, -(pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 16F, 0.0F);
        mesh.chunkSetAngles("Z_ColumnCable1", 0.0F, pictElev * 16F, 0.0F);
        if(fm.Gears.lgear)
            mesh.chunkSetAngles("Z_GearInd1", 0.0F, cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 72F), 0.0F);
        if(fm.Gears.rgear)
            mesh.chunkSetAngles("Z_GearInd2", 0.0F, cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, -72F), 0.0F);
        mesh.chunkSetAngles("Z_GearInd3", 0.0F, cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 88F), 0.0F);
        mesh.chunkSetAngles("Z_FlapInd1", 0.0F, cvt(fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, -80F), 0.0F);
        mesh.chunkSetAngles("Z_Ignition", 0.0F, cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 90F), 0.0F);
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage4", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("Z_TurnBank1", false);
            mesh.chunkVisible("Z_TurnBank2", false);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_Fuel1", false);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_Pres1", false);
            mesh.chunkVisible("Z_Suction1", false);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_Temp1", false);
            mesh.chunkVisible("Z_OilPres", false);
            mesh.chunkVisible("Z_FuelPres", false);
            mesh.chunkVisible("Z_Coolant1", false);
            mesh.chunkVisible("XHullDamage2", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XHullDamage4", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlassDamage5", true);
            mesh.chunkVisible("XHullDamage1", true);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XGlassDamage5", true);
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
    private boolean bNeedSetUp;
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
