// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitDB3M_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, IL_4_DB3M, Cockpit

public class CockpitDB3M_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            resetYPRmodifier();
            float f = ((com.maddox.il2.objects.air.IL_4_DB3M)aircraft()).fSightCurSpeed;
            float f1 = ((com.maddox.il2.objects.air.IL_4_DB3M)aircraft()).fSightCurAltitude;
            curAlt = (19F * curAlt + f1) / 20F;
            curSpd = (19F * curSpd + f) / 20F;
            mesh.chunkSetAngles("zScaleKM", 0.04F * curAlt, 0.0F, 0.0F);
            mesh.chunkSetAngles("zScaleM", 0.36F * curAlt, 0.0F, 0.0F);
            mesh.chunkSetAngles("zScaleKMH", -0.8F * (curSpd - 50F), 0.0F, 0.0F);
            float f2 = 0.5F * (float)java.lang.Math.tan(java.lang.Math.atan((83.333335876464844D * java.lang.Math.sqrt((2.0F * curAlt) / com.maddox.il2.fm.Atmosphere.g())) / (double)curAlt));
            float f3 = (float)java.lang.Math.tan(java.lang.Math.atan(((double)(curSpd / 3.6F) * java.lang.Math.sqrt((2.0F * curAlt) / com.maddox.il2.fm.Atmosphere.g())) / (double)curAlt));
            com.maddox.il2.objects.air.Cockpit.xyz[0] = -0.0005F * curAlt;
            com.maddox.il2.objects.air.Cockpit.xyz[1] = -1F * (f2 - f3);
            mesh.chunkSetLocate("zScaleCurve", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
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
        com.maddox.rts.CmdEnv.top().exec("fov 45.0");
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
            return;
        } else
        {
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

    public CockpitDB3M_Bombardier()
    {
        super("3DO/Cockpit/TB-3-Bombardier/hier.him", "he111");
        curAlt = 300F;
        curSpd = 50F;
        bEntered = false;
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
            "BombGauges", "Gauge03"
        });
        setNightMats(false);
        interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkVisible("BlackBox", true);
        mesh.chunkVisible("zReticle", true);
        mesh.chunkVisible("zScaleCurve", true);
        mesh.chunkVisible("zScaleM", true);
        mesh.chunkVisible("zScaleKM", true);
        mesh.chunkVisible("zScaleKMH", true);
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

    private float curAlt;
    private float curSpd;
    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitDB3M_Bombardier.class)))), "astatePilotIndx", 0);
    }




}
