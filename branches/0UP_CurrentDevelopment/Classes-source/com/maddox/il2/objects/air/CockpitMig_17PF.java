// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitMig_17PF.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Mig_17, Cockpit

public class CockpitMig_17PF extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle = 0.9F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl * 0.1F;
                setNew.altimeter = fm.getAltitude();
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                float a = waypointAzimuth();
                if(useRealisticNavigationInstruments())
                {
                    setNew.waypointAzimuth.setDeg(a - 90F);
                    setOld.waypointAzimuth.setDeg(a - 90F);
                } else
                {
                    setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), a - setOld.azimuth.getDeg(1.0F));
                }
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.azimut());
                setNew.beaconDirection = (10F * setOld.beaconDirection + getBeaconDirection()) / 11F;
                setNew.beaconRange = (10F * setOld.beaconRange + getBeaconRange()) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                if(((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getStage() < 6 && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasAileronControl && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasElevatorControl && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasGearControl && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasAirBrakeControl && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasFlapsControl)
                {
                    if(setNew.stbyPosition3 > 0.0F)
                        setNew.stbyPosition3 = setOld.stbyPosition3 - 0.005F;
                } else
                if(setNew.stbyPosition3 < 1.0F)
                    setNew.stbyPosition3 = setOld.stbyPosition3 + 0.005F;
                float f = ((com.maddox.il2.objects.air.Mig_17)aircraft()).k14Distance;
                setNew.k14w = (5F * com.maddox.il2.objects.air.CockpitMig_17PF.k14TargetWingspanScale[((com.maddox.il2.objects.air.Mig_17)aircraft()).k14WingspanType]) / f;
                setNew.k14w = 0.9F * setOld.k14w + 0.1F * setNew.k14w;
                setNew.k14wingspan = 0.9F * setOld.k14wingspan + 0.1F * com.maddox.il2.objects.air.CockpitMig_17PF.k14TargetMarkScale[((com.maddox.il2.objects.air.Mig_17)aircraft()).k14WingspanType];
                setNew.k14mode = 0.8F * setOld.k14mode + 0.2F * (float)((com.maddox.il2.objects.air.Mig_17)aircraft()).k14Mode;
                com.maddox.JGP.Vector3d vector3d = ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM.getW();
                double d = 0.00125D * (double)f;
                float f_0_ = (float)java.lang.Math.toDegrees(d * ((com.maddox.JGP.Tuple3d) (vector3d)).z);
                float f_1_ = -(float)java.lang.Math.toDegrees(d * ((com.maddox.JGP.Tuple3d) (vector3d)).y);
                float f_2_ = floatindex((f - 200F) * 0.04F, com.maddox.il2.objects.air.CockpitMig_17PF.k14BulletDrop) - com.maddox.il2.objects.air.CockpitMig_17PF.k14BulletDrop[0];
                f_1_ += (float)java.lang.Math.toDegrees(java.lang.Math.atan(f_2_ / f));
                setNew.k14x = 0.92F * setOld.k14x + 0.08F * f_0_;
                setNew.k14y = 0.92F * setOld.k14y + 0.08F * f_1_;
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

        float throttle;
        float dimPosition;
        float stage;
        float vspeed;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float beaconDirection;
        float beaconRange;
        float k14wingspan;
        float k14mode;
        float k14x;
        float k14y;
        float k14w;
        float stbyPosition;
        float stbyPosition2;
        float stbyPosition3;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

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
        {
            return;
        } else
        {
            aircraft().hierMesh().chunkVisible("Blister1_D0", true);
            super.doFocusLeave();
            return;
        }
    }

    public CockpitMig_17PF()
    {
        super("3DO/Cockpit/MiG-17PF/MiG_17PF.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictETP = 0.0F;
        pictFlap = 0.0F;
        pictGear = 0.0F;
        pictTLck = 0.0F;
        pictMet1 = 0.0F;
        pictMet2 = 0.0F;
        pictETrm = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        super.cockpitNightMats = (new java.lang.String[] {
            "Gauges_01", "Gauges_02", "Gauges_03", "Gauges_04", "Gauges_05", "Gauges_06", "Gauges_08", "MiG-15_Compass"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(super.acoustics != null)
            super.acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    protected float waypointAzimuth()
    {
        return waypointAzimuthInvertMinus(30F);
    }

    private float machNumber()
    {
        return ((com.maddox.il2.objects.air.Mig_17)super.aircraft()).calculateMach();
    }

    public void reflectWorldToInstruments(float f)
    {
        com.maddox.il2.objects.air.Mig_17 Mig_17 = (com.maddox.il2.objects.air.Mig_17)aircraft();
        com.maddox.il2.objects.air.Mig_17 _tmp = Mig_17;
        if(com.maddox.il2.objects.air.Mig_17.bChangedPit)
        {
            com.maddox.il2.objects.air.Mig_17 Mig_17_3_ = (com.maddox.il2.objects.air.Mig_17)aircraft();
            com.maddox.il2.objects.air.Mig_17 _tmp1 = Mig_17;
            com.maddox.il2.objects.air.Mig_17.bChangedPit = false;
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) == 0)
        {
            int i = ((com.maddox.il2.objects.air.Mig_17)aircraft()).k14Mode;
            boolean bool = i < 2;
            super.mesh.chunkVisible("Z_Z_RETICLE", bool);
            bool = i > 0;
            super.mesh.chunkVisible("Z_Z_RETICLE1", bool);
            super.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, setNew.k14x, setNew.k14y);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = setNew.k14w;
            for(int i_4_ = 1; i_4_ < 7; i_4_++)
            {
                super.mesh.chunkVisible("Z_Z_AIMMARK" + i_4_, bool);
                super.mesh.chunkSetLocate("Z_Z_AIMMARK" + i_4_, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            }

        }
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F, 0.99F, -0.49F, 0.0F);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F, 0.99F, -0.065F, 0.0F);
        com.maddox.il2.objects.air.Cockpit.ypr[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.99F, 0.01F, -3F, 0.0F);
        super.mesh.chunkSetLocate("CanopyOpen01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen03", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen04", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen05", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen06", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen07", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen08", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen09", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetLocate("XGlassDamage4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 50F * (pictGear = 0.82F * pictGear + 0.18F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl));
        super.mesh.chunkSetAngles("Z_FlapsLever", -35F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Gas1a", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel / 2.0F, 0.0F, 700F, 0.0F, 180F), 0.0F);
        super.mesh.chunkSetAngles("Z_Target1", 1.2F * setNew.k14wingspan, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Gunsight_Button", -10F * setNew.k14wingspan, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Gunsight_Mire", 0.0F, cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 47F), 0.0F);
        super.mesh.chunkSetAngles("Z_Amp", 0.0F, cvt(interp(setNew.stbyPosition2, setOld.stbyPosition2, f), 0.0F, 1.0F, 0.0F, 40F), 0.0F);
        super.mesh.chunkSetAngles("Z_HydroPressure", 0.0F, cvt(interp(setNew.stbyPosition3, setOld.stbyPosition3, f), 0.0F, 1.0F, 0.0F, 190F), 0.0F);
        w.set(super.fm.getW());
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.transform(w);
        super.mesh.chunkSetAngles("Z_Turn1a", 0.0F, cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
        super.mesh.chunkSetAngles("Z_Slide1a", 0.0F, cvt(getBall(8D), -8F, 8F, -24F, 24F), 0.0F);
        super.mesh.chunkSetAngles("Z_Slide1a2", 0.0F, cvt(getBall(8D), -8F, 8F, -20F, 20F), 0.0F);
        if(useRealisticNavigationInstruments())
            super.mesh.chunkSetAngles("Z_Compass2", 90F + setNew.azimuth.getDeg(f * 0.1F) + setNew.beaconDirection, 0.0F, 0.0F);
        else
            super.mesh.chunkSetAngles("Z_Compass2", 90F + setNew.azimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_horizont1a", 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren(), 0.0F);
        super.mesh.chunkSetAngles("Z_GasPrs1a", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 3050F, 0.0F, 4F), 0.0F, 5F, -45F, 225F), 0.0F);
        super.mesh.chunkSetAngles("Z_GasPrs2a", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 3050F, 0.0F, 4F), 0.0F, 5F, -180F, 0.0F), 0.0F);
        super.mesh.chunkSetAngles("Z_TOilOut1a", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 110F, 5F, 300F), 0.0F);
        super.mesh.chunkSetAngles("Z_OilPrs1a", 0.0F, cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 15F, -155F, -360F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), -1F, 1.0F, -0.035F, 0.035F);
        super.mesh.chunkSetLocate("Pedal_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -com.maddox.il2.objects.air.Cockpit.xyz[1];
        super.mesh.chunkSetLocate("Pedal_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 0.0328F, -0.0328F);
        super.mesh.chunkSetLocate("Z_horizont1b", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("Stick01", 0.0F, 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl));
        super.mesh.chunkSetAngles("Stick02", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F);
        super.mesh.chunkSetAngles("Stick03", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F);
        super.mesh.chunkSetAngles("Stick04", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F);
        super.mesh.chunkSetAngles("Stick05", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F);
        super.mesh.chunkSetAngles("Stick06", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F);
        super.mesh.chunkSetAngles("Stick07", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F);
        super.mesh.chunkSetAngles("Z_RPM1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 3480F, 90F, 625F), 0.0F, 0.0F);
        pictMet1 = 0.96F * pictMet1 + 0.04F * (0.6F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getThrustOutput() * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlThrottle() * (((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getStage() != 6 ? 0.02F : 1.0F));
        super.mesh.chunkSetAngles("Z_FuelPress1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : 0.55F, 0.0F, 1.0F, 0.0F, 284F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_ExstT1", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F, 1200F, 0.0F, 112F), 0.0F);
        super.mesh.chunkSetAngles("Z_Azimuth1", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Compass1", 0.0F, -setNew.azimuth.getDeg(f * 0.1F), 0.0F);
        super.mesh.chunkSetAngles("Z_Oxypres1", 142.5F, 0.0F, 0.0F);
        if(machNumber() < 0.95F || machNumber() > 1.0F)
        {
            super.mesh.chunkSetAngles("Z_Alt_Km", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 60000F, 0.0F, 2160F), 0.0F);
            super.mesh.chunkSetAngles("Z_Alt_M", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 60000F, 0.0F, 21600F), 0.0F);
            super.mesh.chunkSetAngles("Z_Alt2_Km", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 12000F, 225F, 495F), 0.0F);
            super.mesh.chunkSetAngles("Z_Alt3_Km", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 12000F, 295F, 395F), 0.0F);
            super.mesh.chunkSetAngles("Z_Speed", 0.0F, cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 1200F, 0.0F, 360F), 0.0F);
            super.mesh.chunkSetAngles("Z_AirFlow", 0.0F, cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 800F, -90F, 160F), 0.0F);
            super.mesh.chunkSetAngles("Z_Climb", 0.0F, cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        }
        super.mesh.chunkSetAngles("Z_Vibrations", 0.0F, floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 4500F, 5.5F, 14F), rpmScale), 0.0F);
        super.mesh.chunkSetAngles("Z_Throttle", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 3480F, -110F, 170F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Second1", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkVisible("FlareGearUp_R", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkVisible("FlareGearUp_L", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
        super.mesh.chunkVisible("FlareGearUp_C", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.01F);
        super.mesh.chunkVisible("FlareGearDn_R", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkVisible("FlareGearDn_L", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
        super.mesh.chunkVisible("FlareGearDn_C", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F);
        super.mesh.chunkVisible("FlareFuel", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 296.1F);
        super.mesh.chunkVisible("FlareTankFuel", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 1215F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel > 1207F);
        super.mesh.chunkVisible("FlareFire", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateEngineStates[0] > 2);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.ypr[0] = interp(setNew.throttle, setOld.throttle, f) * 65F;
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(com.maddox.il2.objects.air.Cockpit.ypr[0], 7.5F, 11.5F, 0.0F, 0.0F);
        super.mesh.chunkSetLocate("Throttle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        float f_5_ = com.maddox.il2.ai.World.Rnd().nextFloat(0.87F, 1.04F);
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor() == 1.0F)
        {
            super.mesh.chunkVisible("V_G", false);
            super.mesh.chunkVisible("V_D", false);
        } else
        {
            super.mesh.chunkVisible("V_G", true);
            super.mesh.chunkVisible("V_D", true);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor() == 1.0F && !((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.bIsAboutToBailout)
            super.mesh.chunkVisible("CanopyOpen07", true);
        else
            super.mesh.chunkVisible("CanopyOpen07", false);
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor() < 1.0F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.bHasCockpitDoorControl)
            super.mesh.chunkVisible("CanopyOpen06", true);
        else
            super.mesh.chunkVisible("CanopyOpen06", false);
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.bIsAboutToBailout)
        {
            super.mesh.chunkVisible("CanopyOpen01", false);
            super.mesh.chunkVisible("CanopyOpen02", false);
            super.mesh.chunkVisible("CanopyOpen03", false);
            super.mesh.chunkVisible("CanopyOpen04", false);
            super.mesh.chunkVisible("CanopyOpen05", false);
            super.mesh.chunkVisible("CanopyOpen08", false);
            super.mesh.chunkVisible("CanopyOpen09", false);
            super.mesh.chunkVisible("XGlassDamage4", false);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.BayDoorControl == 1.0F)
        {
            super.mesh.chunkVisible("Stick04", false);
            super.mesh.chunkVisible("Stick05", true);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.BayDoorControl == 0.0F)
        {
            super.mesh.chunkVisible("Stick04", true);
            super.mesh.chunkVisible("Stick05", false);
        }
        super.mesh.chunkVisible("Z_Z_RETICLE", true);
        super.mesh.chunkVisible("Z_Gunsight_Button2", false);
        super.mesh.chunkVisible("Z_Gunsight_Button3", true);
        if(((com.maddox.il2.objects.air.Mig_17)aircraft()).k14Mode == 2)
        {
            super.mesh.chunkVisible("Z_Z_RETICLE", false);
            super.mesh.chunkVisible("Z_Gunsight_Button2", true);
            super.mesh.chunkVisible("Z_Gunsight_Button3", false);
        }
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
        {
            super.mesh.chunkVisible("Instruments", false);
            super.mesh.chunkVisible("InstrumentsD", true);
            super.mesh.chunkVisible("Z_Z_RETICLE", false);
            super.mesh.chunkVisible("Z_Z_MASK", false);
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("XGlassDamage2", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
            super.mesh.chunkVisible("Z_Speed", false);
            super.mesh.chunkVisible("Z_Compass1", false);
            super.mesh.chunkVisible("Z_Azimuth1", false);
            super.mesh.chunkVisible("Z_GasPrs1a", false);
            super.mesh.chunkVisible("Z_GasPrs2a", false);
            super.mesh.chunkVisible("Z_Alt_Km", false);
            super.mesh.chunkVisible("Z_Alt_M", false);
            super.mesh.chunkVisible("Z_Turn", false);
            super.mesh.chunkVisible("Z_Turn1a", false);
            super.mesh.chunkVisible("Z_Slide1a", false);
            super.mesh.chunkVisible("Z_RPM1", false);
            super.mesh.chunkVisible("Z_Z_RETICLE1", false);
            for(int i = 1; i < 7; i++)
                super.mesh.chunkVisible("Z_Z_AIMMARK" + i, false);

        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("XGlassDamage2", true);
        }
        if(((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0) && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("XGlassDamage2", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
        }
        if(((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0 || (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0) && (((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
            retoggleLight();
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

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictETP;
    private float pictFlap;
    private float pictGear;
    private float pictTLck;
    private float pictMet1;
    private float pictMet2;
    private float pictETrm;
    private float pictTurba;
    private static final float rpmScale[] = {
        0.0F, 8F, 23.5F, 40F, 58.5F, 81F, 104.5F, 130.2F, 158.5F, 187F, 
        217.5F, 251.1F, 281.5F, 289.5F, 295.5F
    };
    private static final float k14TargetMarkScale[] = {
        -0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70F, -95F, -102.5F, -106F
    };
    private static final float k14TargetWingspanScale[] = {
        11F, 11.3F, 11.8F, 15F, 20F, 25F, 30F, 35F, 40F, 43.05F
    };
    private static final float k14BulletDrop[] = {
        5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 
        8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928F, 9.992F, 10.282F, 10.381F, 10.513F, 
        10.603F, 10.704F, 10.739F, 10.782F, 10.789F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;










}
