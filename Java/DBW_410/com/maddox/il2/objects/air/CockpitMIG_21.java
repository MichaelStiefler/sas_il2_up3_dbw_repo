// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 11/02/2011 12:29:33 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CockpitMIG_21.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitMIG_21 extends CockpitPilot
{
    private class Variables
    {

        float throttle;
        float vspeed;
        float starter;
        float altimeter;
        AnglesFork azimuth;
        AnglesFork waypointAzimuth;

        private Variables()
        {
            throttle = 0.0F;
            starter = 0.0F;
            altimeter = 0.0F;
            vspeed = 0.0F;
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }

    class Interpolater extends InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.throttle = 0.9F * setOld.throttle + ((FlightModelMain) (fm)).CT.PowerControl * 0.1F;
                setNew.starter = 0.94F * setOld.starter + 0.06F * (((FlightModelMain) (fm)).EI.engines[0].getStage() > 0 && ((FlightModelMain) (fm)).EI.engines[0].getStage() < 6 ? 1.0F : 0.0F);
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((FlightModelMain) (fm)).Or.azimut());
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + World.Rnd().nextFloat(-10F, 10F));
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    protected boolean doFocusEnter() {
        if (super.doFocusEnter()) {
          aircraft().hierMesh().chunkVisible("Blister1_D0", false);
          return true;
        } else {
          return false;
        }
      }

      protected void doFocusLeave() {
        if (!isFocused()) {
          return;
        } else {
          aircraft().hierMesh().chunkVisible("Blister1_D0", true);
          super.doFocusLeave();
          return;
        }
      }
    
   private float machNumber() {
    	 return ((MIG_21) super.aircraft()).calculateMach();
   }
      
    public CockpitMIG_21()
    {
        super("3DO/Cockpit/MiG-21/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
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
        super.cockpitNightMats = (new String[] {
            "gauges_01", "gauges_02", "gauges_03", "gauges_04", "gauges_05", "Dgauges_01", "Dgauges_02", "Dgauges_03", "Dgauges_05"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, Time.current(), null);
        if(super.acoustics != null)
            super.acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        resetYPRmodifier();
        mesh.chunkSetAngles("Canopy", 0.0F, -50F * ((FlightModelMain) (fm)).CT.getCockpitDoor(), 0.0F);
        super.mesh.chunkSetAngles("stick", 0.0F,(pictElev = 0.85F * pictElev + 0.15F * ((FlightModelMain) (super.fm)).CT.ElevatorControl) * 10F, -(pictAiler = 0.85F * pictAiler + 0.15F * ((FlightModelMain) (super.fm)).CT.AileronControl) * 10F);
        mesh.chunkSetAngles("leftrudder", 10F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("rightrudder", 10F * fm.CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("throttle", 0.0F, -40.909F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        mesh.chunkSetAngles("NeSpeed", 0.0F, 0.0F, -cvt(Pitot.Indicator((float) fm.Loc.z, fm.getSpeedKMH()), 0.0F, 2500.0F, 0.0F, 360.0F));
        mesh.chunkSetAngles("NeMach", 0.0F, 0.0F, -cvt(machNumber(), 0.2F, 2.6F, 0.0F, 360F));
        mesh.chunkSetAngles("NeAlt1", 0.0F, 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F));
        mesh.chunkSetAngles("NeAlt2", 0.0F, 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F));
        mesh.chunkSetAngles("NeRPM", 0.0F, 0.0F, -cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 351.0F));
        mesh.chunkSetAngles("NeFuel", 0.0F, 0.0F, -cvt(fm.M.fuel / 2.0F, 0.0F, 1350.0F, 0.0F, 340.0F));
        mesh.chunkSetAngles("NeExhaustTemp", 0.0F, 0.0F, -cvt(fm.EI.engines[0].tOilOut, 20F, 175F, 0F, 265F));
        /*
        super.mesh.chunkSetAngles("CnOpenLvr", cvt(((FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F, 0.08F, 0.0F, -94F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 50F * (pictGear = 0.82F * pictGear + 0.18F * ((FlightModelMain) (super.fm)).CT.GearControl));
        super.mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, 111F * (pictFlap = 0.88F * pictFlap + 0.12F * ((FlightModelMain) (super.fm)).CT.FlapsControl));
        super.mesh.chunkSetAngles("TQHandle1", 0.0F, 0.0F, -40.909F * interp(setNew.throttle, setOld.throttle, f));
        super.mesh.chunkSetAngles("TQHandle2", 0.0F, 0.0F, -40.909F * interp(setNew.throttle, setOld.throttle, f));
        super.mesh.chunkSetAngles("NossleLvr1", 0.0F, 0.0F, -40.909F * interp(setNew.throttle, setOld.throttle, f));
        super.mesh.chunkSetAngles("NossleLvr2", 0.0F, 0.0F, -40.909F * interp(setNew.throttle, setOld.throttle, f));
        super.mesh.chunkSetAngles("Lvr1", 0.0F, 0.0F, -25F * (pictTLck = 0.85F * pictTLck + 0.15F * (((FlightModelMain) (super.fm)).Gears.bTailwheelLocked ? 1.0F : 0.0F)));

        if(((FlightModelMain) (super.fm)).CT.getTrimElevatorControl() != pictETP)
        {
            if(((FlightModelMain) (super.fm)).CT.getTrimElevatorControl() - pictETP > 0.0F)
            {
                super.mesh.chunkSetAngles("ElevTrim", 0.0F, -30F, 0.0F);
                pictETrm = Time.current();
            } else
            {
                super.mesh.chunkSetAngles("ElevTrim", 0.0F, 30F, 0.0F);
                pictETrm = Time.current();
            }
            pictETP = ((FlightModelMain) (super.fm)).CT.getTrimElevatorControl();
        } else
        if((float)Time.current() > pictETrm + 500F)
        {
            super.mesh.chunkSetAngles("ElevTrim", 0.0F, 0.0F, 0.0F);
            pictETrm = Time.current() + 0x7a120L;
        }
        resetYPRmodifier();
        super.mesh.chunkSetAngles("FLCSA", 0.0F, 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * ((FlightModelMain) (super.fm)).CT.ElevatorControl));
        super.mesh.chunkSetAngles("FLCSB", 0.0F, 10F * (pictAiler = 0.85F * pictAiler + 0.15F * ((FlightModelMain) (super.fm)).CT.AileronControl), 0.0F);
        super.mesh.chunkSetAngles("NeedRPM1", 0.0F, floatindex(cvt(((FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 4500F, 0.0F, 14F), rpmScale), 0.0F);
        pictMet1 = 0.96F * pictMet1 + 0.024F * ((FlightModelMain) (super.fm)).EI.engines[0].getThrustOutput() * ((FlightModelMain) (super.fm)).EI.engines[0].getControlThrottle() * (((FlightModelMain) (super.fm)).EI.engines[0].getStage() != 6 ? 0.02F : 1.0F);
        super.mesh.chunkSetAngles("NeedExhstPress1", 0.0F, cvt(pictMet1, 0.0F, 1.0F, 0.0F, 270F), 0.0F);
        super.mesh.chunkSetAngles("NeedFuelPress1", cvt(((FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : 0.55F, 0.0F, 1.0F, 0.0F, 284F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedExstT1", 0.0F, cvt(((FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F, 1200F, 0.0F, 112F), 0.0F);
        super.mesh.chunkSetAngles("NeedOilP1", 0.0F, cvt(1.0F + 0.05F * ((FlightModelMain) (super.fm)).EI.engines[0].tOilOut * ((FlightModelMain) (super.fm)).EI.engines[0].getReadyness(), 0.0F, 6.46F, 0.0F, 270F), 0.0F);
        super.mesh.chunkSetAngles("NeedFuel1", 0.0F, floatindex(cvt(((FlightModelMain) (super.fm)).M.fuel, 0.0F, 864F, 0.0F, 4F), fuelScale), 0.0F);
        super.mesh.chunkSetAngles("NeedFuel2", 0.0F, floatindex(cvt(((FlightModelMain) (super.fm)).M.fuel, 864F, 1728F, 0.0F, 4F), fuelScale), 0.0F);
        super.mesh.chunkSetAngles("NeedAlt_Km", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 60000F, 0.0F, 2160F), 0.0F);
        super.mesh.chunkSetAngles("NeedAlt_M", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 60000F, 0.0F, 21600F), 0.0F);
        super.mesh.chunkSetAngles("NeedCompassA", 0.0F, -setNew.azimuth.getDeg(f), 0.0F);
        super.mesh.chunkSetAngles("NeedCompassB", 0.0F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedSpeed", 0.0F, cvt(Pitot.Indicator((float)((Tuple3d) (((FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 1200F, 0.0F, 360F), 0.0F);
        super.mesh.chunkSetAngles("NeedClimb", 0.0F, cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        super.mesh.chunkSetAngles("NeedAHCyl", 0.0F, -((FlightModelMain) (super.fm)).Or.getKren() + 180F, 0.0F);
        super.mesh.chunkSetAngles("NeedAHBar", 0.0F, 0.0F, -((FlightModelMain) (super.fm)).Or.getTangage());
        super.mesh.chunkSetAngles("NeedTurn", 0.0F, cvt(getBall(8D), -8F, 8F, -15F, 15F), 0.0F);
        super.mesh.chunkSetAngles("NeedDF", 0.0F, cvt(setNew.waypointAzimuth.getDeg(f), -90F, 90F, -16.5F, 16.5F), 0.0F);
        super.mesh.chunkSetAngles("NeedHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        super.mesh.chunkSetAngles("NeedMin", 0.0F, cvt(super.fm.getOverload(), 0.0F, 12F, 0.0F, 360F), 0.0F);
        super.mesh.chunkSetAngles("NeedStarter1", cvt(setNew.starter, 0.0F, 1.0F, 0.0F, -120F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedStarter2", cvt(setNew.starter, 0.0F, 1.0F, 0.0F, -120F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedEmrgAirP", -63.5F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("NeedAirSysP", ((FlightModelMain) (super.fm)).Gears.isHydroOperable() ? -133.5F : 0.0F, 0.0F, 0.0F);
        super.mesh.chunkVisible("FlareGearUp_R", ((FlightModelMain) (super.fm)).CT.getGear() < 0.01F || !((FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkVisible("FlareGearUp_L", ((FlightModelMain) (super.fm)).CT.getGear() < 0.01F || !((FlightModelMain) (super.fm)).Gears.lgear);
        super.mesh.chunkVisible("FlareGearUp_C", ((FlightModelMain) (super.fm)).CT.getGear() < 0.01F);
        super.mesh.chunkVisible("FlareGearDn_R", ((FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkVisible("FlareGearDn_L", ((FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((FlightModelMain) (super.fm)).Gears.lgear);
        super.mesh.chunkVisible("FlareGearDn_C", ((FlightModelMain) (super.fm)).CT.getGear() > 0.99F);
        super.mesh.chunkVisible("FlareFuel", ((FlightModelMain) (super.fm)).M.fuel < 296.1F);
*/
    }

    protected float waypointAzimuth()
    {
        WayPoint waypoint = ((FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, ((FlightModelMain) (super.fm)).Loc);
            return (float)(57.295779513082323D * Math.atan2(-((Tuple3d) (tmpV)).y, ((Tuple3d) (tmpV)).x));
        }
    }

    public void reflectCockpitState()
    {
    	/*
        if((((FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
        {
            super.mesh.chunkVisible("Z_Z_RETICLE", false);
            super.mesh.chunkVisible("Z_Z_MASK", false);
        }
        if((((FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
        {
            super.mesh.chunkVisible("DamageGlass2", true);
            super.mesh.chunkVisible("DamageGlass3", true);
        }
        if((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0 || (((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0)
        {
            super.mesh.chunkVisible("Gages1_D0", false);
            super.mesh.chunkVisible("Gages1_D1", true);
            super.mesh.chunkVisible("NeedSpeed", false);
            super.mesh.chunkVisible("NeedClimb", false);
            super.mesh.chunkVisible("NeedAlt_Km", false);
            super.mesh.chunkVisible("NeedAlt_M", false);
            super.mesh.chunkVisible("NeedDF", false);
            super.mesh.chunkVisible("NeedCompassA", false);
            super.mesh.chunkVisible("NeedCompassB", false);
            super.mesh.chunkVisible("DamageHull1", true);
        }
        if((((FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
        {
            super.mesh.chunkVisible("Gages3_D0", false);
            super.mesh.chunkVisible("Gages3_D1", true);
            super.mesh.chunkVisible("NeedHour", false);
            super.mesh.chunkVisible("NeedMin", false);
            super.mesh.chunkVisible("NeedRPM1", false);
            super.mesh.chunkVisible("NeedExhstPress1", false);
            super.mesh.chunkVisible("DamageHull3", true);
        }
        if((((FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0 || (((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
        {
            super.mesh.chunkVisible("Gages4_D0", false);
            super.mesh.chunkVisible("Gages4_D1", true);
            super.mesh.chunkVisible("NeedRPM2", false);
            super.mesh.chunkVisible("NeedOilP1", false);
            super.mesh.chunkVisible("NeedFuel1", false);
            super.mesh.chunkVisible("NeedExstT1", false);
            super.mesh.chunkVisible("DamageHull2", true);
        }
        if((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
        {
            super.mesh.chunkVisible("Gages5_D0", false);
            super.mesh.chunkVisible("Gages5_D1", true);
            super.mesh.chunkVisible("NeedOilP2", false);
            super.mesh.chunkVisible("NeedExhstPress2", false);
            super.mesh.chunkVisible("NeedExstT2", false);
            super.mesh.chunkVisible("NeedFuel2", false);
        retoggleLight();
        */
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

    public void doToggleDim()
    {
    }

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    private float pictAiler;
    private float pictElev;
    private float pictETP;
    private float pictFlap;
    private float pictGear;
    private float pictTLck;
    private float pictMet1;
    private float pictMet2;
    private float pictETrm;
    private static final float rpmScale[] = {
        0.0F, 8F, 23.5F, 40F, 58.5F, 81F, 104.5F, 130.2F, 158.5F, 187F, 
        217.5F, 251.1F, 281.5F, 289.5F, 295.5F
    };
    private static final float fuelScale[] = {
        0.0F, 18.5F, 49F, 80F, 87F
    };
    private Point3d tmpP;
    private Vector3d tmpV;
}