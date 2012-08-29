package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitFI_156 extends CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float throttle;
        float dimPosition;
        float azimuth;
        float waypointAzimuth;
        float mix;
        float vspeed;

        private Variables()
        {
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = fm.getAltitude();
            if(cockpitDimControl)
            {
                if(setNew.dimPosition > 0.0F)
                    setNew.dimPosition = setOld.dimPosition - 0.05F;
            } else
            if(setNew.dimPosition < 1.0F)
                setNew.dimPosition = setOld.dimPosition + 0.05F;
            setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
            setNew.mix = (8F * setOld.mix + fm.EI.engines[0].getControlMix()) / 9F;
            setNew.azimuth = fm.Or.getYaw();
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            buzzerFX(fm.CT.getGear() < 0.999999F && fm.CT.getFlap() > 0.0F);
            setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            return true;
        }

        Interpolater()
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
            return (float)(57.295779513082323D * java.lang.Math.atan2(tmpV.y, tmpV.x));
        }
    }

    public CockpitFI_156()
    {
        super("3DO/Cockpit/FI_156/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictManifold = 0.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        setNew.dimPosition = 1.0F;
        cockpitDimControl = !cockpitDimControl;
        cockpitNightMats = (new java.lang.String[] {
            "ZClocks1", "ZClocks1DMG", "ZClocks2", "ZClocks3", "FW190A4Compass", "oxigen"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ATA1", 15.5F + cvt(pictManifold = 0.75F * pictManifold + 0.25F * fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelQuantity1", -44.5F + floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 400F, 0.0F, 8F), fuelScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Iengtemprad1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 160F, 0.0F, 58.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_EngTemp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 58.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FuelPress1", cvt(fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        float f1;
        if(aircraft().isFMTrackMirror())
        {
            f1 = aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f1 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -3F, 3F, 30F, -30F);
            if(aircraft().fmTrack() != null)
                aircraft().fmTrack().setCockpitAzimuthSpeed(f1);
        }
        mesh.chunkSetAngles("Z_TurnBank1", f1, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(6D), -6F, 6F, -7F, 7F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zVSI", cvt(setNew.vspeed, -15F, 15F, -160F, 160F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Azimuth1", -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PropPitch1", 270F - (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PropPitch2", 105F - (float)java.lang.Math.toDegrees(fm.EI.engines[0].getPropPhi() - fm.EI.engines[0].getPropPhiMin()) * 5F, 0.0F, 0.0F);
        mesh.chunkVisible("Z_FuelRed1", fm.M.fuel < 36F);
        mesh.chunkVisible("Z_GearLRed1", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_GearRRed1", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_GearLGreen1", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_GearRGreen1", fm.CT.getGear() == 1.0F);
        mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Second1", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Z_PedalStrut", fm.CT.getRudder() * 15F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PedalStrut2", fm.CT.getRudder() * 15F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", -fm.CT.getRudder() * 15F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", -fm.CT.getRudder() * 15F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle", interp(setNew.throttle, setOld.throttle, f) * 57.72727F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle_D1", interp(setNew.throttle, setOld.throttle, f) * 27F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle_tube", -interp(setNew.throttle, setOld.throttle, f) * 57.72727F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mix", interp(setNew.mix, setOld.mix, f) * 70F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Mix_tros", -interp(setNew.mix, setOld.mix, f) * 70F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoSwitch", -45F + 28.333F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("CF_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("CF_D0", true);
        super.doFocusLeave();
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
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
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("Z_Throttle", false);
            mesh.chunkVisible("Z_Throttle_tube", false);
            mesh.chunkVisible("Z_Throttle_D1", true);
            mesh.chunkVisible("Z_Throttle_TD1", true);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
            if(type == 0 || type == 1)
                mesh.chunkVisible("Z_OilSplatE4_D1", true);
            else
                mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("Z_HullDamage3", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("Z_HullDamage2", true);
            if(type == 0 || type == 1)
                mesh.chunkVisible("Z_Holes2E4_D1", true);
            else
                mesh.chunkVisible("Z_Holes2_D1", true);
        }
    }

    protected void reflectPlaneMats()
    {
        if(com.maddox.il2.engine.Actor.isValid(fm.actor))
        {
            if(fm.actor instanceof FI_156)
                type = 0;
            switch(type)
            {
            case 0: // '\0'
                mesh.chunkVisible("Body", false);
                mesh.chunkVisible("BodyE4", true);
                mesh.chunkVisible("Top", false);
                mesh.chunkVisible("TopE4", true);
                mesh.chunkVisible("PanelE4_D0", true);
                mesh.chunkVisible("PanelE4B_D0", false);
                mesh.chunkVisible("PanelE7_D0", false);
                mesh.chunkVisible("oxigen-7", true);
                mesh.chunkVisible("oxigen-7z", false);
                break;
            }
        }
    }

    private float tmp;
    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictManifold;
    private boolean bNeedSetUp;
    private int type;
    private static final int TYPE_FI_156 = 0;
    private static final float speedometerScale[] = {
        0.0F, -12.33333F, 18.5F, 37F, 62.5F, 90F, 110.5F, 134F, 158.5F, 186F, 
        212.5F, 238.5F, 265F, 289.5F, 315F, 339.5F, 346F, 346F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 54F, 111F, 171.5F, 229.5F, 282.5F, 334F, 342.5F, 342.5F
    };
    private static final float fuelScale[] = {
        0.0F, 9F, 21F, 29.5F, 37F, 48F, 61.5F, 75.5F, 92F, 92F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;









}
