// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitBI_6.java

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
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitBI_6 extends com.maddox.il2.objects.air.CockpitPilot
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
                if(fm.AS.astateSootStates[1] > 3)
                    pictT1 = 0.993F * pictT1 + 0.007F * com.maddox.il2.ai.World.Rnd().nextFloat(600F, 1000F);
                else
                    pictT1 = 0.9992F * pictT1 + 0.0008F * (com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 200F);
                if(fm.AS.astateSootStates[2] > 3)
                    pictT2 = 0.993F * pictT2 + 0.007F * com.maddox.il2.ai.World.Rnd().nextFloat(600F, 1000F);
                else
                    pictT2 = 0.9992F * pictT2 + 0.0008F * (com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 200F);
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
            waypoint.getP(P1);
            V.sub(P1, fm.Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(-V.y, V.x));
        }
    }

    public CockpitBI_6()
    {
        super("3DO/Cockpit/BI-6/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictKg1 = 0.0F;
        pictKg2 = 0.0F;
        pictT1 = 0.0F;
        pictT2 = 0.0F;
        cockpitNightMats = (new java.lang.String[] {
            "ONE", "TWO", "THREE", "FOUR"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Ped_Base", fm.CT.getRudder() * 15F, 0.0F, 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, fm.CT.getRudder() * 15F, 0.0F);
        resetYPRmodifier();
        ypr[1] = -80.08F * interp(setNew.throttle, setOld.throttle, f);
        xyz[1] = ypr[1] >= -33F ? 0.0F : -0.0065F;
        mesh.chunkSetLocate("Throttle", xyz, ypr);
        if(fm.CT.GearControl == 0.0F && fm.CT.getGear() != 0.0F)
            mesh.chunkSetAngles("Lever_Gear", -17F, 0.0F, 0.0F);
        else
        if(fm.CT.GearControl == 1.0F && fm.CT.getGear() != 1.0F)
            mesh.chunkSetAngles("Lever_Gear", 15F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Lever_Gear", 0.0F, 0.0F, 0.0F);
        if(java.lang.Math.abs(fm.CT.FlapsControl - fm.CT.getFlap()) > 0.02F)
        {
            if(fm.CT.FlapsControl - fm.CT.getFlap() > 0.0F)
                mesh.chunkSetAngles("Lever_Flaps", 15F, 0.0F, 0.0F);
            else
                mesh.chunkSetAngles("Lever_Flaps", -20F, 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("Lever_Flaps", 0.0F, 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F);
        mesh.chunkSetAngles("zSpeed1a", 0.0F, cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1200F, 0.0F, 360F), 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(w.z, -0.47124F, 0.47124F, 40F, -40F), 0.0F);
        mesh.chunkSetAngles("zSlide1a", cvt(getBall(8D), -8F, 8F, 22.5F, -22.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zGas1a", 0.0F, cvt(fm.M.fuel / 0.72F, 0.0F, 300F, 0.0F, 180F), 0.0F);
        mesh.chunkVisible("Z_Red8", fm.CT.getGear() > 0.05F && fm.CT.getGear() < 0.95F);
        mesh.chunkVisible("Z_Red5", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_Red7", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_Red4", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_Red6", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_Red15", fm.EI.engines[1].getPowerOutput() > 0.5F);
        mesh.chunkVisible("Z_Red16", fm.EI.engines[2].getPowerOutput() > 0.5F);
        mesh.chunkSetAngles("Switch1_1", 0.0F, 40F, 0.0F);
        mesh.chunkSetAngles("Switch1_2", 0.0F, 40F, 0.0F);
        mesh.chunkSetAngles("Switch2_1", 0.0F, 40F, 0.0F);
        mesh.chunkSetAngles("Switch2_2", 0.0F, 40F, 0.0F);
        resetYPRmodifier();
        xyz[0] = cvt(fm.EI.engines[1].getControlThrottle(), 0.8F, 1.0F, 0.0F, -0.09F);
        mesh.chunkSetLocate("Throttle2", xyz, ypr);
        xyz[0] = cvt(fm.EI.engines[2].getControlThrottle(), 0.8F, 1.0F, 0.0F, -0.09F);
        mesh.chunkSetLocate("Throttle3", xyz, ypr);
        mesh.chunkSetAngles("zPVRDFuel", 0.0F, cvt(fm.M.nitro, 0.0F, 432F, 6.5F, 282F), 0.0F);
        float f1 = 0.0F;
        if(fm.EI.engines[1].getControlThrottle() > 0.8F)
            f1 = 20F * fm.EI.engines[1].getReadyness();
        pictKg1 = 0.93F * pictKg1 + 0.07F * f1;
        mesh.chunkSetAngles("zPVRD1_1", 0.0F, cvt(pictKg1, 0.0F, 25F, 0.0F, 275F), 0.0F);
        f1 = 0.0F;
        if(fm.EI.engines[2].getControlThrottle() > 0.8F)
            f1 = 20F * fm.EI.engines[2].getReadyness();
        pictKg2 = 0.93F * pictKg2 + 0.07F * f1;
        mesh.chunkSetAngles("zPVRD1_2", 0.0F, cvt(pictKg1, 0.0F, 25F, 0.0F, 275F), 0.0F);
        mesh.chunkSetAngles("zPVRD2_1", 0.0F, cvt(pictT1, 0.0F, 1000F, 0.0F, 120F), 0.0F);
        mesh.chunkSetAngles("zPVRD2_2", 0.0F, cvt(pictT2, 0.0F, 1000F, 0.0F, 120F), 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x40) != 0 || (fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 0x10) != 0 || (fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.materialReplace("ONE", "ONE_D1");
            mesh.materialReplace("ONE_night", "ONE_D1_night");
            mesh.materialReplace("Dash", "Dash_D1");
            mesh.chunkVisible("zAlt1a", false);
            mesh.chunkVisible("zAlt1b", false);
            mesh.chunkVisible("zSpeed1a", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x20) != 0 || (fm.AS.astateCockpitState & 1) != 0 || (fm.AS.astateCockpitState & 0x80) != 0)
        {
            mesh.materialReplace("THREE", "THREE_D1");
            mesh.materialReplace("THREE_night", "THREE_D1_night");
            mesh.chunkVisible("zSlide1a", false);
        }
        retoggleLight();
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            setNightMats(true);
            mesh.chunkVisible("Z_Red11", true);
            mesh.chunkVisible("Z_Red14", true);
        } else
        {
            setNightMats(false);
            mesh.chunkVisible("Z_Red11", false);
            mesh.chunkVisible("Z_Red14", false);
        }
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
    private float pictKg1;
    private float pictKg2;
    private float pictT1;
    private float pictT2;










}
