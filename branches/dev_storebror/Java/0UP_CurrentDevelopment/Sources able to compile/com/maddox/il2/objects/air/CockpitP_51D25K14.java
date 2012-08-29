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

public class CockpitP_51D25K14 extends CockpitPilot
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
                setNew.throttle = 0.85F * setOld.throttle + fm.CT.PowerControl * 0.15F;
                setNew.prop = 0.85F * setOld.prop + fm.CT.getStepControl() * 0.15F;
                setNew.stage = 0.85F * setOld.stage + (float)fm.EI.engines[0].getControlCompressor() * 0.15F;
                setNew.mix = 0.85F * setOld.mix + fm.EI.engines[0].getControlMix() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + waypointAzimuth() + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                float f = ((P_51D25NA)aircraft()).k14Distance;
                setNew.k14w = (5F * CockpitP_51D25K14.k14TargetWingspanScale[((P_51D25NA)aircraft()).k14WingspanType]) / f;
                setNew.k14w = 0.9F * setOld.k14w + 0.1F * setNew.k14w;
                setNew.k14wingspan = 0.9F * setOld.k14wingspan + 0.1F * CockpitP_51D25K14.k14TargetMarkScale[((P_51D25NA)aircraft()).k14WingspanType];
                setNew.k14mode = 0.8F * setOld.k14mode + 0.2F * (float)((P_51D25NA)aircraft()).k14Mode;
                com.maddox.JGP.Vector3d vector3d = aircraft().FM.getW();
                double d = 0.00125D * (double)f;
                float f1 = (float)java.lang.Math.toDegrees(d * vector3d.z);
                float f2 = -(float)java.lang.Math.toDegrees(d * vector3d.y);
                float f3 = floatindex((f - 200F) * 0.04F, CockpitP_51D25K14.k14BulletDrop) - CockpitP_51D25K14.k14BulletDrop[0];
                f2 += (float)java.lang.Math.toDegrees(java.lang.Math.atan(f3 / f));
                setNew.k14x = 0.92F * setOld.k14x + 0.08F * f1;
                setNew.k14y = 0.92F * setOld.k14y + 0.08F * f2;
                if(setNew.k14x > 7F)
                    setNew.k14x = 7F;
                if(setNew.k14x < -7F)
                    setNew.k14x = -7F;
                if(setNew.k14y > 7F)
                    setNew.k14y = 7F;
                if(setNew.k14y < -7F)
                    setNew.k14y = -7F;
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
        float mix;
        float stage;
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;
        float k14wingspan;
        float k14mode;
        float k14x;
        float k14y;
        float k14w;

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

    public CockpitP_51D25K14()
    {
        super("3DO/Cockpit/P-51D-25(K14)/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "Fuel", "Textur1", "Textur2", "Textur3", "Textur4", "Textur5", "Textur6", "Textur8"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void removeCanopy()
    {
        mesh.chunkVisible("Blister_D0", false);
    }

    public void reflectWorldToInstruments(float f)
    {
        int i = ((P_51D25NA)aircraft()).k14Mode;
        boolean flag = i < 2;
        mesh.chunkVisible("Z_Z_RETICLE", flag);
        flag = i > 0;
        mesh.chunkVisible("Z_Z_RETICLE1", flag);
        mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, setNew.k14x, setNew.k14y);
        resetYPRmodifier();
        Cockpit.xyz[0] = setNew.k14w;
        for(int j = 1; j < 7; j++)
        {
            mesh.chunkVisible("Z_Z_AIMMARK" + j, flag);
            mesh.chunkSetLocate("Z_Z_AIMMARK" + j, Cockpit.xyz, Cockpit.ypr);
        }

        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.55F);
        mesh.chunkSetLocate("Blister_D0", Cockpit.xyz, Cockpit.ypr);
        mesh.chunkSetAngles("Z_Trim1", 722F * fm.CT.getTrimAileronControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim2", 722F * fm.CT.getTrimRudderControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim3", -722F * fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flaps1", 21F * fm.CT.FlapsControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gear1", -30F + 30F * fm.CT.GearControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 77F * setNew.throttle, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prop1", 83.3F * setNew.prop, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixture1", 66F * setNew.mix, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPedalStep", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal2", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LPedalStep", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal2", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Columnbase", (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 16F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Target1", setNew.k14wingspan, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mode1", -90F * setNew.k14mode, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0362F, -0.0362F);
        mesh.chunkSetLocate("Z_TurnBank2a", Cockpit.xyz, Cockpit.ypr);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter3", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14F), speedometerScale), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, 22.5F, -22.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(7D), -7F, 7F, 14F, -14F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Heading1", setNew.azimuth, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", 90F + setNew.azimuth, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 90F + interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 4500F, 0.0F, 316F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.35F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel2", cvt(fm.M.fuel, 0.0F, 245.2F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel3", cvt(fm.M.fuel, 245.2F, 490.4F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel4", cvt(fm.M.fuel, 245.2F, 490.4F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pres1", cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.5395F, 0.0F, 345F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 14F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Coolant1", cvt(fm.EI.engines[0].tWaterOut, 40F, 150F, 0.0F, 130F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Carbair1", cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.15F, -50F, 50F, -60F, 60F), 0.0F, 0.0F);
        float f1 = fm.EI.engines[0].getRPM();
        f1 = 2.5F * (float)java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(f1))));
        mesh.chunkSetAngles("Z_Suction1", cvt(f1, 0.0F, 10F, 0.0F, 300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oxypres1", 142.5F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", cvt(fm.EI.engines[0].getReadyness(), 0.0F, 2.0F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkVisible("Z_GearGreen1", fm.CT.getGear() > 0.95F);
        mesh.chunkVisible("Z_GearRed1", fm.CT.getGear() < 0.05F || !fm.Gears.lgear || !fm.Gears.rgear);
        mesh.chunkVisible("Z_Supercharger1", fm.EI.engines[0].getControlCompressor() > 0);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) == 0);
        if((fm.AS.astateCockpitState & 1) == 0);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
        }
        if((fm.AS.astateCockpitState & 4) == 0);
        if((fm.AS.astateCockpitState & 8) == 0);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 0x10) == 0);
        if((fm.AS.astateCockpitState & 0x20) == 0);
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

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private static final float fuelGallonsScale[] = {
        0.0F, 8.25F, 17.5F, 36.5F, 54F, 90F, 108F
    };
    private static final float fuelGallonsAuxScale[] = {
        0.0F, 38F, 62.5F, 87F, 104F
    };
    private static final float speedometerScale[] = {
        0.0F, 5F, 47.5F, 92F, 134F, 180F, 227F, 241F, 255F, 272.5F, 
        287F, 299.5F, 312.5F, 325.5F, 338.5F
    };
    private static final float variometerScale[] = {
        -170F, -147F, -124F, -101F, -78F, -48F, 0.0F, 48F, 78F, 101F, 
        124F, 147F, 170F
    };
    private static final float k14TargetMarkScale[] = {
        -0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70F, -95F, -102.5F, -106F
    };
    private static final float k14TargetWingspanScale[] = {
        9.9F, 10.52F, 13.8F, 16.34F, 19F, 20F, 22F, 29.25F, 30F, 32.85F
    };
    private static final float k14BulletDrop[] = {
        5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 
        8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928F, 9.992F, 10.282F, 10.381F, 10.513F, 
        10.603F, 10.704F, 10.739F, 10.782F, 10.789F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;










}
