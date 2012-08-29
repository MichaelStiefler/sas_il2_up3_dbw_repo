// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitG4M2E.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitG4M2E extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float throttle1;
        float throttle2;
        float prop1;
        float prop2;
        float mix1;
        float mix2;
        float man1;
        float man2;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        float vspeed;

        private Variables()
        {
            azimuth = new AnglesFork();
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
                setNew.throttle1 = 0.9F * setOld.throttle1 + 0.1F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlThrottle();
                setNew.prop1 = 0.9F * setOld.prop1 + 0.1F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlProp();
                setNew.mix1 = 0.8F * setOld.mix1 + 0.2F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix();
                setNew.throttle2 = 0.9F * setOld.throttle2 + 0.1F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlThrottle();
                setNew.prop2 = 0.9F * setOld.prop2 + 0.1F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlProp();
                setNew.mix2 = 0.8F * setOld.mix2 + 0.2F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlMix();
                setNew.man1 = 0.92F * setOld.man1 + 0.08F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getManifoldPressure();
                setNew.man2 = 0.92F * setOld.man2 + 0.08F * ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getManifoldPressure();
                setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).azimut());
                setNew.vspeed = (199F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 200F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AP.way.curr();
        if(waypoint == null)
            return 0.0F;
        waypoint.getP(tmpP);
        ((com.maddox.JGP.Tuple3d) (tmpV)).sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)));
        float f;
        for(f = (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x)); f <= -180F; f += 180F);
        for(; f > 180F; f -= 180F);
        return f;
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Cockpit_D0", false);
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("CF_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Cockpit_D0", ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().isChunkVisible("CF_D0") || ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().isChunkVisible("CF_D1") || ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().isChunkVisible("CF_D2") || ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().isChunkVisible("CF_D3"));
        ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("CF_D0", true);
        super.doFocusLeave();
    }

    public CockpitG4M2E()
    {
        super("3DO/Cockpit/G4M2EBetty/hier.him", "he111");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "gauges5", "GP1_d1", "GP1", "GP2_d1", "GP2", "GP3", "GP4_d1", "GP4", "GP5_d1", "GP5", 
            "GP6_d1", "GP6", "GP7", "GP9", "throttle", "Volt_Tacho"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        if(((com.maddox.il2.engine.Actor)this).acoustics != null)
            ((com.maddox.il2.engine.Actor)this).acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Columnbase", 0.0F, 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ColumnL", 0.0F, 0.0F, -60.6F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_ColumnR", 0.0F, 0.0F, -60.6F * pictAiler);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Throtle1", 40F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle1, setOld.throttle1, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Throtle2", 40F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle2, setOld.throttle2, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Prop1", 75.5F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.prop1, setOld.prop1, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Prop2", 75.5F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.prop2, setOld.prop2, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Mixture1", 62.9F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.mix1, setOld.mix1, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Mixture2", 62.9F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.mix2, setOld.mix2, f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPedal", 0.0F, 0.0F, -22.2F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder());
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPedalStep1", 0.0F, 0.0F, -22.2F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder());
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPedalStep2", 0.0F, 0.0F, -22.2F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder());
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_LPedal", 0.0F, 0.0F, -22.2F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder());
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_LPedalStep1", 0.0F, 0.0F, -22.2F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder());
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_LPedalStep2", 0.0F, 0.0F, -22.2F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder());
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass1", 0.0F, -setNew.azimuth.getDeg(f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass2", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass3", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass4", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Compass5", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AH1", 0.0F, 0.0F, ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren());
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -45F, 45F, -0.03F, 0.03F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_AH2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_AH3", 0.0F, 0.0F, ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren());
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage(), -45F, 45F, -0.03F, 0.03F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_AH4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getW())));
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        float f1 = ((com.maddox.il2.objects.air.CockpitPilot)this).getBall(7D);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TurnBank1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(f1, -5F, 5F, 8.5F, -8.5F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TurnBank2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 22F, -22F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TurnBank3", ((com.maddox.il2.objects.air.Cockpit)this).cvt(f1, -7F, 7F, 16F, -16F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TurnBank4", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 22F, -22F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_TurnBank5", ((com.maddox.il2.objects.air.Cockpit)this).cvt(f1, -7F, 7F, 16F, -16F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Speedometer1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 300F, 0.0F, 15F), speedometerScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Speedometer2", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH()), 0.0F, 300F, 0.0F, 15F), speedometerScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Hour1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Minute1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 1440F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Altimeter2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 1440F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].tWaterOut, 0.0F, 350F, 0.0F, 90F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, 90F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp3", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].tOilIn, 0.0F, 350F, 0.0F, 90F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp4", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilIn, 0.0F, 350F, 0.0F, 90F), 0.0F, 0.0F);
        float f2 = (com.maddox.il2.fm.Atmosphere.temperature((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z) - 273.09F) + 44.4F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getPowerOutput();
        if(f2 < 0.0F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp5", ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, -40F, 0.0F, 0.0F, 45F), 0.0F, 0.0F);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp5", ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 0.0F, 60F, 45F, 90F), 0.0F, 0.0F);
        f2 = (com.maddox.il2.fm.Atmosphere.temperature((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z) - 273.09F) + 44.4F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].getPowerOutput();
        if(f2 < 0.0F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp6", ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, -40F, 0.0F, 0.0F, 45F), 0.0F, 0.0F);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp6", ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 0.0F, 60F, 45F, 90F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp7", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].tOilOut, 0.0F, 120F, 0.0F, 12F), oilTempScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Temp8", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 12F), oilTempScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Flap1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getFlap(), 0.0F, 0.75F, 0.0F, 90F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Flap2", 57F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getFlap(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Fuel1", ((com.maddox.il2.objects.air.Cockpit)this).cvt((float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel), 0.0F, 34.641F, 0.0F, 225F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Fuel2", ((com.maddox.il2.objects.air.Cockpit)this).cvt((float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel), 0.0F, 34.641F, 0.0F, 225F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Fuel3", ((com.maddox.il2.objects.air.Cockpit)this).cvt((float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel), 0.0F, 38.729F, 0.0F, 225F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Fuel4", ((com.maddox.il2.objects.air.Cockpit)this).cvt((float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel), 26.925F, 38.729F, 0.0F, 225F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Fuel5", ((com.maddox.il2.objects.air.Cockpit)this).cvt((float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel), 26.925F, 38.729F, 0.0F, 225F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[2] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -15F, 15F, -0.055F, 0.055F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Z_Climb1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Climb2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -10F, 10F, -180F, 180F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Manifold1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.man1, 0.33339F, 1.66661F, -162F, 162F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Manifold2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.man2, 0.33339F, 1.66661F, -162F, 162F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Oil1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_Oil2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, 180F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_fuelpress1", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.5F, 0.0F, 180F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_fuelpress2", ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.5F, 0.0F, 180F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPM1", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM(), 0.0F, 3500F, 0.0F, 7F), rpmScale), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Z_RPM2", ((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[1].getRPM(), 0.0F, 3500F, 0.0F, 7F), rpmScale), 0.0F, 0.0F);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) == 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearGreen1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearGreen2", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearGreen3", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearRed1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearRed2", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.rgear);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearRed3", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getGear() < 0.01F || !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Gears.lgear);
        }
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XHullDamage5", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage5", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XHullDamage4", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Panel_D0", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Panel_D1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Fuel1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Fuel2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_TurnBank2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_TurnBank3", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_TurnBank4", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_TurnBank5", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Flap2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearGreen1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearGreen2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearGreen3", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearRed1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearRed2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_GearRed3", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_RPM1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_RPM2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Oil1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Oil2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_fuelpress1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_fuelpress2", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Temp1", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Z_Temp2", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XHullDamage2", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XHullDamage1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XHullDamage4", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage4", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XHullDamage3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XHullDamage5", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x20) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XGlassDamage5", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("XHullDamage1", true);
        }
        retoggleLight();
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
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 6.5F, 30F, 66F, 105F, 158.5F, 212F, 272.5F, 333F, 384F, 
        432.5F, 479.5F, 526.5F, 573.5F, 624.5F, 674F
    };
    private static final float oilTempScale[] = {
        0.0F, 4F, 17.5F, 38F, 63F, 90.5F, 115F, 141.3F, 180F, 221.7F, 
        269.5F, 311F, 357F
    };
    private static final float rpmScale[] = {
        0.0F, 10F, 75F, 126.5F, 179.5F, 232F, 284.5F, 336F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
