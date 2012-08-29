package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitHS123 extends CockpitPilot
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
            if(((HS123)aircraft()).bChangedPit)
            {
                reflectPlaneToModel();
                ((HS123)aircraft()).bChangedPit = false;
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


    public CockpitHS123()
    {
        super("3DO/Cockpit/HS123/hier.him", "u2");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictSupc = 0.0F;
        pictFlap = 0.0F;
        cockpitNightMats = (new java.lang.String[] {
            "z_clocks", "z_clocks2", "z_clocks3", "z_clocks4", "z_clocks5"
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
        if(((HS123)aircraft()).bChangedPit)
        {
            reflectPlaneToModel();
            ((HS123)aircraft()).bChangedPit = false;
        }
        if(gun[0] == null)
        {
            gun[0] = ((Aircraft)fm.actor).getGunByHookName("_MGUN01");
            gun[1] = ((Aircraft)fm.actor).getGunByHookName("_MGUN02");
        }
        mesh.chunkSetAngles("Z_Column", 4F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 2.0F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        resetYPRmodifier();
        if(fm.CT.saveWeaponControl[0])
            Cockpit.xyz[2] = -0.01115F;
        mesh.chunkSetLocate("Z_Column_but", Cockpit.xyz, Cockpit.ypr);
        mesh.chunkSetAngles("Pedals", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Ped_trossL", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Ped_trossR", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle", 100F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoSwitch", 27F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mix", 100F * interp(setNew.mix, setOld.mix, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PropPitch1", 90F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flap", -180F * (pictFlap = 0.85F * pictFlap + 0.15F * fm.CT.FlapsControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim", 1444F * fm.CT.getTrimAileronControl(), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[2] = 0.015F * fm.CT.getTrimAileronControl();
        mesh.chunkSetLocate("Z_Trim1", Cockpit.xyz, Cockpit.ypr);
        mesh.chunkSetAngles("Z_OilRad", 70F * fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OverPres", 70F * fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 500F, 0.0F, 10F), speedometerScale), 0.0F, 0.0F);
        float f1 = interp(setNew.altimeter, setOld.altimeter, f);
        if(f1 < 5000F)
            mesh.chunkSetAngles("Z_Altimeter1", cvt(f1, 0.0F, 5000F, 0.0F, 360F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Altimeter1", cvt(f1, 5000F, 11000F, 360F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", cvt(setNew.vspeed, -10F, 10F, -180F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 5000F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_EngTemp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 150F, 0.0F, 305.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilTemp1", floatindex(cvt(fm.EI.engines[0].tOilOut, 0.0F, 150F, 0.0F, 5F), oilScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPress1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 3F, 0.0F, 6F, 0.0F, 297F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelQuantity1", cvt(fm.M.fuel, 0.0F, 252F, 0.0F, -316.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        if(gun[0] != null)
            mesh.chunkSetAngles("Z_AmmoCounter1", cvt(gun[0].countBullets(), 0.0F, 600F, 0.0F, 338F), 0.0F, 0.0F);
        if(gun[1] != null)
            mesh.chunkSetAngles("Z_AmmoCounter2", cvt(gun[1].countBullets(), 0.0F, 600F, 0.0F, 338F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Turn1", cvt(setNew.turn, -0.2F, 0.2F, 35F, -35F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Turn2", 0.0F, cvt(getBall(8D), -8F, 8F, -16.4F, 16.4F), 0.0F);
        mesh.chunkSetAngles("Z_ManfoldPress", cvt(fm.EI.engines[0].getManifoldPressure(), 0.533288F, 1.33322F, 0.0F, 330F), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
        {
            mesh.chunkVisible("glass", false);
            mesh.chunkVisible("glass_d1", true);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("pricel", false);
            mesh.chunkVisible("pricel_d1", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("panel", false);
            mesh.chunkVisible("panel_d1", true);
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_d1", true);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Turn1", false);
            mesh.chunkVisible("Z_Altimeter1", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XHullDamage1", true);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("XHullDamage3", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XHullDamage2", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XHullDamage3", true);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("WingLMid_D0", hiermesh.isChunkVisible("WingLMid_D0"));
        mesh.chunkVisible("WingLMid_D1", hiermesh.isChunkVisible("WingLMid_D1"));
        mesh.chunkVisible("WingLMid_D2", hiermesh.isChunkVisible("WingLMid_D2"));
        mesh.chunkVisible("WingLMid_D3", hiermesh.isChunkVisible("WingLMid_D3"));
        mesh.chunkVisible("WingLMid_CAP", hiermesh.isChunkVisible("WingLMid_CAP"));
        mesh.chunkVisible("WingRMid_D0", hiermesh.isChunkVisible("WingRMid_D0"));
        mesh.chunkVisible("WingRMid_D1", hiermesh.isChunkVisible("WingRMid_D1"));
        mesh.chunkVisible("WingRMid_D2", hiermesh.isChunkVisible("WingRMid_D2"));
        mesh.chunkVisible("WingRMid_D3", hiermesh.isChunkVisible("WingRMid_D3"));
        mesh.chunkVisible("WingRMid_CAP", hiermesh.isChunkVisible("WingRMid_CAP"));
        mesh.chunkVisible("CF_D0", hiermesh.isChunkVisible("CF_D0"));
        mesh.chunkVisible("CF_D1", hiermesh.isChunkVisible("CF_D1"));
        mesh.chunkVisible("CF_D2", hiermesh.isChunkVisible("CF_D2"));
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
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D1o"));
        mesh.materialReplace("Matt1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D2o"));
        mesh.materialReplace("Matt2D2o", mat);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null
    };
    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private float pictSupc;
    private float pictFlap;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 38F, 76.5F, 116F, 156F, 195F, 234F, 271F, 308.5F, 
        326F
    };
    private static final float oilScale[] = {
        0.0F, 36.5F, 53.5F, 103F, 194.5F, 332F
    };












}
