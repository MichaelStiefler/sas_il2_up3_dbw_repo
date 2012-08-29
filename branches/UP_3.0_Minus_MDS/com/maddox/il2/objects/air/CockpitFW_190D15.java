// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitFW_190D15.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
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
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitFW_190D15 extends com.maddox.il2.objects.air.CockpitPilot
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

        Variables(com.maddox.il2.objects.air.Variables variables)
        {
            this();
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
            tmpV.sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (fm.Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(tmpV.y, tmpV.x));
        }
    }

    public CockpitFW_190D15()
    {
        super("3DO/Cockpit/FW-190D-15/hier.him", "bf109");
        bNeedSetUp = true;
        gun = new com.maddox.il2.objects.weapons.Gun[12];
        setOld = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        setNew = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        bomb = new com.maddox.il2.ai.BulletEmitter[4];
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        setNew.dimPosition = 1.0F;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (mesh)), "LIGHTHOOK_L");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", ((java.lang.Object) (light1)));
        hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (mesh)), "LIGHTHOOK_R");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(126F, 232F, 245F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK2", ((java.lang.Object) (light2)));
        cockpitNightMats = (new java.lang.String[] {
            "D9GP1", "A8GP2", "D9GP3", "A8GP4", "A8GP5", "A4GP6", "A5GP3Km", "DA8GP1", "DA8GP2", "DA8GP3", 
            "DA8GP4", "D9GP5", "D9Trans2"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        cockpitDimControl = !cockpitDimControl;
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.CT.getCockpitDoor(), 0.1F, 0.99F, 0.0F, 0.35F);
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("CanopyHandle", ((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.CT.getCockpitDoor(), 0.1F, 0.99F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Handle", -((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.CT.getCockpitDoor(), 0.1F, 0.99F, 0.0F, 3600F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        switch(((com.maddox.il2.objects.air.Cockpit)this).aircraft().chunkDamageVisible("WingLIn"))
        {
        case 0: // '\0'
            mesh.chunkVisible("WingLIn_D0", true);
            mesh.chunkVisible("WingLIn_D1", false);
            mesh.chunkVisible("WingLIn_D2", false);
            mesh.chunkVisible("WingLIn_D3", false);
            break;

        case 1: // '\001'
            mesh.chunkVisible("WingLIn_D0", false);
            mesh.chunkVisible("WingLIn_D1", true);
            mesh.chunkVisible("WingLIn_D2", false);
            mesh.chunkVisible("WingLIn_D3", false);
            break;

        case 2: // '\002'
            mesh.chunkVisible("WingLIn_D0", false);
            mesh.chunkVisible("WingLIn_D1", false);
            mesh.chunkVisible("WingLIn_D2", true);
            mesh.chunkVisible("WingLIn_D3", false);
            break;

        case 3: // '\003'
            mesh.chunkVisible("WingLIn_D0", false);
            mesh.chunkVisible("WingLIn_D1", false);
            mesh.chunkVisible("WingLIn_D2", false);
            mesh.chunkVisible("WingLIn_D3", true);
            break;

        default:
            mesh.chunkVisible("WingLIn_D0", false);
            mesh.chunkVisible("WingLIn_D1", false);
            mesh.chunkVisible("WingLIn_D2", false);
            mesh.chunkVisible("WingLIn_D3", false);
            break;
        }
        switch(((com.maddox.il2.objects.air.Cockpit)this).aircraft().chunkDamageVisible("WingRIn"))
        {
        case 0: // '\0'
            mesh.chunkVisible("WingRIn_D0", true);
            mesh.chunkVisible("WingRIn_D1", false);
            mesh.chunkVisible("WingRIn_D2", false);
            mesh.chunkVisible("WingRIn_D3", false);
            break;

        case 1: // '\001'
            mesh.chunkVisible("WingRIn_D0", false);
            mesh.chunkVisible("WingRIn_D1", true);
            mesh.chunkVisible("WingRIn_D2", false);
            mesh.chunkVisible("WingRIn_D3", false);
            break;

        case 2: // '\002'
            mesh.chunkVisible("WingRIn_D0", false);
            mesh.chunkVisible("WingRIn_D1", false);
            mesh.chunkVisible("WingRIn_D2", true);
            mesh.chunkVisible("WingRIn_D3", false);
            break;

        case 3: // '\003'
            mesh.chunkVisible("WingRIn_D0", false);
            mesh.chunkVisible("WingRIn_D1", false);
            mesh.chunkVisible("WingRIn_D2", false);
            mesh.chunkVisible("WingRIn_D3", true);
            break;

        default:
            mesh.chunkVisible("WingRIn_D0", false);
            mesh.chunkVisible("WingRIn_D1", false);
            mesh.chunkVisible("WingRIn_D2", false);
            mesh.chunkVisible("WingRIn_D3", false);
            break;
        }
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON02");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON03");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON04");
            gun[4] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON05");
            gun[5] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON06");
            gun[6] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON07");
            gun[7] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON08");
            gun[8] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON11");
            gun[9] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON12");
            gun[10] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON13");
            gun[11] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON14");
        }
        if(bomb[0] == null)
        {
            for(int i = 0; i < bomb.length; i++)
                bomb[i] = ((com.maddox.il2.ai.BulletEmitter) (com.maddox.il2.objects.weapons.GunEmpty.get()));

            if(((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb01") != com.maddox.il2.objects.weapons.GunEmpty.get())
            {
                bomb[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb01");
                bomb[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb01");
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
            t1 = com.maddox.rts.Time.current();
        }
        mesh.chunkSetAngles("NeedleALT", -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.altimeter, 0.0F, 10000F, 0.0F, -180F));
        mesh.chunkSetAngles("NeedleManPress", -((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleKMH", -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleRPM", -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleFuel", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.M.fuel / 0.72F, 0.0F, 400F, 0.0F, 4F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleWaterTemp", ((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleOilTemp", ((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 60F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleFuelPress", ((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleOilPress", -((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        float f_0_;
        if(((com.maddox.il2.objects.air.Cockpit)this).aircraft().isFMTrackMirror())
        {
            f_0_ = ((com.maddox.il2.objects.air.Cockpit)this).aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f_0_ = ((com.maddox.il2.objects.air.Cockpit)this).cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -6F, 6F, 20F, -20F);
            if(((com.maddox.il2.objects.air.Cockpit)this).aircraft().fmTrack() != null)
                ((com.maddox.il2.objects.air.Cockpit)this).aircraft().fmTrack().setCockpitAzimuthSpeed(f_0_);
        }
        mesh.chunkSetAngles("NeedleAHTurn", 0.0F, f_0_, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
            mesh.chunkSetAngles("NeedleAHBank", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(7D), -7F, 7F, -11F, 11F), 0.0F);
        mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, fm.Or.getKren());
        mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(fm.Or.getTangage(), -45F, 45F, 12F, -12F));
        mesh.chunkSetAngles("NeedleCD", setNew.vspeed < 0.0F ? ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(-setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale) : -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("RepeaterOuter", -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("RepeaterPlane", ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleHBSmall", -105F + (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 5F, 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleHBLarge", -270F + (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 2) == 0 && (fm.AS.astateCockpitState & 1) == 0 && (fm.AS.astateCockpitState & 4) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
            mesh.chunkSetAngles("NeedleTrimmung", fm.CT.getTrimElevatorControl() * 25F, 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x20) == 0)
        {
            mesh.chunkSetAngles("NeedleHClock", -((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleMClock", -((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleSClock", -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        }
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        if(gun[0] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[0].countBullets(), 0.0F, 250F, -0.044F, 0.0F);
            mesh.chunkSetLocate("RC_MG17_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[1] != null)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[1].countBullets(), 0.0F, 250F, -0.044F, 0.0F);
            mesh.chunkSetLocate("RC_MG17_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[2] != null && (float)gun[2].countBullets() > 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[2].countBullets(), 0.0F, 125F, -0.017F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[3] != null && (float)gun[3].countBullets() > 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[3].countBullets(), 0.0F, 125F, -0.017F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[4] != null && (float)gun[4].countBullets() > 0.0F && gun[5] != null && (float)gun[5].countBullets() > 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt((float)gun[4].countBullets() + (float)gun[5].countBullets(), 0.0F, 250F, -0.018F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[6] != null && (float)gun[6].countBullets() > 0.0F && gun[7] != null && (float)gun[7].countBullets() > 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt((float)gun[6].countBullets() + (float)gun[7].countBullets(), 0.0F, 250F, -0.018F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[8] != null && (float)gun[8].countBullets() > 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[8].countBullets(), 0.0F, 35F, -0.017F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[9] != null && (float)gun[9].countBullets() > 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[9].countBullets(), 0.0F, 35F, -0.017F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[10] != null && (float)gun[10].countBullets() > 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[10].countBullets(), 0.0F, 55F, -0.017F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(gun[11] != null && (float)gun[11].countBullets() > 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(gun[11].countBullets(), 0.0F, 55F, -0.017F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
        if(t1 < com.maddox.rts.Time.current())
        {
            t1 = com.maddox.rts.Time.current() + 500L;
            mesh.chunkVisible("XLampBombCL", bomb[1].haveBullets());
            mesh.chunkVisible("XLampBombCR", bomb[2].haveBullets());
        }
        mesh.chunkSetAngles("IgnitionSwitch", 24F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Revi16Tinter", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -45F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 20F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = fm.CT.WeaponControl[1] ? -0.004F : 0.0F;
        mesh.chunkSetLocate("SecTrigger", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.ypr[0] = ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f) * 34F * 0.91F;
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
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.0012F, 0.75F);
            light2.light.setEmit(0.0012F, 0.75F);
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        }
    }

    private void retoggleLight()
    {
        if(cockpitLightControl)
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
        if((fm.AS.astateCockpitState & 2) != 0 || (fm.AS.astateCockpitState & 1) != 0 || (fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.materialReplace("D9GP1", "DA8GP1");
            mesh.materialReplace("D9GP1_night", "DA8GP1_night");
            mesh.materialReplace("A8GP4", "DA8GP4");
            mesh.chunkVisible("NeedleManPress", false);
            mesh.chunkVisible("NeedleRPM", false);
            mesh.chunkVisible("RepeaterOuter", false);
            mesh.chunkVisible("RepeaterPlane", false);
            mesh.chunkVisible("NeedleHBLarge", false);
            mesh.chunkVisible("NeedleHBSmall", false);
            mesh.chunkVisible("NeedleFuel", false);
            mesh.chunkVisible("XGlassDamage1", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.materialReplace("A8GP2", "DA8GP2");
            mesh.materialReplace("A8GP2_night", "DA8GP2_night");
            ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = 0.0F;
            com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.003F;
            com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.012F;
            com.maddox.il2.objects.air.Cockpit.ypr[0] = -3F;
            com.maddox.il2.objects.air.Cockpit.ypr[1] = -3F;
            com.maddox.il2.objects.air.Cockpit.ypr[2] = 9F;
            mesh.chunkSetLocate("IPCentral", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            mesh.chunkVisible("NeedleAHCyl", false);
            mesh.chunkVisible("NeedleAHBar", false);
            mesh.chunkVisible("NeedleAHTurn", false);
            mesh.chunkVisible("NeedleFuelPress", false);
            mesh.chunkVisible("NeedleOilPress", false);
            mesh.chunkVisible("NeedleOilTemp", false);
            mesh.chunkVisible("XGlassDamage2", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.materialReplace("D9GP3", "DA8GP3");
            mesh.materialReplace("D9GP3_night", "DA8GP3_night");
            mesh.chunkVisible("NeedleKMH", false);
            mesh.chunkVisible("NeedleCD", false);
            mesh.chunkVisible("NeedleAlt", false);
            mesh.chunkVisible("NeedleAltKM Kill", false);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XGlassDamage4", true);
        }
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.objects.air.Cockpit)this).aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D1o"));
        mesh.materialReplace("Gloss2D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        mesh.materialReplace("Gloss1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
    }

    boolean bNeedSetUp;
    private float tmp;
    private com.maddox.il2.objects.weapons.Gun gun[];
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private com.maddox.il2.ai.BulletEmitter bomb[];
    private long t1;
    private float pictAiler;
    private float pictElev;
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
    private static final float oilTempScale[] = {
        0.0F, 23F, 52F, 81F, 81F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
