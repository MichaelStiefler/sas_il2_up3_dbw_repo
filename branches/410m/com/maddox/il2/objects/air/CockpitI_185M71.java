// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitI_185M71.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft

public class CockpitI_185M71 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
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
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.prop = 0.85F * setOld.prop + fm.EI.engines[0].getControlProp() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                if(useRealisticNavigationInstruments())
                    setNew.waypointAzimuth = 0.0F;
                else
                    setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                if(fm.getAltitude() > 3000F)
                {
                    float f = (float)java.lang.Math.sin(1.0F * cvt(fm.getOverload(), 1.0F, 8F, 1.0F, 0.45F) * cvt(fm.AS.astatePilotStates[0], 0.0F, 100F, 1.0F, 0.1F) * (0.001F * (float)com.maddox.rts.Time.current()));
                    if(f > 0.0F)
                    {
                        pictBlinker+= = 0.3F;
                        if(pictBlinker > 1.0F)
                            pictBlinker = 1.0F;
                    } else
                    {
                        pictBlinker-= = 0.3F;
                        if(pictBlinker < 0.0F)
                            pictBlinker = 0.0F;
                    }
                }
                pictStage = 0.8F * pictStage + 0.1F * (float)fm.EI.engines[0].getControlCompressor();
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
        float azimuth;
        float vspeed;
        float waypointAzimuth;
        float xyz[] = {
            0.0F, 0.0F, 0.0F
        };
        float ypr[] = {
            0.0F, 0.0F, 0.0F
        };

        private Variables()
        {
        }

    }


    protected float waypointAzimuth()
    {
        return super.waypointAzimuthInvertMinus(10F);
    }

    public CockpitI_185M71()
    {
        super("3DO/Cockpit/I-185M-71/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        t1 = 0L;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictBlinker = 0.0F;
        pictStage = 0.0F;
        pictBDrop = 0.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "Prib_One", "Prib_Two", "Prib_Three", "Prib_Four", "Prib_Five"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(useRealisticNavigationInstruments())
        {
            mesh.materialReplace("prib_three", "EmptyGauge");
            mesh.materialReplace("prib_three_night", "EmptyGauge_night");
            mesh.chunkVisible("zRPK10", false);
            setNightMats(true);
            setNightMats(false);
        }
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkVisible("XGearDown_L", fm.CT.getGear() == 1.0F && fm.Gears.lgear);
        mesh.chunkVisible("XGearDown_C", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("XGearDown_R", fm.CT.getGear() == 1.0F && fm.Gears.rgear);
        mesh.chunkVisible("XGearUP_L", fm.CT.getGear() == 0.0F && fm.Gears.lgear);
        mesh.chunkVisible("XGearUP_R", fm.CT.getGear() == 0.0F && fm.Gears.rgear);
        if(t1 < com.maddox.rts.Time.current())
        {
            com.maddox.il2.ai.BulletEmitter bulletemitter = ((com.maddox.il2.objects.air.Aircraft)(com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb01");
            if(bulletemitter != com.maddox.il2.objects.weapons.GunEmpty.get())
                mesh.chunkVisible("XBombOnboard_L", bulletemitter.haveBullets());
            else
                mesh.chunkVisible("XBombOnboard_L", false);
            bulletemitter = ((com.maddox.il2.objects.air.Aircraft)(com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb02");
            if(bulletemitter != com.maddox.il2.objects.weapons.GunEmpty.get())
                mesh.chunkVisible("XBombOnboard_R", bulletemitter.haveBullets());
            else
                mesh.chunkVisible("XBombOnboard_R", false);
            t1 = com.maddox.rts.Time.current() + 500L;
        }
        if(java.lang.Math.abs(fm.CT.FlapsControl - fm.CT.getFlap()) > 0.02F)
        {
            if(fm.CT.FlapsControl - fm.CT.getFlap() > 0.0F)
                mesh.chunkSetAngles("FlapHandle", 5F, 0.0F, 0.0F);
            else
                mesh.chunkSetAngles("FlapHandle", -5F, 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, 0.0F);
        }
        if(fm.CT.GearControl == 0.0F && fm.CT.getGear() != 0.0F)
            mesh.chunkSetAngles("GearHandle", -16F, 0.0F, 0.0F);
        else
        if(fm.CT.GearControl == 1.0F && fm.CT.getGear() != 1.0F)
            mesh.chunkSetAngles("GearHandle", 16F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("IgnitionSwitch", -23F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        if(fm.EI.engines[0].getStage() == 0)
            mesh.chunkSetAngles("IgnitionSwitch", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("TQHandle", 16.3F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("MixtureHandle", -18.3F * fm.EI.engines[0].getControlMix(), 0.0F, 0.0F);
        mesh.chunkSetAngles("PropPitchHandle", -21F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("ChargerHandle", fm.EI.engines[0].getStage() >= 4 ? -22F * pictStage : 0.0F, 0.0F, 0.0F);
        if(fm.CT.saveWeaponControl[3])
        {
            mesh.chunkSetAngles("BSLHandle", 22F, 0.0F, 0.0F);
            mesh.chunkSetAngles("BSLClamp", -11F, 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("CStick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Ped_Base", fm.CT.getRudder() * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("PedalL", -fm.CT.getRudder() * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("PedalR", -fm.CT.getRudder() * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("PShad_L", -fm.CT.getRudder() * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("PShad_R", -fm.CT.getRudder() * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Tross_L", -fm.CT.getRudder() * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Tross_R", -fm.CT.getRudder() * 20F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zSpeed1a", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt1a", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt1b", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("zTurn1a", cvt(w.z, -0.23562F, 0.23562F, 25F, -25F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zSlide1a", -cvt(getBall(8D), -8F, 8F, 25F, -25F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zManifold1a", floatindex(cvt(fm.EI.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 3F, 16F), manifoldScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPM1a", cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPM1b", cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zTOilOut1a", cvt(fm.EI.engines[0].tOilOut, 0.0F, 125F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilPrs1a", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGasPrs1a", cvt(fm.M.fuel <= 1.0F ? 0.0F : cvt(fm.EI.engines[0].getRPM(), 0.0F, 3050F, 0.0F, 4F), 0.0F, 8F, 0.0F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGas1a", cvt(fm.M.fuel / 0.725F, 0.0F, 300F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zRPK10", cvt(interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), -25F, 25F, 35F, -35F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zClock1a", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zClock1b", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zVariometer1a", floatindex(cvt(setNew.vspeed, -30F, 30F, 0.0F, 6F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("zAzimuth1a", 0.0F, 0.0F, cvt(fm.Or.getTangage(), -40F, 40F, 40F, -40F));
        mesh.chunkSetAngles("zAzimuth1b", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        resetYPRmodifier();
        setOld.xyz[1] = cvt(fm.Or.getTangage(), -40F, 40F, 0.03F, -0.03F);
        mesh.chunkSetLocate("zHorizon1a", setOld.xyz, setOld.ypr);
        mesh.chunkSetAngles("zHorizon1b", fm.Or.getKren(), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCylHead", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 300F, 0.0F, 70F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCarbpress", cvt(0.6F * fm.CT.getPower() + 0.6F * (fm.EI.engines[0].getw() / 3000F), 0.0F, 1.5F, 0.0F, 105F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOiltemper_I", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, -293F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOiltemper_II", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, -293F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOiltemper_III", cvt(fm.EI.engines[0].tOilIn, 0.0F, 100F, 0.0F, -293F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilpress", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 296F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zPitchprop", cvt(fm.EI.engines[0].getPropAoA(), 0.0F, 12F, 0.0F, -225F), 0.0F, 0.0F);
        resetYPRmodifier();
        if(fm.Gears.rgear)
            xyz[0] = 0.08F * fm.CT.getGear();
        mesh.chunkSetLocate("zRGear_ind", xyz, ypr);
        resetYPRmodifier();
        if(fm.Gears.lgear)
            xyz[0] = 0.08F * fm.CT.getGear();
        mesh.chunkSetLocate("zLGear_ind", xyz, ypr);
        float f1 = 25F;
        if(fm.AS.bLandingLightOn)
            f1 = 0.0F;
        mesh.chunkSetAngles("SW_LandLight", f1, 0.0F, 0.0F);
        if(cockpitLightControl)
            f1 = 25F;
        else
            f1 = 0.0F;
        mesh.chunkSetAngles("SW_UVLight", f1, 0.0F, 0.0F);
        if(fm.AS.bNavLightsOn)
            f1 = 25F;
        else
            f1 = 0.0F;
        mesh.chunkSetAngles("SW_NavLight", f1, 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
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
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private long t1;
    private float pictAiler;
    private float pictElev;
    private float pictBlinker;
    private float pictStage;
    private float pictBDrop;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 15.5F, 50F, 95.5F, 137F, 182.5F, 212F, 230F, 242F, 
        254.5F, 267.5F, 279F, 292F, 304F, 317F, 329.5F, 330F
    };
    private static final float manifoldScale[] = {
        0.0F, 0.0F, 0.0F, 0.0F, 26F, 52F, 79F, 106F, 132F, 160F, 
        185F, 208F, 235F, 260F, 286F, 311F, 336F
    };
    private static final float variometerScale[] = {
        -180F, -90F, -45F, 0.0F, 45F, 90F, 180F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;















}
