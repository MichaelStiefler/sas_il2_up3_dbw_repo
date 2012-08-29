// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitTA_152C.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
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
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitTA_152C extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.altimeter = fm.getAltitude();
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.vspeed = (499F * setOld.vspeed + fm.getVertSpeed()) / 500F;
                setNew.azimuth = fm.Or.getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float altimeter;
        float throttle;
        float dimPosition;
        float azimuth;
        float waypointAzimuth;
        float vspeed;

        private Variables()
        {
        }

    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(com.maddox.il2.objects.air.Cockpit.P1);
            com.maddox.il2.objects.air.Cockpit.V.sub(com.maddox.il2.objects.air.Cockpit.P1, fm.Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(com.maddox.il2.objects.air.Cockpit.V.y, com.maddox.il2.objects.air.Cockpit.V.x));
        }
    }

    public CockpitTA_152C()
    {
        super("3DO/Cockpit/Ta-152C-1/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictGear = 0.0F;
        setNew.dimPosition = 1.0F;
        cockpitNightMats = (new java.lang.String[] {
            "D9GP1", "D9GP2", "Ta152GP3", "A5GP3Km", "Ta152GP4_MW50", "Ta152GP5", "A4GP6", "Ta152Trans2", "D9GP2"
        });
        setNightMats(false);
        cockpitDimControl = !cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON03");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON04");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON05");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON06");
        }
        if(bomb[0] == null)
        {
            for(int i = 0; i < bomb.length; i++)
                bomb[i] = com.maddox.il2.objects.weapons.GunEmpty.get();

            if(((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb05") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb05");
                bomb[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb05");
            } else
            if(((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalDev02") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalDev02");
                bomb[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalDev02");
            }
            if(((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalRock01") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalRock01");
                bomb[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalRock02");
            } else
            if(((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb03") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb03");
                bomb[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb04");
            } else
            if(((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalDev05") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalDev05");
                bomb[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalDev06");
            }
        }
        resetYPRmodifier();
        float f1;
        if(fm.CT.GearControl == 0.0F)
            f1 = 0.0205F;
        else
            f1 = 0.0F;
        pictGear = 0.93F * pictGear + 0.07F * f1;
        com.maddox.il2.objects.air.Cockpit.xyz[2] = pictGear;
        mesh.chunkSetLocate("FahrHandle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        if((fm.AS.astateCockpitState & 0x20) == 0 && (fm.AS.astateCockpitState & 0x80) == 0)
        {
            mesh.chunkSetAngles("NeedleALT", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(setNew.altimeter, 0.0F, 10000F, 0.0F, -180F));
        }
        mesh.chunkSetAngles("NeedleManPress", -cvt(fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 15F, 345F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0 && (fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
            mesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleFuel", floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 400F, 0.0F, 4F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleWaterTemp", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleOilTemp", cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 60F), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 2) == 0 && (fm.AS.astateCockpitState & 1) == 0 && (fm.AS.astateCockpitState & 4) == 0)
        {
            mesh.chunkSetAngles("NeedleFuelPress", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        }
        if((fm.AS.astateCockpitState & 0x40) == 0 && (fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
        {
            float f2;
            if(aircraft().isFMTrackMirror())
            {
                f2 = aircraft().fmTrack().getCockpitAzimuthSpeed();
            } else
            {
                f2 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -6F, 6F, 20F, -20F);
                if(aircraft().fmTrack() != null)
                    aircraft().fmTrack().setCockpitAzimuthSpeed(f2);
            }
            mesh.chunkSetAngles("NeedleAHTurn", 0.0F, f2, 0.0F);
            mesh.chunkSetAngles("NeedleAHBank", 0.0F, -cvt(getBall(6D), -6F, 6F, 11F, -11F), 0.0F);
            mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, fm.Or.getKren());
            mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(fm.Or.getTangage(), -45F, 45F, 12F, -12F));
        }
        if((fm.AS.astateCockpitState & 0x20) == 0 && (fm.AS.astateCockpitState & 0x80) == 0)
            mesh.chunkSetAngles("NeedleCD", setNew.vspeed < 0.0F ? floatindex(cvt(-setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale) : -floatindex(cvt(setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0 && (fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
        {
            mesh.chunkSetAngles("RepeaterOuter", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
            mesh.chunkSetAngles("RepeaterPlane", interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        }
        if((fm.AS.astateCockpitState & 0x40) == 0 && (fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
            mesh.chunkSetAngles("NeedleTrimmung", fm.CT.getTrimElevatorControl() * 25F * 2.0F, 0.0F, 0.0F);
        resetYPRmodifier();
        if(gun[1] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[1].countBullets(), 0.0F, 500F, -0.046F, 0.0F);
            mesh.chunkSetLocate("RC_MG17_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[2] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[2].countBullets(), 0.0F, 500F, -0.046F, 0.0F);
            mesh.chunkSetLocate("RC_MG17_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[0] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[0].countBullets(), 0.0F, 500F, -0.019F, 0.024F);
            mesh.chunkSetLocate("RC_MG151_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[3] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(gun[3].countBullets(), 0.0F, 500F, -0.019F, 0.024F);
            mesh.chunkSetLocate("RC_MG151_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        mesh.chunkSetAngles("IgnitionSwitch", 24F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 20F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = fm.CT.WeaponControl[1] ? -0.004F : 0.0F;
        mesh.chunkSetLocate("SecTrigger", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.ypr[0] = interp(setNew.throttle, setOld.throttle, f) * 34F * 0.91F;
        com.maddox.il2.objects.air.Cockpit.xyz[2] = com.maddox.il2.objects.air.Cockpit.ypr[0] <= 7F ? 0.0F : -0.006F;
        mesh.chunkSetLocate("ThrottleQuad", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("RPedalBase", 0.0F, 0.0F, fm.CT.getRudder() * 15F);
        mesh.chunkSetAngles("RPedalStrut", 0.0F, 0.0F, -fm.CT.getRudder() * 15F);
        mesh.chunkSetAngles("RPedal", 0.0F, 0.0F, -fm.CT.getRudder() * 15F - fm.CT.getBrake() * 15F);
        mesh.chunkSetAngles("LPedalBase", 0.0F, 0.0F, -fm.CT.getRudder() * 15F);
        mesh.chunkSetAngles("LPedalStrut", 0.0F, 0.0F, fm.CT.getRudder() * 15F);
        mesh.chunkSetAngles("LPedal", 0.0F, 0.0F, fm.CT.getRudder() * 15F - fm.CT.getBrake() * 15F);
        mesh.chunkVisible("XLampTankSwitch", fm.M.fuel > 144F);
        mesh.chunkVisible("XLampFuelLow", fm.M.fuel < 43.2F);
        mesh.chunkVisible("XLampGearL_1", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("XLampGearL_2", fm.CT.getGear() > 0.95F && fm.Gears.lgear);
        mesh.chunkVisible("XLampGearR_1", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("XLampGearR_2", fm.CT.getGear() > 0.95F && fm.Gears.rgear);
        mesh.chunkVisible("XLampGearC_1", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("XLampGearC_2", fm.CT.getGear() > 0.95F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = fm.CT.GearControl >= 0.5F ? 0.0F : 0.02F;
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

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0 || (fm.AS.astateCockpitState & 1) != 0 || (fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("Revi16", false);
            mesh.chunkVisible("Revi16Tinter", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("DRevi16", true);
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.materialReplace("D9GP1", "DD9GP1");
            mesh.materialReplace("D9GP1_night", "DD9GP1_night");
            mesh.chunkVisible("NeedleManPress", false);
            mesh.chunkVisible("NeedleRPM", false);
            mesh.chunkVisible("RepeaterOuter", false);
            mesh.chunkVisible("RepeaterPlane", false);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0 || (fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.materialReplace("D9GP2", "DD9GP2");
            mesh.materialReplace("D9GP2_night", "DD9GP2_night");
            mesh.chunkVisible("NeedleAHCyl", false);
            mesh.chunkVisible("NeedleAHBank", false);
            mesh.chunkVisible("NeedleAHBar", false);
            mesh.chunkVisible("NeedleAHTurn", false);
            mesh.chunkVisible("NeedleFuelPress", false);
            mesh.chunkVisible("NeedleOilPress", false);
            mesh.materialReplace("Ta152GP4_MW50", "DTa152GP4");
            mesh.materialReplace("Ta152GP4_MW50_night", "DTa152GP4_night");
            mesh.chunkVisible("NeedleFuel", false);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = -0.001F;
            com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.008F;
            com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.025F;
            com.maddox.il2.objects.air.Cockpit.ypr[0] = 3F;
            com.maddox.il2.objects.air.Cockpit.ypr[1] = -6F;
            com.maddox.il2.objects.air.Cockpit.ypr[2] = 1.5F;
            mesh.chunkSetLocate("IPCentral", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0 || (fm.AS.astateCockpitState & 0x80) != 0)
        {
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.materialReplace("Ta152GP3", "DTa152GP3");
            mesh.materialReplace("Ta152GP3_night", "DTa152GP3_night");
            mesh.chunkVisible("NeedleKMH", false);
            mesh.chunkVisible("NeedleCD", false);
            mesh.chunkVisible("NeedleAlt", false);
            mesh.chunkVisible("NeedleAltKM", false);
            mesh.materialReplace("Ta152Trans2", "DTa152Trans2");
            mesh.materialReplace("Ta152Trans2_night", "DTa152Trans2_night");
            mesh.chunkVisible("NeedleWaterTemp", false);
            mesh.chunkVisible("NeedleOilTemp", false);
        }
        retoggleLight();
    }

    private float tmp;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null, null, null
    };
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.ai.BulletEmitter bomb[] = {
        null, null, null, null
    };
    private float pictAiler;
    private float pictElev;
    private float pictGear;
    private static final float speedometerScale[] = {
        0.0F, 18.5F, 67F, 117F, 164F, 215F, 267F, 320F, 379F, 427F, 
        428F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 53F, 108F, 170F, 229F, 282F, 334F, 342.5F, 342.5F
    };
    private static final float fuelScale[] = {
        0.0F, 16F, 35F, 52.5F, 72F, 72F
    };
    private static final float manPrsScale[] = {
        0.0F, 0.0F, 0.0F, 15.5F, 71F, 125F, 180F, 235F, 290F, 245F, 
        247F, 247F
    };
    private static final float oilfuelNeedleScale[] = {
        0.0F, 38F, 84F, 135.5F, 135F
    };
    private static final float vsiNeedleScale[] = {
        0.0F, 48F, 82F, 96.5F, 111F, 120.5F, 130F, 130F
    };







}
