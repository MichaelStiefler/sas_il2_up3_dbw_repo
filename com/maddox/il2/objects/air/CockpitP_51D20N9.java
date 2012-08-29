// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitP_51D20N9.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, AircraftLH

public class CockpitP_51D20N9 extends com.maddox.il2.objects.air.CockpitPilot
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
                float f = waypointAzimuth(10F);
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f);
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
        float mix;
        float stage;
        float altimeter;
        float vspeed;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    protected float waypointAzimuth()
    {
        return super.waypointAzimuthInvertMinus(30F);
    }

    public CockpitP_51D20N9()
    {
        super("3DO/Cockpit/P-51D-20(N9)/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        cockpitNightMats = (new java.lang.String[] {
            "Fuel", "Textur1", "Textur2", "Textur3", "Textur4", "Textur5", "Textur6", "Textur8"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    public void reflectWorldToInstruments(float f)
    {
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
        mesh.chunkSetAngles("Z_TurnBank2", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0362F, -0.0362F);
        mesh.chunkSetLocate("Z_TurnBank2a", xyz, ypr);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14F), speedometerScale), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, 22.5F, -22.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(7D), -7F, 7F, 14F, -14F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank4", cvt(getBall(7D), -7F, 7F, 14F, -14F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Heading1", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 90F + setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 4500F, 0.0F, 316F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.35F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel2", cvt(fm.M.fuel, 0.0F, 245.2F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel3", cvt(fm.M.fuel, 245.2F, 490.4F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel4", cvt(fm.M.fuel, 245.2F, 490.4F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pres1", cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.370465F, 0.0F, 320F), 0.0F, 0.0F);
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

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
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







}
