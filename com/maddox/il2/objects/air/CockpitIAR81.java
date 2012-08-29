// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitIAR81.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
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
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitIAR81 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
            setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            setNew.azimuth = fm.Or.getYaw();
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
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

        float throttle;
        float azimuth;
        float waypointAzimuth;
        float vspeed;
        float dimPosition;

        private Variables()
        {
        }

    }


    public CockpitIAR81()
    {
        super("3DO/Cockpit/IAR-81A/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        tmpP = new Point3d();
        tmpV = new Vector3d();
        setNew.dimPosition = 1.0F;
        cockpitNightMats = (new java.lang.String[] {
            "gauges1", "gauges2", "gauges3", "gauges4", "gauges5", "gauges6", "gauges1_d1", "gauges2_d1", "gauges3_d1", "gauges4_d1"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkVisible("Z_GearLRed1", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_GearRRed1", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_GearLGreen1", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_GearRGreen1", fm.CT.getGear() == 1.0F);
        mesh.chunkSetAngles("sun_off", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 165F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Stick", (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        resetYPRmodifier();
        if(fm.CT.WeaponControl[0])
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -0.01F;
        mesh.chunkSetAngles("Ped_Base", 0.0F, -15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Throttle", 0.0F, 0.0F, 22.2F - 80F * interp(setNew.throttle, setOld.throttle, f));
        mesh.chunkSetAngles("Throttle_rod", 0.0F, 0.0F, -22.2F + 80F * interp(setNew.throttle, setOld.throttle, f));
        mesh.chunkSetAngles("Magneto", 0.0F, 0.0F, 16.66667F * (float)fm.EI.engines[0].getControlMagnetos());
        mesh.chunkSetAngles("Flaps", 0.0F, 0.0F, -50F * (pictFlap = 0.85F * pictFlap + 0.15F * fm.CT.FlapsControl));
        if(fm.Gears.isHydroOperable())
            mesh.chunkSetAngles("Gears", 0.0F, 0.0F, -50F * (pictGear = 0.85F * pictGear + 0.15F * fm.CT.GearControl));
        else
            mesh.chunkSetAngles("H-manual", 0.0F, 0.0F, fm.CT.GearControl >= 0.1F ? -22.5F : 0.0F);
        mesh.chunkSetAngles("Radiator", 0.0F, 0.0F, -50F * (pictRadiator = 0.85F * pictRadiator + 0.15F * fm.EI.engines[0].getControlRadiator()));
        float f1 = com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH());
        mesh.chunkSetAngles("zSpeed1a", 0.0F, f1 <= 100F ? cvt(f1, 0.0F, 100F, 0.0F, 22.5F) : cvt(f1, 100F, 800F, 22.5F, 337.5F), 0.0F);
        mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(fm.getAltitude(), 0.0F, 16000F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zClock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zClock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zClock1c", 0.0F, cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zTOil1a", 0.0F, (f1 = fm.EI.engines[0].tOilOut) <= 100F ? floatindex(cvt(f1, 0.0F, 100F, 0.0F, 5F), oilTScale) : 180F + 3.725F * (f1 - 100F), 0.0F);
        mesh.chunkSetAngles("zGasPrs1a", 0.0F, floatindex(cvt(1.388889F * fm.M.fuel, 0.0F, 450F, 0.0F, 9F), fuelScale), 0.0F);
        mesh.chunkSetAngles("zTure1a", 0.0F, cvt(fm.EI.engines[0].getRPM(), 500F, 3000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(setNew.vspeed, -20F, 20F, -180F, 180F), 0.0F);
        float f2;
        if(aircraft().isFMTrackMirror())
        {
            f2 = aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f2 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -6F, 6F, 18F, -18F);
            if(aircraft().fmTrack() != null)
                aircraft().fmTrack().setCockpitAzimuthSpeed(f2);
        }
        mesh.chunkSetAngles("zTurn1a", 0.0F, f2, 0.0F);
        mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8D), -8F, 8F, -15F, 15F), 0.0F);
        mesh.chunkSetAngles("zPitch1a", 0.0F, 270F - (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 60F, 0.0F);
        mesh.chunkSetAngles("zPitch1b", 0.0F, 105F - (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 5F, 0.0F);
        mesh.chunkSetAngles("zPress1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.67F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("zPress1b", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 20F, 0.0F, 124F), 0.0F);
        mesh.chunkSetAngles("zPressure1a", 0.0F, cvt(pictManifold = 0.85F * pictManifold + 0.15F * fm.EI.engines[0].getManifoldPressure(), 0.266644F, 1.866508F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Azimuth1", -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
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
            return (float)(57.295779513082323D * java.lang.Math.atan2(tmpV.y, tmpV.x));
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

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) == 0);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_d1", true);
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_d1", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
        if((fm.AS.astateCockpitState & 8) == 0);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
        if((fm.AS.astateCockpitState & 0x20) == 0);
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private float pictAiler;
    private float pictElev;
    private float pictFlap;
    private float pictGear;
    private float pictRadiator;
    private float pictManifold;
    private static final float oilTScale[] = {
        0.0F, 32F, 50.05F, 78F, 123.5F, 180F
    };
    private static final float fuelScale[] = {
        0.0F, 10.5F, 30F, 71F, 114F, 148.5F, 175.5F, 202.5F, 232F, 258F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
