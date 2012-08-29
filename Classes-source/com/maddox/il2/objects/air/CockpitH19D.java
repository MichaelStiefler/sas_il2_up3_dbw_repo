// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitH19D.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitH19D extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float throttle;
        float prop;
        float mix;
        float stage;
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;
        float radioalt;
        float dimPosition;

        private Variables()
        {
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.throttle = 0.85F * setOld.throttle + fm.CT.PowerControl * 0.15F;
                setNew.prop = 0.85F * setOld.prop + fm.CT.getStepControl() * 0.15F;
                setNew.stage = 0.85F * setOld.stage + (float)fm.EI.engines[0].getControlCompressor() * 0.15F;
                setNew.mix = 0.85F * setOld.mix + fm.EI.engines[0].getControlMix() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + waypointAzimuth() + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                com.maddox.il2.objects.air.Variables variables = setNew;
                float f = 0.5F * setOld.radioalt;
                float f1 = 0.5F;
                float f2 = fm.getAltitude();
                com.maddox.il2.ai.World.cur();
                com.maddox.il2.ai.World.land();
                variables.radioalt = f + f1 * (f2 - com.maddox.il2.engine.Landscape.HQ_Air((float)fm.Loc.x, (float)fm.Loc.y));
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
            }
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
            return (float)(57.295779513082323D * java.lang.Math.atan2(-tmpV.y, tmpV.x));
        }
    }

    public CockpitH19D()
    {
        super("3DO/Cockpit/H19D/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        rotorrpm = 0;
        pictGear = 0.0F;
        pictFlap = 0.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "Instrumentos001", "Instrumentos002", "Instrumentos003"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        setNew.dimPosition = 1.0F;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("Z_Colectivo", 0.0F, 0.0F, -30F * setNew.throttle);
        mesh.chunkSetAngles("Z_Gases", 0.0F, 200F * setNew.throttle, 0.0F);
        mesh.chunkSetAngles("Z_Gases2", 0.0F, 200F * setNew.throttle, 0.0F);
        mesh.chunkSetAngles("Z_Palanca", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * -10F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * -10F);
        mesh.chunkSetAngles("Z_Palanca2", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * -10F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * -10F);
        mesh.chunkSetAngles("Z_Pedal_D", 0.0F, 0.0F, -15F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_Pedal_I", 0.0F, 0.0F, 15F * fm.CT.getRudder());
        mesh.chunkSetAngles("Z_Radiador", 0.0F, 0.0F, -60F * fm.EI.engines[0].getControlRadiator());
        if((double)fm.CT.PowerControl <= 0.11D)
            mesh.chunkSetAngles("Z_clutch", 0.0F, 0.0F, 700F * fm.CT.PowerControl);
        mesh.chunkSetAngles("Z_Magneto", 0.0F, 20F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F);
        mesh.chunkVisible("A_Fuel1", fm.M.fuel < 52F);
        mesh.chunkVisible("A_Fuel2", fm.M.fuel < 26F);
        resetYPRmodifier();
        if(fm.EI.engines[0].getStage() > 0 && fm.EI.engines[0].getStage() < 3)
            com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.01F;
        mesh.chunkSetLocate("Z_Starter", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkVisible("Z_Starter2", fm.EI.engines[0].getStage() > 0 && fm.EI.engines[0].getStage() < 3);
        mesh.chunkVisible("A_EngFire", fm.AS.astateEngineStates[0] > 2);
        if(fm.CT.bHasArrestorControl)
        {
            mesh.chunkVisible("Z_Flotadores_A", true);
            mesh.chunkVisible("Z_Flotadores_D", false);
            mesh.chunkVisible("A_Foats_A", true);
        }
        mesh.chunkVisible("A_Foats_R", fm.CT.getArrestor() > 0.05F);
        mesh.chunkSetAngles("Mira", 0.0F, cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -90F), 0.0F);
        mesh.chunkVisible("Reticulo", setNew.dimPosition < 0.5F);
        mesh.chunkSetAngles("Z_Altimetro2", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F);
        mesh.chunkSetAngles("Z_Altimetro1", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("Z_Altimetro4", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F);
        mesh.chunkSetAngles("Z_Altimetro3", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("Z_ASI1", 0.0F, -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("Z_ASI2", 0.0F, -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14F), speedometerScale), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_Horizonte1", 0.0F, fm.Or.getKren(), 0.0F);
        mesh.chunkSetAngles("Z_Horizonte2", 0.0F, 0.0F, -fm.Or.getTangage());
        mesh.chunkSetAngles("Z_Horizonte3", 0.0F, fm.Or.getKren(), 0.0F);
        mesh.chunkSetAngles("Z_Horizonte4", 0.0F, 0.0F, -fm.Or.getTangage());
        mesh.chunkSetAngles("Z_Compass1", 0.0F, -90F + -setNew.azimuth, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", 0.0F, -90F + -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Compass3", 0.0F, -90F + -setNew.azimuth, 0.0F);
        mesh.chunkSetAngles("Z_Compass4", 0.0F, -90F + -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Compass5", 0.0F, -90F + -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Compass6", 0.0F, -90F + -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F);
        mesh.chunkSetAngles("Z_Variometro1", 0.0F, -floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F);
        mesh.chunkSetAngles("Z_Variometro2", 0.0F, -floatindex(cvt(setNew.vspeed, -30.48F, 30.48F, 0.0F, 12F), variometerScale), 0.0F);
        rotorrpm = java.lang.Math.abs((int)((double)(fm.EI.engines[0].getw() * 1.0F) + fm.Vwld.length() * 4D));
        if(rotorrpm >= 250)
            rotorrpm = 250;
        if(rotorrpm <= 40)
            rotorrpm = 0;
        mesh.chunkSetAngles("Z_RotorRPM", 0.0F, -rotorrpm, 0.0F);
        mesh.chunkSetAngles("Z_EngineRPM", 0.0F, -cvt(fm.EI.engines[0].getRPM(), 0.0F, 5000F, 0.0F, 504F), 0.0F);
        mesh.chunkSetAngles("Z_Temp1", 0.0F, -cvt(fm.EI.engines[0].tOilOut, 20F, 120F, 0.0F, 210F), 0.0F);
        mesh.chunkSetAngles("Z_Oil1", 0.0F, -cvt(fm.EI.engines[0].tOilOut, 20F, 120F, 0.0F, 250F), 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", 0.0F, -cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 256F), 0.0F);
        mesh.chunkSetAngles("Z_FuelPres1", 0.0F, -cvt(fm.M.fuel <= 5F ? 0.0F : 0.78F, 0.0F, 1.0F, 0.0F, 255F), 0.0F);
        mesh.chunkSetAngles("Z_Compass", setNew.azimuth, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Hora", 0.0F, -cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("Z_Minuto", 0.0F, -cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(fm.M.fuel, 0.0F, 180F, 0.0F, 110F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel2", 0.0F, -cvt(fm.M.fuel, 0.0F, 180F, 0.0F, 110F), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_Palo", 0.0F, -cvt(w.z, -0.23562F, 0.23562F, 22.5F, -22.5F), 0.0F);
        mesh.chunkSetAngles("Z_Bola", 0.0F, -cvt(getBall(7D), -7F, 7F, -15F, 15F), 0.0F);
        mesh.chunkSetAngles("Z_RadioAlt", 0.0F, cvt(interp(setNew.radioalt, setOld.radioalt, f), 6.27F, 306.27F, 0.0F, -330F), 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage3", true);
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    private void retoggleLight()
    {
        if(cockpitLightControl)
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
    private int rotorrpm;
    private float pictGear;
    private float pictFlap;
    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null, null, null
    };
    private com.maddox.il2.ai.BulletEmitter bgun[] = {
        null, null, null, null
    };
    private com.maddox.il2.ai.BulletEmitter rgun[] = {
        null, null, null, null
    };
    private com.maddox.il2.ai.BulletEmitter tgun[] = {
        null, null
    };
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 15.5F, 77F, 175F, 275F, 360F, 412F, 471F, 539F, 610.5F, 
        669.75F, 719F
    };
    private static final float variometerScale[] = {
        -170F, -147F, -124F, -101F, -78F, -48F, 0.0F, 48F, 78F, 101F, 
        124F, 147F, 170F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
