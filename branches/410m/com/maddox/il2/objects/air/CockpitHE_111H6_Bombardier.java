// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHE_111H6_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
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
//            CockpitPilot, HE_111, HE_111H6, Aircraft, 
//            AircraftLH

public class CockpitHE_111H6_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(doToKGCheck)
            {
                doToKGCheck = false;
                if((aircraft() instanceof com.maddox.il2.objects.air.HE_111H6) && ((com.maddox.il2.objects.air.HE_111H6)aircraft()).hasToKG)
                {
                    hasToKG = true;
                    showToKG();
                }
            }
            float f = ((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurForwardAngle;
            float f1 = ((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurSideslip;
            mesh.chunkSetAngles("BlackBox", -10F * f1, 0.0F, f);
            if(bEntered && !hasToKG)
            {
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(aAim + 10F * f1, tAim + f, 0.0F);
            }
            mesh.chunkSetAngles("TurretA", 0.0F, aircraft().FM.turret[0].tu[0], 0.0F);
            mesh.chunkSetAngles("TurretB", 0.0F, aircraft().FM.turret[0].tu[1], 0.0F);
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            float f2 = waypointAzimuthInvertMinus(30F);
            if(useRealisticNavigationInstruments())
            {
                setNew.waypointAzimuth.setDeg(f2 - 90F);
                setOld.waypointAzimuth.setDeg(f2 - 90F);
                setNew.radioCompassAzimuth.setDeg(setOld.radioCompassAzimuth.getDeg(0.02F), radioCompassAzimuthInvertMinus() - setOld.azimuth.getDeg(1.0F) - 90F);
            } else
            {
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), f2 - setOld.azimuth.getDeg(1.0F));
            }
            setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
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
        com.maddox.il2.ai.AnglesFork radioCompassAzimuth;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
            radioCompassAzimuth = new AnglesFork();
        }

    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.objects.air.HE_111)(com.maddox.il2.objects.air.HE_111)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Cockpit_D0", false);
            aircraft().hierMesh().chunkVisible("Turret1C_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot1_FAK", false);
            aircraft().hierMesh().chunkVisible("Head1_FAK", false);
            aircraft().hierMesh().chunkVisible("Pilot1_FAL", false);
            aircraft().hierMesh().chunkVisible("Pilot2_FAK", false);
            aircraft().hierMesh().chunkVisible("Pilot2_FAL", false);
            aircraft().hierMesh().chunkVisible("Turret1C_D0", false);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setMinMax(155F, -89F, 89F);
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
            ((com.maddox.il2.objects.air.HE_111)(com.maddox.il2.objects.air.HE_111)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Cockpit_D0", aircraft().hierMesh().isChunkVisible("Nose_D0") || aircraft().hierMesh().isChunkVisible("Nose_D1") || aircraft().hierMesh().isChunkVisible("Nose_D2"));
            aircraft().hierMesh().chunkVisible("Turret1C_D0", aircraft().hierMesh().isChunkVisible("Turret1B_D0"));
            aircraft().hierMesh().chunkVisible("Pilot1_FAK", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
            aircraft().hierMesh().chunkVisible("Head1_FAK", aircraft().hierMesh().isChunkVisible("Head1_D0"));
            aircraft().hierMesh().chunkVisible("Pilot1_FAL", aircraft().hierMesh().isChunkVisible("Pilot1_D1"));
            aircraft().hierMesh().chunkVisible("Pilot2_FAK", aircraft().hierMesh().isChunkVisible("Pilot2_D0"));
            aircraft().hierMesh().chunkVisible("Pilot2_FAL", aircraft().hierMesh().isChunkVisible("Pilot2_D1"));
            leave();
            super.doFocusLeave();
            return;
        }
    }

    private void enter()
    {
        if(hasToKG)
        {
            saveFov = com.maddox.il2.game.Main3D.FOVX;
            com.maddox.rts.CmdEnv.top().exec("fov 5.0");
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            if(hookpilot.isPadlock())
                hookpilot.stopPadlock();
            hookpilot.doAim(true);
            hookpilot.setAim(new Point3d(5.75D, -0.14599999785423279D, 0.18000000715255737D));
            hookpilot.setSimpleUse(true);
            hookpilot.setSimpleAimOrient(0.0F, -3F, 0.0F);
            com.maddox.rts.HotKeyEnv.enable("PanView", false);
            com.maddox.rts.HotKeyEnv.enable("SnapView", false);
            mesh.chunkVisible("Binoculars", true);
            bEntered = true;
        } else
        {
            saveFov = com.maddox.il2.game.Main3D.FOVX;
            com.maddox.rts.CmdEnv.top().exec("fov 23.913");
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot1 = com.maddox.il2.engine.hotkey.HookPilot.current;
            if(hookpilot1.isPadlock())
                hookpilot1.stopPadlock();
            hookpilot1.doAim(true);
            hookpilot1.setSimpleUse(true);
            hookpilot1.setSimpleAimOrient(aAim, tAim, 0.0F);
            com.maddox.rts.HotKeyEnv.enable("PanView", false);
            com.maddox.rts.HotKeyEnv.enable("SnapView", false);
            bEntered = true;
        }
    }

    private void leave()
    {
        if(!bEntered)
            return;
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
        com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        hookpilot.doAim(false);
        if(hasToKG)
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
        else
            hookpilot.setSimpleAimOrient(0.0F, -30F, 0.0F);
        hookpilot.setMinMax(155F, -89F, 89F);
        hookpilot.setSimpleUse(false);
        boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
        com.maddox.rts.HotKeyEnv.enable("PanView", flag);
        com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
        mesh.chunkVisible("Binoculars", false);
        bEntered = false;
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

    public CockpitHE_111H6_Bombardier()
    {
        super("3DO/Cockpit/He-111H-6-Bombardier/hier.him", "he111");
        doToKGCheck = true;
        hasToKG = false;
        bEntered = false;
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
        cockpitNightMats = (new java.lang.String[] {
            "clocks1", "clocks2", "clocks4", "clocks5", "TorpBox", "Z_Angle"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        setOld = new Variables();
        setNew = new Variables();
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    public CockpitHE_111H6_Bombardier(java.lang.String s)
    {
        super(s, "he111");
        doToKGCheck = true;
        hasToKG = false;
        bEntered = false;
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
        cockpitNightMats = (new java.lang.String[] {
            "clocks1", "clocks2", "clocks4", "clocks5"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        setOld = new Variables();
        setNew = new Variables();
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
    }

    private void showToKG()
    {
        mesh.chunkVisible("TorpedoBoxNew", true);
        mesh.chunkVisible("ToKGMounts", true);
        mesh.chunkVisible("speedDial", true);
        mesh.chunkVisible("angleDial", true);
        mesh.chunkVisible("Ship", true);
        mesh.chunkVisible("Z_Angle", true);
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
        if(bEntered && !hasToKG)
        {
            mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
            boolean flag = ((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurReadyness > 0.93F;
            mesh.chunkVisible("BlackBox", true);
            mesh.chunkVisible("zReticle", flag);
            mesh.chunkVisible("zAngleMark", flag);
        } else
        {
            mesh.chunkVisible("BlackBox", false);
            mesh.chunkVisible("zReticle", false);
            mesh.chunkVisible("zAngleMark", false);
        }
        mesh.chunkSetAngles("zAltWheel", 0.0F, cvt(((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurAltitude, 0.0F, 10000F, 0.0F, 375.8333F), 0.0F);
        mesh.chunkSetAngles("zAnglePointer", 0.0F, ((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurForwardAngle, 0.0F);
        mesh.chunkSetAngles("zAngleWheel", 0.0F, -10F * ((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurForwardAngle, 0.0F);
        mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 400F, 0.0F, 16F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zSpeedPointer", 0.0F, cvt(((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurSpeed, 150F, 600F, 0.0F, 60F), 0.0F);
        mesh.chunkSetAngles("zSpeedWheel", 0.0F, 0.333F * ((com.maddox.il2.objects.air.HE_111)aircraft()).fSightCurSpeed, 0.0F);
        mesh.chunkSetAngles("zAlt1", 0.0F, cvt(fm.getAltitude(), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zAlt2", -cvt(fm.getAltitude(), 0.0F, 10000F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkVisible("zRed1", fm.CT.BayDoorControl > 0.66F);
        mesh.chunkVisible("zYellow1", fm.CT.BayDoorControl < 0.33F);
        mesh.chunkSetAngles("TurretA", 0.0F, fm.turret[0].tu[2], 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, fm.turret[0].tu[1], 0.0F);
        if(useRealisticNavigationInstruments())
        {
            mesh.chunkSetAngles("Z_Compass3", setNew.azimuth.getDeg(f) - setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass4", -setNew.waypointAzimuth.getDeg(f) - 90F, 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass5", setNew.radioCompassAzimuth.getDeg(f * 0.02F) + 90F, 0.0F, 0.0F);
        } else
        {
            mesh.chunkSetAngles("Z_Compass4", -setNew.azimuth.getDeg(f) - 90F, 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_Compass3", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        }
        if(hasToKG)
        {
            float f1 = ((com.maddox.il2.objects.air.HE_111H6)aircraft()).fAOB;
            float f2 = ((com.maddox.il2.objects.air.HE_111H6)aircraft()).fShipSpeed;
            float f3 = ((com.maddox.il2.objects.air.HE_111H6)aircraft()).FM.AS.getGyroAngle();
            mesh.chunkSetAngles("Ship", 0.0F, -f1, 0.0F);
            mesh.chunkSetAngles("speedDial", 0.0F, 6.4F * f2, 0.0F);
            mesh.chunkSetAngles("Z_Angle", 0.0F, f3, 0.0F);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean doToKGCheck;
    private boolean hasToKG;
    private static final float angleScale[] = {
        -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64F, 67F, 70F, 72F, 73.25F, 
        75F, 76.5F, 77F, 78F, 79F, 80F
    };
    private static final float speedometerScale[] = {
        0.0F, 0.1F, 19F, 37.25F, 63.5F, 91.5F, 112F, 135.5F, 159.5F, 186.5F, 
        213F, 238F, 264F, 289F, 314.5F, 339.5F, 359.5F, 360F, 360F, 360F
    };
    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111H6_Bombardier.class, "astatePilotIndx", 0);
    }














}
