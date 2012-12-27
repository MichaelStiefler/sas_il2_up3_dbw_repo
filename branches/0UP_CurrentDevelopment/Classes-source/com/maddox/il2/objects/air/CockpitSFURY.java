// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitSFURY.java

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
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
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

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitSFURY extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle = 0.92F * setOld.throttle + 0.08F * ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl;
                setNew.prop = 0.92F * setOld.prop + 0.08F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlProp();
                setNew.mix = 0.92F * setOld.mix + 0.08F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix();
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth = 0.97F * setOld.azimuth + 0.03F * -((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getYaw();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = 0.91F * setOld.waypointAzimuth + 0.09F * (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F);
                setNew.vspeed = 0.99F * setOld.vspeed + 0.01F * fm.getVertSpeed();
                if(((com.maddox.il2.fm.FlightModelMain) (fm)).CT.GearControl < 0.5F)
                {
                    if(setNew.gearPhi > 0.0F)
                        setNew.gearPhi = setOld.gearPhi - 0.021F;
                } else
                if(setNew.gearPhi < 1.0F)
                    setNew.gearPhi = setOld.gearPhi + 0.021F;
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
        float prop;
        float mix;
        float altimeter;
        float azimuth;
        float vspeed;
        float gearPhi;
        float waypointAzimuth;

        private Variables()
        {
        }

    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public CockpitSFURY()
    {
        super("3DO/Cockpit/Seafury/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictBrake = 0.0F;
        pictFlap = 0.0F;
        pictSupc = 0.0F;
        pictLlit = 0.0F;
        pictManf = 1.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        super.cockpitNightMats = (new java.lang.String[] {
            "TEMPPIT5-op", "TEMPPIT6-op", "TEMPPIT14-op", "TEMPPIT18-op", "TEMPPIT22-op", "TEMPPIT28-op", "TEMPPIT38-op", "TEMPPIT1-tr", "TEMPPIT2-tr", "TEMPPIT3-tr", 
            "TEMPPIT4-tr", "TEMPPIT5-tr", "TEMPPIT6-tr", "TEMPPIT14-tr", "TEMPPIT18-tr", "TEMPPIT22-tr", "TEMPPIT28-tr", "TEMPPIT38-tr", "TEMPPIT1_damage", "TEMPPIT3_damage"
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
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.62F);
        super.mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkVisible("XLampGearUpR", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.01F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.99F || !((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkVisible("XLampGearUpL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.01F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.99F || !((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
        super.mesh.chunkVisible("XLampGearUpC", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.01F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.99F);
        super.mesh.chunkVisible("XLampGearDownR", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkVisible("XLampGearDownL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear);
        super.mesh.chunkVisible("XLampGearDownC", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F);
        super.mesh.chunkSetAngles("Z_Columnbase", 16F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Column", 45F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Elev", -16F * pictElev, 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = cvt(pictAiler, -1F, 1.0F, -0.027F, 0.027F);
        super.mesh.chunkSetLocate("Z_Shlang01", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        com.maddox.il2.objects.air.Cockpit.xyz[2] = -com.maddox.il2.objects.air.Cockpit.xyz[2];
        super.mesh.chunkSetLocate("Z_Shlang02", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_Throtle1", 65.45F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_BasePedal", 20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 0.0F, -20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder());
        super.mesh.chunkSetAngles("Z_RightPedal", 0.0F, 0.0F, -20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder());
        super.mesh.chunkSetAngles("Z_Gear1", cvt(setNew.gearPhi, 0.2F, 0.8F, 0.0F, 116F), 0.0F, 0.0F);
        if(setNew.gearPhi < 0.5F)
            super.mesh.chunkSetAngles("Z_Gear2", cvt(setNew.gearPhi, 0.0F, 0.2F, 0.0F, -65F), 0.0F, 0.0F);
        else
            super.mesh.chunkSetAngles("Z_Gear2", cvt(setNew.gearPhi, 0.8F, 1.0F, -65F, 0.0F), 0.0F, 0.0F);
        float f1;
        if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap()) > 0.02F)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() > 0.0F)
                f1 = 24F;
            else
                f1 = -24F;
        } else
        {
            f1 = 0.0F;
        }
        super.mesh.chunkSetAngles("Z_Flaps1", pictFlap = 0.8F * pictFlap + 0.2F * f1, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Trim1", 1000F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Prop1", 72.5F * setNew.prop, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Surch1", cvt(pictSupc = 0.8F * pictSupc + 0.1F * (float)((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlCompressor(), 0.0F, 1.0F, 0.0F, 60F), 0.0F, 0.0F);
        f1 = 0.0F;
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.bLandingLightOn)
            f1 = 66F;
        super.mesh.chunkSetAngles("Z_Land1", pictLlit = 0.85F * pictLlit + 0.15F * f1, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("COMPASS_M", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELK_V_LONG", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeed()), 0.0F, 257.2222F, 0.0F, 10F), speedometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STREL_ALT_LONG", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -10800F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STREL_ALT_SHORT", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -1080F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("SHKALA_DIRECTOR", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_VY", -floatindex(cvt(setNew.vspeed, -20.32F, 20.32F, 0.0F, 8F), variometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_BOOST", cvt(pictManf = 0.91F * pictManf + 0.09F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.7242097F, 2.103161F, 60F, -240F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 88.38F, 350.23F, 0.0F, -306F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL2", cvt((float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel), 0.0F, (float)java.lang.Math.sqrt(88.379997253417969D), 0.0F, -317F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL3", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 88.38F, 170.2F, 0.0F, -311F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL4", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 88.38F, 170.2F, 0.0F, -311F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_RPM", -floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 5000F, 0.0F, 10F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELK_TEMP_OIL", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 160F, 0.0F, -306F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELK_TEMP_RAD", -floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STR_OIL_LB", cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, -36F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELK_TURN_UP", -cvt(getBall(8D), -8F, 8F, 35F, -35F), 0.0F, 0.0F);
        w.set(super.fm.getW());
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.transform(w);
        super.mesh.chunkSetAngles("STREL_TURN_DOWN", -cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, -48F, 48F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_GOR", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 0.022F, -0.022F);
        super.mesh.chunkSetLocate("STRELKA_GOS", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("STRELKA_HOUR", -cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_MINUTE", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_SECUND", -cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
            super.mesh.chunkVisible("Z_OilSplats_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
            super.mesh.chunkVisible("XGlassDamage4", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0)
            super.mesh.chunkVisible("XGlassDamage2", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
            super.mesh.chunkVisible("XGlassDamage3", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
            super.mesh.chunkVisible("XGlassDamage2", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0)
        {
            super.mesh.chunkVisible("Panel_D0", false);
            super.mesh.chunkVisible("Panel_D1", true);
            super.mesh.chunkVisible("STRELK_V_LONG", false);
            super.mesh.chunkVisible("STREL_ALT_LONG", false);
            super.mesh.chunkVisible("STREL_ALT_SHORT", false);
            super.mesh.chunkVisible("STRELKA_VY", false);
            super.mesh.chunkVisible("STRELKA_RPM", false);
            super.mesh.chunkVisible("STRELK_TEMP_RAD", false);
            super.mesh.chunkVisible("STRELK_TEMP_OIL", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
            super.mesh.chunkVisible("XGlassDamage2", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
            super.mesh.chunkVisible("XGlassDamage1", true);
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        super.mesh.materialReplace("Gloss1D0o", mat);
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

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictBrake;
    private float pictFlap;
    private float pictSupc;
    private float pictLlit;
    private float pictManf;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 15.5F, 76F, 153.5F, 234F, 304F, 372.5F, 440F, 504F, 566F, 
        630F
    };
    private static final float radScale[] = {
        0.0F, 3F, 7F, 13.5F, 30.5F, 40.5F, 51.5F, 68F, 89F, 114F, 
        145.5F, 181F, 222F, 270.5F, 331.5F
    };
    private static final float rpmScale[] = {
        0.0F, 15F, 32F, 69.5F, 106.5F, 143F, 180F, 217.5F, 253F, 290F, 
        327.5F
    };
    private static final float variometerScale[] = {
        -158F, -111F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111F, 158F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}