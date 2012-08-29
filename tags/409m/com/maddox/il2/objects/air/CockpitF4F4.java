// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitF4F4.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
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
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, F4F, Aircraft, Cockpit

public class CockpitF4F4 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.mix = 0.85F * setOld.mix + fm.EI.engines[0].getControlMix() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(1.0F), f);
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            }
            boolean flag = false;
            if(setNew.gCrankAngle < fm.CT.getGear() - 0.05F)
                if(java.lang.Math.abs(setNew.gCrankAngle - fm.CT.getGear()) > 0.05F && java.lang.Math.abs(setNew.gCrankAngle - fm.CT.getGear()) < 0.9F)
                {
                    setNew.gCrankAngle += 0.0025F;
                    flag = true;
                } else
                {
                    setNew.gCrankAngle = fm.CT.getGear();
                    setOld.gCrankAngle = fm.CT.getGear();
                }
            if(setNew.gCrankAngle > fm.CT.getGear() + 0.05F)
                if(java.lang.Math.abs(setNew.gCrankAngle - fm.CT.getGear()) > 0.05F && java.lang.Math.abs(setNew.gCrankAngle - fm.CT.getGear()) < 0.9F)
                {
                    setNew.gCrankAngle -= 0.0025F;
                    flag = true;
                } else
                {
                    setNew.gCrankAngle = fm.CT.getGear();
                    setOld.gCrankAngle = fm.CT.getGear();
                }
            if(flag != sfxPlaying)
            {
                if(flag)
                    sfxStart(16);
                else
                    sfxStop(16);
                sfxPlaying = flag;
            }
            return true;
        }

        boolean sfxPlaying;

        Interpolater()
        {
            sfxPlaying = false;
        }
    }

    private class Variables
    {

        float throttle;
        float prop;
        float mix;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        float gCrankAngle;

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
            aircraft().hierMesh().chunkVisible("Blister1_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(fm.AS.astateBailoutStep < 2)
            aircraft().hierMesh().chunkVisible("Blister1_D0", true);
        super.doFocusLeave();
    }

    public CockpitF4F4()
    {
        super("3DO/Cockpit/F4F-4/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bNeedSetUp = true;
        cockpitNightMats = (new java.lang.String[] {
            "DGP_02", "DGP_03", "DGP_04", "GP_01", "GP_02", "GP_03", "GP_04", "GP_05", "GP_06", "GP_07"
        });
        setNightMats(false);
        light1 = new LightPointActor(new LightPoint(), new Point3d(-0.80000000000000004D, 0.0D, 1.0D));
        light1.light.setColor(232F, 75F, 44F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            reflectPlaneToModel();
            bNeedSetUp = false;
        }
        com.maddox.il2.objects.air.F4F _tmp = (com.maddox.il2.objects.air.F4F)aircraft();
        if(com.maddox.il2.objects.air.F4F.bChangedPit)
        {
            reflectPlaneToModel();
            com.maddox.il2.objects.air.F4F _tmp1 = (com.maddox.il2.objects.air.F4F)aircraft();
            com.maddox.il2.objects.air.F4F.bChangedPit = false;
        }
        float f1 = fm.CT.getWing();
        mesh.chunkSetAngles("WingLMid_D0", 0.0F, -110F * f1, 0.0F);
        mesh.chunkSetAngles("WingRMid_D0", 0.0F, -110F * f1, 0.0F);
        f1 = -50F * fm.CT.getFlap();
        mesh.chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.69F);
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetLocate("XGlassDamage2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Trim1", 0.0F, 722F * fm.CT.getTrimAileronControl(), 0.0F);
        mesh.chunkSetAngles("Z_Trim2", 0.0F, 116F * fm.CT.getTrimRudderControl(), 0.0F);
        mesh.chunkSetAngles("Z_Trim3", 0.0F, -722F * fm.CT.getTrimElevatorControl(), 0.0F);
        mesh.chunkSetAngles("Z_Flaps1", 0.0F, -168F * fm.CT.FlapsControl, 0.0F);
        mesh.chunkSetAngles("Z_Gear1", 0.0F, (15840F * interp(setNew.gCrankAngle, setOld.gCrankAngle, f)) % 360F, 0.0F);
        if(fm.CT.GearControl > prevGearC)
            mesh.chunkSetAngles("Z_Gear2", 0.0F, 62F, 0.0F);
        else
        if(fm.CT.GearControl < prevGearC)
            mesh.chunkSetAngles("Z_Gear2", 0.0F, 0.0F, 0.0F);
        prevGearC = fm.CT.GearControl;
        mesh.chunkSetAngles("Z_Throtle1", 0.0F, 43.4F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.065F * setNew.prop;
        mesh.chunkSetLocate("Z_Prop1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Mixture1", 0.0F, 41.25F * cvt(fm.EI.engines[0].getControlMix(), 1.0F, 1.2F, 0.55F, 1.15F), 0.0F);
        mesh.chunkSetAngles("Z_Supercharger1", 0.0F, -34F * (float)fm.EI.engines[0].getControlCompressor(), 0.0F);
        mesh.chunkSetAngles("Z_RightPedal1", 0.0F, -20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_RightPedal2", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal1", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal2", 0.0F, -20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_Columnbase", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 8F, 0.0F);
        mesh.chunkSetAngles("Z_Column", 0.0F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 8F);
        mesh.chunkSetAngles("Z_Hook1", 0.0F, -60F * fm.CT.arrestorControl, 0.0F);
        mesh.chunkSetAngles("Z_CowlFlaps", 0.0F, 90F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 1080F), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 10800F), 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 796.3598F, 0.0F, 11F), speedometerScale), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 23F, -23F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(7D), -7F, 7F, -10F, 10F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank3", 0.0F, -fm.Or.getKren(), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(fm.Or.getTangage(), -45F, 45F, -0.0285F, 0.0285F);
        mesh.chunkSetLocate("Z_TurnBank4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Climb1", 0.0F, floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, -90F + setNew.waypointAzimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Z_Compass2", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Z_Compass3", 0.0F, -setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, -720F), 0.0F);
        mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(fm.M.fuel, 0.0F, 400F, 0.0F, 296F), 0.0F);
        mesh.chunkSetAngles("Z_FuelPres", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 4F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, -180F), 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
            mesh.chunkSetAngles("Z_Pres1", 0.0F, cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 1.693189F, 0.0F, 342F), 0.0F);
        mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 295F), 0.0F);
        mesh.chunkSetAngles("Z_AirTemp1", 0.0F, cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.15F, -50F, 50F, -45F, 45F), 0.0F);
        mesh.chunkSetAngles("Z_CylTemp1", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 20F, 120F, 0.0F, 78.5F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.0301F * fm.CT.getGear();
        mesh.chunkSetLocate("Z_GearInd1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        if(fm.Gears.bTailwheelLocked)
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -0.05955F;
        mesh.chunkSetLocate("Z_TWheel_Lock", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("Pricel_D0", false);
            mesh.chunkVisible("Pricel_D1", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage2", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("Z_CylTemp1", false);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_TurnBank1", false);
            mesh.chunkVisible("Z_TurnBank2", false);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_AirTemp1", false);
            mesh.chunkVisible("Z_Fuel1", false);
            mesh.chunkVisible("XHullDamage4", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XHullDamage1", true);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("XHullDamage2", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XHullDamage4", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XHullDamage3", true);
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D0o"));
        mesh.materialReplace("Matt2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D2o"));
        mesh.materialReplace("Matt2D2o", mat);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("WingLIn_D0", hiermesh.isChunkVisible("WingLIn_D0"));
        mesh.chunkVisible("WingLIn_D1", hiermesh.isChunkVisible("WingLIn_D1"));
        mesh.chunkVisible("WingLIn_D2", hiermesh.isChunkVisible("WingLIn_D2"));
        mesh.chunkVisible("WingLIn_D3", hiermesh.isChunkVisible("WingLIn_D3"));
        mesh.chunkVisible("WingRIn_D0", hiermesh.isChunkVisible("WingRIn_D0"));
        mesh.chunkVisible("WingRIn_D1", hiermesh.isChunkVisible("WingRIn_D1"));
        mesh.chunkVisible("WingRIn_D2", hiermesh.isChunkVisible("WingRIn_D2"));
        mesh.chunkVisible("WingRIn_D3", hiermesh.isChunkVisible("WingRIn_D3"));
        mesh.chunkVisible("WingLMid_D0", hiermesh.isChunkVisible("WingLMid_D0"));
        mesh.chunkVisible("WingLMid_D1", hiermesh.isChunkVisible("WingLMid_D1"));
        mesh.chunkVisible("WingLMid_D2", hiermesh.isChunkVisible("WingLMid_D2"));
        mesh.chunkVisible("WingLMid_D3", hiermesh.isChunkVisible("WingLMid_D3"));
        mesh.chunkVisible("WingRMid_D0", hiermesh.isChunkVisible("WingRMid_D0"));
        mesh.chunkVisible("WingRMid_D1", hiermesh.isChunkVisible("WingRMid_D1"));
        mesh.chunkVisible("WingRMid_D2", hiermesh.isChunkVisible("WingRMid_D2"));
        mesh.chunkVisible("WingRMid_D3", hiermesh.isChunkVisible("WingRMid_D3"));
        mesh.chunkVisible("Flap01_D0", hiermesh.isChunkVisible("Flap01_D0"));
        mesh.chunkVisible("Flap02_D0", hiermesh.isChunkVisible("Flap02_D0"));
        mesh.chunkVisible("Flap03_D0", hiermesh.isChunkVisible("Flap03_D0"));
        mesh.chunkVisible("Flap04_D0", hiermesh.isChunkVisible("Flap04_D0"));
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            setNightMats(true);
            light1.light.setEmit(0.005F, 1.0F);
        } else
        {
            setNightMats(false);
            light1.light.setEmit(0.0F, 0.0F);
        }
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
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float prevGearC;
    private com.maddox.il2.engine.LightPointActor light1;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 15.5F, 77F, 175F, 275F, 360F, 412F, 471F, 539F, 610.5F, 
        669.75F, 719F
    };
    private static final float variometerScale[] = {
        -175.5F, -160.5F, -145.5F, -128F, -100F, -65.5F, 0.0F, 65.5F, 100F, 128F, 
        145.5F, 160.5F, 175.5F
    };







}
