// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitTB_3_Bombardier.java

package com.maddox.il2.objects.air;

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
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, TB_3_4M_17, Cockpit

public class CockpitTB_3_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            resetYPRmodifier();
            float f = ((com.maddox.il2.objects.air.TB_3_4M_17)aircraft()).fSightCurSpeed;
            float f1 = ((com.maddox.il2.objects.air.TB_3_4M_17)aircraft()).fSightCurAltitude;
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
            if((double)java.lang.Math.abs(fm.Or.getKren()) < 30D)
                mesh.chunkSetAngles("Z_Compass1", 0.0F, -fm.Or.getYaw(), 0.0F);
            if((double)java.lang.Math.abs(fm.Or.getKren()) > 3.5D)
            {
                pencilDisp-= = 0.0004F * fm.Or.getKren();
                if(pencilDisp > 0.1725F)
                    pencilDisp = 0.1725F;
                if(pencilDisp < -0.2529F)
                    pencilDisp = -0.2529F;
                com.maddox.il2.objects.air.Cockpit.xyz[0] = 0.0F;
                com.maddox.il2.objects.air.Cockpit.xyz[1] = pencilDisp;
                mesh.chunkSetLocate("Z_Pencil1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                mesh.chunkSetAngles("Z_Pencilrot1", 0.0F, 11459.16F * pencilDisp, 0.0F);
            }
            mesh.chunkSetAngles("Z_ANO1", 0.0F, fm.AS.bNavLightsOn ? -50F : 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_CockpLight1", 0.0F, cockpitLightControl ? -50F : 0.0F, 0.0F);
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

    public CockpitTB_3_Bombardier()
    {
        super("3DO/Cockpit/TB-3-Bombardier/hier.him", "he111");
        pencilDisp = 0.0F;
        curAlt = 300F;
        curSpd = 50F;
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
            "BombGauges", "Gauge03"
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

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("HullDamage1", false);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("HullDamage2", false);
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("HullDamage3", false);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("HullDamage4", false);
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", false);
            mesh.chunkVisible("XGlassDamage2", false);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage3", false);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bEntered)
        {
            mesh.chunkVisible("BlackBox", true);
            mesh.chunkVisible("zReticle", true);
            mesh.chunkVisible("zScaleCurve", true);
            mesh.chunkVisible("zScaleM", true);
            mesh.chunkVisible("zScaleKM", true);
            mesh.chunkVisible("zScaleKMH", true);
        } else
        {
            mesh.chunkVisible("BlackBox", false);
            mesh.chunkVisible("zReticle", false);
            mesh.chunkVisible("zScaleCurve", false);
            mesh.chunkVisible("zScaleM", false);
            mesh.chunkVisible("zScaleKM", false);
            mesh.chunkVisible("zScaleKMH", false);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float pencilDisp;
    private float curAlt;
    private float curSpd;
    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitTB_3_Bombardier.class, "astatePilotIndx", 3);
    }







}
