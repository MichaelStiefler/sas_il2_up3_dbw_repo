// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitMC_205.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
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
//            CockpitPilot, Aircraft, Cockpit

public class CockpitMC_205 extends com.maddox.il2.objects.air.CockpitPilot
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

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    public CockpitMC_205()
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
        cockpitNightMats = (new java.lang.String[] {
            "mat2_tr", "strum1dmg", "strum2dmg", "strum4dmg", "strum1", "strum2", "strum4", "strumsxdmg", "strumsx", "ruddersystem"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN02");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN03");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN04");
            if(gun[2].countBullets() <= 0)
            {
                gun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON01");
                gun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON02");
            }
        }
        mesh.chunkVisible("Z_Z_RETICLE", !cockpitDimControl);
        mesh.chunkVisible("Z_Z_RETICLE2", cockpitDimControl);
        mesh.chunkSetAngles("Z_Column", 16F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 8F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("Pedals", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle", -49.54F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoSwitch", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 14F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mix", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PropPitch1", -61.5F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PropPitch2", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flap1", 45F * (pictFlap = 0.75F * pictFlap + 0.25F * fm.CT.FlapsControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gear1", -32F * (pictGear = 0.8F * pictGear + 0.2F * fm.CT.GearControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim1", -76.5F * fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.06325F);
        mesh.chunkSetLocate("Z_OilRad1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.01625F);
        mesh.chunkSetLocate("Z_OilRad2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_OilRad3", 0.0F, 0.0F, 0.0F);
        if(gun[0].haveBullets())
        {
            mesh.chunkSetAngles("Z_AmmoCounter1", 0.36F * (float)gun[0].countBullets(), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_AmmoCounter2", 3.6F * (float)gun[0].countBullets(), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_AmmoCounter3", 36F * (float)gun[0].countBullets(), 0.0F, 0.0F);
        }
        if(gun[1].haveBullets())
        {
            mesh.chunkSetAngles("Z_AmmoCounter4", 0.36F * (float)gun[1].countBullets(), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_AmmoCounter5", 3.6F * (float)gun[1].countBullets(), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_AmmoCounter6", 36F * (float)gun[1].countBullets(), 0.0F, 0.0F);
        }
        if(gun[2].haveBullets())
            mesh.chunkSetAngles("Z_AmmoCounter7", cvt(gun[2].countBullets(), 0.0F, 500F, 7F, 358F), 0.0F, 0.0F);
        if(gun[3].haveBullets())
            mesh.chunkSetAngles("Z_AmmoCounter8", cvt(gun[3].countBullets(), 0.0F, 500F, 7F, 358F), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.06055F);
        mesh.chunkSetLocate("Z_Cooling", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 0.05475F);
        mesh.chunkSetLocate("Z_FlapPos", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 100F, 700F, 0.0F, 12F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", cvt(setNew.vspeed, -25F, 25F, -180F, 180F), 0.0F, 0.0F);
        float f1 = setNew.azimuth.getDeg(f) - 90F;
        if(f1 < 0.0F)
            f1 += 360F;
        mesh.chunkSetAngles("Z_Compass1", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 322.5F), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Turn1", cvt(w.z, -0.23562F, 0.23562F, 21F, -21F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Turn2", 0.0F, cvt(getBall(8D), -8F, 8F, -11F, 11F), 0.0F);
        mesh.chunkSetAngles("Z_ManfoldPress", -(pictManf = 0.9F * pictManf + 0.1F * cvt(fm.EI.engines[0].getManifoldPressure(), 0.5F, 2.0F, 0.0F, -300F)), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_EngTemp1", floatindex(cvt(fm.EI.engines[0].tWaterOut, 30F, 130F, 0.0F, 10F), waterTempScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilTemp1", floatindex(cvt(fm.EI.engines[0].tOilOut, 30F, 150F, 0.0F, 4F), oilTempScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 184F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPress2", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_AirPress1", -135F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_AirPress2", -135F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPress1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.32F, 0.0F, 1.0F, 0.0F, -300F), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.M.fuel, 0.0F, 252F, 0.0F, 0.3661F);
        mesh.chunkSetLocate("Z_FuelQuantity1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0135F, -0.0135F);
        mesh.chunkSetLocate("Z_Horison1c", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Horison1a", fm.Or.getKren(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Horison1b", 0.0F, 0.0F, 0.0F);
        mesh.chunkVisible("XLampGunL", gun[0].countBullets() < 90);
        mesh.chunkVisible("XLampGunR", gun[1].countBullets() < 90);
        mesh.chunkVisible("XLampCanL", gun[2].countBullets() < 90);
        mesh.chunkVisible("XLampCanR", gun[3].countBullets() < 90);
        mesh.chunkVisible("XLampSPIA", fm.EI.engines[0].getRPM() < 1200F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
        {
            mesh.chunkVisible("XLampGearUpR", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
            mesh.chunkVisible("XLampGearUpL", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
            mesh.chunkVisible("XLampGearDownR", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
            mesh.chunkVisible("XLampGearDownL", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
            mesh.chunkVisible("XLampGearDownC", fm.CT.getGear() > 0.99F);
        } else
        {
            mesh.chunkVisible("XLampGearUpR", false);
            mesh.chunkVisible("XLampGearUpL", false);
            mesh.chunkVisible("XLampGearDownR", false);
            mesh.chunkVisible("XLampGearDownL", false);
            mesh.chunkVisible("XLampGearDownC", false);
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

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
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
