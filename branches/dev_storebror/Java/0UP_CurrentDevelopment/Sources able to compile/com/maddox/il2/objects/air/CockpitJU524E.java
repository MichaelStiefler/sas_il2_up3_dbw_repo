package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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

public class CockpitJU524E extends CockpitPilot
{
    private class Variables
    {

        float throttle1;
        float throttle2;
        float throttle3;
        float prop1;
        float prop2;
        float prop3;
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

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.throttle1 = 0.85F * setOld.throttle1 + fm.EI.engines[0].getControlThrottle() * 0.15F;
                setNew.prop1 = 0.85F * setOld.prop1 + fm.EI.engines[0].getControlProp() * 0.15F;
                setNew.throttle2 = 0.85F * setOld.throttle2 + fm.EI.engines[1].getControlThrottle() * 0.15F;
                setNew.prop2 = 0.85F * setOld.prop2 + fm.EI.engines[1].getControlProp() * 0.15F;
                setNew.throttle3 = 0.85F * setOld.throttle3 + fm.EI.engines[2].getControlThrottle() * 0.15F;
                setNew.prop3 = 0.85F * setOld.prop3 + fm.EI.engines[2].getControlProp() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f - setOld.azimuth.getDeg(1.0F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = 0.99F * setOld.vspeed + 0.01F * fm.getVertSpeed();
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("CF_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("CF_D0", true);
        super.doFocusLeave();
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

    public CockpitJU524E()
    {
        super("3DO/Cockpit/Ju52/CockpitJU524E.him", "he111");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictBrake = 0.0F;
        pictFlap = 0.0F;
        pictSupc = 0.0F;
        pictLlit = 0.0F;
        pictManf1 = 1.0F;
        pictManf2 = 1.0F;
        pictManf3 = 1.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "01", "02", "03", "04", "05", "12", "20", "23", "24", "26", 
            "27", "01_damage", "03_damage", "04_damage", "24_damage"
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
        mesh.chunkVisible("XLampFuel1", fm.EI.engines[0].getRPM() < 300F);
        mesh.chunkVisible("XLampFuel2", fm.EI.engines[1].getRPM() < 300F);
        mesh.chunkSetAngles("Z_Columnbase", 12F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", 45F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column52R", 0.0F, 45F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F);
        mesh.chunkSetAngles("Z_ColumnSwitch", 20F * (pictBrake = 0.91F * pictBrake + 0.09F * fm.CT.BrakeControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 62.72F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle2", 62.72F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("ZThrottleR", 62.72F * interp(setNew.throttle3, setOld.throttle3, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", -20F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", 20F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("zHour", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zMinute", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zSecond", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        float f1;
        if(java.lang.Math.abs(fm.CT.FlapsControl - fm.CT.getFlap()) > 0.02F)
        {
            if(fm.CT.FlapsControl - fm.CT.getFlap() > 0.0F)
                f1 = -0.0299F;
            else
                f1 = -0F;
        } else
        {
            f1 = -0.0144F;
        }
        pictFlap = 0.8F * pictFlap + 0.2F * f1;
        resetYPRmodifier();
        Cockpit.xyz[2] = pictFlap;
        mesh.chunkSetLocate("Z_Flaps1", Cockpit.xyz, Cockpit.ypr);
        mesh.chunkSetAngles("Z_Trim1", -1000F * fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim2", 1000F * fm.CT.getTrimAileronControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim3", 90F * fm.CT.getTrimRudderControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prop1", 90F * setNew.prop1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prop2", 90F * setNew.prop2, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prop3", 90F * setNew.prop3, 0.0F, 0.0F);
        mesh.chunkSetAngles("COMPASS_M", 90F + setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("SHKALA_DIRECTOR", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", setNew.azimuth.getDeg(f) + setNew.waypointAzimuth.getDeg(f) + 90F, 0.0F, 0.0F);
        mesh.chunkSetAngles("STREL_ALT_LONG", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("STREL_ALT_SHORT", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 600F), 0.0F, 0.0F);
        pictManf1 = 0.91F * pictManf1 + 0.09F * fm.EI.engines[0].getManifoldPressure();
        f1 = pictManf1 - 1.0F;
        float f2 = 1.0F;
        if(f1 <= 0.0F)
            f2 = -1F;
        f1 = java.lang.Math.abs(f1);
        mesh.chunkSetAngles("STRELKA_BOOST1", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13F), boostScale), 0.0F, 0.0F);
        pictManf2 = 0.91F * pictManf2 + 0.09F * fm.EI.engines[1].getManifoldPressure();
        f1 = pictManf2 - 1.0F;
        f2 = 1.0F;
        if(f1 <= 0.0F)
            f2 = -1F;
        f1 = java.lang.Math.abs(f1);
        mesh.chunkSetAngles("STRELKA_BOOST2", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13F), boostScale), 0.0F, 0.0F);
        pictManf3 = 0.91F * pictManf3 + 0.09F * fm.EI.engines[2].getManifoldPressure();
        f1 = pictManf2 - 1.0F;
        f2 = 1.0F;
        if(f1 <= 0.0F)
            f2 = -1F;
        f1 = java.lang.Math.abs(f1);
        mesh.chunkSetAngles("STRELKA_BOOST3", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13F), boostScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("STRELKA_FUEL1", cvt(fm.M.fuel, 0.0F, 763F, 0.0F, 301F), 0.0F, 0.0F);
        mesh.chunkSetAngles("STRELKA_FUEL2", cvt(fm.M.fuel, 0.0F, 763F, 0.0F, 301F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM_SHORT1", floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM_LONG1", floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 80F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM_SHORT2", floatindex(cvt(fm.EI.engines[1].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM_LONG2", floatindex(cvt(fm.EI.engines[1].getRPM(), 0.0F, 4000F, 0.0F, 80F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM_SHORT3", floatindex(cvt(fm.EI.engines[2].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM_LONG3", floatindex(cvt(fm.EI.engines[2].getRPM(), 0.0F, 4000F, 0.0F, 80F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TEMP_OIL1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 266F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TEMP_OIL2", cvt(fm.EI.engines[1].tOilOut, 0.0F, 100F, 0.0F, 266F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TEMP_OIL3", cvt(fm.EI.engines[2].tOilOut, 0.0F, 100F, 0.0F, 266F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TEMP_RAD1", floatindex(cvt(fm.EI.engines[0].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TEMP_RAD2", floatindex(cvt(fm.EI.engines[1].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TEMP_RAD3", floatindex(cvt(fm.EI.engines[2].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("STRELK_TURN_UP", cvt(getBall(8D), -8F, 8F, 31F, -31F), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("STREL_TURN_DOWN", cvt(w.z, -0.23562F, 0.23562F, -50F, 50F), 0.0F, 0.0F);
        mesh.chunkSetAngles("STRELK_V_LONG", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 400F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkVisible("STRELK_V_SHORT", false);
        mesh.chunkSetAngles("STRELKA_GOS", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.02355F, -0.02355F);
        mesh.chunkSetLocate("STRELKA_GOR", Cockpit.xyz, Cockpit.ypr);
        if((fm.AS.astateCockpitState & 0x40) == 0)
            mesh.chunkSetAngles("STR_CLIMB", floatindex(cvt(setNew.vspeed, -20.32F, 20.32F, 0.0F, 8F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FlapPos", cvt(fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 125F), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0);
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("HullDamage3", true);
            mesh.chunkVisible("XGlassDamage3", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("HullDamage4", true);
            mesh.chunkVisible("XGlassDamage3", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XGlassDamage4", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XGlassDamage4", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("HullDamage2", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("STRELKA_FUEL2", false);
            mesh.chunkVisible("Z_RPM_SHORT1", false);
            mesh.chunkVisible("Z_RPM_LONG1", false);
            mesh.chunkVisible("Z_RPM_SHORT2", false);
            mesh.chunkVisible("Z_RPM_LONG2", false);
            mesh.chunkVisible("STRELKA_BOOST1", false);
            mesh.chunkVisible("Z_TEMP_OIL1", false);
            mesh.chunkVisible("Z_TEMP_OIL2", false);
            mesh.chunkVisible("Z_TEMP_RAD1", false);
            mesh.chunkVisible("STRELK_V_LONG", false);
            mesh.chunkVisible("STRELK_V_SHORT", false);
            mesh.chunkVisible("STRELKA_GOR", false);
            mesh.chunkVisible("STRELKA_GOS", false);
            mesh.chunkVisible("STREL_ALT_LONG", false);
            mesh.chunkVisible("STREL_ALT_SHORT", false);
            mesh.chunkVisible("STRELK_TURN_UP", false);
            mesh.chunkVisible("Z_FlapPos", false);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XGlassDamage4", true);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XGlassDamage4", true);
        }
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

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictBrake;
    private float pictFlap;
    private float pictGear;
    private float pictSupc;
    private float pictLlit;
    private float pictManf1;
    private float pictManf2;
    private float pictManf3;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 0.1F, 19F, 37.25F, 63.5F, 91.5F, 112F, 135.5F, 159.5F, 186.5F, 
        213F, 238F, 264F, 289F, 314.5F, 339.5F, 359.5F, 360F, 360F, 360F
    };
    private static final float radScale[] = {
        0.0F, 0.1F, 0.2F, 0.3F, 3.5F, 11F, 22F, 37.5F, 58.5F, 82.5F, 
        112.5F, 147F, 187F, 236F, 298.5F
    };
    private static final float boostScale[] = {
        0.0F, 21F, 39F, 56F, 90.5F, 109.5F, 129F, 146.5F, 163F, 179.5F, 
        196F, 212.5F, 231.5F, 250.5F
    };
    private static final float variometerScale[] = {
        -158F, -111F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111F, 158F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 54F, 111F, 171.5F, 229.5F, 282.5F, 334F, 342.5F, 342.5F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
