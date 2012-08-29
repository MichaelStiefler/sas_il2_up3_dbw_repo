// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitKI_27OTSU.java

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
//            CockpitPilot, KI_27, Aircraft, Cockpit

public class CockpitKI_27OTSU extends com.maddox.il2.objects.air.CockpitPilot
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
            aircraft().hierMesh().chunkVisible("Head1_D0", false);
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
            aircraft().hierMesh().chunkVisible("Head1_D0", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
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
        mesh.chunkVisible("EDET", false);
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
            mesh.chunkVisible("EDET", true);
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

    public CockpitKI_27OTSU()
    {
        super("3DO/Cockpit/Ki-27(Otsu)/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictRad = 0.0F;
        pictGun = 0.0F;
        pictFlap = 0.0F;
        bNeedSetUp = true;
        oldTime = -1L;
        bEntered = false;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "gauge1", "gauge2", "gauge3", "gauge4", "gauge1_d", "gauge2_d", "gauge3_d", "gauge4_d", "Arrows", "Digits"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            reflectPlaneToModel();
            bNeedSetUp = false;
        }
        com.maddox.il2.objects.air.KI_27 _tmp = (com.maddox.il2.objects.air.KI_27)aircraft();
        if(com.maddox.il2.objects.air.KI_27.bChangedPit)
        {
            reflectPlaneToModel();
            com.maddox.il2.objects.air.KI_27 _tmp1 = (com.maddox.il2.objects.air.KI_27)aircraft();
            com.maddox.il2.objects.air.KI_27.bChangedPit = false;
        }
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.05F, 0.95F, 0.0F, 0.55F);
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_ReViTinter", 0.0F, cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("Z_BoxTinter", 0.0F, cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, -180F), 0.0F);
        mesh.chunkSetAngles("Z_ColumnBase", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 20F, 0.0F);
        mesh.chunkSetAngles("Z_Column", 0.0F, -(pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F, 0.0F);
        mesh.chunkSetAngles("Z_ColumnWire", 0.0F, pictElev * 20F, 0.0F);
        mesh.chunkSetAngles("Z_PedalBase", 0.0F, -30F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", 0.0F, 20F * fm.CT.getBrake(), 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 20F * fm.CT.getBrake(), 0.0F);
        mesh.chunkSetAngles("Z_RightWire", -30F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftWire", -30F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Radiat", 0.0F, -450F * (pictRad = 0.9F * pictRad + 0.1F * fm.EI.engines[0].getControlRadiator()), 0.0F);
        mesh.chunkSetAngles("Z_Throtle1", 0.0F, cvt(setNew.throttle, 0.0F, 1.1F, -38F, 38F), 0.0F);
        mesh.chunkSetAngles("Z_Throtle2", 0.0F, 30F * (pictGun = 0.8F * pictGun + 0.2F * (fm.CT.saveWeaponControl[0] ? 1.0F : 0.0F)), 0.0F);
        mesh.chunkSetAngles("zPitch1", 0.0F, cvt(setNew.prop, 0.0F, 1.0F, -38F, 38F), 0.0F);
        mesh.chunkSetAngles("zTrim1", 0.0F, cvt(fm.CT.trimElevator, -0.5F, 0.5F, 35F, -35F), 0.0F);
        mesh.chunkSetAngles("zTrim2", 0.0F, cvt(fm.CT.trimElevator, -0.5F, 0.5F, -35F, 35F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(setNew.mix, 0.0F, 1.2F, 0.03675F, 0.0F);
        long l = com.maddox.rts.Time.current();
        long l1 = l - oldTime;
        oldTime = l;
        float f2 = (float)l1 * 0.00016F;
        if(pictFlap < fm.CT.FlapsControl)
        {
            if(pictFlap + f2 >= fm.CT.FlapsControl)
                pictFlap = fm.CT.FlapsControl;
            else
                pictFlap += f2;
        } else
        if(pictFlap - f2 <= fm.CT.FlapsControl)
            pictFlap = fm.CT.FlapsControl;
        else
            pictFlap -= f2;
        mesh.chunkSetAngles("Z_Flaps", 0.0F, -3450F * pictFlap, 0.0F);
        mesh.chunkSetAngles("Z_Mag1", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 76.5F, -28.5F), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
        {
            mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -7200F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -720F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 300F, 0.0F, 15F), speedometerScale), 0.0F, 0.0F);
        w.set(fm.getW());
        fm.Or.transform(w);
        mesh.chunkSetAngles("Z_TurnBank1", cvt(w.z, -0.23562F, 0.23562F, -30F, 30F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(6D), -6F, 6F, 6F, -6F), 0.0F, 0.0F);
        float f1 = setNew.vspeed;
        if(java.lang.Math.abs(f1) < 5F)
            mesh.chunkSetAngles("Z_Climb1", cvt(f1, -5F, 5F, 90F, -90F), 0.0F, 0.0F);
        else
        if(f1 > 0.0F)
            mesh.chunkSetAngles("Z_Climb1", cvt(f1, 5F, 30F, -90F, -180F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_Climb1", cvt(f1, -30F, -5F, 180F, 90F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Clock_H", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, -720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Clock_Min", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360F), 0.0F, 0.0F);
        f1 = cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 8.1F, 0.0F, 20F);
        for(int i = 1; i < 20; i++)
            mesh.chunkVisible("Z_OilP" + (i >= 10 ? "" + i : "0" + i), f1 > (float)(20 - i));

        mesh.chunkSetAngles("Z_Manipres", cvt(setNew.manifold, 0.33339F, 1.66661F, 150F, -150F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 200F, 3000F, -8.5F, -323F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuelpres", cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 2.0F, 0.0F, -360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oiltemp1", cvt(fm.EI.engines[0].tOilOut, 0.0F, 130F, 0.0F, -300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 5.5F, 0.0F, -300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Tempcyl", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 360F, 0.0F, -90.6F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Fuel", cvt(fm.M.fuel, 0.0F, 108F, -41F, -320F), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage5", true);
            mesh.chunkVisible("XGlassDamage7", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_TurnBank1", false);
            mesh.chunkVisible("Z_TurnBank2", false);
            mesh.chunkVisible("Z_Climb1", false);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_Manipres", false);
            mesh.chunkVisible("Z_Fuel", false);
            mesh.chunkVisible("Z_Fuelpres", false);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Oiltemp1", false);
            mesh.chunkVisible("Z_Oilpres1", false);
            mesh.chunkVisible("Z_Clock_H", false);
            mesh.chunkVisible("Z_Clock_Min", false);
            mesh.chunkVisible("Z_Tempcyl", false);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XHullDamage1", true);
            mesh.chunkVisible("XHullDamage2", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XHullDamage4", true);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassDamage6", true);
            mesh.chunkVisible("XHullDamage1", true);
            mesh.chunkVisible("XHullDamage5", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("XHullDamage4", true);
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
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D0o"));
        mesh.materialReplace("Matt2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D1o"));
        mesh.materialReplace("Matt1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D2o"));
        mesh.materialReplace("Matt2D2o", mat);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("WingLIn_CAP", hiermesh.isChunkVisible("WingLIn_CAP"));
        mesh.chunkVisible("WingLIn_D0", hiermesh.isChunkVisible("WingLIn_D0"));
        mesh.chunkVisible("WingLIn_D1", hiermesh.isChunkVisible("WingLIn_D1"));
        mesh.chunkVisible("WingLIn_D2", hiermesh.isChunkVisible("WingLIn_D2"));
        mesh.chunkVisible("WingLIn_D3", hiermesh.isChunkVisible("WingLIn_D3"));
        mesh.chunkVisible("WingRIn_CAP", hiermesh.isChunkVisible("WingRIn_CAP"));
        mesh.chunkVisible("WingRIn_D0", hiermesh.isChunkVisible("WingRIn_D0"));
        mesh.chunkVisible("WingRIn_D1", hiermesh.isChunkVisible("WingRIn_D1"));
        mesh.chunkVisible("WingRIn_D2", hiermesh.isChunkVisible("WingRIn_D2"));
        mesh.chunkVisible("WingRIn_D3", hiermesh.isChunkVisible("WingRIn_D3"));
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictRad;
    private float pictGun;
    private float pictFlap;
    private boolean bNeedSetUp;
    private long oldTime;
    private static final float speedometerScale[] = {
        0.0F, 6.5F, 16.5F, 49F, 91.5F, 143.5F, 199F, 260F, 318F, 376.5F, 
        433F, 484F, 534F, 576F, 620F, 660F
    };
    private static final float oilScale[] = {
        0.0F, -27.5F, 12F, 59.5F, 127F, 212.5F, 311.5F
    };
    private float saveFov;
    private boolean bEntered;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
