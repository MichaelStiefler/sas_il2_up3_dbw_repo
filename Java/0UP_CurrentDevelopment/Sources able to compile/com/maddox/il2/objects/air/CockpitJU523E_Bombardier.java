package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
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

public class CockpitJU523E_Bombardier extends CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            float f = ((JU_52_3MG3E)aircraft()).fSightCurForwardAngle;
            float f1 = ((JU_52_3MG3E)aircraft()).fSightCurSideslip;
            mesh.chunkSetAngles("BlackBox", 0.0F, -f1, f);
            if(bEntered)
            {
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(aAim + f1, tAim + f, 0.0F);
            }
            mesh.chunkSetAngles("TurretA", 0.0F, aircraft().FM.turret[0].tu[0], 0.0F);
            mesh.chunkSetAngles("TurretB", 0.0F, aircraft().FM.turret[0].tu[1], 0.0F);
            return true;
        }

        Interpolater()
        {
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((JU_52_3MG3E)fm.actor).bPitUnfocused = false;
            bTurrVisible = aircraft().hierMesh().isChunkVisible("Turret1C_D0");
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
            enter();
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
            ((JU_52_3MG3E)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Turret1C_D0", bTurrVisible);
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
    }

    public CockpitJU523E_Bombardier()
    {
        super("3DO/Cockpit/He-111H-2-Bombardier/BombardierJU523E.him", "he111");
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
        if(bEntered)
        {
            mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((JU_52_3MG3E)aircraft()).fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
            boolean flag = ((JU_52_3MG3E)aircraft()).fSightCurReadyness > 0.93F;
            mesh.chunkVisible("BlackBox", true);
            mesh.chunkVisible("zReticle", flag);
            mesh.chunkVisible("zAngleMark", flag);
        } else
        {
            mesh.chunkVisible("BlackBox", false);
            mesh.chunkVisible("zReticle", false);
            mesh.chunkVisible("zAngleMark", false);
        }
        mesh.chunkSetAngles("zAltWheel", 0.0F, cvt(((JU_52_3MG3E)aircraft()).fSightCurAltitude, 0.0F, 10000F, 0.0F, 375.8333F), 0.0F);
        mesh.chunkSetAngles("zAnglePointer", 0.0F, ((JU_52_3MG3E)aircraft()).fSightCurForwardAngle, 0.0F);
        mesh.chunkSetAngles("zAngleWheel", 0.0F, -10F * ((JU_52_3MG3E)aircraft()).fSightCurForwardAngle, 0.0F);
        mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 400F, 0.0F, 16F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zSpeedPointer", 0.0F, cvt(((JU_52_3MG3E)aircraft()).fSightCurSpeed, 150F, 600F, 0.0F, 60F), 0.0F);
        mesh.chunkSetAngles("zSpeedWheel", 0.0F, 0.333F * ((JU_52_3MG3E)aircraft()).fSightCurSpeed, 0.0F);
        mesh.chunkSetAngles("zAlt1", 0.0F, cvt(fm.getAltitude(), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zAlt2", -cvt(fm.getAltitude(), 0.0F, 10000F, 0.0F, 180F), 0.0F, 0.0F);
        mesh.chunkVisible("zRed1", fm.CT.BayDoorControl > 0.66F);
        mesh.chunkVisible("zYellow1", fm.CT.BayDoorControl < 0.33F);
    }

    private static final float angleScale[] = {
        -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64F, 67F, 70F, 72F, 73.25F, 
        75F, 76.5F, 77F, 78F, 79F, 80F
    };
    private static final float speedometerScale[] = {
        0.0F, 0.1F, 19F, 37.25F, 63.5F, 91.5F, 112F, 135.5F, 159.5F, 186.5F, 
        213F, 238F, 264F, 289F, 314.5F, 339.5F, 359.5F, 360F, 360F, 360F
    };
    private boolean bTurrVisible;
    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;

    static 
    {
        com.maddox.rts.Property.set(CockpitJU523E_Bombardier.class, "astatePilotIndx", 0);
    }



}
