// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHE_162C.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitHE_162C extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.vspeed = (299F * setOld.vspeed + fm.getVertSpeed()) / 300F;
            setNew.altimeter = fm.getAltitude();
            if(cockpitDimControl)
            {
                if(setNew.dimPosition > 0.0F)
                    setNew.dimPosition = setOld.dimPosition - 0.05F;
            } else
            if(setNew.dimPosition < 1.0F)
                setNew.dimPosition = setOld.dimPosition + 0.05F;
            setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
            setNew.mix = (8F * setOld.mix + fm.EI.engines[0].getControlMix()) / 9F;
            setNew.azimuth = fm.Or.getYaw();
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float altimeter;
        float throttle;
        float dimPosition;
        float azimuth;
        float waypointAzimuth;
        float mix;
        float vspeed;

        private Variables()
        {
        }

    }


    public CockpitHE_162C()
    {
        super("3DO/Cockpit/He-162C/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictManifold = 0.0F;
        tmpL = new Loc();
        bNeedSetUp = true;
        setNew.dimPosition = 1.0F;
        cockpitNightMats = (new java.lang.String[] {
            "gauges1", "gauges2", "gauges3", "gauges4", "gauges5", "gauges6", "gauges1_d1", "gauges2_d1", "gauges3_d1", "gauges4_d1", 
            "gauges5_d1", "gauges6_d1", "turnbank", "aiguill1", "Oxi_Press"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Throttle", 0.0F, 0.0F, -interp(setNew.throttle, setOld.throttle, f) * 58F);
        mesh.chunkSetAngles("sunOFF", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -44.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Fuel_Tank", 0.0F, 0.0F, fm.EI.engines[0].getStage() != 0 ? 0.0F : 38F);
        mesh.chunkSetAngles("Landing_Gear", 0.0F, 0.0F, -24F + 24F * fm.CT.GearControl);
        mesh.chunkSetAngles("ElevatorCrank", 3600F * fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.1F * fm.CT.getRudder();
        mesh.chunkSetLocate("PedalL", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -com.maddox.il2.objects.air.Cockpit.xyz[1];
        mesh.chunkSetLocate("PedalR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1000F, 0.0F, 10F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer2", floatindex(cvt(fm.getSpeedKMH(), 0.0F, 1000F, 0.0F, 10F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Second1", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM", floatindex(cvt(fm.EI.engines[0].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelContent", cvt(0.6F * fm.EI.engines[0].getPowerOutput(), 0.0F, 0.92F, 0.0F, 272F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_GasTemp", cvt(fm.EI.engines[0].tWaterOut, 300F, 1000F, 0.0F, 83F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPressure", cvt(1.0F + 0.005F * fm.EI.engines[0].tOilOut, 0.0F, 3.2F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPressure", cvt(fm.M.fuel, 0.0F, 500F, 0.0F, 77F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", setNew.vspeed < 0.0F ? -floatindex(cvt(-setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale) : floatindex(cvt(setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("z_Slide1a", 0.0F, cvt(getBall(7D), -7F, 7F, -13.5F, 13.5F), 0.0F);
        float f1;
        if(aircraft().isFMTrackMirror())
        {
            f1 = aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f1 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -6F, 6F, 24F, -24F);
            if(aircraft().fmTrack() != null)
                aircraft().fmTrack().setCockpitAzimuthSpeed(f1);
        }
        mesh.chunkSetAngles("Z_Azimuth1", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil1", cvt(fm.Or.getTangage(), -30F, 30F, -30F, 30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil3", cvt(fm.Or.getKren(), -45F, 45F, -45F, 45F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil2", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        aircraft().hierMesh().setCurChunk("GearC1_D0");
        aircraft().hierMesh().getChunkLocObj(tmpL);
        mesh.chunkSetAngles("GearC1_D0", 0.0F, tmpL.getOrient().getTangage(), 0.0F);
        mesh.chunkSetAngles("GearC2_D0", 0.0F, 120F * fm.CT.getGear(), 0.0F);
        f1 = java.lang.Math.max(-fm.CT.getGear() * 1500F, -110F);
        mesh.chunkSetAngles("GearC3_D0", 0.0F, -f1, 0.0F);
        resetYPRmodifier();
        if(aircraft().FM.CT.getGear() > 0.99F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(aircraft().FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 0.0632F);
            com.maddox.il2.objects.air.Cockpit.ypr[1] = 40F * aircraft().FM.CT.getRudder();
            mesh.chunkSetLocate("GearC25_D0", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            mesh.chunkSetAngles("GearC27_D0", 0.0F, cvt(aircraft().FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, -15F), 0.0F);
            mesh.chunkSetAngles("GearC28_D0", 0.0F, cvt(aircraft().FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 30F), 0.0F);
        } else
        {
            mesh.chunkSetAngles("GearC25_D0", 0.0F, 0.0F, 0.0F);
            mesh.chunkSetAngles("GearC27_D0", 0.0F, 0.0F, 0.0F);
            mesh.chunkSetAngles("GearC28_D0", 0.0F, 0.0F, 0.0F);
        }
        if(fm.CT.Weapons[0] != null)
        {
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.Weapons[0][0].countBullets(), 0.0F, 120F, 0.0F, 0.0415F);
            mesh.chunkSetLocate("Zammo_counter1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.Weapons[0][1].countBullets(), 0.0F, 120F, 0.0F, 0.0415F);
            mesh.chunkSetLocate("Zammo_counter2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
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

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_d1", true);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_GasTemp", false);
            mesh.chunkVisible("Z_Hour1", false);
            mesh.chunkVisible("Z_Minute1", false);
            mesh.chunkVisible("Z_Second1", false);
            mesh.chunkVisible("Z_OilPressure", false);
            mesh.chunkVisible("Z_FuelContent", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_d1", true);
            mesh.chunkVisible("z_Slide1a", false);
            mesh.chunkVisible("Z_Azimuth1", false);
            mesh.chunkVisible("Z_FuelPressure", false);
            mesh.chunkVisible("Z_RPM", false);
        }
        if((fm.AS.astateCockpitState & 8) == 0);
        if((fm.AS.astateCockpitState & 0x80) == 0);
        if((fm.AS.astateCockpitState & 0x10) == 0);
        if((fm.AS.astateCockpitState & 0x20) == 0);
    }

    private float tmp;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private float pictAiler;
    private float pictElev;
    private float pictManifold;
    private com.maddox.il2.engine.Loc tmpL;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        -1F, 3.5F, 48.5F, 97.5F, 144F, 198F, 253F, 305F, 358F, 407F, 
        419.5F
    };
    private static final float vsiNeedleScale[] = {
        0.0F, 48F, 82F, 96.5F, 111F, 120.5F, 130F, 130F
    };
    private static final float rpmScale[] = {
        0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55F, 77.5F, 104F, 133.5F, 162.5F, 
        192F, 224F, 254F, 255.5F, 260F
    };









}
