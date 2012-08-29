// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitPZL37_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, PZL37B, Cockpit

public class CockpitPZL37_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bEntered)
            {
                float f = ((com.maddox.il2.objects.air.PZL37B)aircraft()).fSightCurForwardAngle;
                float f1 = -((com.maddox.il2.objects.air.PZL37B)aircraft()).fSightCurSideslip;
                mesh.chunkSetAngles("BlackBox", f1, 0.0F, -f);
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(-f1, tAim + f, 0.0F);
            }
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
        if(((com.maddox.il2.objects.air.Cockpit)this).isFocused())
        {
            leave();
            super.doFocusLeave();
        }
    }

    private void enter()
    {
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 50.0");
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
        if(bEntered)
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
        }
    }

    public void destroy()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
    }

    public CockpitPZL37_Bombardier()
    {
        super("3DO/Cockpit/Pe-2-Bombardier/BombardierPZL37.him", "he111");
        bEntered = false;
        try
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (((com.maddox.il2.objects.air.Cockpit)this).mesh)), "CAMERA");
            hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Actor)this).pos.getAbs(), loc);
            aAim = loc.getOrient().getAzimut();
            tAim = loc.getOrient().getTangage();
            kAim = loc.getOrient().getKren();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(((java.lang.Throwable) (exception)).getMessage());
            ((java.lang.Throwable) (exception)).printStackTrace();
        }
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void reflectWorldToInstruments(float f)
    {
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zMark1", ((com.maddox.il2.objects.air.PZL37B)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).fSightCurForwardAngle * 3.675F, 0.0F, 0.0F);
        float f1 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.PZL37B)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).fSightSetForwardAngle, -15F, 75F, -15F, 75F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zMark2", f1 * 3.675F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren() * java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren()), -1225F, 1225F, -0.23F, 0.23F);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt((((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage() - 1.0F) * java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getTangage() - 1.0F), -1225F, 1225F, 0.23F, -0.23F);
        com.maddox.il2.objects.air.Cockpit.ypr[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren(), -45F, 45F, -180F, 180F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("zBulb", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.objects.air.Cockpit.xyz[0], -0.23F, 0.23F, 0.0095F, -0.0095F);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.objects.air.Cockpit.xyz[1], -0.23F, 0.23F, 0.0095F, -0.0095F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("zRefraction", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "astatePilotIndx", 0);
    }


}
