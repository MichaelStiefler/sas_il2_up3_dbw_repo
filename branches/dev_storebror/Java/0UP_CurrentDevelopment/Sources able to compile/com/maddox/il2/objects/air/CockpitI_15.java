package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
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
import com.maddox.util.HashMapExt;

public class CockpitI_15 extends CockpitPilot
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
            if((I_153_M62)aircraft() == null);
            if(I_153_M62.bChangedPit)
            {
                reflectPlaneToModel();
                if((I_153_M62)aircraft() == null);
                I_153_M62.bChangedPit = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
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
        if(isFocused())
        {
            leave();
            super.doFocusLeave();
        }
    }

    private void enter()
    {
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 31");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
        mesh.chunkVisible("SuperReticle", true);
        mesh.chunkVisible("Fonar", false);
        mesh.chunkVisible("target", false);
        mesh.chunkVisible("Pricel_D0", false);
        mesh.chunkVisible("truba", false);
    }

    private void leave()
    {
        if(bEntered)
        {
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
            mesh.chunkVisible("SuperReticle", false);
            mesh.chunkVisible("Fonar", true);
            mesh.chunkVisible("target", true);
            mesh.chunkVisible("Pricel_D0", true);
            mesh.chunkVisible("truba", true);
        }
    }

    public void destroy()
    {
        leave();
        super.destroy();
    }

    public void doToggleAim(boolean flag)
    {
        if(isFocused() && isToggleAim() != flag)
            if(flag)
                enter();
            else
                leave();
    }

    public CockpitI_15()
    {
        super("3DO/Cockpit/I-15/hier.him", "u2");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictSupc = 0.0F;
        bEntered = false;
        for(int i = 0; i < 4; i++)
        {
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "_light0" + (i + 1));
            com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
            lights[i] = new LightPointActor(new LightPoint(), loc.getPoint());
            lights[i].light.setColor(0.8980392F, 0.8117647F, 0.6235294F);
            lights[i].light.setEmit(0.0F, 0.0F);
            pos.base().draw.lightMap().put("_light0" + (i + 1), lights[i]);
        }

        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("crank", 40F * interp(setNew.mix, setOld.mix, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("cr_tross", 0.0F, 0.0F, -40F * interp(setNew.mix, setOld.mix, f));
        mesh.chunkSetAngles("handle", 65F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("hand_tross", 0.0F, 0.0F, -65F * interp(setNew.throttle, setOld.throttle, f));
        mesh.chunkSetAngles("lever", -50F * pictSupc, 0.0F, 0.0F);
        mesh.chunkSetAngles("lev_tross", 0.0F, 0.0F, 50F * pictSupc);
        mesh.chunkSetAngles("magto", 44F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        mesh.chunkSetAngles("prop", -2160F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, 16F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 10.2F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("Ped_Base", 0.0F, -15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Ped_trossL", -15.65F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Ped_trossR", -15.65F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 600F, 0.0F, 12F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(fm.Or.getTangage(), -40F, 40F, 40F, -40F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        if((fm.AS.astateCockpitState & 4) == 0)
            mesh.chunkSetAngles("zRPS1a", 0.0F, floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 2400F, 0.0F, 11F), rpmScale), 0.0F);
        mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(setNew.turn, -0.2F, 0.2F, 26F, -26F), 0.0F);
        mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8D), -8F, 8F, 26F, -26F), 0.0F);
        mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 125F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 8F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zClock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zClock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zTwater1a", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 300F, 0.0F, -60F), 0.0F);
        mesh.chunkSetAngles("zManifold1a", 0.0F, floatindex(cvt(fm.EI.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 3F, 16F), manifoldScale), 0.0F);
        mesh.chunkSetAngles("Fire1", 0.0F, 0.0F, fm.CT.saveWeaponControl[0] ? -15F : 0.0F);
        mesh.chunkSetAngles("Fire2", 0.0F, 0.0F, fm.CT.saveWeaponControl[2] ? -15F : 0.0F);
        mesh.chunkSetAngles("Fire3", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Fire4", 0.0F, 0.0F, fm.CT.saveWeaponControl[3] ? -15F : 0.0F);
        mesh.chunkSetAngles("Fire5", 0.0F, 0.0F, fm.CT.saveWeaponControl[1] ? -15F : 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("pricel", false);
            mesh.chunkVisible("pricel_d1", true);
            mesh.chunkVisible("Z_Holes2_D1", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_dd", true);
            mesh.chunkVisible("zSpeed1a", false);
            mesh.chunkVisible("zTOilOut1a", false);
            mesh.chunkVisible("zOilPrs1a", false);
            mesh.chunkVisible("zGasPrs1a", false);
            mesh.chunkVisible("zVariometer1a", false);
            mesh.chunkVisible("zManifold1a", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_dd", true);
            mesh.chunkVisible("zAlt1a", false);
            mesh.chunkVisible("zAlt1b", false);
            mesh.chunkVisible("zTurn1a", false);
            mesh.chunkVisible("zSlide1a", false);
            mesh.chunkVisible("zTwater1a", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0);
        if((fm.AS.astateCockpitState & 0x10) != 0);
        if((fm.AS.astateCockpitState & 0x20) != 0);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("WingLMid_D0", hiermesh.isChunkVisible("WingLMid_D0"));
        mesh.chunkVisible("WingLMid_D1", hiermesh.isChunkVisible("WingLMid_D1"));
        mesh.chunkVisible("WingLMid_D2", hiermesh.isChunkVisible("WingLMid_D2"));
        mesh.chunkVisible("WingLMid_D3", hiermesh.isChunkVisible("WingLMid_D3"));
        mesh.chunkVisible("WingRMid_D0", hiermesh.isChunkVisible("WingRMid_D0"));
        mesh.chunkVisible("WingRMid_D1", hiermesh.isChunkVisible("WingRMid_D1"));
        mesh.chunkVisible("WingRMid_D2", hiermesh.isChunkVisible("WingRMid_D2"));
        mesh.chunkVisible("WingRMid_D3", hiermesh.isChunkVisible("WingRMid_D3"));
        mesh.chunkVisible("CF_D0", hiermesh.isChunkVisible("CF_D0"));
        mesh.chunkVisible("CF_D1", hiermesh.isChunkVisible("CF_D1"));
        mesh.chunkVisible("CF_D2", hiermesh.isChunkVisible("CF_D2"));
        mesh.chunkVisible("CF_D3", hiermesh.isChunkVisible("CF_D3"));
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
        if(aircraft() instanceof I_153P)
            mesh.chunkVisible("guns_down", false);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        sfxClick(1);
        if(cockpitLightControl)
        {
            for(int i = 0; i < 4; i++)
                lights[i].light.setEmit(0.5F, 0.5F);

        } else
        {
            for(int j = 0; j < 4; j++)
                lights[j].light.setEmit(0.0F, 0.0F);

        }
    }

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private float pictSupc;
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
    private float saveFov;
    private boolean bEntered;












}
