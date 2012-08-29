package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;

public class BombsightNordenCockpitLogic
{

    public BombsightNordenCockpitLogic()
    {
        m_Pit = null;
        m_aAim = 0.0F;
        m_tAim = 0.0F;
        m_kAim = 0.0F;
    }

    public BombsightNordenCockpitLogic(Cockpit cockpit)
    {
        m_Pit = cockpit;
        m_aAim = 0.0F;
        m_tAim = 0.0F;
        m_kAim = 0.0F;
    }

    public void setKoeffs(float f, float f1, float f2)
    {
        m_aAim = f;
        m_tAim = f1;
        m_kAim = f2;
    }

    public void OnTick(boolean flag)
    {
        if(null == m_Pit)
            return;
        float f = BombsightNorden.fSightCurForwardAngle;
        float f1 = BombsightNorden.fSightCurSideslip;
        float f2 = m_Pit.aircraft().FM.Or.getTangage();
        float f3 = 7.5F;
        float f4 = 0.0F;
        if(f2 > 0.0F)
            f4 = f2 >= f3 ? f3 : f2;
        else
            f4 = f2 <= -f3 ? -f3 : f2;
        m_Pit.mesh.chunkSetAngles("BlackBox", 0.0F, -f1, f - f4);
        if(flag)
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.setSimpleAimOrient(m_aAim + f1, (m_tAim + f) - f4, 0.0F);
        }
    }

    public void reflectWorldToInstruments(boolean flag)
    {
        if(null == m_Pit)
            return;
        if(flag)
        {
            m_Pit.mesh.chunkSetAngles("zAngleMark", -m_Pit.floatindex(m_Pit.cvt(BombsightNorden.fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
            boolean flag1 = BombsightNorden.fSightCurReadyness > 0.93F;
            m_Pit.mesh.chunkVisible("BlackBox", true);
            m_Pit.mesh.chunkVisible("zReticle", flag1);
            m_Pit.mesh.chunkVisible("zAngleMark", flag1);
        } else
        {
            m_Pit.mesh.chunkVisible("BlackBox", false);
            m_Pit.mesh.chunkVisible("zReticle", false);
            m_Pit.mesh.chunkVisible("zAngleMark", false);
        }
    }

    public void enter()
    {
        m_SaveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 23.913");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(m_aAim, m_tAim, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
    }

    public void leave()
    {
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
        com.maddox.rts.CmdEnv.top().exec("fov " + m_SaveFov);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        hookpilot.doAim(false);
        hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
        hookpilot.setSimpleUse(false);
        boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
        com.maddox.rts.HotKeyEnv.enable("PanView", flag);
        com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
    }

    private static final float angleScale[] = {
        -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64F, 67F, 70F, 72F, 73.25F, 
        75F, 76.5F, 77F, 78F, 79F, 80F
    };
    private float m_SaveFov;
    private float m_aAim;
    private float m_tAim;
    private float m_kAim;
    private Cockpit m_Pit;

}
