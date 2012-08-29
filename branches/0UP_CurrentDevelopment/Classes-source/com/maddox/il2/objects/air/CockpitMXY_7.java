// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitMXY_7.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitMXY_7 extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float throttle;
        float altimeter;

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
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.altimeter = fm.getAltitude();
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
            waypoint.getP(com.maddox.il2.objects.air.Cockpit.P1);
            com.maddox.il2.objects.air.Cockpit.V.sub(com.maddox.il2.objects.air.Cockpit.P1, fm.Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(-com.maddox.il2.objects.air.Cockpit.V.y, com.maddox.il2.objects.air.Cockpit.V.x));
        }
    }

    public CockpitMXY_7()
    {
        super("3DO/Cockpit/MXY7/CockpitMXY7.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        cockpitNightMats = (new java.lang.String[] {
            "ONE", "TWO", "THREE"
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
        com.maddox.il2.objects.air.Cockpit.ypr[1] = -80.08F * interp(setNew.throttle, setOld.throttle, f);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = com.maddox.il2.objects.air.Cockpit.ypr[1] >= -33F ? 0.0F : -0.0065F;
        mesh.chunkSetLocate("Throttle", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitMXY_7.class, "normZN", 1.08F);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitMXY_7.class, "gsZN", 1.0F);
    }






}
