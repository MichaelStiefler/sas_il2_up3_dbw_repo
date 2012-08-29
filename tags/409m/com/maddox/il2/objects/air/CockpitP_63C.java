// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitP_63C.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
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
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitP_63C extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.mix = (10F * setOld.mix + fm.EI.engines[0].getControlMix()) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 45F)
                    setNew.azimuthMag = (349F * setOld.azimuthMag + fm.Or.azimut()) / 350F;
                if(setOld.azimuthMag > 270F && setNew.azimuthMag < 90F)
                    setOld.azimuthMag -= 360F;
                if(setOld.azimuthMag < 90F && setNew.azimuthMag > 270F)
                    setOld.azimuthMag += 360F;
                setNew.azimuth = fm.Or.azimut();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.vspeed = (499F * setOld.vspeed + fm.getVertSpeed()) / 500F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + waypointAzimuth() + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                int i = fm.EI.engines[0].getControlMagnetos();
                if(oldMag == 0 && i != 0)
                    tOldMag = com.maddox.rts.Time.current() + 10000L;
                oldMag = i;
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
        float mix;
        float altimeter;
        float azimuth;
        float azimuthMag;
        float vspeed;
        float waypointAzimuth;

        private Variables()
        {
        }

    }


    public CockpitP_63C()
    {
        super("3DO/Cockpit/P-63C/hier.him", "p39");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        oldMag = 0;
        tOldMag = -1L;
        light1 = new LightPointActor(new LightPoint(), new Point3d(0.95499999999999996D, 0.0D, 0.59799999999999998D));
        light1.light.setColor(232F, 75F, 44F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("PedalBaseLeft", 0.0F, 0.0F, -20F * fm.CT.getRudder());
        mesh.chunkSetAngles("PedalBaseRight", 0.0F, 0.0F, 20F * fm.CT.getRudder());
        mesh.chunkSetAngles("PedalLeft", 20F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("PedalRight", -20F * fm.CT.getRudder(), 0.0F, 0.0F);
        resetYPRmodifier();
        if(fm.CT.saveWeaponControl[1])
            com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.009F;
        mesh.chunkSetLocate("PriTrigger", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Throttle", 0.0F, 0.0F, -45.5F * interp(setNew.throttle, setOld.throttle, f));
        mesh.chunkSetAngles("Mixture", 0.0F, 0.0F, -50F * interp(setNew.mix, setOld.mix, f));
        mesh.chunkSetAngles("IgnitionSW", -110F * (float)oldMag * 0.333333F, 0.0F, 0.0F);
        mesh.chunkSetAngles("SWCLight", 0.0F, 0.0F, cockpitLightControl ? 40F : 0.0F);
        mesh.chunkSetAngles("SWGear", 0.0F, 0.0F, fm.CT.GearControl <= 0.5F ? 0.0F : 40F);
        mesh.chunkSetAngles("SWLandLight", 0.0F, 0.0F, fm.AS.bLandingLightOn ? 40F : 0.0F);
        mesh.chunkSetAngles("SWNavTail", 0.0F, 0.0F, fm.AS.bNavLightsOn ? 20F : 0.0F);
        mesh.chunkSetAngles("SWNavWing", 0.0F, 0.0F, fm.AS.bNavLightsOn ? 20F : 0.0F);
        mesh.chunkSetAngles("SWStarter", 0.0F, 0.0F, 0.0F);
        if(tOldMag > 0L && com.maddox.rts.Time.current() < tOldMag)
            mesh.chunkSetAngles("SWStarter", 0.0F, 0.0F, 40F);
        mesh.chunkSetAngles("Alt1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Alt2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("TurnInd", interp(setNew.azimuthMag, setOld.azimuthMag, f), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 2) == 0)
        {
            mesh.chunkSetAngles("FltInd", fm.Or.getKren(), 0.0F, 0.0F);
            mesh.chunkSetAngles("FltIndMesh", 0.0F, 0.0F, cvt(fm.Or.getTangage(), -45F, 45F, 1.5F, -1.5F));
        }
        mesh.chunkSetAngles("CompassStrelka", -90F + interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("SettingStrelki", 90F + interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("AirSpeed", floatindex(cvt(0.6213711F * com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 500F, 0.0F, 10F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Climb", setNew.vspeed < 0.0F ? -floatindex(cvt(-setNew.vspeed / 5.08F, 0.0F, 6F, 0.0F, 12F), variometerScale) : floatindex(cvt(setNew.vspeed / 5.08F, 0.0F, 6F, 0.0F, 12F), variometerScale), 0.0F, 0.0F);
        float f1;
        if(aircraft().isFMTrackMirror())
        {
            f1 = aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f1 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -3F, 3F, -30F, 30F);
            if(aircraft().fmTrack() != null)
                aircraft().fmTrack().setCockpitAzimuthSpeed(f1);
        }
        mesh.chunkSetAngles("TurnStrelka", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Hodilka", cvt(getBall(7D), -7F, 7F, -15F, 15F), 0.0F, 0.0F);
        mesh.chunkSetAngles("TBReVi", cvt(getBall(4D), -4F, 4F, 13F, -13F), 0.0F, 0.0F);
        mesh.chunkSetAngles("ManPress", cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386F, 2.5398F, 0.0F, 345F), 0.0F, 0.0F);
        mesh.chunkSetAngles("CoolantTemp", cvt(fm.EI.engines[0].tOilIn, 40F, 160F, 0.0F, 116.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Engine", cvt(fm.EI.engines[0].tOilOut, 0.0F, 140F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Oil", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 17.2369F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Fuel", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 1.72369F, 0.0F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Liquid1", cvt(fm.M.fuel / 0.72F, 0.0F, 454.2493F, 0.0F, 97F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Liquid2", cvt(fm.M.fuel / 0.72F, 0.0F, 454.2493F, 0.0F, 97F), 0.0F, 0.0F);
        mesh.chunkSetAngles("RPMStrelka", cvt(fm.EI.engines[0].getRPM(), 0.0F, 3500F, 0.0F, 280F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Hour", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Min", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("OilPress", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 20.68428F, 0.0F, 300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("SuperOilPress", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut + 5F * fm.EI.engines[0].getManifoldPressure(), 0.0F, 40F, 0.0F, 300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("CarbAir", cvt((com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z - 278F) + fm.EI.engines[0].tOilIn) / 2.0F, -50F, 50F, -52F, 52F), 0.0F, 0.0F);
        mesh.chunkSetAngles("SuctStrelka", cvt((fm.EI.engines[0].getw() / 57F) * com.maddox.il2.fm.Atmosphere.density((float)fm.Loc.z), 0.0F, 12F, 0.0F, 300F), 0.0F, 0.0F);
        mesh.chunkVisible("XLampGearUp", fm.CT.getGear() < 0.01F);
        mesh.chunkVisible("XLampGearDn", fm.CT.getGear() > 0.99F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Kollimator", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("DKollimator", true);
            mesh.chunkVisible("Z_Z_MASK_D", true);
            mesh.chunkVisible("Z_Z_RETICLE_D", true);
            mesh.chunkVisible("XHullHoles1", true);
            mesh.chunkVisible("XHullHoles4", true);
            mesh.materialReplace("APanelUp", "DPanelUp");
            mesh.chunkVisible("TBReVi", false);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("GunSightN9", false);
            mesh.chunkVisible("DGunSightN9", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlAssHoles3", true);
            mesh.chunkVisible("XGlAssHoles4", true);
            mesh.chunkVisible("XGlAssHoles5", true);
            mesh.chunkVisible("XHullHoles1", true);
            mesh.materialReplace("APanelUp", "DPanelUp");
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("XHullHoles1", true);
            mesh.chunkVisible("XHullHoles6", true);
            mesh.materialReplace("GagePanel1", "DGagePanel1");
            mesh.chunkVisible("Climb", false);
            mesh.chunkVisible("ManPress", false);
            mesh.chunkVisible("Engine", false);
            mesh.chunkVisible("Oil", false);
            mesh.chunkVisible("Fuel", false);
            mesh.chunkVisible("RPMStrelka", false);
            mesh.materialReplace("GagePanel2", "DGagePanel2");
            mesh.chunkVisible("AirSpeed", false);
            mesh.chunkVisible("TurnStrelka", false);
            mesh.chunkVisible("Hodilka", false);
            mesh.chunkVisible("OilPress", false);
            mesh.chunkVisible("SuperOilPress", false);
            mesh.chunkVisible("CarbAir", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlAssHoles1", true);
            mesh.chunkVisible("XHullHoles1", true);
            mesh.chunkVisible("XHullHoles2", true);
            mesh.chunkVisible("XHullHoles4", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlAssHoles3", true);
            mesh.chunkVisible("XHullHoles1", true);
            mesh.chunkVisible("XHullHoles3", true);
            mesh.materialReplace("GagePanel3", "DGagePanel3");
            mesh.chunkVisible("Alt1", false);
            mesh.chunkVisible("Alt2", false);
            mesh.chunkVisible("CoolantTemp", false);
            mesh.chunkVisible("Liquid1", false);
            mesh.chunkVisible("Liquid2", false);
            mesh.chunkVisible("SuctStrelka", false);
        }
        if((fm.AS.astateCockpitState & 0x80) == 0);
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlAssHoles2", true);
            mesh.chunkVisible("XHullHoles1", true);
            mesh.chunkVisible("XHullHoles2", true);
            mesh.chunkVisible("XHullHoles6", true);
            mesh.chunkVisible("RDoorHandle", false);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("XGlAssHoles3", true);
            mesh.chunkVisible("XHullHoles1", true);
            mesh.chunkVisible("XHullHoles4", true);
            mesh.chunkVisible("XHullHoles5", true);
            mesh.materialReplace("GagePanel4", "DGagePanel4");
            mesh.chunkVisible("TurnInd", false);
            mesh.chunkVisible("CompassStrelka", false);
            mesh.chunkVisible("SettingStrelki", false);
            mesh.chunkVisible("Hour", false);
            mesh.chunkVisible("Min", false);
        }
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            light1.light.setEmit(0.005F, 1.0F);
        else
            light1.light.setEmit(0.0F, 0.0F);
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private float pictAiler;
    private float pictElev;
    private int oldMag;
    private long tOldMag;
    private static final float speedometerScale[] = {
        0.0F, 17F, 80.5F, 143.5F, 205F, 227F, 249F, 271.5F, 294F, 317F, 
        339.5F, 341.5F
    };
    private static final float variometerScale[] = {
        0.0F, 25F, 49.5F, 64F, 78.5F, 89.5F, 101F, 112F, 123F, 134.5F, 
        145.5F, 157F, 168F, 168F
    };










}
