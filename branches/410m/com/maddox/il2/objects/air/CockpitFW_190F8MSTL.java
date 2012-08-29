// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitFW_190F8MSTL.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, TypeDockable, AircraftLH

public class CockpitFW_190F8MSTL extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle0 = 0.9F * setOld.throttle0 + 0.1F * fm.EI.engines[0].getControlThrottle();
                if(fm.EI.getNum() > 1)
                {
                    setNew.throttle1 = 0.9F * setOld.throttle1 + 0.1F * fm.EI.engines[1].getControlThrottle();
                    setNew.throttle2 = 0.9F * setOld.throttle2 + 0.1F * fm.EI.engines[2].getControlThrottle();
                }
                setNew.vspeed = (499F * setOld.vspeed + fm.getVertSpeed()) / 500F;
                float f = waypointAzimuth();
                if(useRealisticNavigationInstruments())
                {
                    setNew.waypointAzimuth.setDeg(f - 90F);
                    setOld.waypointAzimuth.setDeg(f - 90F);
                } else
                {
                    setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f - setOld.azimuth.getDeg(1.0F));
                }
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                w.set(fm.getW());
                fm.Or.transform(w);
                setNew.turn = (12F * setOld.turn + w.z) / 13F;
                setNew.beaconDirection = (10F * setOld.beaconDirection + getBeaconDirection()) / 11F;
                setNew.beaconRange = (10F * setOld.beaconRange + getBeaconRange()) / 11F;
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
        float throttle0;
        float throttle1;
        float throttle2;
        float dimPosition;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float turn;
        float beaconDirection;
        float beaconRange;
        float vspeed;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    protected float waypointAzimuth()
    {
        return waypointAzimuthInvertMinus(30F);
    }

    public CockpitFW_190F8MSTL()
    {
        super("3DO/Cockpit/FW-190F-8Mistel/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        w = new Vector3f();
        setNew.dimPosition = 1.0F;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "LIGHTHOOK_L");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        hooknamed = new HookNamed(mesh, "LIGHTHOOK_R");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(126F, 232F, 245F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK2", light2);
        cockpitNightMats = (new java.lang.String[] {
            "D9GP1", "A8GP2", "D9GP3", "F8SWGP4", "F8SWGP5", "A4GP6", "A5GP3Km", "DA8GP1", "DA8GP2", "DA8GP3", 
            "DF8SWGP4"
        });
        setNightMats(false);
        cockpitDimControl = !cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN02");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON03");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON04");
            gun[4] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON01");
            gun[5] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_CANNON02");
        }
        if(bomb[0] == null)
        {
            for(int i = 0; i < bomb.length; i++)
                bomb[i] = ((com.maddox.il2.objects.air.Aircraft)(com.maddox.il2.objects.air.Aircraft)fm.actor).getBulletEmitterByHookName("_ExternalBomb0" + (i + 1));

            t1 = com.maddox.rts.Time.current();
        }
        mesh.chunkSetAngles("NeedleALT", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(setNew.altimeter, 0.0F, 10000F, 0.0F, -180F));
        mesh.chunkSetAngles("NeedleManPress", -cvt(fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336F), 0.0F, 0.0F);
        if(fm.EI.getNum() > 1)
        {
            mesh.chunkSetAngles("NeedleMP88_L", -cvt(fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336F), 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleMP88_R", -cvt(fm.EI.engines[2].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        if(fm.EI.getNum() > 1)
        {
            mesh.chunkSetAngles("NeedleRPM88_L", -floatindex(cvt(fm.EI.engines[1].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleRPM88_R", -floatindex(cvt(fm.EI.engines[2].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("NeedleFuel", floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 400F, 0.0F, 4F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleOilTemp", floatindex(cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 3F), oilTempScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleFuelPress", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleAHTurn", 0.0F, cvt(setNew.turn, -0.23562F, 0.23562F, 25F, -25F), 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
            mesh.chunkSetAngles("NeedleAHBank", 0.0F, cvt(getBall(7D), -7F, 7F, -11F, 11F), 0.0F);
        mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, fm.Or.getKren());
        mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(fm.Or.getTangage(), -45F, 45F, 12F, -12F));
        mesh.chunkSetAngles("NeedleCD", setNew.vspeed < 0.0F ? floatindex(cvt(-setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale) : -floatindex(cvt(setNew.vspeed, 0.0F, 30F, 0.0F, 6F), vsiNeedleScale), 0.0F, 0.0F);
        if(useRealisticNavigationInstruments())
        {
            mesh.chunkSetAngles("RepeaterPlane", -setNew.azimuth.getDeg(f) + setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
            mesh.chunkSetAngles("RepeaterOuter", setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("RepeaterOuter", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
            mesh.chunkSetAngles("RepeaterPlane", -setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("NeedleHBSmall", -105F + (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 5F, 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleHBLarge", -270F + (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        if(fm.EI.getNum() > 1)
        {
            mesh.chunkSetAngles("NeedleHBS88_L", -105F + (float)java.lang.Math.toDegrees(fm.EI.engines[1].getPropPhi() - fm.EI.engines[1].getPropPhiMin()) * 5F, 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleHBL88_L", -270F + (float)java.lang.Math.toDegrees(fm.EI.engines[1].getPropPhi() - fm.EI.engines[1].getPropPhiMin()) * 60F, 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleHBS88_R", -105F + (float)java.lang.Math.toDegrees(fm.EI.engines[2].getPropPhi() - fm.EI.engines[2].getPropPhiMin()) * 5F, 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleHBL88_R", -270F + (float)java.lang.Math.toDegrees(fm.EI.engines[2].getPropPhi() - fm.EI.engines[2].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        }
        if((fm.AS.astateCockpitState & 2) == 0 && (fm.AS.astateCockpitState & 1) == 0 && (fm.AS.astateCockpitState & 4) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
            mesh.chunkSetAngles("NeedleTrimmung", fm.CT.getTrimElevatorControl() * 25F, 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x20) == 0)
        {
            mesh.chunkSetAngles("NeedleHClock", -cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleMClock", -cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedleSClock", -cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        }
        resetYPRmodifier();
        if(gun[0] != null)
        {
            xyz[0] = cvt(gun[0].countBullets(), 0.0F, 500F, -0.044F, 0.0F);
            mesh.chunkSetLocate("RC_MG17_L", xyz, ypr);
        }
        if(gun[1] != null)
        {
            xyz[0] = cvt(gun[1].countBullets(), 0.0F, 500F, -0.044F, 0.0F);
            mesh.chunkSetLocate("RC_MG17_R", xyz, ypr);
        }
        if(gun[4] != null)
        {
            xyz[0] = cvt(gun[4].countBullets(), 0.0F, 200F, -0.017F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_L", xyz, ypr);
        }
        if(gun[5] != null)
        {
            xyz[0] = cvt(gun[5].countBullets(), 0.0F, 200F, -0.017F, 0.0F);
            mesh.chunkSetLocate("RC_MG151_R", xyz, ypr);
        }
        mesh.chunkVisible("XLampDrop", ((com.maddox.il2.objects.air.TypeDockable)aircraft()).typeDockableIsDocked());
        mesh.chunkSetAngles("IgnitionSwitch", 24F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Revi16Tinter", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -45F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 20F);
        mesh.chunkSetAngles("SteeringHatUD", 30F * pictElev, 0.0F, 0.0F);
        mesh.chunkSetAngles("SteeringHatLR", 30F * pictAiler, 0.0F, 0.0F);
        resetYPRmodifier();
        xyz[2] = fm.CT.WeaponControl[1] ? -0.004F : 0.0F;
        mesh.chunkSetLocate("SecTrigger", xyz, ypr);
        resetYPRmodifier();
        ypr[0] = interp(setNew.throttle0, setOld.throttle0, f) * 34F * 0.91F;
        mesh.chunkSetLocate("ThrottleQuad", xyz, ypr);
        if(fm.EI.getNum() > 1)
        {
            mesh.chunkSetAngles("Ju88TQ_L", 0.0F, -30F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F);
            mesh.chunkSetAngles("Ju88TQ_R", 0.0F, -30F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F);
        }
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
        mesh.chunkSetAngles("NeedleNahe1", cvt(setNew.beaconDirection, -45F, 45F, 20F, -20F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleNahe2", cvt(setNew.beaconRange, 0.0F, 1.0F, -20F, 20F), 0.0F, 0.0F);
        mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
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
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
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
            resetYPRmodifier();
            xyz[0] = 0.0F;
            xyz[1] = 0.003F;
            xyz[2] = 0.012F;
            ypr[0] = -3F;
            ypr[1] = -3F;
            ypr[2] = 9F;
            mesh.chunkSetLocate("IPCentral", xyz, ypr);
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float tmp;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null, null, null, null, null
    };
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private com.maddox.il2.ai.BulletEmitter bomb[] = {
        null, null, null, null, null
    };
    private long t1;
    private float pictAiler;
    private float pictElev;
    public com.maddox.JGP.Vector3f w;
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

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitFW_190F8MSTL.class, "normZN", 0.72F);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitFW_190F8MSTL.class, "gsZN", 0.66F);
    }






}
