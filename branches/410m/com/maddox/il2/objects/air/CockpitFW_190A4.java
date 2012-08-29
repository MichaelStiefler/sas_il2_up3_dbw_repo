// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitFW_190A4.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
//            CockpitPilot, Aircraft, AircraftLH

public class CockpitFW_190A4 extends com.maddox.il2.objects.air.CockpitPilot
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
        float throttle;
        float dimPosition;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float beaconDirection;
        float beaconRange;
        float turn;
        float vspeed;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    protected float waypointAzimuth()
    {
        return super.waypointAzimuthInvertMinus(30F);
    }

    public CockpitFW_190A4()
    {
        super("3DO/Cockpit/FW-190A-4/hier.him", "bf109");
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
            "A4GP1", "A4GP2", "A4GP3", "A4GP4", "A4GP5", "A4GP6", "A5GP3Km"
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
        mesh.chunkSetAngles("NeedleALT", -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(setNew.altimeter, 0.0F, 10000F, 0.0F, -180F));
        mesh.chunkSetAngles("NeedleManPress", -cvt(fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleFuel", floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 400F, 0.0F, 4F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleOilTemp", floatindex(cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 3F), oilTempScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleFuelPress", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleAHTurn", cvt(setNew.turn, -0.23562F, 0.23562F, -50F, 50F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleAHBank", cvt(getBall(7D), -7F, 7F, 11F, -11F), 0.0F, 0.0F);
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
        mesh.chunkSetAngles("NeedleTrimmung", fm.CT.getTrimElevatorControl() * 25F, 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleHClock", -cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleMClock", -cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("NeedleSClock", -cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        resetYPRmodifier();
        if(gun[0] != null)
        {
            xyz[0] = cvt(gun[0].countBullets(), 0.0F, 500F, -0.044F, 0.0F);
            mesh.chunkSetLocate("RC_MG17_L", xyz, ypr);
            mesh.chunkVisible("XLampMG17_1", !gun[0].haveBullets());
        }
        if(gun[1] != null)
        {
            xyz[0] = cvt(gun[1].countBullets(), 0.0F, 500F, -0.044F, 0.0F);
            mesh.chunkSetLocate("RC_MG17_R", xyz, ypr);
            mesh.chunkVisible("XLampMG17_2", !gun[1].haveBullets());
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
        if(gun[2] != null)
        {
            xyz[0] = cvt(gun[2].countBullets(), 0.0F, 55F, -0.018F, 0.0F);
            mesh.chunkSetLocate("RC_MGFF_WingL", xyz, ypr);
        }
        if(gun[3] != null)
        {
            xyz[0] = cvt(gun[3].countBullets(), 0.0F, 55F, -0.018F, 0.0F);
            mesh.chunkSetLocate("RC_MGFF_WingR", xyz, ypr);
        }
        mesh.chunkSetAngles("IgnitionSwitch", 24F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Revi16Tinter", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -45F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 20F);
        resetYPRmodifier();
        xyz[2] = fm.CT.WeaponControl[1] ? -0.004F : 0.0F;
        mesh.chunkSetLocate("SecTrigger", xyz, ypr);
        ypr[0] = interp(setNew.throttle, setOld.throttle, f) * 34F * 0.91F;
        xyz[2] = ypr[0] <= 7F ? 0.0F : -0.006F;
        mesh.chunkSetLocate("ThrottleQuad", xyz, ypr);
        mesh.chunkSetAngles("RPedalBase", 0.0F, 0.0F, fm.CT.getRudder() * 15F);
        mesh.chunkSetAngles("RPedalStrut", 0.0F, 0.0F, -fm.CT.getRudder() * 15F);
        mesh.chunkSetAngles("RPedal", 0.0F, 0.0F, -fm.CT.getRudder() * 15F - fm.CT.getBrake() * 15F);
        mesh.chunkSetAngles("LPedalBase", 0.0F, 0.0F, -fm.CT.getRudder() * 15F);
        mesh.chunkSetAngles("LPedalStrut", 0.0F, 0.0F, fm.CT.getRudder() * 15F);
        mesh.chunkSetAngles("LPedal", 0.0F, 0.0F, fm.CT.getRudder() * 15F - fm.CT.getBrake() * 15F);
        mesh.chunkVisible("XLampTankSwitch", fm.M.fuel > 144F);
        mesh.chunkVisible("XLampFuelLow", fm.M.fuel < 43.2F);
        mesh.chunkVisible("XLampFlapLPos_3", fm.CT.getFlap() > 0.9F);
        mesh.chunkVisible("XLampFlapLPos_2", fm.CT.getFlap() > 0.1F && fm.CT.getFlap() < 0.5F);
        mesh.chunkVisible("XLampFlapLPos_1", fm.CT.getFlap() < 0.1F);
        mesh.chunkVisible("XLampFlapRPos_3", fm.CT.getFlap() > 0.9F);
        mesh.chunkVisible("XLampFlapRPos_2", fm.CT.getFlap() > 0.1F && fm.CT.getFlap() < 0.5F);
        mesh.chunkVisible("XLampFlapRPos_1", fm.CT.getFlap() < 0.1F);
        mesh.chunkVisible("XLampGearL_1", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("XLampGearL_2", fm.CT.getGear() > 0.95F && fm.Gears.lgear);
        mesh.chunkVisible("XLampGearR_1", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("XLampGearR_2", fm.CT.getGear() > 0.95F && fm.Gears.rgear);
        mesh.chunkVisible("XLampGearC_1", fm.CT.getGear() < 0.05F);
        mesh.chunkVisible("XLampGearC_2", fm.CT.getGear() > 0.95F);
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
            light1.light.setEmit(0.005F, 0.75F);
            light2.light.setEmit(0.005F, 0.75F);
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
        if((fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 0x10) != 0 || (fm.AS.astateCockpitState & 2) != 0 || (fm.AS.astateCockpitState & 1) != 0)
        {
            if((fm.AS.astateCockpitState & 2) != 0)
            {
                mesh.chunkVisible("Revi16", false);
                mesh.chunkVisible("Revi16Tinter", false);
                mesh.chunkVisible("Z_Z_MASK", false);
                mesh.chunkVisible("Z_Z_RETICLE", false);
                mesh.chunkVisible("DRevi16", true);
            }
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("HullDamage1", true);
            mesh.materialReplace("A4GP1", "DA4GP1");
            mesh.materialReplace("A4GP1_night", "DA4GP1_night");
            mesh.chunkVisible("NeedleRPM", false);
            mesh.chunkVisible("NeedleManPress", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x20) != 0 || (fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("HullDamage3", true);
            mesh.materialReplace("A4GP2", "DA4GP2");
            mesh.materialReplace("A4GP2_night", "DA4GP2_night");
            mesh.chunkVisible("NeedleAHBank", false);
            resetYPRmodifier();
            xyz[0] = -0.01F;
            xyz[1] = -0.01F;
            ypr[0] = -5F;
            ypr[2] = -5F;
            mesh.chunkSetLocate("IPCentral", xyz, ypr);
            mesh.chunkVisible("NeedleOilPress", false);
            mesh.chunkVisible("NeedleFuelPress", false);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
        {
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("HullDamage2", true);
            mesh.materialReplace("A4GP3", "DA4GP3");
            mesh.materialReplace("A4GP3_night", "DA4GP3_night");
            mesh.chunkVisible("NeedleALT", false);
            mesh.chunkVisible("NeedleKMH", false);
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
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitFW_190A4.class, "normZN", 0.72F);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitFW_190A4.class, "gsZN", 0.66F);
    }






}
