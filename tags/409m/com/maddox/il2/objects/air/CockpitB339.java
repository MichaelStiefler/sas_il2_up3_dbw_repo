// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitB339.java

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
import com.maddox.il2.fm.Atmosphere;
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
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitB339 extends com.maddox.il2.objects.air.CockpitPilot
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

    public CockpitB339()
    {
        super("3DO/Cockpit/B-339/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "F2ABoxes", "F2Acables", "F2Agauges", "F2Agauges1", "F2Agauges3", "F2AWindshields", "F2Azegary4"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)fm.actor).getGunByHookName("_MGUN02");
        }
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.725F);
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Columnbase", 0.0F, 25F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 0.0F);
        mesh.chunkSetAngles("Z_Column", 0.0F, 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(pictAiler, -1F, 1.0F, -0.054F, 0.054F);
        mesh.chunkSetLocate("Z_ColumnR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetLocate("Z_ColumnL", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.CT.getRudder(), -1F, 1.0F, -0.0575F, 0.0575F);
        mesh.chunkSetLocate("Z_Pedal_L", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.CT.getRudder(), -1F, 1.0F, 0.0575F, -0.0575F);
        mesh.chunkSetLocate("Z_Pedal_R", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        if(fm.EI.engines[0].getStage() > 0 && fm.EI.engines[0].getStage() < 3)
            com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.02825F;
        mesh.chunkSetLocate("Z_Starter", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Ign_Switch", 0.0F, -20F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F);
        mesh.chunkSetAngles("Z_Throttle", 0.0F, 100F * (pictThtl = 0.9F * pictThtl + 0.1F * fm.EI.engines[0].getControlThrottle()), 0.0F);
        mesh.chunkSetAngles("Z_mixture", 0.0F, 91.66667F * (pictMix = 0.9F * pictMix + 0.1F * fm.EI.engines[0].getControlMix()), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(pictProp = 0.9F * pictProp + 0.1F * fm.EI.engines[0].getControlProp(), 0.0F, 1.0F, 0.0F, -0.035F);
        mesh.chunkSetLocate("Z_Pitch", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Booster_Lever", 0.0F, -5F * (float)fm.EI.engines[0].getControlCompressor(), 0.0F);
        mesh.chunkSetAngles("Z_TL_lock", 0.0F, fm.Gears.bTailwheelLocked ? -30F : 0.0F, 0.0F);
        if(fm.CT.FlapsControl == 0.0F && fm.CT.getFlap() != 0.0F)
            mesh.chunkSetAngles("Z_Flaps", 0.0F, 0.0F, 0.0F);
        else
        if(fm.CT.FlapsControl == 1.0F && fm.CT.getFlap() != 1.0F)
            mesh.chunkSetAngles("Z_Flaps", 0.0F, -70F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Flaps", 0.0F, -35F, 0.0F);
        if(fm.CT.GearControl == 0.0F && fm.CT.getGear() != 0.0F)
            mesh.chunkSetAngles("Z_gearlever", 0.0F, 4F, 0.0F);
        else
        if(fm.CT.GearControl == 1.0F && fm.CT.getGear() != 1.0F)
            mesh.chunkSetAngles("Z_gearlever", 0.0F, -70F, 0.0F);
        else
            mesh.chunkSetAngles("Z_gearlever", 0.0F, -35F, 0.0F);
        mesh.chunkSetAngles("Z_Compass", 90F + interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.CT.getGear(), 0.0F, 1.0F, 0.0F, -0.135F);
        mesh.chunkSetLocate("Z_Gear", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.CT.getFlap(), 0.0F, 1.0F, -0.04155F, 0.0211F);
        mesh.chunkSetLocate("Z_Flap", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        if(gun[0] != null && gun[0].haveBullets())
        {
            mesh.chunkSetAngles("Z_Ammo_W1", 0.0F, -0.36F * (float)gun[0].countBullets(), 0.0F);
            mesh.chunkSetAngles("Z_Ammo_W2", 0.0F, -3.6F * (float)gun[0].countBullets(), 0.0F);
            mesh.chunkSetAngles("Z_Ammo_W3", 0.0F, -36F * (float)gun[0].countBullets(), 0.0F);
        }
        if(gun[1] != null && gun[1].haveBullets())
        {
            mesh.chunkSetAngles("Z_Ammo_W4", 0.0F, -0.36F * (float)gun[1].countBullets(), 0.0F);
            mesh.chunkSetAngles("Z_Ammo_W5", 0.0F, -3.6F * (float)gun[1].countBullets(), 0.0F);
            mesh.chunkSetAngles("Z_Ammo_W6", 0.0F, -36F * (float)gun[1].countBullets(), 0.0F);
        }
        mesh.chunkSetAngles("Z_Manifold", cvt(fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 1.693189F, 20F, 340F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Alt_Large", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 10800F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Alt_Small", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 1080F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speed", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 981.5598F, 0.0F, 53F), speedometerScale), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_Turn", cvt(w.z, -0.23562F, 0.23562F, 22.5F, -22.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Bank", cvt(getBall(8D), -8F, 8F, 12F, -12F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb", cvt(setNew.vspeed, -20F, 20F, -180F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Clock_Min", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Clock_H", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hor_Handle", -fm.Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.019F, -0.019F);
        mesh.chunkSetLocate("Z_Hor_Handle2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Temp_Handle", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 400F, 0.0F, 100F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp_Eng", cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 170F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil_Eng", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel_Eng", cvt(fm.M.fuel <= 1.0F ? 0.0F : 10F * fm.EI.engines[0].getPowerOutput(), 0.0F, 20F, 0.0F, 180F), 0.0F, 0.0F);
        if(fm.EI.engines[0].getRPM() < 1000F)
            mesh.chunkSetAngles("Z_Tahometr_Eng", cvt(fm.EI.engines[0].getRPM(), 0.0F, 1000F, 0.0F, 90F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Tahometr_Eng", cvt(fm.EI.engines[0].getRPM(), 1000F, 3500F, 90F, 540F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_carbmixtemp", cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z), 213.09F, 333.09F, -180F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel_1", cvt(fm.M.fuel, 0.0F, 504F, 0.0F, -272.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel_2", cvt(fm.M.fuel, 0.0F, 504F, 0.0F, -272.5F), 0.0F, 0.0F);
        if(fm.Gears.isHydroOperable())
            mesh.chunkSetAngles("Z_hydropress", 133F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_hydropress", 1.0F, 0.0F, 0.0F);
        mesh.chunkVisible("XLampGear_1", !fm.Gears.lgear || !fm.Gears.lgear);
    }

    public void reflectCockpitState()
    {
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
    private float pictThtl;
    private float pictMix;
    private float pictProp;
    private static final float speedometerScale[] = {
        0.0F, 0.5F, 1.0F, 2.0F, 5.5F, 14F, 20F, 26F, 33.5F, 42F, 
        50.5F, 60.5F, 71.5F, 81.5F, 95.2F, 108.5F, 122.5F, 137F, 152F, 166.7F, 
        182F, 198F, 214.5F, 231F, 247.5F, 263.5F, 278.5F, 294F, 307F, 317F, 
        330.5F, 343F, 355.5F, 367.5F, 379.5F, 391.5F, 404F, 417F, 430.5F, 444F, 
        458F, 473.5F, 487.5F, 503.5F, 519.5F, 535.5F, 552F, 569.5F, 586F, 602.5F, 
        619F, 631.5F, 643F, 648.5F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
