// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitB25J15_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, B_25J15, AircraftLH, Aircraft

public class CockpitB25J15_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            float f = ((com.maddox.il2.objects.air.B_25J15)aircraft()).fSightCurForwardAngle;
            float f1 = ((com.maddox.il2.objects.air.B_25J15)aircraft()).fSightCurSideslip;
            com.maddox.il2.objects.air.CockpitB25J15_Bombardier.calibrAngle = 360F - fm.Or.getPitch();
            mesh.chunkSetAngles("BlackBox", -10F * f1, 0.0F, com.maddox.il2.objects.air.CockpitB25J15_Bombardier.calibrAngle + f);
            if(bEntered)
            {
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(aAim + 10F * f1, tAim + com.maddox.il2.objects.air.CockpitB25J15_Bombardier.calibrAngle + f, 0.0F);
            }
            mesh.chunkSetAngles("TurretA", 0.0F, aircraft().FM.turret[0].tu[0], 0.0F);
            mesh.chunkSetAngles("TurretB", 0.0F, aircraft().FM.turret[0].tu[1], 0.0F);
            float f2 = waypointAzimuth();
            setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
            if(useRealisticNavigationInstruments())
            {
                setNew.waypointAzimuth.setDeg(f2 - 90F);
                setOld.waypointAzimuth.setDeg(f2 - 90F);
            } else
            {
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f2);
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;

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
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 23.913");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(aAim, tAim, 0.0F);
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

    public CockpitB25J15_Bombardier()
    {
        super("3DO/Cockpit/B-25J-Bombardier/BombardierB25J15.him", "bf109");
        bEntered = false;
        setOld = new Variables();
        setNew = new Variables();
        try
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (mesh)), "CAMERAAIM");
            hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), pos.getAbs(), loc);
            aAim = loc.getOrient().getAzimut();
            tAim = loc.getOrient().getTangage();
            kAim = loc.getOrient().getKren();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        cockpitNightMats = (new java.lang.String[] {
            "textrbm9", "texture25"
        });
        setNightMats(false);
        interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zSpeed1", 0.0F, floatindex(cvt(fm.getSpeedKMH(), 0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zAlt1", 0.0F, cvt((float)fm.Loc.z, 0.0F, 9144F, 0.0F, 10800F), 0.0F);
        mesh.chunkSetAngles("zAlt2", 0.0F, cvt((float)fm.Loc.z, 0.0F, 9144F, 0.0F, 1080F), 0.0F);
        mesh.chunkSetAngles("zHour", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zMinute", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zSecond", 0.0F, cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zCompass1", 0.0F, setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("zCompass2", 0.0F, setNew.waypointAzimuth.getDeg(0.1F), 0.0F);
        if(bEntered)
        {
            mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((com.maddox.il2.objects.air.B_25J15)aircraft()).fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
            boolean flag = ((com.maddox.il2.objects.air.B_25J15)aircraft()).fSightCurReadyness > 0.93F;
            mesh.chunkVisible("BlackBox", true);
            mesh.chunkVisible("zReticle", flag);
            mesh.chunkVisible("zAngleMark", flag);
        } else
        {
            mesh.chunkVisible("BlackBox", false);
            mesh.chunkVisible("zReticle", false);
            mesh.chunkVisible("zAngleMark", false);
        }
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
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XHullDamage1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XHullDamage2", true);
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

    private static final float angleScale[] = {
        -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64F, 67F, 70F, 72F, 73.25F, 
        75F, 76.5F, 77F, 78F, 79F, 80F
    };
    private static final float speedometerScale[] = {
        0.0F, 2.5F, 54F, 104F, 154.5F, 205.5F, 224F, 242F, 259.5F, 277.5F, 
        296.25F, 314F, 334F, 344.5F
    };
    private static float calibrAngle = 0.0F;
    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private static com.maddox.JGP.Point3d P1 = new Point3d();
    private static com.maddox.JGP.Vector3d V = new Vector3d();

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitB25J15_Bombardier.class)))), "astatePilotIndx", 0);
    }











}
