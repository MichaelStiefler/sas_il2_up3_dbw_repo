// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitJU_87D3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, JU_87, JU_87G1, 
//            Cockpit

public class CockpitJU_87D3 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            fm = com.maddox.il2.ai.World.getPlayerFM();
            if(fm != null)
            {
                if(bNeedSetUp)
                {
                    reflectPlaneMats();
                    bNeedSetUp = false;
                }
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.altimeter = fm.getAltitude();
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.azimuth = fm.Or.getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (499F * setOld.vspeed + fm.getVertSpeed()) / 500F;
                if(buzzerFX != null)
                    if(fm.Loc.z < 750D && ((com.maddox.il2.objects.air.JU_87)fm.actor).diveMechStage == 1)
                        buzzerFX.play();
                    else
                    if(buzzerFX.isPlaying())
                        buzzerFX.stop();
            }
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
        float vspeed;

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
            return (float)(57.295779513082323D * java.lang.Math.atan2(tmpV.y, tmpV.x));
        }
    }

    public CockpitJU_87D3()
    {
        super("3DO/Cockpit/Ju-87D-3/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bNeedSetUp = true;
        bG1 = false;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        setNew.dimPosition = 1.0F;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "LAMPHOOK1");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        hooknamed = new HookNamed(mesh, "LAMPHOOK2");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(126F, 232F, 245F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK2", light2);
        cockpitNightMats = (new java.lang.String[] {
            "87DClocks1", "87DClocks2", "87DClocks3", "87DClocks4", "87DClocks5", "87DPlanks2"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        buzzerFX = aircraft().newSound("models.buzzthru", false);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(fm == null)
            return;
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        if(gun[0] == null && !bNeedSetUp)
            if(bG1)
            {
                gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON01");
                gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON02");
            } else
            {
                gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN01");
                gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN02");
            }
        mesh.chunkSetAngles("zAlt1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, -360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAltCtr1", (((com.maddox.il2.objects.air.JU_87)aircraft()).fDiveRecoveryAlt * 360F) / 6000F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zAltCtr2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 6000F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ReviTinter", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ReviTint", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 40F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zBoost1", cvt(fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zSpeed", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPM1", floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuel1", floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 250F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilTemp1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 120F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuelPrs1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilPrs1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zTurnBank", cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -3F, 3F, 30F, -30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zBall", cvt(getBall(6D), -6F, 6F, -10F, 10F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompass", 0.0F, -interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("zRepeater", -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil1", fm.Or.getTangage(), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil3", fm.Or.getKren(), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil2", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("zVSI", cvt(setNew.vspeed, -15F, 15F, -135F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zwatertemp", cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 100F), 0.0F, 0.0F);
        if(bG1)
        {
            if(gun[0] != null)
                mesh.chunkSetAngles("Z_AmmoCounter1", cvt(gun[0].countBullets(), 0.0F, 50F, 15F, 0.0F), 0.0F, 0.0F);
            if(gun[1] != null)
                mesh.chunkSetAngles("Z_AmmoCounter2", cvt(gun[1].countBullets(), 0.0F, 50F, 15F, 0.0F), 0.0F, 0.0F);
        } else
        {
            if(gun[0] != null)
                mesh.chunkSetAngles("Z_AmmoCounter1", cvt(gun[0].countBullets(), 0.0F, 500F, 15F, 0.0F), 0.0F, 0.0F);
            if(gun[1] != null)
                mesh.chunkSetAngles("Z_AmmoCounter2", cvt(gun[1].countBullets(), 0.0F, 500F, 15F, 0.0F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("zHour", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zMinute", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zColumn1", (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("zPedalL", -fm.CT.getRudder() * 10F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zPedalR", fm.CT.getRudder() * 10F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zThrottle1", interp(setNew.throttle, setOld.throttle, f) * 80F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zPitch1", (fm.CT.getStepControl() < 0.0F ? interp(setNew.throttle, setOld.throttle, f) : fm.CT.getStepControl()) * 45F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zFlaps1", 55F * fm.CT.FlapsControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("zPipka1", 60F * fm.CT.AirBrakeControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("zBrake1", 46.5F * fm.CT.AirBrakeControl, 0.0F, 0.0F);
        resetYPRmodifier();
        if(fm.EI.engines[0].getControlCompressor() > 0)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = 0.155F;
            com.maddox.il2.objects.air.Cockpit.ypr[2] = 22F;
        }
        mesh.chunkSetLocate("zBoostCrank1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.005F, 0.5F);
            light2.light.setEmit(0.005F, 0.5F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("Z_Holes2_D1", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0 || (fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("Z_Holes2_D1", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0 || (fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Revi_D0", false);
            mesh.chunkVisible("Z_ReviTint", false);
            mesh.chunkVisible("Z_ReviTinter", false);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("Revi_D1", true);
            if(bG1)
                mesh.chunkVisible("Z_Holes3G_D1", true);
            else
                mesh.chunkVisible("Z_Holes3D_D1", true);
        }
        if((fm.AS.astateCockpitState & 0x40) == 0);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
    }

    protected void reflectPlaneMats()
    {
        if(com.maddox.il2.engine.Actor.isValid(fm.actor) && (fm.actor instanceof com.maddox.il2.objects.air.JU_87G1))
        {
            mesh.chunkVisible("ARMOR", true);
            bG1 = true;
        }
    }

    protected com.maddox.sound.SoundFX buzzerFX;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null
    };
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private float pictAiler;
    private float pictElev;
    private boolean bNeedSetUp;
    private boolean bG1;
    private static final float speedometerScale[] = {
        0.0F, -12.33333F, 18.5F, 37F, 62.5F, 90F, 110.5F, 134F, 158.5F, 186F, 
        212.5F, 238.5F, 265F, 289.5F, 315F, 339.5F, 346F, 346F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 54F, 111F, 171.5F, 229.5F, 282.5F, 334F, 342.5F, 342.5F
    };
    private static final float fuelScale[] = {
        0.0F, 11.5F, 24.5F, 46.5F, 67F, 88F
    };
    private static final float temperatureScale[] = {
        0.0F, 15.5F, 35F, 50F, 65F, 79F, 92F, 117.5F, 141.5F, 178.5F, 
        222.5F, 261.5F, 329F, 340F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;









}
