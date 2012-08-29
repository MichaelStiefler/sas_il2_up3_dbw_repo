// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitBF_109G5AS.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, AircraftLH, Aircraft

public class CockpitBF_109G5AS extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
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
            setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
            setNew.mix = (8F * setOld.mix + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix()) / 9F;
            float f = waypointAzimuth();
            if(useRealisticNavigationInstruments())
            {
                setNew.waypointAzimuth.setDeg(f - 90F);
                setOld.waypointAzimuth.setDeg(f - 90F);
            } else
            {
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f - setOld.azimuth.getDeg(1.0F));
            }
            setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.azimut());
            w.set(fm.getW());
            ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.transform(w);
            setNew.turn = (12F * setOld.turn + ((com.maddox.JGP.Tuple3f) (w)).z) / 13F;
            buzzerFX(((com.maddox.il2.fm.FlightModelMain) (fm)).CT.getGear() < 0.999999F && ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.getFlap() > 0.0F);
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

        float altimeter;
        float throttle;
        float dimPosition;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float turn;
        float beaconDirection;
        float beaconRange;
        float mix;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

        Variables(com.maddox.il2.objects.air.Variables variables)
        {
            this();
        }
    }


    protected float waypointAzimuth()
    {
        return waypointAzimuthInvertMinus(30F);
    }

    public CockpitBF_109G5AS()
    {
        super("3do/cockpit/Bf-109G-6Late/G5AS.him", "bf109");
        gun = new com.maddox.il2.objects.weapons.Gun[3];
        hasCanopy = true;
        setOld = new Variables(null);
        setNew = new Variables(null);
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictManifold = 0.0F;
        w = new Vector3f();
        setNew.dimPosition = 1.0F;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(super.mesh, "LAMPHOOK1");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        hooknamed = new HookNamed(super.mesh, "LAMPHOOK2");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(126F, 232F, 245F);
        light2.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK2", light2);
        super.cockpitNightMats = (new java.lang.String[] {
            "ZClocks1", "ZClocks1DMG", "ZClocks2", "ZClocks3", "FW190A4Compass"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        loadBuzzerFX();
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    public void removeCanopy()
    {
        hasCanopy = false;
        if(super.mesh.chunkFindCheck("TopOpen") >= 0)
            super.mesh.chunkVisible("TopOpen", false);
        super.mesh.chunkVisible("Z_Holes2_D1", false);
        super.mesh.chunkVisible("Z_Holes1_D1", false);
        if(super.mesh.chunkFindCheck("Top2") >= 0)
            super.mesh.chunkVisible("Top2", false);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_CANNON01");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN02");
        }
        resetYPRmodifier();
        super.mesh.chunkSetAngles("Top", 0.0F, 100F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 180F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_ReviTint", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 45F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_ATA1", cvt(pictManifold = 0.75F * pictManifold + 0.25F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM1", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelQuantity1", -44.5F + floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel / 0.72F, 0.0F, 400F, 0.0F, 8F), fuelScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_EngTemp1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 160F, 0.0F, 58.5F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelPress1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        if(useRealisticNavigationInstruments())
        {
            super.mesh.chunkSetAngles("Z_Azimuth1", setNew.azimuth.getDeg(f) - setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("Z_Compass1", 0.0F, -setNew.waypointAzimuth.getDeg(f), 0.0F);
        } else
        {
            super.mesh.chunkSetAngles("Z_Compass1", 0.0F, -setNew.azimuth.getDeg(f), 0.0F);
            super.mesh.chunkSetAngles("Z_Azimuth1", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        }
        super.mesh.chunkSetAngles("Z_PropPitch1", 270F - (float)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhi() - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PropPitch2", 105F - (float)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhi() - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhiMin()) * 5F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TurnBank1", cvt(setNew.turn, -0.23562F, 0.23562F, 30F, -30F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(6D), -6F, 6F, -4.5F, 4.5F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Horizon1", 0.0F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren());
        super.mesh.chunkSetAngles("Z_Horizon2", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, -13F, 13F), 0.0F, 0.0F);
        super.mesh.chunkVisible("Z_FuelWarning1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 36F);
        super.mesh.chunkVisible("Z_GearLRed1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F);
        super.mesh.chunkVisible("Z_GearRRed1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F);
        super.mesh.chunkVisible("Z_GearLGreen1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F);
        super.mesh.chunkVisible("Z_GearRGreen1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F);
        if(gun[0] != null)
            super.mesh.chunkSetAngles("Z_AmmoCounter1", cvt(gun[0].countBullets(), 0.0F, 500F, 15F, 0.0F), 0.0F, 0.0F);
        if(gun[1] != null)
            super.mesh.chunkSetAngles("Z_AmmoCounter2", cvt(gun[1].countBullets(), 0.0F, 500F, 15F, 0.0F), 0.0F, 0.0F);
        if(gun[2] != null)
            super.mesh.chunkSetAngles("Z_AmmoCounter3", cvt(gun[2].countBullets(), 0.0F, 500F, 15F, 0.0F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Second1", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Column", (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl) * 15F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl) * 10F);
        super.mesh.chunkSetAngles("Z_PedalStrut", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_LeftPedal", -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RightPedal", -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Column", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getAileron() * 15F, 0.0F, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getElevator() * 10F);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) == 0)
            super.mesh.chunkSetAngles("Z_Throttle", interp(setNew.throttle, setOld.throttle, f) * 68.18182F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Mix", interp(setNew.mix, setOld.mix, f) * 62.5F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_MagnetoSwitch", -45F + 28.333F * (float)((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_AFN22", cvt(setNew.beaconDirection, -45F, 45F, -20F, 20F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_AFN21", cvt(setNew.beaconRange, 0.0F, 1.0F, 20F, -20F), 0.0F, 0.0F);
        super.mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.bIsAboutToBailout)
        {
            super.mesh.chunkVisible("TopOpen", false);
            super.mesh.chunkVisible("Z_Holes2_D1", false);
            super.mesh.chunkVisible("Z_Holes1_D1", false);
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
        {
            light1.light.setEmit(0.005F, 0.5F);
            light2.light.setEmit(0.005F, 0.5F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
        {
            super.mesh.chunkVisible("Z_Holes3_D1", true);
            super.mesh.chunkVisible("Revi_D0", false);
            super.mesh.chunkVisible("Z_ReviTint", false);
            super.mesh.chunkVisible("Z_Z_RETICLE", false);
            super.mesh.chunkVisible("Z_Z_MASK", false);
            super.mesh.chunkVisible("Revi_D1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
            super.mesh.chunkVisible("Z_Holes2_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0)
        {
            super.mesh.chunkVisible("PoppedPanel_D0", false);
            super.mesh.chunkVisible("Z_Repeater1", false);
            super.mesh.chunkVisible("Z_Azimuth1", false);
            super.mesh.chunkVisible("Z_Compass1", false);
            super.mesh.chunkVisible("Z_Speedometer1", false);
            super.mesh.chunkVisible("PoppedPanel_D1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
            super.mesh.chunkVisible("Z_Holes1_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
            super.mesh.chunkVisible("Z_OilSplats_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
        {
            super.mesh.chunkVisible("Z_Holes1_D1", true);
            super.mesh.chunkVisible("Radio_D0", false);
            super.mesh.chunkVisible("Radio_D1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0);
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("Blister1_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!isFocused())
            return;
        if(hasCanopy)
        {
            aircraft().hierMesh().chunkVisible("Blister1_D0", true);
            super.doFocusLeave();
            return;
        } else
        {
            return;
        }
    }

    private com.maddox.il2.objects.weapons.Gun gun[];
    boolean hasCanopy;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private float pictAiler;
    private float pictElev;
    private float pictManifold;
    public com.maddox.JGP.Vector3f w;
    private static final float speedometerScale[] = {
        0.0F, -12.33333F, 18.5F, 37F, 62.5F, 90F, 110.5F, 134F, 158.5F, 186F, 
        212.5F, 238.5F, 265F, 289.5F, 315F, 339.5F, 346F, 346F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 54F, 111F, 171.5F, 229.5F, 282.5F, 334F, 342.5F, 342.5F
    };
    private static final float fuelScale[] = {
        0.0F, 9F, 21F, 29.5F, 37F, 48F, 61.5F, 75.5F, 92F, 92F
    };

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitBF_109G5AS.class, "normZN", 0.5F);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitBF_109G5AS.class, "gsZN", 0.5F);
    }






}
