package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.sound.SoundFX;

public class CockpitJU_88C6_RGunner extends CockpitGunner
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
                setNew.throttle1 = 0.85F * setOld.throttle1 + fm.EI.engines[0].getControlThrottle() * 0.15F;
                setNew.prop1 = 0.85F * setOld.prop1 + fm.EI.engines[0].getControlProp() * 0.15F;
                setNew.throttle2 = 0.85F * setOld.throttle2 + fm.EI.engines[1].getControlThrottle() * 0.15F;
                setNew.prop2 = 0.85F * setOld.prop2 + fm.EI.engines[1].getControlProp() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f - setOld.azimuth.getDeg(1.0F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                float f1 = prevFuel - fm.M.fuel;
                prevFuel = fm.M.fuel;
                f1 /= 0.72F;
                f1 /= com.maddox.rts.Time.tickLenFs();
                f1 *= 3600F;
                setNew.cons = 0.91F * setOld.cons + 0.09F * f1;
                if(buzzerFX != null)
                    if(fm.Loc.z < (double)((JU_88C6)aircraft()).fDiveRecoveryAlt && ((JU_88C6)fm.actor).diveMechStage == 1)
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

        float throttle1;
        float prop1;
        float throttle2;
        float prop2;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        float dimPosition;
        float cons;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("fakeNose_D0", false);
            aircraft().hierMesh().chunkVisible("fakeNose_D1", false);
            aircraft().hierMesh().chunkVisible("fakeNose_D2", false);
            aircraft().hierMesh().chunkVisible("fakeNose_D3", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(isFocused())
        {
            aircraft().hierMesh().chunkVisible("fakeNose_D0", aircraft().hierMesh().isChunkVisible("Nose_D0"));
            aircraft().hierMesh().chunkVisible("fakeNose_D1", aircraft().hierMesh().isChunkVisible("Nose_D1"));
            aircraft().hierMesh().chunkVisible("fakeNose_D2", aircraft().hierMesh().isChunkVisible("Nose_D2"));
            aircraft().hierMesh().chunkVisible("fakeNose_D3", aircraft().hierMesh().isChunkVisible("Nose_D3"));
            super.doFocusLeave();
        }
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        mesh.chunkSetAngles("zTurret1A", 0.0F, -f, 0.0F);
        mesh.chunkSetAngles("zTurret1B", 0.0F, f1, 0.0F);
        if(f > 10F)
            f = 10F;
        if(f < -25F)
            f = -25F;
        if(f1 < -10F)
            f1 = -10F;
        if(f1 > 15F)
            f1 = 15F;
        if(f > 0.0F && f1 < -4.5F)
            f1 = -4.5F;
        mesh.chunkSetAngles("CameraRodA", 0.0F, -f, 0.0F);
        mesh.chunkSetAngles("CameraRodB", 0.0F, f1, 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(isRealMode())
            if(!aiTurret().bIsOperable)
            {
                orient.setYPR(0.0F, 0.0F, 0.0F);
            } else
            {
                float f = orient.getYaw();
                float f1 = orient.getTangage();
                if(f < -45F)
                    f = -45F;
                if(f > 25F)
                    f = 25F;
                if(f1 > 60F)
                    f1 = 60F;
                if(f1 < -10F)
                    f1 = -10F;
                if(f < -2F)
                {
                    if(f1 < cvt(f, -6.8F, -2F, -10F, -2.99F))
                        f1 = cvt(f, -6.8F, -2F, -10F, -2.99F);
                } else
                if(f < 0.5F)
                {
                    if(f1 < cvt(f, -2F, 0.5F, -2.99F, -2.3F))
                        f1 = cvt(f, -2F, 0.5F, -2.99F, -2.3F);
                } else
                if(f < 5.3F)
                {
                    if(f1 < cvt(f, 0.5F, 5.3F, -2.3F, -2.3F))
                        f1 = cvt(f, 0.5F, 5.3F, -2.3F, -2.3F);
                } else
                if(f1 < cvt(f, 5.3F, 25F, -2.3F, -7.2F))
                    f1 = cvt(f, 5.3F, 25F, -2.3F, -7.2F);
                orient.setYPR(f, f1, 0.0F);
                orient.wrap();
            }
    }

    protected void interpTick()
    {
        if(isRealMode())
        {
            if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
                bGunFire = false;
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        }
    }

    public void doGunFire(boolean flag)
    {
        if(isRealMode())
        {
            if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
                bGunFire = false;
            else
                bGunFire = flag;
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        }
    }

    public CockpitJU_88C6_RGunner()
    {
        super("3DO/Cockpit/Ju-88C6MOD-RGun/C6hier.him", "he111");
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictFlap = 0.0F;
        pictGear = 0.0F;
        pictManf1 = 1.0F;
        pictManf2 = 1.0F;
        prevFuel = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "88A4_I_Set1", "88A4_I_Set2", "88A4_I_Set3", "88a4_I_Set4", "88a4_I_Set5", "88a4_I_Set6", "88a4_SlidingGlass", "88gardinen", "lofte7_02", "Peil1", 
            "Peil2", "skala"
        });
        setNightMats(false);
        setNew.dimPosition = setOld.dimPosition = 1.0F;
        cockpitDimControl = !cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
        buzzerFX = aircraft().newSound("models.buzzthru", false);
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("Z_Trim1", cvt(fm.CT.getTrimElevatorControl(), -0.5F, 0.5F, -750F, 750F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ReviTinter", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 130F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zColumn1", 7F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("zColumn2", 52.2F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[2] = -0.1F * fm.CT.getRudder();
        mesh.chunkSetLocate("zPedalL", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[2] = 0.1F * fm.CT.getRudder();
        mesh.chunkSetLocate("zPedalR", Cockpit.xyz, Cockpit.ypr);
        mesh.chunkSetAngles("z_Object20", cvt(((JU_88C6)aircraft()).fSightCurSpeed, 400F, 800F, 87F, -63.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("TempMeter", floatindex(cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z), 213.09F, 293.09F, 0.0F, 8F), frAirTempScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Radiator_Sw1", cvt(fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -120F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Radiator_Sw2", cvt(fm.EI.engines[1].getControlRadiator(), 0.0F, 1.0F, 0.0F, -120F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoSw1", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 100F, 0.0F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoSw2", cvt(fm.EI.engines[1].getControlMagnetos(), 0.0F, 3F, 100F, 0.0F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zHour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zMinute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zHour2", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zMinute2", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 6000F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt3", (((JU_88C6)aircraft()).fDiveRecoveryAlt * 360F) / 6000F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zSpeed", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 50F, 750F, 0.0F, 14F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zSpeed2", floatindex(cvt(fm.getSpeedKMH(), 50F, 750F, 0.0F, 14F), speedometerScale2), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", cvt(setNew.vspeed, -15F, 15F, -151F, 151F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zWaterTemp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 64F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zWaterTemp2", cvt(fm.EI.engines[1].tWaterOut, 0.0F, 120F, 0.0F, 64F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilPress1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.47F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilPress2", cvt(1.0F + 0.05F * fm.EI.engines[1].tOilOut * fm.EI.engines[1].getReadyness(), 0.0F, 7.47F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuelPress1", cvt(fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 0.5F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuelPress2", cvt(fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 0.5F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPM1", cvt(fm.EI.engines[0].getRPM(), 600F, 3600F, 0.0F, 331F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPM2", cvt(fm.EI.engines[1].getRPM(), 600F, 3600F, 0.0F, 331F), 0.0F, 0.0F);
        mesh.chunkSetAngles("ATA1", pictManf1 = 0.9F * pictManf1 + 0.1F * cvt(fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 325.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("ATA2", pictManf2 = 0.9F * pictManf2 + 0.1F * cvt(fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 325.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuel1", cvt(fm.M.fuel, 0.0F, 1008F, 0.0F, 77F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuel2", cvt(fm.M.fuel, 0.0F, 1008F, 0.0F, 77F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zFuelPress", cvt(setNew.cons, 100F, 500F, 0.0F, 240F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass3", setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass4", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass5", 180F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass6", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass7", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass8", setNew.waypointAzimuth.getDeg(f) + setNew.azimuth.getDeg(f) + 90F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zHORIZ1", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.045F, -0.045F);
        mesh.chunkSetLocate("zHORIZ2", Cockpit.xyz, Cockpit.ypr);
        resetYPRmodifier();
        Cockpit.xyz[2] = 0.02125F * fm.CT.getTrimElevatorControl();
        mesh.chunkSetLocate("zTRIM1", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[2] = 0.02125F * fm.CT.getTrimAileronControl();
        mesh.chunkSetLocate("zTRIM2", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[2] = 0.02125F * fm.CT.getTrimRudderControl();
        mesh.chunkSetLocate("zTRIM3", Cockpit.xyz, Cockpit.ypr);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, 25F, -25F), 0.0F, 0.0F);
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
        if((fm.AS.astateCockpitState & 0x80) == 0);
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassDamage5", true);
        if((fm.AS.astateCockpitState & 0x40) == 0);
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XGlassDamage3", true);
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("XGlassDamage5", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XGlassDamage6", true);
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

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
    }

    private boolean bNeedSetUp;
    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictFlap;
    private float pictGear;
    private float pictManf1;
    private float pictManf2;
    private float prevFuel;
    protected com.maddox.sound.SoundFX buzzerFX;
    private static final float speedometerScale[] = {
        0.0F, 16F, 35.5F, 60.5F, 88F, 112.5F, 136F, 159.5F, 186.5F, 211.5F, 
        240F, 268F, 295.5F, 321F, 347F
    };
    private static final float speedometerScale2[] = {
        0.0F, 23.5F, 47.5F, 72F, 95.5F, 120F, 144.5F, 168.5F, 193F, 217F, 
        241F, 265F, 288F, 311.5F, 335.5F
    };
    private static final float frAirTempScale[] = {
        76.5F, 68F, 57F, 44.5F, 29.5F, 14.5F, 1.5F, -10F, -19F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "aiTuretNum", 0);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "weaponControlNum", 10);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "astatePilotIndx", 1);
    }








}
