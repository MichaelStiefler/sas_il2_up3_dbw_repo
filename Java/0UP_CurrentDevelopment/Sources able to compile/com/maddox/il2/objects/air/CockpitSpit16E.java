package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Time;

public class CockpitSpit16E extends CockpitPilot
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
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.throttle = 0.92F * setOld.throttle + 0.08F * ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl;
                setNew.prop = 0.92F * setOld.prop + 0.08F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlProp();
                setNew.mix = 0.92F * setOld.mix + 0.08F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix();
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getKren()) < 30F)
                    setNew.azimuth = 0.97F * setOld.azimuth + 0.03F * -((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = 0.91F * setOld.waypointAzimuth + 0.09F * (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F);
                setNew.vspeed = 0.99F * setOld.vspeed + 0.01F * fm.getVertSpeed();
                float f = ((SPITFIRE16E)aircraft()).k14Distance;
                setNew.k14w = (5F * CockpitSpit16E.k14TargetWingspanScale[((SPITFIRE16E)aircraft()).k14WingspanType]) / f;
                setNew.k14w = 0.9F * setOld.k14w + 0.1F * setNew.k14w;
                setNew.k14wingspan = 0.9F * setOld.k14wingspan + 0.1F * CockpitSpit16E.k14TargetMarkScale[((SPITFIRE16E)aircraft()).k14WingspanType];
                setNew.k14mode = 0.8F * setOld.k14mode + 0.2F * (float)((SPITFIRE16E)aircraft()).k14Mode;
                com.maddox.JGP.Vector3d vector3d = ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM.getW();
                double d = 0.00125D * (double)f;
                float f1 = (float)java.lang.Math.toDegrees(d * ((com.maddox.JGP.Tuple3d) (vector3d)).z);
                float f2 = -(float)java.lang.Math.toDegrees(d * ((com.maddox.JGP.Tuple3d) (vector3d)).y);
                float f3 = floatindex((f - 200F) * 0.04F, CockpitSpit16E.k14BulletDrop) - CockpitSpit16E.k14BulletDrop[0];
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

        Variables(Variables variables)
        {
            this();
        }
    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public CockpitSpit16E()
    {
        super("3DO/Cockpit/SpitfireMkXVI/hier.him", "bf109");
        setOld = new Variables(null);
        setNew = new Variables(null);
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictBrake = 0.0F;
        pictFlap = 0.0F;
        pictGear = 0.0F;
        pictManf = 1.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        super.cockpitNightMats = (new java.lang.String[] {
            "COMPASS", "BORT2", "prib_five", "prib_five_damage", "prib_one", "prib_one_damage", "prib_three", "prib_three_damage", "prib_two", "prib_two_damage", 
            "text13", "text15"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void removeCanopy()
    {
        super.mesh.chunkVisible("Canopy", false);
    }

    public void reflectWorldToInstruments(float f)
    {
        int i = ((SPITFIRE16E)aircraft()).k14Mode;
        boolean flag = i < 2;
        super.mesh.chunkVisible("Z_Z_RETICLE", flag);
        flag = i > 0;
        super.mesh.chunkVisible("Z_Z_RETICLE1", flag);
        super.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, setNew.k14x, setNew.k14y);
        resetYPRmodifier();
        Cockpit.xyz[0] = setNew.k14w;
        for(int j = 1; j < 7; j++)
        {
            super.mesh.chunkVisible("Z_Z_AIMMARK" + j, flag);
            super.mesh.chunkSetLocate("Z_Z_AIMMARK" + j, Cockpit.xyz, Cockpit.ypr);
        }

        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.55F);
        super.mesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkVisible("XLampGearUpL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F);
        super.mesh.chunkVisible("XLampGearDownL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F);
        super.mesh.chunkVisible("XLampFuel", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 0.25F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.maxFuel);
        super.mesh.chunkVisible("XLampboost", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlCompressor() > 0);
        super.mesh.chunkSetAngles("Z_Columnbase", 0.0F, 8F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl), 0.0F);
        super.mesh.chunkSetAngles("Z_Column", -30F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Target1", setNew.k14wingspan, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang01a", -5F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang01b", -9F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang01c", -12F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang02a", -5F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang02b", -7.5F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang02c", -15F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang03a", -5F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang03b", -8.5F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Shlang03c", -18.5F * pictAiler, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Stick_Shtok01", 0.0F, 0.0F, 8F * pictElev);
        super.mesh.chunkSetAngles("Z_ColumnSwitch", -18F * (pictBrake = 0.89F * pictBrake + 0.11F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.BrakeControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throtle1", -64.5454F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_BasePedal", 20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[2] = 0.0578F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder();
        super.mesh.chunkSetLocate("Z_LeftPedal", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[2] = -0.0578F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder();
        super.mesh.chunkSetLocate("Z_RightPedal", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_Gear1", -160F + 160F * (pictGear = 0.89F * pictGear + 0.11F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Flaps1", 160F * (pictFlap = 0.89F * pictFlap + 0.11F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Trim1", 1000F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Trim2", 1000F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimRudderControl(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Prop1", -90F * setNew.prop, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Mixture1", -60F * setNew.mix, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("COMPASS_M", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("SHKALA_DIRECTOR", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STREL_ALT_LONG", 0.0F, 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -10800F));
        super.mesh.chunkSetAngles("STREL_ALT_SHORT", 0.0F, 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -1080F));
        super.mesh.chunkSetAngles("STREL_ALT_SHRT1", 0.0F, 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -108F));
        super.mesh.chunkSetAngles("STRELKA_BOOST", 0.0F, 0.0F, -cvt(pictManf = 0.91F * pictManf + 0.09F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.5173668F, 2.72369F, -70F, 250F));
        super.mesh.chunkSetAngles("STRELKA_FUEL", 0.0F, 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 378.54F, 0.0F, 68F));
        super.mesh.chunkSetAngles("STRELKA_RPM", 0.0F, 0.0F, -floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 1000F, 5000F, 2.0F, 10F), rpmScale));
        super.mesh.chunkSetAngles("STRELK_TEMP_OIL", 0.0F, 0.0F, -cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 50F, 100F, 0.0F, 271F));
        super.mesh.chunkSetAngles("STRELK_TEMP_RAD", 0.0F, 0.0F, -floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale));
        super.mesh.chunkSetAngles("STR_OIL_LB", 0.0F, cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, -37F), 0.0F);
        w.set(super.fm.getW());
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.transform(w);
        super.mesh.chunkSetAngles("STREL_TURN_DOWN", 0.0F, 0.0F, -cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, -48F, 48F));
        super.mesh.chunkSetAngles("STRELK_TURN_UP", 0.0F, 0.0F, -cvt(getBall(8D), -8F, 8F, 35F, -35F));
        super.mesh.chunkVisible("STRELK_V_SHORT", false);
        super.mesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F, -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeed()), 0.0F, 223.52F, 0.0F, 25F), speedometerScale));
        super.mesh.chunkSetAngles("STRELKA_VY", 0.0F, 0.0F, -floatindex(cvt(setNew.vspeed, -20.32F, 20.32F, 0.0F, 8F), variometerScale));
        super.mesh.chunkSetAngles("STRELKA_GOR", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren());
        resetYPRmodifier();
        Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 0.032F, -0.032F);
        super.mesh.chunkSetLocate("STRELKA_GOS", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("STRELKA_HOUR", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        super.mesh.chunkSetAngles("STRELKA_MINUTE", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        super.mesh.chunkSetAngles("STRELKA_SECUND", 0.0F, cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        super.mesh.chunkSetAngles("STR_CLIMB", 0.0F, 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.trimElevator, -0.5F, 0.5F, -35.23F, 35.23F));
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
            super.mesh.chunkVisible("Z_OilSplats_D1", true);
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        super.mesh.materialReplace("Gloss1D0o", mat);
    }

    public void toggleLight()
    {
        super.cockpitLightControl = !super.cockpitLightControl;
        if(super.cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
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
    private float pictManf;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 7.5F, 17.5F, 37F, 63F, 88.5F, 114.5F, 143F, 171.5F, 202.5F, 
        228.5F, 255.5F, 282F, 309F, 336.5F, 366.5F, 394F, 421F, 448.5F, 474.5F, 
        500.5F, 530F, 557.5F, 584F, 609F, 629F
    };
    private static final float radScale[] = {
        0.0F, 3F, 7F, 13.5F, 21.5F, 27F, 34.5F, 50.5F, 71F, 94F, 
        125F, 161F, 202.5F, 253F, 315.5F
    };
    private static final float rpmScale[] = {
        0.0F, 0.0F, 0.0F, 22F, 58F, 103.5F, 152.5F, 193.5F, 245F, 281.5F, 
        311.5F
    };
    private static final float variometerScale[] = {
        -158F, -111F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111F, 158F
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
