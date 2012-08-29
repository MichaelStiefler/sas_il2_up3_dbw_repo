// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitIL_10.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
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
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitIL_10 extends com.maddox.il2.objects.air.CockpitPilot
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


    public CockpitIL_10()
    {
        super("3DO/Cockpit/Il-10/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictFlap = 0.0F;
        pictGear = 0.0F;
        pictManf = 1.0F;
        pictClap = 0.0F;
        pictFuel = 0.0F;
        tClap = -1;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "DGM_gauges_03", "DMG_gauges_01", "DMG_gauges_02", "DMG_gauges_04", "DMG_gauges_05", "gauges_01", "gauges_02", "gauges_03", "gauges_04", "gauges_05", 
            "gauges_06"
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
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN01");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN02");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON02");
        }
        if(bgun[0] == null)
        {
            bgun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_BombSpawn01");
            bgun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_BombSpawn02");
            bgun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb01");
            bgun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb02");
        }
        mesh.chunkSetAngles("Ped_Base", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalL_wire", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("PedalR_wire", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("ruchkaShassi", 0.0F, 0.0F, 42.5F * (pictGear = 0.8F * pictGear + 0.2F * fm.CT.GearControl));
        mesh.chunkSetAngles("ruchkaShitkov", 0.0F, 0.0F, 42.5F * (pictFlap = 0.75F * pictFlap + 0.25F * fm.CT.FlapsControl));
        mesh.chunkSetAngles("ruchkaGaza", 0.0F, 50.5F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        mesh.chunkSetAngles("ruchkaGaza_wire", 0.0F, -55F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        mesh.chunkSetAngles("Z_Mixture", 0.0F, -55F * interp(setNew.prop, setOld.prop, f), 0.0F);
        mesh.chunkSetAngles("Z_Prop", 0.0F, 45.8F * fm.EI.engines[0].getControlMix(), 0.0F);
        mesh.chunkSetAngles("Z_Prop_wire", 0.0F, -52F * fm.EI.engines[0].getControlMix(), 0.0F);
        mesh.chunkSetAngles("os_V", 0.0F, 0.0F, 8F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("richag", 0.0F, 0.0F, 12F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl));
        mesh.chunkSetAngles("tyga_V", 0.0F, -8F * pictElev, 0.0F);
        float f1 = 0.0F;
        if(fm.CT.saveWeaponControl[0] || fm.CT.saveWeaponControl[1] || fm.CT.saveWeaponControl[2] || fm.CT.saveWeaponControl[3])
            tClap = com.maddox.rts.Time.tickCounter() + com.maddox.il2.ai.World.Rnd().nextInt(190, 260);
        if(com.maddox.rts.Time.tickCounter() < tClap)
            f1 = 1.0F;
        mesh.chunkSetAngles("r_one", 0.0F, 0.0F, -145F * (pictClap = 0.85F * pictClap + 0.15F * f1));
        mesh.chunkSetAngles("r_two", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoSwitch", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        pictGunB[0] = 0.8F * pictGunB[0] + 0.2F * (100F * (float)(int)(0.01F * (float)gun[1].countBullets()));
        pictGunB[1] = 0.8F * pictGunB[1] + 0.2F * (100F * (float)(int)(0.01F * (float)gun[0].countBullets()));
        pictGunB[2] = 0.8F * pictGunB[2] + 0.2F * (100F * (float)(int)(0.01F * (float)gun[2].countBullets()));
        pictGunB[3] = 0.8F * pictGunB[3] + 0.2F * (100F * (float)(int)(0.01F * (float)gun[3].countBullets()));
        mesh.chunkSetAngles("Z_AmmoCounter1", 0.0F, 0.18F * pictGunB[0], 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter3", 0.0F, 0.18F * pictGunB[1], 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter5", 0.0F, 0.18F * pictGunB[2], 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter7", 0.0F, 0.18F * pictGunB[3], 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter2", 0.0F, 3.6F * ((float)gun[1].countBullets() - 100F * (float)(int)(0.01F * (float)gun[1].countBullets())), 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter4", 0.0F, 3.6F * ((float)gun[0].countBullets() - 100F * (float)(int)(0.01F * (float)gun[0].countBullets())), 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter6", 0.0F, 3.6F * ((float)gun[2].countBullets() - 100F * (float)(int)(0.01F * (float)gun[2].countBullets())), 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter8", 0.0F, 3.6F * ((float)gun[3].countBullets() - 100F * (float)(int)(0.01F * (float)gun[3].countBullets())), 0.0F);
        mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F);
        mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zRPM1a", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zRPM1b", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 5F, 89F), 0.0F);
        mesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.32F, 0.0F, 1.0F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zGas1a", 0.0F, pictFuel = 0.99F * pictFuel + 0.01F * (cvt(fm.M.fuel * 1.33F, 0.0F, 1152F, 0.0F, 245F) + cvt(fm.Or.getTangage(), -12F, 12F, 21.5F, -21.5F)), 0.0F);
        mesh.chunkSetAngles("zTWater1a", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, 5F, 89F), 0.0F);
        mesh.chunkSetAngles("zTOilIn1a", 0.0F, cvt(fm.EI.engines[0].tOilIn, 0.0F, 125F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zClock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zClock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0241F, -0.0241F);
        mesh.chunkSetLocate("zHorizon1b", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("zHorizon1a", 0.0F, fm.Or.getKren(), 0.0F);
        mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(7D), -7F, 7F, -29F, 29F), 0.0F);
        mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1a", 0.0F, -setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("zAzimuth1b", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ATA1", 0.0F, pictManf = 0.9F * pictManf + 0.1F * cvt(fm.EI.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 0.0F, 337F), 0.0F);
        mesh.chunkSetAngles("Z_Course", 0.0F, cvt(setNew.waypointAzimuth.getDeg(f), -90F, 90F, -20.5F, 20.5F), 0.0F);
        mesh.chunkSetAngles("Z_Water", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil", 0.0F, 68F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        mesh.chunkSetAngles("Z_Air1", 0.0F, 42F, 0.0F);
        mesh.chunkSetAngles("Z_Air2", 0.0F, fm.CT.bHasFlapsControl ? 50.5F : 0.0F, 0.0F);
        mesh.chunkVisible("Z_Red1", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
        mesh.chunkVisible("Z_Red2", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
        mesh.chunkVisible("Z_Red3", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        mesh.chunkVisible("Z_Red4", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
        mesh.chunkVisible("Z_Green1", fm.CT.getGear() > 0.99F);
        mesh.chunkVisible("Z_Red5", gun[1].countBullets() < 75);
        mesh.chunkVisible("Z_Red6", gun[0].countBullets() < 30);
        mesh.chunkVisible("Z_Red7", gun[2].countBullets() < 75);
        mesh.chunkVisible("Z_Red8", gun[3].countBullets() < 30);
        mesh.chunkVisible("Z_Red9", gun[0].countBullets() != 0 || gun[1].countBullets() != 0 || gun[2].countBullets() != 0 || gun[3].countBullets() != 0);
        mesh.chunkVisible("Z_Red10", fm.CT.GearControl > 0.5F);
        mesh.chunkVisible("Z_Green2", fm.CT.getFlap() > 0.9F);
        mesh.chunkVisible("Z_White1", !bgun[0].haveBullets());
        mesh.chunkVisible("Z_White2", !bgun[1].haveBullets());
        mesh.chunkVisible("Z_White4", !bgun[2].haveBullets());
        mesh.chunkVisible("Z_White3", !bgun[3].haveBullets());
        mesh.chunkVisible("Z_White5", fm.CT.getBayDoor() > 0.5F);
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
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("Z_Holes6_D1", true);
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("Z_Holes2_D1", true);
            mesh.chunkVisible("Z_Holes6_D1", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Z_Holes6_D1", true);
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_d1", true);
            mesh.chunkVisible("zAzimuth1a", false);
            mesh.chunkVisible("zAzimuth1b", false);
            mesh.chunkVisible("zHorizon1a", false);
            mesh.chunkVisible("zHorizon1b", false);
            mesh.chunkVisible("zSpeed1a", false);
            mesh.chunkVisible("zVariometer1a", false);
            mesh.chunkVisible("zAlt1a", false);
            mesh.chunkVisible("zAlt1b", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("Z_Holes3_D1", true);
            mesh.chunkVisible("Z_Holes6_D1", true);
            mesh.chunkVisible("Z_Holes7_D1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("Z_Holes4_D1", true);
            mesh.chunkVisible("Z_Holes6_D1", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("Z_Holes5_D1", true);
            mesh.chunkVisible("Z_Holes6_D1", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("Z_Holes4_D1", true);
            mesh.chunkVisible("Z_Holes6_D1", true);
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_d2", true);
            mesh.chunkVisible("zTOilOut1a", false);
            mesh.chunkVisible("zRPM1a", false);
            mesh.chunkVisible("zRPM1b", false);
            mesh.chunkVisible("zTWater1a", false);
            mesh.chunkVisible("Z_Water", false);
            mesh.chunkVisible("Z_ATA1", false);
            mesh.chunkVisible("Z_Air1", false);
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
    private float pictAiler;
    private float pictElev;
    private float pictFlap;
    private float pictGear;
    private float pictManf;
    private float pictClap;
    private float pictFuel;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null, null, null
    };
    private com.maddox.il2.ai.BulletEmitter bgun[] = {
        null, null, null, null
    };
    private float pictGunB[] = {
        0.0F, 0.0F, 0.0F, 0.0F
    };
    private int tClap;
    private static final float speedometerScale[] = {
        0.0F, 4F, 14F, 51.5F, 90.5F, 129.5F, 167.2F, 196F, 222.5F, 251.5F, 
        279.5F, 308F, 338.5F, 370.5F, 400.5F, 421F, 443F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
