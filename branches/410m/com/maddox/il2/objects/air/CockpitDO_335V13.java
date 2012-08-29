// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitDO_335V13.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
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
//            CockpitPilot, Aircraft, AircraftLH

public class CockpitDO_335V13 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle1 = 0.85F * setOld.throttle1 + fm.EI.engines[0].getControlThrottle() * 0.15F;
                setNew.prop1 = 0.85F * setOld.prop1 + fm.EI.engines[0].getControlProp() * 0.15F;
                setNew.mix1 = 0.85F * setOld.mix1 + fm.EI.engines[0].getControlMix() * 0.15F;
                setNew.throttle2 = 0.85F * setOld.throttle2 + fm.EI.engines[1].getControlThrottle() * 0.15F;
                setNew.prop2 = 0.85F * setOld.prop2 + fm.EI.engines[1].getControlProp() * 0.15F;
                setNew.mix2 = 0.85F * setOld.mix2 + fm.EI.engines[1].getControlMix() * 0.15F;
                setNew.altimeter = fm.getAltitude();
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
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                setNew.radioalt = 0.9F * setOld.radioalt + 0.1F * (fm.getAltitude() - com.maddox.il2.ai.World.cur().HQ_Air(com.maddox.il2.ai.World.land(), (float)fm.Loc.x, (float)fm.Loc.y));
            }
            setNew.beaconDirection = (10F * setOld.beaconDirection + getBeaconDirection()) / 11F;
            setNew.beaconRange = (10F * setOld.beaconRange + getBeaconRange()) / 11F;
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float throttle1;
        float prop1;
        float mix1;
        float throttle2;
        float prop2;
        float mix2;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        float dimPosition;
        float radioalt;
        float beaconDirection;
        float beaconRange;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    public CockpitDO_335V13()
    {
        super("3DO/Cockpit/Do-335V-13/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictTriA = 0.0F;
        pictTriE = 0.0F;
        pictTriR = 0.0F;
        pictManf1 = 1.0F;
        pictManf2 = 1.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "1", "2", "3", "4", "5", "8", "9", "11", "alt_km", "kompass", 
            "ok42", "D1", "D2", "D3", "D4", "D5", "D8", "D9"
        });
        setNightMats(false);
        setNew.dimPosition = 1.0F;
        cockpitDimControl = !cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN02");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN03");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN01");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN04");
            gun[4] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN05");
        }
        mesh.chunkSetAngles("Revisun", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -45F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Columnbase", 12F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", -45F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Columnbutton1", fm.CT.saveWeaponControl[0] ? -14.5F : 0.0F, 0.0F, 0.0F);
        resetYPRmodifier();
        if(fm.CT.saveWeaponControl[1])
            xyz[2] = -0.005F;
        mesh.chunkSetLocate("Z_Columnbutton2", xyz, ypr);
        resetYPRmodifier();
        if(fm.CT.saveWeaponControl[3])
            xyz[2] = -0.0035F;
        mesh.chunkSetLocate("Z_Columnbutton3", xyz, ypr);
        resetYPRmodifier();
        mesh.chunkSetLocate("Z_Columnbutton4", xyz, ypr);
        resetYPRmodifier();
        mesh.chunkSetLocate("Z_Columnbutton5", xyz, ypr);
        resetYPRmodifier();
        xyz[2] = 0.12F * fm.CT.getRudder();
        mesh.chunkSetLocate("Z_LeftPedal", xyz, ypr);
        mesh.chunkSetAngles("Z_LeftPedal1", -32.7F * fm.CT.getRudder(), 0.0F, 0.0F);
        xyz[2] = -0.12F * fm.CT.getRudder();
        mesh.chunkSetLocate("Z_RightPedal", xyz, ypr);
        mesh.chunkSetAngles("Z_RightPedal1", -32.7F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle1", 37.28F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle2", 37.28F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ThrottleLock", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixture1", 34.37F * interp(setNew.mix1, setOld.mix1, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixture2", 34.37F * interp(setNew.mix2, setOld.mix2, f), 0.0F, 0.0F);
        if(fm.EI.engines[0].getControlFeather() == 0)
            mesh.chunkSetAngles("Z_Pitch1", 23F + 46.5F * interp(setNew.prop1, setOld.prop1, f), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Pitch1", 0.0F, 0.0F, 0.0F);
        if(fm.EI.engines[1].getControlFeather() == 0)
            mesh.chunkSetAngles("Z_Pitch2", 23F + 46.5F * interp(setNew.prop2, setOld.prop2, f), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Pitch2", 0.0F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Radiat1", 30F * fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Radiat2", 30F * fm.EI.engines[1].getControlRadiator(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gear", 50F * fm.CT.GearControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flap", 50F * fm.CT.FlapsControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Magneto1", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Magneto2", cvt(fm.EI.engines[1].getControlMagnetos(), 0.0F, 3F, 0.0F, 75F), 0.0F, 0.0F);
        resetYPRmodifier();
        xyz[1] = 0.065F * fm.CT.BayDoorControl;
        mesh.chunkSetLocate("Z_BombBay", xyz, ypr);
        resetYPRmodifier();
        xyz[2] = cvt(gun[0].countBullets(), 0.0F, 500F, -0.00465F, 0.04175F);
        mesh.chunkSetLocate("Z_AmmoCounter1", xyz, ypr);
        xyz[2] = cvt(gun[2].countBullets(), 0.0F, 100F, -0.00465F, 0.04175F);
        mesh.chunkSetLocate("Z_AmmoCounter2", xyz, ypr);
        xyz[2] = cvt(gun[1].countBullets(), 0.0F, 500F, -0.00465F, 0.04175F);
        mesh.chunkSetLocate("Z_AmmoCounter3", xyz, ypr);
        xyz[2] = cvt(gun[3].countBullets(), 0.0F, 100F, -0.00465F, 0.04175F);
        mesh.chunkSetLocate("Z_AmmoCounter4", xyz, ypr);
        xyz[2] = cvt(gun[4].countBullets(), 0.0F, 100F, -0.00465F, 0.04175F);
        mesh.chunkSetLocate("Z_AmmoCounter5", xyz, ypr);
        if(useRealisticNavigationInstruments())
        {
            mesh.chunkSetAngles("Z_Compass2", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass1", setNew.waypointAzimuth.getDeg(0.1F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass4", -setNew.waypointAzimuth.getDeg(0.1F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass3", 90F - setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("Z_Compass1", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass2", setNew.waypointAzimuth.getDeg(0.1F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass3", -setNew.waypointAzimuth.getDeg(0.1F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass4", 90F - setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Second1", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, 33F, -33F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.024F, -0.024F);
        mesh.chunkSetLocate("Z_TurnBank2a", xyz, ypr);
        mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(8D), -8F, 8F, -15F, 15F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speed1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        float f1 = setNew.vspeed <= 0.0F ? -1F : 1.0F;
        mesh.chunkSetAngles("Z_Climb1", f1 * floatindex(cvt(java.lang.Math.abs(setNew.vspeed), 0.0F, 30F, 0.0F, 6F), variometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("RPM1", floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("RPM2", floatindex(cvt(fm.EI.engines[1].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("ATA1", pictManf1 = 0.9F * pictManf1 + 0.1F * cvt(fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329F), 0.0F, 0.0F);
        mesh.chunkSetAngles("ATA2", pictManf2 = 0.9F * pictManf2 + 0.1F * cvt(fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 57F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp2", cvt(fm.EI.engines[1].tWaterOut, 0.0F, 120F, 0.0F, 57F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oiltemp1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 57F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oiltemp2", cvt(fm.EI.engines[1].tOilOut, 0.0F, 120F, 0.0F, 57F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel, 0.0F, 864F, 0.0F, 80F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuelpress1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.32F, 0.0F, 1.0F, 0.0F, 58F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuelpress2", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.32F, 0.0F, 1.0F, 0.0F, 58F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpress1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 58F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpress2", cvt(1.0F + 0.05F * fm.EI.engines[1].tOilOut * fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, 58F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedPitch1", 270F - (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedPitch2", 105F - (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 5F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedPitch3", 270F - (float)java.lang.Math.toDegrees(fm.EI.engines[1].getPropPhi() - fm.EI.engines[1].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_NeedPitch4", 105F - (float)java.lang.Math.toDegrees(fm.EI.engines[1].getPropPhi() - fm.EI.engines[1].getPropPhiMin()) * 5F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp3", cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z), 213.09F, 313.09F, -45F, 30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("z_Alt1", cvt(interp(setNew.radioalt, setOld.radioalt, f), 0.0F, 750F, 0.0F, 232F), 0.0F, 0.0F);
        mesh.chunkSetAngles("z_Alt2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("z_Alt3", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 14000F, 0.0F, 315F), 0.0F, 0.0F);
        mesh.chunkSetAngles("z_HydroPress1", fm.Gears.isHydroOperable() ? 130F : 0.0F, 0.0F, 0.0F);
        mesh.chunkVisible("Z_GearLGreen", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
        mesh.chunkVisible("Z_GearRGreen", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        mesh.chunkVisible("Z_GearCGreen", fm.CT.getGear() > 0.99F);
        mesh.chunkVisible("Z_GearLRed", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
        mesh.chunkVisible("Z_GearRRed", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
        mesh.chunkVisible("Z_GearCRed", fm.CT.getGear() < 0.01F);
        mesh.chunkVisible("Z_FlapCombat", fm.CT.getFlap() > 0.1F);
        mesh.chunkVisible("Z_FlapTakeOff", fm.CT.getFlap() > 0.265F);
        mesh.chunkVisible("Z_FlapLanding", fm.CT.getFlap() > 0.665F);
        mesh.chunkVisible("Z_Ammo1", gun[0].countBullets() == 0);
        mesh.chunkVisible("Z_Ammo2", gun[2].countBullets() == 0);
        mesh.chunkVisible("Z_Ammo3", gun[1].countBullets() == 0);
        mesh.chunkVisible("Z_Ammo4", gun[3].countBullets() == 0);
        mesh.chunkVisible("Z_Ammo5", gun[4].countBullets() == 0);
        mesh.chunkVisible("Z_Stall", fm.getSpeedKMH() < 145F);
        mesh.chunkVisible("Z_FuelWhite", true);
        mesh.chunkVisible("Z_FuelRed", fm.M.fuel < 256.08F);
        mesh.chunkVisible("Z_Warning1", false);
        mesh.chunkVisible("Z_Warning2", false);
        mesh.chunkVisible("Z_Warning3", false);
        mesh.chunkVisible("Z_Warning4", false);
        mesh.chunkVisible("Z_Warning5", false);
        mesh.chunkVisible("Z_Warning6", false);
        mesh.chunkVisible("Z_Warning7", false);
        mesh.chunkVisible("Z_Warning8", false);
        mesh.chunkVisible("Z_Warning9", false);
        mesh.chunkSetAngles("Z_AFN22", cvt(setNew.beaconDirection, -45F, 45F, -20F, 20F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_AFN21", cvt(setNew.beaconRange, 0.0F, 1.0F, 20F, -20F), 0.0F, 0.0F);
        mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
    }

    protected float waypointAzimuth()
    {
        return super.waypointAzimuthInvertMinus(15F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        retoggleLight();
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
    private float pictTriA;
    private float pictTriE;
    private float pictTriR;
    private float pictManf1;
    private float pictManf2;
    private com.maddox.il2.ai.BulletEmitter gun[] = {
        null, null, null, null, null
    };
    private static final float speedometerScale[] = {
        0.0F, 21F, 69.5F, 116F, 163F, 215.5F, 266.5F, 318.5F, 378F, 430.5F
    };
    private static final float variometerScale[] = {
        0.0F, 47F, 82F, 97F, 112F, 111.7F, 132F
    };
    private static final float rpmScale[] = {
        0.0F, 2.5F, 19F, 50.5F, 102.5F, 173F, 227F, 266.5F, 297F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
