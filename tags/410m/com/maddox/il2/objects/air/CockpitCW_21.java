// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitCW_21.java

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
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, CW_21, Aircraft

public class CockpitCW_21 extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        float throttle;
        float mix;
        float prop;
        float turn;
        float vspeed;
        float stbyPosition;

        private Variables()
        {
            azimuth = new AnglesFork();
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
            com.maddox.il2.objects.air.CW_21 _tmp = (com.maddox.il2.objects.air.CW_21)aircraft();
            if(com.maddox.il2.objects.air.CW_21.bChangedPit)
            {
                reflectPlaneToModel();
                com.maddox.il2.objects.air.CW_21 _tmp1 = (com.maddox.il2.objects.air.CW_21)aircraft();
                com.maddox.il2.objects.air.CW_21.bChangedPit = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            if((fm.AS.astateCockpitState & 2) != 0 && setNew.stbyPosition < 1.0F)
            {
                setNew.stbyPosition = setOld.stbyPosition + 0.0125F;
                setOld.stbyPosition = setNew.stbyPosition;
            }
            setNew.altimeter = fm.getAltitude();
            setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
            setNew.throttle = (10F * setOld.throttle + fm.EI.engines[0].getControlThrottle()) / 11F;
            setNew.mix = (10F * setOld.mix + fm.EI.engines[0].getControlMix()) / 11F;
            setNew.prop = (8F * setOld.prop + fm.CT.getStepControl()) / 9F;
            w.set(fm.getW());
            fm.Or.transform(w);
            setNew.turn = (12F * setOld.turn + w.z) / 13F;
            setNew.vspeed = (299F * setOld.vspeed + fm.getVertSpeed()) / 300F;
            pictSupc = 0.8F * pictSupc + 0.2F * (float)fm.EI.engines[0].getControlCompressor();
            if(flapsLever != 0.0F && flaps == fm.CT.getFlap())
            {
                flapsLever = flapsLever * 0.8F;
                if(java.lang.Math.abs(flapsLever) < 0.1F)
                    flapsLever = 0.0F;
            } else
            if(flaps < fm.CT.getFlap())
            {
                flaps = fm.CT.getFlap();
                flapsLever = flapsLever + 2.0F;
                if(flapsLever > 20F)
                    flapsLever = 20F;
            } else
            if(flaps > fm.CT.getFlap())
            {
                flaps = fm.CT.getFlap();
                flapsLever = flapsLever - 2.0F;
                if(flapsLever < -20F)
                    flapsLever = -20F;
            }
            if(gearsLever != 0.0F && gears == fm.CT.getGear())
            {
                gearsLever = gearsLever * 0.8F;
                if(java.lang.Math.abs(gearsLever) < 0.1F)
                    gearsLever = 0.0F;
            } else
            if(gears < fm.CT.getGear())
            {
                gears = fm.CT.getGear();
                gearsLever = gearsLever + 2.0F;
                if(gearsLever > 20F)
                    gearsLever = 20F;
            } else
            if(gears > fm.CT.getGear())
            {
                gears = fm.CT.getGear();
                gearsLever = gearsLever - 2.0F;
                if(gearsLever < -20F)
                    gearsLever = -20F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitCW_21()
    {
        super("3DO/Cockpit/CW-21/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictSupc = 0.0F;
        pictFlap = 0.0F;
        rpmGeneratedPressure = 0.0F;
        oilPressure = 0.0F;
        flapsLever = 0.0F;
        flaps = 0.0F;
        gearsLever = 0.0F;
        gears = 0.0F;
        cockpitNightMats = (new java.lang.String[] {
            "CLOCKS1", "CLOCK2", "CLOCK3", "CLOCK5", "Compass", "STUFF4", "STUFF5"
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
        com.maddox.il2.objects.air.CW_21 _tmp = (com.maddox.il2.objects.air.CW_21)aircraft();
        if(com.maddox.il2.objects.air.CW_21.bChangedPit)
        {
            reflectPlaneToModel();
            com.maddox.il2.objects.air.CW_21 _tmp1 = (com.maddox.il2.objects.air.CW_21)aircraft();
            com.maddox.il2.objects.air.CW_21.bChangedPit = false;
        }
        mesh.chunkSetAngles("Z_NeedManifoldPres", cvt(pictManifold = 0.85F * pictManifold + 0.15F * fm.EI.engines[0].getManifoldPressure() * 76F, 30F, 120F, 15F, 285F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Stick", -14F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F, -14F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl));
        mesh.chunkSetAngles("Z_PedalR1", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PedalL1", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        float f1 = fm.CT.getBrake() * 45F;
        mesh.chunkSetAngles("Z_PedalR2", 15F * fm.CT.getRudder() + f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PedalL2", -15F * fm.CT.getRudder() + f1, 0.0F, 0.0F);
        f1 = 70F * interp(setNew.throttle, setOld.throttle, f);
        mesh.chunkSetAngles("Z_LevelThrottle", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RodThrottle", -f1, 0.0F, 0.0F);
        f1 = 70F * interp(setNew.mix, setOld.mix, f);
        mesh.chunkSetAngles("Z_LevelMixture", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RodMixture", -f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeverRPM", 70F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeverSuperc", pictSupc * 70F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RodSuperc", -pictSupc * 70F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedAlt", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 720F), 0.0F, 0.0F);
        float f2 = com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH());
        if(f2 < 150F)
            mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 0.0F, 150F, 0.0F, 90F), 0.0F, 0.0F);
        else
        if(f2 < 300F)
            mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 150F, 300F, 90F, 198F), 0.0F, 0.0F);
        else
        if(f2 < 400F)
            mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 300F, 400F, 198F, 270F), 0.0F, 0.0F);
        else
        if(f2 < 550F)
            mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 400F, 550F, 270F, 378F), 0.0F, 0.0F);
        else
        if(f2 < 700F)
            mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 550F, 700F, 378F, 489F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 700F, 900F, 489F, 630F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedCompass", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        f1 = fm.EI.engines[0].getRPM();
        mesh.chunkSetAngles("Z_NeedRPM", cvt(f1, 500F, 3500F, 0.0F, 540F), 0.0F, 0.0F);
        if(fm.Or.getKren() < -110F || fm.Or.getKren() > 110F)
            rpmGeneratedPressure = rpmGeneratedPressure - 0.5F;
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
        float f3 = 0.0F;
        if(fm.EI.engines[0].tOilOut > 90F)
            f3 = cvt(fm.EI.engines[0].tOilOut, 90F, 110F, 1.1F, 1.5F);
        else
        if(fm.EI.engines[0].tOilOut < 50F)
            f3 = cvt(fm.EI.engines[0].tOilOut, 0.0F, 50F, 2.0F, 0.9F);
        else
            f3 = cvt(fm.EI.engines[0].tOilOut, 50F, 90F, 0.9F, 1.1F);
        float f4 = f3 * fm.EI.engines[0].getReadyness() * oilPressure;
        mesh.chunkSetAngles("Z_NeedOilPres", cvt(f4, 0.0F, 15F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedClockHour", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedClockMinute", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedFuelPres", -cvt(rpmGeneratedPressure, 0.0F, 2000F, 0.0F, 120F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedHydrPres", cvt(rpmGeneratedPressure, 0.0F, 4200F, 0.0F, 270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedOilTemp", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedCylTemp", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, 110F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedTurn", -cvt(setNew.turn, -0.2F, 0.2F, -45F, 45F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedBall", -cvt(getBall(8D), -8F, 8F, 10F, -10F), 0.0F, 0.0F);
        if(java.lang.Math.abs(setNew.vspeed) <= 5F)
            mesh.chunkSetAngles("Z_NeedVSpeed", cvt(setNew.vspeed, -5F, 5F, -90F, 90F), 0.0F, 0.0F);
        else
        if(setNew.vspeed > 5F)
            mesh.chunkSetAngles("Z_NeedVSpeed", cvt(setNew.vspeed, 5F, 20F, 90F, 180F), 0.0F, 0.0F);
        else
        if(setNew.vspeed < -5F)
            mesh.chunkSetAngles("Z_NeedVSpeed", cvt(setNew.vspeed, -20F, -5F, -180F, -90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedFuel", cvt(fm.M.fuel, 0.0F, 280F, 0.0F, 54F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Ignition", 20F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[0] = fm.CT.getCockpitDoor() * -0.49F;
        com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(fm.AS.bLandingLightOn)
            mesh.chunkSetAngles("Z_Switch3", 45F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Switch3", 0.0F, 0.0F, 0.0F);
        if(fm.AS.bNavLightsOn)
            mesh.chunkSetAngles("Z_Switch2", 45F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Switch2", 0.0F, 0.0F, 0.0F);
        if(cockpitLightControl)
            mesh.chunkSetAngles("Z_Switch7", 45F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Switch7", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeverFlaps", flapsLever, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeverGear", gearsLever, 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("CanopyGlassDamage", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Glass_damage", true);
        if((fm.AS.astateCockpitState & 0x40) == 0);
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("Gauges1_D0", false);
            mesh.chunkVisible("Gauges1_D1", true);
            mesh.chunkVisible("Z_NeedCylTemp", false);
            mesh.chunkVisible("Z_NeedSpeed", false);
            mesh.chunkVisible("Z_NeedBall", false);
            mesh.chunkVisible("Z_NeedTurn", false);
        }
        if((fm.AS.astateCockpitState & 8) == 0);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("GlassOil", true);
        if((fm.AS.astateCockpitState & 0x10) == 0);
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("Gauges2_D0", false);
            mesh.chunkVisible("Gauges2_D1", true);
            mesh.chunkVisible("Z_NeedVSpeed", false);
            mesh.chunkVisible("Z_NeedRPM", false);
            mesh.chunkVisible("Z_NeedAlt", false);
        }
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
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
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private float pictSupc;
    private float pictFlap;
    private float pictManifold;
    private float rpmGeneratedPressure;
    private float oilPressure;
    private float flapsLever;
    private float flaps;
    private float gearsLever;
    private float gears;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHS_129.class, "normZN", 0.8F);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHS_129.class, "gsZN", 0.8F);
    }



















}
