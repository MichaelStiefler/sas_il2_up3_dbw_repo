// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitP_47D25.java

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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitP_47D25 extends com.maddox.il2.objects.air.CockpitPilot
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
                if(java.lang.Math.abs(fm.Or.getKren()) < 45F)
                    setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                pictTurba = 0.97F * pictTurba + 0.03F * (0.5F * fm.EI.engines[0].getPowerOutput() + 0.5F * cvt(fm.EI.engines[0].getRPM(), 0.0F, 2000F, 0.0F, 1.0F));
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

    public CockpitP_47D25()
    {
        super("3DO/Cockpit/P-47D-25/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "prib1", "prib2", "prib3", "prib4", "prib5", "prib6", "shkala", "prib1_d1", "prib2_d1", "prib3_d1", 
            "prib4_d1", "prib5_d1", "prib6_d1"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("armPedalL", 0.0F, -15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("armPedalR", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, -15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 16F);
        mesh.chunkSetAngles("supercharge", 0.0F, 70F * setNew.stage, 0.0F);
        mesh.chunkSetAngles("throtle", 0.0F, 62.7F * setNew.throttle, 0.0F);
        mesh.chunkSetAngles("prop", 0.0F, 70F * setNew.prop, 0.0F);
        mesh.chunkSetAngles("mixtura", 0.0F, 55F * setNew.mix, 0.0F);
        mesh.chunkSetAngles("flaplever", 0.0F, 0.0F, 70F * fm.CT.FlapsControl);
        mesh.chunkSetAngles("zfuelR", 0.0F, floatindex(cvt(fm.M.fuel, 0.0F, 981F, 0.0F, 6F), fuelGallonsScale), 0.0F);
        mesh.chunkSetAngles("zfuelL", 0.0F, -floatindex(cvt(fm.M.fuel, 0.0F, 981F, 0.0F, 4F), fuelGallonsAuxScale), 0.0F);
        mesh.chunkSetAngles("zacceleration", 0.0F, cvt(fm.getOverload(), -4F, 12F, -77F, 244F), 0.0F);
        mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zclimb", 0.0F, floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 25F, -25F), 0.0F);
        mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(7D), -7F, 7F, -16F, 16F), 0.0F);
        mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.370465F, 0.0F, 320F), 0.0F);
        mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 10800F), 0.0F);
        mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 1080F), 0.0F);
        mesh.chunkSetAngles("zRPM1a", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 4500F, 0.0F, 316F), 0.0F);
        mesh.chunkSetAngles("zoiltemp1a", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 300F, 0.0F, 84F), 0.0F);
        mesh.chunkSetAngles("zClock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zClock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zhorizont1a", 0.0F, -fm.Or.getKren(), 0.0F);
        resetYPRmodifier();
        xyz[2] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0328F, -0.0328F);
        mesh.chunkSetLocate("zhorizont1b", xyz, ypr);
        mesh.chunkSetAngles("zturborpm1a", 0.0F, cvt(pictTurba, 0.0F, 2.0F, 0.0F, 207.5F), 0.0F);
        mesh.chunkSetAngles("zpressfuel1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.4F, 0.0F, -154F), 0.0F);
        mesh.chunkSetAngles("zpressoil1a", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(fm.Or.getTangage(), -5F, 5F, -5F, 5F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1b", 0.0F, 90F - setNew.azimuth, 0.0F);
        mesh.chunkSetAngles("zMagAzimuth1a", 0.0F, cvt(fm.Or.getTangage(), -65F, 65F, -65F, 65F), 0.0F);
        mesh.chunkSetAngles("zMagAzimuth1b", -90F + setNew.azimuth, 0.0F, 0.0F);
        mesh.chunkVisible("Z_Red1", fm.CT.getGear() < 0.05F || !fm.Gears.lgear || !fm.Gears.rgear);
        mesh.chunkVisible("Z_Green1", fm.CT.getGear() > 0.95F);
        mesh.chunkVisible("Z_Red2", fm.M.fuel / fm.M.maxFuel < 0.15F);
        mesh.chunkVisible("Z_Red3", false);
        mesh.chunkVisible("Z_Green2", false);
        mesh.chunkVisible("Z_Red4", false);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("pricel", false);
            mesh.chunkVisible("pricel_d1", true);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_d1", true);
            mesh.chunkVisible("zamper", false);
            mesh.chunkVisible("zAzimuth1a", false);
            mesh.chunkVisible("zAzimuth1b", false);
            mesh.chunkVisible("zSpeed1a", false);
            mesh.chunkVisible("zacceleration", false);
            mesh.chunkVisible("zMagAzimuth1a", false);
            mesh.chunkVisible("zMagAzimuth1b", false);
            mesh.chunkVisible("zpresswater1a", false);
            mesh.chunkVisible("zclimb", false);
            mesh.chunkVisible("zRPM1a", false);
            mesh.chunkVisible("zoiltemp1a", false);
            mesh.chunkVisible("zturbormp1a", false);
            mesh.chunkVisible("zfas1a", false);
            mesh.chunkVisible("zoxipress1a", false);
        }
        if((fm.AS.astateCockpitState & 4) == 0);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 0x10) == 0);
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_d1", true);
            mesh.chunkVisible("zClock1b", false);
            mesh.chunkVisible("zClock1a", false);
            mesh.chunkVisible("zfuelR", false);
            mesh.chunkVisible("zfuelL", false);
            mesh.chunkVisible("zsuction1a", false);
            mesh.chunkVisible("zTurn1a", false);
            mesh.chunkVisible("zSlide1a", false);
            mesh.chunkVisible("zhorizont1a", false);
            mesh.chunkVisible("zAlt1a", false);
            mesh.chunkVisible("zAlt1b", false);
            mesh.chunkVisible("zpressfuel1a", false);
            mesh.chunkVisible("zpressoil1a", false);
            mesh.chunkVisible("ztempoil1a", false);
            mesh.chunkVisible("zManifold1a", false);
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
    private float pictAiler;
    private float pictElev;
    private float pictTurba;
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
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;









}
