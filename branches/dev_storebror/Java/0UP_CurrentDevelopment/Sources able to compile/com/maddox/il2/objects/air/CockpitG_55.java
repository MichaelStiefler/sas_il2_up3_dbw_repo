package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

public class CockpitG_55 extends CockpitPilot
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
                setNew.throttle = 0.85F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl * 0.15F;
                setNew.prop = 0.85F * setOld.prop + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.getStepControl() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.azimut());
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitG_55()
    {
        super("3DO/Cockpit/MC-205/hier.him", "bf109");
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
        super.cockpitNightMats = (new java.lang.String[] {
            "mat2_tr", "strum1dmg", "strum2dmg", "strum4dmg", "strum1", "strum2", "strum4", "strumsxdmg", "strumsx", "ruddersystem"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(super.acoustics != null)
            super.acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN01");
            gun[1] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN02");
            gun[2] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN03");
            gun[3] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN04");
            if(gun[2].countBullets() <= 0)
            {
                gun[2] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON01");
                gun[3] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON02");
            }
        }
        super.mesh.chunkVisible("Z_Z_RETICLE", !super.cockpitDimControl);
        super.mesh.chunkVisible("Z_Z_RETICLE2", super.cockpitDimControl);
        super.mesh.chunkSetAngles("Z_Column", 16F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F, 8F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl));
        super.mesh.chunkSetAngles("Pedals", 15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throttle", -49.54F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_MagnetoSwitch", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 14F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Mix", 0.0F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PropPitch1", -61.5F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PropPitch2", 0.0F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Flap1", 45F * (pictFlap = 0.75F * pictFlap + 0.25F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Gear1", -32F * (pictGear = 0.8F * pictGear + 0.2F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Trim1", -76.5F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl(), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.06325F);
        super.mesh.chunkSetLocate("Z_OilRad1", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.01625F);
        super.mesh.chunkSetLocate("Z_OilRad2", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_OilRad3", 0.0F, 0.0F, 0.0F);
        if(gun[0].haveBullets())
        {
            super.mesh.chunkSetAngles("Z_AmmoCounter1", 0.36F * (float)gun[0].countBullets(), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("Z_AmmoCounter2", 3.6F * (float)gun[0].countBullets(), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("Z_AmmoCounter3", 36F * (float)gun[0].countBullets(), 0.0F, 0.0F);
        }
        if(gun[1].haveBullets())
        {
            super.mesh.chunkSetAngles("Z_AmmoCounter4", 0.36F * (float)gun[1].countBullets(), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("Z_AmmoCounter5", 3.6F * (float)gun[1].countBullets(), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("Z_AmmoCounter6", 36F * (float)gun[1].countBullets(), 0.0F, 0.0F);
        }
        if(gun[2].haveBullets())
            super.mesh.chunkSetAngles("Z_AmmoCounter7", cvt(gun[2].countBullets(), 0.0F, 500F, 7F, 358F), 0.0F, 0.0F);
        if(gun[3].haveBullets())
            super.mesh.chunkSetAngles("Z_AmmoCounter8", cvt(gun[3].countBullets(), 0.0F, 500F, 7F, 358F), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.06055F);
        super.mesh.chunkSetLocate("Z_Cooling", Cockpit.xyz, Cockpit.ypr);
        resetYPRmodifier();
        Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap(), 0.0F, 1.0F, 0.0F, 0.05475F);
        super.mesh.chunkSetLocate("Z_FlapPos", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 100F, 700F, 0.0F, 12F), speedometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Climb1", cvt(setNew.vspeed, -25F, 25F, -180F, 180F), 0.0F, 0.0F);
        float f1 = setNew.azimuth.getDeg(f) - 90F;
        if(f1 < 0.0F)
            f1 += 360F;
        super.mesh.chunkSetAngles("Z_Compass1", f1, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 322.5F), 0.0F, 0.0F);
        w.set(super.fm.getW());
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.transform(w);
        super.mesh.chunkSetAngles("Turn1", cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 21F, -21F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Turn2", 0.0F, cvt(getBall(8D), -8F, 8F, -11F, 11F), 0.0F);
        super.mesh.chunkSetAngles("Z_ManfoldPress", -(pictManf = 0.9F * pictManf + 0.1F * cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.5F, 2.0F, 0.0F, -300F)), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_EngTemp1", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 30F, 130F, 0.0F, 10F), waterTempScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_OilTemp1", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 30F, 150F, 0.0F, 4F), oilTempScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 184F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_OilPress2", cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 300F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_AirPress1", -135F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_AirPress2", -135F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelPress1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, -300F), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 252F, 0.0F, 0.3661F);
        super.mesh.chunkSetLocate("Z_FuelQuantity1", Cockpit.xyz, Cockpit.ypr);
        resetYPRmodifier();
        Cockpit.xyz[0] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 0.0135F, -0.0135F);
        super.mesh.chunkSetLocate("Z_Horison1c", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_Horison1a", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Horison1b", 0.0F, 0.0F, 0.0F);
        super.mesh.chunkVisible("XLampGunL", gun[0].countBullets() < 90);
        super.mesh.chunkVisible("XLampGunR", gun[1].countBullets() < 90);
        super.mesh.chunkVisible("XLampCanL", gun[2].countBullets() < 90);
        super.mesh.chunkVisible("XLampCanR", gun[3].countBullets() < 90);
        super.mesh.chunkVisible("XLampSPIA", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() < 1200F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0)
        {
            super.mesh.chunkVisible("XLampGearUpR", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
            super.mesh.chunkVisible("XLampGearUpL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
            super.mesh.chunkVisible("XLampGearDownR", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
            super.mesh.chunkVisible("XLampGearDownL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
            super.mesh.chunkVisible("XLampGearDownC", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F);
        } else
        {
            super.mesh.chunkVisible("XLampGearUpR", false);
            super.mesh.chunkVisible("XLampGearUpL", false);
            super.mesh.chunkVisible("XLampGearDownR", false);
            super.mesh.chunkVisible("XLampGearDownL", false);
            super.mesh.chunkVisible("XLampGearDownC", false);
        }
    }

    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
            return 0.0F;
        waypoint.getP(tmpP);
        tmpV.sub(tmpP, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc);
        float f;
        for(f = (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x)); f <= -180F; f += 180F);
        for(; f > 180F; f -= 180F);
        return f;
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
            super.mesh.chunkVisible("Z_OilSplats_D1", true);
        retoggleLight();
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

    private void retoggleLight()
    {
        if(super.cockpitLightControl)
        {
            setNightMats(false);
            setNightMats(true);
        } else
        {
            setNightMats(true);
            setNightMats(false);
        }
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    private boolean bNeedSetUp;
    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictMix;
    private float pictFlap;
    private float pictGear;
    private float pictManf;
    private float pictFuel;
    private com.maddox.il2.ai.BulletEmitter gun[] = {
        null, null, null, null
    };
    private static final float speedometerScale[] = {
        0.0F, 38.5F, 77F, 100.5F, 125F, 147F, 169.5F, 193.5F, 216.5F, 240.5F, 
        265F, 287.5F, 303.5F
    };
    private static final float waterTempScale[] = {
        0.0F, 20.5F, 37F, 52F, 73.5F, 95.5F, 120F, 150F, 192F, 245F, 
        302F
    };
    private static final float oilTempScale[] = {
        0.0F, 33F, 80F, 153F, 301.5F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
