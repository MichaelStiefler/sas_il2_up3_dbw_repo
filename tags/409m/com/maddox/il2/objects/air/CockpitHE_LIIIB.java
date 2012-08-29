// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHE_LIIIB.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitHE_LIIIB extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle1 = 0.85F * setOld.throttle1 + fm.EI.engines[0].getControlThrottle() * 0.15F;
                setNew.prop1 = 0.85F * setOld.prop1 + fm.EI.engines[0].getControlProp() * 0.15F;
                setNew.mix1 = 0.85F * setOld.mix1 + fm.EI.engines[0].getControlMix() * 0.15F;
                setNew.throttle2 = 0.85F * setOld.throttle2 + fm.EI.engines[1].getControlThrottle() * 0.15F;
                setNew.prop2 = 0.85F * setOld.prop2 + fm.EI.engines[1].getControlProp() * 0.15F;
                setNew.mix2 = 0.85F * setOld.mix2 + fm.EI.engines[1].getControlMix() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-2F, 2.0F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = 0.8F * setOld.vspeed + 0.2F * fm.getVertSpeed();
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                setNew.radioalt = 0.5F * setOld.radioalt + 0.5F * (fm.getAltitude() - com.maddox.il2.ai.World.cur().HQ_Air(com.maddox.il2.ai.World.land(), (float)fm.Loc.x, (float)fm.Loc.y));
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float throttle1;
        float prop1;
        float mix1;
        float throttle2;
        float prop2;
        float mix2;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        float dimPosition;
        float radioalt;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!isFocused())
        {
            return;
        } else
        {
            leave();
            super.doFocusLeave();
            return;
        }
    }

    private void enter()
    {
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(aAim, tAim, kAim);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
    }

    private void leave()
    {
        if(!bEntered)
        {
            return;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
            return;
        }
    }

    public void destroy()
    {
        super.destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
        if(!isFocused())
            return;
        if(isToggleAim() == flag)
            return;
        if(flag)
            enter();
        else
            leave();
    }

    public CockpitHE_LIIIB()
    {
        super("3DO/Cockpit/He-LercheIIIb/hier.him", "he111");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictTriA = 0.0F;
        pictTriE = 0.0F;
        pictTriR = 0.0F;
        pictManf1 = 1.0F;
        pictManf2 = 1.0F;
        bEntered = false;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        try
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "CAMERAAIM");
            hooknamed.computePos(this, pos.getAbs(), loc);
            aAim = loc.getOrient().getAzimut();
            tAim = loc.getOrient().getTangage();
            kAim = loc.getOrient().getKren();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("Z_Stick", -(pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 10F, 0.0F, -(pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Z_THTL1", 25F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_THTL2", 25F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 57F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp2", cvt(fm.EI.engines[1].tWaterOut, 0.0F, 120F, 0.0F, 57F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpress1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 58F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpress2", cvt(1.0F + 0.05F * fm.EI.engines[1].tOilOut * fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, 58F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM2", floatindex(cvt(fm.EI.engines[1].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ATA1", pictManf1 = 0.9F * pictManf1 + 0.1F * cvt(fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_ATA2", pictManf2 = 0.9F * pictManf2 + 0.1F * cvt(fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel, 0.0F, 864F, -8F, 80F), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, 33F, -33F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(8D), -8F, 8F, -15F, 15F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, fm.Or.getKren());
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.024F, -0.024F);
        mesh.chunkSetLocate("Z_TurnBank2a", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Speed1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 900F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Alt1", cvt(interp(setNew.radioalt, setOld.radioalt, f), 6.27F, 206.27F, 0.0F, -100F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Alt2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Alt3", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 14000F, 0.0F, 315F), 0.0F, 0.0F);
        float f1 = setNew.vspeed <= 0.0F ? -1F : 1.0F;
        mesh.chunkSetAngles("Z_Climb1", f1 * floatindex(cvt(java.lang.Math.abs(setNew.vspeed), 0.0F, 30F, 0.0F, 6F), variometerScale), 0.0F, 0.0F);
        mesh.chunkVisible("Z_EnRoute", false);
        mesh.chunkVisible("Z_TD1", setNew.radioalt < 6.5F);
        mesh.chunkVisible("Z_TD2", setNew.radioalt < 6.5F);
        mesh.chunkVisible("Z_TD3", setNew.radioalt < 6.5F);
        mesh.chunkVisible("Z_Stabilizer1", fm.CT.AirBrakeControl > 0.5F);
        mesh.chunkVisible("Z_Stabilizer2", fm.CT.getAirBrake() > 0.99F && fm.Or.getTangage() > 30F);
        mesh.chunkVisible("Z_Power25", fm.EI.getPowerOutput() > 0.25F);
        mesh.chunkVisible("Z_LoFuel", fm.M.fuel < 65F);
        mesh.chunkVisible("Z_Fire1", fm.AS.astateEngineStates[0] > 2 || fm.AS.astateEngineStates[1] > 2);
        mesh.chunkVisible("Z_Fire2", fm.AS.astateEngineStates[0] > 2 || fm.AS.astateEngineStates[1] > 2);
        mesh.chunkVisible("Z_Fire3", fm.AS.astateEngineStates[0] > 2 || fm.AS.astateEngineStates[1] > 2);
        mesh.chunkVisible("Z_MPlus", fm.EI.engines[0].getStage() > 0 || fm.EI.engines[1].getStage() > 0);
    }

    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
            return 0.0F;
        waypoint.getP(tmpP);
        tmpV.sub(tmpP, fm.Loc);
        float f;
        for(f = (float)(57.295779513082323D * java.lang.Math.atan2(-tmpV.y, tmpV.x)); f <= -180F; f += 180F);
        for(; f > 180F; f -= 180F);
        return f;
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("Z_Z_RETICLE", false);
            mesh.chunkVisible("Z_Z_MASK", false);
            mesh.chunkVisible("Revi_D0", false);
            mesh.chunkVisible("Revisun", false);
            mesh.chunkVisible("Revi_D1", true);
        }
        if((fm.AS.astateCockpitState & 1) == 0);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("PanelL_D0", false);
            mesh.chunkVisible("PanelL_D1", true);
            mesh.chunkVisible("Z_TurnBank1", false);
            mesh.chunkVisible("Z_TurnBank2", false);
            mesh.chunkVisible("Z_TurnBank2a", false);
            mesh.chunkVisible("Z_TurnBank3", false);
            mesh.chunkVisible("Z_Alt2", false);
            mesh.chunkVisible("Z_Alt3", false);
            mesh.chunkVisible("Z_Speed1", false);
        }
        if((fm.AS.astateCockpitState & 4) == 0);
        if((fm.AS.astateCockpitState & 8) == 0);
        if((fm.AS.astateCockpitState & 0x80) == 0);
        if((fm.AS.astateCockpitState & 0x10) == 0);
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("PanelR_D0", false);
            mesh.chunkVisible("PanelR_D1", true);
            mesh.chunkVisible("Z_Temp1", false);
            mesh.chunkVisible("Z_Temp2", false);
            mesh.chunkVisible("Z_Oilpress1", false);
            mesh.chunkVisible("Z_Oilpress2", false);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_RPM2", false);
            mesh.chunkVisible("Z_ATA1", false);
            mesh.chunkVisible("Z_ATA2", false);
            mesh.chunkVisible("Z_Fuel1", false);
        }
        retoggleLight();
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
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
    private float pictTriA;
    private float pictTriE;
    private float pictTriR;
    private float pictManf1;
    private float pictManf2;
    private static final float speedometerScale[] = {
        0.0F, 21F, 69.5F, 116F, 163F, 215.5F, 266.5F, 318.5F, 378F, 430.5F
    };
    private static final float variometerScale[] = {
        0.0F, 47F, 82F, 97F, 112F, 111.7F, 132F
    };
    private static final float rpmScale[] = {
        0.0F, 2.5F, 19F, 50.5F, 102.5F, 173F, 227F, 266.5F, 297F
    };
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
