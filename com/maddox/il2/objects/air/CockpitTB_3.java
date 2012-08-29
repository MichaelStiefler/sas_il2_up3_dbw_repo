// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitTB_3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
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
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft

public class CockpitTB_3 extends com.maddox.il2.objects.air.CockpitPilot
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
                for(int i = 0; i < 4; i++)
                    setNew.throttle[i] = (10F * setOld.throttle[i] + fm.EI.engines[i].getControlThrottle()) / 11F;

                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                if(useRealisticNavigationInstruments())
                    setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + getBeaconDirection()) / 11F;
                else
                    setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth)) / 11F;
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

        float throttle[] = {
            0.0F, 0.0F, 0.0F, 0.0F
        };
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;

        private Variables()
        {
        }

    }


    protected float waypointAzimuth()
    {
        return super.waypointAzimuthInvertMinus(10F);
    }

    public CockpitTB_3()
    {
        super("3DO/Cockpit/TB-3/hier.him", "i16");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        light1 = new LightPointActor(new LightPoint(), new Point3d(3.6749499999999999D, 0.72745000000000004D, 1.04095D));
        light2 = new LightPointActor(new LightPoint(), new Point3d(3.6749499999999999D, -0.77925D, 1.04095D));
        light1.light.setColor(0.9607843F, 0.8666667F, 0.7411765F);
        light2.light.setColor(0.9607843F, 0.8666667F, 0.7411765F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        pos.base().draw.lightMap().put("LAMPHOOK2", light2);
        cockpitNightMats = (new java.lang.String[] {
            "Bombgauges", "Gauge02", "Gauge03", "Instr01", "Instr01_dd", "Instr02", "Instr02_dd", "oxigen"
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
        mesh.chunkSetAngles("Z_Column", 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F);
        mesh.chunkSetAngles("Z_AroneL", 0.0F, -115F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F);
        mesh.chunkSetAngles("Z_AroneR", 0.0F, -115F * pictAiler, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", 0.0F, -25F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 25F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_RPedalStep", 0.0F, -25F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_LPedalStep", 0.0F, 25F * fm.CT.getRudder(), 0.0F);
        for(int i = 0; i < 4; i++)
        {
            mesh.chunkSetAngles("Z_Throttle" + (i + 1), 0.0F, -90F * interp(setNew.throttle[i], setOld.throttle[i], f), 0.0F);
            mesh.chunkSetAngles("Z_Throtlev" + (i + 1), 0.0F, -90F * interp(setNew.throttle[i], setOld.throttle[i], f), 0.0F);
        }

        mesh.chunkSetAngles("Z_Compass1", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Compass2", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter3", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter4", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 50F, 400F, 1.0F, 8F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Speedometer2", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 50F, 400F, 1.0F, 8F), speedometerScale), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 22F, -22F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 22F, -22F), 0.0F);
        mesh.chunkSetAngles("Z_Horizon1", 0.0F, fm.Or.getKren(), 0.0F);
        mesh.chunkSetAngles("Z_Horizon2", 0.0F, fm.Or.getKren(), 0.0F);
        resetYPRmodifier();
        xyz[2] = cvt(fm.Or.getTangage(), -35F, 35F, 0.028F, -0.028F);
        mesh.chunkSetLocate("Z_Tangage1", xyz, ypr);
        mesh.chunkSetLocate("Z_Tangage2", xyz, ypr);
        mesh.chunkSetAngles("Z_Variometr", 0.0F, cvt(setNew.vspeed, -40F, 40F, -180F, 180F), 0.0F);
        for(int j = 0; j < 4; j++)
            mesh.chunkSetAngles("Z_RPM" + (j + 1), 0.0F, floatindex(cvt(fm.EI.engines[j].getRPM(), 400F, 2400F, 2.0F, 13F), engineRPMScale), 0.0F);

        mesh.chunkSetAngles("Z_RPK1", 0.0F, cvt(interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), -25F, 25F, 30F, -30F), 0.0F);
        mesh.chunkSetAngles("Z_RPK2", 0.0F, cvt(interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), -25F, 25F, 30F, -30F), 0.0F);
        mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("HullDamage3", true);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("HullDamage4", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("HullDamage1", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("HullDamage2", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("HullDamage3", true);
            mesh.materialReplace("Instr01", "Instr01_dd");
            mesh.materialReplace("Instr01_night", "Instr01_dd_night");
            mesh.materialReplace("Instr02", "Instr02_dd");
            mesh.materialReplace("Instr02_night", "Instr02_dd_night");
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_Speedometer2", false);
            mesh.chunkVisible("Z_TurnBank1", false);
            mesh.chunkVisible("Z_TurnBank2", false);
            mesh.chunkVisible("Z_Variometr", false);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Altimeter3", false);
            mesh.chunkVisible("Z_Altimeter4", false);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage3", true);
        }
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.engine.ActorHMesh)(com.maddox.il2.engine.ActorHMesh)fm.actor).hierMesh().chunkVisible("Windscreen_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        ((com.maddox.il2.engine.ActorHMesh)(com.maddox.il2.engine.ActorHMesh)fm.actor).hierMesh().chunkVisible("Windscreen_D0", true);
        super.doFocusLeave();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.98F, 0.45F);
            light2.light.setEmit(0.98F, 0.45F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 52F, 91F, 139.5F, 190F, 249.5F, 308F, 360F
    };
    private static final float engineRPMScale[] = {
        0.0F, 0.0F, 0.0F, 40F, 80.5F, 115.3F, 145.5F, 177.6F, 206.5F, 234.5F, 
        261F, 287F, 320F, 358.5F
    };









}
