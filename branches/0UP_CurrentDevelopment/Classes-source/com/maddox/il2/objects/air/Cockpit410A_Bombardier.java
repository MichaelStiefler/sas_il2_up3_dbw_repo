// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cockpit410A_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
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
//            CockpitPilot, ME_410A

public class Cockpit410A_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            float f = ((com.maddox.il2.objects.air.ME_410A)aircraft()).fSightCurForwardAngle;
            float f1 = ((com.maddox.il2.objects.air.ME_410A)aircraft()).fSightCurSideslip;
            mesh.chunkSetAngles("BlackBox", 0.0F, -f1, f);
            if(bEntered && bBAiming)
            {
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(aAim + f1, tAim + f, 0.0F);
            }
            float f2 = fm.getAltitude();
            float f3 = (float)(-(java.lang.Math.abs(fm.Vwld.length()) * java.lang.Math.sin(java.lang.Math.toRadians(java.lang.Math.abs(fm.Or.getTangage())))) * 0.10189999639987946D);
            f3 += (float)java.lang.Math.sqrt(f3 * f3 + 2.0F * f2 * 0.1019F);
            float f4 = java.lang.Math.abs((float)fm.Vwld.length()) * (float)java.lang.Math.cos(java.lang.Math.toRadians(java.lang.Math.abs(fm.Or.getTangage())));
            float f5 = (f4 * f3 + 10F) - 10F;
            alpha = 90F - java.lang.Math.abs(fm.Or.getTangage()) - (float)java.lang.Math.toDegrees(java.lang.Math.atan(f5 / f2));
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
            go_top();
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

    private void go_top()
    {
        bBAiming = false;
        com.maddox.rts.CmdEnv.top().exec("fov 30.0");
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(false);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(aDiv, tDiv, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
    }

    public void destroy()
    {
        super.destroy();
        leave();
    }

    public Cockpit410A_Bombardier()
    {
        super("3DO/Cockpit/Ar-234B-2-Bombardier/410A.him", "he111");
        bEntered = false;
        bBAiming = false;
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
        try
        {
            com.maddox.il2.engine.Loc loc1 = new Loc();
            com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(mesh, "CAMERA");
            hooknamed1.computePos(this, pos.getAbs(), loc1);
            aDiv = loc1.getOrient().getAzimut();
            tDiv = loc1.getOrient().getTangage();
            kDiv = loc1.getOrient().getKren();
        }
        catch(java.lang.Exception exception1)
        {
            java.lang.System.out.println(exception1.getMessage());
            exception1.printStackTrace();
        }
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bBAiming)
        {
            mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((com.maddox.il2.objects.air.ME_410A)aircraft()).fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
            boolean flag = ((com.maddox.il2.objects.air.ME_410A)aircraft()).fSightCurReadyness > 0.93F;
            mesh.chunkVisible("zReticle", flag);
            mesh.chunkVisible("zAngleMark", flag);
        } else
        {
            mesh.chunkSetAngles("zGSDimm", -alpha, 0.0F, 0.0F);
        }
    }

    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private float aDiv;
    private float tDiv;
    private float kDiv;
    private float alpha;
    private boolean bEntered;
    private boolean bBAiming;
    private static final float angleScale[] = {
        -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64F, 67F, 70F, 72F, 73.25F, 
        75F, 76.5F, 77F, 78F, 79F, 80F
    };

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "astatePilotIndx", 0);
    }





}
