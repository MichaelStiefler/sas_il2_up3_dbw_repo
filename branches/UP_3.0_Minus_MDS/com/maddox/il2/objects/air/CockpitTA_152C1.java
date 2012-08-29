// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitTA_152C1.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, TA_152C1, Aircraft, Cockpit, 
//            NetAircraft

public class CockpitTA_152C1 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
                setNew.vspeed = (499F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 500F;
                setNew.azimuth = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                float f = ((com.maddox.il2.objects.air.TA_152C1)aircraft()).k14Distance;
                setNew.k14w = (5F * com.maddox.il2.objects.air.CockpitTA_152C1.k14TargetWingspanScale[((com.maddox.il2.objects.air.TA_152C1)aircraft()).k14WingspanType]) / f;
                setNew.k14w = 0.9F * setOld.k14w + 0.1F * setNew.k14w;
                setNew.k14wingspan = 0.9F * setOld.k14wingspan + 0.1F * com.maddox.il2.objects.air.CockpitTA_152C1.k14TargetMarkScale[((com.maddox.il2.objects.air.TA_152C1)aircraft()).k14WingspanType];
                setNew.k14mode = 0.8F * setOld.k14mode + 0.2F * (float)((com.maddox.il2.objects.air.TA_152C1)aircraft()).k14Mode;
                com.maddox.JGP.Vector3d vector3d = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM)).getW();
                double d = 0.00125D * (double)f;
                float f1 = (float)java.lang.Math.toDegrees(d * ((com.maddox.JGP.Tuple3d) (vector3d)).z);
                float f2 = -(float)java.lang.Math.toDegrees(d * ((com.maddox.JGP.Tuple3d) (vector3d)).y);
                float f3 = floatindex((f - 200F) * 0.04F, com.maddox.il2.objects.air.CockpitTA_152C1.k14BulletDrop) - com.maddox.il2.objects.air.CockpitTA_152C1.k14BulletDrop[0];
                f2 += (float)java.lang.Math.toDegrees(java.lang.Math.atan(f3 / f));
                setNew.k14x = 0.92F * setOld.k14x + 0.08F * f1;
                setNew.k14y = 0.92F * setOld.k14y + 0.08F * f2;
                if(setNew.k14x > 7F)
                    setNew.k14x = 7F;
                if(setNew.k14x < -7F)
                    setNew.k14x = -7F;
                if(setNew.k14y > 7F)
                    setNew.k14y = 7F;
                if(setNew.k14y < -7F)
                    setNew.k14y = -7F;
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
        float k14wingspan;
        float k14mode;
        float k14x;
        float k14y;
        float k14w;

        private Variables()
        {
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
            waypoint.getP(com.maddox.il2.objects.air.Cockpit.P1);
            ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.V)).sub(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.P1)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.V)).y, ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Cockpit.V)).x));
        }
    }

    public CockpitTA_152C1()
    {
        super("3DO/Cockpit/Ta-152C1/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictGear = 0.0F;
        setNew.dimPosition = 1.0F;
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "D9GP1", "D9GP2", "Ta152GP3", "A5GP3Km", "Ta152GP4_MW50", "Ta152GP5", "A4GP6", "Ta152Trans2", "D9GP2"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl;
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void reflectWorldToInstruments(float f)
    {
        int i = ((com.maddox.il2.objects.air.TA_152C1)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).k14Mode;
        boolean flag = i < 2;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", flag);
        flag = i > 0;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE1", flag);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, setNew.k14x, setNew.k14y);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = setNew.k14w;
        for(int j = 1; j < 7; j++)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_AIMMARK" + j, flag);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Z_AIMMARK" + j, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }

        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON03");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON04");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON05");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_CANNON06");
        }
        if(bomb[0] == null)
        {
            for(int k = 0; k < bomb.length; k++)
                bomb[k] = ((com.maddox.il2.ai.BulletEmitter) (com.maddox.il2.objects.weapons.GunEmpty.get()));

            if(((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalBomb05") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[1] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalBomb05");
                bomb[2] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalBomb05");
            } else
            if(((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalDev02") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[1] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalDev02");
                bomb[2] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalDev02");
            }
            if(((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalRock01") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalRock01");
                bomb[3] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalRock02");
            } else
            if(((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalBomb03") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalBomb03");
                bomb[3] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalBomb04");
            } else
            if(((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalDev05") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalDev05");
                bomb[3] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getBulletEmitterByHookName("_ExternalDev06");
            }
        }
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        float f1;
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl == 0.0F)
            f1 = 0.0205F;
        else
            f1 = 0.0F;
        pictGear = 0.93F * pictGear + 0.07F * f1;
        com.maddox.il2.objects.air.Cockpit.xyz[2] = pictGear;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FahrHandle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) == 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleALT", -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.altimeter, 0.0F, 10000F, 0.0F, -180F));
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleManPress", -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 15F, 345F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleKMH", -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) == 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleRPM", -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleFuel", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel / 0.72F, 0.0F, 400F, 0.0F, 4F), fuelScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleWaterTemp", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 60F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleOilTemp", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 60F), 0.0F, 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) == 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleFuelPress", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleOilPress", -((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) == 0)
        {
            float f2;
            if(((com.maddox.il2.objects.air.NetAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).isFMTrackMirror())
            {
                f2 = ((com.maddox.il2.objects.air.NetAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).fmTrack().getCockpitAzimuthSpeed();
            } else
            {
                f2 = ((com.maddox.il2.objects.air.Cockpit)this).cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -6F, 6F, 20F, -20F);
                if(((com.maddox.il2.objects.air.NetAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).fmTrack() != null)
                    ((com.maddox.il2.objects.air.NetAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).fmTrack().setCockpitAzimuthSpeed(f2);
            }
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleAHTurn", 0.0F, f2, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleAHBank", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(6D), -6F, 6F, 11F, -11F), 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren());
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -45F, 45F, 12F, -12F));
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) == 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleCD", setNew.vspeed < 0.0F ? ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(-setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale) : -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale), 0.0F, 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) == 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RepeaterOuter", -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RepeaterPlane", ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) == 0 && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) == 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedleTrimmung", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getTrimElevatorControl() * 25F * 2.0F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        if(gun[1] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.GunGeneric) (gun[1])).countBullets(), 0.0F, 500F, -0.046F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("RC_MG17_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[2] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.GunGeneric) (gun[2])).countBullets(), 0.0F, 500F, -0.046F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("RC_MG17_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[0] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.GunGeneric) (gun[0])).countBullets(), 0.0F, 500F, -0.019F, 0.024F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("RC_MG151_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[3] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.GunGeneric) (gun[3])).countBullets(), 0.0F, 500F, -0.019F, 0.024F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("RC_MG151_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("IgnitionSwitch", 24F * (float)((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl) * 20F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.WeaponControl[1] ? -0.004F : 0.0F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("SecTrigger", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.ypr[0] = ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f) * 34F * 0.91F;
        com.maddox.il2.objects.air.Cockpit.xyz[2] = com.maddox.il2.objects.air.Cockpit.ypr[0] <= 7F ? 0.0F : -0.006F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("ThrottleQuad", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RPedalBase", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder() * 15F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RPedalStrut", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder() * 15F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RPedal", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder() * 15F - ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getBrake() * 15F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("LPedalBase", 0.0F, 0.0F, -((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder() * 15F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("LPedalStrut", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder() * 15F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("LPedal", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder() * 15F - ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getBrake() * 15F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampTankSwitch", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 144F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampFuelLow", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel < 43.2F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearL_1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.05F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearL_2", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.95F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearR_1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.05F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearR_2", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.95F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearC_1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.05F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearC_2", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.95F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl >= 0.5F ? 0.0F : 0.02F;
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

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Revi16", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Revi16Tinter", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_MASK", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DRevi16", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("D9GP1", "DD9GP1");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("D9GP1_night", "DD9GP1_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleManPress", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleRPM", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("RepeaterOuter", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("RepeaterPlane", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("D9GP2", "DD9GP2");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("D9GP2_night", "DD9GP2_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleAHCyl", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleAHBank", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleAHBar", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleAHTurn", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleFuelPress", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleOilPress", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Ta152GP4_MW50", "DTa152GP4");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Ta152GP4_MW50_night", "DTa152GP4_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleFuel", false);
            ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = -0.001F;
            com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.008F;
            com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.025F;
            com.maddox.il2.objects.air.Cockpit.ypr[0] = 3F;
            com.maddox.il2.objects.air.Cockpit.ypr[1] = -6F;
            com.maddox.il2.objects.air.Cockpit.ypr[2] = 1.5F;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("IPCentral", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Ta152GP3", "DTa152GP3");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Ta152GP3_night", "DTa152GP3_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleKMH", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleCD", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleAlt", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleAltKM", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Ta152Trans2", "DTa152Trans2");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Ta152Trans2_night", "DTa152Trans2_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleWaterTemp", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedleOilTemp", false);
        }
        retoggleLight();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
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
    private static final float k14TargetMarkScale[] = {
        -0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70F, -95F, -102.5F, -106F
    };
    private static final float k14TargetWingspanScale[] = {
        9.9F, 10.52F, 13.8F, 16.34F, 19F, 20F, 22F, 29.25F, 30F, 32.85F
    };
    private static final float k14BulletDrop[] = {
        5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 
        8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928F, 9.992F, 10.282F, 10.381F, 10.513F, 
        10.603F, 10.704F, 10.739F, 10.782F, 10.789F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitTA_152C1.class)))), "normZN", 0.72F);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitTA_152C1.class)))), "gsZN", 0.66F);
    }









}
