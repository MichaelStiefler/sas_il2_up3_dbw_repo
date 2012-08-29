// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitBLENHEIM4_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, BLENHEIM4, Cockpit

public class CockpitBLENHEIM4_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bEntered)
            {
                float f = ((com.maddox.il2.objects.air.BLENHEIM4)aircraft()).fSightCurForwardAngle;
                float f1 = -((com.maddox.il2.objects.air.BLENHEIM4)aircraft()).fSightCurSideslip;
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
        if(isFocused())
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
        super.destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
    }

    public CockpitBLENHEIM4_Bombardier()
    {
        super("3DO/Cockpit/Pe-2-Bombardier/BombardierBLENHEIM4.him", "he111");
        bEntered = false;
        try
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "CAMERA");
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
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("zMark1", ((com.maddox.il2.objects.air.BLENHEIM4)aircraft()).fSightCurForwardAngle * 3.675F, 0.0F, 0.0F);
        float f1 = cvt(((com.maddox.il2.objects.air.BLENHEIM4)aircraft()).fSightSetForwardAngle, -15F, 75F, -15F, 75F);
        mesh.chunkSetAngles("zMark2", f1 * 3.675F, 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.Or.getKren() * java.lang.Math.abs(fm.Or.getKren()), -1225F, 1225F, -0.23F, 0.23F);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt((fm.Or.getTangage() - 1.0F) * java.lang.Math.abs(fm.Or.getTangage() - 1.0F), -1225F, 1225F, 0.23F, -0.23F);
        com.maddox.il2.objects.air.Cockpit.ypr[0] = cvt(fm.Or.getKren(), -45F, 45F, -180F, 180F);
        mesh.chunkSetLocate("zBulb", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(com.maddox.il2.objects.air.Cockpit.xyz[0], -0.23F, 0.23F, 0.0095F, -0.0095F);
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(com.maddox.il2.objects.air.Cockpit.xyz[1], -0.23F, 0.23F, 0.0095F, -0.0095F);
        mesh.chunkSetLocate("zRefraction", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "astatePilotIndx", 0);
    }


}
