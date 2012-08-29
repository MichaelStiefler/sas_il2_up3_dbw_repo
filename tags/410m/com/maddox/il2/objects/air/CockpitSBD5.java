// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitSBD5.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
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
//            CockpitPilot

public class CockpitSBD5 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.flaps = 0.9F * setOld.flaps + 0.1F * fm.CT.FlapsControl;
                setNew.gear = 0.7F * setOld.gear + 0.3F * fm.CT.GearControl;
                setNew.throttle = 0.8F * setOld.throttle + 0.2F * fm.CT.PowerControl;
                setNew.prop = 0.8F * setOld.prop + 0.2F * fm.EI.engines[0].getControlProp();
                setNew.mix = 0.8F * setOld.mix + 0.2F * fm.EI.engines[0].getControlMix();
                setNew.divebrake = 0.8F * setOld.divebrake + 0.2F * fm.CT.AirBrakeControl;
                setNew.man = 0.92F * setOld.man + 0.08F * fm.EI.engines[0].getManifoldPressure();
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = (399F * setOld.vspeed + fm.getVertSpeed()) / 400F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float flaps;
        float gear;
        float throttle;
        float prop;
        float mix;
        float divebrake;
        float altimeter;
        float man;
        float vspeed;
        com.maddox.il2.ai.AnglesFork azimuth;

        private Variables()
        {
            azimuth = new AnglesFork();
        }

    }


    public CockpitSBD5()
    {
        super("3DO/Cockpit/SBD-5/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        cockpitNightMats = (new java.lang.String[] {
            "GagePanel1", "GagePanel1_D1", "GagePanel2", "GagePanel2_D1", "GagePanel3", "GagePanel3_D1", "GagePanel4", "GagePanel4_D1", "misc2"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        resetYPRmodifier();
        xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.7F);
        mesh.chunkSetLocate("Canopy", xyz, ypr);
        mesh.chunkSetAngles("Z_Trim1", 0.0F, 1444F * fm.CT.getTrimAileronControl(), 0.0F);
        mesh.chunkSetAngles("Z_Trim2", 0.0F, 1444F * fm.CT.getTrimRudderControl(), 0.0F);
        mesh.chunkSetAngles("Z_Trim3", 0.0F, 1444F * fm.CT.getTrimElevatorControl(), 0.0F);
        mesh.chunkSetAngles("Z_Flaps1", 0.0F, -77F * setNew.flaps, 0.0F);
        mesh.chunkSetAngles("Z_Gear1", 0.0F, -77F * setNew.gear, 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 0.0F, -40F * setNew.throttle, 0.0F);
        mesh.chunkSetAngles("Z_Prop1", 0.0F, -68F * setNew.prop, 0.0F);
        mesh.chunkSetAngles("Z_Mixture1", 0.0F, -55F * setNew.mix, 0.0F);
        mesh.chunkSetAngles("Z_Supercharger1", 0.0F, -55F * (float)fm.EI.engines[0].getControlCompressor(), 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 20F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_Columnbase", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 8F, 0.0F);
        mesh.chunkSetAngles("Z_Column", 0.0F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 8F, 0.0F);
        mesh.chunkSetAngles("Z_DiveBrake1", 0.0F, -77F * setNew.divebrake, 0.0F);
        mesh.chunkSetAngles("Z_CowlFlap1", 0.0F, -28F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        if(fm.CT.Weapons[3] != null && !fm.CT.Weapons[3][0].haveBullets())
            mesh.chunkSetAngles("Z_Bomb1", 0.0F, 35F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, -3600F), 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, -36000F), 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", 0.0F, -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 925.9998F, 0.0F, 10F), speedometerScale), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 22F, -22F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(6D), -6F, 6F, -16F, 16F), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank3", 0.0F, -fm.Or.getKren(), 0.0F);
        resetYPRmodifier();
        xyz[2] = cvt(fm.Or.getTangage(), -45F, 45F, 0.025F, -0.025F);
        mesh.chunkSetLocate("Z_TurnBank4", xyz, ypr);
        mesh.chunkSetAngles("Z_Climb1", 0.0F, setNew.vspeed < 0.0F ? floatindex(cvt(-setNew.vspeed / 5.08F, 0.0F, 6F, 0.0F, 12F), variometerScale) : -floatindex(cvt(setNew.vspeed / 5.08F, 0.0F, 6F, 0.0F, 12F), variometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, -setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Z_Compass2", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, -360F), 0.0F);
        mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, -360F), 0.0F);
        mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(fm.M.fuel, 0.0F, 400F, 0.0F, -87F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel2", 0.0F, cvt(fm.M.fuel, 0.0F, 400F, 0.0F, -87F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel3", 0.0F, cvt(fm.M.fuel, 0.0F, 400F, 0.0F, -87F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel4", 0.0F, cvt(fm.M.fuel, 0.0F, 400F, 0.0F, -87F), 0.0F);
        mesh.chunkSetAngles("Z_FuelPres1", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.3F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, -74F), 0.0F);
        mesh.chunkSetAngles("Z_Temp2", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("Z_Pres1", 0.0F, cvt(setNew.man, 0.3386378F, 2.539784F, 0.0F, -344F), 0.0F);
        mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 200F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -301F), 0.0F);
        float f1 = fm.EI.engines[0].getRPM();
        f1 = 2.5F * (float)java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(f1))));
        mesh.chunkSetAngles("Z_Suction1", 0.0F, cvt(f1, 0.0F, 10F, 0.0F, -300F), 0.0F);
        if(fm.Gears.lgear)
        {
            resetYPRmodifier();
            xyz[0] = -0.133F * fm.CT.getGear();
            mesh.chunkSetLocate("Z_GearL1", xyz, ypr);
        }
        if(fm.Gears.rgear)
        {
            resetYPRmodifier();
            xyz[0] = -0.133F * fm.CT.getGear();
            mesh.chunkSetLocate("Z_GearR1", xyz, ypr);
        }
        resetYPRmodifier();
        xyz[0] = -0.118F * fm.CT.getFlap();
        mesh.chunkSetLocate("Z_Flap1", xyz, ypr);
        mesh.chunkSetAngles("Z_EnginePrim", 0.0F, fm.EI.engines[0].getStage() <= 0 ? 0.0F : -36F, 0.0F);
        mesh.chunkSetAngles("Z_MagSwitch", 0.0F, cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, -111F), 0.0F);
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

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 16.5F, 79.5F, 143F, 206.5F, 229.5F, 251F, 272.5F, 294F, 316F, 
        339.5F
    };
    private static final float variometerScale[] = {
        0.0F, 25F, 49.5F, 64F, 78.5F, 89.5F, 101F, 112F, 123F, 134.5F, 
        145.5F, 157F, 168F, 168F
    };







}
