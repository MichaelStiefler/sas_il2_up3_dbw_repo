// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitF6F5.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, F6F, Cockpit, Aircraft

public class CockpitF6F5 extends com.maddox.il2.objects.air.CockpitPilot
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
        float mix;
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


    public CockpitF6F5()
    {
        super("3DO/Cockpit/F6F-5/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bNeedSetUp = true;
        cockpitNightMats = (new java.lang.String[] {
            "Image11_Damage", "Image11", "Image12_Damage", "Image12", "Image14", "Image27_Damage", "Image27", "Image09"
        });
        setNightMats(false);
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
        com.maddox.il2.objects.air.F6F _tmp = (com.maddox.il2.objects.air.F6F)aircraft();
        if(com.maddox.il2.objects.air.F6F.bChangedPit)
        {
            reflectPlaneToModel();
            com.maddox.il2.objects.air.F6F _tmp1 = (com.maddox.il2.objects.air.F6F)aircraft();
            com.maddox.il2.objects.air.F6F.bChangedPit = false;
        }
        float f1 = fm.CT.getWing();
        mesh.chunkSetAngles("WingLMid_D0", 100F * f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("WingRMid_D0", -100F * f1, 0.0F, 0.0F);
        f1 = -50F * fm.CT.getFlap();
        mesh.chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.625F);
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Trim2", 180F * fm.CT.getTrimRudderControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Trim3", 180F * fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flaps1", 90F * fm.CT.FlapsControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gear1", 70F * fm.CT.GearControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 0.0F, 50F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        mesh.chunkSetAngles("Z_Prop1", 0.0F, 82F * interp(setNew.prop, setOld.prop, f), 0.0F);
        mesh.chunkSetAngles("Z_Mixture1", 0.0F, 50F * fm.EI.engines[0].getControlMix(), 0.0F);
        mesh.chunkSetAngles("Pedal_L", 0.0F, -20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal1_L", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal2_L", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal_R", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal1_R", 0.0F, -20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal2_R", 0.0F, -20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_Columnbase", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, 0.0F);
        mesh.chunkSetAngles("Z_Column", 0.0F, -(pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 7F, 0.0F);
        mesh.chunkSetAngles("Z_Supercharger1", 0.0F, 25F - 25F * (float)fm.EI.engines[0].getControlCompressor(), 0.0F);
        mesh.chunkSetAngles("Z_CowlFlap1", 0.0F, -70F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        mesh.chunkSetAngles("Z_OilCooler1", 0.0F, -70F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 1080F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 10800F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 963.0397F, 0.0F, 13F), speedometerScale), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(7D), -7F, 7F, 16F, -16F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank3", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.025F, -0.025F);
        mesh.chunkSetLocate("Z_TurnBank4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", 270F + setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass3", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 3500F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel, 0.0F, 700F, 0.0F, 74F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Manifold1", cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 343.75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oxy1", 120F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Coolant1", cvt(fm.EI.engines[0].tWaterOut, 40F, 160F, 0.0F, 120F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPres1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 295F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_AmmoCtr1", cvt(aircraft().getGunByHookName("_MGUN01").countBullets(), -500F, 1200F, -103F, 235.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_AmmoCtr2", cvt(aircraft().getGunByHookName("_MGUN02").countBullets(), -500F, 1200F, -103F, 235.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPres", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 4F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPres", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FlapInd1", cvt(fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 60F), 0.0F, 0.0F);
        if(fm.Gears.lgear)
            mesh.chunkSetAngles("Z_GearInd1", 0.0F, cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 65F), 0.0F);
        if(fm.Gears.rgear)
            mesh.chunkSetAngles("Z_GearInd2", 0.0F, cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, -65F), 0.0F);
        mesh.chunkSetAngles("Z_GearInd3", 0.0F, cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 80F), 0.0F);
        mesh.chunkSetAngles("Z_AirPres", cvt(12.4F, 0.0F, 20F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_HydroPres", cvt(fm.Gears.isHydroOperable() ? 7.5F : 0.0F, 0.0F, 20F, 0.0F, 180F), 0.0F, 0.0F);
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
        if((fm.AS.astateCockpitState & 1) == 0);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("Z_Hour1", false);
            mesh.chunkVisible("Z_Minute1", false);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_Fuel1", false);
            mesh.chunkVisible("Z_Temp1", false);
            mesh.chunkVisible("Z_OilPres", false);
            mesh.chunkVisible("Z_FuelPres", false);
        }
        if((fm.AS.astateCockpitState & 4) == 0);
        if((fm.AS.astateCockpitState & 8) == 0);
        if((fm.AS.astateCockpitState & 0x10) == 0);
        if((fm.AS.astateCockpitState & 0x20) == 0);
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
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D1o"));
        mesh.materialReplace("Matt1D1o", mat);
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
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 17F, 56.5F, 107.5F, 157F, 204F, 220.5F, 238.5F, 256.5F, 274.5F, 
        293F, 311F, 330F, 342F
    };
    private static final float variometerScale[] = {
        -170F, -147F, -124F, -101F, -78F, -48F, 0.0F, 48F, 78F, 101F, 
        124F, 147F, 170F
    };







}
