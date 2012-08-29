// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitME_262.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
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
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, ME_262A1AU4, Cockpit

public class CockpitME_262 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = fm.getAltitude();
            setNew.throttlel = (10F * setOld.throttlel + fm.EI.engines[0].getControlThrottle()) / 11F;
            setNew.throttler = (10F * setOld.throttler + fm.EI.engines[1].getControlThrottle()) / 11F;
            setNew.azimuth = fm.Or.getYaw();
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            setNew.vspeed = (299F * setOld.vspeed + fm.getVertSpeed()) / 300F;
            if(cockpitDimControl)
            {
                if(setNew.dimPosition > 0.0F)
                    setNew.dimPosition = setOld.dimPosition - 0.05F;
            } else
            if(setNew.dimPosition < 1.0F)
                setNew.dimPosition = setOld.dimPosition + 0.05F;
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float altimeter;
        float throttlel;
        float throttler;
        float azimuth;
        float waypointAzimuth;
        float vspeed;
        float dimPosition;

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
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, fm.Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(tmpV.y, tmpV.x));
        }
    }

    public CockpitME_262()
    {
        super("3DO/Cockpit/Me-262/hier.him", "he111");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        tmpP = new Point3d();
        tmpV = new Vector3d();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bU4 = false;
        cockpitNightMats = (new java.lang.String[] {
            "2petitsb_d1", "2petitsb", "aiguill1", "badinetm_d1", "badinetm", "baguecom", "brasdele", "comptemu_d1", "comptemu", "petitfla_d1", 
            "petitfla", "turnbank"
        });
        setNightMats(false);
        setNew.dimPosition = 1.0F;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON03");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON01");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON02");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON04");
        }
        if(fm.isTick(44, 0))
        {
            mesh.chunkVisible("Z_GearLGreen1", fm.CT.getGear() == 1.0F && fm.Gears.lgear);
            mesh.chunkVisible("Z_GearRGreen1", fm.CT.getGear() == 1.0F && fm.Gears.rgear);
            mesh.chunkVisible("Z_GearCGreen1", fm.CT.getGear() == 1.0F);
            mesh.chunkVisible("Z_GearLRed1", fm.CT.getGear() == 0.0F || fm.Gears.isAnyDamaged());
            mesh.chunkVisible("Z_GearRRed1", fm.CT.getGear() == 0.0F || fm.Gears.isAnyDamaged());
            mesh.chunkVisible("Z_GearCRed1", fm.CT.getGear() == 0.0F);
            if(!bU4)
            {
                mesh.chunkVisible("Z_GunLamp01", !gun[0].haveBullets());
                mesh.chunkVisible("Z_GunLamp02", !gun[1].haveBullets());
                mesh.chunkVisible("Z_GunLamp03", !gun[2].haveBullets());
                mesh.chunkVisible("Z_GunLamp04", !gun[3].haveBullets());
            }
            mesh.chunkVisible("Z_MachLamp", fm.getSpeed() / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)fm.Loc.z) > 0.8F);
            mesh.chunkVisible("Z_CabinLamp", fm.Loc.z > 12000D);
            mesh.chunkVisible("Z_FuelLampV", fm.M.fuel < 300F);
            mesh.chunkVisible("Z_FuelLampIn", fm.M.fuel < 300F);
        }
        mesh.chunkSetAngles("Z_ReviTint", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -45F), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = fm.CT.GearControl != 0.0F || fm.CT.getGear() == 0.0F ? 0.0F : -0.0107F;
        mesh.chunkSetLocate("Z_GearEin", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = fm.CT.GearControl != 1.0F || fm.CT.getGear() == 1.0F ? 0.0F : -0.0107F;
        mesh.chunkSetLocate("Z_GearAus", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = fm.CT.FlapsControl >= fm.CT.getFlap() ? 0.0F : -0.0107F;
        mesh.chunkSetLocate("Z_FlapEin", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = fm.CT.FlapsControl <= fm.CT.getFlap() ? 0.0F : -0.0107F;
        mesh.chunkSetLocate("Z_FlapAus", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Column", 10F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        resetYPRmodifier();
        if(fm.CT.saveWeaponControl[0])
            com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.0025F;
        mesh.chunkSetLocate("Z_Columnbutton1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        if(fm.CT.saveWeaponControl[2] || fm.CT.saveWeaponControl[3])
            com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.00325F;
        mesh.chunkSetLocate("Z_Columnbutton2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_PedalStrut", 20F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", -20F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", -20F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ThrottleL", 0.0F, -75F * interp(setNew.throttlel, setOld.throttlel, f), 0.0F);
        mesh.chunkSetAngles("Z_ThrottleR", 0.0F, -75F * interp(setNew.throttler, setOld.throttler, f), 0.0F);
        mesh.chunkSetAngles("Z_FuelLeverL", fm.EI.engines[0].getControlMagnetos() != 3 ? 0.0F : 6.5F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelLeverR", fm.EI.engines[1].getControlMagnetos() != 3 ? 0.0F : 6.5F, 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.03675F * fm.CT.getTrimElevatorControl();
        mesh.chunkSetLocate("Z_TailTrim", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        if(fm.CT.Weapons[3] != null && !fm.CT.Weapons[3][0].haveBullets())
            mesh.chunkSetAngles("Z_Bombbutton", 0.0F, 53F, 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter1", cvt(gun[1].countBullets(), 0.0F, 100F, 0.0F, -7F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_AmmoCounter2", cvt(gun[2].countBullets(), 0.0F, 100F, 0.0F, -7F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 100F, 400F, 2.0F, 8F), speedometerIndScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer2", floatindex(cvt(fm.getSpeedKMH(), 100F, 1000F, 1.0F, 10F), speedometerTruScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 16000F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Second1", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank1", fm.Or.getTangage(), 0.0F, fm.Or.getKren());
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, -cvt(getBall(6D), -6F, 6F, -7.5F, 7.5F));
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(w.z, -0.23562F, 0.23562F, -50F, 50F));
        mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(setNew.vspeed, -20F, 50F, 0.0F, 14F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPML", floatindex(cvt(fm.EI.engines[0].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPMR", floatindex(cvt(fm.EI.engines[1].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_GasPressureL", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.6F * fm.EI.engines[0].getPowerOutput(), 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_GasPressureR", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.6F * fm.EI.engines[1].getPowerOutput(), 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_GasTempL", cvt(fm.EI.engines[0].tWaterOut, 300F, 1000F, 0.0F, 96F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_GasTempR", cvt(fm.EI.engines[1].tWaterOut, 300F, 1000F, 0.0F, 96F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPressureL", cvt(1.0F + 0.005F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPressureR", cvt(1.0F + 0.005F * fm.EI.engines[1].tOilOut, 0.0F, 10F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPressL", cvt(fm.M.fuel <= 1.0F ? 0.0F : 80F * fm.EI.engines[0].getPowerOutput() * fm.EI.engines[0].getReadyness(), 0.0F, 160F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPressR", cvt(fm.M.fuel <= 1.0F ? 0.0F : 80F * fm.EI.engines[1].getPowerOutput() * fm.EI.engines[1].getReadyness(), 0.0F, 160F, 0.0F, 278F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelRemainV", floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 1000F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelRemainIn", floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 1000F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("HullDamage2", true);
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("Speedometer1", false);
            mesh.chunkVisible("Speedometer1_D1", true);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_Speedometer2", false);
            mesh.chunkVisible("RPML", false);
            mesh.chunkVisible("RPML_D1", true);
            mesh.chunkVisible("Z_RPML", false);
            mesh.chunkVisible("FuelRemainV", false);
            mesh.chunkVisible("FuelRemainV_D1", true);
            mesh.chunkVisible("Z_FuelRemainV", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("HullDamage4", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("Altimeter1", false);
            mesh.chunkVisible("Altimeter1_D1", true);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("GasPressureL", false);
            mesh.chunkVisible("GasPressureL_D1", true);
            mesh.chunkVisible("Z_GasPressureL", false);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("RPMR", false);
            mesh.chunkVisible("RPMR_D1", true);
            mesh.chunkVisible("Z_RPMR", false);
            mesh.chunkVisible("FuelPressR", false);
            mesh.chunkVisible("FuelPressR_D1", true);
            mesh.chunkVisible("Z_FuelPressR", false);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("HullDamage3", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("GasPressureR", false);
            mesh.chunkVisible("GasPressureR_D1", true);
            mesh.chunkVisible("Z_GasPressureR", false);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("Climb", false);
            mesh.chunkVisible("Climb_D1", true);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("FuelPressR", false);
            mesh.chunkVisible("FuelPressR_D1", true);
            mesh.chunkVisible("Z_FuelPressR", false);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("HullDamage2", true);
            mesh.chunkVisible("Revi_D0", false);
            mesh.chunkVisible("Revi_D1", true);
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("FuelPressL", false);
            mesh.chunkVisible("FuelPressL_D1", true);
            mesh.chunkVisible("Z_FuelPressL", false);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("HullDamage1", true);
            mesh.chunkVisible("Altimeter1", false);
            mesh.chunkVisible("Altimeter1_D1", true);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Climb", false);
            mesh.chunkVisible("Climb_D1", true);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("AFN", false);
            mesh.chunkVisible("AFN_D1", true);
            mesh.chunkVisible("Z_AFN1", false);
            mesh.chunkVisible("Z_AFN2", false);
            mesh.chunkVisible("FuelPressL", false);
            mesh.chunkVisible("FuelPressL_D1", true);
            mesh.chunkVisible("Z_FuelPressL", false);
            mesh.chunkVisible("FuelRemainIn", false);
            mesh.chunkVisible("FuelRemainIn_D1", true);
            mesh.chunkVisible("Z_FuelRemainIn", false);
        }
        if((fm.AS.astateCockpitState & 0x80) == 0);
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        if(aircraft() instanceof com.maddox.il2.objects.air.ME_262A1AU4)
        {
            mesh.chunkVisible("Z_Ammo262U4", true);
            bU4 = true;
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
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null, null, null
    };
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private boolean bU4;
    private static final float speedometerIndScale[] = {
        0.0F, 0.0F, 0.0F, 17F, 35.5F, 57.5F, 76F, 95F, 112F
    };
    private static final float speedometerTruScale[] = {
        0.0F, 32.75F, 65.5F, 98.25F, 131F, 164F, 200F, 237F, 270.5F, 304F, 
        336F
    };
    private static final float variometerScale[] = {
        0.0F, 13.5F, 27F, 43.5F, 90F, 142.5F, 157F, 170.5F, 184F, 201.5F, 
        214.5F, 226F, 239.5F, 253F, 266F
    };
    private static final float rpmScale[] = {
        0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55F, 77.5F, 104F, 133.5F, 162.5F, 
        192F, 224F, 254F, 255.5F, 260F
    };
    private static final float fuelScale[] = {
        0.0F, 11F, 31F, 57F, 84F, 103.5F
    };







}
