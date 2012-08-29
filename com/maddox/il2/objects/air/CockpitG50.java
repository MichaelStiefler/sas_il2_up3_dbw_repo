// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitG50.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, Cockpit

public class CockpitG50 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
            setNew.prop = (10F * setOld.prop + fm.EI.engines[0].getControlProp()) / 11F;
            setNew.mix = (10F * setOld.mix + fm.EI.engines[0].getControlMix()) / 11F;
            setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            setNew.azimuth = fm.Or.getYaw();
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float throttle;
        float prop;
        float mix;
        float azimuth;
        float waypointAzimuth;
        float vspeed;

        private Variables()
        {
        }

    }


    public CockpitG50()
    {
        super("3DO/Cockpit/G50/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        cockpitNightMats = (new java.lang.String[] {
            "texture07", "texture08", "texture09", "texture10", "texture11", "texture12", "texture15"
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
        mesh.chunkVisible("Z_GearRedL", fm.CT.getGear() == 0.0F || fm.Gears.isAnyDamaged());
        mesh.chunkVisible("Z_GearRedR", fm.CT.getGear() == 0.0F || fm.Gears.isAnyDamaged());
        mesh.chunkVisible("Z_GearGreenL", fm.CT.getGear() == 1.0F && fm.Gears.lgear);
        mesh.chunkVisible("Z_GearGreenR", fm.CT.getGear() == 1.0F && fm.Gears.rgear);
        mesh.chunkSetAngles("Z_Columnbase", 0.0F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Z_Column", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, 0.0F);
        mesh.chunkSetAngles("Z_Pedal1", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pedal2", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pedal3", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pedal4", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Gear", 50F * (pictGear = 0.85F * pictGear + 0.15F * fm.CT.GearControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Flap", 50F * (pictFlap = 0.85F * pictFlap + 0.15F * fm.CT.FlapsControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter1", -cvt(fm.getAltitude(), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", -cvt(fm.getAltitude(), 0.0F, 20000F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", -60F - interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hour1", 0.0F, 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F));
        mesh.chunkSetAngles("Z_Minute1", 0.0F, 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F));
        mesh.chunkSetAngles("Z_Secund1", 0.0F, 0.0F, cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F));
        mesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 600F, 0.0F, 12F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer2", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 600F, 0.0F, 12F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 70F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prop1", 70F * interp(setNew.prop, setOld.prop, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mixture1", 70F * interp(setNew.mix, setOld.mix, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", -cvt(fm.EI.engines[0].getRPM(), 0.0F, 3000F, 0.0F, 316F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(getBall(8D), -8F, 8F, 9F, -9F), 0.0F, 0.0F);
        float f1;
        if(aircraft().isFMTrackMirror())
        {
            f1 = aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f1 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -9F, 9F, -24F, 24F);
            if(aircraft().fmTrack() != null)
                aircraft().fmTrack().setCockpitAzimuthSpeed(f1);
        }
        mesh.chunkSetAngles("Z_TurnBank2", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", cvt(setNew.vspeed, -25F, 25F, 180F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.6F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, -300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oiltemp1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 150F, 0.0F, -300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, -74F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Prescockp1", -floatindex(cvt(fm.EI.engines[0].getManifoldPressure(), 0.533288F, 1.33322F, 0.0F, 6F), manifoldScale), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(gun[0].countBullets(), 0.0F, 150F, 0.0F, 0.06025F);
        mesh.chunkSetLocate("Z_AmmoCounter1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(gun[1].countBullets(), 0.0F, 150F, 0.0F, 0.06025F);
        mesh.chunkSetLocate("Z_AmmoCounter2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x80) != 0)
        {
            mesh.chunkVisible("Front", false);
            mesh.chunkVisible("Front_D1", true);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("Pricel_D0", false);
            mesh.chunkVisible("Pricel_D1", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 0x40) == 0);
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XHullDamage1", true);
        if((fm.AS.astateCockpitState & 8) == 0);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XHullDamage2", true);
        if((fm.AS.astateCockpitState & 0x20) == 0);
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private float pictAiler;
    private float pictElev;
    private float pictFlap;
    private float pictGear;
    private float pictRadiator;
    private float pictManifold;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null
    };
    private static final float speedometerScale[] = {
        0.0F, 6F, 27.5F, 66F, 108F, 146.5F, 183.1F, 217.5F, 251F, 281.5F, 
        310.5F, 388F, 394F
    };
    private static final float manifoldScale[] = {
        0.0F, 56F, 111F, 166.5F, 220.5F, 276F, 327.5F
    };







}
