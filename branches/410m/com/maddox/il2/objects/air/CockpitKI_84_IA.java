// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitKI_84_IA.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitKI_84_IA extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.flap = 0.88F * setOld.flap + 0.12F * fm.CT.FlapsControl;
                setNew.tlock = 0.7F * setOld.tlock + 0.3F * (fm.Gears.bTailwheelLocked ? 1.0F : 0.0F);
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                if(fm.CT.GearControl < 0.5F && setNew.gear < 1.0F)
                    setNew.gear = setOld.gear + 0.02F;
                if(fm.CT.GearControl > 0.5F && setNew.gear > 0.0F)
                    setNew.gear = setOld.gear - 0.02F;
                setNew.throttle = 0.9F * setOld.throttle + 0.1F * fm.CT.PowerControl;
                setNew.manifold = 0.8F * setOld.manifold + 0.2F * fm.EI.engines[0].getManifoldPressure();
                setNew.pitch = 0.9F * setOld.pitch + 0.1F * fm.EI.engines[0].getControlProp();
                setNew.mix = 0.9F * setOld.mix + 0.1F * fm.EI.engines[0].getControlMix();
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
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

        float flap;
        float throttle;
        float pitch;
        float mix;
        float gear;
        float tlock;
        float altimeter;
        float manifold;
        com.maddox.il2.ai.AnglesFork azimuth;
        float vspeed;
        float dimPosition;

        private Variables()
        {
            azimuth = new AnglesFork();
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

    public CockpitKI_84_IA()
    {
        super("3DO/Cockpit/Ki-84-Ia/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "GP_I", "GP_II", "GP_III", "GP_IV", "GP_V", "GP_VI", "GP_VII"
        });
        setNightMats(false);
        cockpitDimControl = !cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("FLCS", -20F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F);
        resetYPRmodifier();
        xyz[1] = cvt(setNew.gear, 0.0F, 0.05F, 0.0F, -0.008F);
        if(setNew.gear > 0.85F)
            xyz[1] = 0.0F;
        mesh.chunkSetLocate("GearHandleKnob", xyz, ypr);
        mesh.chunkSetAngles("GearLockHandle", cvt(setNew.gear, 0.1F, 0.25F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("GearLockSpring", 0.0F, 0.0F, cvt(setNew.gear, 0.1F, 0.25F, 0.0F, 45F));
        mesh.chunkSetAngles("GearHandle", cvt(setNew.gear, 0.5F, 1.0F, 0.0F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, -60F * setNew.flap);
        resetYPRmodifier();
        if(java.lang.Math.abs(setNew.flap - setOld.flap) > 0.001F)
            xyz[2] = -0.008F;
        mesh.chunkSetLocate("FlapHandleKnob", xyz, ypr);
        mesh.chunkSetAngles("TWheelLockLvr", -30F * setNew.tlock, 0.0F, 0.0F);
        mesh.chunkSetAngles("ChargerLvr", 0.0F, 0.0F, 40F * (float)fm.EI.engines[0].getControlCompressor());
        mesh.chunkSetAngles("TQHandle", 0.0F, 0.0F, 54.5454F * setNew.throttle);
        mesh.chunkSetAngles("PropPitchLvr", 0.0F, 0.0F, 60F * setNew.pitch);
        mesh.chunkSetAngles("MixLvr", 0.0F, 0.0F, 60F * cvt(setNew.mix, 1.0F, 1.2F, 0.5F, 1.0F));
        mesh.chunkSetAngles("PedalCrossBar", 0.0F, -15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal_L", 15F * fm.CT.getBrake(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Pedal_R", 15F * fm.CT.getBrake(), 0.0F, 0.0F);
        mesh.chunkSetAngles("RudderCable_L", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("RudderCable_R", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("PriTrigger", 0.0F, 0.0F, fm.CT.saveWeaponControl[0] ? 15F : 0.0F);
        resetYPRmodifier();
        if(fm.CT.saveWeaponControl[1])
            xyz[2] = -0.005F;
        mesh.chunkSetLocate("SecTrigger", xyz, ypr);
        if(fm.CT.saveWeaponControl[2])
            xyz[2] = -0.005F;
        mesh.chunkSetLocate("TreTrigger", xyz, ypr);
        mesh.chunkSetAngles("CowlFlapLvr", 0.0F, 0.0F, 50F * fm.EI.engines[0].getControlRadiator());
        mesh.chunkSetAngles("OilCoolerLvr", -86F * fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
        mesh.chunkSetAngles("FBoxSW_ANO", 0.0F, 0.0F, fm.AS.bNavLightsOn ? 50F : 0.0F);
        mesh.chunkSetAngles("FBoxSW_LandLt", 0.0F, 0.0F, fm.AS.bLandingLightOn ? 50F : 0.0F);
        mesh.chunkSetAngles("FBoxSW_Starter", 0.0F, 0.0F, fm.EI.engines[0].getStage() <= 0 ? 0.0F : 50F);
        mesh.chunkSetAngles("FBoxSW_UVLight", 0.0F, 0.0F, cockpitLightControl ? 50F : 0.0F);
        mesh.chunkSetAngles("GSDimmArm", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -55F), 0.0F, 0.0F);
        resetYPRmodifier();
        xyz[1] = cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 0.05F);
        mesh.chunkSetLocate("GSDimmBase", xyz, ypr);
        mesh.chunkSetAngles("NeedCylTemp", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 360F, 0.0F, 75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedExhTemp", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 324F, 0.0F, 75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedOilTemp", cvt(fm.EI.engines[0].tOilOut, 0.0F, 130F, 0.0F, 75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedOilPress", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedFuelPress", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.6F, 0.0F, 305F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedVMPress", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3F, 0.0F, 300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("IgnitionSwitch", fm.EI.engines[0].getStage() != 0 ? cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 60F) : 0.0F, 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("NeedTurn", cvt(w.z, -0.23562F, 0.23562F, -25F, 25F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedBank", cvt(getBall(8D), -8F, 8F, 10F, -10F), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0 || (fm.AS.astateCockpitState & 0x10) == 0 || (fm.AS.astateCockpitState & 4) == 0)
        {
            mesh.chunkSetAngles("NeedAHCyl", 0.0F, -fm.Or.getKren(), 0.0F);
            mesh.chunkSetAngles("NeedAHBar", 0.0F, 0.0F, cvt(fm.Or.getTangage(), -45F, 45F, 20F, -20F));
        }
        mesh.chunkSetAngles("NeedClimb", floatindex(cvt(setNew.vspeed, -30F, 30F, 0.5F, 6.5F), vsiNeedleScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedSpeed", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1000F, 0.0F, 20F), speedometerScale), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0 || (fm.AS.astateCockpitState & 8) == 0 || (fm.AS.astateCockpitState & 0x20) == 0)
        {
            mesh.chunkSetAngles("NeedCompass_A", 0.0F, 0.0F, cvt(fm.Or.getTangage(), -20F, 20F, 20F, -20F));
            mesh.chunkSetAngles("NeedCompass_B", -setNew.azimuth.getDeg(f) - 90F, 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("NeedAlt_Km", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedAlt_M", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedMin", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedHour", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedManPress", cvt(setNew.manifold, 0.4000511F, 1.799932F, -144F, 192F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedRPM", floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 3500F, 0.0F, 7F), revolutionsScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedFuel", cvt(fm.M.fuel, 0.0F, 525F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkVisible("FlareFuelLow", fm.M.fuel < 52.5F && (fm.AS.astateCockpitState & 0x40) == 0);
        mesh.chunkVisible("FlareGearDn_A", fm.CT.getGear() > 0.99F);
        mesh.chunkVisible("FlareGearDn_B", fm.CT.getGear() > 0.99F);
        mesh.chunkVisible("FlareGearUp_A", fm.CT.getGear() < 0.01F);
        mesh.chunkVisible("FlareGearUp_B", fm.CT.getGear() < 0.01F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.materialReplace("GP_II", "DGP_II");
            mesh.chunkVisible("NeedManPress", false);
            mesh.chunkVisible("NeedRPM", false);
            mesh.chunkVisible("NeedFuel", false);
            retoggleLight();
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.materialReplace("GP_III", "DGP_III");
            mesh.chunkVisible("NeedAlt_Km", false);
            mesh.chunkVisible("NeedAlt_M", false);
            mesh.chunkVisible("NeedSpeed", false);
            retoggleLight();
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.materialReplace("GP_IV", "DGP_IV");
            mesh.chunkVisible("NeedCylTemp", false);
            mesh.chunkVisible("NeedOilTemp", false);
            mesh.chunkVisible("NeedVAmmeter", false);
            mesh.materialReplace("GP_IV_night", "DGP_IV_night");
            retoggleLight();
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.materialReplace("GP_V", "DGP_V");
            mesh.chunkVisible("NeedExhTemp", false);
            mesh.chunkVisible("NeedOilPress", false);
            mesh.chunkVisible("NeedHour", false);
            mesh.chunkVisible("NeedMin", false);
            mesh.chunkVisible("NeedTurn", false);
            mesh.chunkVisible("NeedBank", false);
            retoggleLight();
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("OilSplats", true);
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("GunSight_T3", false);
            mesh.chunkVisible("GS_Lenz", false);
            mesh.chunkVisible("GSGlassMain", false);
            mesh.chunkVisible("GSDimmArm", false);
            mesh.chunkVisible("GSDimmBase", false);
            mesh.chunkVisible("GSGlassDimm", false);
            mesh.chunkVisible("DGunSight_T3", true);
            mesh.chunkVisible("DGS_Lenz", true);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("DamageGlass2", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("DamageGlass1", true);
            mesh.chunkVisible("DamageGlass3", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.materialReplace("GP_VI", "DGP_VI");
            mesh.chunkVisible("NeedFuelPress", false);
            mesh.chunkVisible("NeedVMPress", false);
            mesh.chunkVisible("NeedClimb", false);
            mesh.materialReplace("GP_VI_night", "DGP_VI_night");
            retoggleLight();
            mesh.chunkVisible("DamageGlass4", true);
        }
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
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
    private static final float vsiNeedleScale[] = {
        -200F, -160F, -125F, -90F, 90F, 125F, 160F, 200F
    };
    private static final float speedometerScale[] = {
        0.0F, 10F, 35F, 70F, 105F, 145F, 190F, 230F, 275F, 315F, 
        360F, 397.5F, 435F, 470F, 505F, 537.5F, 570F, 600F, 630F, 655F, 
        680F
    };
    private static final float revolutionsScale[] = {
        0.0F, 20F, 75F, 125F, 180F, 220F, 285F, 335F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
