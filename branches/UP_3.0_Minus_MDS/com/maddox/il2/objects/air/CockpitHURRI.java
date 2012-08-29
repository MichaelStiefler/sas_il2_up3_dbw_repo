// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitHURRI.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
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
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitHURRI extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
                setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + -((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 200F;
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
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;

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
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            ((com.maddox.JGP.Tuple3d) (tmpV)).sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public CockpitHURRI()
    {
        super("3DO/Cockpit/HurricaneMkI/hier.him", "bf109");
        setOld = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        setNew = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "BORT2", "BORT4", "COMPASS", "prib_five_fin", "prib_five", "prib_four", "prib_one_fin", "prib_one", "prib_six", "prib_three", 
            "prib_two", "pricel"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        if(((com.maddox.il2.engine.Actor)this).acoustics != null)
            ((com.maddox.il2.engine.Actor)this).acoustics.globFX = new ReverbFXRoom(0.2F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampEngHeat", (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut > 105.5F) | (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut > 135.5F));
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.55F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FONAR2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("FONAR_GLASS2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage3", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("XGlassDamage4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearUpL", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 0.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearUpR", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 0.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearDownL", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 1.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XLampGearDownR", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() == 1.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RUSBase", 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RUS", -35F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("RUS_GUN", !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.WeaponControl[0]);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RUS_TORM", 0.0F, 0.0F, -40F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getBrake());
        com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.01625F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getAileron();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("RUS_TAND_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = -com.maddox.il2.objects.air.Cockpit.xyz[2];
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("RUS_TAND_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("RUD", 0.0F, -81.81F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl == 0.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() != 0.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = 0.05F;
            com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.0F;
        } else
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.GearControl == 1.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() != 1.0F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = -0.05F;
            com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.0F;
        } else
        if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl - ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getFlap()) > 0.02F)
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.FlapsControl - ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getFlap() > 0.0F)
            {
                com.maddox.il2.objects.air.Cockpit.xyz[0] = -0.05F;
                com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.0345F;
            } else
            {
                com.maddox.il2.objects.air.Cockpit.xyz[0] = 0.05F;
                com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.0345F;
            }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("SHAS_RUCH_T", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("PROP_CONTR", (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getStepControl() >= 0.0F ? ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getStepControl() : 1.0F - ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f)) * -60F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("PEDALY", 9F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("COMPASS_M", -((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("SHKALA_DIRECTOR", ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STREL_ALT_LONG", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -10800F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STREL_ALT_SHORT", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -1080F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STREL_ALT_SHRT1", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -108F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELKA_BOOST", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getManifoldPressure(), 0.7242097F, 1.620528F, -111.5F, 221F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELKA_FUEL", 0.0F, 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel, 0.0F, 307.7F, 0.0F, 60F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELK_FUEL_LB", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 1.0F ? 10F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 10F, 0.0F, 10F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELKA_RPM", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 1000F, 5000F, 2.0F, 10F), rpmScale));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELK_TEMP_OIL", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 40F, 100F, 0.0F, 270F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELK_TEMP_RAD", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale));
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = 0.05865F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlRadiator();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("zRadFlap", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getW())));
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STREL_TURN_DOWN", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, -48F, 48F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELK_TURN_UP", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(8D), -8F, 8F, 35F, -35F));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELK_V_SHORT", false);
        if(bFAF)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.objects.air.Cockpit)this).fm.getSpeed()), 0.0F, 143.0528F, 0.0F, 32F), speedometerScaleFAF));
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.objects.air.Cockpit)this).fm.getSpeed()), 0.0F, 180.0555F, 0.0F, 35F), speedometerScale));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELKA_VY", 0.0F, 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -20.32F, 20.32F, 0.0F, 8F), variometerScale));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELKA_GOR_FAF", 0.0F, 0.0F, ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren());
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -45F, 45F, 0.032F, -0.032F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("STRELKA_GOR_RAF", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("STRELKA_GOS_FAF", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELKA_HOUR", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELKA_MINUTE", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("STRELKA_SECUND", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_four", "prib_four_damage");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_four_night", "prib_four_damage_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_three", "prib_three_damage");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_three_night", "prib_three_damage_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELK_TEMP_OIL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELK_TEMP_RAD", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_BOOST", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_FUEL", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage4", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage2_RAF", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage2_FAF", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_three", "prib_three_damage");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_three_night", "prib_three_damage_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELK_TEMP_OIL", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELK_TEMP_RAD", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_BOOST", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_FUEL", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) == 0);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage6", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_two", "prib_two_damage");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_two_night", "prib_two_damage_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STREL_ALT_LONG", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STREL_ALT_SHORT", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_RPM", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage5", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage6", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage1", true);
            if(bFAF)
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage2_FAF", true);
            else
                ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("HullDamage2_RAF", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_one", "prib_one_damage");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_one_fin", "prib_one_fin_damage");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_one_night", "prib_one_damage_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("prib_one_fin_night", "prib_one_fin_damage_night");
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELK_V_LONG", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELK_V_SHORT", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_GOR_FAF", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_GOR_RAF", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_VY", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STREL_TURN_DOWN", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage2", true);
        }
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        if(((com.maddox.il2.objects.air.Cockpit)this).aircraft().getRegiment().country().equals("fi"))
        {
            bFAF = true;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("PRIBORY_RAF", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("PRIBORY_FAF", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_GOR_RAF", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_GOR_FAF", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_GOS_FAF", true);
        } else
        {
            bFAF = false;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("PRIBORY_RAF", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("PRIBORY_FAF", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_GOR_RAF", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_GOR_FAF", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("STRELKA_GOS_FAF", false);
        }
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D0o", mat);
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
    private boolean bFAF;
    private float pictAiler;
    private float pictElev;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 1.0F, 3F, 7.5F, 34.5F, 46F, 63F, 76F, 94F, 
        112.5F, 131F, 150F, 168.5F, 187F, 203F, 222F, 242.5F, 258.5F, 277F, 
        297F, 315.5F, 340F, 360F, 376.5F, 392F, 407F, 423.5F, 442F, 459F, 
        476F, 492.5F, 513F, 534.5F, 552F, 569.5F
    };
    private static final float speedometerScaleFAF[] = {
        0.0F, 0.0F, 2.0F, 6F, 21F, 40F, 56F, 72.5F, 89.5F, 114F, 
        135.5F, 157F, 182.5F, 205F, 227.5F, 246.5F, 265.5F, 286F, 306F, 326F, 
        345.5F, 363F, 385F, 401F, 414.5F, 436.5F, 457F, 479F, 496.5F, 515F, 
        539F, 559.5F, 576.5F
    };
    private static final float radScale[] = {
        0.0F, 3F, 7F, 13.5F, 21.5F, 27F, 34.5F, 50.5F, 71F, 94F, 
        125F, 161F, 202.5F, 253F, 315.5F
    };
    private static final float rpmScale[] = {
        0.0F, 0.0F, 0.0F, 22F, 58F, 103.5F, 152.5F, 193.5F, 245F, 281.5F, 
        311.5F
    };
    private static final float variometerScale[] = {
        -158F, -110F, -63.5F, -29F, 0.0F, 29F, 63.5F, 110F, 158F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;









}
