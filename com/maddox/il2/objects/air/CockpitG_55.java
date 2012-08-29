// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitG_55.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitG_55 extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float throttle;
        float prop;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.throttle = 0.85F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl * 0.15F;
                setNew.prop = 0.85F * setOld.prop + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.getStepControl() * 0.15F;
                setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).azimut());
                setNew.vspeed = (199F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 200F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitG_55()
    {
        super("3DO/Cockpit/MC-205/hier.him", "bf109");
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictMix = 0.0F;
        pictFlap = 0.0F;
        pictGear = 0.0F;
        pictManf = 1.0F;
        pictFuel = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "mat2_tr", "strum1dmg", "strum2dmg", "strum4dmg", "strum1", "strum2", "strum4", "strumsxdmg", "strumsx", "ruddersystem"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        if(((com.maddox.il2.engine.Actor)this).acoustics != null)
            ((com.maddox.il2.engine.Actor)this).acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.ai.BulletEmitter) (((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_MGUN01")));
            gun[1] = ((com.maddox.il2.ai.BulletEmitter) (((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_MGUN02")));
            gun[2] = ((com.maddox.il2.ai.BulletEmitter) (((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_MGUN03")));
            gun[3] = ((com.maddox.il2.ai.BulletEmitter) (((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_MGUN04")));
            if(gun[2].countBullets() <= 0)
            {
                gun[2] = ((com.maddox.il2.ai.BulletEmitter) (((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON01")));
                gun[3] = ((com.maddox.il2.ai.BulletEmitter) (((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON02")));
            }
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", !((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE2", ((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Column", 16F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F, 8F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Pedals", 15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Throttle", -49.54F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_MagnetoSwitch", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 14F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Mix", 0.0F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_PropPitch1", -61.5F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_PropPitch2", 0.0F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Flap1", 45F * (pictFlap = 0.75F * pictFlap + 0.25F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Gear1", -32F * (pictGear = 0.8F * pictGear + 0.2F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Trim1", -76.5F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getTrimElevatorControl(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.06325F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_OilRad1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.01625F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_OilRad2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_OilRad3", 0.0F, 0.0F, 0.0F);
        if(gun[0].haveBullets())
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter1", 0.36F * (float)gun[0].countBullets(), 0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter2", 3.6F * (float)gun[0].countBullets(), 0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter3", 36F * (float)gun[0].countBullets(), 0.0F, 0.0F);
        }
        if(gun[1].haveBullets())
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter4", 0.36F * (float)gun[1].countBullets(), 0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter5", 3.6F * (float)gun[1].countBullets(), 0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter6", 36F * (float)gun[1].countBullets(), 0.0F, 0.0F);
        }
        if(gun[2].haveBullets())
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter7", ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[2].countBullets(), 0.0F, 500F, 7F, 358F), 0.0F, 0.0F);
        if(gun[3].haveBullets())
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AmmoCounter8", ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[3].countBullets(), 0.0F, 500F, 7F, 358F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.06055F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Cooling", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getFlap(), 0.0F, 1.0F, 0.0F, 0.05475F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlapPos", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Speedometer1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 100F, 700F, 0.0F, 12F), speedometerScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Climb1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -25F, 25F, -180F, 180F), 0.0F, 0.0F);
        float f1 = setNew.azimuth.getDeg(f) - 90F;
        if(f1 < 0.0F)
            f1 += 360F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass1", f1, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPM1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 322.5F), 0.0F, 0.0F);
        ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getW())));
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turn1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 21F, -21F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turn2", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(8D), -8F, 8F, -11F, 11F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ManfoldPress", -(pictManf = 0.9F * pictManf + 0.1F * ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getManifoldPressure(), 0.5F, 2.0F, 0.0F, -300F)), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Hour1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Minute1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_EngTemp1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut, 30F, 130F, 0.0F, 10F), waterTempScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_OilTemp1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 30F, 150F, 0.0F, 4F), oilTempScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_OilPress1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 184F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_OilPress2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 300F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AirPress1", -135F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AirPress2", -135F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FuelPress1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, -300F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel, 0.0F, 252F, 0.0F, 0.3661F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FuelQuantity1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -45F, 45F, 0.0135F, -0.0135F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Horison1c", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Horison1a", ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Horison1b", 0.0F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGunL", gun[0].countBullets() < 90);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGunR", gun[1].countBullets() < 90);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampCanL", gun[2].countBullets() < 90);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampCanR", gun[3].countBullets() < 90);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampSPIA", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM() < 1200F);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) == 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearUpR", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearUpL", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearDownR", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearDownL", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearDownC", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F);
        } else
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearUpR", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearUpL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearDownR", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearDownL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearDownC", false);
        }
    }

    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AP.way.curr();
        if(waypoint == null)
            return 0.0F;
        waypoint.getP(tmpP);
        ((com.maddox.JGP.Tuple3d) (tmpV)).sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)));
        float f;
        for(f = (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x)); f <= -180F; f += 180F);
        for(; f > 180F; f -= 180F);
        return f;
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) != 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_OilSplats_D1", true);
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D0o", mat);
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

    public void toggleDim()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl;
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictMix;
    private float pictFlap;
    private float pictGear;
    private float pictManf;
    private float pictFuel;
    private com.maddox.il2.ai.BulletEmitter gun[] = {
        null, null, null, null
    };
    private static final float speedometerScale[] = {
        0.0F, 38.5F, 77F, 100.5F, 125F, 147F, 169.5F, 193.5F, 216.5F, 240.5F, 
        265F, 287.5F, 303.5F
    };
    private static final float waterTempScale[] = {
        0.0F, 20.5F, 37F, 52F, 73.5F, 95.5F, 120F, 150F, 192F, 245F, 
        302F
    };
    private static final float oilTempScale[] = {
        0.0F, 33F, 80F, 153F, 301.5F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
