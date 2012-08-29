// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitF9F2.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
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
//            CockpitPilot, Cockpit, F9F

public class CockpitF9F2 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.azimuth = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 200F;
                if(((com.maddox.il2.fm.FlightModelMain) (fm)).CT.arrestorControl <= 0.5F)
                {
                    if(setNew.stbyPosition > 0.0F)
                        setNew.stbyPosition = setOld.stbyPosition - 0.05F;
                } else
                if(setNew.stbyPosition < 1.0F)
                    setNew.stbyPosition = setOld.stbyPosition + 0.05F;
                if(((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getStage() < 6 && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasAileronControl && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasElevatorControl && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasGearControl && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasAirBrakeControl && !((com.maddox.il2.fm.FlightModelMain) (fm)).CT.bHasFlapsControl)
                {
                    if(setNew.stbyPosition3 > 0.0F)
                        setNew.stbyPosition3 = setOld.stbyPosition3 - 0.005F;
                } else
                if(setNew.stbyPosition3 < 1.0F)
                    setNew.stbyPosition3 = setOld.stbyPosition3 + 0.005F;
                float f = ((com.maddox.il2.objects.air.F9F)aircraft()).k14Distance;
                setNew.k14w = (5F * com.maddox.il2.objects.air.CockpitF9F2.k14TargetWingspanScale[((com.maddox.il2.objects.air.F9F)aircraft()).k14WingspanType]) / f;
                setNew.k14w = 0.9F * setOld.k14w + 0.1F * setNew.k14w;
                setNew.k14wingspan = 0.9F * setOld.k14wingspan + 0.1F * com.maddox.il2.objects.air.CockpitF9F2.k14TargetMarkScale[((com.maddox.il2.objects.air.F9F)aircraft()).k14WingspanType];
                setNew.k14mode = 0.8F * setOld.k14mode + 0.2F * (float)((com.maddox.il2.objects.air.F9F)aircraft()).k14Mode;
                com.maddox.JGP.Vector3d vector3d = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM)).getW();
                double d = 0.00125D * (double)f;
                float f1 = (float)java.lang.Math.toDegrees(d * ((com.maddox.JGP.Tuple3d) (vector3d)).z);
                float f2 = -(float)java.lang.Math.toDegrees(d * ((com.maddox.JGP.Tuple3d) (vector3d)).y);
                float f3 = floatindex((f - 200F) * 0.04F, com.maddox.il2.objects.air.CockpitF9F2.k14BulletDrop) - com.maddox.il2.objects.air.CockpitF9F2.k14BulletDrop[0];
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

        Variables(com.maddox.il2.objects.air.Variables variables)
        {
            this();
        }
    }


    public CockpitF9F2()
    {
        super("3DO/Cockpit/F9/F9.him", "bf109");
        setOld = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        setNew = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictGear = 0.0F;
        pictMet1 = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "Gauges_01", "Gauges_02", "Gauges_03", "Gauges_04", "Gauges_05", "Gauges_06", "Gauges_08", "Compass1", "Needles"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        if(((com.maddox.il2.engine.Actor)this).acoustics != null)
            ((com.maddox.il2.engine.Actor)this).acoustics.globFX = new ReverbFXRoom(0.45F);
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

    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            ((com.maddox.JGP.Tuple3d) (tmpV)).sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public void reflectWorldToInstruments(float f)
    {
        if(com.maddox.il2.objects.air.F9F.bChangedPit)
            com.maddox.il2.objects.air.F9F.bChangedPit = false;
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) == 0)
        {
            int i = ((com.maddox.il2.objects.air.F9F)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).k14Mode;
            boolean flag = i < 2;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", flag);
            flag = i > 0;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE1", flag);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, setNew.k14x, setNew.k14y);
            ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = setNew.k14w;
            for(int k = 1; k < 7; k++)
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_AIMMARK" + k, flag);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Z_AIMMARK" + k, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            }

        }
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor(), 0.01F, 0.99F, -0.52F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen03", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        if(!((com.maddox.il2.objects.air.Cockpit)this).fm.brakeShoe && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.nOfGearsOnGr > 0)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -0.02F;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Body", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Boites", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanLever01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanLever02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanLever03", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanLever04", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Collimateur", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("EjectSeat", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("EjectSeat2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("EjectSeat3", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearDn_C", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearDn_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearDn_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearUp_C", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearUp_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearUp_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Instruments", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("InstrumentsD", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Interior", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Manette", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("NewVis", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Panel", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("PareBrise2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("PareBrise", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Pedal_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Pedal_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Placards", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Stick01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Stick02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Throttle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Vis2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Vis", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("VisFace", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage3", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Canopy1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Canopy2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlapsLever", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlareFire1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlareFire2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlareFuel", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_GearHandle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Hook2a", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Hook2b", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Hook", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Ignition1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Ignition2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Master1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Master2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Slide1a", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Target1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() == 1.0F)
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen03", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            }
        }
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.nOfGearsOnGr == 0)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.0F;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Body", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Boites", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanLever01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanLever02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanLever03", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanLever04", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Collimateur", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("EjectSeat", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("EjectSeat2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("EjectSeat3", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearDn_C", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearDn_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearDn_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearUp_C", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearUp_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FlareGearUp_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Instruments", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("InstrumentsD", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Interior", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Manette", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("NewVis", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Panel", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("PareBrise2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("PareBrise", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Pedal_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Pedal_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Placards", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Stick01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Stick02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Throttle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Vis2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Vis", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("VisFace", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage3", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Canopy1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Canopy2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlapsLever", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlareFire1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlareFire2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_FlareFuel", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_GearHandle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Hook2a", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Hook2b", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Hook", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Ignition1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Ignition2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Master1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Master2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Slide1a", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Target1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() == 1.0F)
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("CanopyOpen03", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            }
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_GearHandle", 0.0F, 0.0F, 50F * (pictGear = 0.82F * pictGear + 0.18F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_FlapsLever", -35F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Hook", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.stbyPosition, setOld.stbyPosition, f), 0.0F, 1.0F, 0.0F, 35F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Target1", setNew.k14wingspan, 0.0F, 0.0F);
        ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getW())));
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_G-Factor", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getOverload(), -4F, 12F, -80.5F, 241.5F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Fuel", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel, 0.0F, 1358F, -150F, 150F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Turn1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Slide1a", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(8D), -8F, 8F, -24F, 24F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_horizont1a", 0.0F, -((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Suction", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 3500F, 100F, 395F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), -1F, 1.0F, -0.035F, 0.035F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Pedal_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -com.maddox.il2.objects.air.Cockpit.xyz[1];
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Pedal_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -45F, 45F, 0.0328F, -0.0328F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_horizont1b", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick01", (pictElev = 0.7F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl) * 16F, 0.0F, -(pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl) * 15F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Stick02", (pictElev = 0.7F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl) * 16F, 0.0F, -(pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl) * 15F);
        pictMet1 = 0.96F * pictMet1 + 0.04F * (0.6F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getThrustOutput() * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlThrottle() * (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getStage() != 6 ? 0.02F : 1.0F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass-Emerg1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -30F, 30F, 30F, -30F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass-Emerg3", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren(), -45F, 45F, -45F, 45F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass-Emerg2", -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPM-1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 2550F, -180F, 50F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPM-2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 2550F, 3500F, -10F, 335F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Exhaust_Temp", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 20F, 95F, -160F, -25F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass1", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass2", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass3", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Azimuth1", -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter_1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter_2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter_3", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter2_1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter2_2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter2_3", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Alt2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 1219.2F, -150F, 160F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Speed", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14F), speedometerScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Climb", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Hour1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Minute1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Second1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Hour2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Minute2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Second2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearUp_R", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearUp_L", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearUp_C", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearDn_R", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearDn_L", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("FlareGearDn_C", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FlareFuel", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel < 150F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FlareFire1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateEngineStates[0] > 2);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_FlareFire2", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateEngineStates[0] > 2);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.ypr[0] = ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f) * 45F;
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.objects.air.Cockpit.ypr[0], 7.5F, 9.5F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Throttle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateEngineStates[0] > 2 && (((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor instanceof com.maddox.il2.objects.air.CockpitF9F2))
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            ((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor.pos.getCurrent(loc);
            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor, ((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor.findHook("_Smoke"), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "EFFECTS/Smokes/SmokeBoiling.eff", 1200F);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.bIsAboutToBailout && !((com.maddox.il2.objects.air.Cockpit)this).fm.brakeShoe && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH() >= 100F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.nOfGearsOnGr == 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Canopy1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Canopy2", true);
        } else
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Canopy1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Canopy2", false);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.bIsAboutToBailout)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen01", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen02", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanopyOpen03", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", false);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.cockpitDoorControl == 1.0F && !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.bIsAboutToBailout)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() > 0.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() < 0.2F)
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever01", false);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever02", true);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever04", false);
            } else
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever01", true);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever02", false);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever04", false);
            }
        } else
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.cockpitDoorControl == 0.0F && !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.bIsAboutToBailout)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() < 1.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor() > 0.8F)
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever01", false);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever03", true);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever04", false);
            } else
            {
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever01", true);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever03", false);
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever04", false);
            }
        } else
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.bIsAboutToBailout && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH() < 100F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.nOfGearsOnGr > 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever01", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("CanLever04", true);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.BayDoorControl == 1.0F)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Master1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Master2", true);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.BayDoorControl == 0.0F)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Master1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Master2", false);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.arrestorControl <= 0.5F)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Hook2b", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Hook2a", true);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.arrestorControl > 0.5F)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Hook2a", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Hook2b", true);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getStage() < 6 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.nOfGearsOnGr == 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Ignition1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Ignition2", true);
        } else
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Ignition1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Ignition2", false);
        }
        if(((com.maddox.il2.objects.air.F9F)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).k14Mode == 2)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", false);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Z_RETICLE", true);
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

    public void doToggleDim()
    {
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
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
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;










}
