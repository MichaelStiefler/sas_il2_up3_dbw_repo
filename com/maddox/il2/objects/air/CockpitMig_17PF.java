// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
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
//            CockpitPilot, Cockpit, Mig_17

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
                setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
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
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).azimut());
                setNew.beaconDirection = (10F * setOld.beaconDirection + getBeaconDirection()) / 11F;
                setNew.beaconRange = (10F * setOld.beaconRange + getBeaconRange()) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 200F;
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
                com.maddox.JGP.Vector3d vector3d = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM)).getW();
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

        Variables(com.maddox.il2.objects.air.Variables variables)
        {
            this();
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Blister1_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!((com.maddox.il2.objects.air.Cockpit)this).isFocused())
        {
            return;
        } else
        {
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Blister1_D0", true);
            super.doFocusLeave();
            return;
        }
    }

    public CockpitMig_17PF()
    {
        super("3DO/Cockpit/MiG-17PF/MiG_17PF.him", "bf109");
        setOld = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        setNew = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
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
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "Gauges_01", "Gauges_02", "Gauges_03", "Gauges_04", "Gauges_05", "Gauges_06", "Gauges_08", "MiG-15_Compass"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        if(((com.maddox.il2.engine.Actor)this).acoustics != null)
            ((com.maddox.il2.engine.Actor)this).acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    protected float waypointAzimuth()
    {
        return ((com.maddox.il2.objects.air.Cockpit)this).waypointAzimuthInvertMinus(30F);
    }

    private float machNumber()
    {
        return ((com.maddox.il2.objects.air.Mig_17)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).calculateMach();
    }

    public void reflectWorldToInstruments(float f)
    {
        com.maddox.il2.objects.air.Mig_17 Mig_17 = (com.maddox.il2.objects.air.Mig_17)((com.maddox.il2.objects.air.Cockpit)this).aircraft();
        if(com.maddox.il2.objects.air.Mig_17.bChangedPit)
        {
            com.maddox.il2.objects.air.Mig_17 Mig_17_3_ = (com.maddox.il2.objects.air.Mig_17)((com.maddox.il2.objects.air.Cockpit)this).aircraft();
            com.maddox.il2.objects.air.Mig_17.bChangedPit = false;
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) == 0)
        {
            int i = ((com.maddox.il2.objects.air.Mig_17)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).k14Mode;
            boolean bool = i < 2;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", bool);
            bool = i > 0;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE1", bool);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, setNew.k14x, setNew.k14y);
            ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = setNew.k14w;
            for(int i_4_ = 1; i_4_ < 7; i_4_++)
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_AIMMARK" + i_4_, bool);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Z_AIMMARK" + i_4_, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            }

        }
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor(), 0.01F, 0.99F, -0.49F, 0.0F);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor(), 0.01F, 0.99F, -0.065F, 0.0F);
        com.maddox.il2.objects.air.Cockpit.ypr[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor(), 0.99F, 0.01F, -3F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen03", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen04", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen05", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen06", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen07", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen08", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen09", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 50F * (pictGear = 0.82F * pictGear + 0.18F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FlapsLever", -35F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Gas1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel / 2.0F, 0.0F, 700F, 0.0F, 180F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Target1", 1.2F * setNew.k14wingspan, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Gunsight_Button", -10F * setNew.k14wingspan, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Gunsight_Mire", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 47F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Amp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.stbyPosition2, setOld.stbyPosition2, f), 0.0F, 1.0F, 0.0F, 40F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_HydroPressure", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.stbyPosition3, setOld.stbyPosition3, f), 0.0F, 1.0F, 0.0F, 190F), 0.0F);
        ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getW())));
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Turn1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Slide1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(8D), -8F, 8F, -24F, 24F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Slide1a2", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(8D), -8F, 8F, -20F, 20F), 0.0F);
        if(((com.maddox.il2.objects.air.Cockpit)this).useRealisticNavigationInstruments())
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass2", 90F + setNew.azimuth.getDeg(f * 0.1F) + setNew.beaconDirection, 0.0F, 0.0F);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass2", 90F + setNew.azimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_horizont1a", 0.0F, -((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_GasPrs1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel <= 1.0F ? 0.0F : ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 3050F, 0.0F, 4F), 0.0F, 5F, -45F, 225F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_GasPrs2a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel <= 1.0F ? 0.0F : ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 3050F, 0.0F, 4F), 0.0F, 5F, -180F, 0.0F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TOilOut1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 110F, 5F, 300F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_OilPrs1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 15F, -155F, -360F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), -1F, 1.0F, -0.035F, 0.035F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Pedal_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -com.maddox.il2.objects.air.Cockpit.xyz[1];
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Pedal_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -45F, 45F, 0.0328F, -0.0328F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_horizont1b", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick01", 0.0F, 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick02", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick03", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick04", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick05", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick06", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick07", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPM1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 3480F, 90F, 625F), 0.0F, 0.0F);
        pictMet1 = 0.96F * pictMet1 + 0.04F * (0.6F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getThrustOutput() * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlThrottle() * (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getStage() != 6 ? 0.02F : 1.0F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FuelPress1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel <= 1.0F ? 0.0F : 0.55F, 0.0F, 1.0F, 0.0F, 284F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ExstT1", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut, 0.0F, 1200F, 0.0F, 112F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Azimuth1", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass1", 0.0F, -setNew.azimuth.getDeg(f * 0.1F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Oxypres1", 142.5F, 0.0F, 0.0F);
        if(machNumber() < 0.95F || machNumber() > 1.0F)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Alt_Km", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 60000F, 0.0F, 2160F), 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Alt_M", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 60000F, 0.0F, 21600F), 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Alt2_Km", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 12000F, 225F, 495F), 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Alt3_Km", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 12000F, 295F, 395F), 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Speed", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 1200F, 0.0F, 360F), 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AirFlow", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 800F, -90F, 160F), 0.0F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Climb", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Vibrations", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 4500F, 5.5F, 14F), rpmScale), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Throttle", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 3480F, -110F, 170F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Hour1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Minute1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Second1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearUp_R", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearUp_L", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearUp_C", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearDn_R", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearDn_L", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearDn_C", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareFuel", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel < 296.1F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareTankFuel", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel < 1215F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 1207F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareFire", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateEngineStates[0] > 2);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.ypr[0] = ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f) * 65F;
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.objects.air.Cockpit.ypr[0], 7.5F, 11.5F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Throttle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        float f_5_ = com.maddox.il2.ai.World.Rnd().nextFloat(0.87F, 1.04F);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() == 1.0F)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("V_G", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("V_D", false);
        } else
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("V_G", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("V_D", true);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() == 1.0F && !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.bIsAboutToBailout)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen07", true);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen07", false);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() < 1.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.bHasCockpitDoorControl)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen06", true);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen06", false);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.bIsAboutToBailout)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen01", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen02", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen03", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen04", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen05", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen08", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen09", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", false);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.BayDoorControl == 1.0F)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Stick04", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Stick05", true);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.BayDoorControl == 0.0F)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Stick04", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Stick05", false);
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", true);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Gunsight_Button2", false);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Gunsight_Button3", true);
        if(((com.maddox.il2.objects.air.Mig_17)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).k14Mode == 2)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Gunsight_Button2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Gunsight_Button3", false);
        }
    }

    public void toggleDim()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitDimControl;
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Instruments", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("InstrumentsD", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_MASK", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Speed", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Compass1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Azimuth1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GasPrs1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GasPrs2a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Alt_Km", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Alt_M", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Turn", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Turn1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Slide1a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_RPM1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE1", false);
            for(int i = 1; i < 7; i++)
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_AIMMARK" + i, false);

        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage2", true);
        }
        if(((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) != 0) && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", true);
        }
        if(((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) != 0 || (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) != 0) && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) != 0)
            retoggleLight();
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
