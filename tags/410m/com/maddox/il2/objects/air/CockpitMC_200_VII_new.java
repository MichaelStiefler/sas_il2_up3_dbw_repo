// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitMC_200_VII_new.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft

public class CockpitMC_200_VII_new extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float throttle;
        float prop;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
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
                setNew.throttle = 0.85F * setOld.throttle + fm.CT.PowerControl * 0.15F;
                setNew.prop = 0.85F * setOld.prop + fm.CT.getStepControl() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitMC_200_VII_new()
    {
        super("3DO/Cockpit/MC-200_VII_new/hier.him", "i16");
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictMix = 0.0F;
        pictFlap = 0.0F;
        pictGear = 0.0F;
        pictManf = 1.0F;
        pictFuel = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "mat2_tr", "strum1dmg", "strum1", "strum2dmg", "strum2", "strum4dmg", "strum4", "strumsxdmg", "strumsx"
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
        mesh.chunkSetAngles("Z_Column", 16F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 8F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("Pedals", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle", 46.36F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoSwitch", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 96F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mix", 46.25F * (pictMix = 0.85F * pictMix + 0.15F * fm.EI.engines[0].getControlMix()), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PropPitch1", 54F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flap1", 40F * (pictFlap = 0.75F * pictFlap + 0.25F * fm.CT.FlapsControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gear1", 90F * (pictGear = 0.8F * pictGear + 0.2F * fm.CT.GearControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim1", -76.5F * fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilRad1", 91F * fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilRad2", 111F * fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", 0.0F, -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 50F, 550F, 0.0F, 10F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Speedometer2", 0.0F, -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 50F, 550F, 0.0F, 10F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("Z_Climb1", 0.0F, cvt(setNew.vspeed, -25F, 25F, 180F, -180F), 0.0F);
        mesh.chunkSetAngles("Z_Compass1", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 3000F, 0.0F, -327F), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Turn1", 0.0F, cvt(w.z, -0.23562F, 0.23562F, -21F, 21F), 0.0F);
        mesh.chunkSetAngles("Turn2", 0.0F, cvt(getBall(8D), -8F, 8F, -12F, 12F), 0.0F);
        mesh.chunkSetAngles("Z_ManfoldPress", 0.0F, pictManf = 0.9F * pictManf + 0.1F * cvt(fm.EI.engines[0].getManifoldPressure(), 0.533288F, 1.33322F, 0.0F, -317F), 0.0F);
        mesh.chunkSetAngles("Z_OilTemp1", 0.0F, -floatindex(cvt(fm.EI.engines[0].tOilOut, 30F, 150F, 0.0F, 4F), oilTempScale), 0.0F);
        mesh.chunkSetAngles("Z_OilPress1", 0.0F, -cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 301.5F), 0.0F);
        mesh.chunkSetAngles("Z_OilPress2", 0.0F, -cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 184F), 0.0F);
        mesh.chunkSetAngles("Z_FuelPress1", 0.0F, -cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.32F, 0.0F, 1.0F, 0.0F, 301.5F), 0.0F);
        float f1 = cvt(java.lang.Math.abs(fm.Or.getTangage()), 0.0F, 12F, 1.0F, 0.0F);
        pictFuel = 0.92F * pictFuel + 0.08F * cvt(fm.M.fuel, 0.0F, 234F, cvt(f1, 0.0F, 1.0F, 0.0F, 24.5F), cvt(f1, 0.0F, 1.0F, 215F, 205F));
        mesh.chunkSetAngles("Z_FuelQuantity1", -pictFuel, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_EngTemp1", 0.0F, -cvt(fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, 74F), 0.0F);
        mesh.chunkSetAngles("Z_AirPress1", 0.0F, -135F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
        {
            mesh.chunkVisible("XLampGearUpR", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
            mesh.chunkVisible("XLampGearUpL", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
            mesh.chunkVisible("XLampGearDownR", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
            mesh.chunkVisible("XLampGearDownL", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        } else
        {
            mesh.chunkVisible("XLampGearUpR", false);
            mesh.chunkVisible("XLampGearUpL", false);
            mesh.chunkVisible("XLampGearDownR", false);
            mesh.chunkVisible("XLampGearDownL", false);
        }
    }

    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
            return 0.0F;
        waypoint.getP(tmpP);
        tmpV.sub(tmpP, fm.Loc);
        float f;
        for(f = (float)(57.295779513082323D * java.lang.Math.atan2(-tmpV.y, tmpV.x)); f <= -180F; f += 180F);
        for(; f > 180F; f -= 180F);
        return f;
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
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

    private boolean bNeedSetUp;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictMix;
    private float pictFlap;
    private float pictGear;
    private float pictManf;
    private float pictFuel;
    private static final float speedometerScale[] = {
        0.0F, 29F, 63.5F, 98.5F, 115.5F, 132.5F, 165.5F, 202.5F, 241F, 280F, 
        316F
    };
    private static final float oilTempScale[] = {
        0.0F, 33F, 80F, 153F, 301.5F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
