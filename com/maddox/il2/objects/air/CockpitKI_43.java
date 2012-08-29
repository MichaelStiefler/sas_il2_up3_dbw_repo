// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitKI_43.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
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
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft

public class CockpitKI_43 extends com.maddox.il2.objects.air.CockpitPilot
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
                if(cockpitDimControl)
                {
                    if(setNew.dimPos < 1.0F)
                        setNew.dimPos = setOld.dimPos + 0.03F;
                } else
                if(setNew.dimPos > 0.0F)
                    setNew.dimPos = setOld.dimPos - 0.03F;
                setNew.manifold = 0.8F * setOld.manifold + 0.2F * fm.EI.engines[0].getManifoldPressure();
                setNew.throttle = 0.8F * setOld.throttle + 0.2F * fm.CT.PowerControl;
                setNew.prop = 0.8F * setOld.prop + 0.2F * fm.EI.engines[0].getControlProp();
                setNew.mix = 0.8F * setOld.mix + 0.2F * fm.EI.engines[0].getControlMix();
                setNew.man = 0.92F * setOld.man + 0.08F * fm.EI.engines[0].getManifoldPressure();
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.waypointDeviation.setDeg(setOld.waypointDeviation.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-5F, 5F));
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(1.0F), f);
                setNew.vspeed = 0.5F * setOld.vspeed + 0.5F * fm.getVertSpeed();
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float dimPos;
        float throttle;
        float prop;
        float mix;
        float altimeter;
        float man;
        float vspeed;
        float manifold;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        com.maddox.il2.ai.AnglesFork waypointDeviation;

        private Variables()
        {
            dimPos = 0.0F;
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
            waypointDeviation = new AnglesFork();
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
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 31");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
        mesh.chunkVisible("SuperReticle", true);
        mesh.chunkVisible("Z_BoxTinter", true);
    }

    private void leave()
    {
        if(!bEntered)
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
            mesh.chunkVisible("SuperReticle", false);
            mesh.chunkVisible("Z_BoxTinter", false);
            return;
        }
    }

    public void destroy()
    {
        leave();
        super.destroy();
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

    public CockpitKI_43()
    {
        super("3DO/Cockpit/Ki-43/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        bNeedSetUp = true;
        bEntered = false;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "Fuel", "gauge1_D1", "gauge1", "gauge2_D2", "gauge2", "gauge3", "gauge4_D4", "gauge4", "gauge"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
            bNeedSetUp = false;
        resetYPRmodifier();
        xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.59F);
        mesh.chunkSetLocate("Canopy", xyz, ypr);
        mesh.chunkSetAngles("Z_ReViTinter", cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_BoxTinter", cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Radiat", 0.0F, -45F * fm.EI.engines[0].getControlRadiator(), 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 70.45F * setNew.throttle, 0.0F, 0.0F);
        mesh.chunkSetAngles("zPitch1", 77F * setNew.prop, 0.0F, 0.0F);
        mesh.chunkSetAngles("zMix1", 64.1F * setNew.mix, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_PedalBase", 10F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", 10F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", 10F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 8F, 0.0F, -(pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 8F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
        {
            mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 7200F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, 720F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 1000F, 0.0F, 20F), speedometerScale), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(8D), -8F, 8F, -16F, 16F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(setNew.vspeed, -30F, 30F, 0.5F, 6.5F), vsiNeedleScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 3000F, 0.0F, 335F), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
        {
            mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 2.0F, 0.0F, -360F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Oil1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 130F, 0.0F, 295.5F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_Pres1", cvt(setNew.manifold, 0.2000681F, 2.066576F, -144.75F, 193F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 5.5F, 0.0F, 295.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 130F, 0.0F, 76.8F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp2", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 200F, 0.0F, 64F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel2", 0.0F, cvt(fm.M.fuel, 0.0F, 400F, 0.0F, 282.5F), 0.0F);
        mesh.chunkSetAngles("Z_Fuel3", 0.0F, cvt(fm.M.fuel, 0.0F, 400F, 0.0F, 247F), 0.0F);
        mesh.chunkVisible("Z_Green1", fm.CT.getGear() > 0.99F);
        mesh.chunkVisible("Z_Red1", fm.CT.getGear() < 0.01F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassDamage3", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_TurnBank1", false);
            mesh.chunkVisible("Z_TurnBank2", false);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_Pres1", false);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_Oilpres1", false);
            mesh.chunkVisible("Z_Temp1", false);
            mesh.chunkVisible("Z_Temp2", false);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XHullDamage1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("XHullDamage3", true);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XHullDamage2", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("XHullDamage1", true);
            mesh.chunkVisible("XGlassDamage4", true);
        }
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

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Pilot2"));
        mesh.materialReplace("Pilot2", mat);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("Pilot2_D0", hiermesh.isChunkVisible("Pilot2_D0"));
        mesh.chunkVisible("Pilot2_D1", hiermesh.isChunkVisible("Pilot2_D1"));
        mesh.chunkVisible("Turret1B_D0", hiermesh.isChunkVisible("Turret1B_D0"));
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private boolean bNeedSetUp;
    private static final float speedometerScale[] = {
        0.0F, 10F, 35F, 70F, 105F, 145F, 190F, 230F, 275F, 315F, 
        360F, 397.5F, 435F, 470F, 505F, 537.5F, 570F, 600F, 630F, 655F, 
        680F
    };
    private static final float oilScale[] = {
        0.0F, -27.5F, 12F, 59.5F, 127F, 212.5F, 311.5F
    };
    private static final float vsiNeedleScale[] = {
        -200F, -160F, -125F, -90F, 90F, 125F, 160F, 200F
    };
    private float saveFov;
    private boolean bEntered;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
