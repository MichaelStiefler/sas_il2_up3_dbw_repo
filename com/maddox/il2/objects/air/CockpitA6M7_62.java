// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitA6M7_62.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitA6M7_62 extends com.maddox.il2.objects.air.CockpitPilot
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
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                if((fm.AS.astateCockpitState & 2) != 0)
                {
                    if(setNew.stbyPosition > 0.0F)
                        setNew.stbyPosition = setOld.stbyPosition - 0.025F;
                } else
                if(setNew.stbyPosition < 1.0F)
                    setNew.stbyPosition = setOld.stbyPosition + 0.025F;
                setNew.throttle = 0.9F * setOld.throttle + 0.1F * fm.CT.PowerControl;
                setNew.prop = 0.9F * setOld.prop + 0.1F * fm.EI.engines[0].getControlProp();
                setNew.mix = 0.8F * setOld.mix + 0.2F * fm.EI.engines[0].getControlMix();
                setNew.man = 0.92F * setOld.man + 0.08F * fm.EI.engines[0].getManifoldPressure();
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-5F, 5F));
                setNew.waypointDirection.setDeg(setOld.waypointDirection.getDeg(1.0F), f);
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
        float man;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        com.maddox.il2.ai.AnglesFork waypointDirection;
        float vspeed;
        float dimPosition;
        float stbyPosition;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
            waypointDirection = new AnglesFork();
        }

    }


    protected float waypointAzimuth()
    {
        float f;
        for(f = super.waypointAzimuth(); f <= -180F; f += 180F);
        for(; f > 180F; f -= 180F);
        return f;
    }

    public CockpitA6M7_62()
    {
        super("3DO/Cockpit/A6M7_62/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bNeedSetUp = true;
        light1 = new LightPointActor(new LightPoint(), new Point3d(-0.17559999999999998D, 0.39240000000000003D, 0.59130000000000005D));
        light2 = new LightPointActor(new LightPoint(), new Point3d(-0.14789999999999998D, -0.36120000000000002D, 0.59130000000000005D));
        light1.light.setColor(0.9607843F, 0.8666667F, 0.7411765F);
        light2.light.setColor(0.9607843F, 0.8666667F, 0.7411765F);
        light1.light.setEmit(0.0F, 0.0F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        pos.base().draw.lightMap().put("LAMPHOOK2", light2);
        setNew.dimPosition = 1.0F;
        cockpitNightMats = (new java.lang.String[] {
            "gauges1", "gauges2", "gauges3", "gauges4", "gauges5", "gauges1_d1", "gauges2_d1", "gauges3_d1", "gauges4_d1", "turnbank", 
            "pressmix"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.6F);
        mesh.chunkSetLocate("Blister_D0", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("sunOFF", 0.0F, cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -77F), 0.0F);
        mesh.chunkSetAngles("sight_rev", 0.0F, cvt(interp(setNew.stbyPosition, setOld.stbyPosition, f), 0.0F, 1.0F, 0.0F, -115F), 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 15.5F);
        mesh.chunkSetAngles("Stick_tube", 0.0F, -15.5F * pictElev, 0.0F);
        if(fm.CT.Weapons[3] != null && !fm.CT.Weapons[3][0].haveBullets())
            mesh.chunkSetAngles("Turn1", 0.0F, 53F, 0.0F);
        mesh.chunkSetAngles("Turn2", 0.0F, fm.Gears.bTailwheelLocked ? 53F : 0.0F, 0.0F);
        mesh.chunkSetAngles("Turn3", 0.0F, 68F * cvt(interp(setNew.mix, setOld.mix, f), 1.0F, 1.2F, 0.5F, 1.0F), 0.0F);
        mesh.chunkSetAngles("Turn3_rod", 0.0F, -68F * cvt(interp(setNew.mix, setOld.mix, f), 1.0F, 1.2F, 0.5F, 1.0F), 0.0F);
        mesh.chunkSetAngles("Turn5", 0.0F, 75F * interp(setNew.prop, setOld.prop, f), 0.0F);
        mesh.chunkSetAngles("Turn6", 0.0F, 68F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        mesh.chunkSetAngles("Turn6_rod", 0.0F, -68F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        mesh.chunkSetAngles("Turn7", 0.0F, fm.CT.saveWeaponControl[1] ? 26F : 0.0F, 0.0F);
        mesh.chunkSetAngles("Turn8", 0.0F, 20F - 40F * (float)fm.EI.engines[0].getControlCompressor(), 0.0F);
        mesh.chunkSetAngles("Pedals", 11F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Ped_trossL", -11F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Ped_trossR", -11F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil1", cvt(fm.Or.getTangage(), -10F, 10F, -10F, 10F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil3", cvt(fm.Or.getKren(), -10F, 10F, -10F, 10F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zCompassOil2", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Azimuth1a", 0.0F, -setNew.waypointDirection.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Z_Navigation", 0.0F, cvt(setNew.waypointAzimuth.getDeg(f), -25F, 25F, 45F, -45F), 0.0F);
        mesh.chunkSetAngles("Z_Mixture", 0.0F, cvt(fm.EI.engines[0].getControlMix(), 0.0F, 1.0F, 0.0F, 90F), 0.0F);
        mesh.chunkSetAngles("Z_Clock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Clock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("Z_Magneto", 0.0F, cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, -104F), 0.0F);
        mesh.chunkSetAngles("Z_AirSpeed", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 740.7998F, 0.0F, 20F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Alt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 14400F), 0.0F);
        mesh.chunkSetAngles("Z_Alt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 1440F), 0.0F);
        mesh.chunkSetAngles("Z_Horison1a", 0.0F, -fm.Or.getKren(), 0.0F);
        mesh.chunkSetAngles("Z_Horison1b", 0.0F, cvt(fm.Or.getTangage(), -33F, 33F, 33F, -33F), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_Horison1c", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
        mesh.chunkSetAngles("Z_Turn1a", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
        mesh.chunkSetAngles("Z_Turn1b", 0.0F, cvt(getBall(8D), -8F, 8F, -14F, 14F), 0.0F);
        mesh.chunkSetAngles("Z_Vspeed", 0.0F, cvt(setNew.vspeed, -10F, 10F, -180F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_TempCilinder", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, 90F), 0.0F);
        mesh.chunkSetAngles("Z_TempOil", 0.0F, floatindex(cvt(fm.EI.engines[0].tOilOut, 0.0F, 160F, 0.0F, 7F), oilScale), 0.0F);
        mesh.chunkSetAngles("Z_pressFuel", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 8F, 0.0F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_pressOil", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 5F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("Z_Revolution", 0.0F, cvt(fm.EI.engines[0].getRPM(), 500F, 4500F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("Z_Manifold", 0.0F, cvt(setNew.man, 0.400051F, 1.333305F, -202.5F, 112.5F), 0.0F);
        mesh.chunkSetAngles("Z_FuelWing", 0.0F, cvt(fm.M.fuel * 1.388F, 0.0F, 250F, 0.0F, 235F), 0.0F);
        mesh.chunkSetAngles("Z_FuelFuse", 0.0F, cvt(fm.M.fuel * 1.388F, 0.0F, 80F, 0.0F, 264F), 0.0F);
        mesh.chunkSetAngles("Z_PressMixt1a", 0.0F, cvt((float)java.lang.Math.sqrt(java.lang.Math.sqrt(fm.M.nitro)), 0.0F, 2.783F, 0.0F, 330F), 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("Z_Holes2_D1", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes4_D1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_d1", true);
            mesh.chunkVisible("Z_Clock1a", false);
            mesh.chunkVisible("Z_Clock1b", false);
            mesh.chunkVisible("Z_TempOil", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_d1", true);
            mesh.chunkVisible("Z_Horison1c", false);
            mesh.chunkVisible("Z_AirSpeed", false);
            mesh.chunkVisible("Z_pressFuel", false);
            mesh.chunkVisible("Z_pressOil", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("Z_Holes3_D1", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("Z_Holes3_D1", true);
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.5F, 0.25F);
            light2.light.setEmit(1.0F, 0.25F);
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
    private float pictAiler;
    private float pictElev;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        45F, 48.5F, 51.5F, 80F, 129F, 180F, 227F, 270.5F, 315F, 360F, 
        401F, 440F, 476F, 512F, 546.4F, 579F, 608F, 637.5F, 666.5F, 696.5F, 
        726F
    };
    private static final float oilScale[] = {
        0.0F, -27.5F, 12F, 59.5F, 127F, 212.5F, 311.5F
    };







}
