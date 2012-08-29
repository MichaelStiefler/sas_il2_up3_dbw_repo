// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitJ2M3.java

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

public class CockpitJ2M3 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                setNew.manifold = 0.8F * setOld.manifold + 0.2F * fm.EI.engines[0].getManifoldPressure();
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
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        float manifold;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    public CockpitJ2M3()
    {
        super("3DO/Cockpit/J2M3/hier.him", "bf109");
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
        pictMetl = 0.0F;
        pictTriE = 0.0F;
        pictTriR = 0.0F;
        pictSupc = 0.0F;
        pictBoox = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "Arrows_Segment", "D_g_ind_01", "D_g_ind_02", "D_g_ind_03", "D_g_ind_04", "D_g_ind_05", "D_g_ind_06", "D_g_ind_07", "g_ind_01", "g_ind_02", 
            "g_ind_03", "g_ind_04", "g_ind_05", "g_ind_06", "g_ind_07"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("Z_IronSight", 0.0F, -72F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.07F;
        mesh.chunkSetLocate("Z_SightTint", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Trim1", 720F * (pictTriE = 0.92F * pictTriE + 0.08F * fm.CT.getTrimElevatorControl()), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim2", 160F * (pictTriR = 0.92F * pictTriR + 0.08F * fm.CT.getTrimRudderControl()), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_CowlFlaps1", 175.5F * fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
        float f1;
        if(fm.CT.GearControl == 0.0F && fm.CT.getGear() != 0.0F)
            f1 = -32F;
        else
        if(fm.CT.GearControl == 1.0F && fm.CT.getGear() != 1.0F)
            f1 = 32F;
        else
            f1 = 0.0F;
        mesh.chunkSetAngles("Z_Gear1", pictGear = 0.8F * pictGear + 0.2F * f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gear2", pictGear <= 0.0F ? 0.0F : 5F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 70.45F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prop1", 95F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixture1", 79F * (pictMix = 0.8F * pictMix + 0.2F * fm.EI.engines[0].getControlMix()), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Supercharger1", -32F * (pictSupc = 0.91F * pictSupc + 0.09F * (float)fm.EI.engines[0].getControlCompressor()), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Boost", -32F * (pictBoox = 0.91F * pictBoox + 0.09F * (fm.EI.engines[0].getControlAfterburner() ? 1.0F : 0.0F)), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Rudder", 16F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", 40F * fm.CT.getBrake() * cvt(fm.CT.getRudder(), -0.5F, 1.0F, 0.0F, 1.0F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", 40F * fm.CT.getBrake() * cvt(fm.CT.getRudder(), -1F, 0.5F, 1.0F, 0.0F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Columnbase", -18F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", -14F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trigger", !fm.CT.saveWeaponControl[0] && !fm.CT.saveWeaponControl[1] ? 0.0F : -16.5F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flaps1", -90F * fm.CT.FlapsControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilCooler", -175.5F * fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Magneto", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 76F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Overboost", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 92.59998F, 740.7998F, 0.0F, 7F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank1", fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.02705F, -0.02705F);
        mesh.chunkSetLocate("Z_TurnBank1a", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank2", cvt(w.z, -0.23562F, 0.23562F, -21F, 21F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(8D), -8F, 8F, 12F, -12F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", cvt(setNew.vspeed, -10F, 10F, 180F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 500F, 3500F, 0.0F, -315F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel, 50F, 403.2F, 0.0F, -240F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel2", cvt(fm.M.fuel, 0.0F, 43.2F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPres1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.32F, 0.0F, 1.0F, 0.0F, -278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_HydPres1", fm.Gears.isHydroOperable() ? -116F : 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tWaterOut * fm.EI.engines[0].getPowerOutput() * 3.5F, 500F, 900F, 0.0F, -65F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pres1", cvt(setNew.manifold, 0.200068F, 1.799932F, 164F, -164F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil1", -floatindex(cvt(fm.EI.engines[0].tOilOut, 30F, 110F, 0.0F, 4F), oilTempScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Methanol", cvt(fm.M.nitro, 0.0F, 80F, 0.0F, -69F), 0.0F, 0.0F);
        f1 = 0.0F;
        if(fm.EI.engines[0].getControlAfterburner())
        {
            f1 = 0.025F;
            if(fm.EI.engines[0].getControlThrottle() > 1.0F && fm.M.nitro > 0.05F)
                f1 = 0.68F;
        }
        pictMetl = 0.9F * pictMetl + 0.1F * f1;
        mesh.chunkSetAngles("Z_Methanol_Pres", cvt(pictMetl, 0.0F, 1.0F, 0.0F, -276F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_CylHead_Temp", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, -64F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FireExt_Quan", cvt(fm.EI.engines[0].getExtinguishers(), 0.0F, 11F, 0.0F, -180F), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 0.045F);
        mesh.chunkSetLocate("Z_Flap_Ind", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Ext_Air_Temp", -floatindex(cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z), 233.09F, 333.09F, 0.0F, 5F), frAirTempScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim1a", 0.0F, 0.0F, 27F * fm.CT.getTrimElevatorControl());
        mesh.chunkSetAngles("Z_Trim2a", 0.0F, 0.0F, 27F * fm.CT.getTrimRudderControl());
        mesh.chunkVisible("XLampGearUpR", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
        mesh.chunkVisible("XLampGearUpL", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
        mesh.chunkVisible("XLampGearDownR", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        mesh.chunkVisible("XLampGearDownL", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
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

    public void doToggleDim()
    {
    }

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
    private float pictMetl;
    private float pictTriE;
    private float pictTriR;
    private float pictSupc;
    private float pictBoox;
    private static final float speedometerScale[] = {
        0.0F, 109.5F, 220.5F, 337F, 433.5F, 513F, 605.5F, 301.5F
    };
    private static final float frAirTempScale[] = {
        0.0F, 27.5F, 49.5F, 66F, 82F, 100F
    };
    private static final float oilTempScale[] = {
        0.0F, 43.5F, 95.5F, 172F, 262F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
