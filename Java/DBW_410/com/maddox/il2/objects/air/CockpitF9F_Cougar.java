// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 2/04/2011 11:07:38 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CockpitF9.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, F9, Aircraft, Cockpit

public class CockpitF9F_Cougar extends CockpitPilot
{
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
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth = fm.Or.getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                if(fm.CT.arrestorControl <= 0.5F)
                {
                    if(setNew.stbyPosition > 0.0F)
                        setNew.stbyPosition = setOld.stbyPosition - 0.05F;
                } else
                if(setNew.stbyPosition < 1.0F)
                    setNew.stbyPosition = setOld.stbyPosition + 0.05F;
                if(fm.EI.engines[0].getStage() < 6 && !fm.CT.bHasAileronControl && !fm.CT.bHasElevatorControl && !fm.CT.bHasGearControl && !fm.CT.bHasAirBrakeControl && !fm.CT.bHasFlapsControl)
                {
                    if(setNew.stbyPosition3 > 0.0F)
                        setNew.stbyPosition3 = setOld.stbyPosition3 - 0.005F;
                } else
                if(setNew.stbyPosition3 < 1.0F)
                    setNew.stbyPosition3 = setOld.stbyPosition3 + 0.005F;
                float f = ((F9F_Cougar)aircraft()).k14Distance;
                setNew.k14w = (5F * CockpitF9F_Cougar.k14TargetWingspanScale[((F9F_Cougar)aircraft()).k14WingspanType]) / f;
                setNew.k14w = 0.9F * setOld.k14w + 0.1F * setNew.k14w;
                setNew.k14wingspan = 0.9F * setOld.k14wingspan + 0.1F * CockpitF9F_Cougar.k14TargetMarkScale[((F9F_Cougar)aircraft()).k14WingspanType];
                setNew.k14mode = 0.8F * setOld.k14mode + 0.2F * (float)((F9F_Cougar)aircraft()).k14Mode;
                Vector3d vector3d = aircraft().FM.getW();
                double d = 0.00125D * (double)f;
                float f1 = (float)Math.toDegrees(d * vector3d.z);
                float f2 = -(float)Math.toDegrees(d * vector3d.y);
                float f3 = floatindex((f - 200F) * 0.04F, CockpitF9F_Cougar.k14BulletDrop) - CockpitF9F_Cougar.k14BulletDrop[0];
                f2 += (float)Math.toDegrees(Math.atan(f3 / f));
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

        float throttle;
        float vspeed;
        float altimeter;
        float azimuth;
        float waypointAzimuth;
        float k14wingspan;
        float k14mode;
        float k14x;
        float k14y;
        float k14w;
        float stbyPosition;
        float stbyPosition3;

