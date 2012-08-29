// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitGO_229.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
//            CockpitPilot, AircraftLH, Aircraft

public class CockpitGO_229 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = fm.getAltitude();
            setNew.throttlel = (10F * setOld.throttlel + fm.EI.engines[0].getControlThrottle()) / 11F;
            setNew.throttler = (10F * setOld.throttler + fm.EI.engines[1].getControlThrottle()) / 11F;
            float f = waypointAzimuth();
            if(useRealisticNavigationInstruments())
            {
                setNew.waypointAzimuth.setDeg(f - 90F);
                setOld.waypointAzimuth.setDeg(f - 90F);
            } else
            {
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f - setOld.azimuth.getDeg(1.0F));
            }
            setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
            setNew.vspeed = (299F * setOld.vspeed + fm.getVertSpeed()) / 300F;
            if(cockpitDimControl)
            {
                if(setNew.dimPosition > 0.0F)
                    setNew.dimPosition = setOld.dimPosition - 0.05F;
            } else
            if(setNew.dimPosition < 1.0F)
                setNew.dimPosition = setOld.dimPosition + 0.05F;
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float altimeter;
        float throttlel;
        float throttler;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        float dimPosition;

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

    public CockpitGO_229()
    {
        super("3DO/Cockpit/Go-229/hier.him", "he111");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        tmpP = new Point3d();
        tmpV = new Vector3d();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        cockpitNightMats = (new java.lang.String[] {
            "2petitsb_d1", "2petitsb", "aiguill1", "badinetm_d1", "badinetm", "baguecom", "brasdele", "comptemu_d1", "comptemu", "petitfla_d1", 
            "petitfla", "turnbank"
        });
        setNightMats(false);
        setNew.dimPosition = 1.0F;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        if(fm.isTick(44, 0))
        {
            mesh.chunkVisible("Z_GearLGreen1", fm.CT.getGear() == 1.0F && fm.Gears.lgear);
            mesh.chunkVisible("Z_GearRGreen1", fm.CT.getGear() == 1.0F && fm.Gears.rgear);
            mesh.chunkVisible("Z_GearCGreen1", fm.CT.getGear() == 1.0F);
            mesh.chunkVisible("Z_GearLRed1", fm.CT.getGear() == 0.0F || fm.Gears.isAnyDamaged());
            mesh.chunkVisible("Z_GearCRed1", fm.CT.getGear() == 0.0F);
            mesh.chunkVisible("Z_FuelLampL", fm.M.fuel < 300F);
            mesh.chunkVisible("Z_FuelLampR", fm.M.fuel < 300F);
            mesh.chunkVisible("Z_Fire", false);
        }
        mesh.chunkVisible("Z_FlapEin", fm.CT.getFlap() < 0.05F);
        mesh.chunkVisible("Z_FlapStart", fm.CT.getFlap() > 0.28F && fm.CT.getFlap() < 0.38F);
        mesh.chunkVisible("Z_FlapAus", fm.CT.getFlap() > 0.95F);
        mesh.chunkSetAngles("zColumn1", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 10F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("zColumn2", 0.0F, -10F * pictAiler, 0.0F);
        mesh.chunkSetAngles("Z_PedalStrut", 10F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", -10F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", -10F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFlaps1", 0.0F, 0.0F, -32F + 42.5F * fm.CT.FlapsControl);
        mesh.chunkSetAngles("zThrottle1", 0.0F, 0.0F, 20.5F - 32F * interp(setNew.throttlel, setOld.throttlel, f));
        mesh.chunkSetAngles("zThrottle2", 0.0F, 0.0F, 20.5F - 32F * interp(setNew.throttler, setOld.throttler, f));
        mesh.chunkSetAngles("zGear1", 0.0F, 0.0F, -35.5F + 35.5F * fm.CT.GearControl);
        mesh.chunkSetAngles("zAirBrake1", 0.0F, 0.0F, 32F * fm.CT.AirBrakeControl);
        mesh.chunkSetAngles("Z_TurnBank1", fm.Or.getTangage(), 0.0F, fm.Or.getKren());
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, -cvt(getBall(6D), -6F, 6F, -7.5F, 7.5F));
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(w.z, -0.23562F, 0.23562F, -50F, 50F));
        mesh.chunkSetAngles("zSpeed1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 100F, 400F, 2.0F, 8F), speedometerIndScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zSpeed2", floatindex(cvt(fm.getSpeedKMH(), 100F, 1000F, 1.0F, 10F), speedometerTruScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 16000F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        if(useRealisticNavigationInstruments())
        {
            mesh.chunkSetAngles("zRepeater", setNew.azimuth.getDeg(f) - setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
            mesh.chunkSetAngles("zCompass", -setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("zCompass", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
            mesh.chunkSetAngles("zRepeater", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("zRPM1", floatindex(cvt(fm.EI.engines[0].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPM2", floatindex(cvt(fm.EI.engines[1].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuel1", floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 1000F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuel2", floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 1000F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zExtT", cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z), 273.09F, 373.09F, -26F, 144.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_GasTempL", cvt(fm.EI.engines[0].tWaterOut, 300F, 1000F, 0.0F, 96F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_GasTempR", cvt(fm.EI.engines[1].tWaterOut, 300F, 1000F, 0.0F, 96F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPressureL", cvt(1.0F + 0.005F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPressureR", cvt(1.0F + 0.005F * fm.EI.engines[1].tOilOut, 0.0F, 10F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPressure", cvt(fm.M.fuel <= 1.0F ? 0.0F : 80F * fm.EI.engines[0].getPowerOutput() * fm.EI.engines[0].getReadyness(), 0.0F, 160F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(setNew.vspeed, -20F, 50F, 0.0F, 14F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ReviTint", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -45F), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("HullDamage2", true);
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("Speedometer1", false);
            mesh.chunkVisible("Speedometer1_D1", true);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_Speedometer2", false);
            mesh.chunkVisible("RPML", false);
            mesh.chunkVisible("RPML_D1", true);
            mesh.chunkVisible("Z_RPML", false);
            mesh.chunkVisible("FuelRemainV", false);
            mesh.chunkVisible("FuelRemainV_D1", true);
            mesh.chunkVisible("Z_FuelRemainV", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("HullDamage4", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("Altimeter1", false);
            mesh.chunkVisible("Altimeter1_D1", true);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("GasPressureL", false);
            mesh.chunkVisible("GasPressureL_D1", true);
            mesh.chunkVisible("Z_GasPressureL", false);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("RPMR", false);
            mesh.chunkVisible("RPMR_D1", true);
            mesh.chunkVisible("Z_RPMR", false);
            mesh.chunkVisible("FuelPressR", false);
            mesh.chunkVisible("FuelPressR_D1", true);
            mesh.chunkVisible("Z_FuelPressR", false);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("HullDamage3", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("GasPressureR", false);
            mesh.chunkVisible("GasPressureR_D1", true);
            mesh.chunkVisible("Z_GasPressureR", false);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("Climb", false);
            mesh.chunkVisible("Climb_D1", true);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("FuelPressR", false);
            mesh.chunkVisible("FuelPressR_D1", true);
            mesh.chunkVisible("Z_FuelPressR", false);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("HullDamage2", true);
            mesh.chunkVisible("Revi_D0", false);
            mesh.chunkVisible("Z_ReViTint", false);
            mesh.chunkVisible("Revi_D1", true);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("FuelPressL", false);
            mesh.chunkVisible("FuelPressL_D1", true);
            mesh.chunkVisible("Z_FuelPressL", false);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("Altimeter1", false);
            mesh.chunkVisible("Altimeter1_D1", true);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Climb", false);
            mesh.chunkVisible("Climb_D1", true);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("AFN", false);
            mesh.chunkVisible("AFN_D1", true);
            mesh.chunkVisible("Z_AFN1", false);
            mesh.chunkVisible("Z_AFN2", false);
            mesh.chunkVisible("FuelPressL", false);
            mesh.chunkVisible("FuelPressL_D1", true);
            mesh.chunkVisible("Z_FuelPressL", false);
            mesh.chunkVisible("FuelRemainIn", false);
            mesh.chunkVisible("FuelRemainIn_D1", true);
            mesh.chunkVisible("Z_FuelRemainIn", false);
        }
        if((fm.AS.astateCockpitState & 0x80) == 0);
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
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
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerIndScale[] = {
        0.0F, 0.0F, 0.0F, 17F, 35.5F, 57.5F, 76F, 95F, 112F
    };
    private static final float speedometerTruScale[] = {
        0.0F, 32.75F, 65.5F, 98.25F, 131F, 164F, 200F, 237F, 270.5F, 304F, 
        336F
    };
    private static final float variometerScale[] = {
        0.0F, 13.5F, 27F, 43.5F, 90F, 142.5F, 157F, 170.5F, 184F, 201.5F, 
        214.5F, 226F, 239.5F, 253F, 266F
    };
    private static final float rpmScale[] = {
        0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55F, 77.5F, 104F, 133.5F, 162.5F, 
        192F, 224F, 254F, 255.5F, 260F
    };
    private static final float fuelScale[] = {
        0.0F, 11F, 31F, 57F, 84F, 103.5F
    };







}
