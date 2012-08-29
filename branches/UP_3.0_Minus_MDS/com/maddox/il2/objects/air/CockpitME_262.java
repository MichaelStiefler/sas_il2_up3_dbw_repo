// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitME_262.java

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

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft, ME_262A1AU4

public class CockpitME_262 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
            setNew.throttlel = (10F * setOld.throttlel + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlThrottle()) / 11F;
            setNew.throttler = (10F * setOld.throttler + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlThrottle()) / 11F;
            setNew.azimuth = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getYaw();
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            setNew.vspeed = (299F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 300F;
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

        Variables(com.maddox.il2.objects.air.Variables variables)
        {
            this();
        }
    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            ((com.maddox.JGP.Tuple3d) (tmpV)).sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public CockpitME_262()
    {
        super("3DO/Cockpit/Me-262/hier.him", "he111");
        setOld = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        setNew = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        w = new Vector3f();
        tmpP = new Point3d();
        tmpV = new Vector3d();
        gun = new com.maddox.il2.objects.weapons.Gun[4];
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bU4 = false;
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "2petitsb_d1", "2petitsb", "aiguill1", "badinetm_d1", "badinetm", "baguecom", "brasdele", "comptemu_d1", "comptemu", "petitfla_d1", 
            "petitfla", "turnbank"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        setNew.dimPosition = 1.0F;
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void removeCanopy()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Canopy", false);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes2_D1", false);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Holes1_D1", false);
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
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON03");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON01");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON02");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON04");
        }
        if(((com.maddox.il2.fm.FMMath) (((com.maddox.il2.objects.air.Cockpit)this).fm)).isTick(44, 0))
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearLGreen1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 1.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearRGreen1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 1.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearCGreen1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 1.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearLRed1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 0.0F || ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.isAnyDamaged());
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearRRed1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 0.0F || ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.isAnyDamaged());
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearCRed1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 0.0F);
            if(!bU4)
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GunLamp01", !((com.maddox.il2.engine.GunGeneric) (gun[0])).haveBullets());
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GunLamp02", !((com.maddox.il2.engine.GunGeneric) (gun[1])).haveBullets());
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GunLamp03", !((com.maddox.il2.engine.GunGeneric) (gun[2])).haveBullets());
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GunLamp04", !((com.maddox.il2.engine.GunGeneric) (gun[3])).haveBullets());
            }
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_MachLamp", ((com.maddox.il2.objects.air.Cockpit)this).fm.getSpeed() / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z) > 0.8F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_CabinLamp", ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z > 12000D);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FuelLampV", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel < 300F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FuelLampIn", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel < 300F);
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ReviTint", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -45F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Canopy", 0.0F, 0.0F, -100F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor());
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl != 0.0F || ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 0.0F ? 0.0F : -0.0107F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_GearEin", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl != 1.0F || ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 1.0F ? 0.0F : -0.0107F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_GearAus", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl >= ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getFlap() ? 0.0F : -0.0107F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlapEin", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl <= ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getFlap() ? 0.0F : -0.0107F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlapAus", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Column", 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl));
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.saveWeaponControl[0])
            com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.0025F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Columnbutton1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.saveWeaponControl[2] || ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.saveWeaponControl[3])
            com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.00325F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Columnbutton2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_PedalStrut", 20F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_LeftPedal", -20F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RightPedal", -20F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ThrottleL", 0.0F, -75F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttlel, setOld.throttlel, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ThrottleR", 0.0F, -75F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttler, setOld.throttler, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FuelLeverL", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlMagnetos() != 3 ? 0.0F : 6.5F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FuelLeverR", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].getControlMagnetos() != 3 ? 0.0F : 6.5F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.03675F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getTrimElevatorControl();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_TailTrim", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.Weapons[3] != null && !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.Weapons[3][0].haveBullets())
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Bombbutton", 0.0F, 53F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.GunGeneric) (gun[1])).countBullets(), 0.0F, 100F, 0.0F, -7F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.GunGeneric) (gun[2])).countBullets(), 0.0F, 100F, 0.0F, -7F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Speedometer1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 100F, 400F, 2.0F, 8F), speedometerIndScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Speedometer2", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH(), 100F, 1000F, 1.0F, 10F), speedometerTruScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 16000F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Hour1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Minute1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Second1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TurnBank1", ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), 0.0F, ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren());
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(6D), -6F, 6F, -7.5F, 7.5F));
        ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getW())));
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, -50F, 50F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Climb1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -20F, 50F, 0.0F, 14F), variometerScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPML", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPMR", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].getRPM() * 10F * 0.25F, 2000F, 14000F, 2.0F, 14F), rpmScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass1", ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass2", -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_GasPressureL", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel <= 1.0F ? 0.0F : 0.6F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getPowerOutput(), 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_GasPressureR", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel <= 1.0F ? 0.0F : 0.6F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].getPowerOutput(), 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_GasTempL", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut, 300F, 1000F, 0.0F, 96F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_GasTempR", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].tWaterOut, 300F, 1000F, 0.0F, 96F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_OilPressureL", ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.005F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 278F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_OilPressureR", ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.005F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].tOilOut, 0.0F, 10F, 0.0F, 278F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FuelPressL", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel <= 1.0F ? 0.0F : 80F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getPowerOutput() * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getReadyness(), 0.0F, 160F, 0.0F, 278F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FuelPressR", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel <= 1.0F ? 0.0F : 80F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].getPowerOutput() * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].getReadyness(), 0.0F, 160F, 0.0F, 278F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FuelRemainV", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel / 0.72F, 0.0F, 1000F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FuelRemainIn", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel / 0.72F, 0.0F, 1000F, 0.0F, 5F), fuelScale), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Speedometer1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Speedometer1_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Speedometer1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Speedometer2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("RPML", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("RPML_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_RPML", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelRemainV", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelRemainV_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FuelRemainV", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage4", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Altimeter1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Altimeter1_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Altimeter1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Altimeter2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("GasPressureL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("GasPressureL_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GasPressureL", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("RPMR", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("RPMR_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_RPMR", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelPressR", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelPressR_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FuelPressR", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("GasPressureR", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("GasPressureR_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GasPressureR", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Climb", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Climb_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Climb1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelPressR", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelPressR_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FuelPressR", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Revi_D0", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Revi_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_MASK", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelPressL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelPressL_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FuelPressL", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Altimeter1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Altimeter1_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Altimeter1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Altimeter2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Climb", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Climb_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Climb1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("AFN", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("AFN_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_AFN1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_AFN2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelPressL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelPressL_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FuelPressL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelRemainIn", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FuelRemainIn_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FuelRemainIn", false);
        }
        ((java.lang.Object) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS)).getClass();
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D0o", mat);
        if(((com.maddox.il2.objects.air.Cockpit)this).aircraft() instanceof com.maddox.il2.objects.air.ME_262A1AU4)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Ammo262U4", true);
            bU4 = true;
        }
    }

    public void toggleDim()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl;
    }

    public void toggleLight()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl;
        if(((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl)
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
    }

    private void retoggleLight()
    {
        if(((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
        } else
        {
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        }
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
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
