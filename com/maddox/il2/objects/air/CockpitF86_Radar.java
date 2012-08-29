// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitF86_Radar.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitF86_Radar extends com.maddox.il2.objects.air.CockpitPilot
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            enter();
            go_top();
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!((com.maddox.il2.objects.air.Cockpit)this).isFocused())
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
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
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

    private void go_top()
    {
        bBAiming = false;
        com.maddox.rts.CmdEnv.top().exec("fov 33.3");
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(false);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(aDiv, tDiv, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
    }

    private void go_bottom()
    {
        bBAiming = true;
        com.maddox.rts.CmdEnv.top().exec("fov 23.913");
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

    public void destroy()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
        if(!((com.maddox.il2.objects.air.Cockpit)this).isFocused())
            return;
        if(((com.maddox.il2.objects.air.CockpitPilot)this).isToggleAim() == flag)
            return;
        if(flag)
            go_bottom();
        else
            go_top();
    }

    public CockpitF86_Radar()
    {
        super("3DO/Cockpit/F86_Radar/hier.him", "he111");
        bEntered = false;
        bBAiming = false;
    }

    public void reflectWorldToInstruments(float f1)
    {
    }

    private float saveFov;
    private float aAim;
    private float tAim;
    private float aDiv;
    private float tDiv;
    private boolean bEntered;
    private boolean bBAiming;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "astatePilotIndx", 0);
    }
}
