package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;

public class CockpitI_15Bis extends CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float azimuth;
        float throttle;
        float mix;
        float prop;
        float turn;
        float vspeed;
        float dimPos;
        float radiator;

        private Variables()
        {
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            if((I_15xyz)aircraft() == null);
            if(I_15xyz.bChangedPit)
            {
                reflectPlaneToModel();
                if((I_15xyz)aircraft() == null);
                I_15xyz.bChangedPit = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            if(cockpitDimControl)
            {
                if(setNew.dimPos < 1.0F)
                    setNew.dimPos = setOld.dimPos + 0.05F;
            } else
            if(setNew.dimPos > 0.0F)
                setNew.dimPos = setOld.dimPos - 0.05F;
            setNew.altimeter = fm.getAltitude();
            if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                setNew.azimuth = (21F * setOld.azimuth + fm.Or.azimut()) / 22F;
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.throttle = (10F * setOld.throttle + fm.EI.engines[0].getControlThrottle()) / 11F;
            setNew.mix = (10F * setOld.mix + fm.EI.engines[0].getControlMix()) / 11F;
            setNew.prop = setOld.prop;
            if(setNew.prop < fm.EI.engines[0].getControlProp() - 0.01F)
                setNew.prop += 0.0025F;
            if(setNew.prop > fm.EI.engines[0].getControlProp() + 0.01F)
                setNew.prop -= 0.0025F;
            w.set(fm.getW());
            fm.Or.transform(w);
            setNew.turn = (12F * setOld.turn + w.z) / 13F;
            setNew.vspeed = (299F * setOld.vspeed + fm.getVertSpeed()) / 300F;
            pictSupc = 0.8F * pictSupc + 0.2F * (float)fm.EI.engines[0].getControlCompressor();
            setNew.radiator = (10F * setOld.radiator + fm.CT.getRadiatorControl()) / 11F;
            if(((I_15xyz)aircraft()).blisterRemoved)
            {
                mesh.chunkVisible("Z_BlisterR1", false);
                mesh.chunkVisible("Z_BlisterR2", false);
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitI_15Bis()
    {
        super("3DO/Cockpit/I-15Bis/hier.him", "u2");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictSupc = 0.0F;
        scopeZoomArea = 15F;
        isIron = false;
        bEntered = false;
        rpmGeneratedPressure = 0.0F;
        oilPressure = 0.0F;
        isSlideRight = false;
        cockpitNightMats = (new java.lang.String[] {
            "prib_four_damage", "prib_four", "PRIB_ONE", "prib_one_damage", "PRIB_three", "prib_three_damage", "PRIB_TWO", "prib_two_damage", "Shkala128"
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
        mesh.chunkSetAngles("Z_Mix1", 50F * interp(setNew.mix, setOld.mix, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mix2", 0.0F, 0.0F, -50F * interp(setNew.mix, setOld.mix, f));
        mesh.chunkSetAngles("Z_Throtle1", 60F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle2", 0.0F, 0.0F, -60F * interp(setNew.throttle, setOld.throttle, f));
        mesh.chunkSetAngles("Z_Supc1", -50F * pictSupc, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Supc2", 0.0F, 0.0F, 50F * pictSupc);
        mesh.chunkSetAngles("Z_Rad1", -60F * interp(setNew.radiator, setOld.radiator, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Rad2", 0.0F, 0.0F, -60F * interp(setNew.radiator, setOld.radiator, f));
        mesh.chunkSetAngles("Z_Magto", 44F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Stick1", 0.0F, 16F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 10.2F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("Z_Stick2", 0.0F, 0.0F, 10.2F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("Z_Ped_Base", 0.0F, -15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_PedalL", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_PedalR", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_Ped_trossL", -15.65F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Ped_trossR", -15.65F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 600F, 0.0F, 12F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, -interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(setNew.turn, -0.2F, 0.2F, 26F, -26F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(getBall(8D), -8F, 8F, 26F, -26F), 0.0F);
        mesh.chunkSetAngles("Z_Climb1", 0.0F, cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 125F, 0.0F, 180F), 0.0F);
        float f1 = fm.EI.engines[0].getRPM();
        mesh.chunkSetAngles("Z_RPM1", 0.0F, floatindex(cvt(f1, 0.0F, 2400F, 0.0F, 11F), rpmScale), 0.0F);
        if(fm.Or.getKren() < -110F || fm.Or.getKren() > 110F)
            rpmGeneratedPressure = rpmGeneratedPressure - 2.0F;
        else
        if(f1 < rpmGeneratedPressure)
            rpmGeneratedPressure = rpmGeneratedPressure - (rpmGeneratedPressure - f1) * 0.01F;
        else
            rpmGeneratedPressure = rpmGeneratedPressure + (f1 - rpmGeneratedPressure) * 0.001F;
        if(rpmGeneratedPressure < 800F)
            oilPressure = cvt(rpmGeneratedPressure, 0.0F, 800F, 0.0F, 4F);
        else
        if(rpmGeneratedPressure < 1800F)
            oilPressure = cvt(rpmGeneratedPressure, 800F, 1800F, 4F, 5F);
        else
            oilPressure = cvt(rpmGeneratedPressure, 1800F, 2750F, 5F, 5.8F);
        float f2 = 0.0F;
        if(fm.EI.engines[0].tOilOut > 90F)
            f2 = cvt(fm.EI.engines[0].tOilOut, 90F, 110F, 1.1F, 1.5F);
        else
        if(fm.EI.engines[0].tOilOut < 50F)
            f2 = cvt(fm.EI.engines[0].tOilOut, 0.0F, 50F, 2.0F, 0.9F);
        else
            f2 = cvt(fm.EI.engines[0].tOilOut, 50F, 90F, 0.9F, 1.1F);
        float f3 = f2 * fm.EI.engines[0].getReadyness() * oilPressure;
        mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(f3, 0.0F, 7F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_Fuelpres1", 0.0F, -cvt(rpmGeneratedPressure, 0.0F, 1800F, 0.0F, 120F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel", 0.0F, cvt(fm.M.fuel, 0.0F, 234F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_Clock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Clock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 300F, 0.0F, -60F), 0.0F);
        mesh.chunkSetAngles("Z_Pres1", 0.0F, floatindex(cvt(fm.EI.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 3F, 16F), manifoldScale), 0.0F);
        mesh.chunkSetAngles("Z_Fire1", 0.0F, 0.0F, fm.CT.saveWeaponControl[0] ? -15F : 0.0F);
        mesh.chunkSetAngles("Z_Fire5", 0.0F, 0.0F, fm.CT.saveWeaponControl[0] ? -15F : 0.0F);
        mesh.chunkSetAngles("Z_Fire3", 0.0F, 0.0F, fm.CT.saveWeaponControl[2] ? -15F : 0.0F);
        mesh.chunkSetAngles("Z_Bomb", 0.0F, 0.0F, fm.CT.saveWeaponControl[3] ? 15F : 0.0F);
        mesh.chunkSetAngles("Z_Fire4", 0.0F, 0.0F, fm.CT.saveWeaponControl[1] ? -15F : 0.0F);
        mesh.chunkSetAngles("Z_Fire2", 0.0F, 0.0F, fm.CT.saveWeaponControl[1] ? -15F : 0.0F);
        f1 = cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, -130F);
        mesh.chunkSetAngles("Z_Tinter", 0.0F, 0.0F, f1);
        mesh.chunkSetAngles("Z_Tinter_I", 0.0F, 0.0F, f1);
        mesh.chunkSetAngles("Z_BoxTinter", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Z_GS", f1 / 2.0F, 0.0F, 0.0F);
        f1 = fm.CT.getCockpitDoor();
        mesh.chunkSetAngles("Z_BlisterR1", 0.0F, 0.0F, f1 * 177.7F);
        mesh.chunkSetAngles("Z_BlisterR2", 0.0F, 0.0F, f1 * 15.6F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Gauges1_D0", false);
            mesh.chunkVisible("Gauges1_D1", true);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_Fuel", false);
            mesh.chunkVisible("Z_Clock1a", false);
            mesh.chunkVisible("Z_Clock1b", false);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_Pres1", false);
            mesh.chunkVisible("Z_Oilpres1", false);
            mesh.chunkVisible("Z_Fuelpres1", false);
            mesh.chunkVisible("Z_Oil1", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("Gauges2_D0", false);
            mesh.chunkVisible("Gauges2_D1", true);
            mesh.chunkVisible("Z_Temp1", false);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_TurnBank1", false);
            mesh.chunkVisible("Z_TurnBank2", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0);
        if((fm.AS.astateCockpitState & 0x10) != 0);
        if((fm.AS.astateCockpitState & 0x20) != 0);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("CF_D0_00", hiermesh.isChunkVisible("CF_D0"));
        mesh.chunkVisible("CF_D1_00", hiermesh.isChunkVisible("CF_D1"));
        mesh.chunkVisible("CF_D2_00", hiermesh.isChunkVisible("CF_D2"));
        mesh.chunkVisible("WingLMid_D0_00", !hiermesh.isChunkVisible("WingLMid_Cap"));
        mesh.chunkVisible("WingRMid_D0_00", !hiermesh.isChunkVisible("WingRMid_Cap"));
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        mesh.materialReplace("Gloss1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D0o"));
        mesh.materialReplace("Matt2D0o", mat);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        setNightMats(cockpitLightControl);
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    public void doToggleAim(boolean flag)
    {
        if(isFocused())
            if(flag && !isIron)
            {
                com.maddox.il2.engine.Loc loc = new Loc();
                com.maddox.il2.engine.Loc loc1 = new Loc();
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.computePos(this, loc, loc1);
                float f = loc1.getOrient().getYaw();
                if(hookpilot.isPadlock() || f > -scopeZoomArea && f < scopeZoomArea && isToggleAim() != flag)
                    enterScope();
                else
                if(f < -scopeZoomArea)
                {
                    hookpilot.doAim(true);
                    hookpilot.setAim(new Point3d(-1.6799999475479126D, -0.079999998211860657D, 0.84799998998641968D));
                    isIron = true;
                } else
                if(f > scopeZoomArea)
                {
                    hookpilot.doAim(true);
                    hookpilot.setAim(new Point3d(-1.6799999475479126D, 0.079999998211860657D, 0.84799998998641968D));
                    isIron = true;
                }
            } else
            {
                isIron = false;
                leave();
            }
    }

    private void enterScope()
    {
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        hookpilot.setAim(new Point3d(0.18400000035762787D, 0.0D, 0.84810000658035278D));
        hookpilot.doAim(true);
        bEntered = true;
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 31");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        mesh.chunkVisible("SuperReticle", true);
        mesh.chunkVisible("Z_BoxTinter", true);
    }

    private void leave()
    {
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        hookpilot.doAim(false);
        hookpilot.setAim(new Point3d(0.19830000400543213D, -0.0043999999761581421D, 0.84810000658035278D));
        if(bEntered)
        {
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            mesh.chunkVisible("SuperReticle", false);
            mesh.chunkVisible("Z_BoxTinter", false);
        }
    }

    protected boolean doFocusEnter()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        hiermesh.chunkVisible("Wire_D0", false);
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        hiermesh.chunkVisible("Wire_D0", true);
        if(isFocused())
        {
            leave();
            super.doFocusLeave();
        }
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

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private float pictSupc;
    private float saveFov;
    private float scopeZoomArea;
    private boolean isIron;
    private boolean bEntered;
    private float rpmGeneratedPressure;
    private float oilPressure;
    private com.maddox.il2.engine.LightPointActor lights[] = {
        null, null, null, null
    };
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 18F, 45F, 75.5F, 107F, 137.5F, 170F, 206.5F, 243.75F, 
        286.5F, 329.5F, 374.5F
    };
    private static final float rpmScale[] = {
        0.0F, 5.5F, 18.5F, 59F, 99.5F, 134.5F, 165.75F, 198F, 228F, 255.5F, 
        308F, 345F
    };
    private static final float manifoldScale[] = {
        0.0F, 0.0F, 0.0F, 0.0F, 26F, 52F, 79F, 106F, 132F, 160F, 
        185F, 208F, 235F, 260F, 286F, 311F, 336F
    };
    private boolean isSlideRight;












}
