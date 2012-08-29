// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitKI_61.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
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

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitKI_61 extends com.maddox.il2.objects.air.CockpitPilot
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

        float throttle;
        float pitch;
        float mix;
        float altimeter;
        float manifold;
        com.maddox.il2.ai.AnglesFork azimuth;
        float vspeed;

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

    public CockpitKI_61()
    {
        super("3DO/Cockpit/Ki-61/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictGear = 0.0F;
        pictFlaps = 0.0F;
        ordnanceCounter = 0;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        mesh.chunkVisible("FlarePilonEn_L", false);
        mesh.chunkVisible("FlarePilonEn_R", false);
        if(fm.CT.Weapons[3] != null)
        {
            ordnanceCounter = fm.CT.Weapons[3].length;
            if(fm.CT.Weapons[9] != null)
                ordnanceCounter = 3;
        } else
        if(fm.CT.Weapons[9] != null)
            ordnanceCounter = 3 + fm.CT.Weapons[9].length;
        cockpitNightMats = (new java.lang.String[] {
            "GP_I", "GP_II", "GP_III", "GP_IV", "GP_V", "GP_VI", "GP_II_D", "GP_III_D", "GP_IV_D", "GP_V_D", 
            "GP_VI_D", "GP_VII", "GP_VIII"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        resetYPRmodifier();
        xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.16F, 0.99F, 0.0F, -0.54F);
        mesh.chunkSetLocate("Canopy", xyz, ypr);
        mesh.chunkSetAngles("Z_canopylock_R", 0.0F, cvt(fm.CT.getCockpitDoor(), 0.01F, 0.12F, 0.0F, -105F), 0.0F);
        mesh.chunkSetAngles("Z_canopylock_L", 0.0F, cvt(fm.CT.getCockpitDoor(), 0.01F, 0.12F, 0.0F, -105F), 0.0F);
        mesh.chunkSetAngles("Z_Gear1", 0.0F, -57F * (pictGear = 0.8F * pictGear + 0.2F * fm.CT.GearControl), 0.0F);
        mesh.chunkSetAngles("Z_stickmount", 0.0F, -(pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 12F, 0.0F);
        mesh.chunkSetAngles("Z_Column", 0.0F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 12F, 0.0F);
        mesh.chunkSetAngles("Z_elevshtck", 0.0F, pictElev * 12F, 0.0F);
        mesh.chunkSetAngles("Z_Pedal_L", 0.0F, -18F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_Pedal_R", 0.0F, 18F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_WlBreak_L", 0.0F, -30F * fm.CT.getBrake(), 0.0F);
        mesh.chunkSetAngles("Z_WlBreak_R", 0.0F, -30F * fm.CT.getBrake(), 0.0F);
        mesh.chunkSetAngles("Z_ElevTrimHandl", 0.0F, -45F * fm.CT.getTrimElevatorControl(), 0.0F);
        mesh.chunkSetAngles("Z_FlapLever", 0.0F, 57F * (pictFlaps = 0.8F * pictFlaps + 0.2F * fm.CT.FlapsControl), 0.0F);
        mesh.chunkSetAngles("Z_OilCoolerLvr", 0.0F, -57F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        mesh.chunkSetAngles("Z_TQHandle", 0.0F, 72F * setNew.throttle, 0.0F);
        mesh.chunkSetAngles("Z_PropPitchLvr", 0.0F, 90F * setNew.pitch, 0.0F);
        mesh.chunkSetAngles("Z_MixLvr", 0.0F, 30F * setNew.mix, 0.0F);
        mesh.chunkSetAngles("Z_NeedElevTrim", 0.0F, -90F * fm.CT.trimElevator, 0.0F);
        mesh.chunkSetAngles("NeedCompass_B", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedAlt_Km", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedAlt_M", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedBank", cvt(getBall(8D), -8F, 8F, 10F, -10F), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("NeedTurn", cvt(w.z, -0.23562F, 0.23562F, -25F, 25F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedClimb", floatindex(cvt(setNew.vspeed, -30F, 30F, 0.5F, 6.5F), vsiNeedleScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedFuel", cvt(fm.M.fuel, 0.0F, 525F, 0.0F, 328F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedFuelPress", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.6F, 0.0F, 308F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedMin", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedHour", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedManPress", cvt(setNew.manifold, 0.200068F, 1.799932F, -164F, 164F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedOilPress", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedOil_L", cvt(fm.EI.engines[0].tOilOut, 0.0F, 130F, 0.0F, 200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedOil_R", -cvt(fm.EI.engines[0].tOilIn, 0.0F, 130F, 0.0F, 200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedCoolantT_L", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 130F, 0.0F, 200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedCoolantT_R", -cvt(fm.EI.engines[0].tWaterOut - 20F, 0.0F, 130F, 0.0F, 200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedRPM", floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 3500F, 0.0F, 7F), revolutionsScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedSpeed", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1000F, 0.0F, 20F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedExhTemp", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 200F, 0.0F, 64F), 0.0F, 0.0F);
        float f1 = com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.09F;
        if(f1 > 0.0F)
            mesh.chunkSetAngles("NeedFreeAirTemp", cvt(f1, 0.0F, 60F, 0.0F, 34.4F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("NeedFreeAirTemp", cvt(f1, -40F, 0.0F, -34.4F, 0.0F), 0.0F, 0.0F);
        resetYPRmodifier();
        xyz[0] = cvt(fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 0.0567F);
        mesh.chunkSetLocate("NeedFlap", xyz, ypr);
        resetYPRmodifier();
        xyz[0] = cvt(fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.0567F);
        mesh.chunkSetLocate("NeedRadiator", xyz, ypr);
        mesh.chunkVisible("FlareGearDn_A", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
        mesh.chunkVisible("FlareGearDn_B", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        mesh.chunkVisible("FlareGearUp_A", fm.CT.getGear() < 0.01F && fm.Gears.lgear);
        mesh.chunkVisible("FlareGearUp_B", fm.CT.getGear() < 0.01F && fm.Gears.rgear);
        if(fm.CT.bHasGearControl)
        {
            mesh.chunkVisible("FlareGearDn_C", fm.CT.getGear() > 0.99F && fm.Gears.cgear);
            mesh.chunkVisible("FlareGearUp_C", fm.CT.getGear() < 0.01F && fm.Gears.cgear);
        } else
        {
            mesh.chunkVisible("GIND2", false);
        }
        switch(ordnanceCounter)
        {
        default:
            break;

        case 1: // '\001'
            if(fm.CT.Weapons[3][0].haveBullets())
                mesh.chunkVisible("FlarePilonEn_R", true);
            else
                mesh.chunkVisible("FlarePilonEn_R", false);
            break;

        case 2: // '\002'
            if(fm.CT.Weapons[3][0].haveBullets())
            {
                mesh.chunkVisible("FlarePilonEn_R", true);
                mesh.chunkVisible("FlarePilonEn_L", true);
            } else
            {
                mesh.chunkVisible("FlarePilonEn_R", false);
                mesh.chunkVisible("FlarePilonEn_L", false);
            }
            break;

        case 3: // '\003'
            if(fm.CT.Weapons[3][0].haveBullets())
                mesh.chunkVisible("FlarePilonEn_R", true);
            else
                mesh.chunkVisible("FlarePilonEn_R", false);
            if(fm.CT.Weapons[9][0].haveBullets())
                mesh.chunkVisible("FlarePilonEn_L", true);
            else
                mesh.chunkVisible("FlarePilonEn_L", false);
            break;

        case 4: // '\004'
            if(fm.CT.Weapons[9][0].haveBullets())
                mesh.chunkVisible("FlarePilonEn_R", true);
            else
                mesh.chunkVisible("FlarePilonEn_R", false);
            break;

        case 5: // '\005'
            if(fm.CT.Weapons[9][0].haveBullets())
            {
                mesh.chunkVisible("FlarePilonEn_R", true);
                mesh.chunkVisible("FlarePilonEn_L", true);
            } else
            {
                mesh.chunkVisible("FlarePilonEn_R", false);
                mesh.chunkVisible("FlarePilonEn_L", false);
            }
            break;
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XHullDamage1", true);
        if((fm.AS.astateCockpitState & 8) == 0);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XHullDamage2", true);
        if((fm.AS.astateCockpitState & 0x20) == 0);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("OilSplats", true);
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("Z_Z_MASK_D1", true);
            mesh.chunkVisible("GS_Glass_D0", false);
            mesh.chunkVisible("GS_Glass_D1", true);
            mesh.chunkVisible("Z_wndshld_holes", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_canopy_holes", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Gages", false);
            mesh.chunkVisible("Gages_D1", true);
            mesh.chunkVisible("NeedBank", false);
            mesh.chunkVisible("NeedSpeed", false);
            mesh.chunkVisible("NeedAlt_Km", false);
            mesh.chunkVisible("NeedAlt_M", false);
            mesh.chunkVisible("NeedManPress", false);
            mesh.chunkVisible("NeedFreeAirTemp", false);
            mesh.chunkVisible("NeedExhTemp", false);
            mesh.chunkVisible("NeedRPM", false);
            mesh.chunkVisible("NeedOilPress", false);
            mesh.chunkVisible("NeedFuel", false);
            mesh.chunkVisible("NeedHPres_01", false);
            mesh.chunkVisible("NeedHPres_02", false);
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
    private float pictGear;
    private float pictFlaps;
    private int ordnanceCounter;
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
