// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitPZL37.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
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
import com.maddox.il2.fm.Autopilotage;
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
//            CockpitPilot, Cockpit, Aircraft

public class CockpitPZL37 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle1 = 0.9F * setOld.throttle1 + 0.1F * fm.EI.engines[0].getControlThrottle();
                setNew.prop1 = 0.9F * setOld.prop1 + 0.1F * fm.EI.engines[0].getControlProp();
                setNew.mix1 = 0.8F * setOld.mix1 + 0.2F * fm.EI.engines[0].getControlMix();
                setNew.man1 = 0.92F * setOld.man1 + 0.08F * fm.EI.engines[0].getManifoldPressure();
                setNew.throttle2 = 0.9F * setOld.throttle2 + 0.1F * fm.EI.engines[1].getControlThrottle();
                setNew.prop2 = 0.9F * setOld.prop2 + 0.1F * fm.EI.engines[1].getControlProp();
                setNew.mix2 = 0.8F * setOld.mix2 + 0.2F * fm.EI.engines[1].getControlMix();
                setNew.man2 = 0.92F * setOld.man2 + 0.08F * fm.EI.engines[1].getManifoldPressure();
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = (100F * setOld.vspeed + fm.getVertSpeed()) / 101F;
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                setNew.waypointDirection.setDeg(setOld.waypointDirection.getDeg(1.0F), f);
                setNew.inert = 0.999F * setOld.inert + 0.001F * (fm.EI.engines[0].getStage() != 6 ? 0.0F : 0.867F);
                w.set(fm.getW());
                fm.Or.transform(w);
                setNew.turn = (33F * setOld.turn + w.z) / 34F;
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
        float throttle2;
        float prop1;
        float prop2;
        float mix1;
        float mix2;
        float man1;
        float man2;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        com.maddox.il2.ai.AnglesFork waypointDirection;
        float vspeed;
        float inert;
        float turn;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
            waypointDirection = new AnglesFork();
        }

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

    public CockpitPZL37()
    {
        super("3DO/Cockpit/PZLP-37BLOSII/CockpitPZL37.him", "he111");
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "GP_I", "GP_II", "GP_II_DM", "GP_III_DM", "GP_III", "GP_IV_DM", "GP_IV", "GP_V", "GP_VI", "GP_Vii", 
            "Compass", "Eqpt_II", "Trans_II", "Trans_VI_Pilot", "Trans_VII_Pilot"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        light1 = new LightPointActor(new LightPoint(), new Point3d(2.3799999999999999D, 0.14999999999999999D, 0.40000000000000002D));
        light2 = new LightPointActor(new LightPoint(), new Point3d(2.3599999999999999D, -0.25D, 0.40000000000000002D));
        light3 = new LightPointActor(new LightPoint(), new Point3d(2.3599999999999999D, 0.27000000000000002D, 0.34000000000000002D));
        light4 = new LightPointActor(new LightPoint(), new Point3d(2.3500000000000001D, 0.28000000000000003D, 0.28000000000000003D));
        float f = 0.6F;
        light1.light.setColor(f * 0.96F, f * 0.87F, f * 0.74F);
        light2.light.setColor(f * 0.96F, f * 0.87F, f * 0.74F);
        light3.light.setColor(f * 0.96F, f * 0.87F, f * 0.74F);
        light4.light.setColor(f * 0.96F, f * 0.87F, f * 0.74F);
        light1.light.setEmit(0.0F, 0.0F);
        light2.light.setEmit(0.0F, 0.0F);
        light3.light.setEmit(0.0F, 0.0F);
        light4.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1R", light1);
        pos.base().draw.lightMap().put("LAMPHOOK2R", light2);
        pos.base().draw.lightMap().put("LAMPHOOK3R", light3);
        pos.base().draw.lightMap().put("LAMPHOOK4R", light4);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("Z_Gear1", fm.CT.GearControl <= 0.5F ? 0.0F : -60F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", -31F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle2", -31F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prop1", -720F * interp(setNew.prop1, setOld.prop1, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prop2", -720F * interp(setNew.prop2, setOld.prop2, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixture1", 41.67F * interp(setNew.mix1, setOld.mix1, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixture2", 41.67F * interp(setNew.mix2, setOld.mix2, f), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -0.095F * fm.CT.getRudder();
        mesh.chunkSetLocate("Z_RightPedal", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -com.maddox.il2.objects.air.Cockpit.xyz[1];
        mesh.chunkSetLocate("Z_LeftPedal", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Columnbase", -8F * (pictElev = 0.65F * pictElev + 0.35F * fm.CT.ElevatorControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", -45F * (pictAiler = 0.65F * pictAiler + 0.35F * fm.CT.AileronControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Brake", 21.5F * fm.CT.BrakeControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoL", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, -85F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoR", cvt(fm.EI.engines[1].getControlMagnetos(), 0.0F, 3F, 0.0F, 85F), 0.0F, 0.0F);
        mesh.chunkSetLocate("Z_RadR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.018F, -0.018F);
        mesh.chunkSetLocate("Z_TurnBank1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_TurnBank1Q", -fm.Or.getKren(), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank2", cvt(w.z, -0.23562F, 0.23562F, -27F, 27F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zTurn", 0.0F, cvt(setNew.turn, -0.6F, 0.6F, -1.8F, 1.8F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(7D), -7F, 7F, 10F, -10F), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.Or.getTangage(), -20F, 20F, 0.0385F, -0.0385F);
        mesh.chunkSetLocate("zPitch", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Climb1", cvt(setNew.vspeed, -30F, 30F, 180F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 3300F, 150F, -150F), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
            mesh.chunkSetAngles("Z_RPM3", cvt(fm.EI.engines[1].getRPM(), 0.0F, 3300F, 150F, -150F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel, 0.0F, 360F, 0.0F, -198F), 0.0F, 0.0F);
        float f1 = 0.0F;
        if(fm.M.fuel > 1.0F)
            f1 = cvt(fm.EI.engines[0].getRPM(), 0.0F, 570F, 0.0F, 0.26F);
        mesh.chunkSetAngles("Z_FuelPres1", cvt(f1, 0.0F, 1.0F, 0.0F, -270F), 0.0F, 0.0F);
        f1 = 0.0F;
        if(fm.M.fuel > 1.0F)
            f1 = cvt(fm.EI.engines[1].getRPM(), 0.0F, 570F, 0.0F, 0.26F);
        mesh.chunkSetAngles("Z_FuelPres2", cvt(f1, 0.0F, 1.0F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp2", cvt(fm.EI.engines[1].tWaterOut, 0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pres1", cvt(setNew.man1, 0.399966F, 1.599864F, 0.0F, -300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pres2", cvt(setNew.man2, 0.399966F, 1.599864F, 0.0F, -300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil1", cvt(fm.EI.engines[0].tOilIn, 0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil2", cvt(fm.EI.engines[1].tOilIn, 0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres2", cvt(1.0F + 0.05F * fm.EI.engines[1].tOilOut * fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_HPres", fm.Gears.isHydroOperable() ? -102F : 0.0F, 0.0F, 0.0F);
        f1 = com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.09F;
        if(f1 < -40F)
            mesh.chunkSetAngles("Z_AirTemp", cvt(f1, -40F, -20F, 52F, 35F), 0.0F, 0.0F);
        else
        if(f1 < 0.0F)
            mesh.chunkSetAngles("Z_AirTemp", cvt(f1, -20F, 0.0F, 35F, 0.0F), 0.0F, 0.0F);
        else
        if(f1 < 20F)
            mesh.chunkSetAngles("Z_AirTemp", cvt(f1, 0.0F, 20F, 0.0F, -18.5F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_AirTemp", cvt(f1, 20F, 50F, -18.5F, -37F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FlapPos", cvt((float)java.lang.Math.pow(fm.CT.FlapsControl, 0.60000002384185791D), 0.0F, 1.0F, 0.0F, -130F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass4", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkVisible("XRGearUp", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
        mesh.chunkVisible("XLGearUp", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
        mesh.chunkVisible("XRGearDn", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        mesh.chunkVisible("XLGearDn", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
        f1 = fm.CT.getFlap();
        mesh.chunkVisible("XFlapsUp", f1 < 0.1F);
        mesh.chunkVisible("XFlapsMd", f1 < 0.9F && f1 > 0.1F);
        mesh.chunkVisible("XFlapsDn", f1 > 0.9F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("Z_Fuel1", false);
            mesh.chunkVisible("Z_Pres1", false);
            mesh.chunkVisible("Z_Altimeter3", false);
            mesh.chunkVisible("Z_Altimeter4", false);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XHullDamage1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage5", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("XHullDamage2", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage6", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 2) != 0 && (fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", false);
            mesh.chunkVisible("Panel_D2", true);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_Speedometer2", false);
            mesh.chunkVisible("Z_AirTemp", false);
            mesh.chunkVisible("Z_Pres2", false);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_RPM2", false);
            mesh.chunkVisible("Z_InertGas", false);
            mesh.chunkVisible("Z_FuelPres2", false);
            mesh.chunkVisible("Z_Oilpres1", false);
            mesh.chunkVisible("Z_Oilpres2", false);
        }
        retoggleLight();
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(1.0F, 0.5F);
            light2.light.setEmit(1.0F, 0.5F);
            light3.light.setEmit(1.0F, 0.5F);
            light4.light.setEmit(1.0F, 0.5F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            light3.light.setEmit(0.0F, 0.0F);
            light4.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
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

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private com.maddox.il2.engine.LightPointActor light3;
    private com.maddox.il2.engine.LightPointActor light4;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 10.5F, 42.5F, 85F, 125F, 165.5F, 181F, 198F, 214.5F, 
        231F, 249F, 266.5F, 287.5F, 308F, 326.5F, 346F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
