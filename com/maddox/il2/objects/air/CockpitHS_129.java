// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHS_129.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.il2.objects.weapons.MGunBK374Hs129;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, HS_129, HS_129B2, Aircraft, 
//            AircraftLH, Cockpit

public class CockpitHS_129 extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float throttle1;
        float throttle2;
        float flaps;
        float dimPosition;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float turn;
        float mix1;
        float mix2;
        float vspeed;
        float beaconDirection;
        float beaconRange;

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
            if(ac != null && ac.bChangedPit)
            {
                reflectPlaneToModel();
                ac.bChangedPit = false;
            }
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = fm.getAltitude();
            if(!cockpitDimControl)
            {
                if(setNew.dimPosition > 0.0F)
                    setNew.dimPosition = setNew.dimPosition - 0.05F;
            } else
            if(setNew.dimPosition < 1.0F)
                setNew.dimPosition = setNew.dimPosition + 0.05F;
            setNew.throttle1 = 0.91F * setOld.throttle1 + 0.09F * fm.EI.engines[0].getControlThrottle();
            setNew.throttle2 = 0.91F * setOld.throttle2 + 0.09F * fm.EI.engines[1].getControlThrottle();
            setNew.mix1 = 0.88F * setOld.mix1 + 0.12F * fm.EI.engines[0].getControlMix();
            setNew.mix2 = 0.88F * setOld.mix2 + 0.12F * fm.EI.engines[1].getControlMix();
            pictManifold1 = 0.75F * pictManifold1 + 0.25F * fm.EI.engines[0].getManifoldPressure();
            pictManifold2 = 0.75F * pictManifold2 + 0.25F * fm.EI.engines[1].getManifoldPressure();
            if(gearsLever != 0.0F && gears == fm.CT.getGear())
            {
                gearsLever = gearsLever * 0.8F;
                if(java.lang.Math.abs(gearsLever) < 0.1F)
                    gearsLever = 0.0F;
            } else
            if(gears < fm.CT.getGear())
            {
                gears = fm.CT.getGear();
                gearsLever = gearsLever + 2.0F;
                if(gearsLever > 14F)
                    gearsLever = 14F;
            } else
            if(gears > fm.CT.getGear())
            {
                gears = fm.CT.getGear();
                gearsLever = gearsLever - 2.0F;
                if(gearsLever < -14F)
                    gearsLever = -14F;
            }
            float f = waypointAzimuth();
            if(useRealisticNavigationInstruments())
            {
                setNew.waypointAzimuth.setDeg(f);
                setOld.waypointAzimuth.setDeg(f);
            } else
            {
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f - setOld.azimuth.getDeg(1.0F));
            }
            setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
            w.set(fm.getW());
            fm.Or.transform(w);
            setNew.turn = (12F * setOld.turn + w.z) / 13F;
            setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            setNew.beaconDirection = (10F * setOld.beaconDirection + getBeaconDirection()) / 11F;
            setNew.beaconRange = (10F * setOld.beaconRange + getBeaconRange()) / 11F;
            f = fm.CT.FlapsControl;
            float f1 = 0.0F;
            if(f < 0.2F)
                f1 = 1.5F;
            else
            if(f < 0.3333333F)
                f1 = 2.0F;
            else
                f1 = 1.0F;
            setNew.flaps = 0.91F * setOld.flaps + 0.09F * f * f1;
            if(MG17s[0] != null)
            {
                if(MG17s[0].countBullets() == 0 || oldbullets1 != MG17s[0].countBullets())
                {
                    oldbullets1 = MG17s[0].countBullets();
                    gunLight1 = true;
                } else
                {
                    gunLight1 = false;
                }
                if(MG17s[1].countBullets() == 0 || oldbullets2 != MG17s[1].countBullets())
                {
                    oldbullets2 = MG17s[1].countBullets();
                    gunLight2 = true;
                } else
                {
                    gunLight2 = false;
                }
                if(MG17s[2].countBullets() == 0 || oldbullets3 != MG17s[2].countBullets())
                {
                    oldbullets3 = MG17s[2].countBullets();
                    gunLight3 = true;
                } else
                {
                    gunLight3 = false;
                }
                if(MG17s[3].countBullets() == 0 || oldbullets4 != MG17s[3].countBullets())
                {
                    oldbullets4 = MG17s[3].countBullets();
                    gunLight4 = true;
                } else
                {
                    gunLight4 = false;
                }
            }
            if(bombs[0] != null)
                mesh.chunkVisible("ZFlare_Bombs_01", bombs[0].haveBullets());
            if(bombs[1] != null)
                mesh.chunkVisible("ZFlare_Bombs_02", bombs[1].haveBullets());
            if(bombs[2] != null)
                mesh.chunkVisible("ZFlare_Bombs_04", bombs[2].haveBullets());
            if(bombs[3] != null)
                mesh.chunkVisible("ZFlare_Bombs_03", bombs[3].haveBullets());
            if(bombs[4] != null)
                mesh.chunkVisible("ZFlare_Bombs_06", bombs[4].haveBullets());
            if(bombs[5] != null)
                mesh.chunkVisible("ZFlare_Bombs_05", bombs[5].haveBullets());
            if(fm.CT.getStepControlAuto(0))
            {
                if(engine1PitchMode < 1.0F)
                    engine1PitchMode = engine1PitchMode + 0.5F;
            } else
            if(engine1PitchMode > -1F)
                engine1PitchMode = engine1PitchMode - 0.5F;
            if(fm.CT.getStepControlAuto(1))
            {
                if(engine2PitchMode < 1.0F)
                    engine2PitchMode = engine2PitchMode + 0.5F;
            } else
            if(engine2PitchMode > -1F)
                engine2PitchMode = engine2PitchMode - 0.5F;
            magneto1 = 0.5F * magneto1 + 0.5F * (float)fm.EI.engines[0].getControlMagnetos();
            magneto2 = 0.5F * magneto2 + 0.5F * (float)fm.EI.engines[1].getControlMagnetos();
            engine1PropPitch = 0.5F * engine1PropPitch + 0.5F * (float)fm.EI.engines[0].getControlPropDelta();
            engine2PropPitch = 0.5F * engine2PropPitch + 0.5F * (float)fm.EI.engines[1].getControlPropDelta();
            if(fm.CT.trimElevator < currentElevatorTrim)
                etDelta = -1;
            else
            if(fm.CT.trimElevator > currentElevatorTrim)
                etDelta = 1;
            if(elevatorTrim > 1.0F || elevatorTrim < -1F)
                etDelta = 0;
            if(etDelta != 0)
                elevatorTrim = elevatorTrim + (float)etDelta * 0.2F;
            else
            if(currentElevatorTrim == fm.CT.trimElevator)
                elevatorTrim = elevatorTrim * 0.5F;
            currentElevatorTrim = fm.CT.trimElevator;
            if(fm.CT.trimRudder < currentRudderTrim)
                rtDelta = 1;
            else
            if(fm.CT.trimRudder > currentRudderTrim)
                rtDelta = -1;
            if(rudderTrim > 1.0F || rudderTrim < -1F)
                rtDelta = 0;
            if(rtDelta != 0)
                rudderTrim = rudderTrim + (float)rtDelta * 0.2F;
            else
            if(currentRudderTrim == fm.CT.trimRudder)
                rudderTrim = rudderTrim * 0.5F;
            currentRudderTrim = fm.CT.trimRudder;
            buzzerFX(fm.CT.getGear() < 0.999999F && fm.CT.getFlap() > 0.0F);
            return true;
        }

        Interpolater()
        {
        }
    }


    protected float waypointAzimuth()
    {
        return waypointAzimuthInvertMinus(30F);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        if(hiermesh.isChunkVisible("engine1_d2") || hiermesh.isChunkVisible("WingLIn_D2"))
        {
            mesh.chunkVisible("gaugesEx_01", false);
            mesh.chunkVisible("D_gaugesEx_01", true);
            mesh.chunkVisible("Z_need_RPM_01", false);
            mesh.chunkVisible("ZFlare_Fuel_01", false);
            mesh.chunkVisible("Z_need_temp_01", false);
            mesh.chunkVisible("Z_fuel_01", false);
            mesh.chunkVisible("Z_need_oilpress_b_01", false);
            mesh.chunkVisible("Z_need_oilpress_a_01", false);
            mesh.chunkVisible("Z_need_oilsystem", false);
        }
        if(hiermesh.isChunkVisible("engine2_d2") || hiermesh.isChunkVisible("WingRIn_D2"))
        {
            mesh.chunkVisible("gaugesEx_02", false);
            mesh.chunkVisible("D_gaugesEx_02", true);
            mesh.chunkVisible("Z_need_RPM_02", false);
            mesh.chunkVisible("ZFlare_Fuel_02", false);
            mesh.chunkVisible("Z_need_temp_02", false);
            mesh.chunkVisible("Z_fuel_02", false);
            mesh.chunkVisible("Z_need_oilpress_b_02", false);
            mesh.chunkVisible("Z_need_oilpress_a_02", false);
        }
        if(!hiermesh.isChunkVisible("WingLIn_D0") && !hiermesh.isChunkVisible("WingLIn_D1") && !hiermesh.isChunkVisible("WingLIn_D2"))
        {
            mesh.chunkVisible("gaugesEx_01", false);
            mesh.chunkVisible("D_gaugesEx_01", false);
            mesh.chunkVisible("Z_need_RPM_01", false);
            mesh.chunkVisible("ZFlare_Fuel_01", false);
            mesh.chunkVisible("Z_need_temp_01", false);
            mesh.chunkVisible("Z_fuel_01", false);
            mesh.chunkVisible("Z_need_oilpress_b_01", false);
            mesh.chunkVisible("Z_need_oilpress_a_01", false);
            mesh.chunkVisible("Z_need_oilsystem", false);
        }
        if(!hiermesh.isChunkVisible("WingRIn_D0") && !hiermesh.isChunkVisible("WingRIn_D1") && !hiermesh.isChunkVisible("WingRIn_D2"))
        {
            mesh.chunkVisible("gaugesEx_02", false);
            mesh.chunkVisible("D_gaugesEx_02", false);
            mesh.chunkVisible("Z_need_RPM_02", false);
            mesh.chunkVisible("ZFlare_Fuel_02", false);
            mesh.chunkVisible("Z_need_temp_02", false);
            mesh.chunkVisible("Z_fuel_02", false);
            mesh.chunkVisible("Z_need_oilpress_b_02", false);
            mesh.chunkVisible("Z_need_oilpress_a_02", false);
        }
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    public CockpitHS_129(java.lang.String s)
    {
        super(s, "bf109");
        ammoCountGun0 = 0.0F;
        ammoCountGun1 = 0.0F;
        ammoCountGun2 = 0.0F;
        ammoCountGun3 = 0.0F;
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictManifold1 = 0.0F;
        pictManifold2 = 0.0F;
        bNeedSetUp = true;
        oilPressure1 = 0.0F;
        rpmGeneratedPressure1 = 0.0F;
        oilPressure2 = 0.0F;
        rpmGeneratedPressure2 = 0.0F;
        ac = null;
        gearsLever = 0.0F;
        gears = 0.0F;
        oldbullets1 = -1;
        oldbullets2 = -1;
        oldbullets3 = -1;
        oldbullets4 = -1;
        gunLight1 = false;
        gunLight2 = false;
        gunLight3 = false;
        gunLight4 = false;
        cannonLight = false;
        shotTime = -1L;
        reloadTimeNeeded = 0L;
        engine1PropPitch = 0.0F;
        engine2PropPitch = 0.0F;
        engine1PitchMode = 0.0F;
        engine2PitchMode = 0.0F;
        magneto1 = 0.0F;
        magneto2 = 0.0F;
        elevatorTrim = 0.0F;
        currentElevatorTrim = 0.0F;
        etDelta = 0;
        rudderTrim = 0.0F;
        currentRudderTrim = 0.0F;
        rtDelta = 0;
        tClap = -1;
        pictClap = 0.0F;
        selectorAngle = 10F;
        isSlideRight = false;
        setNew.dimPosition = 0.0F;
        cockpitDimControl = !cockpitDimControl;
        cockpitNightMats = (new java.lang.String[] {
            "gauges_1_TR", "gauges_2_TR", "gauges_3_TR", "gauges_4_TR", "gauges_6_TR", "gauges_7_TR", "D_gauges_1_TR", "D_gauges_2_TR", "D_gauges_3_TR", "D_gauges_4_TR", 
            "D_gauges_5_TR", "D_gauges_7_TR", "equip01_TR", "equip03_TR"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        loadBuzzerFX();
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "LAMPHOOK01");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        randomizeGlassDamage();
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
        ac = (com.maddox.il2.objects.air.HS_129)aircraft();
        ac.registerPit(this);
        try
        {
            if(!(ac.getGunByHookName("_CANNON01") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            {
                mainGuns[0] = ac.getGunByHookName("_CANNON01");
                mainGuns[3] = ac.getGunByHookName("_CANNON02");
                ammoCountGun0 = 500F;
                ammoCountGun3 = 500F;
            }
        }
        catch(java.lang.Exception exception) { }
        try
        {
            if(!(ac.getGunByHookName("_MGUN01") instanceof com.maddox.il2.objects.weapons.GunEmpty))
                if(ac instanceof com.maddox.il2.objects.air.HS_129B2)
                {
                    mainGuns[1] = ac.getGunByHookName("_MGUN01");
                    mainGuns[2] = ac.getGunByHookName("_MGUN02");
                    ammoCountGun1 = 1000F;
                    ammoCountGun2 = 1000F;
                } else
                {
                    mainGuns[0] = ac.getGunByHookName("_MGUN01");
                    mainGuns[2] = ac.getGunByHookName("_MGUN02");
                    ammoCountGun0 = 500F;
                    ammoCountGun2 = 500F;
                }
        }
        catch(java.lang.Exception exception1) { }
        try
        {
            if(!(ac.getGunByHookName("_MGUN03") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            {
                MG17s[0] = ac.getGunByHookName("_MGUN03");
                MG17s[1] = ac.getGunByHookName("_MGUN04");
                MG17s[2] = ac.getGunByHookName("_MGUN05");
                MG17s[3] = ac.getGunByHookName("_MGUN06");
                mesh.chunkVisible("X_4xMG17_gauge", true);
                mesh.chunkVisible("X_noExGuns", false);
            }
        }
        catch(java.lang.Exception exception2) { }
        try
        {
            if(!(ac.getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            {
                cannon = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON03");
                reloadTimeNeeded = 131L;
                oldbullets1 = cannon.countBullets();
                mesh.chunkVisible("X_biggun_light", true);
                mesh.chunkVisible("X_noExGuns", false);
            }
        }
        catch(java.lang.Exception exception3) { }
        try
        {
            if(!(ac.getGunByHookName("_CANNON04") instanceof com.maddox.il2.objects.weapons.GunEmpty) && cannon == null)
            {
                cannon = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON04");
                reloadTimeNeeded = 43L;
                oldbullets1 = cannon.countBullets();
                mesh.chunkVisible("X_biggun_light", true);
                mesh.chunkVisible("X_noExGuns", false);
            }
        }
        catch(java.lang.Exception exception4) { }
        try
        {
            if(!(ac.getGunByHookName("_HEAVYCANNON01") instanceof com.maddox.il2.objects.weapons.GunEmpty) && cannon == null)
            {
                cannon = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_HEAVYCANNON01");
                if(cannon instanceof com.maddox.il2.objects.weapons.MGunBK374Hs129)
                {
                    reloadTimeNeeded = 700L;
                } else
                {
                    mainGuns[1] = ac.getGunByHookName("_HEAVYCANNON01");
                    cannon = null;
                    ammoCountGun1 = 16F;
                }
                oldbullets1 = cannon.countBullets();
                mesh.chunkVisible("X_biggun_light", true);
                mesh.chunkVisible("X_noExGuns", false);
            }
        }
        catch(java.lang.Exception exception5) { }
        try
        {
            bombs[0] = (com.maddox.il2.objects.weapons.BombGun)((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb07");
        }
        catch(java.lang.Exception exception6) { }
        try
        {
            bombs[1] = (com.maddox.il2.objects.weapons.BombGun)((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb08");
        }
        catch(java.lang.Exception exception7) { }
        try
        {
            bombs[2] = (com.maddox.il2.objects.weapons.BombGun)((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb05");
        }
        catch(java.lang.Exception exception8) { }
        try
        {
            bombs[3] = (com.maddox.il2.objects.weapons.BombGun)((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb06");
        }
        catch(java.lang.Exception exception9) { }
        try
        {
            bombs[4] = (com.maddox.il2.objects.weapons.BombGun)((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb03");
        }
        catch(java.lang.Exception exception10) { }
        try
        {
            bombs[5] = (com.maddox.il2.objects.weapons.BombGun)((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb04");
        }
        catch(java.lang.Exception exception11) { }
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    public void reflectWorldToInstruments(float f)
    {
        float f1 = interp(setNew.dimPosition, setOld.dimPosition, f);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.09F * f1;
        mesh.chunkSetLocate("Z_revidim_01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_revidim_02", 0.0F, f1 * -23F, 0.0F);
        mesh.chunkSetAngles("Z_revidim_03", 0.0F, f1 * 23F, 0.0F);
        mesh.chunkVisible("ZFlare_Gear_03", fm.CT.getGear() == 1.0F && fm.Gears.lgear);
        mesh.chunkVisible("ZFlare_Gear_04", fm.CT.getGear() == 1.0F && fm.Gears.rgear);
        mesh.chunkVisible("ZFlare_Gear_01", fm.CT.getGear() == 0.0F && fm.Gears.lgear);
        mesh.chunkVisible("ZFlare_Gear_02", fm.CT.getGear() == 0.0F && fm.Gears.rgear);
        mesh.chunkVisible("ZFlare_Fuel_01", fm.M.fuel < 36F);
        mesh.chunkVisible("ZFlare_Fuel_02", fm.M.fuel < 36F);
        mesh.chunkSetAngles("stick", -14F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.ElevatorControl), 14F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.AileronControl), 0.0F);
        resetYPRmodifier();
        if(fm.CT.saveWeaponControl[3])
            com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.003F;
        mesh.chunkSetLocate("Z_trigger_02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        float f2 = 0.0F;
        int i = 0;
        if(fm.CT.saveWeaponControl[0] || fm.CT.saveWeaponControl[1] || fm.CT.saveWeaponControl[3])
        {
            if(fm.CT.saveWeaponControl[0] || fm.CT.saveWeaponControl[1])
                f2 = 20F;
            tClap = com.maddox.rts.Time.tickCounter() + com.maddox.il2.ai.World.Rnd().nextInt(500, 1000);
            if(fm.CT.saveWeaponControl[0] && !fm.CT.saveWeaponControl[1])
                selectorAngle = 24F;
            else
            if(!fm.CT.saveWeaponControl[0] && fm.CT.saveWeaponControl[1])
                selectorAngle = 43F;
            else
                selectorAngle = 10F;
        }
        if(com.maddox.rts.Time.tickCounter() < tClap)
            i = 1;
        mesh.chunkSetAngles("Z_trigger_01", -(240F + f2) * (pictClap = 0.85F * pictClap + 0.15F * (float)i), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_trigger_03", selectorAngle, 0.0F, 0.0F);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.05F * fm.CT.getRudder();
        mesh.chunkSetLocate("Z_ruddersupp_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.05F * fm.CT.getRudder();
        mesh.chunkSetLocate("Z_ruddersupp_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_rudder_R", fm.CT.getBrake() * 15F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_rudder_L", fm.CT.getBrake() * 15F, 0.0F, 0.0F);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.05F * fm.CT.getFlap();
        mesh.chunkSetLocate("Z_indicator_flaps", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        float f3 = interp(setNew.throttle1, setOld.throttle1, f);
        mesh.chunkSetAngles("Z_throttle_01", cvt(f3, 0.0F, 1.0F, 0.0F, 42.5F), 0.0F, 0.0F);
        float f4 = interp(setNew.throttle2, setOld.throttle2, f);
        mesh.chunkSetAngles("Z_throttle_02", cvt(f4, 0.0F, 1.0F, 0.0F, 42.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_wep", cvt(java.lang.Math.max(f3, f4), 1.0F, 1.1F, 0.0F, 18F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_mixture", interp(setNew.mix1, setOld.mix1, f) * 19F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gear", -gearsLever, 0.0F, 0.0F);
        if(gearsLever < -10F)
            mesh.chunkSetAngles("Z_gear_safety", cvt(gearsLever, -13F, -10F, 0.0F, 30F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_gear_safety", cvt(gearsLever, -10F, -2.5F, 30F, 0.0F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_flaps", interp(setNew.flaps, setOld.flaps, f) * -28.5F, 0.0F, 0.0F);
        if(useRealisticNavigationInstruments())
        {
            mesh.chunkSetAngles("Z_need_kompass_03", (-setNew.azimuth.getDeg(f) - 90F) + setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_need_kompass_02", setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("Z_need_kompass_02", -setNew.azimuth.getDeg(f) + 90F, 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_need_kompass_03", -setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_magn_01", -26F * magneto1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_magn_02", -26F * magneto2, 0.0F, 0.0F);
        float f5 = 0.0F;
        float f10 = 0.046F;
        if(mainGuns[0] != null)
        {
            float f6 = cvt(mainGuns[0].countBullets(), 0.0F, ammoCountGun0, f10, 0.0F);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -f6;
            mesh.chunkSetLocate("Z_ammo_counter_01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        } else
        {
            mesh.chunkVisible("Z_ammo_counter_01", false);
        }
        if(mainGuns[1] != null)
        {
            float f7 = cvt(mainGuns[1].countBullets(), 0.0F, ammoCountGun1, f10, 0.0F);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -f7;
            mesh.chunkSetLocate("Z_ammo_counter_02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        } else
        {
            mesh.chunkVisible("Z_ammo_counter_02", false);
        }
        if(mainGuns[2] != null)
        {
            float f8 = cvt(mainGuns[2].countBullets(), 0.0F, ammoCountGun2, f10, 0.0F);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -f8;
            mesh.chunkSetLocate("Z_ammo_counter_03", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        } else
        {
            mesh.chunkVisible("Z_ammo_counter_03", false);
        }
        if(mainGuns[3] != null)
        {
            float f9 = cvt(mainGuns[3].countBullets(), 0.0F, ammoCountGun3, f10, 0.0F);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -f9;
            mesh.chunkSetLocate("Z_ammo_counter_04", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        } else
        if(ammoCountGun0 == 1000F)
            mesh.chunkVisible("Z_ammo_counter_04", false);
        if(cannon != null)
            if(cannon.countBullets() == 0)
                cannonLight = true;
            else
            if(oldbullets1 != cannon.countBullets())
            {
                if(shotTime == -1L)
                {
                    shotTime = com.maddox.rts.Time.current();
                    cannonLight = true;
                } else
                if(com.maddox.rts.Time.current() - shotTime >= reloadTimeNeeded)
                {
                    oldbullets1 = cannon.countBullets();
                    shotTime = -1L;
                    cannonLight = false;
                } else
                {
                    cannonLight = true;
                }
            } else
            {
                cannonLight = false;
            }
        if(MG17s[0] != null)
        {
            mesh.chunkVisible("ZFlare_Ordn_03", !gunLight1);
            mesh.chunkVisible("ZFlare_Ordn_01", !gunLight2);
            mesh.chunkVisible("ZFlare_Ordn_02", !gunLight3);
            mesh.chunkVisible("ZFlare_Ordn_04", !gunLight4);
        }
        if(cannon != null)
            mesh.chunkVisible("ZFlare_bigguns", !cannonLight);
        mesh.chunkSetAngles("Z_need_clock_01", -cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_clock_02", -cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_clock_03", -cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_turnbank_02", cvt(setNew.turn, -0.23562F, 0.23562F, -21F, 21F), 0.0F, 0.0F);
        float f11 = getBall(4D);
        mesh.chunkSetAngles("Z_turnbank_01", cvt(f11, -4F, 4F, 8F, -8F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_climb", cvt(setNew.vspeed, -15F, 15F, 135F, -135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_speed", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_pressure_01", -cvt(pictManifold1, 0.6F, 1.8F, 15F, 345.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_pressure_02", -cvt(pictManifold2, 0.6F, 1.8F, 15F, 345.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_alt_01", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_alt_02", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 14000F, 0.0F, 313F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_fuel_01", -cvt(fm.M.fuel * 0.4001F, 0.0F, 205F, 0.0F, 96F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_fuel_02", -cvt(fm.M.fuel * 0.4001F, 0.0F, 205F, 0.0F, 96F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_temp_01", -cvt(fm.EI.engines[0].tOilOut, 0.0F, 160F, 0.0F, 332F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_temp_02", -cvt(fm.EI.engines[1].tOilOut, 0.0F, 160F, 0.0F, 332F), 0.0F, 0.0F);
        float f12 = fm.EI.engines[0].getRPM();
        mesh.chunkSetAngles("Z_need_RPM_01", -floatindex(cvt(f12, 0.0F, 3500F, 0.0F, 10F), rpmScale), 0.0F, 0.0F);
        if(fm.Or.getKren() < -110F || fm.Or.getKren() > 110F)
            rpmGeneratedPressure1 = rpmGeneratedPressure1 - 2.0F;
        else
        if(f12 < rpmGeneratedPressure1)
            rpmGeneratedPressure1 = rpmGeneratedPressure1 - (rpmGeneratedPressure1 - f12) * 0.01F;
        else
            rpmGeneratedPressure1 = rpmGeneratedPressure1 + (f12 - rpmGeneratedPressure1) * 0.001F;
        if(rpmGeneratedPressure1 < 800F)
            oilPressure1 = cvt(rpmGeneratedPressure1, 0.0F, 800F, 0.0F, 4F);
        else
        if(rpmGeneratedPressure1 < 1800F)
            oilPressure1 = cvt(rpmGeneratedPressure1, 800F, 1800F, 4F, 5F);
        else
            oilPressure1 = cvt(rpmGeneratedPressure1, 1800F, 3500F, 5F, 6F);
        float f13 = 0.0F;
        if(fm.EI.engines[0].tOilOut > 90F)
            f13 = cvt(fm.EI.engines[0].tOilOut, 90F, 110F, 1.1F, 1.5F);
        else
        if(fm.EI.engines[0].tOilOut < 50F)
            f13 = cvt(fm.EI.engines[0].tOilOut, 0.0F, 50F, 2.0F, 0.9F);
        else
            f13 = cvt(fm.EI.engines[0].tOilOut, 50F, 90F, 0.9F, 1.1F);
        float f14 = f13 * fm.EI.engines[0].getReadyness() * oilPressure1;
        mesh.chunkSetAngles("Z_need_oilpress_b_01", cvt(f14, 0.0F, 10F, 0.0F, 140F), 0.0F, 0.0F);
        f12 = fm.EI.engines[1].getRPM();
        mesh.chunkSetAngles("Z_need_RPM_02", -floatindex(cvt(f12, 0.0F, 3500F, 0.0F, 10F), rpmScale), 0.0F, 0.0F);
        if(fm.Or.getKren() < -110F || fm.Or.getKren() > 110F)
            rpmGeneratedPressure2 = rpmGeneratedPressure2 - 2.0F;
        else
        if(f12 < rpmGeneratedPressure2)
            rpmGeneratedPressure2 = rpmGeneratedPressure2 - (rpmGeneratedPressure2 - f12) * 0.01F;
        else
            rpmGeneratedPressure2 = rpmGeneratedPressure2 + (f12 - rpmGeneratedPressure2) * 0.001F;
        if(rpmGeneratedPressure2 < 800F)
            oilPressure2 = cvt(rpmGeneratedPressure2, 0.0F, 800F, 0.0F, 4F);
        else
        if(rpmGeneratedPressure2 < 1800F)
            oilPressure2 = cvt(rpmGeneratedPressure2, 800F, 1800F, 4F, 5F);
        else
            oilPressure2 = cvt(rpmGeneratedPressure2, 1800F, 3500F, 5F, 6F);
        f13 = 0.0F;
        if(fm.EI.engines[0].tOilOut > 90F)
            f13 = cvt(fm.EI.engines[0].tOilOut, 90F, 110F, 1.1F, 1.5F);
        else
        if(fm.EI.engines[0].tOilOut < 50F)
            f13 = cvt(fm.EI.engines[0].tOilOut, 0.0F, 50F, 2.0F, 0.9F);
        else
            f13 = cvt(fm.EI.engines[0].tOilOut, 50F, 90F, 0.9F, 1.1F);
        float f15 = f13 * fm.EI.engines[0].getReadyness() * oilPressure2;
        mesh.chunkSetAngles("Z_need_oilpress_b_02", cvt(f15, 0.0F, 10F, 0.0F, 140F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_oilpress_a_01", -cvt(rpmGeneratedPressure1, 0.0F, 1800F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_oilpress_a_02", -cvt(rpmGeneratedPressure2, 0.0F, 1800F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_oilsystem", -cvt((rpmGeneratedPressure1 + rpmGeneratedPressure2) / 2.0F, 0.0F, 2000F, 48F, 250F), 0.0F, 0.0F);
        if(ac.fullCanopyOpened)
        {
            com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[1] = fm.CT.getCockpitDoor() * 1.0F;
            if((double)com.maddox.il2.objects.air.Aircraft.xyz[1] < 0.01D)
                com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.0F;
            mesh.chunkSetLocate("Z_canopy", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        } else
        {
            com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[1] = fm.CT.getCockpitDoor() * 0.33F;
            if((double)com.maddox.il2.objects.air.Aircraft.xyz[1] < 0.01D)
                com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.0F;
            if(isSlideRight)
            {
                mesh.chunkSetLocate("Sliding_glass_01", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                mesh.chunkSetAngles("Z_glass_opener01", cvt(com.maddox.il2.objects.air.Aircraft.xyz[1], 0.0F, 0.05F, 0.0F, -27F), 0.0F, 0.0F);
            } else
            {
                mesh.chunkSetLocate("Sliding_glass", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                mesh.chunkSetAngles("Z_glass_opener", cvt(com.maddox.il2.objects.air.Aircraft.xyz[1], 0.0F, 0.05F, 0.0F, -27F), 0.0F, 0.0F);
            }
        }
        if(fm.EI.engines[0].getExtinguishers() == 0)
        {
            com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.05F;
            mesh.chunkSetLocate("Z_extinguisher_01", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        }
        if(fm.EI.engines[1].getExtinguishers() == 0)
        {
            com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.05F;
            mesh.chunkSetLocate("Z_extinguisher_02", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        }
        mesh.chunkSetAngles("Z_need_radio_01", cvt(setNew.beaconDirection, -45F, 45F, 20F, -20F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_need_radio_02", cvt(setNew.beaconRange, 0.0F, 1.0F, -20F, 20F), 0.0F, 0.0F);
        mesh.chunkVisible("ZFlare_Radio_01", isOnBlindLandingMarker());
        mesh.chunkVisible("ZFlare_Pitch_02", fm.EI.engines[0].getElPropPos() < 1.0F && fm.EI.engines[0].getControlPropDelta() == 1);
        mesh.chunkVisible("ZFlare_Pitch_01", fm.EI.engines[0].getElPropPos() > 0.0F && fm.EI.engines[0].getControlPropDelta() == -1);
        mesh.chunkVisible("ZFlare_Pitch_04", fm.EI.engines[1].getElPropPos() < 1.0F && fm.EI.engines[1].getControlPropDelta() == 1);
        mesh.chunkVisible("ZFlare_Pitch_03", fm.EI.engines[1].getElPropPos() > 0.0F && fm.EI.engines[1].getControlPropDelta() == -1);
        mesh.chunkSetAngles("Z_knobPITCH_01", engine1PropPitch * -15F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_knobPITCH_02", engine2PropPitch * -15F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_knobPITCH_03", engine1PitchMode * 17F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_knobPITCH_04", engine2PitchMode * 17F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_elevator_trim", elevatorTrim * 24F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_rudder_trim", rudderTrim * 24F, 0.0F, 0.0F);
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
            light1.light.setEmit(0.003F, 1.0F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    private void randomizeGlassDamage()
    {
        double d = java.lang.Math.random();
        if(d < 0.29999999999999999D)
            mesh.materialReplace("XArmor_glass_DMG", "XArmor_glass_DMG2");
        else
        if(d < 0.59999999999999998D)
            mesh.materialReplace("XArmor_glass_DMG", "XArmor_glass_DMG3");
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("Xfront_glass_D0", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassHoles_03", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("XHoles_01", true);
            mesh.chunkVisible("gauges1_d0", false);
            mesh.chunkVisible("D_gauges1_d0", true);
            mesh.chunkVisible("Z_need_pressure_01", false);
            mesh.chunkVisible("Z_need_pressure_02", false);
            mesh.chunkVisible("Z_need_clock_03", false);
            mesh.chunkVisible("Z_need_clock_02", false);
            mesh.chunkVisible("Z_need_clock_01", false);
            mesh.chunkVisible("Z_need_speed", false);
            mesh.chunkVisible("Z_need_kompass_03", false);
            mesh.chunkVisible("Z_need_kompass_02", false);
            mesh.chunkVisible("XHoles_01", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XGlassHoles_02", true);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("XGlassHoles_01", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("D_revi", true);
            mesh.chunkVisible("D_reviglass", true);
            mesh.chunkVisible("D_dimmerglas", true);
            mesh.chunkVisible("revi_mounting", false);
            mesh.chunkVisible("revi", false);
            mesh.chunkVisible("Z_revidim_01", false);
            mesh.chunkVisible("Z_revidim_02", false);
            mesh.chunkVisible("Z_revidim_03", false);
            mesh.chunkVisible("reviglass", false);
            mesh.chunkVisible("reticle", false);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("XHoles_02", true);
            mesh.chunkVisible("gauges2_d0", false);
            mesh.chunkVisible("D_gauges2_d0", true);
            mesh.chunkVisible("Z_need_climb", false);
            mesh.chunkVisible("Z_need_turnbank_02", false);
            mesh.chunkVisible("Z_turnbank_01", false);
            mesh.chunkVisible("Z_need_alt_01", false);
            mesh.chunkVisible("Z_need_alt_02", false);
            mesh.chunkVisible("Z_need_radio_02", false);
            mesh.chunkVisible("Z_need_radio_01", false);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
        {
            if(fm.AS.astateOilStates[0] == 0)
            {
                mesh.chunkVisible("ZOil_01", false);
                mesh.chunkVisible("ZOil_03", false);
            } else
            {
                mesh.chunkVisible("ZOil_01", true);
                mesh.chunkVisible("ZOil_03", true);
            }
            if(fm.AS.astateOilStates[1] == 0)
            {
                mesh.chunkVisible("ZOil_02", false);
                mesh.chunkVisible("ZOil_04", false);
            } else
            {
                mesh.chunkVisible("ZOil_02", true);
                mesh.chunkVisible("ZOil_04", true);
            }
        }
    }

    protected boolean doFocusEnter()
    {
        boolean flag = super.doFocusEnter();
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        hiermesh.chunkVisible("Revi_D0", false);
        return flag;
    }

    protected void doFocusLeave()
    {
        super.doFocusLeave();
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        hiermesh.chunkVisible("Revi_D0", true);
    }

    public boolean isViewRight()
    {
        com.maddox.il2.engine.Loc loc = new Loc();
        com.maddox.il2.engine.Loc loc1 = new Loc();
        com.maddox.il2.engine.hotkey.HookPilot.current.computePos(this, loc, loc1);
        float f = loc1.getOrient().getYaw();
        if(f < 0.0F)
            isSlideRight = true;
        else
            isSlideRight = false;
        return isSlideRight;
    }

    private com.maddox.il2.objects.weapons.Gun mainGuns[] = {
        null, null, null, null
    };
    private float ammoCountGun0;
    private float ammoCountGun1;
    private float ammoCountGun2;
    private float ammoCountGun3;
    private com.maddox.il2.objects.weapons.Gun MG17s[] = {
        null, null, null, null
    };
    private com.maddox.il2.objects.weapons.Gun cannon;
    private com.maddox.il2.objects.weapons.BombGun bombs[] = {
        null, null, null, null, null, null
    };
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictManifold1;
    private float pictManifold2;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.LightPointActor light1;
    private static final float speedometerScale[] = {
        0.0F, -12.33333F, 18.5F, 37F, 62.5F, 90F, 110.5F, 134F, 158.5F, 186F, 
        212.5F, 238.5F, 265F, 289.5F, 315F, 339.5F, 346F, 346F
    };
    private static final float rpmScale[] = {
        0.0F, 7F, 24.5F, 60F, 99F, 140F, 180.2F, 221.5F, 260F, 297.5F, 
        334.5F
    };
    float oilPressure1;
    float rpmGeneratedPressure1;
    float oilPressure2;
    float rpmGeneratedPressure2;
    com.maddox.il2.objects.air.HS_129 ac;
    private float gearsLever;
    private float gears;
    private int oldbullets1;
    private int oldbullets2;
    private int oldbullets3;
    private int oldbullets4;
    private boolean gunLight1;
    private boolean gunLight2;
    private boolean gunLight3;
    private boolean gunLight4;
    private boolean cannonLight;
    private long shotTime;
    private long reloadTimeNeeded;
    private float engine1PropPitch;
    private float engine2PropPitch;
    private float engine1PitchMode;
    private float engine2PitchMode;
    private float magneto1;
    private float magneto2;
    private float elevatorTrim;
    private float currentElevatorTrim;
    private int etDelta;
    private float rudderTrim;
    private float currentRudderTrim;
    private int rtDelta;
    private int tClap;
    private float pictClap;
    private float selectorAngle;
    private boolean isSlideRight;























































}
