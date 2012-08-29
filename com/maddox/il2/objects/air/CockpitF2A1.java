// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitF2A1.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitF2A1 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
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

    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, fm.Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(-tmpV.y, tmpV.x));
        }
    }

    public CockpitF2A1()
    {
        super("3DO/Cockpit/F2A-1/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "F2ABoxes", "F2Acables", "F2AWindshields", "F2Agauges1", "F2Agauges3", "F2Agauges", "F2Azegary4"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN02");
        }
        mesh.chunkVisible("XLampGear_1", !fm.Gears.lgear || !fm.Gears.lgear);
        mesh.chunkSetAngles("Z_Gear", 32F * fm.CT.getGear(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flap", 48F * fm.CT.getFlap(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Pedal_L", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal1_L", 0.0F, (fm.CT.getRudder() <= 0.0F ? 25F : 30F) * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal2_L", 0.0F, (fm.CT.getRudder() <= 0.0F ? 20F : 25F) * fm.CT.getBrake(), 0.0F);
        mesh.chunkSetAngles("Pedal_R", 0.0F, -20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal1_R", 0.0F, (fm.CT.getRudder() >= 0.0F ? -25F : -30F) * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Pedal2_R", 0.0F, (fm.CT.getRudder() >= 0.0F ? -20F : -25F) * fm.CT.getBrake(), 0.0F);
        mesh.chunkSetAngles("Columnbase", 0.0F, -10F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F);
        mesh.chunkSetAngles("Column", 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 0.0F);
        mesh.chunkSetAngles("ColumnL", 0.0F, 10F * pictAiler, 0.0F);
        mesh.chunkSetAngles("ColumnR", 0.0F, -10F * pictAiler, 0.0F);
        mesh.chunkSetAngles("Z_Manifold", fm.EI.engines[0].getManifoldPressure() >= 0.399966F ? cvt(fm.EI.engines[0].getManifoldPressure(), 0.399966F, 1.599864F, 34F, 326F) : cvt(fm.EI.engines[0].getManifoldPressure(), 0.0F, 0.399966F, 0.0F, 34F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Alt_Large", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 21600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Alt_Small", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 2160F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speed", com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeed()) >= 41.15554F ? cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeed()), 41.15554F, 246.9333F, 30F, 340F) : cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeed()), 0.0F, 41.15554F, 0.0F, 30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass", 90F + interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Magn_Compas", 90F + interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Comp_Handle", cvt(interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), -45F, 45F, -20F, 20F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Bank", cvt(getBall(8D), -8F, 8F, 14F, -14F), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_Turn", cvt(w.z, -0.23562F, 0.23562F, 34F, -34F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb", cvt(setNew.vspeed, -20F, 20F, -180F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Clock_H", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Clock_Min", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        if(gun[0] != null && gun[0].haveBullets())
        {
            mesh.chunkSetAngles("Z_Ammo_W1", 0.36F * (float)gun[0].countBullets(), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Ammo_W2", 3.6F * (float)gun[0].countBullets(), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Ammo_W3", 36F * (float)gun[0].countBullets(), 0.0F, 0.0F);
        }
        if(gun[1] != null && gun[1].haveBullets())
        {
            mesh.chunkSetAngles("Z_Ammo_W4", 0.36F * (float)gun[1].countBullets(), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Ammo_W5", 3.6F * (float)gun[1].countBullets(), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Ammo_W6", 36F * (float)gun[1].countBullets(), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_Temp_Handle", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 400F, 0.0F, 70F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp_Eng", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil_Eng", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel_Eng", cvt(fm.M.fuel <= 1.0F ? 0.0F : 10F * fm.EI.engines[0].getPowerOutput(), 0.0F, 20F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Tahometr_Eng", cvt(fm.EI.engines[0].getRPM(), 0.0F, 2000F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Ign_Switch", 0.0F, fm.EI.engines[0].getStage() != 0 ? -60F : 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel_1", cvt(fm.M.fuel, 0.0F, 504F, 0.0F, 155F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel_2", cvt(fm.M.fuel, 0.0F, 504F, 0.0F, 155F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Rudder_1", 0.0F, -60F * fm.CT.getTrimRudderControl(), 0.0F);
        mesh.chunkSetAngles("Z_Elevator_2", 0.0F, -60F * fm.CT.getTrimElevatorControl(), 0.0F);
        mesh.chunkSetAngles("Z_Tail_W_Lock", 0.0F, fm.Gears.bTailwheelLocked ? -40F : 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Booster_Level", 0.0F, -38F * fm.EI.engines[0].getControlMix(), 0.0F);
        mesh.chunkSetAngles("Z_Throttle", 0.0F, -77.27F * fm.EI.engines[0].getControlThrottle(), 0.0F);
        mesh.chunkSetAngles("Z_Pitch", 0.0F, -68F * fm.EI.engines[0].getControlProp(), 0.0F);
        mesh.chunkSetAngles("Z_Flaps_Lever", 0.0F, -45F * fm.CT.FlapsControl, 0.0F);
        mesh.chunkSetAngles("Z_Gear_Lever", 0.0F, -45F * fm.CT.GearControl, 0.0F);
        mesh.chunkSetAngles("Z_Hor_Handle", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.019F, -0.019F);
        mesh.chunkSetLocate("Z_Hor_Handle2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("Revi_D0", false);
            mesh.chunkVisible("Revi_D1", true);
            mesh.chunkVisible("Z_Hullhole_3", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Bullethole_1", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("Z_Manifold", false);
            mesh.chunkVisible("Z_Speed", false);
            mesh.chunkVisible("Z_Alt_Large", false);
            mesh.chunkVisible("Z_Alt_Small", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("Z_Bullethole_2", true);
            mesh.chunkVisible("Z_Hullhole_1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("Z_Hullhole_2", true);
            mesh.chunkVisible("Z_Hullhole_3", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("Z_Bullethole_2", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("Z_Hullhole_1", true);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null
    };
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;






}
