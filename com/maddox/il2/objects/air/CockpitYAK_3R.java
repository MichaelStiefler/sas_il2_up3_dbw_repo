// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitYAK_3R.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
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
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitYAK_3R extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.prop = (10F * setOld.prop + fm.EI.engines[0].getControlProp()) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + fm.Or.azimut()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                setNew.preved = 0.99F * setOld.preved + 0.01F * (fm.EI.engines[1].getStage() != 6 ? 55F : 800F);
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
        float preved;

        private Variables()
        {
        }

    }


    public CockpitYAK_3R()
    {
        super("3DO/Cockpit/Yak-3R/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        light1 = new LightPointActor(new LightPoint(), new Point3d(-0.3075D, 0.3392D, 0.29560000000000003D));
        light2 = new LightPointActor(new LightPoint(), new Point3d(-0.20950000000000002D, 0.28420000000000001D, 0.26549999999999996D));
        light3 = new LightPointActor(new LightPoint(), new Point3d(-0.037000000000000005D, 0.20100000000000001D, 0.20449999999999999D));
        light4 = new LightPointActor(new LightPoint(), new Point3d(-0.3075D, -0.3392D, 0.29560000000000003D));
        light5 = new LightPointActor(new LightPoint(), new Point3d(-0.20950000000000002D, -0.28420000000000001D, 0.26549999999999996D));
        light6 = new LightPointActor(new LightPoint(), new Point3d(-0.037000000000000005D, -0.20100000000000001D, 0.20449999999999999D));
        light1.light.setColor(245F, 221F, 189F);
        light2.light.setColor(245F, 221F, 189F);
        light3.light.setColor(245F, 221F, 189F);
        light4.light.setColor(245F, 221F, 189F);
        light5.light.setColor(245F, 221F, 189F);
        light6.light.setColor(245F, 221F, 189F);
        light1.light.setEmit(0.0F, 0.0F);
        light2.light.setEmit(0.0F, 0.0F);
        light3.light.setEmit(0.0F, 0.0F);
        light4.light.setEmit(0.0F, 0.0F);
        light5.light.setEmit(0.0F, 0.0F);
        light6.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        pos.base().draw.lightMap().put("LAMPHOOK2", light2);
        pos.base().draw.lightMap().put("LAMPHOOK3", light3);
        pos.base().draw.lightMap().put("LAMPHOOK4", light4);
        pos.base().draw.lightMap().put("LAMPHOOK5", light5);
        pos.base().draw.lightMap().put("LAMPHOOK6", light6);
        cockpitNightMats = (new java.lang.String[] {
            "prib_one", "prib_two", "prib_four", "prib_five"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("zKerosin", 0.0F, cvt(fm.M.nitro, 0.0F, 250F, 0.0F, -270F), 0.0F);
        mesh.chunkSetAngles("zAzotKislota", 0.0F, cvt(fm.M.nitro, 0.0F, 250F, 0.0F, -270F), 0.0F);
        mesh.chunkSetAngles("zPVRD_Temp", 0.0F, cvt(setNew.preved, 0.0F, 900F, 0.0F, -118F), 0.0F);
        mesh.chunkSetAngles("SW_PVRD", 0.0F, fm.EI.engines[1].getStage() <= 0 ? 0.0F : -45F, 0.0F);
        mesh.chunkSetAngles("richag", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Ped_Base", 0.0F, -fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("norm_gaz", 0.0F, -13F + interp(setNew.throttle, setOld.throttle, f) * 33.8F, 0.0F);
        mesh.chunkSetAngles("shag_vinta", 0.0F, interp(setNew.prop, setOld.prop, f) * 33.8F - 13F, 0.0F);
        mesh.chunkSetAngles("nadduv", 0.0F, -19F + 39F * (float)fm.EI.engines[0].getControlCompressor(), 0.0F);
        if(fm.CT.GearControl == 0.0F && fm.CT.getGear() != 0.0F)
            mesh.chunkSetAngles("shassy", 0.0F, 24F, 0.0F);
        else
        if(fm.CT.GearControl == 1.0F && fm.CT.getGear() != 1.0F)
            mesh.chunkSetAngles("shassy", 0.0F, -24F, 0.0F);
        else
            mesh.chunkSetAngles("shassy", 0.0F, 0.0F, 0.0F);
        if(java.lang.Math.abs(fm.CT.FlapsControl - fm.CT.getFlap()) > 0.02F)
        {
            if(fm.CT.FlapsControl - fm.CT.getFlap() > 0.0F)
                mesh.chunkSetAngles("shitki", 0.0F, -24F, 0.0F);
            else
                mesh.chunkSetAngles("shitki", 0.0F, 24F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("shitki", 0.0F, 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(fm.Or.getTangage(), -40F, 40F, -40F, 40F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("zClock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zClock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F);
        if((fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x20) == 0 && (fm.AS.astateCockpitState & 0x40) == 0)
        {
            w.set(fm.getW());
            fm.Or.transform(w);
            mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
            mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8D), -8F, 8F, 24F, -24F), 0.0F);
            mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
            mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        }
        mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(setNew.vspeed, -10F, 10F, -180F, 180F), 0.0F);
        if((fm.AS.astateCockpitState & 4) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
            mesh.chunkSetAngles("zGas1a", 0.0F, cvt(fm.M.fuel / 0.72F, 0.0F, 300F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(fm.EI.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.286F), 0.0F);
        mesh.chunkSetAngles("zRPM1a", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zRPM1b", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 125F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 8F, 0.0F, -180F), 0.0F);
        mesh.chunkVisible("Z_Red1", fm.M.fuel < 36F);
        mesh.chunkVisible("Z_Red2", fm.EI.engines[0].getRPM() < 650F);
        mesh.chunkVisible("Z_Green1", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_Green2", fm.CT.getGear() == 1.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_d1", true);
            mesh.chunkVisible("zManifold1a", false);
            mesh.chunkVisible("zVariometer1a", false);
            mesh.chunkVisible("zSpeed1a", false);
            mesh.chunkVisible("Z_Holes1_D1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x20) != 0 || (fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_d1", true);
            mesh.chunkVisible("zAzimuth1a", false);
            mesh.chunkVisible("zAzimuth1b", false);
            mesh.chunkVisible("zRPM1a", false);
            mesh.chunkVisible("zRPM1b", false);
            mesh.chunkVisible("zSlide1a", false);
            mesh.chunkVisible("zTOilOut1a", false);
            mesh.chunkVisible("zOilPrs1a", false);
            mesh.chunkVisible("zGasPrs1a", false);
            mesh.chunkVisible("panel", false);
            mesh.chunkVisible("panel_d1", true);
        }
        if((fm.AS.astateCockpitState & 2) != 0 || (fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.09F, 0.1F);
            light2.light.setEmit(0.02F, 0.2F);
            light3.light.setEmit(0.02F, 0.5F);
            light4.light.setEmit(0.09F, 0.1F);
            light5.light.setEmit(0.02F, 0.2F);
            light6.light.setEmit(0.02F, 0.5F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            light3.light.setEmit(0.0F, 0.0F);
            light4.light.setEmit(0.0F, 0.0F);
            light5.light.setEmit(0.0F, 0.0F);
            light6.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private com.maddox.il2.engine.LightPointActor light3;
    private com.maddox.il2.engine.LightPointActor light4;
    private com.maddox.il2.engine.LightPointActor light5;
    private com.maddox.il2.engine.LightPointActor light6;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 15.5F, 50F, 95.5F, 137F, 182.5F, 212F, 230F, 242F, 
        254.5F, 267.5F, 279F, 292F, 304F, 317F, 329.5F, 330F
    };







}
