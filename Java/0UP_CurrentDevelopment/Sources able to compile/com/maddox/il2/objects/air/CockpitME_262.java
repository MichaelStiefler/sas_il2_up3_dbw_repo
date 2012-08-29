package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitME_262 extends CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = fm.getAltitude();
            setNew.throttlel = (10F * setOld.throttlel + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlThrottle()) / 11F;
            setNew.throttler = (10F * setOld.throttler + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlThrottle()) / 11F;
            setNew.azimuth = ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getYaw();
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

        Variables(Variables variables)
        {
            this();
        }
    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public CockpitME_262()
    {
        super("3DO/Cockpit/Me-262/hier.him", "he111");
        setOld = new Variables(null);
        setNew = new Variables(null);
        w = new Vector3f();
        tmpP = new Point3d();
        tmpV = new Vector3d();
        gun = new com.maddox.il2.objects.weapons.Gun[4];
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bU4 = false;
        super.cockpitNightMats = (new java.lang.String[] {
            "2petitsb_d1", "2petitsb", "aiguill1", "badinetm_d1", "badinetm", "baguecom", "brasdele", "comptemu_d1", "comptemu", "petitfla_d1", 
            "petitfla", "turnbank"
        });
        setNightMats(false);
        setNew.dimPosition = 1.0F;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void removeCanopy()
    {
        super.mesh.chunkVisible("Canopy", false);
        super.mesh.chunkVisible("Z_Holes2_D1", false);
        super.mesh.chunkVisible("Z_Holes1_D1", false);
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
            gun[0] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON03");
            gun[1] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON01");
            gun[2] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON02");
            gun[3] = ((Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON04");
        }
        if(super.fm.isTick(44, 0))
        {
            super.mesh.chunkVisible("Z_GearLGreen1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
            super.mesh.chunkVisible("Z_GearRGreen1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
            super.mesh.chunkVisible("Z_GearCGreen1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F);
            super.mesh.chunkVisible("Z_GearLRed1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F || ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.isAnyDamaged());
            super.mesh.chunkVisible("Z_GearRRed1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F || ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.isAnyDamaged());
            super.mesh.chunkVisible("Z_GearCRed1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F);
            if(!bU4)
            {
                super.mesh.chunkVisible("Z_GunLamp01", !gun[0].haveBullets());
                super.mesh.chunkVisible("Z_GunLamp02", !gun[1].haveBullets());
                super.mesh.chunkVisible("Z_GunLamp03", !gun[2].haveBullets());
                super.mesh.chunkVisible("Z_GunLamp04", !gun[3].haveBullets());
            }
            super.mesh.chunkVisible("Z_MachLamp", super.fm.getSpeed() / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z) > 0.8F);
            super.mesh.chunkVisible("Z_CabinLamp", ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z > 12000D);
            super.mesh.chunkVisible("Z_FuelLampV", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 300F);
            super.mesh.chunkVisible("Z_FuelLampIn", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 300F);
        }
        super.mesh.chunkSetAngles("Z_ReviTint", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -45F), 0.0F, 0.0F);
        resetYPRmodifier();
        super.mesh.chunkSetAngles("Canopy", 0.0F, 0.0F, -100F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor());
        Cockpit.xyz[1] = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl != 0.0F || ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F ? 0.0F : -0.0107F;
        super.mesh.chunkSetLocate("Z_GearEin", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[1] = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl != 1.0F || ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F ? 0.0F : -0.0107F;
        super.mesh.chunkSetLocate("Z_GearAus", Cockpit.xyz, Cockpit.ypr);
        resetYPRmodifier();
        Cockpit.xyz[1] = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl >= ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() ? 0.0F : -0.0107F;
        super.mesh.chunkSetLocate("Z_FlapEin", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[1] = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl <= ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() ? 0.0F : -0.0107F;
        super.mesh.chunkSetLocate("Z_FlapAus", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_Column", 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl));
        resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.saveWeaponControl[0])
            Cockpit.xyz[2] = -0.0025F;
        super.mesh.chunkSetLocate("Z_Columnbutton1", Cockpit.xyz, Cockpit.ypr);
        resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.saveWeaponControl[2] || ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.saveWeaponControl[3])
            Cockpit.xyz[2] = -0.00325F;
        super.mesh.chunkSetLocate("Z_Columnbutton2", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_PedalStrut", 20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_LeftPedal", -20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RightPedal", -20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_ThrottleL", 0.0F, -75F * interp(setNew.throttlel, setOld.throttlel, f), 0.0F);
        super.mesh.chunkSetAngles("Z_ThrottleR", 0.0F, -75F * interp(setNew.throttler, setOld.throttler, f), 0.0F);
        super.mesh.chunkSetAngles("Z_FuelLeverL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlMagnetos() != 3 ? 0.0F : 6.5F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelLeverR", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getControlMagnetos() != 3 ? 0.0F : 6.5F, 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[1] = 0.03675F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl();
        super.mesh.chunkSetLocate("Z_TailTrim", Cockpit.xyz, Cockpit.ypr);
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.Weapons[3] != null && !((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.Weapons[3][0].haveBullets())
            super.mesh.chunkSetAngles("Z_Bombbutton", 0.0F, 53F, 0.0F);
        super.mesh.chunkSetAngles("Z_AmmoCounter1", cvt(gun[1].countBullets(), 0.0F, 100F, 0.0F, -7F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_AmmoCounter2", cvt(gun[2].countBullets(), 0.0F, 100F, 0.0F, -7F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 100F, 400F, 2.0F, 8F), speedometerIndScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Speedometer2", floatindex(cvt(super.fm.getSpeedKMH(), 100F, 1000F, 1.0F, 10F), speedometerTruScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 16000F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Second1", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TurnBank1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren());
        super.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, -cvt(getBall(6D), -6F, 6F, -7.5F, 7.5F));
        w.set(super.fm.getW());
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.transform(w);
        super.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, -50F, 50F));
        super.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(setNew.vspeed, -20F, 50F, 0.0F, 14F), variometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPML", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPMR", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Compass1", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Compass2", -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_GasPressureL", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : 0.6F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPowerOutput(), 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_GasPressureR", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : 0.6F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getPowerOutput(), 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_GasTempL", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 300F, 1000F, 0.0F, 96F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_GasTempR", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tWaterOut, 300F, 1000F, 0.0F, 96F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_OilPressureL", cvt(1.0F + 0.005F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 278F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_OilPressureR", cvt(1.0F + 0.005F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tOilOut, 0.0F, 10F, 0.0F, 278F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelPressL", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : 80F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPowerOutput() * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getReadyness(), 0.0F, 160F, 0.0F, 278F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelPressR", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : 80F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getPowerOutput() * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getReadyness(), 0.0F, 160F, 0.0F, 278F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelRemainV", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel / 0.72F, 0.0F, 1000F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelRemainIn", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel / 0.72F, 0.0F, 1000F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
        {
            super.mesh.chunkVisible("HullDamage2", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
            super.mesh.chunkVisible("Speedometer1", false);
            super.mesh.chunkVisible("Speedometer1_D1", true);
            super.mesh.chunkVisible("Z_Speedometer1", false);
            super.mesh.chunkVisible("Z_Speedometer2", false);
            super.mesh.chunkVisible("RPML", false);
            super.mesh.chunkVisible("RPML_D1", true);
            super.mesh.chunkVisible("Z_RPML", false);
            super.mesh.chunkVisible("FuelRemainV", false);
            super.mesh.chunkVisible("FuelRemainV_D1", true);
            super.mesh.chunkVisible("Z_FuelRemainV", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0)
        {
            super.mesh.chunkVisible("HullDamage4", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("Altimeter1", false);
            super.mesh.chunkVisible("Altimeter1_D1", true);
            super.mesh.chunkVisible("Z_Altimeter1", false);
            super.mesh.chunkVisible("Z_Altimeter2", false);
            super.mesh.chunkVisible("GasPressureL", false);
            super.mesh.chunkVisible("GasPressureL_D1", true);
            super.mesh.chunkVisible("Z_GasPressureL", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
        {
            super.mesh.chunkVisible("HullDamage1", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
            super.mesh.chunkVisible("RPMR", false);
            super.mesh.chunkVisible("RPMR_D1", true);
            super.mesh.chunkVisible("Z_RPMR", false);
            super.mesh.chunkVisible("FuelPressR", false);
            super.mesh.chunkVisible("FuelPressR_D1", true);
            super.mesh.chunkVisible("Z_FuelPressR", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
        {
            super.mesh.chunkVisible("HullDamage3", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("GasPressureR", false);
            super.mesh.chunkVisible("GasPressureR_D1", true);
            super.mesh.chunkVisible("Z_GasPressureR", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("XGlassDamage2", true);
            super.mesh.chunkVisible("Climb", false);
            super.mesh.chunkVisible("Climb_D1", true);
            super.mesh.chunkVisible("Z_Climb1", false);
            super.mesh.chunkVisible("FuelPressR", false);
            super.mesh.chunkVisible("FuelPressR_D1", true);
            super.mesh.chunkVisible("Z_FuelPressR", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("HullDamage1", true);
            super.mesh.chunkVisible("HullDamage2", true);
            super.mesh.chunkVisible("Revi_D0", false);
            super.mesh.chunkVisible("Revi_D1", true);
            super.mesh.chunkVisible("Z_Z_RETICLE", false);
            super.mesh.chunkVisible("Z_Z_MASK", false);
            super.mesh.chunkVisible("FuelPressL", false);
            super.mesh.chunkVisible("FuelPressL_D1", true);
            super.mesh.chunkVisible("Z_FuelPressL", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0)
        {
            super.mesh.chunkVisible("HullDamage1", true);
            super.mesh.chunkVisible("Altimeter1", false);
            super.mesh.chunkVisible("Altimeter1_D1", true);
            super.mesh.chunkVisible("Z_Altimeter1", false);
            super.mesh.chunkVisible("Z_Altimeter2", false);
            super.mesh.chunkVisible("Climb", false);
            super.mesh.chunkVisible("Climb_D1", true);
            super.mesh.chunkVisible("Z_Climb1", false);
            super.mesh.chunkVisible("AFN", false);
            super.mesh.chunkVisible("AFN_D1", true);
            super.mesh.chunkVisible("Z_AFN1", false);
            super.mesh.chunkVisible("Z_AFN2", false);
            super.mesh.chunkVisible("FuelPressL", false);
            super.mesh.chunkVisible("FuelPressL_D1", true);
            super.mesh.chunkVisible("Z_FuelPressL", false);
            super.mesh.chunkVisible("FuelRemainIn", false);
            super.mesh.chunkVisible("FuelRemainIn_D1", true);
            super.mesh.chunkVisible("Z_FuelRemainIn", false);
        }
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.getClass();
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        super.mesh.materialReplace("Gloss1D0o", mat);
        if(aircraft() instanceof ME_262A1AU4)
        {
            super.mesh.chunkVisible("Z_Ammo262U4", true);
            bU4 = true;
        }
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    public void toggleLight()
    {
        super.cockpitLightControl = !super.cockpitLightControl;
        if(super.cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    private void retoggleLight()
    {
        if(super.cockpitLightControl)
        {
            setNightMats(false);
            setNightMats(true);
        } else
        {
            setNightMats(true);
            setNightMats(false);
        }
    }

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;
    private com.maddox.il2.objects.weapons.Gun gun[];
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