        private Variables()
        {
        }

    }


    public CockpitF9F_Cougar()
    {
        super("3DO/Cockpit/F9/F9.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictGear = 0.0F;
        pictMet1 = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        super.cockpitNightMats = (new String[] {
            "Gauges_01", "Gauges_02", "Gauges_03", "Gauges_04", "Gauges_05", "Gauges_06", "Gauges_08", "Compass1", "Needles"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, Time.current(), null);
        if(super.acoustics != null)
            super.acoustics.globFX = new ReverbFXRoom(0.45F);
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

    protected float waypointAzimuth()
    {
        WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, fm.Loc);
            return (float)(57.295779513082323D * Math.atan2(tmpV.y, tmpV.x));
        }
    }

    public void reflectWorldToInstruments(float f)
    {
        if(F9F_Cougar.bChangedPit)
        {
            F9F_Cougar.bChangedPit = false;
        }
        if((fm.AS.astateCockpitState & 2) == 0)
        {
            int i = ((F9F_Cougar)aircraft()).k14Mode;
            boolean flag = i < 2;
            mesh.chunkVisible("Z_Z_RETICLE", flag);
            flag = i > 0;
            mesh.chunkVisible("Z_Z_RETICLE1", flag);
            mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, setNew.k14x, setNew.k14y);
            resetYPRmodifier();
            Cockpit.xyz[0] = setNew.k14w;
            for(int k = 1; k < 7; k++)
            {
                mesh.chunkVisible("Z_Z_AIMMARK" + k, flag);
                mesh.chunkSetLocate("Z_Z_AIMMARK" + k, Cockpit.xyz, Cockpit.ypr);
            }

        }
        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, -0.52F, 0.0F);
        super.mesh.chunkSetLocate("CanopyOpen01", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen02", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetLocate("CanopyOpen03", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetLocate("XGlassDamage4", Cockpit.xyz, Cockpit.ypr);
        resetYPRmodifier();
        /*if(((FlightModelMain) (fm)).EI.getCatapult() && !fm.brakeShoe && fm.Gears.nOfGearsOnGr > 0)
        {
            Cockpit.xyz[1] = -0.02F;
            super.mesh.chunkSetLocate("Body", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Boites", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("CanLever01", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("CanLever02", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("CanLever03", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("CanLever04", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Collimateur", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("EjectSeat", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("EjectSeat2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("EjectSeat3", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearDn_C", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearDn_L", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearDn_R", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearUp_C", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearUp_L", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearUp_R", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Instruments", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("InstrumentsD", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Interior", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Manette", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("NewVis", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Panel", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("PareBrise2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("PareBrise", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Pedal_L", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Pedal_R", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Placards", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Stick01", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Stick02", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Throttle", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Vis2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Vis", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("VisFace", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("XGlassDamage1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("XGlassDamage2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("XGlassDamage3", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("XGlassDamage4", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Canopy1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Canopy2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_FlapsLever", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_FlareFire1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_FlareFire2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_FlareFuel", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_GearHandle", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Hook2a", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Hook2b", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Hook", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Ignition1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Ignition2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Master1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Master2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Slide1a", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Target1", Cockpit.xyz, Cockpit.ypr);
            if(fm.CT.getCockpitDoor() == 1.0F)
            {
                super.mesh.chunkSetLocate("CanopyOpen01", Cockpit.xyz, Cockpit.ypr);
                super.mesh.chunkSetLocate("CanopyOpen02", Cockpit.xyz, Cockpit.ypr);
                super.mesh.chunkSetLocate("CanopyOpen03", Cockpit.xyz, Cockpit.ypr);
            }
        }*/
        resetYPRmodifier();
        if(fm.Gears.nOfGearsOnGr == 0)
        {
            Cockpit.xyz[1] = 0.0F;
            super.mesh.chunkSetLocate("Body", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Boites", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("CanLever01", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("CanLever02", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("CanLever03", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("CanLever04", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Collimateur", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("EjectSeat", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("EjectSeat2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("EjectSeat3", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearDn_C", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearDn_L", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearDn_R", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearUp_C", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearUp_L", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("FlareGearUp_R", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Instruments", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("InstrumentsD", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Interior", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Manette", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("NewVis", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Panel", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("PareBrise2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("PareBrise", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Pedal_L", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Pedal_R", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Placards", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Stick01", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Stick02", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Throttle", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Vis2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Vis", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("VisFace", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("XGlassDamage1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("XGlassDamage2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("XGlassDamage3", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("XGlassDamage4", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Canopy1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Canopy2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_FlapsLever", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_FlareFire1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_FlareFire2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_FlareFuel", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_GearHandle", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Hook2a", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Hook2b", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Hook", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Ignition1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Ignition2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Master1", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Master2", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Slide1a", Cockpit.xyz, Cockpit.ypr);
            super.mesh.chunkSetLocate("Z_Target1", Cockpit.xyz, Cockpit.ypr);
            if(fm.CT.getCockpitDoor() == 1.0F)
            {
                super.mesh.chunkSetLocate("CanopyOpen01", Cockpit.xyz, Cockpit.ypr);
                super.mesh.chunkSetLocate("CanopyOpen02", Cockpit.xyz, Cockpit.ypr);
                super.mesh.chunkSetLocate("CanopyOpen03", Cockpit.xyz, Cockpit.ypr);
            }
        }
        super.mesh.chunkSetAngles("Z_GearHandle", 0.0F, 0.0F, 50F * (pictGear = 0.82F * pictGear + 0.18F * ((FlightModelMain) (super.fm)).CT.GearControl));
        mesh.chunkSetAngles("Z_FlapsLever", -35F * fm.CT.FlapsControl, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hook", 0.0F, cvt(interp(setNew.stbyPosition, setOld.stbyPosition, f), 0.0F, 1.0F, 0.0F, 35F), 0.0F);
        mesh.chunkSetAngles("Z_Target1", setNew.k14wingspan, 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_G-Factor", cvt(fm.getOverload(), -4F, 12F, -80.5F, 241.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel", cvt(fm.M.fuel, 0.0F, 1358F, -150F, 150F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Turn1a", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
        mesh.chunkSetAngles("Z_Slide1a", 0.0F, cvt(getBall(8D), -8F, 8F, -24F, 24F), 0.0F);
        mesh.chunkSetAngles("Z_horizont1a", 0.0F, -fm.Or.getKren(), 0.0F);
        mesh.chunkSetAngles("Z_Suction", cvt(fm.EI.engines[0].getRPM(), 0.0F, 3500F, 100F, 395F), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(((FlightModelMain) (super.fm)).CT.getRudder(), -1F, 1.0F, -0.035F, 0.035F);
        super.mesh.chunkSetLocate("Pedal_L", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[1] = -Cockpit.xyz[1];
        super.mesh.chunkSetLocate("Pedal_R", Cockpit.xyz, Cockpit.ypr);
        resetYPRmodifier();
        Cockpit.xyz[2] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0328F, -0.0328F);
        mesh.chunkSetLocate("Z_horizont1b", Cockpit.xyz, Cockpit.ypr);
        mesh.chunkSetAngles("Stick01", (pictElev = 0.7F * pictElev + 0.15F * fm.CT.ElevatorControl) * 16F, 0.0F, -(pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F);
        mesh.chunkSetAngles("Stick02", (pictElev = 0.7F * pictElev + 0.15F * fm.CT.ElevatorControl) * 16F, 0.0F, -(pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F);
        pictMet1 = 0.96F * pictMet1 + 0.04F * (0.6F * fm.EI.engines[0].getThrustOutput() * fm.EI.engines[0].getControlThrottle() * (fm.EI.engines[0].getStage() == 6 ? 1.0F : 0.02F));
        mesh.chunkSetAngles("Z_Compass-Emerg1", cvt(fm.Or.getTangage(), -30F, 30F, 30F, -30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass-Emerg3", cvt(fm.Or.getKren(), -45F, 45F, -45F, 45F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass-Emerg2", -interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM-1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 2550F, -180F, 50F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM-2", cvt(fm.EI.engines[0].getRPM(), 2550F, 3500F, -10F, 335F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Exhaust_Temp", cvt(fm.EI.engines[0].tOilOut, 20F, 95F, -160F, -25F), 0.0F, 0.0F);
        //mesh.chunkSetAngles("Z_OilP1", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 6.46F, 0.0F, 270F), 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Compass2", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Compass3", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Azimuth1", -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter_1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter_2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter_3", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2_1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2_2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2_3", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Alt2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 1219.2F, -150F, 160F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speed", floatindex(cvt(Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14F), speedometerScale), 0.0F, 0.0F);     
        //mesh.chunkSetAngles("Z_AirFlow", 0.0F, cvt(Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, -90F, 160F), 0.0F);
        mesh.chunkSetAngles("Z_Climb", 0.0F, cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Second1", cvt(((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour2", cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute2", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Second2", cvt(((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);   
        //mesh.chunkSetAngles("Z_AirSysP", fm.Gears.isHydroOperable() ? -133.5F : 0.0F, 0.0F, 0.0F);
        mesh.chunkVisible("FlareGearUp_R", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
        mesh.chunkVisible("FlareGearUp_L", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
        mesh.chunkVisible("FlareGearUp_C", fm.CT.getGear() < 0.01F);
        mesh.chunkVisible("FlareGearDn_R", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        mesh.chunkVisible("FlareGearDn_L", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
        mesh.chunkVisible("FlareGearDn_C", fm.CT.getGear() > 0.99F);
        mesh.chunkVisible("Z_FlareFuel", fm.M.fuel < 150F);
        mesh.chunkVisible("Z_FlareFire1", fm.AS.astateEngineStates[0] > 2);
        mesh.chunkVisible("Z_FlareFire2", fm.AS.astateEngineStates[0] > 2);
        resetYPRmodifier();
        Cockpit.ypr[0] = interp(setNew.throttle, setOld.throttle, f) * 45F;
        Cockpit.xyz[2] = cvt(Cockpit.ypr[0], 7.5F, 9.5F, 0.0F, 0.0F);
        mesh.chunkSetLocate("Throttle", Cockpit.xyz, Cockpit.ypr);
        float f1 = World.Rnd().nextFloat(0.87F, 1.04F);
        /*
        if(fm.CT.getCockpitDoor() == 1.0F)
        {
            mesh.chunkVisible("V_G", false);
            mesh.chunkVisible("V_D", false);
        } else
        {
            mesh.chunkVisible("V_G", true);
            mesh.chunkVisible("V_D", true);
        }
        */
        if(fm.CT.getCockpitDoor() > 0.49F && fm.getSpeed() > 164F * f1)
        {
            fm.CT.bHasCockpitDoorControl = false;
            mesh.chunkVisible("CanopyOpen01", false);
            mesh.chunkVisible("CanopyOpen02", false);
            mesh.chunkVisible("CanopyOpen03", false);
            mesh.chunkVisible("XGlassDamage4", false);
        }
        if(fm.AS.bIsAboutToBailout && !fm.brakeShoe && fm.getSpeedKMH() >= 100F && fm.Gears.nOfGearsOnGr == 0)
        {
            mesh.chunkVisible("Z_Canopy1", false);
            mesh.chunkVisible("Z_Canopy2", true);
        } else
        {
            mesh.chunkVisible("Z_Canopy1", true);
            mesh.chunkVisible("Z_Canopy2", false);
        }
        if(fm.AS.bIsAboutToBailout)
        {
            mesh.chunkVisible("CanopyOpen01", false);
            mesh.chunkVisible("CanopyOpen02", false);
            mesh.chunkVisible("CanopyOpen03", false);
            mesh.chunkVisible("XGlassDamage4", false);
        }
        if(fm.CT.cockpitDoorControl == 1.0F && !fm.AS.bIsAboutToBailout)
        {
            if(fm.CT.getCockpitDoor() > 0.0F && fm.CT.getCockpitDoor() < 0.2F)
            {
                mesh.chunkVisible("CanLever01", false);
                mesh.chunkVisible("CanLever03", true);
                mesh.chunkVisible("CanLever04", false);
            } else
            {
                mesh.chunkVisible("CanLever01", true);
                mesh.chunkVisible("CanLever03", false);
                mesh.chunkVisible("CanLever04", false);
            }
        } else
        if(fm.CT.cockpitDoorControl == 0.0F && !fm.AS.bIsAboutToBailout)
        {
            if(fm.CT.getCockpitDoor() < 1.0F && fm.CT.getCockpitDoor() > 0.8F)
            {
                mesh.chunkVisible("CanLever01", false);
                mesh.chunkVisible("CanLever02", true);
                mesh.chunkVisible("CanLever04", false);
            } else
            {
                mesh.chunkVisible("CanLever01", true);
                mesh.chunkVisible("CanLever02", false);
                mesh.chunkVisible("CanLever04", false);
            }
        } else
        if(fm.AS.bIsAboutToBailout && fm.getSpeedKMH() < 100F && fm.Gears.nOfGearsOnGr > 0)
        {
            mesh.chunkVisible("CanLever01", false);
            mesh.chunkVisible("CanLever04", true);
        }
        if(fm.CT.BayDoorControl == 1.0F)
        {
            mesh.chunkVisible("Z_Master1", false);
            mesh.chunkVisible("Z_Master2", true);
        }
        if(fm.CT.BayDoorControl == 0.0F)
        {
            mesh.chunkVisible("Z_Master1", true);
            mesh.chunkVisible("Z_Master2", false);
        }
        if(fm.CT.arrestorControl <= 0.5F)
        {
            mesh.chunkVisible("Z_Hook2b", false);
            mesh.chunkVisible("Z_Hook2a", true);
        }
        if(fm.CT.arrestorControl > 0.5F)
        {
            mesh.chunkVisible("Z_Hook2a", false);
            mesh.chunkVisible("Z_Hook2b", true);
        }
        if(fm.EI.engines[0].getStage() < 6 && fm.Gears.nOfGearsOnGr == 0)
        {
            mesh.chunkVisible("Z_Ignition1", false);
            mesh.chunkVisible("Z_Ignition2", true);
        }
        else
        {
            mesh.chunkVisible("Z_Ignition1", true);
            mesh.chunkVisible("Z_Ignition2", false);
        }
        if(((F9F_Cougar)aircraft()).k14Mode == 2){
            mesh.chunkVisible("Z_Z_RETICLE", false);
        }
        else {
            mesh.chunkVisible("Z_Z_RETICLE", true);
        	}
    }

    public void reflectCockpitState()
    {
        if((((FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
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
            super.mesh.chunkVisible("Z_Alt_Km", false);
            super.mesh.chunkVisible("Z_Alt_M", false);
            super.mesh.chunkVisible("Z_Turn", false);
            super.mesh.chunkVisible("Z_Turn1a", false);
            super.mesh.chunkVisible("Z_Slide1a", false);
            super.mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_Z_RETICLE1", false);
            for(int i = 1; i < 7; i++)
                mesh.chunkVisible("Z_Z_AIMMARK" + i, false);

        }
        if((((FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("XGlassDamage2", true);
        }
        if(((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0 || (((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0) && (((FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("XGlassDamage2", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
        }
        if(((((FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0 || (((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0) && (((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
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

    public void doToggleDim()
    {
    }

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictGear;
    private float pictMet1;
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
    private static final float speedometerScale[] = {
        0.0F, 42F, 65.5F, 88.5F, 111.3F, 134F, 156.5F, 181F, 205F, 227F, 
        249.4F, 271.7F, 294F, 316.5F, 339.5F
    };
    private Point3d tmpP;
    private Vector3d tmpV;
    
    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitF9F_Cougar.class, "normZN", 0.93F);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitF9F_Cougar.class, "gsZN", 0.93F);
    }
}