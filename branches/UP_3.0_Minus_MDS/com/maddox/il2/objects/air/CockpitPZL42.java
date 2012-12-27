// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitPZL42.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, PZL42

public class CockpitPZL42 extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float azimuth;
        float mix;
        float throttle;
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
            com.maddox.il2.objects.air.PZL42 pzl42 = (com.maddox.il2.objects.air.PZL42)aircraft();
            if(com.maddox.il2.objects.air.PZL42.bChangedPit)
            {
                reflectPlaneToModel();
                com.maddox.il2.objects.air.PZL42 pzl42_1 = (com.maddox.il2.objects.air.PZL42)aircraft();
                com.maddox.il2.objects.air.PZL42.bChangedPit = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
            if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getKren()) < 30F)
                setNew.azimuth = (35F * setOld.azimuth + ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).azimut()) / 36F;
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.mix = (10F * setOld.mix + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix()) / 11F;
            setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
            ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (fm)).getW())));
            ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
            setNew.turn = (33F * setOld.turn + ((com.maddox.JGP.Tuple3f) (w)).z) / 34F;
            setNew.vspeed = (199F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 200F;
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitPZL42()
    {
        super("3DO/Cockpit/PZL42/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (((com.maddox.il2.objects.air.Cockpit)this).mesh)), "LAMPHOOK1");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(0.8980392F, 0.8117647F, 0.9235294F);
        light1.light.setEmit(0.0F, 0.0F);
        ((com.maddox.il2.engine.Actor)this).pos.base().draw.lightMap().put("LAMPHOOK1", ((java.lang.Object) (light1)));
        hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (((com.maddox.il2.objects.air.Cockpit)this).mesh)), "LAMPHOOK2");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(0.8980392F, 0.8117647F, 0.9235294F);
        light2.light.setEmit(0.0F, 0.0F);
        ((com.maddox.il2.engine.Actor)this).pos.base().draw.lightMap().put("LAMPHOOK2", ((java.lang.Object) (light2)));
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAlt1", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAlt2", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 1000F, 0.0F, 360F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zSpeed", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 500F, 0.0F, 25F), speedometerScale), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zBoost", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getManifoldPressure(), 0.72421F, 1.27579F, -160F, 160F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zMinute", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zHour", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zCompass1", 0.0F, 90F + ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zCompass2", 0.0F, 90F + ((com.maddox.il2.objects.air.Cockpit)this).interp(-setNew.azimuth, -setOld.azimuth, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick", 0.0F, 16F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 12F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zFuelPrs", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 8F, 0.0F, 270F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zOilPrs", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 270F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zOilIn", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilIn, 20F, 140F, 29F, 330F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zOilOut", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 20F, 140F, 29F, 330F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zMagnetoSwitch", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 90F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -20F, 20F, 0.0485F, -0.0485F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("zPitch", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zRPM", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 3000F, 0.0F, 317F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Rudder", 26F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zTurn", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.turn, -0.6F, 0.6F, 1.8F, -1.8F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zSlide1", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(5D), -5F, 5F, -8F, 8F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zSlide2", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(3D), -3F, 3F, -6F, 6F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Mixture", 0.0F, 0.0F, -33F + 30F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.mix, setOld.mix, f));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Throttle", 0.0F, 0.0F, -55F + 50F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("z_AH", 0.0F, -((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zVspeed", ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -18F, 18F, -180F, 180F), 0.0F, 0.0F);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Engine1_D0", hiermesh.isChunkVisible("Engine1_D0"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Engine1_D1", hiermesh.isChunkVisible("Engine1_D1"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Engine1_D2", hiermesh.isChunkVisible("Engine1_D2"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingLIn_D0", hiermesh.isChunkVisible("WingLIn_D0"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingLIn_D1", hiermesh.isChunkVisible("WingLIn_D1"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingLIn_D2", hiermesh.isChunkVisible("WingLIn_D2"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingLIn_D3", hiermesh.isChunkVisible("WingLIn_D3"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingRIn_D0", hiermesh.isChunkVisible("WingRIn_D0"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingRIn_D1", hiermesh.isChunkVisible("WingRIn_D1"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingRIn_D2", hiermesh.isChunkVisible("WingRIn_D2"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingRIn_D3", hiermesh.isChunkVisible("WingRIn_D3"));
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D2o", mat);
    }

    public void toggleLight()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl;
        if(((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl)
        {
            light1.light.setEmit(0.5F, 0.5F);
            light2.light.setEmit(0.5F, 0.5F);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
        }
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 1.0F, 3F, 6.2F, 12F, 26.5F, 39F, 51F, 67.5F, 85.5F, 
        108F, 131.5F, 154F, 180F, 205.7F, 228.2F, 251F, 272.9F, 291.9F, 314.5F, 
        336.5F, 354F, 360F, 363F, 364F, 365F
    };










}